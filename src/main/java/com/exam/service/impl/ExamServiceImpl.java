package com.exam.service.impl;

import com.exam.data.dto.MessageResponse;
import com.exam.data.dto.PaginationDTO;
import com.exam.data.dto.QuestionDTO;
import com.exam.data.dto.exam.ExamBasicInformationDTO;
import com.exam.data.dto.exam.ExamDTO;
import com.exam.data.dto.exam.ExamShowDTO;
import com.exam.data.enity.*;
import com.exam.data.mapper.ExamMapper;
import com.exam.data.mapper.QuestionMapper;
import com.exam.data.repository.*;
import com.exam.enumeration.ERole;
import com.exam.exception.AccessDeniedException;
import com.exam.exception.ResourceNotFoundException;
import com.exam.service.ExamService;
import com.exam.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class ExamServiceImpl implements ExamService {
    @Autowired
    private ExamRepository examRepository;
    @Autowired
    private ExamMapper examMapper;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private LecturerRepository lecturerRepository;
    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private AllowedStudentRepository allowedStudentRepository;
    @Autowired
    private QuestionServiceImpl questionService;
    @Autowired
    private ExamResultRepository examResultRepository;

    private static final String CHARACTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final int LENGTH = 8;

    public String generateRandomString() {
        Random random = new Random();
        StringBuilder sb = new StringBuilder(LENGTH);

        for (int i = 0; i < LENGTH; i++) {

            int index = random.nextInt(CHARACTERS.length());
            sb.append(CHARACTERS.charAt(index));
        }

        return sb.toString();
    }

    public String createExam(ExamDTO examDTO) {
        String code;
        do {

            code = generateRandomString();
        } while (examRepository.existsByCode(code)); // loop the genaration code to create new code Exam

        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(email).orElseThrow(
                () -> new ResourceNotFoundException(Collections.singletonMap("message", "Not authentication"))
        );
        Lecturer lecturer = null;

        if (user.getRole().getId() != ERole.roleAdmin) { // if role = role admin => set lecturer = null

            lecturer = lecturerRepository.findByUserId(user.getId()).orElse(null);
        }

        Exam exam = examMapper.toEntity(examDTO);
        exam.setCode(code);
        exam.setLecturer(lecturer);

        Exam savedExam = examRepository.save(exam); //save exam => get id saved

        List<Question> list = examDTO.getQuestionDTOS().stream().map(
                questionDTO -> {
                    Question question = questionMapper.toEntity(questionDTO);
                    question.setExam(savedExam);

                    return question;
                }).collect(Collectors.toList()); //map questionDTO => question

        questionRepository.saveAll(list);

        return code;
    }

    public ExamShowDTO findTheExam(String code) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(email).orElseThrow(
                () -> new ResourceNotFoundException(Collections.singletonMap("message", "Not authentication")));

        Student student = studentRepository.findByUserId(user.getId()).orElseThrow(
                () -> new AccessDeniedException(Collections.singletonMap("message", "You aren't a student"))
        );

        Exam exam = examRepository.findByCode(code).orElseThrow(
                () -> new ResourceNotFoundException(Collections.singletonMap("exam code: ", code)));

        boolean isAllowed = allowedStudentRepository.existsByExamIdAndStudentId(exam.getId(), student.getId());
        int ZERO = 0;

        // if student not exists the allowed or exam expried
        if(!isAllowed || LocalDate.now().compareTo(exam.getExpiryDate()) > ZERO ) {

            throw new AccessDeniedException(Collections.singletonMap("message", "you aren't allowed to participate"));
        }

        List<Question> questions = questionRepository.findAllByExamId(exam.getId());
        Collections.shuffle(questions);
        List<QuestionDTO> shuffledQuestionDTOs = questions.stream().map(question -> questionMapper.toDTO(question))
                .collect(Collectors.toList());

        List<Long> positionQuestions = shuffledQuestionDTOs.stream().map(question -> question.getId())
                .collect(Collectors.toList());

        ExamShowDTO examShowDTO = examMapper.toDTOShow(exam);

        examShowDTO.getExamDTO().setQuestionDTOS(shuffledQuestionDTOs);
        examShowDTO.setPositionQuestions(positionQuestions);
        examShowDTO.setRandomCode(generateRandomString());

        return examShowDTO;
    }

    public PaginationDTO findAllExam(int pageNumber, int pageSize) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(email).orElseThrow(
                () -> new ResourceNotFoundException(Collections.singletonMap("message", "Not authentication")));

        Lecturer lecturer = lecturerRepository.findByUserId(user.getId()).orElseThrow(
                () -> new ResourceNotFoundException(Collections.singletonMap("message", "lecture is null"))
        );

        Page<ExamBasicInformationDTO> page = examRepository.findAllByLecturerId(
                lecturer.getId(), PageRequest.of(pageNumber, pageSize)).map(exam -> examMapper.toDTOBasic(exam));

        return new PaginationDTO(page.getContent(), page.isFirst(), page.isLast(),
                page.getTotalPages(), page.getTotalElements(), page.getNumber(), page.getSize());
    }

    public ExamDTO findExamById(long id) {
        List<QuestionDTO> questionDTOS = questionRepository.findAllByExamId(id).stream().map(
                question -> questionMapper.toDTO(question)).collect(Collectors.toList());

        Exam exam = examRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(Collections.singletonMap("exam id:", id))
        );

        ExamDTO examDTO = examMapper.toDTO(exam);
        examDTO.setQuestionDTOS(questionDTOS);

        return examDTO;
    }

    public MessageResponse deleteExamById(long id) {
        Exam exam = examRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(Collections.singletonMap("exam id:", id))
        );


        Lecturer lecturer = questionService.getLecturerByToken();
        if (lecturer.getId() != exam.getLecturer().getId()) { // can't update anyone's exam
            throw new AccessDeniedException(Collections.singletonMap("message", "The exam does not belong to you"));
        }

        if (examResultRepository.existsByExamId(exam.getId()))
            throw new AccessDeniedException(Collections.singletonMap("message", "The exam has been token by student"));

        questionRepository.deleteAllByExamId(exam.getId());
        examRepository.deleteById(id);

        return new MessageResponse(HttpServletResponse.SC_OK, "successfully");
    }
}

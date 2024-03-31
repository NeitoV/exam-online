package com.exam.service.impl;

import com.exam.data.dto.StudentClassDTO;
import com.exam.data.dto.MessageResponse;

import com.exam.data.enity.StudentClass;
import com.exam.data.repository.StudentClassRepository;
import com.exam.enumeration.EConstantNumber;
import com.exam.exception.ConflictException;
import com.exam.service.StudentClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.Collections;

import java.util.UUID;

@Service
public class StudentClassServiceImpl implements StudentClassService {
    @Autowired
    private StudentClassRepository myClassRepository;

    public static String generateRandomString(int length) {
        UUID uuid = UUID.randomUUID();

        String randomString = uuid.toString().replaceAll("-", "");
        randomString = randomString.substring(0, length);

        return randomString;
    }

    @Override
    public String saveNewClass(StudentClassDTO classDTO) {
        if (myClassRepository.findByName(classDTO.getName()).isPresent())
            throw new ConflictException(Collections.singletonMap("Name: ", classDTO.getName()));

            StudentClass myClass = new StudentClass();
            int countExistCode;
            String randomCode;
            do {
                randomCode = generateRandomString(EConstantNumber.MaxLengthCode);
                countExistCode = myClassRepository.countByCode(randomCode);
                myClass.setCode(randomCode);
                myClass.setName(classDTO.getName());
            } while (countExistCode != 0);
            myClassRepository.save(myClass);

        //return new MessageResponse(HttpServletResponse.SC_OK, "Create Class successful with class code " + randomCode);
        return randomCode;
    }
}

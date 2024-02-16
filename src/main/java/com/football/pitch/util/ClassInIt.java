package com.football.pitch.util;

import com.football.pitch.data.enity.*;
import com.football.pitch.data.repository.*;
import com.football.pitch.enumeration.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Component
public class ClassInIt {
    @Autowired
    private TypePitchRepository typePitchRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private DurationRepository durationRepository;
    @Autowired
    private PriceRepository priceRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @Autowired
    private PeriodRepository periodRepository;
    @Autowired
    private StatusRepository statusRepository;

    private final int interval = 90;
    private final LocalTime startTime = LocalTime.of(6, 00);
    private LocalTime currentTime = startTime;
    private int count = 0;
    private final int numberOfLoop = 12;



    @PostConstruct
    void inIt() {
        inIt_TypePitch();
        inIt_Role();
        inIt_User();
        inIt_Period_Duration();
        inIt_Price();
        inIt_Status();
    }

    private void inIt_TypePitch() {
        for (ETypePitch eTypePitch : ETypePitch.values()) {

            if (!typePitchRepository.existsByName(eTypePitch.toString())) {
                Type type = new Type();
                type.setName(eTypePitch.toString());

                typePitchRepository.save(type);
            }
        }
    }

    private void inIt_Role() {
        for (ERole eRole : ERole.values()) {

            if (!roleRepository.existsByName(eRole.toString())) {
                Role role = new Role();
                role.setName(eRole.toString());

                roleRepository.save(role);
            }
        }
    }

    private void inIt_Period_Duration() {

        for (EPeriod ePeriod : EPeriod.values()) {

            if (!periodRepository.existsByName(ePeriod.toString())) {
                Period period = new Period();
                period.setName(ePeriod.toString());

                periodRepository.save(period);
            }
        }

        while (count < numberOfLoop) {

            if (!durationRepository.existsByStartTime(currentTime)) {
                Duration duration = new Duration();

                duration.setStartTime(currentTime);
                duration.setEndTime(currentTime.plus(interval, ChronoUnit.MINUTES));

                if(count < 4) {
                    Period period = periodRepository.findById(EPeriod.morning).orElse(null);
                    duration.setPeriod(period);
                } else if (count < 7) {
                    Period period = periodRepository.findById(EPeriod.afternoon).orElse(null);
                    duration.setPeriod(period);
                } else if (count < 10) {
                    Period period = periodRepository.findById(EPeriod.evening).orElse(null);
                    duration.setPeriod(period);
                } else {
                    Period period = periodRepository.findById(EPeriod.midnight).orElse(null);
                    duration.setPeriod(period);
                }

                durationRepository.save(duration);
            }

            currentTime = currentTime.plus(interval, ChronoUnit.MINUTES);
            count++;
        }
    }

    private void inIt_Price() {
        List<Type> types = typePitchRepository.findAllByOrderByIdDesc();
        List<Period> periods = periodRepository.findAllByOrderByIdDesc();

        for (Type type : types) {
            for (Period period : periods) {

                if (!priceRepository.existsByPeriodIdAndTypeId(period.getId(), type.getId())) {
                    Price price = new Price();

                    price.setType(type);
                    price.setPeriod(period);
                    price.setPricePerHour(setPrice(type, period.getName()));

                    priceRepository.save(price);
                }
            }
        }
    }

    private BigDecimal setPrice(Type type, String per) {
        BigDecimal price = BigDecimal.ZERO;
        String typeName = type.getName();

        switch (typeName) {
            case "Five a side":
                price = getPriceFromFiveSideEnum(per);
                break;
            case "Seven a side":
                price = getPriceFromSevenSideEnum(per);
                break;
            case "Eleven a side":
                price = getPriceFromElevenSideEnum(per);
                break;
            default:
                break;
        }

        return price;
    }

    private BigDecimal getPriceFromFiveSideEnum(String per) {
        EPriceOfFiveSide priceEnum = EPriceOfFiveSide.valueOf(per.toUpperCase());

        return BigDecimal.valueOf(priceEnum.getValue());
    }

    private BigDecimal getPriceFromSevenSideEnum(String per) {
        EPriceOfSevenSide priceEnum = EPriceOfSevenSide.valueOf(per.toUpperCase());

        return BigDecimal.valueOf(priceEnum.getValue());
    }

    private BigDecimal getPriceFromElevenSideEnum(String per) {
        EPriceOfElevenSide priceEnum = EPriceOfElevenSide.valueOf(per.toUpperCase());

        return BigDecimal.valueOf(priceEnum.getValue());
    }

    private void inIt_User() {
        String email = "admin@gmail.com";
        String password = "123456";

        if (!userRepository.existsByEmail(email)) {
            User user = new User();

            user.setEmail(email);
            user.setPassword(passwordEncoder.encode(password));
            user.setRole(roleRepository.findById(ERole.roleAdmin).orElse(null));

            userRepository.save(user);
        }
    }

    private void inIt_Status() {
        for(EStatus eStatus: EStatus.values()) {
            if(!statusRepository.existsByName(eStatus.toString())) {
                Status status = new Status();

                status.setName(eStatus.toString());
                statusRepository.save(status);
            }
        }
    }
}

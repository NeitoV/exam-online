package com.football.pitch.data.dto.booking;

import com.football.pitch.data.dto.StatusDTO;
import com.football.pitch.data.dto.customer.CustomerDTO;
import com.football.pitch.data.dto.pitch.PitchDetailsShowDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingShowDTO {
    private long id;
    private PitchDetailsShowDTO pitchDetailsShowDTO;
    private Date date;
    private CustomerDTO customerDTO;
    private StatusDTO statusDTO;
    private BigDecimal totalRevenue;
}

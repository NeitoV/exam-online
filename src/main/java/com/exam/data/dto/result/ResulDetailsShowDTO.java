package com.exam.data.dto.result;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResulDetailsShowDTO {
    private StudentResultDTO studentResultDTO;
    private List<ResultDetailsDTO> resultDetailsDTOList;
}

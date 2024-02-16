package com.football.pitch.data.dto.pitch;

import com.football.pitch.data.dto.TypeDTO;
import com.football.pitch.data.dto.manager.ManagerDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PitchShowDTO {
    private Long id;
    private String name;
    private String description;
    private ManagerDTO managerDTO;
    private TypeDTO typeDTO;
    private Float averageRating;
    private String picture;
    private String url;
}

package com.football.pitch.data.dto.pitch;

import com.football.pitch.data.dto.TypeDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PitchDTO {
    private long id;
    private String name;
    private String description;
    private TypeDTO typeDTO;
    private String picture;
    private String url;
}

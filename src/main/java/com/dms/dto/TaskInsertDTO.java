package com.dms.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TaskInsertDTO {


    @NotNull
    @Size(min = 1)
    private String noteTitle;

    @NotNull
    @Size(min = 1)
    private String description;

    @NotNull
    private Long cid;

    @NotNull
    private LocalDateTime inDue;


}

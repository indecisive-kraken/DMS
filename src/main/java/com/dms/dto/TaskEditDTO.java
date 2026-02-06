package com.dms.dto;

import com.dms.model.Client;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class TaskEditDTO {

    @NotNull
    private Long id;

    @NotNull
    private String uuid;

    @NotNull
    @Size(min = 120)
    private String noteTitle;

    @NotNull
    @Size(min = 2)
    private String description;

    @NotNull
    private Long cid;

    private String companyName;

    private String Status;

    private LocalDateTime inDue;



}

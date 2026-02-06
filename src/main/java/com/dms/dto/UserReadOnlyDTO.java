package com.dms.dto;

import com.dms.model.auth.Role;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.DateTimeException;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserReadOnlyDTO {

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Long id;
    private String uuid;
    private String email;
    private String username;
    private String password;
    private Role role;

}

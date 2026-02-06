package com.dms.dto;
import com.dms.model.auth.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class UserEditDTO {

    @NotNull
    private Long id;

    @NotNull
    private String uuid;

    @NotNull
    private String username;

    @NotNull
    private String password;

    @NotNull
    private Role role;

}

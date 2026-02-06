package com.dms.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserInsertDTO {

    @NotNull(message = "Username cannot be null")
    @Size(min = 2, max = 20, message = "Το username πρέπει να είναι μεταξύ 2 - 20 χαρακτήρες.")
    private String username;

    @NotNull(message = "Password cannot be null.")
    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&+=])^.{8,}$",
            message = "Το password πρέπει να περιέχει τουλάχιστον 1 πεζό, 1 κεφαλαίο, 1 ψηφίο και 1 ειδικό χαρακτήρα χωρίς κενά.")
    private String password;

//    @NotNull(message = "email is required")
    @Pattern(regexp = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$", message = "Invalid email")
    private String email;

    @NotNull(message = "Role cannot be null")
    private Long roleId;

    @NotNull(message = "Do not ever think of leaving this null..")
    private String uuid;
}

package com.phunghung29.microservice.user.dto;

import com.phunghung29.microservice.user.entities.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserRegisterDTO {
    private String roleName = "Customer";

    @NotEmpty(message = "Username can not be empty")
    @NotBlank(message = "Username can not be blank")
    @Pattern(regexp = "^\\w*$", message = "Username does not match with format")
    private String username;

    @NotEmpty(message = "Email can not be empty")
    @NotBlank(message = "Email can not be blank")
    @Pattern(regexp = "^[_a-z\\d-]+(\\.[_a-z\\d-]+)*@"
            + "[a-z\\d-]+(\\.[a-z\\d]+)*(\\.[a-z]{2,})$", message = "Email format is incorrect")
    private String email;

    @NotBlank(message = "Phone can not be blank")
    @Pattern(regexp = "^\\d*$", message = "Phone must have digit only")
    @Size(min = 10, max = 10, message = "Phone must be 10 digits")
    private String phone;

    @NotEmpty(message = "Password can not be empty")
    @NotBlank(message = "Password can not be blank")
    @Size(min = 8, max = 16, message = "Password size out of require (8-16)")
    @Pattern(regexp = "(?=.*\\d).+", message = "Password must have at least one of digits (0-9)")
    @Pattern(regexp = "(?=.*[a-z]).+", message = "Password must have at least one of lowers (a-z)")
    @Pattern(regexp = "(?=.*[A-Z]).+", message = "Password must have at least one of uppers (A-Z)")
    @Pattern(regexp = "(?=.*[_@$#]).+", message = "Password must have at least one of @ # $")
    @Pattern(regexp = "^[\\da-zA-Z_@$#]*$", message = "Password must have at least one of digits (0-9) lowers (a-z) uppers (A-Z) and @ # $")
    private String password;

    private Integer age;
    private Integer gender;
}

package com.test.bankkata.api.dto.requests;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
public class AccountCreationDto {

    @NotBlank(message = "The first name is required : please provide  one")
    @Size(max = 50, message = "The first name should not be over 50 parameter")
    private String firstName;
    @NotBlank(message = "The last name is required please provide  one")
    @Size(max = 50, message = "The last name should not be over 50 characters")
    private String lastName;

    @NotBlank(message = "The last name is required : please provide one")
    @Size(max = 50, message = "The last name should not be over 50 characters")
    @Pattern(regexp = "RUNNING|SAVING|running|saving")
    private String type;

    public AccountCreationDto(String firstName, String lastName, String type) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.type = type;
    }
}

package com.test.bankkata.repositories.dto.requests;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Setter
public class AccountCreationDto {

    @NotBlank(message = "The first name is required : please provide  one")
    @Size(max = 50, message = "The first name should not be over 50 parameter")
    private final String firstName;
    @NotBlank(message = "The last name is required please provide  one")
    @Size(max = 50, message = "The last name should not be over 50 characters")
    private final String lastName;

    @NotBlank(message = "The last name is required : please provide one")
    @Size(max = 50, message = "The last name should not be over 50 characters")
    @Pattern(regexp = "RUNNING|SAVING|running|saving")
    private final String type;

    public AccountCreationDto(String firstName, String lastName, String type) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.type = type;
    }

}

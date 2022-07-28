package com.test.bankkata.api.dto.requests;

import com.test.bankkata.api.dto.validations.CodeInjectionConstraint;
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

    /**
     * First name of the owner
     */

    @NotBlank(message = "The first name is required : please provide  one")
    @Size(max = 50, message = "The first name should not be over 50 parameter")
    @CodeInjectionConstraint(message = "The name should not contain links or scripts")
    private String firstName;
    /**
     * Last name of the owner
     */

    @NotBlank(message = "The last name is required please provide  one")
    @Size(max = 50, message = "The last name should not be over 50 characters")
    @CodeInjectionConstraint(message = "The lastName should not contain links or scripts")
    private String lastName;

    /**
     * The account type (Running or saving)
     */
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

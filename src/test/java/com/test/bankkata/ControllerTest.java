package com.test.bankkata;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.bankkata.api.Controller;
import com.test.bankkata.api.dto.requests.AccountCreationDto;
import com.test.bankkata.api.dto.responses.ResponseMessage;
import com.test.bankkata.api.exceptionHandlers.GlobalExceptionHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.Assert;
import org.assertj.core.api.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class ControllerTest {
    private Controller controller;
    private MockMvc mvc;
    @BeforeEach
    public  void prepare(){
        controller = new Controller();
        mvc = MockMvcBuilders.standaloneSetup(controller)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    @Test
    public void createControllerShouldReturnCreatedAccount() throws Exception {
        var objectMapper = new ObjectMapper();
        var accountCreation= objectMapper.writeValueAsBytes(new AccountCreationDto("John","Doe","RUNNING"));
         mvc.perform(post("/accounts")
                         .content(accountCreation)
                         .contentType(MediaType.APPLICATION_JSON_VALUE))
                 .andExpect(status().is(201));
    }
    @Test
    public void createControllerShouldReturnBadRequest() throws Exception {

        var objectMapper = new ObjectMapper();
        var accountCreation= objectMapper.writeValueAsBytes(new AccountCreationDto(null,"Doe","RUNNING"));
        var result=mvc.perform(post("/accounts")
                        .content(accountCreation)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().is(400)).andReturn().getResponse();
}
}

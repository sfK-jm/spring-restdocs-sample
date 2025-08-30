package com.restdocs.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.restdocs.request.UserRequest;
import com.restdocs.response.UserResponse;
import com.restdocs.service.UserService;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@AutoConfigureRestDocs(uriScheme = "https", uriHost = "docs.example.com", uriPort = 443)
@RequiredArgsConstructor
@SpringBootTest
@ExtendWith(RestDocumentationExtension.class)
class UserControllerDocTest {

    @Autowired MockMvc mockMvc;
    @Autowired ObjectMapper objectMapper;
    @Autowired UserService userService;

    @Test
    @DisplayName("사용자 추가")
    void createUser() throws Exception {
        UserRequest request = UserRequest.of("username", "email@email.com", "password");

        mockMvc.perform(post("/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                )
                .andExpect(status().isOk())
                .andDo(document("user-create",
                        requestFields(
                                fieldWithPath("name").description("유저명"),
                                fieldWithPath("email").description("계정 이메일"),
                                fieldWithPath("password").description("계정 비밀번호")
                        ),
                        responseFields(
                                fieldWithPath("id").description("유저ID"),
                                fieldWithPath("name").description("유저명"),
                                fieldWithPath("email").description("유저 이메일"),
                                fieldWithPath("createdAt").description("유저 계정 생성 날짜")
                        )
                ));
    }

    @Test
    @DisplayName("유저 삭제")
    void deleteUser() throws Exception {
        UserResponse user = userService.createUser(
                UserRequest.of("username", "email@email.com", "password"));

        mockMvc.perform(delete("/user/{userId}", user.getId()))
                .andExpect(status().isOk())
                .andDo(document("user-delete",
                        pathParameters(
                                parameterWithName("userId").description("유저IDf")
                        )));
    }

    @Test
    @DisplayName("유저 조회")
    void findUser() throws Exception {
        UserResponse request = userService.createUser(
                UserRequest.of("username", "email@email.com", "password"));

        mockMvc.perform(get("/user/{userId}", request.getId()))
                .andExpect(status().isOk())
                .andDo(document("user-find",
                        pathParameters(
                                parameterWithName("userId").description("유저 ID")),
                        responseFields(
                                fieldWithPath("id").description("유저ID"),
                                fieldWithPath("name").description("유저명"),
                                fieldWithPath("email").description("유저 이메일"),
                                fieldWithPath("createdAt").description("계정 생성 일시")
                        )
                ));
    }

}
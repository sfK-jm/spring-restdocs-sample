package com.restdocs.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.restdocs.request.UserRequest;
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
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@AutoConfigureRestDocs(uriScheme = "https", uriHost = "docs.example.com", uriPort = 443)
@RequiredArgsConstructor
@SpringBootTest
@ExtendWith(RestDocumentationExtension.class)
class UserControllerDocTest {

    @Autowired MockMvc mockMvc;
    @Autowired ObjectMapper objectMapper;

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
                        responseFields(
                                fieldWithPath("id").description("유저ID"),
                                fieldWithPath("name").description("유저명"),
                                fieldWithPath("email").description("유저 이메일"),
                                fieldWithPath("createdAt").description("유저 계정 생성 날짜")
                        )
                ));
    }

}
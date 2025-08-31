# Rest docs

## build.gradle 추가

```gradle
plugins {
    id 'org.asciidoctor.jvm.convert' version '3.3.2'
}

ext {
    set('snippetsDir', file("build/generated-snippets"))
}

dependencies {
    testImplementation 'org.springframework.restdocs:spring-restdocs-mockmvc'
}

tasks.named('test') {
    outputs.dir snippetsDir
    useJUnitPlatform()
}

tasks.named('test') {
    outputs.dir snippetsDir
    useJUnitPlatform()
}

tasks.named('asciidoctor') {
    inputs.dir snippetsDir
    dependsOn test
}

asciidoctor.doFirst {
    delete file('src/main/resources/static/docs')
}

tasks.register('copyDocument', Copy) {
    dependsOn asciidoctor
    from file("build/docs/asciidoc")
    into file("src/main/resources/static/docs")
}

build {
    dependsOn copyDocument
}

```

## 테스트 코드 작성 

```java
@AutoConfigureMockMvc
@AutoConfigureRestDocs(uriScheme = "https", uriHost = "docs.example.com", uriPort = 443)
@RequiredArgsConstructor
@SpringBootTest
@ExtendWith(RestDocumentationExtension.class)
class UserControllerDocTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    UserService userService;

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
}
```

### 문서 스니펫 파일 확인

작성한 테스트가 성공적으로 끝났다면, `build/generated-snippets`경로에 adoc파일이 생성된다.

### index.adoc 파일 작성

`src/docs/asciidoc/index.adoc` 파일 생성

```adoc
ifndef::snippets[]
:snippets: ./build/generated-snippets
endif::[]

= API Docs
:doctype: book
:icons: font
:source-highlighter: highlightjs
:toc: left
:toclevels: 1
:toc-title: 목록

== 유저 생성

=== REQUEST
include::{snippets}/user-create/http-request.adoc[]

include::{snippets}/user-create/request-fields.adoc[]

=== RESPONSE
include::{snippets}/user-create/http-response.adoc[]

include::{snippets}/user-create/response-fields.adoc[]

== 유저 삭제

=== REQUEST

include::{snippets}/user-delete/http-request.adoc[]

include::{snippets}/user-delete/path-parameters.adoc[]

=== RESPONSE

include::{snippets}/user-delete/http-response.adoc[]

== 유저 조회

=== REQUEST

include::{snippets}/user-find/http-request.adoc[]
include::{snippets}/user-find/path-parameters.adoc[]

=== RESPONSE

include::{snippets}/user-find/response-body.adoc[]
include::{snippets}/user-find/response-fields.adoc[]

```

## build 및 문서 확인

http://localhost:8080/docs/index.html 접속

<img src="/imgs/docs.png">


## 문서 커스텀

`src/test/resources/org/springframework/restdocs/templates/커스텀_파일명.snippet`을 이용하면 커스텀 snippet파일을 만들 수 있다. 
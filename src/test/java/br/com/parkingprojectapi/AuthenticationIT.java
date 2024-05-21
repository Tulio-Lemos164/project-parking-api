package br.com.parkingprojectapi;


import br.com.parkingprojectapi.jwt.JwtToken;
import br.com.parkingprojectapi.web.controller.exceptions.StandardError;
import br.com.parkingprojectapi.web.dto.UserLoginDTO;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "/sql/users/users-insert.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/sql/users/users-delete.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class AuthenticationIT {

    @Autowired
    WebTestClient webTestClient;

    @Test
    public void authenticate_ValidCredentials_ReturnTokenWithStatus200(){
        JwtToken responseBody;

        responseBody = webTestClient
                .post()
                .uri("/api/v1/auth")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UserLoginDTO("barney@gmail.com", "barney"))
                .exchange()
                .expectStatus().isOk()
                .expectBody(JwtToken.class)
                .returnResult().getResponseBody();

        Assertions.assertThat(responseBody).isNotNull();
    }

    @Test
    public void authenticate_InvalidCredentials_ReturnTokenWithStatus400(){
        StandardError responseBody;

        responseBody = webTestClient
                .post()
                .uri("/api/v1/auth")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UserLoginDTO("barneyinvalid@gmail.com", "barney"))
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody(StandardError.class)
                .returnResult().getResponseBody();

        Assertions.assertThat(responseBody).isNotNull();
        Assertions.assertThat(responseBody.getStatus()).isEqualTo(400);

        responseBody = webTestClient
                .post()
                .uri("/api/v1/auth")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UserLoginDTO("barney@gmail.com", "barnze"))
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody(StandardError.class)
                .returnResult().getResponseBody();

        Assertions.assertThat(responseBody).isNotNull();
        Assertions.assertThat(responseBody.getStatus()).isEqualTo(400);
    }

    @Test
    public void authenticate_InvalidUsername_ReturnTokenWithStatus422(){
        StandardError responseBody;

        responseBody = webTestClient
                .post()
                .uri("/api/v1/auth")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UserLoginDTO("barney@gmail", "barney"))
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(StandardError.class)
                .returnResult().getResponseBody();

        Assertions.assertThat(responseBody).isNotNull();
        Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);

        responseBody = webTestClient
                .post()
                .uri("/api/v1/auth")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UserLoginDTO("", "barney"))
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(StandardError.class)
                .returnResult().getResponseBody();

        Assertions.assertThat(responseBody).isNotNull();
        Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);
    }

    @Test
    public void authenticate_InvalidPassword_ReturnTokenWithStatus422(){
        StandardError responseBody;

        responseBody = webTestClient
                .post()
                .uri("/api/v1/auth")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UserLoginDTO("barney@gmail.com", "barny"))
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(StandardError.class)
                .returnResult().getResponseBody();

        Assertions.assertThat(responseBody).isNotNull();
        Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);

        responseBody = webTestClient
                .post()
                .uri("/api/v1/auth")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UserLoginDTO("barney@gmail.com", ""))
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(StandardError.class)
                .returnResult().getResponseBody();

        Assertions.assertThat(responseBody).isNotNull();
        Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);

        responseBody = webTestClient
                .post()
                .uri("/api/v1/auth")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UserLoginDTO("barney@gmail.com", "barneyDBro"))
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(StandardError.class)
                .returnResult().getResponseBody();

        Assertions.assertThat(responseBody).isNotNull();
        Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);
    }
}

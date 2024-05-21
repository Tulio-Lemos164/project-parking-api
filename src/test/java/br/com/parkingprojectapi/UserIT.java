package br.com.parkingprojectapi;

import br.com.parkingprojectapi.web.controller.exceptions.StandardError;
import br.com.parkingprojectapi.web.dto.UserInsertDTO;
import br.com.parkingprojectapi.web.dto.UserPasswordDTO;
import br.com.parkingprojectapi.web.dto.UserResponseDTO;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "/sql/users/users-insert.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/sql/users/users-delete.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class UserIT {

    @Autowired
    WebTestClient webTestClient;

    @Test
    public void insertUser_UsernameAndPasswordValid_ReturnCreatedUserStatus201(){
        UserResponseDTO responseBody;
        responseBody= webTestClient.post()
                    .uri("/api/v1/users")
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(new UserInsertDTO("marshall@gmail.com", "marlil"))
                    .exchange().expectStatus().isCreated()
                    .expectBody(UserResponseDTO.class)
                    .returnResult().getResponseBody();

        Assertions.assertThat(responseBody).isNotNull();
        Assertions.assertThat(responseBody.getId()).isNotNull();
        Assertions.assertThat(responseBody.getUsername()).isEqualTo("marshall@gmail.com");
        Assertions.assertThat(responseBody.getRole()).isEqualTo("CLIENT");
    }

    @Test
    public void insertUser_InvalidUsername_ReturnStandardErrorStatus422(){
        StandardError responseBody;
        responseBody= webTestClient.post()
                .uri("/api/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UserInsertDTO("", "marlil"))
                .exchange().expectStatus().isEqualTo(422)
                .expectBody(StandardError.class)
                .returnResult().getResponseBody();

        Assertions.assertThat(responseBody).isNotNull();
        Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);

        responseBody= webTestClient.post()
                .uri("/api/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UserInsertDTO("marshall@", "marlil"))
                .exchange().expectStatus().isEqualTo(422)
                .expectBody(StandardError.class)
                .returnResult().getResponseBody();

        Assertions.assertThat(responseBody).isNotNull();
        Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);

        responseBody= webTestClient.post()
                .uri("/api/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UserInsertDTO("marshall@gmail", "marlil"))
                .exchange().expectStatus().isEqualTo(422)
                .expectBody(StandardError.class)
                .returnResult().getResponseBody();

        Assertions.assertThat(responseBody).isNotNull();
        Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);
    }

    @Test
    public void insertUser_InvalidPassword_ReturnStandardErrorStatus422(){
        StandardError responseBody;
        responseBody= webTestClient.post()
                .uri("/api/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UserInsertDTO("marshall@gmail.com", ""))
                .exchange().expectStatus().isEqualTo(422)
                .expectBody(StandardError.class)
                .returnResult().getResponseBody();

        Assertions.assertThat(responseBody).isNotNull();
        Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);

        responseBody= webTestClient.post()
                .uri("/api/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UserInsertDTO("marshall@gmail.com", "marsh"))
                .exchange().expectStatus().isEqualTo(422)
                .expectBody(StandardError.class)
                .returnResult().getResponseBody();

        Assertions.assertThat(responseBody).isNotNull();
        Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);

        responseBody= webTestClient.post()
                .uri("/api/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UserInsertDTO("marshall@gmail.com", "marshmallow"))
                .exchange().expectStatus().isEqualTo(422)
                .expectBody(StandardError.class)
                .returnResult().getResponseBody();

        Assertions.assertThat(responseBody).isNotNull();
        Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);
    }

    @Test
    public void insertUser_AlreadyInUseUsername_ReturnStandardErrorStatus409(){
        StandardError responseBody;
        responseBody= webTestClient.post()
                .uri("/api/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UserInsertDTO("ted@gmail.com", "arqted"))
                .exchange().expectStatus().isEqualTo(409)
                .expectBody(StandardError.class)
                .returnResult().getResponseBody();

        Assertions.assertThat(responseBody).isNotNull();
        Assertions.assertThat(responseBody.getStatus()).isEqualTo(409);
    }

    @Test
    public void findUserById_ExistingId_ReturnUserStatus200(){
        UserResponseDTO responseBody;
        responseBody= webTestClient.get()
                .uri("/api/v1/users/200")
                .headers(JwtAuthentication.getHeaderAuthorization(webTestClient, "barney@gmail.com", "barney"))
                .exchange().expectStatus().isOk()
                .expectBody(UserResponseDTO.class)
                .returnResult().getResponseBody();

        Assertions.assertThat(responseBody).isNotNull();
        Assertions.assertThat(responseBody.getId()).isEqualTo(200);
        Assertions.assertThat(responseBody.getUsername()).isEqualTo("barney@gmail.com");
        Assertions.assertThat(responseBody.getRole()).isEqualTo("ADMIN");

        responseBody= webTestClient.get()
                .uri("/api/v1/users/100")
                .headers(JwtAuthentication.getHeaderAuthorization(webTestClient, "barney@gmail.com", "barney"))
                .exchange().expectStatus().isOk()
                .expectBody(UserResponseDTO.class)
                .returnResult().getResponseBody();

        Assertions.assertThat(responseBody).isNotNull();
        Assertions.assertThat(responseBody.getId()).isEqualTo(100);
        Assertions.assertThat(responseBody.getUsername()).isEqualTo("ted@gmail.com");
        Assertions.assertThat(responseBody.getRole()).isEqualTo("CLIENT");

        responseBody= webTestClient.get()
                .uri("/api/v1/users/100")
                .headers(JwtAuthentication.getHeaderAuthorization(webTestClient, "ted@gmail.com", "arqted"))
                .exchange().expectStatus().isOk()
                .expectBody(UserResponseDTO.class)
                .returnResult().getResponseBody();

        Assertions.assertThat(responseBody).isNotNull();
        Assertions.assertThat(responseBody.getId()).isEqualTo(100);
        Assertions.assertThat(responseBody.getUsername()).isEqualTo("ted@gmail.com");
        Assertions.assertThat(responseBody.getRole()).isEqualTo("CLIENT");
    }

    @Test
    public void findUserById_NonExistingId_ReturnStandardErrorStatus404(){
        StandardError responseBody;
        responseBody= webTestClient.get()
                .uri("/api/v1/users/0")
                .headers(JwtAuthentication.getHeaderAuthorization(webTestClient, "barney@gmail.com", "barney"))
                .exchange().expectStatus().isNotFound()
                .expectBody(StandardError.class)
                .returnResult().getResponseBody();

        Assertions.assertThat(responseBody).isNotNull();
        Assertions.assertThat(responseBody.getStatus()).isEqualTo(404);
    }

    @Test
    public void findUserById_ClientUserSearchingOtherIdThanHis_ReturnStandardErrorStatus403(){
        StandardError responseBody;
        responseBody= webTestClient.get()
                .uri("/api/v1/users/150")
                .headers(JwtAuthentication.getHeaderAuthorization(webTestClient, "ted@gmail.com", "arqted"))
                .exchange().expectStatus().isForbidden()
                .expectBody(StandardError.class)
                .returnResult().getResponseBody();

        Assertions.assertThat(responseBody).isNotNull();
        Assertions.assertThat(responseBody.getStatus()).isEqualTo(403);
    }

    @Test
    public void updatePassword_ValidDataADMIN_ReturnStatus200(){
        webTestClient.patch()
                .uri("/api/v1/users/200")
                .headers(JwtAuthentication.getHeaderAuthorization(webTestClient, "barney@gmail.com", "barney"))
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UserPasswordDTO("barney", "notTed", "notTed"))
                .exchange().expectStatus().isOk();
    }

    @Test
    public void updatePassword_ValidDataCLIENT_ReturnStatus200(){
        webTestClient.patch()
                .uri("/api/v1/users/100")
                .headers(JwtAuthentication.getHeaderAuthorization(webTestClient, "ted@gmail.com", "arqted"))
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UserPasswordDTO("arqted", "tedarq", "tedarq"))
                .exchange().expectStatus().isOk();
    }

    @Test
    public void updatePassword_DifferentUsers_ReturnStandardErrorStatus403(){
        StandardError responseBody;
        responseBody= webTestClient.patch()
                .uri("/api/v1/users/200")
                .headers(JwtAuthentication.getHeaderAuthorization(webTestClient, "ted@gmail.com", "arqted"))
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UserPasswordDTO("arqted", "tedarq", "tedarq"))
                .exchange().expectStatus().isForbidden()
                .expectBody(StandardError.class)
                .returnResult().getResponseBody();

        Assertions.assertThat(responseBody).isNotNull();
        Assertions.assertThat(responseBody.getStatus()).isEqualTo(403);
    }

    @Test
    public void updatePassword_InvalidFields_ReturnStandardErrorStatus422(){
        StandardError responseBody;
        responseBody= webTestClient.patch()
                .uri("/api/v1/users/100")
                .headers(JwtAuthentication.getHeaderAuthorization(webTestClient, "ted@gmail.com", "arqted"))
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UserPasswordDTO("", "", ""))
                .exchange().expectStatus().isEqualTo(422)
                .expectBody(StandardError.class)
                .returnResult().getResponseBody();

        Assertions.assertThat(responseBody).isNotNull();
        Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);

        responseBody= webTestClient.patch()
                .uri("/api/v1/users/100")
                .headers(JwtAuthentication.getHeaderAuthorization(webTestClient, "ted@gmail.com", "arqted"))
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UserPasswordDTO("arqte", "tedar", "tedar"))
                .exchange().expectStatus().isEqualTo(422)
                .expectBody(StandardError.class)
                .returnResult().getResponseBody();

        Assertions.assertThat(responseBody).isNotNull();
        Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);

        responseBody= webTestClient.patch()
                .uri("/api/v1/users/100")
                .headers(JwtAuthentication.getHeaderAuthorization(webTestClient, "ted@gmail.com", "arqted"))
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UserPasswordDTO("arqteddy", "tedarqT", "tedarqT"))
                .exchange().expectStatus().isEqualTo(422)
                .expectBody(StandardError.class)
                .returnResult().getResponseBody();

        Assertions.assertThat(responseBody).isNotNull();
        Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);
    }

    @Test
    public void updatePassword_InvalidPasswords_ReturnStandardErrorStatus400(){
        StandardError responseBody;
        responseBody= webTestClient.patch()
                .uri("/api/v1/users/100")
                .headers(JwtAuthentication.getHeaderAuthorization(webTestClient, "ted@gmail.com", "arqted"))
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UserPasswordDTO("arqted", "tedarq", "tedboy"))
                .exchange().expectStatus().isEqualTo(400)
                .expectBody(StandardError.class)
                .returnResult().getResponseBody();

        Assertions.assertThat(responseBody).isNotNull();
        Assertions.assertThat(responseBody.getStatus()).isEqualTo(400);

        responseBody= webTestClient.patch()
                .uri("/api/v1/users/100")
                .headers(JwtAuthentication.getHeaderAuthorization(webTestClient, "ted@gmail.com", "arqted"))
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UserPasswordDTO("arqtoy", "tedarq", "tedarq"))
                .exchange().expectStatus().isEqualTo(400)
                .expectBody(StandardError.class)
                .returnResult().getResponseBody();

        Assertions.assertThat(responseBody).isNotNull();
        Assertions.assertThat(responseBody.getStatus()).isEqualTo(400);
    }

    @Test
    public void findAllUsers_GotAllUsers_ReturnUserStatus200(){
        List<UserResponseDTO> responseBody;
        responseBody= webTestClient.get()
                .uri("/api/v1/users")
                .exchange().expectStatus().isOk()
                .expectBodyList(UserResponseDTO.class)
                .returnResult().getResponseBody();

        Assertions.assertThat(responseBody).isNotNull();
        Assertions.assertThat(responseBody.size()).isEqualTo(4);
    }
}

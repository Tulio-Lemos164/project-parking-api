package br.com.parkingprojectapi;


import br.com.parkingprojectapi.web.controller.exceptions.StandardError;
import br.com.parkingprojectapi.web.dto.ClientInsertDTO;
import br.com.parkingprojectapi.web.dto.ClientResponseDTO;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "/sql/clients/clients-insert.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/sql/clients/clients-delete.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class ClientIT {

    @Autowired
    WebTestClient webTestClient;

    @Test
    public void insertClient_ValidData_ReturnCreatedClientStatus201(){
        ClientResponseDTO responseBody;
        responseBody= webTestClient
                .post()
                .uri("/api/v1/clients")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(JwtAuthentication.getHeaderAuthorization(webTestClient, "lily@gmail.com", "lilmar"))
                .bodyValue(new ClientInsertDTO("Lily Aldrin", "46753013040"))
                .exchange()
                .expectStatus().isCreated()
                .expectBody(ClientResponseDTO.class)
                .returnResult().getResponseBody();

        Assertions.assertThat(responseBody).isNotNull();
        Assertions.assertThat(responseBody.getId()).isNotNull();
        Assertions.assertThat(responseBody.getName()).isEqualTo("Lily Aldrin");
        Assertions.assertThat(responseBody.getCpf()).isEqualTo("46753013040");
    }

    @Test
    public void insertClient_AlreadyInUseName_ReturnStandardErrorStatus409(){
        StandardError responseBody;
        responseBody= webTestClient
                .post()
                .uri("/api/v1/clients")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(JwtAuthentication.getHeaderAuthorization(webTestClient, "lily@gmail.com", "lilmar"))
                .bodyValue(new ClientInsertDTO("Lily Aldrin", "34560737045"))
                .exchange()
                .expectStatus().isEqualTo(409)
                .expectBody(StandardError.class)
                .returnResult().getResponseBody();

        Assertions.assertThat(responseBody).isNotNull();
        Assertions.assertThat(responseBody.getStatus()).isEqualTo(409);
    }

    @Test
    public void insertClient_InvalidName_ReturnStandardErrorStatus422(){
        StandardError responseBody;
        responseBody= webTestClient
                .post()
                .uri("/api/v1/clients")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(JwtAuthentication.getHeaderAuthorization(webTestClient, "lily@gmail.com", "lilmar"))
                .bodyValue(new ClientInsertDTO("", "46753013040"))
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(StandardError.class)
                .returnResult().getResponseBody();

        Assertions.assertThat(responseBody).isNotNull();
        Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);

        responseBody= webTestClient
                .post()
                .uri("/api/v1/clients")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(JwtAuthentication.getHeaderAuthorization(webTestClient, "lily@gmail.com", "lilmar"))
                .bodyValue(new ClientInsertDTO("Li", "46753013040"))
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(StandardError.class)
                .returnResult().getResponseBody();

        Assertions.assertThat(responseBody).isNotNull();
        Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);
    }

    @Test
    public void insertClient_InvalidPassword_ReturnStandardErrorStatus422(){
        StandardError responseBody;
        responseBody= webTestClient
                .post()
                .uri("/api/v1/clients")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(JwtAuthentication.getHeaderAuthorization(webTestClient, "lily@gmail.com", "lilmar"))
                .bodyValue(new ClientInsertDTO("Lily Aldrin", "467530130"))
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(StandardError.class)
                .returnResult().getResponseBody();

        Assertions.assertThat(responseBody).isNotNull();
        Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);

        responseBody= webTestClient
                .post()
                .uri("/api/v1/clients")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(JwtAuthentication.getHeaderAuthorization(webTestClient, "lily@gmail.com", "lilmar"))
                .bodyValue(new ClientInsertDTO("Lily Aldrin", ""))
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(StandardError.class)
                .returnResult().getResponseBody();

        Assertions.assertThat(responseBody).isNotNull();
        Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);

        responseBody= webTestClient
                .post()
                .uri("/api/v1/clients")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(JwtAuthentication.getHeaderAuthorization(webTestClient, "lily@gmail.com", "lilmar"))
                .bodyValue(new ClientInsertDTO("Lily Aldrin", "00000000000"))
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(StandardError.class)
                .returnResult().getResponseBody();

        Assertions.assertThat(responseBody).isNotNull();
        Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);

        responseBody= webTestClient
                .post()
                .uri("/api/v1/clients")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(JwtAuthentication.getHeaderAuthorization(webTestClient, "lily@gmail.com", "lilmar"))
                .bodyValue(new ClientInsertDTO("Lily Aldrin", "467.530.130-40"))
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(StandardError.class)
                .returnResult().getResponseBody();

        Assertions.assertThat(responseBody).isNotNull();
        Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);
    }

    @Test
    public void insertClient_NotAllowedUser_ReturnStandardErrorStatus403(){
        StandardError responseBody;
        responseBody= webTestClient
                .post()
                .uri("/api/v1/clients")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(JwtAuthentication.getHeaderAuthorization(webTestClient, "barney@gmail.com", "barney"))
                .bodyValue(new ClientInsertDTO("Ted Mosby", "65712430088"))
                .exchange()
                .expectStatus().isForbidden()
                .expectBody(StandardError.class)
                .returnResult().getResponseBody();

        Assertions.assertThat(responseBody).isNotNull();
        Assertions.assertThat(responseBody.getStatus()).isEqualTo(403);
    }
}

package br.com.parkingprojectapi;


import br.com.parkingprojectapi.web.controller.exceptions.StandardError;
import br.com.parkingprojectapi.web.dto.ClientInsertDTO;
import br.com.parkingprojectapi.web.dto.ClientResponseDTO;
import br.com.parkingprojectapi.web.dto.PageableDTO;
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

    @Test
    public void findClientById_ExistingIdByAdmin_ReturnClientStatus200(){
        ClientResponseDTO responseBody;
        responseBody= webTestClient.get()
                .uri("/api/v1/clients/10")
                .headers(JwtAuthentication.getHeaderAuthorization(webTestClient, "barney@gmail.com", "barney"))
                .exchange().expectStatus().isOk()
                .expectBody(ClientResponseDTO.class)
                .returnResult().getResponseBody();

        Assertions.assertThat(responseBody).isNotNull();
        Assertions.assertThat(responseBody.getId()).isEqualTo(10);
        Assertions.assertThat(responseBody.getName()).isEqualTo("Ted Mosby");
        Assertions.assertThat(responseBody.getCpf()).isEqualTo("65712430088");
    }

    @Test
    public void findClientById_NonExistingIdByAdmin_ReturnStandardErrorStatus404(){
        StandardError responseBody;
        responseBody= webTestClient.get()
                .uri("/api/v1/clients/12")
                .headers(JwtAuthentication.getHeaderAuthorization(webTestClient, "barney@gmail.com", "barney"))
                .exchange().expectStatus().isNotFound()
                .expectBody(StandardError.class)
                .returnResult().getResponseBody();

        Assertions.assertThat(responseBody).isNotNull();
        Assertions.assertThat(responseBody.getStatus()).isEqualTo(404);
    }

    @Test
    public void findClientById_ExistingIdByClient_ReturnStandardErrorStatus403(){
        StandardError responseBody;
        responseBody= webTestClient.get()
                .uri("/api/v1/clients/10")
                .headers(JwtAuthentication.getHeaderAuthorization(webTestClient, "ted@gmail.com", "arqted"))
                .exchange().expectStatus().isForbidden()
                .expectBody(StandardError.class)
                .returnResult().getResponseBody();

        Assertions.assertThat(responseBody).isNotNull();
        Assertions.assertThat(responseBody.getStatus()).isEqualTo(403);
    }

    @Test
    public void findAllClients_PaginationByAdmin_ReturnClientStatus200(){
        PageableDTO responseBody;
        responseBody= webTestClient.get()
                .uri("/api/v1/clients")
                .headers(JwtAuthentication.getHeaderAuthorization(webTestClient, "barney@gmail.com", "barney"))
                .exchange().expectStatus().isOk()
                .expectBody(PageableDTO.class)
                .returnResult().getResponseBody();

        Assertions.assertThat(responseBody).isNotNull();
        Assertions.assertThat(responseBody.getContent().size()).isEqualTo(2);
        Assertions.assertThat(responseBody.getNumber()).isEqualTo(0);
        Assertions.assertThat(responseBody.getTotalPages()).isEqualTo(1);

        responseBody= webTestClient.get()
                .uri("/api/v1/clients?size=1&page=1")
                .headers(JwtAuthentication.getHeaderAuthorization(webTestClient, "barney@gmail.com", "barney"))
                .exchange().expectStatus().isOk()
                .expectBody(PageableDTO.class)
                .returnResult().getResponseBody();

        Assertions.assertThat(responseBody).isNotNull();
        Assertions.assertThat(responseBody.getContent().size()).isEqualTo(1);
        Assertions.assertThat(responseBody.getNumber()).isEqualTo(1);
        Assertions.assertThat(responseBody.getTotalPages()).isEqualTo(2);
    }

    @Test
    public void findAllClients_PaginationIdByClient_ReturnStandardErrorStatus403(){
        StandardError responseBody;
        responseBody= webTestClient.get()
                .uri("/api/v1/clients")
                .headers(JwtAuthentication.getHeaderAuthorization(webTestClient, "ted@gmail.com", "arqted"))
                .exchange().expectStatus().isForbidden()
                .expectBody(StandardError.class)
                .returnResult().getResponseBody();

        Assertions.assertThat(responseBody).isNotNull();
        Assertions.assertThat(responseBody.getStatus()).isEqualTo(403);
    }
}

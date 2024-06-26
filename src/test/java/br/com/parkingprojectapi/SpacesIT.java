package br.com.parkingprojectapi;

import br.com.parkingprojectapi.web.dto.SpaceInsertDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "/sql/spaces/spaces-insert.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/sql/spaces/spaces-delete.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class SpacesIT {

    @Autowired
    WebTestClient webTestClient;

    @Test
    public void insertSpace_ValidData_ReturnLocationStatus201(){
        webTestClient
                .post()
                .uri("api/v1/spaces")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(JwtAuthentication.getHeaderAuthorization(webTestClient, "barney@gmail.com", "barney"))
                .bodyValue(new SpaceInsertDTO("A-05", "FREE"))
                .exchange()
                .expectStatus().isCreated()
                .expectHeader().exists(HttpHeaders.LOCATION);
    }

    @Test
    public void insertSpace_NotAllowedUser_ReturnStandardErrorStatus403(){
        webTestClient
                .post()
                .uri("api/v1/spaces")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(JwtAuthentication.getHeaderAuthorization(webTestClient, "ted@gmail.com", "arqted"))
                .bodyValue(new SpaceInsertDTO("A-05", "FREE"))
                .exchange()
                .expectStatus().isEqualTo(403)
                .expectBody()
                .jsonPath("status").isEqualTo(403)
                .jsonPath("path").isEqualTo("/api/v1/spaces");
    }

    @Test
    public void insertSpace_AlreadyInUseCode_ReturnStandardErrorStatus409(){
        webTestClient
                .post()
                .uri("api/v1/spaces")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(JwtAuthentication.getHeaderAuthorization(webTestClient, "barney@gmail.com", "barney"))
                .bodyValue(new SpaceInsertDTO("A-03", "FREE"))
                .exchange()
                .expectStatus().isEqualTo(409)
                .expectBody()
                .jsonPath("status").isEqualTo(409)
                .jsonPath("path").isEqualTo("/api/v1/spaces");
    }

    @Test
    public void insertSpace_InvalidData_ReturnStandardErrorStatus422(){
        webTestClient
                .post()
                .uri("api/v1/spaces")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(JwtAuthentication.getHeaderAuthorization(webTestClient, "barney@gmail.com", "barney"))
                .bodyValue(new SpaceInsertDTO("", ""))
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody()
                .jsonPath("status").isEqualTo(422)
                .jsonPath("path").isEqualTo("/api/v1/spaces");

        webTestClient
                .post()
                .uri("api/v1/spaces")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(JwtAuthentication.getHeaderAuthorization(webTestClient, "barney@gmail.com", "barney"))
                .bodyValue(new SpaceInsertDTO("A-100", "NOTFREE"))
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody()
                .jsonPath("status").isEqualTo(422)
                .jsonPath("path").isEqualTo("/api/v1/spaces");
    }

    @Test
    public void findSpaceByCode_ExistingCode_ReturnSpaceStatus200(){
        webTestClient
                .get()
                .uri("api/v1/spaces/{code}", "A-01")
                .headers(JwtAuthentication.getHeaderAuthorization(webTestClient, "barney@gmail.com", "barney"))
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("id").isEqualTo(10)
                .jsonPath("status").isEqualTo("FREE")
                .jsonPath("code").isEqualTo("A-01");
    }

    @Test
    public void findSpaceByCode_NotAllowedUser_ReturnStandardErrorStatus403(){
        webTestClient
                .get()
                .uri("api/v1/spaces/{code}", "A-3")
                .headers(JwtAuthentication.getHeaderAuthorization(webTestClient, "ted@gmail.com", "arqted"))
                .exchange()
                .expectStatus().isEqualTo(403)
                .expectBody()
                .jsonPath("status").isEqualTo(403)
                .jsonPath("path").isEqualTo("/api/v1/spaces/A-3");
    }

    @Test
    public void findSpaceByCode_NonExistingCode_ReturnStandardErrorStatus404(){
        webTestClient
                .get()
                .uri("api/v1/spaces/{code}", "A-10")
                .headers(JwtAuthentication.getHeaderAuthorization(webTestClient, "barney@gmail.com", "barney"))
                .exchange()
                .expectStatus().isNotFound()
                .expectBody()
                .jsonPath("status").isEqualTo(404)
                .jsonPath("path").isEqualTo("/api/v1/spaces/A-10");
    }
}

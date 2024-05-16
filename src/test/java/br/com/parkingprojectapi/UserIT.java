package br.com.parkingprojectapi;

import br.com.parkingprojectapi.web.dto.UserInsertDTO;
import br.com.parkingprojectapi.web.dto.UserResponseDTO;
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
public class UserIT {

    @Autowired
    WebTestClient webTestClient;

    @Test
    public void insertUser_UsernameAndPasswordValid_ReturnCreatedUserStatus201(){
        UserResponseDTO responseDTO;
        responseDTO= webTestClient.post()
                    .uri("/api/v1/users")
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(new UserInsertDTO("marshall@gmail.com", "marlil"))
                    .exchange().expectStatus().isCreated()
                    .expectBody(UserResponseDTO.class)
                    .returnResult().getResponseBody();

        Assertions.assertThat(responseDTO).isNotNull();
        Assertions.assertThat(responseDTO.getId()).isNotNull();
        Assertions.assertThat(responseDTO.getUsername()).isEqualTo("marshall@gmail.com");
        Assertions.assertThat(responseDTO.getRole()).isEqualTo("CLIENT");
    }
}

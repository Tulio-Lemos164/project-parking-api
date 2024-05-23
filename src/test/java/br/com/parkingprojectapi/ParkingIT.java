package br.com.parkingprojectapi;

import br.com.parkingprojectapi.web.dto.ParkingInsertDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "/sql/parking/parking-insert.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/sql/parking/parking-delete.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class ParkingIT {

    @Autowired
    WebTestClient webTestClient;

    @Test
    public void createCheckIn_ValidData_ReturnCreatedAndLocation(){
        ParkingInsertDTO insertDTO = ParkingInsertDTO.builder()
                .licensePlate("TDB-3920").brand("FIAT").model("PALIO 1.6").color("RED").clientCpf("65712430088")
                .build();

        webTestClient
                .post()
                .uri("/api/v1/parking-lots/check-in")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(JwtAuthentication.getHeaderAuthorization(webTestClient, "barney@gmail.com", "barney"))
                .bodyValue(insertDTO)
                .exchange()
                .expectStatus().isCreated()
                .expectHeader().exists(HttpHeaders.LOCATION)
                .expectBody()
                .jsonPath("licensePlate").isEqualTo("TDB-3920")
                .jsonPath("brand").isEqualTo("FIAT")
                .jsonPath("model").isEqualTo("PALIO 1.6")
                .jsonPath("color").isEqualTo("RED")
                .jsonPath("clientCpf").isEqualTo("65712430088")
                .jsonPath("receipt").exists()
                .jsonPath("entryDate").exists()
                .jsonPath("spaceCode").exists();
    }

    @Test
    public void createCheckIn_RoleClient_ReturnStandardError403(){
        ParkingInsertDTO insertDTO = ParkingInsertDTO.builder()
                .licensePlate("TDB-3920").brand("FIAT").model("PALIO 1.6").color("RED").clientCpf("65712430088")
                .build();

        webTestClient
                .post()
                .uri("/api/v1/parking-lots/check-in")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(JwtAuthentication.getHeaderAuthorization(webTestClient, "ted@gmail.com", "arqted"))
                .bodyValue(insertDTO)
                .exchange()
                .expectStatus().isForbidden()
                .expectBody()
                .jsonPath("status").isEqualTo("403")
                .jsonPath("path").isEqualTo("/api/v1/parking-lots/check-in");
    }

    @Test
    public void createCheckIn_InvalidData_ReturnStandardError422(){
        ParkingInsertDTO insertDTO = ParkingInsertDTO.builder()
                .licensePlate("").brand("").model("").color("").clientCpf("")
                .build();

        webTestClient
                .post()
                .uri("/api/v1/parking-lots/check-in")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(JwtAuthentication.getHeaderAuthorization(webTestClient, "barney@gmail.com", "barney"))
                .bodyValue(insertDTO)
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody()
                .jsonPath("status").isEqualTo("422")
                .jsonPath("path").isEqualTo("/api/v1/parking-lots/check-in");
    }

    @Test
    public void createCheckIn_InvalidDataCpf_ReturnStandardError422(){
        ParkingInsertDTO insertDTO = ParkingInsertDTO.builder()
                .licensePlate("TDB-3920").brand("FIAT").model("PALIO 1.6").color("RED").clientCpf("657124300886")
                .build();

        webTestClient
                .post()
                .uri("/api/v1/parking-lots/check-in")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(JwtAuthentication.getHeaderAuthorization(webTestClient, "barney@gmail.com", "barney"))
                .bodyValue(insertDTO)
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody()
                .jsonPath("status").isEqualTo("422")
                .jsonPath("path").isEqualTo("/api/v1/parking-lots/check-in");

        insertDTO = ParkingInsertDTO.builder()
                .licensePlate("TDB-3920").brand("FIAT").model("PALIO 1.6").color("RED").clientCpf("6571243008")
                .build();

        webTestClient
                .post()
                .uri("/api/v1/parking-lots/check-in")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(JwtAuthentication.getHeaderAuthorization(webTestClient, "barney@gmail.com", "barney"))
                .bodyValue(insertDTO)
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody()
                .jsonPath("status").isEqualTo("422")
                .jsonPath("path").isEqualTo("/api/v1/parking-lots/check-in");
    }


    @Test
    public void createCheckIn_InvalidDataLicensePlate_ReturnStandardError422(){
        ParkingInsertDTO insertDTO = ParkingInsertDTO.builder()
                .licensePlate("TDB-392").brand("FIAT").model("PALIO 1.6").color("RED").clientCpf("65712430088")
                .build();

        webTestClient
                .post()
                .uri("/api/v1/parking-lots/check-in")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(JwtAuthentication.getHeaderAuthorization(webTestClient, "barney@gmail.com", "barney"))
                .bodyValue(insertDTO)
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody()
                .jsonPath("status").isEqualTo("422")
                .jsonPath("path").isEqualTo("/api/v1/parking-lots/check-in");

        insertDTO = ParkingInsertDTO.builder()
                .licensePlate("TDB-39203").brand("FIAT").model("PALIO 1.6").color("RED").clientCpf("65712430088")
                .build();

        webTestClient
                .post()
                .uri("/api/v1/parking-lots/check-in")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(JwtAuthentication.getHeaderAuthorization(webTestClient, "barney@gmail.com", "barney"))
                .bodyValue(insertDTO)
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody()
                .jsonPath("status").isEqualTo("422")
                .jsonPath("path").isEqualTo("/api/v1/parking-lots/check-in");

        insertDTO = ParkingInsertDTO.builder()
                .licensePlate("3920-TDB").brand("FIAT").model("PALIO 1.6").color("RED").clientCpf("65712430088")
                .build();

        webTestClient
                .post()
                .uri("/api/v1/parking-lots/check-in")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(JwtAuthentication.getHeaderAuthorization(webTestClient, "barney@gmail.com", "barney"))
                .bodyValue(insertDTO)
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody()
                .jsonPath("status").isEqualTo("422")
                .jsonPath("path").isEqualTo("/api/v1/parking-lots/check-in");
    }

    @Test
    public void createCheckIn_NonExistingCpf_ReturnStandardError404(){
        ParkingInsertDTO insertDTO = ParkingInsertDTO.builder()
                .licensePlate("TDB-3920").brand("FIAT").model("PALIO 1.6").color("RED").clientCpf("53888526019")
                .build();

        webTestClient
                .post()
                .uri("/api/v1/parking-lots/check-in")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(JwtAuthentication.getHeaderAuthorization(webTestClient, "barney@gmail.com", "barney"))
                .bodyValue(insertDTO)
                .exchange()
                .expectStatus().isNotFound()
                .expectBody()
                .jsonPath("status").isEqualTo("404")
                .jsonPath("path").isEqualTo("/api/v1/parking-lots/check-in");
    }

    @Sql(scripts = "/sql/parking/parking-insert-occupied-spaces.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/sql/parking/parking-delete-occupied-spaces.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Test
    public void createCheckIn_NoEmptySpace_ReturnStandardError404(){
        ParkingInsertDTO insertDTO = ParkingInsertDTO.builder()
                .licensePlate("TDB-3920").brand("FIAT").model("PALIO 1.6").color("RED").clientCpf("65712430088")
                .build();

        webTestClient
                .post()
                .uri("/api/v1/parking-lots/check-in")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(JwtAuthentication.getHeaderAuthorization(webTestClient, "barney@gmail.com", "barney"))
                .bodyValue(insertDTO)
                .exchange()
                .expectStatus().isNotFound()
                .expectBody()
                .jsonPath("status").isEqualTo("404")
                .jsonPath("path").isEqualTo("/api/v1/parking-lots/check-in");
    }

    @Test
    public void findCheckIn_RoleADMIN_ReturnDataStatus200(){
        webTestClient
                .get()
                .uri("/api/v1/parking-lots/check-in/{receipt}", "20230313-101300")
                .headers(JwtAuthentication.getHeaderAuthorization(webTestClient, "barney@gmail.com", "barney"))
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("licensePlate").isEqualTo("FIT-1020")
                .jsonPath("brand").isEqualTo("FIAT")
                .jsonPath("model").isEqualTo("PALIO")
                .jsonPath("color").isEqualTo("GREEN")
                .jsonPath("clientCpf").isEqualTo("65712430088")
                .jsonPath("receipt").isEqualTo("20230313-101300")
                .jsonPath("entryDate").isEqualTo("2023-03-13 10:15:00")
                .jsonPath("spaceCode").isEqualTo("A-01");
    }

    @Test
    public void findCheckIn_RoleCLIENT_ReturnDataStatus200(){
        webTestClient
                .get()
                .uri("/api/v1/parking-lots/check-in/{receipt}", "20230313-101300")
                .headers(JwtAuthentication.getHeaderAuthorization(webTestClient, "barney@gmail.com", "barney"))
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("licensePlate").isEqualTo("FIT-1020")
                .jsonPath("brand").isEqualTo("FIAT")
                .jsonPath("model").isEqualTo("PALIO")
                .jsonPath("color").isEqualTo("GREEN")
                .jsonPath("clientCpf").isEqualTo("65712430088")
                .jsonPath("receipt").isEqualTo("20230313-101300")
                .jsonPath("entryDate").isEqualTo("2023-03-13 10:15:00")
                .jsonPath("spaceCode").isEqualTo("A-01");
    }

    @Test
    public void findCheckIn_NonExistingReceipt_ReturnStandardErrorStatus404(){
        webTestClient
                .get()
                .uri("/api/v1/parking-lots/check-in/{receipt}", "20230313-101344")
                .headers(JwtAuthentication.getHeaderAuthorization(webTestClient, "barney@gmail.com", "barney"))
                .exchange()
                .expectStatus().isNotFound()
                .expectBody()
                .jsonPath("status").isEqualTo("404")
                .jsonPath("path").isEqualTo("/api/v1/parking-lots/check-in/20230313-101344");
    }

    @Test
    public void createCheckOut_ExistingReceipt_ReturnDataStatus200(){
        webTestClient
                .put()
                .uri("/api/v1/parking-lots/check-out/{receipt}", "20230313-101300")
                .headers(JwtAuthentication.getHeaderAuthorization(webTestClient, "barney@gmail.com", "barney"))
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("licensePlate").isEqualTo("FIT-1020")
                .jsonPath("brand").isEqualTo("FIAT")
                .jsonPath("model").isEqualTo("PALIO")
                .jsonPath("color").isEqualTo("GREEN")
                .jsonPath("clientCpf").isEqualTo("65712430088")
                .jsonPath("receipt").isEqualTo("20230313-101300")
                .jsonPath("entryDate").isEqualTo("2023-03-13 10:15:00")
                .jsonPath("exitDate").exists()
                .jsonPath("value").exists()
                .jsonPath("discount").exists()
                .jsonPath("spaceCode").isEqualTo("A-01");
    }

    @Test
    public void createCheckOut_NonExistingReceipt_ReturnStandardErrorStatus404(){
        webTestClient
                .put()
                .uri("/api/v1/parking-lots/check-out/{receipt}", "20230313-101344")
                .headers(JwtAuthentication.getHeaderAuthorization(webTestClient, "barney@gmail.com", "barney"))
                .exchange()
                .expectStatus().isNotFound()
                .expectBody()
                .jsonPath("status").isEqualTo("404")
                .jsonPath("path").isEqualTo("/api/v1/parking-lots/check-out/20230313-101344");
    }

    @Test
    public void createCheckOut_RoleClient_ReturnStandardError403(){
        webTestClient
                .put()
                .uri("/api/v1/parking-lots/check-out/{receipt}", "20230313-101300")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(JwtAuthentication.getHeaderAuthorization(webTestClient, "ted@gmail.com", "arqted"))
                .exchange()
                .expectStatus().isForbidden()
                .expectBody()
                .jsonPath("status").isEqualTo("403")
                .jsonPath("path").isEqualTo("/api/v1/parking-lots/check-out/20230313-101300");
    }
}

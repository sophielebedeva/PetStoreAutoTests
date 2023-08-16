import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import static io.restassured.RestAssured.given;

public class PetAPItests {

    /* Достать всех доступных питомцев по статусу */
    @Test
    void getAvailablePetsByStatus(){
        given()
                .queryParam("status","available")
                .when()
                .request("GET", "https://petstore.swagger.io/v2/pet/findByStatus")
                .then()
                .assertThat()
                .statusCode(200);
    }

    /* Достать по статусу питомцев из нескольких категорий (проданные, ожидающие) */
    @Test
    void getPetsWithSeveralStatusesByStatus(){
        given()
                .queryParam("status","sold","pending")
                .when()
                .request("GET", "https://petstore.swagger.io/v2/pet/findByStatus")
                .then()
                .assertThat()
                .statusCode(200);
    }

   /* Достать существующего питомца по id и проверить, что его имя doggie */
    @Test
    void getPetByIdExpectedStatus200(){
        int petId = 10;
        Response response = given().
                contentType(ContentType.JSON)
                .get("https://petstore.swagger.io/v2/pet/"+petId)
                .then()
                .statusCode(200)
                .extract().response();
        Pet pet = response.as(Pet.class);
        System.out.println(pet.getName());
        Assertions.assertEquals("doggie",pet.getName());
    }

    /* Попробовать достать несуществующего питомца по id и получить код 404 Not found*/
    @Test
    void getPetByIdExpectedStatus404(){
        String petId = "NotFound";
            given().
                contentType(ContentType.JSON)
                .get("https://petstore.swagger.io/v2/pet/"+petId)
                .then()
                .statusCode(200);

    }

    /* Проверить, что у всех питомцев в ожидании есть имя != null*/
    @Test
    void checkNamesOfPendingPets(){
        Pet[] pets = given()
                .when()
                .contentType(ContentType.JSON)
                .get("https://petstore.swagger.io/v2/pet/findByStatus?status=pending")
                .then()
                .extract().response().as(Pet[].class);
        Assertions.assertTrue(Arrays.stream(pets).allMatch(pet -> pet.getName() != null));
    }


}

import io.restassured.response.Response;
import model.Category;
import model.Pet;
import model.Tag;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.Arrays;

public class PetApiTests {
    TestClient testClient = new TestClient();

    /* Достать всех доступных питомцев по статусу */
    @Test
    void getAvailablePetsByStatus() {
        testClient.getRequestSpecFindPetByStatus()
                .queryParam("status", "available")
        .when()
                .get()
        .then()
                .assertThat()
                .statusCode(200);
    }

    /* Достать по статусу питомцев из нескольких категорий (проданные, ожидающие) */
    @Test
    void getPetsWithSeveralStatusesByStatus() {
       testClient.getRequestSpecFindPetByStatus()
                .queryParam("status", "sold", "pending")
        .when()
                .get()
        .then()
                .assertThat()
                .statusCode(200);
    }

    /* Достать существующего питомца по id и проверить, что его имя doggie */
    @Test
    void getPetByIdExpectedStatus200() {
        int petId = 10;
        Response response =
                testClient.getRequestSpecPetById(petId)
                        .when()
                        .get()
                .then()
                        .statusCode(200)
                        .extract()
                        .response();
        Pet pet = response.as(Pet.class);
        Assertions.assertEquals("doggie", pet.getName());
    }

    /* Попробовать достать несуществующего питомца по id и получить код 404 Not found*/
    @Test
    void getPetByIdExpectedStatus404() {
        int petId = 004000001;
       testClient.getRequestSpecPetById(petId)
        .then()
                .statusCode(404);
    }

    /* Проверить, что у всех питомцев в ожидании есть имя != null*/
    @Test
    void checkNamesOfPendingPets() {
        Pet[] pets =
                testClient.getRequestSpecFindPetByStatus()
                        .queryParam("status","pending")
                        .when()
                        .get()
                .then()
                        .extract()
                        .response()
                        .as(Pet[].class);
        Assertions.assertTrue(Arrays.stream(pets).allMatch(pet -> pet.getName() != null));
    }

    /* Проверить, что возвращается созданный питомцец с актуальными значениями */
    @Test
    void createPet() {
        ArrayList<Tag> tags = new ArrayList<>();
        tags.add(new Tag(10001, "cat"));
        Pet pet = new Pet(10101,
                new Category(10001, "TestCategory"),
                "cat",
                null,
                tags,
                "available");
        Pet createdPet =
              testClient.getRequestSpecAddPet(pet)
                .when()
                        .post()
                .then()
                        .statusCode(200)
                .and()
                        .extract()
                        .response()
                        .as(Pet.class);
        Assertions.assertEquals(pet.getName(), createdPet.getName());
        Assertions.assertEquals(pet.getCategory().getName(), createdPet.getCategory().getName());
        Assertions.assertEquals(pet.getTags().get(0).getName(), createdPet.getTags().get(0).getName());
        Assertions.assertEquals(pet.getPhotoUrls(), createdPet.getPhotoUrls());
        Assertions.assertEquals(pet.getStatus(), createdPet.getStatus());
    }
}

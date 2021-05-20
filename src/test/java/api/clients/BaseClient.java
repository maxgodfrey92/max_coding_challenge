package api.clients;

import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import org.junit.Assert;

/**
 * A Base Client class that can be inherited by other API Clients.
 * Contains a simple helper function to assert expected HTTP status code, and return validatable response needed for RestAssured
 */
public class BaseClient {
    /**
     * Takes response from client request, asserts status code, and returns RestAssured Validatable Response
     */
    public static ValidatableResponse getValidatableResponse(Response response, int expStatusCode) {
        assertStatusCode(response, expStatusCode);
        return response.then();
    }

    /**
     * Asserts response status code against expected
     */
    public static void assertStatusCode(Response response, int expStatusCode) {
        Assert.assertEquals("Expected status code '"+ expStatusCode + "' but saw '" + response.statusCode() + "'", expStatusCode, response.statusCode());
    }
}

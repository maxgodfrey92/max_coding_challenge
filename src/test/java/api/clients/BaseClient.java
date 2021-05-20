package api.clients;

import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import org.junit.Assert;

public class BaseClient {
    public static ValidatableResponse getValidatableResponse(Response response, int expStatusCode) {
        Assert.assertEquals(expStatusCode, response.statusCode());
        return response.then();
    }
}

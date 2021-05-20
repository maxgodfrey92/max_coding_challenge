package api.clients;

import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.json.simple.JSONObject;
import utils.TestUtils;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

/**
 * API Client for the Github Gist API. Contains simple CRUD operations and returns responses where appropriate
 */
public class GistClient extends BaseClient {

    RequestSpecification requestSession;
    String baseUrl;

    /**
     * Constructor creates a request session to include Github Token for further use in below endpoints
     * Also sets the Base URL to that specified in config.properties
     */
    public GistClient() {
        this.baseUrl = TestUtils.getProperty("API_URL");

        String githubToken = TestUtils.getProperty("GITHUB_TOKEN");
        this.requestSession = given();
        this.requestSession.header("accept", "application/vnd.github.v3+json");
        this.requestSession.header("Authorization", "token " + githubToken);
    }

    /**
     * Returns all Gists for specified username
     */
    public ValidatableResponse getAllGistsForUser(String username) {
        String endpoint = this.baseUrl + "/users/" + username + "/gists";
        Response response = this.requestSession.get(endpoint);
        return getValidatableResponse(response, 200);
    }

    /**
     * Gets a specific Gist using the specified ID
     */
    public ValidatableResponse getGistById(String gistId) {
        String endpoint = this.baseUrl + "/gists/" + gistId;
        Response response = this.requestSession.get(endpoint);
        return getValidatableResponse(response, 200);
    }

    /**
     * Creates new Gist with description and file map passed in
     * Extra handling done to convert file map to JSON Object
     */
    public ValidatableResponse createGist(String description, HashMap<String, String> files) {
        String endpoint = this.baseUrl + "/gists";
        // Converting description and files to a JSON Object which can be passed in request params
        JSONObject requestParams = createGistParams(description, files);
        Response response = this.requestSession.body(requestParams.toJSONString()).post(endpoint);
        return getValidatableResponse(response, 201);
    }

    /**
     * Updates an existing Gist with new description and file map passed in
     * Uses the same handling as Create Gist endpoint
     */
    public void updateGist(String gistId, String description, HashMap<String, String> files) {
        String endpoint = this.baseUrl + "/gists/" + gistId;
        JSONObject requestParams = createGistParams(description, files);
        Response response = this.requestSession.body(requestParams.toJSONString()).patch(endpoint);
        // No return data so just assert status code
        assertStatusCode(response, 200);
    }

    /**
     * Deletes a Gist by the Gist ID provided
     */
    public void deleteGist(String gistId) {
        String endpoint = this.baseUrl + "/gists/" + gistId;
        Response response = this.requestSession.delete(endpoint);
        // No return data so just assert status code
        assertStatusCode(response, 204);
    }

    /**
     * Converts Description as a String and File Map as Hashmap to JSON Objects
     * This can then be used as the request parameters for the Create / Patch endpoints
     */
    public JSONObject createGistParams(String description, HashMap<String, String> files) {
        JSONObject fileParams = createFileParams(files);
        JSONObject requestParams = new JSONObject();
        requestParams.put("description", description);
        requestParams.put("files", fileParams);
        requestParams.put("public", false);
        return requestParams;
    }

    /**
     * Convert Hashmap of Files to a JSON Object
     * Format is {fileName: {"content": fileContent}}
     */
    public JSONObject createFileParams(HashMap<String, String> files) {
        JSONObject fileParams = new JSONObject();
        for (Map.Entry<String, String> entry : files.entrySet()) {
            JSONObject contentEntry = new JSONObject();
            contentEntry.put("content", entry.getValue());
            fileParams.put(entry.getKey(), contentEntry);
        }
        return fileParams;
    }
}

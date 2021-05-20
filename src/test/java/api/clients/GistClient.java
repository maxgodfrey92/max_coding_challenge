package api.clients;

import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.json.simple.JSONObject;
import utils.TestUtils;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class GistClient extends BaseClient {

    RequestSpecification requestSession;
    String baseUrl;

    public GistClient() {
        this.baseUrl = TestUtils.getProperty("API_URL");

        String githubToken = TestUtils.getProperty("GITHUB_TOKEN");
        this.requestSession = given();
        this.requestSession.header("accept", "application/vnd.github.v3+json");
        this.requestSession.header("Authorization", "token " + githubToken);
    }

    public ValidatableResponse getAllGistsForUser(String username) {
        String endpoint = this.baseUrl + "/users/" + username + "/gists";
        Response response = this.requestSession.get(endpoint);
        return getValidatableResponse(response, 200);
    }

    public ValidatableResponse getGist(String gistId) {
        String endpoint = this.baseUrl + "/gists/" + gistId;
        Response response = this.requestSession.get(endpoint);
        return getValidatableResponse(response, 200);
    }

    public ValidatableResponse createGist(String description, HashMap<String, String> files) {
        String endpoint = this.baseUrl + "/gists";
        JSONObject requestParams = createGistParams(description, files);
        Response response = this.requestSession.body(requestParams.toJSONString()).post(endpoint);
        return getValidatableResponse(response, 201);
    }

    public ValidatableResponse updateGist(String gistId, String description, HashMap<String, String> files) {
        String endpoint = this.baseUrl + "/gists/" + gistId;
        JSONObject requestParams = createGistParams(description, files);
        Response response = this.requestSession.body(requestParams.toJSONString()).patch(endpoint);
        return getValidatableResponse(response, 200);
    }

    public ValidatableResponse deleteGist(String gistId) {
        String endpoint = this.baseUrl + "/gists/" + gistId;
        Response response = this.requestSession.delete(endpoint);
        return getValidatableResponse(response, 204);
    }

    public JSONObject createGistParams(String description, HashMap<String, String> files) {
        JSONObject fileParams = createFileParams(files);
        JSONObject requestParams = new JSONObject();
        requestParams.put("description", description);
        requestParams.put("files", fileParams);
        requestParams.put("public", false);
        return requestParams;
    }

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

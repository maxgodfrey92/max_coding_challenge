package api;

import io.restassured.response.ValidatableResponse;
import org.junit.Assert;
import org.junit.Test;
import utils.TestUtils;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.not;

public class GistTest extends BaseAPITest {

    @Test
    public void createGist() {
        String username = TestUtils.getProperty("GITHUB_USERNAME");
        HashMap<String, String> files = new HashMap<>();
        files.put("myFile.txt", "This is a test Gist");
        String description = "Testing Gist Creation API";

        this.gistClient.createGist(description, files);
        this.gistClient.getAllGistsForUser(username).assertThat().body("description", hasItem(description));
    }

    @Test
    public void updateGist() {
        HashMap<String, String> files = new HashMap<>();
        files.put("myFile1.txt", "This file will be updated");
        files.put("myFile2.txt", "This file will be deleted");
        String description = "Testing Gist Update API";

        ValidatableResponse createdGist = this.gistClient.createGist(description, files);
        String gistId = createdGist.extract().path("id");
        verifyGistFiles(gistId, files);

        files.put("myFile1.txt", "Text successfully updated");
        files.put("myFile2.txt", "");

        this.gistClient.updateGist(gistId, description, files);
        verifyGistFiles(gistId, files);
    }

    @Test
    public void deleteGist() {
        String username = TestUtils.getProperty("GITHUB_USERNAME");
        HashMap<String, String> files = new HashMap<>();
        files.put("myFile3.txt", "This Gist will be deleted");
        String description = "Testing Gist Deletion API";

        ValidatableResponse createdGist = this.gistClient.createGist(description, files);
        String gistId = createdGist.extract().path("id");
        this.gistClient.getAllGistsForUser(username).assertThat().body("description", hasItem(description));

        this.gistClient.deleteGist(gistId);
        this.gistClient.getAllGistsForUser(username).assertThat().body("description", not(hasItem(description)));
    }

    public void verifyGistFiles(String gistId, HashMap<String, String> files) {
        ValidatableResponse retrievedGist = this.gistClient.getGist(gistId);
        HashMap<String, Object> gistFiles = retrievedGist.extract().path("files");

        for (Map.Entry<String, String> fileEntry : files.entrySet()) {
            if (fileEntry.getValue().equalsIgnoreCase("")) {
                Assert.assertFalse(gistFiles.containsKey(fileEntry.getKey()));
            } else {
                Assert.assertTrue(gistFiles.containsKey(fileEntry.getKey()));
                HashMap<String, Object> gistFile = (HashMap<String, Object>) gistFiles.get(fileEntry.getKey());
                Assert.assertEquals(gistFile.get("content"), fileEntry.getValue());
            }
        }
    }
}

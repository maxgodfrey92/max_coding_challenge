package api;

import io.restassured.response.ValidatableResponse;
import org.junit.Assert;
import org.junit.Test;
import utils.TestUtils;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.not;

/**
 * Test file for the Gist API
 */
public class GistTest extends BaseAPITest {

    /**
     * Combines Test 1 and Test 2
     * Creates a new Gist, then verifies the created Gist is in the list of Gists for user
     */
    @Test
    public void createGist() {
        // Putting together the input data for the Gist
        String username = TestUtils.getProperty("GITHUB_USERNAME");
        HashMap<String, String> files = new HashMap<>();
        files.put("myFile.txt", "This is a test Gist");
        String description = "Testing Gist Creation API";

        // Creates new Gist
        this.gistClient.createGist(description, files);

        // Gets all Gists for user, and verifies created Gist is included
        this.gistClient.getAllGistsForUser(username).assertThat().body("description", hasItem(description));
    }

    /**
     * Test 3
     * Creates a new Gist, then updates and verifies Update successful
     */
    @Test
    public void updateGist() {
        // Putting together the input data for the Gist
        HashMap<String, String> files = new HashMap<>();
        files.put("myFile1.txt", "This file will be updated");
        files.put("myFile2.txt", "This file will be deleted");
        String description = "Testing Gist Update API";

        // Create a new Gist, and extracts the ID
        ValidatableResponse createdGist = this.gistClient.createGist(description, files);
        String gistId = createdGist.extract().path("id");

        // Verifies the files in the created Gist are correct
        verifyGistFiles(gistId, files);

        // Updates the input data for the Gist
        files.put("myFile1.txt", "Text successfully updated");
        files.put("myFile2.txt", "");

        // Updates the Gist using the new input data
        this.gistClient.updateGist(gistId, description, files);

        // Verifies the files in the updated Gist are correct
        verifyGistFiles(gistId, files);
    }

    /**
     * Test 4
     * Creates a new Gist, then deletes and ensure it has been removed
     */
    @Test
    public void deleteGist() {
        // Putting together the input data for the Gist
        String username = TestUtils.getProperty("GITHUB_USERNAME");
        HashMap<String, String> files = new HashMap<>();
        files.put("myFile3.txt", "This Gist will be deleted");
        String description = "Testing Gist Deletion API";

        // Create a new Gist, and extract the ID
        ValidatableResponse createdGist = this.gistClient.createGist(description, files);
        String gistId = createdGist.extract().path("id");

        // Gets all Gists for user, and verifies created Gist is included
        this.gistClient.getAllGistsForUser(username).assertThat().body("description", hasItem(description));

        // Deletes the created Gist
        this.gistClient.deleteGist(gistId);

        // Gets all Gists for user, and verifies created Gist is no longer included
        this.gistClient.getAllGistsForUser(username).assertThat().body("description", not(hasItem(description)));
    }

    /**
     * Helper function to verify the files inside of a Gist
     */
    public void verifyGistFiles(String gistId, HashMap<String, String> files) {
        // First get the Gist, and extract the files
        ValidatableResponse retrievedGist = this.gistClient.getGistById(gistId);
        HashMap<String, Object> gistFiles = retrievedGist.extract().path("files");

        // Loops through all files in the Gist
        for (Map.Entry<String, String> fileEntry : files.entrySet()) {
            if (fileEntry.getValue().equalsIgnoreCase("")) {
                // Check file isn't present if it has been removed in a patch
                Assert.assertFalse(gistFiles.containsKey(fileEntry.getKey()));
            } else {
                // Check file is present and checks the content of the file is as expected
                Assert.assertTrue(gistFiles.containsKey(fileEntry.getKey()));
                // Below cast could probably be done in a nicer way
                HashMap<String, Object> gistFile = (HashMap<String, Object>) gistFiles.get(fileEntry.getKey());
                Assert.assertEquals(gistFile.get("content"), fileEntry.getValue());
            }
        }
    }
}

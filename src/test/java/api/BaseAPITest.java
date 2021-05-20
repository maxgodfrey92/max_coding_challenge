package api;

import api.clients.GistClient;
import org.junit.Before;

/**
 * Base Class for API Tests to inherit
 * Contains simple Before method to create a new instance of Gist Client
 */
public class BaseAPITest {

    GistClient gistClient;

    @Before
    public void setup() {
        this.gistClient = new GistClient();
    }

}

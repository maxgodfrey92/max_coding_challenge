package api;

import api.clients.GistClient;
import org.junit.Before;

public class BaseAPITest {

    GistClient gistClient;

    @Before
    public void setup() {
        this.gistClient = new GistClient();
    }

}

package multiplayerTest;
import multiplayer.LocalServer;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import multiplayer.Guest;
import multiplayer.Host;

public class ConnectionTest {

    Host host;
    Guest guest;

    @Test
    void hostCreateLocalServer(){
        host = new Host();
        assertTrue(host.getLocalServer() instanceof LocalServer);
    }

}


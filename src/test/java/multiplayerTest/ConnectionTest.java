package multiplayerTest;
import multiplayer.LocalServer;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import multiplayer.Guest;
import multiplayer.Host;

public class ConnectionTest {

    Host host;
    Guest guest;
    LocalServer localServer;

    @Test
    void hostCreateLocalServer(){
        host = new Host();
        assertInstanceOf(LocalServer.class, host.getLocalServer());
    }

    @Test
    void hostConnectsToServer() {
        host = new Host();
        assertEquals(1, host.getLocalServer().getnConnection());
    }

    @Test
    void guestConnectsToServer() {
        localServer = new LocalServer(10000);
        localServer.start();
        guest = new Guest();
        assertEquals(1, localServer.getnConnection());
    }
}


package multiplayerTest;
import multiplayer.LocalServer;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import multiplayer.Guest;
import multiplayer.Host;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

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
    void hostConnectsToServer() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        host = new Host();

        new Thread(() -> {
            try {
                assertTrue(latch.await(5, TimeUnit.SECONDS));
                assertEquals(1, host.getLocalServer().getnConnection());
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }).start();
    }

    @Test
    void guestConnectsToServer() {
        CountDownLatch latch = new CountDownLatch(1);
        localServer = new LocalServer(10000);
        localServer.start();
        guest = new Guest();

        new Thread(() -> {
            try {
                assertTrue(latch.await(5, TimeUnit.SECONDS));
                assertEquals(1, localServer.getnConnection());
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }).start();
    }
}


package multiplayerTest;

import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;


public class ProtocolTest {

    @Test
    void formatTest() {
        String moveProtocol = String.format("%d;%d;%d;%d", 3, 4, 6, 7);
        assertEquals("3;4;6;7", moveProtocol);
    }

    @Test
    void splitTest() {
        String moveProtocol = String.format("%d;%d;%d;%d", 3, 4, 6, 7);
        String[] command = moveProtocol.split(";");
        assertEquals(4, command.length);
    }

    @Test
    void pointReturn() {
        String moveProtocol = String.format("%d;%d;%d;%d", 3, 4, 6, 7);
        String[] command = moveProtocol.split(";");
        Point oppStartTitle = new Point(Integer.parseInt(command[0]), Integer.parseInt(command[1]));
        Point oppEndTitle = new Point(Integer.parseInt(command[2]), Integer.parseInt(command[3]));
        Point[] points = new Point[]{oppStartTitle, oppEndTitle};
        assertEquals(new Point(3, 4), points[0]);
        assertEquals(new Point(6, 7), points[1]);
    }
}

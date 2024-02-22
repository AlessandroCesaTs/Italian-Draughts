package multiplayer;

import java.awt.*;

public interface MultiplayerActions {

    void sendMove(Point startTitle, Point endTitle, int messageType);

    void setReceivedMove(Point[] receivedMove, int newMove);

    Point[] getReceivedMove();
}

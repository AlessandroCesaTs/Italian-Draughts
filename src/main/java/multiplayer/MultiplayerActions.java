package multiplayer;

import java.awt.*;

public interface MultiplayerActions {

    void sendMove(Point startTitle, Point endTitle, int messageType);

    void connect();
    String getName();
    void setCanMove(boolean canMove);
    boolean isCanMove();
}

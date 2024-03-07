package logic;

import java.util.List;

public enum NeighborPosition {
    BOTTOM_LEFT, BOTTOM_RIGHT, TOP_LEFT, TOP_RIGHT;
    
    public static List<NeighborPosition> getNeighborPosition(){
        return List.of(BOTTOM_LEFT, BOTTOM_RIGHT, TOP_LEFT, TOP_RIGHT);
    }
}

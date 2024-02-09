package logic;

import java.util.List;

public enum NeighborPosition {
    BottomLeft,BottomRight,TopLeft,TopRight;
    
    public static List<NeighborPosition> getNeighborPosition(){
        return List.of(BottomLeft,BottomRight,TopLeft,TopRight);
    }
}

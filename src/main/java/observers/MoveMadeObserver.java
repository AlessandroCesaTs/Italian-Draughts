package observers;

import exceptions.NotOnDiagonalException;

public interface MoveMadeObserver {
    void onMoveMade() throws NotOnDiagonalException;
}

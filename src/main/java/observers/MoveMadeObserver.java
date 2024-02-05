package observers;

import Exceptions.NotOnDiagonalException;

public interface MoveMadeObserver {
    void onMoveMade() throws NotOnDiagonalException;
}

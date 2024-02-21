package observers;

import Exceptions.NotOnDiagonalException;
import Exceptions.OutOfBoundsException;

public interface MoveMadeObserver {
    void onMoveMade() throws NotOnDiagonalException,OutOfBoundsException;
}

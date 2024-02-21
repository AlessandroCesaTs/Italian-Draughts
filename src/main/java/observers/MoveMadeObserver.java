package observers;

import Exceptions.CantEatException;
import Exceptions.IllegalMovementException;
import Exceptions.NotOnDiagonalException;
import Exceptions.OutOfBoundsException;

public interface MoveMadeObserver {
    void onMoveMade() throws NotOnDiagonalException,OutOfBoundsException;
}

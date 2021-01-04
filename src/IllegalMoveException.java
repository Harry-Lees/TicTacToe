public class IllegalMoveException extends RuntimeException { // checking the square before placing a piece will prevent this exception (such happens in the AI)
    IllegalMoveException(String message) {
        super(message);
    }
}

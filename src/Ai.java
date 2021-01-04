public class Ai {
    public static int move(Board board) {
        int bestScore = -99;
        int bestMove = 1;

        for (int move : board.availableMoves()) {
            int score = minimax(board, 0);
            if (score > bestScore) {
                bestScore = score;
                bestMove = move;
            }
        }
        return bestMove;
    }


    public static int minimax(Board board, int depth) {
        int result = board.won();
        if (result>-1)
            return result == 1 ? 1 : -1;
    
        int turn = depth%2;
        for (int move : board.availableMoves()) {
            board.makeMove(move, turn);
            int score = minimax(board, depth++);
            board.unmakeMove(move, turn);

            if (turn == 0)
                return Math.max(score, Integer.MIN_VALUE);
            else
                return Math.min(score, Integer.MAX_VALUE);
        }
        return 0;
    }
}

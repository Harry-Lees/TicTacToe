import java.util.Arrays;
import java.util.ArrayList;

public class Board {
    private int[] players;

    private int[] wins = { // bitmasks for win conditions
        0b000000000000000111, // rows
        0b000000000000111000, 
        0b000000000111000000,

        0b000000000100100100, // columns
        0b000000000010010010, 
        0b000000000001001001,

        0b000000000100010001, // diagonals
        0b000000000001010100 
    };


    public Board() {
        players = new int[]{ // tic-tac-toe board annoyingly 3x3 so too big for a single byte. 
            0b000000000000000000, // X
            0b000000000000000000  // O
        };
    }


    public Board(int[] players) {
        this.players = players;
    }


    public void drawBoard() { // draw the board
        String[][] board = new String[3][3];
        for (int i = 8; i >= 0; i--) {
            board[i/3][i%3] = " ";

            if (((players[0] >> i) & 1) == 1)
                board[i/3][i%3] = "X";

            if (((players[1] >> i) & 1) == 1)
                board[i/3][i%3] = "O";
        }

        for (int i = 0; i < 3; i++) {
            System.out.println(Arrays.toString(board[i]));
        }
    }


    public ArrayList<Integer> availableMoves() { // returns a list of available moves for the current player, used for AI
        ArrayList<Integer> moves = new ArrayList<>();
        for (int i=0; i<9; i++) {
            int bit = ((players[0]|players[1])>>i)&1;
            if (bit == 0)
                moves.add(i+1);
        }
        return moves;
    }


    public String player(int n) { // String representation of given player
        return n == 0 ? "X" : "O";
    }


    public void makeMove(int n, int p) throws IllegalMoveException { // make a move for the current player
        if (n > 9) throw new IllegalMoveException("move must be between between 1-9");

        int bitMove = 1<<n-1; // bitboard representation of move
        if ((bitMove & (players[0] | players[1]))>>n-1 == 0) // check that the place is empty
            players[p] |= bitMove;
        else
            throw new IllegalMoveException("there is already a piece there, please pick a different square");
    }


    public void unmakeMove(int n, int p) { // terrible function, only called by AI so doesn't need to be that robust
        int bitMove = 1<<n-1; // bitboard representation of move
        players[p] ^= bitMove;
    }


    public boolean isPlaying() { // return whether the game is still in progress
        return !((players[0] | players[1]) == 511) && won() < 0;
    }


    public int won() { // returns the player that won or -1 if the game is still in progress
        for (int p=0; p<2; p++) // loop over players
            for (int i=0; i<8; i++) // loop over win bitmasks
                if ((players[p] & wins[i]) == wins[i])
                    return p;
        return -1;
    }

    public int[] getBitboards() {
        return players;
    }
}
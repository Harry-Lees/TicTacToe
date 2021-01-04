/*
Written by Harry Lees

Links:
- Tic-Tac-Toe bitboard http://libfbp.blogspot.com/2017/05/tic-tac-toe-bitboards.html
- MiniMax algorithm https://www.geeksforgeeks.org/minimax-algorithm-in-game-theory-set-1-introduction/

to-do:
- Finish Minimax class
*/

import java.util.Scanner;

public class Main {
    private static Scanner input;
    private static Board board;
    private static int p; // current player

    public static void main(String[] args) {
        input = new Scanner(System.in);
        board = new Board();
        p = 0; // 0 = human, 1 = AI

        while (board.isPlaying()) {            
            if (p == 0) {
                board.drawBoard();
                System.out.print("Please input a move (1-9): ");
                String s = input.nextLine();

                try {
                    int move = Integer.parseInt(s);
                    board.makeMove(move, p);
                } catch (Exception e) {
                    System.out.println("invalid input: " + e.getMessage());
                    continue;
                }
            } else {
                int move = Ai.move(board);
                board.makeMove(move, p);
            }
            p = 1-p; // toggle the move
        }

        int winner = board.won();
        board.drawBoard();
        if (winner > -1)
            System.out.println(board.player(winner) + " has Won!");
        else
            System.out.println("Draw!");

        input.close();
    }
}
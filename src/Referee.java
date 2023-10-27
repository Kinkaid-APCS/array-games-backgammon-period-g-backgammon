import java.util.InputMismatchException;
import java.util.Scanner;
/**
 * The Referee class keeps track of the board, any dice, and all interactions
 * with the players. It keeps track of whose turn it is, displays the board,
 * rolls dice, and asks the users to make their moves. 
 */
public class Referee {

	// TODO: decide which private member variables the Referee should have and declare them here.
	// suggestion: the dice are an array of integers, typically 1-6, where 0 means unrolled or 
	// used up already.
	private boolean continuePlaying;
	private DiceCup blackCup;
	private DiceCup whiteCup;
	private Board board;
	private boolean whiteTurn;
	private String winner;
	/**
	 * constructor - set up the board and players 
	 */
	// TODO: you write the Referee's constructor
	Referee() {
		continuePlaying = true;
		blackCup = new DiceCup(-1);
		whiteCup = new DiceCup(1);
		board = new Board();
		if ((int)(Math.random() * 2) == 0 ) {
			whiteTurn = true;
		}
	}
	/**
	 * playGame - the main game loop. Roll the dice, ask the user for a
	 * move, determine whether it is legal, and then execute the move. 
	 * Repeat for any remaining dice.
	 */

	public void playGame()
	{
		while (continuePlaying) {
			if(whiteTurn) {
				turnOrder(whiteCup);
			} else {
				turnOrder(blackCup);
			}

		}
	}

	public int whichMove(DiceCup player) {
		int move = -1;
		Scanner sc = new Scanner(System.in);
		while (move < 1 || move > 6) {
			System.out.println("Choose move: ");
			move = sc.nextInt();
		}
		if (player.player < 0) {
			move = move * -1;
		}
		return move;
	}
	//parameter player is positive or negative based on whether white or black is going
	public int whichLocation(DiceCup player) {
		int loc = -1;
		boolean goodAnswer = false;
		if (player.player > 0 && board.boardArray[0] > 0) {
			loc = 0;
			System.out.println("You are playing from the bar.");
		} else if (player.player < 0 && board.boardArray[25] < 0) {
			loc = 25;
			System.out.println("You are playing from the bar.");
		} else {
			Scanner sc = new Scanner(System.in);
			while (loc < 1 || loc > 24 || !goodAnswer) {

				try {
					System.out.println("Choose location: ");
					loc = sc.nextInt();
					goodAnswer = true;
				} catch (InputMismatchException imExp) {
					goodAnswer = false;
					sc.nextLine();
				}
			}
		}
		return loc;
	}

	public void turnOrder (DiceCup player) {
		player.roll();
		if (!board.anySpacesAvailable(player, board)) {
			if (whiteTurn) {
				System.out.println("White's Turn");
			} else {
				System.out.println("Black's Turn");
			}
			System.out.println(player.toString());
			System.out.println("No valid moves. (Turn Skipped)");
		}
		while (player.hasMovesLeft() && board.anySpacesAvailable(player, board)) {
			System.out.println(board.toString());
			if (whiteTurn) {
				System.out.println("White's Turn");
			} else {
				System.out.println("Black's Turn");
			}
			System.out.println(player.toString());
			int location = whichLocation(player);
			int move = whichMove(player);
			while (!(board.isLegal(location, move, player) && player.isLegal(move))) {
				System.out.println("Not a valid move.");
				location = whichLocation(player);
				move = whichMove(player);
			}
			board.makeMove(location, move);
			player.moveMade(move);
		}
		if (board.doesTeamWin(player)) {
			continuePlaying = false;
			if (player.player > 0) {
				winner = "WHITE WINS!!!";
			} else {
				winner = "BLACK WINS!!!";
			}
		}
		whiteTurn = !whiteTurn;
	}
}

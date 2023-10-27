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
		Scanner sc = new Scanner(System.in);
		System.out.println("Choose move: ");
		if (player.player > 0) {
			return sc.nextInt();
		} else {
			return -1 * sc.nextInt();
		}
	}
	//parameter player is positive or negative based on whether white or black is going
	public int whichLocation(DiceCup player) {
		int loc;
		if (player.player > 0 && board.boardArray[0] > 0) {
			loc = 0;
			System.out.println("You are playing from the bar.");
		} else if (player.player < 0 && board.boardArray[25] < 0) {
			loc = 25;
			System.out.println("You are playing from the bar.");
		} else {
			Scanner sc = new Scanner(System.in);
			System.out.println("Choose location: ");
			loc = sc.nextInt();
		}
		return loc;
	}

	public void turnOrder (DiceCup player) {
		player.roll();
		while (player.hasMovesLeft() && board.anySpacesAvailable(player, board)) {
			System.out.println(board.toString());
			if (whiteTurn) {
				System.out.println("White's Turn");
			} else {
				System.out.println("Black's Turn");
			}
			System.out.println(player.toString());
			int move = whichMove(player);
			int location = whichLocation(player);
			while (!(board.isLegal(location, move, player) && player.isLegal(move))) {
				System.out.println("Not a valid move.");
				move = whichMove(player);
				location = whichLocation(player);
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

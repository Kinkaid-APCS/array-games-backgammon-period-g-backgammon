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
			System.out.println(board.toString());
			if(whiteTurn) {
				System.out.println("White's Turn");
				whiteCup.roll();
				System.out.println(whiteCup.toString());
				while (whiteCup.hasMovesLeft() && board.anySpacesAvailable(whiteCup, board)) {
					int move = whichMove();
					int location = whichLocation(whiteCup);
					while (!board.isLegal(location, move) && !whiteCup.isLegal(move)) {
						System.out.println("Not a valid move.");
						move = whichMove();
						location = whichLocation(whiteCup);
					}
					board.makeMove(location, move);
				}
			} else {
				System.out.println("Brown's Turn"); //actually black, but brown looks better
				blackCup.roll();
				System.out.println(blackCup.toString());
				while (blackCup.hasMovesLeft() && board.anySpacesAvailable(blackCup, board)) {
					int move = whichMove();
					int location = whichLocation(blackCup);
					while (!board.isLegal(location, move) && !blackCup.isLegal(move)) {
						System.out.println("Not a valid move.");
						move = whichMove();
						location = whichLocation(blackCup);
					}
					board.makeMove(location, move);
				}
			}

		}
	}

	public int whichMove() {
		Scanner sc = new Scanner(System.in);
		System.out.println("Choose move: ");
		int input = sc.nextInt();
		sc.close();
		return input;
	}
	//parameter player is positive or negative based on whether white or black is going
	public int whichLocation(DiceCup player) {
		int loc;
		if (player.player > 0 && board.boardArray[0] > 0) {
			loc = 0;
			System.out.println("You are playing from the bar.");
		} else if (player.player < 0 && board.boardArray[25] < 0) {
			loc = 0;
			System.out.println("You are playing from the bar.");
		} else {
			Scanner sc = new Scanner(System.in);
			System.out.println("Choose location: ");
			loc = sc.nextInt();
			sc.close();
		}
		return loc;
	}
}

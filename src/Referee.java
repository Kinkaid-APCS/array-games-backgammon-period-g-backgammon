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
			board.toString();
			if(whiteTurn) {
				whiteCup.roll();
				System.out.println(whiteCup.toString());
				while (whiteCup.hasMovesLeft() && board.anySpacesAvailable(whiteCup)) {
					int move = whichMove();
					int location = whichLocation();
					while (!board.isLegal(location, move) && !whiteCup.isLegal(move)) {
						System.out.println("Not a valid move.");
						move = whichMove();
						location = whichLocation();
					}
					board.makeMove(location, move);
				}
			} else {
				blackCup.roll();
				System.out.println(blackCup.toString());
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

	public int whichLocation() {
		Scanner sc = new Scanner(System.in);
		System.out.println("Choose location: ");
		int input = sc.nextInt();
		sc.close();
		return input;
	}
}

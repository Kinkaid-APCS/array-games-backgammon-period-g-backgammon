/**
 * The board class keeps track of 24 spaces (the skinny triangles on 
 * a regular board) and the bar for each player. It keeps track of 
 * these values and determines whether a given move is legal or not.
 */
public class Board {
	// TODO: decide which private member variables the Board class needs, and declare them here.
	// suggestion - for the 24 spaces, I suggest an array that holds the number
	// of pieces on each square. One player will have positive numbers, and
	// the other will have negative numbers. So if space (5) contains 3, that
	// means that there are 3 white pieces trying to move to higher numbered
	// spaces, and if space (7) contains -2, that means that there are 2 black
	// pieces trying to move to lower numbered spaces. 
	
	// Locations 0 and 25 are the bars (penalty zones) for the two teams - if 
	// the "negative" team is trying to move its pieces to smaller numbers,
	// then moving them to 0 or less actually removes them from the board - they
	// don't go to position 0. On the other hand if a "positive-moving" piece is
	// captured, it goes to position 0, the farthest point from it's goal of 25 or
	// more.
	private int[] boardArray;
	private String blackChar;
	private String whiteChar;
	/**
	 * constructor - set up the starting locations of the pieces.
	 */
	public Board()
	{
		blackChar = "⚫";
		whiteChar = "⚪";
		boardArray = new int[26];
		for (int i = 0; i < boardArray.length; i++) {
			if (i == 1) {
				//positive is white
				boardArray[i] = 2;
			} else if (i == 6 || i == 13) {
				//negative is black
				boardArray[i] = -5;
			} else if (i == 12 || i == 19) {
				boardArray[i] = 5;
			} else if (i == 8) {
				boardArray[i] = -3;
			} else if (i == 17) {
				boardArray[i] = 3;
			} else if (i == 24) {
				boardArray[i] = -2;
			} else {
				boardArray[i] = 0;
			}
		}
	}
	
	/**
	 * toString - create a string representing the state of the board.
	 * @return a string containing the board state.
	 * for example, it might look like:
	 * 0 (BAR) O
	 * 1
	 * 2 OO
	 * 3 OOO
	 * 4 XX
	 * 5 
	 * 6 XXXXX
	 * ....
	 * 23 O
	 * 24 XX
	 * 25 (BAR) XX
	 */
	public String toString()
	{
		StringBuilder result = new StringBuilder();
		String bar;
		//--------------------
		for (int i = 0; i < boardArray.length; i ++) {
			bar = "";
			if (i == 0 || i == 25) {
				bar = "(BAR) ";
			}
			result.append("\n ").append(i).append(" ").append(bar);
			for (int j = 0; j < Math.abs(boardArray[i]); j++) {
				if (boardArray[i] > 0) {
					result.append(whiteChar);
				} else {
					result.append(blackChar);
				}
			}
		}
		
		//--------------------
		return result.toString();
	}
	
	/**
	 * playerHasPieceAtLocation - determines whether the player has at 
	 * least one chip at the given space.
	 * @param location - the number of the space in question.
	 * @return whether (true/false) the player has a chip of his/her own 
	 * color at this space.
	 */
	public int howManyPieceAtLocation(int location)
	{
		return boardArray[location];
	}
	
	/**
	 * isLegal - determines whether a chip at the given space can move
	 * the desired number of spaces
	 * @param - startingSpace
	 * @param - numSpacesToMove (this is a positive number, but might be 
	 * a move up or down, depending on what chip is in the starting space)
	 * @return whether (true/false) the player is allowed to make such a move.
	 * precondition: yes, there's at least one chip in the space.
	 * postcondition: the board is unchanged.
	 */
	public boolean isLegal(int startingSpace, int numSpaces)
	{
		boolean legal = false;
		if (numSpaces > 0) {
			if (boardArray[startingSpace + numSpaces] > -2) {
				legal = true;
			}
		}
		if (numSpaces < 0) {
			if (boardArray[startingSpace + numSpaces] < 2) {
				legal = true;
			}
		}
		return legal;
		
	}
	
	/**
	 * makeMove - moves one chip from the given space by the specified amount;
	 * @param - startingSpace
	 * @param - numSpacesToMove (this is a positive number, but might be 
	 * a move up or down, depending on what chip is in the starting space)
	 * precondition: there is a chip at the starting space
	 * postcondition: the chip may be moved to a different space, or off the board.
	 * If the chip lands on a single enemy piece, that piece is sent to its bar.
	 */
	public void makeMove(int startingSpace, int numSpacesToMove)
	{
		if (numSpacesToMove > 0) {
			boardArray[startingSpace] -= 1;
			boardArray[startingSpace + numSpacesToMove] += 1;
		}
		if (numSpacesToMove < 0) {
			boardArray[startingSpace] += 1;
			boardArray[startingSpace + numSpacesToMove] -= 1;
		}
	}
	
	
	/**
	 * gameIsOver - determines whether either player has removed all 
	 * his/her pieces from the board.
	 * @return - whether (true/false) the game is over.
	 */
	public boolean gameIsOver()
	{
		boolean gameOver = false;
		//--------------------
		// TODO: Insert your code here
		
		//--------------------
		return gameOver;
	}
}

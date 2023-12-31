/**
 * The dice cup is a class that takes care of rolling two dice, determining whether 
 * doubles were rolled, and keeping track of which moving numbers have been used.
 *
 */
public class DiceCup {
	public int player; //if +1 then white, if -1 then black
	private int die1, die2;
	private int[] availableMoves; // this will be four numbers, though positions
								 //     2 and 3 will often be zero.
	
	public DiceCup(int player)
	{
		availableMoves = new int[]{0, 0, 0, 0};
		this.player = player;
    }
	/**
	 * calculateAvailableMoves - based on the information stored in die1 and die2,
	 * determines which moves could be made by the player and stores them in an
	 * array. This is pretty straightforward, if you don't roll doubles.
	 * If the dice are doubles, then the player gets 4 iterations of the value of
	 *  the faces on the dice.
	 * So if die1 = 5 and die2 = 2, then availableMoves is [5,2,0,0]. (in any order)
	 * If die1 = 3 and die3 = 3, then availableMoves is [3,3,3,3]. 
	 */
	public void calculateAvailableMoves()
	{
		//--------------------
		// TODO: insert your code here.
		int equalVariable = 0;
		if (isDoubles(die1, die2)) {
			equalVariable = die1;
		}
		availableMoves[0] = die1;
		availableMoves[1] = die2;
		availableMoves[2] = equalVariable;
		availableMoves[3] = equalVariable;
		//--------------------
	}
	/**
	 * pick two numbers for the dice, and copy these to the available moves. In the
	 * case of doubles, there are four instances. For instance, if the
	 * dice are 3 and 5, then availableMoves = [3, 5, 0, 0]. If the dice are 6 and 6, then 
	 * availableMoves = [6, 6, 6, 6].
	 */
	public void roll()
	{
		//--------------------
		// TODO: insert your code here.
		// Hint: (int)(Math.random()*10) gives a random number from 0 to 9, inclusive.
		// Hint: write calculateAvailableMoves() before you write this one.
		//--------------------
		die1 = (int)(Math.random()*6) + 1;
		die2 = (int)(Math.random()*6) + 1;
		if (player < 0) {
			die1 *= -1;
			die2 *= -1;
		}
		calculateAvailableMoves();
	}
	/**
	 * returns a string describing the dice, the available (non-zero) moves and whether this 
	 * was doubles.
	 * For example, if the player just rolled a 2 and a 5 the board might look like:
	 *   +-+ +-+
	 *   |2| |5|
	 *   +-+ +-+
	 *   Available: 2, 5
	 *   (although availableMoves might be [2, 5, 0, 0].)
	 * If the player rolled a pair of threes and has already used one three, the board
	 * might look like:
	 *   +-+ +-+
	 *   |3| |3| doubles
	 *   +-+ +-+
	 *   Available: 3, 3, 3
	 * 	(although availableMoves might be [0, 3, 3, 3].)
	 * @return a string describing the state of the dice and available non-zero moves.
	 */
	public String toString()
	{
		StringBuilder result = new StringBuilder();
		String[] diceEmojis = {"", "⚀", "⚁", "⚂", "⚃", "⚄", "⚅"};
		int[] moves = new int[availableMoves.length];
		for (int i = 0; i < availableMoves.length; i++) {
			moves[i] = Math.abs(availableMoves[i]);
		}
		//--------------------
		// TODO: insert your code here.
		for (int i = 0; i < 4; i++) {
			if (moves[i] != 0) {
				result.append(diceEmojis[moves[i]]).append(" ");
			}
		}
		result.append(": ");
		for (int i = 0; i < 4; i++) {
			if (moves[i] != 0) {
				result.append(moves[i]).append(" ");
			}
		}

		//--------------------
		return result.toString();
		
	}

	public boolean isLegal(int amountToMove)
	{
		boolean legal = false;
		for (int i : availableMoves) {
			if (i == amountToMove) {
				legal = true;
				break;
			}
		}
		return legal;
	}

	public boolean isDoubles(int die1, int die2)
	{
		return die1 == die2;
	}
	
	/**
	 * finds the first instance of the amountToMove in availableMoves and resets
	 * it to zero.
	 * @param amountToMove
	 * precondition: amountToMove is a legal move.
	 */
	public void moveMade(int amountToMove)
	{
		for (int i = 0; i < availableMoves.length; i++) {
			if (availableMoves[i] == amountToMove) {
				availableMoves[i] = 0;
				break;
			}
		}
	}
	
	/**
	 * hasMovesLeft - indicates whether there are still non-zero
	 * values in availableMoves.
	 * @return whether moves are still available.
	 */
	public boolean hasMovesLeft()
	{
		boolean hasMoves = false;
		for (int i : availableMoves) {
			if (i != 0) {
				hasMoves = true;
				break;
			}
		}
		return hasMoves;
	}

	public int[] debugGetAvailableMoves()
	{
		return availableMoves;
	}
}

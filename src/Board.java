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
	// captured, it goes to position 0, the farthest point from its goal of 25 or
	// more.
	public int[] boardArray;
	private final String blackChar;
	private final String whiteChar;
	/**
	 * constructor - set up the starting locations of the pieces.
	 */
	public Board()
	{
		blackChar = "🟤";
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

	public boolean isLegal(int startingSpace, int numSpaces, DiceCup player)
	{
		boolean legal = false;
		boolean readyToRemove = true;
		if ((player.player > 0 && boardArray[startingSpace] > 0) || (player.player < 0 && boardArray[startingSpace] < 0)) {
			if (startingSpace + numSpaces > 25 || startingSpace + numSpaces < 0) {
				readyToRemove = false; //stand in code
			} else if (numSpaces > 0) {
				for (int i = 0; i < boardArray.length; i++) {
					if (boardArray[i] > 0 && i < 19) {
						readyToRemove = false;
						break;
					}
				}
				if (startingSpace + numSpaces == 25) {
					legal = readyToRemove;
				} else {
					if (boardArray[startingSpace + numSpaces] > -2) {
						legal = true;
					}
				}
			} else {
				for (int i = 0; i < boardArray.length; i++) {
					if (boardArray[i] < 0 && i > 6) {
						readyToRemove = false;
						break;
					}
				}
				if (startingSpace + numSpaces == 0) {
					legal = readyToRemove;
				} else {
					if (boardArray[startingSpace + numSpaces] < 2) {
						legal = true;
					}
				}
			}
		}
		return legal;
	}
	

	public void makeMove(int startingSpace, int numSpacesToMove)
	{
		if (numSpacesToMove > 0) {
			boardArray[startingSpace] -= 1;
			if (startingSpace + numSpacesToMove != 25) {
				if (boardArray[startingSpace + numSpacesToMove] < 0) {
					boardArray[startingSpace + numSpacesToMove] = 0;
					boardArray[25] -= 1;
				}
				boardArray[startingSpace + numSpacesToMove] += 1;
			}
			//white moves
		}
		if (numSpacesToMove < 0) {
			boardArray[startingSpace] += 1;
			if (startingSpace + numSpacesToMove != 0) {
				if (boardArray[startingSpace + numSpacesToMove] > 0) {
					boardArray[startingSpace + numSpacesToMove] = 0;
					boardArray[0] += 1;
				}
				boardArray[startingSpace + numSpacesToMove] -= 1;
			}
		}
	}
	
	
	/**
	 * gameIsOver - determines whether either player has removed all 
	 * his/her pieces from the board.
	 * @team will be positive for white or negative for black
	 * @return - whether (true/false) the game is over.
	 */
	public boolean doesTeamWin(DiceCup player) {
		boolean teamWins = true;
		//--------------------
        for (int i : boardArray) {
            if (player.player > 0 && i > 0) {
                teamWins = false;
                break;
            } else if (player.player < 0 && i < 0) {
                teamWins = false;
                break;
            }
        }
		return teamWins;
	}

	public boolean anySpacesAvailable(DiceCup player, Board board) {
		boolean anyMoves = false;
		if (player.player > 0 && board.boardArray[0] > 0) {
			for (int i: player.debugGetAvailableMoves()) {
				anyMoves = isLegal(0, i, player);
				if (anyMoves) {
					break;
				}
			}
		} else if (player.player < 0 && board.boardArray[25] < 0) {
			for (int i: player.debugGetAvailableMoves()) {
				anyMoves = isLegal(25, i, player);
				if (anyMoves) {
					break;
				}
			}
		} else {
			for (int i : player.debugGetAvailableMoves()) {
				for (int j = 0; j < boardArray.length; j++) {
					if (player.player > 0) {
						while (boardArray[j] < 1 && j < 25) {
							j++;
						}
					}
					if (player.player < 0) {
						while (boardArray[j] > -1 && j < 25) {
							j++;
						}
					}
					if (isLegal(j, i, player)) {
						anyMoves = true;
						break;
					}
				}
			}
		}
		return anyMoves;
	}
}

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
	private static final String ANSI_COLOR = "\u001b[48;5;53m";
	private static final String ANSI_RESET = "\u001B[0m";
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
		String extra;
		String extra2;
		String color;
		//--------------------
		for (int i = 0; i < boardArray.length; i ++) {
			extra = "";
			color = "";
			if (i == 0) {
				extra = " (BAR) ";
			}
			if (i == 25) {
				extra = "(BAR) ";
			}
			if (i < 7 && i > 0) {
				extra = " (🟤HOME) ";
			}
			if (i < 25 && i > 18) {
				extra = "(⚪️HOME) ";
			}
			if (i > 6 && i < 10) {
				extra = " (------) ";
			}
			if (i > 9 && i < 19) {
				extra = "(------) ";
			}
			if (i < 7 && i > 0) {
				color = (ANSI_COLOR);
			}
			if (i < 25 && i > 18) {
				color = ANSI_COLOR;
			}
			//result.append("\n ").append(color).append(i).append(ANSI_RESET).append(" ").append(extra);
			result.append("\n ").append(i).append(" ").append(extra);
			for (int j = 0; j < Math.abs(boardArray[i]); j++) {
				if (boardArray[i] > 0) {
					result.append(whiteChar);
				} else {
					result.append(blackChar);
				}
			}
		}

//		for (int i = 0; i < boardArray.length/2; i ++) {
//			extra = "";
//			extra2 = "";
//			color = "";
//			if (i == 0) {
//				extra = " (BAR)    ";
//				extra2 = "(BAR)    ";
//			} else if (i == 6) {
//				extra = " (🟤HOME) ";
//				extra2 = "(⚪️HOME) ";
//			} else if (i > 9) {
//				extra = "(------) ";
//				extra2 = "(------) ";
//			} else {
//				extra = " (------) ";
//				extra2 = "(------) ";
//			}
//			result.append("\n ").append(i).append(" ").append(extra);
//			for (int j = 0; j < Math.abs(boardArray[i]); j++) {
//				if (boardArray[i] > 0) {
//					result.append(whiteChar);
//				} else {
//					result.append(blackChar);
//				}
//			}
//			for (int k = 0; k < 15 - Math.abs(boardArray[i]); k++) {
//				result.append("  ");
//			}
//			if (boardArray[i] == 0) {
//				result.append(" ");
//			}
//			result.append(25 - i).append(" ").append(extra2);
//			for (int j = 0; j < Math.abs(boardArray[i]); j++) {
//				if (boardArray[25 - i] > 0) {
//					result.append(whiteChar);
//				} else {
//					result.append(blackChar);
//				}
//			}
//		}
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
				if (i != 0) {
					anyMoves = isLegal(0, i, player);
				}
				if (anyMoves) {
					break;
				}
			}
		} else if (player.player < 0 && board.boardArray[25] < 0) {
			for (int i: player.debugGetAvailableMoves()) {
				if (i != 0) {
					anyMoves = isLegal(25, i, player);
				}
				if (anyMoves) {
					break;
				}
			}
		} else {
			for (int i : player.debugGetAvailableMoves()) {
				if (i != 0) {
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
		}
		return anyMoves;
	}
}

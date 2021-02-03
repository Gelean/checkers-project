import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

public class CheckersAI
{
	private int colorOfAI;
	private int maxPlies;

	public CheckersAI(int colorOfAI, int maxPlies)
	{
		this.colorOfAI = colorOfAI;
		this.maxPlies = maxPlies;
	}

	private void multiJumpCheck(Board newBoard, int color)
	{
		while(newBoard.isMultiJump())
		{
			Move prevMove = newBoard.moveToMake();
			int row = prevMove.getRow();
			int col = prevMove.getColumn(); 

			if(color == Board.BLACK)
			{
				if(row > 1 && col < 6 && newBoard.isMoveLegal(row-2, col+2, prevMove, newBoard.pieceAtPosition(row, col)))
					newBoard = newBoard.tryMove(new Move(row-2, col+2), prevMove, newBoard.pieceAtPosition(row, col), newBoard);
				else if(row > 1 && col > 1 && newBoard.isMoveLegal(row-2, col-2, prevMove, newBoard.pieceAtPosition(row, col)))
					newBoard = newBoard.tryMove(new Move(row-2, col-2), prevMove, newBoard.pieceAtPosition(row, col), newBoard);
				else if(newBoard.pieceAtPosition(row, col) == Board.BLACKKING)
				{
					if(row < 6 && col < 6 && newBoard.isMoveLegal(row+2, col+2, prevMove, newBoard.pieceAtPosition(row, col)))
						newBoard = newBoard.tryMove(new Move(row+2, col+2), prevMove, newBoard.pieceAtPosition(row, col), newBoard);
					else if(row < 6 && col > 1 && newBoard.isMoveLegal(row+2, col-2, prevMove, newBoard.pieceAtPosition(row, col)))
						newBoard = newBoard.tryMove(new Move(row+2, col-2), prevMove, newBoard.pieceAtPosition(row, col), newBoard);
				}
			}
			else //if(color == Board.WHITE)
			{
				if(row < 6 && col < 6 && newBoard.isMoveLegal(row+2, col+2, prevMove, newBoard.pieceAtPosition(row, col)))
					newBoard = newBoard.tryMove(new Move(row+2, col+2), prevMove, newBoard.pieceAtPosition(row, col), newBoard);
				else if(row < 6 && col > 1 && newBoard.isMoveLegal(row+2, col-2, prevMove, newBoard.pieceAtPosition(row, col)))
					newBoard = newBoard.tryMove(new Move(row+ 2, col-2), prevMove, newBoard.pieceAtPosition(row, col), newBoard);
				else if(newBoard.pieceAtPosition(row, col) == Board.WHITEKING)
				{
					if(row > 1 && col < 6 && newBoard.isMoveLegal(row-2, col+2, prevMove, newBoard.pieceAtPosition(row, col)))
						newBoard = newBoard.tryMove(new Move(row-2, col+2), prevMove, newBoard.pieceAtPosition(row, col), newBoard);
					else if(row > 1 && col > 1 && newBoard.isMoveLegal(row-2, col-2, prevMove, newBoard.pieceAtPosition(row, col)))
						newBoard = newBoard.tryMove(new Move(row-2, col-2), prevMove, newBoard.pieceAtPosition(row, col), newBoard);	
				}
			}
		}
	}

	public TransPosTableEntry chooseMove(GameClock gameClock, Board board, TransPosTable gameStates, int turnCount) //in progress
	{
		//int alpha = Integer.MIN_VALUE;
		//int beta = Integer.MAX_VALUE;

		int turns = turnCount;
		Board newBoard = null, bestBoard = null;
		TransPosTableEntry newEntry = null;
		int miniMaxValue, bestValue = Integer.MIN_VALUE;
		Iterator<Pair> moveIterator;
		boolean jumpsAvailable = false, moveWasMade = false;

		if((colorOfAI == Board.BLACK && board.getJumps().getBlackJumpCount() != 0) ||
				(colorOfAI == Board.WHITE && board.getJumps().getWhiteJumpCount() != 0))
		{
			jumpsAvailable = true;
		}

		boolean phase2 = false;
		boolean phase3 = false;
		int tempPlies = maxPlies;
		int loopIterations=0;
		ArrayList<TransPosTableEntry> IDstates = new ArrayList<TransPosTableEntry>(); 
		ArrayList<TransPosTableEntry> IDphase3 = new ArrayList<TransPosTableEntry>();

		for(int i=0;i<3;i++)
		{
			if(phase3 && !phase2)
			{
				//System.out.println("PHASE 3");
				miniMaxValue = Integer.MIN_VALUE;
				bestBoard = null;
				bestValue = Integer.MIN_VALUE;
				//alpha = Integer.MIN_VALUE;
				//beta = Integer.MAX_VALUE;

				Collections.sort(IDphase3);
				maxPlies = tempPlies;

				//(if running to slow)Might want to just do top 2 after the 5 ply search on half of list it should be in a good order
				//if(IDphase3.size() == 2)
				loopIterations = 2;
				//else
				//	loopIterations = 3;

				for(int j =0;j<(loopIterations);j++)
				{
					Board tempBoard = new Board(IDphase3.get(j).getBoard());

					if(gameStates.contains(tempBoard.getCurrZobrist()) && (colorOfAI == gameStates.retrieveEntry(tempBoard.getCurrZobrist()).getColor()) 
							&& gameStates.retrieveEntry(tempBoard.getCurrZobrist()).getDepth() >= maxPlies)
					{
						miniMaxValue = gameStates.retrieveEntry(tempBoard.getCurrZobrist()).getEval();
						//System.out.println("USED A SAVE STATE!!!!!!!" + "Score:" + miniMaxValue);
					}
					else
						miniMaxValue = minValue(tempBoard, 1, -colorOfAI, Integer.MIN_VALUE, Integer.MAX_VALUE,board,gameStates,turns);
					//System.out.println("Score:" + miniMaxValue);

					if(miniMaxValue > bestValue)
					{
						bestBoard = IDphase3.get(j).getBoard();
						bestValue = miniMaxValue;
						//System.out.println("BestValue: "+bestValue);
						newEntry = new TransPosTableEntry(bestBoard,bestValue,maxPlies, colorOfAI);
					}
				}				
			}
			else if(phase2 && !phase3)
			{
				//System.out.println("PHASE 2");
				miniMaxValue = Integer.MIN_VALUE;
				bestBoard = null;
				bestValue = Integer.MIN_VALUE;
				//alpha = Integer.MIN_VALUE;
				//beta = Integer.MAX_VALUE;

				Collections.sort(IDstates);
				//System.out.println("MAXPLIES: "+tempPlies+" :: SIZE: "+IDstates.size());

				if(tempPlies > 6)
					maxPlies = 7;//5;
				else
					maxPlies = tempPlies;

				//Can never be 0, should be detected a win before it could decide to call makeAImove?
				//If only 1 move(a jump usually) will make move in phase2 and skip phase3, move will be done.
				if(IDstates.size() == 0)
				{
					phase3 = true;
					phase2 = true;
					maxPlies = tempPlies;
				}
				else if((IDstates.size() == 1))
				{
					bestBoard = IDstates.get(0).getBoard();
					bestValue = IDstates.get(0).getEval();
					//System.out.println("BestValue: "+bestValue);
					newEntry = new TransPosTableEntry(bestBoard,bestValue,0,colorOfAI);

					phase3 = true;
					phase2 = true;
					maxPlies = tempPlies;
				}
				else if(IDstates.size() >= 2)
				{
					if(IDstates.size() == 3)
						loopIterations = 3;
					else
						loopIterations = 4;//(int)IDstates.size()/2;

					//Should phase 2 be skipped if only 2 moves available?(Usually happens when a player has a choice between
					//two jumps) CURRENTLY being skipped.
					if(IDstates.size() != 2)
					{
						for(int j =0;j<(loopIterations);j++)
						{
							Board tempBoard = new Board(IDstates.get(j).getBoard());

							if(gameStates.contains(tempBoard.getCurrZobrist()) && (colorOfAI == gameStates.retrieveEntry(tempBoard.getCurrZobrist()).getColor()) 
									&& gameStates.retrieveEntry(tempBoard.getCurrZobrist()).getDepth() >= maxPlies)
							{
								miniMaxValue = gameStates.retrieveEntry(tempBoard.getCurrZobrist()).getEval();
								//System.out.println("USED A SAVE STATE!!!!!!!" + "Score:" + miniMaxValue);
							}
							else
								miniMaxValue = minValue(tempBoard, 1, -colorOfAI, Integer.MIN_VALUE,Integer.MAX_VALUE,board,gameStates,turns);

							//System.out.println("Score:" + miniMaxValue);

							if(miniMaxValue > bestValue)
							{
								bestBoard = IDstates.get(j).getBoard();
								bestValue = miniMaxValue;
								//System.out.println("BestValue: "+bestValue);
								newEntry = new TransPosTableEntry(bestBoard,bestValue,maxPlies, colorOfAI);								
							}
							IDphase3.add(new TransPosTableEntry(tempBoard,miniMaxValue,maxPlies,colorOfAI));
						}
					}
					else
					{
						for(int j =0;j<2;j++)
							IDphase3.add(new TransPosTableEntry(IDstates.get(j).getBoard(),IDstates.get(j).getEval(),
									IDstates.get(j).getDepth(),IDstates.get(j).getColor()));
					}
					phase3 = true;
					phase2 = false;
					maxPlies = tempPlies;
				}
			}
			else if(!phase2 && !phase3)
			{
				//System.out.println(colorOfAI + ": PHASE 1");

				if(tempPlies > 5)
					maxPlies = 4;//5;
				else
					maxPlies = 2;
				
				if((colorOfAI == Board.BLACK && board.getJumps().getBlackJumpCount() == 1) ||
						(colorOfAI == Board.WHITE && board.getJumps().getWhiteJumpCount() == 1))
				{
					newBoard = new Board(board);
					Iterator<Pair> jump = board.getJumps().getJump().iterator();
					while(jump.hasNext())
					{
						Pair p = new Pair(jump.next());
						Move move = p.getMove();
						Move prevMove = p.getPrevMove();

						if(((colorOfAI == newBoard.pieceAtPosition(prevMove.getRow(), prevMove.getColumn())) ||
								(colorOfAI*2 == newBoard.pieceAtPosition(prevMove.getRow(), prevMove.getColumn()))) &&
								(newBoard.isMoveLegal(move.getRow(), move.getColumn(), prevMove, newBoard.pieceAtPosition(prevMove.getRow(), prevMove.getColumn()))))
						{

							moveWasMade = true;
							newBoard = newBoard.tryMove(move, prevMove, newBoard.pieceAtPosition(prevMove.getRow(), prevMove.getColumn()), newBoard);

							if(newBoard.isMultiJump())
								multiJumpCheck(newBoard, colorOfAI);

							bestBoard = newBoard;
							bestValue = evaluationFunction(turns,colorOfAI, bestBoard, null, gameStates);
							newEntry = new TransPosTableEntry(bestBoard,bestValue,0,colorOfAI);
							IDstates.add(newEntry);
						}
					}
				}
				else
				{
					if(colorOfAI == Board.BLACK)
						moveIterator = board.getBlackMoves().iterator();
					else
						moveIterator = board.getWhiteMoves().iterator();

					while(moveIterator.hasNext())//iterator2.hasNext())
					{
						newBoard = new Board(board);
						Pair curr = new Pair(moveIterator.next());
						Move prevMove = curr.getPrevMove();
						Move move = curr.getMove();

						if((colorOfAI == newBoard.pieceAtPosition(prevMove.getRow(), prevMove.getColumn()) ||
								colorOfAI*2 == newBoard.pieceAtPosition(prevMove.getRow(), prevMove.getColumn())) && 
								(newBoard.isMoveLegal(move.getRow(), move.getColumn(), prevMove, newBoard.pieceAtPosition(prevMove.getRow(), prevMove.getColumn())))
								&& ((jumpsAvailable && newBoard.isJumpAvailable(move,prevMove, newBoard.pieceAtPosition(prevMove.getRow(), prevMove.getColumn())))
										|| !jumpsAvailable))
						{
							moveWasMade = true;
							Board prevBoard = new Board(newBoard);
							newBoard = newBoard.tryMove(move, prevMove, newBoard.pieceAtPosition(prevMove.getRow(), prevMove.getColumn()), newBoard); //doMove

							if(newBoard.isMultiJump())
								multiJumpCheck(newBoard, colorOfAI);

							if(gameStates.contains(newBoard.getCurrZobrist()) && (colorOfAI == gameStates.retrieveEntry(newBoard.getCurrZobrist()).getColor()) 
									&& gameStates.retrieveEntry(newBoard.getCurrZobrist()).getDepth() >= maxPlies) 
							{
								miniMaxValue = gameStates.retrieveEntry(newBoard.getCurrZobrist()).getEval();
								//System.out.println("USED A SAVE STATE!!!!!!!" + "Score:" + miniMaxValue);
							}
							else
								miniMaxValue = minValue(newBoard, 1, -colorOfAI, Integer.MIN_VALUE, Integer.MAX_VALUE,prevBoard,gameStates, turns);
							//System.out.println("Score:" + miniMaxValue);
							//search half of maxPlies first and store all newBoards here in a list, sort based on value,choose the top 3 boards from the list
							//and perform a search on those boards to see which one is actually bestMove
							IDstates.add(new TransPosTableEntry(newBoard,miniMaxValue,maxPlies,colorOfAI));

							if(miniMaxValue > bestValue)
							{
								bestBoard = newBoard;
								bestValue = miniMaxValue;
								//System.out.println("BestValue: "+bestValue);
								newEntry = new TransPosTableEntry(bestBoard,bestValue,maxPlies,colorOfAI);
							}
						}
					}
				}
				phase2 = true;
			}
		}
		if(!moveWasMade)
			System.out.println("Error occured");

		//System.out.println(bestBoard.getCurrZobrist());
		return newEntry;
	}

	public int maxValue(Board board, int depth, int color, int alpha, int beta, Board prevBoard,TransPosTable gameStates, int turnCount)
	{
		int turns = turnCount+1;
		Board newBoard = null;
		int maxValue = Integer.MIN_VALUE;
		Iterator<Pair> moveIterator;
		boolean jumpsAvailable = false, moveWasMade = false;

		//When the depth limit has been reached or one side no longer has any pieces
		if(depth == maxPlies ||  ((color == Board.WHITE && board.victoryForBlack(board)) || (color == Board.BLACK && board.victoryForWhite(board)) ))
		{
			return evaluationFunction(turns,color, board,prevBoard,gameStates);
		}
		else
		{
			if( (color == Board.BLACK && board.getJumps().getBlackJumpCount() != 0) || 
					color == Board.WHITE  && board.getJumps().getWhiteJumpCount() != 0)
			{
				jumpsAvailable = true;
			}
			if((color == Board.BLACK && board.getJumps().getBlackJumpCount() == 1) ||
					(color == Board.WHITE && board.getJumps().getWhiteJumpCount() == 1))
			{
				newBoard = new Board(board);
				Iterator<Pair> jump = newBoard.getJumps().getJump().iterator();
				while(jump.hasNext())
				{
					Pair p = new Pair(jump.next());
					Move move = p.getMove();
					Move prevMove = p.getPrevMove();

					if(((color == newBoard.pieceAtPosition(prevMove.getRow(), prevMove.getColumn())) ||
							(color*2 == newBoard.pieceAtPosition(prevMove.getRow(), prevMove.getColumn())))&&
							(newBoard.isMoveLegal(move.getRow(), move.getColumn(), prevMove, newBoard.pieceAtPosition(prevMove.getRow(), prevMove.getColumn()))))
					{
						moveWasMade = true;
						prevBoard = new Board(newBoard);
						newBoard = newBoard.tryMove(move, prevMove, newBoard.pieceAtPosition(prevMove.getRow(), prevMove.getColumn()), newBoard);

						if(newBoard.isMultiJump())
							multiJumpCheck(newBoard,color);

						if(gameStates.contains(newBoard.getCurrZobrist()) && (colorOfAI == gameStates.retrieveEntry(newBoard.getCurrZobrist()).getColor()) 
								&& gameStates.retrieveEntry(newBoard.getCurrZobrist()).getDepth() >= (maxPlies-depth))
						{
							maxValue = gameStates.retrieveEntry(newBoard.getCurrZobrist()).getEval();
							//System.out.println("Max USED A SAVE STATE!!!!!!!" + "Score:" + maxValue);
						}
						else
							maxValue = Math.max(maxValue, minValue(newBoard, depth+1, -color, alpha, beta, prevBoard,gameStates,turns));

						if (maxValue >= beta)
							return maxValue;
						alpha = Math.max(maxValue, alpha);
					}
				}
			}
			else
			{
				if(color == Board.BLACK)
					moveIterator = board.getBlackMoves().iterator();
				else
					moveIterator = board.getWhiteMoves().iterator();

				while(moveIterator.hasNext())//iterator2.hasNext())
				{
					newBoard = new Board(board);
					Pair curr = new Pair(moveIterator.next());
					Move prevMove = curr.getPrevMove();
					Move move = curr.getMove();

					if((color == newBoard.pieceAtPosition(prevMove.getRow(), prevMove.getColumn()) ||
							color*2 == newBoard.pieceAtPosition(prevMove.getRow(), prevMove.getColumn())) && 
							(newBoard.isMoveLegal(move.getRow(), move.getColumn(), prevMove,newBoard.pieceAtPosition(prevMove.getRow(), prevMove.getColumn())))
							&& ((jumpsAvailable && newBoard.isJumpAvailable(move,prevMove, newBoard.pieceAtPosition(prevMove.getRow(), prevMove.getColumn())))
									|| !jumpsAvailable))
					{
						moveWasMade = true;
						prevBoard = new Board(newBoard);
						newBoard = newBoard.tryMove(move, prevMove, newBoard.pieceAtPosition(prevMove.getRow(), prevMove.getColumn()), newBoard);

						if(newBoard.isMultiJump())
							multiJumpCheck(newBoard,color);

						if(gameStates.contains(newBoard.getCurrZobrist()) && (colorOfAI == gameStates.retrieveEntry(newBoard.getCurrZobrist()).getColor()) 
								&& gameStates.retrieveEntry(newBoard.getCurrZobrist()).getDepth() >= (maxPlies-depth))
						{								maxValue = gameStates.retrieveEntry(newBoard.getCurrZobrist()).getEval();
							//System.out.println("MaxUSED A SAVE STATE!!!!!!!" + "Score:" + maxValue);
						}
						else
							maxValue = Math.max(maxValue, minValue(newBoard, depth+1, -color, alpha, beta,prevBoard,gameStates, turns));

						if (maxValue >= beta)
							return maxValue;
						alpha = Math.max(maxValue, alpha);
					}
				}
			}
			if(!moveWasMade)
			{
				System.out.println("Error occured");
				return Integer.MIN_VALUE;
			}
			return maxValue;
		}
	}

	public int minValue(Board board, int depth, int color, int alpha, int beta, Board prevBoard, TransPosTable gameStates, int turnCount)
	{
		int turns = turnCount + 1;
		Board newBoard = null;
		int minValue = Integer.MAX_VALUE;
		Iterator<Pair> moveIterator;
		boolean jumpsAvailable = false, moveWasMade = false;

		//When the depth limit has been reached or one side no longer has any pieces
		if(depth == maxPlies ||  ((color == Board.WHITE && board.victoryForBlack(board)) || (color == Board.BLACK && board.victoryForWhite(board)) ))
		{
			return evaluationFunction(turns, color, board, prevBoard,gameStates);
		}
		else
		{
			if( (color == Board.BLACK && board.getJumps().getBlackJumpCount() != 0) || 
					color == Board.WHITE && board.getJumps().getWhiteJumpCount() != 0)
			{
				jumpsAvailable = true;
			}
			if((color == Board.BLACK && board.getJumps().getBlackJumpCount() == 1) ||
					(color == Board.WHITE && board.getJumps().getWhiteJumpCount() == 1))
			{
				newBoard = new Board(board);
				Iterator<Pair> jump = newBoard.getJumps().getJump().iterator();
				while(jump.hasNext())
				{
					Pair p = new Pair(jump.next());
					Move move = new Move(p.getMove());
					Move prevMove = new Move(p.getPrevMove());

					if(((color == newBoard.pieceAtPosition(prevMove.getRow(), prevMove.getColumn())) ||
							(color*2 == newBoard.pieceAtPosition(prevMove.getRow(), prevMove.getColumn())))&&
							(newBoard.isMoveLegal(move.getRow(), move.getColumn(), prevMove, newBoard.pieceAtPosition(prevMove.getRow(), prevMove.getColumn()))))
					{
						moveWasMade = true;
						prevBoard = new Board(newBoard);
						newBoard = newBoard.tryMove(move, prevMove, newBoard.pieceAtPosition(prevMove.getRow(), prevMove.getColumn()), newBoard);

						if(newBoard.isMultiJump())
							multiJumpCheck(newBoard,color);

						if(gameStates.contains(newBoard.getCurrZobrist()) && (colorOfAI == gameStates.retrieveEntry(newBoard.getCurrZobrist()).getColor()) 
								&& gameStates.retrieveEntry(newBoard.getCurrZobrist()).getDepth() >= (maxPlies-depth))
						{
							minValue = gameStates.retrieveEntry(newBoard.getCurrZobrist()).getEval();
							//System.out.println("MinUSED A SAVE STATE!!!!!!!" + "Score:" + minValue);
						}
						else
							minValue = Math.min(minValue, maxValue(newBoard, depth+1, -color, alpha, beta,prevBoard,gameStates,turns));

						if (minValue <= alpha)
							return minValue;
						beta = Math.min(minValue, beta);
					}
				}
			}
			else
			{
				if(color == Board.BLACK)
					moveIterator = board.getBlackMoves().iterator();
				else
					moveIterator = board.getWhiteMoves().iterator();

				while(moveIterator.hasNext())
				{
					newBoard = new Board(board);
					Pair curr = new Pair(moveIterator.next());
					Move prevMove = curr.getPrevMove();
					Move move = curr.getMove();

					if((color == newBoard.pieceAtPosition(prevMove.getRow(), prevMove.getColumn()) ||
							color*2 == newBoard.pieceAtPosition(prevMove.getRow(), prevMove.getColumn())) &&
							(newBoard.isMoveLegal(move.getRow(), move.getColumn(), prevMove, newBoard.pieceAtPosition(prevMove.getRow(), prevMove.getColumn())))
							&& ((jumpsAvailable && newBoard.isJumpAvailable(move,prevMove, newBoard.pieceAtPosition(prevMove.getRow(), prevMove.getColumn())))
									|| !jumpsAvailable))
					{
						moveWasMade = true;

						prevBoard = new Board(newBoard);
						newBoard = newBoard.tryMove(move, prevMove, newBoard.pieceAtPosition(prevMove.getRow(), prevMove.getColumn()), newBoard);

						if(newBoard.isMultiJump())
							multiJumpCheck(newBoard,color);

						if(gameStates.contains(newBoard.getCurrZobrist()) && (colorOfAI == gameStates.retrieveEntry(newBoard.getCurrZobrist()).getColor()) 
								&& gameStates.retrieveEntry(newBoard.getCurrZobrist()).getDepth() >= (maxPlies-depth))
						{
							minValue = gameStates.retrieveEntry(newBoard.getCurrZobrist()).getEval();
							//System.out.println("MinUSED A SAVE STATE!!!!!!!" + "Score:" + minValue);
						}
						else
							minValue = Math.min(minValue, maxValue(newBoard, depth+1, -color, alpha, beta,prevBoard,gameStates,turns));

						if (minValue <= alpha)
							return minValue;
						beta = Math.min(minValue, beta);
					}
				}
			}
			if(!moveWasMade)
			{
				System.out.println("Error occured");
				return Integer.MIN_VALUE;
			}
			return minValue;
		}
	}

	private int evaluationFunction(int turnCount, int color, Board board, Board prevBoard, TransPosTable gameStates)
	{
		int eval = 0; //-(turnCount/4);
		//System.out.println(board.toString());

		if(gameStates.contains(board.getCurrZobrist()) && (color == gameStates.retrieveEntry(board.getCurrZobrist()).getColor()))
		{
			//System.out.println("Eval:USED A SAVE STATE!!!!!!!!!!!!!!!!!");
			return gameStates.retrieveEntry(board.getCurrZobrist()).getEval();
		}
		if(colorOfAI == Board.BLACK)
		{
			if(board.victoryForBlack(board))
				return 100000;
			else if(board.victoryForWhite(board))
				return -100000;
			else
			{
				//Keep back row until endgame
				if(board.getNumBlackPieces() + board.getNumWhitePieces() + 
						board.getNumBlackKings() + board.getNumWhiteKings() > 8)
				{
					if(board.pieceAtPosition(7, 0) != Board.BLACK)
						eval -= 5;
					if(board.pieceAtPosition(7, 2) != Board.BLACK)
						eval -= 5;
					if(board.pieceAtPosition(7, 4) != Board.BLACK)
						eval -= 5;
					if(board.pieceAtPosition(7, 6) != Board.BLACK)
						eval -= 5;
				}

				//Advance pawns
				if(board.pieceAtPosition(3, 0) == Board.BLACK)
					eval += 1;
				if(board.pieceAtPosition(3, 2) == Board.BLACK)
					eval += 1;
				if(board.pieceAtPosition(3, 4) == Board.BLACK)
					eval += 1;
				if(board.pieceAtPosition(3, 6) == Board.BLACK)
					eval += 1;

				if(board.pieceAtPosition(2, 1) == Board.BLACK)
					eval += 2;
				if(board.pieceAtPosition(2, 3) == Board.BLACK)
					eval += 2;
				if(board.pieceAtPosition(2, 5) == Board.BLACK)
					eval += 2;
				if(board.pieceAtPosition(2, 7) == Board.BLACK)
					eval += 2;

				//Checkers that will king on the next move
				if(board.pieceAtPosition(1,0) == Board.BLACK && board.pieceAtPosition(0, 1) == Board.EMPTY)
					eval += 3;
				if(board.pieceAtPosition(1,2) == Board.BLACK && (board.pieceAtPosition(0, 1) == Board.EMPTY
						|| board.pieceAtPosition(0, 3) == Board.EMPTY))
					eval += 3;
				if(board.pieceAtPosition(1,4) == Board.BLACK && (board.pieceAtPosition(0, 3) == Board.EMPTY
						|| board.pieceAtPosition(0, 5) == Board.EMPTY))
					eval += 3;
				if(board.pieceAtPosition(1,6) == Board.BLACK && (board.pieceAtPosition(0, 5) == Board.EMPTY
						|| board.pieceAtPosition(0, 7) == Board.EMPTY))
					eval += 3;

				//Check center strength
				if(board.pieceAtPosition(3, 0) == Board.BLACK || board.pieceAtPosition(3, 0) == Board.BLACKKING)
					eval += 1;
				else if(board.pieceAtPosition(3, 0) == Board.WHITE || board.pieceAtPosition(3, 0) == Board.WHITEKING)
					eval -= 1;
				if(board.pieceAtPosition(3, 2) == Board.BLACK || board.pieceAtPosition(3, 2) == Board.BLACKKING)
					eval += 1;
				else if(board.pieceAtPosition(3, 2) == Board.WHITE || board.pieceAtPosition(3, 2) == Board.WHITEKING)
					eval -= 1;
				if(board.pieceAtPosition(3, 4) == Board.BLACK || board.pieceAtPosition(3, 4) == Board.BLACKKING)
					eval += 1;
				else if(board.pieceAtPosition(3, 4) == Board.WHITE || board.pieceAtPosition(3, 4) == Board.WHITEKING)
					eval -= 1;
				if(board.pieceAtPosition(3, 6) == Board.BLACK || board.pieceAtPosition(3, 6) == Board.BLACKKING)
					eval += 1;
				else if(board.pieceAtPosition(3, 6) == Board.WHITE || board.pieceAtPosition(3, 6) == Board.WHITEKING)
					eval -= 1;
				if(board.pieceAtPosition(4, 1) == Board.BLACK || board.pieceAtPosition(4, 1) == Board.BLACKKING)
					eval += 1;
				else if(board.pieceAtPosition(4, 1) == Board.WHITE || board.pieceAtPosition(4, 1) == Board.WHITEKING)
					eval -= 1;
				if(board.pieceAtPosition(4, 3) == Board.BLACK || board.pieceAtPosition(4, 3) == Board.BLACKKING)
					eval += 1;
				else if(board.pieceAtPosition(4, 3) == Board.WHITE || board.pieceAtPosition(4, 3) == Board.WHITEKING)
					eval -= 1;
				if(board.pieceAtPosition(4, 5) == Board.BLACK || board.pieceAtPosition(4, 5) == Board.BLACKKING)
					eval += 1;
				else if(board.pieceAtPosition(4, 5) == Board.WHITE || board.pieceAtPosition(4, 5) == Board.WHITEKING)
					eval -= 1;
				if(board.pieceAtPosition(4, 7) == Board.BLACK || board.pieceAtPosition(4, 7) == Board.BLACKKING)
					eval += 1;
				else if(board.pieceAtPosition(4, 7) == Board.WHITE || board.pieceAtPosition(4, 7) == Board.WHITEKING)
					eval -= 1;

				//Keep checkers off of corners
				if(board.pieceAtPosition(0, 7) == Board.BLACKKING)
					eval -= 3;
				if(board.pieceAtPosition(7, 0) == Board.BLACKKING)
					eval -= 3;

				//Mobility
				eval += (board.blackMoves() - board.whiteMoves());

				//Trapped kings
				if(board.pieceAtPosition(0, 1) == Board.BLACKKING && (board.pieceAtPosition(1, 0) == Board.WHITE
						|| board.pieceAtPosition(1, 0) == Board.WHITEKING))
					eval -= 3;
				if(board.pieceAtPosition(0, 3) == Board.BLACKKING)
				{
					if(board.pieceAtPosition(2, 3) == Board.WHITEKING && board.pieceAtPosition(0, 1) == Board.EMPTY
							&& board.pieceAtPosition(0, 5) == Board.EMPTY)
						eval -= 3;
					else if(board.pieceAtPosition(2, 3) == Board.EMPTY && (board.pieceAtPosition(0, 1) == Board.WHITE || board.pieceAtPosition(0, 1) == Board.WHITEKING)
							&& (board.pieceAtPosition(0, 5) == Board.WHITE || board.pieceAtPosition(0, 5) == Board.WHITEKING))
						eval -= 3;
				}
				if(board.pieceAtPosition(0, 5) == Board.BLACKKING)
				{
					if(board.pieceAtPosition(2, 5) == Board.WHITEKING && board.pieceAtPosition(0, 3) == Board.EMPTY
							&& board.pieceAtPosition(0, 7) == Board.EMPTY)
						eval -= 3;
					else if((board.pieceAtPosition(0, 1) == Board.WHITE || board.pieceAtPosition(0, 1) == Board.WHITEKING)
							&& (board.pieceAtPosition(0, 5) == Board.WHITE || board.pieceAtPosition(0, 5) == Board.WHITEKING))
						eval -= 3;
				}
				if(board.pieceAtPosition(0, 7) == Board.BLACKKING)
				{
					if(board.pieceAtPosition(2, 5) == Board.WHITEKING) 
						eval -= 3;
					else if((board.pieceAtPosition(0, 5) == Board.WHITE || board.pieceAtPosition(0, 5) == Board.WHITEKING)
							&& board.pieceAtPosition(2, 7) == Board.EMPTY)
						eval -= 3;
					else if(board.pieceAtPosition(2, 7) == Board.WHITEKING && board.pieceAtPosition(0, 5) == Board.EMPTY)
						eval -= 3;
				}

				//Trapped enemy kings
				if(board.pieceAtPosition(0, 3) == Board.WHITEKING && board.pieceAtPosition(2, 3) == Board.BLACKKING
						&& board.pieceAtPosition(0, 1) == Board.EMPTY && board.pieceAtPosition(0, 5) == Board.EMPTY)
					eval += 5;
				if(board.pieceAtPosition(0, 5) == Board.WHITEKING && board.pieceAtPosition(2, 5) == Board.BLACKKING
						&& board.pieceAtPosition(0, 3) == Board.EMPTY && board.pieceAtPosition(0, 7) == Board.EMPTY)
					eval += 5;
				if(board.pieceAtPosition(0, 7) == Board.WHITEKING && board.pieceAtPosition(2, 5) == Board.BLACKKING)
					eval += 5;
				if(board.pieceAtPosition(2, 7) == Board.WHITEKING && board.pieceAtPosition(2, 5) == Board.BLACKKING
						&& board.pieceAtPosition(0, 7) == Board.EMPTY && board.pieceAtPosition(4, 7) == Board.EMPTY)
					eval += 5;
				if(board.pieceAtPosition(4, 7) == Board.WHITEKING && board.pieceAtPosition(4, 5) == Board.BLACKKING
						&& board.pieceAtPosition(2, 7) == Board.EMPTY && board.pieceAtPosition(6, 7) == Board.EMPTY)
					eval += 5;
				if(board.pieceAtPosition(3, 0) == Board.WHITEKING && board.pieceAtPosition(3, 2) == Board.BLACKKING
						&& board.pieceAtPosition(1, 0) == Board.EMPTY && board.pieceAtPosition(5, 0) == Board.EMPTY)
					eval += 5;
				if(board.pieceAtPosition(5, 0) == Board.WHITEKING && board.pieceAtPosition(5, 2) == Board.BLACKKING
						&& board.pieceAtPosition(3, 0) == Board.EMPTY && board.pieceAtPosition(7, 0) == Board.EMPTY)
					eval += 5;
				if(board.pieceAtPosition(7, 0) == Board.WHITEKING && board.pieceAtPosition(5, 2) == Board.BLACKKING)
					eval += 5;
				if(board.pieceAtPosition(7, 2) == Board.WHITEKING && board.pieceAtPosition(5, 2) == Board.BLACKKING
						&& board.pieceAtPosition(7, 0) == Board.EMPTY && board.pieceAtPosition(7, 4) == Board.EMPTY)
					eval += 5;
				if(board.pieceAtPosition(7, 4) == Board.WHITEKING && board.pieceAtPosition(5, 4) == Board.BLACKKING
						&& board.pieceAtPosition(7, 2) == Board.EMPTY && board.pieceAtPosition(7, 6) == Board.EMPTY)
					eval += 5;

				//Number of pieces and kings
				eval += ((13 * board.getNumBlackKings()) + (10 * board.getNumBlackPieces()) +
						(-13 * board.getNumWhiteKings()) + (-10 * board.getNumWhitePieces()));
				return eval;
			}
		}
		else //if(colorOfAI == Board.WHITE)
		{
			if(board.victoryForWhite(board))
				return 100000;
			else if(board.victoryForBlack(board))
				return -100000;
			else
			{
				//Keep back row until endgame
				if(board.getNumBlackPieces() + board.getNumWhitePieces() + 
						board.getNumBlackKings() + board.getNumWhiteKings() > 8)
				{
					if(board.pieceAtPosition(0, 1) != Board.WHITE)
						eval -= 5;
					if(board.pieceAtPosition(0, 3) != Board.WHITE)
						eval -= 5;
					if(board.pieceAtPosition(0, 5) != Board.WHITE)
						eval -= 5;
					if(board.pieceAtPosition(0, 7) != Board.WHITE)
						eval -= 5;
				}

				//Advance pawns
				if(board.pieceAtPosition(4, 1) == Board.WHITE)
					eval += 1;
				if(board.pieceAtPosition(4, 3) == Board.WHITE)
					eval += 1;
				if(board.pieceAtPosition(4, 5) == Board.WHITE)
					eval += 1;
				if(board.pieceAtPosition(4, 7) == Board.WHITE)
					eval += 1;

				if(board.pieceAtPosition(5, 0) == Board.WHITE)
					eval += 2;
				if(board.pieceAtPosition(5, 2) == Board.WHITE)
					eval += 2;
				if(board.pieceAtPosition(5, 4) == Board.WHITE)
					eval += 2;
				if(board.pieceAtPosition(5, 6) == Board.WHITE)
					eval += 2;

				//Checkers that will king on the next move
				if(board.pieceAtPosition(6,1) == Board.BLACK && (board.pieceAtPosition(7, 0) == Board.EMPTY
						|| board.pieceAtPosition(7, 2) == Board.EMPTY))
					eval += 3;
				if(board.pieceAtPosition(6,3) == Board.BLACK && (board.pieceAtPosition(7, 2) == Board.EMPTY
						|| board.pieceAtPosition(7, 4) == Board.EMPTY))
					eval += 3;
				if(board.pieceAtPosition(6,5) == Board.BLACK && (board.pieceAtPosition(7, 4) == Board.EMPTY
						|| board.pieceAtPosition(7, 6) == Board.EMPTY))
					eval += 3;
				if(board.pieceAtPosition(6,7) == Board.BLACK && board.pieceAtPosition(7, 6) == Board.EMPTY)
					eval += 3;

				//Check center strength
				if(board.pieceAtPosition(3, 0) == Board.BLACK || board.pieceAtPosition(3, 0) == Board.BLACKKING)
					eval -= 1;
				else if(board.pieceAtPosition(3, 0) == Board.WHITE || board.pieceAtPosition(3, 0) == Board.WHITEKING)
					eval += 1;
				if(board.pieceAtPosition(3, 2) == Board.BLACK || board.pieceAtPosition(3, 2) == Board.BLACKKING)
					eval -= 1;
				else if(board.pieceAtPosition(3, 2) == Board.WHITE || board.pieceAtPosition(3, 2) == Board.WHITEKING)
					eval += 1;
				if(board.pieceAtPosition(3, 4) == Board.BLACK || board.pieceAtPosition(3, 4) == Board.BLACKKING)
					eval -= 1;
				else if(board.pieceAtPosition(3, 4) == Board.WHITE || board.pieceAtPosition(3, 4) == Board.WHITEKING)
					eval += 1;
				if(board.pieceAtPosition(3, 6) == Board.BLACK || board.pieceAtPosition(3, 6) == Board.BLACKKING)
					eval -= 1;
				else if(board.pieceAtPosition(3, 6) == Board.WHITE || board.pieceAtPosition(3, 6) == Board.WHITEKING)
					eval += 1;
				if(board.pieceAtPosition(4, 1) == Board.BLACK || board.pieceAtPosition(4, 1) == Board.BLACKKING)
					eval -= 1;
				else if(board.pieceAtPosition(4, 1) == Board.WHITE || board.pieceAtPosition(4, 1) == Board.WHITEKING)
					eval += 1;
				if(board.pieceAtPosition(4, 3) == Board.BLACK || board.pieceAtPosition(4, 3) == Board.BLACKKING)
					eval -= 1;
				else if(board.pieceAtPosition(4, 3) == Board.WHITE || board.pieceAtPosition(4, 3) == Board.WHITEKING)
					eval += 1;
				if(board.pieceAtPosition(4, 5) == Board.BLACK || board.pieceAtPosition(4, 5) == Board.BLACKKING)
					eval -= 1;
				else if(board.pieceAtPosition(4, 5) == Board.WHITE || board.pieceAtPosition(4, 5) == Board.WHITEKING)
					eval += 1;
				if(board.pieceAtPosition(4, 7) == Board.BLACK || board.pieceAtPosition(4, 7) == Board.BLACKKING)
					eval -= 1;
				else if(board.pieceAtPosition(4, 7) == Board.WHITE || board.pieceAtPosition(4, 7) == Board.WHITEKING)
					eval += 1;

				//Keep kings off of corners
				if(board.pieceAtPosition(0, 7) == Board.WHITEKING)
					eval -= 3;
				if(board.pieceAtPosition(7, 0) == Board.WHITEKING)
					eval -= 3;

				//Mobility
				eval += (board.whiteMoves() - board.blackMoves());

				//Trapped kings
				if(board.pieceAtPosition(7, 0) == Board.WHITEKING)
				{
					if(board.pieceAtPosition(5, 2) == Board.BLACKKING) 
						eval -= 3;
					else if((board.pieceAtPosition(7, 2) == Board.BLACK || board.pieceAtPosition(7, 2) == Board.BLACKKING)
							&& board.pieceAtPosition(5, 0) == Board.EMPTY)
						eval -= 3;
					else if(board.pieceAtPosition(5, 0) == Board.BLACKKING && board.pieceAtPosition(7, 2) == Board.EMPTY)
						eval -= 3;
				}
				if(board.pieceAtPosition(7, 2) == Board.WHITEKING)
				{
					if(board.pieceAtPosition(5, 2) == Board.BLACKKING && board.pieceAtPosition(7, 0) == Board.EMPTY
							&& board.pieceAtPosition(7, 4) == Board.EMPTY)
						eval -= 3;
					else if((board.pieceAtPosition(7, 0) == Board.BLACK || board.pieceAtPosition(7, 0) == Board.BLACKKING)
							&& (board.pieceAtPosition(7, 4) == Board.BLACK || board.pieceAtPosition(7, 4) == Board.BLACKKING))
						eval -= 3;
				}
				if(board.pieceAtPosition(7, 4) == Board.WHITEKING)
				{
					if(board.pieceAtPosition(5, 4) == Board.BLACKKING && board.pieceAtPosition(7, 2) == Board.EMPTY
							&& board.pieceAtPosition(7, 6) == Board.EMPTY)
						eval -= 3;
					else if((board.pieceAtPosition(7, 2) == Board.BLACK || board.pieceAtPosition(7, 2) == Board.BLACKKING)
							&& (board.pieceAtPosition(7, 6) == Board.BLACK || board.pieceAtPosition(7, 6) == Board.BLACKKING))
						eval -= 3;
				}
				if(board.pieceAtPosition(7, 6) == Board.WHITEKING && (board.pieceAtPosition(6, 7) == Board.BLACK
						|| board.pieceAtPosition(6, 7) == Board.BLACKKING))
					eval -= 3;

				//Trapped enemy kings				
				if(board.pieceAtPosition(0, 3) == Board.BLACKKING && board.pieceAtPosition(2, 3) == Board.WHITEKING
						&& board.pieceAtPosition(0, 1) == Board.EMPTY && board.pieceAtPosition(0, 5) == Board.EMPTY)
					eval += 5;
				if(board.pieceAtPosition(0, 5) == Board.BLACKKING && board.pieceAtPosition(2, 5) == Board.WHITEKING
						&& board.pieceAtPosition(0, 3) == Board.EMPTY && board.pieceAtPosition(0, 7) == Board.EMPTY)
					eval += 5;
				if(board.pieceAtPosition(0, 7) == Board.BLACKKING && board.pieceAtPosition(2, 5) == Board.WHITEKING)
					eval += 5;
				if(board.pieceAtPosition(2, 7) == Board.BLACKKING && board.pieceAtPosition(2, 5) == Board.WHITEKING
						&& board.pieceAtPosition(0, 7) == Board.EMPTY && board.pieceAtPosition(4, 7) == Board.EMPTY)
					eval += 5;
				if(board.pieceAtPosition(4, 7) == Board.BLACKKING && board.pieceAtPosition(4, 5) == Board.WHITEKING
						&& board.pieceAtPosition(2, 7) == Board.EMPTY && board.pieceAtPosition(6, 7) == Board.EMPTY)
					eval += 5;
				if(board.pieceAtPosition(3, 0) == Board.BLACKKING && board.pieceAtPosition(3, 2) == Board.WHITEKING
						&& board.pieceAtPosition(1, 0) == Board.EMPTY && board.pieceAtPosition(5, 0) == Board.EMPTY)
					eval += 5;
				if(board.pieceAtPosition(5, 0) == Board.BLACKKING && board.pieceAtPosition(5, 2) == Board.WHITEKING
						&& board.pieceAtPosition(3, 0) == Board.EMPTY && board.pieceAtPosition(7, 0) == Board.EMPTY)
					eval += 5;
				if(board.pieceAtPosition(7, 0) == Board.BLACKKING && board.pieceAtPosition(5, 2) == Board.WHITEKING)
					eval += 5;
				if(board.pieceAtPosition(7, 2) == Board.BLACKKING && board.pieceAtPosition(5, 2) == Board.WHITEKING
						&& board.pieceAtPosition(7, 0) == Board.EMPTY && board.pieceAtPosition(7, 4) == Board.EMPTY)
					eval += 5;
				if(board.pieceAtPosition(7, 4) == Board.BLACKKING && board.pieceAtPosition(5, 4) == Board.WHITEKING
						&& board.pieceAtPosition(7, 2) == Board.EMPTY && board.pieceAtPosition(7, 6) == Board.EMPTY)
					eval += 5;

				//Number of pieces and kings
				eval += ((13 * board.getNumWhiteKings()) + (10 * board.getNumWhitePieces()) +
						(-13 * board.getNumBlackKings()) + (-10 * board.getNumBlackPieces()));

				return eval;
			}
		}
	}
}
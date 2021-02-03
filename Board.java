import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Random;

public class Board implements Serializable
{
	private static final long serialVersionUID = 1L;
	public static int ILLEGAL = -100, EMPTY = 0, WHITE = 1, BLACK = -1, WHITEKING = 2, BLACKKING = -2;
	private int numWhitePieces, numBlackPieces, numWhiteKings, numBlackKings;
	private int[][] board;
	private boolean multiJump = false, moveIsAJump = false, justCrowned=false;
	private Move moveToMake = null;
	private Jumps jumps;
	private ArrayList<Move> legalPositionsWithoutPiece;
	private ArrayList<Move> locationsWithWhitePiece;
	private ArrayList<Move> locationsWithBlackPiece;
	private ArrayList<Pair> whiteMoveList;
	private ArrayList<Pair> blackMoveList;
	private ZobristMapping zobristKeys;
	private String currZobrist;

	public Board()
	{
		zobristKeys = new ZobristMapping();
		currZobrist = Integer.toBinaryString(0);
		locationsWithWhitePiece = new ArrayList<Move>();
		locationsWithBlackPiece = new ArrayList<Move>();
		legalPositionsWithoutPiece = new ArrayList<Move>();
		whiteMoveList = new ArrayList<Pair>();
		blackMoveList = new ArrayList<Pair>();
		jumps = new Jumps();
		board = new int[8][8];
		multiJump = false;
		moveIsAJump = false;
		moveToMake = new Move();
		Random generator = new Random();
		int temp;
		
		//Initialize all positions to be illegal
		for (int row = 0; row < 8; row++)
			for (int column = 0; column < 8; column++)
				board[row][column] = Board.ILLEGAL;
		//Now go over and set up the initial board configuration
		for(int row = 0; row < 8; row++)
		{
			if(row % 2 == 0)
			{
				for(int column = 1; column < 8; column += 2)
				{
					if(row == 0 || row == 2)
					{
						
						board[row][column] = Board.WHITE;
						currZobrist = XOR(currZobrist, zobristKeys.getMoveString(row, column, Board.WHITE));

						if(locationsWithWhitePiece.size() != 0)
						{
							temp = generator.nextInt(locationsWithWhitePiece.size());
							locationsWithWhitePiece.add(temp,new Move(row, column));
						}
						else
							locationsWithWhitePiece.add(new Move(row, column));
					}
					else if(row == 6)
					{
						board[row][column] = Board.BLACK;
						currZobrist = XOR(currZobrist, zobristKeys.getMoveString(row, column, Board.BLACK));

						if(locationsWithBlackPiece.size() != 0)
						{
							temp = generator.nextInt(locationsWithBlackPiece.size());
							locationsWithBlackPiece.add(temp,new Move(row, column));
						}
						else
							locationsWithBlackPiece.add(new Move(row, column));
					}
					else //if row == 4
					{
						board[row][column] = Board.EMPTY;
						currZobrist = XOR(currZobrist, zobristKeys.getMoveString(row, column, Board.EMPTY));
						if(legalPositionsWithoutPiece.size() != 0)
						{
							temp = generator.nextInt(legalPositionsWithoutPiece.size());
							legalPositionsWithoutPiece.add(temp,new Move(row, column));
						}
						else
							legalPositionsWithoutPiece.add(new Move(row, column));
					}	
				}
			}
			else //(row % 2 == 1)
			{
				for(int column = 0; column < 8; column += 2)
				{
					if(row == 1)
					{
						board[row][column] = Board.WHITE;
						currZobrist = XOR(currZobrist, zobristKeys.getMoveString(row, column, Board.WHITE));

						if(locationsWithWhitePiece.size() != 0)
						{
							temp = generator.nextInt(locationsWithWhitePiece.size());
							locationsWithWhitePiece.add(temp,new Move(row, column));
						}
						else
							locationsWithWhitePiece.add(new Move(row, column));
					}
					else if(row == 5 || row == 7)
					{
						board[row][column] = Board.BLACK;
						currZobrist = XOR(currZobrist, zobristKeys.getMoveString(row, column, Board.BLACK));
		
						if(locationsWithBlackPiece.size() != 0)
						{
							temp = generator.nextInt(locationsWithBlackPiece.size());
							locationsWithBlackPiece.add(temp,new Move(row, column));
						}
						else
							locationsWithBlackPiece.add(new Move(row, column));
					}
					else //if row == 3
					{
						board[row][column] = Board.EMPTY;
						currZobrist = XOR(currZobrist, zobristKeys.getMoveString(row, column, Board.EMPTY));
						if(legalPositionsWithoutPiece.size() != 0)
						{
							temp = generator.nextInt(legalPositionsWithoutPiece.size());
							legalPositionsWithoutPiece.add(temp,new Move(row, column));
						}
						else
							legalPositionsWithoutPiece.add(new Move(row, column));
					}
				}
			}
		}

		numWhitePieces = 12;
		numBlackPieces = 12;
		numWhiteKings = 0;
		numBlackKings = 0;
		
		for(int i=0;i<locationsWithBlackPiece.size();i++)
		{
			addToMoveList(locationsWithBlackPiece.get(i), 
					pieceAtPosition(locationsWithBlackPiece.get(i).getRow(),locationsWithBlackPiece.get(i).getColumn()));
		}
		for(int i=0;i<locationsWithWhitePiece.size();i++)
		{
			addToMoveList(locationsWithWhitePiece.get(i), 
					pieceAtPosition(locationsWithWhitePiece.get(i).getRow(),locationsWithWhitePiece.get(i).getColumn()));
		}
		//System.out.println("W: "+whiteMoveList.size()+"|B: "+blackMoveList.size());
	}

	public Board(Board copy)
	{
		this.currZobrist = new String(copy.currZobrist);
		this.zobristKeys = copy.zobristKeys; 	//mappings don't change, completely static class, shallow copy ok!
		
		this.legalPositionsWithoutPiece = new ArrayList<Move>();
		this.legalPositionsWithoutPiece.addAll(copy.legalPositionsWithoutPiece);
		this.locationsWithWhitePiece = new ArrayList<Move>();
		this.locationsWithWhitePiece.addAll(copy.locationsWithWhitePiece);
		this.locationsWithBlackPiece = new ArrayList<Move>();
		this.locationsWithBlackPiece.addAll(copy.locationsWithBlackPiece);
		this.whiteMoveList = new ArrayList<Pair>();
		this.whiteMoveList.addAll(copy.whiteMoveList);
		this.blackMoveList = new ArrayList<Pair>();
		this.blackMoveList.addAll(copy.blackMoveList);
		
		this.jumps = new Jumps(copy.jumps);
		this.moveToMake = new Move(copy.moveToMake);
		this.multiJump = new Boolean(copy.multiJump);
		this.moveIsAJump = new Boolean(copy.moveIsAJump);
		this.justCrowned = new Boolean(copy.justCrowned);
		
		this.board = new int[8][8];
		for (int row = 0; row < 8; row++)
			for (int column = 0; column < 8; column++)
				this.board[row][column] = copy.board[row][column];
		
		this.numWhitePieces = new Integer(copy.numWhitePieces);
		this.numBlackPieces = new Integer(copy.numBlackPieces);
		this.numWhiteKings = new Integer(copy.numWhiteKings);
		this.numBlackKings = new Integer(copy.numBlackKings);
	}
	
	//strings could be of different length bcz toBinaryString gets rid of all leading 0's
	//Input: two String objects that are Bit Strings either of different or equal length
	//Output: XOR of the two strings encapsulated in String 
	public String XOR(String temp1, String temp2)
	{
		int diff=0;
		int count = 0;
		String key="";
		char[] charArr1 = temp1.toCharArray();
		char[] charArr2 = temp2.toCharArray();
		
		 if(temp1.length() == temp2.length())
			{
				for(int i=0;i<temp1.length();i++)
				{
					if(charArr1[i] != charArr2[i])
						key+="1";
					else
						key+="0";
				}
			}
		 else if(temp1.length() > temp2.length())
		{
			diff = temp1.length() - temp2.length();
			while (count < diff)
			{
				if(charArr1[count] == '0')
					key+="0";
				else if(charArr1[count] == '1')
					key+="1";
				count++;
			}
			for(int i=diff;i<temp1.length();i++)
			{
				if(charArr1[i] != charArr2[(i-diff)])
					key+="1";
				else
					key+="0";
			}
		}
		else if(temp1.length() < temp2.length())
		{
			diff = temp2.length() - temp1.length();
			while (count < diff)
			{
				if(charArr2[count] == '0')
					key+="0";
				else if(charArr2[count] == '1')
					key+="1";
				count++;
			}
			
			for(int i=diff;i<temp2.length();i++)
			{
				if(charArr1[i-diff] != charArr2[i])
					key+="1";
				else
					key+="0";
			}
		}
		return key;
	}
	
	
	//Given prevMove find Move's
	private void addToMoveList(Move pM, int color)
	{
		int row = pM.getRow();
		int col = pM.getColumn();
		
		if(color == Board.WHITE || color == Board.WHITEKING)
		{
			//Single Moves
			if(row < 7 && col < 7 && pieceAtPosition(row+1,col+1) == Board.EMPTY)
				whiteMoveList.add(new Pair(new Move(row+1,col+1),pM));
			if(row < 7 && col > 0 && pieceAtPosition(row+1,col-1) == Board.EMPTY)
				whiteMoveList.add(new Pair(new Move(row+1,col-1),pM));
			
			//Jumps
			if(row < 6 && col < 6 && isJumpAvailable(new Move(row+2,col+2),pM,color))
				whiteMoveList.add(new Pair(new Move(row+2,col+2),pM));
			if(row < 6 && col > 1 && isJumpAvailable(new Move(row+2,col-2),pM,color))
				whiteMoveList.add(new Pair(new Move(row+2,col-2),pM));
			
			if(color == Board.WHITEKING)
			{
				if(row > 0 && col < 7 && pieceAtPosition(row-1,col+1) == Board.EMPTY)
					whiteMoveList.add(new Pair(new Move(row-1,col+1),pM));
				if(row > 0 && col > 0 && pieceAtPosition(row-1,col-1) == Board.EMPTY)
					whiteMoveList.add(new Pair(new Move(row-1,col-1),pM));
				
				if(row > 1 && col < 6 && isJumpAvailable(new Move(row-2,col+2),pM,color))
					whiteMoveList.add(new Pair(new Move(row-2,col+2),pM));
				if(row > 1 && col > 1 && isJumpAvailable(new Move(row-2,col-2),pM,color))
					whiteMoveList.add(new Pair(new Move(row-2,col-2),pM));
			}
		}
		else
		{
			if(row > 0 && col < 7 && pieceAtPosition(row-1,col+1) == Board.EMPTY)
				blackMoveList.add(new Pair(new Move(row-1,col+1),pM));
			if(row > 0 && col > 0 && pieceAtPosition(row-1,col-1) == Board.EMPTY)
				blackMoveList.add(new Pair(new Move(row-1,col-1),pM));
			
			//Jumps
			if(row > 1 && col < 6 && isJumpAvailable(new Move(row-2,col+2),pM,color))
				blackMoveList.add(new Pair(new Move(row-2,col+2),pM));
			if(row > 1 && col > 1 && isJumpAvailable(new Move(row-2,col-2),pM,color))
				blackMoveList.add(new Pair(new Move(row-2,col-2),pM));
			
			
			if(color == Board.BLACKKING)
			{
				if(row < 7 && col < 7 && pieceAtPosition(row+1,col+1) == Board.EMPTY)
					blackMoveList.add(new Pair(new Move(row+1,col+1),pM));
				if(row < 7 && col > 0 && pieceAtPosition(row+1,col-1) == Board.EMPTY)
					blackMoveList.add(new Pair(new Move(row+1,col-1),pM));
				
				if(row < 6 && col < 6 && isJumpAvailable(new Move(row+2,col+2),pM,color))
					blackMoveList.add(new Pair(new Move(row+2,col+2),pM));
				if(row < 6 && col > 1 && isJumpAvailable(new Move(row+2,col-2),pM,color))
					blackMoveList.add(new Pair(new Move(row+2,col-2),pM));
			}
		}
	}
	
	/*
	private void removeFromMoveList(Move pM, int color)
	{
		int row = pM.getRow();
		int col = pM.getColumn();
		
		if(color == Board.WHITE || color == Board.WHITEKING)
		{
			//Single Moves
			if(row < 7 && col < 7 && pieceAtPosition(row+1,col+1) == Board.EMPTY)
				whiteMoveList.remove(new Pair(new Move(row+1,col+1),pM));
			if(row < 7 && col > 0 && pieceAtPosition(row+1,col-1) == Board.EMPTY)
				whiteMoveList.remove(new Pair(new Move(row+1,col-1),pM));
			
			//Jumps
			if(row < 6 && col < 6 && isJumpAvailable(new Move(row+2,col+2),pM,color))
				whiteMoveList.remove(new Pair(new Move(row+2,col+2),pM));
			if(row < 6 && col > 1 && isJumpAvailable(new Move(row+2,col-2),pM,color))
				whiteMoveList.remove(new Pair(new Move(row+2,col-2),pM));
			
			if(color == Board.WHITEKING)
			{
				if(row > 0 && col < 7 && pieceAtPosition(row-1,col+1) == Board.EMPTY)
					whiteMoveList.remove(new Pair(new Move(row-1,col+1),pM));
				if(row > 0 && col > 0 && pieceAtPosition(row-1,col-1) == Board.EMPTY)
					whiteMoveList.remove(new Pair(new Move(row-1,col-1),pM));
				
				//Jumps
				if(row > 1 && col < 6 && isJumpAvailable(new Move(row-2,col+2),pM,color))
					whiteMoveList.remove(new Pair(new Move(row-2,col+2),pM));
				if(row > 1 && col > 1 && isJumpAvailable(new Move(row-2,col-2),pM,color))
					whiteMoveList.remove(new Pair(new Move(row-2,col-2),pM));
			}
		}
		else
		{
			if(row > 0 && col < 7 && pieceAtPosition(row-1,col+1) == Board.EMPTY)
				blackMoveList.remove(new Pair(new Move(row-1,col+1),pM));
			if(row > 0 && col > 0 && pieceAtPosition(row-1,col-1) == Board.EMPTY)
				blackMoveList.remove(new Pair(new Move(row-1,col-1),pM));
			
			//Jumps
			if(row > 1 && col < 6 && isJumpAvailable(new Move(row-2,col+2),pM,color))
				blackMoveList.remove(new Pair(new Move(row-2,col+2),pM));
			if(row > 1 && col > 1 && isJumpAvailable(new Move(row-2,col-2),pM,color))
				blackMoveList.remove(new Pair(new Move(row-2,col-2),pM));
			
			
			if(color == Board.BLACKKING)
			{
				if(row < 7 && col < 7 && pieceAtPosition(row+1,col+1) == Board.EMPTY)
					blackMoveList.remove(new Pair(new Move(row+1,col+1),pM));
				if(row < 7 && col > 0 && pieceAtPosition(row+1,col-1) == Board.EMPTY)
					blackMoveList.remove(new Pair(new Move(row+1,col-1),pM));
				
				//Jumps
				if(row < 6 && col < 6 && isJumpAvailable(new Move(row+2,col+2),pM,color))
					blackMoveList.remove(new Pair(new Move(row+2,col+2),pM));
				if(row< 6 && col > 1 && isJumpAvailable(new Move(row+2,col-2),pM,color))
					blackMoveList.remove(new Pair(new Move(row+2,col-2),pM));
			}
		}
	}
	*/
	
	public String getCurrZobrist()
	{
		return currZobrist;
	}
	
	public boolean victoryForWhite(Board board)
	{
		Board newBoard = new Board(board);
		Move move, prevMove;
		int color;
		
		if((newBoard.getNumBlackPieces() + newBoard.getNumBlackKings()) == 0)
			return true;
		else
		{
			Iterator<Move> blackPieces = newBoard.getLocationsWithBlackPiece().iterator();
			Iterator<Move> iterator;
		
			while(blackPieces.hasNext())
			{
				prevMove = blackPieces.next();
				color = newBoard.pieceAtPosition(prevMove.getRow(), prevMove.getColumn());
				iterator = newBoard.getLegalPositionsWithoutPiece().iterator();
				
				while(iterator.hasNext())
				{
					move = iterator.next();
					if(newBoard.isMoveLegal(move.getRow(), move.getColumn(), prevMove, color))
						return false;
				}
			}
			return true;
			//if black can't move then White wins
		}
	}
	
	public boolean victoryForBlack(Board board)
	{
		Board newBoard = new Board(board);
		Move move, prevMove;
		int color;
		
		if((newBoard.getNumWhitePieces() + newBoard.getNumWhiteKings()) == 0)
			return true;
		else 
		{
			Iterator<Move> whitePieces = newBoard.getLocationsWithWhitePiece().iterator();
			Iterator<Move> iterator;
			
			while(whitePieces.hasNext())
			{
				prevMove = whitePieces.next();
				color = newBoard.pieceAtPosition(prevMove.getRow(), prevMove.getColumn());
				iterator = newBoard.getLegalPositionsWithoutPiece().iterator();
				
				while(iterator.hasNext())
				{
					move = iterator.next();
					if(newBoard.isMoveLegal(move.getRow(), move.getColumn(), prevMove, color))
						return false;
				}
			}
			return true;
			//if white can't move then black wins
		}
	}
	
	public ArrayList<Pair> getBlackMoves()
	{
		return blackMoveList;
	}
	
	public ArrayList<Pair> getWhiteMoves()
	{
		return whiteMoveList;
	}
	
	public int blackMoves()
	{
		return blackMoveList.size();
	}
	
	public int whiteMoves()
	{
		return whiteMoveList.size();
	}
	
	public int whiteJumpCount()
	{
		return jumps.getWhiteJumpCount();
	}

	public int blackJumpCount()
	{
		return jumps.getBlackJumpCount();
	}

	public int getPieceAtPosition(int row, int column)
	{
		return board[row][column];
	}

	public int getNumWhitePieces()
	{
		return numWhitePieces;
	}

	public int getNumBlackPieces()
	{
		return numBlackPieces;
	}

	public int getNumWhiteKings()
	{
		return numWhiteKings;
	}

	public int getNumBlackKings()
	{
		return numBlackKings;
	}
	
	public Jumps getJumps()
	{
		return jumps;
	}
	
	public ArrayList<Move> getLegalPositionsWithoutPiece()
	{
		return legalPositionsWithoutPiece;
	}
	
	public ArrayList<Move> getLocationsWithWhitePiece()
	{
		return locationsWithWhitePiece;
	}
	
	public ArrayList<Move> getLocationsWithBlackPiece()
	{
		return locationsWithBlackPiece;
	}
	
	public Move moveToMake()
	{
		return moveToMake;
	}

	public boolean isGameOver()
	{
		if (numBlackPieces == 0 || numWhitePieces == 0)
			return true;
		else
			return false;
	}

	public boolean isMultiJump()
	{
		return multiJump;
	}

	public boolean isKing(int row, int column)
	{
		if(board[row][column] == Board.WHITEKING || board[row][column] == Board.BLACKKING)
			return true;
		else
			return false;
	}

	public boolean isMoveLegal(int row, int column, Move prevMove, int color)
	{
		if(color == Board.WHITE)
		{
			if(multiJump)
			{
				if(moveToMake.getRow() == prevMove.getRow() && moveToMake.getColumn() == prevMove.getColumn() 
						&& isJumpAvailable(new Move(row,column),prevMove, color))
					return true;
				else
					return false;
			}
			else if(board[row][column] == Board.EMPTY && (row - prevMove.getRow() == 1 && (Math.abs(column - prevMove.getColumn()) == 1))
					|| isJumpAvailable(new Move(row,column),prevMove, color))
				return true;
			else
				return false;
		}
		else if(color == Board.BLACK)
		{
			if(multiJump)
			{
				if(moveToMake.getRow() == prevMove.getRow() && moveToMake.getColumn() == prevMove.getColumn() 
						&& isJumpAvailable(new Move(row,column),prevMove, color))
					return true;
				else
					return false;
			}
			else if(board[row][column] == Board.EMPTY && (row - prevMove.getRow() == -1 && (Math.abs(column - prevMove.getColumn()) == 1))
					|| isJumpAvailable(new Move(row,column),prevMove, color))
				return true;
			else
				return false;
		}
		else if(color == Board.WHITEKING || color == Board.BLACKKING)
		{
			if(multiJump)
			{
				if(moveToMake.getRow() == prevMove.getRow() && moveToMake.getColumn() == prevMove.getColumn() 
						&& isJumpAvailable(new Move(row,column),prevMove, color))
					return true;
				else
					return false;
			}
			else if(board[row][column] == Board.EMPTY && (Math.abs(row - prevMove.getRow()) == 1 && (Math.abs(column - prevMove.getColumn()) == 1))
					|| isJumpAvailable(new Move(row,column),prevMove, color))
				return true;
			else
				return false;
		}
		else 
			return false;
	}

	public void RemoveJumpsFromPosition(Move m)
	{
		int row = m.getRow();
		int column = m.getColumn();

		if(row < 6 && column < 6 && pieceAtPosition(row+2,column+2) == Board.EMPTY)
			jumps.removeJumpIfExists(new Move(row+2,column+2),m,pieceAtPosition(row,column));
		if(row > 1 && column < 6 && pieceAtPosition(row-2,column+2) == Board.EMPTY)
			jumps.removeJumpIfExists(new Move(row-2,column+2),m,pieceAtPosition(row,column));
		if(row < 6 && column > 1 && pieceAtPosition(row+2,column-2) == Board.EMPTY)
			jumps.removeJumpIfExists(new Move(row+2,column-2),m,pieceAtPosition(row,column));
		if(row > 1 && column > 1 && pieceAtPosition(row-2,column-2) == Board.EMPTY)
			jumps.removeJumpIfExists(new Move(row-2,column-2),m,pieceAtPosition(row,column));		
	}

	public void CheckJumpsFromSquareItself(Move m)
	{
		int row = m.getRow();
		int column = m.getColumn();

		if(row < 6 && column < 6 //&& at(row+2,col+2) != Board.EMPTY  
				&& isJumpAvailable(new Move(row+2,column+2),m,pieceAtPosition(row,column)))
		{
			jumps.insertJump(new Move(row+2,column+2),m,pieceAtPosition(row,column));
		}
		if(row > 1 && column < 6 //&& at(row-2,col+2) != Board.EMPTY
				&& isJumpAvailable(new Move(row-2,column+2),m,pieceAtPosition(row,column)))
		{
			jumps.insertJump(new Move(row-2,column+2),m,pieceAtPosition(row,column));
		}
		if(row < 6 && column > 1 && isJumpAvailable(new Move(row+2,column-2),m,pieceAtPosition(row,column)))
		{
			jumps.insertJump(new Move(row+2,column-2),m,pieceAtPosition(row,column));
		}
		if(row > 1 && column > 1 && isJumpAvailable(new Move(row-2,column-2),m,pieceAtPosition(row,column)))
		{
			jumps.insertJump(new Move(row-2,column-2),m,pieceAtPosition(row,column));
		}
	}

	//m is result of move need to check if legal squares two away couldve jumped and are now blocked
	//check true should be called From doMove with prevMove and false should be called from doMove with move
	public void CheckJumpsFromLegalSquaresTwoAway(Move m, boolean check)
	{
		int row = m.getRow();
		int column = m.getColumn();

		//No jumps to check if move was on the edges for legal squares
		if(check)
		{
			if(row < 6 && column < 6 && pieceAtPosition(row+2,column+2) != Board.EMPTY  
					&& isJumpAvailable(m,new Move(row+2,column+2),pieceAtPosition(row+2,column+2)))
			{
				jumps.insertJump(m,new Move(row+2,column+2),pieceAtPosition(row+2,column+2));
			}
			if(row > 1 && column < 6 && pieceAtPosition(row-2,column+2) != Board.EMPTY
					&& isJumpAvailable(m,new Move(row-2,column+2),pieceAtPosition(row-2,column+2)))
			{
				jumps.insertJump(m,new Move(row-2,column+2),pieceAtPosition(row-2,column+2));
			}
			if(row < 6 && column > 1 && pieceAtPosition(row+2,column-2) != Board.EMPTY
					&& isJumpAvailable(m,new Move(row+2,column-2),pieceAtPosition(row+2,column-2)))
			{
				jumps.insertJump(m,new Move(row+2,column-2),pieceAtPosition(row+2,column-2));
			}
			if(row > 1 && column > 1 && pieceAtPosition(row-2,column-2) != Board.EMPTY
					&& isJumpAvailable(m,new Move(row-2,column-2),pieceAtPosition(row-2,column-2)))
			{
				jumps.insertJump(m,new Move(row-2,column-2),pieceAtPosition(row-2,column-2));
			}
		}
		else
		{
			if(row < 6 && column < 6 && pieceAtPosition(row+2,column+2) != Board.EMPTY)
				jumps.removeJumpIfExists(m,new Move(row+2,column+2),pieceAtPosition(row+2,column+2));
			if(row > 1 && column < 6 && pieceAtPosition(row-2,column+2) != Board.EMPTY)
				jumps.removeJumpIfExists(m,new Move(row-2,column+2),pieceAtPosition(row-2,column+2));
			if(row < 6 && column > 1 && pieceAtPosition(row+2,column-2) != Board.EMPTY)
				jumps.removeJumpIfExists(m,new Move(row+2,column-2),pieceAtPosition(row+2,column-2));
			if(row > 1 && column > 1 && pieceAtPosition(row-2,column-2) != Board.EMPTY)
				jumps.removeJumpIfExists(m,new Move(row-2,column-2),pieceAtPosition(row-2,column-2));
		}
	}

	//m is piece that needs to be checked if legal squares one away can capture it
	//color is color of piece that is being captured
	public void CheckJumpsWithMoveAsCapturedPiece(Move m, boolean check)
	{
		int row = m.getRow();
		int column = m.getColumn();

		//No jumps to check if move was on the edges for legal squares
		if(check)
		{
			if(row > 0 && row < 7 && column > 0 && column < 7)
			{
				if(pieceAtPosition(row-1,column-1) != Board.EMPTY 
						&& isJumpAvailable(new Move(row+1,column+1),new Move(row-1,column-1),pieceAtPosition(row-1,column-1)))
				{
					jumps.insertJump(new Move(row+1,column+1),new Move(row-1,column-1),pieceAtPosition(row-1,column-1));
				}
				if(pieceAtPosition(row+1,column-1) != Board.EMPTY 
						&& isJumpAvailable(new Move(row-1,column+1),new Move(row+1,column-1),pieceAtPosition(row+1,column-1)))
				{
					jumps.insertJump(new Move(row-1,column+1),new Move(row+1,column-1),pieceAtPosition(row+1,column-1));
				}
				if(pieceAtPosition(row-1,column+1) != Board.EMPTY 
						&& isJumpAvailable(new Move(row+1,column-1),new Move(row-1,column+1),pieceAtPosition(row-1,column+1)))
				{
					jumps.insertJump(new Move(row+1,column-1),new Move(row-1,column+1),pieceAtPosition(row-1,column+1));
				}
				if(pieceAtPosition(row+1,column+1) != Board.EMPTY 
						&& isJumpAvailable(new Move(row-1,column-1),new Move(row+1,column+1),pieceAtPosition(row+1,column+1)))
				{
					jumps.insertJump(new Move(row-1,column-1),new Move(row+1,column+1),pieceAtPosition(row+1,column+1));
				}
			}
		}
		else	//will do one extra check (the square the piece moved to, to which no jump could have come from)
		{
			if(row > 0 && row < 7 && column > 0 && column < 7)
			{
				if(pieceAtPosition(row-1,column-1) != Board.EMPTY)
					jumps.removeJumpIfExists(new Move(row+1,column+1),new Move(row-1,column-1),pieceAtPosition(row-1,column-1));
				if(pieceAtPosition(row+1,column-1) != Board.EMPTY)
					jumps.removeJumpIfExists(new Move(row-1,column+1),new Move(row+1,column-1),pieceAtPosition(row+1,column-1));
				if(pieceAtPosition(row-1,column+1) != Board.EMPTY)
					jumps.removeJumpIfExists(new Move(row+1,column-1),new Move(row-1,column+1),pieceAtPosition(row-1,column+1));
				if(pieceAtPosition(row+1,column+1) != Board.EMPTY)
					jumps.removeJumpIfExists(new Move(row-1,column-1),new Move(row+1,column+1),pieceAtPosition(row+1,column+1));
			}
		}
	}

	//Check if jumps are available given a position and color
	public boolean isJumpAvailable(Move m, int color)
	{
		if(color == Board.WHITE)
		{
			return ((m.getRow() < 6 && m.getColumn() < 6 && isJumpAvailable(new Move(m.getRow()+2,m.getColumn()+2),m,color))
					|| (m.getRow() < 6 && m.getColumn() > 1 && isJumpAvailable(new Move(m.getRow()+2,m.getColumn()-2),m,color)));
		}
		else if(color == Board.BLACK)
		{
			return ((m.getRow() > 1 && m.getColumn() < 6 &&isJumpAvailable(new Move(m.getRow()-2,m.getColumn()+2),m,color)) 
					|| (m.getRow() > 1 && m.getColumn()  > 1 &&isJumpAvailable(new Move(m.getRow()-2,m.getColumn()-2),m,color)));
		}
		else if(color == Board.WHITEKING || color == Board.BLACKKING)
		{
			return ((m.getRow() < 6 && m.getColumn() < 6 && isJumpAvailable(new Move(m.getRow()+2,m.getColumn()+2),m,color)) 
					|| (m.getRow() < 6 && m.getColumn() > 1 && isJumpAvailable(new Move(m.getRow()+2,m.getColumn()-2),m,color))
					|| (m.getRow() > 1 && m.getColumn() < 6 &&isJumpAvailable(new Move(m.getRow()-2,m.getColumn()+2),m,color))
					|| (m.getRow() > 1 && m.getColumn()  > 1 &&isJumpAvailable(new Move(m.getRow()-2,m.getColumn()-2),m,color)));
		}
		else 
			return false;
	}

	//Checks if a jump is possible given a Move(checks to see if move will capture a piece)
	public boolean isJumpAvailable(Move curr, Move prev, int color)
	{
		int row = curr.getRow();
		int column = curr.getColumn();
		int prevRow = prev.getRow();
		int prevColumn = prev.getColumn();

		if(board[row][column] == Board.EMPTY)
		{
			if(color == Board.WHITE)
			{
				if((row-prevRow == 2) && (Math.abs(column - prevColumn) == 2) &&
						(board[(row+prevRow)/2][(column+prevColumn)/2] == Board.BLACK || board[(row+prevRow)/2][(column+prevColumn)/2] == Board.BLACKKING))
				{
					moveIsAJump = true;
					return true;
				}
				else 
					return false;
			}
			else if(color == Board.BLACK)
			{
				if((row-prevRow == -2) && (Math.abs(column - prevColumn) == 2) &&
						(board[(row+prevRow)/2][(column+prevColumn)/2] == Board.WHITE || board[(row+prevRow)/2][(column+prevColumn)/2] == Board.WHITEKING))
				{
					moveIsAJump = true;
					return true;
				}
				else 
					return false;
			}
			else if(color == Board.WHITEKING)
			{
				if((Math.abs(row-prevRow) == 2) && (Math.abs(column - prevColumn) == 2) &&
						(board[(row+prevRow)/2][(column+prevColumn)/2] == Board.BLACK || board[(row+prevRow)/2][(column+prevColumn)/2] == Board.BLACKKING))
				{
					moveIsAJump = true;
					return true;
				}
				else 
					return false;
			}
			else if(color == Board.BLACKKING)
			{
				if((Math.abs(row-prevRow) == 2) && (Math.abs(column - prevColumn) == 2) &&
						(board[(row+prevRow)/2][(column+prevColumn)/2] == Board.WHITE || board[(row+prevRow)/2][(column+prevColumn)/2] == Board.WHITEKING))
				{
					moveIsAJump = true;
					return true;
				}
				else 
					return false;
			}
			else 
				return false;
		}
		else 
			return false;
	}

	private boolean performJumpIfExists(Move m, Move prevMove, int color)
	{
		int row = m.getRow();
		int column = m.getColumn();
		int prevRow = prevMove.getRow();
		int prevCol = prevMove.getColumn();
		Move captured = new Move((row+prevRow)/2,(column+prevCol)/2);
		Random generator = new Random();
		int i = generator.nextInt(legalPositionsWithoutPiece.size());
		
		if(color == Board.WHITE)
		{
			if((row-prevRow == 2) && (Math.abs(column - prevCol) == 2))
			{
				if(board[(row+prevRow)/2][(column+prevCol)/2] == Board.BLACK)
					numBlackPieces--;
				else if(board[(row+prevRow)/2][(column+prevCol)/2] == Board.BLACKKING)
					numBlackKings--;

				locationsWithBlackPiece.remove(captured);
				legalPositionsWithoutPiece.add(i,captured);
				
				currZobrist = XOR(currZobrist,zobristKeys.getMoveString(((row+prevRow)/2), ((column+prevCol)/2),pieceAtPosition(((row+prevRow)/2),((column+prevCol)/2))));
				currZobrist = XOR(currZobrist,zobristKeys.getMoveString(((row+prevRow)/2), ((column+prevCol)/2),Board.EMPTY ));
				
				board[(row+prevRow)/2][(column+prevCol)/2] = Board.EMPTY;
				
				moveIsAJump = false;
				return true;
			}
			else
				return false;
		}
		else if(color == Board.WHITEKING)
		{
			if((Math.abs(row-prevRow) == 2) && (Math.abs(column - prevCol) == 2))
			{
				if(board[(row+prevRow)/2][(column+prevCol)/2] == Board.BLACK)
				{
					numBlackPieces--;
				}
				else if(board[(row+prevRow)/2][(column+prevCol)/2] == Board.BLACKKING)
				{
					numBlackKings--;
				}

				locationsWithBlackPiece.remove(captured);
				legalPositionsWithoutPiece.add(i,captured);
				
				currZobrist = XOR(currZobrist,zobristKeys.getMoveString(((row+prevRow)/2), ((column+prevCol)/2),pieceAtPosition(((row+prevRow)/2),((column+prevCol)/2))));
				currZobrist = XOR(currZobrist,zobristKeys.getMoveString(((row+prevRow)/2), ((column+prevCol)/2),Board.EMPTY ));
				
				board[(row+prevRow)/2][(column+prevCol)/2] = Board.EMPTY;

				moveIsAJump = false;
				return true;
			}
			else
				return false;
		}
		else if(color == Board.BLACK)
		{
			if((row-prevRow == -2) && (Math.abs(column - prevCol) == 2))
			{				
				if(board[(row+prevRow)/2][(column+prevCol)/2] == Board.WHITE)
					numWhitePieces--;
				else if(board[(row+prevRow)/2][(column+prevCol)/2] == Board.WHITEKING)
					numWhiteKings--;


				locationsWithWhitePiece.remove(captured);
				legalPositionsWithoutPiece.add(i,captured);
				
				currZobrist = XOR(currZobrist,zobristKeys.getMoveString(((row+prevRow)/2), ((column+prevCol)/2),pieceAtPosition(((row+prevRow)/2),((column+prevCol)/2))));
				currZobrist = XOR(currZobrist,zobristKeys.getMoveString(((row+prevRow)/2), ((column+prevCol)/2),Board.EMPTY ));
				
				board[(row+prevRow)/2][(column+prevCol)/2] = Board.EMPTY;

				moveIsAJump = false;
				return true;
			}
			else
				return false;
		}
		else if(color == Board.BLACKKING)
		{
			if((Math.abs(row-prevRow) == 2) && (Math.abs(column - prevCol) == 2))
			{
				if(board[(row+prevRow)/2][(column+prevCol)/2] == Board.WHITE)
					numWhitePieces--;
				else if(board[(row+prevRow)/2][(column+prevCol)/2] == Board.WHITEKING)
					numWhiteKings--;

				
				locationsWithWhitePiece.remove(captured);
				legalPositionsWithoutPiece.add(i,captured);
				
				currZobrist = XOR(currZobrist,zobristKeys.getMoveString(((row+prevRow)/2), ((column+prevCol)/2),pieceAtPosition(((row+prevRow)/2),((column+prevCol)/2))));
				currZobrist = XOR(currZobrist,zobristKeys.getMoveString(((row+prevRow)/2), ((column+prevCol)/2),Board.EMPTY ));
				
				board[(row+prevRow)/2][(column+prevCol)/2] = Board.EMPTY;

				moveIsAJump = false;
				return true;
			}
			else
				return false;
		}
		else 
			return false;
	}

	//might not need this
	public void makeKing(int row, int column, int color)
	{
		if(board[row][column] == Board.WHITE)
			board[row][column] = Board.WHITEKING;
		else //board[row][column] == Board.BLACK
			board[row][column] = Board.BLACKKING;
	}

	public int pieceAtPosition(int row, int column)
	{
		if(board[row][column] == Board.BLACK)
			return Board.BLACK;
		else if(board[row][column] == Board.WHITE)
			return Board.WHITE;
		else if(board[row][column] == Board.BLACKKING)
			return Board.BLACKKING;
		else if(board[row][column] == Board.WHITEKING)
			return Board.WHITEKING;
		else if(board[row][column] == Board.EMPTY) 
			return Board.EMPTY;
		else
			return Board.ILLEGAL;
	}

	private void finishMove(Move m, Move prevMove, int color)
	{	
		if(performJumpIfExists(m,prevMove,color))
		{
			multiJump = isJumpAvailable(m,color);
			moveToMake = new Move(m.getRow(),m.getColumn());
			
			if(justCrowned)
			{
				multiJump = false;
			}
			
			//Update Jumps After Board Changes-----> Jump Move
			CheckJumpsFromLegalSquaresTwoAway(prevMove,true);
			CheckJumpsFromLegalSquaresTwoAway(new Move((m.getRow()+prevMove.getRow())/2,(m.getColumn()+prevMove.getColumn())/2),true);
			CheckJumpsWithMoveAsCapturedPiece(m,true);
			CheckJumpsFromSquareItself(m);
		
		}
		else
		{
			//Update Jumps After Board Changes ---> Regular Move
			CheckJumpsFromLegalSquaresTwoAway(prevMove, true);
			CheckJumpsWithMoveAsCapturedPiece(m,true);		
			CheckJumpsFromSquareItself(m);
		}
		
		moveIsAJump = false;
		justCrowned = false;
	}

	private void moveUpdatesNaive()
	{
		blackMoveList.clear();
		whiteMoveList.clear();
		
		Collections.shuffle(locationsWithBlackPiece);
		Collections.shuffle(locationsWithWhitePiece);
		
		for(int i=0;i<locationsWithBlackPiece.size();i++)
		{
			addToMoveList(locationsWithBlackPiece.get(i), 
					pieceAtPosition(locationsWithBlackPiece.get(i).getRow(),locationsWithBlackPiece.get(i).getColumn()));
		}
		for(int i=0;i<locationsWithWhitePiece.size();i++)
		{
			addToMoveList(locationsWithWhitePiece.get(i), 
					pieceAtPosition(locationsWithWhitePiece.get(i).getRow(),locationsWithWhitePiece.get(i).getColumn()));
		}
		
		//for(int j=0;j< blackMoveList.size();j++)
		//	System.out.println(blackMoveList.get(j));
		//System.out.println("W: "+whiteMoveList.size()+"|B: "+blackMoveList.size());		
	}
	
	private void JumpUpdatesBeforeBoardChange(Move m, Move prevMove) 
	{
		if(!moveIsAJump)
		{
			RemoveJumpsFromPosition(prevMove);
			CheckJumpsWithMoveAsCapturedPiece(prevMove,false);
			CheckJumpsFromLegalSquaresTwoAway(m, false);
		}
		else
		{
			RemoveJumpsFromPosition(prevMove);
			RemoveJumpsFromPosition(new Move((m.getRow()+prevMove.getRow())/2,(m.getColumn()+prevMove.getColumn())/2));
			CheckJumpsWithMoveAsCapturedPiece(prevMove,false);
			CheckJumpsWithMoveAsCapturedPiece(new Move((m.getRow()+prevMove.getRow())/2,(m.getColumn()+prevMove.getColumn())/2),false);
			CheckJumpsFromLegalSquaresTwoAway(m, false);
		}
	}
	
	//Look over
	public Board tryMove(Move m, Move prevMove, int color, Board board)
	{
		//System.out.println("Move: " + m.toString() + " from " + prevMove.toString());
		//Board newBoard = new Board(board);
		boolean okMove = this.doMove(m, prevMove, color);
		//System.out.println(this.toString());
		//System.out.println("JUMPS- B:" + this.blackJumpCount() +"W: "+this.whiteJumpCount());
		if(!okMove)
			System.out.println("Invalid move");
		return this;
	}

	public boolean doMove(Move m, Move prevMove, int color)
	{
		Random generator = new Random();
		int row = m.getRow();
		int column = m.getColumn();
		int prevRow = prevMove.getRow();
		int prevCol = prevMove.getColumn();

		JumpUpdatesBeforeBoardChange(m,prevMove);
		//moveUpdatesBeforeBoardChange(m,prevMove);
		
		//done before any changes
		currZobrist = XOR(currZobrist,zobristKeys.getMoveString(prevRow, prevCol, color)); //for XOR out @ prevMove
		currZobrist = XOR(currZobrist,zobristKeys.getMoveString(prevRow, prevCol, Board.EMPTY)); //for XOR in @ prevMove
		currZobrist = XOR(currZobrist,zobristKeys.getMoveString(row, column, pieceAtPosition(row,column))); //for XOR out @ Move
		
		board[row][column] = color;
		board[prevRow][prevCol] = Board.EMPTY;
		

		if(color == Board.WHITE && row == 7)
		{
			board[row][column] = Board.WHITEKING;
			color = Board.WHITEKING;
			numWhiteKings++;
			numWhitePieces--;
			justCrowned = true;
		}
		else if(color == Board.BLACK && row == 0)
		{
			board[row][column] = Board.BLACKKING;
			color = Board.BLACKKING;
			numBlackKings++;
			numBlackPieces--;
			justCrowned = true;
		}
		
		//Has to be after in case a king is a result of move 
		currZobrist = XOR(currZobrist,zobristKeys.getMoveString(row, column, color)); //for XOR in @ Move

		if(color == Board.BLACK || color == Board.BLACKKING)
		{
			int i = generator.nextInt(locationsWithBlackPiece.size());
			locationsWithBlackPiece.remove(new Move(prevMove));
			locationsWithBlackPiece.add(i,new Move(m));
		}
		else
		{
			int i = generator.nextInt(locationsWithWhitePiece.size());
			locationsWithWhitePiece.remove(new Move(prevMove));
			locationsWithWhitePiece.add(i,new Move(m));
		}

		int j = generator.nextInt(legalPositionsWithoutPiece.size());
		legalPositionsWithoutPiece.remove(new Move(m));
		legalPositionsWithoutPiece.add(j,new Move(prevMove));

		//Check if move captures piece and performs updates accordingly
		//or discovers that move wasn't a jump and performs updates accordingly
		finishMove(m,prevMove,color);
		moveUpdatesNaive();
		return true;
	}

	public String toString()
	{
		StringBuffer s = new StringBuffer(64);
		for (int row = 0; row < 8; row++)
		{
			for (int column = 0; column < 8; column++)
			{
				if (board[row][column] == Board.EMPTY)
					s.append('e');
				else if (board[row][column] == Board.WHITE)
					s.append('w');
				else if (board[row][column] == Board.BLACK)
					s.append('b');
				else if (board[row][column] == Board.WHITEKING)
					s.append('W');
				else if (board[row][column] == Board.BLACKKING)
					s.append('B');
				else //if (board[row][column] == Board.ILLEGAL)
					s.append('_');
			}
			s.append('\n');
		}
		return s.toString();
	}
}
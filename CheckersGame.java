import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class CheckersGame
{
	private int turnCount;
	private Board currentBoard;
	private boolean gameIsOver, blackIsAI, whiteIsAI, blacksTurn;
	private boolean whiteHasWon = false, blackHasWon = false;
	private CheckersAI blackAI, whiteAI;
	private GameClock blackGameClock, whiteGameClock;
	private TransPosTable theMotherload;
	private ArrayList<TransPosTableEntry> whiteMoves;
	private ArrayList<TransPosTableEntry> blackMoves;

	public CheckersGame(boolean blackIsHuman, int blackMaxPlies, boolean whiteIsHuman, 
			int whiteMaxPlies, int secondsPerPlayer)
	{
		//theMotherload = new TransPosTable();
		try 
		{
			FileInputStream fis = new FileInputStream("serial");
			ObjectInputStream ois = new ObjectInputStream(fis);
			theMotherload = (TransPosTable)ois.readObject();
			ois.close();
			//System.out.println("Table Size: " + theMotherload.size());

		}
		catch(Exception e) 
		{
			System.out.println("Exception during deserialization: " + e + ", New transposition table created(Will overwrite " + 
			"exisiting records if a new game is played and finished.");
			theMotherload = new TransPosTable();
		} 

		turnCount = 0;
		//theMotherload = new TransPosTable();
		whiteMoves = new ArrayList<TransPosTableEntry>();
		blackMoves = new ArrayList<TransPosTableEntry>();

		currentBoard = new Board();
		gameIsOver = whiteHasWon = blackHasWon = false;
		blackIsAI = !blackIsHuman;
		whiteIsAI = !whiteIsHuman;
		blacksTurn = true; //!blacksTurn == whitesTurn
		if(blackIsAI)
			blackAI = new CheckersAI(Board.BLACK, blackMaxPlies);
		if(whiteIsAI)
			whiteAI = new CheckersAI(Board.WHITE, whiteMaxPlies);
		blackGameClock = new GameClock(secondsPerPlayer);
		whiteGameClock = new GameClock(secondsPerPlayer);
		//whiteGameClock.start();
		blackGameClock.start();
	}

	public Board getCurrentBoard()
	{
		return currentBoard;
	}

	public boolean blackIsAI()
	{
		return blackIsAI;
	}

	public boolean whiteIsAI()
	{
		return whiteIsAI;
	}

	public boolean blackIsHuman()
	{
		return !blackIsAI;
	}

	public boolean whiteIsHuman()
	{
		return !whiteIsAI;
	}

	public boolean blacksTurn()
	{
		return blacksTurn;
	}

	public boolean whitesTurn()
	{
		return !blacksTurn;
	}

	public void flipTurns()
	{
		blacksTurn = !blacksTurn;
	}

	public boolean isOver()
	{
		return gameIsOver;
	}

	public int winner()
	{
		if(blackHasWon)
			return Board.BLACK;
		else if(whiteHasWon)
			return Board.WHITE;
		else
			return 0;
	}

	public boolean gameIsOver()
	{
		if(victoryForWhite() || victoryForBlack())
			return true;
		else
			return false;
	}

	public boolean victoryForWhite()
	{
		return currentBoard.victoryForWhite(currentBoard);
	}

	public boolean victoryForBlack()
	{
		return currentBoard.victoryForBlack(currentBoard);
	}

	public int pieceAtPosition(int row, int col)
	{
		return currentBoard.pieceAtPosition(row, col);
	}

	private void flipClocks()
	{
		if(blacksTurn)
		{
			whiteGameClock.pause();
			blackGameClock.start();		
		}
		else
		{
			blackGameClock.pause();
			whiteGameClock.start();	
		}
	}

	public long getRemainingTime(int color)
	{
		if(color == Board.BLACK)
			return blackGameClock.millisRemaining();
		else
			return whiteGameClock.millisRemaining();
	}

	public boolean isValidMove(int row, int column, Move prevMove, int color)
	{
		return currentBoard.isMoveLegal(row, column, prevMove, color);
	}

	public boolean isValidMove(int row, int column, Move prevMove, int color, Board board)
	{
		return board.isMoveLegal(row, column, prevMove, color);
	}

	public boolean makeMove(Move move, Move prevMove, int color)
	{
		if(gameIsOver) //game should be in progress
		{
			System.err.println("makeMove called when game is Over.");
			System.exit(0);
		}
		if(((blacksTurn && color != Board.BLACK) && (blacksTurn && color != Board.BLACKKING)) ||
				((!blacksTurn && color != Board.WHITE) && (!blacksTurn && color != Board.WHITEKING)))
		{
			System.err.println("makeMove called with invalid color: " + color);
			System.exit(0);
		}

		// Return true if valid move, false otherwise.
		boolean valid = currentBoard.doMove(move, prevMove, color);
		turnCount++;

		blacksTurn = !blacksTurn;
		flipClocks();

		if(!currentBoard.isMultiJump())
		{
			if(victoryForWhite())
			{
				whiteHasWon = true;
				gameIsOver = true;
				blackGameClock.pause();
				whiteGameClock.pause();

				updateTable(whiteHasWon);
			}
			else if(victoryForBlack())
			{
				blackHasWon = true;
				gameIsOver = true;
				blackGameClock.pause();
				whiteGameClock.pause();

				updateTable(false);
			}
		}
		return valid;
	}

	public boolean makeMove(int row, int column, int prevRow, int prevColumn, int color)
	{
		return makeMove(new Move(row, column), new Move(prevRow, prevColumn), color);
	}

	public boolean AIisReady()
	{
		if(gameIsOver) //if(gameIsOver())
			return false;
		else if(blacksTurn && blackIsAI)
			return true;
		else if(!blacksTurn && whiteIsAI)
			return true;
		else
			return false;
	}

	public void makeAIMove()
	{
		if(blacksTurn)
		{
			getAIMove(Board.BLACK);
			turnCount++;
			blacksTurn = false;
		}
		else if(!blacksTurn) //if(whitesTurn)
		{
			getAIMove(Board.WHITE);
			turnCount++;
			blacksTurn = true;
		}

		flipClocks();
		//Perform Transposition table update here inside each branch ForWhite and ForBlack,
		//take boards from arraylist and insert or update table accordingly
		if(victoryForWhite())
		{
			whiteHasWon = true;
			gameIsOver = true;

			updateTable(true);

			try
			{
				FileOutputStream fos = new FileOutputStream("serial");
				ObjectOutputStream oos = new ObjectOutputStream(fos);
				oos.writeObject(theMotherload);
				oos.flush();
				oos.close(); 
			}
			catch(Exception e)
			{
				System.out.println("whiteSerializationError: " + e);
				System.exit(0);
			} 

			blackGameClock.pause();
			whiteGameClock.pause();
		}
		else if(victoryForBlack())
		{
			blackHasWon = true;
			gameIsOver = true;

			updateTable(false);

			try
			{
				FileOutputStream fos = new FileOutputStream("serial");
				ObjectOutputStream oos = new ObjectOutputStream(fos);
				oos.writeObject(theMotherload);
				oos.flush();
				oos.close(); 
			}
			catch(Exception e)
			{
				System.out.println("blackSerializationError: " + e);
				System.exit(0);
			} 

			blackGameClock.pause();
			whiteGameClock.pause();
		}
	}

	//Assuming a true value means whiteWins, false value means black wins
	private void updateTable(boolean whiteWon)
	{
		if(whiteWon)
		{
			for(int i=0;i<whiteMoves.size();i++)
			{
				theMotherload.endGameUpdates(whiteMoves.get(i), whiteWon,true);
			}
			for(int j=0;j<blackMoves.size();j++)
			{
				theMotherload.endGameUpdates(blackMoves.get(j), whiteWon,false);
			}
		}
		else
		{
			for(int i=0;i<blackMoves.size();i++)
			{
				theMotherload.endGameUpdates(blackMoves.get(i), whiteWon,false);
			}
			for(int j=0;j<whiteMoves.size();j++)
			{
				theMotherload.endGameUpdates(whiteMoves.get(j), whiteWon,true);
			}
		}
	}

	private void getAIMove(int color)
	{
		TransPosTableEntry temp = null;
		if(color == Board.BLACK)
		{
			temp = blackAI.chooseMove(blackGameClock, currentBoard, theMotherload, turnCount);
			currentBoard = new Board(temp.getBoard());
			blackMoves.add(temp);
		}
		else //(color == Board.WHITE)
		{
			temp = whiteAI.chooseMove(whiteGameClock, currentBoard, theMotherload, turnCount);
			currentBoard = new Board(temp.getBoard());
			whiteMoves.add(temp);
		}
		
		//System.out.println(currentBoard.toString());
	}
}
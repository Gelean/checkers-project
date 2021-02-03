import ucigame.*;

public class Checkers extends Ucigame
{
	private static final long serialVersionUID = 1L;
	int IcurrPiece, prevRow = -1, prevColumn = -1;
	static int INVALID = -99;
	boolean humanMoved = true, holdingMouse = false;
	Sprite whitePiece, whiteKingPiece, blackPiece, blackKingPiece, newGameButton; //undoButton
	CheckersGame game = null;

	public void setup()
	{
		framerate(50);
		window.size(800, 700);
		window.title("Checkers, Team 4");
		canvas.background(getImage("images/checkersboard.png"));
		canvas.font("Times New Roman", PLAIN, 16, 255, 255, 255);

		whitePiece = makeSprite(getImage("images/whitePiece.png", 255, 255, 0));
		whiteKingPiece = makeSprite(getImage("images/whiteKingPiece.png", 255, 255, 0));
		blackPiece = makeSprite(getImage("images/blackPiece.png", 255, 255, 0));
		blackKingPiece = makeSprite(getImage("images/blackKingPiece.png", 255, 255, 0));
		newGameButton = makeButton("NewGame", getImage("images/newGame.png"), 95, 25);
		newGameButton.position(200, 550);
		//undoButton.position(300, 550);
	}

	public void draw()
	{
		canvas.clear();
		if(game == null)
			canvas.putText("No game is in progress", 150, 25);
		else
		{
			//If the human moved, don't call the AI
			if(humanMoved)
				humanMoved = false;
			else
				if(game.AIisReady())
					game.makeAIMove();
			
			if(game.isOver())
			{
				canvas.putText("Game Over", 1360, 25);
				if(game.winner() == Board.BLACK)
					canvas.putText("Black wins", 260, 25);
				else if (game.winner() == Board.WHITE)
					canvas.putText("White wins", 260, 25);
				else
					canvas.putText("Draw", 260, 25);
			}
			else
			{
				canvas.putText("Game in progress: ", 120, 25);
				if(game.blacksTurn())
					canvas.putText("Black's move", 240, 25);
				else
					canvas.putText("White's move", 240, 25);
			}
			if(game.blackIsHuman())
				canvas.putText("Black (Human):", 50, 50);
			else
				canvas.putText("Black (AI):", 50, 50);
			if(game.whiteIsHuman())
				canvas.putText("White (Human):", 50, 70);
			else
				canvas.putText("White (AI):", 50, 70);

			canvas.putText("" + (int)(game.getRemainingTime(Board.BLACK)/1000) + " seconds remaining", 180, 50);
			canvas.putText("" + (int)(game.getRemainingTime(Board.WHITE)/1000) + " seconds remaining", 180, 70);

			if((game.getCurrentBoard().getNumWhitePieces() + game.getCurrentBoard().getNumWhiteKings()) <= 9)
				canvas.putText("0" + (game.getCurrentBoard().getNumWhitePieces() + 
						game.getCurrentBoard().getNumWhiteKings()) + " piece(s) remaining", 360, 70);
			else
				canvas.putText("" + (game.getCurrentBoard().getNumWhitePieces() + 
						game.getCurrentBoard().getNumWhiteKings()) + " piece(s) remaining", 360, 70);
			if((game.getCurrentBoard().getNumBlackPieces() + game.getCurrentBoard().getNumBlackKings()) <= 9)
				canvas.putText("0" + (game.getCurrentBoard().getNumBlackPieces() + 
						game.getCurrentBoard().getNumBlackKings()) +" piece(s) remaining", 360, 50);
			else
				canvas.putText("" + (game.getCurrentBoard().getNumBlackPieces() + 
						game.getCurrentBoard().getNumBlackKings()) +" piece(s) remaining", 360, 50);
			
			for(int row = 0; row < 8; row++)
			{
				for(int column = 0; column < 8; column++)
				{
					if(game.pieceAtPosition(row, column) == Board.WHITE)
					{
						if(holdingMouse && !(row == prevRow && column == prevColumn))
						{
							whitePiece.position(columnToPixel(column), rowToPixel(row));
							whitePiece.draw();
						}
						if(!holdingMouse)
						{
							whitePiece.position(columnToPixel(column), rowToPixel(row));
							whitePiece.draw();
						}
					}
					else if(game.pieceAtPosition(row, column) == Board.BLACK)
					{
						if(holdingMouse && !(row == prevRow && column == prevColumn))
						{
							blackPiece.position(columnToPixel(column), rowToPixel(row));
							blackPiece.draw();
						}
						else if(!holdingMouse)
						{
							blackPiece.position(columnToPixel(column), rowToPixel(row));
							blackPiece.draw();
						}
					}
					else if(game.pieceAtPosition(row, column) == Board.WHITEKING)
					{
						if(holdingMouse && !(row == prevRow && column == prevColumn))
						{
							whiteKingPiece.position(columnToPixel(column), rowToPixel(row));
							whiteKingPiece.draw();
						}
						else if(!holdingMouse)
						{
							whiteKingPiece.position(columnToPixel(column), rowToPixel(row));
							whiteKingPiece.draw();
						}
					}
					else if(game.pieceAtPosition(row, column) == Board.BLACKKING)
					{
						if(holdingMouse && !(row == prevRow && column == prevColumn))
						{
							blackKingPiece.position(columnToPixel(column), rowToPixel(row));
							blackKingPiece.draw();
						}
						else if(!holdingMouse)
						{
							blackKingPiece.position(columnToPixel(column), rowToPixel(row));
							blackKingPiece.draw();
						}
					}
				}
			}
			if(holdingMouse)
			{
				if(IcurrPiece == Board.WHITE && game.whitesTurn())
				{
					whitePiece.position(mouse.x()-25,mouse.y()-25);
					whitePiece.draw();
				}
				else if(IcurrPiece == Board.BLACK && game.blacksTurn())
				{
					blackPiece.position(mouse.x()-25,mouse.y()-25);
					blackPiece.draw();
				}
				else if(IcurrPiece == Board.WHITEKING && game.whitesTurn())
				{
					whiteKingPiece.position(mouse.x()-25,mouse.y()-25);
					whiteKingPiece.draw();
				}
				else if(IcurrPiece == Board.BLACKKING && game.blacksTurn())
				{
					blackKingPiece.position(mouse.x()-25,mouse.y()-25);
					blackKingPiece.draw();
				}
			}
		}

		newGameButton.draw();
	}

	//Create a new game upon clicking the New Game button
	public void onClickNewGame()
	{
		println("New Game");
		NewGameDialog dialog = new NewGameDialog();
		dialog.setLocationRelativeTo(this);
		dialog.setVisible(true);

		if(dialog.okPressed())
		{
			game = new CheckersGame(dialog.blackIsHuman(), dialog.blackMaxPlies(), 
					dialog.whiteIsHuman(), dialog.whiteMaxPlies(), dialog.secondsPerPlayer());
		}
	}

	//Make move when the mouse button is released
	public void onMouseReleased()
	{
		if(game == null)
			return;
		else if(game.isOver())
			return;
		int column = mouseXtoColumn(mouse.x());
		int row = mouseYtoRow(mouse.y());
		if(column == INVALID || row == INVALID) //click was not in a board square
			return;								

		//Add this after isValidmove change white to black for blacksTurn
		//&& (game.getCurrentBoard().whiteJumpCount() == 0 || (game.getCurrentBoard().whiteJumpCount > 0 && 
		//								isJumpAvailable(new Move(row,column),new Move(prevRow,prevCol),Board.WHITE))) 
		else if(game.whitesTurn() && game.whiteIsHuman() && IcurrPiece == Board.WHITE && 
				game.isValidMove(row, column,new Move(prevRow,prevColumn), Board.WHITE) 
				&& (game.getCurrentBoard().whiteJumpCount() == 0 || ( 
						game.getCurrentBoard().isJumpAvailable(new Move(row,column),new Move(prevRow,prevColumn),Board.WHITE))))
		{
			game.makeMove(row, column, prevRow, prevColumn, Board.WHITE);

			//Keep turn same as it was before move if a jump is possible as a result of move
			if(game.getCurrentBoard().isMultiJump())
				game.flipTurns();
		}
		else if(game.blacksTurn() && game.blackIsHuman() && IcurrPiece == Board.BLACK &&
				game.isValidMove(row, column,new Move(prevRow,prevColumn), Board.BLACK)
				&& (game.getCurrentBoard().blackJumpCount() == 0 || ( 
						game.getCurrentBoard().isJumpAvailable(new Move(row,column),new Move(prevRow,prevColumn),Board.BLACK))))
		{
			game.makeMove(row, column, prevRow, prevColumn, Board.BLACK);

			if(game.getCurrentBoard().isMultiJump())
				game.flipTurns();
				
		}
		else if(game.whitesTurn() && game.whiteIsHuman() && IcurrPiece == Board.WHITEKING && 
				game.isValidMove(row, column,new Move(prevRow,prevColumn), Board.WHITEKING)
				&& (game.getCurrentBoard().whiteJumpCount() == 0 || ( 
						game.getCurrentBoard().isJumpAvailable(new Move(row,column),new Move(prevRow,prevColumn),Board.WHITEKING))))
		{
			game.makeMove(row, column, prevRow, prevColumn, Board.WHITEKING);

			if(game.getCurrentBoard().isMultiJump())
				game.flipTurns();
		}
		else if(game.blacksTurn() && game.blackIsHuman() && IcurrPiece == Board.BLACKKING &&
				game.isValidMove(row, column,new Move(prevRow,prevColumn), Board.BLACKKING)
				&& (game.getCurrentBoard().blackJumpCount() == 0 || ( 
						game.getCurrentBoard().isJumpAvailable(new Move(row,column),new Move(prevRow,prevColumn),Board.BLACKKING))))
		{
			game.makeMove(row, column, prevRow, prevColumn, Board.BLACKKING);

			if(game.getCurrentBoard().isMultiJump())
				game.flipTurns();
		}
		humanMoved = true;
		holdingMouse = false;
		IcurrPiece = Board.EMPTY;
	}

	//Check if there's a sprite on square where mouse is at, at time of click
	//if so make sprite the current piece 
	public void onMousePressed()
	{
		if(game == null)
			return;
		else if(game.isOver())
			return;
		int column = mouseXtoColumn(mouse.x());
		int row = mouseYtoRow(mouse.y());
		if(column == INVALID || row == INVALID) //click was not in a board square
			return;

		Board board = new Board(game.getCurrentBoard());

		if(Board.WHITE == board.getPieceAtPosition(row, column))
			IcurrPiece = Board.WHITE;			
		else if(Board.BLACK == board.getPieceAtPosition(row, column))
			IcurrPiece = Board.BLACK;
		else if(Board.WHITEKING == board.getPieceAtPosition(row, column))
			IcurrPiece = Board.WHITEKING;
		else if(Board.BLACKKING == board.getPieceAtPosition(row, column))
			IcurrPiece = Board.BLACKKING;

		prevRow = row;
		prevColumn = column;
		holdingMouse = true;
	}

	// The following methods convert pixel coordinates to rows and columns,
	// and rows and columns to pixels.  If the position or dimensions of
	// background.png changes, then these will have to change.
	private int rowToPixel(int row)
	{
		if(row < 0 || row >= 8)
			return -1;
		else
			return 105 + (row * 50) + 2;
	}

	private int columnToPixel(int column)
	{
		if(column < 0 || column >= 8)
			return -1;
		else
			return 15 + (column * 50) + 2;
	}

	private int mouseXtoColumn(int x)
	{
		if(x < 15 || x > 413)
			return INVALID;
		else
		{
			int offset = (x - 15) % 50;
			if (offset == 49)
				return INVALID;
			else
				return (x - 15) / 50;
		}
	}

	private int mouseYtoRow(int y)
	{
		if(y < 105 || y > 503)
			return INVALID;
		else
		{
			int offset = (y - 105) % 50;
			if (offset == 49)
				return INVALID;
			else
				return (y - 105) / 50;
		}
	}
}
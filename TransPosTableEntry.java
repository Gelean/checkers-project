import java.io.Serializable;

//Info for board entry, could store the board, need to store depth, need evaluation...
public class TransPosTableEntry implements Serializable, Comparable<TransPosTableEntry>
{
	private static final long serialVersionUID = 1L;
	private Board board;
	private int eval;
	private int depth;
	private int winCount;
	private int lossCount;
	private int color;
	
	public int compareTo(TransPosTableEntry newEntry)
	{
		if(this.eval <= newEntry.eval)
			return 1;
		else 
			return 0;
	}
	
	public TransPosTableEntry(Board board,int eval, int depth, int color)
	{
		this.board = board;
		this.eval = eval;
		this.depth = depth;
		this.color = color;
	}
	
	public TransPosTableEntry(TransPosTableEntry temp)
	{
		this.board = new Board(temp.board);
		this.eval = new Integer(temp.eval);
		this.depth = new Integer(temp.depth);
	}

	public Board getBoard()
	{
		return board;
	}
	
	public int getColor()
	{
		return color;
	}
	
	public int getEval()
	{
		return eval;
	}
	
	public int getDepth()
	{
		return depth;
	}
	
	public void updateEval(int eval)
	{
		this.eval = eval;
	}
	
	public void incrementWinCount()
	{
		winCount++;
	}
	
	public void incrementLossCount()
	{
		lossCount++;
	}
	
	public int getWinCount()
	{
		return winCount;
	}
	
	public int getLossCount()
	{
		return lossCount;
	}
}

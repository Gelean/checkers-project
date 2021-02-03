import java.io.Serializable;
import java.util.Collection;
import java.util.Hashtable;

/*
 Board Class needs to implement this jump class just handles counting, insertions and deletions
 check the lega squares that are one away(row+1, col+1 ; row+1, col-1...) to see if a jump has been created 
 with the piece at the new position as the captured piece and for the legal squares two away 
 check if a jump into the current one was removed by placing a piece there and check the square 
 itself to see if a jump was created.  And then do the same for the old sport but opposite, check 
 the legal squares 1 away to see if a jump was removed(because the capturing piece is now and 
 empty location) and check the legal squares two away to see if a jump is now 
 available(because it's now an empty location).  
 */
public class Jumps implements Serializable
{
	private static final long serialVersionUID = 1L;
	private int whiteJumpCount;
	private int blackJumpCount;
	private Hashtable<Double, Pair> jumpTable;
	//private ArrayList<Move> movesStart;

	public Jumps()
	{
		whiteJumpCount = 0;
		blackJumpCount = 0;
		jumpTable = new Hashtable<Double, Pair>();
	}

	public Jumps(Jumps jumps)
	{
		this.whiteJumpCount = jumps.whiteJumpCount;
		this.blackJumpCount = jumps.blackJumpCount;
		this.jumpTable = new Hashtable<Double, Pair>();
		this.jumpTable.putAll(jumps.jumpTable);
	}

	public double hashFunction(Move m, Move pM, int color)
	{
		return (  (  (((m.getRow()*23) + (m.getColumn()*13) * (pM.getRow()*19) + (pM.getColumn()*7))*color)
				/ (m.getRow()+m.getColumn()+pM.getRow()+pM.getColumn())  )  );
	}

	public int getWhiteJumpCount()
	{
		return whiteJumpCount;
	}

	public int getBlackJumpCount()
	{
		return blackJumpCount;
	}
	
	public Collection<Pair> getJump()
	{	
		Hashtable<Double,Pair> temp = new Hashtable<Double,Pair>(jumpTable);
		return temp.values();
	}


	public void insertJump(Move m, Move pM, int color)
	{
		double temp = hashFunction(m,pM,color);
		if(temp != 0 && !jumpTable.containsKey(temp))
		{
			Pair p = new Pair(m,pM);
			jumpTable.put(temp, p);
			//jumpTable.put(temp, color);

			if(color == Board.WHITE || color == Board.WHITEKING)
				whiteJumpCount++;
			else //if(color == Board.BLACK || color == Board.BLACKKING)
				blackJumpCount++;
		}
	}
	
	public void removeJumpIfExists(Move m, Move pM, int color)
	{
		double temp = hashFunction(m,pM,color);
		if(temp != 0 && jumpTable.containsKey(temp))
		{
			jumpTable.remove(temp);	

			if(color == Board.WHITE || color == Board.WHITEKING)
				whiteJumpCount--;
			else //if(color == Board.BLACK || color == Board.BLACKKING)
				blackJumpCount--;
		}
	}
}
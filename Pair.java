import java.io.Serializable;

public class Pair implements Serializable
{
	private static final long serialVersionUID = 1L;
	private Move m;
	private Move pm;
	

	public Pair(Move m, Move pM)
	{
		this.m = m;
		this.pm = pM;
	}

	public Pair(Pair p)
	{
		this.m = new Move(p.getMove());
		this.pm = new Move(p.getPrevMove());
	}
	
	public boolean equals(Object m)
	{
		Pair pair = new Pair((Pair)m);
		return ((this.getMove().getRow() == pair.getMove().getRow() && this.getMove().getColumn() == pair.getMove().getColumn()) 
			&& (this.getPrevMove().getRow() == pair.getPrevMove().getRow() && this.getPrevMove().getColumn() == pair.getPrevMove().getColumn()));
	}
	
	public Move getMove()
	{
		return this.m;
	}

	public Move getPrevMove()
	{
		return this.pm;
	}

	public String toString()
	{
		return "[" + this.m.getRow() + "," + this.m.getColumn() + "]" + "[" + this.pm.getRow() + "," + this.pm.getColumn() + "]";
	}
}

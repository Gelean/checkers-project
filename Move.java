import java.io.Serializable;

public class Move implements Serializable
{
	private static final long serialVersionUID = 1L;
	private int row;
	private int column;

	public Move(int row, int column)
	{
		this.row = row;
		this.column = column;
	}

	public Move()
	{
		this.row = Integer.MIN_VALUE;
		this.column = Integer.MIN_VALUE;
	}

	public Move(Move m)
	{
		this.row = new Integer(m.row);
		this.column = new Integer(m.column);
	}

	public boolean equals(Object m)
	{
		Move move = new Move((Move)m);
		return (this.getRow() == move.getRow()) && (this.getColumn() == move.getColumn());
	}

	public int getRow()
	{
		return this.row;
	}

	public int getColumn()
	{
		return this.column;
	}

	public String toString()
	{
		return "[" + row + "," + column + "]";
	}
}
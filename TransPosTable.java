import java.io.Serializable;
import java.util.Hashtable;

public class TransPosTable implements Serializable
{
	private static final long serialVersionUID = 1L;
	private Hashtable<String,TransPosTableEntry> transTable;
	//private Board prevBoard = null;
	
	public TransPosTable()
	{
		transTable = new Hashtable<String,TransPosTableEntry>();
	}
	
	public int size()
	{
		return transTable.size();
	}
	
	private void adjustEval(TransPosTableEntry temp)
	{
		temp.updateEval(((temp.getWinCount()*7) - (temp.getLossCount()*7)) + temp.getEval());
	}
	
	//Assuming a true value means whiteWins, false value means black wins: assuming true value passed in for whiteMove means
	//the Entry being evaluated came from 
	public void endGameUpdates(TransPosTableEntry newEntry, boolean whiteWon, boolean whiteMove)
	{
		//System.out.println("END GAME UPDATES"+transTable.size());
		if(whiteWon)
		{
			if(!transTable.contains(newEntry.getBoard().getCurrZobrist()))
			{
				if(whiteMove)
				{
					newEntry.incrementWinCount();
					adjustEval(newEntry);
					transTable.put(newEntry.getBoard().getCurrZobrist(),newEntry);
				}
				else
				{
					newEntry.incrementLossCount();
					adjustEval(newEntry);
					transTable.put(newEntry.getBoard().getCurrZobrist(),newEntry);
				}
			}	
			else//Check whether depth of new entry is greater then entry already present
			{
				if(whiteMove)
				{
					if(transTable.get(newEntry.getBoard().getCurrZobrist()).getDepth() < newEntry.getDepth())
					{
						newEntry.incrementWinCount();
						adjustEval(newEntry);
						transTable.put(newEntry.getBoard().getCurrZobrist(),newEntry);
					}
					else if(transTable.get(newEntry.getBoard().getCurrZobrist()).getDepth() >= newEntry.getDepth())
					{
						transTable.get(newEntry.getBoard().getCurrZobrist()).incrementWinCount();
						adjustEval(transTable.get(newEntry.getBoard().getCurrZobrist()));
					}
				}
				else
				{
					if(transTable.get(newEntry.getBoard().getCurrZobrist()).getDepth() < newEntry.getDepth())
					{
						newEntry.incrementLossCount();
						adjustEval(newEntry);
						transTable.put(newEntry.getBoard().getCurrZobrist(),newEntry);
					}
					else if(transTable.get(newEntry.getBoard().getCurrZobrist()).getDepth() >= newEntry.getDepth())
					{
						
						transTable.get(newEntry.getBoard().getCurrZobrist()).incrementLossCount();
						adjustEval(transTable.get(newEntry.getBoard().getCurrZobrist()));
					}
				}
			}
		}
		else
		{
			if(!transTable.contains(newEntry.getBoard().getCurrZobrist()))
			{
				if(whiteMove)
				{
					newEntry.incrementLossCount();
					adjustEval(newEntry);
					transTable.put(newEntry.getBoard().getCurrZobrist(),newEntry);
				}
				else
				{
					newEntry.incrementWinCount();
					adjustEval(newEntry);
					transTable.put(newEntry.getBoard().getCurrZobrist(),newEntry);
				}
			}	
			else//Check whether depth of new entry is greater then entry already present
			{
				if(whiteMove)
				{
					if(transTable.get(newEntry.getBoard().getCurrZobrist()).getDepth() < newEntry.getDepth())
					{
						newEntry.incrementLossCount();
						adjustEval(newEntry);
						transTable.put(newEntry.getBoard().getCurrZobrist(),newEntry);
					}
					else if(transTable.get(newEntry.getBoard().getCurrZobrist()).getDepth() >= newEntry.getDepth())
					{
						transTable.get(newEntry.getBoard().getCurrZobrist()).incrementLossCount();
						adjustEval(transTable.get(newEntry.getBoard().getCurrZobrist()));
					}
				}
				else
				{
					if(transTable.get(newEntry.getBoard().getCurrZobrist()).getDepth() < newEntry.getDepth())
					{
						newEntry.incrementWinCount();
						adjustEval(newEntry);
						transTable.put(newEntry.getBoard().getCurrZobrist(),newEntry);
					}
					else if(transTable.get(newEntry.getBoard().getCurrZobrist()).getDepth() >= newEntry.getDepth())
					{
						transTable.get(newEntry.getBoard().getCurrZobrist()).incrementWinCount();
						adjustEval(transTable.get(newEntry.getBoard().getCurrZobrist()));
					}
				}
			}
		}
	}
	
	public boolean contains(String key)
	{
		return transTable.containsKey(key);
	}
	
	
	public TransPosTableEntry retrieveEntry(String key)
	{
		return transTable.get(key);
	}
	
}

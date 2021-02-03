public class GameClock
{
	private long millisLeft;
	private long startTime;
	private boolean timerTicking;

	public GameClock(long seconds)
	{
		millisLeft = seconds * 1000;
		timerTicking = false;
	}

	public void start()
	{
		if(!timerTicking)
		{
			startTime = System.currentTimeMillis();
			timerTicking = true;
		}
	}

	public void pause()
	{
		if(timerTicking)
		{
			long elapsed = System.currentTimeMillis() - startTime;
			millisLeft -= elapsed;
			timerTicking = false;
		}
	}

	public long millisRemaining()
	{
		if(timerTicking)
		{
			long elapsed = System.currentTimeMillis() - startTime;
			return millisLeft - elapsed;
		}
		else
			return millisLeft;
	}
}

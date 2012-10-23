package gameLoader;

@SuppressWarnings("serial")
public class EndGameException extends Exception{
	public EndGameException()
	{
		super();
	}
	public EndGameException(String string) {
		super(string);
	}
}

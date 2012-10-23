package gameLoader;

@SuppressWarnings("serial")
public class EndGameException extends Exception{

	String msg;
	public EndGameException(String string) {
		msg = string;
	}
	
	public String getMessage()
	{
		return msg;
	}
}

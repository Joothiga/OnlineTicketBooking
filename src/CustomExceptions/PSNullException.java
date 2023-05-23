package CustomExceptions;

@SuppressWarnings("serial")
public class PSNullException extends NullPointerException
{
	public PSNullException()
	{
		super("Check your DB Credentials and DB Syntax");
	}
	
	public PSNullException(String msg)
	{
		super("Check your DB Credentials and DB Syntax");
	}
}

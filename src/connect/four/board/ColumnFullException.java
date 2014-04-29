package connect.four.board;

/**
 * Extension of IndexOutOfBoundsException which indicates that a method
 * attempted to add a piece to an already full column of a board.
 * 
 * @author Moore, Zachary
 * 
 */
@SuppressWarnings("serial")
public class ColumnFullException extends IndexOutOfBoundsException
{
	/**
	 * Constructs a ColumnFullException object with the specified message.
	 * 
	 * @param message Exception message
	 */
	public ColumnFullException(String message)
	{
		super(message);
	}
	
	/**
	 * Constructs a ColumnFullException object with the default message.
	 */
	public ColumnFullException()
	{
		super("Played in a full column.");
	}
}

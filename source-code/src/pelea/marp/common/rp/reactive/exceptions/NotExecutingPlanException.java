package pelea.marp.common.rp.reactive.exceptions;

public class NotExecutingPlanException extends GeneralException {
	
	private static final long serialVersionUID = -4427713323458858212L;

	public NotExecutingPlanException(String message) {
        super(message);
        name_exception = "NotExecutingPlanException";
    }
}

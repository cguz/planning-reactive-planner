package pelea.marp.common.rp.reactive.exceptions;

public class NoStatisticException extends GeneralException {
	
	private static final long serialVersionUID = -2686457418404768719L;

	public NoStatisticException(String message) {
        super(message);
        name_exception = "NoStatisticException";
    }
}

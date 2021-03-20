package pelea.marp.common.rp.reactive.exceptions;

public class NoSearchSpaceException extends GeneralException {
	
	private static final long serialVersionUID = -2686457418404768719L;

	public NoSearchSpaceException(String message) {
        super(message);
        name_exception = "NoSearchSpaceException";
    }
}

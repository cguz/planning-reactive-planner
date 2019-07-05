package pelea.marp.common.rp.reactive.exceptions;

public class NotInitializedException extends GeneralException {

	private static final long serialVersionUID = -7733253792526124026L;

	public NotInitializedException(String message) {
        super(message);
        name_exception = "NotInitializedException";
    }
}

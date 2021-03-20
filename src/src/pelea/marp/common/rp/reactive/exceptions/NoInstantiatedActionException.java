package pelea.marp.common.rp.reactive.exceptions;

public class NoInstantiatedActionException extends GeneralException {
	
	private static final long serialVersionUID = -7733253792526124026L;

	public NoInstantiatedActionException(String message) {
        super(message);
        name_exception = "NoInstantiatedActionException";
    }
}

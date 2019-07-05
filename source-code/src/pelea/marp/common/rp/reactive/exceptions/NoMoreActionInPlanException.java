package pelea.marp.common.rp.reactive.exceptions;

public class NoMoreActionInPlanException extends GeneralException {

	private static final long serialVersionUID = -7733253792526124026L;

	public NoMoreActionInPlanException(String message) {
        super(message);
        name_exception = "NoMoreActionInPlanException";
    }
}

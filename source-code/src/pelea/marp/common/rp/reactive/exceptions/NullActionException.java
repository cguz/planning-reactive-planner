package pelea.marp.common.rp.reactive.exceptions;

public class NullActionException extends GeneralException {

	private static final long serialVersionUID = 7077472932797737013L;

	public NullActionException(String message) {
		super(message);
		
		name_exception = message;
	}

}

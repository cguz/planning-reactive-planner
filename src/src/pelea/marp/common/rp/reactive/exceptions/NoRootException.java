package pelea.marp.common.rp.reactive.exceptions;

public class NoRootException extends GeneralException {
	
	private static final long serialVersionUID = 3588542335309683922L;

	public NoRootException(String message) {
        super(message);
        name_exception = "NoRootException";
    }
}

package pelea.marp.common.rp.reactive.exceptions;

public class BadProblemDefinitionException extends GeneralException {

	private static final long serialVersionUID = -3310184142965184365L;
	
	public BadProblemDefinitionException(String message) {
        super(message);
        name_exception = "BadProblemDefinitionException";
    }
}

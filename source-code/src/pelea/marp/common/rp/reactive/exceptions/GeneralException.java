package pelea.marp.common.rp.reactive.exceptions;

public class GeneralException extends Exception {

	private static final long serialVersionUID = -3310184142965184365L;
	
	protected String name_exception = "GeneralException";
	
	public GeneralException(String message) {
        super(message);
    }
	
	@Override
	public String toString() {
		return "["+name_exception+"]"+this.getMessage();
	}
	
	public void printStackTrace() {
	    System.out.println(toString());
	}
}

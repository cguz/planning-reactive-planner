package pelea.marp.common.rp.reactive.exceptions;

public class NoPosiblePlanException extends GeneralException {
	
	private static final long serialVersionUID = -2686457418404768719L;

	public NoPosiblePlanException(String message) {
        super(message);
        name_exception = "NO-POSSIBLE-PLAN-EXCEPTION";
    }
}

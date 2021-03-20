package pelea.marp.service.rp.reactive.shared.pddl;

import pddl4j.state.Atom;
import pelea.marp.common.rp.reactive.shared.pddl.SharedFluent;
import pelea.marp.common.rp.reactive.shared.pddl.SharedVariable;

public class SharedFluentImp implements SharedFluent {

	private static final long serialVersionUID = 743400689009815281L;
	
	private SharedVariable variable;
	private String value;
	private int condition;
	
	protected String string;
	

	public SharedFluentImp(SharedVariable variable, String value, int condition) {
		this.variable = variable;
		this.value = value;
		this.condition = condition;
	}

	@Override
	public SharedVariable getVariable() {
		return variable;
	}

	@Override
	public String getValue() {
		return value;
	}
	
	@Override
	public int getCondition() {
		return condition;
	}
	
	@Override
    public String toString() {
    	if (string != null) return string;
    	
    	string = "";
        
        string += "<"+variable.toPddl();
        if(this.getCondition() == EQUAL)       			string += ",";
        else if(this.getCondition() == DISTINCT)    	string += ")<>";
        else if(this.getCondition() == ASSIGN)    		string += ",";
        else if(this.getCondition() == INCREASE)     	string += ")increase ";
        else if(this.getCondition() == DECREASE)    	string += ")decrease ";
        else if(this.getCondition() == MORE_EQUAL)   	string += ")>=";
        else if(this.getCondition() == LESS_EQUAL)   	string += ")<=";
        else if(this.getCondition() == MORE)       		string += ")>";
        else if(this.getCondition() == LESS)    		string += ")<";
        else if(this.getCondition() == NOT_MORE_EQUAL)  string += ") not >= ";
        else if(this.getCondition() == NOT_LESS_EQUAL)  string += ") not <= ";
        else if(this.getCondition() == NOT_MORE)       	string += ") not > ";
        else if(this.getCondition() == NOT_LESS)    	string += ") not < ";
        string += value+">";

        return string;
    }
	
	@Override
	public int hashCode() {
		return toString().hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if( obj instanceof Atom) { 
			return equalsToAtom((Atom) obj);
		}
		return toString().equals(((SharedFluent)obj).toString());
	}

	private boolean equalsToAtom(Atom obj) {
		
		if(getVariable().equals(obj)){
			if(value.equals(determinatingValue(obj, true)))
				return true;
		}
		
		return false;
	}

	private String determinatingValue(Atom v, boolean realValue) {
		
		String atr; 
		String s_var = toString().toLowerCase().trim();
		int index=0;
		for (int i = 0; i < v.getAtributos().size();++i) {
			atr = v.getAtributos().get(i);
			if(s_var.contains("-"+atr)){
				++index;
				s_var = s_var.replace("-"+atr, "");
			}
		}
		
		if(index==v.getAtributos().size()){
			if(realValue) { 
				return value.replaceAll("false", "true").replaceAll("not", "yes");
			}
			return value;
		}else{
			if(index==0)
				return v.getAtributos().get(0);
			return v.getAtributos().get(v.getAtributos().size()-1);
		}
	}
}

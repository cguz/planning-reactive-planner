package pelea.marp.service.rp.planning_task.pddl;

import pelea.marp.common.rp.planning_task.pddl.Fluent;
import pelea.planning_task.common.pddl.DSCondition;

public class FluentImp implements Fluent {

	private int index_fluent;
	private int index_var;
	private String var_name;

	private int condition;
	private String value;
	
	protected String string;
	

	public FluentImp(int index_fluent, int variable, String variable_s, String value, int condition) {
		this.index_fluent = index_fluent;
		this.index_var = variable;
		this.var_name = variable_s;
		this.value = value;
		this.condition = condition;
	}

	@Override
	public int getIndexVariable() {
		return index_var;
	}

	@Override
	public String getValue() {
		return value;
	}
	
	@Override
	public int getIndex_fluent() {
		return index_fluent;
	}

	@Override
	public int getCondition() {
		return condition;
	}
	
	@Override
    public String toString() {
    	if (string != null) return string;
    	
    	string = "";
        
        string += "<"+var_name;
        if(this.getCondition() == DSCondition.EQUAL) 				string += ",";
        else if(this.getCondition() == DSCondition.DISTINCT)    	string += ")<>";
        else if(this.getCondition() == DSCondition.ASSIGN)    		string += ",";
        else if(this.getCondition() == DSCondition.INCREASE)     	string += ")increase ";
        else if(this.getCondition() == DSCondition.DECREASE)    	string += ")decrease ";
        else if(this.getCondition() == DSCondition.MORE_EQUAL)   	string += ")>=";
        else if(this.getCondition() == DSCondition.LESS_EQUAL)   	string += ")<=";
        else if(this.getCondition() == DSCondition.MORE)       		string += ")>";
        else if(this.getCondition() == DSCondition.LESS)    		string += ")<";
        else if(this.getCondition() == DSCondition.NOT_MORE_EQUAL)  string += ") not >= ";
        else if(this.getCondition() == DSCondition.NOT_LESS_EQUAL)  string += ") not <= ";
        else if(this.getCondition() == DSCondition.NOT_MORE)       	string += ") not > ";
        else if(this.getCondition() == DSCondition.NOT_LESS)    	string += ") not < ";
        string += value+">";

        return string;
    }
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Fluent)) return false;
		return index_var==((Fluent)obj).getIndexVariable() 
			&& condition==((Fluent)obj).getCondition()
			&& value.equals(((Fluent)obj).getValue());
	}

	@Override
	public int hashCode() {
		final int prime = 31;
        int result = 1;
        result = prime * result + index_var + condition + value.hashCode();  
		return result;
	}
}

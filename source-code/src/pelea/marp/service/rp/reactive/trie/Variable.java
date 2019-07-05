package pelea.marp.service.rp.reactive.trie;

import pelea.planning_task.common.pddl.DSCondition;
import pelea.planning_task.common.pddl.DSVariables;



/**
 * @author Cesar Guzman Alvarez
 * @description represents a state variable
 */
public class Variable implements Comparable<Variable>{
	DSCondition var;		// represents a tuple <DSVariables,value>
	
	public Integer 	indexAlphabet;
	
	// variable as object
	DSVariables 	variable;
	
	// index of the variable
	public int 		indexVar;
	
	// index of the current value of the variable
	public int 		indexValue;

	public Variable(Integer index, DSCondition var) {
		this.indexVar=index;
		this.var=var;
		
		variable=var.getFunction();
		if(variable.getInitialTrueValue().equals(var.getValue()))
			indexValue=0; // represents the initialTrueValue
		
		int i=0;
		for(i=0;i<variable.getInitialFalseValues().size();++i)
			if(variable.getInitialFalseValues().get(i).equals(var.getValue()))
				break;
		
		indexValue = i+1;
	}

	public Variable(Integer in_v, DSVariables variable, int in_value) {
		this.indexVar	=in_v;
		this.variable	=variable;
		indexValue		=in_value;
	}

	public Variable(Integer position, Integer in_v, DSVariables variable, int in_value) {
		this.indexVar	=in_v;
		this.variable	=variable;
		indexValue		=in_value;
		indexAlphabet	=position;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj instanceof Variable && this.indexVar == ((Variable)obj).indexVar && this.indexValue == ((Variable)obj).indexValue)
			return true;
		return super.equals(obj);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		/**/String value="";
		if(indexValue==0)
			value = variable.getInitialTrueValue();
		else
			value = variable.getInitialFalseValues().get(indexValue-1);
		
		return "["+indexAlphabet+"<" + variable.toString()+","+value+">]";
		// return "["+indexAlphabet+"<" + indexVar+","+indexValue+">]";
	}

	@Override
	public int compareTo(Variable o) {
		return this.indexAlphabet-o.indexAlphabet;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		int result = 17;
        result = 37*result + (int)indexVar;
        result = 37*result + (int)indexValue;
        
		return result;
	}
}

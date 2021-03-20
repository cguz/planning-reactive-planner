package pelea.marp.service.rp.reactive.shared.pddl;

import java.util.ArrayList;

import pddl4j.state.Atom;
import pelea.marp.common.rp.reactive.shared.pddl.SharedVariable;

/**
 * @author cguzman
 * 
 */
public class SharedVariableImp implements SharedVariable {

	private static final long serialVersionUID = 781930027696836889L;
	
	private String name;

	private ArrayList<String> params;

	private ArrayList<String> reached_values;

	private ArrayList<String> value_types;
	
	
	protected String string;

	
	public SharedVariableImp(String name, ArrayList<String> params, ArrayList<String> reached_values, ArrayList<String> value_types) {
		this.name = name;
		this.params = params;
		this.reached_values = reached_values;
		this.value_types = value_types;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public ArrayList<String> getParams() {
		return params;
	}

	@Override
	public ArrayList<String> getReachableValues() {
		return reached_values;
	}

	@Override
	public ArrayList<String> getValueTypes() {
		return value_types;
	}

	@Override
	public String toString() {
		if(this.string != null) return this.string;
        
    	this.string = "";  // type of condition
        int n = 0;
        
        if(this.getParams().size() > 0 ){
            this.string += this.getName() + "-";
            for(String s: this.getParams()) {
                if(n == 0) n++; else this.string += "-";
                this.string += s;
            }
        }else{
            this.string += this.getName();        	
        }
        
        this.string += reached_values.toString();
        
        return this.string;
	}
	
	@Override
	public String toPddl() {
		
    	String string = ""; 
        int n = 0;

        if(this.getParams().size() > 0 ){
	        string += this.getName() + "-";
	        for(String s: this.getParams()) {
	            if(n == 0) n++; else string += "-";
	            string += s;
	        }
        }else{
	        string += this.getName();        	
        }
        
        return string;
	}

	@Override
	public boolean equals(Object obj) {
		
		if( obj instanceof Atom) 
			return containsVariable((Atom) obj);
		
		return super.equals(obj);
	}

	private boolean containsVariable(Atom v) { 
		
		return  v.toString().replaceAll(" ", "-").toLowerCase().trim().contains(toPddl());
		
	}
}

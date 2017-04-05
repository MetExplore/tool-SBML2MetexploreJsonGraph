package phnmnl.sbml.converter;

import org.sbml.jsbml.Model;

import com.google.gson.JsonObject;

public abstract class AbstractConverter {
	
	protected Model model;

	public AbstractConverter(){}
	
	public AbstractConverter(Model m){
		this.model=m;
		
	}

	public abstract JsonObject convert();
	
}

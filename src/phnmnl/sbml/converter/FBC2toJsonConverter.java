package phnmnl.sbml.converter;

import org.sbml.jsbml.Model;
import org.sbml.jsbml.Reaction;

import com.google.gson.JsonObject;

public class FBC2toJsonConverter extends AbstractConverter {
	
	
	public FBC2toJsonConverter(Model m){
		super(m);
	}

	@Override
	protected JsonObject createReactionNode(Reaction jSBMLReaction) {
		// TODO Auto-generated method stub
		return null;
	}
}

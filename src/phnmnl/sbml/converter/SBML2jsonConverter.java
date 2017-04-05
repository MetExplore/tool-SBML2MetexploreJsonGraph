package phnmnl.sbml.converter;

import org.sbml.jsbml.Model;
import org.sbml.jsbml.Reaction;

import com.google.gson.JsonObject;

public class SBML2jsonConverter extends AbstractConverter {

	public SBML2jsonConverter(Model m){
		super (m);
	}

	@Override
	protected JsonObject createReactionNode(Reaction jSBMLReaction) {
		// TODO Auto-generated method stub
		return null;
	}
	
}

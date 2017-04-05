package phnmnl.sbml.converter;

import org.sbml.jsbml.Model;
import org.sbml.jsbml.Reaction;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public abstract class AbstractConverter {
	
	protected Model model;
	protected JsonObject json;
	
	protected JsonArray nodes, links;

	public AbstractConverter(){}
	
	public AbstractConverter(Model m){
		this.model=m;
		
	}

	public void convert(){
		
	}
	
	public void parseReaction(){
		for(Reaction jSBMLReaction: model.getListOfReactions()){
			
			JsonObject reactionNode=this.createReactionNode(jSBMLReaction);
			nodes.add(reactionNode);
			int reactionindex=nodes.size()-1;
			
			//TODO look in jsbml if index of reactions and metabolites can be retrieved from somewhere
			
		}
	}
	
	protected abstract JsonObject createReactionNode(Reaction jSBMLReaction);

	public void writeJsonToFile(String outputFileName){
		
	}
	
}

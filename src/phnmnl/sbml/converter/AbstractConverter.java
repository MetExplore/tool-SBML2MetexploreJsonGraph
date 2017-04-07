package phnmnl.sbml.converter;

import org.sbml.jsbml.Model;
import org.sbml.jsbml.Reaction;
import org.sbml.jsbml.Species;
import org.sbml.jsbml.SpeciesReference;

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
			
			/*
			 * Reactant list => "In" links
			 */
			for(SpeciesReference sref:jSBMLReaction.getListOfReactants() ){
				Species s=sref.getSpeciesInstance();
				
				// TODO generate notes containing the index values for metabolites
				
			}
			
			/*
			 * Product list => "out" links
			 */
			for(SpeciesReference sref:jSBMLReaction.getListOfProducts() ){
				Species s=sref.getSpeciesInstance();
				
				
			}
			
			
			
			
		}
	}
	
	protected abstract JsonObject createReactionNode(Reaction jSBMLReaction);

	public void writeJsonToFile(String outputFileName){
		
	}
	
}

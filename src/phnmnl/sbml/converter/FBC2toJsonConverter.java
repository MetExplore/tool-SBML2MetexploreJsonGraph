package phnmnl.sbml.converter;

import javax.xml.stream.XMLStreamException;

import org.sbml.jsbml.Model;
import org.sbml.jsbml.Reaction;
import org.sbml.jsbml.Species;
import org.sbml.jsbml.ext.fbc.FBCReactionPlugin;
import org.sbml.jsbml.ext.fbc.FBCSpeciesPlugin;

import com.google.gson.JsonObject;


public class FBC2toJsonConverter extends AbstractConverter {
	
	
	public FBC2toJsonConverter(Model m){
		super(m);
	}

	@Override
	protected JsonObject createReactionNode(Reaction r) {
		
		JsonObject rxn=new JsonObject();
		
		rxn.addProperty("biologicalType","reaction");
		rxn.addProperty("name", r.getName());
		rxn.addProperty("dbIdentifier", r.getId());
		rxn.addProperty("reactionReversibility",r.getReversible());
		
		try {
			this.getAdditionalInfoInNote(rxn, r);
		} catch (XMLStreamException e) {
			e.printStackTrace();
		}
		
		
		FBCReactionPlugin rfbc=(FBCReactionPlugin)r.getPlugin("fbc");
		
		if(rfbc.isSetLowerFluxBound()){
			JsonObject lb=new JsonObject();
			lb.addProperty("node",  r.getId());
			lb.addProperty("value",  rfbc.getLowerFluxBoundInstance().getValue());
			this.mincdt.get("data").getAsJsonArray().add(lb);
		}
		if(rfbc.isSetUpperFluxBound()){
			JsonObject lb=new JsonObject();
			lb.addProperty("node",  r.getId());
			lb.addProperty("value",  rfbc.getUpperFluxBoundInstance().getValue());
			this.maxcdt.get("data").getAsJsonArray().add(lb);
		}		
		
		return rxn;
	}

	@Override
	protected JsonObject createMetaboliteNode(Species s) {
		JsonObject met=new JsonObject();
		
		met.addProperty("biologicalType","metabolite");
		met.addProperty("name",s.getName());
		met.addProperty("dbIdentifier",s.getId());
		met.addProperty("compartment", s.getCompartmentInstance().getName());
		
		met.addProperty("isSideCompound", false);
		
		FBCSpeciesPlugin sfbc=(FBCSpeciesPlugin)s.getPlugin("fbc");
		
		if(sfbc.isSetCharge()) 
			met.addProperty("charge", String.valueOf(sfbc.getCharge()));
		if(sfbc.isSetChemicalFormula())
			met.addProperty("formula", sfbc.getChemicalFormula());
		
		met.addProperty("svg","");
		met.addProperty("svgWidth","0");
		met.addProperty("svgHeight","0");
		
		return met;
	}
}

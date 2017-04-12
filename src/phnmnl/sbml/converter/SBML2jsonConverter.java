package phnmnl.sbml.converter;

import javax.xml.stream.XMLStreamException;

import org.sbml.jsbml.LocalParameter;
import org.sbml.jsbml.Model;
import org.sbml.jsbml.Reaction;
import org.sbml.jsbml.Species;

import com.google.gson.JsonObject;

public class SBML2jsonConverter extends AbstractConverter {

	public SBML2jsonConverter(Model m){
		super (m);
	}

	@Override
	public JsonObject createReactionNode(Reaction r) {
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
		
		if(r.isSetKineticLaw()){
			for(LocalParameter p: r.getKineticLaw().getListOfLocalParameters()){
				if(p.getId().equalsIgnoreCase("UPPER_BOUND")){
					JsonObject ub=new JsonObject();
					ub.addProperty("node",  r.getId());
					ub.addProperty("value", p.getValue());
					
					this.maxcdt.get("data").getAsJsonArray().add(ub);
				}else if(p.getId().equalsIgnoreCase("LOWER_BOUND")){
					JsonObject lb=new JsonObject();
					lb.addProperty("node",  r.getId());
					lb.addProperty("value", p.getValue());
					this.mincdt.get("data").getAsJsonArray().add(lb);
				}
			}
			
		}
		
		
		return rxn;
	}

	@Override
	public JsonObject createMetaboliteNode(Species s) {
		JsonObject met=new JsonObject();
		
		met.addProperty("biologicalType","metabolite");
		met.addProperty("name",s.getName());
		met.addProperty("dbIdentifier",s.getId());
		met.addProperty("compartment", s.getCompartmentInstance().getName());
		
		met.addProperty("isSideCompound", false);
		
		try {
			this.getAdditionalInfoInNote(met, s);
		} catch (XMLStreamException e) {
			e.printStackTrace();
		}
		
		met.addProperty("svg","");
		met.addProperty("svgWidth","0");
		met.addProperty("svgHeight","0");
		
		return met;
	}
	
}

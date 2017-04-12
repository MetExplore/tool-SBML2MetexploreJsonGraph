package phnmnl.tests.utils;

import org.sbml.jsbml.Compartment;
import org.sbml.jsbml.KineticLaw;
import org.sbml.jsbml.LocalParameter;
import org.sbml.jsbml.Model;
import org.sbml.jsbml.Reaction;
import org.sbml.jsbml.Species;
import org.sbml.jsbml.SpeciesReference;

import com.google.gson.JsonObject;

public class Lvl2Dummy implements Dummy {
	
	private Model model;
	private Reaction rxn;
	private Species s;
	

	private static final int nbReactions = 1;
	private static final int nbMetabolites = 1;
	private static final int nbCompartments = 1;


	private JsonObject json;
	private static final int nbNodes = 2;
	private static final int nbLinks = 1;
	private static final boolean hasMappings = true;
	private static final int[] nbMappings = { 1, 1 };
	
	private static final boolean isFbc = false;
	
	public Lvl2Dummy(){
		this.initSBML();
	}


	@Override
	public void initSBML() {
		
		this.setModel(new Model(2, 4));
		
		Reaction r=new Reaction();
		r.setId("reactionID");
		r.setName("reactionName");
		r.setReversible(true);
		
		KineticLaw k = r.createKineticLaw();
		LocalParameter lb = k.createLocalParameter("LOWER_BOUND");
		lb.setValue(0.0);
		LocalParameter ub = k.createLocalParameter("UPPER_BOUND");
		ub.setValue(0.0);
		
		this.model.addReaction(r);
		
		Species s=new Species();
		s.setId("metID");
		s.setName("metName");
		
		this.model.addSpecies(s);
		this.model.addCompartment(new Compartment("testCmpt","testCmpt", 2, 4));
		
		s.setCompartment("testCmpt");
		
		r.addReactant(new SpeciesReference(s));
		
		this.setSpecies(s);
		this.setRxn(r);
		
	}

	@Override
	public Reaction getRxn() {
		return rxn;
	}

	@Override
	public void setRxn(Reaction rxn) {
		this.rxn = rxn;
	}

	@Override
	public Species getSpecies() {
		return s;
	}

	@Override
	public void setSpecies(Species s) {
		this.s = s;
	}

	@Override
	public Model getModel() {
		return this.model;
	}

	@Override
	public void setModel(Model model) {
		this.model=model;
	}


	@Override
	public boolean isFbc() {
		return isFbc;
	}


	@Override
	public int getNbReactions() {
		return nbReactions;
	}


	@Override
	public int getNbMetabolites() {
		return nbMetabolites;
	}


	@Override
	public int getNbCompartments() {
		return nbCompartments;
	}


	@Override
	public void testModel() {
		return;		
	}


	@Override
	public String getInputFile() {
		return null;
	}


	@Override
	public String getOutputFile() {
		return null;
	}


	@Override
	public int getNbNodes() {
		
		return nbNodes;
	}


	@Override
	public int getNbLinks() {
		return nbLinks;
	}


	@Override
	public boolean getHasMappings() {
		return hasMappings;
	}


	@Override
	public int getNbMappingsCdt() {
		return nbMappings.length;
	}


	@Override
	public int getNbMappingsData(int i) {
		
		return nbMappings[i];
	}


	@Override
	public void testJson() {
		return;
	}


	@Override
	public JsonObject getJson() {
		return this.json;
	}


	@Override
	public void setJson(JsonObject j) {
		this.json=j;
		
	}

}

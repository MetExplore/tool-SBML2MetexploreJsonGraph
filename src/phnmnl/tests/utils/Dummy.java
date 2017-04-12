package phnmnl.tests.utils;

import org.sbml.jsbml.Model;
import org.sbml.jsbml.Reaction;
import org.sbml.jsbml.Species;

public interface Dummy {

	public void initSBML();
	
	public Model getModel();

	public void setModel(Model model);

	Reaction getRxn();

	void setRxn(Reaction rxn);

	Species getSpecies();

	void setSpecies(Species s);
	
	public boolean isFbc();
}

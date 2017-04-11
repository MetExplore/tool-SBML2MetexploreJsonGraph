package phnmnl.tests.utils;

import org.sbml.jsbml.Model;

public interface Dummy {
	

	public int getNbReactions();

	public int getNbMetabolites();

	public int getNbCompartments();

	public String getInputFile();

	public String getOutputFile();

	public void testModel(Model m);
	
	public boolean isFbc();
	
}

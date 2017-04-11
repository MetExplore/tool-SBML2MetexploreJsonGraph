package phnmnl.tests.utils;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.sbml.jsbml.Model;

public class MiniRec2Dummy implements Dummy {
	
	private static final boolean isFbc = false;
	private static final  int nbReactions=1;
	private static final  int nbMetabolites=5;
	private static final  int nbCompartments=1;
	
	private static final  String inputFile="tests/inputData/miniRec2.xml";
	private static final  String outputFile="tests/outputData/ExpectedminiRec2.json";

		
	
	@Test
	public void testModel(Model m){
		assertEquals("Incorrect Number of compartments", this.getNbCompartments(), m.getNumCompartments());
		assertEquals("Incorrect Number of metabolites", this.getNbMetabolites(), m.getNumSpecies());
		assertEquals("Incorrect Number of reactions", this.getNbReactions(), m.getNumReactions());
	}

	@Override
	public int getNbReactions() {
		return MiniRec2Dummy.nbReactions;
	}

	@Override
	public int getNbMetabolites() {
		return MiniRec2Dummy.nbMetabolites;
	}

	@Override
	public int getNbCompartments() {
		return MiniRec2Dummy.nbCompartments;
	}

	@Override
	public String getInputFile() {
		return MiniRec2Dummy.inputFile;
	}

	@Override
	public String getOutputFile() {
		return MiniRec2Dummy.outputFile;
	}

	@Override
	public boolean isFbc() {
		return MiniRec2Dummy.isFbc;
	}
}

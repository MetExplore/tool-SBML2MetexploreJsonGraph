package phnmnl.tests.utils;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.sbml.jsbml.Model;

public class UbFluxNEtDummy implements Dummy {

	private static final boolean isFbc = true;
	private static final int nbReactions = 19;
	private static final int nbMetabolites = 15;
	private static final int nbCompartments = 1;

	private static final String inputFile = "tests/inputData/ubFluxNet.sbml";
	private static final String outputFile = "tests/outputData/ExpectedUbFluxNet.sbml";

	@Test
	public void testModel(Model m) {
		assertEquals("Incorrect Number of compartments", this.getNbCompartments(), m.getNumCompartments());
		assertEquals("Incorrect Number of metabolites", this.getNbMetabolites(), m.getNumSpecies());
		assertEquals("Incorrect Number of reactions", this.getNbReactions(), m.getNumReactions());
	}

	@Override
	public int getNbReactions() {
		return UbFluxNEtDummy.nbReactions;
	}

	@Override
	public int getNbMetabolites() {
		return UbFluxNEtDummy.nbMetabolites;
	}

	@Override
	public int getNbCompartments() {
		return UbFluxNEtDummy.nbCompartments;
	}

	@Override
	public String getInputFile() {
		return UbFluxNEtDummy.inputFile;
	}

	@Override
	public String getOutputFile() {
		return UbFluxNEtDummy.outputFile;
	}

	@Override
	public boolean isFbc() {
		return UbFluxNEtDummy.isFbc;
	}
}

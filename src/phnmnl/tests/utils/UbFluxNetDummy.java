package phnmnl.tests.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.sbml.jsbml.Model;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class UbFluxNetDummy implements Dummy {

	private Model model;
	private static final boolean isFbc = true;
	private static final int nbReactions = 19;
	private static final int nbMetabolites = 15;
	private static final int nbCompartments = 1;

	private static final String inputFile = "tests/inputData/ubFluxNet.sbml";
	private static final String outputFile = "tests/outputData/ExpectedUbFluxNet.sbml";

	private JsonObject json;
	private static final int nbNodes = 34;
	private static final int nbLinks = 41;
	private static final  boolean hasMappings=true;
	private static final int[] nbMappings = { 19, 19 };

	@Override
	public Model getModel() {
		return this.model;
	}

	@Override
	public void setModel(Model model) {
		this.model=model;
	}
	
	@Test
	public void testModel() {
		Model m=this.getModel();
		assertEquals("Incorrect Number of compartments", this.getNbCompartments(), m.getNumCompartments());
		assertEquals("Incorrect Number of metabolites", this.getNbMetabolites(), m.getNumSpecies());
		assertEquals("Incorrect Number of reactions", this.getNbReactions(), m.getNumReactions());
	}

	@Override
	public int getNbReactions() {
		return UbFluxNetDummy.nbReactions;
	}

	@Override
	public int getNbMetabolites() {
		return UbFluxNetDummy.nbMetabolites;
	}

	@Override
	public int getNbCompartments() {
		return UbFluxNetDummy.nbCompartments;
	}

	@Override
	public String getInputFile() {
		return UbFluxNetDummy.inputFile;
	}

	@Override
	public String getOutputFile() {
		return UbFluxNetDummy.outputFile;
	}

	@Override
	public boolean isFbc() {
		return UbFluxNetDummy.isFbc;
	}

	@Override
	public JsonObject getJson() {
		return this.json;
	}

	@Override
	public void setJson(JsonObject j) {
		this.json=j;
	}

	@Override
	@Test
	public void testJson() {

		JsonObject j=this.getJson();
		
		assertTrue("No nodes in json", j.has("nodes"));
		if (j.has("nodes"))
			assertEquals("Incorrect Number of nodes", this.getNbNodes(), j.get("nodes").getAsJsonArray().size());

		assertTrue("No links in json", j.has("links"));
		if (j.has("links"))
			assertEquals("Incorrect Number of links", this.getNbLinks(), j.get("links").getAsJsonArray().size());

		assertEquals("No Nodes in json", this.getHasMappings(), j.has("mappingdata"));
		if (j.has("mappingdata")) {

			JsonArray mappings = j.get("mappingdata").getAsJsonArray();
			assertEquals("Wrong Number of Mappings", 1, mappings.size());

			if (mappings.size() == 1) {
				JsonObject m = mappings.get(0).getAsJsonObject();

				assertTrue("No name in Mapping n°1", m.has("name"));
				if (j.has("name"))
					assertEquals("Wrong Name of Mapping n°1", "Flux", m.get("name").getAsString());

				assertTrue("No TargetLabel in Mapping n°1", m.has("targetLabel"));
				if (j.has("targetLabel"))
					assertEquals("Wrong Name of Mapping n°1", "reactionId", m.get("targetLabel").getAsString());

				assertTrue("No condition in Mapping n°1", m.has("mappings"));
				if (j.has("mappings")){
					
					int n=this.getNbMappingsCdt();
					assertEquals("Wrong number of conditions in Mapping n°1", n, m.get("mappings").getAsJsonArray().size());
					if(m.get("mappings").getAsJsonArray().size()==n){
						for (int i=0; i<n ; i++){
							JsonObject c=m.get("mappings").getAsJsonArray().get(i).getAsJsonObject();
							
							assertTrue("No data in condition "+i+" of Mapping n°1", c.has("data"));
							if(c.has("data")){
								
							}
						}
					}
				}
			}
		}

	}

	@Override
	public int getNbNodes() {
		return UbFluxNetDummy.nbNodes;
	}

	@Override
	public int getNbLinks() {
		return UbFluxNetDummy.nbLinks;
	}

	@Override
	public boolean getHasMappings() {
		return UbFluxNetDummy.hasMappings;
	}

	@Override
	public int getNbMappingsCdt() {
		return UbFluxNetDummy.nbMappings.length;
	}

	@Override
	public int getNbMappingsData(int i) {
		return UbFluxNetDummy.nbMappings[i];
	}
}

package phnmnl.tests.utils;

import static org.junit.Assert.*;

import org.junit.Test;
import org.sbml.jsbml.Model;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class MiniRec2Dummy implements Dummy {

	private Model model;
	private static final boolean isFbc = false;
	private static final int nbReactions = 1;
	private static final int nbMetabolites = 5;
	private static final int nbCompartments = 1;

	private static final String inputFile = "tests/inputData/miniRec2.xml";
	private static final String outputFile = "tests/outputData/ExpectedminiRec2.json";

	private JsonObject json;
	private static final int nbNodes = 6;
	private static final int nbLinks = 5;
	private static final boolean hasMappings = true;
	private static final int[] nbMappings = { 1, 1 };

	@Override
	public Model getModel() {
		return this.model;
	}

	@Override
	public void setModel(Model model) {
		this.model=model;
	}

	@Test
	@Override
	public void testModel() {
		Model m=this.getModel();
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

	
	@Override
	public JsonObject getJson() {
		return this.json;
	}

	@Override
	public void setJson(JsonObject j) {
		this.json=j;
	}

	@Test
	@Override
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
		return MiniRec2Dummy.nbNodes;
	}

	@Override
	public int getNbLinks() {
		return MiniRec2Dummy.nbLinks;
	}

	@Override
	public boolean getHasMappings() {
		return MiniRec2Dummy.hasMappings;
	}

	@Override
	public int getNbMappingsCdt() {
		return MiniRec2Dummy.nbMappings.length;
	}

	@Override
	public int getNbMappingsData(int i) {
		return MiniRec2Dummy.nbMappings[i];
	}
}

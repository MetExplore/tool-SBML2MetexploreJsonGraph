package phnmnl.tests;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;

import javax.xml.stream.XMLStreamException;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;
import org.sbml.jsbml.Reaction;
import org.sbml.jsbml.Species;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import phnmnl.sbml.converter.AbstractConverter;
import phnmnl.sbml.converter.FBC2toJsonConverter;
import phnmnl.sbml.converter.SBML2jsonConverter;
import phnmnl.tests.utils.Dummy;
import phnmnl.tests.utils.FBC2Dummy;
import phnmnl.tests.utils.Lvl2Dummy;

@RunWith(Parameterized.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class Test3_Converter {

	public static AbstractConverter conv;

	@Parameters
	public static Collection<Dummy[]> data() {
		return Arrays.asList(new Dummy[][] { { new Lvl2Dummy() }, { new FBC2Dummy() } });
	}

	@Parameter(0)
	public Dummy inputDummy;

	@Test
	public void testA_constructor() throws XMLStreamException, IOException {

		if (inputDummy.isFbc()) {
			conv = new FBC2toJsonConverter(inputDummy.getModel(),true);
		} else {
			conv = new SBML2jsonConverter(inputDummy.getModel(), true);
		}

		assertNotNull("Converter is null", conv);
	}

	@Test
	public void testB_initMappings() {

		conv.initializeMappingArray();

		assertTrue("No name in min condition object", conv.getMincdt().has("name"));
		if (conv.getMincdt().has("name"))
			assertEquals("Incorrect name in min condition object", "Min", conv.getMincdt().get("name").getAsString());

		assertTrue("No data in min condition object", conv.getMincdt().has("data"));
		if (conv.getMincdt().has("data"))
			assertEquals("Data array not empty in min condition object", 0,
					conv.getMincdt().get("data").getAsJsonArray().size());

		assertTrue("No name in Max condition object", conv.getMaxcdt().has("name"));
		if (conv.getMaxcdt().has("name"))
			assertEquals("Incorrect name in Max condition object", "Max", conv.getMaxcdt().get("name").getAsString());

		assertTrue("No data in Max condition object", conv.getMaxcdt().has("data"));
		if (conv.getMaxcdt().has("data"))
			assertEquals("Data array not empty in Max condition object", 0,
					conv.getMaxcdt().get("data").getAsJsonArray().size());

	}

	@Test
	public void testC_AddLink() {

		int initialL = conv.getLinks().size();
		conv.addLink(10, 11, "in", true);

		assertEquals("Link not added", initialL + 1, conv.getLinks().size());
		if (conv.getLinks().size() == initialL + 1) {
			JsonObject lastlink = conv.getLinks().get(initialL).getAsJsonObject();

			assertTrue("No id in added Link", lastlink.has("id"));
			if (lastlink.has("id"))
				assertEquals("Incorrect id in added Link", "10 -- 11", lastlink.get("id").getAsString());

			assertTrue("No source in added Link", lastlink.has("source"));
			if (lastlink.has("source"))
				assertEquals("Incorrect source in added Link", 10, lastlink.get("source").getAsInt());

			assertTrue("No target in added Link", lastlink.has("target"));
			if (lastlink.has("target"))
				assertEquals("Incorrect target in added Link", 11, lastlink.get("target").getAsInt());

			assertTrue("No interaction in added Link", lastlink.has("interaction"));
			if (lastlink.has("interaction"))
				assertEquals("Incorrect interaction in added Link", "in", lastlink.get("interaction").getAsString());

			assertTrue("No 'reversible' attribute in added Link", lastlink.has("reversible"));
			if (lastlink.has("reversible"))
				assertEquals("Incorrect reversible in added Link", true, lastlink.get("reversible").getAsBoolean());

			conv.getLinks().remove(lastlink);
			assertEquals("Link not removed", initialL, conv.getLinks().size());
		}
	}

	@Test
	public void testD_createMetaboliteNode() {
		Species s = inputDummy.getSpecies();
		JsonObject so = conv.createMetaboliteNode(s);

		assertTrue("No biologicalType in created Metabolite Node", so.has("biologicalType"));
		if (so.has("biologicalType"))
			assertEquals("Incorrect interaction in added Metabolite Node", "metabolite",
					so.get("biologicalType").getAsString());

		assertTrue("No name in created Metabolite Node", so.has("name"));
		if (so.has("name"))
			assertEquals("Incorrect name in added Metabolite Node", s.getName(), so.get("name").getAsString());

		assertTrue("No dbIdentifier in created Metabolite Node", so.has("dbIdentifier"));
		if (so.has("dbIdentifier"))
			assertEquals("Incorrect dbIdentifier in added Metabolite Node", s.getId(),
					so.get("dbIdentifier").getAsString());

		assertTrue("No compartment in created Metabolite Node", so.has("compartment"));
		if (so.has("compartment"))
			assertEquals("Incorrect compartment in added Metabolite Node", s.getCompartmentInstance().getName(),
					so.get("compartment").getAsString());

		assertTrue("No 'isSideCompound' attribute in created Metabolite Node", so.has("isSideCompound"));
		if (so.has("isSideCompound"))
			assertEquals("Incorrect isSideCompound in added Metabolite Node", false,
					so.get("isSideCompound").getAsBoolean());

		assertTrue("No 'svg' attribute in created Metabolite Node", so.has("svg"));
		if (so.has("svg"))
			assertEquals("Incorrect svg in added Metabolite Node", "", so.get("svg").getAsString());

		assertTrue("No 'svgWidth' attribute in created Metabolite Node", so.has("svgWidth"));
		if (so.has("svgWidth"))
			assertEquals("Incorrect svgWidth in added Metabolite Node", "0", so.get("svgWidth").getAsString());

		assertTrue("No 'svgHeight' attribute in created Metabolite Node", so.has("svgHeight"));
		if (so.has("svgHeight"))
			assertEquals("Incorrect svgHeight in added Metabolite Node", "0", so.get("svgHeight").getAsString());

	}

	@Test
	public void testE_createReactionNode() {

		Reaction r = inputDummy.getRxn();
		JsonObject so = conv.createReactionNode(r);

		assertTrue("No 'biologicalType' attribute in created Reaction Node", so.has("biologicalType"));
		if (so.has("biologicalType"))
			assertEquals("Incorrect biologicalType in added Reaction Node", "reaction",
					so.get("biologicalType").getAsString());

		assertTrue("No dbIdentifier in created Reaction Node", so.has("dbIdentifier"));
		if (so.has("dbIdentifier"))
			assertEquals("Incorrect name in added Reaction Node", r.getId(), so.get("dbIdentifier").getAsString());

		assertTrue("No name in created Reaction Node", so.has("name"));
		if (so.has("name"))
			assertEquals("Incorrect name in added Reaction Node", r.getName(), so.get("name").getAsString());

		assertTrue("No 'reactionReversibility' attribute in created Reaction Node", so.has("reactionReversibility"));
		if (so.has("name"))
			assertEquals("Incorrect 'reactionReversibility' attribute in added Reaction Node", r.getReversible(),
					so.get("reactionReversibility").getAsBoolean());

		JsonObject cdt = conv.getMincdt();
		assertEquals("Wrong number of data in min condition", 1, cdt.get("data").getAsJsonArray().size());
		if (cdt.get("data").getAsJsonArray().size() == 1) {
			JsonObject mapp = cdt.get("data").getAsJsonArray().get(0).getAsJsonObject();

			assertTrue("No node in mapping test data", mapp.has("node"));
			if (mapp.has("node"))
				assertEquals("Incorrect node in mapping test data", r.getId(), mapp.get("node").getAsString());

			assertTrue("No value in mapping test data", mapp.has("value"));
			if (mapp.has("value"))
				assertEquals("Incorrect name in mapping test data", 0.0, mapp.get("value").getAsDouble(), 0);
		}

		cdt = conv.getMaxcdt();
		assertEquals("Wrong number of data in min condition", 1, cdt.get("data").getAsJsonArray().size());
		if (cdt.get("data").getAsJsonArray().size() == 1) {
			JsonObject mapp = cdt.get("data").getAsJsonArray().get(0).getAsJsonObject();

			assertTrue("No node in mapping test data", mapp.has("node"));
			if (mapp.has("node"))
				assertEquals("Incorrect node in mapping test data", r.getId(), mapp.get("node").getAsString());

			assertTrue("No value in mapping test data", mapp.has("value"));
			if (mapp.has("value"))
				assertEquals("Incorrect name in mapping test data", 0.0, mapp.get("value").getAsDouble(), 0);
		}
		
		/*
		 * reset arrays for future tests
		 */
		conv.setMincdt(new JsonObject());
		conv.setMaxcdt(new JsonObject());
		conv.initializeMappingArray();
	}

	@Test
	public void testF_parseReaction() {
		conv.setModel(inputDummy.getModel());
		conv.parseReaction();

		assertEquals("Wrong Number of Nodes", inputDummy.getNbNodes(), conv.getNodes().size());
		assertEquals("Wrong Number of Nodes", inputDummy.getNbLinks(), conv.getLinks().size());
	}

	@Test
	public void testG_addMapping() {
		
		JsonObject j=new JsonObject();
		conv.setJson(j);
		conv.addMappings();
		
		assertEquals("Error in mapping data", inputDummy.getHasMappings(), j.has("mappingdata"));
		if (j.has("mappingdata")){
			JsonArray mds=j.get("mappingdata").getAsJsonArray();
			
			assertEquals("More than one mappings added",1,mds.size());
			if(mds.size()==1){
				JsonObject map = mds.get(0).getAsJsonObject();
				
				assertTrue("No name in mapping test data", map.has("name"));
				if (map.has("name"))
					assertEquals("Incorrect name in mapping test data", "Flux", map.get("name").getAsString());
				
				assertTrue("No 'targetLabel' attribute in mapping test data", map.has("targetLabel"));
				if (map.has("targetLabel"))
					assertEquals("Incorrect 'targetLabel' attribute in mapping test data","reactionDBIdentifier", map.get("targetLabel").getAsString());
				
				assertTrue("No 'object' attribute in mapping test data", map.has("object"));
				if (map.has("object"))
					assertEquals("Incorrect 'object' attribute in mapping test data","Reaction", map.get("object").getAsString());
				
				assertTrue("No condition data in mapping test data", map.has("mappings"));
				if (map.has("mappings")){
					
					int n=inputDummy.getNbMappingsCdt();
					assertEquals("Incorrect number of condition in mapping test data",n, map.get("mappings").getAsJsonArray().size());
					if(map.get("mappings").getAsJsonArray().size()==n){
						for(int i=0;i<n;i++){
							JsonArray data = map.get("mappings").getAsJsonArray().get(i).getAsJsonObject().get("data").getAsJsonArray();
							assertEquals("Wrong number of data in condition "+i,inputDummy.getNbMappingsData(i),data.size());
						}
					}
				}
			}
			
			
		}
		

	}

	//
	// @Test
	// public void testD_WriteJsonToFile() {
	// fail("Not yet implemented"); // TODO
	// }

}

package phnmnl.tests.utils;

import org.sbml.jsbml.Model;

import com.google.gson.JsonObject;

public interface TestData {
	
	Model getModel();
	void setModel(Model model);
	
	public int getNbReactions();
	public int getNbMetabolites();
	public int getNbCompartments();
	
	public void testModel();
	
	public String getInputFile();
	public String getOutputFile();

	public int getNbNodes();
	public int getNbLinks();
	public boolean getHasMappings();
	public int getNbMappingsCdt();
	public int getNbMappingsData(int i);
	
	public void testJson();
	public JsonObject getJson();
	public void setJson(JsonObject j);
	
	public boolean isFbc();
	
}

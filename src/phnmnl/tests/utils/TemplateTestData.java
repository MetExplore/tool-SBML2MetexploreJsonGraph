package phnmnl.tests.utils;

import org.sbml.jsbml.Model;

import com.google.gson.JsonObject;

public interface TemplateTestData {
	
	Model getModel();
	void setModel(Model model);
	
	int getNbReactions();
	int getNbMetabolites();
	int getNbCompartments();
	
	void testModel();
	
	String getInputFile();
	String getOutputFile();
	String getExpectedOutput();
	

	int getNbNodes();
	int getNbLinks();
	boolean getHasMappings();
	int getNbMappingsCdt();
	int getNbMappingsData(int i);
	
	void testJson();
	JsonObject getJson();
	void setJson(JsonObject j);
	
	boolean isFbc();
	
}

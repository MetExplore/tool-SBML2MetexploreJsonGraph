package phnmnl.sbml;

import java.io.File;
import java.io.IOException;

import javax.xml.stream.XMLStreamException;

import org.sbml.jsbml.Model;
import org.sbml.jsbml.SBMLDocument;
import org.sbml.jsbml.SBMLReader;

public class Reader {
	
	private String file;
	private Model model;

	public Reader (String filename){
		this.file=filename;
		
	}
	
	public void read() throws XMLStreamException, IOException{
		File sbmlFile= new File(this.getFile());
		
		SBMLDocument doc=SBMLReader.read(sbmlFile);
		
		this.model= doc.getModel();
	}
	
	public boolean isFBCModel(){
		return model.isPackageURIEnabled("http://www.sbml.org/sbml/level3/version1/fbc/version2");
	}

	public String getFile() {
		return file;
	}

	public void setFile(String file) {
		this.file = file;
	}

	public Model getModel() {
		return model;
	}

	public void setModel(Model model) {
		this.model = model;
	}
	
}

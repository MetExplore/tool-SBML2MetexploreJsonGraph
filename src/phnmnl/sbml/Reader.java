package phnmnl.sbml;

import java.io.File;
import java.io.IOException;

import javax.xml.stream.XMLStreamException;

import org.sbml.jsbml.Model;
import org.sbml.jsbml.SBMLDocument;
import org.sbml.jsbml.SBMLReader;

public class Reader {
	
	private String file;

	public Reader (String filename){
		this.file=filename;
		
	}
	
	public Model read() throws XMLStreamException, IOException{
		File sbmlFile= new File(this.getFile());
		
		SBMLDocument doc=SBMLReader.read(sbmlFile);
		
		return doc.getModel();
	}

	public String getFile() {
		return file;
	}

	public void setFile(String file) {
		this.file = file;
	}
	
}

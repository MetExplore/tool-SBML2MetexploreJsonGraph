package phnmnl;

import java.io.IOException;

import javax.xml.stream.XMLStreamException;

import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;

import phnmnl.sbml.Reader;
import phnmnl.sbml.converter.AbstractConverter;
import phnmnl.sbml.converter.FBC2toJsonConverter;
import phnmnl.sbml.converter.SBML2jsonConverter;

public class SBML2Json {

	@Option(name = "-h", usage = "Prints this help.")
	public boolean phelp = false;

	@Option(name = "-inFile", usage = "[Required] Input SBML file.", required = true)
	public String inFile = null;
	
	@Option(name = "-validate", usage = "Validate input SBML file. Default to false")
	public boolean useValidator=false;

	@Option(name = "-outFile", usage = "[Required] Output json file name.", required = true)
	public String outFile;

	public static void main(String[] args) {

		SBML2Json s2j = new SBML2Json();
		CmdLineParser parser = new CmdLineParser(s2j);
		
		try {
			parser.parseArgument(args);
			

			if (s2j.phelp) {
				System.out.println("Application Usage:");
				parser.printUsage(System.out);
				System.exit(0);
			}
		} catch (CmdLineException e) {

			System.out.println("Application Usage:");
			parser.printUsage(System.out);
			System.exit(0);

		}
		s2j.convert	();

	}

	private void convert() {
		
		Reader r=new Reader(this.inFile,this.useValidator);
		try {
			r.read();
			
			AbstractConverter conv;
			
			if(r.isFBCModel()){
				conv=new FBC2toJsonConverter(r.getModel());
			}else{
				conv=new SBML2jsonConverter(r.getModel());
			}
			
			conv.convert();
			//System.err.println(conv.getJson().toString());
			conv.writeJsonToFile(this.outFile);
			
			
		} catch (XMLStreamException | IOException e) {
			e.printStackTrace();
		}
		
	}
}

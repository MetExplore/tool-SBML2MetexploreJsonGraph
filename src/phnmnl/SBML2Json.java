package phnmnl;

import org.kohsuke.args4j.Option;

public class SBML2Json {

	@Option(name="-h", usage="Prints this help.")
	public boolean phelp = false;
	
	@Option(name="-inFile", usage="[Required] Input SBML file.", required=true)
	public String inFile = null;
	
	@Option(name="-outFile", usage="[Required] Output json file name.", required=true)
	public String outFile ;
	
	public static void main(String[] args) {
		
	}
}

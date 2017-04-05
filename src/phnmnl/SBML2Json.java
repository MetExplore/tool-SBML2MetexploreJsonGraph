package phnmnl;

import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;

public class SBML2Json {

	@Option(name = "-h", usage = "Prints this help.")
	public boolean phelp = false;

	@Option(name = "-inFile", usage = "[Required] Input SBML file.", required = true)
	public String inFile = null;

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
		
		
		
	}
}

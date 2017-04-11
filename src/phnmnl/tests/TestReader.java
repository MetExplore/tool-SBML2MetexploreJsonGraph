package phnmnl.tests;

import static org.junit.Assert.fail;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

import phnmnl.sbml.Reader;

@RunWith(Parameterized.class)
public class TestReader {
	
	public String file="test/inputData/miniRec2.xml";
	public Reader tester;
	
	
	@Parameters
    public static Collection<String[]> data() {
        return Arrays.asList(new String[][] {
                 {"test/inputData/miniRec2.xml","test/outputData/ExpectedminiRec2.json" }, 
                 {"test/inputData/ubFluxNet.sbml", "test/outputData/ExpectedUbFluxNet.sbml" }  
           });
    }

    @Parameter(0)
    public /* NOT private */ String fInput;

    @Parameter(1)
    public /* NOT private */ String outputfile;
	
	@Test
	public void testReaderString() {
		fail("Not yet implemented");
	}

	@Test
	public void testRead() {
		fail("Not yet implemented");
	}

	@Test
	public void testIsFBCModel() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetFile() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetModel() {
		fail("Not yet implemented");
	}

}

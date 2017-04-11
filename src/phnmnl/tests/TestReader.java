package phnmnl.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

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

import phnmnl.sbml.Reader;
import phnmnl.tests.utils.Dummy;
import phnmnl.tests.utils.MiniRec2Dummy;
import phnmnl.tests.utils.UbFluxNetDummy;

@RunWith(Parameterized.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestReader {
	
	public static Reader tester;
	
	
	@Parameters
    public static Collection<Dummy[]> data() {
        return Arrays.asList(new Dummy[][] {
                 {new MiniRec2Dummy() }, 
                 {new UbFluxNetDummy() }  
           });
    }

    @Parameter(0)
    public Dummy inputDummy;


	@Test
	public void testA_ReaderString() {
		tester=new Reader(inputDummy.getInputFile());
		assertNotNull("Failed to instantiate JSBML reader: object is null", tester);
		
	}

	@Test
	public void testB_Read() {
		try {
			tester.read();
		} catch (XMLStreamException e) {
			e.printStackTrace();
			fail("XMLStreamException raised");			
		} catch (IOException e) {
			e.printStackTrace();
			fail("IOException raised");
		}
		assertNotNull("Read model is null", tester.getModel());
		inputDummy.setModel(tester.getModel());
		inputDummy.testModel();
	}

	@Test
	public void testC_IsFBCModel() {
		assertEquals("Use of FBC module doesn't match dummy parameter",inputDummy.isFbc(),tester.isFBCModel());
	}


}

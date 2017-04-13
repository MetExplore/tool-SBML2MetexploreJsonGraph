package phnmnl.tests;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
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
import phnmnl.sbml.converter.AbstractConverter;
import phnmnl.sbml.converter.FBC2toJsonConverter;
import phnmnl.sbml.converter.SBML2jsonConverter;
import phnmnl.tests.utils.MiniRec2TestData;
import phnmnl.tests.utils.TemplateTestData;
import phnmnl.tests.utils.UbFluxNetTestData;

@RunWith(Parameterized.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class Test4_DataConvertion {

	public static AbstractConverter conv;

	@Parameters
	public static Collection<TemplateTestData[]> data() {
		return Arrays.asList(new TemplateTestData[][] { { new MiniRec2TestData() }, { new UbFluxNetTestData() } });
	}

	@Parameter(0)
	public TemplateTestData data;

	@Test
	public final void testA_constructor() throws XMLStreamException, IOException {
		Reader r = new Reader(data.getInputFile());

		r.read();
		if (r.isFBCModel()) {
			conv = new FBC2toJsonConverter(r.getModel());
		} else {
			conv = new SBML2jsonConverter(r.getModel());
		}

		assertNotNull("Converter is null", conv);
	}

	@Test
	public void testB_Convert() {
		conv.convert();
		assertNotNull("converted Json is null", conv.getJson());
		data.setJson(conv.getJson());
		data.testJson();

	}
	
	@Test
	public void testC_WriteFile() throws IOException{
		conv.writeJsonToFile(data.getOutputFile());
		
		BufferedReader br = new BufferedReader(new FileReader(data.getOutputFile()));
	    try {
	    	assertTrue("output File was not written",br.ready());

	    } finally {
	        if (br != null) br.close();
	        
	        File file = new File(data.getOutputFile());
	        boolean deleted=file.delete();
    		assertTrue("Unable to delete test output file",deleted);
	    }
	}

}

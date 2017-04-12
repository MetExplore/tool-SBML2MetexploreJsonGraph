package phnmnl.tests;

import static org.junit.Assert.*;

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
import phnmnl.tests.utils.TestData;
import phnmnl.tests.utils.UbFluxNetTestData;

@RunWith(Parameterized.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestDataConvertion {

	public static AbstractConverter conv;

	@Parameters
	public static Collection<TestData[]> data() {
		return Arrays.asList(new TestData[][] { { new MiniRec2TestData() }, { new UbFluxNetTestData() } });
	}

	@Parameter(0)
	public TestData data;

	@Test
	public final void test() throws XMLStreamException, IOException {
		Reader r = new Reader(data.getInputFile());

		r.read();
		if (r.isFBCModel()) {
			conv = new FBC2toJsonConverter(r.getModel());
		} else {
			conv = new SBML2jsonConverter(r.getModel());
		}

		assertNotNull("Converter is null", conv);
	}

	public void testB_Convert() {

//		conv.convert();
//		assertNotNull("converted Json is null", conv.getJson());
//		data.setJson(conv.getJson());
//		data.testJson();

	}
	
	public void testC_WriteFile(){
		
	}

}

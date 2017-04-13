package phnmnl.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

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
import phnmnl.tests.utils.TemplateTestData;
import phnmnl.tests.utils.MiniRec2TestData;
import phnmnl.tests.utils.UbFluxNetTestData;

@RunWith(Parameterized.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class Test1_Reader {

	public static Reader tester;

	@Parameters
	public static Collection<TemplateTestData[]> data() {
		return Arrays.asList(new TemplateTestData[][] { { new MiniRec2TestData() }, { new UbFluxNetTestData() } });
	}

	@Parameter(0)
	public TemplateTestData inputDummy;

	@Test
	public void testA_ReaderString() {
		tester = new Reader(inputDummy.getInputFile());
		assertNotNull("Failed to instantiate JSBML reader: object is null", tester);

	}

	@Test
	public void testB_Read() throws XMLStreamException, IOException {

		tester.read();

		assertNotNull("Read model is null", tester.getModel());
		inputDummy.setModel(tester.getModel());
		inputDummy.testModel();
	}

	@Test
	public void testC_IsFBCModel() {
		assertEquals("Use of FBC module doesn't match dummy parameter", inputDummy.isFbc(), tester.isFBCModel());
	}

}

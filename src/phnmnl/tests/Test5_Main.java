package phnmnl.tests;

import static org.junit.Assert.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Arrays;
import java.util.Collection;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import phnmnl.SBML2Json;
import phnmnl.tests.utils.JsonTestUtils;
import phnmnl.tests.utils.MiniRec2TestData;
import phnmnl.tests.utils.TemplateTestData;
import phnmnl.tests.utils.UbFluxNetTestData;

@RunWith(Parameterized.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class Test5_Main {

	public static JsonObject output = null;
	public static JsonObject expectedOutput = null;

	@Parameters
	public static Collection<TemplateTestData[]> data() {
		return Arrays.asList(new TemplateTestData[][] { { new MiniRec2TestData() }, { new UbFluxNetTestData() } });
	}

	@Parameter(0)
	public TemplateTestData data;

	@Test
	public void testA_args() throws IOException {
		
		String[] args= { "-inFile", data.getInputFile(), "-outFile", data.getOutputFile() };

		SBML2Json.main(args);

		InputStream inputStream = new FileInputStream(data.getOutputFile());
		Reader inputStreamReader = new InputStreamReader(inputStream);

		output = (JsonObject) new JsonParser().parse(inputStreamReader);

		inputStream = new FileInputStream(data.getExpectedOutput());
		inputStreamReader = new InputStreamReader(inputStream);

		expectedOutput = (JsonObject) new JsonParser().parse(inputStreamReader);

		assertNotNull("Output object is a null Object",output);
		assertFalse("Output object is a Json null Object", output.isJsonNull());
		assertTrue("Output object is a not a JsonObject", output.isJsonObject());
		
		assertNotNull("Expected Output object is a null Object",expectedOutput);
		assertFalse("Expected Output object is a Json null Object", expectedOutput.isJsonNull());
		assertTrue("Expected Output object is a not a JsonObject", expectedOutput.isJsonObject());
		
	}

	@Test
	public void testB_Equality() {
		
		JsonTestUtils.test(expectedOutput, output);
		
//		assertTrue(output.has("nodes"));
//		assertTrue(expectedOutput.has("nodes"));
//		if(output.has("nodes") && expectedOutput.has("nodes")){
//			
//			
//			JsonTestUtils.test(output.get("nodes"),expectedOutput.get("nodes"));
////			assertEquals("",output.get("nodes").getAsJsonArray().size(),expectedOutput.get("nodes").getAsJsonArray().size());
//			
//		}
		
	}
	
//	@Test
//	public void testC_links() {
////		assertTrue(output.h)
//	}
//	
//	@Test
//	public void testD_Mappings() {
////		assertTrue(output.h)
//	}

}

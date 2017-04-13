package phnmnl.tests.utils;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import static org.junit.Assert.*;

import java.util.Map.Entry;
import java.util.Set;

public class JsonTestUtils {

	/**
	 * Recursively test all values present in the output {@link JsonElement} onto the expected {@link JsonElement}
	 * This test takes into account order in {@link JsonArray} elements.
	 * @param expected
	 * @param output
	 */
	public static void test(JsonElement expected, JsonElement output) {

		Class<? extends JsonElement> outClass = output.getClass();
		Class<? extends JsonElement> expectedClass = expected.getClass();

		assertEquals("Output and Expected are not of the same Json Class", outClass, expectedClass);

		switch (expectedClass.getSimpleName()) {
		case "JsonObject":
			JsonTestUtils.testJsonObject((JsonObject) expected, (JsonObject) output);
			break;
		case "JsonArray":
			JsonTestUtils.testJsonArray((JsonArray) expected, (JsonArray) output);
			break;
		case "JsonPrimitive":
			JsonTestUtils.testJsonPrimitive((JsonPrimitive) expected, (JsonPrimitive) output);
			break;
		}

		//System.out.println(outClass + " / " + expectedClass);

	}

	private static void testJsonObject(JsonObject expected, JsonObject output) {

		Set<Entry<String, JsonElement>> expectedEntries = expected.entrySet();

		assertEquals("Output does not have the expected number of attribute", expectedEntries.size(),
				output.entrySet().size());

		for (Entry<String, JsonElement> expectedPairs : expectedEntries) {

			assertTrue("", output.has(expectedPairs.getKey()));
			if (output.has(expectedPairs.getKey())) {

				JsonTestUtils.test(expectedPairs.getValue(), output.get(expectedPairs.getKey()));
			}
		}
	}

	private static void testJsonArray(JsonArray expected, JsonArray output) {

		assertEquals("JsonArray does not have the expected number of members", expected.size(), output.size());
		if (expected.size() == output.size()) {
			int n = expected.size();
			for (int i = 0; i < n; i++) {
				JsonTestUtils.test(expected.get(i), output.get(i));
			}
		}
	}

	private static void testJsonPrimitive(JsonPrimitive expected, JsonPrimitive output) {
		
		if(expected.isBoolean()){
			assertTrue("Output attribute "+output.toString()+" is not a boolean",output.isBoolean());
			assertEquals("Output attribute does not have the expected value",expected.getAsBoolean(),output.getAsBoolean());
		}
		
		if(expected.isNumber()){
			assertTrue("Output attribute "+output.toString()+" is not a number",output.isNumber());
			assertEquals("Output attribute does not have the expected value",expected.getAsNumber(),expected.getAsNumber());
		}
		
		if(expected.isString()){
			assertTrue("Output attribute "+output.toString()+" is not a string",output.isString());
			assertEquals("Output attribute does not have the expected value",expected.getAsString(),output.getAsString());
		}
		
		if(expected.isJsonNull()){
			assertTrue("Output attribute "+output.toString()+" is not a null value",output.isJsonNull());
			assertEquals("Output attribute does not have the expected value",expected.getAsJsonNull(),output.getAsJsonNull());
		}
		
		
	}

}

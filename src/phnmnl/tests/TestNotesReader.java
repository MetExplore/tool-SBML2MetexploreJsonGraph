package phnmnl.tests;

import static org.junit.Assert.*;

import org.junit.Test;

import com.google.gson.JsonObject;

import phnmnl.sbml.NoteReader;

public class TestNotesReader {

	@Test
	public final void testParseSBaseNotes_OnePathway() {
		JsonObject j = new JsonObject();
		String n = "<p>SUBSYSTEM: Androgen and estrogen synthesis and metabolism</p>";

		NoteReader.parseSBaseNotes(j, n);

		assertTrue("No pathways retrieved from note string", j.has("pathways"));
		if (j.has("pathways")) {		

			assertEquals("Incorrect number of pathways retrieved from note string", 1,	j.get("pathways").getAsJsonArray().size());

			if (j.get("pathways").getAsJsonArray().size() == 1) {

				String p = j.get("pathways").getAsJsonArray().get(0).getAsString();
				assertEquals("Wrong pathway name retrieved from note string", "Androgen and estrogen synthesis and metabolism", p);

			}
		}

	}

	@Test
	public final void testParseSBaseNotes_twoPathways() {
		JsonObject j = new JsonObject();
		String n = "<p>SUBSYSTEM: Androgen and estrogen synthesis and metabolism || Glycolysis</p>";

		NoteReader.parseSBaseNotes(j, n);
		assertTrue("No pathways retrieved from note string", j.has("pathways"));
		if (j.has("pathways")) {		

			assertEquals("Incorrect number of pathways retrieved from note string", 2,	j.get("pathways").getAsJsonArray().size());

			if (j.get("pathways").getAsJsonArray().size() == 2) {

				String p = j.get("pathways").getAsJsonArray().get(0).getAsString();
				assertEquals("Wrong pathway name retrieved from note string", "Androgen and estrogen synthesis and metabolism", p);
				
				p = j.get("pathways").getAsJsonArray().get(1).getAsString();
				assertEquals("Wrong pathway name retrieved from note string", "Glycolysis", p);
			}
		}
	}

	@Test
	public final void testParseSBaseNotes_EcNumber() {
		JsonObject j = new JsonObject();
		String n = "<p>EC Number: 1.1.1.50</p>";

		NoteReader.parseSBaseNotes(j, n);
		assertTrue("No EC Number retrieved from note string", j.has("ec"));
		if (j.has("ec")) {
			String ec = j.get("ec").getAsString();
			assertEquals("Wrong EC Number retrieved from note string", "1.1.1.50", ec);
		}
	}

	@Test
	public final void testParseSBaseNotes_NoEcNumber() {
		JsonObject j = new JsonObject();
		j.addProperty("biologicalType", "reaction");
		String n = "<p>SUBSYSTEM: Androgen and estrogen synthesis and metabolism</p>";

		NoteReader.parseSBaseNotes(j, n);
		assertTrue("No default value EC Number for reaction nodes", j.has("ec"));
		if (j.has("ec")) {
			String ec = j.get("ec").getAsString();
			assertEquals("Wrong default value for the EC Number of reaction nodes", "NA", ec);
		}
	}

	@Test
	public final void testParseSBaseNotes_Charge() {
		JsonObject j = new JsonObject();
		String n = "<p>CHARGE: -4</p>";

		NoteReader.parseSBaseNotes(j, n);
		assertTrue("No Charge retrieved from note string", j.has("charge"));
		if (j.has("charge")) {
			String charge = j.get("charge").getAsString();
			assertEquals("Wrong charge retrieved from note string", "-4", charge);
		}
	}

	@Test
	public final void testParseSBaseNotes_Formula() {
		JsonObject j = new JsonObject();
		String n = "<p>FORMULA: C21H26N7O17P3</p>";

		NoteReader.parseSBaseNotes(j, n);
		assertTrue("No Formula retrieved from note string", j.has("formula"));
		if (j.has("formula")) {
			String f = j.get("formula").getAsString();
			assertEquals("Wrong formula retrieved from note string", "C21H26N7O17P3", f);
		}
	}
}

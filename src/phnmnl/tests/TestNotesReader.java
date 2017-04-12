package phnmnl.tests;

import static org.junit.Assert.*;

import org.junit.Test;

import com.google.gson.JsonObject;

import phnmnl.sbml.NoteReader;

public class TestNotesReader {

	@Test
	public final void testParseSBaseNotes_OnePathway() {
		JsonObject j=new JsonObject();
		String n="<p>SUBSYSTEM: Androgen and estrogen synthesis and metabolism</p>";
		
		NoteReader.parseSBaseNotes(j, n);
		fail("Not yet implemented");
	}
	
	@Test
	public final void testParseSBaseNotes_twoPathways() {
		JsonObject j=new JsonObject();
		String n="<p>SUBSYSTEM: Androgen and estrogen synthesis and metabolism || Glycolysis</p>";
		
		NoteReader.parseSBaseNotes(j, n);
		fail("Not yet implemented");
	}
	
	@Test
	public final void testParseSBaseNotes_EcNumber() {
		JsonObject j=new JsonObject();
		String n="<p>EC Number: 1.1.1.50</p>";
		
		NoteReader.parseSBaseNotes(j, n);
		fail("Not yet implemented");
	}
	
	@Test
	public final void testParseSBaseNotes_NoEcNumber() {
		JsonObject j=new JsonObject();
		j.addProperty("biologicalType", "reaction");
		String n="<p>SUBSYSTEM: Androgen and estrogen synthesis and metabolism</p>";
		
		NoteReader.parseSBaseNotes(j, n);
		fail("Not yet implemented");
	}
	
	@Test
	public final void testParseSBaseNotes_Charge() {
		JsonObject j=new JsonObject();
		String n="<p>CHARGE: -4</p>";
		
		NoteReader.parseSBaseNotes(j, n);
		fail("Not yet implemented");
	}
	
	@Test
	public final void testParseSBaseNotes_Formula() {
		JsonObject j=new JsonObject();
		String n="<p>FORMULA: C21H26N7O17P3</p>";
		
		NoteReader.parseSBaseNotes(j, n);
		fail("Not yet implemented");
	}
}

package phnmnl.sbml;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class NoteReader {

	public static final String defaultPathwayPattern="[> ]+SUBSYSTEM:\\s*([^<]+)<";
	public static final String defaultECPattern="[> ]+EC.Number:\\s*([^<]+)<";
	public static final String defaultchargePattern="[> ]+CHARGE:\\s*([^<]+)<";
	public static final String defaultformulaPattern="[> ]+FORMULA:\\s*([^<]+)<";
	
	public static final String pathwaySep=" \\|\\| ";
	
	public static void parseReactionNotes(JsonObject jObject, String note){
		Matcher m;
		
		//get the pathways
		if((m = Pattern.compile(defaultPathwayPattern).matcher(note)).find() ){
			
			String[] pthwList=m.group(1).split(pathwaySep);
			JsonArray paths=new JsonArray();			
			for(String val:pthwList){
				String value = val.replaceAll("[^\\p{ASCII}]", "");
				
				paths.add(value);
			}
			jObject.add("pathways", paths);
		}

		//get the ec number 
		if( (m=Pattern.compile(defaultECPattern).matcher(note) ).find()){
			jObject.addProperty("ec",m.group(1));
		}else if(jObject.has("biologicalType") && jObject.get("biologicalType").getAsString().equals("reaction")){
			jObject.addProperty("ec","NA");
		}
		
		//get formula
		if( (m = Pattern.compile(defaultformulaPattern).matcher(note) ).find()){
			String value = m.group(1);
			
			jObject.addProperty("formula", value);
		}

		if( (m = Pattern.compile(defaultchargePattern).matcher(note) ).find()){
			String value = m.group(1);

			jObject.addProperty("charge", value);
		}
		
		
	}
}

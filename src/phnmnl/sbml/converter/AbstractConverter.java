package phnmnl.sbml.converter;

import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

import javax.xml.stream.XMLStreamException;

import org.sbml.jsbml.Model;
import org.sbml.jsbml.Reaction;
import org.sbml.jsbml.SBase;
import org.sbml.jsbml.Species;
import org.sbml.jsbml.SpeciesReference;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import phnmnl.sbml.NoteReader;

public abstract class AbstractConverter {

	protected Model model;
	protected JsonObject json;

	protected JsonArray nodes = new JsonArray();
	protected JsonArray links = new JsonArray();

	/**
	 * The two flux conditions
	 */
	protected JsonObject mincdt = new JsonObject(), maxcdt = new JsonObject();

	protected HashMap<String, Integer> indexMap = new HashMap<String, Integer>();

	public AbstractConverter() {
	}

	public AbstractConverter(Model m) {
		this.model = m;

	}

	public void convert() {
		this.json = new JsonObject();

		this.initializeMappingArray();

		this.parseReaction();

		this.json.add("nodes", nodes);
		this.json.add("links", links);

		this.addMappings();
	}

	public void initializeMappingArray() {

		mincdt.addProperty("name", "Min");
		mincdt.add("data", new JsonArray());

		maxcdt.addProperty("name", "Max");
		maxcdt.add("data", new JsonArray());
	}

	public void parseReaction() {
		for (Reaction r : model.getListOfReactions()) {

			JsonObject reactionNode = this.createReactionNode(r);
			nodes.add(reactionNode);
			int reactionindex = nodes.size() - 1;
			this.indexMap.put(r.getId(), reactionindex);
			
			reactionNode.addProperty("id", reactionindex);
			
			JsonArray rxnPaths=null;
			if(reactionNode.has("pathways"))
				rxnPaths=reactionNode.get("pathways").getAsJsonArray();

			/*
			 * Reactant list => "In" links
			 */
			for (SpeciesReference sref : r.getListOfReactants()) {
				Species s = sref.getSpeciesInstance();

				JsonObject mtbNode;
				int mtbIndex;
				if (this.indexMap.containsKey(s.getId())) {
					mtbIndex = this.indexMap.get(s.getId());

					mtbNode = (JsonObject) nodes.get(mtbIndex);

				} else {
					mtbNode = this.createMetaboliteNode(s);
					nodes.add(mtbNode);
					mtbIndex = nodes.size() - 1;

					this.indexMap.put(s.getId(), mtbIndex);
					
					mtbNode.addProperty("id", mtbIndex);
				}
				
				if(rxnPaths != null){
					if(mtbNode.has("pathways")){
						mtbNode.get("pathways").getAsJsonArray().addAll(rxnPaths);
					}else{
						mtbNode.add("pathways", rxnPaths);
					}
				}

				this.addLink(mtbIndex,reactionindex, "in", r.getReversible());

			}

			/*
			 * Product list => "out" links
			 */
			for (SpeciesReference sref : r.getListOfProducts()) {
				Species s = sref.getSpeciesInstance();

				JsonObject mtbNode;
				int mtbIndex;
				if (this.indexMap.containsKey(s.getId())) {
					mtbIndex = this.indexMap.get(s.getId());

					mtbNode = (JsonObject) nodes.get(mtbIndex);

				} else {
					mtbNode = this.createMetaboliteNode(s);
					nodes.add(mtbNode);
					mtbIndex = nodes.size() - 1;

					this.indexMap.put(s.getId(), mtbIndex);
					
					mtbNode.addProperty("id", mtbIndex);
				}

				if(rxnPaths != null){
					if(mtbNode.has("pathways")){
						mtbNode.get("pathways").getAsJsonArray().addAll(rxnPaths);
					}else{
						mtbNode.add("pathways", rxnPaths);
					}
				}
				
				this.addLink(reactionindex, mtbIndex, "out", r.getReversible());

			}

		}
	}

	/**
	 * Generate a link JsonObject and adds it to the JsonArray
	 * 
	 * @param idSource
	 * @param idTarget
	 * @param dir
	 * @param reversible
	 */
	public void addLink(int idSource, int idTarget, String dir, boolean reversible) {
		JsonObject link = new JsonObject();

		link.addProperty("id", idSource + " -- " + idTarget);
		link.addProperty("source", idSource);
		link.addProperty("target", idTarget);
		link.addProperty("interaction", dir);
		link.addProperty("reversible", String.valueOf(reversible));

		links.add(link);
	}

	public void addMappings() {

		JsonArray mappingsdata = new JsonArray();

		if (mincdt.get("data").getAsJsonArray().size() != 0 || maxcdt.get("data").getAsJsonArray().size() != 0) {
			JsonObject mapp = new JsonObject();

			mapp.addProperty("name", "Flux");
			mapp.addProperty("targetLabel", "reactionId");

			JsonArray mappingsCond = new JsonArray();
			mappingsCond.add(mincdt);
			mappingsCond.add(maxcdt);
			mapp.add("mappings", mappingsCond);

			mappingsdata.add(mapp);

		}

		if(mappingsdata.size() != 0 ){
			this.json.add("mappingdata", mappingsdata);
		}

	}

	protected void getAdditionalInfoInNote(JsonObject jobj, SBase sbase) throws XMLStreamException {
		String n=sbase.getNotesString();
		if(!n.isEmpty())
			NoteReader.parseSBaseNotes(jobj, n);
	}

	public abstract JsonObject createMetaboliteNode(Species s);

	public abstract JsonObject createReactionNode(Reaction jSBMLReaction);

	public void writeJsonToFile(String outputFileName) {

		try (FileWriter file = new FileWriter(outputFileName)) {

			file.write(this.getJson().toString());
			file.flush();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public JsonObject getJson() {
		return json;
	}

	public void setJson(JsonObject json) {
		this.json = json;
	}

	public Model getModel() {
		return model;
	}

	public void setModel(Model model) {
		this.model = model;
	}

	public JsonArray getNodes() {
		return nodes;
	}

	public void setNodes(JsonArray nodes) {
		this.nodes = nodes;
	}

	public JsonArray getLinks() {
		return links;
	}

	public void setLinks(JsonArray links) {
		this.links = links;
	}

	public JsonObject getMincdt() {
		return mincdt;
	}

	public void setMincdt(JsonObject mincdt) {
		this.mincdt = mincdt;
	}

	public JsonObject getMaxcdt() {
		return maxcdt;
	}

	public void setMaxcdt(JsonObject maxcdt) {
		this.maxcdt = maxcdt;
	}

	public HashMap<String, Integer> getIndexMap() {
		return indexMap;
	}

	public void setIndexMap(HashMap<String, Integer> indexMap) {
		this.indexMap = indexMap;
	}

}

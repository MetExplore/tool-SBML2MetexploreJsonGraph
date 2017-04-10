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

	private void initializeMappingArray() {

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

			/*
			 * Reactant list => "In" links
			 */
			for (SpeciesReference sref : r.getListOfReactants()) {
				Species s = sref.getSpeciesInstance();

				JsonObject mtbNode;
				if (this.indexMap.containsKey(s.getId())) {
					int mtbIndex = this.indexMap.get(s.getId());

					mtbNode = (JsonObject) nodes.get(mtbIndex);

				} else {
					mtbNode = this.createMetaboliteNode(s);
					nodes.add(mtbNode);
					int mtbIndex = nodes.size() - 1;

					this.indexMap.put(s.getId(), mtbIndex);
				}

				this.addLink(s.getId(), r.getId(), "in", r.getReversible());

			}

			/*
			 * Product list => "out" links
			 */
			for (SpeciesReference sref : r.getListOfProducts()) {
				Species s = sref.getSpeciesInstance();

				JsonObject mtbNode;
				if (this.indexMap.containsKey(s.getId())) {
					int mtbIndex = this.indexMap.get(s.getId());

					mtbNode = (JsonObject) nodes.get(mtbIndex);

				} else {
					mtbNode = this.createMetaboliteNode(s);
					nodes.add(mtbNode);
					int mtbIndex = nodes.size() - 1;

					this.indexMap.put(s.getId(), mtbIndex);
				}

				this.addLink(r.getId(), s.getId(), "out", r.getReversible());

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
	protected void addLink(String idSource, String idTarget, String dir, boolean reversible) {
		JsonObject link = new JsonObject();

		link.addProperty("id", idSource + " -- " + idTarget);
		link.addProperty("source", indexMap.get(idSource));
		link.addProperty("target", indexMap.get(idTarget));
		link.addProperty("interaction", dir);
		link.addProperty("reversible", String.valueOf(reversible));

		links.add(link);
	}

	private void addMappings() {

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

		// TODO add mapping on metabolites if any

		this.json.add("mappingdata", mappingsdata);

	}

	protected void getAdditionalInfoInNote(JsonObject rxn, SBase jSBMLReaction) throws XMLStreamException {
		NoteReader.parseReactionNotes(rxn, jSBMLReaction.getNotesString());
	}

	protected abstract JsonObject createMetaboliteNode(Species s);

	protected abstract JsonObject createReactionNode(Reaction jSBMLReaction);

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

}

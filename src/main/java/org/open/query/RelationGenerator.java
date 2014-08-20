package org.open.query;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.open.util.WordCustomizeUtil;

public class RelationGenerator {
	Properties data = new Properties();
	final String SUBJECT = "getSubject";
	final String OBJECT = "result";
	
	public RelationGenerator(){
		try {
			InputStream file = new FileInputStream("relation.properties");
			data.load(file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public StringBuffer getOwl() throws ArrayIndexOutOfBoundsException {
		String [] relations = WordCustomizeUtil.getRelations(data.getProperty("dbpedia-owl"));
		StringBuffer output = buildQuery(SUBJECT,Predicate.DBPEDIA_OWL,relations,OBJECT);
		return output;
	}
	
	public StringBuffer getProp() throws ArrayIndexOutOfBoundsException {
		String [] relations = WordCustomizeUtil.getRelations(data.getProperty("dbpprop"));
		StringBuffer output = buildQuery(SUBJECT,Predicate.DBBPROP,relations,OBJECT);
		return output;
	}
	
	public StringBuffer getOwlOf() throws ArrayIndexOutOfBoundsException {
		String [] relations = WordCustomizeUtil.getRelations(data.getProperty("dbpeida-owl-of"));
		StringBuffer output = buildQuery(OBJECT,Predicate.DBPEDIA_OWL,relations,SUBJECT);
		return output;
	}
	
	public StringBuffer getPropOf() throws ArrayIndexOutOfBoundsException {
		String [] relations = WordCustomizeUtil.getRelations(data.getProperty("dbpprop-of"));
		StringBuffer output = buildQuery(OBJECT,Predicate.DBBPROP,relations,SUBJECT);
		return output;
	}
																																																																			
	public StringBuffer getFoaf() throws ArrayIndexOutOfBoundsException {
		String [] relations = WordCustomizeUtil.getRelations(data.getProperty("foaf"));
		StringBuffer output = buildQuery(SUBJECT,Predicate.FOAF,relations,OBJECT);
		return output;
	}
	
	/**
	 * Get the subject from predicate where we are search using object. So the sample 
	 * Query will be like.
	 * {?subject predicate: relation ?object}
	 * @param subject
	 * @param predicate
	 * @param relations
	 * @param object
	 * @return
	 */
	private StringBuffer buildQuery(String subject , String predicate,String[] relations,String object) {
		StringBuffer output = new StringBuffer();
		if (relations.length > 1) {
			for (int i = 0; i < relations.length - 1; i++) {
				output.append(" { ?" + subject+ " " + predicate +":" + relations[i]	+ " ?" + object +" } UNION");
			}
		}
		output.append(" { ?" + subject+ " " + predicate +":" + relations[relations.length - 1] + " ?" + object +" }");
		return output;
	}
}

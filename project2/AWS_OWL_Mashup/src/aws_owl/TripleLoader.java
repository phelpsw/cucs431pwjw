package aws_owl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.Iterator;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.mindswap.pellet.jena.PelletReasonerFactory;
import org.w3c.dom.*;

import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntClass;
import com.hp.hpl.jena.ontology.ProfileRegistry;
import com.hp.hpl.jena.rdf.model.*;
import com.hp.hpl.jena.reasoner.Reasoner;
import com.hp.hpl.jena.reasoner.ValidityReport;
import com.hp.hpl.jena.vocabulary.*;

import org.xml.sax.SAXException;

public class TripleLoader {

	Document xml;
	int type;
	static String aws = "http://www.owl-ontologies.com/Ontology1209425965.owl#";
	Resource subj;
	Property isWrittenBy;
	Property isPerformedBy;
	Property isPerformerIn;
	Property isAuthorOf;
	Property hasTitle;
	Model model;
	
	public TripleLoader()
	{
		model = ModelFactory.createDefaultModel();
		isWrittenBy = model.createProperty(aws, "isWrittenBy");
		isPerformedBy = model.createProperty(aws, "isPerformedBy");
		isPerformerIn = model.createProperty(aws, "isPerformerIn");
		isAuthorOf = model.createProperty(aws,"isAuthorOf");
		hasTitle = model.createProperty(aws,"hasTitle");
	}
	
	public void Load(Document xml)
	{
		this.type = AwsHandler.type;
		this.xml = xml;
		Node items = xml.getElementsByTagName("Items").item(0);
		if(items.getFirstChild().getFirstChild().getTextContent().equals("True"))
		{
			NodeList itemList = xml.getElementsByTagName("Item");
			NodeList itemAttributesList = xml.getElementsByTagName("ItemAttributes");
			NodeList Titles = xml.getElementsByTagName("Title");
			for(int i=0; i<itemList.getLength(); i++)
				nodeToTriple((Element)itemList.item(i),(Element)itemAttributesList.item(i),
						(Element)Titles.item(i));
		}
		else (new Exception("Invalid search query")).printStackTrace();
		
		writeToFile();
		Inferencer();
	}
	
	private void nodeToTriple(Element item, Element itemAttributes, Element Title)
	{
		String ASIN = item.getFirstChild().getTextContent();
		Resource[] subjects = addAgents(itemAttributes, ASIN, type);
		String title = Title.getTextContent();
		Resource product = model.createResource(aws+ASIN);
		String productCategory = "";
		String agentCategory = "";
		Property p = null;
		
		switch(type)
		{
		case AwsHandler.BOOK:
			productCategory = "book";
			agentCategory = "Author";
			p = isAuthorOf;
			break;
		case AwsHandler.DVD:
			productCategory = "DVD";
			agentCategory = "Actor";
			p = isPerformerIn;
			break;
		case AwsHandler.MUSIC:
			productCategory = "CD";
			agentCategory = "Artist";
			p = isPerformerIn;
			break;
		}
		
		model.add(product, RDF.type, aws+productCategory);
		model.add(product, hasTitle, model.createLiteral(title));
		for(int i=0; i<subjects.length; i++)
		{
			model.add(subjects[i], p, product);
			model.add(subjects[i], RDF.type, aws+agentCategory);
		}
	}
	
	private Resource[] addAgents(Element itemAttributes, String ASIN, int type)
	{
		String agentTag = itemAttributes.getFirstChild().getNodeName();
		Element index = (Element)itemAttributes.getFirstChild();
		//subj = model.createResource(aws+"Agent:"+agent);
		int count = 0;
		while(agentTag == index.getTagName())
		{
			index = (Element)index.getNextSibling();
			count++;
		}
		
		Resource[] subjects = new Resource[count];
		index = (Element)itemAttributes.getFirstChild();
		for(int i=0; i<count; i++)
		{
			subjects[i] = model.createResource(aws+"Agent:"+index.getTextContent());
			index = (Element)index.getNextSibling();
		}
		return subjects;
	}
	
	private void writeToFile()
	{
		try{
			OutputStream out = new FileOutputStream("out.xml");
			RDFWriter writer = model.getWriter("N3");
			writer.setProperty("showXmlDeclaration","true");
		    writer.setProperty("tab","8");
		    writer.setProperty("relativeURIs","same-document,relative");
			writer.write(model, out, null);
			out.close();
			}
			catch(Exception e){}
	}
	
	private void Inferencer()
	{
		File owl = new File("../aws_proj2.owl");
		FileReader fr = null;
		OntModel ontmodel;
		
		try{
			fr = new FileReader(owl);
			ontmodel = ModelFactory.createOntologyModel( PelletReasonerFactory.THE_SPEC );
			
			
			ontmodel.read(fr, null);
			ValidityReport report = ontmodel.validate();
	        printIterator( report.getReports(), "Validation Results" );
			
	        
	        
	        
	        /*
			OutputStream out = new FileOutputStream("ontout.xml");
			RDFWriter writer = ontmodel.getWriter("N-TRIPLES");
			writer.setProperty("showXmlDeclaration","true");
		    writer.setProperty("tab","8");
		    writer.setProperty("relativeURIs","same-document,relative");
			writer.write(ontmodel, out, null);
			out.close();
			*/
		}
		catch(Exception e){}
		
	}
	public static void printIterator(Iterator i, String header) {
        System.out.println(header);
        for(int c = 0; c < header.length(); c++)
            System.out.print("=");
        System.out.println();
        
        if(i.hasNext()) {
	        while (i.hasNext()) 
	            System.out.println( i.next() );
        }       
        else
            System.out.println("<EMPTY>");
        
        System.out.println();
    }
}

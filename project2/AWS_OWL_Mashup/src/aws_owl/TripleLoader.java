package aws_owl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Iterator;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.mindswap.pellet.jena.PelletReasoner;
import org.mindswap.pellet.jena.PelletReasonerFactory;
import org.w3c.dom.*;

import com.hp.hpl.jena.datatypes.RDFDatatype;
import com.hp.hpl.jena.datatypes.TypeMapper;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntClass;
import com.hp.hpl.jena.ontology.ProfileRegistry;
import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.ResultSetFormatter;
import com.hp.hpl.jena.rdf.model.*;
//import com.hp.hpl.jena.reasoner.Reasoner;
import org.mindswap.pellet.owlapi.Reasoner;
import com.hp.hpl.jena.reasoner.ReasonerRegistry;
import com.hp.hpl.jena.reasoner.ValidityReport;
import com.hp.hpl.jena.util.FileManager;
import com.hp.hpl.jena.util.iterator.ExtendedIterator;
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
	Property isConnectedTo;
	Property hasName;
	OntModel model;
	Resource Author;
	Resource Artist;
	Resource DVD;
	
	public TripleLoader()
	{
		model = ModelFactory.createOntologyModel( PelletReasonerFactory.THE_SPEC );
		LoadOntology();
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
		RunQuery();
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
				productCategory = "Book";
				agentCategory = "Author";
				p = isAuthorOf;
				break;
			case AwsHandler.DVD:
				productCategory = "DVD";
				agentCategory = "Actor";
				model.add(product, RDF.type, DVD);
				p = isPerformerIn;
				break;
			case AwsHandler.MUSIC:
				productCategory = "CD";
				agentCategory = "Artist";
				p = isPerformerIn;
				break;
		}
		
		//System.out.println(lit.getDatatype());
		//model.add(product, RDF.type, model.getResource(aws+productCategory));
		Literal lit =  model.createLiteral(title);
        model.add(product, hasTitle, lit);
        //model.add(product, hasTitle, model.createResource(aws+Utility.forURL(title)));
        for(int i=0; i<subjects.length; i++)
        {
                model.add(subjects[i], p, product);
                if(i != 0) model.add(subjects[0], isConnectedTo, subjects[i]);
                //model.add(subjects[i], RDF.type, model.getResource(aws+agentCategory));
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
			subjects[i] = model.createResource(aws+Utility.forURL(index.getTextContent()));
			model.add(subjects[i], hasName, index.getTextContent());
			index = (Element)index.getNextSibling();
		}
		return subjects;
	}
	
	private void writeToFile()
	{
		try{
			OutputStream out = new FileOutputStream("out.xml");
			RDFWriter writer = model.getWriter("RDF/XML");
			writer.setProperty("showXmlDeclaration","true");
		    writer.setProperty("tab","8");
		    writer.setProperty("relativeURIs","same-document,relative");
			writer.write(model, out, null);
			out.close();
			}
			catch(Exception e){}
	}
	
	private void LoadOntology()
	{
		Model schema = FileManager.get().loadModel("file:../aws_proj2.owl");
		model.add(schema);
		isWrittenBy = model.getProperty(aws, "isWrittenBy");
		isPerformedBy = model.getProperty(aws, "isPerformedBy");
		isPerformerIn = model.getProperty(aws, "isPerformerIn");
		isAuthorOf = model.getProperty(aws,"isAuthorOf");
		hasTitle = model.getProperty(aws,"hasTitle");
		isConnectedTo = model.getProperty(aws,"isConnectedTo");
		DVD = model.getResource(aws+"DVD");
		hasName = model.getProperty(aws,"hasName");
	}
	
	private void Inferencer()
	{
	    ValidityReport report = model.validate();
        printIterator( report.getReports(), "Validation Results" );	
        /*
        ExtendedIterator iterator = model.listIndividuals();
        while(iterator.hasNext())
        {
        	System.out.println(iterator.next());
        }
        Resource cd = model.getOntResource(aws+"CD");
        System.out.println(cd);
        */
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
	
	public void RunQuery()
	{
		//		 Create a new query
		String queryString = 
			"PREFIX aws: <"+aws+"> " +
			"SELECT ?x " +
			"WHERE {" +
			"      ?x aws:hasTitle \'Cats+-+The+Musical+%28Commemorative+Edition%29\'"+". " +
			"      }";

		Query query = QueryFactory.create(queryString);

		//		 Execute the query and obtain results
		QueryExecution qe = QueryExecutionFactory.create(query, model);
		//ResultSet result = (ResultSet)qe.execSelect();
		
		//		 Output query results	
		ResultSetFormatter.out(System.out, qe.execSelect(), query);

		//		 Important - free up resources used running the query
		qe.close();
	}
	
}

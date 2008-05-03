package aws_owl;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.*;

import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.ontology.ProfileRegistry;
import com.hp.hpl.jena.rdf.model.*;
import com.hp.hpl.jena.vocabulary.*;

import org.xml.sax.SAXException;

public class TripleLoader {

	Document xml;
	int type;
	
	static String isWrittenBy = "";
	static String isPerformedBy = "";
	static String isAuthorOf = "";
	static String isPerformerOf = "";

	
	
	public TripleLoader(Document xml, int type)
	{
		this.xml = xml;
		this.type = type;
	}
	
	public void Load()
	{
		Node items = xml.getElementsByTagName("Items").item(0);

		if(items.getFirstChild().getFirstChild().getTextContent().equals("True"))
		{
			NodeList itemList = xml.getElementsByTagName("Item");
			for(int i=0; i<itemList.getLength(); i++)
				nodeToTriple(itemList.item(i));
		}
		else (new Exception("Invalid search query")).printStackTrace();
	}
	
	private void nodeToTriple(Node n)
	{
		NodeList list = n.getChildNodes();
		Element itemAttributes = null;
		for(int i=0; i<list.getLength(); i++)
		{
			Node  node= list.item(i);
			if(node.getNodeName()=="ItemAttributes") itemAttributes = (Element)node;
		}
		
		Model model = ModelFactory.createDefaultModel();
		Resource subj = model.createResource(itemAttributes.getFirstChild().getTextContent());
		OntModel m = ModelFactory.createOntologyModel(OntModelSpec.OWL_DL_MEM, model);
		Property pisWrittenBy = model.createProperty("", isWrittenBy);
		
		
		switch(type)
		{
		case AwsHandler.BOOK:
			break;
		case AwsHandler.DVD:
			break;
		case AwsHandler.MUSIC:
			break;
		}
		
		
	}
}

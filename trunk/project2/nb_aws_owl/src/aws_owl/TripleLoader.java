package aws_owl;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Iterator;
import org.mindswap.pellet.jena.PelletReasonerFactory;
import org.w3c.dom.*;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.ResultSetFormatter;
import com.hp.hpl.jena.rdf.model.*;
import com.hp.hpl.jena.reasoner.ValidityReport;
import com.hp.hpl.jena.util.FileManager;
import com.hp.hpl.jena.vocabulary.*;
import java.util.ArrayList;

public class TripleLoader 
{
    public static String aws = "http://www.owl-ontologies.com/Ontology1209425965.owl#";
    OntModel model;
    Property isWrittenBy;
    Property isPerformedBy;
    Property isPerformerIn;
    Property isAuthorOf;
    Property hasTitle;
    Property isConnectedTo;
    Property hasName;
    Property isInList;
    Property hasProduct;
    Property hasListName;
    Resource Author;
    Resource Artist;
    Resource DVD;
    Resource BOOK;
    Resource CD;

    public TripleLoader()
    {
        initializeModel();
    }
    
    public void initializeModel()
    {
        model = ModelFactory.createOntologyModel(PelletReasonerFactory.THE_SPEC);
        loadOntology();
    }

    public void load(Document xml, AWSSearchType type) throws InvalidAWSResponseException
    {
        Node items = xml.getElementsByTagName("Items").item(0);

        // check validity of aws search result
        if(items.getFirstChild().getFirstChild().getTextContent().equals("True"))
        {
            NodeList itemList = xml.getElementsByTagName("Item");
            NodeList itemAttributesList = xml.getElementsByTagName("ItemAttributes");
            NodeList listmania = xml.getElementsByTagName("ListmaniaLists");
            NodeList Titles = xml.getElementsByTagName("Title");
            for(int i=0; i<itemList.getLength(); i++)
                nodeToTriple((Element)itemList.item(i),
                        (Element)itemAttributesList.item(i),
                        (Element)Titles.item(i), (Element)listmania.item(i),
                        type);
        }
        else 
            throw new InvalidAWSResponseException();
        writeToFile("out.xml");
    }

    private void nodeToTriple(Element item, Element itemAttributes, Element Title,Element listmaniaLists, AWSSearchType type)
    {
        String ASIN = item.getFirstChild().getTextContent();
        Resource[] subjects = addAgents(itemAttributes, ASIN);
        String title = Title.getTextContent();
        Resource product = model.createResource(aws+ASIN);
        String productCategory = "";
        String agentCategory = "";
        Property p = null;
        NodeList listmania = null;
        if(listmaniaLists != null) listmania = listmaniaLists.getElementsByTagName("ListmaniaList");
        if(listmania != null)
            for(int i=0; i< listmania.getLength(); i++)
            {
                Node n = listmania.item(i);
                Resource list = model.createResource(aws+n.getFirstChild().getTextContent());
                model.add(product, isInList, list);
                model.add(list, hasListName, (String)n.getLastChild().getTextContent());
            }
        switch(type.getType())
        {
            case AWSSearchType.BOOK:
                productCategory = "Book";
                agentCategory = "Author";
                model.add(product, RDF.type, BOOK);
                p = isAuthorOf;
                break;
            case AWSSearchType.DVD:
                productCategory = "DVD";
                agentCategory = "Actor";
                model.add(product, RDF.type, DVD);
                p = isPerformerIn;
                break;
            case AWSSearchType.MUSIC:
                productCategory = "CD";
                agentCategory = "Artist";
                model.add(product, RDF.type, CD);
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

    private Resource[] addAgents(Element itemAttributes, String ASIN)
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

    public void writeToFile(String filename)
    {
        try{
            OutputStream out = new FileOutputStream(filename);
            RDFWriter writer = model.getWriter("RDF/XML");
            writer.setProperty("showXmlDeclaration","true");
            writer.setProperty("tab","8");
            writer.setProperty("relativeURIs","same-document,relative");
            writer.write(model, out, null);
            out.close();
        } 
        catch (FileNotFoundException fnfe)
        {
            fnfe.printStackTrace();
        } 
        catch (IOException ioe)
        {
            ioe.printStackTrace();
        }
    }
    
    public void readFromFile(String filename)
    {
        Model in = FileManager.get().loadModel(filename);
        model.add(in);
    }

    private void loadOntology()
    {
        Model schema = FileManager.get().loadModel("file:aws_proj2.owl");
        model.add(schema);
        isWrittenBy = model.getProperty(aws, "isWrittenBy");
        isPerformedBy = model.getProperty(aws, "isPerformedBy");
        isPerformerIn = model.getProperty(aws, "isPerformerIn");
        isAuthorOf = model.getProperty(aws,"isAuthorOf");
        hasTitle = model.getProperty(aws,"hasTitle");
        isConnectedTo = model.getProperty(aws,"isConnectedTo");
        hasName = model.getProperty(aws,"hasName");
        hasListName = model.getProperty(aws, "hasListName");
        hasProduct = model.getProperty(aws, "hasProduct");
        isInList = model.getProperty(aws, "isInList");
        DVD = model.getResource(aws+"DVD");
        BOOK = model.getResource(aws+"Book");
        CD = model.getResource(aws+"CD");
    }

    public String validateOntology()
    {
        ValidityReport report = model.validate();
        
        if(report.isClean() && report.isValid())
        {
            return "Ontology is Clean and Valid\n";
        } else {
            String header = "Validation Error Information\n";
            header += "Clean: " + report.isClean() + " Valid: " + report.isValid();
            return stringFormatIterator(report.getReports(), header);
        }
        //printIterator( report.getReports(), "Validation Results" );	
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

    public String stringFormatIterator(Iterator i, String header) 
    {
        String output="";
        output += header + "\n";
        for(int c = 0; c < header.length(); c++)
            output += "=";
        output += "\n";

        if(i.hasNext()) 
        {
            while (i.hasNext())
                output += i.next() + "\n";
        }
        else
            output += "<EMPTY>\n";
        output += "\n";
        
        return output;
    }

    public String runQuery(String queryString)
    {
        Query query = QueryFactory.create(queryString);

        // Execute the query and obtain results
        QueryExecution qe = QueryExecutionFactory.create(query, model);

        // Output query results	
        String returnval = ResultSetFormatter.asXMLString(qe.execSelect());

        // Important - free up resources used running the query
        qe.close();

        return returnval;
    }
    
    public class InvalidAWSResponseException extends Exception
    {
        public InvalidAWSResponseException()
        {
        }
    }
    public ArrayList<Statement> populate(String type)
    {
        Property p = null;
        
        if(type.equals("agent")) p = hasName;
        else if (type.equals("product")) p = hasTitle;
        else if (type.equals("list")) p = hasListName;
        //NodeIterator iterator = model.listObjectsOfProperty(p);
        StmtIterator iterator = model.listStatements(new SimpleSelector(null, p, (String)null));
        ArrayList<Statement> list = new ArrayList<Statement>();
        while(iterator.hasNext())
        {
            Statement n = iterator.nextStatement();
            list.add(n);
        }
        return list;
    }

}

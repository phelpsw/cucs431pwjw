package aws_owl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.*;

import org.xml.sax.SAXException;

import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.ontology.ProfileRegistry;
import com.hp.hpl.jena.rdf.model.*;
import com.hp.hpl.jena.vocabulary.*;


public class AwsHandler 
{
	public AwsHandler()
	{
	}
	
	public Document query(String agent, AWSSearchType type) 
            throws MalformedURLException, 
                IOException, 
                ParserConfigurationException,
                SAXException
	{
            String url;
            if(type.getType() == AWSSearchType.BOOK) {
                url = "http://ecs.amazonaws.com/onca/xml?Service=AWSECommerceService&ResponseGroup=Large&Operation=ItemSearch&AWSAccessKeyId=0RBFG1JEYFQRVTPWYQ02&SearchIndex=Books&Author="+Utility.forURL(agent);
            } else if (type.getType() == AWSSearchType.DVD) { 
                url = "http://ecs.amazonaws.com/onca/xml?Service=AWSECommerceService&ResponseGroup=Large&Operation=ItemSearch&AWSAccessKeyId=0RBFG1JEYFQRVTPWYQ02&SearchIndex=DVD&Actor="+Utility.forURL(agent);
            } else if (type.getType() == AWSSearchType.MUSIC) {
                url = "http://ecs.amazonaws.com/onca/xml?Service=AWSECommerceService&ResponseGroup=Large&Operation=ItemSearch&AWSAccessKeyId=0RBFG1JEYFQRVTPWYQ02&SearchIndex=Music&Artist="+Utility.forURL(agent);
            } else { // unknown
                return null;
            }

            URL datafile = new URL(url);
            URLConnection conn = datafile.openConnection();
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();

            return builder.parse(conn.getInputStream());
	}

}


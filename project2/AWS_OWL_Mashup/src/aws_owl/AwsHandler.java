package aws_owl;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.*;

import org.xml.sax.SAXException;

public class AwsHandler {

	public final static int BOOK = 0;
	public final static int DVD = 1;
	public final static int MUSIC = 2;
	
	public AwsHandler()
	{
	}
	
	public Document query(String agent, int type)
	{
		String url;
		if(type == BOOK) {
			url = "http://ecs.amazonaws.com/onca/xml?Service=AWSECommerceService&ResponseGroup=Large&Operation=ItemSearch&AWSAccessKeyId=0RBFG1JEYFQRVTPWYQ02&SearchIndex=Books&Author="+forURL(agent);
		} else if (type == DVD) { 
			url = "http://ecs.amazonaws.com/onca/xml?Service=AWSECommerceService&ResponseGroup=Large&Operation=ItemSearch&AWSAccessKeyId=0RBFG1JEYFQRVTPWYQ02&SearchIndex=DVD&Actor="+forURL(agent);
		} else if (type == MUSIC) {
			url = "http://ecs.amazonaws.com/onca/xml?Service=AWSECommerceService&ResponseGroup=Large&Operation=ItemSearch&AWSAccessKeyId=0RBFG1JEYFQRVTPWYQ02&SearchIndex=Music&Artist="+forURL(agent);
		} else {
			// unknown
			return null;
		}
		
		try {
			
			URL datafile = new URL(url);
			/*
			URLConnection conn = datafile.openConnection();
			InputStreamReader isr = new InputStreamReader(conn.getInputStream());
			BufferedReader reader = new BufferedReader(isr);
			String str;
			while ((str = reader.readLine()) != null) {
				System.out.println(str+"\n");
			}
			*/
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			return builder.parse(url);
		} catch (Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	

	
	private String forURL(String frag)
	{
		String result = null;
		try 
		{
			result = URLEncoder.encode(frag, "UTF-8");
	    } catch (UnsupportedEncodingException ex) {
	    	throw new RuntimeException("UTF-8 not supported", ex);
	    }
		return result;
	}
}


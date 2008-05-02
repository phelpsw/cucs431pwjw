package aws_owl;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class AwsHandler {

	public static int BOOK = 0;
	public static int DVD = 1;
	public static int MUSIC = 2;
	
	public AwsHandler()
	{
	}
	
	public void query(String agent, int type)
	{
		String url;
		if(type == BOOK) {
			url = "http://ecs.amazonaws.com/onca/xml?Service=AWSECommerceService&Operation=ItemSearch&AWSAccessKeyId=0RBFG1JEYFQRVTPWYQ02&ResponseGroup=Large&SearchIndex=Books&Author="+forURL(agent);
		} else if (type == DVD) { 
			url = "http://ecs.amazonaws.com/onca/xml?Service=AWSECommerceService&Operation=ItemSearch&AWSAccessKeyId=0RBFG1JEYFQRVTPWYQ02&ResponseGroup=Large&SearchIndex=DVD&Actor="+forURL(agent);
		} else if (type == MUSIC) {
			url = "http://ecs.amazonaws.com/onca/xml?Service=AWSECommerceService&Operation=ItemSearch&AWSAccessKeyId=0RBFG1JEYFQRVTPWYQ02&ResponseGroup=Large&SearchIndex=Music&Artist="+forURL(agent);
		} else {
			// unknown
			return;
		}
		
		try {
			System.out.println(url);
			URL datafile = new URL(url);
			URLConnection conn = datafile.openConnection();
			
			InputStreamReader isr = new InputStreamReader(conn.getInputStream());
			BufferedReader reader = new BufferedReader(isr);
			String str;
			while ((str = reader.readLine()) != null) {
				System.out.println(str);
			}
		} catch (Exception e){
			e.printStackTrace();
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


package aws_owl;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class Utility {
	public static String forURL(String frag)
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

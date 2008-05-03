package aws_owl;
import org.w3c.dom.*;

public class QueryTool {

		
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		AwsHandler ah = new AwsHandler();
		
		TripleLoader t = new TripleLoader(ah.query("mariah carey", AwsHandler.MUSIC), AwsHandler.MUSIC);
		t.Load();
		
	}

}

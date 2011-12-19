import javax.swing.JFrame;

/**
 * Tester class
 * @author MasonSilber
 *
 */
public class Tester {

		/**
	 * Main method
	 * @param args command line arguments 
	 * This method just creates the initial window and displays it
		 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception 
	{
		HTMLParser.setWordsThatDontCount();
		
		JFrame inputFrame = new InputWindow("Make a word cloud from a URL");
		inputFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		inputFrame.pack();
		inputFrame.setVisible(true);
	}

}

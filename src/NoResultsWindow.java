import java.awt.BorderLayout;
import java.awt.event.*;
//import java.util.ArrayList;
//import java.util.HashSet;

import javax.swing.*;


/**
 * SearchResultWindow
 * @author MasonSilber
 * This class is the window that displays the results (or lack thereof) of your search 
 */
public class NoResultsWindow extends JFrame
{
	private static final long serialVersionUID = -211979331959200539L;
	
	private static final int NO_RESULTS_FRAME_WIDTH = 300;
	private static final int NO_RESULTS_FRAME_HEIGHT = 100;
	
	JButton close;
	
	/**
	 * NoResultsWindow constructor
	 * This constructor is specifically for a search for a node that doens't exist
	 * It creates the "No results" dialog all in this method, without calling any other methods
	 * (aside from createCloseButton)
	 */
	public NoResultsWindow()
	{
		super("Search Results");
		JLabel noMatches = new JLabel("There were no results for your search");
		createCloseButton();
		JPanel noResultsPanel = new JPanel();
		noResultsPanel.setLayout(new BorderLayout());
		noResultsPanel.add(noMatches,BorderLayout.NORTH);
		noResultsPanel.add(close,BorderLayout.CENTER);

		add(noResultsPanel);
		setSize(NO_RESULTS_FRAME_WIDTH,NO_RESULTS_FRAME_HEIGHT);
	}
	
	/**
	 * createCloseButton
	 * This method creates the close button that appears on either possibility
	 * of search result windows
	 */
	public void createCloseButton()
	{
		close = new JButton("Close");

		/**
		 * CloseActionListener
		 * @author MasonSilber
		 * This is the action listener that gets added to the close button
		 * so that the window will close
		 */
		class CloseActionListener implements ActionListener
		{
			/**
			 * actionPerformed
			 * This method tells the close button to close the dialog box when it's clicked
			 */
			public void actionPerformed(ActionEvent event)
			{
				dispose();
			}
		}
		
		close.addActionListener(new CloseActionListener());
	}
}

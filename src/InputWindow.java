import javax.swing.*;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;

import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 * InputWindow
 * @author MasonSilber
 * This class is the first window the user sees when the program is launched
 * It allows the user to generate a word cloud either from a serialized file or from
 * a list of URLs
 */
public class InputWindow extends JFrame 
{

	private static final long serialVersionUID = -2944046138844724312L;
	private static final int FRAME_WIDTH = 500;
	private static final int FRAME_HEIGHT = 150;
	
	private JPanel inputPanel;
	private JPanel buttonPanel;
	
	private JTextArea URLTextArea;
	private JTextArea numberOfAdjacenciesTextArea;
	private JButton fetchButton;
	private JButton loadButton;
	
	private ArrayList<Node> loadedNodes;
	private ArrayList<String> loadedURLs;
	private String loadedName;
	
	/**
	 * Input window
	 * @param title the title of the window, used in JFrame's constructor through "super"
	 * This method creates the initial window, and calls all the other initialization methods
	 */
	public InputWindow(String title)
	{
		super(title);
		
		loadedNodes = null;
		loadedURLs = null;
		loadedName = null;
		
		createFetchButton();
		createLoadButton();
		createTextAreas();
		createPanel();

		setSize(FRAME_WIDTH,FRAME_HEIGHT);
	}
	
	/**
	 * Create Panel
	 * This method creates the main panel to hold the components in the home window
	 */
	public void createPanel()
	{
		inputPanel = new JPanel();
		buttonPanel = new JPanel();
		JPanel adjacenciesInputPanel = new JPanel();
		
		buttonPanel.setLayout(new BorderLayout());
		buttonPanel.add(fetchButton,BorderLayout.NORTH);
		buttonPanel.add(loadButton,BorderLayout.CENTER);
		
		inputPanel.add(new JLabel("Enter URLs:"));
		JScrollPane scrollPane = new JScrollPane(URLTextArea);
		inputPanel.add(scrollPane);
				
		adjacenciesInputPanel.setLayout(new GridLayout(1,2));
		adjacenciesInputPanel.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		adjacenciesInputPanel.add(new JLabel("Enter number of adjacencies: "));
		adjacenciesInputPanel.add(numberOfAdjacenciesTextArea);
		buttonPanel.add(adjacenciesInputPanel,BorderLayout.SOUTH);
		
		inputPanel.add(buttonPanel);
		add(inputPanel);
	}
	
	/**
	 * CreateLoadButton
	 * This method creates the button that can load saved word clouds
	 */
	public void createLoadButton()
	{
		loadButton = new JButton("Load Saved Cloud");
		
		/**
		 * LoadGraphListener
		 * @author MasonSilber
		 * This is the action listener that uses a JFileChooser to allow the user
		 * to choose a saved word cloud to load, so they don't have to query the internet
		 * again
		 */
		class LoadGraphListener implements ActionListener
		{
			@SuppressWarnings("unchecked")
			/**
			 * actionPerformed
			 * This method is the one that gets triggered when the load button is pressed
			 * It presents a dialog box and loads the file from memory
			 */
			public void actionPerformed(ActionEvent event)
			{
				JFileChooser fileChooser = new JFileChooser();
				
				if(event.getSource() == loadButton)
				{
					int returnVal = fileChooser.showOpenDialog(InputWindow.this);
					
					if(returnVal == JFileChooser.APPROVE_OPTION)
					{
						//Get file from the file chooser
						File file = fileChooser.getSelectedFile();
						loadedName = file.getName();
						try 
						{
							//Read in the objects from the file
							ObjectInputStream inStream = new ObjectInputStream(new FileInputStream(file));
							ArrayList<String> URLs = (ArrayList<String>)inStream.readObject();
							loadedURLs = URLs;
							
							ArrayList<Node> loaded = (ArrayList<Node>)inStream.readObject();
							loadedNodes = loaded;
							
							//Create the graph window from there
							createGraphWindow();
						} 
						catch (Exception e) 
						{
							e.printStackTrace();
						}						
					}
					else
					{
						return;
					}
				}
			}
		}
		
		loadButton.addActionListener(new LoadGraphListener());
	}
	
	/**
	 * CreateFetchButton
	 * This method creates the button that queries the web to get the 
	 * HTML to generate the word cloud
	 */
	public void createFetchButton()
	{
		fetchButton = new JButton("Generate Cloud");
				
		/**
		 * GenerateGraphListener
		 * @author MasonSilber
		 * This class creates the action listener that is added to the
		 * button that generates the cloud
		 */
		class GenerateGraphListener implements ActionListener
		{
			/**
			 * actionPerformed
			 * This method is what's called when the fetch button is pressed.
			 * It creates a new graph window with the newly generated word cloud
			 */
			public void actionPerformed(ActionEvent event)
			{
				createGraphWindow();
			}
		}
		
		ActionListener listener = new GenerateGraphListener();
		fetchButton.addActionListener(listener);
	}
	
	/**
	 * createGraphWindow
	 * This method creates the graph window using either the
	 * deserialized objects or the URLs in the text box
	 */
	public void createGraphWindow()
	{
		GraphWindow graphFrame;
		//If the graph is being generated from the URLs
		if (loadedNodes == null) 
		{
			StringTokenizer tokenizer = new StringTokenizer(
					URLTextArea.getText(), "\n", false);
			//Checks to see if there have been URLs requested
			if (tokenizer.countTokens() == 0) {
				URLTextArea.setText("Please enter one or more URLs");
				return;
			}

			//Does not take more than 10 URLs
			else if (tokenizer.countTokens() > 10) {
				URLTextArea.setText("Please enter less than 10 URLs"
						+ URLTextArea.getText());
				return;
			}
			
			if(numberOfAdjacenciesTextArea.getText().length() < 1 || numberOfAdjacenciesTextArea.getText().length() > 3 || !isInteger(numberOfAdjacenciesTextArea.getText()) || Integer.parseInt(numberOfAdjacenciesTextArea.getText()) < 1)
			{
				numberOfAdjacenciesTextArea.setText(" Enter a number 1-100");
				return;
			}
			
			ArrayList<String> URLs = new ArrayList<String>();
			while (tokenizer.hasMoreTokens()) {
				URLs.add(tokenizer.nextToken());
			}
			graphFrame = new GraphWindow(URLs, Integer.parseInt(numberOfAdjacenciesTextArea.getText()));
		}
		//If the graph is being generated from a loaded file
		else
		{
			graphFrame = new GraphWindow(loadedNodes,loadedURLs,loadedName);
		}

		graphFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		graphFrame.setVisible(true);
	}
	
	/**
	 * createTextArea
	 * This method creates the text area in which the user can input
	 * URLs to add to the word cloud
	 */
	public void createTextAreas()
	{
		URLTextArea = new JTextArea("Enter up to 10 URLs\n(One per line).\nFor more URLs, give the application \nmore time to load.",5,20);
		URLTextArea.setEditable(true);

		numberOfAdjacenciesTextArea = new JTextArea(" Enter a number 1-100",1,2);
		numberOfAdjacenciesTextArea.setEditable(true);
		numberOfAdjacenciesTextArea.setBorder(BorderFactory.createLineBorder(Color.BLACK));
	}
	
	public boolean isInteger(String s)
	{
		try
		{
			Integer.valueOf(s);
			return true;
		}
		catch(NumberFormatException e)
		{
			return false;
		}
	}
}

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;

public class NodeDetailWindow extends JFrame
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4695465381677564640L;
	private static final int FRAME_WIDTH=200;
	private static final int FRAME_HEIGHT=400;

	int frequency;
	ArrayList<String> URLs;
	ArrayList<String> previousWords;
	ArrayList<String> followingWords;

	private JTextArea nodeInfo;

	private JButton close;
	private JPanel panel;
	
	public NodeDetailWindow(int freq, ArrayList<String> sites, ArrayList<String> prev, ArrayList<String> fol)
	{
		frequency = freq;
		URLs = sites;
		previousWords = prev;
		followingWords = fol;
		createPanel();

		add(panel);
		setSize(FRAME_WIDTH,FRAME_HEIGHT);
	}

	public void createPanel()
	{
		String l = "";
		
		l+="Frequency: " + frequency + "\n\n";
		
		l+="URLs:\n";

		for(String s:URLs)
		{
			l+=s+"\n";
		}

		l+="\nPrevious Words:\n";

		for(String s:previousWords)
		{
			l+=s+"\n";
		}

		l+="\nFollowing Words:\n";

		if(followingWords != null)
		{
			for(String s:followingWords)
			{
				l+=s+"\n";
			}
		}
		

		nodeInfo = new JTextArea(50,15);
		nodeInfo.setEditable(false);
		nodeInfo.setText(l);
	
		JScrollPane scrollPane = new JScrollPane(nodeInfo);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setBorder(BorderFactory.createEmptyBorder(4,4,4,4));
		
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
		
		panel = new JPanel();
		panel.setLayout(new BorderLayout());
		panel.add(scrollPane,BorderLayout.NORTH);
		panel.add(close,BorderLayout.CENTER);
	}
}

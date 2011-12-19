import org.jsoup.*;
import org.jsoup.nodes.Document;


import java.io.File;
import java.lang.Exception;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.PriorityQueue;
import java.util.Scanner;


/**
 * HTMLParser
 * @author MasonSilber
 * Class of static methods that parse the HTML and process it appropriately
 */
public final class HTMLParser {

	public static ArrayList<String> wordsThatDontCount = new ArrayList<String>();
	
	/**
	 * HTMLParser constructor
	 * Never called, hence it being a private function
	 */
	private HTMLParser()
	{
		
	}
	
	/**
	 * setWordsThatDontCount
	 * @throws Exception Would be an IOException of some kind
	 * This method reads in words that shouldn't be counted towards the world cloud 
	 * from a text file I created (and submitted with the program)
	 * O(n)
	 */
	public static void setWordsThatDontCount() throws Exception
	{
		File file = new File("./preps.txt");
		Scanner scan = new Scanner(file);
		
		while(scan.hasNextLine())
		{
			String str = scan.nextLine();
			wordsThatDontCount.add(str);
		}
	}
	
	/**
	 * getWords
	 * @param URLs The URLs to parse and generate the nodes for the word cloud
	 * @return The list of Nodes used to generate the word cloud
	 * Total time complexity is O(n^4)
	 * More documentation within the method on the complexity 
	 * Note: the notation uses n instead of V and E because at this point there is no graph
	 */
	public static ArrayList<Node> getWords(ArrayList<String> URLs, int adjacencies) 
	{
		Hashtable<String,Node> wordFrequencies = new Hashtable<String,Node>();

		ArrayList<Node> allTopNodes = new ArrayList<Node>();

		//O(n^4) (contains an O(n^3) block within it)
		for(String URL : URLs)
		{
			ArrayList<Node> topNodes = new ArrayList<Node>();

			try
			{
				//Use the Jsoup library to parse the HTML
				Document docSite = Jsoup.connect(URL).get();
				Document parsedHTML = Jsoup.parse(docSite.toString());
				String title = parsedHTML.title();
				String body = parsedHTML.text();

				//Using the split method with a regular expression to only keep words with alphabetic characters
				String[] titleWords = title.split("[^a-z^A-Z]");
				String[] bodyWords = body.split("[^a-z^A-Z]");

				for(int i = 0; i < titleWords.length; i++)
				{
					titleWords[i] = titleWords[i].toLowerCase();
				}
				
				for(int i = 0; i < bodyWords.length; i++)
				{
					bodyWords[i] = bodyWords[i].toLowerCase();
				}
				
				//Used to make adjacencies
				String previousNode = null;
				String nextNode = null;
				
				//Going through words in title
				//This block of code is O(n^2), because the Hashtable.containsKey() is called on each word in titleWords
				for(int i = 0; i < titleWords.length; i++)
				{
					if(wordsThatDontCount.contains(titleWords[i]) || titleWords[i].length() < 3)
					{
						continue;
					}

					if(i != 0)
					{
						previousNode = titleWords[i-1];
					}
					if(i != titleWords.length-1)
					{
						nextNode = titleWords[i+1];
					}
					
					String s = titleWords[i];
					if(wordFrequencies.containsKey(s))
					{
						Node oldNode = wordFrequencies.get(s);
						oldNode.incrementFrequency();

						if(!oldNode.getURLs().contains(URL))
						{
							oldNode.addURL(URL);
						}

						if(i != 0)
						{
							oldNode.addPreviousAdjacency(previousNode);
						}
						if(i != titleWords.length-1)
						{
							oldNode.addFollowingAdjacency(nextNode);
						}
						wordFrequencies.put(s,oldNode);
					}
					else
					{
						Node newNode = new Node(s);
						newNode.addURL(URL);
						
						if(i != 0)
						{
							newNode.addPreviousAdjacency(previousNode);
						}
						if(i != titleWords.length-1)
						{
							newNode.addFollowingAdjacency(nextNode);
						}
						wordFrequencies.put(s,newNode);
					}

				}

				previousNode = null;
				nextNode = null;
				
				//Going through all the words in the body of the HTML document
				//This block of code is O(n^2) as well for the same reason (ArrayList.contains() is called on every word in the array)
				for(int i = 0; i < bodyWords.length; i++)
				{
					if(wordsThatDontCount.contains(bodyWords[i]) || bodyWords[i].length() < 3)
					{
						continue;
					}

					if(i != 0)
					{
						previousNode = bodyWords[i-1];
					}
					if(i != bodyWords.length-1)
					{
						nextNode = bodyWords[i+1];
					}
					
					String s = bodyWords[i];
					if(wordFrequencies.containsKey(s))
					{
						Node oldNode = wordFrequencies.get(s);
						oldNode.incrementFrequency();

						if(!oldNode.getURLs().contains(URL))
						{
							oldNode.addURL(URL);
						}
						if(i != 0)
						{
							oldNode.addPreviousAdjacency(previousNode);
						}
						if(i != bodyWords.length-1)
						{
							oldNode.addFollowingAdjacency(nextNode);
						}
						wordFrequencies.put(s,oldNode);
					}
					else
					{
						Node newNode = new Node(s);
						newNode.addURL(URL);
						if(i != 0)
						{
							newNode.addPreviousAdjacency(previousNode);
						}
						if(i != bodyWords.length-1)
						{
							newNode.addFollowingAdjacency(nextNode);
						}
						wordFrequencies.put(s, newNode);
					}
				}
				
				//Use max heap to sort through nodes and figure out which are most frequent in the document
				//Instantiate and initialize heap
				PriorityQueue<Node> maxHeap = new PriorityQueue<Node>(wordFrequencies.size(),new Comparator<Node>()
				{
					public int compare(Node a, Node b)
					{
						return b.compareTo(a);
					}
				});


				Enumeration<String> keys = wordFrequencies.keys();

				//Add all nodes to heap
				//This is O(n) (creating a heap)
				while(keys.hasMoreElements())
				{
					Node newNode = wordFrequencies.get(keys.nextElement());
					maxHeap.add(newNode);
				}

				//Remove the most frequent nodes and keep track of them
				//This has complexity of O(n*log(n))
				for(int i = 0; i < adjacencies; i++)
				{
					topNodes.add(maxHeap.remove());
				}
			}
			catch(Exception error)
			{
				System.out.println("Bad URL. Restart program and try again");
				error.printStackTrace();
			}
			
			//Remove duplicates from the adjacency lists
			//This is O(n^2) (goes through each node, and then adds and removes from a HashSet)
			for(Node n : topNodes)
			{	
				//Remove duplicates
				//This is an O(n) operation
				//Add all the adjacencies to a hash set. This is O(n) and removes the duplicates
				//Then transfer them back into the adjacencies list, overwriting the old the old.
				//However, because we have a complexity of O(n^2) above within this for loop, this section
				//does not contribute to the runtime in the long run
				HashSet<String> h = new HashSet<String>(n.getPreviousAdjacencies());
				ArrayList<String> adj = new ArrayList<String>();
				adj.addAll(h);
				n.setPreviousAdjacencies(adj);
				
				h = new HashSet<String>(n.getFollowingAdjacencies());
				adj = new ArrayList<String>();
				adj.addAll(h);
				n.setFollowingAdjacencies(adj);
			}
			
			//Create the final list of top nodes to return to the graph
			allTopNodes.addAll(topNodes);
		}

		return allTopNodes;
	}
	
}

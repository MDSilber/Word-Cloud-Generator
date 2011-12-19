
import java.io.Serializable;
import java.util.ArrayList;


/**
 * Node Class
 * @author MasonSilber
 * This class stores all the necessary data for use in the graph that will be generated
 */
public class Node implements Comparable<Node>,Serializable
{

	private static final long serialVersionUID = 355091795407261008L;

	private String data;
	private ArrayList<String> previousAdjacencies;
	private ArrayList<String> followingAdjacencies;
	private ArrayList<String> URLs;
	private int frequency;
	private ArrayList<Integer> edges;
	

	
	/**
	 * Node Constructor
	 * @param word The data for the Node
	 * @param freq The number of times the word appeared in the html
	 */
	public Node(String word, int freq)
	{
		data = word;
		previousAdjacencies = new ArrayList<String>();
		followingAdjacencies = new ArrayList<String>();
		URLs = new ArrayList<String>();
		edges = new ArrayList<Integer>();
		frequency = freq;
	}
	
	/**
	 * Node constructor
	 * @param word The data for the node
	 * The frequency in this case is automatically set to 1
	 */
	public Node(String word)
	{
		data = word;
		previousAdjacencies = new ArrayList<String>();
		followingAdjacencies = new ArrayList<String>();
		URLs = new ArrayList<String>();
		edges = new ArrayList<Integer>();
		frequency = 1;
	}

	/**
	 * addEdge
	 * @param edge edge to be added to the list of edges
	 */
	public void addEdge(int edge)
	{
		edges.add(edge);
	}
	
	/**
	 * getEdges
	 * @return list of edges
	 */
	public ArrayList<Integer> getEdges()
	{
		return edges;
	}
	
	/**
	 * setData
	 * @param word new word for the node
	 * This is the setter method for the data member of the node
	 */
	public void setData(String word)
	{
		data = word;
	}
	
	/**
	 * getData
	 * @return the node's data
	 */
	public String getData()
	{
		return data;
	}
	
	/**
	 * addURL
	 * @param URL the URL to be added to the list of URLs associated with this particular node
	 */
	public void addURL(String URL)
	{
		URLs.add(URL);
	}
	
	/**
	 * getURLs
	 * @return The list of URLs associated with this particular node
	 */
	public ArrayList<String> getURLs()
	{
		return URLs;
	}
	
	/**
	 * addPreviousAdjacency
	 * @param aNode Word that will be added to list of previous adjacent words for this node
	 */
	public void addPreviousAdjacency(String aNode)
	{
		if(aNode.length() > 2)
		{
			previousAdjacencies.add(aNode);
		}
		else
		{
			return;
		}
	}
	
	/**
	 * addFollowingAdjacency
	 * @param aNode Word that will be added to list of following words for this node
	 */
	public void addFollowingAdjacency(String aNode)
	{
		if(aNode.length() > 2)
		{
			followingAdjacencies.add(aNode);
		}
		else
		{
			return;
		}
	}
	
	/**
	 * getPreviousAdjacencies
	 * @return The list of previous words adjacent to this particular node
	 */
	public ArrayList<String> getPreviousAdjacencies()
	{
		return previousAdjacencies;
	}
	
	/**
	 * getFollowingAdjacencies
	 * @return the list of following words adjacent to this node
	 */
	public ArrayList<String> getFollowingAdjacencies()
	{
		return followingAdjacencies;
	}
	
	/**
	 * setPreviousAdjacencies
	 * @param newAdjacencies new list of adjacent nodes
	 */
	public void setPreviousAdjacencies(ArrayList<String> newAdjacencies)
	{
		previousAdjacencies = newAdjacencies;
	}
	
	/**
	 * setFollowingAdjacencies
	 * @param newAdjacencies list of new following adjacent nodes
	 */
	public void setFollowingAdjacencies(ArrayList<String> newAdjacencies)
	{
		followingAdjacencies = newAdjacencies;
	}
	
	/**
	 * getPreviousAdjacenciesCount
	 * @return the number of previous adjacent words
	 */
	public int getPreviousAdjacenciesCount()
	{
		return previousAdjacencies.size();
	}
	
	/**
	 * getFollowingAdjacenciesCount
	 * @return the number of following adjacent words
	 */
	public int getFollowingAdjacenciesCount()
	{
		return followingAdjacencies.size();
	}
	
	/**
	 * incrementFrequency
	 * This method increments the frequency by 1
	 */
	public void incrementFrequency()
	{
		frequency++;
	}
	
	/**
	 * setFrequency
	 * @param i the new frequency of the node
	 */
	public void setFrequency(int i)
	{
		frequency = i;
	}
	
	/**
	 * getFrequency
	 * @return frequency member for this node
	 */
	public int getFrequency()
	{
		return frequency;
	}
	/**
	 * compareTo
	 * This method is the interface method for Comparable
	 * It allows comparison between two different nodes
	 */
	public int compareTo(Node aNode) {
		if(this.frequency > aNode.getFrequency())
		{
			return 1;
		}
		else if(this.frequency < aNode.getFrequency())
		{
			return -1;
		}
		else return 0;
	}
	
	/**
	 * hashCode
	 * Overrides hashCode() in order to check for equality between nodes
	 * (in situations where .equals() isn't explicitly called, such as in
	 * ArrayList.contains() instance method, etc.
	 */
	public int hashCode()
	{
		return data.hashCode();
	}
	
	/**
	 * equals
	 * Overrides equals() in order to check for equality between nodes
	 * @param otherNode
	 * @return
	 */
	public boolean equals(Node otherNode)
	{
		if(otherNode.getData() == data)
		{
			return true;
		}
		else return false;
	}
}

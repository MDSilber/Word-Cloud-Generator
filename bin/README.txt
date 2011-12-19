Mason Silber
12/19/2011
Data Structures and Algorithms
COMS W3137
Shlomo Hershkop

***********************************************************************
README 

The program launches, and the user is presented with a simple UI, asking
for the input of a few URLs and the number of adjacencies the user wants
the graph to show. If the information is not inputted correctly, the
text fields will tell the user what the issue is.

Note: if the user is loading a graph from memory, instead of generating
a new one, the user does not have to input any information at all. It
should all be stored in the serialized graph. To do so, the user simply
clicks the "Load Saved Cloud" button, and is presented with a file dialogue
that asks the user to find the file and select it.

Once the "Generate Cloud" button is clicked, the program takes a few
seconds to generate the graph. The time it takes depends on the number
of URLs the user has requested.

Once the graph has appeared, the user is once again presented with a
simple UI. The graph is displayed, and the UI is in "transform mode" by
default (meaning the graph can be dragged around the screen). The user
can also zoom into the graph (and out from the graph) using the
scrolling wheel on the mouse, or the equivalent on a trackpad. 

If the user decides to save the graph, all the user does is press "Save
Cloud", and a dialogue box will pop up, asking the user where to save
the graph. Once the graph is saved, the program can be quit, restarted,
and loaded up with the saved graph.

The graph's UI also has a "Picker mode" which allows the user to select
any node. When a node is selected, a window will pop up that will
display the frequency of the given word, the URLS to which the word
connects, the words that were previous to it in any of the URLs entered,
and the words that followed it in any of the URLS entered. NOTE: The
graph will only show the number of nodes that the user input from the
beginning. However, inspecting a node will display ALL the adjacencies,
not only the ones displayed on the graph.

Lastly, the graph interface contains a text box where the user can input
a word to search for. Clicking "search" will search through the nodes
looking for a node whose word matches the search term. If a match
exists, a window will appear identical to the one that would appear if
the user clicked on that node (displaying frequency, URLs, Adjacencies,
etc). If no match is found, a window will appear and tell the user that
there was no match for the given search.

************************************************************************
Algorithm Analysis

Though the javadoc annotations throughout the program give the time
complexity of each non-trivial method, I'll list them here as well.
Aside from these four, however, all the methods are doing work like
creating buttons and panels and ActionListeners, etc., so they're not
really necessary to list. That being said, they're all documented using
Javadoc throuhgout the program.

HTMLParser.java
***************
setWordsThatDontCount: O(n) - need to read in the list of words I didn't
want to count from the provided text file and add them to the array list

getWords: O(n^3) - This algorithm isn't very efficient, and likely adds
significant time to the processing of the HTML. However, Shlomo said
that the efficiency didn't matter as long as we knew what it was. There
are multiple blocks of code within this method that run in O(n^2), and
because the function has to be run on all of the URLs, that gives us
O(n^3) total time complexity

GraphWindow.java
****************
createDataGraph: O(V^2*E) - This is the method that actually creates the
graph itself, by adding each node and it's adjacencies via vertices and
edges

Search:O(V) - The search method just goes through the nodes in the graph
and checks to see if the data a given node contains matches the search
term. If it does, then there's a search result. Otherwise, there isn't.

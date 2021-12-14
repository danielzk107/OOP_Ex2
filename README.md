# OOP_Ex2
This project is the third assignment in the course "Object Oriented Programming", in which we implement an api of directed weighted graphes and various related algorithms.
In this README file, we will provide a short explanation for each of the objects and show how we implemented their interfaces, as well as give instructions as to how one can download and run the program. to skip to the explanation of how to install and run the program, press here

## Implementaion

The most important thing in projects like this one that run exhaustive functions is the implementation of the data structures; If you dont use optimal means to store the required data, your functions would never finish, and the entire project would go to waste. With that in mind, we used what we consider to be the most optimal implementation of all the data structures: 

### NodeData

The NodeData implementation might be considered a bit messy, simply because of the relatively large amount of veriables in the class. the NodeData class uses numerous hashmaps and lists to keep track of all of its connections to other nodes and edges in the most convenient way possible, so when we want to traverse our graph or just a part of it, we won't need to spend much effort. 

### DirectedWeightedGraph

The implementation of the graph class is relatively simple. Again, with efficiency in mind, we have used hashmaps to store both the nodes and the edges (in two separate maps, of course). This class has three different functions that return an iterator- one for the nodes, one for the edges, and one for all the edges that are connected to a specific given node. Those iterators were implemented in two different forms and are rather self explanatory, for clarifications see EdgelistIterator.java and NodelistIterator.java. 
The creation of an iterator for a specific node, the removal of a node from the graph, and similar tasks were infinetely simplified by our implementation of the Node class, which keeps track of all nodes and edges that are related to it.

### DirectedWeightedGraphAlgorithms

This class was the main objective of this project, with its different difficult functions. For the implementation of the functions, we tries to use the dynamic programming approach, so the class has some private variables, including arrays and lists. We anticipated for this class to recieve large graphs, so in order to save on time and efficiency, we created a threading class to split the work of the shortest path distance algorithm in four (more than four would not have worked for most laptops). The threading class works only on the shortest path algorithm because thaat function loops around the entire graph multiple times to store the distance between every two nodes in an array, in a form of dynamic programming. 

### GUI

The GUI in this project uses java swing for the presentation of the graph. The GUI is very simple and minimalistic, and simply opens a window with buttons to show the graph, show the centre node of the graph, and clean the window. 

### Tests

The tests we have created for this project are relatively simple; we load graphs with known results (i.e connectivity, shortest paths, and so on) and compare the known results to the results from our implementation of the different functions. The testing class also has a graph generator function, which generates a random graph for any given number of nodes and edges. This function is used in some of the tests, and is also used in the temporary main class that we forgot to delete in the GUI file :).


## Performance

In terms of speed and effiecency, we consider our project to be mostly optimal, since it uses dynamic programming almost exclusively. However, that comes at the cost of space effiecency and capability: when ran using a random graph of size 1000 with an average of 20 edges per node, the program found the centre of the graph in less than 100 milliseconds; when ran using a graph ten times larger, the program found the centre of the graph in less than 5 seconds; But when ran using a graph of size 100000 or larger, the program threw a heap error for a lack of memmory.
In the future, we will ponder this dilema of effiecency vs capacity in more depth, in order to have a truly optimal system.

## How to install and run

In order to install the executable jar file and example test json files, simply go to the release (v1.1) and download the files straight from there by simply pressing the names of the files. In order to install the entire project, go to the main branch and press the green button that says "code". from there, a small interactive window will open. from inside that window, choose the "Download as zip" option, and after the .zip file is downloaded to your machine, unzip it. In order to fork the project, follow https://docs.github.com/en/get-started/quickstart/fork-a-repo.


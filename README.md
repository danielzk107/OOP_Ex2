# OOP_Ex2
This project is the third assignment in the course "Object Oriented Programming", in which we implement an api of directed weighted graphes and various related algorithms.
In this README file, we will provide a short explanation for each of the objects and show how we implemented their interfaces, as well as give instructions as to how one can download and run the program.

## Implementaion

The most important thing in projects like this one that run exhaustive functions is the implementation of the data structures; If you dont use optimal means to store the required data, your functions would never finish, and the entire project would go to waste. With that in mind, we used what we consider to be the most optimal implementation of all the data structures: 

### NodeData

The NodeData implementation might be considered a bit messy, simply because of the relatively large amount of veriables in the class. the NodeData class uses numerous hashmaps and lists to keep track of all of its connections to other nodes and edges in the most convenient way possible, so when we want to traverse our graph or just a part of it, we won't need to spend much effort. 

### DirectedWeightedGraph

The implementation of the graph class is relatively simple. Again, with efficiency in mind, we have used hashmaps to store both the nodes and the edges (in two separate maps, of course). This class has three different functions that return an iterator- one for the nodes, one for the edges, and one for all the edges that are connected to a specific given node. Those iterators were implemented in two different forms and are rather self explanatory, for clarifications see EdgelistIterator.java and NodelistIterator.java. 
The creation of an iterator for a specific node, the removal of a node from the graph, and similar tasks were infinetely simplified by our implementation of the Node class, which keeps track of all nodes and edges that are related to it.

### DirectedWeightedGraphAlgorithms


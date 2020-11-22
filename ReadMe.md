Task 1 deals with the realization of an unintentional weighted graph.
Using fields that I have implemented such as:
1. key
2. tag
3. data

And implemented methods such as:
1. getKey
2. getInfo
3. setInfo
4. getTag
5. setTag
I got the realization that I would need to node, and this is the first step on our way to realizing the graph
I executed the node as an internal class within the graph implementation. That this is our next step.

Using fields that I have implemented such as:
1. HashNode
2. HashEdge
3. CounterMc
4. EdgeCounter

And implemented methods such as:
1. setCounterMc
2. getNode
3. hasEdge
4. getEdge
5. addNode
6. connect
7. getV
8. removeNode
9. removeEdge
10. nodeSize
11. edgeSize
12. getMC

I received an exercise for an unintentional weighted graph in which I are told in each given change the amount
 of changes in the graph and the amount of ribs in the graph.
With the help of the WGraph_DS class implementation I will move on to the next and third stage of the task,
 the implementation of algorithms on the graph.

Using fields that I have implemented such as:
1. Graph

And implemented methods such as:
1. getGraph
2. isConnected
3. shortestPathDist
4. shortestPath
5. save
6. load

I received a ready-made template for a weighted and unintentional graph that knows how to perform the following
actions on it: check whether the graph is binding, return the shortest route weight,
return the shortest route vertices, convert from a graph to a text file and vice versa.
I implemented the methods in a way that things would work with the efficiency required in the task,
for this I chose to implement the way with the help of a hashmap within a hashmap that expresses the sides and the
list of neighbors and another hashmap that expresses the vertices.
For the save and load methods, a number of additional methods had to be implemented, such as:
1. equals
2. compareTo
3. toString

In addition, I used the tests attached to the assignment to test the suitability of the methods.


import java.util.*;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;


// Conquering A*
/*
At each iteration of the loop, A* determines which path to take. It chooses what path to take based
on the cost of the path and an estimate of the cost to extend path all the way to the goal.
The formula is f(n) = g(n) + h(n)
n, is the next node on the path h(n) estimates cheapest path from n to goal. g(n) is the cost of the path
from the start node to the current node.
It utilizes a priority queue, and manhattan distance. PQ is an open set and at each step
the node with the lowest f value will be removed. Continues until a removed node is a goal node.
Need two sets: one for nodes to consider and ones that are visited
Need a linked list to create the path, should be a list of points

 */
public class AStarPathingStrategy implements PathingStrategy {
    public List<Point> computePath(Point start,
                                   Point end,
                                   Predicate<Point> canPassThrough,
                                   BiPredicate<Point, Point> withinReach,
                                   Function<Point, Stream<Point>> potentialNeighbors) {

        // need to initialize a path, an open set used with priority queue
        // initialize a map for nodes in open set (unvisited), and another map for the visited nodes

        // need to initialize a path, an open set used with priority queue
        // initialize a map for nodes in open set (unvisited), and another map for the visited nodes


        LinkedList<Point> path = new LinkedList<>();  // this is for the path
        PriorityQueue<Node> openSet = new PriorityQueue<>();  // set to consider nodes
        HashMap<Point, Node> openMap = new HashMap<>();  // takes in a point and node, take nodes and store in map
        HashMap<Point, Node> visited = new HashMap<>(); // takes in a point and node, closed set nodes alr visited

        // initialized a start node
        //     public Node(Point point, Node prev, int GVal, int HVal, int FVal){
        Node startNode = new Node(start, null, 0, Point.ManhattanDistance(start, end), Point.ManhattanDistance(start, end));

        // add the start node to the set that considers the node, put the point and the start node into the map
        openSet.add(startNode);
        openMap.put(startNode.getPoint(), startNode);

        // main loop that goes thru each node in the open set
        while (!openSet.isEmpty()) {
            // returns & removes the element at the head of queue, stores in the value curr
            // https://www.geeksforgeeks.org/queue-poll-method-in-java/
            Node current = openSet.poll();
            openMap.remove(current.getPoint());

            // checks if the current point is at the end, if so run the below
            if (withinReach.test(current.getPoint(), end)) {
                while (current != null) {  // run thru the values until the set is empty
                    path.add(current.getPoint());  // add the point to the known path
                    current = current.getPrev();  // update the value of current to the previous node, works back
                }
                // you want to remove the start and end points, just want the intermediate points in between
                path.remove(start);
                path.remove(end);
                return reverse(path); // path goes end -> front, so need to reverse it
            }

            // all the neighbors of the current point, turns them into a list and is filtered by its ability to be passed thru
            List<Point> neighbors = potentialNeighbors.apply(current.getPoint())
                    .filter(canPassThrough)
                    .toList();

            // going to have to start doing the heuristic
            for (Point neighbor : neighbors) {
                int currentGScore = current.getGscore() + 1;  // current g score adding one to g score, this is distance from curr to start
                Node neighborNode = new Node(neighbor, current, current.getGscore() + 1, // h is the distance of node to end
                        Point.ManhattanDistance(neighbor, end), current.getHscore() + current.getGscore());  // f = g+h
                // checks if in the visited map there is a matching neighbor

                if (!visited.containsKey(neighbor)) {
                    if (!openMap.containsKey(neighbor)) {  // if the open map does not already have the key as its neighbor
                        openSet.add(neighborNode);  // add to open set
                        openMap.put(neighborNode.getPoint(), neighborNode); // add to the map with point and Node
                    } else { // if the neighbors G score is larger than the current, change the values bc the path is shorter
                        if (openMap.get(neighbor).getGscore() > currentGScore) {
                            openSet.remove(openMap.get(neighbor));  // remove the value
                            openMap.replace(neighbor, neighborNode);  // replace the value
                            openSet.add(neighborNode); // add the neighborNode
                        }
                    }
                }
            }
            visited.put(current.getPoint(), current); // add the point and node to visited map
        }
        return path;  // return the path
    }


    // reverses the path because of the way points were added in
    public LinkedList<Point> reverse(LinkedList<Point> path){
        int length;
        LinkedList<Point> rev_path = new LinkedList<>();
        length = path.size();
        for(int i = length - 1; i >= 0; i--)  // start from last element and inc down, ordered nearest -> farthest, want farthest -> nearest
            rev_path.add(path.get(i));
        return rev_path;
    }
}
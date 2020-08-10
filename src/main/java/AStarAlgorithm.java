import java.util.PriorityQueue;
import java.util.HashSet;
import java.util.Set;
import java.util.List;
import java.util.Comparator;
import java.util.ArrayList;
import java.util.Collections;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
class AStarAlgorithm {


    //h huredistic straight line
    public static void main(String[] args)throws IOException{

//Node inputs
        Node n1=new Node("S",10);

        Node n2=new Node("A",4);
        Node n3=new Node("B",2);
        Node n4=new Node("C",4);
        Node n5=new Node("D",4.5);
        Node n6=new Node("E",2);
        Node n7=new Node("F",0);
        //initialize the edges

        //node1 s

        n1.adjacencies = new Edge[]{

                new Edge(n2,1.5),
                new Edge(n5,2),

        };

        //node2 A
        n2.adjacencies = new Edge[]{
                new Edge(n3,2),
                new Edge(n1,1.5),

        };


        //node3 B
        n3.adjacencies = new Edge[]{
                new Edge(n4,3),
                new Edge(n2,2),

        };

        //node4 C
        n4.adjacencies = new Edge[]{
                new Edge(n7,4),
                new Edge(n3,3),

        };


        //node5 D
        n5.adjacencies = new Edge[]{
                new Edge(n6,3),
                new Edge(n1,2),

        };

        //node6 E
        n6.adjacencies = new Edge[]{
                new Edge(n7,2),
                new Edge(n5,3),
        };

        //node7 F
        n7.adjacencies = new Edge[]{
                new Edge(n6,2),
                new Edge(n4,4),
        };


        AstarSearch(n1,n7);

        List<Node> path = printPath(n7);

        System.out.println("Path "+path);
        //System.out.println("Path "+Node.f);


    }

    public static List<Node> printPath(Node target){
        List<Node> path = new ArrayList<Node>();

        for(Node node = target; node!=null; node = node.parent){
            path.add(node);
        }

        Collections.reverse(path);

        return path;
    }

    public static void AstarSearch(Node source, Node goal){

        Set<Node> explored = new HashSet<Node>();

        PriorityQueue<Node> queue = new PriorityQueue<Node>(
                new Comparator<Node>(){
                    //override compare method
                    public int compare(Node i, Node j){
                        if(i.f > j.f){
                            return 1;
                        }

                        else if (i.f < j.f){
                            return -1;
                        }

                        else{
                            return 0;
                        }
                    }



                }
        );

        //cost from start
        source.g = 0;

        queue.add(source);

        boolean found = false;

        while((!queue.isEmpty())&&(!found)){

            //the node in having the lowest f
            Node current = queue.poll();

            explored.add(current);

            //goal
            if(current.value.equals(goal.value)){
                found = true;
            }

            //check every child of current node
            for(Edge e : current.adjacencies){
                Node child = e.target;
                double cost = e.cost;
                double temp_g = current.g + cost;
                double temp_f = temp_g + child.h;
 
 
                                /*if child node has been evaluated and 
                                the newer f is higher, skip*/
                //don't need for this graph
                if((explored.contains(child)) &&
                        (temp_f >= child.f)){
                    continue;
                }
 
                                /*else if child node is not in queue or 
                                newer f is lower*/

                else if((!queue.contains(child)) ||
                        (temp_f < child.f)){

                    child.parent = current;
                    child.g = temp_g;
                    child.f = temp_f;
                    //don't need for this graph
                                      /* if(queue.contains(child)){
                                                queue.remove(child);
                                                
                                        }*/

                    queue.add(child);
                    //g=parent.g+child.g;
                    //System.out.println(g);

                }
                else{
                    System.out.println(current.g);
                }

            }

        }

    }

}

class Node{

    public  String value;
    public double g;
    public  double h;
    public double f = 0;
    public Edge[] adjacencies;
    public Node parent;

    public Node(String val, double hVal){
        value = val;
        h = hVal;
    }

    @Override
    public String toString(){
        return value;
    }

}

class Edge{
    public  double cost;
    public  Node target;

    public Edge(Node targetNode, double costVal){
        target = targetNode;
        cost = costVal;
    }
}

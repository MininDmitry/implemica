import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;

import java.util.*;
import java.util.stream.Collectors;

class task2_2 {

    private static class Node {

        @NotNull
        String name;

        Node() { }

        private Node(String name) {
            this.name = name;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == null) {
                return false;
            }
            if (obj == this) {
                return true;
            }
            if (obj instanceof Node) {
                return name.equals(((Node) obj).name);
            } else {
                return  false;
            }
        }

        @Override
        public int hashCode() {
            return name.hashCode();
        }
    }

    private static class Path {
        @NotNull
        Node source;
        @NotNull
        Node destination;
        @Nullable
        Integer value;

        private Path(Node source, Node destination, Integer value) {
            this.source = source;
            this.destination = destination;
            this.value = value;
        }

        @Override
        public String toString() {
            return source.name + " " + destination.name + " value: " + value;
        }
    }

    private static class Graph {

        private List<Node> nodes = new ArrayList<>();
        private List<Path> paths = new ArrayList<>();


        public void addPath(@NotNull Path path) {
            if (!nodes.contains(path.source)) {
                addNode(path.source);
            }
            if (!nodes.contains(path.destination)) {
                addNode(path.destination);
            }
            paths.add(path);
        }

        public void addNode(@NotNull Node node) {
            if (!nodes.contains(node)) {
                nodes.add(node);
            }
        }

        @NotNull
        List<Node> getNodes() {
            return Collections.unmodifiableList(nodes);
        }

        @NotNull
        List<Path> getPaths() {
            return Collections.unmodifiableList(paths);
        }

        List<Path> getConnectedPaths(final Node node) {
            return getPaths().stream().filter(
                    path -> path.source.equals(node) || path.destination.equals(node)
            ).collect(Collectors.toList());
        }
    }

    public interface Algorithm {

        void calculatePath(Graph graph, Node source, Node destination);
    }

    public static class DjikstraAlgoritm implements Algorithm {

        @Override
        public void calculatePath(Graph graph, final Node source, final Node destination) {
            // not visited nodes (as boolean array)
            List<Node> notVisitedNodes = new ArrayList<>(graph.getNodes());
            List<Node> evaluatedNodes = new ArrayList<>();
            evaluatedNodes.add(source);
            // wrappers (items for storing the value)
            HashMap<Node, Integer> wrappers = new HashMap<>();
            // initialization of wrappers
            notVisitedNodes.forEach(node -> wrappers.put(node, node.equals(source)? 0 : Integer.MAX_VALUE));
            // algorithm
            Node current = source;
            while (!notVisitedNodes.isEmpty()) {
                List<Path> connectedPaths = graph.getConnectedPaths(current).stream().filter(path -> {
                    return notVisitedNodes.contains(path.source) && notVisitedNodes.contains(path.destination);
                }).collect(Collectors.toList());

                Node min = null;
                int minValue = -1;
                for (Path path : connectedPaths) {
                    Node possibleNext = current.equals(path.destination) ? path.source : path.destination;
                    Integer value = evaluatedNodes.contains(possibleNext)
                            ? Integer.min(wrappers.get(possibleNext), wrappers.get(current) + path.value) // not visited nodes
                            : wrappers.get(current) + path.value; // visited nodes
                    wrappers.put(possibleNext, value);

                    if (minValue == -1) {
                        minValue = path.value;
                        min = possibleNext;
                    }
                    if (minValue > path.value) {
                        minValue = path.value;
                        min = possibleNext;
                    }
                    if (!evaluatedNodes.contains(possibleNext)) {
                        evaluatedNodes.add(possibleNext);
                    }
                }
                notVisitedNodes.remove(current);
                current = min;
            }
            System.out.println("ANSWER: " + wrappers.get(destination));
        }
    }

    static Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) {
       // testGraph();

        System.out.println("how many tests do you want to conduct");
        int c = scanner.nextInt();
        while(c>0) {
            Graph graph = new Graph();
            System.out.println("Please write number of sities");
            int num = scanner.nextInt();
            for (int i = 0; i < num; i++) {
                System.out.println("Please write names town ");
                graph.addNode(new Node(scanner.next()));
            }
            List<Node> nodes = graph.getNodes();
            String rezstring ="";
            nodes.forEach(n -> System.out.println(n.name ) );
            int n1,n2;
            while(!rezstring.equals("exit")){
                System.out.println("Please write reletives first the number of the city to be contacted, after, the cost of communication");
                n1 = scanner.nextInt() - 1;// for the convenience of the user assuming the number input is not from 0
                n2 = scanner.nextInt() - 1;
                int value = scanner.nextInt();
                graph.addPath(new Path(nodes.get(n1), nodes.get(n2), value));
                System.out.println("If you wont Exit - write exit.else = write new reletives");
                rezstring = scanner.next();
            }
            System.out.println("Which city do you want to go to from?");
            n1 = scanner.nextInt() - 1;
            n2 = scanner.nextInt() - 1;
            new DjikstraAlgoritm().calculatePath(graph, nodes.get(n1), nodes.get(n2));
            c--;
        }

    }

    private static void testGraph() {
        Graph graph = new Graph();
        for (int i = 0; i < 6; i++) {
            graph.addNode(new Node(Integer.toString(i)));
        }
        List<Node> nodes = graph.getNodes();
        graph.addPath(new Path(nodes.get(0), nodes.get(1), 7));
        graph.addPath(new Path(nodes.get(0), nodes.get(2), 9));
        graph.addPath(new Path(nodes.get(0), nodes.get(2), 6));
        graph.addPath(new Path(nodes.get(0), nodes.get(5), 14));
        graph.addPath(new Path(nodes.get(1), nodes.get(2), 10));
        graph.addPath(new Path(nodes.get(1), nodes.get(3), 15));
        graph.addPath(new Path(nodes.get(2), nodes.get(3), 11));
        graph.addPath(new Path(nodes.get(2), nodes.get(3), 7));
        graph.addPath(new Path(nodes.get(2), nodes.get(5), 2));
        graph.addPath(new Path(nodes.get(3), nodes.get(4), 6));
        graph.addPath(new Path(nodes.get(5), nodes.get(4), 9));
        graph.addPath(new Path(nodes.get(5), nodes.get(4), 6));
        new DjikstraAlgoritm().calculatePath(graph, nodes.get(0), nodes.get(4));
    }

}
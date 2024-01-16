import java.util.*;

class Node{
    String value;
    List<Node> children;

    public Node(String value, List<Node> children){
        this.value = value;
        this.children = children;
    }

    public Node(String value){
        this.value = value;
        this.children = new ArrayList<>();
    }

    public String getValue() {
        return value;
    }

    public List<Node> getChildren() {
        return children;
    }
}

public class BFS{
    public static void breathFirstSearch(Node start, Node target){
        Queue<Node> queue = new LinkedList<>();
        Set<Node> visited = new HashSet<>();
        Map<Node, Node> parentMap = new HashMap<>(); 
        queue.add(start);
        while (!queue.isEmpty()) {
            Node current = queue.poll();
            visited.add(current);

            if (current.getValue().equals(target.getValue())) {
                System.out.println("Đã tìm thấy đích: " + target.getValue());
                printPath(parentMap, start, target);
                return;
            }

            for (Node child : current.getChildren()) {
                if (!visited.contains(child) && !queue.contains(child)) {
                    parentMap.put(child, current); 
                    queue.add(child);
                }
            }
        }

        System.out.println("Không tìm thấy đích: " + target.getValue());
    }

    private static void printPath(Map<Node, Node> parentMap, Node start, Node target) {
        List<String> path = new ArrayList<>();
        Node node = target;
        while (node != null) {
            path.add(node.getValue());
            node = parentMap.get(node);
        }
        Collections.reverse(path);
        System.out.println("Đường đi: " + String.join(" -> ", path));
    }

    public static void main(String[] args) {
    //        Ví dụ 3
    //        Node G = new Node("G");
    //        Node H = new Node("H");
    //        Node I = new Node("I");
    //        Node J = new Node("J");
    //        Node F = new Node("F", List.of(J));
    //        Node E = new Node("E", Arrays.asList(H, I));
    //        Node D = new Node("D");
    //        Node C = new Node("C", Arrays.asList(F, G));
    //        Node B = new Node("B", Arrays.asList(E, F));
    //        Node A = new Node("A", Arrays.asList(B, C, D));
    //        BFS.breathFirstSearch(A, G);
        Scanner scanner = new Scanner(System.in);
        Map<String, Node> nodes = new HashMap<>();

        System.out.println("Nhập số lượng nút:");
        int numNodes = scanner.nextInt();
        scanner.nextLine(); 

        for (int i = 0; i < numNodes; i++) {
            System.out.println("Nhập tên nút:");
            String nodeName = scanner.nextLine();
            nodes.put(nodeName, new Node(nodeName));
        }

        System.out.println("Nhập số lượng cạnh:");
        int numEdges = scanner.nextInt();
        scanner.nextLine(); 

        for (int i = 0; i < numEdges; i++) {
            System.out.println("Nhập cạnh dưới dạng 'nút nguồn -> nút đích':");
            String edge = scanner.nextLine();
            String[] nodeNames = edge.split(" -> ");
            nodes.get(nodeNames[0]).getChildren().add(nodes.get(nodeNames[1]));
        }

        System.out.println("Nhập nút bắt đầu:");
        String startNodeName = scanner.nextLine();

        System.out.println("Nhập nút đích:");
        String targetNodeName = scanner.nextLine();

        BFS.breathFirstSearch(nodes.get(startNodeName), nodes.get(targetNodeName));
    }
}
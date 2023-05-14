import java.util.*;

/**
1. 중위순회 트리 만들기
-> 중위순회 특징: x좌표가 가장 작은 순서대로 차례로 노드를 트리에 추가하면 됨

2. 만든 중위순회 트리에 대해서 전위순회(preOrder) 트리 만들기
-> 현재 노드 추가 -> 왼쪽으로 이동 -> 오른쪽으로 이동

3. 만든 중위순회 트리에 대해서 후위순회(postOrder) 트리 만들기
-> 왼쪽으로 이동 -> 오른쪽으로 이동 -> 현재 노드 추가

*/
class Solution {
    
    static int n;
    static Node[] nodes; // inOrder
    static List<Node> preOrder = new ArrayList<>();
    static List<Node> postOrder = new ArrayList<>();

    
    public int[][] solution(int[][] nodeinfo) {
        n = nodeinfo.length;
        
        nodes = new Node[n];
        Node root = new Node(-1, -1, -1);
        for(int i = 0; i < n; i++){
            nodes[i] = new Node(i + 1, nodeinfo[i][0], nodeinfo[i][1]);
            if(root.y < nodeinfo[i][1]){
                root = new Node(i + 1, nodeinfo[i][0], nodeinfo[i][1]);
            }
        }
        Arrays.sort(nodes, new Comparator<Node>(){
            @Override
            public int compare(Node n1, Node n2){
                return n1.x - n2.x;
            }
        });

        for(int i = 0; i < n; i++){
            if(nodes[i].num ==  root.num){
                findPreOrder(0, n - 1, i, 1);
                findPostOrder(0, n-1, i, 1);
                break;
            }
        }

        int[][] answer = new int[2][n];
        for(int i = 0; i < n; i++){
            Node pre = preOrder.get(i);
            Node post = postOrder.get(i);
            
            answer[0][i] = pre.num;
            answer[1][i] = post.num;
        }
        
        return answer;
    }
    
    public static void findPreOrder(int left, int right, int root, int idx){
        if(root == -1){
            return;
        }
        
        Node leftRoot = new Node(-1, -1, -1);
        Node rightRoot = new Node(-1, -1, -1);
        int leftIdx = -1;
        int rightIdx = -1;
        
        for(int i = left; i < root; i++){ // 왼쪽 서브트리의 루트 노드 찾기 -> 현재 루트 기준 배열에서 왼쪽에 있는 노드들 중, y값이 가장 큰 노드 = 루트 노드
            if(nodes[i].y > leftRoot.y){
                leftRoot = nodes[i];
                leftIdx = i;
            }
        }
        for(int i = root + 1; i <= right; i++){ // 오른쪽 서브트리의 루트 노드 찾기 -> 현재 루트 기준 배열에서 오른쪽ㅇ0 있는 노드들 중, y값이 가장 큰 노드 = 루트 노드
            if(nodes[i].y > rightRoot.y){
                rightRoot = nodes[i];
                rightIdx = i;
            }
        }
        
        preOrder.add(nodes[root]);
        findPreOrder(left, root - 1, leftIdx, idx); // 왼쪽으로 이동
        findPreOrder(root + 1, right, rightIdx, idx); // 오른쪽으로 이동
    }
    
    public static void findPostOrder(int left, int right, int root, int idx){
        if(root == -1){
            return;
        }
        
        Node leftRoot = new Node(-1, -1, -1);
        Node rightRoot = new Node(-1, -1, -1);
        int leftIdx = -1;
        int rightIdx = -1;
        
        for(int i = left; i < root; i++){ // 왼쪽 서브트리의 루트 노드 찾기 -> 현재 루트 기준 배열에서 왼쪽에 있는 노드들 중, y값이 가장 큰 노드 = 루트 노드
            if(nodes[i].y > leftRoot.y){ // 오른쪽 서브트리의 루트 노드 찾기 -> 현재 루트 기준 배열에서 오른쪽ㅇ0 있는 노드들 중, y값이 가장 큰 노드 = 루트 노드
                leftRoot = nodes[i];
                leftIdx = i;
            }
        }
        for(int i = root + 1; i <= right; i++){
            if(nodes[i].y > rightRoot.y){
                rightRoot = nodes[i];
                rightIdx = i;
            }
        }
        
        findPostOrder(left, root - 1, leftIdx, idx);    // 왼쪽으로 이동
        findPostOrder(root + 1, right, rightIdx, idx); // 오른쪽으로 이동
        postOrder.add(nodes[root]);
    }
    
    public static class Node{
        int num;
        int x;
        int y;
        
        public Node(int num, int x, int y){
            this.num = num;
            this.x = x;
            this.y = y;
        }
        
        @Override
        public String toString(){
            return "[num=" + num + ", x=" + x + ", y=" + y + "}";
        }
    }
}
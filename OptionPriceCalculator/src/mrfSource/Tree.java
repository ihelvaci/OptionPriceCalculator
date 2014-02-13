package mrfSource;


public class Tree {
	
	int deep;
	Node[][] arrayNode;
	public Tree(int deep) {
		this.deep = deep;
	}
	
	public Node[][] createTree() {
		
		arrayNode = new Node[deep+1][];
		int temp = 0;

		
		for(int i=0; i<arrayNode.length; i++) {
			arrayNode[i] = new Node[i+1];
			for(int j=0; j<=i ;j++){
				arrayNode[i][j] = new Node(temp);
				temp++;
			}
		}
		return arrayNode;
	}
}

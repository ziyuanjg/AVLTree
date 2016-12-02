package test;

public class Node {

	private Node parent;
	private Node left;
	private Node right;
	private int item;
	private int avl;
	
	public Node(int item) {
		this.item = item;
		this.avl = 0;
	}
	
	public int getValue(){
		return item;
	}
	
	public Node getLeft(){
		return left;
	}
	
	public Node getRight(){
		return right;
	}
	
	public Node getParent(){
		return parent;
	}
	
	public void setValue(int item){
		this.item = item;
	}
	
	public void setParent(Node parent){
		this.parent = parent;
	}
	
	public void setLeft(Node item){
		this.left = item;
	}
	
	public void setRight(Node item){
		this.right = item;
	}
	
	public int getAVL(){
		return avl;
	}
	
	public void setAVL(int avl){
		this.avl = avl;
	}
	
	public int subAVL(){
		avl -= 1;
		return avl;
	}
	
	public int addAVL(){
		avl += 1;
		return avl;
	}
	
	@Override
	public String toString() {
		return String.valueOf(item);
	}
	
	public boolean hasBrother(){
		if(parent == null)
			return false;
		else if(this == parent.getLeft() && parent.getRight() != null)
			return true;
		else if(this == parent.getRight() && parent.getLeft() != null)
			return true;
		else
			return false;
	}
	
	public boolean hasChild(){
		if(left != null || right != null)
			return true;
		else 
			return false;
	}
	
	public Node getBrother(){
		if(parent == null)
			return null;
		else if(this == parent.getLeft())
			return parent.getRight();
		else
			return parent.getLeft();
	}
	
	public void deleteChildNode(Node child){
		if(child == null)
			return;
		else if(child == left)
			left = null;
		else if(child == right)
			right = null;
	}
}

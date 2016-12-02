package test;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class AVLTree {

	//根节点
	private Node root;
	
	/**
	 * 添加节点
	 * @param item
	 */
	public void add(int item){
		if(root == null)
			root = new Node(item);
		else{
			Node parent = root;
			Node temp = root;
			do{
				parent = temp;
				if(item < parent.getValue()){
					temp = parent.getLeft();
				}else if(item > parent.getValue()){
					temp = parent.getRight();
				}else{
					break;
				}
			}while(temp != null);
			if(temp != null){
				temp.setValue(item);
			}else{
				Node node = new Node(item);
				node.setParent(parent);
				if(item < parent.getValue()){
					parent.setLeft(node);
					if(parent.getRight() == null)
						balance(node);
					else
						parent.subAVL();
				}else{
					parent.setRight(node);
					if(parent.getLeft() == null)
						balance(node);
					else
						parent.addAVL();
				}
			}
		}
	}
	
	/**
	 * 增加节点时修改节点平衡值，对不平衡的子树进行调整
	 * 依次向上遍历，修改父节点的平衡因子，直到出现最小不平衡节点
	 * @param node
	 */
	private void balance(Node node){
		Node parent = node.getParent();
		Node node_middle = node;
		Node node_prev = node;
		
		Boolean avl = true;
		do{
			if(node_middle == parent.getLeft() && (-1 <= parent.getAVL()-1 && parent.getAVL()-1 <= 1)){
				parent.subAVL();
				node_prev = node_middle;
				node_middle = parent;
				if(parent != null && parent.getAVL() == 0)
					parent = null;
				else
					parent = parent.getParent();
			}else if(node_middle == parent.getRight() && (-1 <= parent.getAVL()+1 && parent.getAVL()+1 <= 1)){
				parent.addAVL();
				node_prev = node_middle;
				node_middle = parent;
				if(parent != null && parent.getAVL() == 0)
					parent = null;
				else
					parent = parent.getParent();
			}else{//出现最小不平衡节点，新增时不需要考虑更高节点，所以直接中断循环，调用平衡方法
				avl = false;
			}
		}while(parent != null && avl);
		
		if(parent == null){
			return;
		}
		chooseCalculation(parent, node_middle, node_prev);
	}
	
	/**
	 * 选择合适的转换规则，返回变更的高度值（删除平衡时会用到）。
	 * @param parent
	 * @param node_middle
	 * @param node_prev
	 */
	private int chooseCalculation(Node parent, Node node_middle, Node node_prev) {
		
		//变更的高度值
		int height = 0;
		
		if(node_middle == parent.getLeft() && node_prev == node_middle.getLeft()){
			if(node_middle.getAVL() == -1)
				height = -1;
			LeftLeftRotate(node_middle);
		}else if(node_middle == parent.getLeft() && node_prev == node_middle.getRight()){
			height = -1;
			LeftRightRotate(node_middle);
		}else if(node_middle == parent.getRight() && node_prev == node_middle.getLeft()){
			height = -1;
			RightLeftRotate(node_middle);
		}else if(node_middle == parent.getRight() && node_prev == node_middle.getRight()){
			if(node_middle.getAVL() == 1)
				height = -1;
			RightRightRotate(node_middle);
		}
		
		return height;
	}
	
	/**
	 * 查询节点
	 * @param item
	 * @return
	 */
	public Node get(int item){
		if(root == null)
			return null;
		if(item == root.getValue()){
			return root;
		}else{
			Node node = root;
			do{
				if(item < node.getValue()){
					node = node.getLeft();
				}else if(item > node.getValue()){
					node = node.getRight();
				}else{
					return node;
				}
			}while(node != null);
		}
		return null;
	}
	
	/**
	 * 获取树的高度
	 * @return
	 */
	public int treeLength(){
		return getLength(root);
	}
	
	/**
	 * 获取node到树底的高度
	 * @param node
	 * @return
	 */
	private int getLength(Node node){
		if(node == null)
			return 0;
		int num = 1;
		List<Node> l = new ArrayList<Node>();
		l.add(node);
		num = itrableNode(num, l);
		return num;
	}
	
	private int itrableNode(int num, List<Node> list){
		List<Node> newlist = new ArrayList<Node>();
		list.forEach(new Consumer<Node>() {
			@Override
			public void accept(Node t) {
				if(t.getLeft() != null){
					newlist.add(t.getLeft());
				}
				if(t.getRight() != null){
					newlist.add(t.getRight());
				}
			}
		});
		if(newlist.size() > 0){
			num += 1;
			num = itrableNode(num,newlist);
		}
		return num;
	}
	
	/**
	 * 打印树形图(仅限10到99的正整数组成的树，否则排版会有问题)，测试用方法
	 */
	public void printByPhoto(){
		
		if(root == null){
			return;
		}
		int length = treeLength();
		int blank = countoutBlank(length, 1);
		List<Node> l = new ArrayList<Node>();
		l.add(root);
		printRow(length,blank,1,l);
	}
	
	/**
	 * 计算每行的空格数
	 * @param length
	 * @param row
	 * @return
	 */
	private int countoutBlank(int length, int row){
		int half = (int)Math.pow(2, length-1-row);
		int blank = 0;
		if(length == 2){
			blank = 2;
		}else if(length > 2){
			if(half == 2){
				blank = 6;
			}else{
				blank = (int) (Math.pow(2, length-2-row) * 6 + (Math.pow(2, length - 2-row) - 1) * 2);
			}
		}else{
			blank = 0;
		}
		return blank;
	}
	
	/**
	 * 绘制行
	 * @param length
	 * @param blank
	 * @param row
	 * @param list
	 */
	private void printRow(int length, int blank, int row, List<Node> list){
		
		for(int n = 0; n < blank; n++){
			System.out.print(" ");
		}
		
		StringBuffer x = new StringBuffer();
		for(int n = 0; n < blank*2; n++){
			x.append(" ");
		}
		x.append("  ");
		StringBuffer y = new StringBuffer();
		for(int n = 0; n < blank*2; n++){
			y.append(" ");
		}
		y.append("  ");
		
		List<Node> newlist = new ArrayList<Node>();
		if(row == 1){
			System.out.print(root.getValue());
			System.out.println("");
			printRow(length,countoutBlank(length, row+1),row+1,list);
		}else{
			for(Node t : list){
				if(t.getLeft() != null){
					newlist.add(t.getLeft());
					System.out.print(t.getLeft().getValue()+x.toString());
				}else if(t.getValue() != 0 || row <= length){
					newlist.add(new Node(0));
					System.out.print("x "+x.toString());
				}
				if(t.getRight() != null){
					newlist.add(t.getRight());
					System.out.print(t.getRight().getValue()+y.toString());
				}else if(t.getValue() != 0 || row <= length){
					newlist.add(new Node(0));
					System.out.print("x "+y.toString());
				}
			}
			System.out.println("");
			if(newlist.size() > 0)
				printRow(length,countoutBlank(length, row+1),row+1,newlist);
		}
	}
	
	/**
	 * 左左旋转
	 * @param node
	 */
	private void LeftLeftRotate(Node node){
		
		Node parent = node.getParent();
		
		if(parent.getParent() != null && parent == parent.getParent().getLeft()){
			node.setParent(parent.getParent());
			parent.getParent().setLeft(node);
		}else if(parent.getParent() != null && parent == parent.getParent().getRight()){
			node.setParent(parent.getParent());
			parent.getParent().setRight(node);
		}else{
			root = node;
			node.setParent(null);
		}
		parent.setParent(node);
		parent.setLeft(node.getRight());
		if(node.getRight() != null)
			node.getRight().setParent(parent);
		node.setRight(parent);
		
		if(node.getAVL() == -1){//只有左节点时，parent转换后没有子节点
			parent.setAVL(0);
			node.setAVL(0);
		}else if(node.getAVL() == 0){//node有两个子节点，转换后parent有一个左节点
			parent.setAVL(-1);
			node.setAVL(1);
		}//node.getAVL()为1时会调用左右旋转
	}
	
	/**
	 * 右右旋转
	 * @param node
	 */
	private void RightRightRotate(Node node){
		
		Node parent = node.getParent();
		
		if(parent.getParent() != null && parent == parent.getParent().getLeft()){
			node.setParent(parent.getParent());
			parent.getParent().setLeft(node);
		}else if(parent.getParent() != null && parent == parent.getParent().getRight()){
			node.setParent(parent.getParent());
			parent.getParent().setRight(node);
		}else{
			root = node;
			node.setParent(null);
		}
		parent.setParent(node);
		parent.setRight(node.getLeft());
		if(node.getLeft() != null)
			node.getLeft().setParent(parent);
		node.setLeft(parent);
		
		if(node.getAVL() == 1){
			node.setAVL(0);
			parent.setAVL(0);
		}else if(node.getAVL() == 0){//当node有两个节点时，转换后层数不会更改，左树比右树高1层，parent的右树比左树高一层
			parent.setAVL(1);
			node.setAVL(-1);
		}
	}
	
	/**
	 * 左右旋转
	 * @param node
	 */
	private void LeftRightRotate(Node node){
		
		Node parent = node.getParent();
		Node child = node.getRight();
		
		//左右旋转时node的avl必为1，所以只需考虑child的avl
		if(!child.hasChild()){
			node.setAVL(0);
			parent.setAVL(0);
		}else if(child.getAVL() == -1){
			node.setAVL(0);
			parent.setAVL(1);
		}else if(child.getAVL() == 1){
			node.setAVL(-1);
			parent.setAVL(0);
		}else if(child.getAVL() == 0){
			node.setAVL(0);
			parent.setAVL(0);
		}
		child.setAVL(0);
		
		//第一次交换
		parent.setLeft(child);
		node.setParent(child);
		node.setRight(child.getLeft());
		if(child.getLeft() != null)
			child.getLeft().setParent(node);
		child.setLeft(node);
		child.setParent(parent);
		
		//第二次交换
		if(parent.getParent() != null && parent == parent.getParent().getLeft()){
			child.setParent(parent.getParent());
			parent.getParent().setLeft(child);
		}else if(parent.getParent() != null && parent == parent.getParent().getRight()){
			child.setParent(parent.getParent());
			parent.getParent().setRight(child);
		}else{
			root = child;
			child.setParent(null);
		}
		parent.setParent(child);
		parent.setLeft(child.getRight());
		if(child.getRight() != null)
			child.getRight().setParent(parent);
		child.setRight(parent);
		
		
	}
	
	/**
	 * 右左旋转
	 * @param node
	 */
	private void RightLeftRotate(Node node){
		
		Node parent = node.getParent();
		Node child = node.getLeft();
		
		if(!child.hasChild()){
			node.setAVL(0);
			parent.setAVL(0);
		}else if(child.getAVL() == -1){
			node.setAVL(1);
			parent.setAVL(0);
		}else if(child.getAVL() == 1){
			node.setAVL(0);
			parent.setAVL(-1);
		}else if(child.getAVL() == 0){
			parent.setAVL(0);
			node.setAVL(0);
		}
		child.setAVL(0);
		
		//第一次交换
		parent.setRight(child);
		node.setParent(child);
		node.setLeft(child.getRight()); 
		if(child.getRight() != null)
			child.getRight().setParent(node);
		child.setRight(node);
		child.setParent(parent);
		
		//第二次交换
		if(parent.getParent() != null && parent == parent.getParent().getLeft()){
			child.setParent(parent.getParent());
			parent.getParent().setLeft(child);
		}else if(parent.getParent() != null && parent == parent.getParent().getRight()){
			child.setParent(parent.getParent());
			parent.getParent().setRight(child);
		}else{
			root = child;
			child.setParent(null);
		}
		parent.setParent(child);
		parent.setRight(child.getLeft());
		if(child.getLeft() != null)
			child.getLeft().setParent(parent);
		child.setLeft(parent);
		
	}
	
	/**
	 * 删除节点
	 * @param item
	 */
	public void deleteNode(int item){

		Node node = get(item);
		if(node == null)
			return;
		Node parent = node.getParent();
		if(!node.hasChild()){//叶子节点
			if(parent == null){//删除最后节点
				root = null;
				return;
			}
			if(node.hasBrother()){//node有兄弟节点时，需要判断是否需要调用平衡方法
				if(node == parent.getLeft())
					isBalance(node, 1);
				else
					isBalance(node, -1);
				parent.deleteChildNode(node);
			}else{//node没有兄弟节点时，高度减一，需要进行平衡
				deleteAvl(node);
				parent.deleteChildNode(node);
			}
		}else if(node.getLeft() != null && node.getRight() == null){//有一个子节点时，将子节点上移一位，然后进行平衡即可
			if(parent == null){//删除的是跟节点
				root = node;
				return;
			}
			if(node == parent.getLeft()){
				parent.setLeft(node.getLeft());
			}else{
				parent.setRight(node.getLeft());
			}
			node.getLeft().setParent(parent);
			deleteAvl(node.getLeft());
		}else if(node.getLeft() == null && node.getRight() != null){//有一个子节点时，将子节点上移一位，然后进行平衡即可
			if(parent == null){//删除的是跟节点
				root = node;
				return;
			}
			if(node == parent.getRight()){
				parent.setRight(node.getRight());
			}else{
				parent.setLeft(node.getRight());
			}
			node.getRight().setParent(parent);
			deleteAvl(node.getRight());
		}
		else{//有两个子节点时，先在节点左树寻找最大节点last，然后删除last，最后将被删除节点的value替换为last的value
			Node last = findLastNode(node);
			int tmp = last.getValue();
			deleteNode(last.getValue());
			node.setValue(tmp);
		}
		node = null;//GC
	}
	
	/**
	 * 判断是否需要平衡
	 * @param node
	 * @param avl
	 */
	private void isBalance(Node node, int avl){
		
		Node parent = node.getParent();
		if(avl == 1){
			if(parent.getAVL() + 1 > 1){
				deleteAvl(node);
			}else{
				parent.addAVL();
			}
		}else if(avl == -1){
			if(parent.getAVL() - 1 < -1){
				deleteAvl(node);
			}else{
				parent.subAVL();
			}
		}
	}

	/**
	 * 搜索node节点左树的最大节点，用于替换被删除节点
	 * @param n
	 * @return
	 */
	private Node findLastNode(Node n){
		
		Node last = null;
		Node node = n.getLeft();
		if(node != null){
			do{
				last = node;
				node = node.getRight();
			}while(node != null);
		}
		return last;
	}
	
	/**
	 * 删除时平衡上级节点
	 * @param node
	 */
	private void deleteAvl(Node node){
		
		Node node_middle = node;
		Node parent = node_middle.getParent();
		Node node_prev = node_middle;
		boolean avl = true;
		
		do{
			node_prev = node_middle;
			if(node_middle == parent.getLeft() && (parent.getAVL() + 1 <= 1)){
				if(parent.getAVL() == 0){
					parent.addAVL(); 
					return;
				}
				parent.addAVL();
				node_middle = parent;
				parent = node_middle.getParent();
			}else if(node_middle == parent.getRight() && (parent.getAVL() - 1 >= -1)){
				if(parent.getAVL() == 0){
					parent.subAVL();
					return;
				}
				parent.subAVL();
				node_middle = parent;
				parent = node_middle.getParent();
			}else{
				//由于删除时有可能进行多次平衡，所以不直接使用node_middle的值，防止影响之后的循环
				Node middle = node_middle.getBrother();
				Node child = middle.getAVL() == -1 ? middle.getLeft() : (middle.getAVL() == 1 ? middle.getRight() : (parent.getAVL() == -1 ? middle.getLeft() : middle.getRight()));
				int height = chooseCalculation(parent, middle, child);
				if(height == 0)
					return;
				node_middle = parent.getParent();
				parent = node_middle.getParent();
			}
		}while(parent != null && avl);
	}
}

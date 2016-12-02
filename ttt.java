package test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

public class ttt {

	public static void main(String[] args) {
		
		try {
			for(int n = 0; n < 100; n++){
				test();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static void test(){
		int[] list = new int[100];
		List list2 = new ArrayList();
		List list3 = new ArrayList();
		for(int n = 0; n < 90; n++){
			list[n] = n+10;
		}
		AVLTree tree = new AVLTree();
		for(int n = 0; n < 30; n++){
			int a = list[(int)(Math.random()*89)];
			list2.add(a);
			tree.add(a);
		}
		System.out.println("添加完毕------------------------------");
		for(int n = 0; n < 90; n++){
			int a = list[(int)(Math.random()*89)];
			list3.add(a);
			tree.deleteNode(a);
		}
		System.out.println("删除完毕------------------------------");
		printTree(tree,1);
		System.out.println("list2=="+list2.toString());
		System.out.println("list3=="+list3.toString());
	}
	
	private static void printTree(AVLTree tree,int item){
		System.out.println("树高度="+tree.treeLength()+"，修改节点为："+item);
		System.out.println("树打印如下-----------------------------------------");
		tree.printByPhoto();
		System.out.println("树打印结束-----------------------------------------");
	}
}


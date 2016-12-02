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
		
//		test();
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
			//printTree(tree,a);
		}
		System.out.println("添加完毕------------------------------");
		for(int n = 0; n < 90; n++){
			int a = list[(int)(Math.random()*89)];
			list3.add(a);
			tree.deleteNode(a);
			//printTree(tree,a);
		}
		System.out.println("删除完毕------------------------------");

		
//		int[] add = {12, 88, 42, 19, 75, 40, 17, 45, 81, 22, 73, 70, 57, 48, 52, 48, 88, 32, 15, 94, 20, 24, 86, 10, 50, 76, 67, 32, 72, 97};
//		int[] sub = {10, 38, 47, 96, 82, 80, 58, 45, 59, 37, 21, 57, 21, 50, 55, 26, 62, 36, 68, 63, 10, 16, 61, 63, 43, 31, 88, 65, 81, 65};
//		for(int n = 0; n < add.length; n++){
//			tree.add(add[n]);
//			printTree(tree,add[n]);
//		}
//		System.out.println("添加完毕------------------------------");
//		for(int n = 0; n < sub.length; n++){
//			tree.deleteNode(sub[n]);
//			printTree(tree,sub[n]);
//		}
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


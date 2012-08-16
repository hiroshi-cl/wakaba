package com.github.hiroshi_cl.wakaba.v2009.sol.dp2009;
import java.math.*;
import java.util.*;
import java.io.*;
import static java.lang.Math.*;
import static java.math.BigInteger.*;
import static java.util.Arrays.*;
import static java.util.Collections.*;

class D {
	static void debug(Object... os) {
		System.err.println(deepToString(os));
	}

	public static void main(String[] args) {
		new D().run();
	}

	void run() {
		Scanner sc = new Scanner(System.in);
		try {
			sc = new Scanner(new File("D"));
			System.setOut(new PrintStream("D.out"));
		} catch (Exception e) {
		}
		for(;;){
			int n=sc.nextInt();
			if(n==0)break;
			root=new Node(0,1000000001,-1);
			for(int i=0;i<n;i++){
				char c=sc.next().charAt(0);
				switch (c) {
				case 'D':
					root.delete(sc.nextInt());
					break;
				case 'R':
					System.out.println(root.read(sc.nextInt()));
					break;
				case 'W':
					root.write(sc.nextInt(), sc.nextInt());
				default:
					break;
				}
			}
			System.out.println();
		}
	}
	Node root;
	class Node{
		Node left,right;
		int id;
		int s,t;
		Node(int s,int t,int id){
			this.s=s;
			this.t=t;
			this.id=id;
		}
		int read(int i){
			if(left==null && right==null){
				return id;
			}
			else{
				if(i<=left.t){
					return left.read(i);
				}
				else{
					return right.read(i);
				}
			}
		}
		void delete(int i){
			if(id==i)id=-1;
			if(left!=null){
				left.delete(i);
			}
			if(right!=null)right.delete(i);
		}
		int write(int i,int length){
			if(length==0)return 0;
			if(left==null){
				if(id==-1){
					int mylen=t-s+1;
					if(mylen <= length){
						id=i;
						return mylen;
					}
					else{
						left=new Node(s,s+length-1,i);
						right=new Node(s+length,t,-1);
						return length;
					}
				}
				else{
					return 0;
				} 
			}
			else{
				int res=left.write(i, length);
				res+=right.write(i, length-res);
				return res;
			}
		}
	}
}


package com.github.hiroshi_cl.wakaba.v2009.sol.dp2009;
import java.io.*;
import java.math.*;
import java.util.*;

import static java.lang.Math.*;
import static java.math.BigInteger.*;
import static java.util.Arrays.*;
import static java.util.Collections.*;

class B{
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		try {
			sc = new Scanner(new File("B"));
			System.setOut(new PrintStream("B.out"));
		} catch (Exception e) {
		}
		int max=200000;
		boolean[] bs=new boolean[max];
		fill(bs,true);
		bs[0]=bs[1]=false;
		for(int i=2;i<max;i++){
			if(bs[i]){
				for(int j=2;j*i<max;j++){
					bs[i*j]=false;
				}
			}
		}
		for(;;){
			int n=sc.nextInt(),p=sc.nextInt();
			if(n==-1 && p==-1)break;
			int[] ps=new int[100];
			for(int i=n+1,j=0;j<100;i++){
				if(bs[i]){
					ps[j++]=i;
				}
			}
			ArrayList<Integer> list=new ArrayList<Integer>();
			for(int i=0;i<100;i++){
				for(int j=i;j<100;j++){
					list.add(ps[i]+ps[j]);
				}
			}
			sort(list);
			System.out.println(list.get(p-1));
		}
	}
	static void debug(Object... o){
		System.err.println(deepToString(o));
	}
}
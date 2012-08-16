package com.github.hiroshi_cl.wakaba.v2009.sol.d2008;
import java.util.*;
import java.io.*;
public class B {
	static boolean p(int x) {
		return (x%7 == 1) || (x%7==6);
	}
	
	public static void main(String[] args) throws Exception {
		Scanner sc = new Scanner(new File("B.in"));
		System.setOut(new PrintStream("B.out"));
//		sc = new Scanner(System.in);
		List<Integer> pr = new ArrayList<Integer>();
		boolean [] comp = new boolean[300000];
		for(int i=2;i<550;i++) {
			if(p(i) && !comp[i]) {
				for(int j = 2;i*j<300000;j++) {
					comp[i*j] = true;
				}
				pr.add(i);
			}
		}
		for(int i=550;i<300000;i++) {
			if(p(i) && !comp[i]) pr.add(i);
		}
		//System.out.println(pr);
		for(int n = sc.nextInt(); n != 1; n = sc.nextInt()) {
			System.out.print(n+":");
			for(int i = 0;i<pr.size();i++) {
				if(n%pr.get(i) == 0) 
					System.out.print(" "+pr.get(i));
			}
			System.out.println();
		}
	}
}

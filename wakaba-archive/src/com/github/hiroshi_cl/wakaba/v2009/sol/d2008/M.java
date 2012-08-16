package com.github.hiroshi_cl.wakaba.v2009.sol.d2008;
import java.util.*;
import java.io.*;
public class M {
	public static void main(String[] args) throws Exception {
		final int section = 17;
		Scanner sc = new Scanner(new File("M.in"));
		System.setOut(new PrintStream("M.out"));
		List<Integer> l = new ArrayList<Integer>();
		for(int i = sc.nextInt(); i > 0; i = sc.nextInt()) {
			l.add(i);
		}
		Queue<Integer> q = new PriorityQueue<Integer>(l);
		for(int i = 0; i < section; i++)
			q.poll();
		int inf = q.poll();
		int sup = q.poll();
		if(sup - inf == 1)
			System.err.println("!");
//		int sn = inf+1;
		int sn = 2029;
		for(int i : l)
			if(i > sn)
				System.out.println("larger");
			else
				System.out.println("smaller");
	}
}

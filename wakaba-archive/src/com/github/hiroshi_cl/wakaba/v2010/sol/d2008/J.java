package com.github.hiroshi_cl.wakaba.v2010.sol.d2008;
import java.util.*;
import java.io.*;
public class J {
	public static void main(String[] args) throws Exception {
		Scanner sc = new Scanner(new File("J.in"));
		System.setOut(new PrintStream("J.out"));
		int max = 0;
		for(int i = sc.nextInt(); i > 0; i = sc.nextInt()) {
			max = Math.max(i, max);
		}
		System.out.println(max);
	}
}

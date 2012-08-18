package com.github.hiroshi_cl.wakaba.v2010.sol.d2008;
import java.util.*;
import java.io.*;
public class K {
	public static void main(String[] args) throws Exception {
		Scanner sc = new Scanner(new File("K.in"));
		System.setOut(new PrintStream("K.out"));
		for(int i = sc.nextInt(), j = sc.nextInt(); (i|j) > 0; i = sc.nextInt(), j = sc.nextInt()) {
			System.out.println((double) i / j);
		}
	}
}

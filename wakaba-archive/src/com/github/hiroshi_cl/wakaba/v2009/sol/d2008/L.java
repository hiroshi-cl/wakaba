package com.github.hiroshi_cl.wakaba.v2009.sol.d2008;
import java.util.*;
import java.io.*;
public class L {
	public static void main(String[] args) throws Exception {
		Scanner sc = new Scanner(new File("L.in"));
		System.setOut(new PrintStream("L.out"));
		int cnt = 0;
		for(; sc.hasNextLine(); sc.nextLine()) {
			cnt++;
		}
		System.out.println(cnt);
	}
}

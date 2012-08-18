package com.github.hiroshi_cl.wakaba.v2010.sol.r2001;
import java.util.*;
import static java.lang.Math.*;
import static java.util.Arrays.*;
import static java.util.Collections.*;
public class A {
	public static void main(String[] args) {
		new A().run();
	}
	TreeSet<Integer> set = new TreeSet<Integer>();
	void run() {
		Scanner sc = new Scanner(System.in);
		for (int i = 0; i < 54; i++) {
			for (int j = 0; j < 96; j++) {
				set.add(i * i * i + j * (j + 1) * (j + 2) / 6);
			}
		}
		for (;;) {
			int n = sc.nextInt();
			if (n == 0) return;
			System.out.println(tail(n));
		}
	}
	int tail(int n) {
		SortedSet<Integer> sortedSet = set.headSet(n + 1);
		return sortedSet.last();
	}
}
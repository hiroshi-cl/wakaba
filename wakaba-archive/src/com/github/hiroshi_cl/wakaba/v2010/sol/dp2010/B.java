package com.github.hiroshi_cl.wakaba.v2010.sol.dp2010;

import java.util.*;
import java.math.*;
import java.io.*;
import static java.util.Arrays.*;
import static java.util.Collections.*;
import static java.lang.Math.*;

public class B {
	public static void main(String[] args) {
		new B().run();
	}

	void debug(Object... os) {
		System.err.println(deepToString(os));
	}

	void run() {
		Scanner sc = new Scanner(System.in);
		try {
			String s = this.getClass().getSimpleName();
			sc = new Scanner(new File(s));
			System.setOut(new PrintStream(s + ".out"));
		} catch (Exception e) {
		}
		for (;;) {
			int N = sc.nextInt();
			if (N == 0)
				return;
			String[] L = new String[N];
			int[] P = new int[N];
			int[] A = new int[N];
			int[] B = new int[N];
			int[] C = new int[N];
			int[] D = new int[N];
			int[] E = new int[N];
			int[] F = new int[N];
			int[] S = new int[N];
			int[] M = new int[N];
			for (int i = 0; i < N; i++) {
				L[i] = sc.next();
				P[i] = sc.nextInt();
				A[i] = sc.nextInt();
				B[i] = sc.nextInt();
				C[i] = sc.nextInt();
				D[i] = sc.nextInt();
				E[i] = sc.nextInt();
				F[i] = sc.nextInt();
				S[i] = sc.nextInt();
				M[i] = sc.nextInt();
			}
			ArrayList<T> list = new ArrayList<T>();
			for (int i = 0; i < N; i++) {
				int get = F[i] * M[i] * S[i];
				int tm = A[i] + B[i] + C[i] + (D[i] + E[i]) * M[i];
				list.add(new T(L[i], get - P[i], tm));
			}
			sort(list);
			for (T t : list) {
				System.out.println(t.name);
			}
			System.out.println("#");
		}
	}

	final double EPS = 1e-7;

	boolean eq(double a, double b) {
		return Math.abs(a - b) < EPS;
	}

	class T implements Comparable<T> {
		String name;
		int time;
		int cost;

		T(String n, int c, int t) {
			name = n;
			cost = c;
			time = t;
		}

		public int compareTo(T o) {
			long x = cost * o.time;
			long y = time * o.cost;
			return x == y ? name.compareTo(o.name) : (int) (y - x);
		}
	}
	/*
	 * class T implements Comparable<T>{ String name; int cost; int time; double
	 * rate;
	 * 
	 * T(String nm, int c, int t) { name = nm; cost = c; time = t; rate =
	 * (double)cost / (double)time; }
	 * 
	 * public int compareTo(T o) { return eq(o.rate,rate) ?
	 * name.compareTo(o.name) : (int)Math.signum(o.rate - rate); } }
	 */
}

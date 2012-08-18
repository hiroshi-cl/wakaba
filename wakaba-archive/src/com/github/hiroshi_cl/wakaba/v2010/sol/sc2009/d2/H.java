package com.github.hiroshi_cl.wakaba.v2010.sol.sc2009.d2;
import java.io.*;
import java.math.*;
import java.util.*;
import static java.lang.Math.*;
import static java.math.BigInteger.*;
import static java.util.Arrays.*;
import static java.util.Collections.*;
class H {
	void debug(Object... os) {
		System.err.println(deepToString(os));
	}
	static final int[] ps = { 13, 17, 19, 23 };
	static final int T = 55440;
	void run() {
		Scanner sc = new Scanner(System.in);
		for (int o = 1;; o++) {
			 try {
			 sc = new Scanner(new File(o + ".in"));
			 System.setOut(new PrintStream(o + ".out"));
			 } catch (Exception e) {
			 return;
			 }
			int n = sc.nextInt();
			int[] d = new int[n];
			int[] t = new int[n];
			int[][] q = new int[n][];
			for(int i = 0; i < n; i++) {
				d[i] = sc.nextInt();
				t[i] = sc.nextInt();
				q[i] = new int[d[i]];
				for(int j = 0; j < d[i]; j++)
					q[i][j] = sc.nextInt();
			}
			int[] vol = new int[T];
			int[][] vol2 = new int[24][];
			for(int i : ps)
				vol2[i] = new int[i];
			for(int i = 0; i < n; i++)
				if(d[i] != 13 && d[i] != 17 && d[i] != 19 && d[i] != 23)
					for(int j = 0; j < T; j++)
						vol[j] += q[i][(j + t[i]) % d[i]];
				else
					for(int j = 0; j < d[i]; j++)
						vol2[d[i]][j] += q[i][(j + t[i]) % d[i]];

			int max = 0;
			for(int i = 0; i < T; i++)
				max = max(max, vol[i]);
			for(int i : ps) {
				int max2 = 0;
				try {
					for(int j = 0; j < i; j++)
						max2 = max(max2, vol2[i][j]);
				} catch (Exception e) {
					debug(i);
					e.printStackTrace();
					return;
				}
				max += max2;
			}
			System.out.println(max);

			Scanner sc1, sc2;
			try {
				sc1 = new Scanner(new File(o + ".out"));
				sc2 = new Scanner(new File(o + ".diff"));
				boolean ok = true;
				while (sc1.hasNext()) {
					if (!sc1.next().equals(sc2.next())) {
						ok = false;
						break;
					}
				}
				if (sc2.hasNext()) ok = false;
				System.err.println(ok);
			} catch (Exception e) {}
		}
	}
	double EPS = 1e-7;
	boolean eq(double a, double b) {
		return abs(a - b) < EPS;
	}
	boolean lt(double a, double b) {
		return b - a > EPS;
	}
	public static void main(String[] args) {
		new H().run();
	}
}

package com.github.hiroshi_cl.wakaba.v2010.sol.sc2009.d2;
import java.io.*;
import java.math.*;
import java.util.*;
import static java.lang.Math.*;
import static java.math.BigInteger.*;
import static java.util.Arrays.*;
import static java.util.Collections.*;
class C {
	void debug(Object... os) {
		System.err.println(deepToString(os));
	}
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
			char[] c1 = sc.next().toCharArray();
			char[] cs = sc.next().toCharArray();
			int[] is = new int[n];
			for (int i = 0; i < n; i++) {
				is[i] = c1[i] - cs[i];
				if (is[i] < 0) is[i] += 10;
			}
			System.out.println(dfs(is, 0));
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
	int dfs(int[] is, int id) {
		if (id == is.length) return 0;
		if (is[id] == 0) return dfs(is, id + 1);
		int res = 10;
		int d = is[id];
		for (int i = id; i < is.length; i++) {
			is[i] -= d;
			if (is[i] < 0) is[i] += 10;
			res = min(res, dfs(is, id + 1) + 1);
		}
		for (int i = id; i < is.length; i++) {
			is[i] += d;
			if (is[i] >= 10) is[i] -= 10;
		}
		return res;
	}
	public static void main(String[] args) {
		new C().run();
	}
}

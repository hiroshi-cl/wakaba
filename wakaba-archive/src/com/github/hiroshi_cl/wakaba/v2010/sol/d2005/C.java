package com.github.hiroshi_cl.wakaba.v2010.sol.d2005;

import java.util.*;
import java.io.*;
import java.math.*;
import static java.util.Arrays.*;
import static java.util.Collections.*;
import static java.lang.Math.*;

public class C {
	public static void main(String[] args) throws Exception {
		try {
			System.setIn(new FileInputStream(new File("C.in")));
			System.setOut(new PrintStream("C.out"));
		} catch (Exception e) {
		}
		new C().run();
	}

	static void debug(Object... o) {
		System.err.println(deepToString(o));
	}

	int INF = 1 << 29;
	double EPS = 1e-9;

	void run() {
		Scanner sc = new Scanner(System.in);
		int o = sc.nextInt();
		char[] ls = new char[] { 'm', 'c', 'x', 'i' };
		int[] ps = new int[] { 1000, 100, 10, 1 };
		while (o-- > 0) {
			char[][] cs = new char[2][];
			for (int i = 0; i < 2; i++)
				cs[i] = sc.next().toCharArray();
			int[] ns = new int[2];
			for (int i = 0; i < 2; i++)
				for (int j = 0; j < cs[i].length; j++) {
					int d = 1;
					if ('0' <= cs[i][j] && cs[i][j] <= '9') {
						d = cs[i][j] - '0';
						j++;
					}
					for (int k = 0; k < 4; k++) {
						if (cs[i][j] == ls[k]) {
							ns[i] += d * ps[k];
						}
					}
				}
			// debug(ns);
			int n = ns[0] + ns[1];
			int[] ts = new int[4];
			int r = 10;
			for (int i = 3; i >= 0; i--) {
				ts[i] = n % r;
				n /= r;
			}
			// debug(ts);
			String s = "";
			for (int i = 0; i < 4; i++) {
				if (ts[i] == 0)
					continue;
				if (ts[i] == 1) {
					s = s + ls[i];
				} else
					s += ts[i] + "" + ls[i];
			}
			System.out.println(s);
		}
	}

	int signum(double d) {
		return d < -EPS ? -1 : d > EPS ? 1 : 0;
	}
}

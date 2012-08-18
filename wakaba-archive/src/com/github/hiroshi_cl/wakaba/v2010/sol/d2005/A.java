package com.github.hiroshi_cl.wakaba.v2010.sol.d2005;

import java.util.*;
import java.io.*;
import java.math.*;
import static java.util.Arrays.*;
import static java.util.Collections.*;
import static java.lang.Math.*;

public class A {
	public static void main(String[] args) throws Exception {
		try {
			System.setIn(new FileInputStream(new File("A.in")));
			System.setOut(new PrintStream("A.out"));
		} catch (Exception e) {
		}
		new A().run();
	}

	static void debug(Object... o) {
		System.err.println(deepToString(o));
	}

	int INF = 1 << 29;
	double EPS = 1e-9;

	void run() {
		Scanner sc = new Scanner(System.in);
		int o = sc.nextInt();
		while (o-- > 0) {
			int M = sc.nextInt();
			int m = sc.nextInt(), n = sc.nextInt();
			double max = 0;
			for (int i = 0; i < n; i++) {
				double res = M;
				if (sc.nextInt() == 0) {
					double d = sc.nextDouble();
					double sub = sc.nextDouble();
					int sum = 0;
					for (int j = 0; j < m; j++) {
						sum += (int) (res * d);
						res -= sub;
					}
					max = max(max, res + sum);
				} else {
					double d = sc.nextDouble();
					double sub = sc.nextDouble();
					for (int j = 0; j < m; j++) {
						res += (int) (res * d) - sub;
					}
					max = max(max, res);
				}
			}
			System.out.println((int) max);
		}
	}

	int signum(double d) {
		return d < -EPS ? -1 : d > EPS ? 1 : 0;
	}
}

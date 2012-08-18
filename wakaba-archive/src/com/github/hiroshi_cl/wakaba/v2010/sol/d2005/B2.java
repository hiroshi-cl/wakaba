package com.github.hiroshi_cl.wakaba.v2010.sol.d2005;

import java.io.*;
import java.util.*;
import java.math.*;
import static java.lang.Math.*;
import static java.math.BigInteger.*;
import static java.util.Arrays.*;
import static java.util.Collections.*;

public class B2 {
	public static void main(String[] args) {
		Scanner sc = null;
		try {
			sc = new Scanner(new File("B.in"));
			System.setOut(new PrintStream("B.out"));
		} catch (Exception e) {
		}
		new B2().run(sc);
	}

	static void debug(Object... os) {
		// System.err.println(deepToString(os));
	}

	static int INF = 1 << 27;
	static double EPS = 1e-9;

	static int signum(double d) {
		return d < -EPS ? -1 : d > EPS ? 1 : 0;
	}

	static boolean eq(double a, double b) {
		return signum(a - b) == 0;
	}

	void run(Scanner sc) {
		// int o = sc.nextInt();
		while (true) {
			int n = sc.nextInt();
			if (n == 0)
				return;
			P[][] ps = new P[n + 1][];
			P[][] psrev = new P[n + 1][];
			for (int i = 0; i <= n; i++) {
				int m = sc.nextInt();
				ps[i] = new P[m];
				psrev[i] = new P[m];
				for (int j = 0; j < m; j++) {
					ps[i][j] = new P(sc.nextInt(), sc.nextInt());
				}
				for (int j = 0; j < m; j++) {
					psrev[i][m - 1 - j] = ps[i][j];
				}
			}
			for (int i = 1; i <= n; i++) {
				if (equivalent(ps[0], ps[i]) || equivalent(ps[0], psrev[i]))
					System.out.println(i);
			}
			System.out.println("+++++");
		}
	}

	boolean equivalent(P[] ps, P[] qs) {
		int n = ps.length;
		if (ps.length != qs.length)
			return false;
		for (int i = 1; i < n; i++) {
			if (ps[i].sub(ps[i - 1]).norm2() != qs[i].sub(qs[i - 1]).norm2())
				return false;
		}
		for (int i = 2; i < n; i++) {
			if (ps[i].sub(ps[i - 1]).dot(ps[i - 2].sub(ps[i - 1])) != qs[i]
					.sub(qs[i - 1]).dot(qs[i - 2].sub(qs[i - 1])))
				return false;
		}
		return true;
	}

	class P {
		int x, y;

		P(int x, int y) {
			this.x = x;
			this.y = y;
		}

		P sub(P p) {
			return new P(x - p.x, y - p.y);
		}

		int dot(P p) {
			return x * p.y - y * p.x;
		}

		int norm2() {
			return x * x + y * y;
		}
	}
}
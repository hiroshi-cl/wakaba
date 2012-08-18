package com.github.hiroshi_cl.wakaba.v2010.sol.d2005;

import java.io.*;
import java.util.*;
import java.math.*;
import static java.lang.Math.*;
import static java.math.BigInteger.*;
import static java.util.Arrays.*;
import static java.util.Collections.*;

public class A2 {
	public static void main(String[] args) {
		Scanner sc = null;
		try {
			sc = new Scanner(new File("A.in"));
			System.setOut(new PrintStream("A.out"));
		} catch (Exception e) {
		}
		new A2().run(sc);
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
		int o = sc.nextInt();
		while (o-- > 0) {
			int first = sc.nextInt();
			int year = sc.nextInt();
			int n = sc.nextInt();
			int res = 0;
			for (int i = 0; i < n; i++) {
				boolean tanri = sc.nextInt() == 0;
				double riritu = sc.nextDouble();
				int tesu = sc.nextInt();
				int money = first;
				int risi = 0;
				for (int j = 0; j < year; j++) {
					if (tanri) {
						risi += (int) money * riritu;
						money -= tesu;
					} else {
						money += money * riritu - tesu;

					}
				}
				res = max(money + risi, res);
			}
			System.out.println(res);
		}
	}
}
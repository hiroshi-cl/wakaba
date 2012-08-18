package com.github.hiroshi_cl.wakaba.v2010.sol.d2005;

import java.io.*;
import java.util.*;
import java.math.*;
import static java.lang.Math.*;
import static java.math.BigInteger.*;
import static java.util.Arrays.*;
import static java.util.Collections.*;

public class C2 {
	public static void main(String[] args) {
		Scanner sc = null;
		try {
			sc = new Scanner(new File("C.in"));
			System.setOut(new PrintStream("C.out"));
		} catch (Exception e) {
		}
		new C2().run(sc);
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
			char[] cs = sc.next().toCharArray();
			char[] ds = sc.next().toCharArray();
			int res = c2i(cs) + c2i(ds);
			i2c(res);
		}
	}

	final char[] tani = new char[] { 'm', 'c', 'x', 'i' };

	int c2i(char[] cs) {
		int n = cs.length;
		int p = 0;
		int res = 0;
		while (p < n) {
			if (Character.isDigit(cs[p])) {
				switch (cs[p + 1]) {
				case 'm':
					res += (cs[p] - '0') * 1000;
					break;
				case 'c':
					res += (cs[p] - '0') * 100;
					break;
				case 'x':
					res += (cs[p] - '0') * 10;
					break;
				case 'i':
					res += (cs[p] - '0') * 1;
					break;
				}
				p += 2;
			} else {
				switch (cs[p]) {
				case 'm':
					res += 1000;
					break;
				case 'c':
					res += 100;
					break;
				case 'x':

					res += 10;
					break;
				case 'i':

					res += 1;
					break;
				}
				p++;
			}
		}
		return res;
	}

	void i2c(int n) {
		while (n > 0) {
			if (n >= 1000) {
				if (n / 1000 > 1) {
					System.out.print(n / 1000);
				}
				n %= 1000;
				System.out.print(tani[0]);
			}
			if (n >= 100) {
				if (n / 100 > 1) {
					System.out.print(n / 100);
				}
				n %= 100;
				System.out.print(tani[1]);
			}
			if (n >= 10) {
				if (n / 10 > 1) {
					System.out.print(n / 10);
				}
				n %= 10;
				System.out.print(tani[2]);
			}
			if (n >= 1) {
				if (n / 1 > 1) {
					System.out.print(n / 1);
				}
				n %= 1;
				System.out.print(tani[3]);
			}
		}
		System.out.println();
	}
}
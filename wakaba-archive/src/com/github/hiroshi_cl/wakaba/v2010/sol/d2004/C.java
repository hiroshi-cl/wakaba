package com.github.hiroshi_cl.wakaba.v2010.sol.d2004;

import java.util.*;
import static java.util.Arrays.*;
import static java.lang.Math.*;

public class C {
	public static void main(String[] args) {
		new C().run();
	}

	static void debug(Object... o) {
		System.err.println(deepToString(o));
	}

	void run() {
		Scanner sc = new Scanner(System.in);
		for (;;) {
			double p = sc.nextDouble(), q = sc.nextDouble();
			int a = sc.nextInt(), n = sc.nextInt();
			if (p + q + a + n == 0)
				break;
			System.out.println(calc(p / q, a, n, 1, 0, 0));
		}
	}

	int calc(double d, int a, int n, int before, double val, int res) {
		// debug(d,a,n,before,val,res);
		if (signum(val - d) == 0)
			return res + 1;
		if (n == 0 || signum(val - d) > 0)
			return res;
		for (int i = before; i <= a; i++) {
			res += calc(d, a / i, n - 1, i, val + 1.0 / i, res) - res;
		}
		return res;
	}

	double EPS = 1e-9;

	int signum(double d) {
		return d < -EPS ? -1 : d > EPS ? 1 : 0;
	}
}
package com.github.hiroshi_cl.wakaba.v2009.sol.ogie.pku1338;
import java.math.*;
import java.util.*;
import static java.lang.Math.*;
import static java.math.BigInteger.*;
import static java.util.Arrays.*;
import static java.util.Collections.*;

public class Main {
	public static void main(String[] args) {
		new Main().run();
	}

	void debug(Object... o) {
		System.err.println(deepToString(o));
	}

	static int INF = 1 << 28;
	static double EPS = 1e-9;
	int n = 1500;

	void run() {
		Scanner sc = new Scanner(System.in);
		int[] res = new int[n];
		res[0] = 1;
		for (int i = 1, i2 = 0, i3 = 0, i5 = 0; i < n; i++) {
			res[i] = min(res[i2] * 2, min(res[i3] * 3, res[i5] * 5));
			if (res[i] == res[i2] * 2)
				i2++;
			if (res[i] == res[i3] * 3)
				i3++;
			if (res[i] == res[i5] * 5)
				i5++;
		}
		for (;;) {
			int t = sc.nextInt();
			if (t == 0)
				return;
			System.out.println(res[t - 1]);
		}
	}
}
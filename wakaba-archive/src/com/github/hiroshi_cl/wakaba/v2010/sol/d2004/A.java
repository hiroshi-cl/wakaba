package com.github.hiroshi_cl.wakaba.v2010.sol.d2004;

import java.util.*;
import static java.util.Arrays.*;
import static java.lang.Math.*;

public class A {
	public static void main(String[] args) {
		new A().run();
	}

	static void debug(Object... o) {
		System.err.println(deepToString(o));
	}

	void run() {
		Scanner sc = new Scanner(System.in);
		for (;;) {
			int n = sc.nextInt(), r = sc.nextInt();
			if (n == 0)
				return;
			int ans = n;
			int[] p = new int[r], c = new int[r];
			for (int i = 0; i < r; i++) {
				p[i] = sc.nextInt();
				c[i] = sc.nextInt();
			}
			for (int i = r - 1; i >= 0; i--) {
				if (ans > n - p[i] - c[i] + 1) {
					if (ans > n - c[i])
						ans -= p[i] - 1;
					else
						ans += c[i];
				}
			}
			System.out.println(ans);
		}
	}
}
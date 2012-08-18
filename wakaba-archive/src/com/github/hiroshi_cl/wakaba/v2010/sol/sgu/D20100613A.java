package com.github.hiroshi_cl.wakaba.v2010.sol.sgu;

import java.util.*;
import java.math.*;
import static java.lang.Math.*;
import static java.util.ArrayList.*;
import static java.util.Collections.*;
import static java.math.BigInteger.*;

public class D20100613A {
	public static void main(String[] args) {
		new D20100613A().run();
	}

	void debug(Object... os) {
		System.err.println(os);
	}

	void run() {
		Scanner sc = new Scanner(System.in);
		String N = sc.next();
		int n = N.length();
		int k = sc.nextInt();
		StringBuilder sb = new StringBuilder();
		int ci = 0;
		int nk = n - k;
		for (;;) {
			int idx = nmax(N, ci, n - nk + 1);
			sb.append(N.charAt(idx));
			ci = idx + 1;
			nk--;
			if (nk == 0)
				break;
			if (ci >= n - nk) {
				sb.append(N.substring(ci));
				break;
			}
		}
		System.out.println(sb.toString());
	}

	int nmax(String n, int f, int t) {
		int r = 0;
		int idx = f;
		for (int i = f; i < t; i++) {
			if (n.charAt(i) > r) {
				idx = i;
				r = n.charAt(i);
			}
		}
		return idx;
	}
}

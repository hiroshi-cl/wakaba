package com.github.hiroshi_cl.wakaba.v2010.lib.math.numberTheory;

import static java.lang.Math.*;
import static java.math.BigInteger.*;
import java.util.Random;

/*
 *　素因数分解を高速に行う。
 *	rho algorithm を実装。
 */
public class PollardsRho {
	Random rand = new Random();

	// verified: ImosContest2 F (1478 ms).
	long rho(long n) {// Pollard's rho algorithm. 平均的に O(n ^ 1/4).
		for (;;) {
			long x = rand.nextLong() % n;
			long y = x;
			for (int i = 1, k = 2; i * i <= n; i++) {
				x = valueOf(x).pow(2).mod(valueOf(n)).longValue() - 1;
				long d = gcd(n, abs(y - x));
				if (1 < d && d < n)
					return d;
				if (i == k) {
					y = x;
					k <<= 1;
				}
			}
		}
	}

	private long gcd(long x, long y) {
		return y == 0 ? x : gcd(y, x % y);
	}

	public static void main(String[] args) {
		PollardsRho f = new PollardsRho();
		long st = System.currentTimeMillis();
		int n = 100;
		for (int o = 0; o < n; o++) {
			f.rho(308190188057107l);// 10177019 * 30282953.
		}
		System.err.println((double) (System.currentTimeMillis() - st) / n);
	}
}

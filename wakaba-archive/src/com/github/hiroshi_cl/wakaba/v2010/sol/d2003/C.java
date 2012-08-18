package com.github.hiroshi_cl.wakaba.v2010.sol.d2003;

import java.math.BigInteger;
import java.util.*;

import static java.lang.Math.*;
import static java.util.Arrays.*;
import static java.math.BigInteger.*;

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
			int w = sc.nextInt(), h = sc.nextInt();
			if ((w | h) == 0)
				return;
			char[][] c = new char[h][w];
			for (int i = 0; i < h; i++) {
				c[i] = sc.next().toCharArray();
			}
			BigInteger res = ZERO;
			BigInteger[][] dp = new BigInteger[h][w];
			for (int i = 0; i < h; i++) {
				for (int j = 0; j < w; j++) {
					if (Character.isDigit(c[i][j])) {
						if (i == 0) {
							if (j == 0) {
								dp[i][j] = BigInteger.valueOf(c[i][j] - '0');
							} else {
								dp[i][j] = dp[i][j - 1].multiply(TEN).add(
										valueOf(c[i][j] - '0'));

							}

						} else if (j == 0) {
							dp[i][j] = dp[i - 1][j].multiply(TEN).add(
									valueOf(c[i][j] - '0'));
						} else {
							dp[i][j] = (dp[i - 1][j]).max(dp[i][j - 1])
									.multiply(TEN).add(valueOf(c[i][j] - '0'));
						}
					} else {
						dp[i][j] = ZERO;
					}
				}
			}
			for (int i = 0; i < h; i++) {
				for (int j = 0; j < w; j++) {
					res = res.max(dp[i][j]);
				}
			}
			System.out.println(res);
		}
	}
}
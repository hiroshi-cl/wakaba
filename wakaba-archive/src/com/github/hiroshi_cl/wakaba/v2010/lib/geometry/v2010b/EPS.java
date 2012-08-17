package com.github.hiroshi_cl.wakaba.v2010.lib.geometry.v2010b;

/**
 * 本番では独立したクラスにしない方がよさそう
 */
public class EPS {
	static final double EPS = 1e-7;

	static int sgn(double d) {
		return d < -EPS ? -1 : d > EPS ? 1 : 0;
	}

	static boolean eq(double a, double b) {
		return sgn(a - b) == 0;
	}
}

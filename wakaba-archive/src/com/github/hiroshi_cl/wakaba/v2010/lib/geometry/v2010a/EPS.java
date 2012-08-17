package com.github.hiroshi_cl.wakaba.v2010.lib.geometry.v2010a;

public class EPS {
	final static double EPS = 1e-9;// problem specific.

	static boolean eq(double a, double b) {
		return sig(a - b) == 0;
	}

	static boolean le(double a, double b) {
		return sig(a - b) <= 0;
	}

	static boolean lt(double a, double b) {
		return sig(a - b) < 0;
	}

	static int sig(double d) {
		return d < -EPS ? -1 : d > EPS ? 1 : 0;
	}
}

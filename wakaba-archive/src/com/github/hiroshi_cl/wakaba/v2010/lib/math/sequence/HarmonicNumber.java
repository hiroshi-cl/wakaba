package com.github.hiroshi_cl.wakaba.v2010.lib.math.sequence;

import static java.lang.Math.*;

/*
 * 調和級数 H(n) = 1/1 + 1/2 + ... + 1/n .
 * 1/24(n+1)^2 <= H(n) - ln(n + 1/2) - γ <= 1/24(n)^2.
 * γ = 0.5772156649015313.
 * 
 * harmonicNumber(n,m)は、H(n) - H(m-1) を、誤差1e-9以内で計算する。
 * verified : TopCoder SRM400 DIV1 1000.
 * 
 * harmonicNumber(n) のverifyはまだ。
 */
public class HarmonicNumber {
	int BIG = 10000000;
	double gamma = 0.5772156649015313;

	// 1 / m + ... + 1 / n = H(n) - H(m-1).
	double harmonicNumber(long n, long m) {
		double res = 0;
		if (n < BIG) {
			while (m <= n)
				res += 1.0 / m++;
			return res;
		} else {
			while (m < BIG)
				res += 1.0 / m++;
			return res + log1p((2. * (n - m + 1)) / (2. * m - 1));
		}
	}

	double harmonicNumber(long n) {
		if (n < BIG) {
			double res = 0;
			for (int i = 1; i <= n; i++)
				res += 1.0 / i;
			return res;
		} else {
			return log(n + .5) - gamma;
		}
	}
}

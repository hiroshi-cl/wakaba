package com.github.hiroshi_cl.wakaba.v2010.lib.math.sequence;

/*
 kita_masaさんの方法により、線形漸化式の s 番目の値を高速に求める。

 - 入力: double[] ks , double d , long s.
 - 出力: a(i+n) = Σk(j)a(i+j) + d (a(i)=0 i<0)なる漸化式のa(s).

 - O( n^2 log s ).
 */

public class LinearRecurrenceEquation {
	double linearRecurrenceEquation(double[] ks, double d, long s) {
		int n = ks.length;
		int m = 0;
		for (double k : ks)
			if (k != 0)
				m++;
		int[] is = new int[m];
		double[] nks = new double[m];
		for (int i = 0, j = 0; i < n; i++) {
			if (ks[i] != 0) {
				is[j] = i;
				nks[j++] = ks[i];
			}
		}
		double[] as = new double[n];
		for (int i = 0; i < n; i++) {
			as[i] += d;
			for (int j = 0; j < m; j++) {
				if (i - n + is[j] >= 0) {
					as[i] += as[i - n + is[j]] * nks[j];
				}
			}
		}
		double[] bs = new double[n + 1];
		bs[1] = 1;
		if (n == 1)
			bs[0] = ks[0];
		double[] cs = new double[n + 1];
		cs[0] = 1;
		while (s > 0) {
			if ((s & 1) == 1)
				mul(cs, bs, is, nks);
			mul(bs, bs, is, nks);
			s >>= 1;
		}
		double res = cs[n] * d;
		for (int i = 0; i < n; i++) {
			res += cs[i] * as[i];
		}
		return res;
	}

	void mul(double[] bs, double[] cs, int[] is, double[] ks) {
		int n = bs.length - 1;
		double[] ds = new double[n * 2];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				ds[i + j] += bs[i] * cs[j];
			}
		}
		double dn = 0;
		for (int i = 0; i < n; i++) {
			dn += bs[i];
		}
		dn = dn * cs[n] + bs[n];
		for (int i = n * 2 - 2; i >= n; i--) {
			dn += ds[i];
			for (int j = 0; j < is.length; j++) {
				ds[i + is[j] - n] += ds[i] * ks[j];
			}
		}
		ds[n] = dn;
		System.arraycopy(ds, 0, bs, 0, n + 1);
	}
}

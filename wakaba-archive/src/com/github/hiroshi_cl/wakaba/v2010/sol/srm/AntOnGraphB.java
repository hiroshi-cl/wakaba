package com.github.hiroshi_cl.wakaba.v2010.sol.srm;

import java.util.*;
import java.util.regex.*;
import java.text.*;
import java.math.*;
import static java.lang.Math.*;
import static java.util.Arrays.*;
import static java.math.BigInteger.*;

public class AntOnGraphB {

	static final long INF = -4L * 500 * 100 * 1000000000;

	public String maximumBonus(String[] p0, String[] p1, String[] p2,
			int stepsPerSecond, int timeLimit) {
		int n = p0.length;

		Matrix m = new Matrix(n);
		for (int i = 0; i < n; i++)
			for (int j = 0; j < n; j++)
				m.a[i][j] = cnt(p0[i].charAt(j), p1[i].charAt(j),
						p2[i].charAt(j));

		m = pow(stepsPerSecond, m);
		m = pow(timeLimit, m.add(e(n)));

		return m.a[0][1] == INF ? "IMPOSSIBLE" : "" + m.a[0][1];
	}

	long cnt(char p0, char p1, char p2) {
		int r = (p0 - '0') * 100 + (p1 - '0') * 10 + (p2 - '0') - 500;
		return r == -500 ? INF : r;
	}

	Matrix pow(long n, Matrix m) {
		if (n == 0)
			return e(m.s);
		if (n == 1)
			return m;
		Matrix h = pow(n / 2, m);
		h = h.mul(h);
		return n % 2 == 0 ? h : h.mul(m);
	}

	Matrix e(int s) {
		Matrix m = new Matrix(s);
		for (int i = 0; i < s; i++)
			for (int j = 0; j < s; j++)
				m.a[i][j] = i == j ? 0 : INF;
		return m;
	}

	class Matrix {
		final long[][] a;
		final int s;

		public Matrix(int s_) {
			s = s_;
			a = new long[s][s];
		}

		public Matrix mul(Matrix m) {
			Matrix r = new Matrix(a.length);
			for (int i = 0; i < s; i++)
				for (int j = 0; j < s; j++) {
					r.a[i][j] = INF;
					for (int k = 0; k < s; k++)
						r.a[i][j] = max(r.a[i][j], a[i][k] + m.a[k][j]);
					if (r.a[i][j] < INF / 2)
						r.a[i][j] = INF;
				}
			return r;
		}

		public Matrix add(Matrix m) {
			Matrix r = new Matrix(a.length);
			for (int i = 0; i < s; i++)
				for (int j = 0; j < s; j++)
					r.a[i][j] = max(a[i][j], m.a[i][j]);
			return r;
		}

		@Override
		public String toString() {
			StringBuilder sb = new StringBuilder();
			for (long[] is : a)
				sb.append(Arrays.toString(is)).append('\n');
			return sb.toString();
		}
	}

}
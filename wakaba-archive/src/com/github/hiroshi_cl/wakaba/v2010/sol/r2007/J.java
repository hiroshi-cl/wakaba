package com.github.hiroshi_cl.wakaba.v2010.sol.r2007;

import static java.lang.Math.*;
import static java.math.BigInteger.*;
import static java.util.Arrays.*;
import static java.util.Collections.*;
import java.math.*;
import java.util.*;

public class J {
	public static void main(String[] args) {
		new J().run();
	}

	static void debug(Object... os) {
		// System.err.println(deepToString(os));
	}

	void run() {
		Scanner sc = new Scanner();
		for (;;) {
			int a = sc.nextInt(), m = sc.nextInt(), b = sc.nextInt(), n = sc
					.nextInt();
			if ((a | m | b | n) == 0)
				return;
			int d = m * n;
			long[][][] f = new long[d + 1][m][n];
			f[0][0][0] = 1;
			for (int i = 0; i < d; i++) {
				for (int j = 0; j < m; j++) {
					for (int k = 0; k < n; k++) {
						if (j < m - 1)
							f[i + 1][j + 1][k] += f[i][j][k];
						else
							f[i + 1][0][k] += f[i][j][k] * a;
						if (k < n - 1)
							f[i + 1][j][k + 1] += f[i][j][k];
						else
							f[i + 1][j][0] += f[i][j][k] * b;
					}
				}
			}
			double[][] A = new double[d][d];
			for (int i = 0; i < d; i++) {
				for (int j = 0; j < m; j++) {
					for (int k = 0; k < n; k++) {
						A[j * n + k][i] = f[i + 1][j][k];
					}
				}
			}
			double[][] inv = inverse(A, 0);
			int[] u = new int[d];
			u[0] = -1;
			double[] v = new double[d];
			for (int i = 0; i < d; i++) {
				for (int j = 0; j < d; j++) {
					v[i] += inv[i][j] * u[j];
				}
			}
			for (int i = d - 1; i >= 0; i--) {
				System.out.print(round(v[i] / v[d - 1]) + " ");
			}
			System.out.println(round(1 / v[d - 1]));
		}
	}

	double[][] inverse(double[][] A, double eps) {
		int n = A.length;
		double[][] res = iDentity(n);
		for (int j = 0; j < n; j++) {
			double val = eps;
			int pivot = -1;
			for (int i = j; i < n; i++) {
				if (abs(A[i][j]) > val) {
					val = abs(A[i][j]);
					pivot = i;
				}
			}
			if (pivot == -1)
				return null;
			double[] tmp = A[j];
			A[j] = A[pivot];
			A[pivot] = tmp;
			tmp = res[j];
			res[j] = res[pivot];
			res[pivot] = tmp;
			double d = A[j][j];
			for (int i = 0; i < n; i++) {
				res[j][i] /= d;
				A[j][i] /= d;
			}
			for (int i = 0; i < n; i++) {
				if (i != j) {
					d = A[i][j];
					for (int k = 0; k < n; k++) {
						A[i][k] -= A[j][k] * d;
						res[i][k] -= res[j][k] * d;
					}
				}
			}
		}
		return res;
	}

	// O(n).
	double[][] iDentity(int n) {
		double[][] res = new double[n][n];
		for (int i = 0; i < n; i++)
			res[i][i] = 1;
		return res;
	}

	class Scanner {
		int nextInt() {
			try {
				int c = System.in.read();
				while (c != '-' && (c < '0' || '9' < c))
					c = System.in.read();
				if (c == '-')
					return -nextInt();
				int res = 0;
				do {
					res *= 10;
					res += c - '0';
					c = System.in.read();
				} while ('0' <= c && c <= '9');
				return res;
			} catch (Exception e) {
				return -1;
			}
		}

		long nextLong() {
			try {
				int c = System.in.read();
				while (c != '-' && (c < '0' || '9' < c))
					c = System.in.read();
				if (c == '-')
					return -nextLong();
				long res = 0;
				do {
					res *= 10;
					res += c - '0';
					c = System.in.read();
				} while ('0' <= c && c <= '9');
				return res;
			} catch (Exception e) {
				return -1;
			}
		}

		double nextDouble() {
			return Double.parseDouble(next());
		}

		String next() {
			try {
				StringBuilder res = new StringBuilder("");
				int c = System.in.read();
				while (Character.isWhitespace(c))
					c = System.in.read();
				do {
					res.append((char) c);
				} while (!Character.isWhitespace(c = System.in.read()));
				return res.toString();
			} catch (Exception e) {
				return null;
			}
		}

		String nextLine() {
			try {
				StringBuilder res = new StringBuilder("");
				int c = System.in.read();
				while (c == '\r' || c == '\n')
					c = System.in.read();
				do {
					res.append((char) c);
					c = System.in.read();
				} while (c != '\r' && c != '\n');
				return res.toString();
			} catch (Exception e) {
				return null;
			}
		}
	}
}

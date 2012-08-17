package com.github.hiroshi_cl.wakaba.v2010.lib.math.numberTheory;

import static java.lang.Math.*;

/*
 * SternBrocotTreeは、左側に 0/1 ,右側に 1/0　をおき、m/n , m'/n' の中間に m+m'/n+n' をおくことを繰り返して生成される木。
 * 表れる分数はすべて既約分数であり、かつすべての既約分数が表れる。 また、各段階で、隣り合う分数には、mn'-nm' = 1　という性質がある。
 * ノードに対応する行列を、それを作る分数の、分母を上、分子を下に書いたものとする。 このとき、(1,1)に対応する行列は単位行列となる。
 * (1,1)をルートとしたとき、
 * find(m,n)は、(m,n)に至るノードのたどり方を返す。O(max(n,m)).
 * find(T)は、ノードのたどり方が与えられたとき、それが示す分数を返す。O(length).
 * find(r,N)は、無理数rが与えられたとき、分母がNを超えない、最もrに近い分数を返す。O(N).
 *
 * 参考文献：コンピュータの数学 p115 ~ 122.
 */
public class SternBrocotTree {
	class Ratio {
		long m, n; // m / n.

		Ratio(long m, long n) {
			this.m = m;
			this.n = n;
		}

		double value() {
			return (double) m / n;
		}
	}

	final long[][] I = new long[][] { { 1, 0 }, { 0, 1 } };
	final long[][] L = new long[][] { { 1, 1 }, { 0, 1 } };
	final long[][] R = new long[][] { { 1, 0 }, { 1, 1 } };

	long[][] mul(long[][] A, long[][] B) {
		long[][] C = new long[2][2];
		for (int i = 0; i < 2; i++)
			for (int j = 0; j < 2; j++)
				for (int k = 0; k < 2; k++)
					C[i][j] += A[i][k] * B[k][j];
		return C;
	}

	Ratio f(long[][] S) {
		return new Ratio(S[1][0] + S[1][1], S[0][0] + S[0][1]);
	}

	// TODO check.
	String find(long m, long n) {
		String res = "";
		while (m != n) {
			if (m < n) {
				res += "L";
				n -= m;
			} else {
				res += "R";
				m -= n;
			}
		}
		return res;
	}

	// TODO check.
	Ratio find(String T) {
		long[][] S = I;
		for (char c : T.toCharArray()) {
			if (c == 'L') {
				S = mul(S, L);
			} else {
				S = mul(S, R);
			}
		}
		return f(S);
	}

	// verified : Imos Contest E.
	Ratio find(double r, int N) {
		Ratio res = new Ratio(1, 1);
		long[][] S = I;
		double a = r;
		do {
			Ratio now = f(S);
			if (abs(now.value() - r) < abs(res.value() - r))
				res = now;
			if (a < 1) {
				a = a / (1 - a);
				S = mul(S, L);
			} else {
				a = a - 1;
				S = mul(S, R);
			}
		} while (f(S).n <= N);
		return res;
	}
}
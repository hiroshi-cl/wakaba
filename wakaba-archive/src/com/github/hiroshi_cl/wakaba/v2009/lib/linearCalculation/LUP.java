package com.github.hiroshi_cl.wakaba.v2009.lib.linearCalculation;

/*
 * アルゴリズムイントロダクション３巻 Ｐ１０９〜Ｐ１２０
 * @param A,b
 * @return x
 * Ax=b となるxを計算する
 * Aはn*n行列で正則であることが必要.
 * 正則でないときには、IllegalArgumentExceptionを投げる。
 *
 * verified: TopCoder SRM356 Hard.
 */
import static java.lang.Math.*;

class LUP {
	double[][] A; // input
	double[] b;// input
	double[][] L;
	double[][] U;
	int[] p;
	double[] x; // return
	int n;

	LUP(final double[][] A) {
		this.A = A;
		n = A.length;
		L = new double[n][n];
		U = new double[n][n];
		p = new int[n];
		x = new double[n];
		decomposition();
	}

	void solve(double[] b) {
		this.b = b;
		double[] y = new double[n];
		for (int i = 0; i < n; i++) {
			double sum = 0;
			for (int j = 0; j < i; j++)
				sum += L[i][j] * y[j];
			y[i] = b[p[i]] - sum;
		}
		for (int i = n - 1; i >= 0; i--) {
			double sum = 0;
			for (int j = i + 1; j < n; j++)
				sum += U[i][j] * x[j];
			x[i] = (y[i] - sum) / U[i][i];
		}
	}

	void decomposition() {
		for (int i = 0; i < n; i++) {
			p[i] = i;
		}
		for (int k = 0; k < n; k++) {
			double pivot = 0;
			int k_ = 0;
			for (int i = k; i < n; i++) {
				if (abs(A[i][k]) > pivot) {
					pivot = abs(A[i][k]);
					k_ = i;
				}
			}
			if (pivot == 0) {
				throw new IllegalArgumentException();
			}
			int temp = p[k];
			p[k] = p[k_];
			p[k_] = temp;
			for (int i = 0; i < n; i++) {
				double temp2 = A[k][i];
				A[k][i] = A[k_][i];
				A[k_][i] = temp2;
			}
			for (int i = k + 1; i < n; i++) {
				A[i][k] = A[i][k] / A[k][k];
				for (int j = k + 1; j < n; j++) {
					A[i][j] = A[i][j] - A[i][k] * A[k][j];
				}
			}
		}
		for (int i = 0; i < n; i++) {
			L[i][i] = 1;
			for (int j = 0; j < i; j++) {
				L[i][j] = A[i][j];
			}
			for (int j = i; j < n; j++) {
				U[i][j] = A[i][j];
			}
		}
	}
}
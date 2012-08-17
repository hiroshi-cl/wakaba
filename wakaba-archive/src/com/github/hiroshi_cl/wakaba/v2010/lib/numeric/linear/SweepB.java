package com.github.hiroshi_cl.wakaba.v2010.lib.numeric.linear;

import static java.lang.Math.abs;

/*
- 入力 : 行列Aとeps.
- 出力 : A の逆行列. 計算の途中でどの列の絶対値も eps 以下になったら存在しないとして、null を返す。

- O( n^3 ).
*/

public class SweepB {
	// O(n^3). 誤差に注意。
	double[][] inverse(double[][] A, double eps) {
		int n = A.length;
		double[][] res = identity(n);
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
	double[][] identity(int n) {
		double[][] res = new double[n][n];
		for (int i = 0; i < n; i++)
			res[i][i] = 1;
		return res;
	}
}
//  LUP分解による方法に比べ誤差が出やすい。
package com.github.hiroshi_cl.wakaba.v2010.lib.numeric.linear;

/*
- コンストラクタ : 行列を指定
- inverse : 逆行列
- rank : ランク
- det : 行列式
- row, col : 行と列の入れ替えを記録

- O(nmr). n,m:行列の大きさ, r:行列のランク
 */

public class SweepC {
	double[][] matrix;
	double[][] inverse;
	double det;
	int rank;
	int[] row, col;

	SweepC(double[][] matrix) {
		this.matrix = matrix;
		solve();
	}

	int signum(double d) {
		return d < -EPS ? -1 : d > EPS ? 1 : 0;
	}

	double EPS = 1e-7;

	void solve() {
		int r = 0;
		double d = 1;
		int m = matrix.length, n = matrix[0].length;
		double[][] z = new double[m][n + m]; // 拡大係数行列
		row = new int[m];
		col = new int[n];

		// 初期化
		for (int i = 0; i < m; i++)
			row[i] = i;
		for (int j = 0; j < n; j++)
			col[j] = j;
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++)
				z[i][j] = matrix[i][j];
			for (int j = 0; j < m; j++)
				z[i][n + j] = (i == j ? 1 : 0);
		}

		// 上三角化
		for (;;) {
			// ピボット選択
			double maxAbs = 0;
			int p = -1, q = -1;
			for (int i = r; i < m; i++) {
				for (int j = r; j < n; j++) {
					if (Math.abs(z[i][j]) > maxAbs) {
						maxAbs = Math.abs(z[i][j]);
						p = i;
						q = j;
					}
				}
			}
			if (signum(maxAbs) == 0)
				break;

			if (p != r) { // 行の入れ替え
				for (int j = 0; j < n + m; j++) {
					double tmp = z[p][j];
					z[p][j] = z[r][j];
					z[r][j] = tmp;
				}
				for (int i = 0; i < m; i++) {
					double tmp = z[i][n + p];
					z[i][n + p] = z[i][n + r];
					z[i][n + r] = tmp;
				}
				int tmp = row[p];
				row[p] = row[q];
				row[q] = tmp;
			}
			if (q != r) { // 列の入れ替え
				for (int i = 0; i < m; i++) {
					double tmp = z[i][q];
					z[i][q] = z[i][r];
					z[i][r] = tmp;
				}
				int tmp = col[q];
				col[q] = col[r];
				col[r] = tmp;
			}
			d = d * z[r][r];
			for (int i = r + 1; i < m; i++) {
				double a = z[i][r] / z[r][r];
				for (int j = 0; j < n + m; j++)
					z[i][j] -= a * z[r][j];
			}
			r++;
		}

		// 後退代入
		for (int k = r - 1; k > 0; k--) {
			for (int i = 0; i < k; i++) {
				double a = z[i][k] / z[k][k];
				for (int j = 0; j < n + m; j++)
					z[i][j] -= a * z[k][j];
			}
		}
		for (int k = 0; k < r; k++) {
			for (int j = k + 1; j < n + r; j++)
				z[k][j] /= z[k][k];
		}

		det = d;
		rank = r;
		inverse = new double[r][r];
		for (int i = 0; i < r; i++)
			for (int j = 0; j < r; j++)
				inverse[i][j] = z[i][n + j];
	}
}
/*
連立一次方程式系を解く手順
- 拡大係数行列を喰わす
- rank ＞ 変数の数 → 解なし
- rank ≦ 変数の数 → inverseを定数項ベクトルに掛ける
*/
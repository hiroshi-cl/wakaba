package com.github.hiroshi_cl.wakaba.v2009.lib.string;
/*
 * KMP法は、検索に失敗した時の戻り先をあらかじめ構築することで、計算量を減らす。
 * 文字列as(長さm)を、ts(長さn)から探す。
 * 構築:O(m) クエリ:O(n)
 *
 * verified pku:3461
 */
public class KMP {
	char[] as;
	int n, m;
	int[] f; // failure
	KMP(char[] as) {
		this.as = as;
		m = as.length;
		build();
	}
	void build() {// O(m)
		f = new int[m + 1];
		f[0] = -1;
		for (int i = 0, j = -1; i < m; f[++i] = ++j)
			while (j >= 0 && as[i] != as[j])
				j = f[j];
	}
	int serachFrom(char[] ts) {// マッチする回数。 O(n).
		n = ts.length;
		int res = 0;
		for (int i = 0, j = 0; i < n; i++) {
			while (j >= 0 && ts[i] != as[j])
				j = f[j];
			if (++j == m) {
				res++;
				j = f[j];
			}
		}
		return res;
	}
}
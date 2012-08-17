package com.github.hiroshi_cl.wakaba.v2010.lib.search.permutation;

/*
 - Next Permutation
 - 順列列挙

 大まかな説明

 isを、辞書順で、次の順列にする。
 辞書順で最後ならば、変更せず、falseを返す。

 verified : pku1146
 */

public class NextPermutation {
	boolean nextPermutation(int[] is) {
		int n = is.length;
		for (int i = n - 1; i > 0; i--) {
			if (is[i - 1] < is[i]) {
				int j = n;
				while (is[i - 1] >= is[--j])
					;
				swap(is, i - 1, j);
				rev(is, i, n);
				return true;
			}
		}
		rev(is, 0, n);
		return false;
	}

	void swap(int[] is, int i, int j) {
		int t = is[i];
		is[i] = is[j];
		is[j] = t;
	}

	void rev(int[] is, int s, int t) {
		while (s < --t)
			swap(is, s++, t);
	}
}

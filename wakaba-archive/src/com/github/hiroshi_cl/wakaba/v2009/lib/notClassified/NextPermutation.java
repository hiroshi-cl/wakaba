package com.github.hiroshi_cl.wakaba.v2009.lib.notClassified;

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

package com.github.hiroshi_cl.wakaba.v2010.lib.math.enumeration;

/*
 独立な subsequence の数を法 mod で返す( "" も含む )。 O(n).

 - 入力 : char[] cs, int mod 
 - 出力 : 独立な subsequence の数を法 mod で

 - O(n).
 */

public class DistinctSequences {
	int count(char[] cs, int mod) {
		int[] is = new int[256];
		int res = 1;
		for (int i = 0; i < cs.length; i++) {
			int t = res;
			res = (res << 1) - is[cs[i]];
			is[cs[i]] = t;
			if (res < 0)
				res += mod;
			if (res >= mod)
				res -= mod;
		}
		return res;
	}
}
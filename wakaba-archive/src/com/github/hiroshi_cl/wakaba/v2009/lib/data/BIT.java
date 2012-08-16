package com.github.hiroshi_cl.wakaba.v2009.lib.data;
/*
 * Binary Indexed Treesは、配列isに対し、以下を満たすデータ構造。
 * 1.is[id]に対する、加算（減算）に、O(log n).
 * 2.sum(is[s]...is[t-1]) を計算するのに、O(log n).
 *
 * 構築にO(n).
 *
 * verified: TC SRM424_900.
 *
 * 参考サイトhttp://www.topcoder.com/tc?module=Static&d1=tutorials&d2=binaryIndexedTrees
 */
public class BIT {
	int n;
	long[] is;
	long[] tree;

	BIT(long[] is) {
		this.n = is.length;
		this.is = is;
		build();
	}

	long sum(int s, int t) {// sum(is[s]...is[t-1]). O(log n).
		return read(t) - read(s - 1);
	}

	void add(int id, long val) {// O(log n).
		is[id++] += val;
		while (id <= n) {
			tree[id] += val;
			id += Integer.lowestOneBit(id);
		}
	}

	private void build() {
		long[] cs = new long[n + 1];
		for (int i = 0; i < n; i++)
			cs[i + 1] = cs[i] + is[i];
		tree = new long[n + 1];
		for (int i = 1; i <= n; i++)
			tree[i] = cs[i] - cs[i - Integer.lowestOneBit(i)];
	}

	private long read(int t) {// sum(is[0]...is[t]). O(log n).
		long res = 0;
		while (t > 0) {
			res += tree[t];
			t -= Integer.lowestOneBit(t);
		}
		return res;
	}
}
package com.github.hiroshi_cl.wakaba.v2010.lib.data.tree;

/*
 * Range Minimum Query
 * 
 * query(s,t) は、is[j] = min(is[s], ... ,is[t-1]) (tを含めない!)
 * なる、最小の j を返す。
 * s=>tの場合は、-1 を返す。
 * 
 * 前準備に、O(n logn)
 * クエリに対する応答に、O(1)
 * 
 * verified : pku1341
 */
public class RMQ {
	int[] is;
	int[][] rmq;
	int n;

	RMQ(int[] is) {
		this.is = is;
		this.n = is.length;
		build();
	}

	void build() {
		int logn = log2(n);
		rmq = new int[logn + 1][];
		rmq[0] = new int[n];
		for (int j = 0; j < n; j++)
			rmq[0][j] = j;
		for (int i = 2, li = 1; i <= n; i *= 2, li++) {
			rmq[li] = new int[n + 1 - i];
			for (int j = 0; j <= n - i; j++) {
				if (is[rmq[li - 1][j]] <= is[rmq[li - 1][j + i / 2]]) {
					rmq[li][j] = rmq[li - 1][j];
				} else {
					rmq[li][j] = rmq[li - 1][j + i / 2];
				}
			}
		}
	}

	int query(int s, int t) {// minId in is[s] ... is[t-1]
		int d = t - s;
		if (d <= 0)
			return -1;
		int logd = log2(d);
		return is[rmq[logd][t - (1 << logd)]] < is[rmq[logd][s]] ? rmq[logd][t
				- (1 << logd)] : rmq[logd][s];
	}

	int log2(int n) {
		return 31 - Integer.numberOfLeadingZeros(n);
	}
}
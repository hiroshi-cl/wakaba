package com.github.hiroshi_cl.wakaba.v2010.lib.data.tree;

/*
各要素の値が何度も変化するときに、変化後の区間の和を高速に求められる。
具体的には、
1. 要素に対する加算 : O(log n)
2. 適当な区間内の和 : O(log n)

- コンストラクタ: サイズ, 法
- sum (s,t) : is[s] ... is[t] の和 (両端を含む).
- add (id,val) : is[id] にval を足す.
- set (id,val) : is[id] にval をセット.

- O(log n).
*/

public class BIT {
	long[] is;
	int mod;

	BIT(int n, int mod) {
		is = new long[n + 1];
		this.mod = mod;
	}

	long sum(int s, int t) {// [s,t]
		if (s > 0)
			return (sum(0, t) - sum(0, s - 1) + mod) % mod;
		long res = 0;
		for (int i = t + 1; i > 0; i -= i & -i) {
			res += is[i];
		}
		return res % mod;
	}

	void add(int id, long val) {
		for (int i = id + 1; i < is.length; i += i & -i) {
			is[i] += val;
			is[i] %= mod;
		}
	}

	void set(int id, long val) {
		val -= sum(id, id);
		if (val < 0)
			val += mod;
		add(id, val);
	}
}

package com.github.hiroshi_cl.wakaba.v2009.sol.ogie;
public class ProductOfPrices {
	long MOD = 1000000007;
	public int product(int n, int L, int X0, int A, int B) {
		long a = A, b = B;
		int[] xs = new int[n];
		xs[0] = X0 % L;
		for (int i = 1; i < n; i++) {
			xs[i] = (int) ((xs[i - 1] * a + b) % L);
		}
		BIT dst = new BIT(new long[L]), cnt = new BIT(new long[L]);
		dst.add(xs[0], xs[0]);
		cnt.add(xs[0], 1);
		long res = 1;
		for (int i = 1; i < n; i++) {
			long c =
					(xs[i] * (cnt.sum(0, xs[i]) - cnt.sum(xs[i] + 1, L))
							- dst.sum(0, xs[i]) + dst.sum(xs[i] + 1, L))
							% MOD;
			c = (c + MOD) % MOD;
			res = (res * c) % MOD;
			dst.add(xs[i], xs[i]);
			cnt.add(xs[i], 1);
		}
		return (int) res;
	}
	class BIT {
		int n;
		long[] is;
		long[] tree;
		BIT(long[] is) {
			this.n = is.length;
			this.is = is;
			build();
		}
		long sum(int s, int t) {
			return (read(t) - read(s - 1) + MOD) % MOD;
		}
		void add(int id, long val) {
			is[id++] += val;
			while (id <= n) {
				tree[id] += val;
				tree[id] %= MOD;
				id += Integer.lowestOneBit(id);
			}
		}
		private void build() {
			long[] cs = new long[n + 1];
			for (int i = 0; i < n; i++)
				cs[i + 1] = cs[i] + is[i];
			tree = new long[n + 1];
			for (int i = 1; i <= n; i++) {
				int l = i - Integer.lowestOneBit(i);
				tree[i] = cs[i] - cs[l];
			}
		}
		long read(int t) {
			long res = 0;
			while (t > 0) {
				res += tree[t];
				res %= MOD;
				t -= Integer.lowestOneBit(t);
			}
			return res;
		}
	}
}

// Powered by FileEdit
// Powered by CodeProcessor

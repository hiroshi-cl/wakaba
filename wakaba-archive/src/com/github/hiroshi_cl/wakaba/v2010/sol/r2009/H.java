package com.github.hiroshi_cl.wakaba.v2010.sol.r2009;
import java.util.*;
import java.math.*;
import java.io.*;
import static java.util.Arrays.*;
import static java.util.Collections.*;
import static java.lang.Math.*;
import static java.math.BigInteger.*;

public class H {
	void debug(Object... os) {
		System.err.println(deepToString(os));
	}

	void run() {
		Scanner sc = new Scanner(System.in);
		try {
			sc = new Scanner(new File("H.in"));
		} catch (Exception e) {
		}
		for (;;) {
			m = sc.nextInt();
			n = sc.nextInt();
			if (m == 0 && n == 0)
				return;
			if (n == 1) {
				sc.next();
				System.out.println(0);
				continue;
			}
			bits = new int[n];
			for (int i = 0; i < n; i++) {
				char[] cs = sc.next().toCharArray();
				// debug(cs);
				for (int j = 0; j < m; j++) {
					bits[i] |= (1 << j) * (cs[j] - '0');
				}
				// debug(Integer.toBinaryString(bits[i]));
			}
			// int INF = 1 << 28;
			// res = INF;
			map = new HashMap<Entry, Integer>();
			int[] init = new int[m];
			fill(init, 2);
			Entry ie = new Entry(init);
			// ie.calcRemain();
			System.out.println(dfs(ie));

			// int res = INF;
			// for (int mask = 0; mask < 1 << m; mask++) {
			// boolean ok = true;
			// loop: for (int i = 0; i < n; i++) {
			// for (int j = i + 1; j < n; j++) {
			// if (!(((bits[i] ^ bits[j]) & mask) > 0)) {
			// ok = false;
			// break loop;
			// }
			// }
			// }
			// if (ok) {
			// res = min(res, Integer.bitCount(mask));
			// }
			// }
//			System.out.println(res);
		}
	}

	int n, m;
	int[] bits;
	int res;

	int dfs(Entry e) {
		if (e.remain.cardinality() <= 1) {
			return 0;
		} else if (map.containsKey(e)) {
			return map.get(e);
		}
		int res = 1 << 28;
		for (int i = 0; i < m; i++)
			if (e.que[i] == 2) {
				e.que[i] = 0;
				int d1 = dfs(new Entry(e.que));
				e.que[i] = 1;
				int d2 = dfs(new Entry(e.que));
				res = min(res, max(d1, d2) + 1);
				e.que[i] = 2;
			}
		map.put(e, res);
		return res;
	}

	HashMap<Entry, Integer> map;

	class Entry {
		int[] que;
		BitSet remain;

		Entry(int[] que) {
			this.que = que.clone();
			calcRemain();
		}

		void calcRemain() {
			remain = new BitSet();
			for (int i = 0; i < n; i++) {
				boolean ok = true;
				for (int j = 0; j < m; j++) {
					if (que[j] <= 1) {
						if (((bits[i] >> j) & 1) != que[j]) {
							ok = false;
							break;
						}
					}
				}
				if (ok) {
					remain.set(i);
				}
			}
		}

		@Override
		public int hashCode() {
			final int prime = 3;
			int result = 1;
			result = prime * result + getOuterType().hashCode();
			result = prime * result + Arrays.hashCode(que);
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Entry other = (Entry) obj;
			if (!getOuterType().equals(other.getOuterType()))
				return false;
			if (!Arrays.equals(que, other.que))
				return false;
			return true;
		}

		private H getOuterType() {
			return H.this;
		}

	}

	public static void main(String[] args) {
		new H().run();
	}
}

package com.github.hiroshi_cl.wakaba.v2010.sol.r2006;
import java.io.*;
import java.math.*;
import java.util.*;
import static java.lang.Math.*;
import static java.math.BigInteger.*;
import static java.util.Arrays.*;
import static java.util.Collections.*;
class G2 {
	void run() {
		int max = 300;
		vs = new ArrayList[max + 1];
		for (int i = 0; i <= max; i++) {
			vs[i] = new ArrayList<P>();
		}
		for (int k = 1; k <= max; k++) {
			for (int i = 0; i * i <= k * k; i++) {
				for (int j = i + 1; i * i + j * j <= k * k; j++) {
					if (i * i + j * j == k * k) {
						vs[k].add(new P(j, i));
					}
				}
			}
		}
		for (;;) {
			n = nextInt();
			if (n == 0) { return; }
			int[] input = new int[n];
			for (int i = 0; i < n; i++) {
				input[i] = nextInt();
			}
			int[] is = new int[n - 1];
			for (int i = 0; i < n - 1; i++) {
				is[i] = i + 1;
			}
			res = -1;
			fin = n / 2 ;
			num = new int[n];
			num[0] = input[0];
			map = new HashMap<P, ArrayList<P[]>>();
			ps[0] = new P(0, 0);
			do {
				map.clear();
				for (int i = 1; i < n; i++) {
					num[i] = input[is[i - 1]];
				}
				rs[n - 1] = new P(0, 0);
				dfs_bef(n - 1);
				for (P v : vs[num[0]]) {
					if (v.y == 0) {
						ps[1] = v;
						dfs(1);
					} else {
						ps[1] = v;
						dfs(1);
						ps[1] = new P(v.y, v.x);
						dfs(1);
					}
				}
			} while (nextPermutation(is));
			System.out.println(res);
		}
	}
	P[] rs = new P[6];
	P[] ps = new P[7];
	int res;
	ArrayList<P>[] vs;
	int[] num;
	HashMap<P, ArrayList<P[]>> map;
	void dfs_bef(int id) {
		if (id == fin) {
			if (!map.containsKey(rs[id])) {
				ArrayList<P[]> list = new ArrayList<P[]>();
				map.put(rs[id], list);
			}
			map.get(rs[id]).add(rs.clone());
			return;
		}
		for (P v : vs[num[id]]) {
			for (int d = 0; d < 4; d++, v = v.rot90()) {
				if (id == n - 1) {
					if (v.x < 0 || v.y > 0) {
						rs[id - 1] = v;
						dfs_bef(id - 1);
					}
				} else {
					P bef = rs[id].sub(rs[id + 1]);
					int det = bef.det(v);
					if (det < 0 || det == 0 && bef.dot(v) > 0) {
						rs[id - 1] = rs[id].add(v);
						dfs_bef(id - 1);
					}
				}
				P u = new P(v.y, v.x);
				if (id == n - 1) {
					if (u.x < 0 || u.y > 0) {
						rs[id - 1] = u;
						dfs_bef(id - 1);
					}
				} else {
					P bef = rs[id].sub(rs[id + 1]);
					int det = bef.det(u);
					if (det < 0 || det == 0 && bef.dot(u) > 0) {
						rs[id - 1] = rs[id].add(u);
						dfs_bef(id - 1);
					}
				}
			}
		}
	}
	int fin;
	int n;
	void dfs(int id) {
		if (id == fin + 1) {
			if (!map.containsKey(ps[fin + 1]))
				return;
			loop: for (P[] rs : map.get(ps[fin + 1])) {
				for (int i = id; i < n; i++) {
					ps[i + 1] = rs[i];
				}
				P bef1 = ps[id].sub(ps[id - 1]), v1 = ps[id + 1].sub(ps[id]);
				int det1 = bef1.det(v1);
				if (det1 < 0 || det1 == 0 && bef1.dot(v1) <= 0)
					return;
				P bef2 = ps[0].sub(ps[n - 1]), v2 = ps[1].sub(ps[0]);
				int det2 = bef2.det(v2);
				if (det2 < 0 || det2 == 0 && bef2.dot(v2) <= 0)
					return;
				int area = area(n);
				if (res < area)
					res = area;
			}
			return;
		}
		P bef = ps[id].sub(ps[id - 1]);
		for (P v : vs[num[id]]) {
			boolean ok = v.y > 0;
			for (int d = 0; d < 4; d++, v = v.rot90()) {
				int det = bef.det(v);
				if (det > 0 || det == 0 && bef.dot(v) > 0) {
					ps[id + 1] = ps[id].add(v);
					dfs(id + 1);
				}
				if (ok) {
					P u = new P(v.y, v.x);
					det = bef.det(u);
					if (det > 0 || det == 0 && bef.dot(u) > 0) {
						ps[id + 1] = ps[id].add(u);
						dfs(id + 1);
					}
				}
			}
		}
	}
	double PI2 = PI * 2;
	int area(int n) {// 符号付き面積。反時計回りのとき正、時計回りのとき負。
		int S = 0;
		for (int i = 0; i < n; i++) {
			int j = i + 1;
			if (j == n)
				j = 0;
			S += ps[i].det(ps[j]);
		}
		return S / 2;
	}
	boolean nextPermutation(int[] is) {
		int n = is.length;
		for (int i = n - 1; i > 0; i--) {
			if (is[i - 1] < is[i]) {
				int j = n;
				while (is[i - 1] >= is[--j]);
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
	class P {
		final int x, y;
		public P(int x, int y) {
			super();
			this.x = x;
			this.y = y;
		}
// double angle() {
// return atan2(y, x);
// }
		P add(P p) {
			return new P(x + p.x, y + p.y);
		}
		P sub(P p) {
			return new P(x - p.x, y - p.y);
		}
		int det(P p) {
			return x * p.y - y * p.x;
		}
		int dot(P p) {
			return x * p.x + y * p.y;
		}
		P rot90() {
			return new P(-y, x);
		}
		@Override
		public String toString() {
			return "(" + x + "," + y + ")";
		}
		public int hashCode() {
			return x << 16 + y;
		}
		public boolean equals(Object obj) {
			P p = (P) obj;
			return x == p.x && y == p.y;
		}
	}
	public static void main(String[] args) {
		new G2().run();
	}
	int nextInt() {
		try {
			int c = System.in.read();
			while (c != '-' && (c < '0' || '9' < c))
				c = System.in.read();
			if (c == '-')
				return -nextInt();
			int res = 0;
			do {
				res *= 10;
				res += c - '0';
				c = System.in.read();
			} while ('0' <= c && c <= '9');
			return res;
		} catch (Exception e) {
			return -1;
		}
	}
}
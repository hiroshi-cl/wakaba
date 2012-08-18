package com.github.hiroshi_cl.wakaba.v2010.sol.r2006;
import java.io.*;
import java.math.*;
import java.util.*;
import static java.lang.Math.*;
import static java.math.BigInteger.*;
import static java.util.Arrays.*;
import static java.util.Collections.*;
class G {
	void debug(Object... os) {
		System.err.println(deepToString(os));
	}
	void run() {
		Scanner sc = new Scanner(System.in);
		int max = 300;
		// int[] is = new int[max + 1];
		// P[] vs=new P[max+1];
		vs = new ArrayList[max + 1];
		// ArrayList[] a = vs;
		// a[0] = new ArrayList<P>();
		// a[0].add(1);
		// P p = vs[0].get(0);
		// debug(a[0].get(0));
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
		}long st = System.currentTimeMillis();
		for (;;) {
			n = sc.nextInt();
			if (n == 0) {debug(System.currentTimeMillis() - st);return;}
			int[] input = new int[n];
			for (int i = 0; i < n; i++) {
				input[i] = sc.nextInt();
			}
			int[] is = new int[n - 1];
			for (int i = 0; i < n - 1; i++) {
				is[i] = i + 1;
			}
			res = -1;
			do {
				num = new int[n];
				num[0] = input[0];
				for (int i = 1; i < n; i++) {
					num[i] = input[is[i - 1]];
				}
				count = 0;
				// set = new HashSet<P>();
				map = new HashMap<P, ArrayList<P[]>>();
				P[] rs = new P[n];
				rs[n - 1] = new P(0, 0);
//				long st = System.currentTimeMillis();
				dfs_bef(rs, num, n - 1, n / 2);
//				debug(count);
//				debug(System.currentTimeMillis() - st);
//				st = System.currentTimeMillis();
				P[] ps = new P[n + 1];
				ps[0] = new P(0, 0);
				for (P v : vs[num[0]]) {
					if (v.y == 0) {
						ps[1] = v;
						dfs(ps, 0, 1);
					} else {
						ps[1] = v;
						dfs(ps, 0, 1);
						ps[1] = new P(v.y, v.x);
						dfs(ps, 0, 1);
					}
				}
//				debug(System.currentTimeMillis() - st);
//				debug();
			} while (nextPermutation(is));
			System.out.println(res);
		}
		// for (int i = 1; i <= max; i++) {
		// if (is[i] > 0) debug(i, is[i]);
		// }
	}
	int res;
	ArrayList<P>[] vs;
	int[] num;
	double eps = 1e-2;
	HashMap<P, ArrayList<P[]>> map;
	int count;
	void dfs_bef(P[] rs, int[] num, int id, int fin) {
		int n = num.length;
		if (id == fin) {
			if (!map.containsKey(rs[id])) {
				ArrayList<P[]> list = new ArrayList<P[]>();
				map.put(rs[id], list);
			}
			map.get(rs[id]).add(rs.clone());
			count++;
			// debug(rs[id]);
			return;
		}
		for (P v : vs[num[id]]) {
			for (int d = 0; d < 4; d++, v = v.rot90()) {
				if (id == n - 1) {
					rs[id - 1] = v;
					dfs_bef(rs, num, id - 1, fin);
				} else {
					P bef = rs[id].sub(rs[id + 1]);
					int det = bef.det(v);
					if (det < 0 || det == 0 && bef.dot(v) > 0) {
						rs[id - 1] = rs[id].add(v);
						dfs_bef(rs, num, id - 1, fin);
					}
				}
				P u = new P(v.y, v.x);
				if (id == n - 1) {
					rs[id - 1] = u;
					dfs_bef(rs, num, id - 1, fin);
				} else {
					P bef = rs[id].sub(rs[id + 1]);
					int det = bef.det(u);
					if (det < 0 || det == 0 && bef.dot(u) > 0) {
						rs[id - 1] = rs[id].add(u);
						dfs_bef(rs, num, id - 1, fin);
					}
				}
			}
		}
	}
	int n;
	void dfs(P[] ps, double theta, int id) {
		if (id == n / 2 + 1) {
			if (!map.containsKey(ps[n / 2 + 1])) return;
			for (P[] rs : map.get(ps[n / 2 + 1])) {
				//				debug(ps);
				//				debug(rs);
				//				if (!rs[n / 2].equals(ps[n / 2 + 1])) {
				//					debug("!");
				//				}

				for (int i = id; i < n; i++) {
					ps[i+1] = rs[i];
				}
				double phi = theta;
				for (int i = id; i <= n; i++) {
					phi += asin((double) ps[i].sub(ps[i - 1]).det(ps[(i+1)%n].sub(ps[i])) / ( num[i - 1] * num[i%n] ));
				}
				if(phi > 2 * PI + eps)
					continue;

//				P bef = ps[id].sub(ps[id - 1]);

				P bef1 = ps[id].sub(ps[id - 1]) , v1 = ps[id+1].sub(ps[id]);
				P bef2 = ps[0].sub(ps[n - 1]),  v2 = ps[1].sub(ps[0]);
				int det1 = bef1.det(v1) ;
				int det2 =bef2.det(v2);
				if ((det1 > 0 || det1==0 && bef1.dot(v1)>0 ) &&  (det2>0 || det2==0 && bef2.dot(v2)>0) ) {
					res = max(res, area(ps));
					// debug(ps);
				}
			}

			return;
		}
		//		if (id == ps.length - 1) {
		//			if (ps[ps.length - 1].x == 0 && ps[ps.length - 1].y == 0) {
		//				if (ps[0].sub(ps[n - 1]).det(ps[1].sub(ps[0])) >= 0) {
		//					res = max(res, area(ps));
		//					// debug(ps);
		//				}
		//			}
		//			// debug(ps,res);
		//			return;
		//		}
		if (theta > 2 * PI + eps) return;
		P bef = ps[id].sub(ps[id - 1]);
		for (P v : vs[num[id]]) {
			boolean ok = v.y > 0;
			for (int d = 0; d < 4; d++, v = v.rot90()) {
				int det = bef.det(v);
				if (det > 0 || det == 0 && bef.dot(v) > 0) {
					ps[id + 1] = ps[id].add(v);
					dfs(ps, theta + asin((double) det / ( num[id - 1] * num[id] )), id + 1);
				}
				if (ok) {
					P u = new P(v.y, v.x);
					det = bef.det(u);
					if (det > 0 || det == 0 && bef.dot(u) > 0) {
						ps[id + 1] = ps[id].add(u);
						dfs(ps, theta + asin((double) det / ( num[id - 1] * num[id] )), id + 1);
					}
				}
			}
		}
	}
	int area(P[] ps) {// 符号付き面積。反時計回りのとき正、時計回りのとき負。
		int S = 0;
		for (int i = 0; i < ps.length; i++)
			S += ps[i].det(ps[( i + 1 ) % ps.length]);
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
		double norm() {
			return sqrt(x * x + y * y);
		}
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
		new G().run();
	}
}

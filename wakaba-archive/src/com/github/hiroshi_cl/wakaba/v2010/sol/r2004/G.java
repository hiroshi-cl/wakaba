package com.github.hiroshi_cl.wakaba.v2010.sol.r2004;
import java.io.*;
import java.math.*;
import java.util.*;
import static java.lang.Math.*;
import static java.math.BigInteger.*;
import static java.util.Arrays.*;
import static java.util.Collections.*;
class G {
	void debug(Object... os) {
//		System.err.println(deepToString(os));
	}
	void run() {
		Scanner sc = new Scanner(System.in);
		// try {
		// sc = new Scanner(new File("A"));
		// System.setOut(new PrintStream("A.out"));
		// } catch (Exception e) {}
		for (;;) {
			int n = sc.nextInt();
			if (n == 0) return;
			HashMap<String, Integer> map = new HashMap<String, Integer>();
			int m = 0;
			V[] vs = new V[n];
			for (int i = 0; i < n; i++) {
				String s = sc.next();
				if (!map.containsKey(s)) {
					vs[m] = new V(m);
					map.put(s, m++);
				}
				vs[map.get(s)].input(sc);
			}
			for (int i = 0; i < m; i++) {
				for (int j = i + 1; j < m; j++) {
					if (vs[i].share(vs[j])) {
						debug("!");
						debug(i,j);
//						debug(ps, qs);
						vs[i].es.add(vs[j]);
						vs[j].es.add(vs[i]);
					}
				}
			}
			for (int i = 0; i < m; i++) {
				debug(vs[i]);
			}
			int res = 0;
//			for (int i = 0; i < m; i++) {
//				if (!vs[i].visited) {
//					vs[i].visited = true;
//					res = max(res, dfs(vs[i], 0, 1));
//				}
//			}
			res=dfs2(vs, m, 0, 0);
			System.out.println(res);
		}
	}
	int INF = 1 << 27;

	int dfs2(V[] vs, int m, int c, int max) {
		if(c == m)
			return max;
		boolean[] impossible = new boolean[max];
		for(V v : vs[c].es)
			if(v.color >= 0)
				impossible[v.color] = true;
		int res = m;
		for(int i = 0; i < max; i++)
			if(!impossible[i]) {
				vs[c].color = i;
				res = min(res, dfs2(vs, m, c + 1, max));
				vs[c].color = -1;
			}
		vs[c].color = max;
		res = min(res, dfs2(vs, m, c + 1, max + 1));
		vs[c].color = -1;
		return res;
	}

//	int dfs(V v, int color, int max) {
//		if(max>10)return INF;
//		v.visited = true;
//		for (V nv : v.es)
//			if (nv.color == color) return INF;
//		boolean flag = true;
//		for (V nv : v.es)
//			if (nv.color == -1) {
//				flag = false;
//			}
//		if (flag) return max;
//		v.color = color;
//		int res = INF;
//		for (V nv : v.es) {
//			if (nv.color == -1) {
//				for (int i = 0; i < max; i++) {
//					res = min(res, dfs(nv, i, max));
//				}
//				res = min(res, dfs(nv, max, max + 1));
//			}
//		}
//		v.color = -1;
//		return res;
//	}
	class V {
		int color = -1;
		boolean visited = false;
		ArrayList<P[]> list = new ArrayList<P[]>();
		ArrayList<V> es = new ArrayList<V>();
		int id;
		public String toString() {
			String s = "";
			for (V v : es) {
				s += " " + v.id;
			}
			return s;
		}
		V(int id) {
			this.id = id;
		}
		void input(Scanner sc) {
			ArrayList<P> ps = new ArrayList<P>();
			for (;;) {
				int x = sc.nextInt();
				if (x == -1) break;
				int y = sc.nextInt();
				ps.add(new P(x, y));
			}
			list.add(ps.toArray(new P[0]));
		}
		boolean share(V v) {
			for (int i = 0; i < list.size(); i++) {
				for (int j = 0; j < v.list.size(); j++) {
					P[] ps = list.get(i);
					P[] qs = v.list.get(j);
					if (share2(ps, qs)) return true;
				}
			}
			return false;
		}
	}
	boolean share2(P[] ps, P[] qs) {
		for (int i = 0; i < ps.length; i++) {
			for (int j = 0; j < qs.length; j++) {
				if (nonzero_ss(ps[i], ps[(i + 1) % ps.length], qs[j], qs[(j + 1) % qs.length]))
					return true;
			}
		}
		return false;
	}
	boolean nonzero_ss(P p1, P p2, P q1, P q2) {// 線分と線分の共通部分が線分である
		int d=0;
		if(intersect_sp(p1, p2, q1))d++;
		if(intersect_sp(p1, p2, q2))d++;
		if(intersect_sp(q1, q2, p1))d++;
		if(intersect_sp(q1, q2, p2))d++;
		if(d==2){
			if(p1.equals(q1))return false;
			if(p1.equals(q2))return false;
			if(p2.equals(q1))return false;
			if(p2.equals(q2))return false;
			return true;
		}
		else return d>=3;
	}
	static boolean intersect_sp(P p1, P p2, P p) {// pが線分p1p2上にあるか。
		P pp1 = p1.sub(p), pp2 = p2.sub(p);
		return pp1.det(pp2) == 0 && pp1.dot(pp2) <= 0;
	}
	class P {
		public String toString() {
			return x + " " + y;
		}
		int x, y;
		P(int x, int y) {
			this.x = x;
			this.y = y;
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
		public boolean equals(Object obj) {
			P p=(P)obj;
			return x==p.x && y==p.y;
		}
	}
	public static void main(String[] args) {
		new G().run();
	}
}

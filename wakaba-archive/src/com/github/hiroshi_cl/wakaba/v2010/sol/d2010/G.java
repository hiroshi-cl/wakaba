package com.github.hiroshi_cl.wakaba.v2010.sol.d2010;
import java.util.*;
import java.math.*;
import java.io.*;
import static java.util.Arrays.*;
import static java.util.Collections.*;
import static java.lang.Math.*;

public class G {
	public static void main(String[] args) {
		new G().run();
	}

	void debug(Object... os) {
		System.err.println(deepToString(os));
	}

	void run() {
		Scanner sc = new Scanner(System.in);
		try {
			String s = getClass().getSimpleName();
			sc = new Scanner(new File(s));
			System.setOut(new PrintStream(new File(s + ".out")));
		} catch (Exception e) {
		}
		for (;;) {
			n = sc.nextInt();
			if (n == 0)
				return;
			L[] mir = new L[n];
			for (int i = 0; i < n; i++) {
				mir[i] = new L(new P(sc.nextDouble(), sc.nextDouble()), new P(
						sc.nextDouble(), sc.nextDouble()), 2);
			}
			P s = new P(sc.nextDouble(), sc.nextDouble());
			P g = new P(sc.nextDouble(), sc.nextDouble());
			res = INF;
			dir = new ArrayList<P>();
			dfs(6, mir, s, g);
			for (P d : dir) {
				if (calc(mir, s, g, d, 6)) {
					res = min(res, s.dist(g));
				}
			}

			System.out.println(res);
		}
	}

	boolean calc(L[] ls, P s, P g, P dir, int depth) {
		if (depth == 0)
			return false;
		L mir = butukaru(ls, s, dir);
		P b = butukaruTen(ls, s, g,dir);
		if(b==null)return false;
		if(b.equals(g))return true;
		P ns = ref(mir, s);
		P ndir = b.sub(ns);
//		if(g.contained(r1, r2));
		return calc(ls,b,g,b.sub(ns),depth-1);
	}

	L butukaru(L[] ls, P s, P dir) {
		L r = new L(s, s.add(dir), 1);
		L res = null;
		P resp = null;
		for (L l : ls) {
			// l.kind == 1;
			P p = l.is(r);
			if (p != null) {
				if (resp == null || s.dist(p) < s.dist(resp))
					res = l;
			}
		}
		return res;
	}
	P butukaruTen(L[] ls, P s,P g, P dir) {
		L r = new L(s, s.add(dir), 1);
		L res = null;
		P resp = null;
		for (L l : ls) {
			// l.kind == 1;
			P p = l.is(r);
			if (p != null) {
				if (resp == null || s.dist(p) < s.dist(resp))
					res = l;
			}
		}
		P s2 = s.add(dir);
		if(g.contained(s, s2)==1 || s2.contained(s, g)==1){
			if(resp==null || s.dist(g) < s.dist(resp)){
				resp = g;
			}
		}
//		if(eq())

		return resp;
	}

	int n;
	double res;
	ArrayList<P> dir;

	// int MAX = 6;

	void dfs(int depth, L[] mir, P s, P g) {
		dir.add(g.sub(s));
		if (depth == 0)
			return;
//		res = min(res, calc(mir, s, g));
		for (int i = 0; i < n; i++) {
			dfs(depth-1, mir, s, ref(mir[i], g));
		}
	}

	P ref(L l, P p) {
		P f = l.foot(p);
		return f.add(f.sub(p));
	}

	int INF = 1 << 28;
	// int calc(L[] mir,P s,P g){
	//
	// }

	static final double EPS = 1e-9;

	int sgn(double d) {
		return d < -EPS ? -1 : d > EPS ? 1 : 0;
	}

	boolean eq(double a, double b) {
		return sgn(a - b) == 0;
	}

	class P implements Comparable<P> {
		double x, y;

		P(double x, double y) {
			this.x = x;
			this.y = y;
		}

		P add(P o) {
			return new P(x + o.x, y + o.y);
		}

		P sub(P o) {
			return new P(x - o.x, y - o.y);
		}

		P mul(double d) {
			return new P(x * d, y * d);
		}

		P div(double d) {
			return new P(x / d, y / d);
		}

		double dot(P o) {
			return x * o.x + y * o.y;
		}

		double det(P o) {
			return x * o.y - y * o.x;
		}

		double norm2() {
			return x * x + y * y;
		}

		double norm() {
			return sqrt(norm2());
		}

		double dist(P p) {
			return sub(p).norm();
		}

		public boolean equals(Object obj) {
			return compareTo((P) obj) == 0;
		};

		public int compareTo(P o) {
			return sgn(x - o.x) != 0 ? sgn(x - o.x) : sgn(y - o.y);
		}

		P unit(double d) {
			return div(d / norm());
		}

		int contained(P r1, P r2) {
			if (equals(r1) || equals(r2))
				return 0;
			if (eq(r1.dist(r2), dist(r1) + dist(r2)))
				return 1;
			return -1;
		}
	}

	class L {
		final P p1, p2;
		final int kind;

		public L(P p1, P p2, int kind) {
			super();
			this.p1 = p1;
			this.p2 = p2;
			this.kind = kind;
		}

		int contains(P p) {
			return p.contained(p1, p2);
		}

		P isLL(L l) {
			P a = p2.sub(p1), b = l.p1.sub(p1), c = l.p2.sub(l.p1);
			double d = c.det(a);
			double e = c.det(b);
			if (eq(d, 0))
				return null;
			return p1.add(a.mul(e / d));
		}

		int calc(P p) {
			if (kind == 0)
				return 1;
			if (kind == 1)
				return -p1.contained(p2, p);
			return p.contained(p1, p2);
		}

		P is(L l) {
			P res = isLL(l);
			if (res == null) {
				return null;
			}
			int a = calc(res);
			int b = l.calc(res);
			if (a + b == 2)
				return res;
			return null;
		}

		P foot(P p) {
			P pp1 = p1.sub(p), p1p2 = p2.sub(p1);
			double t = -pp1.dot(p1p2) / p1p2.norm();
			return p1.add(p1p2.mul(t));
		}
	}
}

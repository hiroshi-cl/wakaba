package com.github.hiroshi_cl.wakaba.v2010.sol.d2009;

// 未完成
import java.io.*;
import java.math.*;
import java.util.*;
import static java.lang.Math.*;
import static java.math.BigInteger.*;
import static java.util.Arrays.*;
import static java.util.Collections.*;

class F {
	void debug(Object... o) {
		System.err.println(deepToString(o));
	}

	void run() {
		Scanner sc = new Scanner(System.in);
		try {
			sc = new Scanner(new File("F"));
			System.setOut(new PrintStream("F.out"));
		} catch (Exception e) {
		}
		loop: for (;;) {
			int m = sc.nextInt(), n = sc.nextInt();
			if (m + n == 0)
				return;
			st = new P[m];
			for (int i = 0; i < m; i++) {
				st[i] = new P(sc.nextDouble(), sc.nextDouble());
			}
			poly = new P[n];
			for (int i = 0; i < n; i++)
				poly[i] = new P(sc.nextDouble(), sc.nextDouble());
			boolean[] left = new boolean[n];
			boolean[] right = new boolean[n];
			for (int i = 0; i < n; i++) {
				for (int j = 0; j < m - 1; j++) {
					// P p1 = st[i],p2 = st[(i+1)]
					if (right(st[j], st[j + 1], poly[i])
							&& perpendicularFoot_s(st[j], st[j + 1], poly[j]) != null)
						right[i] = true;
					if (left(st[j], st[j + 1], poly[i])
							&& perpendicularFoot_s(st[j], st[j + 1], poly[j]) != null)
						left[i] = true;
				}
			}
			debug(right);
			debug(left);
			LinkedList<P> list = new LinkedList<P>();
			for (int i = 0; i < m; i++) {
				list.add(st[i]);
			}
			if (list.size() == 2) {
				System.out.println(list.get(0).dist(list.get(1)));
				continue loop;
			}
			debug(stringlen(list));
			double res = Double.MAX_VALUE;
			for (;;) {
				LinkedList<P> decided = new LinkedList<P>();

				for (;;) {
					decided.add(list.get(0));
					if (list.size() <= 2)
						break;
					P p1 = list.remove(0), p2 = list.remove(0), p3 = list
							.removeFirst();
					// boolean[] isRight = new boolean[n];
					// boolean[] isLeft = new boolean[n];
					ArrayList<P> ps = new ArrayList<P>();
					for (int j = 0; j < n; j++) {
						if (perpendicularFoot_s(p1, p3, poly[j]) != null) {
							if (!right(p1, p3, poly[j]) && right(p1, p3, p2)) {
								ps.add(poly[j]);
							} else if (!left(p1, p3, poly[j])
									&& left(p1, p3, p2)) {
								ps.add(poly[j]);
							}
						}
					}
					if (ps.size() == 0) {
						list.addFirst(p3);
						list.addFirst(p1);
						continue;
					}
					ps.add(p1);
					ps.add(p3);
					P[] convex = convexHull(ps.toArray(new P[0]));
					int sid = 0;
					for (sid = 0; sid < n; sid++) {
						if (convex[sid].equals(p1)) {
							break;
						}
					}
					int len = convex.length;
					if (convex[(sid + 1) % convex.length].equals(p3)) {
						for (int i = 1; i < convex.length - 1; i++) {
							decided.add(convex[(sid - i + len) % len]);
							// list.addFirst(sid+1+i)
						}
						list.addFirst(convex[(sid - 2 + len) % len]);
						list.addFirst(p3);
					} else {
						for (int i = 1; i < len - 1; i++) {
							decided.add(convex[(sid + i + len) % len]);
						}
						list.addFirst(convex[(sid + 2) % len]);
						list.addFirst(p3);
					}
					// list.addFirst(p3);
					// boolean left = left(p1,p3,p2);
				}
				decided.addLast(list.removeFirst());
				decided.addLast(list.removeFirst());
				double len = stringlen(decided);
				if (eq(res, len))
					break;
				res = len;
				debug(res);
				list = decided;
			}
			System.out.println(res);
		}
	}

	double stringlen(LinkedList<P> str) {
		int k = str.size();
		double res = 0;
		for (int i = 0; i < k - 1; i++) {
			res += str.get(i).dist(str.get(i + 1));
		}
		return res;
	}

	boolean intersect_sp(P p1, P p2, P p) {
		P pp1 = p1.sub(p), pp2 = p2.sub(p);
		return eq(pp1.det(pp2), 0) && le(pp1.dot(pp2), 0);
	}

	P[] convexHull(P[] ps) {
		sort(ps);
		int n = ps.length;
		P[] ds = new P[n];
		P[] us = new P[n];
		int dn = 0;
		int un = 0;
		for (int i = 0; i < n; ds[dn++] = ps[i++])
			while (dn >= 2
					&& le(ds[dn - 1].sub(ds[dn - 2]).det(ps[i].sub(ds[dn - 2])),
							0))
				dn--;
		for (int i = n - 1; i >= 0; us[un++] = ps[i--])
			while (un >= 2
					&& le(us[un - 1].sub(us[un - 2]).det(ps[i].sub(us[un - 2])),
							0))
				un--;
		P[] res = new P[dn + un - 2];
		System.arraycopy(ds, 0, res, 0, dn - 1);
		System.arraycopy(us, 0, res, dn - 1, un - 1);
		return res;
	}

	boolean right(P p1, P p2, P p) {
		return p2.sub(p1).det(p.sub(p2)) >= 0;
	}

	boolean left(P p1, P p2, P p) {
		return p2.sub(p1).det(p.sub(p2)) <= 0;
	}

	P[] st;
	P[] poly;

	int signum(double d) {
		return d < -EPS ? -1 : d > EPS ? 1 : 0;
	}

	class P implements Comparable<P> {
		@Override
		// P
		public boolean equals(Object obj) {
			P p = (P) obj;
			return p.x == x && p.y == y;
		};

		public int compareTo(P o) {
			// TODO 自動生成されたメソッド・スタブ
			return x == o.x ? signum(y - o.y) : signum(x - o.x);
		}

		double dist(P p) {
			return sub(p).norm();
		}

		double norm() {
			return sqrt(x * x + y * y);
		}

		double x, y;

		P(double x, double y) {
			this.x = x;
			this.y = y;
		}

		P add(P p) {
			return new P(x + p.x, y + p.y);
		}

		P sub(P p) {
			return new P(x - p.x, y - p.y);
		}

		P mul(double t) {
			return new P(x * t, y * t);
		}

		double dot(P p) {
			return x * p.x + y * p.y;
		}

		double det(P p) {
			return x * p.y - y * p.x;
		}

		double norm2() {
			return x * x + y * y;
		}
	}

	double dist_sp(P p1, P p2, P p) {
		P foot = perpendicularFoot_s(p1, p2, p);
		return foot == null ? min(p1.dist(p), p2.dist(p)) : foot.dist(p);
	}

	P perpendicularFoot_s(P p1, P p2, P p) {
		P pp1 = p1.sub(p), p1p2 = p2.sub(p1);
		double t = -pp1.dot(p1p2) / p1p2.norm2();
		return le(0, t) && le(t, 1) ? p1.add(p1p2.mul(t)) : null;
	}

	double EPS = 1e-7;

	boolean le(double a, double b) {
		return a < b - EPS;
	}

	boolean eq(double a, double b) {
		return abs(a - b) <= EPS;
	}

	public static void main(String[] args) {
		new F().run();
	}
}

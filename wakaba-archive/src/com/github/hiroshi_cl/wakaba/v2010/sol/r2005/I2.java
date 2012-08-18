package com.github.hiroshi_cl.wakaba.v2010.sol.r2005;
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import java.math.*;
import java.util.*;
import static java.lang.Math.*;
import static java.math.BigInteger.*;
import static java.util.Arrays.*;
import static java.util.Collections.*;
class Main {
//	void debug(Object... os) {
//		System.err.println(deepToString(os));
//	}
	int MAX=1500;
	final double EPS = 1e-7;// problem specific.
	void run() {
		Scanner sc = new Scanner();
//		try {
//			System.setOut(new PrintStream("2747.txt"));
//		} catch (FileNotFoundException e1) {
//			// TODO 自動生成された catch ブロック
//			e1.printStackTrace();
//		}
		for (int o = 1;; o++) {
			l = sc.nextDouble();
			if (l == 0) return;
			n = sc.nextInt();
			P[] rs = new P[n];
			double minrx = MAX, maxrx = -MAX;
			for (int i = 0; i < n; i++) {
				rs[i] = new P(sc.nextDouble(), sc.nextDouble());
				minrx = min(minrx, rs[i].x);
				maxrx = max(maxrx, rs[i].x);
			}
			P[] trs = new P[n + 1];
			for (int i = 0; i < n + 1; i++) {
				trs[i] = rs[i % n];
			}
			m = sc.nextInt();
			double minpx = MAX, maxpx = -MAX;
			P[] ps = new P[m];
			for (int i = 0; i < m; i++) {
				ps[i] = new P(sc.nextDouble(), sc.nextDouble());
				minpx = min(minpx, ps[i].x);
				maxpx = max(maxpx, ps[i].x);
			}
			P[] tps = new P[m + 1];
			for (int i = 0; i < m + 1; i++) {
				tps[i] = ps[i % m];
			}
//			if (o >= 263) {
//				MinimalVis.debugLines(trs, tps);
//			}
			ArrayList<Entry> list = new ArrayList<Entry>();
			for (int i = 0; i < m; i++) {
				for (int j = 0; j < n; j++) {
					double low = -MAX, high = MAX;
					double d1 = 0;
					while (high - low > EPS) {
						double m1 = ( low * 2 + high ) / 3;
						double m2 = ( low + high * 2 ) / 3;
						P s1 = new P(m1, 0);
						P s2 = new P(m2, 0);
						d1 = dist_ss(rs[j], rs[( j + 1 ) % n], ps[i].add(s1), ps[( i + 1 ) % m]
						.add(s1));
						double d2 = dist_ss(rs[j], rs[( j + 1 ) % n], ps[i].add(s2), ps[( i + 1 )
						% m].add(s2));
						if (d1 < d2) {
							high = m2;
						} else low = m1;
					}
					if (d1 > l) continue;
					double pole = low;
					low = -MAX;
					high = pole;
					while (high - low > EPS) {
						double mid = ( low + high ) / 2;
						P s = new P(mid, 0);
						if (dist_ss(rs[j], rs[( j + 1 ) % n], ps[i].add(s), ps[( i + 1 ) % m]
						.add(s)) < l) {
							high = mid;
						} else {
							low = mid;
						}
					}
					double s = low;
					low = pole;
					high = MAX;
					while (high - low > EPS) {
						double mid = ( low + high ) / 2;
						P s2 = new P(mid, 0);
						if (dist_ss(rs[j], rs[( j + 1 ) % n], ps[i].add(s2), ps[( i + 1 ) % m]
						.add(s2)) < l) {
							low = mid;
						} else {
							high = mid;
						}
					}
					double t = low;
					list.add(new Entry(s, t));
				}
			}
			if (list.size() == 0) {
				System.out.println(max(maxpx - minpx, maxrx - minrx));
				continue;
			}
			sort(list);
			ArrayList<Entry> es = new ArrayList<Entry>();
			double s = list.get(0).s, t = list.get(0).t;
			for (int j = 1; j < list.size(); j++) {
				if (list.get(j).s < t) {
					t = max(t, list.get(j).t);
					if (j == list.size() - 1) {
						es.add(new Entry(s, t));
					}
				} else {
					es.add(new Entry(s, t));
					s = list.get(j).s;
					t = list.get(j).t;
				}
			}
			// debug(es);
			double res = MAX;
			// debug(maxpx, minpx);
			int k = es.size() + 1;
			Entry[] fs = new Entry[k];
			for (int j = 0; j < k; j++) {
				if (j == 0) {
					fs[j] = new Entry(es.get(j).s - 2 * EPS, es.get(j).s);
				} else if (j == k - 1) {
					fs[j] = new Entry(es.get(j - 1).t, es.get(j - 1).t + 2 * EPS);
				} else {
					fs[j] = new Entry(es.get(j - 1).t, es.get(j).s);
				}
			}
			for (Entry e : fs) {
				P p = new P(e.s, 0);
				if (contains(rs, ps[0].add(p)) != 1 && contains(ps, rs[0].sub(p)) != 1) {
					double low = e.s, high = e.t;
					double d1 = 0;
					do {
						double m1 = ( low * 2 + high ) / 3;
						double m2 = ( low + high * 2 ) / 3;
						d1 = max(maxrx, m1 + maxpx) - min(minrx, m1 + minpx);
						double d2 = max(maxrx, m2 + maxpx) - min(minrx, m2 + minpx);
						if (d1 < d2) {
							high = m2;
						} else low = m1;
					}while (high - low > EPS);
					res = min(res, d1);
//					if (o >= 263) {
//						for (int j = 0; j < m + 1; j++) {
//							tps[j] = ps[j % m].add(new P(low, 0));
//						}
				}
			}
			System.out.printf("%.5f%n", res);
		}
	}
	double dist_ss(P p1, P p2, P q1, P q2) {// 線分と線分の距離
		if (intersect_ss(p1, p2, q1, q2)) return 0;
		double res = Integer.MAX_VALUE;
		res = min(res, dist_sp(p1, p2, q1));
		res = min(res, dist_sp(p1, p2, q2));
		res = min(res, dist_sp(q1, q2, p1));
		res = min(res, dist_sp(q1, q2, p2));
		return res;
	}
	boolean intersect_ss(P p1, P p2, P q1, P q2) {// 線分と線分が交わるか。
		if (intersect_sp(p1, p2, q1) || intersect_sp(p1, p2, q2) || intersect_sp(q1, q2, p1)
		|| intersect_sp(q1, q2, p2)) return true; // 端点を共有
		P p1p2 = p2.sub(p1), q1q2 = q2.sub(q1);
		return lt(p1p2.det(q1.sub(p1)) * p1p2.det(q2.sub(p1)), 0) // 真に交わる
		&& lt(q1q2.det(p1.sub(q1)) * q1q2.det(p2.sub(q1)), 0);
	}
	boolean intersect_sp(P p1, P p2, P p) {// pが線分p1p2上にあるか。
		P pp1 = p1.sub(p), pp2 = p2.sub(p);
		return eq(pp1.det(pp2), 0) && le(pp1.dot(pp2), 0);
	}
	int contains(P[] ps, P p) {// 点、多角形包含判定 OUT,ON,IN = -1, 0, 1.
		int n = ps.length;
		int res = -1;
		for (int i = 0; i < n; i++) {
			P a = ps[i].sub(p), b = ps[( i + 1 ) % n].sub(p);
			if (a.y > b.y) {
				P t = a;
				a = b;
				b = t;
			}
			if (a.y <= 0 && 0 < b.y && a.det(b) < 0) res *= -1;
			if (a.det(b) == 0 && a.dot(b) <= 0) return 0;
		}
		return res;
	}
	int INF = 1 << 28;
	class Entry implements Comparable<Entry> {
		double s, t;
		@Override
		public String toString() {
			// TODO 自動生成されたメソッド・スタブ
			return s + " " + t;
		}
		public Entry(double s, double t) {
			super();
			this.s = s;
			this.t = t;
		}
		public int compareTo(Entry o) {
			// TODO 自動生成されたメソッド・スタブ
			return s - o.s != 0 ? (int) signum(s - o.s) : (int) signum(t - o.t);
		}
	}
	double l;
	int n;
	int m;
	P perpendicularFoot_s(P p1, P p2, P p) {// 線分への垂線の足。存在しないときはnullを返す。
		P pp1 = p1.sub(p), p1p2 = p2.sub(p1);
		double t = -pp1.dot(p1p2) / p1p2.norm2();
		return le(0, t) && le(t, 1) ? p1.add(p1p2.mul(t)) : null;
	}
	double dist_sp(P p1, P p2, P p) {// 線分と点の距離
		P foot = perpendicularFoot_s(p1, p2, p);
		return foot == null ? min(p1.dist(p), p2.dist(p)) : foot.dist(p);
	}
	public class P implements Comparable<P> {
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
		P mul(double d) {
			return new P(x * d, y * d);
		}
		P div(double d) {
			return new P(x / d, y / d);
		}
		double dot(P p) {
			return x * p.x + y * p.y;
		}
		double det(P p) {
			return x * p.y - y * p.x;
		}
		double norm() {
			return sqrt(x * x + y * y);
		}
		double norm2() {
			return x * x + y * y;
		}
		double dist(P p) {
			return sub(p).norm();
		}
		P rot90() {
			return new P(-y, x);
		}
		P rot(double t) {// verified
			return new P(x * cos(t) - y * sin(t), x * sin(t) + y * cos(t));
		}
		double angle() {// verified.
			return atan2(y, x);// [-PI,PI].
		}
		P unit() {// 単位ベクトル
			double d = norm();
			return d < EPS ? null : new P(x / d, y / d);
		}
		int signum(double d) {
			return d < -EPS ? -1 : d > EPS ? 1 : 0;
		}
		public int compareTo(P o) {
			return signum(x - o.x) != 0 ? signum(x - o.x) : signum(y - o.y);
		}
		public boolean equals(Object o) {
			return compareTo((P) o) == 0;
		}
		public int hashCode() {
			return new Double(x).hashCode() * 0x0000f000 + new Double(y).hashCode();
		}
		public String toString() {
			return x + " " + y;
		}
	}
	boolean eq(double a, double b) {
		return signum(a - b) == 0;
	}
	boolean le(double a, double b) {
		return signum(a - b) <= 0;
	}
	boolean lt(double a, double b) {
		return signum(a - b) < 0;
	}
	int signum(double d) {
		return d < -EPS ? -1 : d > EPS ? 1 : 0;
	}
	public static void main(String[] args) {
		new Main().run();
	}
	@SuppressWarnings("serial")
	static class MinimalVis {
		private static final Frame fr = new Frame() {
			@Override
			public void paint(Graphics g) {
				super.paint(g);
				if (vs == null) return;
				for (int k = 0; k < vs.length; k++) {
					int[] x = new int[vs[k].length];
					int[] y = new int[vs[k].length];
					for (int i = 0; i < vs[k].length; i++) {
						x[i] = sx + (int) round(pw * vs[k][i].x);
						y[i] = sy - (int) round(pw * vs[k][i].y);
					}
					g.drawPolyline(x, y, vs[k].length);
				}
			}
		};
		private static P[][] vs = null;
		private static int sx = 100;
		private static int sy = 200;
		private static double pw = 1.;
		private static boolean lock = false;
		static {
			fr.setSize(sx * 4, sy * 2);
			fr.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					lock = false;
				}
			});
			fr.addWindowListener(new WindowAdapter() {
				@Override
				public void windowClosing(WindowEvent e) {
					System.exit(0);
				}
			});
			fr.setVisible(true);
		}
		/**
		 * 折れ線を画面に描画する. 中断せず上書き.
		 *
		 * @param v
		 *            折れ線を構成する点
		 */
		public static void debugLine(P... v) {
			vs = new P[][] { v };
			fr.repaint();
			lock = true;
			try {
				while (lock)
					Thread.sleep(100);
			} catch (InterruptedException e) {}
		}
		public static void debugLines(P[]... v) {
			vs = v;
			fr.repaint();
			lock = true;
			try {
				while (lock)
					Thread.sleep(100);
			} catch (InterruptedException e) {}
		}
	}
	class Scanner {
	    int nextInt() {
	        try {
	            int c = System.in.read();
	            while (c != '-' && (c < '0' || '9' < c))
	                c = System.in.read();
	            if (c == '-') return -nextInt();
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
	    long nextLong() {
	        try {
	            int c = System.in.read();
	            while (c != '-' && (c < '0' || '9' < c))
	                c = System.in.read();
	            if (c == '-') return -nextLong();
	            long res = 0;
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
	    double nextDouble() {
	        return Double.parseDouble(next());
	    }
	    String next() {
	        try {
	            StringBuilder res = new StringBuilder("");
	            int c = System.in.read();
	            while (Character.isWhitespace(c))
	                c = System.in.read();
	            do {
	                res.append((char) c);
	            } while (!Character.isWhitespace(c = System.in.read()));
	            return res.toString();
	        } catch (Exception e) {
	            return null;
	        }
	    }
	    String nextLine(){
	        try{
	            StringBuilder res =new StringBuilder("");
	            int c = System.in.read();
	            while (c=='\r' || c=='\n')
	                c = System.in.read();
	            do {
	                res.append((char) c);
	                c = System.in.read();
	            } while (c!='\r' && c!='\n');
	            return res.toString();
	        }catch (Exception e) {
	            return null;
	        }
	    }
	}
}

package com.github.hiroshi_cl.wakaba.v2010.sol.gcj2009.r2;

import java.io.*;
import java.math.*;
import java.util.*;
import static java.lang.Math.*;
import static java.math.BigInteger.*;
import static java.util.Arrays.*;
import static java.util.Collections.*;

public class D_ogie {
	static final boolean _PRACTICE = true;
	static final boolean _SAMPLE = !true;
	static final boolean _SMALL = !true;
	static final String _PROBLEM = "D";

	void debug(Object... os) {
		// System.err.println(deepToString(os));
	}

	void run() {
		Scanner sc = new Scanner(System.in);
		int oo = sc.nextInt();
		for (int o = 1; o <= oo; o++) {
			System.err.println(o);
			System.out.printf("Case #%d: ", o);
			n = sc.nextInt();
			ps = new P[n];
			rs = new double[n];
			for (int i = 0; i < n; i++) {
				ps[i] = new P(sc.nextDouble(), sc.nextDouble());
				rs[i] = sc.nextDouble();
			}
			if (n == 1) {
				System.out.println(rs[0]);
				continue;
			}
			if (n == 2) {
				System.out.println(max(rs[0], rs[1]));
				continue;
			}
			double l = 0, h = 5000;
			for (int i = 0; i < n; i++) {
				l = max(l, rs[i]);
			}
			for (;;) {
				if (h - l < eps)
					break;
				double m = (l + h) / 2;
				boolean can = false;
				loop: for (int i = 0; i < n; i++) {
					P o1 = ps[i];
					for (int j = 0; j < n; j++) {
						if (i == j)
							continue;
						for (int k = j + 1; k < n; k++) {
							if (i == k)
								continue;
							P[] qs = intersection_cc(ps[j], m - rs[j], ps[k], m
									- rs[k]);
							if (qs != null)
								for (P q : qs) {
									if (ok(o1, q, m)) {
										can = true;
										break loop;
									}
								}
						}
					}
				}
				if (!can) {
					loop: for (int i = 0; i < n; i++) {
						for (int j = i + 1; j < n; j++) {
							P[] q1s = intersection_cc(ps[i], m - rs[i], ps[j],
									m - rs[j]);
							if (q1s == null)
								continue;
							for (int i2 = i + 1; i2 < n; i2++) {
								for (int j2 = i2 + 1; j2 < n; j2++) {
									P[] q2s = intersection_cc(ps[i2], m
											- rs[i2], ps[j2], m - rs[j2]);
									if (q2s == null)
										continue;
									for (P q1 : q1s) {
										for (P q2 : q2s) {
											if (ok(q1, q2, m)) {
												can = true;
												break loop;
											}
										}
									}
								}
							}
						}
					}
				}
				if (can) {
					h = m;
				} else {
					l = m;
				}
			}
			System.out.println(h);
		}
	}

	int n;
	double eps = 1e-6;

	boolean ok(P o1, P o2, double R) {
		debug(R, o1, o2);
		for (int i = 0; i < n; i++) {
			if (rs[i] + ps[i].dist(o1) > R + eps
					&& rs[i] + ps[i].dist(o2) > R + eps) {
				return false;
			}
		}
		return true;
	}

	P[] intersection_cc(P c1, double r1, P c2, double r2) {// 円と円の交点
		P c1c2 = c2.sub(c1);
		double r = c1c2.norm();
		double x = (r * r + r1 * r1 - r2 * r2) / (2 * r);
		double t = r1 * r1 - x * x;
		if (t < 0)
			return null;
		P q1 = c1.add(c1c2.mul(x / r)), q2 = c1c2.rot90().mul(sqrt(t) / r);
		return new P[] { q1.add(q2), q1.sub(q2) };
	}

	P[] ps;
	double[] rs;

	public class P implements Comparable<P> {
		double x, y;
		static private final double EPS = 1e-9;

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
			return new Double(x).hashCode() * 0x0000f000
					+ new Double(y).hashCode();
		}

		public String toString() {
			return x + " " + y;
		}
	}

	boolean in(long from, long n, long to) {
		return from <= n && n < to;
	}

	public static void main(String... args) throws IOException {
		if (!_SAMPLE) {
			if (_SMALL) {
				int i = 0;
				while (new File(_SMALLNAME(i) + ".in").exists())
					i++;
				i--;
				boolean test = false;
				if (new File(_SMALLNAME(i) + ".out").exists()) {
					System.err.println("overwrite?(y/n)");
					char c = (char) System.in.read();
					test = c != 'y';
				}
				if (test) {
					System.setIn(new FileInputStream(_SMALLNAME(i) + ".in"));
					System.setOut(new PrintStream(_PROBLEM + "-small-test.out"));
					new D_ogie().run();
					FileReader f1 = new FileReader(_PROBLEM + "-small-test.out");
					FileReader f2 = new FileReader(_SMALLNAME(i) + ".out");
					BufferedReader br1 = new BufferedReader(f1);
					BufferedReader br2 = new BufferedReader(f2);
					for (int j = 1;; j++) {
						String s1 = br1.readLine();
						String s2 = br2.readLine();
						if (s1 == null && s2 == null) {
							System.err.println("OK!");
							break;
						}
						if (s1 == null || s2 == null || !s1.equals(s2)) {
							System.err.println("failed at line " + j);
							System.err.println("expected " + s2);
							System.err.println("but " + s1);
							break;
						}
					}
				} else {
					System.setIn(new FileInputStream(_SMALLNAME(i) + ".in"));
					System.setOut(new PrintStream(_SMALLNAME(i) + ".out"));
					new D_ogie().run();
				}
			} else {
				System.setIn(new FileInputStream(_LARGENAME() + ".in"));
				System.setOut(new PrintStream(_LARGENAME() + ".out"));
				new D_ogie().run();
			}
		} else
			new D_ogie().run();
	}

	private static String _LARGENAME() {
		return _PROBLEM + "-large" + (_PRACTICE ? "-practice" : "");
	}

	private static String _SMALLNAME(int a) {
		return _PROBLEM + "-small"
				+ (_PRACTICE ? a == 0 ? "-practice" : "" : "-attempt" + a);
	}
}

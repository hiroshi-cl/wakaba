package com.github.hiroshi_cl.wakaba.v2010.sol.d2006;

import java.util.*;
import java.math.*;
import static java.lang.Math.*;
import static java.math.BigInteger.*;
import static java.util.Arrays.*;
import static java.util.Collections.*;

public class F {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		new F().run(sc);
	}

	double[] tod(Double[] Ds) {
		double[] ds = new double[Ds.length];
		for (int i = 0; i < ds.length; i++) {
			ds[i] = Ds[i];
		}
		return ds;
	}

	void run(Scanner sc) {
		for (;;) {
			int n = sc.nextInt();
			if (n == 0)
				return;
			Tower[] towers = new Tower[n];
			for (int i = 0; i < n; i++) {
				double x = sc.nextDouble(), y = sc.nextDouble();
				towers[i] = new Tower(-y, x, i);
			}
			TreeSet<Double> set = new TreeSet<Double>();
			set.add(0.);
			set.add(PI);
			for (int i = 0; i < n; i++)
				for (int j = i + 1; j < n; j++)
					calcTheta(set, towers[i].center, towers[j].center);
			double[] thetas = tod(set.toArray(new Double[0]));
			int m = thetas.length;
			double min = Integer.MAX_VALUE;
			double minTheta = 0;
			double max = 0;
			double maxTheta = 0;
			for (int i = 0; i < m; i++) {
				double length = calcSum(normalize(thetas[i]), towers);
				if (length < min) {
					min = length;
					minTheta = thetas[i];
				}
				if (length > max) {
					max = length;
					maxTheta = thetas[i];
				}
			}
			for (int i = 0; i < m - 1; i++) {
				double theta = (thetas[i] + thetas[i + 1]) / 2;
				Entry[] shadows = new Entry[n];
				for (int j = 0; j < n; j++) {
					shadows[j] = towers[j].shadow(theta);
				}
				sort(shadows);
				double child = 0;
				double mother = 0;
				for (int j = 0; j < n; j++) {
					int k = j;
					while (k < n - 1 && shadows[k].r >= shadows[k + 1].l)
						k++;
					int l = shadows[j].id;
					int r = shadows[k].id;
					child += -towers[r].center.y + towers[l].center.y;
					mother += -towers[r].center.x + towers[l].center.x;
					j = k;
				}
				if (mother == 0)
					continue;
				double pole = normalize(atan(-child / mother));
				if (pole < thetas[i] || thetas[i + 1] < pole)
					continue;
				double length = calcSum(normalize(pole), towers);
				if (length < min) {
					min = length;
					minTheta = pole;
				}
				if (length > max) {
					max = length;
					maxTheta = pole;
				}
			}
			System.out.printf("%.10f%n", minTheta);
			System.out.printf("%.10f%n", maxTheta);
		}
	}

	double calcSum(double theta, Tower[] towers) {
		int n = towers.length;
		Entry[] shadows = new Entry[n];
		for (int i = 0; i < n; i++) {
			shadows[i] = towers[i].shadow(theta);
		}
		sort(shadows);
		double res = 0;
		for (int i = 0; i < n; i++) {
			int j = i;
			while (j < n - 1 && shadows[j].r >= shadows[j + 1].l)
				j++;
			res += shadows[j].r - shadows[i].l;
			i = j;
		}
		return res;
	}

	class Entry implements Comparable<Entry> {
		double l, r;
		int id;

		Entry(double l, double r, int id) {
			this.l = l;
			this.r = r;
			this.id = id;
		}

		public int compareTo(Entry o) {
			return (int) signum(l - o.l);
		}
	}

	void calcTheta(TreeSet<Double> set, P c1, P c2) {
		P[][] inTan = innerTangent(c1, 1, c2, 1);
		P[][] ouTan = outerTangent(c1, 1, c2, 1);
		for (int i = 0; i < 2; i++) {
			if (inTan != null && i < inTan.length) {
				set.add(normalize(PI / 2 - inTan[i][1].sub(inTan[i][0]).angle()));
			}
			if (ouTan != null && i < ouTan.length) {
				set.add(normalize(PI / 2 - ouTan[i][1].sub(ouTan[i][0]).angle()));
			}
		}
	}

	double normalize(double theta) {
		return ((theta % PI) + PI) % PI;
	}

	int signum(double d) {
		return d < -EPS ? -1 : d > EPS ? 1 : 0;
	}

	double EPS = 1e-7;

	P[][] innerTangent(P o1, double r1, P o2, double r2) {// 内接線
		P o1o2 = o2.sub(o1);
		double l = o1o2.norm2();
		double d = l - (r1 + r2) * (r1 + r2);
		if (d < 0)
			return null;
		if (eq(d, 0)) {
			P p = o1.add(o1o2.mul(r1 / (r1 + r2)));
			return new P[][] { { p, p.add(o1o2.rot90().mul(1 / (r1 + r2))) } };
		}
		P p = o1o2.mul((r1 + r2) / l);
		P q = o1o2.rot90().mul(sqrt(d) / l);
		return new P[][] {
				{ o1.add(p.mul(r1)).add(q.mul(r1)),
						o2.sub(p.mul(r2)).sub(q.mul(r2)) },
				{ o1.add(p.mul(r1)).sub(q.mul(r1)),
						o2.sub(p.mul(r2)).add(q.mul(r2)) } };
	}

	boolean eq(double a, double b) {
		return signum(a - b) == 0;
	}

	static P[][] outerTangent(P o1, double r1, P o2, double r2) {// 外接線
		P o1o2 = o2.sub(o1);
		double l = o1o2.norm2();
		double d = l - (r1 - r2) * (r1 - r2);
		if (d < 0)
			return null;
		P p = o1o2.mul((r1 - r2) / l);
		P q = o1o2.rot90().mul(sqrt(d) / l);
		return new P[][] {
				{ o1.add(p.mul(r1)).add(q.mul(r1)),
						o2.add(p.mul(r2)).add(q.mul(r2)) },
				{ o1.add(p.mul(r1)).sub(q.mul(r1)),
						o2.add(p.mul(r2)).sub(q.mul(r2)) } };
	}

	class Tower {
		final int id;
		P center;
		final double l;
		final double phi;

		Tower(double x, double y, int id) {
			this.id = id;
			center = new P(x, y);
			l = center.norm();
			phi = center.angle();
		}

		Entry shadow(double theta) {
			double c = l * cos(phi + theta);
			return new Entry(c - 1, c + 1, id);
		}
	}

	class P {
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

		P rot90() {
			return new P(-y, x);
		}

		P rot(double t) {// verified
			return new P(x * cos(t) - y * sin(t), x * sin(t) + y * cos(t));
		}

		double norm() {
			return sqrt(norm2());
		}

		double norm2() {
			return x * x + y * y;
		}

		double angle() {
			return atan2(y, x);// [-PI, PI]
		}
	}

}
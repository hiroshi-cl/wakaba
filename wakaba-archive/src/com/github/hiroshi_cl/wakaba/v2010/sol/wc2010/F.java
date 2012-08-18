package com.github.hiroshi_cl.wakaba.v2010.sol.wc2010;

import java.math.*;
import java.util.*;
import static java.lang.Math.*;
import static java.math.BigInteger.*;
import static java.util.Arrays.*;
import static java.util.Collections.*;
import java.awt.*;
import java.awt.event.*;

public class F {
	void debug(Object... os) {
		System.err.println(deepToString(os));
	}

	void run() {
		Scanner sc = new Scanner(System.in);
		int n = sc.nextInt();
		P[][] line = new P[n][2];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < 2; j++) {
				line[i][j] = new P(sc.nextDouble(), sc.nextDouble());
			}
		}
		Arc[] arcs = new Arc[2 * n];
		for (int i = 0; i < n; i++) {
			P p = line[i][1], q = line[(i + 1) % n][0];
			P mid = p.add(q).div(2);
			int acw = (int) signum(line[i][0].sub(mid).det(line[i][1].sub(mid)));
			P np = line[i][0].sub(p).rot90().unit();
			P nq = q.sub(line[(i + 1) % n][1]).rot90().unit();
			double r = p.sub(q).norm() / nq.sub(np).norm();
			if (eq(r, 0)) {
			}
			P o = p.add(np.mul(-r * acw));
			double arg1 = p.sub(o).angle();
			double arg2 = q.sub(o).angle();
			arcs[2 * i] = new Arc(o, r, arg1, arg2, acw);
			arcs[2 * i + 1] = new Arc(o, r, arg2, arg1, -acw);
		}
		// for(Arc arc:arcs)debug(arc);
		// debug();
		for (int i = 0; i < 2 * n; i++) {// from
			for (int j = 0; j < 2 * n; j++)
				if (i / 2 != j / 2) {// to
					P[][][] its = {
							innerTangent(arcs[i].o, arcs[i].r, arcs[j].o,
									arcs[j].r),
							outerTangent(arcs[i].o, arcs[i].r, arcs[j].o,
									arcs[j].r) };
					for (P[][] it : its)
						if (it != null)
							for (P[] l : it) {
								for (int o = 0; o < 2; o++) {
									P tmp = l[0];
									l[0] = l[1];
									l[1] = tmp;
									// debug(l[0],l[1]);
									if (ok(l, arcs[i], arcs[j])) {
										V v1 = arcs[i].add(l[0]);
										V v2 = arcs[j].add(l[1]);
										// debug(v2);
										v1.es.add(new E(v2, l[0].sub(l[1])
												.norm()));
									}
								}
							}
				}
		}
		for (int i = 0; i < n * 2; i++) {
			arcs[i].sort();
			debug(i);
			debug("arc", arcs[i].vs);
			for (V v : arcs[i].vs) {
				debug(v, "'s Edge");
				for (E e : v.es) {
					debug(e.to);
				}
			}
			arcs[i].makeEdge();
		}
		E init = new E(arcs[0].vs.get(0), 0);
		init.to.dist = 0;
		PriorityQueue<E> que = new PriorityQueue<E>();
		que.offer(init);
		boolean first = true;
		while (!que.isEmpty()) {
			V now = que.poll().to;
			debug("now", now);
			for (E e : now.es) {
				if (e.to.dist > e.dist + now.dist) {
					e.to.dist = e.dist + now.dist;
					// debug(e.to);
					que.offer(e);
				}
			}
			if (first) {
				init.to.dist = 1 << 28;
				first = false;
			}
		}
		System.out.printf("%.3f%n", init.to.dist);
	}

	static double EPS = 1e-4;

	static boolean eq(double a, double b) {
		return abs(a - b) < EPS;
	}

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

	// verified: pku3011.
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

	boolean ok(P[] line, Arc from, Arc to) {
		double l0a = line[0].sub(from.o).angle();
		double l1a = line[1].sub(to.o).angle();
		if (!eq(line[0].sub(from.o).norm(), from.r))
			return false;
		if (!eq(line[1].sub(to.o).norm(), to.r))
			return false;
		if ((int) signum(line[0].sub(from.o).det(line[1].sub(line[0]))) != from.acw)
			return false;
		if ((int) signum(line[1].sub(to.o).det(line[1].sub(line[0]))) != to.acw)
			return false;
		// debug(line);
		return from.isIn(l0a) && to.isIn(l1a);
	}

	class Arc {
		P o;
		double r;
		double arg1, arg2;// [-PI,PI]
		int acw;

		public Arc(P o, double r, double arg1, double arg2, int acw) {
			super();
			this.o = o;
			this.r = r;
			this.arg1 = arg1;
			this.arg2 = arg2;
			this.acw = acw;
		}

		void makeEdge() {
			for (int i = 0; i < vs.size() - 1; i++) {
				V v1 = vs.get(i);
				V v2 = vs.get(i + 1);
				double dist = normalize((v2.p.sub(o).angle() - v1.p.sub(o)
						.angle()) * acw)
						* r;
				// debug(String.format("%.3f",dist/PI));
				v1.es.add(new E(v2, dist));
			}
		}

		public String toString() {
			return String.format("(%.3f %.3f) %.3f %.3f %.3f %d", o.x, o.y, r,
					arg1 / PI, arg2 / PI, acw);
		}

		ArrayList<V> vs = new ArrayList<V>();

		V add(P p) {
			V v = new V(p.x, p.y, this);
			vs.add(v);
			return v;
		}

		boolean isIn2(double a, double arg1, double arg2, boolean acw) {
			if (acw) {
				if (arg2 < arg1)
					arg2 += 2 * PI;
				return (arg1 < a && a < arg2)
						|| (arg1 < a + 2 * PI && a + 2 * PI < arg2);
			} else {
				if (arg1 < arg2)
					arg1 += 2 * PI;
				return (arg2 < a && a < arg1)
						|| (arg2 < a + 2 * PI && a + 2 * PI < arg1);
			}
		}

		boolean isIn(double a) {
			double d1 = arg2 - a, d2 = a - arg1;
			if (eq(d1, 0))
				d1 = 0;
			if (eq(d2, 0))
				d2 = 0;
			return eq(normalize((arg2 - arg1) * acw), normalize(d1 * acw)
					+ normalize(d2 * acw));
		}

		double normalize(double arg) {// [-2*PI.2*PI] -> [0,2*PI)
			return arg == 2 * PI ? 0 : arg < 0 ? arg + 2 * PI : arg;
		}

		void sort() {
			Collections.sort(vs);
			ArrayList<V> nvs = new ArrayList<V>();
			for (int i = 0; i < vs.size();) {
				nvs.add(vs.get(i));
				int j = i + 1;
				while (j < vs.size() && vs.get(i).p.equals(vs.get(j).p)) {
					V v = vs.get(i);
					V u = vs.get(j);
					for (E e : u.es)
						v.es.add(e);
					j++;
				}
				i = j;
			}
			vs = nvs;
		}
	}

	class V implements Comparable<V> {
		public String toString() {
			return p.toString();
		}

		double dist = 1 << 28;
		Arc arc;
		P p;
		ArrayList<E> es = new ArrayList<E>();

		public V(double x, double y, Arc arc) {
			super();
			this.arc = arc;
			p = new P(x, y);
		}

		double arg() {
			double dif = (p.sub(arc.o).angle() - arc.arg1) * arc.acw;
			if (dif == 2 * PI)
				dif = 0;
			if (dif < 0)
				dif += 2 * PI;
			return dif;
		}

		public int compareTo(V o) {
			return (int) signum(arg() - o.arg());
		}
	}

	class E implements Comparable<E> {
		V to;
		double dist;

		public E(V to, double dist) {
			super();
			this.to = to;
			this.dist = dist;
		}

		public int compareTo(E o) {
			return (int) signum(dist - o.dist);
		}
	}

	static class P {
		double x, y;

		public String toString() {
			return String.format("%.3f %.3f", x, y);
		}

		public P(double x, double y) {
			super();
			this.x = x;
			this.y = y;
		}

		double det(P p) {
			return x * p.y - y * p.x;
		}

		P sub(P p) {
			return new P(x - p.x, y - p.y);
		}

		P add(P p) {
			return new P(x + p.x, y + p.y);
		}

		double norm2() {
			return x * x + y * y;
		}

		double norm() {
			return sqrt(x * x + y * y);
		}

		P unit() {
			double d = norm();
			return new P(x / d, y / d);
		}

		P rot90() {
			return new P(-y, x);
		}

		double angle() {
			return atan2(y, x);
		}

		P div(double d) {
			return new P(x / d, y / d);
		}

		P mul(double d) {
			return new P(x * d, y * d);
		}

		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result;
			long temp;
			temp = Double.doubleToLongBits(x);
			result = prime * result + (int) (temp ^ (temp >>> 32));
			temp = Double.doubleToLongBits(y);
			result = prime * result + (int) (temp ^ (temp >>> 32));
			return result;
		}

		public boolean equals(Object obj) {
			P p = (P) obj;
			return eq(x, p.x) && eq(y, p.y);
		}
	}

	public static void main(String[] args) {
		new F().run();
	}

	static class SimpleVis {
		private final Frame fr = new Frame() {
			@Override
			public void paint(Graphics g) {
				super.paint(g);
				if (vs == null)
					return;
				int[] x = new int[vs.length];
				int[] y = new int[vs.length];
				for (int i = 0; i < vs.length; i++) {
					x[i] = sx + (int) round(pw * vs[i].x);
					y[i] = sy - (int) round(pw * vs[i].y);
				}
				g.drawPolyline(x, y, vs.length);
			}
		};
		private P[] vs = null;
		private int sx = 200;
		private int sy = 200;
		private double pw = 1.;
		private boolean lock = false;

		public SimpleVis() {
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
			fr.setSize(sx * 2, sy * 2);
			fr.setVisible(true);
		}

		/**
		 * 折れ線を画面に描画する. 何らかの入力があるまで, プログラムを中断.
		 * 
		 * @param v
		 *            折れ線を構成する点
		 */
		public void debugLine(P... v) {
			vs = v;
			fr.repaint();
			lock = true;
			try {
				while (lock)
					Thread.sleep(100);
			} catch (InterruptedException e) {
			}
		}

		public static void main(String[] args) {
			SimpleVis sv = new SimpleVis();
			while (true) {
				sv.debugLine(new P(50, 100), new P(100, 150), new P(50, 150));
				sv.debugLine(new P(150, 100), new P(100, 150), new P(150, 150));
			}
		}
	}

}

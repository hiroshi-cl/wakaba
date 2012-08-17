package com.github.hiroshi_cl.wakaba.v2010.lib.geometry.v2010b;

import static com.github.hiroshi_cl.wakaba.v2010.lib.geometry.v2010b.EPS.*;

import java.util.*;
import static java.lang.Math.*;
import static java.util.Arrays.*;
import java.awt.Shape;
import java.awt.geom.*;

public class Polygon {
	final P[] ps;

	public Polygon(P[] ps) {
		this.ps = ps;
	}

	static Polygon convexHull(P[] ps) { // O(n logn).
		ps = ps.clone();
		int n = ps.length;
		sort(ps);
		P[] ds = new P[n];// ds[0] = us[un-1] = ps[0].
		P[] us = new P[n];// ds[dn-1] = us[0] = ps[n-1].
		int dn = 0;
		int un = 0;
		for (int i = 0; i < n; ds[dn++] = ps[i++])
			while (dn >= 2
					&& sgn(ds[dn - 1].sub(ds[dn - 2])
							.det(ps[i].sub(ds[dn - 2]))) <= 0)
				dn--;
		for (int i = n - 1; i >= 0; us[un++] = ps[i--])
			while (un >= 2
					&& sgn(us[un - 1].sub(us[un - 2])
							.det(ps[i].sub(us[un - 2]))) <= 0)
				un--;
		P[] res = new P[dn + un - 2];
		System.arraycopy(ds, 0, res, 0, dn - 1);
		System.arraycopy(us, 0, res, dn - 1, un - 1);
		return new Polygon(res);
	}

	double area(P[] ps) {// 符号付き面積。反時計回りのとき正、時計回りのとき負。
		double S = 0;
		for (int i = 0; i < ps.length; i++)
			S += ps[i].det(ps[(i + 1) % ps.length]);
		return S / 2;
	}

	// TODO check.
	Polygon[] convexCut(L l) {// 凸多角形をlで切断した左側、右側を返す。
		List<P> ls = new ArrayList<P>(), rs = new ArrayList<P>();
		P p1p2 = l.p2.sub(l.p1);
		int n = ps.length;
		for (int i = 0; i < n; i++) {
			P q1 = ps[i], q2 = ps[(i + 1) % n];
			double d1 = p1p2.det(q1.sub(l.p1)), d2 = p1p2.det(q2.sub(l.p1));
			if (d1 > 0)
				ls.add(q1);
			else
				rs.add(q1);
			if (d1 * d2 <= 0) {
				P p = l.intersection(new L(q1, q2, 2), false);
				ls.add(p);
				rs.add(p);
			}
		}
		return new Polygon[] { new Polygon(ls.toArray(new P[0])),
				new Polygon(rs.toArray(new P[0])) };
	}

	// verified: pku2187.
	double convexDiameter() {// 凸多角形の直径を求める。O(n).
		int n = ps.length;
		int si = 0, sj = 0;
		for (int k = 0; k < n; k++) {
			if (ps[k].compareTo(ps[si]) < 0)
				si = k;
			if (ps[sj].compareTo(ps[k]) < 0)
				sj = k;
		}
		int i = si, j = sj;
		double res = 0;
		do {
			if (ps[(i + 1) % n].sub(ps[i]).det(ps[(j + 1) % n].sub(ps[j])) >= 0)
				j = (j + 1) % n;
			else
				i = (i + 1) % n;
			res = max(res, ps[i].dist(ps[j]));
		} while (!(i == si && j == sj));
		return res;
	}

	// TODO check
	// 入力された順序で、凸包になっているかを調べる。le->ltで、辺上の点を許可する。O(n).
	boolean isConvexHull() {// O(n).
		int n = ps.length;
		if (n < 3)
			return false;
		boolean anticlockwise = true, clockwise = true;
		for (int i = 0; i < n; i++) {
			double value = ps[(i + 1) % n].sub(ps[i]).det(
					ps[(i + 2) % n].sub(ps[i]));
			if (sgn(value) <= 0)
				anticlockwise = false;
			if (sgn(value) >= 0)
				clockwise = false;
		}
		return anticlockwise || clockwise;
	}

	// verified: pku2504.
	static Polygon regularPolygon(P p1, P p2, P p3, int n) {// p1,p2,p3を含む正n角形
		P o = new Triangle(p1, p2, p3).excentre();
		P[] res = new P[n];
		res[0] = p1;
		boolean ok1 = false, ok2 = false;
		double theta = 2 * PI / n;
		for (int i = 1; i < n; i++) {
			res[i] = o.add(res[i - 1].sub(o).rot(theta));
			if (!ok1 && res[i].equals(p2))
				ok1 = true;
			if (!ok2 && res[i].equals(p3))
				ok2 = true;
		}
		return ok1 && ok2 ? new Polygon(res) : null;
	}

	// verified: pku3525.
	int contains(P p) {// 点、多角形包含判定 OUT,ON,IN = -1, 0, 1.
		int n = ps.length;
		int res = -1;
		for (int i = 0; i < n; i++) {
			P a = ps[i].sub(p), b = ps[(i + 1) % n].sub(p);
			if (a.y > b.y) {
				P t = a;
				a = b;
				b = t;
			}
			if (a.y <= 0 && 0 < b.y && a.det(b) < 0)
				res *= -1;
			if (a.det(b) == 0 && a.dot(b) <= 0)
				return 0;
		}
		return res;
	}

	Shape toShape() {
		int n = ps.length;
		Path2D.Double ret = new Path2D.Double();
		ret.moveTo(ps[0].x, ps[0].y);
		for (int i = 1; i <= ps.length; i++)
			ret.lineTo(ps[i % n].x, ps[i % n].y);
		return ret;
	}

}

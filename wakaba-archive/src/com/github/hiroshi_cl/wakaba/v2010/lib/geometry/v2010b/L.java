package com.github.hiroshi_cl.wakaba.v2010.lib.geometry.v2010b;

import static com.github.hiroshi_cl.wakaba.v2010.lib.geometry.v2010b.EPS.*;

import java.util.*;
import static java.lang.Math.*;
import static java.util.Arrays.*;
import java.awt.Shape;
import java.awt.geom.*;

public class L {
	final P p1, p2;
	final int kind; // 0,1,2 でそれぞれ直線、半直線、線分を意味するフラグ。(端点数)

	L(P p1, P p2, int kind) {
		this.p1 = p1;
		this.p2 = p2;
		this.kind = kind;
	}

	// 線分用
	int contains(P p) { // OUT,ON,IN -> -1,0,1
		return p.contained(p1, p2);
	}

	double distM(P p) {// Manhattan distance
		if (kind < 2)
			throw new UnsupportedOperationException();

		double res = Double.MAX_VALUE;
		if (!eq(p1.x, p2.x)
				&& (p1.x <= p.x && p.x <= p2.x || p2.x <= p.x && p.x <= p1.x))
			res = min(res, abs(p.y - p1.y - (p.x - p1.x) * (p2.y - p1.y)
					/ (p2.x - p1.x)));
		if (!eq(p1.y, p2.y)
				&& (p1.y <= p.y && p.y <= p2.y || p2.y <= p.y && p.y <= p1.y))
			res = min(res, abs(p.x - p1.x - (p.y - p1.y) * (p2.x - p1.x)
					/ (p2.y - p1.y)));
		res = min(res, abs(p1.x - p.x) + abs(p1.y - p.y));
		res = min(res, abs(p2.x - p.x) + abs(p2.y - p.y));
		return res;
	}

	L combine(L s) { // IF 'truly' overlap THEN combined segment ELSE null.
		if (kind < 2 || s.kind < 2)
			throw new UnsupportedOperationException();

		P r = p2.sub(p1), t = s.p2.sub(s.p1);
		if (!eq(r.det(t), 0)) // other direction
			return null;
		if (p1.equals(s.p1) && p2.equals(s.p2) || p1.equals(s.p2)
				&& p2.equals(s.p1))
			return s; // equal segment.
		if (contains(s.p1) < 1 && contains(s.p2) < 1 && s.contains(p1) < 1
				&& s.contains(p2) < 1)
			return null;// not overlap.
		P[] tmp = { p1, p2, s.p1, s.p2 };
		sort(tmp);
		return new L(tmp[0], tmp[3], 2);
	}

	// p1 -> p2 を m : n に内分する。外分は片方マイナスにする
	P dividing(double m, double n) {
		return p1.mul(n).add(p2.mul(m)).div(m + n);
	}

	//

	public boolean equals(Object obj) {
		L s = (L) obj;
		if (kind < 2 || s.kind < 2)
			throw new UnsupportedOperationException();
		return p1.equals(s.p1) && p2.equals(s.p2);
	}

	public int hashCode() {
		throw new UnsupportedOperationException();
	}

	// intersection
	P intersectionLL(L l) {
		P a = p2.sub(p1), b = l.p1.sub(p1), c = l.p2.sub(l.p1);
		double d = c.det(a);
		double e = c.det(b);
		if (eq(d, 0))
			return null; // eq(e,0) なら同一直線上.
		return p1.add(a.mul(e / d));
	}

	/**
	 * pが直線lに乗っていることは前提。
	 * 
	 * @return pが IN -> 1 ,ON -> 0 ,OUT -> -1.
	 */
	private int calc(P p) {
		if (kind == 0)
			return 1;
		if (kind == 1)
			return -p1.contained(p2, p);
		return p.contained(p1, p2);
	}

	/**
	 * 端点処理の対象となる点
	 */
	private List<P> endPoints() {
		List<P> ps = new ArrayList<P>();
		if (kind >= 1)
			ps.add(p1);
		if (kind == 2)
			ps.add(p2);
		return ps;
	}

	/**
	 * 一般化したintersection. 共有点が１個でない場合 nullを返す。 flag = trueの時、端点を含めるようにする。
	 * ただし、flag=trueで線分(半直線)が重なっている場合、重なっている点のいずれか一つを返してしまう。 TODO verify.
	 */
	P intersection(L l, boolean flag) {
		if (flag) {
			List<P> ps = endPoints();
			List<P> rs = l.endPoints();
			for (P p : ps)
				for (P r : rs)
					if (p.equals(r))
						return p;
		}
		P res = intersectionLL(l);
		if (res == null)
			return null;
		int a = calc(res);
		int b = l.calc(res);
		if (a + b == 2 || (flag && a >= 0 && b >= 0))
			return res;
		return null;
	}

	// dist
	// 直線類と直線類の距離
	double dist(L l) {
		if (kind == 1 || l.kind == 1)
			throw new UnsupportedOperationException();
		if (kind == 0 && l.kind == 0) {
			if (!eq(p1.sub(p2).det((l.p1.sub(l.p2))), 0))
				return 0;
			return dist(l.p1);
		} else if (kind == 0 || l.kind == 0) {
			if (intersection(l, true) != null)
				return 0;
			return min(dist(l.p1), l.dist(p1));
		} else {
			if (intersection(l, true) != null)
				return 0;
			double res = Integer.MAX_VALUE;
			res = min(res, dist(l.p1));
			res = min(res, dist(l.p2));
			res = min(res, l.dist(p1));
			res = min(res, l.dist(p2));
			return res;
		}
	}

	double dist(P p) {
		if (kind == 0) {
			P p1p2 = p2.sub(p1), p1p = p.sub(p1);
			return abs(p1p2.det(p1p)) / p1p2.norm();
		} else if (kind == 2) {
			P foot = perpendicularFoot(p);
			return foot == null ? min(p1.dist(p), p2.dist(p)) : foot.dist(p);
		} else
			throw new UnsupportedOperationException();
	}

	// TODO check
	// 直線or線分への垂線の足。存在しないときはnullを返す。
	P perpendicularFoot(P p) {
		P pp1 = p1.sub(p), p1p2 = p2.sub(p1);
		double t = -pp1.dot(p1p2) / p1p2.norm2();
		if (kind == 1)
			throw new UnsupportedOperationException();
		return kind == 0 || sgn(t) >= 0 && sgn(t - 1) <= 0 ? p1
				.add(p1p2.mul(t)) : null;
	}

	Shape toShape() {
		return new Line2D.Double(p1.toPoint2D(), p2.toPoint2D()); // 線分用
	}
}

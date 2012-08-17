package com.github.hiroshi_cl.wakaba.v2010.lib.geometry.v2010a;

import static java.lang.Math.*;
import static com.github.hiroshi_cl.wakaba.v2010.lib.geometry.v2010a.EPS.*;
import static java.util.Arrays.*;

// - 線分クラス
class S { // immutable.
	final P p1, p2;

	S(P p1, P p2) {
		this.p1 = p1;
		this.p2 = p2;
	}

	int contains(P p) { // OUT,ON,IN -> -1,0,1
		if (p1.equals(p) || p2.equals(p))
			return 0;
		P r1 = p1.sub(p), r2 = p2.sub(p);
		if (eq(r1.det(r2), 0) && eq(r1.dot(r2), -r1.norm() * r2.norm()))
			return 1;
		return -1;
	}

	double distM(P p) {// Manhattan distance
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

	S combine(S s) { // IF 'truly' overlap THEN combined segment ELSE null.
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
		return new S(tmp[0], tmp[3]);
	}

	P intersection(S s) {// IF 'truly' intersect '1' point THEN point ELSE null.
		P r = p2.sub(p1), t = s.p2.sub(s.p1);
		double d = t.det(r);
		if (eq(d, 0))
			return null;// parallel.
		P p = p1.add(r.mul(t.det(s.p1.sub(p1)) / d)); // intersection if line.
		if (contains(p) == 1 && s.contains(p) == 1)
			return p;
		return null;
	}

	double length() {
		return p1.dist(p2);
	}

	P mid() {
		return p1.add(p2).div(2);
	}

	public boolean equals(Object obj) {
		S s = (S) obj;
		return p1.equals(s.p1) && p2.equals(s.p2);
	}

	public int hashCode() {
		throw new UnsupportedOperationException();
	}
}
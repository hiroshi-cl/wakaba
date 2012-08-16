package com.github.hiroshi_cl.wakaba.v2009.lib.geometryReal;

import static java.lang.Math.abs;
import static java.lang.Math.min;

class Method_Line {
	static double EPS = 1e-9;

	// TODO check
	static P perpendicularFoot_l(P p1, P p2, P p) {// 直線への垂線の足
		P pp1 = p1.sub(p), p1p2 = p2.sub(p1);
		return p1.sub(p1p2.mul(pp1.dot(p1p2) / p1p2.norm2()));
	}

	// TODO check
	static P intersection_ss(P p1, P p2, P q1, P q2) {// 線分と線分の交点
		if (!intersect_ss(p1, p2, q1, q2))
			return null;
		return intersection_ll(p1, p2, q1, q2);
	}

	// TODO check
	static P intersection_ls(P l1, P l2, P s1, P s2) {
		return intersect_ls(l1, l2, s1, s2) ? intersection_ll(l1, l2, s1, s2)
				: null;
	}

	// verified pku3525.
	static P intersection_ll(P p1, P p2, P q1, P q2) {// 直線と直線の交点
		P p1p2 = p2.sub(p1), p1q1 = q1.sub(p1), q1q2 = q2.sub(q1);
		double d = q1q2.det(p1p2);
		return eq(d, 0) ? null : p1.add(p1p2.mul(q1q2.det(p1q1) / d));
	}

	// verified UVa10709.
	static boolean intersect_ss(P p1, P p2, P q1, P q2) {// 線分と線分が交わるか。
		if (intersect_sp(p1, p2, q1) || intersect_sp(p1, p2, q2)
				|| intersect_sp(q1, q2, p1) || intersect_sp(q1, q2, p2))
			return true; // 端点を共有
		P p1p2 = p2.sub(p1), q1q2 = q2.sub(q1);
		return lt(p1p2.det(q1.sub(p1)) * p1p2.det(q2.sub(p1)), 0) // 真に交わる
				&& lt(q1q2.det(p1.sub(q1)) * q1q2.det(p2.sub(q1)), 0);
	}

	// verified UVa10709.
	static boolean intersect_ls(P l1, P l2, P s1, P s2) {
		if (intersect_lp(l1, l2, s1) || intersect_lp(l1, l2, s2))
			return true;
		P l1l2 = l2.sub(l1);
		return l1l2.det(s1.sub(l1)) * l1l2.det(s2.sub(l1)) < 0;
	}

	// verified UVa10709.
	static boolean intersect_lp(P p1, P p2, P p) {// pが直線p1p2上にあるか。
		return eq(p1.sub(p).det(p2.sub(p)), 0);
	}

	// verified UVa10709.
	static boolean intersect_sp(P p1, P p2, P p) {// pが線分p1p2上にあるか。
		P pp1 = p1.sub(p), pp2 = p2.sub(p);
		return eq(pp1.det(pp2), 0) && le(pp1.dot(pp2), 0);
	}

	// verified UVa10709.
	static double dist_ll(P p1, P p2, P q1, P q2) {// 直線と直線の距離
		if (!eq(p1.sub(p2).det((q1.sub(q2))), 0))
			return 0;
		return dist_lp(p1, p2, q1);
	}

	// verified UVa10709.
	static double dist_ls(P l1, P l2, P s1, P s2) {// 直線と線分の距離
		if (intersect_ls(l1, l2, s1, s2))
			return 0;
		return min(dist_lp(l1, l2, s1), dist_lp(l1, l2, s2));
	}

	// verified UVa10709.
	static double dist_ss(P p1, P p2, P q1, P q2) {// 線分と線分の距離
		if (intersect_ss(p1, p2, q1, q2))
			return 0;
		double res = Integer.MAX_VALUE;
		res = min(res, dist_sp(p1, p2, q1));
		res = min(res, dist_sp(p1, p2, q2));
		res = min(res, dist_sp(q1, q2, p1));
		res = min(res, dist_sp(q1, q2, p2));
		return res;
	}

	// verified UVa10709.
	static double dist_lp(P p1, P p2, P p) {// 点と直線の距離
		P p1p2 = p2.sub(p1), p1p = p.sub(p1);
		return abs(p1p2.det(p1p)) / p1p2.norm();
	}

	// verified UVa10709.
	static double dist_sp(P p1, P p2, P p) {// 線分と点の距離
		P foot = perpendicularFoot_s(p1, p2, p);
		return foot == null ? min(p1.dist(p), p2.dist(p)) : foot.dist(p);
	}

	// verified UVa10709.
	static P perpendicularFoot_s(P p1, P p2, P p) {// 線分への垂線の足。存在しないときはnullを返す。
		P pp1 = p1.sub(p), p1p2 = p2.sub(p1);
		double t = -pp1.dot(p1p2) / p1p2.norm2();
		return le(0, t) && le(t, 1) ? p1.add(p1p2.mul(t)) : null;
	}

	// verified pku3525.
	P center(P a1, P a2, P b1, P b2, P c1, P c2) {// ３線分に接しうる円の中心.平行、鈍角の場合にも対応。※線分は、凸多角形を構成する辺であること。
		P[] ps = new P[5];
		ps[0] = ps[3] = intersection_ll(a1, a2, b1, b2);
		ps[1] = ps[4] = intersection_ll(b1, b2, c1, c2);
		ps[2] = intersection_ll(c1, c2, a1, a2);
		P[] ms = new P[5];
		ms[0] = ms[3] = a1.add(a2).div(2);
		ms[1] = ms[4] = b1.add(b2).div(2);
		ms[2] = c1.add(c2).div(2);
		for (int i = 0;; i++)
			if (ps[i] == null || i == 2) {
				P d1 = ms[i + 1].sub(ps[i + 1]).unit(), d2 = ms[i + 2].sub(
						ps[i + 1]).unit();
				P d3 = ms[i + 2].sub(ps[i + 2]).unit(), d4 = ms[i].sub(
						ps[i + 2]).unit();
				return intersection_ll(ps[i + 1],
						ps[i + 1].add(d1.add(d2).unit()), ps[i + 2],
						ps[i + 2].add(d3.add(d4).unit()));
			}
	}

	static private boolean eq(double a, double b) {
		return signum(a - b) == 0;
	}

	static private boolean le(double a, double b) {
		return signum(a - b) <= 0;
	}

	static private boolean lt(double a, double b) {
		return signum(a - b) < 0;
	}

	static private int signum(double d) {
		return d < -EPS ? -1 : d > EPS ? 1 : 0;
	}
}

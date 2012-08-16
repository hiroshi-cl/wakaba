package com.github.hiroshi_cl.wakaba.v2009.lib.geometryReal;
import static java.lang.Math.PI;
import static java.lang.Math.abs;
import static java.lang.Math.acos;
import static java.lang.Math.min;
import static java.lang.Math.sin;
import static java.lang.Math.sqrt;
import java.util.ArrayList;
class Method_Circle {
	static private double EPS = 1e-9;
	// verified: pku3011.
	P[][] innerTangent(P o1, double r1, P o2, double r2) {// 内接線
		P o1o2 = o2.sub(o1);
		double l = o1o2.norm2();
		double d = l - (r1 + r2) * (r1 + r2);
		if (d < 0) return null;
		if (eq(d, 0)) {
			P p = o1.add(o1o2.mul(r1 / (r1 + r2)));
			return new P[][] { { p, p.add(o1o2.rot90().mul(1 / (r1 + r2))) } };
		}
		P p = o1o2.mul((r1 + r2) / l);
		P q = o1o2.rot90().mul(sqrt(d) / l);
		return new P[][] { { o1.add(p.mul(r1)).add(q.mul(r1)), o2.sub(p.mul(r2)).sub(q.mul(r2)) },
		{ o1.add(p.mul(r1)).sub(q.mul(r1)), o2.sub(p.mul(r2)).add(q.mul(r2)) } };
	}
	// verified: pku3011.
	static P[][] outerTangent(P o1, double r1, P o2, double r2) {// 外接線
		P o1o2 = o2.sub(o1);
		double l = o1o2.norm2();
		double d = l - (r1 - r2) * (r1 - r2);
		if (d < 0) return null;
		P p = o1o2.mul((r1 - r2) / l);
		P q = o1o2.rot90().mul(sqrt(d) / l);
		return new P[][] { { o1.add(p.mul(r1)).add(q.mul(r1)), o2.add(p.mul(r2)).add(q.mul(r2)) },
		{ o1.add(p.mul(r1)).sub(q.mul(r1)), o2.add(p.mul(r2)).sub(q.mul(r2)) } };
	}
	// TODO check
	static double area_cc(P o1, double r1, P o2, double r2) {// 円と円の共通部分の面積
		double d = o1.sub(o2).norm();
		if (le(r1 + r2, d)) return 0;
		if (le(d, abs(r1 - r2))) {
			double r = min(r1, r2);
			return r * r * PI;
		}
		double r1c = (d * d + r1 * r1 - r2 * r2) / (2 * d);
		double theta = acos(r1c / r1);
		double phi = acos((d - r1c) / r2);
		return r1 * r1 * theta + r2 * r2 * phi - d * r1 * sin(theta);
	}
	// TODO check
	static P[] center_c(P p1, P p2, double r) {// p1,p2を通る、半径rの円の中心
		P p1p2 = p2.sub(p1);
		double x = p1p2.norm() / 2;
		double t = r * r - x * x;
		if (t < 0) return null;
		P q1 = p1.add(p2).div(2), q2 = p1p2.rot90().mul(sqrt(t) / (x * 2));
		return new P[] { q1.add(q2), q1.sub(q2) };
	}
	// verified: pku1418.
	P[] intersection_cc(P c1, double r1, P c2, double r2) {// 円と円の交点
		P c1c2 = c2.sub(c1);
		double r = c1c2.norm();
		double x = (r * r + r1 * r1 - r2 * r2) / (2 * r);
		double t = r1 * r1 - x * x;
		if (t < 0) return null;
		P q1 = c1.add(c1c2.mul(x / r)), q2 = c1c2.rot90().mul(sqrt(t) / r);
		return new P[] { q1.add(q2), q1.sub(q2) };
	}
	// verified? : pku1418(x軸に平行な直線のみ）
	static P[] intersection_lc(P p1, P p2, P o, double r) {// 直線と円の交点
		P op1 = p1.sub(o), p1p2 = p2.sub(p1);
		double a = p1p2.norm2();
		double b = op1.dot(p1p2);
		double c = op1.norm2() - r * r;
		double d = b * b - a * c;
		if (eq(d, 0)) return new P[] { p1.add(p1p2.mul(-b / a)) };
		if (d < 0) return null;
		double t1 = (-b + sqrt(d)) / a, t2 = (-b - sqrt(d)) / a;
		return new P[] { p1.add(p1p2.mul(t1)), p1.add(p1p2.mul(t2)) };
	}
	// TODO check
	static P[] intersection_sc(P p1, P p2, P o, double r) {// 線分と円の交点
		P op1 = p1.sub(o), p1p2 = p2.sub(p1);
		double a = p1p2.norm2();
		double b = op1.dot(p1p2);
		double c = op1.norm2() - r * r;
		double d = b * b - a * c;
		if (eq(d, 0)) d = 0;
		else if (le(d, 0)) return null;
		double t1 = (-b + sqrt(d)) / a, t2 = (-b - sqrt(d)) / a;
		ArrayList<P> res = new ArrayList<P>();
		if (le(0, t1) && le(t1, 1)) res.add(p1.add(p1p2.mul(t1)));
		if (t1 != t2 && le(0, t2) && le(t2, 1)) res.add(p1.add(p1p2.mul(t2)));
		return res.toArray(new P[0]);
	}
	// TODO check
	static P[] tangent(P o, double r, P p) {// 円にpから引いた接線の接点
		P op = p.sub(o);
		double op2 = op.norm2(), r2 = r * r;
		double t = op2 - r2;
		if (t < 0) return null;
		P q1 = op.mul(r2 / op2), q2 = op.rot90().mul(r * sqrt(t) / op2);
		return new P[] { o.add(q1).add(q2), o.add(q1).sub(q2) };
	}
	static private boolean eq(double a, double b) {
		return signum(a - b) == 0;
	}
	static private boolean le(double a, double b) {
		return signum(a - b) <= 0;
	}
	static private int signum(double d) {
		return d < -EPS ? -1 : d > EPS ? 1 : 0;
	}
}

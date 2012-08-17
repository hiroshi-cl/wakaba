package com.github.hiroshi_cl.wakaba.v2010.lib.geometry.v2010b;

import static com.github.hiroshi_cl.wakaba.v2010.lib.geometry.v2010b.EPS.*;
import static java.lang.Math.*;
import java.util.*;
import java.awt.Shape;
import java.awt.geom.*;

public class C extends P {
	final double r;
	final P o = this;

	public C(double x, double y, double r) {
		super(x, y);
		this.r = r;
	}

	public C(P p, double r) {
		super(p.x, p.y);
		this.r = r;
	}

	L[] innerTangent(C c2) {// 内接線
		P c1c2 = c2.sub(this);
		double l = c1c2.norm2();
		double d = l - (r + c2.r) * (r + c2.r);
		if (d < 0)
			return null;
		if (eq(d, 0)) {
			P p = add(c1c2.mul(r / (r + c2.r)));
			return new L[] { new L(p, p.add(c1c2.rot90().mul(1 / (r + c2.r))),
					0) };
		}
		P p = c1c2.mul((r + c2.r) / l);
		P q = c1c2.rot90().mul(sqrt(d) / l);
		return new L[] {
				new L(add(p.mul(r)).add(q.mul(r)), c2.sub(p.mul(c2.r)).sub(
						q.mul(c2.r)), 0),
				new L(add(p.mul(r)).sub(q.mul(r)), c2.sub(p.mul(c2.r)).add(
						q.mul(c2.r)), 0) };
	}

	L[] outerTangent(C c2) {// 外接線
		P c1c2 = c2.sub(this);
		double l = c1c2.norm2();
		double d = l - (r - c2.r) * (r - c2.r);
		if (d < 0)
			return null;
		P p = c1c2.mul((r - c2.r) / l);
		P q = c1c2.rot90().mul(sqrt(d) / l);
		return new L[] {
				new L(add(p.mul(r)).add(q.mul(r)), c2.add(p.mul(c2.r)).add(
						q.mul(c2.r)), 0),
				new L(add(p.mul(r)).sub(q.mul(r)), c2.add(p.mul(c2.r)).sub(
						q.mul(c2.r)), 0) };
	}

	// TODO check
	double areaCC(C c2) {// 円と円の共通部分の面積
		double d = sub(c2).norm();
		if (sgn(r + c2.r - d) <= 0)
			return 0;
		if (sgn(d - abs(r - c2.r)) <= 0) {
			double rr = min(r, c2.r);
			return rr * rr * PI;
		}
		double r1c = (d * d + r * r - c2.r * c2.r) / (2 * d);
		double theta = acos(r1c / r);
		double phi = acos((d - r1c) / c2.r);
		return r * r * theta + c2.r * c2.r * phi - d * r * sin(theta);
	}

	// TODO check
	static C[] centerC(P p1, P p2, double r) {// p1,p2を通る、半径rの円の中心
		P p1p2 = p2.sub(p1);
		double x = p1p2.norm() / 2;
		double t = r * r - x * x;
		if (t < 0)
			return null;
		P q1 = p1.add(p2).div(2), q2 = p1p2.rot90().mul(sqrt(t) / (x * 2));
		return new C[] { new C(q1.add(q2), r), new C(q1.sub(q2), r) };
	}

	P[] intersectionCC(C c2) {// 円と円の交点
		P c1c2 = c2.sub(this);
		double rr = c1c2.norm();
		double x = (rr * rr + r * r - c2.r * c2.r) / (2 * rr);
		double t = r * r - x * x;
		if (t < 0)
			return null;
		P q1 = add(c1c2.mul(x / rr)), q2 = c1c2.rot90().mul(sqrt(t) / rr);
		return new P[] { q1.add(q2), q1.sub(q2) };
	}

	// TODO check
	// 直線類と円の交点
	P[] intersection(L l) {
		if (l.kind == 1)
			throw new UnsupportedOperationException();

		P op1 = l.p1.sub(o), p1p2 = l.p2.sub(l.p1);
		double a = p1p2.norm2();
		double b = op1.dot(p1p2);
		double c = op1.norm2() - r * r;
		double d = b * b - a * c;
		if (eq(d, 0))
			if (l.kind == 0)
				return new P[] { l.p1.add(p1p2.mul(-b / a)) };
			else if (l.kind == 2)
				d = 0;
			else if (sgn(d) <= 0)
				return null;

		if (l.kind == 0) {
			double t1 = (-b + sqrt(d)) / a, t2 = (-b - sqrt(d)) / a;
			return new P[] { l.p1.add(p1p2.mul(t1)), l.p1.add(p1p2.mul(t2)) };
		} else if (l.kind == 2) {
			double t1 = (-b + sqrt(d)) / a, t2 = (-b - sqrt(d)) / a;
			ArrayList<P> res = new ArrayList<P>();
			if (sgn(t1) >= 0 && sgn(t1 - 1) <= 0)
				res.add(l.p1.add(p1p2.mul(t1)));
			if (t1 != t2 && sgn(t2) >= 0 && sgn(t2 - 1) <= 0)
				res.add(l.p1.add(p1p2.mul(t2)));
			return res.toArray(new P[0]);
		}
		throw new UnsupportedOperationException();
	}

	// TODO check
	P[] tangent(P p) {// 円にpから引いた接線の接点
		P op = p.sub(o);
		double op2 = op.norm2(), r2 = r * r;
		double t = op2 - r2;
		if (t < 0)
			return null;
		P q1 = op.mul(r2 / op2), q2 = op.rot90().mul(r * sqrt(t) / op2);
		return new P[] { o.add(q1).add(q2), o.add(q1).sub(q2) };
	}

	Shape toShape() {
		return new Ellipse2D.Double(x - r, y - r, 2 * r, 2 * r);
	}
}

package com.github.hiroshi_cl.wakaba.v2010.lib.geometry.v2010b;

import static com.github.hiroshi_cl.wakaba.v2010.lib.geometry.v2010b.EPS.*;
import static java.lang.Math.*;
import java.awt.geom.*;

/*
 - 点クラス
 - このargはacosがNaNを返さないように配慮されています
 */

public class P implements Comparable<P> {
	final double x, y;

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

	// optional
	P rot90() {
		return new P(-y, x);
	}

	P rot(double t) {// verified
		return new P(x * cos(t) - y * sin(t), x * sin(t) + y * cos(t));
	}

	double arg() {// verified.
		return atan2(y, x);// [-PI,PI].
	}

	double arg(P p) {
		double d = dot(p) / (norm() * p.norm());
		return acos(abs(d) > 1 ? signum(d) : d);
	}

	P unit(double d) {
		return div(d / norm());
	}

	int contained(P r1, P r2) {
		if (equals(r1) || equals(r2))
			return 0;
		if (eq(r1.dist(r2), dist(r1) + dist(r2)))
			return 1;
		return -1;
	}

	int ccw(P b, P c) {
		b = sub(b);
		c = sub(c);
		if (b.det(c) > EPS)
			return 1;
		if (b.det(c) < EPS)
			return -1;
		if (b.dot(c) < EPS)
			return 2;
		if (b.norm() + EPS < c.norm())
			return -2;
		return 0;
	}

	//
	public int compareTo(P o) {
		return sgn(x - o.x) != 0 ? sgn(x - o.x) : sgn(y - o.y);
	}

	public boolean equals(Object o) {
		return compareTo((P) o) == 0;
	}

	public int hashCode() {
		throw new UnsupportedOperationException();
	}

	public String toString() {
		return x + " " + y;
	}

	Point2D toPoint2D() {
		return new Point2D.Double(x, y);
	}
}
package com.github.hiroshi_cl.wakaba.v2009.lib.geometryReal;

import static java.lang.Math.atan2;
import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static java.lang.Math.sqrt;

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

	P unit() {
		return div(1 / norm());
	}

	static private int signum(double d) {
		return d < -EPS ? -1 : d > EPS ? 1 : 0;
	}

	public int compareTo(P o) {
		return signum(x - o.x) != 0 ? signum(x - o.x) : signum(y - o.y);
	}

	public boolean equals(Object o) {
		return compareTo((P) o) == 0;
	}

	public int hashCode() {
		return new Double(x).hashCode() * 0x0000f000 + new Double(y).hashCode();
	}

	public String toString() {
		return x + " " + y;
	}
}

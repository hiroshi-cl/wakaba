package com.github.hiroshi_cl.wakaba.v2009.lib.geometryInt;
import static java.lang.Math.*;

class Line {
	class Point {
		double x, y;

		Point(double a, double b) {
			x = a;
			y = b;
		}

		Point subtract(Point o) {
			return new Point(x - o.x, y - o.y);
		}

		double norm() {
			return sqrt(x * x + y * y);
		}
	}

	Point p;
	double arg;

	Line(Point p, double arg) {
		this.p = p;
		this.arg = cos(acos(arg)); // arg <- [0..PI]
	}

	Point cross(Line o) {
		if (eq(o.arg, arg))
			return null;
		return new Point((-sin(o.arg) * (o.p.x - p.x) + cos(o.arg)
				* (o.p.y - p.y))
				/ sin(arg - o.arg), (-sin(arg) * (o.p.x - p.x) + cos(arg)
				* (o.p.y - p.y))
				/ sin(arg - o.arg));
	}

	Line parallel(Point q) {
		return new Line(q, this.arg);
	}

	double distance(Point p) {
		Line l = new Line(p, arg + PI / 2);
		return this.cross(l).subtract(p).norm();
	}

	Point atX(double x) {
		if (eq(arg, PI / 2) && eq(p.x, x))
			return null;
		return new Point(x, p.y + (x - p.x) * tan(arg));
	}

	Point atY(double y) {
		if (eq(arg, 0) && eq(p.y, y))
			return null;
		if (eq(arg, PI) && eq(p.y, y))
			return null;
		return new Point(p.x + (y - p.y) * cos(arg) / sin(arg), y);
	}

	Point cut(double r) {
		return new Point(p.x + r * cos(arg), p.y + r * sin(arg));
	}

	boolean contains(Point q) {
		double argpq = atan2(p.y - q.y, p.x - q.x);
		if (argpq >= PI)
			argpq -= PI;
		return eq(arg, argpq);
	}

	final double EPS = 0.0000001;

	boolean eq(double x, double y) {
		return x - EPS < y && y < x + EPS;
	}
}

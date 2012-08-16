package com.github.hiroshi_cl.wakaba.v2009.lib.geometryInt;

import static java.lang.Math.*;

public class Vector {
	final double x;
	final double y;

	Vector(double X, double Y) {
		x = X;
		y = Y;
	}

	double norm() {
		return hypot(x, y);
	}

	Vector sub(Vector v) {
		return new Vector(x - v.x, y - v.y);
	}

	Vector add(Vector v) {
		return new Vector(x + v.x, y + v.y);
	}

	Vector scale(double d) {
		return new Vector(d * x, d * y);
	}

	double met(Vector v) {
		return x * v.x + y * v.y;
	}

	double det(Vector v) {
		return x * v.y - y * v.x;
	}

	double arg() {
		return atan2(x, y);
	}
}

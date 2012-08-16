package com.github.hiroshi_cl.wakaba.v2009.lib.geometryInt;
import static java.lang.Math.*;
class Point implements Comparable<Point> {
	int x, y;

	public Point(int x, int y) {
		this.x = x;
		this.y = y;
	}

	int cross(Point p) {
		return x * p.y - y * p.x;
	}

	int dot(Point p) {
		return x * p.x + y * p.y;
	}

	double norm() {
		return hypot(x, y);
	}

	Point subtract(Point p) {
		return new Point(x - p.x, y - p.y);
	}

	Point add(Point p) {
		return new Point(x + p.x, y + p.y);
	}

	public int compareTo(Point o) {
		if (x != o.x)
			return x - o.x;
		return y - o.y;
	}
}

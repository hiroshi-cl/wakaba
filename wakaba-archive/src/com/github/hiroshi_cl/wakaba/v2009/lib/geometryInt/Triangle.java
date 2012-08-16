package com.github.hiroshi_cl.wakaba.v2009.lib.geometryInt;

import static java.lang.Math.*;

class Triangle {
	class Vec {
		double x, y;

		Vec(double a, double b) {
			x = a;
			y = b;
		}

		Vec add(Vec o) {
			return new Vec(x + o.x, y + o.y);
		}

		Vec sub(Vec o) {
			return new Vec(x - o.x, y - o.y);
		}

		Vec mul(double a) {
			return new Vec(x * a, y * a);
		}

		Vec div(double a) {
			return new Vec(x / a, y / a);
		}

		double norm() {
			return sqrt(x * x + y * y);
		}
	}

	Vec A, B, C;

	Triangle(Vec x, Vec y, Vec z) {
		A = x;
		B = y;
		C = z;
	}

	Triangle rotate() {
		return new Triangle(B, C, A);
	}

	double a() {
		return B.sub(C).norm();
	}

	double b() {
		return C.sub(A).norm();
	}

	double c() {
		return A.sub(B).norm();
	}

	double area() {
		// ヘロンの公式
		double s = (a() + b() + c()) / 2.0;
		return sqrt(s * (s - a()) * (s - b()) * (s - c()));
	}

	// 余弦定理 cosA = b^2+c^2-a^2
	double get_cos(double a, double b, double c) {
		return (b * b + c * c - a * a) / (2 * b * c);
	}

	double angleA() {
		return acos(get_cos(a(), b(), c()));
	}

	double angleB() {
		return acos(get_cos(b(), c(), a()));
	}

	double angleC() {
		return acos(get_cos(c(), a(), b()));
	}

	Vec gravity() { // 重心
		// (A+B+C)/3
		return A.add(B).add(C).mul(1.0 / 3.0);
	}

	Vec incentre() { // 内心
		// (aA+bB+cC)/(a+b+c)
		return (A.mul(a()).add(B.mul(b())).add(C.mul(c())))
				.div(a() + b() + c());
	}

	Vec excentre() { // 傍心
		// (-aA+bB+cC/(-a+b+c))
		return (A.mul(-a()).add(B.mul(b())).add(C.mul(c()))).div(-a() + b()
				+ c());
	}

	Vec outer() { // 外心
		// (a sin2A+b sin2B+c sin2C)/(sin2A+sin2B+sin2C)
		double aa = angleA();
		double bb = angleB();
		double cc = angleC();
		return A.mul(sin(2 * aa)).add(B.mul(sin(2 * bb)))
				.add(C.mul(sin(2 * cc)))
				.div(sin(2 * aa) + sin(2 * bb) + sin(2 * cc));
	}

	Vec ortho() { // 垂心
		// (a tanA+b tanB+c tanC)/(tanA+tanB+tanC)
		double aa = angleA();
		double bb = angleB();
		double cc = angleC();
		return A.mul(tan(aa)).add(B.mul(tan(bb))).add(C.mul(tan(cc)))
				.div(tan(aa) + tan(bb) + tan(cc));
	}
}

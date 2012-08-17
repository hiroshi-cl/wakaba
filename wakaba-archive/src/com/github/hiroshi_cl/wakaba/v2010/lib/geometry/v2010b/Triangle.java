package com.github.hiroshi_cl.wakaba.v2010.lib.geometry.v2010b;

import static java.lang.Math.*;
import java.awt.Shape;
import java.awt.geom.*;

/**
 * cosA = (b^2 + c^2 - a^2) / 2ab. a/sinA = b/sinB = c/sinC = 2R (Rは外接円の半径).
 */
public class Triangle {
	final P A, B, C;

	public Triangle(P a, P b, P c) {
		A = a;
		B = b;
		C = c;
	}

	Triangle rotate() {
		return new Triangle(B, C, A);
	}

	// 辺の長さ
	double a() {
		return B.sub(C).norm();
	}

	double b() {
		return C.sub(A).norm();
	}

	double c() {
		return A.sub(B).norm();
	}

	// ヘロンの公式
	double area() {
		double s = (a() + b() + c()) * .5;
		return sqrt(s * (s - a()) * (s - b()) * (s - c()));
	}

	// 余弦定理 cosA = b^2+c^2-a^2
	double cos(double a, double b, double c) {
		return (b * b + c * c - a * a) / (2 * b * c);
	}

	// 角度
	double angleA() {
		return acos(cos(a(), b(), c()));
	}

	double angleB() {
		return acos(cos(b(), c(), a()));
	}

	double angleC() {
		return acos(cos(c(), a(), b()));
	}

	// 重心 (A+B+C)/3
	P gravity() {
		return wmean(1, 1, 1);
	}

	// 内心 (aA+bB+cC)/(a+b+c)
	P incentre() {
		return wmean(a(), b(), c());
	}

	// 傍心 (-aA+bB+cC/(-a+b+c)) - 全列挙にはrotateする
	P excentre() {
		return wmean(-a(), b(), c());
	}

	// 外心 (A sin2A+B sin2B+C sin2C)/(sin2A+sin2B+sin2C)
	P outer() {
		return wmean(sin(2 * angleA()), sin(2 * angleB()), sin(2 * angleC()));
	}

	// 垂心 (A tanA+B tanB+C tanC)/(tanA+tanB+tanC)
	P ortho() {
		return wmean(tan(angleA()), tan(angleB()), tan(angleC()));
	}

	private P wmean(double a, double b, double c) {
		return A.mul(a).add(B.mul(b)).add(C.mul(c)).div(a + b + c);
	}

	Shape toShape() {
		Path2D.Double ret = new Path2D.Double();
		ret.moveTo(A.x, A.y);
		ret.lineTo(B.x, B.y);
		ret.lineTo(C.x, C.y);
		ret.lineTo(A.x, A.y);
		return ret;
	}
}

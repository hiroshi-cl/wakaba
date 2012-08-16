package com.github.hiroshi_cl.wakaba.v2009.lib.geometryReal;
import static java.lang.Math.sqrt;
class Methods {
	static final double EPS = 1e-9;
	/*
	 * オイラーの四面体公式。体積を求める。 a = BC b = CA c = AB d = OA e = OB f = OC とする。
	 */
	// TODO check
	double tetrahedron(final double a, final double b, final double c, final double d,
	final double e, final double f) {
		final double a2 = a * a, b2 = b * b, c2 = c * c, d2 = d * d, e2 = e * e, f2 = f * f;
		return sqrt(a2 * d2 * (b2 + c2 + e2 + f2 - a2 - d2) + b2 * e2
		* (c2 + a2 + f2 + d2 - b2 - e2) + c2 * f2 * (a2 + b2 + d2 + e2 - c2 - f2) - a2 * b2 * c2
		- f2 * e2 * a2 - d2 * f2 * b2 - d2 * c2 * e2) / 12;
	}
	// TODO check
	double tetrahedron(final P O, final P A, final P B, final P C) {// オイラーの四面体公式
		final double a = C.sub(B).norm(), b = A.sub(C).norm(), c = B.sub(A).norm(), d =
		A.sub(O).norm(), e = B.sub(O).norm(), f = C.sub(O).norm();
		return tetrahedron(a, b, c, d, e, f);
	}
}

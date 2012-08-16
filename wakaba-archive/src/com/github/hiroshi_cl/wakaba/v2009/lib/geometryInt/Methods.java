package com.github.hiroshi_cl.wakaba.v2009.lib.geometryInt;

public class Methods {
	int triangle2_16(int a, int b, int c) {// 誤差回避のために、面積の２乗の１６倍を返す。
		return (a + b + c) * (a + b - c) * (a - b + c) * (-a + b + c);
	}
	/*
	 * オイラーの四面体公式
	 * a = BC
	 * b = CA
	 * c = AB
	 * d = OA
	 * e = OB
	 * f = OC
	 * とする。
	 */
	int tetrahedron2_144(int a, int b, int c, int d, int e, int f) {// 誤差回避のため、体積の２乗の１４４倍を返す。
		int a2 = a * a, b2 = b * b, c2 = c * c, d2 = d * d, e2 = e * e, f2 = f
				* f;
		return a2 * d2 * (b2 + c2 + e2 + f2 - a2 - d2) + b2 * e2
				* (c2 + a2 + f2 + d2 - b2 - e2) + c2 * f2
				* (a2 + b2 + d2 + e2 - c2 - f2) - a2 * b2 * c2 - f2 * e2 * a2
				- d2 * f2 * b2 - d2 * c2 * e2;
	}

	boolean validTriangle(int a, int b, int c) {
		return a + b >= c && b + c >= a && c + a >= b;
	}

	boolean validTetrahedron(int a, int b, int c, int d, int e, int f) {// verified:TC
																		// srm355
																		// 600
		return tetrahedron2_144(a, b, c, d, e, f) >= 0;
	}
}

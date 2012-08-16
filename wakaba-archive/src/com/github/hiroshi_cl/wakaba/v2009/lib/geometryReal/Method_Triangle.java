package com.github.hiroshi_cl.wakaba.v2009.lib.geometryReal;

/*
 * 三角形に関するメソッド集
 */
public class Method_Triangle {
	// TODO check
	static P barycenter(P A, P B, P C) {// 重心
		return A.add(B).add(C).div(3);
	}

	// TODO check
	static P incenter(P A, P B, P C) {// 内心
		double a = A.norm(), b = B.norm(), c = C.norm();
		return A.mul(a).add(B.mul(b)).add(C.mul(c)).div(a + b + c);
	}

	// verified pku2957.
	static P circumcenter(P A, P B, P C) {// 外心
		P AB = B.sub(A), BC = C.sub(B), CA = A.sub(C);
		return (A.add(B).sub(AB.rot90().mul(BC.dot(CA) / AB.det(BC)))).div(2);
	}

	// TODO check
	static P orthocenter(P A, P B, P C) {// 垂心
		return barycenter(A, B, C).mul(3).sub(circumcenter(A, B, C).mul(2));
	}

	// TODO check
	static P excenter(P A, P B, P C) { // Aの逆側の傍心
		double a = A.norm(), b = B.norm(), c = C.norm();
		return A.mul(-a).add(B.mul(b).add(C.mul(c))).div(-a + b + c);
	}
}

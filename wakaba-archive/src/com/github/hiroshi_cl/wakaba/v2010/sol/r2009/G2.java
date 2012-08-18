package com.github.hiroshi_cl.wakaba.v2010.sol.r2009;
import java.util.*;
import java.math.*;
import java.io.*;
import static java.util.Arrays.*;
import static java.util.Collections.*;
import static java.lang.Math.*;
import static java.math.BigInteger.*;

public class G2 {
	void debug(Object... os) {
//		System.err.println(deepToString(os));
	}

	void run() {
		Scanner sc = new Scanner(System.in);
		try {
			sc = new Scanner(new File("G.in"));
		} catch (Exception e) {
		}
		for (;;) {

			Ta = new P(sc.nextDouble(), sc.nextDouble());
			Tb = new P(sc.nextDouble(), sc.nextDouble());
			Tc = new P(sc.nextDouble(), sc.nextDouble());
			if (Ta.equals(ZERO) && Tb.equals(ZERO) && Tc.equals(ZERO))
				return;
			ex = excenter(Ta, Tb, Tc);
			double high = Ta.sub(ex).norm(), low = 0;
			debug(ex);
			debug(high);
			ra = 0;
			rb = 0;
			rc = 0;
			do {
				double da = (high + low) / 2;
				P halfA = Tb.sub(Ta).unit().add(Tc.sub(Ta).unit()).unit();
//				debug(halfA);
				if (big(halfA, da)) {
					low = da;
				} else {
					high = da;
				}

			} while (high - low > EPS);
			System.out.println(ra+" "+rb+" "+rc);
		}
	}

	P Ta, Tb, Tc;
	P ex;

	double ra, rb, rc;

	boolean big(P hA, double da) {
		P A = Ta.add(hA.mul(da));
		ra = distLP(A, Ta, Tb);
		P halfB = Tc.sub(Tb).unit().add(Ta.sub(Tb).unit()).unit();
		double high = ex.sub(Tb).norm(), low = 0;
		P B = null;
		rb = -1;
		do {
			double db = (high + low) / 2;
			B = Tb.add(halfB.mul(db));
			rb = distLP(B, Tb, Ta);
			if (rb + ra > B.sub(A).norm()) {
				high = db;
			} else {
				low = db;
			}
		} while (high - low > EPS);
		rc = -1;
		P halfC = Ta.sub(Tc).unit().add(Tb.sub(Tc).unit()).unit();
		high = ex.sub(Tc).norm();
		low = 0;
		P C = null;
		do {
			double dc = (high + low) / 2;
			C = Tc.add(halfC.mul(dc));
			rc = distLP(C, Tc, Ta);
			if (rc + ra > C.sub(A).norm()) {
				high = dc;
			} else {
				low = dc;
			}
		} while (high - low > EPS);

		if (rb + rc > B.sub(C).norm())
			return true;
		return false;
	}

	int HIGH;

	double distLP(P p, P p1, P p2) {
		P p1p2 = p2.sub(p1), p1p = p.sub(p1);
		return abs(p1p2.det(p1p) / p1p2.norm());
	}

	P excenter(P A, P B, P C) {
//		P AB = B.sub(A), BC = C.sub(B), CA = A.sub(C);
//		return A.add(B).sub(AB.rot90().mul(BC.dot(CA) / AB.det(BC))).div(2);
		double a = B.sub(C).norm(),b=C.sub(A).norm(),c=A.sub(B).norm();
		return A.mul(a).add(B.mul(b)).add(C.mul(c)).div(a+b+c);
	}

	P ZERO = new P(0, 0);
	double EPS = 1e-7;

	boolean eq(double a, double b) {
		return abs(a - b) < EPS;
	}

	boolean lt(double a, double b) {
		return a < b - EPS;
	}

	boolean le(double a, double b) {
		return a < b + EPS;
	}

	class P {
		@Override
		public String toString() {
			return x + " " + y;
		}

		double dot(P p) {
			return x * p.x + y * p.y;
		}

		double x, y;
		boolean white;

		P(double x, double y) {
			this.x = x;
			this.y = y;
		}

		boolean equals(P p) {
			return eq(x, p.x) && eq(y, p.y);
		}

		double det(P p) {
			return x * p.y - y * p.x;
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

		double norm() {
			return hypot(x, y);
		}

		P unit() {
			return div(norm());
		}

		P div(double d) {
			return new P(x / d, y / d);
		}

		P rot90() {
			return new P(-y, x);
		}
	}

	public static void main(String[] args) {
		new G2().run();
	}
}

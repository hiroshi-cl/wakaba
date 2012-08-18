package com.github.hiroshi_cl.wakaba.v2010.sol.r2009;
import java.util.*;
import java.math.*;
import java.io.*;
import static java.util.Arrays.*;
import static java.util.Collections.*;
import static java.lang.Math.*;
import static java.math.BigInteger.*;

public class D {
	void debug(Object... os) {
//		System.err.println(deepToString(os));
	}

	void run() {
		Scanner sc = new Scanner(System.in);
		try {
			sc = new Scanner(new File("D.in"));
		} catch (Exception e) {
		}
		for (;;) {
			int n = sc.nextInt(), m = sc.nextInt();
			if ((n | m) == 0)
				return;
			P[] ps = new P[n + m];
			for (int i = 0; i < n; i++) {
				ps[i] = new P(sc.nextDouble(), sc.nextDouble(), true);
			}
			for (int i = 0; i < m; i++) {
				ps[n + i] = new P(sc.nextDouble(), sc.nextDouble(), false);
			}
			boolean ok = false;
			loop: for (int i = 0; i < ps.length; i++) {
				for (int j = 0; j < ps.length; j++)
					if (i != j) {
						P d = ps[i].sub(ps[j]).unit().rot90().mul(EPS * 100);
						P np = ps[i].add(d);
						P nr = ps[j].add(d);
						if (can(ps, np, nr)) {
							ok = true;
							break loop;
						}
						np = ps[i].add(d);
						nr = ps[j].sub(d);
						if (can(ps, np, nr)) {
							ok = true;
							break loop;
						}
					}
			}
			System.out.println(ok ? "YES" : "NO");
		}
	}

	boolean can(P[] ps, P p, P r) {
		int lw=0, rw=0, lb=0, rb=0;
		for (int i = 0; i < ps.length; i++) {
			double det = r.sub(p).det(ps[i].sub(p));
			if (det > EPS) {
				if (ps[i].white)
					lw++;
				else
					lb++;
			} else if (det < EPS) {
				if (ps[i].white)
					rw++;
				else
					rb++;
			} else{
				debug("!");
				return false;
			}
		}
		debug(lw,lb,rw,rb);
		boolean can = (lw == 0 && rb == 0) || (rw == 0 && lb == 0);
		debug(can);
		return can;
	}
	double EPS = 1e-9;

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
		double x, y;
		boolean white;

		P(double x, double y, boolean white) {
			this.x = x;
			this.y = y;
			this.white = white;
		}

		double det(P p) {
			return x * p.y - y * p.x;
		}

		P add(P p) {
			return new P(x + p.x, y + p.y, white);
		}

		P sub(P p) {
			return new P(x - p.x, y - p.y, white);
		}

		P mul(double d) {
			return new P(x * d, y * d, white);
		}

		double norm() {
			return hypot(x, y);
		}

		P unit() {
			return div(norm());
		}

		P div(double d) {
			return new P(x / d, y / d, white);
		}

		P rot90() {
			return new P(-y, x, white);
		}
	}

	public static void main(String[] args) {
		new D().run();
	}
}

package com.github.hiroshi_cl.wakaba.v2010.lib.numeric;

import java.util.*; // for debug
import static java.lang.Math.*;

/*
 - DKA
 - Durand-Kerner-Aberth
 - 代数方程式

 Aberthの方法で初期値を設定し、Durand-Kerner法で反復を行い、代数方程式の解を1度に求める。
 面倒だったので、初期値の計算に使うr_0の計算を省略して、適当な大きな数に設定している。
 引数は、(monicな多項式)=0の形にして、最高次の係数を省略して、降冪の順に係数を与える。
 r_0の設定にもよるが、30-150次より大きい方程式は解けない実装になっている。
 */

public class DKA {

	static final double r0 = 1e10;
	static final double EPS = 1e-9;
	static final double DEL = 1e-10;

	public C[] dka(double... a) { // monic
		int n = a.length;
		C[] z = aberth(a, n);
		boolean[] f = new boolean[n];
		loop: while (true) {
			// debug(z);
			z = dk(a, z, n, f);
			for (int i = 0; i < n; i++)
				if (!f[i])
					continue loop;
			break;
		}
		return z;
	}

	private C[] aberth(double[] a, int n) {
		C[] z0 = new C[n];
		C a0 = new C(-a[0] / n, 0);
		double d = PI / (2 * n);
		for (int i = 0; i < n; i++)
			z0[i] = a0.add(C.polar(r0, (4 * i + 1) * d)); // r0 ga tenuki
		return z0;
	}

	private C[] dk(double[] a, C[] z, int n, boolean[] f) {
		C[] zn = new C[n];

		for (int i = 0; i < n; i++)
			if (!f[i]) {
				C p = C.Z;
				{
					C zp = C.I;
					for (int j = 0; j < n; j++, zp = zp.mul(z[i]))
						p = p.add(zp.scale(a[n - j - 1]));
					p = p.add(zp);
				}
				C d = C.I;
				{
					for (int j = 0; j < n; j++)
						if (i != j)
							d = d.mul(z[i].sub(z[j]));
				}
				zn[i] = z[i].sub(p.div(d));
				if (z[i].sub(zn[i]).abs() < EPS)
					f[i] = true;
			} else
				zn[i] = z[i];

		for (int i = 0; i < n; i++)
			for (int j = i + 1; j < n; j++)
				if (zn[i].sub(zn[j]).abs() < DEL)
					zn[j] = zn[i].add(new C(DEL, 0));

		return zn;
	}

	public static class C {
		final double x, y;
		static final C I = new C(1, 0);
		static final C Z = new C(0, 0);

		public C(double x, double y) {
			this.x = x;
			this.y = y;
		}

		public static C polar(double r, double t) {
			return new C(r * cos(t), r * sin(t));
		}

		public C add(C c) {
			return new C(x + c.x, y + c.y);
		}

		public C sub(C c) {
			return new C(x - c.x, y - c.y);
		}

		public C mul(C c) {
			return new C(x * c.x - y * c.y, x * c.y + y * c.x);
		}

		public double abs2() {
			return x * x + y * y;
		}

		public double abs() {
			return sqrt(abs2());
		};

		public C div(C c) {
			double d = 1. / c.abs2();
			return new C((x * c.x + y * c.y) * d, (-x * c.y + y * c.x) * d);
		}

		public C scale(double d) {
			return new C(d * x, d * y);
		}

		@Override
		public String toString() {
			return String.format("%.1f + %.1fi", x, y);
		}
	}

	void debug(Object... os) {
		System.out.println(Arrays.deepToString(os));
	}

	public static void main(String... agrs) {
		new DKA().run();
	}

	public void run() {
		debug(dka(2, 1));
		debug(dka(1, 1));
		debug(dka(-10, 43, -104, 150, -100));
	}
}

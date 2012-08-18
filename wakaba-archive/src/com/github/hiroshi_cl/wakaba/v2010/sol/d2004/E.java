package com.github.hiroshi_cl.wakaba.v2010.sol.d2004;

import java.util.*;
import static java.util.Arrays.*;
import static java.lang.Math.*;

public class E {
	public static void main(String[] args) {
		new E().run();
	}

	static void debug(Object... o) {
		System.err.println(deepToString(o));
	}

	int INF = 1 << 20;
	double EPS = 1e-9;
	Tank[] tanks;

	void run() {
		Scanner sc = new Scanner(System.in);
		int o = sc.nextInt();
		while (o-- > 0) {
			int n = sc.nextInt();
			tanks = new Tank[n + 2];
			int[] bs = new int[n + 3], hs = new int[n + 3];
			bs[0] = 0;
			bs[n + 1] = 100;
			bs[n + 2] = INF;
			hs[0] = INF;
			hs[n + 1] = 50;
			hs[n + 2] = 100;
			for (int i = 1; i <= n; i++) {
				bs[i] = sc.nextInt();
				hs[i] = sc.nextInt();
			}
			int m = sc.nextInt();
			double[] ffs = new double[m], as = new double[m];
			int[] fs = new int[m];
			for (int i = 0; i < m; i++) {
				ffs[i] = sc.nextDouble();
				for (int j = 0; j <= n + 1; j++) {
					if (ffs[i] < bs[j]) {
						fs[i] = j - 1;
						break;
					}
				}
				as[i] = sc.nextDouble() / 30;
			}
			int l = sc.nextInt();
			for (int oo = 0; oo < l; oo++) {
				double p = sc.nextDouble(), T = sc.nextDouble();
				int P = 0;
				for (int i = 0; i <= n + 1; i++) {
					if (p < bs[i]) {
						P = i - 1;
						break;
					}
				}
				for (int i = 0; i <= n + 1; i++) {
					tanks[i] = new Tank(hs[i], hs[i + 1], bs[i + 1] - bs[i]);
				}
				for (int i = 0; i < m; i++) {
					tanks[fs[i]].flux += as[i];
				}
				// showTanks();
				double t = 0;
				while (signum(T - t) > 0) {
					double dt = T - t;
					for (int i = 0; i <= n; i++) {
						Tank tank = tanks[i];
						if (tank.flux == 0)
							continue;
						dt = min(dt,
								(tank.minh * tank.size - tank.volumeOfWater)
										/ tank.flux);
					}
					// debug(t,dt);
					flow(dt);
					// showTanks();
					t += dt;
				}
				// showTanks();
				System.out.printf("%.3f%n", tanks[P].volumeOfWater
						/ tanks[P].size);
			}
		}
	}

	void showTanks() {
		int n = tanks.length;
		for (int i = 0; i < n; i++) {
			debug(tanks[i].minh * tanks[i].size, tanks[i].volumeOfWater,
					tanks[i].flux, tanks[i].size);
		}
		System.err.println();
	}

	void mergeTank(int i, int j) {
		tanks[i].merge(tanks[j]);
		int n = tanks.length;
		for (int k = 0; k < n; k++) {
			if (k != j)
				if (tanks[k] == tanks[j]) {
					tanks[k] = tanks[i];
				}
		}
		tanks[j] = tanks[i];
		// System.err.println("tank "+i+" and "+j+" were merged.");
	}

	boolean flow(double t) {
		int m = tanks.length;
		for (int i = 0; i < m; i++) {
			if (i > 0)
				if (tanks[i - 1] == tanks[i])
					continue;
			Tank tank = tanks[i];
			tank.volumeOfWater += tank.flux * t;
		}
		for (int i = 0; i < m; i++) {
			if (i > 0)
				if (tanks[i - 1] == tanks[i])
					continue;
			if (tanks[i].flux > 0 && tanks[i].isFull()) {
				changeCondition(i);
			}
		}
		return false;
	}

	void changeCondition(int i) {
		Tank tank = tanks[i];
		if (tank.lh < tank.rh) {
			int id = i - 1;
			for (int j = i - 1; j >= 0; j--) {
				if (tanks[j] != tank) {
					id = j;
					break;
				}
			}
			if (tanks[id].isFull()
					&& signum(tanks[id].volumeOfWater - tanks[id].size
							* tanks[id].rh) == 0) {
				mergeTank(i, id);
			} else {
				tanks[id].flux += tank.flux;
				tank.flux = 0;
			}
		} else {
			int id = i + 1;
			for (int j = i + 1; j < tanks.length; j++) {
				if (tanks[j] != tank) {
					id = j;
					break;
				}
			}
			if (tanks[id].isFull()
					&& signum(tanks[id].volumeOfWater - tanks[id].size
							* tanks[id].lh) == 0) {
				mergeTank(i, id);
			} else {
				tanks[id].flux += tank.flux;
				tank.flux = 0;
			}
		}
	}

	int signum(double d) {
		return d < -EPS ? -1 : d > EPS ? 1 : 0;
	}

	class Tank {
		boolean isFull() {
			if (signum(volumeOfWater - size * minh) == 0)
				return true;
			return false;
		}

		double lh, rh, minh;
		double size, volumeOfWater = 0, flux = 0;

		Tank(int lh, int rh, double size) {
			this.lh = lh;
			this.rh = rh;
			this.size = size;
			minh = min(lh, rh);
		}

		void merge(Tank t) {
			size += t.size;
			volumeOfWater += t.volumeOfWater;
			flux += t.flux;
			if (t.lh == rh && rh != 50) {
				rh = t.rh;
			} else {
				lh = t.lh;
			}
			minh = min(lh, rh);
		}
	}
}
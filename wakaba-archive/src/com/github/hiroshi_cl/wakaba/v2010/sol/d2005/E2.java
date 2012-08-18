package com.github.hiroshi_cl.wakaba.v2010.sol.d2005;

import java.io.*;
import java.util.*;
import java.math.*;
import static java.lang.Math.*;
import static java.math.BigInteger.*;
import static java.util.Arrays.*;
import static java.util.Collections.*;

public class E2 {
	public static void main(String[] args) {
		Scanner sc = null;
		try {
			sc = new Scanner(new File("E.in"));
			System.setOut(new PrintStream("E.out"));
		} catch (Exception e) {
		}
		new E2().run(sc);
	}

	static void debug(Object... os) {
		// System.err.println(deepToString(os));
	}

	static int INF = 1 << 27;
	static double EPS = 1e-9;

	static int signum(double d) {
		return d < -EPS ? -1 : d > EPS ? 1 : 0;
	}

	static boolean eq(double a, double b) {
		return signum(a - b) == 0;
	}

	int R;

	void run(Scanner sc) {
		for (;;) {
			int n = sc.nextInt(), t = sc.nextInt(), r = sc.nextInt();
			if (n + t + r == 0)
				return;
			R = r;
			robotinfo = new RobotInfo[n];
			robots = new Robot[n];
			for (int i = 0; i < n; i++) {
				robotinfo[i] = new RobotInfo(sc, t);
				robots[i] = new Robot(robotinfo[i]);
			}
			ArrayList<Integer> times_int = new ArrayList<Integer>();
			for (int i = 0; i < n; i++) {
				for (int j = 0; j < robotinfo[i].k; j++) {
					times_int.add(robotinfo[i].ts[j]);
				}
			}
			sort(times_int);
			debug(times_int);
			times_double = new TreeSet<Double>();
			for (int i = 0; i < times_int.size(); i++) {
				int time = times_int.get(i);
				if (i > 0 && time == (times_int.get(i - 1)))
					continue;
				times_double.add((double) time);
				simu(time);
			}
			for (int i = 0; i < n; i++) {
				robots[i] = new Robot(robotinfo[i]);
			}
			robots[0].hasinfo = true;
			for (double ct : times_double) {
				for (int j = 0; j < n; j++)
					robots[j].simu(ct);
				boolean[][] bss = new boolean[n][n];
				for (int l = 0; l < n; l++) {
					// if (robots[l].hasinfo) {
					for (int m = 0; m < n; m++) {
						if (robots[l].intersect(robots[m])) {
							// robots[m].hasinfo = true;
							bss[l][m] = true;
						}
					}
					// }
				}
				for (int k = 0; k < n; k++) {
					for (int l = 0; l < n; l++) {
						for (int m = 0; m < n; m++) {
							bss[l][m] |= bss[l][k] & bss[k][m];
						}
					}
				}
				for (int k = 0; k < n; k++) {
					for (int l = 0; l < n; l++) {
						if (robots[k].hasinfo && bss[k][l])
							robots[l].hasinfo = true;
					}
				}
			}
			ArrayList<String> ns = new ArrayList<String>();
			for (int i = 0; i < n; i++) {
				if (robots[i].hasinfo)
					ns.add(robots[i].info.nickname);
			}
			sort(ns);
			for (String s : ns)
				System.out.println(s);
		}
	}

	RobotInfo[] robotinfo;
	Robot[] robots;
	TreeSet<Double> times_double;

	void simu(double t) {
		int n = robots.length;
		for (int i = 0; i < n; i++) {
			for (int j = i + 1; j < n; j++) {
				double intersect = robots[i].t
						+ intersect(robots[i], robots[j], t - robots[i].t);
				if (intersect >= 0)
					times_double.add(intersect);
			}
		}
		for (int i = 0; i < n; i++) {
			robots[i].simu(t);
		}
	}

	double intersect(Robot p, Robot q, double interval) {
		double a = pow((p.vx - q.vx), 2) + pow((p.vy - q.vy), 2);
		double b = (p.x - q.x) * (p.vx - q.vx) + (p.y - q.y) * (p.vy - q.vy);
		double c = pow(p.x - q.x, 2) + pow(p.y - q.y, 2) - R * R;
		double d = b * b - a * c;
		debug(a, b, p.x, p.y);
		if (d <= 0)
			return -1;
		double t1 = (-b - sqrt(d)) / a;
		double t2 = (-b + sqrt(d)) / a;
		debug(t1, t2);
		if (0 <= t1 && t1 <= interval)
			return t1;
		if (0 <= t2 && t2 <= interval)
			return t2;
		return -1;
	}

	class Robot {
		RobotInfo info;
		int k;
		double x, y;
		double vx, vy;
		double t;
		boolean hasinfo;

		Robot(RobotInfo info) {
			x = info.nx;
			y = info.ny;
			t = 0;
			this.k = info.k;
			this.vx = info.nvx;
			this.vy = info.nvy;
			this.info = info;
		}

		void simu(double after) {
			for (int i = 1; i < k; i++) {
				if (t < info.ts[i]) {
					x += info.vxs[i] * (after - t);
					y += info.vys[i] * (after - t);
					if (after < info.ts[i])
						;
					else {
						vx = info.vxs[i + 1];
						vy = info.vys[i + 1];
					}
					t = after;
					break;
				}
			}
		}

		boolean intersect(Robot o) {
			return (x - o.x) * (x - o.x) + (y - o.y) * (y - o.y) < R * R
					+ 0.00000001;
		}
	}

	class RobotInfo {
		String nickname;
		int nx, ny;
		int nvx, nvy;
		int[] ts;
		int[] vxs;
		int[] vys;
		int k = 1;

		RobotInfo(Scanner sc, int t) {
			nickname = sc.next();
			ts[0] = sc.nextInt();
			nx = sc.nextInt();
			ny = sc.nextInt();
			for (;;) {
				ts[k] = sc.nextInt();
				vxs[k] = sc.nextInt();
				vys[k] = sc.nextInt();
				if (ts[k++] == t)
					break;
			}
			nvx = vxs[1];
			nvy = vys[1];
		}
	}
}
package com.github.hiroshi_cl.wakaba.v2010.sol.d2005;

import java.util.*;
import java.io.*;
import java.math.*;
import static java.util.Arrays.*;
import static java.util.Collections.*;
import static java.lang.Math.*;

public class E {
	public static void main(String[] args) throws Exception {
		try {
			System.setIn(new FileInputStream(new File("E.in")));
			System.setOut(new PrintStream("E.out"));
		} catch (Exception e) {
		}
		new E().run();
	}

	static void debug(Object... o) {
		System.err.println(deepToString(o));
	}

	int nextInt() {
		int res = 0;
		int c = 0;
		do {
			try {
				c = System.in.read();
				if (c == -1)
					return -1;
				if (c == '-')
					return -nextInt();
			} catch (Exception e) {
			}
		} while (c < '0' || c > '9');
		do {
			res *= 10;
			res += c - '0';
			try {
				c = System.in.read();
			} catch (Exception e) {
			}
		} while ('0' <= c && c <= '9');
		return res;
	}

	int MAXLENGTH = 9;

	String next() {
		char[] cs = new char[MAXLENGTH];
		int c = 0;
		do {
			try {
				c = System.in.read();
				if (c == -1)
					return null;
			} catch (Exception e) {
			}
		} while (Character.isWhitespace(c));
		int i = 0;
		do {
			cs[i++] = (char) c;
			try {
				c = System.in.read();
			} catch (Exception e) {
			}
		} while (!Character.isWhitespace(c));
		return new String(cs, 0, i);
	}

	int INF = 1 << 29;
	double EPS = 1e-9;
	int R, T;

	void run() {
		for (;;) {
			int n = nextInt();
			T = nextInt();
			R = nextInt();
			if (n + T + R == 0)
				return;
			long ct = System.currentTimeMillis();

			Robot[] robots = new Robot[n];
			for (int i = 0; i < n; i++) {
				robots[i] = new Robot(i, next());
				int t = nextInt();// 0
				robots[i].pxs[0] = nextInt();
				robots[i].pys[0] = nextInt();
				for (;;) {
					int nt = nextInt();
					int nvx = nextInt(), nvy = nextInt();
					for (; t < nt; t++) {
						robots[i].vxs[t] = nvx;
						robots[i].vys[t] = nvy;
						robots[i].pxs[t + 1] = robots[i].pxs[t]
								+ robots[i].vxs[t];
						robots[i].pys[t + 1] = robots[i].pys[t]
								+ robots[i].vys[t];
					}
					if (nt == T)
						break;
				}
			}
			ArrayList<Entry> es = new ArrayList<Entry>();
			for (int i = 0; i < n; i++) {
				for (int j = i + 1; j < n; j++) {
					for (int t = 0; t < T; t++) {
						Entry e = robots[i].timeOfChange(robots[j], t);
						if (e != null) {
							es.add(e);
						}
					}
				}
			}
			simu(robots, es);
			ArrayList<String> res = new ArrayList<String>();
			for (int i = 0; i < n; i++) {
				if (robots[i].hasData)
					res.add(robots[i].nickname);
			}
			sort(res);
			for (String s : res) {
				System.out.println(s);
			}
		}
	}

	void simu(Robot[] robots, ArrayList<Entry> es) {
		int n = robots.length;
		sort(es);
		robots[0].hasData = true;
		double t = 0;
		for (int i = 0; i < n; i++) {
			robots[i].nt = t;
			robots[i].npx = robots[i].pxs[0];
			robots[i].npy = robots[i].pys[0];
		}
		HashSet<Integer> is = new HashSet<Integer>();
		for (int i = 1; i < n; i++)
			is.add(i);
		for (int k = 0; k < n; k++) {
			for (int i = 0; i < n; i++) {
				if (robots[i].hasData) {
					for (int j : is) {
						if (robots[i].near(robots[j])) {
							robots[j].hasData = true;
						}
					}
				}
			}
		}
		for (int i = 1; i < n; i++)
			if (robots[i].hasData)
				is.remove(i);
		for (Entry e : es) {
			for (int i = 0; i < n; i++) {
				if (robots[i].hasData)
					is.remove(i);
			}
			for (int i = 0; i < n; i++) {
				robots[i].go(e.t);
			}
			for (int i = 0; i < n; i++) {
				if (robots[i].hasData) {
					for (int j : is) {
						if (robots[i].near(robots[j])) {
							robots[j].hasData = true;
						}
					}
				}
			}
		}
		for (int i = 0; i < n; i++) {
			robots[i].nt = T;
			robots[i].npx = robots[i].pxs[T];
			robots[i].npy = robots[i].pys[T];
		}
		for (int k = 0; k < n; k++) {
			for (int i = 0; i < n; i++) {
				if (robots[i].hasData) {
					for (int j = 0; j < n; j++) {
						if (robots[i].near(robots[j])) {
							robots[j].hasData = true;
						}
					}
				}
			}
		}
	}

	void search(Robot[] robots, int id) {
		int n = robots.length;
		HashSet<Integer> is = new HashSet<Integer>();
		for (int i = 0; i < n; i++)
			if (!robots[i].hasData)
				is.add(i);
		Queue<Integer> q = new LinkedList<Integer>();
		q.offer(id);
		is.remove(id);
		while (!q.isEmpty()) {
			id = q.poll();
			if (is.isEmpty())
				break;
			for (int j = 0; j < is.size(); j++) {
				if (is.isEmpty())
					break;
				for (int i : is) {
					if (robots[i].near(robots[id])) {
						is.remove(i);
						q.offer(i);
						break;
					}
				}
			}
		}
	}

	class Entry implements Comparable<Entry> {
		double t;
		int i, j;

		public int compareTo(Entry o) {
			return t < o.t ? -1 : 1;
		}

		Entry(double t, int i, int j) {
			this.t = t;
			this.i = i;
			this.j = j;
		}

		public String toString() {
			return t + " " + i + " " + j;
		}
	}

	class Robot {
		int id;
		String nickname;
		boolean hasData = false;
		double npx, npy;
		double nt;
		double[] pxs, pys;
		double[] vxs, vys;

		Robot(int id, String nickname) {
			this.id = id;
			this.nickname = nickname;
			pxs = new double[T + 1];
			pys = new double[T + 1];
			vxs = new double[T];
			vys = new double[T];
		}

		void go(double t) {
			if (t > (int) nt + 1) {
				npx += (vxs[(int) nt]) * ((int) nt + 1 - nt);
				npy += (vys[(int) nt]) * ((int) nt + 1 - nt);
				int i = (int) nt + 1;
				for (; i < t - 1; i++) {
					npx += vxs[i];
					npy += vys[i];
				}
				npx += vxs[i] * (t - i);
				npy += vys[i] * (t - i);
			} else {
				npx += (vxs[(int) nt]) * (t - nt);
				npy += (vys[(int) nt]) * (t - nt);
			}
			nt = t;
		}

		boolean near(Robot rb) {
			return (rb.npx - npx) * (rb.npx - npx) + (rb.npy - npy)
					* (rb.npy - npy) < R * R + EPS;
		}

		Entry timeOfChange(Robot rb, double td) {
			td += EPS;
			int ti = (int) (td);
			double px = pxs[ti] + vxs[ti] * (td - ti), py = pys[ti] + vys[ti]
					* (td - ti);
			double opx = rb.pxs[ti] + rb.vxs[ti] * (td - ti), opy = rb.pys[ti]
					+ rb.vys[ti] * (td - ti);
			double dpx = px - opx, dpy = py - opy;
			double dvx = vxs[ti] - rb.vxs[ti], dvy = vys[ti] - rb.vys[ti];
			double a = dvx * dvx + dvy * dvy, b = dpx * dvx + dpy * dvy, c = dpx
					* dpx + dpy * dpy - R * R;
			if (b * b - a * c < 0)
				return null;
			double dt1 = (-b + sqrt(b * b - a * c)) / a, dt2 = (-b - sqrt(b * b
					- a * c))
					/ a;
			if (0 <= dt1 && dt1 <= ti + 1 - td) {
				return new Entry(td + dt1, id, rb.id);
			}
			if (0 <= dt2 && dt2 <= ti + 1 - td) {
				return new Entry(td + dt2, id, rb.id);
			}
			return null;
		}
	}
}

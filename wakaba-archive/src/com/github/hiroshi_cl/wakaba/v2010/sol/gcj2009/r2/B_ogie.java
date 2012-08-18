package com.github.hiroshi_cl.wakaba.v2010.sol.gcj2009.r2;

import java.io.*;
import java.math.*;
import java.util.*;
import static java.lang.Math.*;
import static java.math.BigInteger.*;
import static java.util.Arrays.*;
import static java.util.Collections.*;

public class B_ogie {
	static final boolean _PRACTICE = true;
	static final boolean _SAMPLE = !true;
	static final boolean _SMALL = !true;
	static final String _PROBLEM = "B";

	void debug(Object... os) {
		// System.err.println(deepToString(os));
	}

	void run() {
		Scanner sc = new Scanner(System.in);
		int oo = sc.nextInt();
		for (int o = 1; o <= oo; o++) {
			System.err.println(o);
			System.out.printf("Case #%d: ", o);
			h = sc.nextInt();
			w = sc.nextInt();
			f = sc.nextInt();
			g = new boolean[h][w];
			for (int i = 0; i < h; i++) {
				char[] cs = sc.next().toCharArray();
				for (int j = 0; j < w; j++) {
					g[i][j] = cs[j] == '#';
				}
			}
			int ti = 0;
			while (ti < w && !g[0][ti])
				ti++;
			ti--;
			Entry init = get(0, 0, 0, ti, 0);
			// debug("init",init);
			int res = -1;
			que = new PriorityQueue<Entry>();
			map = new HashMap<Entry, Integer>();
			que.offer(init);
			map.put(init, 0);
			while (!que.isEmpty()) {
				Entry e = que.poll();
				if (e.d == h - 1) {
					res = e.dig;
					break;
				}
				e.addNexts();
			}
			System.out.println(res == -1 ? "No" : ("Yes " + res));
		}
	}

	Entry get(int x, int y, int s, int t, int dig) {
		// debug(x,y,s,t);
		// [s,t] 掘って、(x,y)に落ちたときの次状態
		if (x == h - 1)
			return new Entry(x, s, t, true, true, dig);
		int nx = x;
		if (g[x + 1][y]) {
			int[] is = get(x + 1, y, true);
			Entry e = new Entry(x, max(is[0], s), min(is[1], t), s < is[0],
					t > is[1], dig);
			debug(e);
			return e;
		}
		nx++;
		while (nx < h && !g[nx][y])
			nx++;
		nx--;
		if (nx - x + 1 > f) {
			// debug(null);
			return null;
		}
		if (nx == h - 1) {
			int[] is = get(nx, y, false);
			Entry e = new Entry(nx, is[0], is[1], true, true, dig);
			debug(e);
			return e;
		}
		int[] is = get(nx, y, false);
		int[] js = get(nx + 1, y, true);
		Entry e = new Entry(nx, max(is[0], js[0]), min(is[1], js[1]),
				is[0] < js[0], is[1] > js[1], dig);
		// debug(e);
		return e;
	}

	PriorityQueue<Entry> que = new PriorityQueue<Entry>();

	void add(Entry e) {
		// debug(e);
		if (e == null)
			return;
		if (!map.containsKey(e) || map.get(e) > e.dig) {
			map.put(e, e.dig);
			que.offer(e);
			// debug("add",e);
		}
	}

	int[] get(int x, int y, boolean wall) {// [s,t].
		int ms = y, mt = y;
		while (0 <= ms && g[x][ms] == wall)
			ms--;
		ms++;
		while (mt < w && g[x][mt] == wall)
			mt++;
		mt--;
		return new int[] { ms, mt };
	}

	HashMap<Entry, Integer> map = new HashMap<Entry, Integer>();

	class Entry implements Comparable<Entry> {
		int d;
		int s, t;
		boolean sb, tb;
		int dig;

		public Entry(int depth, int s, int t, boolean sb, boolean tb, int dig) {
			super();
			this.d = depth;
			this.s = s;
			this.t = t;
			this.dig = dig;
			this.sb = sb;
			this.tb = tb;
		}

		public int compareTo(Entry o) {
			// TODO 自動生成されたメソッド・スタブ
			return dig - o.dig;
		}

		@Override
		public String toString() {
			return d + " " + s + " " + t + " " + dig;
		}

		void addNexts() {
			if (0 < s && sb && !g[d + 1][s - 1]) {
				int[] is = get(d + 1, s - 1, false);
				Entry e = get(d + 1, s - 1, is[0], is[1], dig);
				add(e);
			}
			if (t < w - 1 && tb && !g[d + 1][t + 1]) {
				int[] is = get(d + 1, t + 1, false);
				Entry e = get(d + 1, t + 1, is[0], is[1], dig);
				add(e);
			}
			for (int i = s; i <= t; i++) {
				for (int j = i; j <= t; j++) {
					int ns = i > 0 ? get(d + 1, i - 1, false)[0] : i;
					int nt = j < w - 1 ? get(d + 1, j + 1, false)[1] : j;
					Entry e = get(d + 1, i, ns, nt, dig + j - i + 1);
					if (s < i)
						add(e);
					e = get(d + 1, j, ns, nt, dig + j - i + 1);
					if (t > j)
						add(e);
				}
			}
			debug();
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + getOuterType().hashCode();
			result = prime * result + d;
			result = prime * result + s;
			result = prime * result + (sb ? 1231 : 1237);
			result = prime * result + t;
			result = prime * result + (tb ? 1231 : 1237);
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Entry other = (Entry) obj;
			if (!getOuterType().equals(other.getOuterType()))
				return false;
			if (d != other.d)
				return false;
			if (s != other.s)
				return false;
			if (sb != other.sb)
				return false;
			if (t != other.t)
				return false;
			if (tb != other.tb)
				return false;
			return true;
		}

		private B_ogie getOuterType() {
			return B_ogie.this;
		}
	}

	int h, w, f;
	boolean[][] g;

	boolean in(long from, long n, long to) {
		return from <= n && n < to;
	}

	public static void main(String... args) throws IOException {
		if (!_SAMPLE) {
			if (_SMALL) {
				int i = 0;
				while (new File(_SMALLNAME(i) + ".in").exists())
					i++;
				i--;
				boolean test = false;
				if (new File(_SMALLNAME(i) + ".out").exists()) {
					System.err.println("overwrite?(y/n)");
					char c = (char) System.in.read();
					test = c != 'y';
				}
				if (test) {
					System.setIn(new FileInputStream(_SMALLNAME(i) + ".in"));
					System.setOut(new PrintStream(_PROBLEM + "-small-test.out"));
					new B_ogie().run();
					FileReader f1 = new FileReader(_PROBLEM + "-small-test.out");
					FileReader f2 = new FileReader(_SMALLNAME(i) + ".out");
					BufferedReader br1 = new BufferedReader(f1);
					BufferedReader br2 = new BufferedReader(f2);
					for (int j = 1;; j++) {
						String s1 = br1.readLine();
						String s2 = br2.readLine();
						if (s1 == null && s2 == null) {
							System.err.println("OK!");
							break;
						}
						if (s1 == null || s2 == null || !s1.equals(s2)) {
							System.err.println("failed at line " + j);
							System.err.println("expected " + s2);
							System.err.println("but " + s1);
							break;
						}
					}
				} else {
					System.setIn(new FileInputStream(_SMALLNAME(i) + ".in"));
					System.setOut(new PrintStream(_SMALLNAME(i) + ".out"));
					new B_ogie().run();
				}
			} else {
				System.setIn(new FileInputStream(_LARGENAME() + ".in"));
				System.setOut(new PrintStream(_LARGENAME() + ".out"));
				new B_ogie().run();
			}
		} else
			new B_ogie().run();
	}

	private static String _LARGENAME() {
		return _PROBLEM + "-large" + (_PRACTICE ? "-practice" : "");
	}

	private static String _SMALLNAME(int a) {
		return _PROBLEM + "-small"
				+ (_PRACTICE ? a == 0 ? "-practice" : "" : "-attempt" + a);
	}
}

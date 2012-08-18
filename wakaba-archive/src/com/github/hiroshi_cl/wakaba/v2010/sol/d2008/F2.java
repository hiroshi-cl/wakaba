package com.github.hiroshi_cl.wakaba.v2010.sol.d2008;

import java.util.*;

class F2 {
	static final int N = 10;

	static class Z {
		// class
		static final int getx(int i) {
			return i % N;
		}

		static final int gety(int i) {
			return i / N;
		}

		static final int makei(int x, int y) {
			return x + y * N;
		}

		static List<Integer>[] next;

		static void initNext() {
			next = (ArrayList<Integer>[]) (new ArrayList[N * N]);
			for (int i = 0; i < N * N; i++) {
				next[i] = new ArrayList<Integer>();
				if (getx(i) != 0)
					next[i].add(makei(getx(i) - 1, gety(i)));
				if (getx(i) < N - 1)
					next[i].add(makei(getx(i) + 1, gety(i)));
				if (gety(i) != 0)
					next[i].add(makei(getx(i), gety(i) - 1));
				if (gety(i) < N + 1)
					next[i].add(makei(getx(i), gety(i) + 1));
			}
		}

		// instance
		Set<Integer> ps;

		Z() {
			ps = new TreeSet<Integer>();
		}

		protected Z clone() {
			Z r = new Z();
			r.ps = new TreeSet<Integer>(this.ps);
			return r;
		}

		Z diff(Z other) {
			Z r = this.clone();
			for (int e : other.ps)
				r.ps.remove((Integer) e);
			return r;
		}

		boolean eq(Z other) {
			return other.ps.equals(ps);
		}

		boolean eqa(Z other) {
			Z t = this.clone().sh();
			Z o = other.clone();
			if (o.sh().eq(t))
				return true;
			if (o.rot().sh().eq(t))
				return true;
			if (o.rot().sh().eq(t))
				return true;
			if (o.rot().sh().eq(t))
				return true;
			if (o.rev().sh().eq(t))
				return true;
			if (o.rot().sh().eq(t))
				return true;
			if (o.rot().sh().eq(t))
				return true;
			if (o.rot().sh().eq(t))
				return true;
			return false;
		}

		Z sh() {
			Set<Integer> nps = new TreeSet<Integer>();
			int minx = N - 1, miny = N - 1;
			for (int e : ps) {
				if (getx(e) < minx)
					minx = getx(e);
				if (gety(e) < miny)
					miny = gety(e);
			}
			for (int e : ps) {
				nps.add(makei(getx(e) - minx, gety(e) - miny));
			}
			ps = nps;
			return this;
		}

		Z rot() {
			Set<Integer> nps = new TreeSet<Integer>();
			for (int e : ps) {
				nps.add(makei(N - 1 - gety(e), getx(e)));
			}
			ps = nps;
			return this;
		}

		Z rev() {
			Set<Integer> nps = new TreeSet<Integer>();
			for (int e : ps) {
				nps.add(makei(N - 1 - getx(e), gety(e)));
			}
			ps = nps;
			return this;
		}

	};

	static boolean f(Z orig, Z cur, int rest, Set<Integer> checked) {
		// 探索
		if (rest == 0) {
			return orig.diff(cur).eqa(cur);
		}
		Set<Integer> nexts = new TreeSet<Integer>();
		for (int e : cur.ps) {
			for (int g : Z.next[e]) {
				if (!checked.contains(g) && orig.ps.contains(g))
					nexts.add(g);
			}
		}
		Set<Integer> nch = new TreeSet<Integer>(checked);
		for (int e : nexts) {
			nch.add(e);
			cur.ps.add(e);
			if (f(orig, cur, rest - 1, nch))
				return true;
			cur.ps.remove(e);
		}
		return false;
	}

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		Z.initNext();
		Set<Integer> checked = new TreeSet<Integer>();
		List<Integer> fst = new ArrayList<Integer>();
		Z orig = new Z();
		int nn;

		for (;;) {
			nn = readdata(sc, fst);
			if (nn == 0)
				break;
			orig.ps = new TreeSet<Integer>(fst);
			checked.clear();
			Z cur = new Z();

			int e = fst.get(0);
			checked.add(e);
			cur.ps.add(e);
			if (f(orig, cur, nn / 2 - 1, checked))
				System.out.println("YES");
			else
				System.out.println("NO");
		}
	}

	static int readdata(Scanner sc, List<Integer> fst) {
		int w = sc.nextInt();
		int h = sc.nextInt();
		if (w == 0 && h == 0)
			return 0;
		int r = 0;

		fst.clear();

		for (int i = 0; i < h; i++) {
			for (int j = 0; j < w; j++) {
				int x = sc.nextInt();
				if (x == 1) {
					fst.add(Z.makei(i, j));
					r++;
				}
			}
		}
		return r;
	}
}
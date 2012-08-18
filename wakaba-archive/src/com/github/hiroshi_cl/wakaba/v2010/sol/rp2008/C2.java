package com.github.hiroshi_cl.wakaba.v2010.sol.rp2008;

import java.math.*;
import java.util.*;
import static java.lang.Math.*;
import static java.math.BigInteger.*;
import static java.util.Arrays.*;
import static java.util.Collections.*;

public class C2 {
	void debug(Object... os) {
		System.err.println(deepToString(os));
	}

	void run() {
		Scanner sc = new Scanner(System.in);

		int n = sc.nextInt();
		vw = sc.nextInt();
		vc = sc.nextInt();
		P[] ps = new P[n];
		for (int i = 0; i < n; i++) {
			ps[i] = new P(sc.nextInt(), sc.nextInt());
		}
		TreeSet<P> tset = new TreeSet<P>(new CmpX());
		for (int i = 0; i < n; i++)
			tset.add(ps[i]);

		for (int i = 0; i < n - 1; i++) {
			if (ps[i].y < ps[i + 1].y) {
				for (int j = i + 2; j < n; j++) {
					if (ps[i].y > ps[j].y) {
						tset.add(new P(ps[j - 1].x + (ps[i].y - ps[j].y)
								/ (ps[j].y - ps[j - 1].y)
								* (ps[j].x - ps[j - 1].x), ps[i].y));
					}
				}
			}
		}
		for (int i = 1; i < n; i++) {
			if (ps[i - 1].y < ps[i].y) {
				for (int j = 0; j < i - 1; j++) {
					if (ps[j].y < ps[i].y) {
						tset.add(new P(ps[j].x + (ps[i].y - ps[j].y)
								/ (ps[j + 1].y - ps[j].y)
								* (ps[j + 1].x - ps[j].x), ps[i].y));
					}
				}
			}
		}
		P[] pset = tset.toArray(new P[0]);
		memo = new TreeMap<L, Double>();
		debug(pset);

		double result;
		if (pset[pset.length - 1].y == pset[0].y) {
			result = Math.min((pset[pset.length - 1].x - pset[0].x) / vc,
					search(pset, 0, pset.length - 1));
		} else {
			result = search(pset, 0, pset.length - 1);
		}
		System.out.printf("%.6f%n", result);

	}

	int vw;
	int vc;
	TreeMap<L, Double> memo;

	double search(P[] pset, int from, int to) {
		if (from >= to)
			return 0;
		L key = new L(from, to);
		if (memo.containsKey(key)) {
			return memo.get(key);
		}
		for (int i = from + 2; i < to; i++) {
			if (pset[i].y == pset[from].y) {
				double r_from_i = Math.min(search(pset, from, i),
						(pset[i].x - pset[i].y) / vc);
				double r = r_from_i + search(pset, i, to);
				memo.put(key, r);
				return r;
			}
		}
		double r = pset[from].dist(pset[from + 1]) / vw
				+ search(pset, from + 1, to);
		memo.put(key, r);
		return r;
	}

	public static void main(String[] args) {
		new C2().run();
	}

	class L implements Comparable<L> {
		int car, cdr;

		L(int a, int d) {
			car = a;
			cdr = d;
		}

		public int compareTo(L o) {
			return (car == o.car) ? (cdr - o.cdr) : (car - o.car);
		}
	}

	class P {
		double x, y;

		P(double x, double y) {
			this.x = x;
			this.y = y;
		}

		double dist(P o) {
			return Math.sqrt((x - o.x) * (x - o.x) + (y - o.y) * (y - o.y));
		}
	}

	class CmpX implements Comparator<P> {
		public int compare(P p, P q) {
			return (int) Math.signum(p.x - q.x);
		}
	}

	class CmpY implements Comparator<P> {
		public int compare(P p, P q) {
			return (int) Math.signum(p.y - q.y);
		}
	}
}

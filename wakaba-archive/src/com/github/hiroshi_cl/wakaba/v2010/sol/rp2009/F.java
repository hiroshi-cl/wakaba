package com.github.hiroshi_cl.wakaba.v2010.sol.rp2009;
import java.util.*;
import java.math.*;
import java.io.*;
import static java.math.BigInteger.*;
import static java.lang.Math.*;
import static java.util.Arrays.*;
import static java.util.Collections.*;

public class F {
	void debug(Object... os) {
		System.err.println(deepToString(os));
	}

	void run() {
		Scanner sc = new Scanner(System.in);
		try {
			sc = new Scanner(new File("F.txt"));
		} catch (Exception e) {
		}
		for (;;) {
			int n = sc.nextInt();
			Entry[] es = new Entry[n];
			l = sc.nextInt();
			if (n == 0 && l == 0)
				return;
			for (int i = 0; i < n; i++) {
				es[i] = new Entry(sc.nextInt(), sc.nextInt(), sc.nextInt());
			}
			sort(es);
			double low = 0, high = 1e7;
			do {
				double mid = (high + low) / 2;
				double d = can(l, mid, es);
				if (d > 0) {
					full=false;
					double d1 = can(d, mid, es);
					if (d1>=0 && full && eq(d, d1))
						high = mid;
					else
						low = mid;
				}
				else {
					low = mid;
				}
//				if (ok)
//					high = mid;
//				else
//					low = mid;
			} while (high - low > EPS);
			System.out.println(low);
		}
	}
	boolean full;
	double l;
	double can(double now, double mid, Entry[] es) {
		double sum = now;
		int n=es.length;
		int bef = 0;
//		boolean ok = true;
		for (int i = 0; i < n; i++) {
			sum += (es[i].s - bef) * mid;
			if (sum >= l){
				sum = l;
				full=true;
			}
			// debug(sum);
			sum += (es[i].t - es[i].s) * (mid - es[i].u);
			if (sum < 0) {
				return -1;
			}
			bef = es[i].t;
		}
		sum += (86400  - bef) * mid;
		if(sum>l){
			sum=l;
			full=true;
		}
		return sum;
	}

	boolean eq(double a, double b) {
		return abs(a - b) < EPS;
	}

	double EPS = 1e-7;

	class Entry implements Comparable<Entry> {
		@Override
		public int compareTo(Entry o) {
			return s - o.s;
		}

		int s;
		int t;
		int u;

		public Entry(int s, int t, int u) {
			super();
			this.s = s;
			this.t = t;
			this.u = u;
		}

	}

	public static void main(String[] args) {
		new F().run();
	}
}

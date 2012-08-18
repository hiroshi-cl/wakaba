package com.github.hiroshi_cl.wakaba.v2010.sol.srm;

import java.math.*;
import java.util.*;
import static java.lang.Math.*;
import static java.math.BigInteger.*;
import static java.util.Arrays.*;
import static java.util.Collections.*;

public class CommercialPlanner {
	long oD, sPd;
	long[] st, du;
	int n;

	public int bestMinute(int[] starts, int[] durations, int ourDuration,
			int secondsPerDay, int nn) {
		this.n = nn;
		oD = ourDuration;
		sPd = secondsPerDay;
		cm = starts.length;
		if (cm == 0)
			return 0;
		st = new long[cm * 3];
		du = new long[cm * 3];
		for (int i = 0; i < cm * 3; i++) {
			st[i] = starts[i % cm] + sPd * (i / cm);
			du[i] = durations[i % cm];
		}
		if (n > cm + 1)
			n = cm + 1;
		es = new Entry[cm * 3];
		for (int i = 0; i < cm * 3; i++)
			es[i] = new Entry(st[i], st[i] + du[i]);
		sort(es);
		for (int i = 0; i < cm; i++)
			update(es[i].t);
		update(sPd);
		return (int) res;
	}

	int cm;
	long res = -1;
	long rem = -1;
	Entry[] es;

	void update(long l) {
		for (int i = 0; i < cm * 3; i++) {
			if (es[i].s <= l && es[i].t <= l || es[i].s >= l + oD
					&& es[i].t >= l + oD)
				;
			else
				return;
		}
		long nl = l % sPd;
		int m = cm * 3 - 1;
		for (; m >= 0; m--) {
			if (es[m].t <= l)
				break;
		}
		long time;
		debug(l, nl, res, rem);
		debug(cm, n, m);
		if (cm + 1 == n)
			time = 1;
		else
			time = es[m + n].s - l;
		if (rem < time) {
			rem = time;
			res = nl;
		}
		if (rem == time) {
			res = min(res, nl);
		}
	}

	class Entry implements Comparable<Entry> {
		long s, t;

		Entry(long s, long t) {
			this.s = s;
			this.t = t;
		}

		// @Override
		public String toString() {
			return s + " " + t;
		}

		public int compareTo(Entry o) {
			return (int) signum(s - o.s);
		}
	}

	void debug(Object... os) {
		// BEGIN CUT HERE
		// System.err.println(deepToString(os));
		// END CUT HERE
	}
}
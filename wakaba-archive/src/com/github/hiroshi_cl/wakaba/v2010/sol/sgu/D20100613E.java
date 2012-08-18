package com.github.hiroshi_cl.wakaba.v2010.sol.sgu;

import java.util.*;
import java.math.*;
import static java.lang.Math.*;
import static java.util.ArrayList.*;
import static java.util.Collections.*;
import static java.math.BigInteger.*;

public class D20100613E {
	public static void main(String[] args) {
		new D20100613E().run();
	}

	void debug(Object... os) {
		// System.err.println(Arrays.deepToString(os));
	}

	void run() {
		Scanner sc = new Scanner(System.in);
		int N = sc.nextInt();

		P[] ps = new P[N];

		ps[0] = new P(sc.nextInt(), sc.nextInt(), 0);
		for (int i = 1; i < N; i++) {
			ps[i] = new P(sc.nextInt(), sc.nextInt(), ps[i - 1].d);
			ps[i].d += ps[i].dist(ps[i - 1]);
		}

		for (int i = 0; i < N; i++)
			debug(ps[i].d);
		long min = ps[N - 1].d;
		for (int i = 0; i < N - 1; i++) {
			for (int j = i - 2; j >= 0; j--) {
				P ip = intersect(ps[i], ps[i + 1], ps[j], ps[j + 1]);
				// P ip = ss[i].intersection(ss[j]);
				if (ip == null)
					continue;

				long di = ps[i].d + ps[i].dist(ip);
				long dj = ps[j].d + ps[j].dist(ip);
				min = Math.min(di - dj, min);
				break;
			}
		}
		System.out.println(min);
	}

	P intersect(P p1, P p2, P q1, P q2) {
		if (p1.equals(q1) || p1.equals(q2))
			return p1;
		if (p2.equals(q1) || p2.equals(q2))
			return p2;

		long px1 = min(p1.x, p2.x), px2 = max(p1.x, p2.x);
		long qx1 = min(q1.x, q2.x), qx2 = max(q1.x, q2.x);
		long py1 = min(p1.y, p2.y), py2 = max(p1.y, p2.y);
		long qy1 = min(q1.y, q2.y), qy2 = max(q1.y, q2.y);

		if (p1.x == p2.x) {
			if (q1.x == q2.x)
				return null;
			if (py1 <= q1.y && q1.y <= py2 && qx1 <= p1.x && p1.x <= qx2)
				return new P(p1.x, q1.y, -1);
			return null;
		} else {
			if (q1.y == q2.y)
				return null;
			if (qy1 <= p1.y && p1.y <= qy2 && px1 <= q1.x && q1.x <= px2)
				return new P(q1.x, p1.y, -1);
			return null;
		}
	}

	class P {
		long x, y, d;

		P(long x, long y, long d) {
			this.x = x;
			this.y = y;
			this.d = d;
		}

		long dist(P o) {
			return Math.abs((o.x - x) + (o.y - y));
		}

		public boolean equals(Object o) {
			P p = (P) o;
			return p.x == x && p.y == y;
		}
	}

}

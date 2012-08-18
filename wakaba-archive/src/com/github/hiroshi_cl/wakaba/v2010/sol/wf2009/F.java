package com.github.hiroshi_cl.wakaba.v2010.sol.wf2009;

import java.util.*;

public class F {
	public static void main(String[] args) {
		new F().run();
	}

	void run() {
		Scanner sc = new Scanner(System.in);
		for (int caseNo = 1;; caseNo++) {
			int N = sc.nextInt(); // number of sapling
			int M = sc.nextInt(); // required margin
			if (M == 0 && N == 0)
				break;
			P[] ps = new P[N];
			for (int i = 0; i < N; i++)
				ps[i] = new P(sc.nextDouble(), sc.nextDouble());

			double len = search(M, ps, 0, new ArrayList<List<P>>());

			System.out.printf("Case %d: length = %.2f\n", caseNo, len);
		}
	}

	double search(double M, P[] ps, int n, List<List<P>> group) {
		if (n == ps.length) {
			double len = 0;
			for (List<P> ls : group) {
				len += getFenceLength(ls, M);
			}
			return len;
		}

		double min = ps.length * M * Math.PI * 2;
		for (int i = 0; i < group.size(); i++) {
			group.get(i).add(ps[n]);
			min = Math.min(min, search(M, ps, n + 1, group));
			group.get(i).remove(group.get(i).size() - 1);
		}
		List<P> l = new ArrayList<P>();
		l.add(ps[n]);
		group.add(l);
		min = Math.min(min, search(M, ps, n + 1, group));
		group.remove(group.size() - 1);

		return min;
	}

	double getFenceLength(List<P> ps, double M) {
		if (ps.size() == 1)
			return M * Math.PI * 2;

		int N = ps.size();
		List<P> pset = new ArrayList<P>();
		Map<P, Integer> pmap = new TreeMap<P, Integer>();
		for (int i = 0; i < N; i++) {
			for (int j = i + 1; j < N; j++) {
				P[][] otan = outerTangent(ps.get(i), M, ps.get(j), M);
				for (int k = 0; k < otan.length; k++) {
					pset.add(otan[k][0]);
					pset.add(otan[k][1]);

					pmap.put(otan[k][0], i);
					pmap.put(otan[k][1], j);
				}
			}
		}
		P[] cvps = convexHull(pset.toArray(new P[0]));

		double len = 0;

		for (int i = 0; i < cvps.length; i++) {
			int ni = (i + 1) % cvps.length;
			int ix1 = pmap.get(cvps[i]);
			int ix2 = pmap.get(cvps[ni]);

			if (ix1 == ix2) {
				len += M * arc(ps.get(ix1), cvps[i], cvps[ni]);
			} else {
				len += cvps[i].dist(cvps[ni]);
			}
		}
		return len;
	}

	class P implements Comparable<P> {
		double x, y;

		P(double x, double y) {
			this.x = x;
			this.y = y;
		}

		P add(P p) {
			return new P(x + p.x, y + p.y);
		}

		P sub(P p) {
			return new P(x - p.x, y - p.y);
		}

		P mul(double d) {
			return new P(x * d, y * d);
		}

		double norm2() {
			return x * x + y * y;
		}

		double norm() {
			return Math.sqrt(norm2());
		}

		double dist(P p) {
			return sub(p).norm();
		}

		double dot(P p) {
			return x * p.x + y * p.y;
		}

		double det(P p) {
			return x * p.y - y * p.x;
		}

		P rot90() {
			return new P(-y, x);
		}

		public int compareTo(P o) {
			return (int) ((x != o.x) ? Math.signum(x - o.x) : Math.signum(y
					- o.y));
		}

		public boolean equals(Object o) {
			return x == ((P) o).x && y == ((P) o).y;
		}
	}

	double arc(P o, P p1, P p2) {
		double a1 = Math.atan2(p1.y - o.y, p1.x - o.x);
		double a2 = Math.atan2(p2.y - o.y, p2.x - o.x);
		double diff = Math.abs(a1 - a2);
		return Math.min(diff, Math.abs(2 * Math.PI - diff));
	}

	P[][] outerTangent(P o1, double r1, P o2, double r2) {
		P o1o2 = o2.sub(o1);
		double l = o1o2.norm2();
		double d = l - (r1 - r2) * (r1 - r2);
		if (d < 0)
			return new P[0][0];
		P p = o1o2.mul((r1 - r2) / l);
		P q = o1o2.rot90().mul(Math.sqrt(d) / l);
		return new P[][] {
				{ o1.add(p.mul(r1)).add(q.mul(r1)),
						o2.add(p.mul(r2)).add(q.mul(r2)) },
				{ o1.add(p.mul(r1)).sub(q.mul(r1)),
						o2.add(p.mul(r2)).sub(q.mul(r2)) } };
	}

	int ccw(P a, P b, P c) {
		P ab = b.sub(a), ac = c.sub(a);
		if (ab.det(ac) > 0)
			return +1;
		if (ab.det(ac) < 0)
			return -1;
		if (ab.dot(ac) < 0)
			return +2;
		if (ab.norm2() < ac.norm2())
			return -2;
		return 0;
	}

	P[] convexHull(P[] ps) {
		int n = ps.length, k = 0;
		Arrays.sort(ps);
		P[] tmp = new P[n * 2];
		for (int i = 0; i < n; tmp[k++] = ps[i++])
			while (k >= 2 && ccw(tmp[k - 2], tmp[k - 1], ps[i]) <= 0)
				k--;
		for (int i = n - 2, t = k + 1; i >= 0; tmp[k++] = ps[i--])
			while (k >= t && ccw(tmp[k - 2], tmp[k - 1], ps[i]) <= 0)
				k--;
		P[] res = new P[k - 1];
		System.arraycopy(tmp, 0, res, 0, k - 1);
		return res;
	}
}

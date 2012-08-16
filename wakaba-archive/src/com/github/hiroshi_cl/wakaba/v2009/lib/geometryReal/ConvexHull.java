package com.github.hiroshi_cl.wakaba.v2009.lib.geometryReal;

import static java.util.Arrays.sort;



/*
 * 凸包をつくる。 O(n log n) 
 * 点の順序は、反時計回りになっている。
 * 
 * verified : pku2187 ,pku1113
 * 
 * 参考サイト：　spaghetti source (http://www.prefield.com/algorithm/)
 */

public class ConvexHull {
	
	/*
	 * a,b,c の３点の進行方向を判定する。
	 */
	int ccw(P a, P b, P c) {
		P ab = b.sub(a), ac = c.sub(a);
		if (ab.det(ac) > 0)
			return +1; // ab,ac is counter clockwise
		if (ab.det(ac) < 0)
			return -1; // ab,ac is clockwise
		if (ab.dot(ac) < 0)
			return +2; // c-a-b
		if (ab.norm2() < ac.norm2())
			return -2; // a-b-c
		return 0; // a-c-b
	}

	P[] convexHull(P[] ps) {
		int n = ps.length, k = 0;
		sort(ps);
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

package com.github.hiroshi_cl.wakaba.v2010.lib.geometry.v2010a;

import static com.github.hiroshi_cl.wakaba.v2010.lib.geometry.v2010a.EPS.*;
import java.util.ArrayList;

public class MethodLine {

	P intersectionLL(P p1, P p2, P r1, P r2) {
		P a = p2.sub(p1), b = r1.sub(p1), c = r2.sub(r1);
		double d = c.det(a);
		double e = c.det(b);
		if (eq(d, 0))
			return null; // eq(e,0) なら同一直線上.
		return p1.add(a.mul(e / d));
	}

	/**
	 * pが直線r1,r2に乗っていることは前提。
	 * 
	 * @param p
	 * @param r1
	 * @param r2
	 * @param k
	 *            0,1,2 でそれぞれ直線、半直線、線分を意味するフラグ。
	 * @return pが IN -> 1 ,ON -> 0 ,OUT -> -1.
	 */
	private int calc(P p, P r1, P r2, int k) {
		if (k == 0)
			return 1;
		if (k == 1)
			return -new S(r2, p).contains(r1);
		return new S(r1, r2).contains(p);
	}

	/**
	 * 一般化したintersection.　kp,krは0,1,2 でそれぞれ直線、半直線、線分を意味するフラグ。
	 * 端点は含めない。共有点が１個でない場合 nullを返す。 TODO flag = trueの時、端点を含めるようにする。
	 */
	P intersection(P p1, P p2, int kp, P r1, P r2, int kr) {
		P res = intersectionLL(p1, p2, r1, r2);
		if (res == null)
			return null;
		int a = calc(res, p1, p2, kp);
		int b = calc(res, r1, r2, kr);
		if (a + b == 2)
			return res;
		return null;
	}

	/**
	 * 一般化したintersection.　kp,krは0,1,2 でそれぞれ直線、半直線、線分を意味するフラグ。
	 * 端点は含めない。共有点が１個でない場合 nullを返す。 flag = trueの時、端点を含めるようにする。
	 * ただし、flag=trueで線分(半直線)が重なっている場合、重なっている点のいずれか一つを返してしまう。 TODO verify.
	 */
	P intersection(P p1, P p2, int kp, P r1, P r2, int kr, boolean flag) {
		if (flag) {
			ArrayList<P> ps = new ArrayList<P>();
			ArrayList<P> rs = new ArrayList<P>();
			if (kp >= 1)
				ps.add(p1);
			if (kp == 2)
				ps.add(p2);
			if (kr >= 1)
				rs.add(r1);
			if (kr == 2)
				rs.add(r2);
			for (P p : ps)
				for (P r : rs)
					if (p.equals(r))
						return p;
		}
		P res = intersectionLL(p1, p2, r1, r2);
		if (res == null)
			return null;
		int a = calc(res, p1, p2, kp);
		int b = calc(res, r1, r2, kr);
		if (a + b == 2 || (flag && a >= 0 && b >= 0))
			return res;
		return null;
	}

}

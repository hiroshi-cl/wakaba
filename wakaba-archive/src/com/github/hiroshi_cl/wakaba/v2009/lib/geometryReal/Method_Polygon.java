package com.github.hiroshi_cl.wakaba.v2009.lib.geometryReal;
import static com.github.hiroshi_cl.wakaba.v2009.lib.geometryReal.Method_Line.intersection_ll;
import static com.github.hiroshi_cl.wakaba.v2009.lib.geometryReal.Method_Triangle.circumcenter;
import static java.lang.Math.PI;
import static java.lang.Math.max;
import static java.util.Arrays.sort;
import java.util.ArrayList;
/*
 * 多角形に関するメソッド集
 * 090619 containsを追加。
 */
public class Method_Polygon {
	static private double EPS = 1e-9;
	// verified: pku3348.
	static double area(P[] ps) {// 符号付き面積。反時計回りのとき正、時計回りのとき負。
		double S = 0;
		for (int i = 0; i < ps.length; i++)
			S += ps[i].det(ps[(i + 1) % ps.length]);
		return S / 2;
	}
	// verified: pku3348.
	// 凸包を作る。le -> ltで、辺上の点を含める。
	static int dn;
	static P[] ds;
	static int un;
	static P[] us;
	static P[] convexHull(P[] ps) { // O(n logn). psがソートされるので注意。
		int n = ps.length;
		sort(ps);
		ds = new P[n];// ds[0] = us[un-1] = ps[0].
		us = new P[n];// ds[dn-1] = us[0] = ps[n-1].
		dn = 0;
		un = 0;
		for (int i = 0; i < n; ds[dn++] = ps[i++])
			while (dn >= 2 && le(ds[dn - 1].sub(ds[dn - 2]).det(ps[i].sub(ds[dn - 2])), 0))
				dn--;
		for (int i = n - 1; i >= 0; us[un++] = ps[i--])
			while (un >= 2 && le(us[un - 1].sub(us[un - 2]).det(ps[i].sub(us[un - 2])), 0))
				un--;
		P[] res = new P[dn + un - 2];
		System.arraycopy(ds, 0, res, 0, dn - 1);
		System.arraycopy(us, 0, res, dn - 1, un - 1);
		return res;
	}
	// TODO check.
	static P[][] convexCut(P[] ch, P p1, P p2) {// 凸多角形をp1p2で切断した左側、右側を返す。
		ArrayList<P> ls = new ArrayList<P>(), rs = new ArrayList<P>();
		P p1p2 = p2.sub(p1);
		int n = ch.length;
		for (int i = 0; i < n; i++) {
			P q1 = ch[i], q2 = ch[(i + 1) % n];
			double d1 = p1p2.det(q1.sub(p1)), d2 = p1p2.det(q2.sub(p1));
			if (d1 > 0) ls.add(q1);
			else rs.add(q1);
			if (d1 * d2 <= 0) {
				P p = intersection_ll(p1, p2, q1, q2);
				ls.add(p);
				rs.add(p);
			}
		}
		return new P[][] { ls.toArray(new P[0]), rs.toArray(new P[0]) };
	}
	// verified: pku2187.
	static double convexDiameter(P[] ch) {// 凸多角形の直径を求める。O(n).
		int n = ch.length;
		int si = 0, sj = 0;
		for (int k = 0; k < n; k++) {
			if (ch[k].compareTo(ch[si]) < 0) si = k;
			if (ch[sj].compareTo(ch[k]) < 0) sj = k;
		}
		int i = si, j = sj;
		double res = 0;
		do {
			if (ch[(i + 1) % n].sub(ch[i]).det(ch[(j + 1) % n].sub(ch[j])) >= 0) j = (j + 1) % n;
			else i = (i + 1) % n;
			res = max(res, ch[i].dist(ch[j]));
		} while (!(i == si && j == sj));
		return res;
	}
	// TODO check
	// 入力された順序で、凸包になっているかを調べる。le->ltで、辺上の点を許可する。O(n).
	static boolean isConvexHull(P[] ps) {// O(n).
		int n = ps.length;
		if (n < 3) return false;
		boolean anticlockwise = true, clockwise = true;
		for (int i = 0; i < n; i++) {
			double value = ps[(i + 1) % n].sub(ps[i]).det(ps[(i + 2) % n].sub(ps[i]));
			if (le(value, 0)) anticlockwise = false;
			if (le(0, value)) clockwise = false;
		}
		return anticlockwise || clockwise;
	}
	// verified: pku2504.
	static P[] regularPolygon(P p1, P p2, P p3, int n) {// p1,p2,p3を含む正n角形
		P o = circumcenter(p1, p2, p3);
		P[] res = new P[n];
		res[0] = p1;
		boolean ok1 = false, ok2 = false;
		double theta = 2 * PI / n;
		for (int i = 1; i < n; i++) {
			res[i] = o.add(res[i - 1].sub(o).rot(theta));
			if (!ok1 && res[i].equals(p2)) ok1 = true;
			if (!ok2 && res[i].equals(p3)) ok2 = true;
		}
		return ok1 && ok2 ? res : null;
	}
	//verified: pku3525.
	static int contains(P[] ps,P p){//点、多角形方眼判定 OUT,ON,IN = -1, 0, 1.
		int n=ps.length;
		int res=-1;
		for(int i=0;i<n;i++){
			P a=ps[i].sub(p),b=ps[(i+1)%n].sub(p);
			if(a.y>b.y){
				P t=a;a=b;b=t;
			}
			if(a.y<=0 && 0<b.y && a.det(b)<0)res*=-1;
			if(a.det(b)==0 && a.dot(b)<=0)return 0;
		}
		return res;
	}
	static private boolean le(double a, double b) {
		return signum(a - b) <= 0;
	}
	static private boolean lt(double a, double b) {
		return signum(a - b) < 0;
	}
	static private int signum(double d) {
		return d < -EPS ? -1 : d > EPS ? 1 : 0;
	}
}

package com.github.hiroshi_cl.wakaba.v2010.sol.rp2008;
// package contest030D;
import java.math.*;
import java.util.*;
import static java.lang.Math.*;
import static java.math.BigInteger.*;
import static java.util.Arrays.*;
import static java.util.Collections.*;

public class D {
	void debug(Object... os) {
//		System.err.println(deepToString(os));
	}
	void run() {
		Scanner sc = new Scanner(System.in);
		int n = sc.nextInt();
		P[] ps = new P[n];
		P[] rs = new P[n];
		P gp=new P(0,0),gr=new P(0,0);

		for (int i = 0; i < n; i++) {
			ps[i] = new P(sc.nextDouble(), sc.nextDouble());
			gp = gp.add(ps[i]);
		}
		for (int i = 0; i < n; i++) {
			rs[i] = new P(sc.nextDouble(), sc.nextDouble());
			gr = gr.add(rs[i]);
		}
		debug(ps);
		debug(rs);
		gp = gp.div(n);
		gr = gr.div(n);
		debug(gp);
		debug(gr);
		for (int i = 0; i < n; i++) {
			ps[i]=ps[i].sub(gp);
			rs[i] =rs[i].sub(gr);
		}
		if (n < 2) {
			System.out.println(0);
			return;
		}
		double res = PI * 2;
		for (int i = 0; i < n; i++) {
			res = min(res, calc(ps, rs, i));
		}
		if (res == PI * 2) {
			for (int i = 0;; i++) {}
		}
		System.out.println(res);
	}
	double calc(P[] ps, P[] rs, int id) {
		ps = ps.clone();
		rs = rs.clone();
		int n = ps.length;
		double off = rs[id].rad() - ps[0].rad();
		debug("rs["+id+"].rad()",rs[id].rad());
		debug(ps[0].rad());
		for (int i = 0; i < n; i++) {
			ps[i]=ps[i].rot(off);
		}
		sort(rs);
		sort(ps);
		debug(rs);
		debug(ps);
		debug(id);
		debug(off);
		debug(normalize(off));
		double res = PI*2;
		boolean ok=true;
		for (int i = 0; i < n; i++) {
			if(!ps[i].equals(rs[i]))ok=false;
		}
		if(ok)res = min(res,normalize(off));
		return res;
	}
	double normalize(double off) {
		double res = ( off % ( 2 * PI ) + 2 * PI ) % ( 2 * PI );
		if (res > PI) res = 2 * PI - res;
		return res;
	}
	class P implements Comparable<P> {
		public String toString() {
			return String.format("(%.2f,%.2f)",x,y);
		}
		double x, y;
		public P(double x, double y) {
			super();
			this.x = x;
			this.y = y;
		}
		P add(P p){
			return new P(x+p.x,y+p.y);
		}
		P div(double d){
			return new P(x/d,y/d);
		}
		P sub(P p) {
			return new P(x - p.x, y - p.y);
		}
		public int compareTo(P o) {
			return signum(x-o.x)==0 ? signum(y-o.y):signum(x-o.x);
		}
		double norm() {
			return sqrt(x * x + y * y);
		}
		double rad() {
			double d = atan2(y, x);
			return eq(d, -PI) ? PI : d;
		}
		P rot(double t) {
			return new P(x * cos(t) - y * sin(t), x * sin(t) + y * cos(t));
		}
		public boolean equals(Object obj) {
			P p = (P) obj;
			return eq(x, p.x) && eq(y, p.y);
		}
	}
	double EPS = 1e-9;
	boolean eq(double a, double b) {
		return signum(a - b) == 0;
	}
	int signum(double d) {
		return d < -EPS ? -1 : d > EPS ? 1 : 0;
	}
	public static void main(String[] args) {
		new D().run();
	}
}

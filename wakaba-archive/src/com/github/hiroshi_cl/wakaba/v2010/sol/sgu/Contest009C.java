package com.github.hiroshi_cl.wakaba.v2010.sol.sgu;

import java.math.*;
import java.util.*;
import static java.lang.Math.*;
import static java.math.BigInteger.*;
import static java.util.Arrays.*;
import static java.util.Collections.*;

public class Contest009C {
	void debug(Object... os) {
		System.err.println(deepToString(os));
	}

	void run() {
		Scanner sc = new Scanner(System.in);
		int n = sc.nextInt();
		int k = sc.nextInt();
		int l = sc.nextInt();
		P p1 = new P(sc.nextDouble(), sc.nextDouble());
		P p2 = new P(sc.nextDouble(), sc.nextDouble());
		if (k > l) {
			int t = k;
			k = l;
			l = t;
			P tmp = p1;
			p1 = p2;
			p2 = tmp;
		}
		P center = center(p1, p2, n, k, l);
		// debug(p1);
		// debug(center);
		P[] ps = new P[n];
		double r = 2 * PI / n;
		ps[0] = center.add(p1.sub(center).rot(r * (k - 1)));
		// debug(ps[0]);
		for (int i = 0; i < n - 1; i++) {
			ps[i + 1] = center.add(ps[i].sub(center).rot(-r));
		}
		for (int i = 0; i < n; i++) {
			System.out.printf("%6f %6f%n", ps[i].x, ps[i].y);
		}
		// double s1 = Math.sin(2*n1*Math.PI/n);
		// double s2 = Math.sin(2*n2*Math.PI/n);
		// double c1 = Math.cos(2*n1*Math.PI/n);
		// double c2 = Math.cos(2*n2*Math.PI/2);
		//
		// double scq =
		// ((s1-s2)*(n1y-n2y)-(c1-c2)*(n1x-n2x))/((s1-s2)*(n1x-n2x)-(c1-c2)*(n1y-n2y));
		// double st = Math.sqrt(1/(scq*scq+1));
		// double ct = scq * st;
		// double r = n1x==n2x ? (n1y-n2y)/(ct*s1+st*c1-ct*s2-st*c2) :
		// (n1x-n2x)/((ct*c1-st*s1)-(ct*c2-st*s2));
		// double px = n1x-r*(ct*c1-st*s1);
		// double py = n1y-r*(ct*s1+st*c1);
		//
		// debug(n1x, n1y, st,ct,r,px,py);
		// for(int i=0;i<n;i++) {
		// double a = 2*i*Math.PI/n;
		// double cx = ct*Math.cos(a) - st*Math.sin(a);
		// double sx = ct*Math.sin(a) + st*Math.cos(a);
		// System.out.printf("%.6f %.6f%n",px+r*cx,py+r*sx);
		// }
	}

	P center(P p1, P p2, int n, int k, int l) {
		if (n % 2 == 0 && l - k == n / 2) {
			return p1.add(p2).div(2);
		}
		double r = (PI - (2 * PI * (l - k) / n)) / 2;
		// debug(r/PI);
		P p3 = p1.add(p2.sub(p1).rot(-2 * r));
		// debug(p1,p2,p3);
		// debug(cirnumcenter(p1, p2, p3));
		return cirnumcenter(p1, p2, p3);
	}

	P cirnumcenter(P A, P B, P C) {
		P AB = B.sub(A), BC = C.sub(B), CA = A.sub(C);
		return (A.add(B).sub(AB.rot(PI / 2).mul(BC.dot(CA) / AB.det(BC))))
				.div(2);
	}

	class P {
		public String toString() {
			return x + " " + y;
		}

		double x, y;

		P(double x, double y) {
			this.x = x;
			this.y = y;
		}

		P mul(double d) {
			return new P(x * d, y * d);
		}

		double dot(P p) {
			return x * p.x + y * p.y;
		}

		double det(P p) {
			return x * p.y - y * p.x;
		}

		P add(P p) {
			return new P(x + p.x, y + p.y);
		}

		P div(double d) {
			return new P(x / d, y / d);
		}

		P sub(P p) {
			return new P(x - p.x, y - p.y);
		}

		P rot(double t) {
			return new P(x * cos(t) - y * sin(t), x * sin(t) + y * cos(t));
		}
		// P
	}

	public static void main(String[] args) {
		new Contest009C().run();
	}
}

package com.github.hiroshi_cl.wakaba.v2010.sol.r2001;
import java.util.*;
import static java.lang.Math.*;
import static java.util.Arrays.*;
import static java.util.Collections.*;
public class H {
	public static void main(String[] args) {
		new H().run();
	}
	P o;
	P[] ps;
	int n;
	double step;
	void run() {
		Scanner sc = new Scanner(System.in);
		for (;;) {
			n = sc.nextInt();
			if (n == 0) return;
			ps = new P[n];
			o = new P(0, 0, 0);
			for (int i = 0; i < n; i++) {
				ps[i] = new P(sc.nextDouble(), sc.nextDouble(), sc.nextDouble());
				o = o.add(ps[i]);
			}
			o = o.mul(1. / n);
			step = 10;
			for (int i = 0; i < 10; i++) {
				for (int count = 0; count < 200; count++) {
					if (!move()) break;
				}
				step /= 5;
			}
			System.out.println(o.dist(mostDistPoint()));
		}
	}
	boolean eq(double a, double b) {
		return abs(a - b) < step / 100;
	}
	P mostDistPoint() {
		int res = 0;
		double dist = 0;
		for (int i = 0; i < n; i++) {
			if (dist < o.dist(ps[i])) {
				dist = o.dist(ps[i]);
				res = i;
			}
		}
		return ps[res];
	}
	boolean move() {
		P p = mostDistPoint();
		if (eq(o.dist(p), 0)) return false;
		P d = p.sub(o);
		d = d.mul(1. * step / d.norm());
		o = o.add(d);
		return true;
	}
	class P {
		double x, y, z;
		P(double x, double y, double z) {
			this.x = x;
			this.y = y;
			this.z = z;
		}
		P add(P p) {
			return new P(x + p.x, y + p.y, z + p.z);
		}
		P sub(P p) {
			return new P(x - p.x, y - p.y, z - p.z);
		}
		P mul(double d) {
			return new P(x * d, y * d, z * d);
		}
		double norm() {
			return sqrt(x * x + y * y + z * z);
		}
		double dist(P p) {
			return sub(p).norm();
		}
	}
}

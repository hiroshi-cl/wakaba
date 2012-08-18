package com.github.hiroshi_cl.wakaba.v2010.sol.d2004;

import java.math.*;
import java.util.*;

import static java.math.BigInteger.*;
import static java.lang.Math.*;
import static java.util.Arrays.*;
import static java.util.Collections.*;

public class E4 {
	static void debug(Object... os) {
//		System.err.println(deepToString(os));
	}

	public static void main(String[] args) {
		new E4().run();
	}

	int INF = 1 << 28;

	void run() {
		Scanner sc = new Scanner(System.in);
		int oo = sc.nextInt();
		for (int o = 1; o <= oo; o++) {
			que = new PriorityQueue<E>();
			int n = sc.nextInt();
			tank = new Tank();
			count=0;
			int[] h = new int[n + 3], b = new int[n + 3];
			h[0] = 100;
			b[0] = 0;
			h[n + 1] = 50;
			b[n + 1] = 100;
			h[n + 2] = 50;
			b[n + 2] = INF;
			for (int i = 1; i <= n; i++) {
				b[i] = sc.nextInt();
				h[i] = sc.nextInt();
			}
			n += 3;
			for (int i = 1; i < n; i++) {
				tank.bs.add(new Box(b[i - 1], b[i], h[i - 1], h[i], 0, 0));
			}
			int m = sc.nextInt();
			for (int i = 0; i < m; i++) {
				int f = sc.nextInt();
				double a = sc.nextDouble() / 30;
				for (Box box : tank.bs) {
					if (box.b1 < f && f < box.b2)
						box.f += a;
				}
			}
			debug(tank.bs);

			int l = sc.nextInt();
			res = new double[l];
			for (int i = 0; i < l; i++) {
				que.offer(new W(sc.nextInt(), sc.nextDouble(), i));
			}
			que.offer(tank.nextEvent());
			debug(que.size());
			while (!que.isEmpty()) {
				if (res[0] < 0)
					System.exit(1);
				que.poll().go();
				debug(que.peek().time());
				debug(tank.bs);
				debug(res);
				debug();
				if(count==l)
				break;
			}
			for (double r : res) {
				System.out.println(r);
			}
		}
	}
	int count;
	double[] res;
	PriorityQueue<E> que;
	Tank tank;

	class Tank {
		double time = 0;
		ArrayList<Box> bs = new ArrayList<Box>();

		E nextEvent() {
			Box res = bs.get(0);
			double min = res.flowtime();
			for (Box b : bs)
				if (b.flowtime() < min) {
					min = b.flowtime();
					res = b;
				}
			return new F(res, time + min);
		}

		Box getp(int p) {
			for (Box b : bs) {
				if (b.b1 < p && p < b.b2)
					return b;
			}
			return null;
		}
		void g(double ntime){
			double dt = ntime - time;
			time = ntime;
			for (Box b : bs) {
				b.go(dt);
			}
		}

		boolean go(double ntime, Box box) {
			debug("go");
			g(ntime);
			for (int i = 0; i < bs.size() - 1; i++)
				if (bs.get(i) == box) {
					Box nb = box.h1 <= box.h2 ? bs.get(i - 1) : bs.get(i + 1);
					if (eq(nb.h, box.h)) {
						Box nbox = box.combine(nb);
						bs.remove(box);
						bs.add(i, nbox);
						bs.remove(nb);
					} else {
						nb.f += box.f;
						box.f = 0;
					}
					return true;
				}
			return false;
		}
	}

	boolean eq(double a, double b) {
		return abs(a - b) < 1e-9;
	}

	class Box {
		int b1, b2, h1, h2;
		double f;//flow
		double h;//height

		Box(int b1, int b2, int h1, int h2, double f, double h) {
			this.b1 = b1;
			this.b2 = b2;
			this.h1 = h1;
			this.h2 = h2;
			this.f = f;
			this.h = h;
		}

		Box combine(Box b) {
			if (b2 != b.b1)
				return b.combine(this);
			return new Box(b1, b.b2, h1, b.h2, f + b.f, h);
		}

		@Override
		public String toString() {
			return "Box [b1=" + b1 + ", b2=" + b2 + ", f=" + f + ", h=" + h + ", h1=" + h1 + ", h2=" + h2 + "]";
		}

		void go(double dt) {
			h += dt * f / (b2 - b1);
		}

		double flowtime() {
			if (f == 0)
				return INF;
			int b = min(h1, h2);
			return (b - h) / f * (b2 - b1);
		}
	}

	abstract class E implements Comparable<E> {
		abstract double time();

		abstract void go();

		@Override
		public int compareTo(E o) {
			// TODO Auto-generated method stub
			return (int) signum(time() - o.time());
		}
	}

	class W extends E {
		int p;
		double time;
		int id;

		W(int p, double time, int id) {
			this.p = p;
			this.time = time;
			this.id = id;
		}

		@Override
		public double time() {
			// TODO Auto-generated method stub
			return time;
		}

		@Override
		public void go() {
			// TODO Auto-generated method stub
			debug(tank.getp(p));
			tank.g(time);
			res[id] = tank.getp(p).h;
			count++;
		}
	}

	class F extends E {
		double time;
		Box b;

		F(Box b, double time) {
			this.b = b;
			this.time = time;
		}

		@Override
		public double time() {
			// TODO Auto-generated method stub
			return time;
		}

		@Override
		public void go() {
			// TODO Auto-generated method stub
			if (tank.go(time, b)) {
				que.offer(tank.nextEvent());
			}
		}
	}
}
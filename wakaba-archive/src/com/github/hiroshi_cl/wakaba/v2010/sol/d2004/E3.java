package com.github.hiroshi_cl.wakaba.v2010.sol.d2004;

import java.io.*;
import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.math.*;
import static java.lang.Math.*;
import static java.util.Arrays.*;
import static java.util.Collections.*;
import static java.math.BigInteger.*;

class E3 {

	static final int MAXH = 50;
	static final int O = 30;
	static final double EPS = 1e-9;
	public void run() {
		Scanner sc = new Scanner(System.in);
		int D = sc.nextInt();
		while (D-- > 0) {
			int N = sc.nextInt();
			int[] B = new int[N + 2];
			int[] H = new int[N + 2];
			for(int i = 1; i <= N; i++) {
				B[i] = sc.nextInt();
				H[i] = sc.nextInt();
			}
			B[0] = 0;
			B[N+1] = 100;
			H[0] = H[N+1] = 50;
			int M = sc.nextInt();
			int[] F = new int[M];
			int[] A = new int[M];
			for(int i = 0; i < M; i++) {
				F[i] = sc.nextInt();
				A[i] = sc.nextInt();
			}
			int L = sc.nextInt();
			int[] P = new int[L];
			int[] T = new int[L];
			for(int i = 0; i < L; i++) {
				P[i] = sc.nextInt();
				T[i] = sc.nextInt();
			}

			tank = new ArrayList<Tank>();
			for(int i = 0; i <= N; i++)
				tank.add(new Tank(B[i], B[i+1], H[i], H[i+1]));
			for(int i = 0; i < M; i++)
				for(Tank t : tank)
					if(t.l < F[i] && F[i] < t.r)
						t.in += A[i];
			ptime = 0;
			ans = new ArrayList<Pair>();

			Queue<Event> que = new PriorityQueue<Event>();
			for(Tank t : tank)
				if(t.in > 0)
					que.offer(new Fill(t.vol() / t.in, t));
			for(int i = 0; i < L; i++)
				que.offer(new Look(T[i], P[i], i));

			while(!que.isEmpty()) {
				Event e = que.poll();
				double now = e.time;
				for(Tank t : tank)
					t.h += t.in * (now - ptime) / t.size();
				ptime = now;
				que.addAll(e.go());
			}

			sort(ans);
			for(Pair p : ans)
				System.out.printf("%.5f%n", p.depth);
		}
	}

	List<Tank> tank;
	double ptime;
	List<Pair> ans;

	final List<Event> hoge = new ArrayList<Event>();

	class Pair implements Comparable<Pair> {
		final int key;
		final double depth;
		public Pair(int key, double depth) {
			this.key = key;
			this.depth = depth;
		}
		@Override
		public int compareTo(Pair o) {
			return key - o.key;
		}
	}

	class Tank {
		final int l, r, lh, rh;
		int in = 0;
		double h = 0;
		public Tank(int l, int r, int lh, int rh) {
			this.l = l;
			this.r = r;
			this.lh = lh;
			this.rh = rh;
		}
		double vol() {
			return (r - l) * min(lh, rh) * O;
		}
		double size() {
			return (r - l) * O;
		}
		boolean filled() {
			return h > min(lh, rh) - EPS;
		}
		@Override
		public String toString() {
			return String.format("Tank [h=%.3f, in=%d]",h,in);
		}

	}

	abstract class Event implements Comparable<Event> {
		final double time;
		final int type;

		@Override
		public int compareTo(Event o) {
			return (int)signum(time != o.time ? time - o.time : type - o.type);
		}

		public Event(double time, int type) {
			this.time = ptime + time;
			this.type = type;
		}

		abstract List<Event> go();
	}

	class Look extends Event {
		final int p, k;

		public Look(double time, int p, int k) {
			super(time, 1);
			this.p = p;
			this.k = k;
		}

		@Override
		public List<Event> go() {
			for(Tank t : tank)
				if(t.l < p && p < t.r)
					ans.add(new Pair(k, t.h));
			return hoge;
		}
	}

	class Fill extends Event {
		final Tank t;

		public Fill(double time, Tank t) {
			super(time, 0);
			this.t = t;
		}
		@Override
		List<Event> go() {
			if(t.h > MAXH - EPS) {
				t.in = 0;
				return hoge;
			}
			if(t.in == 0 || !t.filled())
				return hoge;

			int idx = tank.indexOf(t);

			if(idx < 0)
				return hoge;

			List<Event> le = new ArrayList<Event>();
			if(t.lh < t.rh) {
				Tank nbt = tank.get(idx-1);
				if(nbt.h > nbt.rh - EPS) {
					Tank nxt = new Tank(nbt.l, t.r, nbt.lh, t.rh);
					nxt.in = nbt.in + t.in;
					nxt.h = t.lh;
					le.add(new Fill(nxt.size() * (min(nxt.lh, nxt.rh) - t.lh) / nxt.in, nxt));
					tank.set(idx-1, nxt);
					tank.remove(idx);
				}
				else {
					nbt.in += t.in;
					t.in = 0;
					le.add(new Fill(nbt.size() * (min(nbt.lh, nbt.rh) - nbt.h) / nbt.in, nbt));
				}
			}
			else {
				Tank nbt = tank.get(idx+1);
				if(nbt.h > nbt.lh - EPS) {
					Tank nxt = new Tank(t.l, nbt.r, t.lh, nbt.rh);
					nxt.in = nbt.in + t.in;
					nxt.h = t.rh;
					le.add(new Fill(nxt.size() * (min(nxt.lh, nxt.rh) - t.rh) / nxt.in, nxt));
					tank.set(idx+1, nxt);
					tank.remove(idx);
				}
				else {
					nbt.in += t.in;
					t.in = 0;
					le.add(new Fill(nbt.size() * (min(nbt.lh, nbt.rh) - nbt.h) / nbt.in, nbt));
				}
			}

			return le;
		}
	}
}

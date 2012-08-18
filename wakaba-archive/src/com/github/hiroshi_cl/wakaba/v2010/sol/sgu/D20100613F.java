package com.github.hiroshi_cl.wakaba.v2010.sol.sgu;

import java.util.*;
import java.math.*;
import static java.lang.Math.*;
import static java.util.Arrays.*;
import static java.util.Collections.*;
import static java.math.BigInteger.*;

public class D20100613F {
	public static void main(String[] args) {
		new D20100613F().run();
	}

	void debug(Object... os) {
		// System.err.println(deepToString(os));
	}

	void run() {
		MyScanner sc = new MyScanner();
		int N = sc.nextInt(), X = sc.nextInt() - 1, Y = sc.nextInt() - 1;
		int M = sc.nextInt();
		V[] vs = new V[N];
		for (int i = 0; i < N; i++) {
			vs[i] = new V(i);
		}
		for (int i = 0; i < M; i++) {
			int s = sc.nextInt() - 1, t = sc.nextInt() - 1, l = sc.nextInt();
			vs[s].es.add(new E(vs[t], l));
			vs[t].es.add(new E(vs[s], l));
		}
		PriorityQueue<E> que = new PriorityQueue<E>();
		E init = new E(vs[X], 0);
		que.offer(init);
		init.to.d = 0;
		while (!que.isEmpty()) {
			V v = que.poll().to;
			if (v == vs[Y])
				break;
			for (E e : v.es) {
				if (e.to.d > e.d + v.d) {
					e.to.d = e.d + v.d;
					// debug(e.to.d);
					que.offer(new E(e.to, e.to.d));
				}
			}
		}
		if (vs[Y].d == INF) {
			StringBuilder res = new StringBuilder("");
			for (int i = 0; i < N; i++) {
				res.append(0);
				if (i < N - 1)
					res.append(' ');
			}
			System.out.println(res.toString());
			return;
		}

		// V[] us = new V[N];
		// debug(0);
		que.clear();
		que.offer(new E(vs[Y], 0));
		int d;
		HashSet<V> set = new HashSet<V>();
		while (!que.isEmpty()) {
			d = que.peek().to.d;
			set.clear();
			while (!que.isEmpty() && que.peek().to.d == d) {
				if (!set.contains(que.peek().to))
					set.add(que.poll().to);
			}
			for (V v : set) {
				v.res = set.size() + que.size();
			}
			for (V v : set) {
				for (E e : v.es) {
					if (e.d == v.d - e.to.d) {
						que.offer(new E(e.to, -e.to.d));
					}
				}
			}
		}
		StringBuilder res = new StringBuilder("");
		for (int i = 0; i < N; i++) {
			res.append(vs[i].res);
			if (i < N - 1)
				res.append(' ');
		}
		System.out.println(res.toString());
	}

	int INF = 1 << 28;

	class V implements Comparable<V> {
		ArrayList<E> es = new ArrayList<E>();
		int id;
		int res = 0;
		int d = INF;

		V(int id) {
			this.id = id;
		}

		@Override
		public int compareTo(V o) {
			// TODO Auto-generated method stub
			return id - o.id;
		}

		@Override
		public int hashCode() {
			// TODO Auto-generated method stub
			return id;
		}

	}

	class E implements Comparable<E> {
		V to;

		int d;

		public E(V to, int d) {
			super();
			this.to = to;
			this.d = d;
		}

		@Override
		public int compareTo(E o) {
			return d - o.d;
		}

	}

	class MyScanner {
		Scanner sc = new Scanner(System.in);

		int nextInt() {
			try {
				int c = System.in.read();
				if (c == -1)
					return c;
				while (c != '-' && (c < '0' || '9' < c)) {
					c = System.in.read();
					if (c == -1)
						return c;
				}
				if (c == '-')
					return -nextInt();
				int res = 0;
				do {
					res *= 10;
					res += c - '0';
					c = System.in.read();
				} while ('0' <= c && c <= '9');
				return res;
			} catch (Exception e) {
				return -1;
			}
		}
	}
}

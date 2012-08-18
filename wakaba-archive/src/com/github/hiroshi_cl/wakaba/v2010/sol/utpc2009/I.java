package com.github.hiroshi_cl.wakaba.v2010.sol.utpc2009;

import static java.lang.Math.min;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class I {
	V[] vs;

	void run() {
		Scanner sc = new Scanner(System.in);
		while (sc.hasNext()) {
			int n = sc.nextInt() + 1, m = sc.nextInt();
			vs = new V[n];
			for (int i = 0; i < n; i++)
				vs[i] = new V(i);
			int natume = sc.nextInt(), lenon = sc.nextInt();
			for (int i = 0; i < m; i++) {
				int x = sc.nextInt(), y = sc.nextInt();
				if (sc.next().equals("N")) {
					vs[x].nd.add(vs[y]);
					vs[y].nd.add(vs[x]);
				} else {
					vs[x].ld.add(vs[y]);
					vs[y].ld.add(vs[x]);
				}
			}
			assignLen(lenon);
			if (vs[0].len) {
				System.out.println(0);
				continue;
			}
			assignSum(0);
			bfsLen();
			bfsSum();
			bfsNat(natume);
			int res = INF;
			for (V v : vs)
				res = min(res, v.lenStep + v.sumStep + v.natStep);
			System.out.println(res);
		}
	}

	void assignLen(int id) {
		vs[id].len = true;
		Queue<V> que = new LinkedList<V>();
		que.offer(vs[id]);
		while (!que.isEmpty()) {
			V v = que.poll();
			for (V nv : v.ld) {
				if (!nv.len) {
					nv.len = true;
					que.offer(nv);
				}
			}
		}
	}

	void assignSum(int id) {
		vs[id].sum = true;
		Queue<V> que = new LinkedList<V>();
		que.offer(vs[id]);
		while (!que.isEmpty()) {
			V v = que.poll();
			for (V nv : v.ld) {
				if (!nv.sum) {
					nv.sum = true;
					que.offer(nv);
				}
			}
		}
	}

	void bfsLen() {
		Queue<V> que = new LinkedList<V>();
		for (V v : vs)
			if (v.len) {
				v.lenStep = 0;
				que.offer(v);
			}
		while (!que.isEmpty()) {
			V v = que.poll();
			for (V nv : v.nd) {
				if (nv.lenStep > v.lenStep + 1) {
					nv.lenStep = v.lenStep + 1;
					que.offer(nv);
				}
			}
		}
	}

	void bfsSum() {
		Queue<V> que = new LinkedList<V>();
		for (V v : vs)
			if (v.sum) {
				v.sumStep = 0;
				que.offer(v);
			}
		while (!que.isEmpty()) {
			V v = que.poll();
			for (V nv : v.nd) {
				if (nv.sumStep > v.sumStep + 1) {
					nv.sumStep = v.sumStep + 1;
					que.offer(nv);
				}
			}
		}
	}

	void bfsNat(int id) {
		Queue<V> que = new LinkedList<V>();
		vs[id].natStep = 0;
		que.offer(vs[id]);
		while (!que.isEmpty()) {
			V v = que.poll();
			for (V nv : v.nd) {
				if (nv.natStep > v.natStep + 1) {
					nv.natStep = v.natStep + 1;
					que.offer(nv);
				}
			}
		}
	}

	Integer INF = 1 << 27;

	class V {
		ArrayList<V> ld = new ArrayList<V>();
		ArrayList<V> nd = new ArrayList<V>();
		boolean len;
		boolean sum;
		int lenStep = INF;
		int sumStep = INF;
		int natStep = INF;
		int id;

		V(int id) {
			this.id = id;
		}
	}

	public static void main(String[] args) {
		new I().run();
	}
}
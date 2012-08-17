package com.github.hiroshi_cl.wakaba.v2010.scr.treeSet;

import java.util.*;

class Dijkstra {
	public static void main(String[] args) {
		new Dijkstra().run();
	}

	void run() {
		Scanner sc = new Scanner(System.in);
		for (;;) {
			int n = sc.nextInt();
			if (n == 0)
				break;
			V[] vs = new V[n];
			for (int i = 0; i < n; i++)
				vs[i] = new V(i);
			for (int m = sc.nextInt(); m > 0; m--)
				addEdge(vs[sc.nextInt()], vs[sc.nextInt()], sc.nextInt());
			dijkstra(vs[0], vs);
			for (V v : vs)
				System.out.println(v.tcost);
		}
		sc.close();
	}

	void addEdge(V a, V b, int c) {
		E to_b = new E(a, b, c);
		E to_a = new E(b, a, c);
		a.es.add(to_b);
		b.es.add(to_a);
	}

	// TreeSet でダイクストラ
	void dijkstra(V start, V[] vs) {
		start.tcost = 0;
		TreeSet<V> q = new TreeSet<V>();
		for (int i = 0; i < vs.length; i++)
			q.add(vs[i]);

		while (!q.isEmpty()) {
			V v = q.pollFirst();
			for (E e : v.es) {
				if (e.src.tcost + e.cost < e.dst.tcost) {
					q.remove(e.dst);
					e.dst.tcost = e.src.tcost + e.cost;
					q.add(e.dst);
				}
			}
		}
	}

	class V implements Comparable<V> {
		V(int i) {
			id = i;
		}

		ArrayList<E> es = new ArrayList<E>();
		int tcost = 1 << 24;
		int id; // コストが等しい頂点識別にidの設定が必要

		public int compareTo(V o) {
			return tcost == o.tcost ? id - o.id : tcost - o.tcost;
		}
	}

	class E {
		V src, dst;
		int cost;

		E(V s, V d, int c) {
			src = s;
			dst = d;
			cost = c;
		}
	}
}

/*
 * テストデータ作成
 * #include<stdio.h>
 * #include<stdlib.h>
 * 
 * #define NTEST 50
 * #define NMIN_V 500
 * #define NMAX_V 1000
 * #define MAXCOST 10
 * 
 * static int es[NMAX_V][NMAX_V];
 *
 * void test(int n, int m)
 * {
 *  for(int i=0;i<n;i++) for(int j=0;j<n;j++) es[i][j] = 0;
 *  printf("%d %d\n", n, m);
 *  for(int i=0;i<m;i++) {
 *    int src,dst;
 *    do{ src=rand()%n; dst=rand()%n; } while(src==dst || es[src][dst]);
 *    es[src][dst]=es[dst][src]=1;
 *    printf("%d %d %d\n",src,dst,1+rand()%(MAXCOST-1));
 *  }
 *  puts("");
 * }
 * int main()
 * {
 *  for(int i=0;i<NTEST;i++) {
 *    int n = NMIN_V+rand()%(NMAX_V-NMIN_V);
 *    int m = n+rand()%(n*(n-1)/2-n);
 *    test(n, m);
 *  }
 *  printf("0 0\n");
 *  return 0;
 * }
 */
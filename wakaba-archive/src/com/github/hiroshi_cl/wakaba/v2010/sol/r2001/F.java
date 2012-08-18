package com.github.hiroshi_cl.wakaba.v2010.sol.r2001;
import java.util.*;
import static java.lang.Math.*;
import static java.util.Arrays.*;
import static java.util.Collections.*;
public class F {
	public static void main(String[] args) {
		new F().run();
	}
	String[] ST = new String[] { "Hakodate", "Tokyo" };
	int[] TIME = new int[] { 0, 600 };
	int INF = 1 << 25;
	void run() {
		Scanner sc = new Scanner(System.in);
		for (;;) {
			int n = sc.nextInt();
			V[][] vs = new V[n * 2][601];
			if (n == 0) return;
			HashMap<String, Integer> map = new HashMap<String, Integer>();
			int k = 0;
			for (int i = 0; i < n; i++) {
				int[] ts = new int[2];
				int[] is = new int[2];
				for (int j = 0; j < 2; j++) {
					String s = sc.next();
					if (!map.containsKey(s)) map.put(s, k++);
					ts[j] = time2i(sc.next());
					is[j] = map.get(s);
				}
				int cost = sc.nextInt();
				if (ts[0] == -1 || ts[1] == -1) continue;
				for (int j = 0; j < 2; j++)
					if (vs[is[j]][ts[j]] == null) vs[is[j]][ts[j]] = new V(is[j], ts[j]);
				make(vs[is[0]][ts[0]], vs[is[1]][ts[1]], cost);
			}
			for (int i = 0; i < k; i++) {
				if (vs[i][0] == null) vs[i][0] = new V(i, 0);
				if (vs[i][600] == null) vs[i][600] = new V(i, 600);
				for (int j = 1, l = 0; j <= 600; j++) {
					while (vs[i][j] == null)
						j++;
					make(vs[i][l], vs[i][j], 0);
					l = j;
				}
			}
			for (l = 0; l < 2; l++)
				for (r = 0; r < 2; r++) {
					V from = vs[map.get(ST[l])][TIME[r]];
					from.cost[l][r] = 0;
					Queue<E> que = new PriorityQueue<E>();
					que.offer(new E(from, 0));
					while (!que.isEmpty()) {
						V v = que.poll().to;
						for (E e : v.es[r]) {
							if (e.to.cost[l][r] > v.cost[l][r] + e.cost) {
								e.to.cost[l][r] = v.cost[l][r] + e.cost;
								que.offer(e);
							}
						}
					}
				}
			int res = INF;
			for (int i = 0; i < k; i++) {
				ArrayList<V> list = new ArrayList<V>();
				for (int j = 0; j <= 600; j++)
					if (vs[i][j] != null) list.add(vs[i][j]);
				V[] us = list.toArray(new V[0]);
				for (int j = 0; j < us.length; j++) {
					int[] come = new int[2];
					fill(come, INF);
					int[] ret = new int[2];
					fill(ret, INF);
					for (int m = 0; m < 2; m++) {
						for (int p = j; p >= 0; p--)
							come[m] = min(come[m], us[p].cost[m][0]);
						for (int p = j; p < us.length; p++)
							if (us[p].time == 600 || us[p].time >= us[j].time + 30) ret[m] =
								min(ret[m], us[p].cost[m][1]);
					}
					res = min(res, come[0] + come[1] + ret[0] + ret[1]);
				}
			}
			System.out.println(res == INF ? 0 : res);
		}
	}
	int l, r;
	void make(V from, V to, int cost) {
		from.es[0].add(new E(to, cost));
		to.es[1].add(new E(from, cost));
	}
	class V {
		ArrayList<E> es[] = new ArrayList[2];
		int id;
		int time;
		int[][] cost = new int[2][2];
		V(int id, int time) {
			this.id = id;
			this.time = time;
			for (int i = 0; i < 2; i++) {
				es[i] = new ArrayList<E>();
				for (int j = 0; j < 2; j++)
					cost[i][j] = INF;
			}
		}
	}
	class E implements Comparable<E> {
		V to;
		int cost;
		E(V to, int cost) {
			this.to = to;
			this.cost = cost;
		}
		public int compareTo(E o) {
			return (cost + to.cost[l][r]) - (o.cost + o.to.cost[l][r]);
		}
	}
	int time2i(String s) {
		String[] ss = s.split(":");
		int res = (Integer.valueOf(ss[0]) - 8) * 60 + Integer.valueOf(ss[1]);
		if (0 <= res && res <= 600) return res;
		return -1;
	}
}
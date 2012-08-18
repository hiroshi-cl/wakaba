package com.github.hiroshi_cl.wakaba.v2010.sol.r2007;
import java.util.*;
public class G2 {
	static final int[] di = { 0, 1, 0, -1, 0 };
	static final int[] dj = { 0, 0, 1, 0, -1 };
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		for(int w = sc.nextInt(); w > 0; w = sc.nextInt()) {
			int h = sc.nextInt();
			int n = sc.nextInt();
			sc.nextLine();
			char[][] c = new char[h][];
			for(int i = 0; i < h; i++)
				c[i] = sc.nextLine().toCharArray();
//			int[] si = new int[n], sj = new int[n];
//			int[] gi = new int[n], gj = new int[n];
//			for(int i = 0; i < h; i++)
//			for(int j = 0; j < w; j++) {
//			char b = c[i][j];
//			if(Character.isLowerCase(b)) {
//			si[b-'a'] = i;
//			sj[b-'a'] = j;
//			} else if(Character.isUpperCase(b)) {
//			gi[b-'A'] = i;
//			gj[b-'A'] = j;
//			}
//			}
//			Dijkstra dk = new Dijkstra(c);
//			int kari = 0;
//			for(int i = 0; i < n; i++)
//			kari = Math.max(kari, dk.search(si[i], sj[i], gi[i], gj[i]));
//			System.out.println(kari);
			System.out.println(new AStar(c, n).search());
		}
	}
	static void debug(Object...os) {
		System.err.println(Arrays.deepToString(os));
	}
	static class Dijkstra {
		final char[][] map;
		final int m, n;
		final int INF = 1 << 20;
		Dijkstra(char[][] cs) {
			map = cs;
			m = cs.length;
			n = cs[0].length;
		}
		int search(int si, int sj, int gi, int gj) {
			int[][] min = new int[m][n];
			for(int i = 0; i < m; i++)
				for(int j = 0; j < n; j++)
					min[i][j] = INF;

			Queue<Edge> q = new PriorityQueue<Edge>();
			q.offer(new Edge(si, sj, min[si][sj] = 0));

			while(true) {
				Edge e = q.poll();
				if(e.i == gi && e.j == gj)
					return e.d;
				if(min[e.i][e.j] < e.d)
					continue;
				for(int d = 1; d < 5; d++) {
					int ni = e.i+di[d], nj = e.j+dj[d];
					if(map[ni][nj] != '#')
						if(e.d + 1 < min[ni][nj])
							q.offer(new Edge(ni, nj, min[ni][nj] = e.d + 1));
				}
			}
		}
	}
	static class Edge implements Comparable<Edge> {
		final int i, j, d;
		Edge(int iin,int jin, int din) {
			i = iin;
			j = jin;
			d = din;
		}
		public int compareTo(Edge o) {
			return d - o.d;
		}
	}
	static class AStar {
		final char[][] map;
		final int m, n;
		final int N;
		final int[] si, sj;
		final int[] gi, gj;
		final Dijkstra dk;
		final int INF = 1 << 20;
		AStar(char[][] cs, int nin) {
			map = cs;
			m = cs.length;
			n = cs[0].length;
			N = nin;
			si = new int[N]; sj = new int[N];
			gi = new int[N]; gj = new int[N];
			for(int i = 0; i < m; i++)
				for(int j = 0; j < n; j++) {
					char b = map[i][j];
					if(Character.isLowerCase(b)) {
						si[b-'a'] = i;
						sj[b-'a'] = j;
					} else if(Character.isUpperCase(b)) {
						gi[b-'A'] = i;
						gj[b-'A'] = j;
					}
				}
			dk = new Dijkstra(map);
		}
		int search() {
			Set<Integer> s = new HashSet<Integer>();
			s.add(state(si, sj));
			Queue<Edge2> q = new PriorityQueue<Edge2>();
			q.offer(new Edge2(si, sj, 0, min(si, sj)));

			while(true) {
				Edge2 e = q.poll();
				if(Arrays.equals(e.i, gi) && Arrays.equals(e.j, gj))
					return e.d;
				int[] a = new int[N];
				while(next(a, 5)) {
					int[] ni = new int[N], nj = new int[N];
					for(int k = 0; k < N; k++) {
						ni[k] = e.i[k] + di[a[k]];
						nj[k] = e.j[k] + dj[a[k]];
					}
					if(isValid(e.i, e.j, ni, nj) && s.contains(state(si, sj))) {
						debugMap(e.i, e.j, ni, nj);
						s.add(state(si, sj));
						q.offer(new Edge2(ni, nj, e.d + 1, min(si, sj)));
					}
				}
			}
		}
		int state(int[] si, int[] sj) {
			int res = 0;
			for(int k = 0; k < si.length; k++)
				res |= si[k] << k * 4;
			for(int k = 0; k < sj.length; k++)
				res |= sj[k] << k * 4 + 12;
			return res;
		}
		int min(int[] si, int[] sj) {
			int res = 0;
			for(int i = 0; i < N; i++)
				res = Math.max(dk.search(si[i], sj[i], gi[i], gj[i]), res);
			return res;
		}
		boolean next(int[] a, int max) {
			boolean res = false;
			for(int i = 0; i < a.length; i++)
				res |= a[i] + 1 < max;

			a[0]++;
			for(int i = 0; i + 1 < a.length; i++)
				if(a[i] == max) {
					a[i] = 0;
					a[i+1]++;
				}
			return res;
		}
		boolean isValid(int[] si, int[] sj, int[] ni, int[] nj) {
			boolean res = true;
			for(int k = 0; k < N; k++)
				res &= map[ni[k]][nj[k]] != '#';
			if(res)
				for(int p = 0; p < N; p++)
					for(int q = p + 1; q < N; q++)
						res &= !(ni[p] == ni[q] && nj[p] == nj[q]);
			if(res)
				for(int p = 0; p < N; p++)
					for(int q = p + 1; q < N; q++)
						res &= !(si[p] == ni[q] && sj[p] == nj[q] && si[q] == ni[p] && sj[q] == nj[p]);
			return res;
		}
		void debugMap(int[] si, int[] sj, int[] ni, int[] nj) {
			char[][] c = new char[m][n];
			char[][] d = new char[m][n];
			for(int i = 0; i < m; i++)
				for(int j = 0; j < n; j++)
					d[i][j] = c[i][j] = map[i][j] == '#' ? '#' : ' ';
			
			for(int k = 0; k < N; k++) {
				c[si[k]][sj[k]] = (char)('a' + k);
				d[ni[k]][nj[k]] = (char)('a' + k);
			}
			for(int i = 0; i < m; i++)
				System.err.println(new String(c[i]) + " " + new String(d[i]));
			System.err.println();
		}
	}
	static class Edge2 implements Comparable<Edge2> {
		final int[] i, j;
		final int d, h;
		Edge2(int[] iin,int[] jin, int din, int hin) {
			i = iin;
			j = jin;
			d = din;
			h = hin;
		}
		public int compareTo(Edge2 o) {
			if(h + d == o.h + o.d)
				return h - o.h;
			else
				return h - o.h + d - o.d;
		}
	}
}

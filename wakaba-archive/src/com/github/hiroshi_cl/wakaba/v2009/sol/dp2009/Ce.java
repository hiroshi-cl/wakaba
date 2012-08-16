package com.github.hiroshi_cl.wakaba.v2009.sol.dp2009;
import java.math.*;
import java.util.*;
import static java.lang.Math.*;
import static java.math.BigInteger.*;

public class Ce {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		for(int N = sc.nextInt(); N > 0; N = sc.nextInt()) {
			int M = sc.nextInt();
			int L = sc.nextInt();
			boolean[][] map = new boolean[N][N];
			int[][] D = new int[N][N];
			int[][] E = new int[N][N];
			while(M-- > 0) {
				int A = sc.nextInt() - 1;
				int B = sc.nextInt() - 1;
				map[A][B] = map[B][A] = true;
				D[A][B] = D[B][A] = sc.nextInt();
				E[A][B] = E[B][A] = sc.nextInt();
			}

			boolean[][] visited = new boolean[N][L+1];
			Queue<Edge> q = new PriorityQueue<Edge>();
			q.offer(new Edge(0, 0, L));
			while(!q.isEmpty()) {
				Edge e = q.poll();
				if(visited[e.to][e.money])
					continue;
				visited[e.to][e.money] = true;
				if(e.to == N - 1) {
					System.out.println(e.enemy);
					break;
				}

				for(int i = 0; i < N; i++)
					if(map[e.to][i]) {
						if(D[e.to][i] <= e.money)
							q.offer(new Edge(e.enemy, i, e.money - D[e.to][i]));
						q.offer(new Edge(e.enemy + E[e.to][i], i, e.money));
					}
			}
		}
	}

	static class Edge implements Comparable<Edge> {
		final int enemy;
		final int to, money;
		Edge(int e, int t, int m) {
			enemy = e;
			to = t;
			money = m;
		}
		@Override
		public int compareTo(Edge o) {
			return enemy - o.enemy;
		}
	}
}

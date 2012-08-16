package com.github.hiroshi_cl.wakaba.v2009.sol.d2005.pku2688;
import java.util.*;

class Main {
	static final int INF = 1 << 20;
	static final int[] di = { 1, 0, -1, 0 };
	static final int[] dj = { 0, 1, 0, -1 };
	int w, h;
	void run() {
		Scanner sc = new Scanner(System.in);
		while(true) {
			w = sc.nextInt();
			h = sc.nextInt();
			if((w|h) == 0)
				break;
			List<Integer> lp = new ArrayList<Integer>();
			lp.add(0);
			char[][] map = new char[h][];
			for(int i = 0; i < h; i++)
				map[i] = sc.next().toCharArray();
			for(int i = 0; i < h; i++)
				for(int j = 0; j < w; j++)
					switch (map[i][j]) {
					case '*' :
						lp.add(cnv(i,j));
						break;
					case 'o' :
						lp.set(0, cnv(i, j));
						break;
					}

			int n = lp.size();
			int[][] dist = new int[n][n];
			for(int i = 0; i < n; i++)
				for(int j = 0; j < n; j++)
					dist[i][j] = INF;
			for(int k = 0; k < n; k++) {
				int s = lp.get(k);
				boolean[] visited = new boolean[h*w];
				Queue<Edge> qe = new PriorityQueue<Edge>();
				qe.offer(new Edge(s, 0));
				while(!qe.isEmpty()) {
					Edge e = qe.poll();
					if(visited[e.next] || e.cost >= INF)
						continue;
					visited[e.next] = true;
					boolean f = false;
					for(int t = k + 1; t < n; t++)
						if(lp.get(t) == e.next)
							dist[k][t] = dist[t][k] = e.cost;
						else if(dist[k][t] == INF)
							f = true;
					if(!f)
						break;
					int i = e.next / w;
					int j = e.next % w;
					for(int d = 0; d < di.length; d++)
						if(in(i, j, d) && map[i+di[d]][j+dj[d]] != 'x')
							qe.offer(new Edge(cnv(i+di[d], j+dj[d]), e.cost + 1));
				}
			}

			int m = 1 << n;
			int[][] dp = new int[n][m];
			for(int i = 0; i < n; i++)
				for(int j = 0; j < m; j++)
					dp[i][j] = INF;
			Queue<Edge> qe = new PriorityQueue<Edge>();
			qe.offer(new Edge(1, 0));
			while(!qe.isEmpty()) {
				Edge e = qe.poll();
				int i = e.next >> n;
				int j = e.next & (m - 1);
				if(dp[i][j] < INF || e.cost >= INF)
					continue;
				dp[i][j] = e.cost;
				for(int k = 0; k < n; k++)
					if((j & (1 << k)) == 0)
						qe.offer(new Edge((k << n) | j | (1 << k), e.cost + dist[i][k]));
			}

			int ans = INF;
			for(int i = 0; i < n; i++)
				ans = Math.min(ans, dp[i][m-1]);
			if(ans < INF)
				System.out.println(ans);
			else
				System.out.println(-1);
		}
	}

	int cnv(int i, int j) {
		return i * w + j;
	}

	boolean in(int i, int j, int d) {
		return i+di[d] >= 0 && j+dj[d] >= 0 && i+di[d] < h && j+dj[d] < w;
	}

	class Node extends ArrayList<Edge> {};

	class Edge implements Comparable<Edge> {
		int cost;
		int next;
		public Edge(int n, int c) {
			next = n;
			cost = c;
		}

		public int compareTo(Edge o) {
			return cost - o.cost;
		}
	}
}

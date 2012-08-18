package com.github.hiroshi_cl.wakaba.v2010.sol.dp2009;
import java.util.*;
public class Ee {
	static final int[][] di = { { 1, 0, -1, 0 }, { 1, 0, -1, 0 }};
	static final int[][] dj = { { 0, -1, 0, 1 }, { 0, 1, 0, -1 }};
	static int W, H;
	static char[][][] maps;
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		for(W = sc.nextInt(); W > 0; W = sc.nextInt()) {
			H = sc.nextInt();
			maps = new char[2][H][];
			for(int i = 0; i < H; i++)
				for(int k = 0; k < 2; k++)
					maps[k][i] = sc.next().toCharArray();
			Pair[] s = new Pair[2], g = new Pair[2];
			for(int k = 0; k < 2; k++)
				for(int i = 0; i < H; i++)
					for(int j = 0; j < W; j++)
						if(maps[k][i][j] == 'L' || maps[k][i][j] == 'R')
							s[k] = new Pair(i, j);
						else if(maps[k][i][j] == '%')
							g[k] = new Pair(i, j);

			boolean[][][][] visited = new boolean[H][W][H][W];
			Queue<Pair[]> q = new LinkedList<Pair[]>();
			boolean res = false;
			q.offer(s);
			while(!q.isEmpty()) {
				Pair[] st = q.poll();
				if(st[0].equals(g[0]) && st[1].equals(g[1])) {
					res = true;
					break;
				}
				else if(st[0].equals(g[0]) || st[1].equals(g[1]))
					continue;

				if(visited[st[0].I][st[0].J][st[1].I][st[1].J])
					continue;
				visited[st[0].I][st[0].J][st[1].I][st[1].J] = true;

				for(int d = 0; d < 4; d++) {
					Pair[] nps = new Pair[2];
					for(int k = 0; k < 2; k++)
						nps[k] = mv(st, k, d);
					q.offer(nps);
				}
			}
			System.out.println(res ? "Yes" : "No");
		}
	}
	static Pair mv(Pair[] st, int k, int d) {
		int i = st[k].I + di[k][d];
		int j = st[k].J + dj[k][d];
		if(i >= 0 && i < H && j >= 0 && j < W && maps[k][i][j] != '#')
			return new Pair(i, j);
		else
			return st[k];
	}
	static class Pair {
		final int I, J;
		public Pair(int i, int j) {
			I = i; J = j;
		}
		@Override
		public String toString() {
			return "(" + I + "," + J +")";
		}
		@Override
		public boolean equals(Object obj) {
			Pair p = (Pair) obj;
			return I == p.I && J == p.J;
		}
	}
}

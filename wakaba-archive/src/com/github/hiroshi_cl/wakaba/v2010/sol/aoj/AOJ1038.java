package com.github.hiroshi_cl.wakaba.v2010.sol.aoj;

import java.io.*;
import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.math.*;
import static java.lang.Math.*;
import static java.util.Arrays.*;
import static java.util.Collections.*;
import static java.math.BigInteger.*;

class AOJ1038 {
	static final int[] di = { 1, 0, -1, 0 };
	static final int[] dj = { 0, 1, 0, -1 };

	public void run() {
		Scanner sc = new Scanner(System.in);
		main: while (sc.hasNext()) {
			H = sc.nextInt();
			W = sc.nextInt();
			if (H == 0 && W == 0)
				return;
			map = new char[H][];
			for (int i = 0; i < H; i++)
				map[i] = sc.next().toCharArray();
			int si = 0, sj = 0;
			int[] ci = new int[3];
			int[] cj = new int[3];
			for (int i = 0, p = 0, c = 0; i < H; i++)
				for (int j = 0; j < W; j++)
					switch (map[i][j]) {
					case '@':
						si = i;
						sj = j;
						map[i][j] = '.';
						break;
					case 'w':
						map[i][j] = (char) ('0' + p++);
						break;
					case 'c':
						ci[c] = i;
						cj[c] = j;
						map[i][j] = '.';
						c++;
						break;
					}
			Queue<State> que = new PriorityQueue<State>();
			Set<State> set = new HashSet<State>();
			que.offer(new State(si, sj, ci, cj, new boolean[3], 0));
			while (!que.isEmpty()) {
				State s = que.poll();
				if (set.contains(s))
					continue;
				set.add(s);
				if (s.isGoal()) {
					System.out.println(s.dist);
					continue main;
				}
				List<State> ls = s.nexts();
				que.addAll(ls);
			}
			System.out.println(-1);
		}
	}

	int H, W;
	char[][] map;

	class State implements Comparable<State> {
		final int i, j;
		final int[] ci, cj;
		final boolean[] panel;
		final int dist;

		public State(int i, int j, int[] ci, int[] cj, boolean[] panel, int dist) {
			this.i = i;
			this.j = j;
			this.ci = ci;
			this.cj = cj;
			this.panel = panel;
			this.dist = dist;
		}

		boolean isGoal() {
			return map[i][j] == 'E';
		}

		List<State> nexts() {
			List<State> ret = new ArrayList<State>();
			// move
			move: for (int d = 0; d < 4; d++)
				switch (map[i + di[d]][j + dj[d]]) {
				case '0':
				case '1':
				case '2':
					if (!panel[map[i + di[d]][j + dj[d]] - '0'])
						continue move;
				case 'E':
				case '.':
					for (int k = 0; k < 3; k++)
						if (ci[k] == i + di[d] && cj[k] == j + dj[d])
							continue move;
					ret.add(new State(i + di[d], j + dj[d], ci, cj, panel, dist + 1));
				}
			// push
			push: for (int d = 0; d < 4; d++)
				for (int k = 0; k < 3; k++)
					if (ci[k] == i + di[d] && cj[k] == j + dj[d]) {
						int ni = ci[k], nj = cj[k];
						boolean[] np = panel.clone();
						loop: while (true) {
							char c = map[ni + di[d]][nj + dj[d]];
							if (c == '#')
								break loop;
							if (Character.isDigit(c) && !panel[c - '0']) {
								np[c - '0'] = true;
								ni = 0;
								nj = 0;
								break loop;
							}
							for (int kk = 0; kk < 3; kk++)
								if (ni + di[d] == ci[kk] && nj + dj[d] == cj[kk])
									break loop;
							ni += di[d];
							nj += dj[d];
						}
						if (ni != ci[k] || nj != cj[k]) {
							int[] ti = ci.clone();
							int[] tj = cj.clone();
							ti[k] = ni;
							tj[k] = nj;
							ret.add(new State(i, j, ti, tj, np, dist));
						}
					}
			return ret;
		}

		@Override
		public int compareTo(State o) {
			return dist - o.dist;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + Arrays.hashCode(ci);
			result = prime * result + Arrays.hashCode(cj);
			result = prime * result + Arrays.hashCode(panel);
			result = prime * result + i;
			result = prime * result + j;
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			State other = (State) obj;
			return (Arrays.equals(ci, other.ci) && Arrays.equals(cj, other.cj) && i == other.i && j == other.j && Arrays
					.equals(panel, other.panel));
		}

		@Override
		public String toString() {
			return "State [ci=" + Arrays.toString(ci) + ", cj=" + Arrays.toString(cj) + ", dist=" + dist + ", i=" + i
					+ ", j=" + j + ", panel=" + Arrays.toString(panel) + "]";
		}

	}
}

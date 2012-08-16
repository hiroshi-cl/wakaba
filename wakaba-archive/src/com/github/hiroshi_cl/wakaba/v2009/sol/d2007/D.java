package com.github.hiroshi_cl.wakaba.v2009.sol.d2007;
import java.util.*;
import static java.lang.Math.*;
public class D {
	static final int INF = 1 << 20;
	boolean[][] st, gl, im;
	boolean[][][][][] visited;
	int[][] map;
	int w, h;
	public static void main(String[] args) {
		new D().run();
	}
	void run() {
		Scanner sc = new Scanner(System.in);
		while(true) {
			w = sc.nextInt();
			h = sc.nextInt();
			if((w | h) == 0)
				break;
			st = new boolean[h][w];
			gl = new boolean[h][w];
			map = new int[h][w];
			for(int i = 0; i < h; i++)
				for(int j = 0; j < w; j++) {
					char c = sc.next().charAt(0);
					st[i][j] = c == 'S';
					gl[i][j] = c == 'T';
					if(Character.isDigit(c))
						map[i][j] = c - '0';
					else if(c == 'X')
						map[i][j] = INF;
					else
						map[i][j] = 0;
				}
			System.out.println(search());
		}
	}
	int search() {
		Queue<State> q = new PriorityQueue<State>();
		for(int ix = 0; ix < w; ix++)
			for(int jx = ix + 1; jx <= min(ix + 3, w - 1); jx++)
				for(int iy = 0; iy < h; iy++)
					for(int jy = max(iy - 3 + jx - ix, 0); jy <= min(iy + 3 - jx + ix, h - 1); jy++)
						if(st[iy][ix])
							q.offer(new State(ix, iy, jx, jy, 1, map[jy][jx]));
						else if(st[jy][jx])
							q.offer(new State(ix, iy, jx, jy, 0, map[iy][ix]));
		int[][][][] visited = new int[h][w][h][2];

		while(!q.isEmpty()) {
			State s = q.poll();
			if(gl[s.ly][s.lx] || gl[s.ry][s.rx])
				if(s.t < INF)
					return s.t;
				else
					return -1;
			if((visited[s.ly][s.lx][s.ry][s.cf] & (1<<s.rx)) > 0) 
				continue;
			visited[s.ly][s.lx][s.ry][s.cf] |= 1 << s.rx;
			if(s.cf == 0) {
				for(int i = 0; i < h; i++)
					for(int j = s.lx + 1; j < w; j++)
						if(abs(i - s.ly) + j - s.lx <= 3)
							if((visited[s.ly][s.lx][i][1] & (1 << j)) == 0)
								q.offer(new State(s.lx, s.ly, j, i, 1, s.t + map[i][j]));
			} else {
				for(int i = 0; i < h; i++)
					for(int j = 0; j < s.rx; j++)
						if(abs(i - s.ry) + s.rx - j <= 3)
							if((visited[i][j][s.ry][0] & (1 << s.rx)) == 0)
								q.offer(new State(j, i, s.rx, s.ry, 0, s.t + map[i][j]));
			}
		}

		return -1;
	}
	class State implements Comparable<State> {
		int lx, ly, rx, ry, cf;
		int t;
		State(int lx, int ly, int rx, int ry, int cf, int t) {
			this.lx = lx;
			this.ly = ly;
			this.rx = rx;
			this.ry = ry;
			this.cf = cf;
			this.t = t;
		}
		public int compareTo(State o) {
			return t - o.t;
		}
		@Override
		public String toString() {
			return String.format("[%d,%d,%d,%d,%d;%d]", lx, ly, rx, ry, cf, t);
		}
	}
}

package com.github.hiroshi_cl.wakaba.v2009.sol.d2008.wakaba090208;
import java.util.*;

public class Main {
	public static void main(String [] args) {
		new D().run();
	}
	static class D {
		void run() {
			Scanner sc = new Scanner(System.in);
			for(;;) {
				w = sc.nextInt();
				h = sc.nextInt();
				if (w==0||h==0) break;
				map = new int[h][w];
				for(int i=0;i<h;i++) {
					for(int j=0;j<w;j++) {
						map[i][j] = sc.nextInt();
					}
				}
				cost = new int[4];
				memo = new int[h][w][4];
				for(int i=0;i<4;i++) cost[i] = sc.nextInt();
				System.out.println(search());
			}
		}
		int w,h;
		int[] cost;
		int[][] map;
		int[] wdir = {1,0,-1,0};
		int[] hdir = {0,1,0,-1};
		int[][][] memo;
		
		int search() {
			Queue<State> q = new PriorityQueue<State>();
			q.offer(new State(0,0,0,0));
			while(true) {
				State s = q.poll();
				memo[s.h][s.w][s.d] = 1;
				if(s.h == h - 1 && s.w == w - 1)
					return s.cost;
				for(int i=0;i<4;i++) {
					int d = (i + s.d) % 4;
					if (s.h+hdir[d] >= h) continue;
					if (s.w+wdir[d] >= w) continue;
					if (s.h+hdir[d] < 0) continue;
					if (s.w+wdir[d] < 0) continue;
					if (memo[s.h+hdir[d]][s.w+wdir[d]][d] != 0) continue;
					q.offer(new State(s.h+hdir[d],s.w+wdir[d],d,s.cost+cost[i]));

				}
				if (map[s.h][s.w] != 4) {
					int d = (map[s.h][s.w]+s.d) % 4;
					if (s.h+hdir[d] >= h);
					else if (s.w+wdir[d] >= w);
					else if (s.h+hdir[d] < 0);
					else if (s.w+wdir[d] < 0);
					else if (memo[s.h+hdir[d]][s.w+wdir[d]][d] != 0);
					else q.offer(new State(s.h+hdir[d],s.w+wdir[d],d,s.cost));
				}
			}
		}
		static class State implements Comparable<State> {
			int h, w, d, cost;
			State(int hi, int wi, int di, int c) {
				h = hi;
				w = wi;
				d = di;
				cost = c;
			}
			public int compareTo(State o) {
				return cost - o.cost;
			}
		}
	}
}

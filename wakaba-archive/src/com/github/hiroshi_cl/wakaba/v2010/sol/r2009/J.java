package com.github.hiroshi_cl.wakaba.v2010.sol.r2009;
import java.util.*;
import java.math.*;
import java.io.*;
import static java.util.Arrays.*;
import static java.util.Collections.*;
import static java.lang.Math.*;
import static java.math.BigInteger.*;

public class J {
	void debug(Object... os) {
//		System.err.println(deepToString(os));
	}

	void run() {
		Scanner sc = new Scanner(System.in);
		try {
			sc = new Scanner(new File("J.in"));
		} catch (Exception e) {
		}
		for (;;) {
			n = sc.nextInt();
			if(n==0)return;
			boolean[][] bs = new boolean[n][n];
			int x=-1,y=-1;
			for (int i = 0; i < n; i++) {
				char[] cs=sc.next().toCharArray();
				for (int j = 0; j < n; j++) {
					bs[i][j] = cs[j]=='#';
					if(cs[j]=='@'){
						x=i;
						y=j;
					}
				}
			}
//			debug(bs);
			que=new LinkedList<State>();
			set=new HashSet<State>();
			State init = new State(bs,x,y,0);
			debug(init.bs);
			que.offer(init);
			set.add(init);
			int INF=1<<28;
			int res = INF;
			while(!que.isEmpty()){
				State now = que.poll();
//				debug(now.bs);
				if(now.isGoal()){
					res = now.step;
					break;
				}
				now.addNexts();
			}
			System.out.println(res==INF?-1:res);
		}
	}
	Queue<State> que=new LinkedList<State>();
	HashSet<State> set=new HashSet<State>();
	int n;
	int[] dx={1,1,1,0,0,-1,-1,-1};
	int[] dy={-1,0,1,-1,1,-1,0,1};
	class State implements Comparable<State> {
		@Override
		public int compareTo(State o) {
			// TODO Auto-generated method stub
			return step - o.step;
		}
		boolean isGoal(){
			for (int i = 0; i < n; i++) {
				for (int j = 0; j < n; j++) {
					if(bs[i][j])return false;
				}
			}
			return true;
		}
		void addNexts(){
			for (int d = 0; d < 8; d++) {
				int nx = x+dx[d],ny=y+dy[d];
				if(0<=nx && nx<n && 0<=ny && ny<n && !bs[nx][ny]){
//					bs[nx][ny] = true;
					State ns = new State(bs,nx,ny,step+1);
					ns.move();
					if(!set.contains(ns)){
//						debug(ns.bs);
						que.offer(ns);
						set.add(ns);
					}
//					bs[nx][ny] = false;
				}
			}
		}
		void move(){
			bs[x][y] = true;
			boolean[][] nb = new boolean[n][n];
			for (int i = 0; i < n; i++) {
				for (int j = 0; j < n; j++) {
					int count=0;
					for (int d = 0; d < 8; d++) {
						int nx = i+dx[d],ny=j+dy[d];
						if(0<=nx && nx<n && 0<=ny && ny<n && bs[nx][ny]){
							count++;
						}
					}
//					debug(count);
					if(bs[i][j]){
						if(count==2 || count==3){
							nb[i][j]=true;
						}
					}
					else{
						if(count==3){
							nb[i][j]=true;
						}
					}
				}
			}
			nb[x][y]=false;
			bs=nb;
		}

		int x, y;
		boolean[][] bs = new boolean[n][];
		int step;

		State(boolean[][] bs, int x, int y, int step) {
			for (int i = 0; i < n; i++) {
				this.bs[i] = bs[i].clone();
			}
			this.x = x;
			this.y = y;
			this.step = step;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + getOuterType().hashCode();
			for (int i = 0; i < n; i++)
				result = prime * result + Arrays.hashCode(bs[i]);
			result = prime * result + x;
			result = prime * result + y;
			return result;
		}

		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			State other = (State) obj;
			if (!getOuterType().equals(other.getOuterType()))
				return false;
			for (int i = 0; i < n; i++)
				if (!Arrays.equals(bs[i], other.bs[i]))
					return false;
			if (x != other.x)
				return false;
			if (y != other.y)
				return false;
			return true;
		}

		private J getOuterType() {
			return J.this;
		}

	}

	public static void main(String[] args) {
		new J().run();
	}
}

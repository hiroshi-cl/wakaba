package com.github.hiroshi_cl.wakaba.v2010.sol.r2003;
import java.io.*;
import java.math.*;
import java.util.*;
import static java.lang.Math.*;
import static java.math.BigInteger.*;
import static java.util.Arrays.*;
import static java.util.Collections.*;
class F {
	void debug(Object... os) {
		System.err.println(deepToString(os));
	}
	void run() {
		Scanner sc = new Scanner(System.in);
		int o = sc.nextInt();
		while (o-- > 0) {
			int[][] f = new int[4][8];
			for (int i = 0; i < 4; i++) {
				for (int j = 1; j < 8; j++) {
					f[i][j] = sc.nextInt();
					if(f[i][j]%10==1)f[i][j]=0;
				}
			}
			for (int i = 0; i < 4; i++) {
				f[i][0] = (i+1)*10+1;
			}
			Entry init = new Entry(f, 0);
			que=new LinkedList<Entry>();
			set=new HashSet<Entry>();
			que.offer(init);
			set.add(init);
			int res = -1;
			while (!que.isEmpty()) {
				Entry e = que.poll();
				e.tos();
				if (e.isGoal()) {
					res = e.step;
					break;
				}
				e.addnexts();
			}
			System.out.println(res);
		}
	}
	Queue<Entry> que = new LinkedList<Entry>();
	HashSet<Entry> set = new HashSet<Entry>();
	class Entry implements Comparable<Entry> {
		void tos(){
//			for (int i = 0; i < 4; i++) {
//				debug(f[i]);
//			}
//			debug();
		}
		int step;
		int[][] f;
		boolean isGoal() {
			for (int i = 0; i < 4; i++) {
				for (int j = 0; j < 7; j++) {
					if (f[i][j] != ( i + 1 ) * 10 + j + 1) return false;
				}
			}
			return true;
		}

		void addnexts(){
			for (int i = 0; i < 4; i++) {
				for (int j = 0; j < 8; j++) {
					if(f[i][j]==0){
						if(f[i][j-1]==0 || f[i][j-1]%10==7)continue;
						f[i][j] = f[i][j-1]+1;
						loop:for (int k = 0; k < 4; k++) {
							for (int l = 0; l < 8; l++) {
								if(!(k==i&&l==j) && f[k][l]==f[i][j]){
									f[k][l]=0;
									Entry ne=new Entry(f,step+1);
									f[k][l]=f[i][j];
									f[i][j]=0;
									if(!set.contains(ne)){
										set.add(ne);
										que.offer(ne);
									}
									break loop;
								}
							}
						}
					}
				}
			}
		}
		public Entry(int[][] g, int step) {
			this.step = step;
			f = new int[4][];
			for (int i = 0; i < 4; i++) {
				f[i] = g[i].clone();
			}
		}
		public int compareTo(Entry o) {
			// TODO 自動生成されたメソッド・スタブ
			return step - o.step;
		}
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + getOuterType().hashCode();
			for (int i = 0; i < f.length; i++) {
				result = prime * result + Arrays.hashCode(f[i]);
			}
			return result;
		}
		@Override
		public boolean equals(Object obj) {
			if (this == obj) return true;
			if (obj == null) return false;
			if (getClass() != obj.getClass()) return false;
			Entry other = (Entry) obj;
			if (!getOuterType().equals(other.getOuterType())) return false;
			for (int i = 0; i < f.length; i++) {
				if (!Arrays.equals(f[i], other.f[i])) return false;
			}
			return true;
		}
		private F getOuterType() {
			return F.this;
		}
	}
	public static void main(String[] args) {
		new F().run();
	}
}

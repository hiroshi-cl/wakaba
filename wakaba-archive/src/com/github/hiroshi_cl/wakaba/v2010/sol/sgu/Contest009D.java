package com.github.hiroshi_cl.wakaba.v2010.sol.sgu;
import java.math.*;
import java.util.*;
import static java.lang.Math.*;
import static java.math.BigInteger.*;
import static java.util.Arrays.*;
import static java.util.Collections.*;

public class Contest009D {
	void debug(Object... os) {
		System.err.println(deepToString(os));
	}
	void run() {
		Scanner sc = new Scanner(System.in);
		int n = sc.nextInt();
		boolean[][] f = new boolean[n][n];
		int m = 0;
		for (int i = 0; i < n; i++) {
			char[] cs = sc.next().toCharArray();
			for (int j = 0; j < n; j++) {
				f[i][j] = cs[j] == '1';
				if (f[i][j]) m++;
			}
		}
		if(m%2!=0){
			System.out.println("NO");return;
		}
		P[] ps = new P[m];
		for (int i = 0, k = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				if (f[i][j]) {
					ps[k++] = new P(i, j);
				}
			}
		}
//		debug(ps);
		int[][] res = null;
		for (int i = 1; i < m; i++) {
			boolean[] bs = make(ps, m, i);
			if(bs!=null){
				 res=new int[n][n];
				for (int j = 0; j < m; j++) {
					if(bs[j]){
						res[ps[j].x][ps[j].y] = 1;
					}
				}
				break;
			}
			 bs = make2(ps, m, i);
			if(bs!=null){
				res=new int[n][n];
				for (int j = 0; j < m; j++) {
					if(bs[j]){
						res[ps[j].x][ps[j].y] = 1;
					}
				}
				break;
			}
		}
		if(res==null){
			System.out.println("NO");
		}
		else{
			System.out.println("YES");
			for (int i = 0; i < n; i++) {
				for (int j = 0; j < n; j++) {
				System.out.print(res[i][j]);
				}
				System.out.println();
			}
		}
	}
	boolean[] make(P[] ps, int m, int to) {
		P[] qs = new P[m];
		HashMap<P, Integer> map = new HashMap<P, Integer>();
		for (int i = 0; i < m; i++) {
			qs[i] = ps[i].rot90();
			map.put(qs[i], i);
		}
//		debug(qs);
		P off = qs[to].sub(ps[0]);
		boolean[] bs = new boolean[m];
		int count = 0;
		for (int i = 0; i < m; i++) {
			if (!bs[i]) {
				if (map.containsKey(ps[i].add(off))) {
					bs[map.get(ps[i].add(off))] = true;
					count++;
				}
			}
		}
		return count==m/2 ? bs : null;
	}
	boolean[] make2(P[] ps, int m, int to) {
		P[] qs = new P[m];
		HashMap<P, Integer> map = new HashMap<P, Integer>();
		for (int i = 0; i < m; i++) {
			qs[i] = ps[i].rot90().rot90().rot90();
			map.put(qs[i], i);
		}
//		debug(qs);
		P off = qs[to].sub(ps[0]);
		boolean[] bs = new boolean[m];
		int count = 0;
		for (int i = 0; i < m; i++) {
			if (!bs[i]) {
				if (map.containsKey(ps[i].add(off))) {
					bs[map.get(ps[i].add(off))] = true;
					count++;
				}
			}
		}
		return count==m/2 ? bs : null;
	}
	class P {
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + getOuterType().hashCode();
			result = prime * result + x;
			result = prime * result + y;
			return result;
		}
		public boolean equals(Object obj) {
			if (this == obj) return true;
			if (obj == null) return false;
			if (getClass() != obj.getClass()) return false;
			P other = (P) obj;
			if (!getOuterType().equals(other.getOuterType())) return false;
			if (x != other.x) return false;
			if (y != other.y) return false;
			return true;
		}
		public String toString() {
			return x + " " + y;
		}
		int x, y;
		P(int x, int y) {
			this.x = x;
			this.y = y;
		}
		P mul(int d) {
			return new P(x * d, y * d);
		}
		double dot(P p) {
			return x * p.x + y * p.y;
		}
		double det(P p) {
			return x * p.y - y * p.x;
		}
		P add(P p) {
			return new P(x + p.x, y + p.y);
		}
		// P div(double d){
		// return new P(x/d,y/d);
		// }
		P sub(P p) {
			return new P(x - p.x, y - p.y);
		}
		// P rot(double t){
		// return new P(x*cos(t) - y*sin(t) ,x*sin(t) + y*cos(t));
		// }
		P rot90() {
			return new P(-y, x);
		}
		// P
		private Contest009D getOuterType() {
			return Contest009D.this;
		}
	}
	public static void main(String[] args) {
		new Contest009D().run();
	}
}

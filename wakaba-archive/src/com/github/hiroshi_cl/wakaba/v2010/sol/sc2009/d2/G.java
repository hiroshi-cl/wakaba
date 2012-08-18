package com.github.hiroshi_cl.wakaba.v2010.sol.sc2009.d2;
import java.io.*;
import java.math.*;
import java.util.*;
import static java.lang.Math.*;
import static java.math.BigInteger.*;
import static java.util.Arrays.*;
import static java.util.Collections.*;
class G {
	void debug(Object... os) {
		System.err.println(deepToString(os));
	}
	void run() {
		Scanner sc = new Scanner(System.in);
		for (int o = 1;; o++) {
			try {
				sc = new Scanner(new File(o + ".in"));
				System.setOut(new PrintStream(o + ".out"));
			} catch (Exception e) {
				return;
			}

			int n=sc.nextInt(),m=sc.nextInt();
			P[] ps=new P[n];
			double[] vs=new double[n];
			for (int i = 0; i < n; i++) {
				ps[i]=new P(sc.nextDouble(),sc.nextDouble());
				vs[i]=sc.nextDouble();
			}
			P[] bs=new P[m];
			for (int i = 0; i < m; i++) {
				bs[i]=new P(sc.nextDouble(),sc.nextDouble());
			}
			need = new double[n][m];
			for (int i = 0; i < n; i++) {
				for (int j = 0; j < m; j++) {
					need[i][j] = ps[i].dist(bs[j]) / vs[i];
				}
			}
			double low = 0 , high=1<<28;
			do{
				double mid = (low+high)/2;
				if(can(ps,vs,bs,mid,n,m)){
					high =mid;
				}
				else low=mid;
			}while((high-low)>EPS);
			System.out.println(high);
			Scanner sc1, sc2;
			try {
				sc1 = new Scanner(new File(o + ".out"));
				sc2 = new Scanner(new File(o + ".diff"));
				boolean ok = true;
				while (sc1.hasNext()) {
					if (!eq(sc1.nextDouble(),sc2.nextDouble())) {
						ok = false;
						break;
					}
				}
				if (sc2.hasNext()) ok = false;
				System.err.println(ok);
			} catch (Exception e) {}
		}
	}
	double[][] need;
	boolean can(P[] ps,double[] vs,P[] bs,double time,int n,int m){
		boolean[][] graph=new boolean[n][m];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				graph[i][j] = time >= need[i][j];
			}
		}
		return new BipartiteMatching().bipartiteMatching(graph) == m;
	}
	public class BipartiteMatching {
	    int n, m;
	    boolean[][] graph;
	    boolean[] visited;
	    int[] match;
	    int bipartiteMatching(boolean[][] graph) {
	        n = graph.length;
	        m = graph[0].length;
	        this.graph = graph;
	        match = new int[m];
	        fill(match, -1);
	        int res = 0;
	        for (int i = 0; i < n; i++) {
	            visited = new boolean[m];
	            if (go(i)) res++;
	        }
	        return res;
	    }
	    boolean go(int v) {
	        for (int i = 0; i < m; i++)  if (!visited[i] && graph[v][i] && match[i] == -1) {
	            visited[i] = true;
	            match[i] = v;
	            return true;
	        }
	        for (int i = 0; i < m; i++) if (!visited[i] && graph[v][i]) {
	            visited[i] = true;
	            if (go(match[i])) {
	                match[i] = v;
	                return true;
	            }
	        }
	        return false;
	    }
	}

	double EPS = 1e-7;
	boolean eq(double a, double b) {
		return abs(a - b) < EPS;
	}
	boolean lt(double a, double b) {
		return b - a > EPS;
	}
	class P implements Comparable<P> {
		public int compareTo(P p) {
			// TODO 自動生成されたメソッド・スタブ
			return x == p.x ? (int) signum(y - p.y) : (int) signum(x - p.x);
		}
		@Override
		public String toString() {
			// TODO 自動生成されたメソッド・スタブ
			return "( " + x + " , " + y + " )";
		}
		P rot(double t) {
			return new P(x * cos(t) - y * sin(t), x * sin(t) + y * cos(t));
		}
		double x, y;
		P(double x, double y) {
			this.x = x;
			this.y = y;
		}
		boolean equals(P p) {
			return eq(dist(p), 0);
		}
		P sub(P o) {
			return new P(x - o.x, y - o.y);
		}
		double dist(P p) {
			return sub(p).norm();
		}
		P add(P p) {
			return new P(x + p.x, y + p.y);
		}
		P div(double d) {
			return new P(x / d, y / d);
		}
		double norm() {
			return sqrt(x * x + y * y);
		}
		double det(P p) {
			return x * p.y - y * p.x;
		}
		double angle(P p) {
			double res = angle() - p.angle();
			if (res < -PI) res += 2 * PI;
			if (res > PI) res -= 2 * PI;
			return res;
		}
		double angle() {
			return atan2(y, x);
		}
	}

	public static void main(String[] args) {
		new G().run();
	}
}

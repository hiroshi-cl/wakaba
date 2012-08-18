package com.github.hiroshi_cl.wakaba.v2010.sol.rp2009;
import java.util.*;
import java.math.*;
import java.io.*;
import static java.math.BigInteger.*;
import static java.lang.Math.*;
import static java.util.Arrays.*;
import static java.util.Collections.*;

public class G {
	void debug(Object... os) {
//		System.err.println(deepToString(os));
	}

	void run() {
		Scanner sc = new Scanner(System.in);
		try {
			sc = new Scanner(new File("G.txt"));
		} catch (Exception e) {
		}
		for (;;) {
			int n = sc.nextInt();
			if (n == 0)
				return;
			P[] ps = new P[n];
			double[] rs = new double[n];
			P s = new P(sc.nextDouble(), sc.nextDouble(), 0);
			P t = new P(sc.nextDouble(), sc.nextDouble(), 0);
			for (int i = 0; i < n; i++) {
				ps[i] = new P(sc.nextDouble(), sc.nextDouble(), sc.nextDouble());
			}
			ArrayList<P> slist = new ArrayList<P>();
			ArrayList<P> tlist = new ArrayList<P>();
			for (int i = 0; i < n; i++) {
				if (ps[i].contains(s) && !ps[i].contains(t)) {
					slist.add(ps[i]);
				}
				if (!ps[i].contains(s) && ps[i].contains(t)) {
					tlist.add(ps[i]);
				}
			}
			P[] ss = slist.toArray(new P[0]);
			P[] ts = tlist.toArray(new P[0]);
			int sl = ss.length;
			// for (int i = 0; i < sl; i++) {
			//
			// }
			for (int i = 0; i < sl; i++) {
				for (int j = 0; j < sl; j++)
					if (i != j) {
						if (lt(ss[i].dist(ss[j]) + ss[i].r, ss[j].r)) {
							ss[j].es.add(ss[i]);
						}
					}
			}
			int tl=ts.length;
			for (int i = 0; i < tl; i++) {
				for (int j = 0; j < tl; j++)
					if (i != j) {
						if (lt(ts[i].dist(ts[j]) + ts[i].r, ts[j].r)) {
							ts[j].es.add(ts[i]);
						}
					}
			}
			debug(ss.length);
			debug(ts.length);

			for (int i = 0; i < sl; i++) {
				int tmp=ss[i].dfs();
//				d
			}
			for (int i = 0; i < tl; i++) {
				ts[i].dfs();
			}
			int res =0;
			for(P p:ss){
				res =max(res,p.max);
			}
			for(P p:ts){
				res = max(res,p.max);
			}

			for(P p:ss){
				for(P r:ts){
					if(lt(p.r + r.r , p.dist(r))){
						res = max(res,r.max + p.max);
					}
				}
			}
			System.out.println(res);
		}
	}

	double EPS = 1e-13;

	boolean lt(double a, double b) {
		return a < b - EPS;
	}

	class P {
		int dfs(){
			if(max>0)return max;
			max = 1;
			for(P p:es){
				max = max(max,p.dfs()+1);
			}
			return max;
		}
		ArrayList<P> es = new ArrayList<P>();
		double x, y, r;
		int max = 0;

		double dist(P p) {
			return sub(p).norm();
		}

		boolean contains(P p) {
			return lt(dist(p), r);
		}

		double norm() {
			return sqrt(x * x + y * y);
		}

		P sub(P p) {
			return new P(x - p.x, y - p.y, r);
		}

		public P(double x, double y, double r) {
			super();
			this.x = x;
			this.y = y;
			this.r = r;
		}
	}

	public static void main(String[] args) {
		new G().run();
	}
}

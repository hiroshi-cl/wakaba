package com.github.hiroshi_cl.wakaba.v2010.sol.sc2009.d2;
import java.io.*;
import java.math.*;
import java.util.*;
import static java.lang.Math.*;
import static java.math.BigInteger.*;
import static java.util.Arrays.*;
import static java.util.Collections.*;
class A {
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


			int n = sc.nextInt(), m = sc.nextInt();
			Entry[] es = new Entry[m];
			for (int i = 0; i < m; i++) {
				es[i] = new Entry(sc.nextInt(), sc.nextInt(), sc.nextInt());
			}
			sort(es);
			boolean[] bs = new boolean[n];
			bs[0] = true;
			for (int i = 0; i < m; i++) {
				if (bs[es[i].s - 1]) {
					bs[es[i].d - 1] = true;
				}
			}
			int res = 0;
			for (int i = 0; i < n; i++) {
				if (bs[i]) res++;
			}
			System.out.println(res);

//			debug(1);

			Scanner sc1, sc2;
			try {
				sc1 = new Scanner(new File(o + ".out"));
				sc2 = new Scanner(new File(o + ".diff"));
				boolean ok=true;
				while(sc1.hasNext()){
					if(!sc1.next().equals(sc2.next())){
						ok=false;
						break;
					}
				}
				if(sc2.hasNext())ok=false;
				System.err.println(ok);
			} catch (Exception e) {}
		}
	}
	class Entry implements Comparable<Entry> {
		int t, s, d;
		public Entry(int t, int s, int d) {
			super();
			this.t = t;
			this.s = s;
			this.d = d;
		}
		public int compareTo(Entry o) {
			// TODO 自動生成されたメソッド・スタブ
			return t - o.t;
		}
	}
	public static void main(String[] args) {
		new A().run();
	}
}

package com.github.hiroshi_cl.wakaba.v2010.sol.r2009;
import java.util.*;
import java.math.*;
import java.io.*;
import static java.util.Arrays.*;
import static java.util.Collections.*;
import static java.lang.Math.*;
import static java.math.BigInteger.*;

public class C {
	void debug(Object... os) {
//		System.err.println(deepToString(os));
	}

	void run() {
		Scanner sc = new Scanner(System.in);
		try {
			sc = new Scanner(new File("C.in"));
		} catch (Exception e) {
		}
		for(;;){
			int n=sc.nextInt();
			if(n == 0)
				return;
			Swimmer[] ss = new Swimmer[n];
			for (int i = 0; i < n; i++) {
				ss[i] = new Swimmer(sc.nextInt(), sc.nextInt());
			}
			int[] T = new int[2];
			PQ[] ps = new PQ[2];
			for(int i = 0; i < 2; i++)
				ps[i] = new PQ();
			Q[] qs = new Q[2];
			for(int i = 0; i < 2; i++)
				qs[i] = new Q();
			for (int i = 0; i < n; i++) {
				ps[0].offer(ss[i]);
			}
			while(!ps[0].isEmpty()){
				qs[0].offer(ps[0].poll());
			}
			//			debug(qs);
			while(!qs[0].isEmpty() || !qs[1].isEmpty()){
				for(int i = 0; i < 2; i++)
					if(!qs[i].isEmpty())
						T[i] = qs[i].peek().T;
				int c = ((T[1] < T[0] && !qs[1].isEmpty()) || qs[0].isEmpty() ? 1 : 0);
				debug(c, T, qs[0].size(), qs[1].size());
				//				T[c] = qs[c].peek().T;
				while(!qs[c].isEmpty() && qs[c].peek().T <= T[c])
					ps[c].offer(qs[c].poll());
				while(!ps[c].isEmpty()) {
					Swimmer s = ps[c].poll();
					if(c == 1)
						s.c--;
					s.T = T[c] + s.t;
					if(s.c > 0)
						qs[c^1].offer(s);
				}
			}
			System.out.println(max(T[0], T[1]));
		}
	}
	class Q extends LinkedList<Swimmer> {};
	class PQ extends PriorityQueue<Swimmer> {};
	class Swimmer implements Comparable<Swimmer> { 
		final int t;
		int c;
		int T=0;
		@Override
		public int compareTo(Swimmer o) {
			return t-o.t;
		}
		public Swimmer(int t, int c) {
			this.t = t;
			this.c = c;
			T = t;
		}

	}

	public static void main(String[] args) {
		new C().run();
	}
}

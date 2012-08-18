package com.github.hiroshi_cl.wakaba.v2010.sol.sgu;

import java.util.*;
import java.math.*;
import static java.lang.Math.*;
import static java.util.Arrays.*;
import static java.util.Collections.*;
import static java.math.BigInteger.*;

public class D20100613J {
	public static void main(String[] args) {
		new D20100613J().run();
	}

	void debug(Object... os) {
//		System.err.println(deepToString(os));
	}

	long[] tol(Long[] Ls) {
		long[] ls = new long[Ls.length];
		for (int i = 0; i < ls.length; i++)
			ls[i] = Ls[i];
		return ls;
	}

	void run() {
		Scanner sc = new Scanner(System.in);
		int N = sc.nextInt();
		long M = sc.nextLong();
		long[] A = new long[N];
		long[] B = new long[N];
		for(int i = 0; i < N; i++) {
			A[i] = sc.nextLong();
			B[i] = sc.nextLong();
		}
//		debug(A);
//		debug(B);
		Set<Long> set = new HashSet<Long>();
		for(int i = 0; i < N; i++)
			for(int j = 0; j < N; j++)if(A[i]*j+B[i]>=0 && A[i]*j+B[i]<M)
				set.add(A[i] * j + B[i]);
		long[] ls = tol(set.toArray(new Long[0]));
//		debug(ls);
		int m = ls.length;
		FordFulkerson ff = new FordFulkerson(N+m+2, 0, N+m+1);
		for(int i=0;i<N;i++){
			ff.make(0, i+1, 1);
		}
		for(int i=0;i<m;i++){
			ff.make(N+1+i, N+m+1, 1);
		}
		for(int i=0;i<N;i++){
			for(int j=0;j<m;j++){
				if( A[i]!=0 && (ls[j]-B[i])*A[i] >= 0 && (ls[j]-B[i])%A[i]==0){
					ff.make(i+1,N+1+j,1);
//					debug()
				}
			}
		}
		 ff.maxflow();
//		System.out.println(f);
		HashSet<Integer> used = new HashSet<Integer>();
		int[] res=new int[N];
		for(int i=0;i<m;i++){
			if(ff.vs[N+1+i].bef != null){
				res[ff.vs[N+1+i].bef.id-1] = (int)(ls[i]);
				used.add((int)ls[i]);
			}
		}
		for(int i=0,tmp=1;i<N;i++){
			if(res[i]==0){
				while(used.contains(tmp))tmp++;
				res[i] = tmp;tmp++;
			}
		}
		StringBuilder sb=new StringBuilder("");
		for(int i=0;i<N;i++){
			sb.append(res[i]);
			if(i<N-1)sb.append(' ');
		}
		System.out.println(sb.toString());
	}

	class FordFulkerson {
		static final int INF = Integer.MAX_VALUE;
		int n;
		V s, t;
		V[] vs;

		FordFulkerson(int n, int s, int t) {
			this.n = n;
			vs = new V[n];
			for (int i = 0; i < n; i++)
				vs[i] = new V(i);
			this.s = vs[s];
			this.t = vs[t];
		}

		void make(int from, int to, int cap) {
			E e = new E(vs[from], vs[to], cap);
			E r = new E(vs[to], vs[from], 0);
			e.rev = r;
			r.rev = e;
			vs[from].es.add(e);
			vs[to].es.add(r);
		}

		int maxflow() {
			for (int p = 1, flow = 0;; p++) {
				s.p = p;
				int f = dfs(s, INF, p);
				if (f == 0)
					return flow;
				flow += f;
			}
		}

		int dfs(V s, int f, int p) {
			if (s == t)
				return f;
			for (E e : s.es) {
				if (e.to.p < p && e.cap > 0) {
					e.to.p = p;
					e.to.bef = s;
					int min = dfs(e.to, min(e.cap, f), p);
					if (min > 0) {
						e.cap -= min;
						e.rev.cap += min;
						return min;
					}
				}
			}
			return 0;
		}

		class V {
			int id;
			V(int id){this.id=id;}
			ArrayList<E> es = new ArrayList<E>();
			int p;
			V bef;
		}

		class E {
			E rev;
			V from, to;
			int cap;

			E(V from, V to, int cap) {
				this.from = from;
				this.to = to;
				this.cap = cap;
			}
		}
	}
}

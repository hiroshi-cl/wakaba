package com.github.hiroshi_cl.wakaba.v2010.sol.sgu;
import java.math.*;
import java.util.*;
import static java.lang.Math.*;
import static java.math.BigInteger.*;
import static java.util.Arrays.*;
import static java.util.Collections.*;

public class Contest009E {
	void debug(Object... os) {
		System.err.println(deepToString(os));
	}
	void run() {
		Scanner sc = new Scanner(System.in);
		int N = sc.nextInt();
		int M = sc.nextInt();
		V[] vs = new V[N];
		for(int i = 0; i < N; i++)
			vs[i] = new V(i);
		for(int i = 0; i < M; i++)
			vs[sc.nextInt()-1].es.add(vs[sc.nextInt()-1]);
		vs = new TSort().sort(vs);
		if(vs == null) {
			System.out.println("No solution");
			return;
		}
		int[] ans = new int[N];
		for(int i = 0; i < N; i++)
			ans[vs[i].id] = i + 1;
		System.out.println(concat(ans, ' '));
	}
	String concat(int[] is, char c) {
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < is.length; i++) {
			sb.append(is[i]);
			if(i < is.length - 1)
				sb.append(c);
		}
		return sb.toString();
	}
	public static void main(String[] args) {
		new Contest009E().run();
	}
	class TSort {
		V[] sorted;
		int n;
		V[] sort(V[] vs) {
			n = vs.length;
			sorted = new V[n];
			for(V v : vs)
				if(v.state == 0 && !dfs(v))
					return null;
			return sorted;
		}
		boolean dfs(V v) {
			v.state = 1;
			for(V u : v.es)
				if(u.state == 1 || u.state == 0 && !dfs(u))
					return false;
			sorted[--n] = v;
			v.state = 2;
			return true;
		}
	}
	class V {
		ArrayList<V> es = new ArrayList<V>();
		int state = 0;
		final int id;
		public V(int id) { this.id = id; }
	}
}

package com.github.hiroshi_cl.wakaba.v2010.lib.graph;
import java.util.*;
public class TwoSAT {
	/*
	 * 2-SAT 問題を解くためのライブラリ。
	 * two_satisfiability(int m,Pair[] ps) は、変数m個で psという制約条件下での2-SATに
	 * 解があるかどうかを判定する。
	 * psの各要素は<x,y> の形で、x∨y を意味する。
	 * x,y はvar(i) or not(var(i)) であること。(0 <= i < m).
	 * test()を参照.
	 */
	boolean two_satisfiability(int m, Pair[] ps) {
		int n = 2 * m;
		SCC scc = new SCC(n);
		for (int i = 0; i < ps.length; i++) {
			int u = ps[i].x, v = ps[i].y;
			scc.make(not(u), v);
			scc.make(not(v), u);
		}
		scc.build();
		for (int i = 0; i < m; i++) {
			if (scc.vs[var(i)].comp == scc.vs[not(var(i))].comp) return false;
		}
		return true;
	}
	class SCC {
		int n;
		V[] vs;
		V[] sorted;
		SCC(int k) {
			vs = new V[k];
			for (int i = 0; i < k; i++)
				vs[i] = new V();
		}
		void make(int from, int to) {
			vs[from].es.add(vs[to]);
			vs[to].rev.add(vs[from]);
		}
		void build() {
			init();
			n = vs.length;
			sorted = new V[n];
			for (V v : vs)
				if (!v.visit) dfs(v);
			for (V v : vs)
				v.visit = false;
			for (V u : sorted)
				if (!u.visit) dfsrev(u, n++);
		}
		void dfs(V v) {
			v.visit = true;
			for (V u : v.es)
				if (!u.visit) dfs(u);
			sorted[--n] = v;
		}
		void dfsrev(V v, int k) {
			v.visit = true;
			for (V u : v.rev)
				if (!u.visit) dfsrev(u, k);
			v.comp = k;
		}
		void init() {
			for (V v : vs)
				v.init();
		}
		class V {
			ArrayList<V> es = new ArrayList<V>();
			ArrayList<V> rev = new ArrayList<V>();
			boolean visit;
			int comp;
			void init() {
				visit = false;
				comp = 0;
			}
		}
	}

	int var(int x) {
		return x << 1;
	}
	int not(int x) {
		return x ^ 1;
	}
	class Pair {
		public String toString() {
			return x + " " + y;
		}
		int x, y;
		Pair(int x_, int y_) {
			x = x_;
			y = y_;
		}
	}
	void test(){
		LinkedList<Pair> list=new LinkedList<Pair>();
		list.add(new Pair(var(0),var(1)));
		list.add(new Pair(var(0),not(var(1))));
		list.add(new Pair(not(var(0)),not(var(1))));
		//(x0∨x1)∧(xo∨¬x1)∧(¬x0∨¬x1).
		System.err.println(two_satisfiability(2, list.toArray(new Pair[0])));
		list.add(new Pair(not(var(0)),var(1)));
		//(x0∨x1)∧(xo∨¬x1)∧(¬x0∨¬x1)∧(¬x0∨x1).
		System.err.println(two_satisfiability(2, list.toArray(new Pair[0])));
	}
	public static void main(String[] args) {
		new TwoSAT().test();
	}
}

package com.github.hiroshi_cl.wakaba.v2010.sol.rp2008;
import java.math.*;
import java.util.*;
import static java.lang.Math.*;
import static java.math.BigInteger.*;
import static java.util.Arrays.*;
import static java.util.Collections.*;

public class G {
	void debug(Object... os) {
		System.err.println(deepToString(os));
	}
	void run() {
		Scanner sc = new Scanner(System.in);
		int N = sc.nextInt();
		int M = sc.nextInt();
		int Q = sc.nextInt();
		SplitInfo[] si = new SplitInfo[M];
		for(int i = 0; i < M; i++) {
			int Y = sc.nextInt();
			int L = sc.nextInt();
			int[] V = new int[L];
			for(int j = 0; j < L; j++)
				V[j] = sc.nextInt();
			si[i] = new SplitInfo(Y, L, V);
		}
		sort(si);
		UnionFind uf = new UnionFind(N);
		Tree[] ts = new Tree[M+N+2];
		for(int i = 1; i <= N; i++)
			ts[i] = new Leaf(i);
		for(int i = 1; i <= M ;i++) {
			int L = si[i-1].L;
			int[] lids = new int[L];
			int[] tids = new int[L];
			for(int j = 0; j < L; j++)
				lids[j] = si[i-1].V[j];
			for(int j = 0; j < L; j++)
				tids[j] = -uf.tree[uf.root(lids[j])];
			int id = N + i;
			for(int j = 1; j < L; j++)
				uf.union(lids[0], lids[j], id);
			Tree[] children = new Tree[L];
			for(int j = 0; j < L; j++)
				children[j] = ts[tids[j]];
			ts[id] = new Tree(children);
		}
		int[] roots = uf.roots();
		{
			int L = roots.length;
			int[] lids = new int[L];
			int[] tids = new int[L];
			for(int j = 0; j < L; j++)
				lids[j] = roots[j];
			for(int j = 0; j < L; j++)
				tids[j] = -uf.tree[uf.root(lids[j])];
			int id = N + M + 1;
			for(int j = 1; j < L; j++)
				uf.union(lids[0], lids[j], id);
			Tree[] children = new Tree[L];
			for(int j = 0; j < L; j++)
				children[j] = ts[tids[j]];
			ts[id] = new Tree(children);
		}
		int[] array = new int[N+1];
		ts[M+N+1].toArray(1, array);
		for(int i = 0; i < Q; i++)
			System.out.println(array[sc.nextInt()]);
	}
	class SplitInfo implements Comparable<SplitInfo> {
		final int Y, L;
		final int[] V;
		public SplitInfo(int y, int l, int[] v) {
			Y = y;
			L = l;
			V = v;
		}
		public int compareTo(SplitInfo o) {
			return o.Y - Y;
		}
	}
	class UnionFind {
		final int[] tree;
		public UnionFind(int size) {
			tree = new int[size+1];
			for(int i = 1; i <= size; i++)
				tree[i] = -i;
		}
		int root(int x) {
			return tree[x] < 0 ? x : (tree[x] = root(tree[x]));
		}
		void union(int x, int y, int id) {
			x = root(x);
			y = root(y);
			if(x == y)
				return;
			tree[y] = x;
			tree[x] = -id;
		}
		int[] roots() {
			int n = 0;
			for(int i = 1; i < tree.length; i++)
				if(tree[i] < 0)
					n++;
			int[] ret = new int[n];
			for(int i = 1, c = 0; i < tree.length; i++)
				if(tree[i] < 0)
					ret[c++] = i;
			return ret;
		}
	}
	class Tree implements Comparable<Tree> {
		final Tree[] children;
		int l;
		public Tree(Tree... ts) {
			children = ts;
			int m = Integer.MAX_VALUE;
			for(Tree t : children)
				m = min(m, t.l);
			l = m;
			sort(children);
		}
		public int compareTo(Tree o) {
			return l - o.l;
		}
		int toArray(int offset, int[] array) {
			for(Tree t : children)
				offset = t.toArray(offset, array);
			return offset;
		}
	}
	class Leaf extends Tree {
		public Leaf(int l) {
			this.l = l;
		}
		int toArray(int offset, int[] array) {
			array[offset] = l;
			return offset + 1;
		}
	}
	public static void main(String[] args) {
		new G().run();
	}
}

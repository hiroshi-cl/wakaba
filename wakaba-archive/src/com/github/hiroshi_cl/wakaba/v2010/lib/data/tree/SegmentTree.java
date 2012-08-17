package com.github.hiroshi_cl.wakaba.v2010.lib.data.tree;

//区分木(Segment Tree)
//生成は O(n lg n)
//簡易テストでは問題なし
import java.util.*;

public class SegmentTree {
	// 区分木生成．
	static SegmentTree build(int l, int r, S[] ss) {
		// 点の座標を一意的にする．
		TreeSet<Integer> set = new TreeSet<Integer>();
		for (int i = 0; i < ss.length; i++) {
			set.add(ss[i].l);
			set.add(ss[i].r);
		}
		// 点間の区間生成
		Integer[] ps = set.toArray(new Integer[0]);
		S[] nss = new S[ps.length * 2 + 1];
		for (int i = 0; i < ps.length; i++) {
			nss[i * 2] = (i == 0) ? new S(l, ps[0]) : new S(ps[i - 1], ps[i]);
			nss[i * 2 + 1] = new S(ps[i], ps[i]);
		}
		nss[nss.length - 1] = new S(ps[ps.length - 1], r);
		// 再帰的に木を生成
		SegmentTree t = new SegmentTree(nss, 0, nss.length);
		for (int i = 0; i < ss.length; i++)
			t.insert(ss[i]);
		return t;
	}

	int l, r;
	boolean lc, rc; // 閉区間か否か
	ArrayList<S> ss;
	SegmentTree lnode, rnode;

	SegmentTree(S[] nss, int begin, int end) {
		l = nss[begin].l;
		lc = nss[begin].l == nss[begin].r;
		r = nss[end - 1].r;
		rc = nss[end - 1].l == nss[end - 1].r;
		ss = new ArrayList<S>();
		if (end - begin > 1) {
			int mid = begin + (end - begin + 1) / 2;
			lnode = new SegmentTree(nss, begin, mid);
			rnode = new SegmentTree(nss, mid, end);
		}
	}

	void insert(S s) {
		if (s.l <= l && r <= s.r)
			ss.add(s);
		else {
			if (s.l <= lnode.r)
				lnode.insert(s);
			if (rnode.l <= s.r)
				rnode.insert(s);
		}
	}

	// xを覆う区間の集合
	List<S> query(int x) {
		List<S> result = new LinkedList<S>(ss);
		if (!isLeaf()) {
			if ((x <= rnode.l && rnode.isLeaf()) || lnode.isIn(x))
				result.addAll(lnode.query(x));
			if ((lnode.r <= x && lnode.isLeaf()) || rnode.isIn(x))
				result.addAll(rnode.query(x));
		}
		return result;
	}

	boolean isLeaf() {
		return lnode == null && rnode == null;
	}

	boolean isIn(int x) {
		return (l < x || (lc && x == l)) && (x < r || (rc && x == r));
	}

	static class S { // 区間
		int l, r;

		S(int l, int r) {
			this.l = l;
			this.r = r;
		}
	}
}

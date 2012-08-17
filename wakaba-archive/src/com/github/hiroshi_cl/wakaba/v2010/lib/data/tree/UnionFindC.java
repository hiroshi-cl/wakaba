package com.github.hiroshi_cl.wakaba.v2010.lib.data.tree;

import java.util.*;

/**
 * union(x,y) : くっつける.
 * find(x,y)  : 同じグループか？ を返す.
 * これらの操作が実用上はほぼO(1).
 * 経路圧縮がついていることが計算量的に重要.
 * 
 * キーの分布が疎なときやintでないときなどに使える
 * 
 * union(x,y) : くっつける.
 * find(x,y)  : 同じグループか？ を返す.
 * getVal(x)  : x を含むグループに付加した値を得る. この実装例では使っていない.
 *
 */
public class UnionFindC<K,V>  {
	final Map<K, Node> map = new HashMap<K, Node>();

	class Node {
		Node parent = null;
		V val; // = someValue;

		Node getRoot() {
			return parent == null ? this : (parent = parent.getRoot());
		}

		void merge(Node n) {
			if(parent == null) {

			}
			else
				getRoot().merge(n);
		}
	}

	Node get(K key) {
		if(!map.containsKey(key))
			map.put(key, new Node());
		return map.get(key);
	}

	boolean find(K x1, K y1) {
		Node x = get(x1).getRoot();
		Node y = get(y1).getRoot();
		return x == y;
	}

	void union(K x1, K y1) {
		Node x = get(x1).getRoot();
		Node y = get(y1).getRoot();
		if(x != y) {
			y.parent = x;
			// x.val = someOperation(x.val, y.val);
		}
	}

	V getVal(K x) {
		return map.get(x).getRoot().val;
	}
}

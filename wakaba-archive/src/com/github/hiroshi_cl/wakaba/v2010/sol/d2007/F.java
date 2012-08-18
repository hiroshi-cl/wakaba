package com.github.hiroshi_cl.wakaba.v2010.sol.d2007;

import java.util.*;
import static java.util.Arrays.*;

public class F {
	public static void main(String[] args) {
		new F().run();
	}

	static void debug(Object... o) {
		System.err.println(deepToString(o));
	}

	void run() {
		Scanner sc = new Scanner(System.in);
		for (;;) {
			String s = sc.nextLine().replaceAll(" ", "");
			c = 0;
			if (s.equals("0"))
				return;
			root = new Node(s);
			root.normalize();
			root.swap();
			System.out.println(root.output());
		}
	}

	Node root;
	int c = 0;
	double EPS = 1e-7;

	class Node implements Comparable<Node> {
		Node p;
		Node left;
		Node right;
		double simi = -1;

		Node(String s) {
			switch (s.charAt(c)) {
			case 'x':
				c++;
				return;
			case '(':
				c++;
				left = new Node(s);
				right = new Node(s);
				c++;
				break;
			}
			left.p = this;
			right.p = this;
		}

		void swap() {
			if (left == null)
				return;
			if (p != null && p.right == this) {
				Node n = left;
				left = right;
				right = n;
			}
			right.swap();
			left.swap();
		}

		public int compareTo(Node o) {
			if (simi() > o.simi() + EPS)
				return -1;
			if (simi() < o.simi() - EPS)
				return 1;
			if (left == null)
				return 0;
			int com = left.compareTo(o.left);
			if (com != 0)
				return com;
			com = right.compareTo(o.right);
			if (com != 0)
				return com;
			return 0;
		}

		double simi() {
			if (left == null)
				return simi = 0;
			if (simi >= 0)
				return simi;
			HashSet<String> ls = left.sub();
			HashSet<String> rs = right.sub();
			HashSet<String> union = new HashSet<String>();
			for (String s : ls) {
				union.add(s);
			}
			int and = 0;
			for (String s : rs) {
				union.add(s);
				if (ls.contains(s))
					and++;
			}
			return simi = (double) and / union.size();
		}

		HashSet<String> sub() {
			HashSet<String> res = new HashSet<String>();
			res.add(output());
			if (left == null) {
				return res;
			}
			res.addAll(left.sub());
			res.addAll(right.sub());
			return res;
		}

		void normalize() {
			if (left == null)
				return;
			left.normalize();
			right.normalize();
			if (left.compareTo(right) < 0) {
				Node n = left;
				left = right;
				right = n;
				return;
			}
		}

		String output() {
			if (left == null) {
				return "x";
			}
			return "(" + left.output() + " " + right.output() + ")";
		}
	}
}
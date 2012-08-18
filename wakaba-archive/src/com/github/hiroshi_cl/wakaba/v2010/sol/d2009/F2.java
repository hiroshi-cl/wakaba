package com.github.hiroshi_cl.wakaba.v2010.sol.d2009;

import java.io.*;

import java.util.*;
import java.math.*;

import static java.lang.Math.*;
import static java.util.Arrays.*;
import static java.util.Collections.*;
import static java.math.BigInteger.*;
import static java.lang.Character.*;

class F2 {
	public void run() {
		Scanner sc = new Scanner();
		while (true) {
			int AN = sc.nextInt();
			int BN = sc.nextInt();
			int R = sc.nextInt();
			if (AN == 0 && BN == 0 && R == 0)
				return;
			P[] A = new P[AN];
			for (int i = 0; i < AN; i++)
				A[i] = new P(sc.nextInt(), sc.nextInt());
			P[] B = new P[BN];
			for (int i = 0; i < BN; i++)
				B[i] = new P(sc.nextInt(), sc.nextInt());
			sort(A);
			sort(B);
			int ret = 0;
			NavigableSet<P> xset = new TreeSet<P>();
			NavigableSet<P> yset = new TreeSet<P>(new Comparator<P>() {
				@Override
				public int compare(P o1, P o2) {
					return o1.y == o2.y ? o1.x - o2.x : o1.y - o2.y;
				}
			});
			for (int i = 0, j = 0; i < AN; i++) {
				while (j < BN && B[j].x <= A[i].x + 4 * R) {
					xset.add(B[j]);
					yset.add(B[j]);
					j++;
				}
				while (!xset.isEmpty() && xset.first().x < A[i].x - 4 * R)
					yset.remove(xset.pollFirst());
				Set<P> set = yset.subSet(new P(0, A[i].y - 4 * R), new P(0, A[i].y + 4 * R + 1));
				for (P p : set) {
					if (A[i].dist(p) <= 16 * R * R)
						ret++;
				}
			}
			System.out.println(ret);
		}
	}

	class P implements Comparable<P> {
		final int x, y;

		public P(int x, int y) {
			this.x = x;
			this.y = y;
		}

		int dist(P p) {
			return (x - p.x) * (x - p.x) + (y - p.y) * (y - p.y);
		}

		@Override
		public int compareTo(P o) {
			return x == o.x ? y - o.y : x - o.x;
		}
	}

	class Scanner {
		int nextInt() {
			try {
				int c = System.in.read();
				if (c == -1)
					return c;
				while (c != '-' && (c < '0' || '9' < c)) {
					c = System.in.read();
					if (c == -1)
						return c;
				}
				if (c == '-')
					return -nextInt();
				int res = 0;
				do {
					res *= 10;
					res += c - '0';
					c = System.in.read();
				} while ('0' <= c && c <= '9');
				return res;
			} catch (Exception e) {
				return -1;
			}
		}

		long nextLong() {
			try {
				int c = System.in.read();
				if (c == -1)
					return -1;
				while (c != '-' && (c < '0' || '9' < c)) {
					c = System.in.read();
					if (c == -1)
						return -1;
				}
				if (c == '-')
					return -nextLong();
				long res = 0;
				do {
					res *= 10;
					res += c - '0';
					c = System.in.read();
				} while ('0' <= c && c <= '9');
				return res;
			} catch (Exception e) {
				return -1;
			}
		}

		double nextDouble() {
			return Double.parseDouble(next());
		}

		String next() {
			try {
				StringBuilder res = new StringBuilder("");
				int c = System.in.read();
				while (Character.isWhitespace(c))
					c = System.in.read();
				do {
					res.append((char) c);
				} while (!Character.isWhitespace(c = System.in.read()));
				return res.toString();
			} catch (Exception e) {
				return null;
			}
		}

		String nextLine() {
			try {
				StringBuilder res = new StringBuilder("");
				int c = System.in.read();
				while (c == '\r' || c == '\n')
					c = System.in.read();
				do {
					res.append((char) c);
					c = System.in.read();
				} while (c != '\r' && c != '\n');
				return res.toString();
			} catch (Exception e) {
				return null;
			}
		}
	}
}
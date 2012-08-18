package com.github.hiroshi_cl.wakaba.v2010.sol.rp2009;
import java.util.*;
import java.math.*;
import java.io.*;
import static java.math.BigInteger.*;
import static java.lang.Math.*;
import static java.util.Arrays.*;
import static java.util.Collections.*;

public class B {
	void debug(Object... os) {
//		System.err.println(deepToString(os));
	}

	void run() {
		Scanner sc = new Scanner(System.in);
		try {
			sc = new Scanner(new File("B.txt"));
		} catch (Exception e) {
		}
		loop: for (;;) {
			int n = sc.nextInt();
			int d = sc.nextInt();
			if (n == 0 && d == 0)
				return;
			int[] ms = new int[n];
			int[][] cs = new int[n][];
			int[] is = new int[n];
			int[] ss = new int[n];
			for (int i = 0; i < n; i++) {
				ms[i] = sc.nextInt();
				cs[i] = new int[ms[i]];
				for (int j = ms[i] - 1; j >= 0; j--) {
					cs[i][j] = sc.nextInt();
					ss[i] += cs[i][j];
				}
			}
			if (n == 1) {
				System.out.println(Yes);
				continue;
			}
			while (true) {
				debug(ss);
				int min = 1 << 28, max = 0;
				for (int i = 0; i < n; i++) {
					min = min(min, ss[i]);
					max = max(max, ss[i]);
				}
				if (max == 0) {
					System.out.println(Yes);
					continue loop;
				}
				if (max - min > d) {
					System.out.println(No);
					continue loop;
				}
				for (int i = 0; i < n; i++) {
					while (is[i] < ms[i] && ss[i] - cs[i][is[i]] >= min) {
						ss[i] -= cs[i][is[i]];
						is[i]++;
					}
				}

				min = 1 << 28;
				max = 0;
				for (int i = 0; i < n; i++) {
					min = min(min, ss[i]);
					max = max(max, ss[i]);
				}
				if (max == 0) {
					System.out.println(Yes);
					break;

				}

				// int val = -1;
				int id = -1;
				Entry[] es = new Entry[n];
				for (int i = 0; i < n; i++) {
					es[i] = new Entry(ss[i], i);
				}
				sort(es);
				// id=-1;
				for (int i = 0; i < n; i++) {
					if (i == 0) {
						if (is[es[i].i] < ms[es[i].i]
								&& es[i + 1].s
										- (es[i].s - cs[es[i].i][is[es[i].i]]) <= d) {
							id = es[i].i;
							break;
						}
					} else {
						if (is[es[i].i] < ms[es[i].i]
								&& es[0].s
										- (es[i].s - cs[es[i].i][is[es[i].i]]) <= d) {
							id = es[i].i;
							break;
						}
					}
				}
				if (id == -1) {
					System.out.println(No);
					break;
				}

				// for (int i = 0; i < n; i++) {
				// if (is[i] < ms[i] && ss[i] - cs[i][is[i]] > val) {
				// val = ss[i] - cs[i][is[i]];
				// id = i;
				// }
				// }
				// if (val == -1) {
				// System.out.println(Yes);
				// break;
				// }
				ss[id] -= cs[id][is[id]];
				is[id]++;
			}
		}
	}

	class Entry implements Comparable<Entry> {
		int s, i;

		@Override
		public int compareTo(Entry o) {
			return o.s - s;
		}

		Entry(int s, int i) {
			this.s = s;
			this.i = i;
		}
	}

	String No = "No";
	String Yes = "Yes";

	public static void main(String[] args) {
		new B().run();
	}
}

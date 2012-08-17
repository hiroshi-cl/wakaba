package com.github.hiroshi_cl.wakaba.v2010.lib.dp;
import static java.lang.Math.*;
import java.util.*;
public class MaxRectangle{
	/*
	 * trueのcellのみからなる長方形のうちの、最大面積を求める。
	 * O(n2)
	 * verified:一応、japanDomestic2001A（サイズが5*5で固定)が通ることを確認。
	 * 参考サイト様:ALGORITHM NOTE
	 */
	long maxAreaIn2Dcells(boolean[][] cells) {
		int n = cells.length, m = cells[0].length;
		long[][] his = new long[n][m];
		for (int j = 0; j < m; j++)
			his[0][j] = cells[0][j] ? 1 : 0;
		for (int i = 1; i < n; i++) {
			for (int j = 0; j < m; j++) {
				his[i][j] = cells[i][j] ? his[i - 1][j] + 1 : 0;
			}
		}
		long res = 0;
		for (int i = 0; i < n; i++) {
			res = max(res, maxAreaInHistogram(his[i]));
		}
		return res;
	}
	/*
	 * 与えられたヒストグラム中の、長方形のうちの、最大面積を求める。
	 * O(n)
	 * verified : pku2559
	 * 参考サイト様:ALGORITHM NOTE
	 */
	long maxAreaInHistogram(long[] his) {
		int n = his.length;
		long area = 0;
		Entry[] es = new Entry[n + 1];
		Entry e = null;
		for (int i = 0; i < n; i++)
			es[i] = new Entry(i, his[i]);
		es[n] = new Entry(n, 0);
		Stack<Entry> st = new Stack<Entry>();
		for (int i = 0; i <= n; i++) {
			if (st.isEmpty() || st.peek().l < es[i].l)
				st.push(es[i]);
			else if (st.peek().l > es[i].l) {
				while (!st.isEmpty() && st.peek().l > es[i].l) {
					e = st.pop();
					area = max(area, e.l * (i - e.i));
				}
				es[i].i = e.i;
				st.push(es[i]);
			}
		}
		return area;
	}

	class Entry {
		int i;
		long l;

		Entry(int i, long l) {
			this.i = i;
			this.l = l;
		}
	}
}
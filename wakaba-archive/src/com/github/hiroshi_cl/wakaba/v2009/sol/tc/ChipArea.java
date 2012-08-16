package com.github.hiroshi_cl.wakaba.v2009.sol.tc;
import static java.lang.Math.max;
import static java.lang.Math.min;
import static java.util.Arrays.sort;
public class ChipArea {
	P[] ps;
	int n;
	long maxArea = 0;
	int[] xs, ys;
	int size = 323537;
	public double maxArea(int skip, int n) {
		this.n = n;
		int R = 1;
		ps = new P[n];
		for (int j = 0; j < skip; j++)
			R = 111 * R % 323537;
		for (int pt = 0; pt < n; pt++) {
			R = 111 * R % 323537;
			int x = R;
			R = 111 * R % 323537;
			ps[pt] = new P(x, R);
		}
		sort(ps);
		xs = new int[n];
		ys = new int[n];
		for (int i = 0; i < n; i++) {
			xs[i] = ps[i].x;
			ys[i] = ps[i].y;
		}
		solve1();
		solve2();
		return (double) maxArea / size / size;
	}
	void solve2() {
		int len = 2;
		long[] ls = new long[n + 4];
		ls[0] = 0;
		ls[1] = size;
		for (int i = 0; i < n; i++) {
			long x0 = xs[i], y0 = ys[i];
			for (int k = 0; k < len; k++) {
				if (ls[k] == y0) break;
				if (ls[k + 1] > y0) {
					maxArea = max(maxArea, (ls[k + 1] - ls[k]) * x0);
					len++;
					for (int j = len; j > k; j--) {
						ls[j + 1] = ls[j];
					}
					ls[k + 1] = y0;
					break;
				}
			}
		}
		for(int i=0;i<len-1;i++){
			maxArea=max(maxArea,(ls[i+1]-ls[i])*size);
		}
	}
	void solve1() {
		for (int i = 0; i < n; i++) {
			long top = size, bottom = 0, x0 = xs[i], y0 = ys[i];
			for (int j = i + 1; j < n; j++) {
				maxArea = max(maxArea, (top - bottom) * (xs[j] - x0));
				if (ys[j] < y0) bottom = max(bottom, ys[j]);
				if (ys[j] > y0) top = min(top, ys[j]);
				if ((top - bottom) * (size - x0) <= maxArea) break;
			}
			maxArea = max(maxArea, (top - bottom) * (size - x0));
		}
	}
	class P implements Comparable<P> {
		public String toString() {
			return (double) x / size + " " + (double) y / size;
		}
		int x, y;
		P(int x, int y) {
			this.x = x;
			this.y = y;
		}
		public int compareTo(P o) {
			return x - o.x;
		}
	}
}


// Powered by FileEdit
// Powered by CodeProcessor
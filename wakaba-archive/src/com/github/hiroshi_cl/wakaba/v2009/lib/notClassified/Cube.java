package com.github.hiroshi_cl.wakaba.v2009.lib.notClassified;

public class Cube {
	static final int[][] FM = { { 1, 2, 4, 5 }, { 1, 5, 4, 2 } };
	static final int[][] DR = { { 3, 3, 1, 3 }, { 1, 1, 3, 1 } };
	static final int N = 6;
	static final int M = 4;
	int[] num = new int[N];
	int cf = 0, cd = 0;

	public void roll(int d) {
		int c = (cd + d) % M;
		int f = cf % 2;
		cf = (cf + FM[f][c]) % N;
		cd = (cd + DR[f][c]) % M;
	}

	public void iroll(int d) {
		int c = (cd + d + M / 2) % M;
		int f = cf % 2;
		cf = (cf + FM[f][c]) % N;
		cd = (cd + DR[f][c]) % M;
	}

	public boolean write(int v) {
		if (num[cf] == 0) {
			num[cf] = v;
			return true;
		} else {
			return false;
		}
	}

	public boolean comp_check() {
		boolean b = true;
		for (int i = 0; i < N; i++)
			b &= num[i] > 0;
		for (int i = 0; i < N / 2; i++)
			b &= num[i] + num[i + N / 2] == N + 1;
		return b;
	}
}

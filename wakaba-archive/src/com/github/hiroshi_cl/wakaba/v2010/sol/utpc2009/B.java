package com.github.hiroshi_cl.wakaba.v2010.sol.utpc2009;

import java.util.*;
import static java.lang.Math.*;

public class B {
	void run() {
		Scanner sc = new Scanner(System.in);
		int gx = sc.nextInt();
		int gy = sc.nextInt();
		int p = sc.nextInt();
		boolean[][][] mat = new boolean[2][gx + 1][gy + 1];
		for (int i = 0; i < p; i++) {
			int x1 = sc.nextInt();
			int y1 = sc.nextInt();
			int x2 = sc.nextInt();
			int y2 = sc.nextInt();
			mat[x1 == x2 ? 1 : 0][min(x1, x2)][min(y1, y2)] = true;
		}
		int[][] dp = new int[gx + 1][gy + 1];
		dp[0][0] = 1;
		for (int x = 0; x <= gx; x++)
			for (int y = 0; y <= gy; y++) {
				if (x > 0 && !mat[0][x - 1][y])
					dp[x][y] += dp[x - 1][y];
				if (y > 0 && !mat[1][x][y - 1])
					dp[x][y] += dp[x][y - 1];
			}
		if (dp[gx][gy] == 0)
			System.out.println("Miserable Hokusai!");
		else
			System.out.println(dp[gx][gy]);
	}

	public static void main(String[] args) {
		new B().run();
	}
}
package com.github.hiroshi_cl.wakaba.v2010.sol.r2001;
import java.util.*;
import static java.lang.Math.*;
import static java.util.Arrays.*;
import static java.util.Collections.*;
public class G {
	public static void main(String[] args) {
		new G().run();
	}
	void run() {
		Scanner sc = new Scanner(System.in);
		for (;;) {
			int n = sc.nextInt();
			if (n == 0) return;
			int s = sc.nextInt();
			int[] ms = new int[n * 2];
			for (int i = 0; i < n * 2; i++) {
				ms[i] = sc.nextInt();
			}
			boolean[][] dp = new boolean[s + 1][n * 2];
			for (int i = 1; i <= s; i++) {
				for (int j = 0; j < n * 2; j++) {
					boolean win = false;
					for (int k = 1; k <= ms[j]; k++) {
						if (i - k >= 1 && !dp[i - k][(j + 1) % (n * 2)]) {
							win = true;
							break;
						}
					}
					dp[i][j] = win;
				}
			}
			System.out.println(dp[s][0] ? 1 : 0);
		}
	}
}
package com.github.hiroshi_cl.wakaba.v2010.sol.dp2008;

import java.util.*;

public class B {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO 自動生成されたメソッド・スタブ
		Scanner sc = new Scanner(System.in);
		for (;;) {
			int N = sc.nextInt(), M = sc.nextInt();
			if (N == 0)
				break;
			Queue<DP> q = new PriorityQueue<DP>();
			int sum = 0;
			for (int i = 0; i < N; i++) {
				int d;
				q.offer(new DP(d = sc.nextInt(), sc.nextInt()));
				sum += d;
			}
			sum -= M;
			int ans = 0;
			if (sum > 0) {
				while (true) {
					DP dp = q.poll();
					if (sum <= dp.D) {
						ans += sum * dp.P;
						break;
					} else {
						ans += dp.P * dp.D;
						sum -= dp.D;
					}
				}
			}
			System.out.println(ans);
		}

	}

	static class DP implements Comparable<DP> {
		int D, P;

		DP(int D, int P) {
			this.D = D;
			this.P = P;
		}

		public int compareTo(DP dp) {
			return new Integer(P).compareTo(dp.P);
		}
	}
}
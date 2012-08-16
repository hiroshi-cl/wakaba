package com.github.hiroshi_cl.wakaba.v2009.lib.game;
import java.util.*;
import static java.lang.Math.*;
public class PKU3537 {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int n = sc.nextInt();
		int[] grundy = new int[n + 1];
		grundy[0] = 0;
		for (int i = 0; i <= n; i++) {
			BitSet bit = new BitSet();
			for (int j = 0; j < i; j++) {
				bit.set(grundy[max(j - 2, 0)] ^ grundy[max(i - j - 3, 0)]);
			}
			grundy[i] = bit.nextClearBit(0);
		}
		System.out.println(grundy[n] == 0 ? 2 : 1);
	}
}

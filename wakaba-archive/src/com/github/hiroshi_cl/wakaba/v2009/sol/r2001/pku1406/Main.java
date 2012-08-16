package com.github.hiroshi_cl.wakaba.v2009.sol.r2001.pku1406;
import java.util.*;

class PKU1406 {
	static final int MAX1 = 54;
	static final int MAX2 = 96;
	void run() {
		final int[] c1 = new int[MAX1+1];
		for(int i = 0; i <= MAX1; i++)
			c1[i] = i * i * i;
		final int[] c2 = new int[MAX2+1];
		for(int i = 0; i <= MAX2; i++)
			c2[i] = i * (i + 1) * (i + 2) / 6;

		Scanner sc = new Scanner(System.in);
		for(int offer = sc.nextInt(); offer > 0; offer = sc.nextInt()) {
			int best = 0;
			for(int i = 0; c1[i] <= offer; i++){
				int r = offer - c1[i];
				int j = 0;
				while(c2[j + 1] <= r)
					j++;
				best = Math.max(best, c1[i] + c2[j]);
			}
			System.out.println(best);
		}
	}
}


public class Main {
	public static void main(String[] args) {
		new PKU1406().run();
	}
}

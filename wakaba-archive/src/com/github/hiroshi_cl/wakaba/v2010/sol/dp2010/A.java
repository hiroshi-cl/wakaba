package com.github.hiroshi_cl.wakaba.v2010.sol.dp2010;

import java.util.*;
import java.math.*;
import java.io.*;
import static java.util.Arrays.*;
import static java.util.Collections.*;
import static java.lang.Math.*;

public class A {
	public static void main(String[] args) {
		new A().run();
	}

	void debug(Object... os) {
		System.err.println(deepToString(os));
	}

	void run() {
		Scanner sc = new Scanner(System.in);
		try {
			String s = this.getClass().getSimpleName();
			sc = new Scanner(new File(s));
			System.setOut(new PrintStream(s + ".out"));
		} catch (Exception e) {
		}
		for (;;) {
			int N = sc.nextInt();
			if (N == 0)
				return;
			int ret = 0;
			for (int i = 1; i < N; i++) {
				int sum = i;
				for (int j = i + 1; j < N; j++) {
					sum += j;
					if (sum == N) {
						ret++;
						break;
					} else if (sum > N)
						break;
				}
			}
			System.out.println(ret);
		}
	}
}

package com.github.hiroshi_cl.wakaba.v2010.sol.d2010;
import java.util.*;
import java.math.*;
import java.io.*;
import static java.util.Arrays.*;
import static java.util.Collections.*;
import static java.lang.Math.*;

public class C {
	public static void main(String[] args) {
		new C().run();
	}

	void debug(Object... os) {
		System.err.println(deepToString(os));
	}

	static final int MAX = 1000 * 1000;
	final int[] minp = new int[MAX + 1];
	final int[] mino = new int[MAX + 1];
	final List<Integer> lt = new ArrayList<Integer>();
	final List<Integer> lo = new ArrayList<Integer>();

	void run() {
		Scanner sc = new Scanner(System.in);
		try {
			String s = getClass().getSimpleName();
			sc = new Scanner(new File(s));
			System.setOut(new PrintStream(new File(s + ".out")));
		} catch (Exception e) {
		}

		for (int i = 1;; i++) {
			int j = calc(i);
			if (j > MAX)
				break;
			if (j % 2 == 1)
				lo.add(j);
			lt.add(j);
		}
		fill(minp, Integer.MAX_VALUE);
		minp[0] = 0;
		for (int t : lt)
			for (int i = t; i <= MAX; i++)
				minp[i] = min(minp[i], minp[i - t] + 1);

		fill(mino, Integer.MAX_VALUE);
		mino[0] = 0;
		for (int t : lo)
			for (int i = t; i <= MAX; i++)
				mino[i] = min(mino[i], mino[i - t] + 1);

		while(true) {
			int N = sc.nextInt();
			if(N == 0)
				return;
			System.out.println(minp[N] + " " + mino[N]);
		}
	}

	int calc(int i) {
		return i * (i + 1) * (i + 2) / 6;
	}
}
package com.github.hiroshi_cl.wakaba.v2010.sol.gcj2010.qual;

import java.io.*;
import java.util.*;
import java.math.*;

import static java.lang.Math.*;
import static java.lang.Integer.*;
import static java.lang.Long.*;
import static java.lang.Double.*;
import static java.lang.Character.*;
import static java.util.Arrays.*;
import static java.util.Collections.*;
import static java.math.BigInteger.*;

public class B {
	static final int INF = 1 << 20;
	static final int[] di = { -1, 0, 0, 1 };
	static final int[] dj = { 0, -1, 1, 0 };
	static final char PROB = B.class.getSimpleName().charAt(0);
	static final boolean PRAC = false;
	static final Mode mode = Mode.TestLarge;

	static enum Mode {
		SampleTestSmall, SampleTestLarge, TestSmall, TestLarge, Diff
	}

	private static class Small extends Solver {
		protected String solve(Scanner sc) {
			int D = sc.nextInt();
			int I = sc.nextInt();
			int M = sc.nextInt();
			int N = sc.nextInt();
			int[] a = new int[N];
			int max = 0;
			for (int i = 0; i < N; i++)
				max = max(max, a[i] = sc.nextInt());
			int[][] dp = new int[N + 1][max + 1];
			for (int i = 0; i <= max; i++)
				dp[1][i] = min(D, abs(i - a[0]));
			for (int i = 1; i < N; i++) {
				for (int j = 0; j <= max; j++)
					for (int k = max(0, j - M); k < j; k++)
						dp[i][j] = min(dp[i][j], dp[i][k] + I);
				for (int j = max; j >= 0; j--)
					for (int k = min(max, j + M); k > j; k--)
						dp[i][j] = min(dp[i][j], dp[i][k] + I);
				int[] min = new int[max + 1];
				for (int j = 0; j <= max; j++) {
					min[j] = dp[i][j];
					for (int k = max(0, j - M); k < j; k++)
						min[j] = min(min[j], dp[i][k]);
					for (int k = min(max, j + M); k > j; k--)
						min[j] = min(min[j], dp[i][k]);
				}
				for (int j = 0; j <= max; j++)
					dp[i + 1][j] = min(min[j] + abs(j - a[i]), dp[i][j] + D);
			}
			int min = Integer.MAX_VALUE;
			for (int i = 0; i <= max; i++)
				min = min(min, dp[N][i]);
			return "" + min;
		}
	}

	private static class Large extends Solver {
		protected String solve(Scanner sc) {
			return new Small().solve(sc);
		}
	}

	private static abstract class Solver {
		protected abstract String solve(Scanner sc);

		public void run() {
			Scanner sc = new Scanner(System.in);
			int T = sc.nextInt();
			sc.nextLine();
			for (int t = 1; t <= T; t++)
				System.out.printf("Case #%s: %s%n", t, solve(sc));
		}
	}

	public static void main(String... args) {
		switch (mode) {
		case TestSmall: {
			String name = smallName(getMaxFileNum());
			setStream(name + ".in", name + ".out");
		}
		case SampleTestSmall:
			new Small().run();
			break;
		case TestLarge:
			setStream(largeName() + ".in", largeName() + ".out");
		case SampleTestLarge:
			new Large().run();
			break;
		case Diff: {
			int max = getMaxFileNum();
			for (int i = 0; i <= max; i++) {
				setStream(smallName(i) + ".in", "small_tmp");
				new Small().run();
				setStream(smallName(i) + ".in", "large_tmp");
				new Large().run();
				setStream("small_tmp", diffName(i));
				new Diff().diff(i, "small_tmp", "large_tmp");
			}
			break;
		}
		}
	}

	private static class Diff {
		public void diff(int id, String small, String large) {
			try {
				Scanner sm = new Scanner(new File(small));
				Scanner lg = new Scanner(new File(large));
				int line = 0;
				while (sm.hasNextLine() && lg.hasNextLine()) {
					String s = sm.nextLine();
					String l = lg.nextLine();
					if (!s.equals(l)) {
						System.out.println(line + ">" + s);
						System.out.println(line + "<" + l);
					}
					line++;
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private static int getMaxFileNum() {
		if (PRAC)
			return 0;
		int ret = -1;
		for (int i = 0; new File(smallName(i) + ".in").exists(); i++)
			ret = i;
		return ret;
	}

	private static String largeName() {
		return PROB + "-large" + (PRAC ? "-practice" : "");
	}

	private static String smallName(int a) {
		return PROB + "-small" + (PRAC ? "-practice" : "-attempt" + a);
	}

	private static String diffName(int a) {
		return PROB + "-small" + (PRAC ? "" : a) + ".dif";
	}

	private static void setStream(String in, String out) {
		try {
			System.setIn(new BufferedInputStream(new FileInputStream(in)));
			System.setOut(new PrintStream(out));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void debug(Object... os) {
		System.err.println(deepToString(os));
	}
}

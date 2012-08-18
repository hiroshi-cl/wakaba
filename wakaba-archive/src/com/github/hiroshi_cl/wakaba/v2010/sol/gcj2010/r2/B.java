package com.github.hiroshi_cl.wakaba.v2010.sol.gcj2010.r2;

import java.io.*;
import java.util.*;
import java.math.*;

import static java.lang.Math.*;
import static java.lang.Character.*;
import static java.util.Arrays.*;
import static java.util.Collections.*;
import static java.math.BigInteger.*;

public class B {
	static final int INF = Integer.MAX_VALUE;
	static final int[] di = {-1, 0, 0, 1};
	static final int[] dj = {0, -1, 1, 0};
	static final char PROB = B.class.getSimpleName().charAt(0);
	static final boolean PRAC = false;
	static final Mode mode = Mode.TestLarge;

	static enum Mode {
		SampleTestSmall, SampleTestLarge, TestSmall, TestLarge, Diff
	}

	private static class Small extends Solver {
		int P;
		int[] M;
		int[][] price;
		protected String solve(Scanner sc) {
			P = sc.nextInt();
			M = new int[1 << P];
			for (int i = 0; i < 1 << P; i++)
				M[i] = sc.nextInt();
			price = new int[P][];
			for (int i = 0; i < P; i++) {
				price[i] = new int[1 << P - i - 1];
				for (int j = 0; j < 1 << P - i - 1; j++)
					price[i][j] = sc.nextInt();
			}
			return "" + dfs(0, P - 1)[0];
		}

		int[] dfs(int i, int d) {
			if (d == -1) {
				int[] ret = new int[P + 2];
				for (int j = 0; j <= P + 1; j++)
					ret[j] = (j <= P && j <= M[i] ? 0 : INF);
				return ret;
			}
			int[] is0 = dfs(i * 2, d - 1);
			int[] is1 = dfs(i * 2 + 1, d - 1);
			int[] ret = new int[P + 2];
			for (int j = 0; j <= P + 1; j++)
				if (is0[j] == INF || is1[j] == INF)
					ret[j] = INF;
				else
					ret[j] = is0[j] + is1[j];
			for (int j = 1; j <= P + 1 && ret[j - 1] < INF; j++)
				ret[j - 1] = min(ret[j - 1] + price[d][i], ret[j]);
			return ret;
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
			for (int t = 1; t <= T; t++) {
				System.err.printf("Case #%s%n", t);
				System.out.printf("Case #%s: %s%n", t, solve(sc));
			}
			System.err.println("done.");
		}
	}

	public static void main(String... args) {
		switch (mode) {
			case TestSmall : {
				String name = smallName(getMaxFileNum());
				setStream(name + ".in", name + ".out");
			}
			case SampleTestSmall :
				new Small().run();
				break;
			case TestLarge :
				setStream(largeName() + ".in", largeName() + ".out");
			case SampleTestLarge :
				new Large().run();
				break;
			case Diff : {
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

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

public class C {
	static final int INF = 1 << 20;
	static final int[] di = { -1, 0, 0, 1 };
	static final int[] dj = { 0, -1, 1, 0 };
	static final char PROB = C.class.getSimpleName().charAt(0);
	static final boolean PRAC = true;
	static final Mode mode = Mode.TestLarge;

	static enum Mode {
		SampleTestSmall, SampleTestLarge, TestSmall, TestLarge, Diff
	}

	private static class Small extends Solver {
		static final double phi = (1 + sqrt(5)) * .5;

		protected String solve(Scanner sc) {
			int A1 = sc.nextInt();
			int A2 = sc.nextInt();
			int B1 = sc.nextInt();
			int B2 = sc.nextInt();
			long ret = 0;
			for (int A = A1; A <= A2; A++)
				ret += ramp(B2 - max(B1, (int) ceil(A * phi)) + 1)
						+ ramp(min(B2, (int) floor(A / phi)) - B1 + 1);
			return "" + ret;
		}

		int ramp(int l) {
			return max(l, 0);
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

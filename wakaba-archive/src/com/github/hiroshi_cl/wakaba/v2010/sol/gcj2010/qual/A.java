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

public class A {
	static final int INF = 1 << 20;
	static final int[] di = { -1, 0, -1, -1 };
	static final int[] dj = { 0, -1, 1, -1 };
	static final char PROB = A.class.getSimpleName().charAt(0);
	static final boolean PRAC = false;
	static final Mode mode = Mode.TestLarge;

	static enum Mode {
		SampleTestSmall, SampleTestLarge, TestSmall, TestLarge, Diff
	}

	private static class Small extends Solver {
		protected String solve(Scanner sc) {
			int N = sc.nextInt();
			int K = sc.nextInt();
			char[][] board = new char[N][];
			for (int i = 0; i < N; i++)
				board[i] = sc.next().toCharArray();
			char[][] rot = new char[N][N];
			for (int i = 0; i < N; i++)
				for (int j = 0; j < N; j++)
					rot[i][j] = board[N - j - 1][i];
			char[][] grav = new char[N][N];
			for (int i = 0; i < N; i++)
				for (int j = 0; j < N; j++)
					grav[i][j] = '.';
			for (int i = 0; i < N; i++)
				for (int j = N - 1, c = 0; j >= 0; j--)
					if (rot[j][i] == '.')
						c++;
					else
						grav[j + c][i] = rot[j][i];
			// for (char[] cs : grav)
			// debug(new String(cs));
			int[][][] R = new int[N][N][4];
			int[][][] B = new int[N][N][4];
			for (int i = 0; i < N; i++)
				for (int j = 0; j < N; j++)
					switch (grav[i][j]) {
					case 'B': {
						for (int d = 0; d < 4; d++)
							B[i][j][d] = (in(i + di[d], j + dj[d], N) ? B[i
									+ di[d]][j + dj[d]][d] : 0) + 1;
						break;
					}
					case 'R': {
						for (int d = 0; d < 4; d++)
							R[i][j][d] = (in(i + di[d], j + dj[d], N) ? R[i
									+ di[d]][j + dj[d]][d] : 0) + 1;
						break;
					}
					}
			boolean Rwin = false, Bwin = false;
			for (int i = 0; i < N; i++)
				for (int j = 0; j < N; j++)
					for (int d = 0; d < 4; d++) {
						if (R[i][j][d] >= K)
							Rwin = true;
						if (B[i][j][d] >= K)
							Bwin = true;
					}

			return Rwin ? Bwin ? "Both" : "Red" : Bwin ? "Blue" : "Neither";
		}

		boolean in(int i, int j, int N) {
			return 0 <= i && i < N && 0 <= j && j < N;
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

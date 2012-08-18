package com.github.hiroshi_cl.wakaba.v2010.sol.dp2008;

import java.util.*;

public class C {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO 自動生成されたメソッド・スタブ
		Scanner sc = new Scanner(System.in);
		for (;;) {
			String s = sc.next();
			if (s.equals("#")) {
				break;
			}
			s = s.replaceAll("ky", "K");
			s = s.replaceAll("sy", "S");
			s = s.replaceAll("ty", "T");
			s = s.replaceAll("hy", "H");
			s = s.replaceAll("py", "P");

			int sL = s.length();

			char[] c = s.toCharArray();
			boolean[] ans = new boolean[sL];
			for (int i = 0; i < sL; i++) {

				if ((i > 1 && ans[i - 2]) || (i > 2 && ans[i - 3])) {
				} else if (i > 0 && isIU(c[i]) && isMusei(c[i - 1])) { //
					if (i == sL - 1 || isMusei(c[i + 1]))
						ans[i] = true;

				} else if (i > 0 && i < sL - 2 && isAO(c[i])
						&& isMusei(c[i - 1]) && isMusei(c[i + 1])
						&& c[i + 2] == c[i]) { //

					ans[i] = true;
				}
			}
			String s2 = "";
			for (int i = 0; i < sL; i++) {
				s2 += ans[i] ? "(" + c[i] + ")" : c[i];
			}
			s2 = s2.replaceAll("K", "ky");
			s2 = s2.replaceAll("S", "sy");
			s2 = s2.replaceAll("T", "ty");
			s2 = s2.replaceAll("H", "hy");
			s2 = s2.replaceAll("P", "py");
			System.out.println(s2);
		}
	}

	static boolean isMusei(char c) {
		if (c == 'k' || c == 's' || c == 't' || c == 'h' || c == 'p'
				|| c == 'K' || c == 'S' || c == 'T' || c == 'H' || c == 'P')
			return (true);
		return (false);
	}

	static boolean isIU(char c) {
		if (c == 'i' || c == 'u')
			return (true);
		return (false);
	}

	static boolean isAO(char c) {
		if (c == 'a' || c == 'o')
			return (true);
		return (false);
	}
}
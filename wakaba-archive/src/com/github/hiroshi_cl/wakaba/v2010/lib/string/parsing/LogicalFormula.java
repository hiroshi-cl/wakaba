package com.github.hiroshi_cl.wakaba.v2010.lib.string.parsing;

import java.util.*;

/*
 * 論理式の構文解析
 *
 * EBNF:
 * expr = term {'|',term} = term ['|',expr]
 * term = fact {'&',fact} = fact ['&',term]
 * fact = '!',fact | '(',expr,')' | 'V' | 'F'
 */
public class LogicalFormula {
	int p;
	char[] cs;

	boolean expr() {
		boolean res = term();
		if (cs[p] == '|') {
			p++;
			res |= expr();
		}
		return res;
	}

	boolean term() {
		boolean res = fact();
		if (cs[p] == '&') {
			p++;
			res &= term();
		}
		return res;
	}

	boolean fact() {
		if (cs[p] == '(') {
			p++;
			boolean res = expr();
			p++;
			return res;
		} else if (cs[p] == '!') {
			p++;
			return !fact();
		}
		return cs[p++] == 'V';
	}

	void run() {
		Scanner sc = new Scanner(System.in);
		for (int o = 1; sc.hasNext(); o++) {
			cs = (sc.nextLine().replace(" ", "") + "=").toCharArray();
			p = 0;
			System.out.printf("Expression %d: %s%n", o, expr() ? "V" : "F");
		}
	}

	public static void main(String[] args) {
		new LogicalFormula().run();
	}
}
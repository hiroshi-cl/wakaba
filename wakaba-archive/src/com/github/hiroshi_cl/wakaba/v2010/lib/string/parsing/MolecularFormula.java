package com.github.hiroshi_cl.wakaba.v2010.lib.string.parsing;

/*
 * 分子式の構文解析
 *
 * EBNF:
 * expr = term[expr]
 * term = fact[number]
 * fact = (expr)|atom
 * atom = upper case letter{lower case letter}
 */
import java.util.*;

public class MolecularFormula {
	HashMap<String, Integer> map;
	char[] cs;
	int p;

	int expr() {
		int res = term();
		if (cs[p] == '(' || Character.isUpperCase(cs[p])) {
			res += expr();
		}
		return res;
	}

	int term() {
		int res = fact();
		res *= number();
		return res;
	}

	int fact() {
		if (cs[p] == '(') {
			p++;
			int res = expr();
			p++;
			return res;
		}
		return atom();
	}

	int number() {
		if (!Character.isDigit(cs[p]))
			return 1;
		int res = 0;
		while (Character.isDigit(cs[p])) {
			res *= 10;
			res += cs[p++] - '0';
		}
		return res;
	}

	int atom() {
		String s = cs[p] + "";
		p++;
		while (Character.isLowerCase(cs[p])) {
			s += cs[p++];
		}
		return map.get(s);
	}

	void run() {
		Scanner sc = new Scanner(System.in);
		map = new HashMap<String, Integer>();
		for (;;) {
			String s = sc.next();
			if (s.equals("END_OF_FIRST_PART"))
				break;
			map.put(s, sc.nextInt());
		}
		for (;;) {
			String s = sc.next();
			if (s.equals("0"))
				return;
			cs = (s + '=').toCharArray();
			p = 0;
			int res = 0;
			try {
				res = expr();
			} catch (Exception e) {
				res = -1;
			}
			System.out.println(res >= 0 ? res : "UNKNOWN");
		}
	}

	public static void main(String[] args) {
		new MolecularFormula().run();
	}
}
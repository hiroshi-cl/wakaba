package com.github.hiroshi_cl.wakaba.v2010.sol.d2006;

import java.util.*;
import java.math.*;
import static java.lang.Math.*;
import static java.math.BigInteger.*;
import static java.util.Arrays.*;
import static java.util.Collections.*;

public class E {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		new E().run(sc);
	}

	int max;

	StringBuilder term() {
		if (cs[p] == '=' || cs[p] == ')') {
			return new StringBuilder("");
		}
		StringBuilder res;
		if (Character.isDigit(cs[p])) {
			int n = number();
			StringBuilder tmp = fact();
			res = new StringBuilder();
			for (int i = 0; i < n; i++) {
				res.append(tmp);
				if (res.length() > max)
					break;
			}
		} else {
			res = new StringBuilder(fact());
		}
		if (res.length() > max)
			return res;
		res.append(term());
		return res;
	}

	StringBuilder fact() {
		if (cs[p] == '(') {
			p++;
			StringBuilder res = term();
			p++;
			return res;
		} else {
			return new StringBuilder(String.valueOf(cs[p++]));
		}
	}

	int number() {
		int res = 0;
		while (Character.isDigit(cs[p])) {
			res *= 10;
			res += cs[p++] - '0';
		}
		return res;
	}

	void run(Scanner sc) {
		for (;;) {
			String s = sc.next();
			int n = sc.nextInt();
			max = n;
			if (s.equals("0") && n == 0)
				break;
			cs = (s + "=").toCharArray();
			p = 0;
			StringBuilder res = term();
			System.out.println(res.length() <= n ? '0' : res.charAt(n));
		}
	}

	char[] cs;
	int p;

}
package com.github.hiroshi_cl.wakaba.v2009.sol.d2005.pku2685;
import java.util.*;

class PKU2685 {
	void run() {
		Scanner sc = new Scanner(System.in);
		int n = sc.nextInt();
		while(n-- > 0)
			System.out.println(toMCXI(toInt(sc.next()) + toInt(sc.next())));
	}

	char[] mcxi = {'m', 'c', 'x', 'i' };
	int[] base = { 1000, 100, 10, 1 };

	int toInt(String s) {
		char[] cs = s.toCharArray();
		int n = cs.length;
		int cash = 1;
		int ret = 0;
		for(int i = 0; i < n; i++) {
			if(Character.isDigit(cs[i]))
				cash = cs[i] - '0';
			else {
				for(int j = 0; j < mcxi.length; j++)
					if(mcxi[j] == cs[i])
						ret += cash * base[j];
				cash = 1;
			}
		}
		return ret;
	}

	String toMCXI(int num) {
		StringBuilder sb = new StringBuilder();
		for(int i = 0; num > 0; num %= base[i], i++) {
			if(num >= 2 * base[i])
				sb.append(num / base[i]);
			if(num >= base[i])
				sb.append(mcxi[i]);
		}
		return sb.toString();
	}
}


public class Main {
	public static void main(String[] args) {
		new PKU2685().run();
	}
}

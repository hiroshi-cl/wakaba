package com.github.hiroshi_cl.wakaba.v2010.sol.srm;

import java.util.*;
import java.util.regex.*;
import java.text.*;
import java.math.*;
import static java.lang.Math.*;
import static java.util.Arrays.*;
import static java.math.BigInteger.*;

public class DateFieldCorrection {

	static final String[] E1 = { "1234567890", "QWERTYUIOP", "ASDFGHJKL",
			"ZXCVBNM", "1Q2W3E4R5T6Y7U8I9O0P", "QAWSEDRFTGYHUJIKOLP",
			"AZSXDCFVGBGBHNHNJMK" };
	static final String[] E3 = { "X C V B N M" };
	static final int N = 37;
	static final int[][] M = new int[N][N];
	static final int INF = 1 << 20;

	static {
		for (int i = 0; i < N; i++)
			for (int j = 0; j < N; j++)
				M[i][j] = INF;
		for (int i = 0; i < N; i++)
			M[i][i] = 0;
		for (String s : E1)
			for (int i = 1; i < s.length(); i++)
				M[enc(s.charAt(i - 1))][enc(s.charAt(i))] = M[enc(s.charAt(i))][enc(s
						.charAt(i - 1))] = 1;
		for (String s : E3)
			for (int i = 1; i < s.length(); i++)
				M[enc(s.charAt(i - 1))][enc(s.charAt(i))] = M[enc(s.charAt(i))][enc(s
						.charAt(i - 1))] = 3;
		for (int k = 0; k < N; k++)
			for (int i = 0; i < N; i++)
				for (int j = 0; j < N; j++)
					M[i][j] = min(M[i][j], M[i][k] + M[k][j]);
	}

	static final String[] Month = { "January", "February", "March", "April",
			"May", "June", "July", "August", "September", "October",
			"November", "December" };
	static final int[] Days = { 31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };

	public String correctDate(String input) {
		int min = INF;
		String ret = null;
		for (int i = 0; i < 12; i++)
			for (int j = 1; j <= Days[i]; j++) {
				String s = Month[i] + " " + j;
				int p = calc(input, s);
				if (p < min) {
					// System.out.println(p + s);
					min = p;
					ret = s;
				}
			}

		return ret;
	}

	int calc(String input, String comp) {
		if (input.length() != comp.length())
			return INF;
		int ret = 0;
		for (int i = 0; i < input.length(); i++)
			ret += M[enc(input.charAt(i))][enc(comp.charAt(i))];
		return ret;
	}

	static int enc(char c) {
		return (c == ' ' ? 36 : c <= '9' ? c - '0' : c <= 'Z' ? c - 'A' + 10
				: c - 'a' + 10);
	}

}
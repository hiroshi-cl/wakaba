package com.github.hiroshi_cl.wakaba.v2010.sol.srm;

import java.util.*;
import java.util.regex.*;
import java.text.*;
import java.math.*;
import static java.lang.Math.*;
import static java.util.Arrays.*;
import static java.math.BigInteger.*;

public class DukeOnLargeChessBoard {
	static final int max = 999999;

	public String lastCell(String initPosition) {
		int c = Integer.parseInt(initPosition.split("[(,) ]+")[1]);
		int r = Integer.parseInt(initPosition.split("[(,) ]+")[2]);

		if (r == max)
			return make(0, c % 2 == 1 ? max : max - c - 1);
		else if (c == 0)
			return make(0, r + 1);
		else if (r == 1)
			return make(max, 0);
		else if (r == 0)
			return make(c % 2 == 1 ? 0 : c - 1, 0);
		else if (r >= c)
			if (r == c && c % 2 == 1)
				return make(1, 0);
			else
				return make(0, c % 2 == 1 ? r - c - 1 : r == max - 1 ? max : r
						- c + 1);
		else if ((r + c) % 2 == 1)
			return make(0, 0);
		else
			return make(r % 2 == 1 ? c - r + 1 : c - r - 1, 0);
	}

	String make(int c, int r) {
		return String.format("(%06d, %06d)", c, r);
	}

	String print(String s) {
		System.out.println(s);
		return s;
	}
}
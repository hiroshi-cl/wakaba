package com.github.hiroshi_cl.wakaba.v2010.sol.srm;

import static java.lang.Math.*;

public class SquaresInsideLattice {

	public long howMany(int width, int height) {
		long ret = 0;

		int m = min(width, height);
		for (long i = 1; i <= m; i++)
			ret += i * (width - i + 1) * (height - i + 1);
		return ret;
	}

}
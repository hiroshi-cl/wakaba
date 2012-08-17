package com.github.hiroshi_cl.wakaba.v2010.lib.math.enumeration;

import java.math.*;
import static java.math.BigInteger.*;


/*
not rooted : n^(n-2)
rooted     : n^(n-1)

-[[oeis:A000272]]
-[[oeis:A000169]]

- 入力 : ノード数
- 出力 : 木の個数

- O(log n).
*/

public class LabeledRootedTrees {
	BigInteger count(int n) {
		return valueOf(n).pow(n - 1);
	}
}

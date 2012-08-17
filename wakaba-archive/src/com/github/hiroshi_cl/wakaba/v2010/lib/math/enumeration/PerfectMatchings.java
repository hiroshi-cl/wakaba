package com.github.hiroshi_cl.wakaba.v2010.lib.math.enumeration;

import java.util.*;
import static java.util.Arrays.*;
import static java.lang.Math.*;

/*
完全マッチングの個数を求める。

theorem of Kasteleyn を使うと、平面グラフの完全マッチングの個数は多項式時間で解けるそうです。
2部グラフに関してもε近似の確率的に多項式時間のアルゴリズムがあるそうです。

- wataさんのコードを参考にさせていただきました。

- 入力 : V[] vs,long mod
- 出力 : グラフの完全マッチングの個数を法 mod で。

- O( Σ 2^activeなノード数 ) ? 
*/

/*
- ノードをひとつづつ追加していく。追加したノードを k とする。
- activeなノードとは、まだ見ていないノードとつながっているノード。
- activeなノードの中で、まだ対応するマッチングが存在しない点の集合を S としてビットで管理する。
-- ビットと実際のid との対応関係はちゃんと持っておかないといけない。
- dp[k][S] で状態を管理する。これは、実際は直前のものだけ持っておけばよい。
- 直前の S' から現在のありうる S を求める部分がちょっと厄介。
-- active → 非active になるノードが2個以上だと S は存在せず、1個だと ひとつだけ。0個 のときは、いくつかありうる。
*/

public class PerfectMatchings {
	long count(V[] vs, long mod) {
		int n = vs.length;
		for (int i = 0; i < n; i++) {
			vs[i].id = i;
		}
		for (V v : vs) {
			for (V to : v.es) {
				v.max = max(v.max, to.id);
			}
		}
		int[] v2i = new int[n];
		int[] i2v = new int[n];
		fill(v2i, -1);
		int active = 0;
		long[] ways = { 1 };
		for (int k = 0; k < n; k++) {
			int[] nv2i = new int[n];
			int[] ni2v = new int[n];
			fill(nv2i, -1);
			int nactive = 0;
			for (int i = 0; i <= k; i++) {
				if (vs[i].max > k) {
					nv2i[i] = nactive;
					ni2v[nactive++] = i;
				}
			}
			long[] nways = new long[1 << nactive];
			loop: for (int mask = 0; mask < 1 << active; mask++) {
				if (ways[mask] > 0) {
					boolean need = false;
					int nmask = 0;
					for (int i = 0; i < active; i++) {
						if ((mask >> i & 1) == 1) {
							if (nv2i[i2v[i]] < 0) {
								if (need)
									continue loop;
								need = true;
							} else {
								nmask |= 1 << nv2i[i2v[i]];
							}
						}
					}
					if (need) {
						nways[nmask] += ways[mask];
						nways[nmask] %= mod;
					} else {
						if (nv2i[k] >= 0) {
							nways[nmask | 1 << nv2i[k]] += ways[mask];
							nways[nmask | 1 << nv2i[k]] %= mod;
						}
						for (V v : vs[k].es) {
							if (v.id < k && (mask >> v2i[v.id] & 1) == 1) {
								nways[nmask ^ 1 << nv2i[v.id]] += ways[mask];
								nways[nmask ^ 1 << nv2i[v.id]] %= mod;
							}
						}
					}
				}
			}
			v2i = nv2i;
			i2v = ni2v;
			active = nactive;
			ways = nways;
		}
		return ways[0];
	}

	class V {
		ArrayList<V> es = new ArrayList<V>();
		int id, max;
	}
}

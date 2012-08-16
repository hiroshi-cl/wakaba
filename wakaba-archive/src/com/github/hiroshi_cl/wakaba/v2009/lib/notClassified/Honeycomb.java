package com.github.hiroshi_cl.wakaba.v2009.lib.notClassified;

import java.util.ArrayList;

/*
 * 六角形のグリッドを表すデータ構造。
 * 縦の線がある六角形を敷き詰めた六角形のグリッドにおいて、以下のように変数を定める。
 * s0:グリッドの下の長さ（六角形の個数）
 * s1:グリッドの左下の長さ
 * s2:グリッドの左上の長さ
 * s3:グリッドの上の長さ
 * x軸を右方向、y軸を左上方向にとり、原点を左下とする。このとき(x,y)がvalidである条件は、
 * 	   0 <=   x <= s2+s3-2,
 *     0 <=   y <= s1+s2-2,
 *  1-s0 <= y-x <= s1-1.
 *
 * 参考サイト： http://www.topcoder.com/tc?module=Static&d1=match_editorials&d2=srm355
 *
 * verified: TopCoder SRM355 DIV1 Hard
 */
class Honeycomb {
	int[] dx = new int[] { 1, 0, -1, -1, 0, 1 };
	int[] dy = new int[] { 0, -1, -1, 0, 1, 1 };
	int s0, s1, s2, s3;
	int xl, yl;// x,yの最大+1
	V[][] vss;

	Honeycomb(int s0, int s1, int s2, int s3) {
		this.s0 = s0;
		this.s1 = s1;
		this.s2 = s2;
		this.s3 = s3;
		xl = s2 + s3 - 1;
		yl = s1 + s2 - 1;
		vss = new V[xl][yl];
		for (int i = 0; i < xl; i++)
			for (int j = 0; j < yl; j++)
				vss[i][j] = new V(i, j);
		build();
	}

	void build() {
		for (int i = 0; i < xl; i++) {
			for (int j = 0; j < yl; j++) {
				vss[i][j] = new V(i, j);
				if (isValid(i, j)) {
					vss[i][j].state = isBorder(i, j) ? 2 : 1;
					for (int d = 0; d < 6; d++)
						if (isValid(i + dx[d], j + dy[d]))
							vss[i][j].es.add(vss[i + dx[d]][j + dy[d]]);
				}
			}
		}
	}

	boolean isValid(int x, int y) {
		return 0 <= x && x < xl && 0 <= y && y < yl && 1 - s0 <= y - x
				&& y - x <= s1 - 1;
	}

	boolean isBorder(int x, int y) {
		return 0 == x || x == xl - 1 || 0 == y || y == yl - 1
				|| 1 - s0 == y - x || y - x == s1 - 1;
	}

	class V {
		ArrayList<V> es = new ArrayList<V>();
		int x, y;
		int state = 0;// 0:invalid , 1:valid , 2:border

		V(int x, int y) {
			this.x = x;
			this.y = y;
		}
	}
}
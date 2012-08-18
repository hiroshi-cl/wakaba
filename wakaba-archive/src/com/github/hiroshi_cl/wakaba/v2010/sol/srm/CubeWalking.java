package com.github.hiroshi_cl.wakaba.v2010.sol.srm;

public class CubeWalking {
	String finalPosition(String movement) {
		char[] cs = movement.toCharArray();
		int l = cs.length;
		int x = 1, y = 1;
		int d = 0;
		for (int i = 0; i < l; i++) {
			switch (cs[i]) {
			case 'L':
				d = (d + 1) % 4;
				break;
			case 'R':
				d = (d + 4 - 1) % 4;
				break;
			case 'W':
				x = (x + dx[d] + 3) % 3;
				y = (y + dy[d] + 3) % 3;
				break;
			default:
				break;
			}
		}
		return c[f[x][y]];
	}

	String[] c = new String[] { "GREEN", "BLUE", "RED" };
	int[][] f = new int[][] { { 2, 1, 2 }, { 1, 0, 1 }, { 2, 1, 2 } };

	int[] dx = new int[] { 1, 0, -1, 0 };
	int[] dy = new int[] { 0, 1, 0, -1 };
}
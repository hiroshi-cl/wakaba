package com.github.hiroshi_cl.wakaba.v2010.sol.d2004;

import java.util.*;
import static java.util.Arrays.*;
import static java.lang.Math.*;

public class B {
	public static void main(String[] args) {
		new B().run();
	}

	static void debug(Object... o) {
		System.err.println(deepToString(o));
	}

	static int[] dx = { -1, 0, 1, 0 }, dy = { 0, 1, 0, -1 };

	void run() {
		Scanner sc = new Scanner(System.in);
		for (;;) {
			int W = sc.nextInt(), H = sc.nextInt();
			if (W == 0)
				break;
			char[][] c = new char[H][W];
			int mx = 0, my = 0;
			for (int i = 0; i < H; i++) {
				c[i] = sc.next().toCharArray();
				for (int j = 0; j < W; j++) {
					if (c[i][j] == '@') {
						mx = i;
						my = j;
					}
				}
			}
			Queue<Integer> qx = new LinkedList<Integer>(), qy = new LinkedList<Integer>();
			qx.offer(mx);
			qy.offer(my);
			int count = 1;
			while (!qx.isEmpty()) {
				mx = qx.poll();
				my = qy.poll();
				for (int i = 0; i < 4; i++) {
					int nx = mx + dx[i], ny = my + dy[i];
					if (nx >= 0 && nx < H && ny >= 0 && ny < W
							&& c[nx][ny] == '.') {
						qx.offer(nx);
						qy.offer(ny);
						count++;
						c[nx][ny] = '#';
					}
				}
			}
			System.out.println(count);
		}
	}
}
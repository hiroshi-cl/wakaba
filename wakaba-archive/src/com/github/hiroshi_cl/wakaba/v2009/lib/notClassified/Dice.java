package com.github.hiroshi_cl.wakaba.v2009.lib.notClassified;

public class Dice {
	/*
	 * 4 _ 5|0|2 ウラ3 x軸：下から上　　y軸：後から前　z軸：左から右 _ 1
	 */
	int[] id = new int[6];
	static final int TOP = 0, FRONT = 1, RIGHT = 2, BOTTOM = 3, BACK = 4,
			LEFT = 5;

	Dice() {
		for (int i = 0; i < 6; i++)
			id[i] = i;
	}

	Dice(Dice di) {
		for (int i = 0; i < 6; i++)
			id[i] = di.id[i];
	}

	Dice rot2(int top, int front) { // 自分と同値で、top,frontの値が同じものを返す(破壊的にすべきか？)
		Dice res = copy();
		for (int i = 0; i < 6; i++) {
			for (int j = 0; j < 4; j++) {
				if (res.id[TOP] == top && res.id[FRONT] == front)
					return res;
				res.roll_z();
			}
			if (i % 2 == 0)
				res.roll_x();
			else
				res.roll_y();
		}
		return null;
	}

	Dice copy() {
		return new Dice(this);
	}

	void roll(int r) { // r方向に転がすr=1,2,4,5
		if (r == 1)
			roll(1, 0, 4, 3);
		if (r == 2)
			roll(2, 0, 5, 3);
		if (r == 4)
			roll(4, 0, 1, 3);
		if (r == 5)
			roll(5, 0, 2, 3);
	};

	void roll_x() {
		roll(2, 1, 5, 4);
	}// x軸周りに９０度回転(x軸の正の向きを上としたとき、前の面を右側にする)

	void roll_y() {
		roll(0, 2, 3, 5);
	}// y 〃

	void roll_z() {
		roll(1, 0, 4, 3);
	}// z 〃

	private void roll(int a, int b, int c, int d) {
		int tmp = id[a];
		id[a] = id[b];
		id[b] = id[c];
		id[c] = id[d];
		id[d] = tmp;
	}

	boolean equivalents(Dice di) {
		Dice cp = new Dice(di);
		for (int i = 0; i < 6; i++) {
			for (int j = 0; j < 4; j++) {
				if (equals(cp))
					return true;
				cp.roll_z();
			}
			if (i % 2 == 0)
				cp.roll_x();
			else
				cp.roll_y();
		}
		return false;
	}

	boolean equals(Dice di) {
		for (int i = 0; i < 6; i++)
			if (id[i] != di.id[i])
				return false;
		return true;
	}
}
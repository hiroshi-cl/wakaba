package com.github.hiroshi_cl.wakaba.v2010.sol.r2004;

import java.util.ArrayList;
import java.util.Arrays;

class Dice {
	static final Dice Identity = new Dice(new int[] { 1, 2, 3, 4, 5, 6 });// problem specific.
	static ArrayList<Dice> find(int[] spots) {
		ArrayList<Dice> res = new ArrayList<Dice>();
		Dice die = Identity;
		for (int i = 0; i < 6; i++) {
			if ((i & 1) == 0) die.roll_Top2Back();
			else die.roll_Top2Right();
			loop: for (int j = 0; j < 4; j++) {
				die.roll_Front2Right();
				for (int k = 0; k < 6; k++) {
					if (spots[k] != 0 && spots[k] != die.spots[k]) continue loop;
				}
				res.add(new Dice(die.spots));
			}
		}
		return res;
	}
	// top,front,right,left,back,bottom.
	int[] spots;
	Dice(int[] spots) {
		this.spots = spots.clone();
	}
	void roll_Top2Back() {// destructive
		roll(4, 0, 1, 5);
	}
	void roll_Top2Right() {// destructive
		roll(2, 0, 3, 5);
	}
	void roll_Front2Right() {// destructive
		roll(2, 1, 3, 4);
	}
	void roll(int a, int b, int c, int d) {// destructive
		int tmp = spots[a];
		spots[a] = spots[b];
		spots[b] = spots[c];
		spots[c] = spots[d];
		spots[d] = tmp;
	}
	boolean equivalent(Dice die) {
		boolean res = false;
		for (int i = 0; i < 6; i++) {
			if ((i & 1) == 0) roll_Top2Back();
			else roll_Top2Right();
			for (int j = 0; j < 4; j++) {
				roll_Front2Right();
				if (!res && Arrays.equals(spots, die.spots)) res = true;
			}
		}
		return res;
	}
	int top() {
		return spots[0];
	}
	int front() {
		return spots[1];
	}
	int right() {
		return spots[2];
	}
	int left() {
		return spots[3];
	}
	int back() {
		return spots[4];
	}
	int bottom() {
		return spots[5];
	}
}
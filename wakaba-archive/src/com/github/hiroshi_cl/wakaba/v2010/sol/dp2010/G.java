package com.github.hiroshi_cl.wakaba.v2010.sol.dp2010;

import java.util.*;
import java.math.*;
import java.io.*;

import static java.util.Arrays.*;
import static java.util.Collections.*;
import static java.lang.Math.*;

public class G {
	public static void main(String[] args) {
		new G().run();
	}

	void debug(Object... os) {
		System.err.println(deepToString(os));
	}

	void run() {
		Scanner sc = new Scanner(System.in);
		try {
			String s = this.getClass().getSimpleName();
			sc = new Scanner(new File(s));
			System.setOut(new PrintStream(s + ".out"));
		} catch (Exception e) {
			// TODO: handle exception
		}

	}

	class P {
		double x, y;

		P(double x, double y) {
			this.x = x;
			this.y = y;
		}

		P add(P o) {
			return new P(x + o.x, y + o.y);
		}

		P sub(P o) {
			return new P(x - o.x, y - o.y);
		}

		P mul(double m) {
			return new P(x * m, y * m);
		}

		P div(double m) {
			return new P(x / m, y / m);
		}

		double dot(P o) {
			return x * o.x + y * o.y;
		}

		double det(P o) {
			return x * o.y - y * o.x;
		}

		double norm2() {
			return x * x + y * y;
		}

		double norm() {
			return sqrt(norm2());
		}

		double dist(P o) {
			return sub(o).norm();
		}

		@Override
		public String toString() {
			return String.format("(%.2f,%.2f)\n", x, y);
		}

	}
}

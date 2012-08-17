package com.github.hiroshi_cl.wakaba.v2010.lib.data.mapping;

import static java.lang.Math.*;

import java.io.*;

public class HoneycombSheet {
	final double r;
	final int w, h;
	final P p0;

	static final P v1 = new P(-.5, sqrt(3) * .5);
	static final P v2 = new P(.5, sqrt(3) * .5);
	static final P v3 = new P(1., 0.);
	static final P u1 = new P(3., 0);
	static final P u2 = new P(0, sqrt(3));
	static final P u3 = new P(1.5, sqrt(3) * .5);

	String draw() {
		StringBuilder sb = new StringBuilder();
		sb.append("newpath\n");
		for (int i = 0; i < w; i++)
			for (int j = 0; j < h; j++)
				for (int k = 0; k < 2; k++) {
					P p = p0.add(u1.scale(i * r)).add(u2.scale(j * r))
							.add(u3.scale(k * r));
					sb.append(String.format("%s moveto%n", p));
					sb.append(String.format("%s rlineto%n", v1.scale(r)));
					sb.append(String.format("%s rlineto%n", v2.scale(r)));
					sb.append(String.format("%s rlineto%n", v3.scale(r)));
					sb.append("\n");
				}
		sb.append("stroke\n");
		return sb.toString();
	}

	public HoneycombSheet(double r, int w, int h, P p0) {
		this.r = r;
		this.w = w;
		this.h = h;
		this.p0 = p0;
	}

	static class P {
		final double x, y;

		public P(double x, double y) {
			this.x = x;
			this.y = y;
		}

		P add(P p) {
			return new P(x + p.x, y + p.y);
		}

		P scale(double d) {
			return new P(d * x, d * y);
		}

		@Override
		public String toString() {
			return String.format("%.3f %.3f", x, y);
		}
	}

	public static void main(String... args) throws Exception {
		System.setOut(new PrintStream("honeycomb.ps"));
		System.out.println(new HoneycombSheet(30, 20, 20, new P(-100, -100))
				.draw());
	}
}

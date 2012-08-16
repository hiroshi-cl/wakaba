package com.github.hiroshi_cl.wakaba.v2009.lib.geometryInt;

import java.awt.*;
import static java.lang.Math.*;

/**
 * 幾何ライブラリのデバッグ用 Visualizer(Minimal).
 * 
 * @author wakaba
 */
@SuppressWarnings("serial")
public class MinimalVis {
	private static final Frame fr = new Frame() {
		@Override
		public void paint(Graphics g) {
			super.paint(g);
			if (vs == null)
				return;
			int[] x = new int[vs.length];
			int[] y = new int[vs.length];
			for (int i = 0; i < vs.length; i++) {
				x[i] = sx + (int) round(pw * vs[i].x);
				y[i] = sy - (int) round(pw * vs[i].y);
			}
			g.drawPolyline(x, y, vs.length);
		}
	};
	private static Vector[] vs = null;
	private static int sx = 200;
	private static int sy = 200;
	private static double pw = 1.;
	static {
		fr.setSize(sx * 2, sy * 2);
		fr.setVisible(true);
	}

	/**
	 * 折れ線を画面に描画する. 中断せず上書き.
	 * 
	 * @param v
	 *            折れ線を構成する点
	 */
	public static void debugLine(Vector... v) {
		vs = v;
		fr.repaint();
	}

	public static void main(String[] args) {
		MinimalVis.debugLine(new Vector(50, 100), new Vector(100, 150),
				new Vector(50, 150));
		MinimalVis.debugLine(new Vector(150, 100), new Vector(100, 150),
				new Vector(150, 150));
	}
}
package com.github.hiroshi_cl.wakaba.v2009.lib.geometryInt;

import java.awt.*;
import java.awt.event.*;
import static java.lang.Math.*;

/**
 * 幾何ライブラリのデバッグ用 Visualizer(Simple).
 * 
 * @author wakaba
 */
@SuppressWarnings("serial")
public class SimpleVis {
	private final Frame fr = new Frame() {
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
	private Vector[] vs = null;
	private int sx = 200;
	private int sy = 200;
	private double pw = 1.;
	private boolean lock = false;

	public SimpleVis() {
		fr.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				lock = false;
			}
		});
		fr.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		fr.setSize(sx * 2, sy * 2);
		fr.setVisible(true);
	}

	/**
	 * 折れ線を画面に描画する. 何らかの入力があるまで, プログラムを中断.
	 * 
	 * @param v
	 *            折れ線を構成する点
	 */
	public void debugLine(Vector... v) {
		vs = v;
		fr.repaint();
		lock = true;
		try {
			while (lock)
				Thread.sleep(100);
		} catch (InterruptedException e) {
		}
	}

	public static void main(String[] args) {
		SimpleVis sv = new SimpleVis();
		while (true) {
			sv.debugLine(new Vector(50, 100), new Vector(100, 150), new Vector(
					50, 150));
			sv.debugLine(new Vector(150, 100), new Vector(100, 150),
					new Vector(150, 150));
		}
	}
}
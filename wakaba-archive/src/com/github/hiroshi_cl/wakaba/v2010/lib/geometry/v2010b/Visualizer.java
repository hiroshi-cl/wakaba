package com.github.hiroshi_cl.wakaba.v2010.lib.geometry.v2010b;

import java.awt.*;
import java.awt.image.*;
import java.awt.event.*;

/**
 * 幾何ライブラリのデバッグ用 Visualizer.
 * 
 * @author wakaba
 */
@SuppressWarnings("serial")
public class Visualizer {
	private static final int sx = 1280, sy = 960;
	private BufferedImage img;
	private Graphics2D g;

	private boolean lock = false;

	private final Frame fr = new Frame() {
		@Override
		public void paint(Graphics g) {
			super.paint(g);
			g.drawImage(img, 0, 0, null);
		}
	};

	public Visualizer() {
		clear();

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
		fr.setSize(sx, sy);
		fr.setVisible(true);
	}

	/** 画像を画面に描画する. 何らかの入力があるまで, プログラムを中断. */
	public void update() {
		lock = true;
		fr.repaint();
		try {
			while (lock)
				Thread.sleep(100);
		} catch (InterruptedException e) {
		}
	}

	/** 図形をバッファに線で描画する. この時点では画面の更新は起きない. */
	public void draw(Shape s) {
		g.setColor(Color.white);
		g.draw(s);
	}

	// toShapeして使う

	public void clear() {
		img = new BufferedImage(sx, sy, BufferedImage.TYPE_3BYTE_BGR);
		g = img.createGraphics();
		g.translate(sx * .5, sy * .5); // 原点を画面中心に持ってくる

		// 問題に合わせて画面全体に広がるようにスケーリング
		g.scale(sy * .5, sy * .5);
		g.setStroke(new BasicStroke(1.f / (sy * .5f))); // 太さを調節する
	}

	public static void main(String... args) {
		Visualizer v = new Visualizer();
		C[] cs = new C[6];
		for (int i = 0; i < 6; i++)
			cs[i] = new C(2 * Math.random() - 1, 2 * Math.random() - 1,
					.25 * Math.random());
		for (int k = 0; k < 10; k++) {
			for (int i = 0; i < 6; i++)
				v.draw(cs[i].toShape());
			// v.draw(new L(new P(0, 0), new P(100, 100), 0).toShape());
			v.update();
			v.clear();
			for (int i = 0; i < 6; i++)
				cs[i] = new C(cs[i], Math.random());
		}

	}
}
/*
- 参考資料/未整理の幾何とか参考資料/幾何ライブラリ2010-1とかを参考に直前にまとめたもの
-- ほとんどVerifyされていない。
-- Visualizerがあればバグなんてすぐ見つかるよね！
- awtがimportされてるが、Visualizer専用なので使わないなら外してよい。
*/
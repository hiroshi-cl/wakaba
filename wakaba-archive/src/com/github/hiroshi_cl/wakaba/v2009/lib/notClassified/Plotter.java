package com.github.hiroshi_cl.wakaba.v2009.lib.notClassified;

import java.io.*;
import java.util.*;

public class Plotter {
	static final boolean eccs = true;
	final String gpath, dpath;
	final Process pr;
	final Formatter gp;

	Plotter(String d) throws Exception {
		gpath = eccs ? "/mnt/sw-10.5/bin/" : "";
		dpath = d + (eccs ? "/" : "\\");
		pr = Runtime.getRuntime().exec(gpath + "gnuplot");
		gp = new Formatter(pr.getOutputStream());
		new ErrorReader(pr.getErrorStream()).start();
		if (!eccs)
			gp.format("set terminal pdf%n");
		gp.format("set style line 1 lt 3 %n");
		gp.format("set style line 2 lt 2 %n");
		gp.format("set style line 3 lt 5 %n");
		gp.format("set style line 4 lt 7 %n");
		gp.format("set style line 5 lt 9 %n");
		gp.format("set style line 6 lt 0 %n");
		gp.flush();
	}

	void xrange(double x0, double x1) {
		gp.format("set xrange [%.3f: %.3f] %n", x0, x1);
		gp.flush();
	}

	void yrange(double y0, double y1) {
		gp.format("set yrange [%.3f: %.3f] %n", y0, y1);
		gp.flush();
	}

	void output(int id, double[] d) {
		try {
			int l = d.length;
			Formatter fm = new Formatter(String.format(dpath + "DA%d.out", id));
			for (int i = 0; i < l; i++)
				fm.format("%d %.3f%n", i, d[i]);
			fm.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	void output2D(int id, double[] x, double[] y) {
		try {
			int l = x.length;
			Formatter fm = new Formatter(String.format(dpath + "DA%d.out", id));
			for (int i = 0; i < l; i++)
				fm.format("%.3f %.3f%n", x[i], y[i]);
			fm.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	void plot(int id, boolean xlog, boolean ylog, boolean line, int... ids) {
		int n = ids.length;
		String[] names = new String[ids.length];
		for (int i = 0; i < ids.length; i++)
			names[i] = dpath + "DA" + ids[i] + ".out";

		if (!eccs)
			gp.format("set terminal pdf%n set output '%sDB%s.pdf' %n", dpath,
					id);

		gp.format("set grid %n");

		if (xlog)
			gp.format("set logscale x%n");
		else
			gp.format("unset logscale x%n");
		if (ylog)
			gp.format("set logscale y%n");
		else
			gp.format("unset logscale y%n");
		if (eccs)
			gp.format("set title 'ID:%d'%n", id);

		gp.format("plot ");
		for (int i = 0; i < n; i++)
			gp.format("'%s' using 1:2 with %s ls %d%s", names[i], line ? "line"
					: "points", i + 1, i == n - 1 ? "" : ",");
		gp.format("%n%n");
		gp.flush();
	}

	void gopen(int id) {
		try {
			if (!eccs)
				Runtime.getRuntime().exec(
						"explorer " + dpath + "DB" + id + ".pdf");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void finalize() throws Throwable {
		quit();
		super.finalize();
	}

	void quit() {
		gp.format("quit%n");
		gp.flush();
	}

	static class ErrorReader extends Thread {
		InputStream error;

		public ErrorReader(InputStream is) {
			error = is;
		}

		public void run() {
			try {
				byte[] ch = new byte[50000];
				int read;
				while ((read = error.read(ch)) > 0) {
					String s = new String(ch, 0, read);
					System.err.print(s);
					System.err.flush();
				}
			} catch (Exception e) {
			}
		}
	}
}

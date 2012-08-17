package com.github.hiroshi_cl.wakaba.v2010.lib.math.number;

public class Complex {
	final double EPS = 0.0000001;

	public double re, im;

	public Complex(double r, double i) {
		re = r;
		im = i;
	}

	public final static Complex i = new Complex(0.0, 1.0);

	public static Complex polar(double theta, double r) {
		return new Complex(r * Math.cos(theta), r * Math.sin(theta));
	}

	public Complex add(Complex o) {
		return new Complex(re + o.re, im + o.im);
	}

	public Complex sub(Complex o) {
		return new Complex(re - o.re, im - o.im);
	}

	public Complex mul(Complex o) {
		return new Complex(re * o.re - im * o.im, re * o.im + im * o.re);
	}

	public Complex div(Complex o) {
		return new Complex((re * o.re + im * o.im) / o.abs2(), (im * o.re - re
				* o.im)
				/ o.abs2());
	}

	public boolean eq(Complex o) {
		return o.re - EPS < re && re < o.re + EPS && o.im - EPS < im
				&& im < o.im + EPS;
	}

	public Complex conj() {
		return new Complex(re, -im);
	}

	public double arg() {
		return Math.atan2(im, re);
	}

	public double abs2() {
		return re * re + im * im;
	}

	public double abs() {
		return Math.sqrt(abs2());
	}

	public double inner_product(Complex o) { // this.mul(o.conj()).re
		return re * o.re + im * o.im;
	}

	public double cross_product(Complex o) { // this.mul(o.conj()).im
		return re * o.im - im * o.re;
	}
}

package com.github.hiroshi_cl.wakaba.v2010.lib.math.number;

/*
複素数が二次元上の点と一対一対応するのと同様に，四元数の虚部は三次元上の点と一対一対応します．
特に三次元の回転を表現するのに便利で回転行列とベクトルの代わりに用いられているようです．
*/

class Quartanion {
	double w, x, y, z;

	Quartanion(double w, double x, double y, double z) {
		this.w = w;
		this.x = x;
		this.y = y;
		this.z = z;
	}

	Quartanion(double w) {
		this(w, 0, 0, 0);
	}

	final static Quartanion i = new Quartanion(0, 1, 0, 0);
	final static Quartanion j = new Quartanion(0, 0, 1, 0);
	final static Quartanion k = new Quartanion(0, 0, 0, 1);

	Quartanion add(Quartanion o) {
		return new Quartanion(w + o.w, x + o.x, y + o.y, z + o.z);
	}

	Quartanion sub(Quartanion o) {
		return new Quartanion(w - o.w, x - o.x, y - o.y, z - o.z);
	}

	Quartanion mul(double a) {
		return new Quartanion(a * w, a * x, a * y, a * z);
	}

	Quartanion mul(Quartanion o) {
		double nw = w * o.w - x * o.x - y * o.y - z * o.z;
		double nx = w * o.x + x * o.w + y * o.z - z * o.y;
		double ny = w * o.y - x * o.z + y * o.w + z * o.x;
		double nz = w * o.z + x * o.y - y * o.x + z * o.w;
		return new Quartanion(nw, nx, ny, nz);
	}

	Quartanion div(double a) {
		return new Quartanion(w / a, x / a, y / a, z / a);
	}

	Quartanion div(Quartanion o) {
		return mul(o.inverse());
	}

	boolean eq(Quartanion o) {
		final double EPS = 0.0000001;
		double dw = w - o.w, dx = x - o.x, dy = y - o.y, dz = z - o.z;
		return Math.abs(dw) < EPS && Math.abs(dx) < EPS && Math.abs(dy) < EPS
				&& Math.abs(dz) < EPS;
	}

	Quartanion conj() {
		return new Quartanion(w, -x, -y, -z);
	}

	Quartanion inverse() {
		return conj().div(norm2());
	}

	double norm() {
		return Math.sqrt(norm2());
	}

	double norm2() {
		return w * w + x * x + y * y + z * z;
	}
}

package com.github.hiroshi_cl.wakaba.v2010.sol.r2001;
import java.util.*;
import static java.lang.Math.*;
import static java.util.Arrays.*;
import static java.util.Collections.*;
public class E {
	public static void main(String[] args) {
		new E().run();
	}
	void run() {
		Scanner sc = new Scanner(System.in);
		int o = sc.nextInt();
		sc.nextLine();
		while (o-- > 0) {
			Shape s1 = new Shape(), s2 = new Shape();
			s1.input(sc.nextLine().toCharArray());
			s2.input(sc.nextLine().toCharArray());
			System.out.println(s1.same(s2) ? "true" : "false");
			sc.nextLine();
		}
	}
	class Shape {
		int n;
		boolean same(Shape s) {
			if (n != s.n) return false;
			s.normalize(0);
			for (int i = 0; i < n; i++) {
				normalize(i);
				for (int j = 0; j < 6; j++) {
					rotate();
					if (equals(s)) return true;
				}
			}
			return false;
		}
		void rotate() {
			for (int i = 0; i < n; i++) {
				cells[i] = cells[i].rotate();
			}
			sort(cells);
		}
		void normalize(int id) {
			Cell c = new Cell(cells[id].x, cells[id].y);
			for (int i = 0; i < n; i++) {
				cells[i] = cells[i].sub(c);
			}
			sort(cells);
		}
		Cell[] cells;
		void input(char[] cs) {
			int k = cs.length;
			TreeSet<Cell> set = new TreeSet<Cell>();
			Cell bef = new Cell(0, 0);
			set.add(bef);
			for (int i = 0; i < k; i++) {
				bef = bef.move(cs[i] - 'a');
				set.add(bef);
			}
			cells = set.toArray(new Cell[0]);
			n = cells.length;
		}
		public boolean equals(Object obj) {
			Shape s = (Shape) obj;
			if (n != s.n) return false;
			for (int i = 0; i < n; i++) {
				if (cells[i].x != s.cells[i].x || cells[i].y != s.cells[i].y) return false;
			}
			return true;
		}
	}
	class Cell implements Comparable<Cell> {
		int x, y;
		void show() {
			System.out.println(x + " " + y);
		}
		Cell rotate() {
			return new Cell(x + y, -x);
		}
		Cell sub(Cell c) {
			return new Cell(x - c.x, y - c.y);
		}
		public int compareTo(Cell o) {
			return x - o.x == 0 ? y - o.y : x - o.x;
		}
		Cell(int x, int y) {
			this.x = x;
			this.y = y;
		}
		public boolean equals(Object obj) {
			Cell c = (Cell) obj;
			return x == c.x && y == c.y;
		}
		Cell move(int d) {
			return new Cell(x + dx[d], y + dy[d]);
		}
	}
	int[] dx = new int[] { -1, 0, 1, 1, 0, -1 };
	int[] dy = new int[] { 1, 1, 0, -1, -1, 0 };
}
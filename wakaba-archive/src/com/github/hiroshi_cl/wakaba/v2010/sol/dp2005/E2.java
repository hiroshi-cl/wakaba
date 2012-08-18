package com.github.hiroshi_cl.wakaba.v2010.sol.dp2005;

import java.io.*;
import java.util.*;
import java.math.*;
import static java.lang.Math.*;
import static java.util.Arrays.*;
import static java.util.Collections.*;
import static java.math.BigInteger.*;

class E2 {
	static final int INF = 1 << 28;

	public void run() {
		Scanner sc = new Scanner(System.in);
		boolean flg = false;
		while (sc.hasNext()) {
			int n = sc.nextInt();
			int m = sc.nextInt();
			if (n == 0 && m == 0)
				return;
			if (flg)
				System.out.println();
			flg = true;
			map = new int[n][n];
			nmp = new int[n][n];

			unwaited = new boolean[n];
			lp = new ArrayList<Letter>();
			of = new LL[n];

			for (int i = 0; i < n; i++)
				for (int j = 0; j < n; j++)
					map[i][j] = INF;
			for (int i = 0; i < m; i++) {
				int s = sc.nextInt() - 1;
				int t = sc.nextInt() - 1;
				int d = sc.nextInt();
				map[s][t] = d;
				map[t][s] = d;
				nmp[s][t] = t;
				nmp[t][s] = s;
			}
			for (int k = 0; k < n; k++)
				for (int i = 0; i < n; i++)
					for (int j = 0; j < n; j++)
						if (map[i][j] > map[i][k] + map[k][j]
								|| (nmp[i][k] < nmp[i][j] && map[i][j] == map[i][k] + map[k][j])) {
							map[i][j] = map[i][k] + map[k][j];
							nmp[i][j] = nmp[i][k];
						}
			for (int i = 0; i < n; i++)
				of[i] = new LL();

			int l = sc.nextInt();
			Queue<Event> que = new PriorityQueue<Event>();
			for (int i = 0; i < l; i++) {
				int s = sc.nextInt() - 1;
				int t = sc.nextInt() - 1;
				int time = sc.nextInt();
				String name = sc.next();
				que.offer(new LetterArrived(time, new Letter(s, t, time, name)));
			}

			while (!que.isEmpty())
				que.addAll(que.poll().go());

			sort(lp, new Comparator<Letter>() {
				@Override
				public int compare(Letter o1, Letter o2) {
					return o1.time != o2.time ? o1.time - o2.time : o1.name.compareTo(o2.name);
				}
			});
			for (Letter p : lp)
				System.out.println(p.name + " " + p.time);
		}
	}

	int[][] map, nmp;
	boolean[] unwaited;
	List<Letter> lp;
	LL[] of;

	class LL extends ArrayList<Letter> {
	};

	abstract class Event implements Comparable<Event> {
		final int time, type;

		@Override
		public int compareTo(Event o) {
			return time != o.time ? time - o.time : type - o.type;
		}

		public Event(int time, int type) {
			this.time = time;
			this.type = type;
		}

		abstract List<Event> go();
	}

	class PostmanReturned extends Event {
		final int office;

		public PostmanReturned(int time, int office) {
			super(time, 1);
			this.office = office;
		}

		@Override
		List<Event> go() {
			List<Event> ne = new ArrayList<Event>();
			int c = office;
			if (!of[c].isEmpty()) {
				sort(of[c]);
				Letter p = of[c].get(0);
				for (int i = 0; i < of[c].size(); i++) {
					Letter let = of[c].get(i);
					if (let.nxt == p.nxt) {
						of[c].remove(i);
						Letter nl = let.go(time, c);
						ne.add(new LetterArrived(nl.time, nl));
						i--;
					}
				}
				ne.add(new PostmanReturned(time + 2 * map[c][p.nxt], c));
			} else
				unwaited[c] = false;
			return ne;
		}
	}

	class LetterArrived extends Event {
		final Letter letter;

		public LetterArrived(int time, Letter letter) {
			super(time, 0);
			this.letter = letter;
		}

		@Override
		List<Event> go() {
			List<Event> ne = new ArrayList<Event>();

			if (letter.dst == letter.nxt) {
				lp.add(letter);
			} else {
				of[letter.nxt].add(letter.arrive());
				if (!unwaited[letter.nxt])
					ne.add(new PostmanReturned(letter.time, letter.nxt));
				unwaited[letter.nxt] = true;
			}
			return ne;
		}
	}

	class Letter implements Comparable<Letter> {
		final int nxt, dst, time;
		final String name;

		public Letter(int nxt, int dst, int time, String name) {
			this.nxt = nxt;
			this.dst = dst;
			this.time = time;
			this.name = name;
		}

		Letter arrive() {
			return new Letter(nmp[nxt][dst], dst, time, name);
		}
		Letter go(int time, int frm) {
			return new Letter(nxt, dst, time + map[frm][nxt], name);
		}

		@Override
		public int compareTo(Letter o) {
			return time != o.time ? time - o.time : nxt - o.nxt;
		}
	}
}

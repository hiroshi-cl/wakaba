package com.github.hiroshi_cl.wakaba.v2010.sol.sgu;

import java.util.*;
import java.math.*;
import static java.lang.Math.*;
import static java.util.ArrayList.*;
import static java.util.Collections.*;
import static java.math.BigInteger.*;

public class D20100613G {
	public static void main(String[] args) {
		new D20100613G().run();
	}

	void debug(Object... os) {
		System.err.println(os);
	}

	void run() {
		Scanner sc = new Scanner(System.in);
		char[] S = sc.next().toCharArray();
		int n = S.length;
		StringBuilder sb = new StringBuilder();
		Deque<State> que = new ArrayDeque<State>();
		for (int i = 0; i < n; i++) {
			if (S[i] == '<') {
				int j = i + 1;
				while (S[j] != '>')
					j++;
				if (S[i + 1] == 'U')
					que.push(State.UP);
				else if (S[i + 1] == 'D')
					que.push(State.DOWN);
				else
					que.pop();
				i = j;
			} else if (que.isEmpty()) {
				sb.append(S[i]);
			} else
				switch (que.peek()) {
				case UP:
					sb.append(Character.toUpperCase(S[i]));
					break;
				case DOWN:
					sb.append(Character.toLowerCase(S[i]));
					break;
				}
		}
		System.out.println(sb);
	}

	enum State {
		UP, DOWN;
	}
}

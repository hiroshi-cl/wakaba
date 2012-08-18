package com.github.hiroshi_cl.wakaba.v2010.sol.srm;

public class PeopleYouMayKnowB {
	public int maximalScore(String[] friends, int person1, int person2) {
		int N = friends.length;
		boolean[][] m = new boolean[N][N];
		for (int i = 0; i < N; i++)
			for (int j = 0; j < N; j++)
				m[i][j] = friends[i].charAt(j) == 'Y';

		boolean[][] n = new boolean[N][N];
		for (int i = 0; i < N; i++)
			n[person1][i] |= m[person1][i] && !m[i][person2];
		for (int i = 0; i < N; i++)
			n[i][person2] |= !m[person1][i] && m[i][person2];
		for (int i = 0; i < N; i++)
			for (int j = 0; j < N; j++)
				n[i][j] |= n[person1][i] && m[i][j] && n[j][person2];

		int ret = 0;
		for (int i = 0; i < N; i++)
			if (m[person1][i] && m[i][person2])
				ret++;
		while (match(n, person1, person2, new boolean[N], N))
			ret++;

		return ret;
	}

	boolean match(boolean[][] n, int i, int person2, boolean[] visited, int N) {
		if (i == person2)
			return true;

		visited[i] = true;

		for (int j = 0; j < N; j++)
			if (n[i][j] && !visited[j]) {
				n[i][j] = false;
				n[j][i] = true;
				if (match(n, j, person2, visited, N))
					return true;
				n[i][j] = true;
				n[j][i] = false;
			}

		return false;
	}
}
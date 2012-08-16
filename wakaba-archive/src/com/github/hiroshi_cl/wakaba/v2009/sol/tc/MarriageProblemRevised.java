package com.github.hiroshi_cl.wakaba.v2009.sol.tc;
import static java.lang.Math.*;
public class MarriageProblemRevised {
	public int neededMarriages(String[] preferences) {
		n = preferences.length;
		m = preferences[0].length();
		girfri = new int[n];
		boyfri = new int[m];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				if (preferences[i].charAt(j) == '1') {
					girfri[i] |= (1 << j);
					boyfri[j] |= (1 << i);
				}
			}
		}
		for (int i : boyfri)
			if (i == 0) return -1;
		for (int i : girfri)
			if (i == 0) return -1;
		int res = min(n, m);
		for (int i = 0; i < (1 << n + m); i++) {
			if (Integer.bitCount(i) >= res) continue;
			if (can(i)) res = min(res, Integer.bitCount(i));
		}
		return res;
	}
	int[] girfri;
	int[] boyfri;
	int n, m;
	boolean can(int id) {
		int boy = 0;
		int girl = 0;
		for (int i = 0; i < n; i++) {
			if ((id & (1 << i)) > 0) {
				boy |= (1 << i);
				girl |= girfri[i];
			}
		}
		for (int j = 0; j < m; j++) {
			if ((id & (1 << (n + j))) > 0) {
				girl |= (1 << j);
				boy |= boyfri[j];
			}
		}
		return Integer.bitCount(boy) == n && Integer.bitCount(girl) == m;
	}
}

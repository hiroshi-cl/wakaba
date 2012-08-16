package com.github.hiroshi_cl.wakaba.v2009.sol.pku1196.wa;
import java.util.*;

public class Main {
	static Map<Integer,Integer> memo = new HashMap<Integer,Integer>();
	static final int S = 5;
	static final int[][] dum = new int[S][S];
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		while(sc.hasNext()) {
			switch(sc.next().charAt(0)) {
			case 'W' :
				int[][] sqw = new int[S][S];
				char[] cs = sc.next().toCharArray();
				for(int i = 0; i < S; i++)
					for(int j = 0; j < S; j++)
						sqw[i][j] = cs[i*S+j] - 'A';
				System.out.println(taskw(0, sqw, new boolean[S][S], false));
				break;
			case 'N' :
				int n = sc.nextInt();
				int[][] sqn = taskn(0, n, new boolean[S][S]);
				StringBuilder sb = new StringBuilder();
				for(int i = 0; i < S; i++) {
					for(int j = 0; j < S; j++)
						sb.append((char)(sqn[i][j] + 'A'));
//					sb.append("\n"); // for debug
				}
				System.out.println(sb);
				break;
			}
		}
	}
	private static int taskw(int c, int[][] square, boolean[][] used, boolean f) {
		if(c == S * S)
			return 1;
		int code = encode(used);
		if(f && memo.containsKey(code))
			return memo.get(code);
		
		int p = -1;
		int[] avi = new int[S];
		int[] avj = new int[S];
		loop : 
		for(int i = 0; i < S; i++)
			for(int j = 0; j < S; j++)
				if(!used[i][j] && (i == 0 || used[i-1][j]) && (j == 0 || used[i][j-1])) {
					avi[++p] = i;
					avj[p]   = j;
					if(!f && square[i][j] == c)
						break loop;
				}
		
		int ret = 0;
		for(int k = 0; k <= p; k++) {
			used[avi[k]][avj[k]] = true;
			ret += taskw(c+1, square, used, f || k < p);
			used[avi[k]][avj[k]] = false;
		}
		
		if(f)
			memo.put(code, ret);
		return ret;
	}
	private static int[][] taskn(int c, int n, boolean[][] used) {
		if(c == S * S)
			return new int[S][S];
		
		int p = -1;
		int[] avi = new int[S];
		int[] avj = new int[S];
		for(int i = 0; i < S; i++)
			for(int j = 0; j < S; j++)
				if(!used[i][j] && (i == 0 || used[i-1][j]) && (j == 0 || used[i][j-1])) {
					avi[++p] = i;
					avj[p]   = j;
				}
		
		int k = 0;
		for(int m = n; k <= p; k++, n = m) {
			used[avi[k]][avj[k]] = true;
			m -= taskw(c+1, dum, used, true);
			if(m <= 0)
				break;
			used[avi[k]][avj[k]] = false;
		}
		int[][] ret = taskn(c+1, n, used);
		ret[avi[k]][avj[k]] = c;
		return ret;
	}
	private static int encode(boolean[][] bs) {
		int ret = 0;
		for(int i = 0; i < S; i++)
			for(int j = 0; j < S; j++)
				ret = (ret << 1) | (bs[i][j] ? 1 : 0);
		return ret;
	}
}

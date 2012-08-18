package com.github.hiroshi_cl.wakaba.v2010.sol.rp2008;
import java.math.*;
import java.util.*;
import static java.lang.Math.*;
import static java.math.BigInteger.*;
import static java.util.Arrays.*;
import static java.util.Collections.*;

public class E2 {
	void debug(Object... os) {
		System.err.println(deepToString(os));
	}
	void run() {
		Scanner sc = new Scanner(System.in);
		int len = 0;
		String[] ss=sc.next().split("R");
		for(String s:ss){
			if(s.length()>0){
				len += 64/Integer.valueOf(s);
			}
		}
		String[] rs=new String[128];
		for (int i = 0; i < 7; i++) {
			int nl = 64/(1<<i);
			String now = "R"+(1<<i);
			int add = nl;
			for (int j = 0; j < 8-i; j++) {
				if(rs[nl]==null || rs[nl].compareTo(now)>0){
					rs[nl] = now;
				}
				add/=2;
				nl += add;
				now +='.';
			}
		}
//		debug(rs);
		int[] dp=new int[len+1];
		int INF=1<<28;
		fill(dp,INF);
		dp[0]=0;
		int[] bef = new int[len+1];
		for (int i = 0; i < len; i++) {
			for (int j = 0; j < 128; j++)if(rs[j]!=null && i+j<=len) {
				int nl = dp[i] + rs[j].length();
				if(nl<dp[i+j]){
					dp[i+j] = nl;
					bef[i+j] = i;
				}
			}
		}
		ArrayList<String> list=new ArrayList<String>();
		int id = len;
		while(id>0){
			list.add(rs[id- bef[id]]);
			id = bef[id];
		}
		sort(list);
		String res="";
		for (int i = 0; i < list.size(); i++) {
			res += list.get(i);
		}
		System.out.println(res);
	}
	public static void main(String[] args) {
		new E2().run();
	}
}

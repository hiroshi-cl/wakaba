package com.github.hiroshi_cl.wakaba.v2009.sol.tc;
import java.util.*;
import java.math.*;
import static java.math.BigInteger.*;
import static java.util.Arrays.*;
import static java.util.Collections.*;
import static java.lang.Math.*;
public class Hotel {
    public int marketCost(int minCustomers, int[] customers, int[] cost) {
        int n=minCustomers+1000;
    	int[] dp=new int[minCustomers+1000];
        int len=customers.length;
        fill(dp, INF);
        dp[0] = 0;
		for (int i = 1; i < n; i++) {
			for (int j = 0; j < len; j++) {
				if (i - customers[j] >= 0) {
					dp[i] = min(dp[i], dp[i - customers[j]] + cost[j]);
				}
			}
		}
		int res = Integer.MAX_VALUE;
		for (int i = minCustomers; i < n; i++) {
			res = min(res, dp[i]);
		}
		return res;
    }
    void debug(Object... o){
        System.err.println(deepToString(o));
    }
    int INF = 1 << 29;
    double EPS=1e-9;
    int signum(double d){
        return d<-EPS?-1:d>EPS?1:0;
    }

}


// Powered by FileEdit
// Powered by CodeProcessor

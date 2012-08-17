package com.github.hiroshi_cl.wakaba.v2010.lib.numeric.linear;

import java.util.*;
import static java.lang.Math.*;

// Max-Plus Algebra
public class MaxMatrix {
	static final int INF = 1 << 20;
	static final int THR = INF >> 1;
    final int[][] a;
    final int n;
    public MaxMatrix(int n_) {
        n = n_;
        a = new int[n][n];
    }
    public MaxMatrix mul(MaxMatrix m) {
        MaxMatrix r = new MaxMatrix(a.length);
        for(int i = 0; i < n; i++)
            for(int j = 0; j < n; j++) {
                r.a[i][j] = -INF;
                for(int k = 0; k < n; k++)
                    r.a[i][j] = max(r.a[i][j], a[i][k] + m.a[k][j]);
                if(r.a[i][j] < -THR)
                    r.a[i][j] = -INF;
            }
        return r;
    }
    
    public MaxMatrix add(MaxMatrix m) {
        MaxMatrix r = new MaxMatrix(a.length);
        for(int i = 0; i < n; i++)
            for(int j = 0; j < n; j++)
                r.a[i][j] = max(a[i][j], m.a[i][j]);
        return r;
    }
    
    public MaxMatrix pow(int m) {
        if(m == 0)
            return E(m);
        if(m == 1)
            return this;
        MaxMatrix h = pow(m / 2); h = h.mul(h);
        return m % 2 == 0 ? h : h.mul(this);
    }
    
    public static MaxMatrix E(int n) {
        MaxMatrix m = new MaxMatrix(n);
        for(int i = 0; i < n; i++)
            for(int j = 0; j < n; j++)
                m.a[i][j] = i == j ? 0 : -INF;
        return m;
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for(int[] is : a)
            sb.append(Arrays.toString(is)).append('\n');
        return sb.toString();
    }
}

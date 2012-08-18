package com.github.hiroshi_cl.wakaba.v2010.sol.rp2008;

import static java.util.Arrays.*;

import java.io.*;
import java.util.*;

public class B {
    public static void main(String[] args) throws Exception{
        System.setIn(new BufferedInputStream(new FileInputStream("B.txt")));
        new B().run();
    }
    static void debug(Object... o){
        System.err.println(deepToString(o));
    }
    void run() {
        Scanner sc = new Scanner(System.in);
        while(true) {
            int x = sc.nextInt();
            int y = sc.nextInt();
            if((x | y) == 0)
                break;
            int d = gcd(x, y);
            x /= d;
            y /= d;
//            debug(x, y);
            if(y == 1) {
                System.out.println("0 0");
                continue;
            }
            int r = y;
            int b = 1;
            while(r % 2 == 0) {
                r /= 2;
                b *= 2;
            }
            while(r % 5 == 0) {
                r /= 5;
                b *= 5;
            }
//            debug(r,b);
            int cnt1 = 0;
            if(r > 1) {
                int rem = 10;
                for(; cnt1 < r; cnt1++, rem = (rem % r) * 10)
                    if(rem % r == 1)
                        break;
                cnt1++;
            }
            int cnt2 = 0;
            if(b > 1) {
                int rem = x * 10;
                for(; cnt2 < b; cnt2++, rem = (rem % b) * 10) {
//                    debug(rem, b);
                    if(rem % b == 0)
                        break;
                }
                cnt2++;
            }
//            debug(x, y, cnt2, cnt1, (double) x / y);
            System.out.println(cnt2 + " " + cnt1);
        }
    }
    int gcd(int x, int y){
        if(x < y) {
            int t = x;
            x = y;
            y = t;
        }
        if(x % y == 0)
            return y;
        else
            return gcd(y, x % y);
    }
}


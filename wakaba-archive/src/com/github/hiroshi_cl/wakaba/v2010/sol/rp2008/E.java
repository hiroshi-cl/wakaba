package com.github.hiroshi_cl.wakaba.v2010.sol.rp2008;

import static java.util.Arrays.*;
import static java.lang.Math.*;

import java.io.*;
import java.util.*;

public class E {
    public static void main(String[] args) throws Exception{
        System.setIn(new BufferedInputStream(new FileInputStream("E.txt")));
        new E().run();
    }
    static void debug(Object... o){
        System.err.println(deepToString(o));
    }
    int max=384*2;
    void run() throws Exception{
        System.setOut(new PrintStream(new FileOutputStream("tmp.out")));
        BufferedReader br=new BufferedReader(new InputStreamReader(System.in),1<<13);
        int N = Integer.parseInt(br.readLine());
        int[] len=new int[]{
                64,  32, 16,  8, 4, 2, 1, 
                96,  48, 24, 12, 6, 3, 
                112, 56, 28, 14, 7,
                120, 60, 30, 15, 
                124, 62, 31, 
                126, 63,
                127
        };
        String[] onpu=new String[]{
            "R1","R2" , "R4" , "R8", "R16" , "R32" , "R64" ,
            "R1.","R2.", "R4.","R8.","R16.","R32.",
            "R1..", "R2..", "R4..", "R8..", "R16..",
            "R1...", "R2...", "R4...", "R8...",
            "R1....", "R2....", "R4....",
            "R1.....", "R2.....",
            "R1......"
        };
        String[] dp=new String[max];
        dp[0]="R1.A";
//        debug(dp[0]);
        for(int i=1;i<max;i++){
            String ns=null;
            for(int j=0;j<onpu.length;j++){
                int ni=i-len[j];
                if(ni>=0){
                    String s1=dp[ni]+onpu[j];
                    if(ns==null || isleast(s1,ns)){
                        ns=s1;
                    }
                }
            }
            for(int j=0;j<onpu.length;j++){
                int ni=i-len[j];
                if(ni>=0){
                    String s1=onpu[j]+dp[ni];
                    if(isleast(s1,ns)){
                        ns=s1;
                    }
                }
            }
            dp[i]=ns;
        }

//        debug(dp);
        
        for(int n = 0; n < N; n++) {
            String[] rests = br.readLine().split("R");
            int[] dots = new int[rests.length-1];
            for(int i = 1; i < rests.length; i++)
                for(int j = rests[i].length() - 1; j >= 0 && rests[i].charAt(j) == '.'; j--)
                    dots[i-1]++;
//            debug(rests, dots);
            int[] nums = new int[rests.length-1];
            for(int i = 1; i < rests.length; i++)
                nums[i-1] = Integer.parseInt((rests[i].split("\\."))[0]);
            int sum = 0;
            for(int i = 0; i < nums.length; i++) {
                sum += 64 / nums[i];
                sum += (64 / nums[i]) - ((64 / nums[i]) >> dots[i]);
            }
//            debug(sum);
            int mod=sum;

            StringBuffer f=new StringBuffer("");
            for(;mod>=max;mod-=192){
                f.append("R1.R1.");
            }
//            debug(rests, sum, mod, max);
            String res=dp[mod].replace("R1.A", f.toString());
            System.out.println(res);
        }
    }
    boolean isleast(String s1,String s2){
        if(s1.length() < s2.length())return true;
        if(s1.length() > s2.length())return false;
        return s1.compareTo(s2)<0;
    }
}


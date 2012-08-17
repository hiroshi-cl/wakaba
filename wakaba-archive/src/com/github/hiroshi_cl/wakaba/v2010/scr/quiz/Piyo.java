package com.github.hiroshi_cl.wakaba.v2010.scr.quiz;

// 次のコードはどのような出力をするでしょうか？
public class Piyo {
	public static void main(String[] args) {
		int i = 0;
		do {
			if (i < 10) {
				System.out.print(i + " ");
				i++;
				continue;
			}
		} while (false);
		System.out.println(".");
	}
}
/*
 * 正解 
 * > 0 .
 *
 * 理由
 *
 * C, javaのcontinue文は，対象とするループの終了地点直前("}"のすぐ内側) に飛ぶと考えてよい．
 * java2の仕様書では曖昧だったが，java6の仕様書では明記されている．
 * よってこの場合ループは即終了する．
 */
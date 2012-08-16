package com.github.hiroshi_cl.wakaba.v2009.exp.stack;

public class Test {
	public static void main(String[] args) {
		rec();
	}

	static int i = 0;

	static void rec() {
		int j = 0;
		int k = 0;
		System.out.println(i++);
		rec();
		j++;
		k++;
	}
}
/*
 * -StackOverFlow直前のiの値
 *  -- ローカル変数0個:6538
 *  -- ローカル変数1個:6410
 *  -- ローカル変数2個:6282
 * 昔やった時はもうちょっと行った気がするのに。
 * 
 * -StackOverFlow直前のiの値(-Xss512M -Xms512M -Xmx512M)
 *  -- ローカル変数0個:137324
 *  -- ローカル変数1個:134518
 *  -- ローカル変数2個:131923
 * ちなみにECCSのEclipse上でJava1.6.0_13(標準インストール)を使用
 */
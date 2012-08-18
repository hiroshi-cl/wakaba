package com.github.hiroshi_cl.wakaba.v2010.sol.javachallenge2010;

import jp.ac.waseda.cs.washi.game.api.*;
import java.util.*;

public class ID075 implements Player {
	Direction[] directions;
	PlayerAction[] actions;
	int indexOfDirections = 0;
	int indexOfActions = 0;
	Direction[] fourDirections = Direction.values();
	GameEnvironmentInfo env;

	PlayerAction[] bonusMakeActions = { PlayerAction.LEFT, PlayerAction.LEFT, PlayerAction.CLEAR, PlayerAction.UP,
			PlayerAction.RIGHT, PlayerAction.CLEAR, PlayerAction.UP, PlayerAction.RIGHT, PlayerAction.CLEAR,
			PlayerAction.RIGHT, PlayerAction.DOWN, PlayerAction.CLEAR, PlayerAction.RIGHT, PlayerAction.DOWN,
			PlayerAction.CLEAR, PlayerAction.DOWN, PlayerAction.LEFT, PlayerAction.CLEAR, PlayerAction.DOWN,
			PlayerAction.LEFT, PlayerAction.CLEAR, PlayerAction.LEFT, PlayerAction.UP, PlayerAction.CLEAR, };
	long LIMIT = 100;

	@Override
	public PlayerAction execute(GameEnvironmentInfo env) {
		this.env = env;
		isWritable = new boolean[env.getMapInfo().getHeight()][env.getMapInfo().getWidth()];

		List<PumpkinInfo> plist = env.getPlayerInfo().getPumpkinInfos();
		for (PumpkinInfo pinfo : plist) {
			Point pp = pinfo.getCenterLocation();
			for (int d = 0; d < 8; d++) {
				for (int i = 0; i < dx2.length; i++) {
					int nx = pp.x + dx2[i], ny = pp.y + dy2[i];
					if (0 <= ny && ny < isWritable.length && 0 <= nx && nx < isWritable[0].length)
						isWritable[ny][nx] = true;
				}
			}
		}

		return AI_beta();
	}

	boolean[][] isWritable;

	ArrayList<Point> getPPos() {
		MapInfo mi = env.getMapInfo();
		ArrayList<Point> r = new ArrayList<Point>();
		for (int i = 1; i < mi.getWidth() - 1; i++) {
			for (int j = 1; j < mi.getHeight() - 1; j++) {
				if (isWritable(new Point(i, j)) && isWritable(new Point(i, j - 1)) && isWritable(new Point(i, j + 1))
						&& isWritable(new Point(i - 1, j)) && isWritable(new Point(i + 1, j))) {
					r.add(new Point(i, j));
				}
			}
		}
		return r;
	}

	boolean isWritable(Point p) {
//		if(isWritable[p.y][p.x])
//			return false;
		MapInfo mi = env.getMapInfo();
		return mi.isMovable(p) && !mi.isLocked(p);
	}

	final int INF = 1 << 28;
	boolean pumpkinX = true;

	PlayerAction AI_beta() {
		MapInfo mi = env.getMapInfo();
		Point p = env.getPlayerInfo().getLocation();
		CharaInfo pi = env.getPlayerInfo();
		//		if (mi.getNeedingSpellCount(env.getPlayerInfo(), p) == 0) {
		SpellPos minSpellPos = null;

		if (writeOk()) {
			deadlock_flag = 0;
			oldSpellPos = null;
		}

		if (pumpkinX) {
			List<PumpkinInfo> plist = env.getEnemyInfo().getPumpkinInfos();
			for (PumpkinInfo pinfo : plist) {
				Point pp = pinfo.getCenterLocation();
				int point;
				if (pinfo.getType() == PumpkinType.KingPumpkin)
					point = 50;
				else
					point = 20;
				SpellPos nsp = new SpellPos(pp, -point);
				minSpellPos = min(minSpellPos, nsp);
			}

			//			List<Point> plist2 = PumpkinType.MiniPumpkin.getMineLocations();
			//			debug(plist2.toArray());
			//			for (Point pp : plist2) {
			//				SpellPos nsp = new SpellPos(pp, -20);
			//				//				if (minSpellPos == null)
			//				//					minSpellPos = nsp;
			//				//				if (minSpellPos.compareTo(nsp) > 0)
			//				//					minSpellPos = nsp;
			//				//				if (minSpellPos.compareTo(nsp) == 0 && Math.random() < 0.5)
			//				//					minSpellPos = nsp;
			//				minSpellPos = min(minSpellPos, nsp);
			//			}
		}

		for (int i = 0; i < mi.getWidth(); i++) {
			for (int j = 0; j < mi.getHeight(); j++) {
				Point pp = new Point(i, j);
				if (mi.getNeedingSpellCount(pi, pp) > 0) {
					SpellPos nsp = new SpellPos(pp, ((i + j + 1) % 2) * 400);
					minSpellPos = min(minSpellPos, nsp);
				}

				if (pampkinMakable(pp)) {
					SpellPos nsp = new SpellPos(pp, -20);
					minSpellPos = min(minSpellPos, nsp);
				}
			}
		}
		if (minSpellPos == null)
			return PlayerAction.NONE;
		if (minSpellPos.ds.length == 0) {
			if(minSpellPos.p.equals(oldSpellPos)) {
			if (deadlock_flag >= 2) {
				if (env.getPlayerInfo().getScore() <= env.getEnemyInfo().getScore()) {
					return Math.random() < 0.5 ? PlayerAction.UP : PlayerAction.DOWN;
				} else {
					return PlayerAction.SPELL;
				}
			}
			deadlock_flag++;
			oldSpellPos = minSpellPos;
			oldNeed = env.getMapInfo().getNeedingSpellCount(env.getPlayerInfo(), minSpellPos.p);
			}
			else {
				deadlock_flag = 0;
				oldSpellPos = null;
			}
			return PlayerAction.SPELL;
		}
		return minSpellPos.ds[0].toPlayerAction();
		//		} else {
		//			return PlayerAction.SPELL;
		//		}
	}

	// for deadlock;
	int deadlock_flag = 0;
	SpellPos oldSpellPos;
	int oldNeed;

	boolean writeOk() {
		if (oldSpellPos == null)
			return true;
		if (oldSpellPos.p.equals(env.getPlayerInfo().getLocation())) {
			return env.getMapInfo().getNeedingSpellCount(env.getPlayerInfo(), oldSpellPos.p) != oldNeed;
		}
		return false;
	}

	int[] dx = { 1, 0, -1, 0 };
	int[] dy = { 0, 1, 0, -1 };
	int[] dx2 = { -2, -1, -1, 0, 0, 1, 1, 2 };
	int[] dy2 = { 0, 1, -1, 2, -2, 1, -1, 0 };

	boolean pampkinMakable(Point p) {
		//		if(env.getEnemyInfo().equals(env.getMapInfo().getTileOwner(p)))
		//			return false;
		if (myMahojin(p))
			return false;
		//		System.out.println(PumpkinType.MiniPumpkin.getElement(p) + " " + p);
		//		int a = PumpkinType.MiniPumpkin.getMineLocations().size();
		//		int b = PumpkinType.MiniPumpkin.getNotMineLocations().size();
		//	debug(p);
		for (int d = 0; d < 4; d++) {
			if (!myMahojin(new Point(p.x + dx[d], p.y + dy[d])))
				return false;
		}
		//		debug(p);
		for (int d = 0; d < 8; d++) {
			if (!notMyMahojin(new Point(p.x + dx2[d], p.y + dy2[d])))
				return false;
		}
		return true;
		//		return a == 1 && b == 0;
	}

	//	boolean noyMyMahojin
	boolean notMyMahojin(Point p) {
		return env.getMapInfo().getCompleteLevel(p) == 0 || !myMahojin(p);
	}

	boolean myMahojin(Point p) {
		return env.getMapInfo().getNeedingSpellCount(env.getPlayerInfo(), p) == 0
				&& env.getPlayerInfo().equals(env.getMapInfo().getTileOwner(p));
	}

	//	boolean sarati(Point p) {
	//
	//	}

	SpellPos min(SpellPos a, SpellPos b) {
		if (a == null)
			return b;
		if (b == null)
			return a;
		int i = a.compareTo(b);
		if (i < 0)
			return a;
		if (i > 0)
			return b;
		return a;
		//		return Math.random() < 0.5 ? a : b;
	}

	class SpellPos implements Comparable<SpellPos> {
		Point p;
		Direction[] ds;
		int spellCount;
		int dist;

		SpellPos(Point p, int x) {
			this.p = p;
			ds = env.getMapInfo().getPath(env.getPlayerInfo().getLocation(), p);
			spellCount = env.getMapInfo().getNeedingSpellCount(env.getPlayerInfo(), p);
			dist = ds.length + spellCount + x;
		}

		SpellPos(Point p) {
			this(p, 0);
		}

		public int compareTo(SpellPos o) {
			return dist - o.dist;
		}
	}

	//	void debug(Object... os) {
	//		System.err.println(Arrays.deepToString(os));
	//	}
}
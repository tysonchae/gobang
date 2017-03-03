package game;

public class Judge {
	// return true when five stones are in a row
		public boolean isGobang(int turn, int x, int y) {
			// count stones 
			// if bigger than 5(last stone will be counted twice), return true
			if (checkStones(turn, x, y, 1, 0) + checkStones(turn, x, y, -1, 0) > 5)
				return true;
			// horizontal
			if (checkStones(turn, x, y, 0, 1) + checkStones(turn, x, y, 0, -1) > 5)
				return true;
			// diagonal
			if (checkStones(turn, x, y, 1, 1) + checkStones(turn, x, y, 1, -1) > 5)
				return true;
			if (checkStones(turn, x, y, -1, -1) + checkStones(turn, x, y, -1, 1) > 5)
				return true;
			// otherwise return false
			return false;
		}

		private int checkStones(int turn, int x, int y, int dX, int dY) {
			// which direction dX and dY will count
			int i = 0;
			for (; Gobang.stones[x][y] == turn; i++) {
				x += dX;
				y += dY;
				// break when it goes over the size of board
				if (x < 0 || x >= Gobang.LINE_NUM || y < 0 || y >= Gobang.LINE_NUM)
					break;
			}
			// count stones in a row (it counts the last stone twice)
			return i;
		}
}

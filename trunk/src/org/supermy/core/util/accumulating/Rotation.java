package org.supermy.core.util.accumulating;
/**
 * 旋转打印,通过方向改变控制数组的行标和列标
 * 
 * @author 莫勇
 * 
 */
class Rotation {
	/**
	 * 长度是7
	 */
	static int length = 7;
	/**
	 * 每次增长为1
	 */
	static int value = 1;
	static int[][] snake = new int[length][length];
	static String[][] snakeS = new String[length][length];

	public static Enum myColor;

	/**
	 * 方向
	 * 
	 * @author 莫勇
	 * 
	 */
	static enum Direction {
		Right, Down, Left, Up;
	}

	static Direction lastDirection = Direction.Right;

	public static String repeat(String string, int count) {
		String result = "";
		for (int i = 0; i < count; i++) {
			result = result + string;
		}
		return result;
	}

	public static void initialArray() {
		Integer total = (length * length);
		int totallength = total.toString().length();

		int row = 0, line = 0;
		for (int c = 0; c < length * length; c++) {
			snake[row][line] = value;
			String string = Integer.toString(value);
			int zerolength = totallength - string.length();
			snakeS[row][line] = repeat("0", zerolength) + string;
			lastDirection = findDirection(row, line);
			switch (lastDirection) {
			case Right:
				line++;
				break;
			case Down:
				row++;
				break;
			case Left:
				line--;
				break;
			case Up:
				row--;
				break;
			default:
				System.out.println("error");
			}
			value++;
		}
	}

	/**
	 * 方向判定
	 * 
	 * @param row
	 * @param line
	 * @return
	 */
	static Direction findDirection(int row, int line) {
		Direction direction = lastDirection;
		switch (direction) {
		case Right: {
			if ((line == length - 1) || (snake[row][line + 1] != 0))
				direction = direction.Down;
			break;
		}
		case Down: {
			if ((row == length - 1) || (snake[row + 1][line] != 0))
				direction = direction.Left;
			break;
		}
		case Left: {
			if ((line == 0) || (snake[row][line - 1] != 0))
				direction = direction.Up;
			break;
		}
		case Up: {
			if (snake[row - 1][line] != 0)
				direction = direction.Right;
			break;
		}
		}
		return direction;
	}

	public static void main(String[] args) {
		initialArray();

		// display.....
		for (int i = 0; i < length; i++) {
			for (int j = 0; j < length; j++) {
				System.out.print(snakeS[i][j] + "  ");
			}
			System.out.println();
		}
	}
}
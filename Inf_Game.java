package game_of_life;

import java.util.Collection;
import java.util.HashMap;

class Cell {
	int x, y;
	String key;

	Cell(int x, int y){
		this.x = x;
		this.y = y;
		key = "c_" + x + "_" + y;
	}
}


class cellSet {
	HashMap<String, Cell> cellMap = new HashMap<String, Cell>();

	void addCell(int x, int y) {
		Cell newCell = new Cell(x, y);
		cellMap.put(newCell.key, newCell);
	}

	Collection<Cell> getCells() {
		return cellMap.values();
	}

	boolean hasCell(int x, int y) {
		return cellMap.containsKey("c_" + x + "_" + y);
	}

	int numNeighborsOf(Cell cell) {
		int count = 0;
		String neighborKey;
		for (int i = cell.x - 1; i <= cell.x + 1; i++) {
			for (int j = cell.y - 1; j <= cell.y + 1; j++) {
				neighborKey = "c_" + i + "_" + j;
				if (cellMap.containsKey(neighborKey) && !neighborKey.equals(cell.key)) {
					count++;
				}
			}
		}
		return count;
	}

	int maxX() {
		int max = Integer.MIN_VALUE;
		for (Cell cell : cellMap.values()) {
		    if (cell.x > max)
		    	max = cell.x;
		}
		return max;
	}

	int maxY() {
		int max = Integer.MIN_VALUE;
		for (Cell cell : cellMap.values()) {
		    if (cell.y > max)
		    	max = cell.y;
		}
		return max;
	}

	int minX() {
		int min = Integer.MAX_VALUE;
		for (Cell cell : cellMap.values()) {
		    if (cell.x < min)
		    	min = cell.x;
		}
		return min;
	}

	int minY() {
		int min = Integer.MAX_VALUE;
		for (Cell cell : cellMap.values()) {
		    if (cell.y < min)
		    	min = cell.y;
		}
		return min;
	}
}

public class Inf_Game implements Cloneable {

    public static void main(String[] args) {
        System.out.println("Welcome to the Inf_Game of Life.");

        // init a new board
        Inf_Game board = new Inf_Game("pentadecathlon", true, 20);

        // tick N times
        int N = 30;
        for (int i = 0; i < N; i++) {
            board.print();
            board.tick();
        }
    }

    cellSet cells;
    boolean lockPrint;
    int frameSize;

	// constructor new for n x n board
	Inf_Game(String code, boolean lockPrint, int frameSize) {

		this.lockPrint = lockPrint;
		this.frameSize = frameSize;

		// init empty set of Cells
		cells = new cellSet();

		if (code.equals("rotator")) {
			cells.addCell(2,1);
			cells.addCell(2,2);
			cells.addCell(2,3);
		} else if (code.equals("glider")) {
			cells.addCell(1,2);
			cells.addCell(2,3);
			cells.addCell(3,1);
			cells.addCell(3,2);
			cells.addCell(3,3);
		} else if (code.equals("pentadecathlon")) {
			cells.addCell(1,1);
			cells.addCell(1,2);
			cells.addCell(1,3);
			cells.addCell(2,1);
			cells.addCell(2,3);
			cells.addCell(3,1);
			cells.addCell(3,2);
			cells.addCell(3,3);
			cells.addCell(4,1);
			cells.addCell(4,2);
			cells.addCell(4,3);
			cells.addCell(5,1);
			cells.addCell(5,2);
			cells.addCell(5,3);
			cells.addCell(6,1);
			cells.addCell(6,2);
			cells.addCell(6,3);
			cells.addCell(7,1);
			cells.addCell(7,3);
			cells.addCell(8,1);
			cells.addCell(8,2);
			cells.addCell(8,3);
		}
	}

	// for a currently alive cell, return whether it is alive in next generation
	boolean checkCell(Cell cell) {

		int neighbors = cells.numNeighborsOf(cell);

		//	1. Any live cell with fewer than two live neighbors dies, as if by under population.
		if (neighbors < 2) {
			return false;
		}
		//	2. Any live cell with two or three live neighbors lives on to the next generation.
		if (neighbors == 2 || neighbors == 3) {
			return true;
		}
		//	3. Any live cell with more than three live neighbors dies, as if by overpopulation.
		if (neighbors > 3) {
			return false;
		}
		return false;
	}

	// iterate to next generation based on rules
	void tick() {

		cellSet nextGen = new cellSet();
		Cell tempCell;

		for (Cell cell : cells.getCells()) {
			if (checkCell(cell))
				nextGen.addCell(cell.x, cell.y);

			for (int i = cell.x - 1; i <= cell.x + 1; i++) {
				for (int j = cell.y - 1; j <= cell.y + 1; j++) {
					tempCell = new Cell(i, j);
					if (cells.numNeighborsOf(tempCell) == 3)
						nextGen.addCell(i, j);
				}
			}
		}

		// swap in new generation
		this.cells = nextGen;
	}

	String to_str() {
		String board_str = "";
		int xLower, xUpper, yLower, yUpper;

		if (lockPrint) {
			xLower = -frameSize;
			xUpper = frameSize;
			yLower = -frameSize;
			yUpper = frameSize;
		} else {
			xLower = cells.minX() - 2;
			xUpper = cells.maxX() + 3;
			yLower = cells.minY() - 2;
			yUpper = cells.maxY() + 3;
		}

		for (int i = xLower; i < xUpper; i++) {
			for (int j = yLower; j < yUpper; j++) {
				if (cells.hasCell(i, j))
					board_str += "X ";
				else
					board_str += "  ";
			}
			board_str += "\n";
		}
		return board_str;
	}

	void print() {
		System.out.println(to_str());
	}
}

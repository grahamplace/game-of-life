package game_of_life;

public class Game implements Cloneable {

    public static void main(String[] args) {
        System.out.println("Welcome to the Game of Life.");

        // init a new board
        Game board = new Game(5, "rotator");

        // tick N times
        int N = 5;
        for (int i = 0; i < N; i++) {
            board.print();
            // board.printNeighbors(); // for debugging
            board.tick();
        }
    }

	int size;
	boolean[][] board;

	// constructor new for n x n board
	Game(int n, String code) {
		// set board size
		size = n;

		// init empty board matrix
		board = new boolean[n][n];
		for(int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				board[i][j] = false;
			}
		}

		// we can build custom initial states here:
		if (code.equals("random")) {
			for(int i = 0; i < n; i++) {
				for (int j = 0; j < n; j++) {
					board[i][j] = Math.random() < 0.5;
				}
			}
		}

		if (code.equals("rotator")) {
			for(int i = 0; i < n; i++) {
				for (int j = 0; j < n; j++) {
					// start with (2,1), (2,2), (2,3)
					if (i == 2) {
						if (j == 1 || j == 2 || j == 3) {
							board[i][j] = true;
						}
					}
				}
			}
		}
	} // end constructor


	// for given location, return count of neighbors of that cell
	int getNeighborCount(int x, int y) {
		int neighbors = 0;
		for (int i = x - 1; i <= x + 1; i++) {
			for (int j = y - 1; j <= y + 1; j++) {
				// ugly, but yay java short circuiting logical expressions
				if (i >= 0 && i < this.size && j >= 0 && j < this.size && board[i][j]) {
					if (!(i == x && j == y))
						neighbors++;
				}
			}
		}
		return neighbors;
	}


	// for given location on this board, return next generation value of that cell
	// Conway's 4 Rules for Life:
	boolean mutateCell(int i, int j) {

		if (i == 2 && j == 2) {
//			System.out.println("we out heeeere");
		}

		int neighbors = getNeighborCount(i, j);
		//	1. Any live cell with fewer than two live neighbors dies, as if by under population.
		if (board[i][j] && neighbors < 2) {
			return false;
		}
		//	2. Any live cell with two or three live neighbors lives on to the next generation.
		if (board[i][j] && (neighbors == 2 || neighbors == 3)) {
			return true;
		}
		//	3. Any live cell with more than three live neighbors dies, as if by overpopulation.
		if (board[i][j] && neighbors > 3) {
			return false;
		}
		//	4. Any dead cell with exactly three live neighbors becomes a live cell, as if by reproduction.
		if (!board[i][j] && neighbors == 3) {
			return true;
		}

		return false;
	}


	// conway called the iteration the "tick"
	// iterate the board based on the rules
	void tick() {
		boolean[][] nextGen = new boolean[this.size][this.size];

		for (int i = 0; i < this.size; i++) {
			for (int j = 0; j < this.size; j++) {
				nextGen[i][j] = mutateCell(i, j);
			}
		}

		// replace board with next generation
		this.board = nextGen;

	}


	String to_str() {
		String board_str = "";
		for (int i = 0; i < this.size; i++) {
			for (int j = 0; j < this.size; j++) {
				if (board[i][j]) {
					board_str += "X ";
				} else {
					board_str += "0 ";
				}
			}
			board_str += "\n";
		}
		return board_str;
	}


	String to_neighborStr() {
		String board_str = "";
		for (int i = 0; i < this.size; i++) {
			for (int j = 0; j < this.size; j++) {
				board_str += "" + getNeighborCount(i, j) +" ";
			}
			board_str += "\n";
		}
		return board_str;
	}


	void print() {
		System.out.println(to_str());
	}


	void printNeighbors() {
		System.out.println(to_neighborStr());
	}
}

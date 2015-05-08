
public class Piece {
	private boolean isFire;
	private Board board;
	private int xLocal;
	private int yLocal;
	private String type;
	private int captures;


	public Piece(boolean isItFire, Board b, int x, int y, String typeOfPiece) {
		isFire = isItFire;
		board = b;
		xLocal = x;
		yLocal = y;
		type = typeOfPiece;
		captures = 0;
	}

	public boolean isFire() {
		return isFire;
	}

	public int side() {
		if (isFire) {
			return 0;
		}
		else {
			return 1;
		}
	}

	public boolean isBomb() {
		if (type == "img/bomb-fire-crowned.png" || 
			type == "img/bomb-fire.png" || 
			type == "img/bomb-water-crowned.png" ||
			type == "img/bomb-water.png") {
			return true;
		}
		else {
			return false;
		}
	}

	public boolean isKing() {
		if (type == "img/bomb-fire-crowned.png" ||
			type == "img/bomb-water-crowned.png" ||
			type == "img/shield-fire-crowned.png" ||
			type == "img/shield-water-crowned.png"||
			type == "img/pawn-fire-crowned.png" ||
			type == "img/pawn-water-crowned.png") {
			return true;
		}
		else {
			return false;
		}
	}


	public void move(int x, int y) {
		int toLeaveX = xLocal;
		int toLeaveY = yLocal;
		this.board.place(this, x, y);
		if (y == 0 || y == 7) {
			System.out.println("flag1");
			makeKing(x, y);
		}
		if ((Math.abs(x - xLocal) == 1)) {
			moveSimple(x, y);
		}
		if ((Math.abs(y - yLocal)) == 2) {
			moveCapture(x, y);
		}
		if (isBomb() && (Math.abs(x - toLeaveX) ==2)) {
			explodeAt(x, y);
		}
		this.board.remove(toLeaveX,toLeaveY);
	}



	private void moveSimple(int x, int y) {
		this.xLocal = x;
		this.yLocal = y;
	}

	private void moveCapture(int x, int y) {
		int locationOfCaptureX = ((x - xLocal) / 2);
		int locationOfCaptureY = ((y - yLocal) / 2);
		captures += 1;

		this.board.remove(xLocal + locationOfCaptureX, yLocal + locationOfCaptureY);
		this.xLocal = x;
		this.yLocal = y;

	}

	public boolean hasCaptured() {
		if (captures > 0) {
			return true;
		}
		else {
			return false;
		}
	}

	private void explodeAt(int x, int y) {
		int[][] piecesExploded = new int[2][8];
		if (safe(x + 1, y + 1)) {
			if (board.pieceAt(x + 1, y + 1) != null) {
				piecesExploded[0][0] = x + 1;
				piecesExploded[1][0] = y + 1;
			}
		}
		if (safe(x + 1, y)) {
			if (board.pieceAt(x + 1, y) != null) {
				piecesExploded[0][1] = x + 1;
				piecesExploded[1][1] = y;
			}
		}
		if (safe(x - 1, y + 1)) {
			if (board.pieceAt(x - 1, y + 1) != null) {
				piecesExploded[0][2] = x - 1;
				piecesExploded[1][2] = y + 1;
			}
		}
		if (safe(x -1, y)) {
			if (board.pieceAt(x -1, y) != null) {
				piecesExploded[0][3] = x - 1;
				piecesExploded[1][3] = y;
			}
		}
		if (safe(x - 1, y - 1)) {
			if (board.pieceAt(x - 1, y - 1) != null) {
				piecesExploded[0][4] = x - 1;
				piecesExploded[1][4] = y - 1;
			}
		}
		if (safe(x, y - 1)) {
			if (board.pieceAt(x, y - 1) != null) {
				piecesExploded[0][5] = x;
				piecesExploded[1][5] = y - 1;
			}
		}
		if (safe(x + 1, y - 1)) {
			if (board.pieceAt(x + 1, y - 1) != null) {
				piecesExploded[0][6] = x + 1;
				piecesExploded[1][6] = y - 1;
			}
		}
		if (safe(x, y + 1)) {
			if (board.pieceAt(x, y + 1) != null) {
				piecesExploded[0][7] = x;
				piecesExploded[1][7] = y + 1;
			}
		}
		for (int i = 0; i < 8; i += 1) {
			if (board.pieceAt(piecesExploded[0][i], piecesExploded[1][i]) != null) {
				if (board.pieceAt(piecesExploded[0][i], piecesExploded[1][i]).isShield() == false) {
					board.remove(piecesExploded[0][i], piecesExploded[1][i]);
				}
			}
		}
		board.remove(x, y);
	}

	private boolean safe(int x, int y) {
		if (x <= 7 && x >= 0 && y <= 7 && y >=0) {
			return true;
		}
		else {
			return false;
		}
	}



	public void doneCapturing() {
		this.captures = 0;
	}

	public boolean isShield() {
		if (type == "img/shield-fire-crowned.png" || 
			type == "img/shield-fire.png" || 
			type == "img/shield-water-crowned.png" ||
			type == "img/shield-water.png") {
			return true;
		}
		else {
			return false;
		}
	}
	private void makeKing(int x, int y) {
		if (isKing()){
			type = type;
		}

		else if ((this.isFire() == true) && (y == 7)) {
			if (this.type == "img/bomb-fire.png") {
				this.type = "img/bomb-fire-crowned.png";
			}
			if (this.type == "img/pawn-fire.png") {
				this.type = "img/pawn-fire-crowned.png";
			}
			if (this.type == "img/shield-fire.png") {
				this.type = "img/shield-fire-crowned.png";
			}
		}
		else if ((this.isFire() == false) && (y == 0)) {
			if (this.type == "img/bomb-water.png") {
				this.type = "img/bomb-water-crowned.png";
			}
			if (this.type == "img/pawn-water.png") {
				this.type = "img/pawn-water-crowned.png";
			}
			if (this.type == "img/shield-water.png") {
				this.type = "img/shield-water-crowned.png";
			}
		}
	}	
}


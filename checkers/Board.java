import org.junit.Test;
import static org.junit.Assert.*;


/**
 *********** Note to Artsy **************
 * This is a game of checkers with a twist. Bomb explode pieces and shields do not explode. 
 * Use mousepad to play and space to switch turns.
 * After using javac *.java to compile run using command line arguements: 
 * java Board
 */

public class Board {
	private Piece[][] playingBoard;
	private String turn;
	private Piece pieceSelected;
	private int pieceSelectedX;
	private int pieceSelectedY;
	private boolean madeMove;
	private boolean recentExplosion;




// ****** NECESSITIES *****

	public Board(boolean shouldBeEmpty) {
		playingBoard = new Piece[8][8];
		turn = "Fire";
		if (shouldBeEmpty == false) {
			this.setUpPlayingBoard();
		}
	}
	public Piece pieceAt(int x, int y) {
		if (x <= 7 && x >= 0 && y <= 7 && y >=0) {
			if (playingBoard[7 - y][x] != null) {
				return playingBoard[7 - y][x];
			}
			else {
				return null;
			}
		}
		else {
			return null;
		}
	}
		private void setUpPlayingBoard() {
		for (int row = 0; row <= 7; row += 1) {
            for (int col = 0; col <= 7; col += 1) {
            	if ((row == 7) && (col % 2 == 0)) {
            		playingBoard[row][col] = new Piece(true, this, col, 7 - row, "img/pawn-fire.png");
            	}
            	if ((row == 6) && (col % 2 == 1)) {
            		playingBoard[row][col] = new Piece(true, this, col, 7 - row, "img/shield-fire.png");
            	}
            	if ((row == 5) && (col % 2 == 0)) {
            		playingBoard[row][col] = new Piece(true, this, col, 7 - row, "img/bomb-fire.png");
            	}
            	if ((row == 2) && (col % 2 == 1)) {
            		playingBoard[row][col] = new Piece(false, this, col, 7 - row, "img/bomb-water.png");
            	}
            	if ((row == 1) && (col % 2 == 0)) {
            		playingBoard[row][col] = new Piece(false, this, col, 7-row, "img/shield-water.png");
            	}
            	if ((row == 0) && (col % 2 == 1)) {
            		playingBoard[row][col] = new Piece(false, this, col, 7-row, "img/pawn-water.png");
            	}
            }
        }
    }





// **** Basic game progression (turns & winner) ****

	public boolean canEndTurn() {
		if (madeMove) {
			return true;
		}
		else {
			return false;
		}
	}
	public void endTurn() {
		madeMove = false;
		pieceSelected.doneCapturing();
		pieceSelected = null;
		pieceSelectedY = 0;
		pieceSelectedY = 0;
		if (this.turn == "Fire") {
			this.turn = "Water";
		}
		else {
			this.turn = "Fire";
		}
	}
	public String winner() {
		int numWaterPieces = 0;
		int numFirePieces = 0;
		for (int row = 0; row <= 7; row += 1) {
            for (int col = 0; col <= 7; col += 1) {
            	if (playingBoard[row][col] != null) {
            		Piece curr = playingBoard[row][col];
            		if (curr.isFire()) {
            			numFirePieces += 1;
            		}
            		if (curr.isFire() == false) {
            			numWaterPieces += 1;	
            		}
            	}
            }
        }
		if ((numFirePieces == 0) && (numWaterPieces == 0)) {
			return "No one";
		}
		if (numFirePieces <= 0) {
			return "Water";
		}
		if (numWaterPieces <= 0) {
			return "Fire";
		}
		else {
			return null;
		}
	}




// ***** This module takes care of main and StdDrawPlus Operations *****

	public static void main(String[] args) {
		Board newB = new Board(false);
		/* draw pieces is going to have to go somewhere else
		 * due to redrawing of peices after turns */
		//newB.drawGui();
		newB.draw();
	}
	private void draw() {
		while(true) {
			this.drawGui();
			if (StdDrawPlus.mousePressed()) {
				int xMouse = ((int) StdDrawPlus.mouseX());
				int yMouse = ((int) StdDrawPlus.mouseY());
				if (this.canSelect(xMouse, yMouse)) {
					this.select(xMouse, yMouse);
				}
			}
			if (StdDrawPlus.isSpacePressed()) {
				if (this.canEndTurn()) {
					this.winner();
					this.endTurn();
				}
			}
			StdDrawPlus.show(50);
		}
	}
	private void drawGui() {
    	drawBoard(7);
    	drawPieces();
    }
    private void drawPieces() {
    	String typeToDraw;
    	Piece curr;
		for (int row = 0; row <= 7; row += 1) {
            for (int col = 0; col <= 7; col += 1) {
            	if (playingBoard[row][col] != null) {
            		curr = playingBoard[row][col];
            		typeToDraw = findtype(curr);
            		StdDrawPlus.picture(col + .5, 7-row + .5, typeToDraw, 1, 1);
            	}
           	}
        }
    }
	
    /* find the type of a Piece p*/
	private String findtype(Piece p) {
		if (p.isFire()) {
			if (p.isKing()) {
				if (p.isBomb()) {
					return "img/bomb-fire-crowned.png";
				}
				if (p.isShield()) {
					return "img/shield-fire-crowned.png";
				}
				else {
					return "img/pawn-fire-crowned.png";
				}
			}
			else {
				if (p.isBomb()) {
					return "img/bomb-fire.png";
				}
				if (p.isShield()) {
					return "img/shield-fire.png";
				}
				else {
					return "img/pawn-fire.png";
				}
			}
		}
		else {
			if (p.isKing()) {
				if (p.isBomb()) {
					return "img/bomb-water-crowned.png";
				}
				if (p.isShield()) {
					return "img/shield-water-crowned.png";
				}
				else {
					return "img/pawn-water-crowned.png";
				}
			}
			else {
				if (p.isBomb()) {
					return "img/bomb-water.png";
				}
				if (p.isShield()) {
					return "img/shield-water.png";
				}
				else {
					return "img/pawn-water.png";
				}
			}
		}
	}
	private void drawBoard(int size) {
		StdDrawPlus.setScale(0, 8);
		for (int x = 0; x <= size; x += 1) {
            for (int y = 0; y <= size; y += 1) {
            	if ((x + y) % 2 == 0) {
            		StdDrawPlus.setPenColor(StdDrawPlus.GRAY);
            	}
            	else {
            		StdDrawPlus.setPenColor(StdDrawPlus.RED);
            	}
            	StdDrawPlus.filledSquare(x + .5, y + .5, .5);
            }
        }
    }




// ***** This Module selects squares and pieces, and then makes sure those are valid selects ****

    /* dispatches to canSelectPiece if there is a Piece as x, y
     * dispatches to can selectSquare if a Piece is already selected */
	public boolean canSelect(int x, int y) {
		if (x <= 7 && x >= 0 && y <= 7 && y >=0) {
			if (pieceSelected != null) {
				if ((pieceSelected.isBomb()) && (pieceSelected.hasCaptured())) {
					return false;
				}
			}
			if (pieceAt(x, y) != null) {
				return canSelectPiece(x, y);
			}
			if (pieceSelected != null) {
				return canSelectSquare(x, y);
			}
			else {
				return false;
			}
		}
		else {
			return false;
		}
	}
	private boolean canSelectSquare(int x, int y) {
		if (madeMove) {
			if (pieceSelected.hasCaptured()) {
				return validCapture(x, y);
			}
			else {
				return false;
			}
		}
		if (pieceSelected.isKing()) {
			return validKingMove(x, y);
		}
		return validMove(x, y);
	}
	private boolean canSelectPiece(int x, int y) {
		if (madeMove) {
			return false;
		}
		Piece localPiece = pieceAt(x, y);
		if (turn == "Fire") {
			if (localPiece.isFire()) {
				return true;
			}
		}
		if (turn == "Water") {
			if (localPiece.isFire() == false) {
				return true;
			}
		}
		return false;
	}
	/* return whether a move to x, y is valid for pieceSelected 
     * if piece is king dispatches to validKing Move
     * if move is aready made or capture is attempted, dispatches to valid capture*/
	private boolean validMove(int x, int y) {
		if((Math.abs(x - pieceSelectedX) == 2) && (Math.abs(y - pieceSelectedY) == 2)) {
			return validCapture(x, y);
			}
		else {
			if (pieceSelected.isFire()) {
				if (((y - pieceSelectedY) == 1) && (Math.abs(x - pieceSelectedX) == 1)) {
					return true;
				}
				else {
					return false;
				}
			}
			else {
				if (((y - pieceSelectedY) == -1) && (Math.abs(x - pieceSelectedX) == 1)) {
					return true;
				}
				else {
					return false;
					}
			}
		}
	}
	/* return true if peiceSelected would capture by moving to x, y*/
	private boolean validCapture(int x, int y) {
		int indicatorX = (x - pieceSelectedX);
		int indicatorY = (y - pieceSelectedY);
		int positionCaptureX;
		int positionCaptureY;
		if (pieceSelected.isKing()) {
			return validKingCapture(x, y, indicatorX, indicatorY);
		}
		/*set to water*/
		positionCaptureY = (-1 + pieceSelectedY);
		//unless Fire
		if (pieceSelected.isFire()) {
			positionCaptureY = (1 + pieceSelectedY);
		}
		positionCaptureX = (indicatorX / 2 + pieceSelectedX);
		if (pieceAt(positionCaptureX, positionCaptureY) != null) {
			if ((pieceAt(positionCaptureX, positionCaptureY).isFire() == false) && (pieceSelected.isFire())) {
					return true;
				}
				if ((pieceAt(positionCaptureX, positionCaptureY).isFire()) &&
					(pieceSelected.isFire() == false)) {
						return true;
				}
				else {
					return false;
				}
		}
		else {
			return false;
		}
	}
	    /* checks wheter a king move capture by moving to x, y
	     * dispatches to validKingcapture if move is already made and nother cab be
	     * or if a capture is being attempted */
	private boolean validKingMove(int x, int y) {
		if (Math.abs(x - pieceSelectedX) == 2) {
			return validCapture(x, y);
		}
		if (((Math.abs(x - pieceSelectedX)) == 1) && ((Math.abs(y - pieceSelectedY)) == 1)) {
			return true;
		}
		else {
			return false;
		}
	}

	/*checks whether a king can capture by moving to x, y */
	private boolean validKingCapture(int x, int y, int indicatorX, int indicatorY) {
		int positionCaptureX;
		int positionCaptureY;
		positionCaptureX = ((indicatorX / 2) + pieceSelectedX);
		positionCaptureY = ((indicatorY / 2) + pieceSelectedY);
		if (pieceAt(positionCaptureX, positionCaptureY) != null) {
				if ((pieceAt(positionCaptureX, positionCaptureY).isFire()) &&
					(pieceSelected.isFire() == false)) {
						return true;
				}
				if ((pieceAt(positionCaptureX, positionCaptureY).isFire() == false) &&
					(pieceSelected.isFire())) {
						return true;
				}
				else {
					return false;
				}
		}
		else {
			return false;
		}	
	}




// **** Selections, movements and special movements: make king & Bomb (explosion at bottom due to length) *****
	
	public void select(int x, int y) {
		if ((pieceSelected == null) || ((madeMove == false) && (pieceAt(x, y) != null))) {
				pieceSelected = pieceAt(x, y);
				pieceSelectedX = x;
				pieceSelectedY = y;
		}
		else {
			madeMove = true;
			pieceSelectedX = x;
			pieceSelectedY = y;
			pieceSelected.move(pieceSelectedX, pieceSelectedY);				
		}
	}

	public void place(Piece p, int x, int y) {
		if (p != null && x <= 7 && x >= 0 && y <= 7 && y >=0) {
			playingBoard[7 - y][x] = p;
		}
	}
	public Piece remove(int x, int y) {
		if (x > 7 || y > 7 || x < 0 || y < 0) {
			System.out.println("Piece (" + x + ", " + y + ") out of bounds");
			return null;
		}
		if (pieceAt(x, y) == null) {
			System.out.println("No piece at " + x + ", " + y);
			return null;
		}
		else{
			Piece toBail = playingBoard[7 - y][x];
			playingBoard[7 - y][x] = null;
			return toBail;
		}
	}
}







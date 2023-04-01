import java.util.LinkedList;

public class Piece {
    int xPosition;
    int yPosition;
    boolean isWhite;
    boolean firstMove;
    boolean inCheck;
    boolean moveAvailable;
    LinkedList<Piece> piece;
    String name;
    int[][] validMoves = new int[8][8];

    public Piece(int xPosition, int yPosition, boolean isWhite, String n, LinkedList<Piece> ps){
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.isWhite = isWhite;
        piece = ps;
        name = n;
        firstMove = true;
        moveAvailable = false;
        inCheck = false;
        ps.add(this);
        initialize(this.validMoves);
    }

    public void gameOver(Piece piece) {
        if(piece.isWhite) {
            Chess.Bwins = true;
        } else {
            Chess.Wwins = true;
        }
    }

    public void move(int xPosition, int yPosition) {
        if(Chess.getPiece(xPosition*100, yPosition*100) != null) {
            if((validMoves[xPosition][yPosition] == 2)) {
                Chess.getPiece(xPosition*100, yPosition*100).kill();
                System.out.println("Kill");
            }else {
                xPosition = xPosition*100;
                yPosition = yPosition*100;
                return;
            }
        } else {
            if((validMoves[xPosition][yPosition] == 0)) {
                xPosition = xPosition*100;
                yPosition = yPosition*100;
                return;
            }
        }
        Chess.check = false;
        System.out.println("Set to false");
        moveAvailable = false;
        inCheck = false;
        firstMove = false;
        this.xPosition = xPosition;
        this.yPosition = yPosition;
    }

    public void initialize(int[][] grid) {
        for(int y = 0; y < 8; y++) {
            for(int x = 0; x < 8; x++) {
                grid[x][y] = 0;
            }
        }
    }

    public int[][] validMoves() {
        System.out.println("Valid Moves");
        initialize(this.validMoves);
        if(name == "pawn") {
            moveCheckPawn(xPosition, yPosition, isWhite);
        } else if (name == "rook") {
            moveCheckRook(xPosition, yPosition);
        } else if (name == "knight") {
            moveCheckKnight(xPosition, yPosition);
        } else if (name == "bishop") {
            moveCheckBishop(xPosition, yPosition);
        } else if (name == "queen") {
            moveCheckQueen(xPosition, yPosition);
        } else if (name == "king") {
            moveCheckKing(xPosition, yPosition);
        }
        return this.validMoves;
    }

    public int[][] checkKing() {
        System.out.println("Check King");
        initialize(this.validMoves);
        if(name == "pawn") {
            moveCheckPawn(xPosition, yPosition, isWhite);
        } else if (name == "rook") {
            moveCheckRook(xPosition, yPosition);
        } else if (name == "knight") {
            moveCheckKnight(xPosition, yPosition);
        } else if (name == "bishop") {
            moveCheckBishop(xPosition, yPosition);
        } else if (name == "queen") {
            moveCheckQueen(xPosition, yPosition);
        } else if (name == "king") {
            moveCheckKing(xPosition, yPosition);
        }
        return inCheck();
    }

    public int[][] inCheck() {
        int[][] temp = new int[8][8];
        initialize(temp);
        for(int y = 0; y < 8; y++) {
            for(int x = 0; x < 8; x++) {
                if(validMoves[x][y] == 2) {
                    if((Chess.getPiece(x*100, y*100).name == "king") && (Chess.getPiece(x*100, y*100) != this)) {
                        System.out.println("Found king");
                        temp[x][y] = 3;
                        Piece tempP = Chess.getPiece(x*100, y*100);
                        tempP.inCheck = true;
                        tempP.moveCheckKing(tempP.xPosition, tempP.yPosition);
                    }
                }
            }
        }
        return temp;
    }

    public void moveCheckQueen(int xp, int yp) {
        up(xp, yp);
        down(xp, yp);
        left(xp, yp);  
        right(xp, yp);
        diagonalUpRight(xp, yp);
        diagonalUpLeft(xp, yp);
        diagonalDownRight(xp, yp);
        diagonalDownLeft(xp, yp);
    }

    public void moveCheckRook(int xp, int yp) {
        up(xp, yp);
        down(xp, yp);
        left(xp, yp);  
        right(xp, yp);
    }

    public void moveCheckBishop(int xp, int yp) {
        diagonalUpRight(xp, yp);
        diagonalUpLeft(xp, yp);
        diagonalDownRight(xp, yp);
        diagonalDownLeft(xp, yp);
    }

    public void moveCheckKnight(int xp, int yp) {
        upL(xp, yp);
        downL(xp, yp);
        leftL(xp, yp);
        rightL(xp, yp);
    }

    public void moveCheckPawn(int xp, int yp, boolean isWhite) {
        if(isWhite) {
            if(firstMove) {
                if((Chess.getPiece((xp)*100, (yp-2)*100) == null)) {
                    this.validMoves[xp][yp-2] = -1;
                }
            }
            if(yp-1 >= 0) {
                if((Chess.getPiece((xp)*100, (yp-1)*100) == null)) {
                    this.validMoves[xp][yp-1] = -1;
                }
                if(Chess.getPiece((xp-1)*100, (yp-1)*100) != null) {
                    if((Chess.getPiece((xp-1)*100, (yp-1)*100).isWhite != isWhite)) {
                        this.validMoves[xp-1][yp-1] = 2;
                    } 
                } else if((xp-1 >= 0)) {
                    this.validMoves[xp-1][yp-1] = -2;
                }
                if(Chess.getPiece((xp+1)*100, (yp-1)*100) != null) {
                    if((Chess.getPiece((xp+1)*100, (yp-1)*100).isWhite != isWhite)) {
                        this.validMoves[xp+1][yp-1] = 2;
                    }
                } else if((xp+1 < 8)) {
                    this.validMoves[xp+1][yp-1] = -2;
                }
            }
        } else {
            if(firstMove) {
                if((Chess.getPiece((xp)*100, (yp+2)*100) == null)) {
                    this.validMoves[xp][yp+2] = -1;
                }
            }
            if((yp+1 < 8)) {
                if((Chess.getPiece((xp)*100, (yp+1)*100) == null)) {
                    this.validMoves[xp][yp+1] = -1;
                }
                if(Chess.getPiece((xp-1)*100, (yp+1)*100) != null) {
                    if((Chess.getPiece((xp-1)*100, (yp+1)*100).isWhite != isWhite)) {
                        this.validMoves[xp-1][yp+1] = 2;
                    }
                } else if((xp-1 >= 0)) {
                    this.validMoves[xp-1][yp+1] = -2;
                }
                if(Chess.getPiece((xp+1)*100, (yp+1)*100) != null) {
                    if((Chess.getPiece((xp+1)*100, (yp+1)*100).isWhite != isWhite)) {
                        this.validMoves[xp+1][yp+1] = 2;
                    }
                } else if((xp+1 < 8)) {
                    this.validMoves[xp+1][yp+1] = -2;
                }
            }
        }
    }

    public int[][] allPiecesAdd() {
        int[][] temp = new int[8][8];
        int[][] all = new int[8][8];
        initialize(temp);
        initialize(all);
        for(int y = 0; y < 8; y++) {
            for(int x = 0; x < 8; x++) {
                if(Chess.getPiece((x)*100, (y)*100) != null) {
                    if((Chess.getPiece(x*100, y*100).isWhite != this.isWhite) && (Chess.getPiece(x*100, y*100).name != "king")) {
                        temp = Chess.getPiece((x)*100, (y)*100).validMoves();
                        for(int y2 = 0; y2 < 8; y2++) {
                            for(int x2 = 0; x2 < 8; x2++) {
                                if(Chess.getPiece(x*100, y*100).name == "pawn") {
                                    if((temp[x2][y2] == -2)) {
                                        all[x2][y2] = -1;
                                    }
                                } else {
                                    if((temp[x2][y2] == -1)) {
                                        all[x2][y2] = -1;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return all;
    }

    public void moveCheckKing(int xp, int yp) {
        int[][] temp = allPiecesAdd();
        for(int y = -1; y < 2; y++) {
            for(int x = -1; x < 2; x++) {
                if((yp+y >= 0) && (yp+y < 8) && (xp+x >= 0) && (xp+x < 8)) {
                    if(temp[xp+x][yp+y] != -1) {
                        if(Chess.getPiece((xp+x)*100, (yp+y)*100) == null) {
                            this.validMoves[xp+x][yp+y] = -1;
                            moveAvailable = true;
                        } else if(Chess.getPiece((xp+x)*100, (yp+y)*100).isWhite != isWhite) {
                            this.validMoves[xp+x][yp+y] = 2;
                            moveAvailable = true;
                        }
                    }
                }
            }
        }
        if(!moveAvailable && inCheck) {
            System.out.println("Check mate");
            gameOver(this);
        }
    }

    public void upL(int xp, int yp) {
        int xpTemp = xp;
        int ypTemp = yp;
        xp += 1;
        yp -= 2;
        if((xp < 8) && (yp >= 0)) {
            if(Chess.getPiece((xp)*100, (yp)*100) == null) {
                this.validMoves[xp][yp] = -1;
            } else if(Chess.getPiece((xp)*100, (yp)*100).isWhite != isWhite) {
                this.validMoves[xp][yp] = 2;
            }
        }
        xp = xpTemp;
        yp = ypTemp;
        xp -= 1;
        yp -= 2;
        if((xp >= 0) && (yp >= 0)) {
            if(Chess.getPiece((xp)*100, (yp)*100) == null) {
                this.validMoves[xp][yp] = -1;
            } else if(Chess.getPiece((xp)*100, (yp)*100).isWhite != isWhite) {
                this.validMoves[xp][yp] = 2;
            }
        }
    }

    public void downL(int xp, int yp) {
        int xpTemp = xp;
        int ypTemp = yp;
        xp += 1;
        yp += 2;
        if((xp < 8) && (yp < 8)) {
            if(Chess.getPiece((xp)*100, (yp)*100) == null) {
                this.validMoves[xp][yp] = -1;
            } else if(Chess.getPiece((xp)*100, (yp)*100).isWhite != isWhite) {
                this.validMoves[xp][yp] = 2;
            }
        }
        xp = xpTemp;
        yp = ypTemp;
        xp -= 1;
        yp += 2;
        if((xp >= 0) && (yp < 8)) {
            if(Chess.getPiece((xp)*100, (yp)*100) == null) {
                this.validMoves[xp][yp] = -1;
            } else if(Chess.getPiece((xp)*100, (yp)*100).isWhite != isWhite) {
                this.validMoves[xp][yp] = 2;
            }
        }
    }

    public void leftL(int xp, int yp) {
        int xpTemp = xp;
        int ypTemp = yp;
        xp -= 2;
        yp += 1;
        if((xp >= 0) && (yp < 8)) {
            if(Chess.getPiece((xp)*100, (yp)*100) == null) {
                this.validMoves[xp][yp] = -1;
            } else if(Chess.getPiece((xp)*100, (yp)*100).isWhite != isWhite) {
                this.validMoves[xp][yp] = 2;
            }
        }
        xp = xpTemp;
        yp = ypTemp;
        xp -= 2;
        yp -= 1;
        if((xp >= 0) && (yp >= 0)) {
            if(Chess.getPiece((xp)*100, (yp)*100) == null) {
                this.validMoves[xp][yp] = -1;
            } else if(Chess.getPiece((xp)*100, (yp)*100).isWhite != isWhite) {
                this.validMoves[xp][yp] = 2;
            }
        }
    }

    public void rightL(int xp, int yp) {
        int xpTemp = xp;
        int ypTemp = yp;
        xp += 2;
        yp += 1;
        if((xp < 8) && (yp < 8)) {
            if(Chess.getPiece((xp)*100, (yp)*100) == null) {
                this.validMoves[xp][yp] = -1;
            } else if(Chess.getPiece((xp)*100, (yp)*100).isWhite != isWhite) {
                this.validMoves[xp][yp] = 2;
            }
        }
        xp = xpTemp;
        yp = ypTemp;
        xp += 2;
        yp -= 1;
        if((xp < 8) && (yp >= 0)) {
            if(Chess.getPiece((xp)*100, (yp)*100) == null) {
                this.validMoves[xp][yp] = -1;
            } else if(Chess.getPiece((xp)*100, (yp)*100).isWhite != isWhite) {
                this.validMoves[xp][yp] = 2;
            }
        }
    }

    public void diagonalUpRight(int xp, int yp) {
        if(Chess.getPiece((xp+1)*100, (yp-1)*100) == null) {
            if((yp-1 >= 0) && (xp+1 < 8)) {
                yp -= 1;
                xp += 1;
                this.validMoves[xp][yp] = -1;
                diagonalUpRight(xp, yp);
            }
        } else if(Chess.getPiece((xp+1)*100, (yp-1)*100).isWhite != isWhite) {
            yp -= 1;
            xp += 1;
            this.validMoves[xp][yp] = 2;
        }
    }

    public void diagonalDownRight(int xp, int yp) {
        if(Chess.getPiece((xp+1)*100, (yp+1)*100) == null) {
            if((yp+1 < 8) && (xp+1 < 8)) {
                yp += 1;
                xp += 1;
                this.validMoves[xp][yp] = -1;
                diagonalDownRight(xp, yp);
            }
        } else if(Chess.getPiece((xp+1)*100, (yp+1)*100).isWhite != isWhite) {
            yp += 1;
            xp += 1;
            this.validMoves[xp][yp] = 2;
        }
    }

    public void diagonalUpLeft(int xp, int yp) {
        if(Chess.getPiece((xp-1)*100, (yp-1)*100) == null) {
            if((yp-1 >= 0) && (xp-1 >= 0)) {
                yp -= 1;
                xp -= 1;
                this.validMoves[xp][yp] = -1;
                diagonalUpLeft(xp, yp);
            }
        } else if(Chess.getPiece((xp-1)*100, (yp-1)*100).isWhite != isWhite) {
            yp -= 1;
            xp -= 1;
            this.validMoves[xp][yp] = 2;
        }
    }

    public void diagonalDownLeft(int xp, int yp) {
        if(Chess.getPiece((xp-1)*100, (yp+1)*100) == null) {
            if((yp+1 < 8) && (xp-1 >= 0)) {
                yp += 1;
                xp -= 1;
                this.validMoves[xp][yp] = -1;
                diagonalDownLeft(xp, yp);
            }
        } else if(Chess.getPiece((xp-1)*100, (yp+1)*100).isWhite != isWhite) {
            yp += 1;
            xp -= 1;
            this.validMoves[xp][yp] = 2;
        }
    }

    public void up(int xp, int yp) {
        if(Chess.getPiece((xp)*100, (yp-1)*100) == null) {
            if(yp-1 >= 0) {
                yp -= 1;
                this.validMoves[xp][yp] = -1;
                up(xp, yp);
            }
        } else if(Chess.getPiece((xp)*100, (yp-1)*100).isWhite != isWhite) {
            yp -= 1;
            this.validMoves[xp][yp] = 2;
        }
    }

    public void down(int xp, int yp) {
        if(Chess.getPiece((xp)*100, (yp+1)*100) == null) {
            if(yp+1 < 8) {
                yp += 1;
                this.validMoves[xp][yp] = -1;
                down(xp, yp);
            }
        } else if(Chess.getPiece((xp)*100, (yp+1)*100).isWhite != isWhite) {
            yp += 1;
            this.validMoves[xp][yp] = 2;
        }
    }

    public void left(int xp, int yp) {
        if(Chess.getPiece((xp-1)*100, (yp)*100) == null) {
            if(xp-1 >= 0) {
                xp -= 1;
                this.validMoves[xp][yp] = -1;
                left(xp, yp);
            }
        } else if(Chess.getPiece((xp-1)*100, (yp)*100).isWhite != isWhite) {
            xp -= 1;
            this.validMoves[xp][yp] = 2;
        }
    }

    public void right(int xp, int yp) {
        if(Chess.getPiece((xp+1)*100, (yp)*100) == null) {
            if(xp+1 < 8) { 
                xp += 1;
                this.validMoves[xp][yp] = -1;
                right(xp, yp);
            }
        } else if(Chess.getPiece((xp+1)*100, (yp)*100).isWhite != isWhite) {
            xp += 1;
            this.validMoves[xp][yp] = 2;
        }
    }

    public void kill(){
        if(this.name == "king") {
            gameOver(this);
        }
        piece.remove(this);
    }
}

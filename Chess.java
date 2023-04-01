import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.event.MouseInputListener;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

public class Chess extends MouseAdapter {
    public static LinkedList<Piece> piece = new LinkedList<>();
    public static Piece selectedPiece = null;
    public static boolean check = false;
    public static boolean Wwins = false;
    public static boolean Bwins = false;
    public static boolean gamePaused = false;
    public static int[][] validMoves = new int[8][8];
    public static void main(String[] args) {
        Image[] paused = new Image[3];
        Image[] highlighted = new Image[3];
        Image[] imgs = new Image[12];
        try {
            highlighted[0] = ImageIO.read(new File("HighlightedEmpty.png"));
            highlighted[1] = ImageIO.read(new File("HighlightedKill.png"));
            highlighted[2] = ImageIO.read(new File("Check.png")).getScaledInstance(100, 100, Image.SCALE_SMOOTH);
            paused[0] = ImageIO.read(new File("Paused.png")).getScaledInstance(800, 800, Image.SCALE_SMOOTH);
            paused[1] = ImageIO.read(new File("WhiteWins.png")).getScaledInstance(800, 800, Image.SCALE_SMOOTH);
            paused[2] = ImageIO.read(new File("BlackWins.png")).getScaledInstance(800, 800, Image.SCALE_SMOOTH);
            imgs[0] = ImageIO.read(new File("KingW.png"));
            imgs[1] = ImageIO.read(new File("QueenW.png"));
            imgs[2] = ImageIO.read(new File("BishopW.png"));
            imgs[3] = ImageIO.read(new File("KnightW.png"));
            imgs[4] = ImageIO.read(new File("RookW.png"));
            imgs[5] = ImageIO.read(new File("PawnW.png"));

            imgs[6] = ImageIO.read(new File("KingB.png"));
            imgs[7] = ImageIO.read(new File("QueenB.png"));
            imgs[8] = ImageIO.read(new File("BishopB.png"));
            imgs[9] = ImageIO.read(new File("KnightB.png"));
            imgs[10] = ImageIO.read(new File("RookB.png"));
            imgs[11] = ImageIO.read(new File("PawnB.png"));
            for (int x = 0 ; x < 12 ; x++){
                imgs[x] = imgs[x].getScaledInstance(100, 100, Image.SCALE_SMOOTH);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        //White pieces
        Piece Wrook1 = new Piece (0,7,true,"rook",piece);
        Piece Wknight1 = new Piece (1,7,true,"knight",piece);
        Piece Wbishop1 = new Piece (2,7,true,"bishop",piece);
        Piece Wking = new Piece (3,7,true,"king",piece);
        Piece Wqueen = new Piece (4,7,true,"queen",piece);
        Piece Wbishop2 = new Piece (5,7,true,"bishop",piece);
        Piece Wknight2 = new Piece (6,7,true,"knight",piece);
        Piece Wrook2 = new Piece (7,7,true,"rook",piece);
        
        Piece Wpawn1 = new Piece (0,6,true,"pawn",piece);
        Piece Wpawn2 = new Piece (1,6,true,"pawn",piece);
        Piece Wpawn3 = new Piece (2,6,true,"pawn",piece);
        Piece Wpawn4 = new Piece (3,6,true,"pawn",piece);
        Piece Wpawn5 = new Piece (4,6,true,"pawn",piece);
        Piece Wpawn6 = new Piece (5,6,true,"pawn",piece);
        Piece Wpawn7 = new Piece (6,6,true,"pawn",piece);
        Piece Wpawn8 = new Piece (7,6,true,"pawn",piece);

        //Black pieces
        Piece Brook1 = new Piece (0,0,false,"rook",piece);
        Piece Bknight1 = new Piece (1,0,false,"knight",piece);
        Piece Bbishop1 = new Piece (2,0,false,"bishop",piece);
        Piece Bking = new Piece (3,0,false,"king",piece);
        Piece Bqueen = new Piece (4,0,false,"queen",piece);
        Piece Bbishop2 = new Piece (5,0,false,"bishop",piece);
        Piece Bknight2 = new Piece (6,0,false,"knight",piece);
        Piece Brook2 = new Piece (7,0,false,"rook",piece);

        Piece Bpawn1 = new Piece (0,1,false,"pawn",piece);
        Piece Bpawn2 = new Piece (1,1,false,"pawn",piece);
        Piece Bpawn3 = new Piece (2,1,false,"pawn",piece);
        Piece Bpawn4 = new Piece (3,1,false,"pawn",piece);
        Piece Bpawn5 = new Piece (4,1,false,"pawn",piece);
        Piece Bpawn6 = new Piece (5,1,false,"pawn",piece);
        Piece Bpawn7 = new Piece (6,1,false,"pawn",piece);
        Piece Bpawn8 = new Piece (7,1,false,"pawn",piece);

        JFrame frame = new JFrame();
        frame.setBounds(50, 100, 800, 800);
        frame.setUndecorated(true);
        
        JPanel panel = new JPanel()
        {
            @Override
            public void paint(Graphics g) {
                boolean white = true;
                for ( int y = 0 ; y < 8 ; y++ ){
                    for ( int x = 0 ; x < 8 ; x++ ){
                        if (white) {
                            //HSB COLOR PEN https://codepen.io/HunorMarton/full/eWvewo
                            g.setColor(Color.white);
                            //g.setColor(new Color(x, y, x));
                        } else {
                            g.setColor(Color.black);
                        }
                        g.fillRect(x*100, y*100, 100, 100);
                        white =! white;
                    }
                    white =! white; 
                }
                for ( int y = 0 ; y < 8 ; y++ ){
                    for ( int x = 0 ; x < 8 ; x++ ){
                        if(validMoves[x][y] == 3) {
                            g.setColor(Color.red);
                            g.fillRect(x*100, y*100, 100, 100);
                            check = true;
                        }
                        if(validMoves[x][y] == 2) {
                            g.drawImage(highlighted[1], x*100, y*100, this);
                        }
                        if(validMoves[x][y] == -1) {
                            g.drawImage(highlighted[0], x*100, y*100, this);
                        }
                    }
                }
                for ( Piece p : piece ){
                    int ind = 0;
                    if (p.name.equalsIgnoreCase("king")) {
                        ind = 0;
                    }
                    if (p.name.equalsIgnoreCase("queen")) {
                        ind = 1;
                    }
                    if (p.name.equalsIgnoreCase("bishop")) {
                        ind = 2;
                    }
                    if (p.name.equalsIgnoreCase("knight")) {
                        ind = 3;
                    }
                    if (p.name.equalsIgnoreCase("rook")) {
                        ind = 4;
                    }
                    if (p.name.equalsIgnoreCase("pawn")) {
                        ind = 5;
                    }
                    if (!p.isWhite) {
                        ind += 6;
                    }
                    g.drawImage(imgs[ind], p.xPosition*100, p.yPosition*100, this);
                }
                //frame.repaint(); ////DONT DO THIS! YOUR COMPUTER WILL SCREAM!!!! (Lesson learned)
                if(check) {
                    for ( int y = 0 ; y < 8 ; y++ ){
                        for ( int x = 0 ; x < 8 ; x++ ){
                            if(validMoves[x][y] == 3) {
                                g.drawImage(highlighted[2], (x*100)+32, (y*100)-60, this);
                            }
                        }
                    }
                }
                if(gamePaused){
                    g.drawImage(paused[0], 0, 0, this);
                }
                if(Wwins){
                    g.drawImage(paused[1], 0, 0, this);
                }
                if(Bwins){
                    g.drawImage(paused[2], 0, 0, this);
                }
            }
        };
        frame.add(panel);
        frame.addMouseListener(new MouseInputListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(!Bwins && !Wwins) {
                    if(gamePaused) {
                        gamePaused = false;
                    } else {
                        if((!check) || (getPiece(e.getX(), e.getY()).name == "king")) {
                            validMoves = getPiece(e.getX(), e.getY()).validMoves();
                        }
                    }
                    frame.repaint();
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {
                if(!Bwins && !Wwins) {
                    if((!gamePaused) && (getPiece(e.getX(), e.getY())!= null)) {
                        if (getPiece(e.getX(), e.getY()).isWhite){
                            System.out.print("White ");
                        }
                        if (!getPiece(e.getX(), e.getY()).isWhite){
                            System.out.print("Black ");
                        }
                        System.out.println(getPiece(e.getX(), e.getY()).name);

                        if((!check) || (getPiece(e.getX(), e.getY()).name == "king")) {
                            selectedPiece = getPiece(e.getX(), e.getY());
                            validMoves = selectedPiece.validMoves();
                            frame.repaint();
                        }
                    }
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (selectedPiece != getPiece(e.getX(), e.getY())){
                    selectedPiece.move(e.getX()/100, e.getY()/100);
                    frame.repaint();
                }
                validMoves = selectedPiece.checkKing();
                selectedPiece = null;
                frame.repaint();
            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {
                if(!Bwins && !Wwins) {
                    gamePaused = true;
                    frame.repaint();
                }
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                if (selectedPiece != null){
                    selectedPiece.xPosition = (e.getX()*100)-50;
                    selectedPiece.yPosition = (e.getY()*100)-50;
                    frame.repaint();
                }
            }

            @Override
            public void mouseMoved(MouseEvent e) {
            }
            
        });
        frame.setDefaultCloseOperation(3);
        frame.setVisible(true);
    }

    public static Piece getPiece(int x, int y){
        int xPosition = x/100;
        int yPosition = y/100;
        for (Piece p : piece){
            if ((p.xPosition == xPosition)&&(p.yPosition == yPosition)){
                return p;
            }
        }
        return null;
    }
}
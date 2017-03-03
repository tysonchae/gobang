package game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

class Gobang extends JFrame implements MouseListener {
	// constant for stones' status
    final int NONE = 0;
	final int BLACK = 1;
	final int WHITE = 2;

	static final int LINE_NUM = 20; // number of line
	static final int LINE_WIDTH = 30; // line width
	final int BOARD_SIZE = LINE_WIDTH * (LINE_NUM - 1); // board size
	final int STONE_SIZE = (int) (LINE_WIDTH * 0.9); // stone size
	final int X0; // start position x
	final int Y0; // start position y
	final int FRAME_WIDTH; // Frame width
	final int FRAME_HEIGHT; // Frame height

	// turn(white or black)
	int turn;
	// stones' status on the board(white, black or empty)
	static int stones[][] = new int[LINE_NUM][LINE_NUM];

	Image img = null;
	Graphics gImg = null;

	public Gobang(String title) {
		super(title);
		// Event Handler
		addMouseListener(this);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		setVisible(true); // make Frame visible 
		Insets insets = getInsets(); // 
		// get the drawing position(LEFT, TOP) on the board

		X0 = insets.left + LINE_WIDTH;
		Y0 = insets.top + LINE_WIDTH;

		// Calculate Frame size
		FRAME_WIDTH = BOARD_SIZE + LINE_WIDTH * 2 + insets.left + insets.right;
		FRAME_HEIGHT = BOARD_SIZE + LINE_WIDTH * 2 + insets.top + insets.bottom;

		// Showing the frame on (100,100)
		setBounds(100, 100, FRAME_WIDTH, FRAME_HEIGHT);
		img = createImage(FRAME_WIDTH, FRAME_HEIGHT);
		gImg = img.getGraphics();
		setResizable(false); // make the size of frame fixed

		// draw gobang board 
		init();

	} 

	public void init() {
		// Black goes first
		turn = BLACK;
		// 
		for (int i = 0; i < LINE_NUM; i++) {
			for (int j = 0; j < LINE_NUM; j++) {
				stones[i][j] = NONE;
			}
		}
		// draw a board
		drawBoard(gImg);

	}

	public void drawBoard(Graphics g) {
		// make the board yellow
		g.setColor(Color.orange);
		g.fillRect(0, 0, FRAME_WIDTH, FRAME_HEIGHT);
		// draw the line black
		g.setColor(Color.black);
		for (int i = 0; i < LINE_NUM; i++) {
			g.drawLine(X0, Y0 + i * LINE_WIDTH, X0 + BOARD_SIZE, Y0 + i
					* LINE_WIDTH);
			g.drawLine(X0 + i * LINE_WIDTH, Y0, X0 + i * LINE_WIDTH, Y0
					+ BOARD_SIZE);
		}
	}

	public void paint(Graphics g) {
		if (img == null)
			return;
		g.drawImage(img, 0, 0, this); // copy to Frame 
	}

	public void mousePressed(MouseEvent e) { // MouseListener
		int x = e.getX(); // x pointer
		int y = e.getY(); // y pointer
		int stoneX, stoneY;

		// 1. finish method if x or y points outside of board
		if (x < X0 - LINE_WIDTH / 2
				|| x > X0 + (LINE_NUM - 1) * LINE_WIDTH + LINE_WIDTH / 2)
			return;
		if (y < Y0 - LINE_WIDTH / 2
				|| y > Y0 + (LINE_NUM - 1) * LINE_WIDTH + LINE_WIDTH / 2)
			return;

		// 2. 
		x = (x - X0 + LINE_WIDTH / 2) / LINE_WIDTH * LINE_WIDTH + X0;
		y = (y - Y0 + LINE_WIDTH / 2) / LINE_WIDTH * LINE_WIDTH + Y0;

		Insets insets = getInsets();
		stoneX = (x - insets.left) / LINE_WIDTH - 1;
		stoneY = (y - insets.top) / LINE_WIDTH - 1;
		// System.out.println(arrI + ":" + arrJ);

		// 3. Stone will be drawn correctly only when it is divided by 2
		x -= STONE_SIZE / 2;
		y -= STONE_SIZE / 2;

		// 4. draw a stone if left button of mouse is pressed
		if (e.getModifiersEx() == MouseEvent.BUTTON1_DOWN_MASK
				&& stones[stoneX][stoneY] == NONE) {
			// draw black stone if it is black turn
			if (turn == BLACK) {
				gImg.setColor(Color.black);
				gImg.fillOval(x, y, STONE_SIZE, STONE_SIZE);
				stones[stoneX][stoneY] = BLACK;
				// otherwise draw white stone
			} else {
				gImg.setColor(Color.white);
				gImg.fillOval(x, y, STONE_SIZE, STONE_SIZE);
				// draw black boarder for white stone
				gImg.setColor(Color.black);
				gImg.drawOval(x, y, STONE_SIZE, STONE_SIZE);
				stones[stoneX][stoneY] = WHITE;
			}
		}

		// 5. call repaint()
			repaint();

		// 6. Judge
		// when five stones are in a row
		Judge judge = new Judge();
		if (judge.isGobang(turn, stoneX, stoneY)) {
			if (turn == BLACK) {
				JOptionPane.showMessageDialog(this, "Black won", "Gobang",
						JOptionPane.INFORMATION_MESSAGE);
			} else {
				JOptionPane.showMessageDialog(this, "White won", "Gobang",
						JOptionPane.INFORMATION_MESSAGE);
			}
			// initiate
			init();
			repaint();
		} else {
			if (turn == BLACK) {
				turn = WHITE;
			} else {
				turn = BLACK;
			}
		}
	}

	public void mouseClicked(MouseEvent e) {
	} // MouseListener

	public void mouseEntered(MouseEvent e) {
	} // MouseListener

	public void mouseExited(MouseEvent e) {
	} // MouseListener

	public void mouseReleased(MouseEvent e) {
	} // MouseListener


}

package com.sjtu.tetris;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.util.Timer;
import java.util.TimerTask;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Game extends JPanel
{
	public static final int ROWS = 20;
	public static final int COLS = 10;
	
	//��ǽ
	private Cell[][] wall = new Cell[ROWS][COLS];
	
	//��������ķ��������
	private Tetromino tetromino;
	
	//��һ�������ķ���
	private Tetromino nextOne;
	
	//����һ������ ����������ͼƬ���� 
	 static BufferedImage bg;
	 static BufferedImage gameOver;
	 static BufferedImage pause;
	static BufferedImage T;
	 static BufferedImage S;
 static BufferedImage Z;
	 static BufferedImage I;
	 static BufferedImage L;
	 static BufferedImage J;
	 static BufferedImage O;
	
	//��Game�����ӻ��ֱ���
	private int score = 0;
	private int lines = 0;
	//�÷ֱ�
	private static final int[]
			SCORE_TABLE = {0,10,30,100,200};
	
	//��ʱ�� ��Game����ӱ���timer
	private Timer timer;
	
	//��Game�������״̬�������
	private int state = RUNNING;
	public static final int RUNNING = 0;
	public static final int PAUSE = 1;
	public static final int GAME_OVER = 2;
	
	//��̬����飬����һ���Ծ�̬��Դ
	//��Ӳ��ͼƬ�ļ���ȡΪ�ڴ�Ķ���
	//ʹ��javax.imageio.ImageIO�����
	static{
		try{
			bg = ImageIO.read(Game.class.getResource("tetris.png"));
			gameOver = ImageIO.read(Game.class.getResource("game-over.png"));
			I = ImageIO.read(Game.class.getResource("I.png"));
			J = ImageIO.read(Game.class.getResource("J.png"));
			L = ImageIO.read(Game.class.getResource("L.png"));
			O= ImageIO.read(Game.class.getResource("O.png"));
			S = ImageIO.read(Game.class.getResource("S.png"));
			T = ImageIO.read(Game.class.getResource("T.png"));
			Z= ImageIO.read(Game.class.getResource("Z.png"));
			pause = ImageIO.read(Game.class.getResource("pause.png"));
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public Game(){
		
	}
	
	//��Ϸ����������
	public void action(){
		tetromino = Tetromino.randomOne();
		nextOne = Tetromino.randomOne();
		
		KeyListener l = new KeyListener(){

			@Override
			public void keyPressed(KeyEvent e)
			{
				int key = e.getKeyCode();
				switch(state){
				case RUNNING:
					handleRunningKeys(key);break;
				case PAUSE:
					handlePauseKeys(key);break;
				case GAME_OVER:
					handleGameOverKeys(key);break;
				}
				repaint();
				
			}

			@Override
			public void keyReleased(KeyEvent e){}

			@Override
			public void keyTyped(KeyEvent e){}
		};
			
			this.addKeyListener(l);
			this.setFocusable(true);
			this.requestFocus();
			
			timer = new Timer();
			TimerTask task = new TimerTask(){
				public void run(){
				
					index++;
					level = score/100 +1;
					level = level >20 ?20:level;
					speed = 21 - level;
				
					if(index % speed == 0){
						switch(state){
						case RUNNING:
							softDropAction();
						}
					}
					repaint();
			}
		};
		timer.schedule(task,10,10);
	}
		
		int index = 0;//���������
		int speed = 20;//�����ٶ�
		int level = 1;//����
		
		//��Ӽ��̴����¼�
		//������Ϸ����״̬ʱ��ļ����¼�
		private void handleRunningKeys(int key){
			switch(key){
			case KeyEvent.VK_UP:
				rotateLeftAction();break;
			case KeyEvent.VK_DOWN:
				softDropAction();   break;
			case KeyEvent.VK_LEFT:
				moveLeftAction();  break;
			case KeyEvent.VK_RIGHT:
				moveRightAction(); break;
			case KeyEvent.VK_SPACE:
				hardDropAction(); break;
			case KeyEvent.VK_Z:
				rotateRightAction(); break;
			case KeyEvent.VK_P:
				state = PAUSE; break;
			case KeyEvent.VK_Q:
				System.exit(0);//�ر�java����
			}
		}
		
		//������ͣ״̬ʱ��ļ����¼�
		private void handlePauseKeys(int key){
			//C  Q
		     switch(key){
		     case KeyEvent.VK_C:
		    	 state = RUNNING;
		    	 break;
		     case KeyEvent.VK_Q:
		    	 System.exit(0);
		     }
		}
		
		//������Ϸ����״̬ʱ��ļ����¼�
		private void handleGameOverKeys(int key){
			switch(key){
			case KeyEvent.VK_S:
				score = 0;
				lines = 0;
				level = 0;
				index = 0;
				wall = new Cell[ROWS][COLS];
				tetromino = Tetromino.randomOne();
				nextOne = Tetromino.randomOne();
				state = RUNNING;
				break;
			case KeyEvent.VK_Q:
				System.exit(0);
			}
		}
		
		//��д�����paint����
		public void paint(Graphics g){
			g.drawImage(bg,0,0,null);//����ͼƬ
			g.translate(15, 15);//ƽ����ʼ����
			paintWall(g);
			
			paintTetromino(g);
			paintNextOne(g);
			paintScore(g);
			paintState(g);
			
		}
		
		//���Ƶ�ǰ��Ϸ״̬
		private void paintState(Graphics g){
			switch(state){
			case GAME_OVER:
				g.drawImage(gameOver, -15,-15,null);//���ƻ�ȥ
				break;
			case PAUSE:
				g.drawImage(pause, -15,-15,null);
				break;
				
			}
		}
		
		private void paintScore(Graphics g){
			int x = 298;
			int y =160;
			Font f = new Font(Font.SANS_SERIF,Font.BOLD, 28);
			g.setColor(new Color(0x667799));
			String s = "SCORE:" + score;
			g.drawString(s,x,y);
			y += 56;
			s = "LINES:" +lines;
			g.drawString(s, x, y);
			y += 56;
			s = "LEVEL:" + level;
			g.drawString(s, x, y);
		}
			
		
		//��װ������������ķ���
		private void paintTetromino(Graphics g){
			if(this.tetromino == null){
				return;
			}
			
			Cell[] cells = tetromino.cells;
			for(Cell c: cells){
				int x = c.getCol()* 26;
				int y = c.getRow()* 26;
				g.drawImage(c.getImage(), x, y, null);
			}
		}
		
		//Game������ӣ���paint�е���
		private void paintNextOne(Graphics g){
			if(nextOne == null){
				return;
			}
			Cell[] cells = nextOne.cells;
			for(Cell c: cells){
				int x = (c.getCol()+10) * 26;
				int y = (c.getRow()+1) * 26;
				g.drawImage(c.getImage(), x, y,null);
			}
		}
		
		//��װ��ǽ�Ļ��ƹ���
		private void paintWall(Graphics g){
			for(int row = 0; row < ROWS; row++){
				for(int col = 0; col < COLS; col++){
					int x = col * 26;
					int y = row * 26;
					//���Ʒ���
					g.drawRect(x,y,26,26);
					if(wall[row][col] != null){
						Cell c = wall[row][col];
						g.drawImage(c.getImage(), x, y,null);
					}
				}
			}
			
		}
		
		public void moveRightAction(){
			tetromino.moveRight();
			if(outOfBounds() || coincide()){
				tetromino.moveLeft();
			}
		}
		
		public void moveLeftAction(){
			tetromino.moveLeft();
			if(outOfBounds() || coincide()){
				tetromino.moveRight();
			}
		}
		
		//�ж��غ�
		//������䷽����4�����ӵ�ĳ�����Ե�ǽ�϶�Ӧλ���з��飬���غ���
		private boolean coincide(){
			Cell[] cells = tetromino.cells;
			
			for(Cell c: cells){
				int row = c.getRow();
				int col = c.getCol();
				if(wall[row][col] != null){
					return true;
				}
			}
			return false;
		}
		
		public void softDropAction(){
			if(canDrop()){
				tetromino.softDrop();
			}else{
				landIntoWall();//��½
				int n = destroyLines();
				lines += n;
				score += SCORE_TABLE[n];
				//�ж���Ϸ�Ƿ����
				if(gameOver()){
					state = GAME_OVER;
				}else{
					//���ٵ�ǰ����
					tetromino = nextOne;
					nextOne = tetromino.randomOne();
				}
			}
		}
		
		//ǽ����һ�����������λ���Ѿ���ռ�ˣ��ͽ�����
		private boolean gameOver(){
			Cell[] cells = nextOne.cells;
			for(Cell c: cells){
				int row = c.getRow();
				int col = c.getCol();
				if(wall[row][col] != null){
					return true;
				}
			}
			return false;
		}
		
		public void hardDropAction(){
			while(canDrop()){
				tetromino.softDrop();
			}
			landIntoWall();//��½
			int n = destroyLines();
			lines += n;
			score += SCORE_TABLE[n];
			if(gameOver()){
				state = GAME_OVER;
			}else{
				tetromino = nextOne;
				nextOne = Tetromino.randomOne();
			}
			
		}
		
		//ÿ��������½����������Ӧ��ǽ����Ӧ��λ��
		private void landIntoWall(){
			Cell[] cells = tetromino.cells;
			for(Cell c: cells){
				int row = c.getRow();
				int col = c.getCol();
				wall[row][col] = c;
			}
		}
		
		//��鷽���Ƿ��ܹ�����
		//����Ͷ˻���ĳһ�����������Ѿ���ǽ�ˣ���������
		private boolean canDrop(){
			Cell[] cells = tetromino.cells;
			for(Cell c: cells){
				int row = c.getRow();
				if(row == ROWS -1){
					return false;//����������
				}
			}
			
			for(Cell c: cells){
				int row = c.getRow();
				int col = c.getCol();
				if(wall[row+1][col]!=null)
					return false;
			}
			return true;
		}
		
		private boolean outOfBounds(){
			Cell[] cells = tetromino.cells;
			for(Cell c: cells){
				if(c.getCol() >= COLS || c.getCol() < 0
						||c.getRow() > ROWS ||c.getRow() <0)
					return true;
				
			}
			return false;
		}
		
		//������
		//�㷨�����ÿ���У����������һ�����У�����������ĸ���ɾ��֮
		public int destroyLines(){
			int lines = 0;
			for(int i = 0; i < ROWS; i++){
				if(fullCells(i)){
					lines++;
					for(int j = i; j >=1; j--){
						//������wall[j-1]���Ƶ�wall[j]
						System.arraycopy(wall[j-1],0,wall[j],0,COLS);
						
					}
					for(int j =0; j < COLS;j++){
						wall[0][j] = null;//���wall[0]��ÿ��Ԫ��Ϊ��
					}
				}
			}
			return lines;
		}
		
		//��鵱ǰ��row�Ƿ������и���
		private boolean fullCells(int row){
			for(int col = 0; col < COLS; col++){
				if(wall[row][col] == null){
					return false;
				}
			}
			return true;//����
		}
		
		public void rotateRightAction(){
			tetromino.rotateRight();
			if(outOfBounds()|| coincide()){
				tetromino.rotateLeft();
			}
		}
		
		public void rotateLeftAction(){
			tetromino.rotateLeft();
			if(outOfBounds()|| coincide()){
				tetromino.rotateRight();
			}
		}
		public static void main(String[] args)
		{
			//�������ڿ����
			JFrame frame = new JFrame();
			Game game = new Game();
			frame.add(game);
			
			frame.setSize(530,580);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setVisible(true);
			game.action();
		}

}

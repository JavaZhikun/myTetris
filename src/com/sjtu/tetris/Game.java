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
	
	//画墙
	private Cell[][] wall = new Cell[ROWS][COLS];
	
	//正在下落的方块的引用
	private Tetromino tetromino;
	
	//下一个出场的方块
	private Tetromino nextOne;
	
	//声明一个变量 ，用于引用图片对象 
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
	
	//在Game中增加积分变量
	private int score = 0;
	private int lines = 0;
	//得分表
	private static final int[]
			SCORE_TABLE = {0,10,30,100,200};
	
	//定时器 在Game中添加变量timer
	private Timer timer;
	
	//在Game类中添加状态管理变量
	private int state = RUNNING;
	public static final int RUNNING = 0;
	public static final int PAUSE = 1;
	public static final int GAME_OVER = 2;
	
	//静态代码块，加载一次性静态资源
	//将硬盘图片文件读取为内存的对象
	//使用javax.imageio.ImageIO类完成
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
	
	//游戏的启动方法
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
		
		int index = 0;//下落计数器
		int speed = 20;//下落速度
		int level = 1;//级别
		
		//添加键盘处理事件
		//处理游戏运行状态时候的键盘事件
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
				System.exit(0);//关闭java程序
			}
		}
		
		//处理暂停状态时候的键盘事件
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
		
		//处理游戏结束状态时候的键盘事件
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
		
		//重写父类的paint方法
		public void paint(Graphics g){
			g.drawImage(bg,0,0,null);//背景图片
			g.translate(15, 15);//平移起始坐标
			paintWall(g);
			
			paintTetromino(g);
			paintNextOne(g);
			paintScore(g);
			paintState(g);
			
		}
		
		//绘制当前游戏状态
		private void paintState(Graphics g){
			switch(state){
			case GAME_OVER:
				g.drawImage(gameOver, -15,-15,null);//再移回去
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
			
		
		//封装绘制正在下落的方块
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
		
		//Game类中添加，在paint中调用
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
		
		//封装了墙的绘制过程
		private void paintWall(Graphics g){
			for(int row = 0; row < ROWS; row++){
				for(int col = 0; col < COLS; col++){
					int x = col * 26;
					int y = row * 26;
					//绘制方格
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
		
		//判断重合
		//如果下落方块中4个格子的某个各自的墙上对应位置有方块，就重合了
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
				landIntoWall();//着陆
				int n = destroyLines();
				lines += n;
				score += SCORE_TABLE[n];
				//判断游戏是否结束
				if(gameOver()){
					state = GAME_OVER;
				}else{
					//销毁当前方块
					tetromino = nextOne;
					nextOne = tetromino.randomOne();
				}
			}
		}
		
		//墙上下一个方块出场的位置已经被占了，就结束了
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
			landIntoWall();//着陆
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
		
		//每个格子着陆到行列所对应的墙上相应的位置
		private void landIntoWall(){
			Cell[] cells = tetromino.cells;
			for(Cell c: cells){
				int row = c.getRow();
				int col = c.getCol();
				wall[row][col] = c;
			}
		}
		
		//检查方块是否能够下落
		//到最低端或者某一个格子下面已经有墙了，则不能下落
		private boolean canDrop(){
			Cell[] cells = tetromino.cells;
			for(Cell c: cells){
				int row = c.getRow();
				if(row == ROWS -1){
					return false;//不能下落了
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
		
		//销毁行
		//算法：检查每个行，如果发现是一个满行，就利用数组的复制删除之
		public int destroyLines(){
			int lines = 0;
			for(int i = 0; i < ROWS; i++){
				if(fullCells(i)){
					lines++;
					for(int j = i; j >=1; j--){
						//将数组wall[j-1]复制到wall[j]
						System.arraycopy(wall[j-1],0,wall[j],0,COLS);
						
					}
					for(int j =0; j < COLS;j++){
						wall[0][j] = null;//填充wall[0]的每个元素为空
					}
				}
			}
			return lines;
		}
		
		//检查当前行row是否是满行格子
		private boolean fullCells(int row){
			for(int col = 0; col < COLS; col++){
				if(wall[row][col] == null){
					return false;
				}
			}
			return true;//满了
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
			//创建窗口框对象
			JFrame frame = new JFrame();
			Game game = new Game();
			frame.add(game);
			
			frame.setSize(530,580);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setVisible(true);
			game.action();
		}

}

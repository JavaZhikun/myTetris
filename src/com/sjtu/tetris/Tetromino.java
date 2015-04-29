package com.sjtu.tetris;

import java.util.Arrays;


/**
 * 四格方块
 * @author JavaZhikun
 *
 */
public abstract class Tetromino
{
	protected Cell[] cells = new Cell[4];
	public Tetromino(){}  //限制外部继承
	
	protected State[] states;//旋转状态数据
	protected int index = 10000;//旋转状态序号
	
	//在Tetromino中添加内部类，旋转状态数据结构
	protected class State{
		int row0,col0,row1,col1,row2,col2,row3,col3;
		public State(int row0,int col0,int row1,int col1, int row2,int col2,int row3,int col3){
			this.row0 = row0;   this.col0 = col0;
			this.row1 = row1;   this.col1 = col1;
			this.row2 = row2;   this.col2 = col2;
			this.row3 = row3;   this.col3 = col3;
		}
	}
	
	//添加旋转算法
	public void rotateRight(){
		index++;
		State s = states[index%states.length];//始终在0-3之间
		Cell o = cells[0]; //获取轴
		
		cells[1].setRow(o.getRow() + s.row1);
		cells[1].setCol(o.getCol() + s.col1);
		cells[2].setRow(o.getRow() + s.row2);
		cells[2].setCol(o.getCol() + s.col2);
		cells[3].setRow(o.getRow() + s.row3);
		cells[3].setCol(o.getCol() + s.col3);
		
	}
	
	public void rotateLeft(){
		index--;
		State s = states[index%states.length];//始终在0-3之间
		Cell o = cells[0]; //获取轴
		
		cells[1].setRow(o.getRow() + s.row1);
		cells[1].setCol(o.getCol() + s.col1);
		cells[2].setRow(o.getRow() + s.row2);
		cells[2].setCol(o.getCol() + s.col2);
		cells[3].setRow(o.getRow() + s.row3);
		cells[3].setCol(o.getCol() + s.col3);
		
	}
	
	
	
	
	//如果需要创建对象T，只能调用RandomOne，   工厂方法
	public static Tetromino randomOne(){
		int type = (int)(Math.random() * 7);
		switch(type){
		case 0: return new T();
		case 1: return new S();
		case 2: return new Z();
		case 3: return new L();
		case 4: return new J();
		case 5: return new O();
		default: return new I();
		}
	}
	
	//增加基本算法
	public void softDrop(){
		for(Cell cell: cells){
			cell.drop();
		}
	}
	
	public void moveLeft(){
		for(Cell cell: cells){
			cell.moveLeft();
		}
	}
	
	public void moveRight(){
		for(Cell cell: cells){
			cell.moveRight();
		}
	}
	
	public String toString(){
		return Arrays.toString(cells);
	}
	

}


/**
 * 将T封装在Tetromino类内部
 * 在类的外部没有任何机会能够创建T对象
 * 在类的内部可以new T的
 * @author Administrator
 *
 */
class T extends Tetromino{
	public T(){
		//0是方块的旋转轴
		cells[0] = new Cell(0,4,Game.T);
		cells[1] = new Cell(0,3,Game.T);
		cells[2] = new Cell(0,5,Game.T);
		cells[3] = new Cell(1,4,Game.T);
		
		//初始化旋转状态数据,即每一种状态需要相对旋转轴做什么样的变化
		states = new State[4];
		states[0] =  new State(0,0,0,-1,0,1,1,0);
		states[1] = new State(0,0,-1,0,1,0,0,-1);
		states[2] = new State(0,0,0,1,0,-1,-1,0);
		states[3] = new State(0,0,1,0,-1,0,0,1);
		
		
	}
	
}

 class S extends Tetromino{
	public S(){
		//0是方块的旋转轴
				cells[0] = new Cell(0,4,Game.S);
				cells[1] = new Cell(0,5,Game.S);
				cells[2] = new Cell(1,3,Game.S);
				cells[3] = new Cell(1,4,Game.S);
				
				//初始化旋转状态数据,即每一种状态需要相对旋转轴做什么样的变化
				states = new State[2];
				states[0] =  new State(0,0,0,-1,-1,0,-1,1);
				states[1] = new State(0,0,-1,0,0,1,1,1);
				
	}
}

class Z extends Tetromino{
	public Z(){
		//0是方块的旋转轴
		cells[0] = new Cell(1,4,Game.Z);
		cells[1] = new Cell(0,3,Game.Z);
		cells[2] = new Cell(0,4,Game.Z);
		cells[3] = new Cell(1,5,Game.Z);
		
		//初始化旋转状态数据,即每一种状态需要相对旋转轴做什么样的变化
		states = new State[2];
		states[0] =  new State(0,0,-1,-1,-1,0,0,1);
		states[1] = new State(0,0,-1,1,0,1,1,0);
		
	}
}

class L extends Tetromino{
	public L(){
		//0是方块的旋转轴
				cells[0] = new Cell(0,4,Game.L);
				cells[1] = new Cell(0,3,Game.L);
				cells[2] = new Cell(0,5,Game.L);
				cells[3] = new Cell(1,3,Game.L);
				
				//初始化旋转状态数据,即每一种状态需要相对旋转轴做什么样的变化
				states = new State[4];
				states[0] =  new State(0,0,0,-1,0,1,1,-1);
				states[1] = new State(0,0,1,0,-1,0,1,1);
				states[2] = new State(0,0,0,-1,0,1,1,-1);
				states[3] = new State(0,0,-1,0,1,0,-1,-1);
	}
}

class J extends Tetromino{
	public J(){
		//0是方块的旋转轴
		cells[0] = new Cell(0,4,Game.J);
		cells[1] = new Cell(0,3,Game.J);
		cells[2] = new Cell(0,5,Game.J);
		cells[3] = new Cell(1,5,Game.J);
		
		//初始化旋转状态数据,即每一种状态需要相对旋转轴做什么样的变化
		states = new State[4];
		states[0] =  new State(0,0,0,-1,0,1,1,1);
		states[1] = new State(0,0,-1,0,1,0,1,-1);
		states[2] = new State(0,0,0,1,0,-1,-1,1);
		states[3] = new State(0,0,-1,0,1,0,1,1);
	}
}

class O extends Tetromino{
	public O(){
		//0是方块的旋转轴
		cells[0] = new Cell(0,4,Game.O);
		cells[1] = new Cell(0,5,Game.O);
		cells[2] = new Cell(1,4,Game.O);
		cells[3] = new Cell(1,5,Game.O);
		
		//初始化旋转状态数据,即每一种状态需要相对旋转轴做什么样的变化
		states = new State[1];
		states[0] =  new State(0,0,0,1,1,0,1,1);
		
	}
}

class I extends Tetromino{
	public I(){
		//0是方块的旋转轴
		cells[0] = new Cell(0,4,Game.I);
		cells[1] = new Cell(0,3,Game.I);
		cells[2] = new Cell(0,5,Game.I);
		cells[3] = new Cell(1,3,Game.I);
		
		//初始化旋转状态数据,即每一种状态需要相对旋转轴做什么样的变化
		states = new State[2];
		states[0] =  new State(0,0,0,-1,0,1,0,2);
		states[1] = new State(0,0,-1,0,1,0,2,0);
	}
}
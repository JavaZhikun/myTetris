package com.sjtu.tetris;

import java.util.Arrays;


/**
 * �ĸ񷽿�
 * @author JavaZhikun
 *
 */
public abstract class Tetromino
{
	protected Cell[] cells = new Cell[4];
	public Tetromino(){}  //�����ⲿ�̳�
	
	protected State[] states;//��ת״̬����
	protected int index = 10000;//��ת״̬���
	
	//��Tetromino������ڲ��࣬��ת״̬���ݽṹ
	protected class State{
		int row0,col0,row1,col1,row2,col2,row3,col3;
		public State(int row0,int col0,int row1,int col1, int row2,int col2,int row3,int col3){
			this.row0 = row0;   this.col0 = col0;
			this.row1 = row1;   this.col1 = col1;
			this.row2 = row2;   this.col2 = col2;
			this.row3 = row3;   this.col3 = col3;
		}
	}
	
	//�����ת�㷨
	public void rotateRight(){
		index++;
		State s = states[index%states.length];//ʼ����0-3֮��
		Cell o = cells[0]; //��ȡ��
		
		cells[1].setRow(o.getRow() + s.row1);
		cells[1].setCol(o.getCol() + s.col1);
		cells[2].setRow(o.getRow() + s.row2);
		cells[2].setCol(o.getCol() + s.col2);
		cells[3].setRow(o.getRow() + s.row3);
		cells[3].setCol(o.getCol() + s.col3);
		
	}
	
	public void rotateLeft(){
		index--;
		State s = states[index%states.length];//ʼ����0-3֮��
		Cell o = cells[0]; //��ȡ��
		
		cells[1].setRow(o.getRow() + s.row1);
		cells[1].setCol(o.getCol() + s.col1);
		cells[2].setRow(o.getRow() + s.row2);
		cells[2].setCol(o.getCol() + s.col2);
		cells[3].setRow(o.getRow() + s.row3);
		cells[3].setCol(o.getCol() + s.col3);
		
	}
	
	
	
	
	//�����Ҫ��������T��ֻ�ܵ���RandomOne��   ��������
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
	
	//���ӻ����㷨
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
 * ��T��װ��Tetromino���ڲ�
 * ������ⲿû���κλ����ܹ�����T����
 * ������ڲ�����new T��
 * @author Administrator
 *
 */
class T extends Tetromino{
	public T(){
		//0�Ƿ������ת��
		cells[0] = new Cell(0,4,Game.T);
		cells[1] = new Cell(0,3,Game.T);
		cells[2] = new Cell(0,5,Game.T);
		cells[3] = new Cell(1,4,Game.T);
		
		//��ʼ����ת״̬����,��ÿһ��״̬��Ҫ�����ת����ʲô���ı仯
		states = new State[4];
		states[0] =  new State(0,0,0,-1,0,1,1,0);
		states[1] = new State(0,0,-1,0,1,0,0,-1);
		states[2] = new State(0,0,0,1,0,-1,-1,0);
		states[3] = new State(0,0,1,0,-1,0,0,1);
		
		
	}
	
}

 class S extends Tetromino{
	public S(){
		//0�Ƿ������ת��
				cells[0] = new Cell(0,4,Game.S);
				cells[1] = new Cell(0,5,Game.S);
				cells[2] = new Cell(1,3,Game.S);
				cells[3] = new Cell(1,4,Game.S);
				
				//��ʼ����ת״̬����,��ÿһ��״̬��Ҫ�����ת����ʲô���ı仯
				states = new State[2];
				states[0] =  new State(0,0,0,-1,-1,0,-1,1);
				states[1] = new State(0,0,-1,0,0,1,1,1);
				
	}
}

class Z extends Tetromino{
	public Z(){
		//0�Ƿ������ת��
		cells[0] = new Cell(1,4,Game.Z);
		cells[1] = new Cell(0,3,Game.Z);
		cells[2] = new Cell(0,4,Game.Z);
		cells[3] = new Cell(1,5,Game.Z);
		
		//��ʼ����ת״̬����,��ÿһ��״̬��Ҫ�����ת����ʲô���ı仯
		states = new State[2];
		states[0] =  new State(0,0,-1,-1,-1,0,0,1);
		states[1] = new State(0,0,-1,1,0,1,1,0);
		
	}
}

class L extends Tetromino{
	public L(){
		//0�Ƿ������ת��
				cells[0] = new Cell(0,4,Game.L);
				cells[1] = new Cell(0,3,Game.L);
				cells[2] = new Cell(0,5,Game.L);
				cells[3] = new Cell(1,3,Game.L);
				
				//��ʼ����ת״̬����,��ÿһ��״̬��Ҫ�����ת����ʲô���ı仯
				states = new State[4];
				states[0] =  new State(0,0,0,-1,0,1,1,-1);
				states[1] = new State(0,0,1,0,-1,0,1,1);
				states[2] = new State(0,0,0,-1,0,1,1,-1);
				states[3] = new State(0,0,-1,0,1,0,-1,-1);
	}
}

class J extends Tetromino{
	public J(){
		//0�Ƿ������ת��
		cells[0] = new Cell(0,4,Game.J);
		cells[1] = new Cell(0,3,Game.J);
		cells[2] = new Cell(0,5,Game.J);
		cells[3] = new Cell(1,5,Game.J);
		
		//��ʼ����ת״̬����,��ÿһ��״̬��Ҫ�����ת����ʲô���ı仯
		states = new State[4];
		states[0] =  new State(0,0,0,-1,0,1,1,1);
		states[1] = new State(0,0,-1,0,1,0,1,-1);
		states[2] = new State(0,0,0,1,0,-1,-1,1);
		states[3] = new State(0,0,-1,0,1,0,1,1);
	}
}

class O extends Tetromino{
	public O(){
		//0�Ƿ������ת��
		cells[0] = new Cell(0,4,Game.O);
		cells[1] = new Cell(0,5,Game.O);
		cells[2] = new Cell(1,4,Game.O);
		cells[3] = new Cell(1,5,Game.O);
		
		//��ʼ����ת״̬����,��ÿһ��״̬��Ҫ�����ת����ʲô���ı仯
		states = new State[1];
		states[0] =  new State(0,0,0,1,1,0,1,1);
		
	}
}

class I extends Tetromino{
	public I(){
		//0�Ƿ������ת��
		cells[0] = new Cell(0,4,Game.I);
		cells[1] = new Cell(0,3,Game.I);
		cells[2] = new Cell(0,5,Game.I);
		cells[3] = new Cell(1,3,Game.I);
		
		//��ʼ����ת״̬����,��ÿһ��״̬��Ҫ�����ת����ʲô���ı仯
		states = new State[2];
		states[0] =  new State(0,0,0,-1,0,1,0,2);
		states[1] = new State(0,0,-1,0,1,0,2,0);
	}
}
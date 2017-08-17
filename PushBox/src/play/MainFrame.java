package play;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;

import map.MapFactory;
import util.MapEnum;

public class MainFrame extends JFrame implements ActionListener,KeyListener{

	private int grade = 0;
	private byte[][] map = null;
	private int manX = 0;
	private int manY = 0;
	private int offsetX = 30;
	private int offsetY = 30;
	
	public MainFrame(){
		 super("pushbox");
		 setSize(640, 640);
		 setLocation(30, 30);
		 setBackground(Color.black);
		 setResizable(false);
		 setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		 setVisible(true);
		 initMap();
		 getManPosition();
		 this.addKeyListener(this);
	}
	public boolean isManOnEnd(){
		if (map[manX][manY] == MapEnum.MANLEFTONEND.getKey() 
				|| map[manX][manY] == MapEnum.MANRIGHTONEND.getKey()
				|| map[manX][manY] == MapEnum.MANUPONEND.getKey()
				|| map[manX][manY] == MapEnum.MANDOWNONEND.getKey()){
			map[manX][manY] = MapEnum.BOXONEND.getKey();
			return true;
		} else { 
			return false;
		}
	}
	/*
	 * 1.manǰ��grass
	 * 	���߻���
	 * 2.manǰ��box
	 * 	(1)boxǰ��grass
	 * 	man��Ϊgrass��man��boxǰ��1
	 * 	(2)boxǰ��wall
	 * 	(3)boxǰ��boxonend
	 * 	man��λgrass��manǰ��1��boxonend��Ϊend
	 * 3.manǰ��boxonend
	 *  man��Ϊmanonend
	 * 4.manǰ��wall
	 * 5.manǰ��end
	 * (1)endǰ��grass
	 * grass��Ϊbox��man��Ϊgrass��end��Ϊmanonend
	 * (2)endǰ��boxonend
	 */
	private void moveDown(){
		if (map[manX][manY+1] == MapEnum.GRASS.getKey()){
			if(!isManOnEnd())
			map[manX][manY] = MapEnum.GRASS.getKey();
			map[manX][manY+1] = MapEnum.MANDOWN.getKey();
			manY++;
		} else if (map[manX][manY+1] == MapEnum.BOX.getKey()){
			 if (map[0].length>manY+2 && map[manX][manY+2]==MapEnum.GRASS.getKey()){
				 map[manX][manY+2] = MapEnum.BOX.getKey();
				 map[manX][manY+1] = MapEnum.MANDOWN.getKey();
				 if(!isManOnEnd())
				 map[manX][manY] = MapEnum.GRASS.getKey();
				 manY++;
			 } else if (map[0].length>manY+2 && map[manX][manY+2]==MapEnum.BOXONEND.getKey()){
				 map[manX][manY+2] = MapEnum.END.getKey();
				 map[manX][manY+1] = MapEnum.MANDOWN.getKey();
				 if(!isManOnEnd())
					 map[manX][manY] = MapEnum.GRASS.getKey();
					 manY++;
			 } else{
				 return;
			 }
		} else if (map[manX][manY+1] == MapEnum.BOXONEND.getKey()){
			map[manX][manY+1] = MapEnum.MANDOWNONEND.getKey();
			if(!isManOnEnd())
				 map[manX][manY] = MapEnum.GRASS.getKey();
				 manY++;
		} else if (map[manX][manY+1] == MapEnum.END.getKey()){
			if (manY+2<map[0].length && map[manX][manY+2] == MapEnum.GRASS.getKey()){
				map[manX][manY+2] = MapEnum.BOX.getKey();
				map[manX][manY+1] = MapEnum.MANDOWNONEND.getKey();
				if(!isManOnEnd())
					 map[manX][manY] = MapEnum.GRASS.getKey();
					 manY++;
			} else if (manY+2<map[0].length && map[manX][manY+2] == MapEnum.BOXONEND.getKey()){
				map[manX][manY+2] = MapEnum.END.getKey();
				map[manX][manY+1] = MapEnum.BOXONEND.getKey();
				if(!isManOnEnd())
					 map[manX][manY] = MapEnum.GRASS.getKey();
					 manY++;
			} else {
				return;
			}
		}
	}
	private void moveRight(){
		if (map[manX+1][manY] == MapEnum.GRASS.getKey()){
			if(!isManOnEnd())
			map[manX][manY] = MapEnum.GRASS.getKey();
			map[manX+1][manY] = MapEnum.MANRIGHT.getKey();
			manX++;
		} else if (map[manX+1][manY] == MapEnum.BOX.getKey()){
			 if (map[0].length>manX+2 && map[manX+2][manY]==MapEnum.GRASS.getKey()){
				 map[manX+2][manY] = MapEnum.BOX.getKey();
				 map[manX+1][manY] = MapEnum.MANRIGHT.getKey();
				 if(!isManOnEnd())
				 map[manX][manY] = MapEnum.GRASS.getKey();
				 manX++;
			 } else if (map[0].length>manX+2 && map[manX+2][manY]==MapEnum.BOXONEND.getKey()){
				 map[manX+2][manY] = MapEnum.END.getKey();
				 map[manX+1][manY] = MapEnum.MANRIGHT.getKey();
				 if(!isManOnEnd())
					 map[manX][manY] = MapEnum.GRASS.getKey();
					 manX++;
			 } else{
				 return;
			 }
		} else if (map[manX+1][manY] == MapEnum.BOXONEND.getKey()){
			map[manX+1][manY] = MapEnum.MANRIGHTONEND.getKey();
			if(!isManOnEnd())
				 map[manX][manY] = MapEnum.GRASS.getKey();
				 manX++;
		} else if (map[manX+1][manY] == MapEnum.END.getKey()){
			if (manX+2<map[0].length && map[manX+2][manY] == MapEnum.GRASS.getKey()){
				map[manX+2][manY] = MapEnum.BOX.getKey();
				map[manX+1][manY] = MapEnum.MANRIGHTONEND.getKey();
				if(!isManOnEnd())
					 map[manX][manY] = MapEnum.GRASS.getKey();
					 manX++;
			} else if (manX+2<map[0].length && map[manX+2][manY] == MapEnum.BOXONEND.getKey()){
				map[manX+2][manY] = MapEnum.END.getKey();
				map[manX+1][manY] = MapEnum.BOXONEND.getKey();
				if(!isManOnEnd())
					 map[manX][manY] = MapEnum.GRASS.getKey();
					 manX++;
			} else {
				return;
			}
		}
	}
	private void moveLeft(){
		if (map[manX-1][manY] == MapEnum.GRASS.getKey()){
			if(!isManOnEnd())
			map[manX][manY] = MapEnum.GRASS.getKey();
			map[manX-1][manY] = MapEnum.MANLEFT.getKey();
			manX--;
		} else if (map[manX-1][manY] == MapEnum.BOX.getKey()){
			 if (map[0].length>manX-2 && map[manX-2][manY]==MapEnum.GRASS.getKey()){
				 map[manX-2][manY] = MapEnum.BOX.getKey();
				 map[manX-1][manY] = MapEnum.MANLEFT.getKey();
				 if(!isManOnEnd())
				 map[manX][manY] = MapEnum.GRASS.getKey();
				 manX--;
			 } else if (map[0].length>manX-2 && map[manX-2][manY]==MapEnum.BOXONEND.getKey()){
				 map[manX-2][manY] = MapEnum.END.getKey();
				 map[manX-1][manY] = MapEnum.MANLEFT.getKey();
				 if(!isManOnEnd())
					 map[manX][manY] = MapEnum.GRASS.getKey();
					 manX--;
			 } else{
				 return;
			 }
		} else if (map[manX-1][manY] == MapEnum.BOXONEND.getKey()){
			map[manX-1][manY] = MapEnum.MANLEFTONEND.getKey();
			if(!isManOnEnd())
				 map[manX][manY] = MapEnum.GRASS.getKey();
				 manX--;
		} else if (map[manX-1][manY] == MapEnum.END.getKey()){
			if (manX-2<map[0].length && map[manX-2][manY] == MapEnum.GRASS.getKey()){
				map[manX-2][manY] = MapEnum.BOX.getKey();
				map[manX-1][manY] = MapEnum.MANLEFTONEND.getKey();
				if(!isManOnEnd())
					 map[manX][manY] = MapEnum.GRASS.getKey();
					 manX--;
			} else if (manX-2<map[0].length && map[manX-2][manY] == MapEnum.BOXONEND.getKey()){
				map[manX-2][manY] = MapEnum.END.getKey();
				map[manX-1][manY] = MapEnum.BOXONEND.getKey();
				if(!isManOnEnd())
					 map[manX][manY] = MapEnum.GRASS.getKey();
					 manX--;
			} else {
				return;
			}
		}
	}
	private void moveUP(){
		if (map[manX][manY-1] == MapEnum.GRASS.getKey()){
			if(!isManOnEnd())
			map[manX][manY] = MapEnum.GRASS.getKey();
			map[manX][manY-1] = MapEnum.MANUP.getKey();
			manY--;
		} else if (map[manX][manY-1] == MapEnum.BOX.getKey()){
			 if (map[0].length>manY-2 && map[manX][manY-2]==MapEnum.GRASS.getKey()){
				 map[manX][manY-2] = MapEnum.BOX.getKey();
				 map[manX][manY-1] = MapEnum.MANUP.getKey();
				 if(!isManOnEnd())
				 map[manX][manY] = MapEnum.GRASS.getKey();
				 manY--;
			 } else if (map[0].length>manY-2 && map[manX][manY-2]==MapEnum.BOXONEND.getKey()){
				 map[manX][manY-2] = MapEnum.END.getKey();
				 map[manX][manY-1] = MapEnum.MANUP.getKey();
				 if(!isManOnEnd())
					 map[manX][manY] = MapEnum.GRASS.getKey();
					 manY--;
			 } else{
				 return;
			 }
		} else if (map[manX][manY-1] == MapEnum.BOXONEND.getKey()){
			map[manX][manY-1] = MapEnum.MANUPONEND.getKey();
			if(!isManOnEnd())
				 map[manX][manY] = MapEnum.GRASS.getKey();
				 manY--;
		} else if (map[manX][manY-1] == MapEnum.END.getKey()){
			if (manY-2<map[0].length && map[manX][manY-2] == MapEnum.GRASS.getKey()){
				map[manX][manY-2] = MapEnum.BOX.getKey();
				map[manX][manY-1] = MapEnum.MANUPONEND.getKey();
				if(!isManOnEnd())
					 map[manX][manY] = MapEnum.GRASS.getKey();
					 manY--;
			} else if (manY-2<map[0].length && map[manX][manY-2] == MapEnum.BOXONEND.getKey()){
				map[manX][manY-2] = MapEnum.END.getKey();
				map[manX][manY-1] = MapEnum.BOXONEND.getKey();
				if(!isManOnEnd())
					 map[manX][manY] = MapEnum.GRASS.getKey();
					 manY--;
			} else {
				return;
			}
		}
	}
	private void getManPosition() {
		for (int i = 0; i < map.length; i++){
			for (int j = 0; j < map[0].length; j++){
				int pst = map[i][j];
				if (pst==MapEnum.MANDOWN.getKey() || pst==MapEnum.MANLEFT.getKey() 
						|| pst==MapEnum.MANRIGHT.getKey() || pst==MapEnum.MANUP.getKey()){
					manX = i;
					manY = j;
					break;
				}
				
			}
			
		}
	}

	private void initMap() {
		this.map = MapFactory.getMap(grade);
	}

	
	public void paint(Graphics g){
		for (int i = 0; i < map.length; i++){
			for (int j = 0; j < map[0].length; j++){
				if (map[i][j]!=0){
					Image image = MapEnum.getImage(map[i][j]);
					g.drawImage(image, i*64+offsetX,j*64+offsetY, this);
				}
			}
		}
	}
	@Override
	public void keyTyped(KeyEvent e) {
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_RIGHT){
			moveRight();
		} else if (e.getKeyCode() == KeyEvent.VK_LEFT){
			moveLeft();
		} else if (e.getKeyCode() == KeyEvent.VK_UP){
			moveUP();
		} else if (e.getKeyCode() == KeyEvent.VK_DOWN){
			moveDown();
		}
		
		repaint();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
	}
	
	public static void main(String[] args){
		new MainFrame();
	}

}

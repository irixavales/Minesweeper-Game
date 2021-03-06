import java.awt.Color;
import java.awt.Component;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class MyMouseAdapter extends MouseAdapter {


	public void mousePressed(MouseEvent e) {
		Component c = e.getComponent();
		while (!(c instanceof JFrame)) {
			c = c.getParent();
			if (c == null) {
				return;
			}
		}
		JFrame myFrame = (JFrame) c;
		MyPanel myPanel = (MyPanel) myFrame.getContentPane().getComponent(0);
		Insets myInsets = myFrame.getInsets();
		int x1 = myInsets.left;
		int y1 = myInsets.top;
		e.translatePoint(-x1, -y1);
		int x = e.getX();
		int y = e.getY();
		myPanel.x = x;
		myPanel.y = y;
		myPanel.mouseDownGridX = myPanel.getGridX(x, y);
		myPanel.mouseDownGridY = myPanel.getGridY(x, y);
		myPanel.repaint();
	}



	public void mouseReleased(MouseEvent e) {

		Component c = e.getComponent();
		while (!(c instanceof JFrame)) {
			c = c.getParent();
			if (c == null) {
				return;
			}
		}
		JFrame myFrame = (JFrame)c;
		MyPanel myPanel = (MyPanel) myFrame.getContentPane().getComponent(0);  //Can also loop among components to find MyPanel
		Insets myInsets = myFrame.getInsets();
		int x1 = myInsets.left;
		int y1 = myInsets.top;
		e.translatePoint(-x1, -y1);
		int x = e.getX();
		int y = e.getY();
		myPanel.x = x;
		myPanel.y = y;
		int gridX = myPanel.getGridX(x, y);
		int gridY = myPanel.getGridY(x, y);


		switch (e.getButton()) {

		case 1:		//Left mouse button
			if ((myPanel.mouseDownGridX == -1) || (myPanel.mouseDownGridY == -1)) {
				//Had pressed outside
				//Do nothing
			} 
			else {
				if ((gridX == -1) || (gridY == -1)) {
					//Is releasing outside
					//Do nothing
				} 
				else {
					if ((myPanel.mouseDownGridX != gridX) || (myPanel.mouseDownGridY != gridY)) {
						//Released the mouse button on a different cell where it was pressed
						//Do nothing
					} 
					else {
						//Released the mouse button on the same cell where it was pressed
						if(!MyPanel.colorArray[myPanel.mouseDownGridX][myPanel.mouseDownGridY].equals(Color.RED)) { //Repaints cell light grey upon left click if the cell is not red.
							boolean isMine = MyPanel.isMine(gridX, gridY);
							if (!isMine) {//Released on cell without mine
								MyPanel.countSurroundingMines(gridX, gridY);
							}
							else {	//Released on a mine
								myPanel.lostGame();								
							}
						}
					}	
				}

			}
			myPanel.repaint();
			if (MyPanel.wonGame()) {
				JOptionPane.showMessageDialog(null, "You Won!", "MINESWEEPER", JOptionPane.INFORMATION_MESSAGE);
				System.exit(0);
			}
			else if (MyPanel.lostGame) {
				JOptionPane.showMessageDialog(null, "You Lost", "MINESWEEPER", JOptionPane.INFORMATION_MESSAGE);
				System.exit(0);
			}

			

			break;

		case 3:		//Right mouse button

			if ((myPanel.mouseDownGridX == -1) || (myPanel.mouseDownGridY == -1)) {
				//Had pressed outside
				//Do nothing
			} 
			else {
				if ((gridX == -1) || (gridY == -1)) {
					//Is releasing outside
					//Do nothing
				} 
				if ((myPanel.mouseDownGridX != gridX) || (myPanel.mouseDownGridY != gridY)) {
					//Released the mouse button on a different cell where it was pressed
					//Do nothing

				}
				else {

					//Released the mouse button on the same cell where it was pressed
					Color newColor = null;	
					if(MyPanel.colorArray[gridX][gridY].equals(Color.WHITE)){ //Changes cell to red upon right click while the original color was white
						newColor = Color.RED;
					}

					else if (MyPanel.colorArray[gridX][gridY].equals(Color.RED)) { //Changes cell to white upon right click while the original color was red
						newColor = Color.WHITE;
					}
					MyPanel.colorArray[gridX][gridY] = newColor;
				}

				if(MyPanel.lostGame==true){
					JOptionPane.showMessageDialog(null, "You Loose", "MINESWEEPER", JOptionPane.INFORMATION_MESSAGE);
					System.exit(0);
				}
				
				if(MyPanel.wonGame()== true){
					JOptionPane.showMessageDialog(null, "You Won", "MINESWEEPER", JOptionPane.INFORMATION_MESSAGE);
					System.exit(0);
				}
					
				myPanel.repaint();
				break;

	}
	


		default:    //Some other button (2 = Middle mouse button, etc.)
			//Do nothing
			break;
		}
	}
	//	}
}


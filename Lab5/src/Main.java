import javax.swing.JFrame;

public class Main {
	public static void main(String[] args) {
		JFrame myFrame = new JFrame("MINESWEEPER");
		myFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		myFrame.setLocation(400, 150);
		myFrame.setSize(320, 375);

		MyPanel myPanel = new MyPanel();
		myFrame.add(myPanel);
			
//		if (MyPanel.lostGame == false && MyPanel.wonGame() == false) {
			MyMouseAdapter myMouseAdapter = new MyMouseAdapter();
			myFrame.addMouseListener(myMouseAdapter);
//		}
		
		
		myFrame.setVisible(true);
	}
}
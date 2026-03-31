package serpiente;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class GameFrame extends JFrame {

    GamePanel panel;

    public GameFrame() {
        panel = new GamePanel();

        add(panel);
        setTitle("Snake");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        
        
        addWindowListener(new WindowAdapter() {
        	
        	public void windowActivated(WindowEvent e) {
        		if(!panel.timer.isRunning()) {
        			panel.pauseGame();
        		}
        	}
        	
        	public void windowDeactivated(WindowEvent e) {
        		if(panel.timer.isRunning()) {
        		panel.pauseGame();
        		}
        	}
        
        });
    }
}

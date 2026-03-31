package serpiente;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

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
        setMenu();
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
    
    public void setMenu() {
		
		JMenuBar mb = new JMenuBar();
		setJMenuBar(mb);
		
		JMenuItem restart = new JMenu("Reiniciar");
		mb.add(restart);
		
		JMenuItem pause = new JMenu("Pausa");
		mb.add(pause);
		
		JMenu difficulty = new JMenu("Dificultad");
		mb.add(difficulty);
		
		JMenuItem easy = new JMenuItem("Fácil");
		difficulty.add(easy);
		
		JMenuItem medium = new JMenuItem("Medio");
		difficulty.add(medium);
		
		JMenuItem hard = new JMenuItem("Dificil");
		difficulty.add(hard);
	}
}

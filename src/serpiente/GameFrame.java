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

        JMenu gameMenu = new JMenu("Juego");
        mb.add(gameMenu);

        JMenuItem restart = new JMenuItem("Reiniciar");
        gameMenu.add(restart);

        JMenuItem pause = new JMenuItem("Pausa");
        gameMenu.add(pause);

        JMenu difficulty = new JMenu("Dificultad");
        mb.add(difficulty);

        JMenuItem easy = new JMenuItem("Fácil");
        JMenuItem medium = new JMenuItem("Medio");
        JMenuItem hard = new JMenuItem("Difícil");

        difficulty.add(easy);
        difficulty.add(medium);
        difficulty.add(hard);


        restart.addActionListener(e -> panel.restart());

        pause.addActionListener(e -> panel.pauseGame());

        easy.addActionListener(e -> panel.setSpeed(150));

        medium.addActionListener(e -> panel.setSpeed(100));

        hard.addActionListener(e -> panel.setSpeed(50));
    }
    
}
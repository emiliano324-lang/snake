package serpiente;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.RenderingHints.Key;
import java.awt.color.ColorSpace;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.LinkedList;

import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

public class GamePanel extends JPanel {
	
	Point food;
	boolean isDead = false;
	// Dimensiones del área de juego
	static final int WIDTH = 600;
	static final int HEIGHT = 600;

	// Tamaño de cada celda del tablero (grid)
	static final int UNIT_SIZE = 25;

	// Timer que controla el ciclo del juego
	Timer timer;

	// Coordenadas de la cabeza de la serpiente
	int snakeX;
	int snakeY;

	// Dirección actual de movimiento de la serpiente
	// U = Up, D = Down, L = Left, R = Right
	char direction;

	// Lista enlazada que almacena todas las posiciones del cuerpo de la serpiente
	LinkedList<Point> snakeBody;
	
	// Color de la serpiente
	Color headColor = Color.YELLOW;
	Color bodyColor = Color.GREEN;
	
	// Color de comida para diferentes frutas
	Color foodColor;
	
	// Etiquetas
	JLabel lblScore;
	int score = 0;
	
	JLabel lblGameOver;
	JLabel lblContinue;
	JLabel lblPause;
	

	public GamePanel() {
		
		
			
			// Posición inicial de la cabeza de la serpiente
			snakeX = 250;
			snakeY = 100;
	
			// Dirección inicial de movimiento
			direction = 'R';
	
			// Crear la lista que almacenará el cuerpo de la serpiente
			snakeBody = new LinkedList<Point>();
	
			// Agregar los primeros segmentos de la serpiente
			snakeBody.add(new Point(250, 100));
			snakeBody.add(new Point(225, 100));
			snakeBody.add(new Point(200, 100));
	
			food = new Point(createRandomCoord(), createRandomCoord());
			foodColor = randomFoodColor();
			
			// Configurar tamaño del panel
			this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
	
			this.setLayout(null);
			
			lblScore = new JLabel("Puntuación: 0");

			lblScore.setFont(new Font("Verdana", Font.BOLD, 12));
			lblScore.setForeground(Color.WHITE);
			lblScore.setBounds(WIDTH - 150, 10, 100, 30);
			
			this.add(lblScore);
			
			lblGameOver = new JLabel("GAME OVER");
			lblGameOver.setHorizontalAlignment(JLabel.CENTER);
			lblGameOver.setBounds(
					(WIDTH - 300) / 2,
					(HEIGHT - 100) / 2,
					300,
					100);
			
			lblGameOver.setFont(new Font("Verdana", Font.BOLD, 40));
			lblGameOver.setForeground(Color.WHITE);
			lblGameOver.setVisible(false);
			
			this.add(lblGameOver);

			lblContinue = new JLabel("Presione cualquier tecla para continuar...");
			lblContinue.setFont(new Font("Verdana", Font.BOLD, 12));
			lblContinue.setForeground(Color.WHITE);
			lblContinue.setHorizontalAlignment(JLabel.CENTER);
			lblContinue.setBounds(
				    (WIDTH - 450) / 2,
				    (HEIGHT / 2) + 30,
				    450,
				    30
				);
			lblContinue.setVisible(false);
			
			this.add(lblContinue);
			
			lblPause = new JLabel("PAUSA");
			lblPause.setHorizontalAlignment(JLabel.CENTER);
			lblPause.setBounds(
					(WIDTH - 300) / 2,
					(HEIGHT - 100) / 2,
					300,
					100);
			
			lblPause.setFont(new Font("Verdana", Font.BOLD, 40));
			lblPause.setForeground(Color.WHITE);
			lblPause.setVisible(false);
			
			this.add(lblPause);
			
			// Color de fondo
			this.setBackground(new Color(0, 80, 40));
	
			// Permitir que el panel reciba eventos de teclado
			this.setFocusable(true);
	
			// Evita que las teclas de navegación (como el TAB) cambien el foco entre componentes
			setFocusTraversalKeysEnabled(false);
	
			// Solicitar el foco cuando el panel ya esté visible
			SwingUtilities.invokeLater(() -> requestFocusInWindow());
	
			// Listener para detectar las teclas presionadas
			this.addKeyListener(new KeyAdapter() {
	
				@Override
				public void keyPressed(KeyEvent e) {
					
					
	
					// Cambiar la dirección dependiendo de la tecla presionada
					switch (e.getKeyCode()) {
					
					case KeyEvent.VK_LEFT:
						//System.out.println("Left");
						if(direction != 'R') {
							direction = 'L';
						}
						
						break;
	
					case KeyEvent.VK_RIGHT:
						//System.out.println("Right");
						if(direction != 'L') {
							direction = 'R';
						}
						break;
	
					case KeyEvent.VK_UP:
						//System.out.println("Up");
						if(direction != 'D') {
							direction = 'U';
						}
						break;
	
					case KeyEvent.VK_DOWN:
						//System.out.println("Down");
						if(direction != 'U') {
							direction = 'D';
						}
						
						break;
						
					case KeyEvent.VK_P:
						pauseGame();
						break;
					case KeyEvent.VK_R:
						if(isDead) {
							restart();
							return;
						}
					break;
					}
				}
				
			});
			
			// Timer que ejecuta el ciclo del juego cada 150 ms
			timer = new Timer(150, e -> {
				
				move();     // Actualiza la posición de la serpiente
				repaint();  // Redibuja el panel (se ejecuta paintComponent)
				
				if (isInFood(food.x, food.y)) {
					
					bodyColor = foodColor;
					headColor = complementaryColor(bodyColor);

					generateFood();
				    //food.setLocation(createRandomCoord(), createRandomCoord());    
				    snakeBody.add(new Point());
				    
				    score++;
				    lblScore.setText("Puntuación: " + score);
				    
				    timer.setDelay(timer.getDelay() - 1);
				    return;
				}	
				if(isDead()){
					timer.stop();
					lblGameOver.setVisible(true);
					lblContinue.setVisible(true);
					isDead = true;
				}
				
			});
	
			timer.start(); //Inicia el timer, para detenerlo se puede usar timer.stop();
	}
	// Método encargado de dibujar los elementos del juego
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		// Recorrer todas las partes de la serpiente
		for (int i = 0; i < snakeBody.size(); i++) {

			// La cabeza se dibuja de color amarillo
			if (i == 0) {
				g.setColor(headColor);
			} 
			// El resto del cuerpo se dibuja de color verde
			else {
				g.setColor(bodyColor);
			}

			// Dibujar cada parte de la serpiente
			g.fillRoundRect(
				snakeBody.get(i).x,
				snakeBody.get(i).y,
				UNIT_SIZE,
				UNIT_SIZE,
				5,
				5
			);
		}
		g.setColor(foodColor);
		g.fillOval(food.x, food.y, UNIT_SIZE, UNIT_SIZE);
	}
	
	public boolean isInFood(int x, int y) {
		Point head = snakeBody.getFirst(); 

		if (head.x == x && head.y == y) {
			return true;
		}
		
		return false;
	}
	
	public void generateFood() {
		do {
			food.setLocation(createRandomCoord(), createRandomCoord());
		} while(isInSnake());
		
		foodColor = randomFoodColor();
	}
	
	public Color randomFoodColor() {
	    Color[] colors = {
	        Color.BLUE,
	        Color.GREEN,    
	        Color.YELLOW,  
	        Color.ORANGE,
	        Color.RED,
	        Color.MAGENTA
	    };
	    
	    int index = (int)(Math.random() * colors.length);
	    return colors[index];
	}
	
	public Color complementaryColor(Color color) {
		return new Color(
				255 - color.getRed(),
		        255 - color.getGreen(),
		        255 - color.getBlue()
				);
	}
	
	public boolean isDead() {
	    Point head = snakeBody.getFirst();
	    for (int i = 1; i < snakeBody.size(); i++) {
	        Point cuerpo = snakeBody.get(i);
	        if (head.x == cuerpo.x && head.y == cuerpo.y) {
	            return true;
	        }
	    }
	    
	    if (snakeBody.getFirst().getX() >= WIDTH ||
	    		snakeBody.getFirst().getY() >= HEIGHT ||
	    		snakeBody.getFirst().getX() < 0 ||
	    		snakeBody.getFirst().getY() < 0) {
	    		return true;
	    }
	    	
	    return false;
	}
	
	public boolean isInSnake() {
		for (Point point : snakeBody) {
			if(food.getX() == point.getX() && food.getY() == point.getY()) {
				return true;
			}
		}
		return false;
	}
	
	public int createRandomCoord(){
		
		boolean exit = false;
		int random = 25;
		while(!exit) {
			random = (int)(Math.random() * (WIDTH / UNIT_SIZE)) * UNIT_SIZE;
			for (Point point : snakeBody) {
				if(point.x == random || point.y == random) {
					random = (int)(Math.random() * (WIDTH / UNIT_SIZE)) * UNIT_SIZE;
				}else {
					exit = true;
					break;
				}
				
			}
		}
		return random;
		
	}

	// Método que actualiza la posición de la serpiente
	public void move() {

		// Cambiar la posición de la cabeza dependiendo de la dirección
		switch (direction) {
		case 'U':
			snakeY -= UNIT_SIZE;
			break;

		case 'D':
			snakeY += UNIT_SIZE;
			break;

		case 'L':
			snakeX -= UNIT_SIZE;
			break;

		case 'R':
			snakeX += UNIT_SIZE;
			break;
		}

		// Agregar una nueva cabeza en la posición actual
		snakeBody.addFirst(new Point(snakeX, snakeY));

		// Eliminar el último elemento para mantener el mismo tamaño
		snakeBody.removeLast();
	}
	
	public void pauseGame() {
		if(timer.isRunning()) {
			timer.stop();
			lblPause.setVisible(true);
		}else {
			timer.start();
			lblPause.setVisible(false);
		}
	}
	
	
	
	public void setSpeed(int speed) {
		
		timer.setDelay(timer.getDelay() - speed);
		
	}
	
	
	public void restart() {
		
		snakeX = 250;
		snakeY = 100;
		
		bodyColor = Color.GREEN;
		
		direction = 'R';
		
		snakeBody.clear();
	    snakeBody.add(new Point(250, 100));
	    snakeBody.add(new Point(225, 100));
	    snakeBody.add(new Point(200, 100));
	    
	    generateFood();
	    
	    score = 0;
	    lblScore.setText("Puntuación: " + score);
	
	    lblContinue.setVisible(false);
	    lblGameOver.setVisible(false);
	
	    isDead = false;
	    
	    timer.setDelay(150);
	    timer.start();
	    
	    repaint();
	}
	
	
	
}
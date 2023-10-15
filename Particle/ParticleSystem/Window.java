import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;
import java.util.Random;
 
import javax.swing.JFrame;
 
public class Window extends JFrame {
 
    private ArrayList<Particle> particles = new ArrayList<Particle>(500);
 
    private int x = 0;
    private int y = 0;
    private BufferStrategy bufferstrat = null;
    private Canvas render;
    private String type = "";
    private Emitter emitter;
 
    private Random rng = new Random(); //used to generate random numbers
    private Color c; 
    private boolean running = true;
    private int screen_height;
    private int screen_width;
    private boolean game_mode = false;
 
    public static void main(String[] args)
    {
        Window window = new Window(450, 280, "Particles: ");
        window.pollInput();
        window.loop();
    }
 
    public Window( int width, int height, String title){
        super();
        setTitle(title);
        setIgnoreRepaint(true);
        setResizable(true);

        this.screen_height = height;
        this.screen_width = width;

        render = new Canvas();
        render.setIgnoreRepaint(true);
        int nHeight = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        int nWidth = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        nHeight /= 2;
        nWidth /= 2;
 
        setBounds(nWidth-(width/2), nHeight-(height/2), width, height);
        render.setBounds(nWidth-(width/2), nHeight-(height/2), width, height);
 
        add(render);
        pack();
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 
        render.createBufferStrategy(2);
        bufferstrat = render.getBufferStrategy();
    }
 
    public void pollInput()
    {
        render.addMouseListener(new MouseListener(){
 
            public void mouseClicked(MouseEvent e) {
                if (!game_mode){
                    c = new Color(rng.nextFloat(), rng.nextFloat(),rng.nextFloat()); // Random particle color when click
                    addParticle();addParticle();addParticle();
                    addParticle();addParticle();addParticle();
                }else{
                    for(int i = 0; i <= particles.size() - 1;i++){
                        Particle targetHit = particles.get(i);
                        if (targetHit.getLoc().x == x && targetHit.getLoc().y == y){
                            particles.remove(i);
                        }
                    }
                }
                
            }
            
            public void addParticle(){
                Particle p = new Particle(x,y,0,0,0,0,c, type);
                p.setVel(random(7),random(7));
                p.setAcc(random(.02),random(.02));
                p.setLife(randomPlus(150)+150);
                p.setSize(randomPlus(25)+25, randomPlus(25)+25);
                p.setMaxSize(50,50);
                p.setGrowth(random(2), random(2));
                p.setUltSize(true);
                particles.add(p);
            }
 
            public void mouseEntered(MouseEvent e) {
 
            }
 
            public void mouseExited(MouseEvent e) {
 
            }
 
            public void mousePressed(MouseEvent e) {
 
            }
 
            public void mouseReleased(MouseEvent e) {
 
            }
 
        });
 
        render.addKeyListener(new KeyListener(){//keylistener
 
            public void keyPressed(KeyEvent e) {
                int code = e.getKeyCode();
                if(code == 'P'){
                    if(running)
                        running = false;
                    else
                        running = true;
                }
                if(code == ' '){//new random color
                    c = new Color((int)randomPlus(255),(int)randomPlus(255),(int) randomPlus(255));
                    if(type.equals("")){
                        type = "image";
                    }else {
                        type = "";
                    }
                }
                if(code == 'A'){
                    Particle p = new Particle((int)(screen_width/2),(int)(screen_height/2),0,0,0,0,c, "animation");
                    p.setLife(randomPlus(150)+150);
                    p.setSize(randomPlus(25)+25, randomPlus(25)+25);
                    p.setMaxSize(50,50);
                    p.setUltSize(false);
                    p.setSizeDeault(true);
                    particles.add(p);
                }
                if(code == 'W'){
                    Particle p = new Particle(x,y+random(32),0,0,16,50,Color.LIGHT_GRAY, "oval");
                    p.setVel(0,random(7)); 
                    p.setAcc(randomPlus(7),-randomPlus(.04)-.02); 
                    p.setMaxSize(25,25);
                    p.setGrowth(-.1, -.1); 
                    p.setSizeDeault(true);
                    p.setUltSize(false);
                    particles.add(p);
                }
                if(code == 'S'){
                    emitter = new smokeEmitter();
                    particles.addAll(emitter.emit(x,y));
                }
                if(code == 'K'){
                    type = "bubble";
                }
                if(code == 'L'){
                    type = "rectangle";
                }
                if(code == 'F'){
                    emitter = new fireEmitter();
                    particles.addAll(emitter.emit(render.getWidth()/4, render.getHeight()-1));
                    particles.addAll(emitter.emit((render.getWidth()/4)*3, render.getHeight()-1));
                }

                if(code == 'D'){
                    emitter = new fireEmitter();
                    particles.addAll(emitter.emit(render.getWidth()-1, render.getHeight()-1));
                    particles.addAll(emitter.emit(0, render.getHeight()-1));
                }

            }
 
            public void keyReleased(KeyEvent e) {
 
            }
 
            public void keyTyped(KeyEvent e) {
 
            }
 
        });
    }
 
    public double random( double num ){//random num
        return (num * 2)  * rng.nextDouble() - num;
    }
 
    public double randomPlus( double num ){//return only a positive number
        double temp = ((num * 2)  * rng.nextDouble()) - num;
        if( temp < 0 )
            return temp * -1;
        else
            return temp;
    }
 
    //This is a bad game loop example but it is quick to write and easy to understand
    public void loop(){
        while(true){
            if(running)
                update();
            render();
            try {
                Thread.sleep(1000/60);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
 
    public void update(){
        Point p = render.getMousePosition();
        if(p !=null ){
            x = p.x;
            y = p.y;
        }
        for(int i = 0; i <= particles.size() - 1;i++){
            Particle part = particles.get(i);
            if(part.update())
                particles.remove(i);
            if(part != null){
                if(part.getLoc().x <= 0){
                    part.getLoc().x = 0;
                    part.getVel().x *= -1;
                }
                if(part.getLoc().x >= render.getWidth()){
                    part.getLoc().x = render.getWidth();
                    part.getVel().x *= -1;
                }
                if(part.getLoc().y <= 0){
                    part.getLoc().y = 0;
                    part.getVel().y *= -1;
                }
                if(part.getLoc().y >= render.getHeight()){
                    part.getLoc().y = render.getHeight();
                    part.getVel().y *= -1;
                }
            }
        }
    }
 
    public void render(){
        do{
            do{
                Graphics2D g2d = (Graphics2D) bufferstrat.getDrawGraphics();
                g2d.fillRect(0, 0, render.getWidth(), render.getHeight()); // fill the screen
                
                renderParticles(g2d);
 
                g2d.dispose();
            }while(bufferstrat.contentsRestored());
                bufferstrat.show();
        }while(bufferstrat.contentsLost());
    }
 
    public void renderParticles(Graphics2D g2d){
        for(int i = 0; i <= particles.size() - 1;i++){
            particles.get(i).render(g2d);
        }
    }

}
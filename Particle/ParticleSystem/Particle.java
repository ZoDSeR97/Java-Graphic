import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.imageio.ImageIO;
import javax.print.DocFlavor.STRING;

import java.io.File;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;
import java.util.ArrayList;
import java.util.Arrays;
 
public class Particle {

    File imageFolder = new File("./image");
    File[] imageFiles = imageFolder.listFiles();

    File effectFolder = new File("./effect");
    File[] effectFiles = effectFolder.listFiles();

    /* NEED TO FIND ANIMATION RESOURCES BEFORE ATTEMPT THIS
    File animationFolder = new File("./animation");
    File[] animationFolders = animationFolder.listFiles();
    */
 
    private Vector2d loc;
    private Vector2d vel;
    private Vector2d acc;
    private Vector2d size;
    private Vector2d maxSize;
    private Vector2d growth;
    private Vector2d life;
    private Color color;
    private BufferedImage image;
    private int iter;
    private String type;
    private String shape;
    private boolean particleEffect = false;
    
    private ArrayList<String> geo = new ArrayList<String>(Arrays.asList("rectangle", "oval", "bubble"));
    private ArrayList<String> effect = new ArrayList<String>(Arrays.asList("smoke", "fire"));
 
    private boolean ultSize = false;
    private boolean defaultSize = false;
 
    public Particle(double x, double y, double dx, double dy, double size, double life, Color c, String type){
        this.loc = new Vector2d(x,y);
        this.vel = new Vector2d(dx,dy);
        this.acc = new Vector2d(0,0);
        this.life = new Vector2d(life,life);
        this.size = new Vector2d(size,size);
        this.growth = new Vector2d(0,0);
        this.maxSize = new Vector2d(0,0);
        this.type = type;
        this.color = c;
        if (type.equals("image")) {
            int randIndex = new Random().nextInt(imageFiles.length);
            try {
                image = ImageIO.read(imageFiles[randIndex]);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (effect.contains(type)){
            particleEffect = true;
            try {
                for (int i = 0; i < effectFiles.length; i++){
                    if (effectFiles[i].toString().contains((type))){
                        image = ImageIO.read(effectFiles[i]);
                        break;
                    }   
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (geo.contains(type)){
            this.shape = type;
        } else {
            this.shape = geo.get(new Random().nextInt(geo.size()));  
        }
    }
 
    public boolean update(){
        vel.add(acc);
        loc.add(vel);
        size.add(growth);
        life.x--;
 
        if(life.x <= 0)
            return true;
 
        if(defaultSize){
            if(size.x >= maxSize.x){
                if(size.y >= maxSize.y)
                    return true;
                else
                    size.x = maxSize.x;
            }
            if(size.y >= maxSize.y)
                size.y = maxSize.y;
            if(size.x <= 0)
                if(size.y <= 0)
                    return true;
                else
                    size.x = 1;
            if(size.y <= 0)
                size.y = 1;
            return false; 
        }
 
        if(ultSize){ // We will shrink and grow back and forth
            if(size.x > maxSize.x){
                size.x = maxSize.x;
                growth.x *= -1;
            }
            if(size.y > maxSize.y){
                size.y = maxSize.y;
                growth.y *= -1;
            }
            if(size.x <= 0){
                size.x = 1;
                growth.x *= -1;
            }
            if(size.y <= 0){
                size.y = 1;
                growth.y *= -1;
            }
        }
        else{ //We stop growing or shrinking.
            if(size.x > maxSize.x)
                size.x = maxSize.x;
            if(size.y > maxSize.y)
                size.y = maxSize.y;
            if(size.x <= 0)
                size.x = 1;
            if(size.y <= 0)
                size.y = 1;
        }
        return false;
    }
 
    public void render(Graphics g){
        Graphics2D g2d = (Graphics2D) g.create();
 
        g2d.setColor(color);
        if (type.equals("image") || particleEffect){
            g2d.drawImage(image, (int)(loc.x-(size.x/2)), (int)(loc.y-(size.y/2)), (int)size.x, (int)size.y, null);
        } else {
            switch(shape){
                case "oval":
                    g2d.fillOval((int)(loc.x-(size.x/2)), (int)(loc.y-(size.y/2)), (int)size.x, (int)size.y);
                    break;
                case "bubble":
                    g2d.drawOval((int)(loc.x-(size.x/2)), (int)(loc.y-(size.y/2)), (int)size.x, (int)size.y);
                    break;
                case "rectangle":
                    g2d.drawRect((int)(loc.x-(size.x/2)), (int)(loc.y-(size.y/2)), (int)size.x, (int)size.y);
                    break;
                default:
                    g2d.fillRect((int)(loc.x-(size.x/2)), (int)(loc.y-(size.y/2)), (int)size.x, (int)size.y);
            }
        }
        g2d.dispose();
    }
 
    public void setLoc(double x,  double y){
        loc.x = x;
        loc.y = y;
    }
 
    public void setVel(double x,  double y){
        vel.x = x;
        vel.y = y;
    }
 
    public void setAcc(double x,  double y){
        acc.x = x;
        acc.y = y;
    }
 
    public void setSize(double x,  double y){
        size.x = x;
        size.y = y;
    }
 
    public void setMaxSize(double x,  double y){
        maxSize.x = x;
        maxSize.y = y;
    }
 
    public void setGrowth(double x,  double y){
        growth.x = x;
        growth.y = y;
    }
 
    public void setLife(double num){
        life.x = num;
        life.y = num;
    }
 
    public void setSizeDeault(boolean c){
        defaultSize = c;
    }
 
    public void setUltSize(boolean c){
        defaultSize = false;
        ultSize = c;
    }
 
    public Vector2d getLoc(){
        return loc;
    }
 
    public Vector2d getVel(){
        return vel;
    }
 
}
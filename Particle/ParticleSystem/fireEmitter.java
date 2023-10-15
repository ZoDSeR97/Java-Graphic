import java.util.ArrayList;
import java.awt.Color;

public class fireEmitter extends Emitter {
    public ArrayList<Particle> emit(double x, double y){
        ArrayList<Particle> particles = new ArrayList<>();
        int numParticles = 15;
        for(int i = 0; i < numParticles; i++){
            Particle p = new Particle(x, y, Math.random() -0.3, Math.random()*(-3), 16, 30, new Color(215, 20, 65), "fire");
            p.setMaxSize(20,20);
            p.setGrowth(-.1, -.1); 
            p.setSizeDeault(true);
            p.setUltSize(true);
            particles.add(p);
        }
        return particles;
    }
}

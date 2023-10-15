import java.util.ArrayList;
import java.awt.Color;

public class smokeEmitter extends Emitter {
    public ArrayList<Particle> emit(double x, double y){
        ArrayList<Particle> particles = new ArrayList<>();
        int numParticles = 10;
        for(int i = 0; i < numParticles; i++){
            Particle p = new Particle(x, y, (Math.random() - 0.5)*0.4, Math.random()*(-3), 16, 40, new Color(178, 190, 181), "smoke");
            p.setMaxSize(20,20);
            p.setGrowth(-.1, -.1); 
            p.setSizeDeault(true);
            p.setUltSize(true);
            particles.add(p);
        }
        return particles;
    }
}

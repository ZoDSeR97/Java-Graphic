// CubePers.java: A cube in perspective.

// Copied from Section 5.4 of
//    Ammeraal, L. and K. Zhang (2007). Computer Graphics for Java Programmers, 2nd Edition,
//       Chichester: John Wiley.

// Uses: Point2D (Section 1.5), Point3D (Section 3.9).

import java.awt.*;
import java.awt.event.*;

public class CubePers extends Frame {
   public static void main(String[] args) {new CubePers();}

   CubePers() {
      super("A cube in perspective");
      addWindowListener(new WindowAdapter() {
         public void windowClosing(WindowEvent e) {System.exit(0);}
      });
      setLayout(new BorderLayout());
      add("Center", new CvCubePers());
      Dimension dim = getToolkit().getScreenSize();
      setSize(dim.width / 2, dim.height / 2);
      setLocation(dim.width / 4, dim.height / 4);
      setVisible(true);
   }
}

class CvCubePers extends Canvas {
   int centerX, centerY;
   Obj obj = new Obj();

   int iX(float x) {
      return Math.round(centerX + x);
   }

   int iY(float y) {
      return Math.round(centerY - y);
   }

   void line(Graphics g, int i, int j) {
      Point2D p = obj.vScr[i], q = obj.vScr[j];
      g.drawLine(iX(p.x), iY(p.y), iX(q.x), iY(q.y));
   }

   public void paint(Graphics g) {
      Dimension dim = getSize();
      int maxX = dim.width - 1, maxY = dim.height - 1, 
          minMaxXY = Math.min(maxX, maxY);
      centerX = maxX / 2; centerY = maxY / 2;
      obj.d = obj.rho * minMaxXY / obj.objSize;
      obj.eyeAndScreen();
      // Horizontal edges at the bottom:
      line(g, 0, 1); line(g, 1, 2); line(g, 2, 3); line(g, 3, 0);
      // Horizontal edges at the top:
      line(g, 4, 5); line(g, 5, 6); line(g, 6, 7); line(g, 7, 4);
      // Vertical edges:
      line(g, 0, 4); line(g, 1, 5); line(g, 2, 6); line(g, 3, 7);
      
      int maxX1 = dim.width - 1, maxY1 = dim.height - 1, 
          minMaxXY1 = Math.min(maxX1, maxY1);
      centerX = maxX1 / 2; centerY = maxY1 / 2;
      obj.d = obj.rho * minMaxXY1 / obj.objSize;
      obj.eyeAndScreen();
      // Horizontal edges at the bottom:
      line(g, 8, 9); line(g, 9, 10); line(g, 10, 11); line(g, 11, 8);
      // Horizontal edges at the top:
      line(g, 12, 13); line(g, 13, 14); line(g, 14, 15); line(g, 15, 12);
      // Vertical edges:
      line(g, 8, 12); line(g, 9, 13); line(g, 10, 14); line(g, 11, 15);
      
   }
}

class Obj { // Contains 3D object data
   float rho, theta = 0.3F, phi = 1.3F, d, objSize, 
         v11, v12, v13, v21, v22, v23, v32, v33, v43;
                   // Elements of viewing matrix V
   Point3D[] w;    // World coordinates
   Point2D[] vScr; // Screen coordinates

   Obj() {
      w = new Point3D[16];
      vScr = new Point2D[16];
      // Bottom surface:
      w[0] = new Point3D(1, -2, -1);
      w[1] = new Point3D(1, 0, -1);
      w[2] = new Point3D(-1, 0, -1);
      w[3] = new Point3D(-1, -2, -1);
      // Top surface:
      w[4] = new Point3D(1, -2, 1);
      w[5] = new Point3D(1, 0, 1);
      w[6] = new Point3D(-1, 0, 1);
      w[7] = new Point3D(-1, -2, 1);

      // Bottom surface:
      w[8] = new Point3D(1, 1, -1);
      w[9] = new Point3D(1, 3, -1);
      w[10] = new Point3D(-1, 3, -1);
      w[11] = new Point3D(-1, 1, -1);
      // Top surface:
      w[12] = new Point3D(1, 1, 1);
      w[13] = new Point3D(1, 3, 1);
      w[14] = new Point3D(-1, 3, 1);
      w[15] = new Point3D(-1, 1, 1);

      objSize = (float) Math.sqrt(12F);
      // = sqrt(2 * 2 + 2 * 2 + 2 * 2)
      // = distance between two opposite vertices.
      rho = 5 * objSize; // For reasonable perspective effect
   }

   void initPersp() {
      float costh = (float) Math.cos(theta), 
            sinth = (float) Math.sin(theta), 
            cosph = (float) Math.cos(phi), 
            sinph = (float) Math.sin(phi);
      v11 = -sinth; v12 = -cosph * costh; v13 = sinph * costh;
      v21 = costh;  v22 = -cosph * sinth; v23 = sinph * sinth;
                    v32 = sinph;          v33 = cosph;
                                          v43 = -rho;
   }

   void eyeAndScreen() {
      initPersp();
      for (int i = 0; i < 16; i++) {
         Point3D p = w[i];
         float x = v11 * p.x + v21 * p.y, 
               y = v12 * p.x + v22 * p.y + v32 * p.z, 
               z = v13 * p.x + v23 * p.y + v33 * p.z + v43;
         vScr[i] = new Point2D(-d * x / z, -d * y / z);
      }
   }
}
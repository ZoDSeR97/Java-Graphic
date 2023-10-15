public class CohenSutherland{
    // Defining region codes 
    public static int INSIDE = 0; // 0000 
    public static int LEFT = 1; // 0001 
    public static int RIGHT = 2; // 0010 
    public static int BOTTOM = 4; // 0100 
    public static int TOP = 8; // 1000 
    
    // Defining x_max, y_max and x_min, y_min for 
    // clipping rectangle. Since diagonal points are 
    // enough to define a rectangle 
    // A(x_min, y_min); B(x_max, y_min); C(x_max, y_max); D(x_min, y_max)
    public static int x_max = 20; 
    public static int y_max = 20; 
    public static int x_min = 10; 
    public static int y_min = 10;
    static public void main(String []args){
        // First Line segment 
        // P11 = (5, 5), P12 = (7, 7) 
        cohenSutherlandClip(15, 5, 15, 15); 
    }

    // Function to compute region code for a point(x, y) 
    public static int computeCode(double x, double y){
        // initialized as being inside 
        int code = INSIDE; 
    
        if (x < x_min) // to the left of rectangle 
            code |= LEFT; 
        else if (x > x_max) // to the right of rectangle 
            code |= RIGHT; 
        if (y < y_min) // below the rectangle 
            code |= BOTTOM; 
        else if (y > y_max) // above the rectangle 
            code |= TOP; 
    
        return code; 
    }

    // Implementing Cohen-Sutherland algorithm 
    // Clipping a line from P1 = (x2, y2) to P2 = (x2, y2) 
    public static void cohenSutherlandClip(double x1, double y1, double x2, double y2){
        // Compute region codes for P1, P2 
        int code1 = computeCode(x1, y1); 
        int code2 = computeCode(x2, y2); 
    
        // Initialize line as outside the rectangular window 
        boolean accept = false; 
        
        while (true) { 
            if ((code1 == 0) && (code2 == 0)) { 
                // If both endpoints lie within rectangle 
                accept = true; 
                break; 
            } 
            else if ((code1 != 0) && (code2 != 0)) { 
                // If both endpoints are outside rectangle, 
                // in same region 
                break; 
            } 
            else { 
                // Some segment of line lies within the 
                // rectangle 
                int code_out; 
                double x = 0, y = 0; 
    
                // At least one endpoint is outside the 
                // rectangle, pick it. 
                if (code1 != 0) 
                    code_out = code1; 
                else
                    code_out = code2; 
    
                // Find intersection point; 
                // using formulas y = y1 + slope * (x - x1), 
                // x = x1 + (1 / slope) * (y - y1) 
                if (code_out == TOP) { 
                    // point is above the clip rectangle 
                    x = x1 + (x2 - x1) * (y_max - y1) / (y2 - y1); 
                    y = y_max; 
                } 
                else if (code_out == BOTTOM) { 
                    // point is below the rectangle 
                    x = x1 + (x2 - x1) * (y_min - y1) / (y2 - y1); 
                    y = y_min; 
                } 
                else if (code_out == RIGHT) { 
                    // point is to the right of rectangle 
                    y = y1 + (y2 - y1) * (x_max - x1) / (x2 - x1); 
                    x = x_max; 
                } 
                else if (code_out == LEFT) { 
                    // point is to the left of rectangle 
                    y = y1 + (y2 - y1) * (x_min - x1) / (x2 - x1); 
                    x = x_min; 
                } 
    
                // Now intersection point x, y is found 
                // We replace point outside rectangle 
                // by intersection point 
                if (code_out == code1) { 
                    x1 = x; 
                    y1 = y; 
                    code1 = computeCode(x1, y1); 
                } 
                else { 
                    x2 = x; 
                    y2 = y; 
                    code2 = computeCode(x2, y2); 
                } 
            } 
        } 
        if (accept) { 
            System.out.println("Line accepted from " +x1 +", " +y1 +" to " +x2 +", " +y2);
            // Here the user can add code to display the rectangle 
            // along with the accepted (portion of) lines 
        } 
        else{
            System.out.println("Line rejected");
        }
    }

}
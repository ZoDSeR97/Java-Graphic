public class testJavaParameterPassing {
    private int a, b;
    public testJavaParameterPassing() { }
    public testJavaParameterPassing(int a, int b) {
        this.a = a; this.b = b;
    }
    public void SwapByVal(int x, int y) {
        int temp=x;
        x=y;
        y=temp;
    }
    public void SwapByRef(testJavaParameterPassing t) { int temp = t.a; t.a = t.b; t.b = temp;
    }
    public static void main(String[] args) {
        int a = 10; int b = 20;
        System.out.println("Intial Value of local a: " + a + " local b: " + b);
        testJavaParameterPassing tjpp = new testJavaParameterPassing(a, b);
        tjpp.SwapByVal(tjpp.a, tjpp.b);
        System.out.println("Value of tjpp.a: " + tjpp.a + " tjpp.b: " + tjpp.b);
        tjpp.SwapByRef(tjpp);
        System.out.println("Value of tjpp.a: " + tjpp.a + " tjpp.b: " + tjpp.b);
    }
}

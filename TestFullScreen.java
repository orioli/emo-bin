
public class TestFullScreen {
    
    // Program entry
    public static void main(String[] args) throws Exception {
        FullScreen f1 = new FullScreen("flat.png");
        Thread.sleep(1000);
        f1.setImage("smile.jpg");
    }
}
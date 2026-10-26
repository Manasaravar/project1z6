import java.util.Random;

public class Person {
    protected int x, y;
    private String image = "\uD83E\uDDD9\u200D";
    private int live = 3;
    private boolean shield = false;
    Random r = new Random();

    Person(int sizeBoard) {
        y = sizeBoard;
        int n = r.nextInt(sizeBoard);
        x = n == 0 ? 1 : n;
    }

    Person(int x, int y){
        this.x = x;
        this.y = y;
    }
    Person(){
        this(1, 1);
    }

    public int getX(){
        return x;
    }

    public int getY() {
        return y;
    }

    public int getLive() {
        return live;
    }

    public String getImage(){
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public boolean hasShield(){ return shield;}

    public void getShield(){ shield = true;}



    public boolean moveCorrect(int x, int y){
        return this.x == x && Math.abs(this.y - y) == 1 || this.y == y && Math.abs(this.x - x) == 1;
    }

    void move(int x, int y){
        this.x = x;
        this.y = y;
    }

    public void downLive() {
        if (shield) {
            shield = false;
            System.out.println("Щит уничтожен, но вы не потеряли жизнь!");
        } else {
            live--;
            System.out.println("Вы потеряли жизнь!");
        }
    }
}
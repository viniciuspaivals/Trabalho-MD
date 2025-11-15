public class Cidade {
    private int id;
    private int x;
    private int y;

    @Override
    public String toString(){
        return "C" + id + "(" + x + "," + y + ")";
    }

    //CONSTRUTOR
    public Cidade(){}
    public Cidade(int id, int x, int y){
        this.id = id;
        this.x = x;
        this.y = y;
    }

    //GETTERS
    public int getId(){return this.id;}
    public int getX(){return this.x;}
    public int getY(){return this.y;}
   //SETTERS
    public void setId(int id){this.id = id;}
    public void setX(int x){this.x = x;}
    public void setY(int y){this.y = y;}
}


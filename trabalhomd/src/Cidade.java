public class Cidade {
    private int id; //TODAS AS CIDADES POSSUEM UM ID INDIVIDUAL
    private int x;  //COORDENADA - EIXO DAS ABSCISSAS
    private int y; //COORDENADA - EIXO DAS ORDENADAS

    @Override
    public String toString(){ //METODO toString - IMPRIME AS INFORMAÇÕES GERAIS DE TODAS INSTÂNCIA CRIADA DA CLASSE CIDADE
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


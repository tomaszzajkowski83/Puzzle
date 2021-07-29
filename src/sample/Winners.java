package sample;

public class Winners {
    String id;
    int wynik;
    public Winners(String id, int wynik){
        this.id=id;
        this.wynik=wynik;
    }

    @Override
    public String toString() {
        return id+" "+wynik+"\r\n";
    }
}

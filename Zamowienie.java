
public class Zamowienie
{
    public int id;
    public Pizza[] pitca;

    public Zamowienie(int idz, Pizza[] pi)
    {
        id = idz;
        pitca = pi;
    }
    
    public String toString(){
        String res =  "Zam" +id+ ": ";
        for(int i = 0; i<pitca.length; i++){
            res += pitca[i].toString();
            res += "\n";
        }
        return res;
    }
}

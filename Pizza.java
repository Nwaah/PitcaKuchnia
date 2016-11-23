
public class Pizza
{
    public String Nazwa;
    public String [] Skladnik;
    public int id;
    
    
    public Pizza(String n, String[] sk, int i)
    {
        Nazwa = n;
        Skladnik = sk;
        id = i;
    }
    
    public String toString()
    {
        return Nazwa+" "+id;
    }
}

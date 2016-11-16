// Demonstracja klasy JColorChooser
import java.awt.*;
import java.io.*;
import javax.swing.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
import java.net.*;
import org.json.*;
import java.nio.charset.Charset;

public class haloBaza extends JFrame 
{  private JButton hallo;
    private JLabel wyświetlRGB;
    private Color kolor = Color.LIGHT_GRAY;
    private Container c;
    public haloBaza()
    {
        super( "Super Kuchnia Pitcy" );
        c = getContentPane();
        c.setLayout( new FlowLayout() );
        hallo = new JButton( "Halo baza ?!" );
        hallo.addActionListener(
            new ActionListener() 
            {  
                public void actionPerformed( ActionEvent e )
                {
                    try {
                        JSONObject json = readJsonFromUrl("http://pitca.cba.pl/kuchnia.php");
                        JSONArray array = json.getJSONArray("result");
                        System.out.println(array);
                        int id[] = new int[array.length()];
                        for(int i = 0; i<array.length(); i++){
                            id[i] = array.getJSONObject(i).getInt("zamowienie");
                            System.out.println(id[i]);
                        }
                        JSONArray array1 = json.getJSONArray("pitce");
                        System.out.println(array1+"-");
                        for(int i = 0; i<array.length(); i++){
                            id[i] = array1.getJSONObject(i).getInt("id");
                            System.out.println("ID "+id[i]);
                        }
                    }catch(Exception ex){};
                }
            }
        );
        c.add( hallo );
        setSize( 800, 600 );

        setVisible(true);

        setResizable(false);
    }

    public static void main( String args[] )
    {
        haloBaza okno = new haloBaza();
        okno.setDefaultCloseOperation(EXIT_ON_CLOSE); 	           
    }

    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

    public static JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
        InputStream is = new URL(url).openStream();
        try {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = readAll(rd);
            JSONObject json = new JSONObject(jsonText);
            return json;
        } finally {
            is.close();
        }
    }

}

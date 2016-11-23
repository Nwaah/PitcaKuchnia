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
import java.util.Timer;
import java.util.TimerTask;

public class JFrameGlowny extends javax.swing.JFrame {
    Timer timer = new Timer();
    JFrame frame;

    class RefreshTask extends TimerTask {
        public void run() {
            refresh();
            timer.schedule(new RefreshTask(), 3000);
        }
    }    

    public JFrameGlowny() {  
        frame = new JFrame("Zamowienia");        
        frame.setVisible(true);
        frame.setSize(250,400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        timer.schedule(new RefreshTask(), 3000);
    }

    public void refresh(){     
        JPanel panel = new JPanel();
        frame.add(panel);
        ArrayList<JButton> butons = new ArrayList<JButton>();  
        try {
            Zamowienie zam[];
            JSONObject json = readJsonFromUrl("http://pitca.cba.pl/kuchnia.php");
            JSONArray array = json.getJSONArray("result");
            System.out.println(array);
            zam = new Zamowienie[array.length()];
            for(int i=0; i<array.length(); i++)
            {
                JSONObject zamowienie = array.getJSONObject(i);
                int id = zamowienie.getInt("zamowienie");
                JSONArray pitce = zamowienie.getJSONArray("pitce");
                Pizza pizze[] = new Pizza[pitce.length()];
                for(int ii=0; ii<pizze.length; ii++)
                {
                    JSONObject p = pitce.getJSONObject(ii);
                    String nazwa = p.getString("nazwa");
                    int idP = p.getInt("id");
                    //TODO get skladniki    
                    pizze[ii] = new Pizza(nazwa, null, idP);
                }                           
                zam[i] = new Zamowienie(id, pizze);
            }

            for(int i=0; i<zam.length; i++)
            {
                JButton b = new JButton("Zamowienie "+zam[i].id);
                butons.add(b);
                frame.add(b);
                System.out.println(zam[i].toString());
            }            

        }catch(Exception ex){System.out.println(ex.getMessage());}
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

    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();       
    }
}

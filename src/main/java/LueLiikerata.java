import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class LueLiikerata {

    private static List<Double> ySkaalattuna;

    public LueLiikerata() {
    }
                       public static List<Double> getSkaalattuLista () {
                         return ySkaalattuna;

                       }
    public static void main(String[] args) throws SlickException {
        Image kuva = new Image("src/main/resources/kayra.png");
        List<Double> yKoordinaatit = new ArrayList<Double>();
        ySkaalattuna = new ArrayList<Double>();
        double suurin = 0;
        double pienin = 100000;

        double erotus = 0;

        for (int i = 0; i < kuva.getWidth(); i++) {
            for (int j = 0; j < kuva.getHeight(); j++) {
                if (kuva.getColor(i, j).getRed() == 0 && kuva.getColor(i, j).getGreen() == 0 && kuva.getColor(i, j).getBlue() == 0) {
                    yKoordinaatit.add((double)j);
                    break;
                }

            }
        }
           for (Double item : yKoordinaatit) {
               if(item > suurin) suurin = item;
               if(item < pienin) pienin = item;
           }

        erotus = suurin - pienin;

        for (Double item : yKoordinaatit) {
          ySkaalattuna.add(item / erotus);
        }
        try {
            FileWriter outFile = new FileWriter("liikerata");
            PrintWriter out = new PrintWriter(outFile);

            for (Double item : ySkaalattuna) {
                out.println(item.toString());

            }


            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
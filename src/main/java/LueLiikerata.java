import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class LueLiikerata {
    public static List<Double> read(Resources resources) throws SlickException {
        List<Double> yKoordinaatit = new ArrayList<Double>();
        List<Double> ySkaalattuna = new ArrayList<Double>();
        double suurin = 0;
        double pienin = 100000;

        double erotus = 0;

        for (int i = 0; i < resources.kayra.getWidth(); i++) {
            for (int j = 0; j < resources.kayra.getHeight(); j++) {
                if (resources.kayra.getColor(i, j).getRed() == 0 && resources.kayra.getColor(i, j).getGreen() == 0 && resources.kayra.getColor(i, j).getBlue() == 0) {
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

        return ySkaalattuna;
    }
}
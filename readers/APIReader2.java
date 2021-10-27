package readers;



import state.State;
import vector.Vector3D;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class APIReader2 {
    /**
     * To read in the solar system form a file with the default name solar_system_data-2020_04_01.txt
     * @return the gui.graphics2d.model of the solar system
     */
    public static State read() {
        return read("solar_system_data-2020_04_01.txt");
    }

    /**
     * To read in the solar system form a file with a specific name
     * @param solarSystemFileName the name of the file
     * @return the gui.graphics2d.model of the solar system
     */
    public static State read(String solarSystemFileName) {
        return read(new File(solarSystemFileName));
    }

    /**
     * To read in the solar system form a specific file
     * @param solarSystemFile the file
     * @return the gui.graphics2d.model of the solar system
     */
    public static State read(File solarSystemFile) {
        try {
            Scanner s = new Scanner(solarSystemFile);

            // measure the amount of objects
            int lines = 0;
            while (s.hasNext()) {
                lines++;
                s.nextLine();
            }

            // indicate the array
            String[] names = new String[lines];
            Vector3D[] positions = new Vector3D[lines];
            Vector3D[] velocities = new Vector3D[lines];
            double[] masses = new double[lines];
            double[] radia = new double[lines];

            s = new Scanner(solarSystemFile);
            // fill the arrays
            String[] line;
            int j;
            for(int i = 0; i < lines; i++){
               positions[i] = new Vector3D();
               velocities[i] = new Vector3D();

               line = s.nextLine().split("=");

               j = 0;
               names[i] = ((line[j].substring(j, line[j].indexOf(":", j))));
               j++;

               masses[i] = (readDouble(line[j]));
               j++;

                  radia[i] = (readDouble(line[j]));
                  j++;

                positions[i].setX(readDouble(line[j]));
                j++;

                positions[i].setY(readDouble(line[j]));
                j++;

                positions[i].setZ(readDouble(line[j]));
                j++;

                velocities[i].setX(readDouble(line[j]));
                j++;

                velocities[i].setY(readDouble(line[j]));
                j++;

                velocities[i].setZ(readDouble(line[j]));
            }
           return new State(names, positions, velocities, masses, radia);
        } catch (FileNotFoundException e) {
            System.out.println("FileNotFoundException");
            e.printStackTrace();
        }
        return null;
    }

    private static double readDouble(String in) {
        String[] q = in.split(" ");
        if (q[0].isEmpty()) {
            in = q[1];
        } else {
            in = q[0];
        }

        if (in.contains(",")) {
            in = in.substring(0, in.indexOf(","));
        }
        return Double.parseDouble(in);
    }
}


package gbw.roguelike.backend;

import gbw.roguelike.LevelInformation;
import gbw.roguelike.RoomExit;
import gbw.roguelike.enums.ExitDirection;
import javafx.geometry.Point2D;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class TextProcessor {

    private final String textDir = "/text";
    private final String projRootToTextDir = ContentEngine.projRootToRessourceDir + textDir;
    private final String roomDir = "/rooms";
    private final String configDir = "/Configs";

    public ArrayList<RoomExit> getRoomExits(int id) {
        ArrayList<RoomExit> output = new ArrayList<>();

        try{
            BufferedReader br = new BufferedReader(new FileReader( projRootToTextDir + roomDir + "/EXITS.txt"));
            String line;
            String[] lineSplit;

            while((line = br.readLine()) != null){
                lineSplit = line.split(",");

                if(Integer.parseInt(lineSplit[0]) == id){
                    output.add(new RoomExit(
                            determineExitDirection(lineSplit[3]),
                            new Point2D(Double.parseDouble(lineSplit[1]), Double.parseDouble(lineSplit[2]))
                    ));
                }
            }

            br.close();
        }catch (IOException e){
            e.printStackTrace();
        }

        return output;
    }

    private ExitDirection determineExitDirection(String s) {

        for(ExitDirection e : ExitDirection.values()){
            if(e.direction.equalsIgnoreCase(s)){
                return e;
            }
        }
        return ExitDirection.NORTH;
    }

    public ArrayList<LevelInformation> getLevelInformations() {
        ArrayList<LevelInformation> output = new ArrayList<>();

        try{
            BufferedReader br = new BufferedReader(new FileReader(projRootToTextDir + configDir + "/LevelConfig.txt"));
            String line;
            String[] lineSplit;

            while((line = br.readLine()) != null){
                lineSplit = line.split(",");

                output.add(new LevelInformation(Integer.parseInt(lineSplit[0]),
                        new Point2D(Double.parseDouble(lineSplit[1]), Double.parseDouble(lineSplit[2])),
                        new Point2D(Double.parseDouble(lineSplit[3]), Double.parseDouble(lineSplit[4])),
                        Integer.parseInt(lineSplit[5])
                        ));
                System.out.println("Loaded found level " + lineSplit[0]);
            }

            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return output;
    }
}

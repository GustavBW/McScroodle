package gbw.winnions.winnions;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Logger {

    private static final List<String> loggedLines = new ArrayList<>();
    private static final SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
    private static File currentLog = null;
    private static long logCount = 0;

    public static void log(String s){
        Date date = new Date();

        loggedLines.add(timeFormat.format(date) + " | " + s);
    }


    public static void onClose(){
        System.out.println("Logger: Closing...");
        StringBuilder builder = new StringBuilder();

        for(String s : loggedLines){
            builder.append(s).append("\n");
        }

        try {
            FileWriter writer = new FileWriter(currentLog);

            writer.write(builder.toString());

            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Logger: Current log is located at: " + currentLog.getAbsolutePath());
    }

    public static void onProgramStart(){

        Date date = new Date(System.currentTimeMillis());
        String dateToUseForLogFile = dateFormat.format(date);
        String pathToLogsDirectory = "";

        File logDir = new File("Logs");

        if(!logDir.exists()) {
            System.out.println("Logger: Making directory...");
            logDir.mkdir();
            pathToLogsDirectory = logDir.getAbsolutePath() + "\\";

        }else{

            logCount = logDir.listFiles().length;

            pathToLogsDirectory = "Logs/";
        }

        System.out.println("Logger: Making logging file...");
        File newFile = new File(pathToLogsDirectory + "/" + "Log_" + dateToUseForLogFile + "_" + logCount + ".txt");
        currentLog = newFile;
        logCount++;
        System.out.println("Logger: Logging file located at: " + newFile.getAbsolutePath());
    }
}

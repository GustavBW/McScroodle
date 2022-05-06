package gbw.tdg.towerdefensegame.backend;

import gbw.tdg.towerdefensegame.UI.RText;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.nio.charset.StandardCharsets;
import java.util.*;

public class TextFormatter {

    private static final Text sampleText = new Text();

    public static List<String> toLinesArray(String text, int symbolsPerLine, String splitBy) {

        List<String> array = new ArrayList<>();        //Array of strings returned
        String[] asIndividualWords = text.split(splitBy);   //When split


        StringBuilder currentLine = new StringBuilder();
        int wordsRolledThrough = 0;

        for (String word : asIndividualWords) {

            //Just add up the words and mush them together until you reach max
            if (asIndividualWords.length - wordsRolledThrough > 1 && word.getBytes(StandardCharsets.UTF_8).length < symbolsPerLine) {

                if ((currentLine + word).getBytes(StandardCharsets.UTF_8).length < symbolsPerLine) {
                    currentLine.append(word).append(" ");
                    wordsRolledThrough++;

                } else {
                    array.add(currentLine.toString());
                    currentLine = new StringBuilder();
                    currentLine.append(word).append(" ");
                    wordsRolledThrough++;
                }

            } else {
                array.add(currentLine + word);
                wordsRolledThrough++;
            }
        }
        return trimStringArray(array);
    }
    public static List<String> toLinesArray(String text, int symbolsPerLine) {
        return toLinesArray(text, symbolsPerLine, "\n");
    }

    public static double getWidthOf(String text, Font font){
        sampleText.setText(text);
        sampleText.setFont(font);
        return sampleText.getLayoutBounds().getWidth();
    }

    public static double getWidthOf(RText rtext){
        return getWidthOf(rtext.getText(),rtext.getFont());
    }


    public static List<String> wordWrapCustom(String wrapMe, Font font, double wrapInPixels){
        //Pulled from StackOverflow: https://stackoverflow.com/questions/52350922/word-wrap-in-java-swing-with-hard-wrap-pixel-limit

        //Cut the string into bits at the spaces
        String[] split = wrapMe.split(" ");

        ArrayList<String> lines = new ArrayList<String>(); //we will return this, each item is a line
        if(wrapInPixels < font.getSize()){return lines;}

        String currentLine = ""; //All contents of the currentline
        Text fm = new Text(currentLine);
        fm.setFont(font);
        StringBuilder sB = new StringBuilder();


        for(String s : split) {
            //Try to add the next string
            fm.setText(currentLine + " " + s);
            if( fm.getLayoutBounds().getWidth() >= wrapInPixels ) {
                //Too big
                if(!currentLine.isBlank()) { //If it is still bigger with an empty string, still add at least 1 word
                    lines.add(currentLine); //Add the line without this string
                    currentLine = s; //Start next line with this string in it
                    continue;
                }
            }
            //Still have room, or a single word that is too big
            //add a space if not the first word
            if(!currentLine.isBlank()) {
                currentLine += " ";
            }

            //Append string to line
            currentLine = currentLine + s;
        }
        //The last line still may need to get added
        if(!currentLine.isBlank()) {
            lines.add(currentLine);
        }

        return lines;
    }

    public static List<String> trimStringArray(List<String> list) {
        List<Integer> indexesToRemove = new ArrayList<>();

        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).isBlank()) {
                indexesToRemove.add(i);
            }
        }

        for (Integer i : indexesToRemove) {
            list.remove(i);
        }

        return list;
    }

    public static String toLines(String text, int symbolsPerLine, String splitBy) {
        List<String> lines = toLinesArray(text, symbolsPerLine, splitBy);
        StringBuilder sB = new StringBuilder();

        for (String line : lines) {
            sB.append(line).append("\n");
        }

        return sB.toString();
    }
    public static String toLines(String text, int symbolsPerLine){
        return toLines(text,symbolsPerLine,"\n");
    }
    public static String concatonateArray(List<String> list){
        return concatonateArray(list,"");
    }
    public static String concatonateArray(List<String> list, String inset){
        StringBuilder sB = new StringBuilder();
        for(String s : list){
            sB.append(s).append(inset);
        }
        return sB.toString();
    }


    public static String getIsolatedClassName(Object obj) {
        String full = obj.toString();
        int indexStart = full.lastIndexOf('.') + 1;
        int indexEnd = full.indexOf('@');
        return full.substring(indexStart, indexEnd);
    }

    public static String intToKMBNotation(int input){
        //By "KMB" notation is meant: x amount K's (1000's), M's (1_000_000's) & B's...
        String output = String.valueOf(input);

        if(input >= 1_000_000_000){
            output = (input / 1_000_000_000) + "B";

        }else if(input >= 1_000_000){
            output = (input / 1_000_000) + "M";

        }else if(input >= 1_000){
            output = (input / 1000) + "K";
        }

        return output;
    }
    public static String doubleToKMBNotation(double input, int decimalPlaces){
        //By "KMB" notation is meant: x amount K's (1000's), M's (1_000_000's) & B's...
        //E.g. 1_325_953 would be simplified to 1.3M if decimalPlaces = 1

        String symbol = "";
        double formatted = input;

        if(input >= 1_000_000_000){
            formatted /= 1_000_000_000;
            symbol = "B";

        }else if(input >= 1_000_000){
            formatted /= 1_000_000;
            symbol = "M";

        }else if(input >= 1_000){
            formatted /= 1000;
            symbol = "K";
        }

        return Decimals.toXDecimalPlaces(formatted, decimalPlaces) + symbol;
    }

    public static String toRomanNumerals(int input) {
        if (input <= 0) {
            return "0";
        }
        if (input > 3999) {
            return String.valueOf(input);
        }
        //Standard signs
        Map<Integer, String> valueOfCharMap = new HashMap<>(Map.of(
                1, "I",
                5, "V",
                10, "X",
                40, "XL",
                50, "L",
                90, "XC",
                100, "C",
                400, "CD",
                500, "D",
                1000, "M"
        ));
        //Edge cases
        valueOfCharMap.putAll(Map.of(
                4, "IV",
                9, "IX",
                49, "IL",
                95, "VC",
                99, "IC",
                399, "ICD",
                490, "XD",
                900, "CM",
                990, "XM",
                999, "IM"
        ));
        List<Integer> values = new ArrayList<>(valueOfCharMap.keySet());
        values.sort(Comparator.comparingInt(o -> (int) o).reversed());
        StringBuilder sB = new StringBuilder();

        int i = 0;

        while (input > 0) {
            int factor = 1;
            int concideredValue = values.get(i);
            String concideredChar = valueOfCharMap.get(concideredValue);

            /*if(input >= concideredValue * 100_000 ){
                sB.append("B");
                factor = 100_000;

            }else if(input >= concideredValue * 1_000) {
                sB.append("|");
                factor = 1_000;

            }else*/
            if (input < concideredValue) {
                i++;
                continue;
            }

            sB.append(concideredChar);
            input -= concideredValue * factor;

        }
        return sB.toString();
    }
}

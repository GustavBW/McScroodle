package gbw.tdg.towerdefensegame.backend;

import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.nio.charset.StandardCharsets;
import java.util.*;

public class TextFormatter {

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
    public static List<String> toLinesArray(String text, int symbolsPerLine){
        return toLinesArray(text, symbolsPerLine, "\n");
    }
    public static List<String> toLinesArray(String text, double prefWidth, Font font){
        List<String> output = new ArrayList<>();
        if(text.isBlank()){return output;}

        List<String> spacedOut = new LinkedList<>(List.of(text.split(" ")));

        int i = 0;

        String currentLine = text;
        Text fullChecker = new Text(currentLine);
        fullChecker.setFont(font);

        Text substringChecker = new Text();
        substringChecker.setFont(font);

        while(fullChecker.getLayoutBounds().getWidth() > prefWidth && spacedOut.size() > 1) {

            while (substringChecker.getLayoutBounds().getWidth() < prefWidth && spacedOut.size() > 1) {

                currentLine = concatonateArray(spacedOut.subList(0, i), " ");
                substringChecker.setText(currentLine);

                if (substringChecker.getLayoutBounds().getWidth() < prefWidth) {
                    i++;

                } else {

                    currentLine = concatonateArray(spacedOut.subList(0, i), " ");
                    output.add(currentLine);

                    for (int k = 0; k < i && k < spacedOut.size(); k++) {
                        spacedOut.remove(k);
                    }

                    i = 0;

                    currentLine = "";
                    substringChecker.setText("");
                }
            }
        }
        output.add(currentLine);


        return output;
    }

    public static void main(String[] args) {
        String text = "Lmao I really gotta white something long here. Got no ideas but todays Deadlands session was cool. Messed up a bunch though, made an, in the words of Thomas' \" super aspergers move \" which authenticity he appreciated. I didn't. There is a reason it's so authentic";
        System.out.println(text);

        for(String s : toLinesArray(text,100,Font.font("Impact",20))){
            System.out.println(s);
        }
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

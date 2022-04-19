package gbw.tdg.towerdefensegame.UI;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class TextFormatter {

    public static List<String> toLinesArray(String text, int symbolsPerLine, String splitBy){

        List<String> array = new ArrayList<>();        //Array of strings returned
        String[] asIndividualWords = text.split(splitBy);   //When split


        StringBuilder currentLine = new StringBuilder();
        int wordsRolledThrough = 0;

        for(String word : asIndividualWords){

            //Just add up the words and mush them together until you reach max
            if(asIndividualWords.length - wordsRolledThrough > 1 && word.getBytes(StandardCharsets.UTF_8).length < symbolsPerLine) {

                if ((currentLine + word).getBytes(StandardCharsets.UTF_8).length < symbolsPerLine) {
                    currentLine.append(word).append(" ");
                    wordsRolledThrough++;

                } else {
                    array.add(currentLine.toString());
                    currentLine = new StringBuilder();
                    currentLine.append(word).append(" ");
                    wordsRolledThrough++;
                }

            }else{
                array.add(currentLine + word);
                wordsRolledThrough++;
            }
        }
        return array;
    }
    public static String toLines(String text, int symbolsPerLine, String splitBy){
        List<String> lines = toLinesArray(text,symbolsPerLine, splitBy);
        StringBuilder sB = new StringBuilder();

        for(String line : lines){
            sB.append(line).append("\n");
        }

        return sB.toString();
    }

    public static String intToRomanNumerals(int input) {
        if (input < 1 || input > 3999)
            return "Invalid Roman Number Value";
        String s = "";
        while (input >= 1000) {
            s += "M";
            input -= 1000;        }
        while (input >= 900) {
            s += "CM";
            input -= 900;
        }
        while (input >= 500) {
            s += "D";
            input -= 500;
        }
        while (input >= 400) {
            s += "CD";
            input -= 400;
        }
        while (input >= 100) {
            s += "C";
            input -= 100;
        }
        while (input >= 90) {
            s += "XC";
            input -= 90;
        }
        while (input >= 50) {
            s += "L";
            input -= 50;
        }
        while (input >= 40) {
            s += "XL";
            input -= 40;
        }
        while (input >= 10) {
            s += "X";
            input -= 10;
        }
        while (input >= 9) {
            s += "IX";
            input -= 9;
        }
        while (input >= 5) {
            s += "V";
            input -= 5;
        }
        while (input >= 4) {
            s += "IV";
            input -= 4;
        }
        while (input >= 1) {
            s += "I";
            input -= 1;
        }
        return s;
    }
}

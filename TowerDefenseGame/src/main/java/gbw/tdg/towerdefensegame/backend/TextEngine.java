package gbw.tdg.towerdefensegame.backend;

import gbw.tdg.towerdefensegame.invocation.Invocation;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TextEngine extends IOEngine{

    private static TextEngine instance;

    protected TextEngine(String root, String def) {
        super(root, def);
    }

    public static TextEngine getInstance(String root){
        if(instance == null){
            instance = new TextEngine(root, "");
        }
        return instance;
    }

    public int getPersistentGameplayValue(String name){
        File file = super.request("persistentGameplayValues");
        if(file == null){
            return -1;
        }

        List<String> lines = readFile(file);

        for(String s : lines){
            if(getAttrName(s).equalsIgnoreCase(name)){
                return Integer.parseInt(getAttrVal(s));
            }
        }

        return -1;
    }

    public void setPersistentGameplayValue(String attrName, int i) {
        File file = super.request("persistentGameplayValues");
        if(file == null){
            return;
        }

        List<String> lines = super.readFile(file);
        List<String> toWrite = new ArrayList<>();

        for(String s : lines){
            if(getAttrName(s).equalsIgnoreCase(attrName)){
                toWrite.add(attrName + "=" + i + ";");
            }else{
                toWrite.add(s);
            }
        }

        super.writeToFile(file,toWrite);
    }

    public Map<String,Integer> getInvocationLevels(){
        File file = super.request("invocationLevels");
        Map<String,Integer> toReturn = new HashMap<>();

        List<String> lines = super.readFile(file);

        for(String s : lines){
            toReturn.put(getAttrName(s),Integer.parseInt(getAttrVal(s)));
        }

        return toReturn;
    }

    private String getAttrVal(String s){
        int indexStart = s.lastIndexOf('=') + 1;
        int indexEnd = s.lastIndexOf(';');
        return s.substring(indexStart,indexEnd);
    }
    private String getAttrName(String s){
        int indexEnd = s.lastIndexOf('=');
        String t = s.substring(0,indexEnd);
        return t;
    }

    public void updateInvocationLevels(List<Invocation> invocations) {
        List<String> toWrite = new ArrayList<>();
        for(Invocation i : invocations){
            toWrite.add(TextFormatter.getIsolatedClassName(i) + "=" + i.getLevel() + ";");
        }
        super.writeToFile(super.request("invocationLevels"),toWrite);
    }


}

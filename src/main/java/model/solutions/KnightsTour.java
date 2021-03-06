package model.solutions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Objects;

public abstract class KnightsTour {
    private ArrayList<ArrayList<String>> field;
    private ArrayList<Integer[]> path;
    private Integer startX;
    private Integer startY;
    private Integer fieldWidth;
    private Integer fieldHeight;
    private String color;
    private Integer delay;

    public Integer getDelay() {
        return delay;
    }

    public void setDelay(Integer delay) {
        this.delay = delay;
    }

    private static ArrayList<String> colorsList =
            new ArrayList<>(Arrays.asList(
                    "RED", "GREEN", "BLUE",
                    "AQUA", "PURPLE", "GOLD",
                    "MAGENTA", "DARKRED","DARKCYAN"));
    public static void refreshColors(){
        colorsList=new ArrayList<>(Arrays.asList(
                "RED", "GREEN", "BLUE",
                "AQUA", "PURPLE", "GOLD",
                "MAGENTA", "DARKRED","DARKCYAN"));
        Collections.shuffle(colorsList);
    }

    boolean isCorner(Integer[] coord) {
        System.out.println(coord[0]==null);
        return (coord[0]==0&&coord[1]==0)
                ||(Objects.equals(coord[0], getFieldWidth()) &&coord[1]==0)
                ||(coord[0]==0&& Objects.equals(coord[1], getFieldHeight()))
                ||(Objects.equals(coord[0], getFieldWidth())
                && Objects.equals(coord[1], getFieldHeight()));
    }

    Integer getFieldWidth() {
        return fieldWidth;
    }

    void setFieldWidth(Integer fieldWidth) {
        this.fieldWidth = fieldWidth;
    }

    Integer getFieldHeight() {
        return fieldHeight;
    }

    void setFieldHeight(Integer fieldHeight) {
        this.fieldHeight = fieldHeight;
    }

    Integer getStartX() {
        return startX;
    }

    void setStartX(Integer startX) {
        this.startX = startX;
    }

    Integer getStartY() {
        return startY;
    }

    void setStartY(Integer startY) {
        this.startY = startY;
    }

    KnightsTour(int width, int height){
        Collections.shuffle(colorsList);
        color=colorsList.get(0);
        colorsList.remove(0);
        field=new ArrayList<>();
        path=new ArrayList<>();
        initField(width,height);
    }

    public ArrayList<Integer[]> getPath() {
        return path;
    }

    void setPath(ArrayList<Integer[]> path) {
        this.path = path;
    }

    ArrayList<ArrayList<String>> getField() {
        return field;
    }

    void setField(ArrayList<ArrayList<String>> field) {
        this.field = field;
    }

    protected void initField(int width, int height){}

    Integer countAvailableTurns(int i,int j){
        int res=0;
        if (getUpLeft(i,j)==0) res++;
        if (getUpRight(i,j)==0) res++;
        if (getDownLeft(i,j)==0) res++;
        if (getDownRight(i,j)==0) res++;
        if (getRightDown(i,j)==0) res++;
        if (getRightUp(i,j)==0) res++;
        if (getLeftDown(i,j)==0) res++;
        if (getLeftUp(i,j)==0) res++;
        return res;
    }

    ArrayList<Integer[]> getAvailableTurns(int i, int j){
        ArrayList<Integer[]> res=new ArrayList<>();
        if (getUpLeft(i,j)==0) res.add(new Integer[]{i-1,j-2});
        if (getUpRight(i,j)==0) res.add(new Integer[]{i+1,j-2});
        if (getDownLeft(i,j)==0) res.add(new Integer[]{i-1,j+2});
        if (getDownRight(i,j)==0) res.add(new Integer[]{i+1,j+2});
        if (getRightDown(i,j)==0) res.add(new Integer[]{i+2,j+1});
        if (getRightUp(i,j)==0) res.add(new Integer[]{i+2,j-1});
        if (getLeftDown(i,j)==0) res.add(new Integer[]{i-2,j+1});
        if (getLeftUp(i,j)==0) res.add(new Integer[]{i-2,j-1});
        return res;
    }

    private int getLeftUp(int i, int j) {
        try{
            return Integer.parseInt(removeLetters(field.get(i-2).get(j-1)));
        }catch (Exception e){
            return -1;
        }
    }

    private int getLeftDown(int i, int j) {
        try{
            return Integer.parseInt(removeLetters(field.get(i-2).get(j+1)));
        }catch (Exception e){
            return -1;
        }
    }

    private int getRightUp(int i, int j) {
        try{
            return Integer.parseInt(removeLetters(field.get(i+2).get(j-1)));
        }catch (Exception e){
            return -1;
        }
    }

    private int getRightDown(int i, int j) {
        try{
            return Integer.parseInt(removeLetters(field.get(i+2).get(j+1)));
        }catch (Exception e){
            return -1;
        }
    }

    private int getDownRight(int i, int j) {
        try{
            return Integer.parseInt(removeLetters(field.get(i+1).get(j+2)));
        }catch (Exception e){
            return -1;
        }
    }

    private int getDownLeft(int i, int j) {
        try{
            return Integer.parseInt(removeLetters(field.get(i-1).get(j+2)));
        }catch (Exception e){
            return -1;
        }
    }

    private int getUpRight(int i, int j) {
        try{
            return Integer.parseInt(removeLetters(field.get(i+1).get(j-2)));
        }catch (Exception e){
            return -1;
        }
    }

    private int getUpLeft(int i, int j) {
        try{
            return Integer.parseInt(removeLetters(field.get(i-1).get(j-2)));
        }catch (Exception e){
            return -1;
        }
    }

    protected abstract void findPath(int counter, ArrayList<ArrayList<String>> field, ArrayList<Integer[]> path);

    public void printPath() {
        System.out.println("Field");
        System.out.println("Columns: "+field.size());
        for (int i = 0; i <field.size(); i++) {
            System.out.println("Column"+(i+1)+":  "+field.get(i).size());
        }
        System.out.println("\n\nPath\nSteps: "+path.size()+'\n');

        for (ArrayList<String> aField : field) {
            for (String anAField : aField) {
                System.out.print(anAField + '\t');
            }
            System.out.println();
        }

        for (int i = 0; i <path.size(); i++) {
            System.out.println((i+1)+")  "+(path.get(i)[0]+1)+"   "+(path.get(i)[1]+1));
        }
    }

    public abstract void startFinding();

    private String removeLetters(String str){
        StringBuilder dgt=new StringBuilder("");
        for (Character chr:str.toCharArray()
             ) {
            if (Character.isDigit(chr))
                dgt.append(chr);
        }
        return (dgt.length()>0)?dgt.toString():"0";
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return "KnightsTour" +
                "\ncolor = " + color+
                "\ndelay = "+getDelay();
    }
}

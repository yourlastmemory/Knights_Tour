package model.solutions;

import java.util.ArrayList;
import java.util.Objects;

public abstract class KnightsTour {
    private ArrayList<ArrayList<String>> field;
    private ArrayList<Integer[]> path;
    private Integer startX;
    private Integer startY;
    private Integer fieldWidth;
    private Integer fieldHeight;

    protected KnightsTour() {
    }

    public boolean isCorner(Integer[] coord) {
        System.out.println(coord[0]==null);
        return (coord[0]==0&&coord[1]==0)
                ||(Objects.equals(coord[0], getFieldWidth()) &&coord[1]==0)
                ||(coord[0]==0&& Objects.equals(coord[1], getFieldHeight()))
                ||(Objects.equals(coord[0], getFieldWidth())
                && Objects.equals(coord[1], getFieldHeight()));
    }

    public Integer getFieldWidth() {
        return fieldWidth;
    }

    public void setFieldWidth(Integer fieldWidth) {
        this.fieldWidth = fieldWidth;
    }

    public Integer getFieldHeight() {
        return fieldHeight;
    }

    public void setFieldHeight(Integer fieldHeight) {
        this.fieldHeight = fieldHeight;
    }

    public Integer getStartX() {
        return startX;
    }

    public void setStartX(Integer startX) {
        this.startX = startX;
    }

    public Integer getStartY() {
        return startY;
    }

    public void setStartY(Integer startY) {
        this.startY = startY;
    }

    public KnightsTour(int width, int height){
        field=new ArrayList<>();
        path=new ArrayList<>();
        initField(width,height);
    }

    public ArrayList<Integer[]> getPath() {
        return path;
    }

    public void setPath(ArrayList<Integer[]> path) {
        this.path = path;
    }

    public ArrayList<ArrayList<String>> getField() {
        return field;
    }

    public void setField(ArrayList<ArrayList<String>> field) {
        this.field = field;
    }

    protected void initField(int width, int height){};

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

        for (int i = 0; i <field.size(); i++) {
            for (int j = 0; j <field.get(i).size(); j++) {
                System.out.print(field.get(i).get(j)+'\t');
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
}

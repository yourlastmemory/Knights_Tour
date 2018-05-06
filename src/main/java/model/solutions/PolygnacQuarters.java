package model.solutions;

import com.google.common.collect.Lists;
import javafx.collections.FXCollections;

import java.util.*;

public class PolygnacQuarters extends KnightsTour {


    @Override
    protected void findPath(int counter, ArrayList<ArrayList<String>> field, ArrayList<Integer[]> path) {
        ArrayList<Integer[]> availableTurns=new ArrayList<>();
        for (int letters = 1; letters <5; letters++) {
            Boolean currentLetter=false;
            List<Integer[]> lastSteps;
            for (int quarters = 1; quarters <5;) {
                lastSteps=new ArrayList<>();
                for (int steps = 0; steps <3; steps++) {
                    availableTurns=new ArrayList<>();
                    if (path.size()==0&&lastSteps.size()==0) {
                        System.out.println("starting path");
                        lastSteps.add(startPath());
//                        System.out.println("last  "+last[0]+"  "+last[1]);
                        currentLetter=true;
                    }
                    if (lastSteps.size()==0) {
                        System.out.println("last steps is empty");
                        availableTurns = getAvailableTurns(
                                path.get(path.size() - 1)[0], path.get(path.size() - 1)[1]);
                    }
                    else /*if (path.size()>0)*/{
                        availableTurns=getAvailableTurns(
                                    lastSteps.get(lastSteps.size()-1)[0],lastSteps.get(lastSteps.size()-1)[1]);
                    }
                    if (availableTurns.size()==0){
                        System.out.println("blyad!");
                    }

                        Collections.shuffle(availableTurns);
                    System.out.println("available turns "+availableTurns.size());
                    Integer[] last=new Integer[2];
                        for (Integer[] available:availableTurns
                             ) {
                            System.out.println("available  "+available[0]+"  "+available[1]);
                            if (currentLetter&&(getLetterFrom(available)
                                    .equals(getLetterFrom(isLastEmpty(lastSteps)))
                                &&getQuarter(available)==getQuarter(isLastEmpty(lastSteps))))
                                if (!isVisited(available)) {
                                    last = available;
                                    steps++;
                                }
                            if (!currentLetter&&!(getLetterFrom(available)
                                    .equals(getLetterFrom(isLastEmpty(lastSteps)))))
                                if (!isVisited(available)) {
                                    last = available;
                                    steps++;
                                }
                            System.out.println("last  "+last[0]+"  "+last[1]);
                        }
                        if (last[0]!=null)lastSteps.add(last);
                        currentLetter=true;
                }
                if((!isCorner(lastSteps.get(lastSteps.size()-1)) &&(quarters<4
                        &&canMoveAway(lastSteps.get(lastSteps.size()-1))))||(quarters==4)){
                    availableTurns=getAvailableTurns(
                            lastSteps.get(lastSteps.size()-1)[0],lastSteps.get(lastSteps.size()-1)[1]);
                    Integer[] last=new Integer[2];
                    for (Integer[] available:availableTurns
                            ) {
                        System.out.println("available  "+available[0]+"  "+available[1]);
                        if ((getLetterFrom(available)
                                .equals(getLetterFrom(lastSteps.get(lastSteps.size()-1))))
                                &&getQuarter(available)!=getQuarter(lastSteps.get(lastSteps.size()-1)))
                            if (!isVisited(available))
                            last=available;
                    }
                    lastSteps.add(last);

                    for (Integer[] step:lastSteps
                         ) {
                        quarters++;
                        path.add(step);
                        field.get(step[0]).set(
                                step[1], String.valueOf(path.size()).concat(field.get(step[0]).get(step[1])));
                    }
                }

            }
        }
    }

    private boolean isVisited(Integer[] available) {
        for (Character chr:getField().get(available[0]).get(available[1]).toCharArray()
             ) {
            if (Character.isDigit(chr)) return true;
        }
        return false;
    }

    private Integer[] isLastEmpty(List<Integer[]> lastSteps) {
        return (lastSteps.size()==0)?getPath().get(getPath().size()-1):lastSteps.get(lastSteps.size()-1);
    }

    @Override
    public void startFinding() {
        setField(new ArrayList<>());
        setPath(new ArrayList<>());
        initField(8,8);
        setFieldHeight(8);
        setFieldWidth(8);
        findPath(0, getField(), getPath());
    }

    private Integer[] startPath() {
        setStartX(new Random().nextInt(8));
        setStartY(new Random().nextInt(8));
        System.out.println("start  "+getStartX()+"   "+getStartY());
        return new Integer[]{getStartX(),getStartY()};
    }

    private boolean canMoveAway(Integer[] integers) {
        ArrayList<Integer[]> availableTurns = getAvailableTurns(
                integers[0], integers[1]);
        for (Integer[] available:availableTurns
             ) {
            if (getQuarter(available)!=getQuarter(integers)&& getLetterFrom(available).equals(getLetterFrom(integers)))
                return true;
        }
        return false;
    }

    private String getLetterFrom(Integer[] integers) {
        return getField().get(integers[0]).get(integers[1])
                .substring(getField().get(integers[0]).get(integers[1]).length() - 1);
    }

    public PolygnacQuarters(int width, int height) {
        super(width, height);
    }

    @Override
    protected void initField(int width, int height) {
        setField(new ArrayList<>());
        for (int i = 0; i < 2; i++) {
            getField().add(new ArrayList<>(Arrays.asList("a","b","c","d","a","b","c","d")));
            getField().add(new ArrayList<>(Arrays.asList("c","d","a","b","c","d","a","b")));
            getField().add(new ArrayList<>(Arrays.asList("c","d","a","b","c","d","a","b")));
            Collections.reverse(getField().get(getField().size()-1));
            getField().add(new ArrayList<>(Arrays.asList("a","b","c","d","a","b","c","d")));
            Collections.reverse(getField().get(getField().size()-1));
        }

    }

    private int getQuarter(Integer[] integers){
        return (int) (Math.ceil((integers[0]+1)/4)+Math.ceil((integers[1]+1)/4));
    }
}

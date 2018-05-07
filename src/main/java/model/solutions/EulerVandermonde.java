package model.solutions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;
import java.util.Random;

public class EulerVandermonde extends KnightsTour {

    @Override
    public String toString() {
        return super.toString();
    }

    public EulerVandermonde(int width, int height) {
        super(width,height);
        setFieldWidth(width);
        setFieldHeight(height);
    }

    @Override
    protected void initField(int width, int height) {
        setField(new ArrayList<>());
        for (int i = 0; i < width; i++) {
            getField().add(new ArrayList<>());
            for (int j = 0; j <height; j++) {
                getField().get(i).add("0");
            }
        }
        setStartX(new Random().nextInt(width));
        setStartY(new Random().nextInt(height));
//        setStartX(0);
//        setStartY(1);
        System.out.println("Start pos "+getStartX()+"  "+getStartY());
        getPath().add(new Integer[]{getStartX(),getStartY()});
        getField().get(getStartX()).set(getStartY(),"1");

        //TODO Fucking bullshit
        String param="Columns "+getField().size();
    }

    @Override
    protected void findPath(int counter,ArrayList<ArrayList<String>> field, ArrayList<Integer[]> path) {
        if (counter<getFieldHeight()*getFieldWidth()) {
            ArrayList<Integer[]> availableTurtnsList=getAvailableTurns(path.get(path.size()-1)[0],path.get(path.size()-1)[1]);
            if (!availableTurtnsList.isEmpty()) {
                Collections.shuffle(availableTurtnsList);
                int minTurns=countAvailableTurns(availableTurtnsList.get(0)[0],availableTurtnsList.get(0)[1]);
                int minPosition=0;
                for (int i = 0; i < availableTurtnsList.size(); i++) {
                    if (countAvailableTurns(availableTurtnsList.get(i)[0],availableTurtnsList.get(i)[1])
                            < countAvailableTurns(availableTurtnsList.get(minPosition)[0],availableTurtnsList.get(minPosition)[1])){
                        minPosition=i;
                        minTurns=countAvailableTurns(availableTurtnsList.get(i)[0],availableTurtnsList.get(i)[1]);
                    }else if (
                            (Objects.equals(countAvailableTurns(availableTurtnsList.get(i)[0], availableTurtnsList.get(i)[1]),
                            countAvailableTurns(availableTurtnsList.get(minPosition)[0], availableTurtnsList.get(minPosition)[1])))
                            && (new Random().nextInt(100)<49)){
                        minPosition=i;
                        minTurns=countAvailableTurns(availableTurtnsList.get(i)[0],availableTurtnsList.get(i)[1]);
                    }
                }
                counter++;
                path.add(new Integer[]{availableTurtnsList.get(minPosition)[0],availableTurtnsList.get(minPosition)[1]});
                field.get(availableTurtnsList
                        .get(minPosition)[0]).set(
                                availableTurtnsList.get(minPosition)[1], String.valueOf(counter+1));
                findPath(counter,field,path);
            }
        }else if (counter==getFieldHeight()*getFieldWidth()){
            setField(field);
            setPath(path);
        }
    }

    @Override
    public void startFinding() {
        while (getPath().size() < getFieldWidth() * getFieldHeight()) {
            setField(new ArrayList<>());
            setPath(new ArrayList<>());
            initField(getFieldWidth(),getFieldHeight());
            findPath(0, getField(), getPath());
        }
    }
}

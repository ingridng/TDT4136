package Øving4.Forsok2;

import java.util.ArrayList;

public class EggPuzzle {

    static int m = 10;
    static int n = 10;
    static int k = 3;
    static int t = 1;
    static double dt = 0.00001;
    static SimulatedAnnealing algorithm = new SimulatedAnnealing();
    static int goal ;

    public static void main(String[] args){
        ArrayList<ArrayList<Boolean>> carton = new ArrayList<ArrayList<Boolean>>();
        for (int i = 0; i < m; i++) {
            ArrayList<Boolean> row = new ArrayList<Boolean>();
            carton.add(row);
            for (int j = 0; j < n ; j++) {
                carton.get(i).add(false);
            }
        }
        goal = Math.min(m,n)*k;
        System.out.println("Startbrett:");
        //randomEggs(carton);
        juks(carton);
        printCarton(carton);
        Carton state = new Carton(carton,k);
        printCarton(algorithm.run(state, t, dt).carton); //evt. m,n også

    }
    public static void printCarton(ArrayList<ArrayList<Boolean>> carton) {
        for (int i = 0; i < carton.size(); i++) {
            System.out.println("");
            for (int j = 0; j < carton.get(0).size(); j++) {
                if(carton.get(i).get(j)){
                    System.out.print(" o");}
                else{System.out.print(" x");}
            }
        }
        System.out.println("");
    }
    public static void randomEggs(ArrayList<ArrayList<Boolean>> carton) {
        int T = 0; //Temperature

        while (T<goal){
            int rand_m = randomInRange(0,m-1);
            int rand_n = randomInRange(0,n-1);
            if(carton.get(rand_m).get(rand_n)){}
            else{carton.get(rand_m).set(rand_n,true);T++;}
        }

    }
    public static int randomInRange(int min, int max){
        int range = (max-min)+1;
        return (int)(Math.random()*range + min);
    }

    public static void juks(ArrayList<ArrayList<Boolean>> carton){
        carton.get(0).set(2,true);
        carton.get(0).set(4,true);
        carton.get(0).set(6,true);
        carton.get(1).set(1,true);
        carton.get(1).set(4,true);
        carton.get(1).set(5,true);
        carton.get(2).set(2,true);
        carton.get(2).set(5,true);
        carton.get(2).set(7,true);
        carton.get(3).set(0,true);
        carton.get(3).set(8,true);
        carton.get(3).set(9,true);
        carton.get(4).set(1,true);
        carton.get(4).set(3,true);
        carton.get(4).set(4,true);
        carton.get(5).set(0,true);
        carton.get(5).set(2,true);
        carton.get(5).set(6,true);
        carton.get(6).set(0,true);
        carton.get(6).set(8,true);
        carton.get(6).set(9,true);
        carton.get(7).set(1,true);
        carton.get(7).set(3,true);
        carton.get(7).set(8,true);
        carton.get(8).set(9,true);
        carton.get(8).set(7,true);
        carton.get(8).set(6,true);
        carton.get(9).set(3,true);
        carton.get(9).set(5,true);
        carton.get(9).set(7,true);


    }

}

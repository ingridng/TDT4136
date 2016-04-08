package Øving4.Forsok2;

import java.util.ArrayList;

/**
 * Created by ingridng on 14.10.15.
 */
public class Carton {
    public ArrayList<ArrayList<Boolean>> carton;
    public int k;
    public double eValue=0;

    public Carton(ArrayList<ArrayList<Boolean>> c, int k){
        carton = c;
        this.k = k;
    }

    public Carton getNeighbor(){
        ArrayList<ArrayList<Boolean>> neighborCarton = carton;
        int rand_m = EggPuzzle.randomInRange(0, neighborCarton.size() - 1);
        int rand_n = EggPuzzle.randomInRange(0, neighborCarton.get(0).size() - 1);

        if(neighborCarton.get(rand_m).get(rand_n)){neighborCarton.get(rand_m).set(rand_n,false);
            int a = EggPuzzle.randomInRange(0, neighborCarton.size() - 1);
            int b = EggPuzzle.randomInRange(0, neighborCarton.get(0).size() - 1);

            while(neighborCarton.get(a).get(b)){
                a = EggPuzzle.randomInRange(0, neighborCarton.size() - 1);
                b = EggPuzzle.randomInRange(0, neighborCarton.get(0).size() - 1);}

            neighborCarton.get(a).set(b,true);}

        else{ neighborCarton.get(rand_m).set(rand_n,true);
            rand_m = EggPuzzle.randomInRange(0, neighborCarton.size() - 1);
            rand_n = EggPuzzle.randomInRange(0, neighborCarton.get(0).size() - 1);

            while(!neighborCarton.get(rand_m).get(rand_n)){
               rand_m = EggPuzzle.randomInRange(0, neighborCarton.size() - 1);
               rand_n = EggPuzzle.randomInRange(0, neighborCarton.get(0).size() - 1);}

            neighborCarton.get(rand_m).set(rand_n,false);}

        return (new Carton(neighborCarton,k));
    }

    public double evaluateCarton(){
        //objektiv funksjon:
        //Teller antall kræsjer i rader, kolonner, og diagonaler. returnerer inversen.
        int row = 0;
        int col = 0;
        int diagonal = 0;
        int kræsjbangbom =0;

        for (int i = 0; i < carton.size(); i++) {
            for (int j = 0; j < carton.get(0).size() ; j++) {
                if (carton.get(i).get(j)){row++;}
                if (carton.get(j).get(i)){col++;}
            }
            kræsjbangbom += Math.max(0,(col-k));
            kræsjbangbom += Math.max(0,(row-k));
            row = 0; col = 0;
        }

        //diagonal-dritt:
        //fra øvre venstre hjørne til og med midten:
        int c = 0;

        for (int i = 0; i < carton.size(); i++) {
            if(carton.get(i).get(c)){diagonal++;}
            int j = i-1;
            while((j>=0) && ((c+1)<=carton.get(0).size())){
                if(carton.get(j).get(c+1)){diagonal++;}
                j--;c++;
            }
            kræsjbangbom += Math.max(0,(diagonal-k));
            c=0;
            diagonal=0;
        }
        //fra nedre høyre hjørne til midten:
        int r = carton.size()-1;

        for (int w = carton.get(0).size()-1; w > 0; w--) {
            if(carton.get(w).get(r)){diagonal++;}
            int j = w+1;
            while((r>0) && ((j<carton.get(0).size()))){
                if(carton.get(j).get(r-1)){diagonal++;}
                j++;r--;
            }
            kræsjbangbom += Math.max(0,(diagonal-k));
            r=carton.size()-1;
            diagonal=0;
        }
        //fra øvre høyre hjørne til og med midten:
        r = 0;

        for (int w = carton.get(0).size()-1; w > -1; w--) {
            if(carton.get(r).get(w)){diagonal++;}
            int j = w+1;
            while((r>=0) && ((j<carton.get(0).size()))){
                if(carton.get(r+1).get(j)){diagonal++;}
                j++;r++;
            }
            kræsjbangbom += Math.max(0,(diagonal-k));
            r=0;
            diagonal=0;
        }

        //fra nedre venstre hjørne til midten:
        r = carton.size()-1;
        for (int w = 0; w < carton.get(0).size()-1; w++) {
            if(carton.get(r).get(w)){diagonal++;}
            int j = w-1;
            while((r>=0) && ((j<carton.get(0).size())) && (j>=0)){
                if(carton.get(r-1).get(j)){diagonal++;}
                j--;r--;
            }
            kræsjbangbom += Math.max(0,(diagonal-k));
            r=carton.size()-1;
            diagonal=0;
        }
        eValue = (100/(kræsjbangbom+1)) ;
        return eValue;
    }

    public double getEValue(){
        return eValue;
    }
}

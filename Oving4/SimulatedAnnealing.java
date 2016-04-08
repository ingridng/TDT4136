package Øving4.Forsok2;

/**
 * Created by ingridng on 14.10.15.
 */
public class SimulatedAnnealing {
    public double q;
    public double p;
    public double x;
    public Carton currentState;
    public Carton best;

    public Carton run(Carton state,double t, double dt){
        currentState = state;
        best = state;

        while(currentState.getEValue()< 100 && t>0){

            Carton Pmax = currentState.getNeighbor();
            q = ((Pmax.evaluateCarton() - currentState.getEValue())/ currentState.getEValue());
            p = Math.min(1, Math.exp(-q/t));
            x = Math.random();
            if(x>p){currentState=Pmax;}
            else{currentState = currentState.getNeighbor();}

            if(best.getEValue() < currentState.getEValue()){best = currentState;}
            if(t-dt>0){t -= dt;}

            else{System.out.println("ingen løsning funnet");
                System.out.println(best.getEValue()+"");

            return best;}
        }
    System.out.println("Løsning funnet: ");
    return best;}
}

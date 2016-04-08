/**
 * Created by ingridng on 30.09.15.
 */
package Øving3;
import java.util.ArrayList;

public class Node {
    public int index;
    public int g; //Forflyttningskostnaden for å komme seg til noden fra startnoden
    public int h; //Avstanden fra noden til målet

    public int f=g+h;

    public ArrayList<Node> children;
    public ArrayList<Node> parents;
    public Node best_parent; //Foreldrenoden med den korteste avstanden

    public Node(int index,int g_value,int h_value){
       //init
        this.index=index;
        g = g_value;
        h = h_value;
        children = new ArrayList<Node>();
        parents = new ArrayList<Node>();
    }

    public void addParent(Node parent){
       if(best_parent==null){best_parent=parent; return;}
       parents.add(parent);

       if(parent.g < best_parent.g) {best_parent = parent; update();}
       //hvis inputnoden,"parent", sin g er bedre enn den nåværende beste g er parent et bedre sted å  komme fra...

    }

    public void update(){
        for (Node c: children) {
            if(g < c.best_parent.g){c.best_parent.g = g;
                c.update();} //oppdaterer rekursivt
        }
    }
    public int get_f_value(){return f=g+h;}
    public int getIndex(){return index;}

    public void findChildren(String task, int indexB, int width){
        if(A1.CanIGoHere((index+1),task)){children.add(new Node(index+1,this.g+1,A1.Heuristic(index+1,indexB,width)));}
        if(A1.CanIGoHere((index-1),task)){children.add(new Node(index-1,this.g+1,A1.Heuristic(index-1,indexB,width)));}
        if(A1.CanIGoHere((index+width),task)){children.add(new Node(index+width,this.g+1,A1.Heuristic(index+width,indexB,width)));}
        if(A1.CanIGoHere((index-width),task)){children.add(new Node(index-width,this.g+1,A1.Heuristic(index-width,indexB,width)));}
    }
    public void findChildrenvol2(String task, int indexB, int width){
        if(A2.CanIGoHere((index+1),task)){children.add(new Node(index+1,this.g+A2.getCost(index + 1, task),A2.Heuristic(index+1,indexB,width)));}
        if(A2.CanIGoHere((index-1),task)){children.add(new Node(index-1,this.g+A2.getCost(index-1,task),A2.Heuristic(index-1,indexB,width)));}
        if(A2.CanIGoHere((index+width),task)){children.add(new Node(index+width,this.g+A2.getCost(index+width,task),A2.Heuristic(index+width,indexB,width)));}
        if(A2.CanIGoHere((index-width),task)){children.add(new Node(index-width,this.g+A2.getCost(index-width,task),A2.Heuristic(index-width,indexB,width)));}
    }

}


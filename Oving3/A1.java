package Øving3;

import java.io.*;
import java.util.*;

/**
 * Created by ingridng on 01.10.15.
 */

public class A1 {

    public static int indexA;
    public static int indexB;
    public static int width;

    public static void main(String[] args) {
        System.out.println(ReadTask("kortesteVeiBrett/board-1-1.txt"));
        indexA = ReadTask("kortesteVeiBrett/board-1-1.txt").indexOf('A');
        indexB = ReadTask("kortesteVeiBrett/board-1-1.txt").indexOf('B');
        width = ReadTask("kortesteVeiBrett/board-1-1.txt").indexOf('\n')+1;
        Node root = new Node(indexA,0,Heuristic(indexA,indexB,width));
        //Djikstra
        Dijkstra(root, ReadTask("board-1-1.txt"));
        //A*
        AStar(root, ReadTask("kortesteVeiBrett/board-1-1.txt"));
        //Bredde først søk:
        BFS(root, ReadTask("board-1-1.txt"));

    }

    public static ArrayList<Integer> Dijkstra (Node root,String task){
        PriorityQueue<Node> open = new PriorityQueue<Node>(200, new Comparator<Node>() {
            public int compare(Node node0,Node node1){
                if(node0.g < node1.g){return -1;}
                else if(node0.g > node1.g){return 1;}
                else{return 0;}
            }
        });
        ArrayList<Integer> closedIndex = new ArrayList<Integer>();
        ArrayList<Integer> path = new ArrayList<Integer>();

        open.add(root);
        closedIndex.add(root.getIndex());
        Node node;

        while (!open.isEmpty()){
            node = open.poll();
            if(isSolution(node.getIndex(),task)){System.out.println("løsningen er funnet, og vi gjør noe");
                System.out.println("Ekspanderte noder:");
                String fix = task;
                for (int beenthere : closedIndex){
                    if(!(fix.substring(beenthere,beenthere+1).equalsIgnoreCase("B")||(fix.substring(beenthere,beenthere+1).equalsIgnoreCase("A")))){
                        fix = fix.substring(0,beenthere) + '+' + fix.substring(beenthere + 1);
                    }}
                System.out.println(fix); //skriver ut ekspanderte noder med "+"-symbol.
                Node current = node;
                while(current.best_parent!=null){
                    path.add(current.getIndex());
                    current=current.best_parent;
                } //finner optimal vei
                String fix2 = fix;
                for (int beenthere : path){
                    if(!(fix2.substring(beenthere,beenthere+1).equalsIgnoreCase("B")||(fix2.substring(beenthere,beenthere+1).equalsIgnoreCase("A")))){
                        fix2 = fix2.substring(0,beenthere) + 'o' + fix2.substring(beenthere + 1);
                    }}System.out.println("vei funnet:");
                System.out.println(fix2);//printer ut korteste vei med "o"-symboler
                return path;}

            node.findChildren(task,0,width);
            for (Node c: node.children){
                c.addParent(node);
                if (!closedIndex.contains(c.getIndex())) {
                    open.add(c);
                    closedIndex.add(c.getIndex());
                }
                else{c.addParent(node);}
            }

        }
        return null;

    }

    public static ArrayList<Integer> BFS (Node root, String task){
        Queue open = new LinkedList();
        ArrayList<Integer> closedIndex = new ArrayList<Integer>();
        ArrayList<Integer> path = new ArrayList<Integer>();
        open.add(root);
        closedIndex.add(root.getIndex());
        Node node;

        while (!open.isEmpty()){
            node = (Node)open.remove();
            if(isSolution(node.getIndex(),task)) {
                System.out.println("Ekspanderte noder:");
                String fix = task;
                for (int beenthere : closedIndex){
                    if(!(fix.substring(beenthere,beenthere+1).equalsIgnoreCase("B")||(fix.substring(beenthere,beenthere+1).equalsIgnoreCase("A")))){
                        fix = fix.substring(0,beenthere) + '+' + fix.substring(beenthere + 1);
                    }}System.out.println(fix);
                Node current = node;
                while(current.best_parent!=null){
                    path.add(current.getIndex());
                    current=current.best_parent;
                }
                String fix2 = task;
                for (int beenthere : path){
                    if(!(fix2.substring(beenthere,beenthere+1).equalsIgnoreCase("B")||(fix2.substring(beenthere,beenthere+1).equalsIgnoreCase("A")))){
                        fix2 = fix2.substring(0,beenthere) + 'o' + fix2.substring(beenthere + 1);
                    }}System.out.println("vei funnet:");
                System.out.println(fix2);//printer ut korteste vei med "o"-symboler
                return path; //returnerer laveste kostnads vei.
            }
            node.findChildren(task,0,width);

            for (Node c: node.children){
                c.addParent(node);
                if (!closedIndex.contains(c.getIndex())) {
                    open.add(c);
                    closedIndex.add(c.getIndex());
                } else{c.addParent(node);} }}
            return null;
    }

    public static ArrayList<Integer> AStar(Node root, String task){

        PriorityQueue<Node> open = new PriorityQueue<Node>(200, new Comparator<Node>() {
            public int compare(Node node0,Node node1){
                if(node0.get_f_value() < node1.get_f_value()){return -1;}
                else if(node0.get_f_value() > node1.get_f_value()){return 1;}
                else{return 0;}
            }
        });

        ArrayList<Integer> closedIndex = new ArrayList<Integer>();
        ArrayList<Integer> path = new ArrayList<Integer>();

        open.add(root);
        closedIndex.add(root.getIndex());
        Node node;

        while(!open.isEmpty()){ //så lenge det finnes uekspanderte noder i open-listen vår...
            node = open.poll(); //tar ut den noden med lavest f-verdi fra prioritetskøen.

            if (isSolution(node.getIndex(),task)){ //Hvis noden er løsningen er vi i mål

                System.out.println("Ekspanderte noder:");
                String fix = task;
                for (int beenthere : closedIndex){
                    if(!(fix.substring(beenthere,beenthere+1).equalsIgnoreCase("B")||(fix.substring(beenthere,beenthere+1).equalsIgnoreCase("A")))){
                    fix = fix.substring(0,beenthere) + '+' + fix.substring(beenthere + 1);
                }}
                System.out.println(fix); //skriver ut ekspanderte noder med "+"-symbol.
                Node current = node;
                while(current.best_parent!=null){
                    path.add(current.getIndex());
                    current=current.best_parent;
                    } //finner optimal vei
                String fix2 = task;
                for (int beenthere : path){
                    if(!(fix2.substring(beenthere,beenthere+1).equalsIgnoreCase("B")||(fix2.substring(beenthere,beenthere+1).equalsIgnoreCase("A")))){
                        fix2 = fix2.substring(0,beenthere) + 'o' + fix2.substring(beenthere + 1);
                    }}System.out.println("vei funnet:");
                System.out.println(fix2);//printer ut korteste vei med "o"-symboler
                return path;

            }


            node.findChildren(task,indexB,width); //genererer barna til noden og legger dem til i nodens tilhørende children-liste.

            for (Node c: node.children){ //går gjennom listen med barn og legger til noden som forelder.
                c.addParent(node);

                //hvis vi ikke har vært der før...
                if (!closedIndex.contains(c.getIndex())) {
                    open.add(c);
                    closedIndex.add(c.getIndex());
                }
                else{c.addParent(node);} //hvis vi alt har ekspandert noden, legger vi bare til forelder.
            }
        }return null;

   }


    public static boolean isSolution(int nodeIndex, String task){ //skjekker om noden er målet = B!
        if(task.charAt(nodeIndex)=='B'){return true;}
        else{return false;}
    }

    public static String ReadTask(String filename) { //tar inn filnavnet og filen om til en streng vi kan bruke viedere
        File file = new File(filename);
        BufferedReader reader = null;
        StringBuilder sb = new StringBuilder();
        String ls = System.getProperty("line.separator");

        try {
            reader = new BufferedReader(new FileReader(file));
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
                sb.append(ls);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        String s = sb.toString();
        return s;
    }

    public static int Heuristic(int index, int indexB, int width ){ //Regner ut avstanden fra en node til målet
        int line = Math.floorDiv(index,width) + 1;
        int lineB = Math.floorDiv(indexB,width) + 1;
        int h = 0;
        int lDifference = Math.abs(lineB-line);

        if(line<lineB){ h = (lDifference + Math.abs(indexB-(index+(lDifference*width)))); }
        else if(lineB<line){ h = (lDifference + Math.abs(indexB-(index-(lDifference*width)))); }
        else{h= Math.abs(index-indexB);}
        return h;
    }

    public static boolean CanIGoHere(int nodeIndex, String task){ //skjekker om noden utfra gitt indeks er gyldig å "gå" på.
        if(nodeIndex >= task.length() || nodeIndex<0 ){return false;}
        if(task.charAt(nodeIndex)=='#'|| task.charAt(nodeIndex)=='\n'){return false;}
        return true;
    }
}
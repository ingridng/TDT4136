package Øving3;

import java.io.*;
import java.util.*;

public class A2 {
    public static int indexA;
    public static int indexB;
    public static int width;


    public static void main(String[] args) {
        System.out.println("oppgave:");

        System.out.println(ReadTask("board-2-4.txt"));
        indexA = ReadTask("board-2-4.txt").indexOf('A');
        indexB = ReadTask("board-2-4.txt").indexOf('B');
        width = ReadTask("board-2-4.txt").indexOf('\n')+1;
        Node root = new Node(indexA,0,Heuristic(indexA,indexB,width));
        AStar(root,ReadTask("board-2-4.txt"));
      //  BFS(root,ReadTask("board-2-4.txt"));
        //Dijkstra(root,ReadTask("board-2-4.txt"));

    }


    public static ArrayList<Integer> AStar(Node root,String task){

        PriorityQueue<Node> open = new PriorityQueue<Node>(200, new Comparator<Node>() {
            @Override
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

        while(!open.isEmpty()){
            node = open.poll();

            if (isSolution(node.getIndex(),task)){

                System.out.println("løsningen er funnet, og vi gjør ett eller annet...");System.out.println("Ekspanderte noder:");
                String fix = task;
                for (int beenthere : closedIndex){
                    if(!(fix.substring(beenthere,beenthere+1).equalsIgnoreCase("B")||(fix.substring(beenthere,beenthere+1).equalsIgnoreCase("A")))){
                        fix = fix.substring(0,beenthere) + '+' + fix.substring(beenthere + 1);
                    }}
                System.out.println(fix);
                Node current = node;

                while(current.best_parent!=null){
                    path.add(current.getIndex());
                    current=current.best_parent;
                }

                String fix2 = task
                        ;
                for (int beenthere : path){
                    if(!(fix2.substring(beenthere,beenthere+1).equalsIgnoreCase("B")||(fix2.substring(beenthere,beenthere+1).equalsIgnoreCase("A")))){
                        fix2 = fix2.substring(0,beenthere) + 'o' + fix2.substring(beenthere + 1);
                    }}System.out.println("vei funnet:");
                System.out.println(fix2);

                return path;}

            node.findChildrenvol2(task, indexB, width);
            //node.findChildren(task,indexB,width);

            for (Node c: node.children){
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


    public static String ReadTask(String filename) {
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
        return s;}

    public static boolean isSolution(int nodeIndex, String task){
        if(task.charAt(nodeIndex)=='B'){return true;}
        else{return false;}
    }

    public static int Heuristic(int index, int indexB, int width ){
        int line = Math.floorDiv(index,width) + 1;
        int lineB = Math.floorDiv(indexB,width) + 1;
        int h = 0;
        int lDifference = Math.abs(lineB-line);

        if(line<lineB){ h = (lDifference + Math.abs(indexB-(index+(lDifference*width)))); }
        else if(lineB<line){ h = (lDifference + Math.abs(indexB-(index-(lDifference*width)))); }
        else{h= Math.abs(index-indexB);}
        return h;
    }

    public static int getCost(int nodeIndex, String task){
        if(nodeIndex >= task.length() || nodeIndex<0 ){System.out.println("FEIL!"); return 0;}

        if(task.charAt(nodeIndex)=='w'){return 100;}
        else if(task.charAt(nodeIndex)=='m'){return 50;}
        else if(task.charAt(nodeIndex)=='f'){return 10;}
        else if(task.charAt(nodeIndex)=='g'){return 5;}
        else if(task.charAt(nodeIndex)=='r'){return 1;}
        else {return 0;}

    }
    public static boolean CanIGoHere(int nodeIndex, String task){
        //System.out.println(task.length()+"");
        if(nodeIndex >= task.length() || nodeIndex<0 ){return false;}
        if(task.charAt(nodeIndex)=='#'|| task.charAt(nodeIndex)=='\n'){return false;}
        return true;
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
                ;System.out.println("Ekspanderte noder:");
                String fix = task;
                for (int beenthere : closedIndex){
                    if(!(fix.substring(beenthere,beenthere+1).equalsIgnoreCase("B")||(fix.substring(beenthere,beenthere+1).equalsIgnoreCase("A")))){
                        fix = fix.substring(0,beenthere) + '+' + fix.substring(beenthere + 1);
                    }}
                System.out.println(fix);
                Node current = node;

                while(current.best_parent!=null){
                    path.add(current.getIndex());
                    current=current.best_parent;
                }

                String fix2 = fix;
                for (int beenthere : path){
                    if(!(fix2.substring(beenthere,beenthere+1).equalsIgnoreCase("B")||(fix2.substring(beenthere,beenthere+1).equalsIgnoreCase("A")))){
                        fix2 = fix2.substring(0,beenthere) + 'o' + fix2.substring(beenthere + 1);
                    }}System.out.println("vei funnet:");
                System.out.println(fix2);

                return path;}

            node.findChildrenvol2(task, 0, width);
            for (Node c: node.children){
                c.addParent(node);
                if (!closedIndex.contains(c.getIndex())) {
                    open.add(c);
                    closedIndex.add(c.getIndex());
                } else{c.addParent(node);} }}
        return null;
    }

    public static ArrayList<Integer> Dijkstra (Node root,String task){
        //lurer på om det er noe som ikke stemmer her .
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
            if(isSolution(node.getIndex(),task)){System.out.println("løsningen er funnet, og vi gjør ett eller annet...");System.out.println("Ekspanderte noder:");
                String fix = task;
                for (int beenthere : closedIndex){
                    if(!(fix.substring(beenthere,beenthere+1).equalsIgnoreCase("B")||(fix.substring(beenthere,beenthere+1).equalsIgnoreCase("A")))){
                        fix = fix.substring(0,beenthere) + '+' + fix.substring(beenthere + 1);
                    }}
                System.out.println(fix);
                Node current = node;

                while(current.best_parent!=null){
                    path.add(current.getIndex());
                    current=current.best_parent;
                }

                String fix2 = fix;
                for (int beenthere : path){
                    if(!(fix2.substring(beenthere,beenthere+1).equalsIgnoreCase("B")||(fix2.substring(beenthere,beenthere+1).equalsIgnoreCase("A")))){
                        fix2 = fix2.substring(0,beenthere) + 'o' + fix2.substring(beenthere + 1);
                    }}System.out.println("vei funnet:");
                System.out.println(fix2);

                return path;}

            node.findChildrenvol2(task, 0, width);
            for (Node c: node.children){
                c.addParent(node);
                if (!closedIndex.contains(c.getIndex())) {
                    open.add(c);
                    closedIndex.add(c.getIndex());
                }
                else{c.addParent(node);}
            }

        }
        return null;}
}

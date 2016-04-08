package Øving5;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class CSP {
    ArrayList<String> variables;
    HashMap<String, ArrayList<String>> domains;
    HashMap<String, HashMap<String, ArrayList<Pair<String>>>> constraints;


    public CSP() {
        this.variables = new ArrayList<String>();
        this.domains = new HashMap<String, ArrayList<String>>();
        this.constraints = new HashMap<String, HashMap<String, ArrayList<Pair<String>>>>();
    }

    //OK
    public void addVariable(String name, ArrayList<String> domain) {
        this.variables.add(name);
        this.domains.put(name, (ArrayList<String>) domain.clone());
        this.constraints.put(name, new HashMap<String, ArrayList<Pair<String>>>());
    }
    //OK
    public ArrayList<Pair<String>> getAllPossiblePairs(ArrayList<String> a, ArrayList<String> b) {
        ArrayList<Pair<String>> pairs = new ArrayList<Pair<String>>();
        for (String x : a) {
            for (String y : b) {
                pairs.add(new Pair<String>(x, y));
            }
        }return pairs;
    }
    //OK
    public ArrayList<Pair<String>> getAllArcs() {
        ArrayList<Pair<String>> arcs = new ArrayList<Pair<String>>();
        for (String i : this.constraints.keySet()) {
            for (String j : this.constraints.get(i).keySet()) {
                arcs.add(new Pair<String>(i, j));
            }
        }return arcs;
    }
    //OK
    public ArrayList<Pair<String>> getAllNeighborArcs(String var) {
        ArrayList<Pair<String>> arcs = new ArrayList<Pair<String>>();
        for (String i : this.constraints.get(var).keySet()) {
            arcs.add(new Pair<String>(i, var));
        }
        return arcs;
    }
    //Ok
    public void addConstraintOneWay(String i, String j) { //gjort litt om her
        if (!this.constraints.get(i).containsKey(j)) {
            this.constraints.get(i).put(j,
                    this.getAllPossiblePairs((this.domains.get(i)),this.domains.get(j)));
        }
        for (Iterator<Pair<String>> iter = this.constraints.get(i).get(j).iterator(); iter.hasNext(); ) {
            Pair<String> valuePair = iter.next();
            if (valuePair.x==valuePair.y) {
                iter.remove();
            }
        }

    }
    public void addAllDifferentContraint(ArrayList<String> variables) {
        for (String i : variables) {
            for (String j : variables) {
                if (!i.equals(j)) {
                    this.addConstraintOneWay(i, j);
                }
            }
        }
    }
    public HashMap<String, ArrayList<String>> backtrackingSearch() {
        HashMap<String, ArrayList<String>> copy = new HashMap<String, ArrayList<String>>();
        for (String key : this.domains.keySet()) {
            copy.put(key, (ArrayList<String>) this.domains.get(key).clone());
        }
        this.inference(copy, this.getAllArcs());
        return this.backtrack(copy);
    }

    public boolean consistent(HashMap<String, ArrayList<String>> copy, String variable, String value) {
        for (String s : this.constraints.get(variable).keySet()) {
            if (copy.get(s).size() == 1 && copy.get(s).get(0).equals(value)) {
                return false;
            }
        }
        return true;
    }

    public HashMap<String, ArrayList<String>> backtrack(HashMap<String, ArrayList<String>> copy) {
        ConstraintSatisfactionProblems.backtrackCalled++;
        String variable = this.selectUnassignedVariable(copy);
        if (variable.trim().equals("")) {
            return copy;
        }
        for (String value : copy.get(variable)) {
            if (this.consistent(copy, variable, value)) {
                HashMap<String, ArrayList<String>> copy2 = new HashMap<String, ArrayList<String>>();
                for (String key : copy.keySet()) {
                    copy2.put(key, (ArrayList<String>) copy.get(key).clone());
                }

                copy2.get(variable).clear();
                copy2.get(variable).add(value);
                if (this.inference(copy2,getAllNeighborArcs(variable))) {
                    HashMap<String, ArrayList<String>> sudokuBoard = backtrack(copy2);
                    if (sudokuBoard != null && WeAreFinished(sudokuBoard)) {
                        return sudokuBoard;
                    }
                }
            }
        }
        ConstraintSatisfactionProblems.backtrackFailed++;
        return null;
    }
    public String selectUnassignedVariable(HashMap<String, ArrayList<String>> copy) {
        for (String key : copy.keySet()) {
            if (copy.get(key).size() > 1) {
                return key;
            }
        }return "";
    }

    public boolean inference(HashMap<String, ArrayList<String>> copy, ArrayList<Pair<String>> queue) {
        ArrayList<Pair<String>> remove = new ArrayList<Pair<String>>();
        ArrayList<Pair<String>> neighbours = new ArrayList<Pair<String>>();
        for (Pair<String> Pair : queue) {
            remove.add(Pair);
            boolean revised = revise(copy, Pair.x, Pair.y);
            if (revised) {
                if (copy.get(Pair.x).size() == 0) {
                    return false;
                }
                neighbours = getAllNeighborArcs(Pair.x);
                neighbours.remove(Pair);

            }
        }
        queue.addAll(neighbours);
        for (Pair<String> p : remove) {
            queue.remove(p);
        }
        return true;
    }
    public boolean revise(HashMap<String, ArrayList<String>> copy, String a, String b) {
        boolean revised = false;
        ArrayList<String> toRemove = new ArrayList<String>();
        for (String x : copy.get(a)) {
            boolean inConstraints = false;
            for (String y : copy.get(b)) {
                Pair<String> PairToCheck = new Pair<String>(x, y);
                if (this.constraints.get(a).get(b).contains(PairToCheck)) inConstraints = true;
            }
            if (!inConstraints) {
                toRemove.add(x);
                revised = true;
            }
        }
        for (String s : toRemove) {
            copy.get(a).remove(s);
        }
        return revised;
    }

    public boolean WeAreFinished(HashMap<String, ArrayList<String>> copy) {
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                for (String s : this.constraints.get(row + "-" + col).keySet()) {
                    if (copy.get(s).size() == 1 && copy.get(s).get(0).equals(copy.get(row + "-" + col).get(0)))
                        return false;
                }
            }
        }
        return true;
    }
    public class Pair<T> {
        public T x, y;

        public Pair(T x, T y) {
            this.x = x;
            this.y = y;
        }

        public boolean equals(Object object) {
            Pair<T> p = (Pair<T>) object;
            return this.x.equals(p.x) && this.y.equals(p.y);
        }
    }

}

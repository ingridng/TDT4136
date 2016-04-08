package Øving5;

import java.io.*;
import java.util.*;

public class ConstraintSatisfactionProblems {

    public static int backtrackCalled = 0;
    public static int backtrackFailed = 0;

    public static void main(String[] args) {
        CSP csp = new CSP();
        ReadTask(csp,"sudokus/veryHard.txt");

        HashMap<String, ArrayList<String>> VariablesToDomainsMapping = csp.backtrackingSearch();
        System.out.println("Backtrack called: " + backtrackCalled);
        System.out.println("Backtrack failed: " + backtrackFailed);
        System.out.println("Solution: ");
        printSudoku(VariablesToDomainsMapping);
    }

    public static void ReadTask(CSP csp,String file) {
        Scanner scanner = null;
        try {
            scanner = new Scanner(new FileReader(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {scanner = new Scanner(new FileReader(file));
        } catch (FileNotFoundException e){System.out.println("tralala, den finner ikke filen...");}
        ArrayList<String> domain = new ArrayList<String>();
        for (int i = 1; i <= 9; i++) {
            domain.add(i + "");
        }
        for (int r = 0; r < 9; r++) {
            String boardRow = scanner.nextLine();
            for (int c = 0; c < 9; c++) {
                if ((boardRow.charAt(c)+"").equalsIgnoreCase("0")) {
                    csp.addVariable(r+"-"+c, domain);
                } else {
                    ArrayList<String> currentDomain = new ArrayList<String>();
                    currentDomain.add(boardRow.charAt(c) + "");
                    csp.addVariable(r+"-"+c, currentDomain);
                }
            }
        }
        scanner.close();

        for (int row = 0; row < 9; row++) {
            ArrayList<String> var = new ArrayList<String>();
            for (int col = 0; col < 9; col++) {
                var.add(row + "-" + col);
            }csp.addAllDifferentContraint(var);
        }
        for (int col = 0; col < 9; col++) {
            ArrayList<String> var = new ArrayList<String>();
            for (int row = 0; row < 9; row++) {
                var.add(row + "-" + col);
            }csp.addAllDifferentContraint(var);
        }
        for (int brow = 0; brow < 3; brow++) {
            for (int boxCol = 0; boxCol < 3; boxCol++) {
                ArrayList<String> var = new ArrayList<String>();
                for (int row = brow * 3; row < (brow + 1) * 3; row++) {
                    for (int col = boxCol * 3; col < (boxCol + 1) * 3; col++) {
                        var.add(row + "-" + col);}
                }csp.addAllDifferentContraint(var);
            }
        }
    }
    public static void printSudoku(HashMap<String, ArrayList<String>> copy) {
        System.out.println("☺-------☺-------☺-------☺");
        System.out.print("| ");
        for (int r = 0; r< 9; r++) {
            for (int c = 0; c < 9; c++) {
               System.out.print(copy.get(r + "-" + c).get(0) + " ");
               if (c%3==2) {System.out.print("| ");}
        }System.out.println();
        if (r%3==2 && r!=8) {
            System.out.println("☺-------☺-------☺-------☺");
            }
       if(r<8)System.out.print("| ");}
        System.out.println("☺-------☺-------☺-------☺");
    }




}

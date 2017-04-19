package org.rlapi;

import java.text.DecimalFormat;
import java.util.Map;

/**
 * Utility class to print maze best action and Q values of actions.
 * @author Alexandre Lima
 */
public class Maze4x3Printer {
    
    public static void print(Map<String, String> policy, ActionValueTable Q){
       DecimalFormat fmt = new DecimalFormat("#,##0.0000");
       
       //Best actions
       System.out.println(policy.get("0") + " " + policy.get("1") + " " + policy.get("2") + " " + policy.get("3"));
       System.out.println(policy.get("4") + " X "+ policy.get("6") + " " + policy.get("7"));
       System.out.println(policy.get("8") + " " + policy.get("9") + " " + policy.get("10") + " " + policy.get("11"));
       
       //Q values
       //line 1
       System.out.println("-------------------------------------");
       System.out.print("|E=" + fmt.format(Q.getValue("0", "E")));
       System.out.print("|E=" + fmt.format(Q.getValue("1", "E")));
       System.out.print("|E=" + fmt.format(Q.getValue("2", "E")));
       System.out.println("|        |");
       //line 2
       System.out.print("|        ");
       System.out.print("|W=" + fmt.format(Q.getValue("1", "W")));
       System.out.print("|W=" + fmt.format(Q.getValue("2", "W")));
       System.out.println("|        |");
       //line 3
       System.out.print("|        ");
       System.out.print("|        ");
       System.out.print("|S=" + fmt.format(Q.getValue("2", "S")));
       System.out.println("|        |");
       //line 4
       System.out.println("-------------------------------------");
       System.out.print("|N=" + fmt.format(Q.getValue("4", "N")));
       System.out.print("|        ");
       System.out.print("|N=" + fmt.format(Q.getValue("6", "N")));
       System.out.println("|        |");
       //line 5
       System.out.print("|S=" + fmt.format(Q.getValue("4", "S")));
       System.out.print("|        ");
       System.out.print("|S=" + fmt.format(Q.getValue("6", "S")));
       System.out.println("|        |");
       //line 6
       System.out.print("|        ");
       System.out.print("|        ");
       System.out.print("|E=" + fmt.format(Q.getValue("6", "E")));
       System.out.println("|        |");
       //line 7
       System.out.println("-------------------------------------");
       System.out.print("|N=" + fmt.format(Q.getValue("8", "N")));
       System.out.print("|        ");
       System.out.print("|N=" + fmt.format(Q.getValue("10", "N")));
       System.out.println("|N=" + fmt.format(Q.getValue("11", "N")) + "|");
       //line 8
       System.out.print("|E=" + fmt.format(Q.getValue("8", "E")));
       System.out.print("|E=" + fmt.format(Q.getValue("9", "E")));
       System.out.print("|E=" + fmt.format(Q.getValue("10", "E")));
       System.out.println("|        |");
       //line 9
       System.out.print("|        ");
       System.out.print("|W=" + fmt.format(Q.getValue("9", "W")));
       System.out.print("|W=" + fmt.format(Q.getValue("10", "W")));
       System.out.println("|W=" + fmt.format(Q.getValue("11", "W")) + "|");
       System.out.println("-------------------------------------");
    }
}

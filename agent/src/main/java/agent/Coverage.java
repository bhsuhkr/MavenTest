package agent;

import it.unimi.dsi.fastutil.ints.IntSet;
import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import java.lang.reflect.Array;
import java.util.*;
import java.util.HashMap;

public class Coverage {
	
	public static HashMap<String, HashMap<String, IntSet>> coverages_testCase;
	public static HashMap<String, IntSet> coverage_testCase;
	public static String name_testCase;
    public static List<String> parameterList; 

    // Called whenever executing a line
    public static void addMethodLine(String className, Integer line){
    	if (coverage_testCase == null) {
    		return;
    	}
		 
    	IntSet lines = coverage_testCase.get(className);
        if (lines != null) {
        	lines.add(line);
        }
        else {
        	lines = new IntOpenHashSet(new int[]{line});
            coverage_testCase.put(className, lines);
        }
    }

    public static void addMethodParameter(String opcode, String varName){
        // System.out.println("opcode: " + opcode + " " + var + "varName: " + varName);
        parameterList.add(opcode + " " + varName);
    }

}

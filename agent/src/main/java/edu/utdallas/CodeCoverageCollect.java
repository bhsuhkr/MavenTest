package edu.utdallas;

import it.unimi.dsi.fastutil.ints.IntSet;
import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import java.lang.reflect.Array;

import java.util.HashMap;

public class CodeCoverageCollect {
	// Need to Map: Test Case -> Class -> Statement Coverage
	public static HashMap<String, HashMap<String, IntSet>> coverages_testCase;
	public static HashMap<String, IntSet> coverage_testCase;
	public static String name_testCase;

    // Called whenever executing a line
    public static void addMethodLine(String className, Integer line){
		 //System.out.println("Here 2");
    	if (coverage_testCase == null) {
    		return;
    	}
    	
		 //System.out.println("Here 3");
		 
    	IntSet lines = coverage_testCase.get(className);
        if (lines != null) {
        	lines.add(line);
        }
        else {
        	lines = new IntOpenHashSet(new int[]{line});
            coverage_testCase.put(className, lines);
        }
    }
}

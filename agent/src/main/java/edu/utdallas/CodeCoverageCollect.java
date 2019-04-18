package edu.utdallas;

import it.unimi.dsi.fastutil.ints.IntSet;
import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import java.lang.reflect.Array;

public class CodeCoverageCollect {
	// Need to Map: Test Case -> Class -> Statement Coverage
	public static Object2ObjectOpenHashMap<String, Object2ObjectOpenHashMap<String, IntSet>> coverages_testCase;
	public static Object2ObjectOpenHashMap<String, IntSet> coverage_testCase;
	public static String name_testCase;

    // Called whenever executing a line
    public static void addMethodLine(String className, Integer line){
		 System.out.println("Here 2");
    	if (coverage_testCase == null) {
    		return;
    	}
    	
		 System.out.println("Here 3");
		 
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

package edu.utdallas;
import java.io.*;
import java.util.Arrays;

import org.junit.runner.Description;
import org.junit.runner.Result;
import org.junit.runner.notification.RunListener;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import java.util.HashMap;
import it.unimi.dsi.fastutil.ints.IntSet;
import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import java.util.*;

public class MyJUnitExecutionListener extends RunListener {
    
    // HashMap<String, HashMap<String, IntSet>> Coverages_testCase;
    // HashMap<String, IntSet> Coverage_testCase;

	// Called before all tests
	public void testRunStarted(Description description) throws Exception {
		if (null == CodeCoverageCollect.coverages_testCase)
		{
			CodeCoverageCollect.coverages_testCase = new HashMap<String, HashMap<String, IntSet>>();
            CodeCoverageCollect.parameterList = new ArrayList<String>();
		}
		
        System.out.println("\nTest Start");
    }
	
	// Called before each @Test
    public void testStarted(Description description) {
    	// Note: Java is pass by value, so this works
    	CodeCoverageCollect.name_testCase = "[TEST] " + description.getClassName() + ":" + description.getMethodName();
    	CodeCoverageCollect.coverage_testCase = new HashMap<String, IntSet>();
        IntSet lines = new IntOpenHashSet(new int[]{});
        CodeCoverageCollect.coverage_testCase.put("Test", lines);
    }
    
    // Called after each @Test Finishes
    public void testFinished(Description description) {
    	CodeCoverageCollect.coverages_testCase.put(CodeCoverageCollect.name_testCase, CodeCoverageCollect.coverage_testCase);
    }
    
    // Called after all tests finish
    public void testRunFinished(Result result) throws IOException {
        System.out.println("Test Finished\n");
        
        // Write to stmt-cov.txt
        File fout = new File("stmt-cov.txt");
        FileOutputStream fos = new FileOutputStream(fout);
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));

        File fout2 = new File("daikon.txt");
        FileOutputStream fos2 = new FileOutputStream(fout2);
        BufferedWriter bw2 = new BufferedWriter(new OutputStreamWriter(fos2));

        StringBuilder builder = new StringBuilder();
        StringBuilder builder2 = new StringBuilder();

        // if(CodeCoverageCollect.parameterList != null){
        System.out.println("Diakon Added ");
        for (String par_name : CodeCoverageCollect.parameterList) {
            builder2.append(par_name + "\n");
        }
        
        for (String testCaseName : CodeCoverageCollect.coverages_testCase.keySet()) {
        	builder.append(testCaseName + "\n");
        	
        	HashMap<String, IntSet> caseCoverage = CodeCoverageCollect.coverages_testCase.get(testCaseName);

            for (String className : caseCoverage.keySet()) {
            	
                int[] lines = caseCoverage.get(className).toIntArray();
            	Arrays.sort(lines);
            	for (int i = 0; i < lines.length; i++) {
                    
                	builder.append(className + ":" + lines[i] + "\n");
				}
            
            }
        }
        bw.write(builder.toString());
        bw.close();
        bw2.write(builder2.toString());
        bw2.close();

        // File fout2 = new File("daikon.txt");
        // FileOutputStream fos2 = new FileOutputStream(fout2);
        // BufferedWriter bw2 = new BufferedWriter(new OutputStreamWriter(fos2));

        // StringBuilder builder2 = new StringBuilder();
        // for (String par_name : CodeCoverageCollect.parameterList) {
        //     builder2.append(par_name + "\n");
        // }

        // bw2.write(builder2.toString());
        // bw2.close();




        
    }
}

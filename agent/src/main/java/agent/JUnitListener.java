package agent;

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

public class JUnitListener extends RunListener {

	public void testRunStarted(Description description) throws Exception {
		if (null == Coverage.coverages_testCase)
		{
			Coverage.coverages_testCase = new HashMap<String, HashMap<String, IntSet>>();
            Coverage.parameterList = new ArrayList<String>();
		}
        System.out.println("\nTest Start");
    }
	
    public void testStarted(Description description) {
    	// Note: Java is pass by value, so this works
    	Coverage.name_testCase = "[TEST] " + description.getClassName() + ":" + description.getMethodName();
    	Coverage.coverage_testCase = new HashMap<String, IntSet>();
        IntSet lines = new IntOpenHashSet(new int[]{});
        Coverage.coverage_testCase.put("Test", lines);
    }
    
    public void testFinished(Description description) {
    	Coverage.coverages_testCase.put(Coverage.name_testCase, Coverage.coverage_testCase);
    }
    
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
        
        System.out.println("Diakon Added ");
        for (String par_name : Coverage.parameterList) {
            builder2.append(par_name + "\n");
        }
        
        for (String testCaseName : Coverage.coverages_testCase.keySet()) {
        	builder.append(testCaseName + "\n");
        	
        	HashMap<String, IntSet> caseCoverage = Coverage.coverages_testCase.get(testCaseName);

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
        
    }
}

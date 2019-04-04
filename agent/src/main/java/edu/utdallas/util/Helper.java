package edu.utdallas.util;

public class Helper {

    //declare an array of string with class name which we need to filter
    private static String[] classesNotToInstrument = {"java", "org/junit", "org/apache/maven", "sun", "edu/utdallas", "junit"};
    static public boolean isClassUnderTest(String className){
        for (String classToFilter: classesNotToInstrument){
            if(className.toLowerCase().contains(classToFilter))
                return false;
        }

        return true;
    }

}

package edu.utdallas;

import java.io.*;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;


class MyClassFileTransform implements ClassFileTransformer {


    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
        
		if (className.equals("FlipTable") || className.equals("FlipTableConverters")) {
         // if (className.startsWith("other")){
            ClassReader cr = new ClassReader(classfileBuffer);
            // ClassWriter cw = new ClassWriter(cr, ClassWriter.COMPUTE_FRAMES);
            ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_FRAMES);
            MyJavaClassTransformVisitor ca = new MyJavaClassTransformVisitor(cw);
            cr.accept(ca, 0);
			
            return cw.toByteArray();
        }
		
        return classfileBuffer;
        
    }
}

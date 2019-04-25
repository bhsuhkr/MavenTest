package edu.utdallas;

import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.AnnotationVisitor;

class MethodTransformVisitor extends MethodVisitor implements Opcodes {
    
    protected int lastVisitedLine;
	protected String className;
	
    public MethodTransformVisitor(final MethodVisitor mv, String nameOfclass) {
        super(ASM5, mv);
        this.className=nameOfclass;
    }

    @Override
    public void visitParameter(String name, int access){
        System.out.println("-****************--------visitParameter Method -****************-----------");
        super.visitParameter(name, access);
    }

    @Override
    public void visitLineNumber(int line, Label start) {
		if (0 != line) {
	    	lastVisitedLine = line;
			mv.visitLdcInsn(className);
			mv.visitLdcInsn(new Integer(line));
			mv.visitMethodInsn(INVOKESTATIC, "java/lang/Integer", "valueOf", "(I)Ljava/lang/Integer;", false);
			mv.visitMethodInsn(INVOKESTATIC, "edu/utdallas/CodeCoverageCollect", "addMethodLine", "(Ljava/lang/String;Ljava/lang/Integer;)V", false);
	    	
		
		//	mv.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
	    	//mv.visitLdcInsn(className + " : " + line);
	    //	mv.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V");
		}
    	super.visitLineNumber(line, start);
	}


    @Override
    public void visitVarInsn(int opcode, int var){
        // System.out.println("-****************--------visitVarInsn Method -****************-----------");
        // System.out.println(opcode + " " + var);
        mv.visitLdcInsn(String.valueOf(opcode));
        mv.visitLdcInsn(String.valueOf(var));
        // mv.visitMethodInsn(INVOKESTATIC, "java/lang/Integer", "valueOf", "(I)Ljava/lang/Integer;", false);
        mv.visitMethodInsn(INVOKESTATIC, "edu/utdallas/CodeCoverageCollect", "addMethodParameter", "(Ljava/lang/String;Ljava/lang/String;)V", false);

        // mv.visitVarIns(opcode, param);
        super.visitVarInsn(opcode, var);   
    }
    

    @Override
    public void visitEnd() {
        // System.out.println("-****************--------visitEnd Method -****************-----------");
        super.visitEnd();
    } 
    // visitlabel

    //  @Override
    // public void visitLabel(Label label){
    //     // System.out.println("-****************--------visitLabel Method -****************-----------");
    //     // mv.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
    //     // mv.visitLdcInsn("line " + line + " executed");
    //     // mv.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false);
    //     super.visitLabel(label);
    // }

    // @Override
    // public void visitLocalVariable(String name,String desc,String signature,Label start,Label end,int index){
    //     // System.out.println("name: " + name + " value: " + desc);
    //     // mv.visitLdcInsn(className);
    //     // mv.visitMethodInsn(INVOKESTATIC, "edu/utdallas/CodeCoverageCollect", "addMethodParameter", "(Ljava/lang/String;)V", false);   d
    //     super.visitLocalVariable(name, desc,signature,start,end,index);
    // }

}
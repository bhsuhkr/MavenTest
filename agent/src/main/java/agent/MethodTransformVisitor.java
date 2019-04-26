package agent;

import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.AnnotationVisitor;

class MethodTransformVisitor extends MethodVisitor implements Opcodes {
    
    protected int lastVisitedLine;
	protected String className;
    static String varName = null;
	
    public MethodTransformVisitor(final MethodVisitor mv, String nameOfclass) {
        super(ASM5, mv);
        this.className=nameOfclass;
    }

    @Override
    public void visitParameter(String name, int access){
        super.visitParameter(name, access);
    }

    @Override
    public void visitLineNumber(int line, Label start) {
		if (0 != line) {
	    	lastVisitedLine = line;
			mv.visitLdcInsn(className);
			mv.visitLdcInsn(new Integer(line));
			mv.visitMethodInsn(INVOKESTATIC, "java/lang/Integer", "valueOf", "(I)Ljava/lang/Integer;", false);
			mv.visitMethodInsn(INVOKESTATIC, "agent/Coverage", "addMethodLine", "(Ljava/lang/String;Ljava/lang/Integer;)V", false);
		}
    	super.visitLineNumber(line, start);
	}

    @Override
    public void visitLocalVariable(String name, String desc, String signature, Label start, Label end, int index){
        varName = name;
        super.visitLocalVariable(name, desc, signature, start, end, index);
    }

    @Override
    public void visitVarInsn(int opcode, int var){
        mv.visitLdcInsn(String.valueOf(opcode));
        if(varName != null && !varName.isEmpty()){
            mv.visitLdcInsn(varName);
        }else{
            mv.visitLdcInsn("None");    
        }
        mv.visitMethodInsn(INVOKESTATIC, "agent/Coverage", "addMethodParameter", "(Ljava/lang/String;Ljava/lang/String;)V", false);
        super.visitVarInsn(opcode, var);   
    }
    

    @Override
    public void visitEnd() {
        super.visitEnd();
    } 
    

    //  @Override
    // public void visitLabel(Label label){
    //     // System.out.println("-****************--------visitLabel Method -****************-----------");
    //     // mv.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
    //     // mv.visitLdcInsn("line " + line + " executed");
    //     // mv.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false);
    //     super.visitLabel(label);
    // }

    

}
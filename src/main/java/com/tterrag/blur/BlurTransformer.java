/*    */ package com.tterrag.blur;
/*    */ 
/*    */ import java.util.Iterator;
/*    */ import net.minecraft.launchwrapper.IClassTransformer;
/*    */ import org.objectweb.asm.ClassReader;
/*    */ import org.objectweb.asm.ClassVisitor;
/*    */ import org.objectweb.asm.ClassWriter;
/*    */ import org.objectweb.asm.tree.AbstractInsnNode;
/*    */ import org.objectweb.asm.tree.ClassNode;
/*    */ import org.objectweb.asm.tree.InsnNode;
/*    */ import org.objectweb.asm.tree.MethodInsnNode;
/*    */ import org.objectweb.asm.tree.MethodNode;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BlurTransformer
/*    */   implements IClassTransformer
/*    */ {
/*    */   private static final String GUI_SCREEN_CLASS_NAME = "net.minecraft.client.gui.GuiScreen";
/*    */   private static final String DRAW_WORLD_BAGKGROUND_METHOD = "drawWorldBackground";
/*    */   private static final String DRAW_WORLD_BAGKGROUND_METHOD_OBF = "func_146270_b";
/*    */   private static final String BLUR_MAIN_CLASS = "com/tterrag/blur/Blur";
/*    */   private static final String COLOR_HOOK_METHOD_NAME = "getBackgroundColor";
/*    */   private static final String COLOR_HOOK_METHOD_DESC = "(Z)I";
/*    */   
/*    */   public byte[] transform(String name, String transformedName, byte[] basicClass) {
/* 29 */     if (transformedName.equals("net.minecraft.client.gui.GuiScreen")) {
/* 30 */       System.out.println("Transforming Class [" + transformedName + "], Method [" + "drawWorldBackground" + "]");
/*    */       
/* 32 */       ClassNode classNode = new ClassNode();
/* 33 */       ClassReader classReader = new ClassReader(basicClass);
/* 34 */       classReader.accept((ClassVisitor)classNode, 0);
/*    */       
/* 36 */       Iterator<MethodNode> methods = classNode.methods.iterator();
/*    */       
/* 38 */       while (methods.hasNext()) {
/* 39 */         MethodNode m = methods.next();
/* 40 */         if (m.name.equals("drawWorldBackground") || m.name.equals("func_146270_b")) {
/* 41 */           for (int i = 0; i < m.instructions.size(); i++) {
/* 42 */             AbstractInsnNode next = m.instructions.get(i);
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */             
/* 51 */             if (next.getOpcode() == 18) {
/* 52 */               System.out.println("Modifying GUI background darkness... ");
/* 53 */               MethodInsnNode methodInsnNode = new MethodInsnNode(184, "com/tterrag/blur/Blur", "getBackgroundColor", "(Z)I", false);
/* 54 */               AbstractInsnNode colorHook2 = methodInsnNode.clone(null);
/*    */ 
/*    */               
/* 57 */               m.instructions.set(next, (AbstractInsnNode)methodInsnNode);
/* 58 */               m.instructions.set(methodInsnNode.getNext(), colorHook2);
/*    */ 
/*    */               
/* 61 */               m.instructions.insertBefore((AbstractInsnNode)methodInsnNode, (AbstractInsnNode)new InsnNode(4));
/* 62 */               m.instructions.insertBefore(colorHook2, (AbstractInsnNode)new InsnNode(3));
/*    */               
/*    */               break;
/*    */             } 
/*    */           } 
/*    */           break;
/*    */         } 
/*    */       } 
/* 70 */       ClassWriter cw = new ClassWriter(1);
/* 71 */       classNode.accept((ClassVisitor)cw);
/* 72 */       System.out.println("Transforming " + transformedName + " Finished.");
/* 73 */       return cw.toByteArray();
/*    */     } 
/*    */     
/* 76 */     return basicClass;
/*    */   }
/*    */ }


/* Location:              C:\Users\030\Documents\Tencent Files\814217012\FileRecv\MobileFile\Blur-1.0.4-14.jar!\com\tterrag\blur\BlurTransformer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
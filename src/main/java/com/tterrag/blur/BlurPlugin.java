/*    */ package com.tterrag.blur;
/*    */ 
/*    */ import java.util.Map;
/*    */ import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;
/*    */ import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin.SortingIndex;
/*    */ 
/*    */ @SortingIndex(2147483647)
/*    */ public class BlurPlugin
/*    */   implements IFMLLoadingPlugin
/*    */ {
/*    */   public String[] getASMTransformerClass() {
/* 12 */     return new String[] { "com.tterrag.blur.BlurTransformer" };
/*    */   }
/*    */ 
/*    */   
/*    */   public String getModContainerClass() {
/* 17 */     return null;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getSetupClass() {
/* 22 */     return null;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void injectData(Map<String, Object> data) {}
/*    */ 
/*    */   
/*    */   public String getAccessTransformerClass() {
/* 31 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\030\Documents\Tencent Files\814217012\FileRecv\MobileFile\Blur-1.0.4-14.jar!\com\tterrag\blur\BlurPlugin.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
/*    */ package com.tterrag.blur.config;
/*    */ 
/*    */ import java.util.Set;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.gui.GuiScreen;
/*    */ import net.minecraftforge.fml.client.IModGuiFactory;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BlurGuiFactory
/*    */   implements IModGuiFactory
/*    */ {
/*    */   public void initialize(Minecraft minecraftInstance) {}
/*    */   
/*    */   public Class<? extends GuiScreen> mainConfigGuiClass() {
/* 16 */     return (Class)BlurConfigGui.class;
/*    */   }
/*    */ 
/*    */   
/*    */   public Set<IModGuiFactory.RuntimeOptionCategoryElement> runtimeGuiCategories() {
/* 21 */     return null;
/*    */   }
/*    */ 
/*    */   
/*    */   @Deprecated
/*    */   public IModGuiFactory.RuntimeOptionGuiHandler getHandlerFor(IModGuiFactory.RuntimeOptionCategoryElement element) {
/* 27 */     return null;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean hasConfigGui() {
/* 33 */     return true;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public GuiScreen createConfigGui(GuiScreen parentScreen) {
/* 39 */     return (GuiScreen)new BlurConfigGui(parentScreen);
/*    */   }
/*    */ }


/* Location:              C:\Users\030\Documents\Tencent Files\814217012\FileRecv\MobileFile\Blur-1.0.4-14.jar!\com\tterrag\blur\config\BlurGuiFactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
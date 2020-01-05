/*    */ package com.tterrag.blur.config;
/*    */ 
/*    */ import com.tterrag.blur.Blur;
/*    */ import javax.annotation.Nonnull;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.gui.GuiScreen;
/*    */ import net.minecraft.client.renderer.Tessellator;
/*    */ import net.minecraft.client.resources.I18n;
/*    */ import net.minecraftforge.common.config.ConfigElement;
/*    */ import net.minecraftforge.fml.client.config.GuiConfig;
/*    */ import net.minecraftforge.fml.client.config.GuiConfigEntries;
/*    */ 
/*    */ 
/*    */ public class BlurConfigGui
/*    */   extends GuiConfig
/*    */ {
/*    */   public BlurConfigGui(GuiScreen parentScreen) {
/* 18 */     super(parentScreen, (new ConfigElement(Blur.instance.config.getCategory("general"))).getChildElements(), "blur", false, false, I18n.func_135052_a("blur.config.title", new Object[0]));
/*    */   }
/*    */ 
/*    */   
/*    */   public void func_73866_w_() {
/* 23 */     if (this.entryList == null || this.needsRefresh) {
/*    */       
/* 25 */       this.entryList = new GuiConfigEntries(this, this.field_146297_k)
/*    */         {
/*    */           protected void drawContainerBackground(@Nonnull Tessellator tessellator)
/*    */           {
/* 29 */             if (this.field_148161_k.field_71441_e == null) {
/* 30 */               super.drawContainerBackground(tessellator);
/*    */             }
/*    */           }
/*    */         };
/* 34 */       this.needsRefresh = false;
/*    */     } 
/* 36 */     super.func_73866_w_();
/*    */   }
/*    */ 
/*    */   
/*    */   public void func_146276_q_() {
/* 41 */     func_146270_b(0);
/*    */   }
/*    */ }


/* Location:              C:\Users\030\Documents\Tencent Files\814217012\FileRecv\MobileFile\Blur-1.0.4-14.jar!\com\tterrag\blur\config\BlurConfigGui.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
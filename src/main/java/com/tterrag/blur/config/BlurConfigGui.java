 package com.tterrag.blur.config;
 
 import com.tterrag.blur.Blur;
 import javax.annotation.Nonnull;
 import net.minecraft.client.Minecraft;
 import net.minecraft.client.gui.GuiScreen;
 import net.minecraft.client.renderer.Tessellator;
 import net.minecraft.client.resources.I18n;
 import net.minecraftforge.common.config.ConfigElement;
 import net.minecraftforge.fml.client.config.GuiConfig;
 import net.minecraftforge.fml.client.config.GuiConfigEntries;
 
 
 public class BlurConfigGui
   extends GuiConfig
 {
   public BlurConfigGui(GuiScreen parentScreen) {
     super(parentScreen, (new ConfigElement(Blur.instance.config.getCategory("general"))).getChildElements(), "blur", false, false, I18n.func_135052_a("blur.config.title", new Object[0]));
   }
 
   
   public void func_73866_w_() {
     if (this.entryList == null || this.needsRefresh) {
       
       this.entryList = new GuiConfigEntries(this, this.field_146297_k)
         {
           protected void drawContainerBackground(@Nonnull Tessellator tessellator)
           {
             if (this.field_148161_k.field_71441_e == null) {
               super.drawContainerBackground(tessellator);
             }
           }
         };
       this.needsRefresh = false;
     } 
     super.func_73866_w_();
   }
 
   
   public void func_146276_q_() {
     func_146270_b(0);
   }
 }



/*     */ package com.tterrag.blur;
/*     */ 
/*     */ import com.google.common.base.Throwables;
/*     */ import com.tterrag.blur.util.ShaderResourcePack;
/*     */ import java.io.File;
/*     */ import java.lang.reflect.Field;
/*     */ import java.util.List;
/*     */ import javax.annotation.Nonnull;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.gui.GuiChat;
/*     */ import net.minecraft.client.renderer.EntityRenderer;
/*     */ import net.minecraft.client.resources.IResourceManagerReloadListener;
/*     */ import net.minecraft.client.resources.SimpleReloadableResourceManager;
/*     */ import net.minecraft.client.shader.Shader;
/*     */ import net.minecraft.client.shader.ShaderGroup;
/*     */ import net.minecraft.client.shader.ShaderUniform;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraftforge.client.event.GuiOpenEvent;
/*     */ import net.minecraftforge.common.MinecraftForge;
/*     */ import net.minecraftforge.common.config.Configuration;
/*     */ import net.minecraftforge.fml.client.event.ConfigChangedEvent;
/*     */ import net.minecraftforge.fml.common.Mod;
/*     */ import net.minecraftforge.fml.common.Mod.EventHandler;
/*     */ import net.minecraftforge.fml.common.Mod.Instance;
/*     */ import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
/*     */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*     */ import net.minecraftforge.fml.common.gameevent.TickEvent;
/*     */ import net.minecraftforge.fml.relauncher.ReflectionHelper;
/*     */ import org.apache.commons.lang3.ArrayUtils;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Mod(modid = "blur", name = "Blur", version = "1.0.4-14", acceptedMinecraftVersions = "[1.9, 1.13)", clientSideOnly = true, guiFactory = "com.tterrag.blur.config.BlurGuiFactory")
/*     */ public class Blur
/*     */ {
/*     */   public static final String MODID = "blur";
/*     */   public static final String MOD_NAME = "Blur";
/*     */   public static final String VERSION = "1.0.4-14";
/*     */   @Instance
/*     */   public static Blur instance;
/*     */   public Configuration config;
/*     */   private String[] blurExclusions;
/*     */   private Field _listShaders;
/*     */   private long start;
/*     */   private int fadeTime;
/*     */   public int radius;
/*     */   private int colorFirst;
/*     */   private int colorSecond;
/*     */   @Nonnull
/*  59 */   private ShaderResourcePack dummyPack = new ShaderResourcePack();
/*     */ 
/*     */ 
/*     */   
/*     */   public Blur() {
/*  64 */     ((List<ShaderResourcePack>)ReflectionHelper.getPrivateValue(Minecraft.class, Minecraft.func_71410_x(), new String[] { "field_110449_ao", "defaultResourcePacks" })).add(this.dummyPack);
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void preInit(FMLPreInitializationEvent event) {
/*  69 */     MinecraftForge.EVENT_BUS.register(this);
/*     */ 
/*     */     
/*  72 */     ((SimpleReloadableResourceManager)Minecraft.func_71410_x().func_110442_L()).func_110542_a((IResourceManagerReloadListener)this.dummyPack);
/*     */     
/*  74 */     this.config = new Configuration(new File(event.getModConfigurationDirectory(), "blur.cfg"));
/*  75 */     saveConfig();
/*     */   }
/*     */ 
/*     */   
/*     */   private void saveConfig() {
/*  80 */     this.blurExclusions = this.config.getStringList("guiExclusions", "general", new String[] { GuiChat.class
/*  81 */           .getName() }, "A list of classes to be excluded from the blur shader.");
/*     */ 
/*     */     
/*  84 */     this.fadeTime = this.config.getInt("fadeTime", "general", 200, 0, 2147483647, "The time it takes for the blur to fade in, in ms.");
/*     */     
/*  86 */     int r = this.config.getInt("radius", "general", 12, 1, 100, "The radius of the blur effect. This controls how \"strong\" the blur is.");
/*  87 */     if (r != this.radius) {
/*  88 */       this.radius = r;
/*  89 */       this.dummyPack.func_110549_a(Minecraft.func_71410_x().func_110442_L());
/*  90 */       if ((Minecraft.func_71410_x()).field_71441_e != null) {
/*  91 */         (Minecraft.func_71410_x()).field_71460_t.func_181022_b();
/*     */       }
/*     */     } 
/*     */     
/*  95 */     this.colorFirst = Integer.parseUnsignedInt(this.config
/*  96 */         .getString("gradientStartColor", "general", "75000000", "The start color of the background gradient. Given in ARGB hex."), 16);
/*     */ 
/*     */ 
/*     */     
/* 100 */     this.colorSecond = Integer.parseUnsignedInt(this.config
/* 101 */         .getString("gradientEndColor", "general", "75000000", "The end color of the background gradient. Given in ARGB hex."), 16);
/*     */ 
/*     */ 
/*     */     
/* 105 */     this.config.save();
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
/* 110 */     if (event.getModID().equals("blur")) {
/* 111 */       saveConfig();
/*     */     }
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onGuiChange(GuiOpenEvent event) {
/* 117 */     if (this._listShaders == null) {
/* 118 */       this._listShaders = ReflectionHelper.findField(ShaderGroup.class, new String[] { "field_148031_d", "listShaders" });
/*     */     }
/* 120 */     if ((Minecraft.func_71410_x()).field_71441_e != null) {
/* 121 */       EntityRenderer er = (Minecraft.func_71410_x()).field_71460_t;
/* 122 */       boolean excluded = (event.getGui() == null || ArrayUtils.contains((Object[])this.blurExclusions, event.getGui().getClass().getName()));
/* 123 */       if (!er.func_147702_a() && !excluded) {
/* 124 */         er.func_175069_a(new ResourceLocation("shaders/post/fade_in_blur.json"));
/* 125 */         this.start = System.currentTimeMillis();
/* 126 */       } else if (er.func_147702_a() && excluded) {
/* 127 */         er.func_181022_b();
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private float getProgress() {
/* 133 */     return Math.min((float)(System.currentTimeMillis() - this.start) / this.fadeTime, 1.0F);
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onRenderTick(TickEvent.RenderTickEvent event) {
/* 138 */     if (event.phase == TickEvent.Phase.END && (Minecraft.func_71410_x()).field_71462_r != null && (Minecraft.func_71410_x()).field_71460_t.func_147702_a()) {
/* 139 */       ShaderGroup sg = (Minecraft.func_71410_x()).field_71460_t.func_147706_e();
/*     */       
/*     */       try {
/* 142 */         List<Shader> shaders = (List<Shader>)this._listShaders.get(sg);
/* 143 */         for (Shader s : shaders) {
/* 144 */           ShaderUniform su = s.func_148043_c().func_147991_a("Progress");
/* 145 */           if (su != null) {
/* 146 */             su.func_148090_a(getProgress());
/*     */           }
/*     */         } 
/* 149 */       } catch (IllegalArgumentException|IllegalAccessException e) {
/* 150 */         Throwables.propagate(e);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public static int getBackgroundColor(boolean second) {
/* 156 */     int color = second ? instance.colorSecond : instance.colorFirst;
/* 157 */     int a = color >>> 24;
/* 158 */     int r = color >> 16 & 0xFF;
/* 159 */     int b = color >> 8 & 0xFF;
/* 160 */     int g = color & 0xFF;
/* 161 */     float prog = instance.getProgress();
/* 162 */     a = (int)(a * prog);
/* 163 */     r = (int)(r * prog);
/* 164 */     g = (int)(g * prog);
/* 165 */     b = (int)(b * prog);
/* 166 */     return a << 24 | r << 16 | b << 8 | g;
/*     */   }
/*     */ }


/* Location:              C:\Users\030\Documents\Tencent Files\814217012\FileRecv\MobileFile\Blur-1.0.4-14.jar!\com\tterrag\blur\Blur.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
 package com.tterrag.blur;
 
 import com.google.common.base.Throwables;
 import com.tterrag.blur.util.ShaderResourcePack;
 import java.io.File;
 import java.lang.reflect.Field;
 import java.util.List;
 import javax.annotation.Nonnull;
 import net.minecraft.client.Minecraft;
 import net.minecraft.client.gui.GuiChat;
 import net.minecraft.client.renderer.EntityRenderer;
 import net.minecraft.client.resources.IResourceManagerReloadListener;
 import net.minecraft.client.resources.SimpleReloadableResourceManager;
 import net.minecraft.client.shader.Shader;
 import net.minecraft.client.shader.ShaderGroup;
 import net.minecraft.client.shader.ShaderUniform;
 import net.minecraft.util.ResourceLocation;
 import net.minecraftforge.client.event.GuiOpenEvent;
 import net.minecraftforge.common.MinecraftForge;
 import net.minecraftforge.common.config.Configuration;
 import net.minecraftforge.fml.client.event.ConfigChangedEvent;
 import net.minecraftforge.fml.common.Mod;
 import net.minecraftforge.fml.common.Mod.EventHandler;
 import net.minecraftforge.fml.common.Mod.Instance;
 import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
 import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
 import net.minecraftforge.fml.common.gameevent.TickEvent;
 import net.minecraftforge.fml.relauncher.ReflectionHelper;
 import org.apache.commons.lang3.ArrayUtils;
 
 @Mod(modid = "blur", name = "Blur", version = "1.0.4-14", acceptedMinecraftVersions = "[1.9, 1.13)", clientSideOnly = true, guiFactory = "com.tterrag.blur.config.BlurGuiFactory")
 public class Blur
 {
   public static final String MODID = "blur";
   public static final String MOD_NAME = "Blur";
   public static final String VERSION = "1.0.4-14";
   @Instance
   public static Blur instance;
   public Configuration config;
   private String[] blurExclusions;
   private Field _listShaders;
   private long start;
   private int fadeTime;
   public int radius;
   private int colorFirst;
   private int colorSecond;
   @Nonnull
   private ShaderResourcePack dummyPack = new ShaderResourcePack();
 
 
   
   public Blur() {
     ((List<ShaderResourcePack>)ReflectionHelper.getPrivateValue(Minecraft.class, Minecraft.func_71410_x(), new String[] { "field_110449_ao", "defaultResourcePacks" })).add(this.dummyPack);
   }
   
   @EventHandler
   public void preInit(FMLPreInitializationEvent event) {
     MinecraftForge.EVENT_BUS.register(this);
 
     
     ((SimpleReloadableResourceManager)Minecraft.func_71410_x().func_110442_L()).func_110542_a((IResourceManagerReloadListener)this.dummyPack);
     
     this.config = new Configuration(new File(event.getModConfigurationDirectory(), "blur.cfg"));
     saveConfig();
   }
 
   
   private void saveConfig() {
     this.blurExclusions = this.config.getStringList("guiExclusions", "general", new String[] { GuiChat.class
           .getName() }, "A list of classes to be excluded from the blur shader.");
 
     
     this.fadeTime = this.config.getInt("fadeTime", "general", 200, 0, 2147483647, "The time it takes for the blur to fade in, in ms.");
     
     int r = this.config.getInt("radius", "general", 12, 1, 100, "The radius of the blur effect. This controls how \"strong\" the blur is.");
     if (r != this.radius) {
       this.radius = r;
       this.dummyPack.func_110549_a(Minecraft.func_71410_x().func_110442_L());
       if ((Minecraft.func_71410_x()).field_71441_e != null) {
         (Minecraft.func_71410_x()).field_71460_t.func_181022_b();
       }
     } 
     
     this.colorFirst = Integer.parseUnsignedInt(this.config
         .getString("gradientStartColor", "general", "75000000", "The start color of the background gradient. Given in ARGB hex."), 16);
 
 
     
     this.colorSecond = Integer.parseUnsignedInt(this.config
         .getString("gradientEndColor", "general", "75000000", "The end color of the background gradient. Given in ARGB hex."), 16);
 
 
     
     this.config.save();
   }
   
   @SubscribeEvent
   public void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
     if (event.getModID().equals("blur")) {
       saveConfig();
     }
   }
   
   @SubscribeEvent
   public void onGuiChange(GuiOpenEvent event) {
     if (this._listShaders == null) {
       this._listShaders = ReflectionHelper.findField(ShaderGroup.class, new String[] { "field_148031_d", "listShaders" });
     }
     if ((Minecraft.func_71410_x()).field_71441_e != null) {
       EntityRenderer er = (Minecraft.func_71410_x()).field_71460_t;
       boolean excluded = (event.getGui() == null || ArrayUtils.contains((Object[])this.blurExclusions, event.getGui().getClass().getName()));
       if (!er.func_147702_a() && !excluded) {
         er.func_175069_a(new ResourceLocation("shaders/post/fade_in_blur.json"));
         this.start = System.currentTimeMillis();
       } else if (er.func_147702_a() && excluded) {
         er.func_181022_b();
       } 
     } 
   }
   
   private float getProgress() {
     return Math.min((float)(System.currentTimeMillis() - this.start) / this.fadeTime, 1.0F);
   }
   
   @SubscribeEvent
   public void onRenderTick(TickEvent.RenderTickEvent event) {
     if (event.phase == TickEvent.Phase.END && (Minecraft.func_71410_x()).field_71462_r != null && (Minecraft.func_71410_x()).field_71460_t.func_147702_a()) {
       ShaderGroup sg = (Minecraft.func_71410_x()).field_71460_t.func_147706_e();
       
       try {
         List<Shader> shaders = (List<Shader>)this._listShaders.get(sg);
         for (Shader s : shaders) {
           ShaderUniform su = s.func_148043_c().func_147991_a("Progress");
           if (su != null) {
             su.func_148090_a(getProgress());
           }
         } 
       } catch (IllegalArgumentException|IllegalAccessException e) {
         Throwables.propagate(e);
       } 
     } 
   }
   
   public static int getBackgroundColor(boolean second) {
     int color = second ? instance.colorSecond : instance.colorFirst;
     int a = color >>> 24;
     int r = color >> 16 & 0xFF;
     int b = color >> 8 & 0xFF;
     int g = color & 0xFF;
     float prog = instance.getProgress();
     a = (int)(a * prog);
     r = (int)(r * prog);
     g = (int)(g * prog);
     b = (int)(b * prog);
     return a << 24 | r << 16 | b << 8 | g;
   }
 }



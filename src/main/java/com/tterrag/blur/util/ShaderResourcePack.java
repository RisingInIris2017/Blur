/*    */ package com.tterrag.blur.util;
/*    */ 
/*    */ import com.google.common.collect.ImmutableSet;
/*    */ import com.tterrag.blur.Blur;
/*    */ import java.awt.image.BufferedImage;
/*    */ import java.io.ByteArrayInputStream;
/*    */ import java.io.FileNotFoundException;
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ import java.util.Scanner;
/*    */ import java.util.Set;
/*    */ import net.minecraft.client.resources.IResourceManager;
/*    */ import net.minecraft.client.resources.IResourceManagerReloadListener;
/*    */ import net.minecraft.client.resources.IResourcePack;
/*    */ import net.minecraft.client.resources.data.MetadataSerializer;
/*    */ import net.minecraft.client.resources.data.PackMetadataSection;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ import net.minecraft.util.text.ITextComponent;
/*    */ import net.minecraft.util.text.TextComponentString;
/*    */ 
/*    */ 
/*    */ public class ShaderResourcePack
/*    */   implements IResourcePack, IResourceManagerReloadListener
/*    */ {
/*    */   protected boolean validPath(ResourceLocation location) {
/* 28 */     return (location.func_110624_b().equals("minecraft") && location.func_110623_a().startsWith("shaders/"));
/*    */   }
/*    */   
/* 31 */   private final Map<ResourceLocation, String> loadedData = new HashMap<>();
/*    */ 
/*    */   
/*    */   public InputStream func_110590_a(ResourceLocation location) throws IOException {
/* 35 */     if (validPath(location)) {
/* 36 */       String s = this.loadedData.computeIfAbsent(location, loc -> {
/*    */             InputStream in = Blur.class.getResourceAsStream("/" + location.func_110623_a());
/*    */             
/*    */             StringBuilder data = new StringBuilder();
/*    */             Scanner scan = new Scanner(in);
/*    */             try {
/*    */               while (scan.hasNextLine()) {
/*    */                 data.append(scan.nextLine().replaceAll("@radius@", Integer.toString(Blur.instance.radius))).append('\n');
/*    */               }
/*    */             } finally {
/*    */               scan.close();
/*    */             } 
/*    */             return data.toString();
/*    */           });
/* 50 */       return new ByteArrayInputStream(s.getBytes());
/*    */     } 
/* 52 */     throw new FileNotFoundException(location.toString());
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean func_110589_b(ResourceLocation location) {
/* 57 */     return (validPath(location) && Blur.class.getResource("/" + location.func_110623_a()) != null);
/*    */   }
/*    */ 
/*    */   
/*    */   public Set<String> func_110587_b() {
/* 62 */     return (Set<String>)ImmutableSet.of("minecraft");
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public <T extends net.minecraft.client.resources.data.IMetadataSection> T func_135058_a(MetadataSerializer metadataSerializer, String metadataSectionName) throws IOException {
/* 68 */     if ("pack".equals(metadataSectionName)) {
/* 69 */       return (T)new PackMetadataSection((ITextComponent)new TextComponentString("Blur's default shaders"), 3);
/*    */     }
/* 71 */     return null;
/*    */   }
/*    */ 
/*    */   
/*    */   public BufferedImage func_110586_a() throws IOException {
/* 76 */     throw new FileNotFoundException("pack.png");
/*    */   }
/*    */ 
/*    */   
/*    */   public String func_130077_b() {
/* 81 */     return "Blur dummy resource pack";
/*    */   }
/*    */ 
/*    */   
/*    */   public void func_110549_a(IResourceManager resourceManager) {
/* 86 */     this.loadedData.clear();
/*    */   }
/*    */ }


/* Location:              C:\Users\030\Documents\Tencent Files\814217012\FileRecv\MobileFile\Blur-1.0.4-14.jar!\com\tterrag\blu\\util\ShaderResourcePack.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
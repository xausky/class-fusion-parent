package io.github.xausky.cfmp.utils;

import org.apache.commons.io.IOUtils;
import org.objectweb.asm.tree.ClassNode;

import java.io.File;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Map;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * Created by xausky on 10/31/16.
 */
public class JarParser {
    public static void parser(File jar, Map<String,ClassNode> classes, Map<String,Set<String>> itfs, Map<String,String> imps, Set<String> fusions){
        JarFile jarFile = null;
        try {
            jarFile = new JarFile(jar);
            Enumeration<JarEntry> jarEntrys = jarFile.entries();
            while (jarEntrys.hasMoreElements()){
                JarEntry jarEntry = jarEntrys.nextElement();
                if(jarEntry.getName().endsWith(".class")){
                    InputStream inputStream = jarFile.getInputStream(jarEntry);
                    byte[] classData = IOUtils.toByteArray(inputStream);
                    inputStream.close();
                    ClassParser.parser(classData,classes,itfs,imps,fusions);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(jarFile != null){
                try {
                    jarFile.close();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    }
}

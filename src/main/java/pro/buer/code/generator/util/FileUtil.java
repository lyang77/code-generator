package pro.buer.code.generator.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 *
 *
 * create date:2017/7/29
 * remark:
 */
public class FileUtil{

    public static String readFile(String fileName){
        File file = new File(FileUtil.class.getResource(fileName).getFile());
        return readFile(file);
    }

    public static String readFile(File file){
        StringBuilder result = new StringBuilder();
        try{
            BufferedReader br = new BufferedReader(new FileReader(file));
            String s;
            while((s = br.readLine()) != null){
                result.append(System.lineSeparator() + s);
            }
            br.close();
        } catch(Exception e){
            e.printStackTrace();
        }
        return result.toString();
    }

    //创建并累加
    public static void writeFileAndAdd(String fileName, String content){
        File file = new File(fileName);
        if(!file.exists()){
            try{
                file.getParentFile().mkdirs();
                file.createNewFile();
            } catch(IOException e){
                e.printStackTrace();
            }
        }
        try(FileOutputStream outputStream = new FileOutputStream(file,true)){
            outputStream.write(content.getBytes("utf-8"));
        } catch(IOException e){
            e.printStackTrace();
        }
    }

    //创建并覆盖
    public static void writeFile(String fileName, String content){
        File file = new File(fileName);
        if(!file.exists()){
            try{
                file.getParentFile().mkdirs();
                file.createNewFile();
            } catch(IOException e){
                e.printStackTrace();
            }
        }
        try(FileOutputStream outputStream = new FileOutputStream(file)){
            outputStream.write(content.getBytes("utf-8"));
        } catch(IOException e){
            e.printStackTrace();
        }
    }

    public static List<File> findFiles(String path){
        String dir = "/";
        String pattern = path;
        if(path.contains("/")){
            dir = path.substring(0, path.lastIndexOf("/"));
            pattern = path.substring(dir.length() + 1);
        }
        pattern = pattern.replace("*", ".*");
        pattern = pattern.replace("?", ".?");
        List<File> files = findClassPathFiles(dir);
        List<File> result = new ArrayList<>();
        for(File file : files){
            if(file.isFile() && file.getName().matches(pattern)){
                result.add(file);
            }
        }
        return result;
    }

    private static List<File> findClassPathFiles(String dir){
        List<File> list = new ArrayList<>();
        try{
            if(dir == "/"){
                dir = "";
            }
            Enumeration<URL> urlEnumeration = FileUtil.class.getClassLoader().getResources(dir);
            while(urlEnumeration.hasMoreElements()){
                File file = new File(urlEnumeration.nextElement().getFile());
                File[] files = file.listFiles();
                for(File f : files){
                    list.add(f);
                }
            }
        } catch(IOException e){
            e.printStackTrace();
        }
        return list;
    }
}

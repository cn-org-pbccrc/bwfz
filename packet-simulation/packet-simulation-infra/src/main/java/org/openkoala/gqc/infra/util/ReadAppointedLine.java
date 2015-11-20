package org.openkoala.gqc.infra.util;

import java.io.*;  
public class ReadAppointedLine {
	public static void main(String[] args) {  
        int lineNumber = 2;  
        File sourceFile = new File("C://Users//apple//Desktop//messageBag.txt");  
    }        

    public static String readAppointedLineNumber(File sourceFile, int lineNumber, int totalLines) throws IOException {  
    	FileInputStream fis = new FileInputStream(sourceFile); //创建文件输入流
    	   InputStreamReader isr = new InputStreamReader(fis,"utf-8"); //指定读取流为GBK编码格式
    	   BufferedReader in = new BufferedReader(isr); //创建字符缓存输入流
    	//FileReader in = new FileReader(sourceFile);  
    	LineNumberReader reader = new LineNumberReader(in);  
        String s = "";  
        if (lineNumber <= 0 || lineNumber > totalLines) {  
            System.out.println("不在文件的行数范围之内。");  
            System.exit(0);  
        }  
        int lines = 0; 
        while (s != null) {  
            lines++;  
            s = reader.readLine();  
            if((lines - lineNumber) == 0) {
                //System.out.println(s); 
            	break;
                //System.exit(0);  
                
            }  
        }  
        
        reader.close();  
        in.close(); 
        return s;
        
    }  

    public static int getTotalLines(File file) throws IOException {  
        FileReader in = new FileReader(file);  
        LineNumberReader reader = new LineNumberReader(in);  
        String s = reader.readLine();  
        int lines = 0;  
        while (s != null) {  
            lines++;  
            s = reader.readLine();  
        }  
        reader.close();  
        in.close();  
        return lines;  
    }  
}

package com.dorun.core.dc.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.*;

@Component
public class TxtUtil {

    @Value("${txt.path}")
    private String path;

    public boolean creatTxtFile(String name) {
        boolean flag = false;
        String file = path + name + ".txt";
        File fileName = new File(file);

        File filePath = fileName.getParentFile();

        if(!filePath.exists())
            filePath.mkdirs();

        if (!fileName.exists()) {
            try {
                fileName.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }

            flag = true;
        }

        return flag;
    }

    public boolean writeTxtFile(String ctx, String fileName) {
        boolean flag = false;

        String filein = ctx + "\r\n";

        String temp = "";

        FileInputStream fis = null;
        InputStreamReader isr = null;
        BufferedReader br = null;

        FileOutputStream fos = null;
        PrintWriter pw = null;

        File file = new File(path + fileName + ".txt");

        try {
            fis = new FileInputStream(file);
            isr = new InputStreamReader(fis);
            br = new BufferedReader(isr);
            StringBuffer buf = new StringBuffer();

            // 保存该文件原有的内容
            for (int j = 1; (temp = br.readLine()) != null; j++) {
                buf = buf.append(temp);
                // 行与行之间的分隔符 相当于“\n”
                buf = buf.append(System.getProperty("line.separator"));
            }
            buf.append(filein);

            fos = new FileOutputStream(file);
            pw = new PrintWriter(fos);
            pw.write(buf.toString().toCharArray());
            pw.flush();
            flag = true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (pw != null) {
                pw.close();
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (isr != null) {
                try {
                    isr.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return flag;

    }

}

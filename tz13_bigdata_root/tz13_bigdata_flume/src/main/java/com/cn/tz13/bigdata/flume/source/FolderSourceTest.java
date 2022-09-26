package com.cn.tz13.bigdata.common.source;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.List;

import static java.io.File.separator;

/**
 * @author :  by zhijun zhang
 * @date :  Created 2022/9/21 18:15
 * @description :  Trial Class
 */
public class FolderSourceTest {

    public static void main(String[] args) {

        String folderDir = "D:\\BigData\\workspace\\TestData\\search";

        //读取文件
        Collection<File> files = FileUtils.listFiles(new File(folderDir), new String[]{"txt"}, true);

        if (files.size() > 0) {
            //遍历读取文件
            files.forEach(file -> {
                String fileName = file.getName();
                //解析过程中，要把文件备份。

                //定义一个根目录
                String succPath = "D:\\BigData\\workspace\\TestData\\search";
                //定义一个新的文件目录，用来存放移走的文件
                String fileDirNew = succPath + separator + "2022-09-21";
                //新文件的绝对路径文件名 = 新目录 + 原来的文件名
                String fileNameNew = fileDirNew + fileName;

                try {
                    if (new File(fileNameNew).exists()) {
                        //如果同名文件存在，不做处理。
                    } else {
                        //如果不存在，处理当前文件。
                        List<String> lines = FileUtils.readLines(file);

                        //文件处理完成后，将解析完成的文件移动到成功的目录下。
                        FileUtils.moveToDirectory(file,new File(fileDirNew),true);
                    }
                    System.out.println(file.getName());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

        }
    }

}

package com.cn.yc.utils.excel;

/**
 * Created by DT167 on 2018/3/20.
 */

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



public class ExceUtil {
    private static Logger logger = LoggerFactory.getLogger(ExceUtil.class);

    public static File exportFile(String filePath,String fileName,List<String> titleName, List<String> titleColumn, List<?> dataList) throws IOException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        return ExcelExport.exportFile(fileName,filePath,titleName,titleColumn,dataList,false);
    }

    public static File exportFileAppend(String filePath,String fileName,List<String> titleName, List<String> titleColumn, List<?> dataList) throws IOException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        return ExcelExport.exportFile(fileName,filePath,titleName,titleColumn,dataList,true);
    }

    public static boolean is2003Excel(String filePath) {
        return filePath.matches("^.+\\.(?i)(xls)$");

    }

    public static List<List<String>> importFile(File file) {
        try {
            List<List<String>> tmp;
            if (is2003Excel(file.getPath())) {
                ExcelImport03 excelUtil03 = new ExcelImport03();
                tmp = excelUtil03.readExcel(file.getPath());
            } else {
              /*  ExcelImport07 example = new ExcelImport07();
                tmp = example.processOneSheet(file.getPath());*/
                ExcelImport077 excelImport077 = new ExcelImport077();
                tmp = excelImport077.read(file.getPath());
            }
            return tmp;
        } catch (Exception e) {
            logger.info(e.getMessage());
        }
        return  null;
    }
}


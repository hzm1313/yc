package com.cn.yc.utils.excel;

import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.awt.Color;
import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.util.List;

/**
 * Created by hasee on 2018/4/6.
 */
public class ExcelExport {
    //Float类型数据小数位
    private static String floatDecimal = ".00";
    //Double类型数据小数位
    private static String doubleDecimal = ".00";
    private static DecimalFormat floatDecimalFormat = new DecimalFormat(floatDecimal);
    private static DecimalFormat doubleDecimalFormat = new DecimalFormat(doubleDecimal);


    public static File exportFile(String exportFileName, String filePath, List<String> headList, List<String> columnList, List<?> dataList,boolean isAppend) throws IOException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Workbook workbook = null;
        Sheet sheet = null;
        int rowNum = 0;
        Row row = null;
        Cell cell;
        String fileStr =filePath + "\\" + exportFileName + ".xlsx";
        FileInputStream fs = null;
        if(isAppend&&(new File(fileStr).exists())){
            fs=new FileInputStream(fileStr);
            workbook = new XSSFWorkbook(fs);
            sheet = workbook.getSheetAt(0);
            rowNum = sheet.getLastRowNum();
        }else{
            //直接使用大量数据的导出，效率较低，但是防止出错
            //workbook = new XSSFWorkbook();
            workbook = new SXSSFWorkbook();
            //文本格式
            CellStyle style = workbook.createCellStyle();
            DataFormat format = workbook.createDataFormat();
            style.setDataFormat(format.getFormat("@"));
            style.setHidden(false);
            sheet = workbook.createSheet(exportFileName);
            sheet.setDefaultColumnWidth((short) 15);
            sheet.setColumnWidth(5, 5000);
            sheet.setColumnWidth(6, 5000);
            sheet.setColumnWidth(0, 5000);
            sheet.setColumnWidth(8, 5000);
            sheet.setColumnWidth(9, 5000);
            sheet.setColumnWidth(10, 5000);
            sheet.setColumnWidth(11, 5000);
            sheet.setColumnWidth(12, 5000);
            sheet.setColumnWidth(13, 5000);
            sheet.setColumnWidth(21, 5000);
            sheet.setDefaultColumnStyle(5, style);
            sheet.setDefaultColumnStyle(6, style);
            sheet.setDefaultColumnStyle(0, style);
            sheet.setDefaultColumnStyle(8, style);
            sheet.setDefaultColumnStyle(9, style);
            sheet.setDefaultColumnStyle(10, style);
            sheet.setDefaultColumnStyle(11, style);
            sheet.setDefaultColumnStyle(12, style);
            sheet.setDefaultColumnStyle(13, style);
            sheet.setDefaultColumnStyle(21, style);
            row = sheet.createRow(rowNum);
            for (short i = 0; i < headList.size(); i++) {
                cell = row.createCell(i);
                XSSFRichTextString text = new XSSFRichTextString(headList.get(i));
                cell.setCellValue(text);
            }
        }
        //利用反射进行代码获取
        for (int i = 0; i < dataList.size(); i++) {
            row = sheet.createRow(++rowNum);
            Object obj = dataList.get(i);
            Class clsss = obj.getClass();
            for (int columnIndex = 0; columnIndex < columnList.size(); columnIndex++) {
                String column = columnList.get(columnIndex).toString().trim();
                if (!"".equals(column)) {  //字段不为空
                    //使首字母大写
                    String UTitle = Character.toUpperCase(column.charAt(0)) + column.substring(1, column.length()); // 使其首字母大写;
                    String methodName = "get" + UTitle;

                    // 设置要执行的方法
                    Method method = clsss.getDeclaredMethod(methodName);

                    //获取返回类型
                    String returnType = method.getReturnType().getName();

                    String data = method.invoke(obj) == null ? "" : method.invoke(obj).toString();
                    cell = row.createCell(columnIndex);
                    if (data != null && !"".equals(data)) {
                        if ("int".equals(returnType)) {
                            cell.setCellValue(Integer.parseInt(data));
                        } else if ("long".equals(returnType)) {
                            cell.setCellValue(Long.parseLong(data));
                        } else if ("float".equals(returnType)) {
                            cell.setCellValue(floatDecimalFormat.format(Float.parseFloat(data)));
                        } else if ("double".equals(returnType)) {
                            cell.setCellValue(doubleDecimalFormat.format(Double.parseDouble(data)));
                        } else {
                            cell.setCellValue(data);
                        }
                    }
                }
            }
        }

        ByteArrayOutputStream os = new ByteArrayOutputStream();
        try {
            workbook.write(os);
        } catch (IOException e) {
            e.printStackTrace();
        }

        byte[] content = os.toByteArray();
        InputStream is = new ByteArrayInputStream(content);

        OutputStream out = new FileOutputStream(filePath + "\\" + exportFileName + ".xlsx");

        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;

        try {
            bis = new BufferedInputStream(is);
            bos = new BufferedOutputStream(out);
            byte[] buff = new byte[2048];
            int bytesRead;
            // Simple read/write loop.
            while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
                bos.write(buff, 0, bytesRead);
            }

        } catch (final IOException e) {
            e.printStackTrace();
        } finally {
            if (bis != null)
                bis.close();
            if (bos != null)
                bos.close();
            if(fs !=null){
                fs.close();
            }
        }
        return new File(filePath + "\\" + exportFileName + ".xlsx");
    }


    public static void exportResponse(String exportFileName, HttpServletResponse httpServletResponse, List<String> headList, List<List<String>> dataList) throws IOException {
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet(exportFileName);
        sheet.setDefaultColumnWidth((short) 15);
        XSSFCellStyle style = workbook.createCellStyle();
        //文本格式
        CellStyle style2 = workbook.createCellStyle();
        XSSFCellStyle style3 = workbook.createCellStyle();
        style3.setFillForegroundColor(new XSSFColor(new Color(255, 0, 0)));
        style3.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        DataFormat format = workbook.createDataFormat();
        style2.setDataFormat(format.getFormat("@"));
        style2.setHidden(false);
        sheet.setColumnWidth(5, 5000);
        sheet.setColumnWidth(6, 5000);
        sheet.setColumnWidth(0, 5000);
        sheet.setColumnWidth(8, 5000);
        sheet.setColumnWidth(9, 5000);
        sheet.setColumnWidth(10, 5000);
        sheet.setColumnWidth(11, 5000);
        sheet.setColumnWidth(12, 5000);
        sheet.setColumnWidth(13, 5000);
        sheet.setColumnWidth(21, 5000);
        sheet.setDefaultColumnStyle(5, style2);
        sheet.setDefaultColumnStyle(6, style2);
        sheet.setDefaultColumnStyle(0, style2);
        sheet.setDefaultColumnStyle(8, style2);
        sheet.setDefaultColumnStyle(9, style2);
        sheet.setDefaultColumnStyle(10, style2);
        sheet.setDefaultColumnStyle(11, style2);
        sheet.setDefaultColumnStyle(12, style2);
        sheet.setDefaultColumnStyle(13, style2);
        sheet.setDefaultColumnStyle(21, style2);
        XSSFFont font = workbook.createFont();
        int rowNum = 0;
        XSSFRow row = sheet.createRow(rowNum);
        XSSFCell cell;
        for (short i = 0; i < headList.size(); i++) {
            cell = row.createCell(i);
            XSSFRichTextString text = new XSSFRichTextString(headList.get(i));
            cell.setCellValue(text);
        }
        for (int i = 0; i < dataList.size(); i++) {
            row = sheet.createRow(++rowNum);
            for (int j = 0; j < dataList.get(i).size(); j++) {
                cell = row.createCell(i);
                XSSFRichTextString text = new XSSFRichTextString(headList.get(i));
                cell.setCellValue(text);
            }
        }

        ByteArrayOutputStream os = new ByteArrayOutputStream();
        try {
            workbook.write(os);
        } catch (IOException e) {
            e.printStackTrace();
        }

        byte[] content = os.toByteArray();
        InputStream is = new ByteArrayInputStream(content);
        httpServletResponse.reset();
        httpServletResponse.setContentType("application/vnd.ms-excel;charset=utf-8");
        httpServletResponse.setHeader("Content-Disposition", "attachment;filename=" + new String((exportFileName + ".xlsx").getBytes(), "iso-8859-1"));

        ServletOutputStream out = httpServletResponse.getOutputStream();

        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;

        try {
            bis = new BufferedInputStream(is);
            bos = new BufferedOutputStream(out);
            byte[] buff = new byte[2048];
            int bytesRead;
            // Simple read/write loop.
            while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
                bos.write(buff, 0, bytesRead);
            }

        } catch (final IOException e) {
            e.printStackTrace();
        } finally {
            if (bis != null)
                bis.close();
            if (bos != null)
                bos.close();
        }
    }
}


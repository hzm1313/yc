package com.cn.yc.utils.excel;

/**
 * Created by hasee on 2018/4/6.
 */
import java.util.HashMap;
import java.util.Map;

/**
 * Created by hasee on 2017/9/7.
 */

import java.io.BufferedWriter;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.BuiltinFormats;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.eventusermodel.XSSFReader;
import org.apache.poi.xssf.model.SharedStringsTable;
import org.apache.poi.xssf.model.StylesTable;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;


public class ExcelImport07 {
    private static Logger logger = LoggerFactory.getLogger(ExceUtil.class);
    private  StylesTable stylesTable =null;

    private static enum CellDataType{
        BOOL, ERROR, FORMULA, INLINESTR, SSTINDEX, NUMBER, DATE, NULL
    }

    /**
     * 处理一个sheet
     * @param filename
     * @throws Exception
     */
    public List<List<String>> processOneSheet(String filename) throws Exception {
        try{
            OPCPackage pkg = OPCPackage.open(filename);
            XSSFReader r = new XSSFReader( pkg );
            stylesTable = r.getStylesTable();
            SharedStringsTable sst = r.getSharedStringsTable();
            XMLReader parser = fetchSheetParser(sst);
            // Seems to either be rId# or rSheet#
            //InputStream sheet2 = r.getSheet("rId1");
            Iterator<InputStream> sheets = r.getSheetsData();
            InputStream sheet2 =sheets.next();
            InputSource sheetSource = new InputSource(sheet2);
            parser.parse(sheetSource);
            sheet2.close();
            SheetHandler tmp=(SheetHandler) parser.getContentHandler();
            pkg.close();
            return tmp.getSheetData();
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;

    }

    /**
     * 处理所有sheet
     * @param filename
     * @throws Exception
     */
    public void processAllSheets(String filename) throws Exception {

        OPCPackage pkg = OPCPackage.open(filename);
        XSSFReader r = new XSSFReader( pkg );
        SharedStringsTable sst = r.getSharedStringsTable();

        XMLReader parser = fetchSheetParser(sst);

        Iterator<InputStream> sheets = r.getSheetsData();
        while(sheets.hasNext()) {
            logger.info("Processing new sheet:\n");
            InputStream sheet = sheets.next();
            InputSource sheetSource = new InputSource(sheet);
            parser.parse(sheetSource);
            sheet.close();
            logger.info("");
        }
    }

    /**
     * 获取解析器
     * @param sst
     * @return
     * @throws SAXException
     */
    public XMLReader fetchSheetParser(SharedStringsTable sst) throws SAXException, ParserConfigurationException {
        XMLReader parser =
                XMLReaderFactory.createXMLReader(
                        "org.apache.xerces.parsers.SAXParser"
                );

        ContentHandler handler = new SheetHandler(sst);
        parser.setContentHandler(handler);
        return parser;
    }

    /**
     * 自定义解析处理器
     * See org.xml.sax.helpers.DefaultHandler javadocs
     */
    private class SheetHandler extends DefaultHandler {

        private SharedStringsTable sst;
        private String lastContents;
        private boolean nextIsString;

        private List<String> rowlist = new ArrayList<String>();
        private List<List<String>> sheetData = new ArrayList<List<String>>();
        //增加的前一个value与其绑定
        private Map<String,String> mapVal =new HashMap<String,String>();
        private String preValue=null;

        private int curRow = 0;
        private int curCol = 0;

        //定义前一个元素和当前元素的位置，用来计算其中空的单元格数量，如A6和A8等
        private String preRef = null, ref = null;
        //定义该文档一行最大的单元格数，用来补全一行最后可能缺失的单元格
        private String maxRef = null;


        private CellDataType nextDataType = CellDataType.SSTINDEX;
        private final DataFormatter formatter = new DataFormatter();
        private short formatIndex;
        private String formatString;
        private String tmpString;

        //用一个enum表示单元格可能的数据类型
		/*enum CellDataType{
			BOOL, ERROR, FORMULA, INLINESTR, SSTINDEX, NUMBER, DATE, NULL
		}*/

        private SheetHandler(SharedStringsTable sst) {
            this.sst = sst;
        }

        public List<List<String>> getSheetData(){
            return  sheetData;
        }

        /**
         * 解析一个element的开始时触发事件
         */
        public void startElement(String uri, String localName, String name,
                                 Attributes attributes) throws SAXException {

            // c => cell
            if(name.equals("c")) {
                //前一个单元格的位置
                if(preRef == null){
                    preRef = attributes.getValue("r");
                }else{
                    preRef = ref;
                }
                //当前单元格的位置
                ref = attributes.getValue("r");

                this.setNextDataType(attributes);

                // Figure out if the value is an index in the SST
                String cellType = attributes.getValue("t");
                if(cellType != null && cellType.equals("s")) {
                    nextIsString = true;
                } else {
                    nextIsString = false;
                }

            }
            // Clear contents cache
            lastContents = "";
        }
        //判断是不是另起一行
        private Boolean isStart=true;
        /**
         * 解析一个element元素结束时触发事件
         */
        public void endElement(String uri, String localName, String name)
                throws SAXException {
            // Process the last contents as required.
            // Do now, as characters() may be called more than once
            if(nextIsString) {
                int idx = Integer.parseInt(lastContents);
                lastContents = new XSSFRichTextString(sst.getEntryAt(idx)).toString();
                nextIsString = false;
            }

            // v => contents of a cell
            // Output after we've seen the string contents
            if (name.equals("v")) {
                String value = this.getDataValue(lastContents.trim(), "");
                if(preValue!=null){
                    preRef=mapVal.get(preValue);
                }
                mapVal.put(value,ref);
                //补全首列为空的情况
                if(isStart){
                    int len=countStart(ref);
                    for(int i=0;i<len;i++){
                        rowlist.add(curCol, "");
                        curCol++;
                    }
                    isStart=false;
                }
                //补全单元格之间的空单元格
                if(!ref.equals(preRef)){
                    //test=false;
                    int len = countNullCell(ref, preRef);
                    for(int i=0;i<len;i++){
                        rowlist.add(curCol, "");
                        curCol++;
                    }
                }
                preValue=value;

                rowlist.add(curCol, value);
                curCol++;
            }else {
                //如果标签名称为 row，这说明已到行尾，调用 optRows() 方法
                if (name.equals("row")) {
                    String value = "";
                    //默认第一行为表头，以该行单元格数目为最大数目
                    if(curRow == 0){
                        maxRef = ref;
                    }
                    //补全一行尾部可能缺失的单元格
                    if(maxRef != null){
                        int len = countNullCell(maxRef, ref);
                        for(int i=0;i<=len;i++){
                            rowlist.add(curCol, "");
                            curCol++;
                        }
                    }
                    //拼接一行的数据
                    for(int i=0;i<rowlist.size();i++){
                        if(rowlist.get(i).contains(",")){
                            value += "\""+rowlist.get(i)+"\",";
                        }else{
                            value += rowlist.get(i)+",";
                        }
                    }
                    //加换行符
                    value += "\n";
                    /*try {
						writer.write(value);
					} catch (IOException e) {
						e.printStackTrace();
					}*/
                    curRow++;
                    //一行的末尾重置一些数据
                    //自然增加，后期可以在大小看是否能改善下空间储存效率
                    List<String> tmpList=new ArrayList<String>(rowlist);
                    sheetData.add(tmpList);
                    rowlist.clear();
                    curCol = 0;
                    preRef = null;
                    ref = null;
                    //换行数据，所以之前绑定的就可以清空
                    mapVal.clear();
                    preValue=null;
                    isStart=true;
                }
            }
        }

        /**
         * 根据element属性设置数据类型
         * @param attributes
         */
        public void setNextDataType(Attributes attributes){

            nextDataType = CellDataType.NUMBER;
            formatIndex = -1;
            formatString = null;
            String cellType = attributes.getValue("t");
            String cellStyleStr = attributes.getValue("s");
            if ("b".equals(cellType)){
                nextDataType = CellDataType.BOOL;
            }else if ("e".equals(cellType)){
                nextDataType = CellDataType.ERROR;
            }else if ("inlineStr".equals(cellType)){
                nextDataType = CellDataType.INLINESTR;
            }else if ("s".equals(cellType)){
                nextDataType = CellDataType.SSTINDEX;
            }else if ("str".equals(cellType)){
                nextDataType = CellDataType.FORMULA;
            }
            if (cellStyleStr != null){
                int styleIndex = Integer.parseInt(cellStyleStr);
                XSSFCellStyle style = stylesTable.getStyleAt(styleIndex);
                formatIndex = style.getDataFormat();
                formatString = style.getDataFormatString();
                if ("m/d/yy" == formatString){
                    nextDataType = CellDataType.DATE;
                    //full format is "yyyy-MM-dd hh:mm:ss.SSS";
                    formatString = "yyyy-MM-dd";
                }
                if (formatString == null){
                    nextDataType = CellDataType.NULL;
                    formatString = BuiltinFormats.getBuiltinFormat(formatIndex);
                }
            }
        }


        /**
         * 根据数据类型获取数据
         * @param value
         * @param thisStr
         * @return
         */
        public String getDataValue(String value, String thisStr)

        {
            switch (nextDataType)
            {
                //这几个的顺序不能随便交换，交换了很可能会导致数据错误
                case BOOL:
                    char first = value.charAt(0);
                    thisStr = first == '0' ? "FALSE" : "TRUE";
                    break;
                case ERROR:
                    thisStr = "\"ERROR:" + value.toString() + '"';
                    break;
                case FORMULA:
                    thisStr = '"' + value.toString() + '"';
                    break;
                case INLINESTR:
                    XSSFRichTextString rtsi = new XSSFRichTextString(value.toString());
                    thisStr = rtsi.toString();
                    rtsi = null;
                    break;
                case SSTINDEX:
                    String sstIndex = value.toString();
                    thisStr = value.toString();
                    break;
                case NUMBER:
                    if (formatString != null){
                        thisStr = formatter.formatRawCellContents(Double.parseDouble(value), formatIndex, formatString).trim();
                    }else{
                        thisStr = value;
                    }
                    thisStr = thisStr.replace("_", "").trim();
                    break;
                case DATE:
                    try{
                        thisStr = formatter.formatRawCellContents(Double.parseDouble(value), formatIndex, formatString);
                    }catch(NumberFormatException ex){
                        thisStr = value.toString();
                    }
                    thisStr = thisStr.replace(" ", "");
                    break;
                default:
                    thisStr = "";
                    break;
            }
            return thisStr;
        }

        /**
         * 获取element的文本数据
         */
        public void characters(char[] ch, int start, int length)
                throws SAXException {
            lastContents += new String(ch, start, length);
        }

        /**
         * 计算两个单元格之间的单元格数目(同一行)
         * @param ref
         * @param preRef
         * @return
         */
        public int countNullCell(String ref, String preRef){
            //excel2007最大行数是1048576，最大列数是16384，最后一列列名是XFD
            String xfd = ref.replaceAll("\\d+", "");
            String xfd_1 = preRef.replaceAll("\\d+", "");

            xfd = fillChar(xfd, 3, '@', true);
            xfd_1 = fillChar(xfd_1, 3, '@', true);

            char[] letter = xfd.toCharArray();
            char[] letter_1 = xfd_1.toCharArray();
            int res = (letter[0]-letter_1[0])*26*26 + (letter[1]-letter_1[1])*26 + (letter[2]-letter_1[2]);
            return res-1;
        }

        /**
         * 计算首个单元格为空的情况
         * @param ref
         * @param preRef
         * @return
         */
        public int countStart(String ref){
            //excel2007最大行数是1048576，最大列数是16384，最后一列列名是XFD
            //
            String A="A";
            ref=ref.substring(0, 1);
            char[] end = ref.toCharArray();
            char[] start=A.toCharArray();
            int len=(int)end[0]-(int)start[0];
            return len;
        }

        /**
         * 字符串的填充
         * @param str
         * @param len
         * @param let
         * @param isPre
         * @return
         */
        String fillChar(String str, int len, char let, boolean isPre){
            int len_1 = str.length();
            if(len_1 <len){
                if(isPre){
                    for(int i=0;i<(len-len_1);i++){
                        str = let+str;
                    }
                }else{
                    for(int i=0;i<(len-len_1);i++){
                        str = str+let;
                    }
                }
            }
            return str;
        }
    }

    BufferedWriter writer = null;

    public static void main(String[] args) throws Exception {
        ExcelImport07 example = new ExcelImport07();
        String filename = "E:\\虚拟货币\\测试数据\\test.xlsx";
        List<List<String>> test=example.getExcel(filename);
        for(int i=0;i<test.size();i++){
            System.out.println(test.get(i));
        }

    }

    // 是否是2003的excel，返回true是2003
    public static boolean is2003Excel(String filePath) {
        return filePath.matches("^.+\\.(?i)(xls)$");

    }
    public  List<List<String>> getExcel(String filePath){
        try{
            List<List<String>> tmp;
            if(is2003Excel(filePath)){
                ExcelImport03 excelUtil03=new ExcelImport03();
                tmp=excelUtil03.readExcel(filePath);
            }else{
                ExcelImport07 example = new ExcelImport07();
                tmp=example.processOneSheet(filePath);
            }
            return tmp;
        }catch(Exception e){
            logger.info(e.getMessage());
        }
        return null;
    }

    public  List<List<String>> getExcel(String fileName,BufferedWriter writerCome){
        try{
            writer=writerCome;
            ExcelImport07 example = new ExcelImport07();
            example.processOneSheet(fileName);
        }catch(Exception e){
            logger.error(e.getMessage());
        }
        return null;
    }
}
package com.vilio.plms.util;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.math.BigDecimal;
import java.util.*;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * EXCEL工具类
 */
public class ExcelUtil {

    //模板map
    private Map<String,Workbook> tempWorkbook = new HashMap<String, Workbook>();
    //模板输入流map
    private Map<String,FileInputStream> tempStream = new HashMap<String, FileInputStream>();

    /**
     *
     * <p class="detail">
     * 描述：临时单元格数据
     * </p>
     */
    class TempCell{
        private int row;
        private int column;
        private CellStyle cellStyle;
        private Object data;
        //用于列表合并，表示几列合并
        private int columnSize = -1;

        public int getColumn() {
            return column;
        }
        public void setColumn(int column) {
            this.column = column;
        }
        public int getRow() {
            return row;
        }
        public void setRow(int row) {
            this.row = row;
        }
        public CellStyle getCellStyle() {
            return cellStyle;
        }
        public void setCellStyle(CellStyle cellStyle) {
            this.cellStyle = cellStyle;
        }
        public Object getData() {
            return data;
        }
        public void setData(Object data) {
            this.data = data;
        }
        public int getColumnSize() {
            return columnSize;
        }
        public void setColumnSize(int columnSize) {
            this.columnSize = columnSize;
        }
    }

    /**
     *
     * <p class="detail">
     * 功能：按模板向Excel中相应地方填充数据
     * </p>
     * @param tempFilePath
     * @param dataMap
     * @param sheetNo
     * @throws IOException
     */
    public void writeData(String tempFilePath,Map<String,Object> dataMap,int sheetNo) throws IOException{
        //获取模板填充格式位置等数据
        //    HashMap tem = getTemp(tempFilePath,sheet);
        //读取模板
        Workbook wbModule = getTempWorkbook(tempFilePath);
        //数据填充的sheet
        Sheet wsheet = wbModule.getSheetAt(sheetNo);

        Iterator it = dataMap.entrySet().iterator();
        while(it.hasNext()){
            Entry<String, Object> entry = (Entry<String, Object>) it.next();
            String point = entry.getKey();
            Object data = entry.getValue();

            TempCell cell = getCell(point, data,wsheet);
            //指定坐标赋值
            setCell(cell,wsheet);
        }
        //设置生成excel中公式自动计算
        wsheet.setForceFormulaRecalculation(true);
    }

    /**
     *
     * <p class="detail">
     * 功能：按模板向Excel中列表填充数据。    只支持列合并
     * </p>
     * @param tempFilePath
     * @param heads   列表头部位置集合
     * @param datalist
     * @param sheetNo
     * @throws FileNotFoundException
     * @throws IOException
     */
    public void writeDateList(String tempFilePath,String[] heads,List<Map<Integer, Object>> datalist,int sheetNo) throws FileNotFoundException, IOException {
        //读取模板
        Workbook wbModule = getTempWorkbook(tempFilePath);
        //数据填充的sheet
        Sheet wsheet = wbModule.getSheetAt(sheetNo);
        //列表数据模板cell
        List<TempCell> tempCells = new ArrayList<TempCell>();
        for(int i=0;i<heads.length;i++){
            String point = heads[i];
            TempCell tempCell = getCell(point,null,wsheet);
            //取得合并单元格位置  -1：表示不是合并单元格
            int pos = isMergedRegion(wsheet, tempCell.getRow(), tempCell.getColumn());
            if(pos>-1){
                CellRangeAddress range = wsheet.getMergedRegion(pos);
                tempCell.setColumnSize(range.getLastColumn()-range.getFirstColumn());
            }
            tempCells.add(tempCell);
        }
        //赋值
        for(int i=0;i<datalist.size();i++){
            Map<Integer, Object> dataMap = datalist.get(i);
            for(int j=0;j<tempCells.size();j++){
                TempCell tempCell = tempCells.get(j);
                tempCell.setRow(tempCell.getRow()+1);
                tempCell.setData(dataMap.get(j+1));
                setCell(tempCell, wsheet);
            }
        }


    }



    /**
     *
     * <p class="detail">
     * 功能：获取输入工作区
     * </p>
     * @param tempFilePath
     * @return
     * @throws FileNotFoundException
     * @throws IOException
     */
    public Workbook getTempWorkbook(String tempFilePath) throws FileNotFoundException, IOException {
        if(!tempWorkbook.containsKey(tempFilePath)){
            if(tempFilePath.endsWith(".xlsx")){
                tempWorkbook.put(tempFilePath, new XSSFWorkbook(getFileInputStream(tempFilePath)));
            }else if(tempFilePath.endsWith(".xls")){
                tempWorkbook.put(tempFilePath, new HSSFWorkbook(getFileInputStream(tempFilePath)));
            }
        }
        return tempWorkbook.get(tempFilePath);
    }


    /**
     *
     * <p class="detail">
     * 功能：获取输入工作区
     */
    public Workbook getTempWorkbook(InputStream inputStream) throws FileNotFoundException, IOException {
        return new XSSFWorkbook(inputStream);
    }

    /**
     *
     * <p class="detail">
     * 功能：获得模板输入流
     * </p>
     * @param tempFilePath
     * @return
     * @throws FileNotFoundException
     */
    private FileInputStream getFileInputStream(String tempFilePath) throws FileNotFoundException {
        if(!tempStream.containsKey(tempFilePath)){
            tempStream.put(tempFilePath, new FileInputStream(tempFilePath));
        }
        return tempStream.get(tempFilePath);
    }

    /**
     *
     * <p class="detail">
     * 功能：设置单元格数据,样式  (根据坐标：B3)
     * </p>
     * @param point
     * @param data
     * @param sheet
     * @return
     */
    private TempCell getCell(String point,Object data,Sheet sheet){
        TempCell tempCell = new TempCell();

        //得到列 字母
        String lineStr = "";
        String reg = "[A-Z]+";
        Pattern p = Pattern.compile(reg);
        Matcher m = p.matcher(point);
        while(m.find()){
            lineStr = m.group();
        }
        //将列字母转成列号  根据ascii转换
        char[] ch = lineStr.toCharArray();
        int column = 0;
        for(int i=0;i<ch.length;i++){
            char c = ch[i];
            int post = ch.length-i-1;
            int r = (int) Math.pow(10, post);
            column = column + r*((int)c-65);
        }
        tempCell.setColumn(column);

        //得到行号
        reg = "[0-9]+";
        p = Pattern.compile(reg);
        m = p.matcher(point);
        while(m.find()){
            tempCell.setRow((Integer.parseInt(m.group())-1));
        }

        //获取模板指定单元格样式，设置到tempCell （写列表数据的时候用）
        Row rowIn = sheet.getRow(tempCell.getRow());
        if(rowIn == null) {
            rowIn = sheet.createRow(tempCell.getRow());
        }
        Cell cellIn = rowIn.getCell(tempCell.getColumn());
        if(cellIn == null) {
            cellIn = rowIn.createCell(tempCell.getColumn());
        }
        tempCell.setCellStyle(cellIn.getCellStyle());

        tempCell.setData(data);
        return tempCell;
    }

    /**
     *
     * <p class="detail">
     * 功能：给指定坐标赋值
     * </p>
     * @param tempCell
     * @param sheet
     */
    private void setCell(TempCell tempCell,Sheet sheet) {
        if(tempCell.getColumnSize()>-1){
            CellRangeAddress rangeAddress = mergeRegion(sheet, tempCell.getRow(), tempCell.getRow(), tempCell.getColumn(), tempCell.getColumn()+tempCell.getColumnSize());
            setRegionStyle(tempCell.getCellStyle(), rangeAddress, sheet);
        }

        Row rowIn = sheet.getRow(tempCell.getRow());
        if(rowIn == null) {
            rowIn = sheet.createRow(tempCell.getRow());
        }
        Cell cellIn = rowIn.getCell(tempCell.getColumn());
        if(cellIn == null) {
            cellIn = rowIn.createCell(tempCell.getColumn());
        }
        //根据data类型给cell赋值
        if(tempCell.getData() instanceof String){
            cellIn.setCellValue((String)tempCell.getData());
        }else if(tempCell.getData() instanceof Integer){
            cellIn.setCellValue((Integer)tempCell.getData());
        }else if(tempCell.getData() instanceof Double){
            cellIn.setCellValue((Double) tempCell.getData());
        }else if(tempCell.getData() instanceof BigDecimal){
            cellIn.setCellValue(((BigDecimal) tempCell.getData()).doubleValue());
        }else{
            cellIn.setCellValue((String)tempCell.getData());
        }
        //样式
        if(tempCell.getCellStyle()!=null && tempCell.getColumnSize()==-1){
            cellIn.getCellStyle().cloneStyleFrom(tempCell.getCellStyle());
        }
    }

    /**
     *
     * <p class="detail">
     * 功能：写到输出流并移除资源
     * </p>
     * @param tempFilePath
     * @param os
     * @throws FileNotFoundException
     * @throws IOException
     */
    public void writeAndClose(String tempFilePath,OutputStream os) throws FileNotFoundException, IOException{
        if(getTempWorkbook(tempFilePath)!=null){
            getTempWorkbook(tempFilePath).write(os);
            tempWorkbook.remove(tempFilePath);
        }
        if(getFileInputStream(tempFilePath)!=null){
            getFileInputStream(tempFilePath).close();
            tempStream.remove(tempFilePath);
        }
    }

    /**
     *
     * <p class="detail">
     * 功能：判断指定的单元格是否是合并单元格
     * </p>
     * @param sheet
     * @param row
     * @param column
     * @return  0:不是合并单元格，i:合并单元格的位置
     */
    private Integer isMergedRegion(Sheet sheet,int row ,int column) {
        int sheetMergeCount = sheet.getNumMergedRegions();
        for (int i = 0; i < sheetMergeCount; i++) {
            CellRangeAddress range = sheet.getMergedRegion(i);
            int firstColumn = range.getFirstColumn();
            int lastColumn = range.getLastColumn();
            int firstRow = range.getFirstRow();
            int lastRow = range.getLastRow();
            if(row >= firstRow && row <= lastRow){
                if(column >= firstColumn && column <= lastColumn){
                    return i;
                }
            }
        }
        return -1;
    }

    /**
     *
     * <p class="detail">
     * 功能：合并单元格
     * </p>
     * @param sheet
     * @param firstRow
     * @param lastRow
     * @param firstCol
     * @param lastCol
     */
    private CellRangeAddress mergeRegion(Sheet sheet, int firstRow, int lastRow, int firstCol, int lastCol) {
        CellRangeAddress rang = new CellRangeAddress(firstRow, lastRow, firstCol, lastCol);
        sheet.addMergedRegion(rang);
        return rang;
    }

    /**
     *
     * <p class="detail">
     * 功能：设置合并单元格样式
     * </p>
     * @param cs
     * @param region
     * @param sheet
     */
    private static void setRegionStyle(CellStyle cs, CellRangeAddress region, Sheet sheet){
        for(int i=region.getFirstRow();i<=region.getLastRow();i++){
            Row row=sheet.getRow(i);
            if(row==null) row=sheet.createRow(i);
            for(int j=region.getFirstColumn();j<=region.getLastColumn();j++){
                Cell cell=row.getCell(j);
                if(cell==null){
                    cell=row.createCell(j);
                    cell.setCellValue("");
                }
                cell.setCellStyle(cs);
            }
        }
    }

    /**
     * <p class="detail">
     * 仅仅copy数值
     * </p>
     * @param tempFilePath
     * @param sourceRowNum
     * @param sheetNo
     * @throws IOException
     */
    public void copyRowValue(String tempFilePath,int sourceRowNum,int targetRowNum, int sheetNo) throws IOException{
        //读取模板
        Workbook wbModule = getTempWorkbook(tempFilePath);
        //数据填充的sheet
        Sheet wsheet = wbModule.getSheetAt(sheetNo);

        Row sourceRow = wsheet.getRow(sourceRowNum);
        Row targetRow = wsheet.createRow(targetRowNum);
        targetRow.setHeight(sourceRow.getHeight());

        for (int m = sourceRow.getFirstCellNum(); m < sourceRow.getLastCellNum(); m++) {
            Cell sourceCell = sourceRow.getCell(m);
            Cell targetCell = targetRow.createCell(m);

            if(sourceCell == null){
                continue;
            }

            //targetCell.setEncoding(sourceCell.getEncoding());
            targetCell.setCellStyle(sourceCell.getCellStyle());
            targetCell.setCellType(sourceCell.getCellType());
            if (null != getCellValue(sourceCell))
                setCellValue(targetCell, getCellValue(sourceCell));//設置值
        }
    }

    /**
     * <p class="detail">
     * 在sourceRowNum行下，复制times行数据
     * </p>
     * @param tempFilePath
     * @param sourceRowNum
     * @param times
     * @param sheetNo
     * @throws IOException
     */
    public void createAndCopyRow(String tempFilePath,int sourceRowNum,int times, int sheetNo) throws IOException{
        //读取模板
        Workbook wbModule = getTempWorkbook(tempFilePath);
        //数据填充的sheet
        Sheet wsheet = wbModule.getSheetAt(sheetNo);

        wsheet.shiftRows(sourceRowNum + 1, wsheet.getLastRowNum(), times,true,false);

        for (int i = 0; i < times; i++) {
            copyRowValue(tempFilePath,sourceRowNum,sourceRowNum+i+1,sheetNo);
        }
    }

    /**
     * <p class="detail">
     * 在sourceRowNum行下，创建并复制times次行数据
     * </p>
     * @param sourceStartRowNum --待copy数据的其实行
     * @param sourceEndRowNum --待copy数据的结束行
     * @param times --copy份数
     * @param sheetNo
     * @throws IOException
     */
    public void createAndCopyRows(String tempFilePath,int sourceStartRowNum,int sourceEndRowNum,int times, int sheetNo) throws  IOException{
        //读取模板
        Workbook wbModule = getTempWorkbook(tempFilePath);
        //数据填充的sheet
        Sheet wsheet = wbModule.getSheetAt(sheetNo);
        //待copy数据的总行数
        int sourceRows = sourceEndRowNum-sourceStartRowNum+1;

        wsheet.shiftRows(sourceEndRowNum + 1, wsheet.getLastRowNum(), sourceRows*times,true,false);

        for(int time = 0; time < times; time++){//copy times份
            for (int i = 0; i < sourceRows; i++) {//循环待copy数据区域的每一行，并写入到目标区域的每一行中
                int sourceRowNum = sourceStartRowNum+i;
                int targetRowNum = time*sourceRows + sourceEndRowNum + i + 1;
                copyRowValue(tempFilePath,sourceRowNum,targetRowNum,sheetNo);
            }
        }
    }

    /**
     * <p class="detail">
     * 删除startRowNum到endRowNum行数据
     * </p>
     * @param startRowNum --待copy数据的开始行
     * @param endRowNum --待copy数据的结束行
     * @param sheetNo
     * @throws IOException
     */
    public void deleteRows(String tempFilePath,int startRowNum,int endRowNum,int sheetNo) throws  IOException{
        //读取模板
        Workbook wbModule = getTempWorkbook(tempFilePath);
        //数据填充的sheet
        Sheet wsheet = wbModule.getSheetAt(sheetNo);
        //待删除数据的总行数
        int deleteRows = endRowNum-startRowNum+1;

        wsheet.shiftRows(endRowNum + 1, wsheet.getLastRowNum(), -deleteRows,true,false);

    }

    /**
     * 获取单元格的值
     * @param cell
     * @return
     */
    private Object getCellValue(Cell cell){
        Object value = null;
        if (null != cell) {
            switch (cell.getCellType()) {
                case Cell.CELL_TYPE_NUMERIC: // 数字
                    value = cell.getNumericCellValue();
                    break;
                case Cell.CELL_TYPE_STRING: // 字符串
                    value = cell.getStringCellValue();
                    break;
                case Cell.CELL_TYPE_BOOLEAN: // Boolean
                    value = cell.getBooleanCellValue();
                    break;
                case Cell.CELL_TYPE_FORMULA: // 公式
                    value = cell.getCellFormula();
                    break;
                case Cell.CELL_TYPE_BLANK: // 空值
                    value = null;
                    break;
                case Cell.CELL_TYPE_ERROR: // 故障
                    value = null;
                    break;
                default:
                    value = null;
                    break;
            }
        }
        return value;
    }

    /**
     *
     * @param cell
     * @param value
     */
    public static void setCellValue(Cell cell,Object value){
        if (null != cell) {
            switch (cell.getCellType()) {
                case Cell.CELL_TYPE_NUMERIC: // 数字
                    cell.setCellValue((Double) value);
                    break;
                case Cell.CELL_TYPE_STRING: // 字符串
                    cell.setCellValue((String) value);
                    break;
                case Cell.CELL_TYPE_BOOLEAN: // Boolean
                    cell.setCellValue((Boolean) value);
                    break;
                case Cell.CELL_TYPE_FORMULA: // 公式
                    cell.setCellValue((String) value);
                    break;
                case Cell.CELL_TYPE_BLANK: // 空值
                    break;
                case Cell.CELL_TYPE_ERROR: // 故障
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * 指定sheet页，第row+1行，第column+1列的值
     * @param sheet
     * @param column
     * @param row
     * @return
     */
    public Object getCellValue(Sheet sheet, int row, int column){
        Row rowIn = sheet.getRow(row);
        Cell cell = rowIn.getCell(column);

        Object value = null;
        if (null != cell) {
            switch (cell.getCellType()) {
                case Cell.CELL_TYPE_NUMERIC: // 数字
                    value = cell.getNumericCellValue();
                    break;
                case Cell.CELL_TYPE_STRING: // 字符串
                    value = cell.getStringCellValue();
                    break;
                case Cell.CELL_TYPE_BOOLEAN: // Boolean
                    value = cell.getBooleanCellValue();
                    break;
                case Cell.CELL_TYPE_FORMULA: // 公式
                    value = cell.getCellFormula();
                    break;
                case Cell.CELL_TYPE_BLANK: // 空值
                    value = null;
                    break;
                case Cell.CELL_TYPE_ERROR: // 故障
                    value = null;
                    break;
                default:
                    value = null;
                    break;
            }
        }
        return value;
    }

    /**
     * 设置指定sheet页，第row+1行，第column+1列的值
     * @param sheet
     * @param row
     * @param column
     * @param value
     */
    public void setCellValue(Sheet sheet, int row, int column,Object value,CellStyle cellStyle){
        TempCell tempCell = new TempCell();
        tempCell.setRow(row);
        tempCell.setColumn(column);
        tempCell.setData(value);
        tempCell.setCellStyle(cellStyle);
        setCell(tempCell, sheet);
//        Row rowIn = sheet.getRow(row);
//        if(rowIn == null) {
//            rowIn = sheet.createRow(row);
//        }
//        Cell cell = rowIn.getCell(column);
//        if(cell == null) {
//            cell = rowIn.createCell(column);
//        }
//
//        if (null != cell) {
//            switch (cell.getCellType()) {
//                case Cell.CELL_TYPE_NUMERIC: // 数字
//                    cell.setCellValue((Double) value);
//                    break;
//                case Cell.CELL_TYPE_STRING: // 字符串
//                    cell.setCellValue((String) value);
//                    break;
//                case Cell.CELL_TYPE_BOOLEAN: // Boolean
//                    cell.setCellValue((Boolean) value);
//                    break;
//                case Cell.CELL_TYPE_FORMULA: // 公式
//                    cell.setCellValue((String) value);
//                    break;
//                case Cell.CELL_TYPE_BLANK: // 空值
//                    break;
//                case Cell.CELL_TYPE_ERROR: // 故障
//                    break;
//                default:
//                    break;
//            }
//        }
    }




    public static void main(String[] args) throws FileNotFoundException, IOException {
        String tempFilePath = "D:\\IdeaProjects\\plms\\V1.0.0\\src\\main\\java\\com\\vilio\\plms\\util\\receiptsRecordUploadTemplet.xlsx";


        ExcelUtil excelUtil = new ExcelUtil();
        //读取模板
        Workbook wbModule = excelUtil.getTempWorkbook(tempFilePath);
        //数据填充的sheet
        Sheet wsheet = wbModule.getSheetAt(0);

        excelUtil.setCellValue(wsheet,2,0,"哈哈你错了dd；",null);

        String targetFilePath= "D:\\IdeaProjects\\plms\\V1.0.0\\src\\main\\java\\com\\vilio\\plms\\util\\receiptsRecordUploadTemplet3.xlsx";

        File file = new File(targetFilePath);
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        OutputStream os = new FileOutputStream(file);

        excelUtil.writeAndClose(tempFilePath, os);

        os.flush();
        os.close();


//        Object value = excelUtil.getCellValue(wsheet,2,0);

//        excelUtil.setCellValue(wsheet,2,0,"哈哈你错了；");

//        System.out.print(value);
    }


}

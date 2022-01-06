package com.beetlsql.study.utils;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.util.CellRangeAddress;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GradeUserExportCsvTest {


    public static List<String[]> initDynamicColumnHeadList() {
        List<String[]> headList = new ArrayList<>();
        String outOf100 = "out of 100";
        //        String[] headColumnItem0 = {"My team are great...", "Discussion", "1-Feb-20", "Exercise(10%)", outOf100};
//        String[] headColumnItem1 = {"Connecting with team...(offline)", "Assignment", "1-Feb-20", "Exercise(10%)", outOf100};
//        String[] headColumnItem2 = {"Effective Teams...", "Assignment", "1-Mar-20", "Exercise(0%)", outOf100};
//        String[] headColumnItem3 = {"Discussion after watcthing...", "Discussion", "1-Apr-20", "Exercise(10%)", outOf100};
//        String[] headColumnItem4 = {"Building effective teams quiz", "Quiz", "1-Jul-20", "Exercise(10%)", outOf100};
//        String[] headColumnItem5 = {"Test3", "Quiz", "1-Jul-22", "Exercise(0%)", outOf100};
//        String[] headColumnItem6 = {"final exammination", "Quiz", "1-Jul-23", "Exercise(200%)", outOf100};
//        headList.add(headColumnItem0);
//        headList.add(headColumnItem1);
//        headList.add(headColumnItem2);
//        headList.add(headColumnItem3);
//        headList.add(headColumnItem4);
//        headList.add(headColumnItem5);
//        headList.add(headColumnItem6);
        return headList;
    }

    public static void main(String[] args) throws IOException {
        List<String[]> headList = initDynamicColumnHeadList();
        /*

        XSSFWorkbook xssfWorkbook = new XSSFWorkbook();
        XSSFSheet xssfSheet = xssfWorkbook.createSheet("sheet");
        XSSFCellStyle style = xssfWorkbook.createCellStyle();
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.index);
        //设置自动换行
        style.setWrapText(true);
        style.setAlignment(HorizontalAlignment.LEFT);
        style.setVerticalAlignment(VerticalAlignment.BOTTOM);

        XSSFRow row = xssfSheet.createRow(0);
        XSSFCell cell = row.createCell(0);
        cell.setCellStyle(style);

        cell.setCellValue(
                "设备ID：123456 " + "\n" +
                        "时间：2021-11-01 00:00:00 ~ 2021-11-02 23:59:59" + "\n" +
                        "通话次数:3次" + "\n" +
                        "通话总时长:6分钟");
//        cell.setHeightInPoints(70);

        */
        /*
//        generateRowAndCell(sheet, workbook, value);
//        for (int i = 0; i < headList.size(); i++) {
//            HSSFCell cell01 = row0.createCell(6 + i);
//            cell01.setCellStyle(cellStyle);
//            String[] columnItemArray = headList.get(i);
//            cell01.setCellValue(columnItemArray[0]);
//        }
//        HSSFRow row1 = sheet.createRow(1);
//        HSSFCell cell10 = row1.createCell(0);
//        cell10.setCellStyle(cellStyle);
//        cell10.setCellValue("typeRegion");
//        for (int i = 0; i < headList.size(); i++) {
//            HSSFCell cell11 = row1.createCell(6 + i);
//            cell11.setCellStyle(cellStyle);
//            String[] columnItemArray = headList.get(i);
//            cell11.setCellValue(columnItemArray[1]);
//        }
//        HSSFRow row2 = sheet.createRow(2);
//        HSSFCell cell20 = row2.createCell(0);
//        cell20.setCellStyle(cellStyle);
//        cell20.setCellValue("dueRegion");
//        for (int i = 0; i < headList.size(); i++) {
//            HSSFCell cell21 = row2.createCell(6 + i);
//            cell21.setCellStyle(cellStyle);
//            String[] columnItemArray = headList.get(i);
//            cell21.setCellValue(columnItemArray[2]);
//        }
//
//        HSSFRow row3 = sheet.createRow(3);
//        HSSFCell cell30 = row3.createCell(0);
//        cell30.setCellStyle(cellStyle);
//        cell30.setCellValue("calculatedWeightRegion");

//        for (int i = 0; i < headList.size(); i++) {
//            HSSFCell cell31 = row3.createCell(6 + i);
//            cell31.setCellStyle(cellStyle);
//            String[] columnItemArray = headList.get(i);
//            cell31.setCellValue(columnItemArray[3]);
//        }

//        HSSFRow row4 = sheet.createRow(4);
//        HSSFCell cell40 = row4.createCell(0);
//        cell40.setCellStyle(cellStyle);
//        cell40.setCellValue("Students(7)");
//        HSSFCell cell41 = row4.createCell(1);
//        cell41.setCellStyle(cellStyle);
//        cell41.setCellValue("Missing(3)");
//        HSSFCell cell42 = row4.createCell(2);
//        cell42.setCellStyle(cellStyle);
//        cell42.setCellValue("Done Late(4)");

//        HSSFCell cell43 = row4.createCell(3);
//        cell43.setCellStyle(cellStyle);
//        cell43.setCellValue("Ungraded(2)");
//
//        HSSFCell cell45 = row4.createCell(4);
//        cell45.setCellStyle(cellStyle);
//        cell45.setCellValue("overallRegion");
//
//        for (int i = 0; i < headList.size(); i++) {
//            HSSFCell cell46 = row4.createCell(6 + i);
//            cell46.setCellStyle(cellStyle);
//            String[] columnItemArray = headList.get(i);
//            cell46.setCellValue(columnItemArray[4]);
//        }

        // 合并日期占两行(4个参数，分别为起始行，结束行，起始列，结束列)
        // 行和列都是从0开始计数，且起始结束都会合并
        // 这里是合并excel中日期的两行为一行
*/
        /*
        //设置第1行左边单元格合并
        CellRangeAddress gradeItemRegion = new CellRangeAddress(0, 0, 0, 6);
        xssfSheet.addMergedRegion(gradeItemRegion);*/

        //TODO
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFCellStyle cellStyle = workbook.createCellStyle();
         /*   cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
//        加边框
        cellStyle.setBorderBottom(BorderStyle.THIN);//下边框
        cellStyle.setBorderLeft(BorderStyle.THIN);//左边框
        cellStyle.setBorderRight(BorderStyle.THIN);//右边框
        cellStyle.setBorderTop(BorderStyle.THIN); //上边框
        cellStyle.setFillForegroundColor((short) 9);

        HSSFFont font = workbook.createFont();
//        设置字体名称
        font.setFontName("楷体");
        font.setFontHeightInPoints((short) 14);//设置字号
        font.setItalic(false);//设置是否为斜体
        font.setBold(true);//设置是否加粗
        font.setColor(IndexedColors.BLACK1.index);//设置字体颜色
        cellStyle.setFont(font);
        cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        cellStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.index);
        //设置自动换行
        cellStyle.setWrapText(true);
        cellStyle.setAlignment(HorizontalAlignment.LEFT);
        cellStyle.setVerticalAlignment(VerticalAlignment.BOTTOM);*/
        HSSFSheet sheet = workbook.createSheet("sheet");
        HSSFRow row0 = sheet.createRow(0);
        HSSFCell cell00 = row0.createCell(0);
        cell00.setCellStyle(cellStyle);
        cell00.setCellValue(
                "设备ID：123456 " + "\n" +
                        "时间：2021-11-01 00:00:00 ~ 2021-11-02 23:59:59" + "\n" +
                        "通话次数:3次" + "\n" +
                        "通话总时长:6分钟");
        row0.setHeightInPoints(70);
        //        generateRowAndCell(sheet, workbook, value);
//        for (int i = 0; i < headList.size(); i++) {
//            HSSFCell cell01 = row0.createCell(6 + i);
//            cell01.setCellStyle(cellStyle);
//            String[] columnItemArray = headList.get(i);
//            cell01.setCellValue(columnItemArray[0]);
//        }
//        HSSFRow row1 = sheet.createRow(1);
//        HSSFCell cell10 = row1.createCell(0);
//        cell10.setCellStyle(cellStyle);
//        cell10.setCellValue("typeRegion");
//        for (int i = 0; i < headList.size(); i++) {
//            HSSFCell cell11 = row1.createCell(6 + i);
//            cell11.setCellStyle(cellStyle);
//            String[] columnItemArray = headList.get(i);
//            cell11.setCellValue(columnItemArray[1]);
//        }
//        HSSFRow row2 = sheet.createRow(2);
//        HSSFCell cell20 = row2.createCell(0);
//        cell20.setCellStyle(cellStyle);
//        cell20.setCellValue("dueRegion");
//        for (int i = 0; i < headList.size(); i++) {
//            HSSFCell cell21 = row2.createCell(6 + i);
//            cell21.setCellStyle(cellStyle);
//            String[] columnItemArray = headList.get(i);
//            cell21.setCellValue(columnItemArray[2]);
//        }
//
//        HSSFRow row3 = sheet.createRow(3);
//        HSSFCell cell30 = row3.createCell(0);
//        cell30.setCellStyle(cellStyle);
//        cell30.setCellValue("calculatedWeightRegion");

//        for (int i = 0; i < headList.size(); i++) {
//            HSSFCell cell31 = row3.createCell(6 + i);
//            cell31.setCellStyle(cellStyle);
//            String[] columnItemArray = headList.get(i);
//            cell31.setCellValue(columnItemArray[3]);
//        }

//        HSSFRow row4 = sheet.createRow(4);
//        HSSFCell cell40 = row4.createCell(0);
//        cell40.setCellStyle(cellStyle);
//        cell40.setCellValue("Students(7)");
//        HSSFCell cell41 = row4.createCell(1);
//        cell41.setCellStyle(cellStyle);
//        cell41.setCellValue("Missing(3)");
//        HSSFCell cell42 = row4.createCell(2);
//        cell42.setCellStyle(cellStyle);
//        cell42.setCellValue("Done Late(4)");

//        HSSFCell cell43 = row4.createCell(3);
//        cell43.setCellStyle(cellStyle);
//        cell43.setCellValue("Ungraded(2)");
//
//        HSSFCell cell45 = row4.createCell(4);
//        cell45.setCellStyle(cellStyle);
//        cell45.setCellValue("overallRegion");
//
//        for (int i = 0; i < headList.size(); i++) {
//            HSSFCell cell46 = row4.createCell(6 + i);
//            cell46.setCellStyle(cellStyle);
//            String[] columnItemArray = headList.get(i);
//            cell46.setCellValue(columnItemArray[4]);
//        }

        // 合并日期占两行(4个参数，分别为起始行，结束行，起始列，结束列)
        // 行和列都是从0开始计数，且起始结束都会合并
        // 这里是合并excel中日期的两行为一行

        //设置第1行左边单元格合并
        CellRangeAddress gradeItemRegion = new CellRangeAddress(0, 0, 0, 6);
        sheet.addMergedRegion(gradeItemRegion);
        /*// 补充下面的操作，可以显示全部框线
//        RegionUtil.setBorderBottom(BorderStyle.THIN, gradeItemRegion, sheet);
//        RegionUtil.setBorderTop(BorderStyle.THIN, gradeItemRegion, sheet);
//        RegionUtil.setBorderLeft(BorderStyle.THIN, gradeItemRegion, sheet);
//        RegionUtil.setBorderRight(BorderStyle.THIN, gradeItemRegion, sheet);

//        CellRangeAddress typeRegion = new CellRangeAddress(1, 1, 0, 5);
//        sheet.addMergedRegion(typeRegion);
//        // 补充下面的操作，可以显示全部框线
//        RegionUtil.setBorderBottom(BorderStyle.THIN, typeRegion, sheet);
//        RegionUtil.setBorderTop(BorderStyle.THIN, typeRegion, sheet);
//        RegionUtil.setBorderLeft(BorderStyle.THIN, typeRegion, sheet);
//        RegionUtil.setBorderRight(BorderStyle.THIN, typeRegion, sheet);

//        CellRangeAddress dueRegion = new CellRangeAddress(2, 2, 0, 5);
//        sheet.addMergedRegion(dueRegion);
//        // 补充下面的操作，可以显示全部框线
//        RegionUtil.setBorderBottom(BorderStyle.THIN, dueRegion, sheet);
//        RegionUtil.setBorderTop(BorderStyle.THIN, dueRegion, sheet);
//        RegionUtil.setBorderLeft(BorderStyle.THIN, dueRegion, sheet);
//        RegionUtil.setBorderRight(BorderStyle.THIN, dueRegion, sheet);

//        CellRangeAddress calculatedWeightRegion = new CellRangeAddress(3, 3, 0, 5);
//        sheet.addMergedRegion(calculatedWeightRegion);
//        // 补充下面的操作，可以显示全部框线
//        RegionUtil.setBorderBottom(BorderStyle.THIN, calculatedWeightRegion, sheet);
//        RegionUtil.setBorderTop(BorderStyle.THIN, calculatedWeightRegion, sheet);
//        RegionUtil.setBorderLeft(BorderStyle.THIN, calculatedWeightRegion, sheet);
//        RegionUtil.setBorderRight(BorderStyle.THIN, calculatedWeightRegion, sheet);

//        CellRangeAddress overallRegion = new CellRangeAddress(4, 4, 4, 5);
//        sheet.addMergedRegion(overallRegion);
//        // 补充下面的操作，可以显示全部框线
//        RegionUtil.setBorderBottom(BorderStyle.THIN, overallRegion, sheet);
//        RegionUtil.setBorderTop(BorderStyle.THIN, overallRegion, sheet);
//        RegionUtil.setBorderLeft(BorderStyle.THIN, overallRegion, sheet);
//        RegionUtil.setBorderRight(BorderStyle.THIN, overallRegion, sheet);
//        sheet.setColumnWidth(0, 20 * 256);//设置列的宽度
//        sheet.setColumnWidth(1, 20 * 256);//设置列的宽度
//        sheet.setColumnWidth(2, 20 * 256);//设置列的宽度
//        sheet.setColumnWidth(3, 20 * 256);//设置列的宽度
//        sheet.setColumnWidth(4, 20 * 256);//设置列的宽度
//        sheet.setColumnWidth(5, 20 * 256);//设置列的宽度
//
//        sheet.setColumnWidth(6, 40 * 256);//设置列的宽度
//        sheet.setColumnWidth(7, 40 * 256);//设置列的宽度
//        sheet.setColumnWidth(8, 40 * 256);//设置列的宽度
//        sheet.setColumnWidth(9, 40 * 256);//设置列的宽度
//        sheet.setColumnWidth(10, 40 * 256);//设置列的宽度
//        sheet.setColumnWidth(11, 40 * 256);//设置列的宽度
//        sheet.setColumnWidth(12, 40 * 256);//设置列的宽度

        //CellRangeAddress region = new CellRangeAddress(1, 2, 0, 0);
        //sheet.setColumnWidth(0,  30* 256);//设置列的宽度*/
        File file = new File("/Users/dinny-xu/Desktop/demo.xlsx");
        FileOutputStream fout = new FileOutputStream(file);
        workbook.write(fout);
        fout.close();
    }

/*
    private static void generateRowAndCell(Sheet sheet, Workbook workbook, String value) {
        // 创建row
        Row row = sheet.createRow(1);
        row.setHeightInPoints(30);
        // 创建cell, 设置样式
        Cell cell = row.createCell(0);
        CellStyle cellStyle = workbook.createCellStyle();
        // 水平对齐
        cellStyle.setAlignment(HorizontalAlignment.LEFT);
        // 垂直对齐
        cellStyle.setVerticalAlignment(VerticalAlignment.BOTTOM);
        cell.setCellValue(value);
        cell.setCellStyle(cellStyle);
    }
*/


}

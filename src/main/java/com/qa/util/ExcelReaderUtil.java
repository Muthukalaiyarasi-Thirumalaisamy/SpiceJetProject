package com.qa.util;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Calendar;

import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelReaderUtil {

    public String path;
    public FileInputStream fis = null;
    public FileOutputStream fileOut = null;
    private XSSFWorkbook workbook = null;
    private XSSFSheet sheet = null;
    private XSSFRow row = null;
    private XSSFCell cell = null;

    public ExcelReaderUtil(String path) {
        this.path = path;
        try {
            fis = new FileInputStream(path);
            workbook = new XSSFWorkbook(fis);
            sheet = workbook.getSheetAt(0);
            fis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Returns the row count in a sheet
    public int getRowCount(String sheetName) {
        int index = workbook.getSheetIndex(sheetName);
        if (index == -1) {
            return 0;
        } else {
            sheet = workbook.getSheetAt(index);
            return sheet.getLastRowNum() + 1;
        }
    }

    // Returns the data from a cell
    public String getCellData(String sheetName, String colName, int rowNum) {
        try {
            if (rowNum <= 0) {
                return "";
            }

            int index = workbook.getSheetIndex(sheetName);
            if (index == -1) {
                return "";
            }

            sheet = workbook.getSheetAt(index);
            row = sheet.getRow(0);
            int colNum = -1;

            for (int i = 0; i < row.getLastCellNum(); i++) {
                if (row.getCell(i).getStringCellValue().trim().equals(colName.trim())) {
                    colNum = i;
                }
            }

            if (colNum == -1) {
                return "";
            }

            row = sheet.getRow(rowNum - 1);
            if (row == null) {
                return "";
            }
            cell = row.getCell(colNum);

            if (cell == null) {
                return "";
            }

            switch (cell.getCellType()) {
                case STRING:
                    return cell.getStringCellValue();
                case NUMERIC:
                case FORMULA:
                    if (DateUtil.isCellDateFormatted(cell)) {
                        Calendar cal = Calendar.getInstance();
                        cal.setTime(cell.getDateCellValue());
                        String cellText = cal.get(Calendar.DAY_OF_MONTH) + "/" +
                                (cal.get(Calendar.MONTH) + 1) + "/" + cal.get(Calendar.YEAR);
                        return cellText;
                    } else {
                        return String.valueOf(cell.getNumericCellValue());
                    }
                case BLANK:
                    return "";
                case BOOLEAN:
                    return String.valueOf(cell.getBooleanCellValue());
                default:
                    return "";
            }

        } catch (Exception e) {
            e.printStackTrace();
            return "row " + rowNum + " or column " + colName + " does not exist in xls";
        }
    }

    // Returns the data from a cell by column number
    public String getCellData(String sheetName, int colNum, int rowNum) {
        try {
            if (rowNum <= 0) {
                return "";
            }

            int index = workbook.getSheetIndex(sheetName);
            if (index == -1) {
                return "";
            }

            sheet = workbook.getSheetAt(index);
            row = sheet.getRow(rowNum - 1);
            if (row == null) {
                return "";
            }
            cell = row.getCell(colNum);
            if (cell == null) {
                return "";
            }

            switch (cell.getCellType()) {
                case STRING:
                    return cell.getStringCellValue();
                case NUMERIC:
                case FORMULA:
                    if (DateUtil.isCellDateFormatted(cell)) {
                        Calendar cal = Calendar.getInstance();
                        cal.setTime(cell.getDateCellValue());
                        return (cal.get(Calendar.MONTH) + 1) + "/" +
                                cal.get(Calendar.DAY_OF_MONTH) + "/" + cal.get(Calendar.YEAR);
                    } else {
                        return String.valueOf(cell.getNumericCellValue());
                    }
                case BLANK:
                    return "";
                case BOOLEAN:
                    return String.valueOf(cell.getBooleanCellValue());
                default:
                    return "";
            }

        } catch (Exception e) {
            e.printStackTrace();
            return "row " + rowNum + " or column " + colNum + " does not exist in xls";
        }
    }

    // Set data in a cell
    public boolean setCellData(String sheetName, String colName, int rowNum, String data) {
        try {
            fis = new FileInputStream(path);
            workbook = new XSSFWorkbook(fis);

            if (rowNum <= 0) {
                return false;
            }

            int index = workbook.getSheetIndex(sheetName);
            if (index == -1) {
                return false;
            }

            sheet = workbook.getSheetAt(index);
            row = sheet.getRow(0);
            int colNum = -1;

            for (int i = 0; i < row.getLastCellNum(); i++) {
                if (row.getCell(i).getStringCellValue().trim().equals(colName.trim())) {
                    colNum = i;
                }
            }

            if (colNum == -1) {
                return false;
            }

            row = sheet.getRow(rowNum - 1);
            if (row == null) {
                row = sheet.createRow(rowNum - 1);
            }

            cell = row.getCell(colNum);
            if (cell == null) {
                cell = row.createCell(colNum);
            }

            cell.setCellValue(data);

            fileOut = new FileOutputStream(path);
            workbook.write(fileOut);
            fileOut.close();

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    // Other methods (addSheet, removeSheet, etc.) remain unchanged.

    public boolean addSheet(String sheetName) {
        try {
            workbook.createSheet(sheetName);
            fileOut = new FileOutputStream(path);
            workbook.write(fileOut);
            fileOut.close();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean removeSheet(String sheetName) {
        try {
            int index = workbook.getSheetIndex(sheetName);
            if (index == -1) {
                return false;
            }

            workbook.removeSheetAt(index);
            fileOut = new FileOutputStream(path);
            workbook.write(fileOut);
            fileOut.close();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean isSheetExist(String sheetName) {
        return workbook.getSheetIndex(sheetName) != -1 || workbook.getSheetIndex(sheetName.toUpperCase()) != -1;
    }

    public int getColumnCount(String sheetName) {
        if (!isSheetExist(sheetName)) {
            return -1;
        }

        sheet = workbook.getSheet(sheetName);
        row = sheet.getRow(0);

        return row == null ? -1 : row.getLastCellNum();
    }
}

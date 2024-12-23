package com.qa.util;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class XlsReader {

    private String filePath;
    private Workbook workbook;
    private Sheet sheet;

    // Constructor to load the Excel file
    public XlsReader(String filePath) throws IOException {
        this.filePath = filePath;
        FileInputStream fis = new FileInputStream(new File(filePath));
        this.workbook = new XSSFWorkbook(fis);
    }

    /**
     * Adds a new sheet to the workbook.
     *
     * @param sheetName the name of the new sheet
     */
    public void addSheet(String sheetName) {
        if (isSheetExist(sheetName)) {
            throw new IllegalArgumentException("Sheet with name '" + sheetName + "' already exists.");
        }
        this.sheet = this.workbook.createSheet(sheetName);
    }

    /**
     * Checks if a sheet exists in the workbook.
     *
     * @param sheetName the name of the sheet
     * @return true if the sheet exists, false otherwise
     */
    public boolean isSheetExist(String sheetName) {
        return this.workbook.getSheet(sheetName) != null;
    }

    /**
     * Adds a new column to the sheet.
     *
     * @param colName the name of the column to add
     */
    public void addColumn(String colName) {
        if (this.sheet == null) {
            throw new IllegalStateException("No active sheet set. Use setSheet() to set the active sheet.");
        }
        Row row = this.sheet.getRow(0);
        if (row == null) {
            row = this.sheet.createRow(0);
        }
        int lastCellNum = row.getLastCellNum() == -1 ? 0 : row.getLastCellNum();
        Cell cell = row.createCell(lastCellNum);
        cell.setCellValue(colName);
    }

    /**
     * Adds a new row to the sheet.
     *
     * @param data the data to add in the new row
     */
    public void addRow(String[] data) {
        if (this.sheet == null) {
            throw new IllegalStateException("No active sheet set. Use setSheet() to set the active sheet.");
        }
        int lastRowNum = this.sheet.getLastRowNum();
        Row row = this.sheet.createRow(lastRowNum + 1);
        for (int i = 0; i < data.length; i++) {
            Cell cell = row.createCell(i);
            cell.setCellValue(data[i]);
        }
    }

    /**
     * Sets the active sheet by name.
     *
     * @param sheetName the name of the sheet to set as active
     */
    public void setSheet(String sheetName) {
        this.sheet = this.workbook.getSheet(sheetName);
        if (this.sheet == null) {
            throw new IllegalArgumentException("Sheet with name '" + sheetName + "' does not exist.");
        }
    }

    /**
     * Sets data to a specific cell.
     *
     * @param rowNum    the row number (0-based index)
     * @param colNum    the column number (0-based index)
     * @param data      the data to set in the cell
     */
    public void setCellData(int rowNum, int colNum, String data) {
        Row row = this.sheet.getRow(rowNum);
        if (row == null) {
            row = this.sheet.createRow(rowNum);
        }
        Cell cell = row.getCell(colNum);
        if (cell == null) {
            cell = row.createCell(colNum);
        }
        cell.setCellValue(data);
    }

    /**
     * Sets data to a specific cell by column name.
     *
     * @param sheetName the name of the sheet
     * @param rowNum    the row number (0-based index)
     * @param columnName the name of the column
     * @param data      the data to set in the cell
     */
    public void setCellDataByColumnName(String sheetName, int rowNum, String columnName, String data) {
        setSheet(sheetName); // Set the active sheet
        Row row = this.sheet.getRow(rowNum);
        if (row == null) {
            row = this.sheet.createRow(rowNum);
        }

        // Get the header row (first row)
        Row headerRow = this.sheet.getRow(0);
        if (headerRow == null) {
            // Initialize the header row if not already present
            headerRow = this.sheet.createRow(0);
        }

        // Find the column index based on the column name
        int colNum = -1;
        for (int i = 0; i < headerRow.getPhysicalNumberOfCells(); i++) {
            if (headerRow.getCell(i).getStringCellValue().equalsIgnoreCase(columnName)) {
                colNum = i;
                break;
            }
        }

        if (colNum == -1) {
            // Add the column if not found
            colNum = headerRow.getPhysicalNumberOfCells();
            Cell cell = headerRow.createCell(colNum);
            cell.setCellValue(columnName);
        }

        // Set the cell data
        Cell cell = row.getCell(colNum);
        if (cell == null) {
            cell = row.createCell(colNum);
        }
        cell.setCellValue(data);
    }

    /**
     * Sets data to an entire row based on the sheet name, row index, and values.
     *
     * @param sheetName the name of the sheet
     * @param rowNum    the row number (0-based index)
     * @param values    the values to be inserted into the row
     */
    public void setRowData(String sheetName, int rowNum, String[] values) {
        setSheet(sheetName); // Set the active sheet
        Row row = this.sheet.getRow(rowNum);
        if (row == null) {
            row = this.sheet.createRow(rowNum);
        }
        for (int i = 0; i < values.length; i++) {
            Cell cell = row.createCell(i);
            cell.setCellValue(values[i]);
        }
    }

    /**
     * Gets data from a specific cell.
     *
     * @param rowNum the row number (0-based index)
     * @param colNum the column number (0-based index)
     * @return the cell data as a string
     */
    public String getCellData(int rowNum, int colNum) {
        Row row = this.sheet.getRow(rowNum);
        if (row == null) return "";
        Cell cell = row.getCell(colNum);
        if (cell == null) return "";

        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    return cell.getDateCellValue().toString();
                } else {
                    return String.valueOf(cell.getNumericCellValue());
                }
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            case FORMULA:
                return cell.getCellFormula();
            case BLANK:
                return "";
            default:
                return "Unsupported cell type";
        }
    }

    /**
     * Gets the number of rows in a sheet.
     *
     * @param sheetName the name of the sheet
     * @return the number of rows
     */
    public int getRowCount(String sheetName) {
        Sheet sheet = this.workbook.getSheet(sheetName);
        return (sheet != null) ? sheet.getLastRowNum() + 1 : 0;
    }

    /**
     * Saves the workbook to the file.
     */
    public void save() throws IOException {
        try (FileOutputStream fos = new FileOutputStream(new File(filePath))) {
            this.workbook.write(fos);
        }
    }

    /**
     * Closes the workbook.
     */
    public void close() throws IOException {
        this.workbook.close();
    }

    public static void main(String[] args) {
        try {
            XlsReader excel = new XlsReader("TestData.xlsx");

            // Add a new sheet if it does not exist
            String sheetName = "TestSheet";
            if (!excel.isSheetExist(sheetName)) {
                excel.addSheet(sheetName);
            }

            // Set active sheet
            excel.setSheet(sheetName);

            // Write data to cells
            excel.setCellData(0, 0, "Name");
            excel.setCellData(0, 1, "Age");
            excel.setCellData(1, 0, "John");
            excel.setCellData(1, 1, "25");

            // Add a new column
            excel.addColumn("Address");

            // Add a new row
            excel.addRow(new String[]{"Alice", "30", "New York"});

            // Set data in a specific column by name
            excel.setCellDataByColumnName(sheetName, 2, "Age", "35");

            // Set data in an entire row
            excel.setRowData(sheetName, 3, new String[]{"Bob", "28", "Los Angeles"});

            // Read data from cells
            System.out.println(excel.getCellData(0, 0)); // Output: Name
            System.out.println(excel.getCellData(1, 1)); // Output: 25

            // Get row count
            System.out.println("Row count: " + excel.getRowCount(sheetName));

            // Save and close
            excel.save();
            excel.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

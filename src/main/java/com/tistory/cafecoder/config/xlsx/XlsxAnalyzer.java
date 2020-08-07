package com.tistory.cafecoder.config.xlsx;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

public class XlsxAnalyzer {

    private List<XSSFWorkbook> workbookList;
    private List<Integer> columnIndex;
    private Set<Map.Entry<String, Integer>> xlsxAnalyzeSet;

    public XlsxAnalyzer(List<MultipartFile> multipartFileList) {
        this.workbookList = new ArrayList<>();

        for (MultipartFile multipartFile : multipartFileList) {
            try {
                workbookList.add(new XSSFWorkbook(multipartFile.getInputStream()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        this.getColumns();
    }

    private void getColumns() {
        this.columnIndex = new ArrayList<>();

        XSSFSheet xssfSheet = this.workbookList.get(0).getSheetAt(workbookList.get(0).getNumberOfSheets() - 1);
        XSSFRow row = xssfSheet.getRow(0);

        for (int index = 0, size = row.getPhysicalNumberOfCells(); index <= size; ++index) {
            switch (row.getCell(index).toString()) {
                case "상품명":
                case "옵션 정보":
                case "수량":
                    this.columnIndex.add(index);
                    break;
            }
        }
    }

    public Set<Map.Entry<String, Integer>> analyzer() {
        Map<String, Integer> dataMap = new HashMap<>();

        XSSFSheet xssfSheet = this.workbookList.get(0).getSheetAt(workbookList.get(0).getNumberOfSheets() - 1);
        Integer maxRow = xssfSheet.getPhysicalNumberOfRows();

        for (int index = 0; index < maxRow; ++index) {
            XSSFRow xssfRow = xssfSheet.getRow(index);

            String productName = xssfRow.getCell(this.columnIndex.get(0)).toString();
            String[] options = xssfRow.getCell(this.columnIndex.get(1)).toString().split("/");
            Integer amount = Integer.parseInt(xssfRow.getCell(this.columnIndex.get(2)).toString());

            for (String option : options) {
                if (!option.toUpperCase().equals("FREE") && option.contains("1개")) {
                    StringBuilder productData = new StringBuilder(productName + "-" + option + "-FREE");

                    try {
                        dataMap.put(productData.toString(), dataMap.get(productName) + amount);
                    }
                    catch (NullPointerException e) {
                        dataMap.put(productData.toString(), amount);
                    }
                }
            }
        }

        return dataMap.entrySet();
    }

    public Set<Map.Entry<String, Integer>> getXlsxAnalyzeSet () {
        return this.xlsxAnalyzeSet;
    }
}

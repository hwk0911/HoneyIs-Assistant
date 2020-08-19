package com.tistory.cafecoder.config.xlsx;

import com.tistory.cafecoder.config.xlsx.dto.XlsxDto;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

public class XlsxAnalyzer {

    private List<XSSFWorkbook> workbookList;
    private Map<String, Integer> columMap;

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
        this.columMap = new HashMap<>();

        XSSFSheet xssfSheet = this.workbookList.get(0).getSheetAt(workbookList.get(0).getNumberOfSheets() - 1);
        XSSFRow row = xssfSheet.getRow(0);

        for (int index = 0, size = row.getPhysicalNumberOfCells(); index < size; ++index) {
            switch (row.getCell(index).toString()) {
                case "상품명":
                case "옵션 정보":
                case "수량":
                    this.columMap.put(row.getCell(index).toString(), index);
                    break;
            }
        }
    }

    public List<XlsxDto> analyzer() {
        List<XlsxDto> analyzerList = new ArrayList<>();

        XSSFSheet xssfSheet = this.workbookList.get(0).getSheetAt(workbookList.get(0).getNumberOfSheets() - 1);
        Integer maxRow = xssfSheet.getPhysicalNumberOfRows();

        for (int index = 1; index < maxRow; ++index) {
            XSSFRow xssfRow = xssfSheet.getRow(index);

            String productName = xssfRow.getCell(this.columMap.get("상품명")).toString();
            String[] options = xssfRow.getCell(this.columMap.get("옵션 정보")).toString().split("/");
            Long amount = Long.parseLong(xssfRow.getCell(this.columMap.get("수량")).toString().split("\\.")[0]);

            for(String option : options) {
                if(option.contains("1개") || option.toUpperCase().equals("FREE")) {
                    continue;
                }
                else {
                    analyzerList.add(new XlsxDto(productName, option, amount));
                }
            }
        }
        return analyzerList;
    }
}

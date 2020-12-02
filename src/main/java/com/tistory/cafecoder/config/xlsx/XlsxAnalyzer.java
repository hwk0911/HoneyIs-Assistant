package com.tistory.cafecoder.config.xlsx;

import com.tistory.cafecoder.config.xlsx.dto.XlsxDto;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.poifs.filesystem.NPOIFSFileSystem;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class XlsxAnalyzer {

    private List<Workbook> workbookList;
    private Map<String, Integer> columMap;

    public XlsxAnalyzer(List<MultipartFile> multipartFileList) {
        this.workbookList = new ArrayList<>();

        for (MultipartFile multipartFile : multipartFileList) {
            try {
                if(multipartFile.getOriginalFilename().contains("xlsx")) {
                    this.workbookList.add(WorkbookFactory.create(multipartFile.getInputStream()));
                }
                else {
                    NPOIFSFileSystem npoifsFileSystem = new NPOIFSFileSystem(multipartFile.getInputStream());
                    this.workbookList.add(WorkbookFactory.create(npoifsFileSystem));
                }
            } catch (IOException | InvalidFormatException e) {
                System.out.println(e.getMessage());
            }
        }

        this.getColumns();
    }

    private void getColumns() {
        this.columMap = new HashMap<>();

        Sheet Sheet = this.workbookList.get(0).getSheetAt(workbookList.get(0).getNumberOfSheets() - 1);
        Row row = Sheet.getRow(0);

        for (int index = 0, size = row.getPhysicalNumberOfCells(); index < size; ++index) {
            switch (row.getCell(index).toString()) {
                case "상품명":
                case "수량":
                    this.columMap.put(row.getCell(index).toString(), index);
                    break;
                case "옵션 정보":
                case "옵션정보":
                    this.columMap.put("옵션 정보", index);
                    break;
            }
        }
    }

    public List<XlsxDto> analyzer() {
        List<XlsxDto> analyzerList = new ArrayList<>();

        Sheet xssfSheet = this.workbookList.get(0).getSheetAt(workbookList.get(0).getNumberOfSheets() - 1);
        Integer maxRow = xssfSheet.getPhysicalNumberOfRows();

        for (int index = 1; index < maxRow; ++index) {
            Row row = xssfSheet.getRow(index);

            String productName = row.getCell(this.columMap.get("상품명")).toString();
            String[] options = row.getCell(this.columMap.get("옵션 정보")).toString().split("/");
            Long amount = Long.parseLong(row.getCell(this.columMap.get("수량")).toString().split("\\.")[0]);

            for(String option : options) {
                if(option.contains("1개") || option.toUpperCase().equals("FREE") || option.equals("단품")) {
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

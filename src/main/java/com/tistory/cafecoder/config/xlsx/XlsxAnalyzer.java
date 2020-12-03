package com.tistory.cafecoder.config.xlsx;

import com.tistory.cafecoder.config.xlsx.dto.XlsxDto;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.poifs.filesystem.NPOIFSFileSystem;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

public class XlsxAnalyzer {

    private List<Workbook> workbookList;
    private List<WorkbookSetter> workbookSetters;

    public XlsxAnalyzer(List<MultipartFile> multipartFileList) {
        this.workbookList = new ArrayList<>();

        for (MultipartFile multipartFile : multipartFileList) {
            try {
                if (multipartFile.getOriginalFilename().contains("xlsx")) {
                    this.workbookList.add(WorkbookFactory.create(multipartFile.getInputStream()));
                } else {
                    NPOIFSFileSystem npoifsFileSystem = new NPOIFSFileSystem(multipartFile.getInputStream());
                    this.workbookList.add(WorkbookFactory.create(npoifsFileSystem));
                }
            } catch (IOException | InvalidFormatException e) {
                System.out.println(e.getMessage());
            }
        }

        this.workbookSetters = new ArrayList<>();

        for(Workbook workbook : this.workbookList) {
            this.workbookSetters.add(this.getColumns(workbook));
        }
    }

    private WorkbookSetter getColumns(Workbook workbook) {
        Map<String, Integer> columMap = new HashMap<>();

        Sheet Sheet = workbook.getSheetAt(workbook.getNumberOfSheets() - 1);
        Row row = Sheet.getRow(0);

        for (int index = 0, size = row.getPhysicalNumberOfCells(); index < size; ++index) {
            switch (row.getCell(index).toString()) {
                case "상품명":
                case "수량":
                    columMap.put(row.getCell(index).toString(), index);
                    break;
                case "옵션 정보":
                case "옵션정보":
                    columMap.put("옵션 정보", index);
                    break;
            }
        }

        return new WorkbookSetter(workbook, columMap);
    }

    public List<XlsxDto> analyzer() {
        List<XlsxDto> analyzerList = new ArrayList<>();

        for(WorkbookSetter workbookSetter : this.workbookSetters) {

            Sheet xssfSheet = workbookSetter.getWorkbook().getSheetAt(workbookSetter.getWorkbook().getNumberOfSheets() - 1);
            Integer maxRow = xssfSheet.getPhysicalNumberOfRows();

            for (int index = 1; index < maxRow; ++index) {
                Row row = xssfSheet.getRow(index);

                String productName = row.getCell(workbookSetter.getColumMap().get("상품명")).toString();
                String[] options = row.getCell(workbookSetter.getColumMap().get("옵션 정보")).toString().split("/");
                Long amount = Long.parseLong(row.getCell(workbookSetter.getColumMap().get("수량")).toString().split("\\.")[0]);

                for (String option : options) {
                    if (option.contains("1개") || option.toUpperCase().equals("FREE") || option.equals("단품")) {
                        continue;
                    } else {
                        analyzerList.add(new XlsxDto(productName, option, amount));
                    }
                }
            }
        }

        return analyzerList;
    }
}

package com.tistory.cafecoder.config.xlsx;

import lombok.Getter;
import org.apache.poi.ss.usermodel.Workbook;

import java.util.Map;

@Getter
public class WorkbookSetter {
    private Workbook workbook;
    private Map<String, Integer> columMap;

    public WorkbookSetter (Workbook workbook, Map<String, Integer> columMap) {
        this.workbook = workbook;
        this.columMap = columMap;
    }
}

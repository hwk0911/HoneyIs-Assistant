package com.tistory.cafecoder.config.xlsx;

import com.tistory.cafecoder.domain.product.ColorRepository;
import com.tistory.cafecoder.domain.product.ProductRepository;
import com.tistory.cafecoder.domain.product.Size;
import com.tistory.cafecoder.domain.product.SizeRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@Getter
public class XlsxAnalyzer {
    private SizeRepository sizeRepository;
    private ColorRepository colorRepository;
    private ProductRepository productRepository;

    private List<XSSFWorkbook> workbookList;
    private List<Integer> columnIndexList;
    private Set<Map.Entry<String, Integer>> xlsxSet;

    public XlsxAnalyzer(SizeRepository sizeRepository, ColorRepository colorRepository, ProductRepository productRepository) {
        this.sizeRepository = sizeRepository;
        this.colorRepository = colorRepository;
        this.productRepository = productRepository;
    }

    public XlsxAnalyzer setMultipartFile (List<MultipartFile> xlsxFileList) {
        this.workbookList = this.transFile(xlsxFileList);
        this.columnIndexList = this.setColumnIndexList();

        return this;
    }

    private List<XSSFWorkbook> transFile(List<MultipartFile> xlsxFileList) {
        List<XSSFWorkbook> workbookList = new ArrayList<>();

        for(MultipartFile multipartFile : xlsxFileList) {
            try{
                workbookList.add(new XSSFWorkbook(multipartFile.getInputStream()));
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }

        return workbookList;
    }

    private List<Integer> setColumnIndexList() {
        List<Integer> orderColumns = new ArrayList<>();

        XSSFWorkbook workbook = this.workbookList.get(0);
        XSSFSheet sheet = workbook.getSheetAt(workbook.getNumberOfSheets() - 1);

        XSSFRow row = sheet.getRow(0);

        int cells = row.getPhysicalNumberOfCells();
        for(int column = 0; column <= cells ; ++column) {
            XSSFCell cell = row.getCell(column);

            if(cell == null) {
                continue;
            }
            else {
                switch (cell.toString()) {
                    case "상품명":
                    case "옵션 정보":
                    case "수량":
                        orderColumns.add(column);
                        break;
                    default:
                        continue;
                }
            }
        }

        return orderColumns;
    }

    public XlsxAnalyzer analyzer() {
        Map<String, Integer> orderMap = new HashMap<>();

        for(XSSFWorkbook workbook : this.workbookList) {
            XSSFSheet sheet = workbook.getSheetAt(workbook.getNumberOfSheets() - 1);

            int size = sheet.getPhysicalNumberOfRows();

            for(int index = 0 ; index < size ; ++index) {
                XSSFRow row = sheet.getRow(index);

                Long name = this.productRepository.findByName(row.getCell(columnIndexList.get(0)).toString()).getId();
                String options = row.getCell(columnIndexList.get(1)).toString();
                Integer amount = Integer.parseInt(row.getCell(columnIndexList.get(2)).toString());

                List<Long> sizeList = new ArrayList<>();
                List<Long> colorList = new ArrayList<>();

                for(String option : options.split("/")) {
                    option = option.toUpperCase();

                    if(this.sizeRepository.findBySize(option) != null) {
                        sizeList.add(this.sizeRepository.findBySize(option).getId());
                    }
                    if(this.colorRepository.findByColor(option) != null) {
                        colorList.add(this.colorRepository.findByColor(option).getId());
                    }
                }

                if(sizeList.isEmpty()) {
                    sizeList.add(this.sizeRepository.findBySize("FREE").getId());
                }

                for(Long colorId : colorList) {
                    for(Long sizeId : sizeList) {
                        Integer tempAmount = 0;

                        if(orderMap.containsKey(name + "-" + colorId + "-" + sizeId)) {
                            tempAmount = orderMap.get(name + "-" + colorId + "-" + sizeId);
                        }

                        orderMap.put(name + "-" + colorId + "-" + sizeId, amount + tempAmount);
                    }
                }
            }
        }

        this.xlsxSet = orderMap.entrySet();

        return this;
    }
}

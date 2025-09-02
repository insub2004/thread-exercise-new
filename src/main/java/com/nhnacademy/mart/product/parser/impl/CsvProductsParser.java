package com.nhnacademy.mart.product.parser.impl;

import com.nhnacademy.mart.product.domain.Product;
import com.nhnacademy.mart.product.parser.ProductParser;
import com.nhnacademy.mart.product.util.ProductIdGenerator;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class CsvProductsParser implements ProductParser {

    //제품 기본 수량
    private final int DEFAULT_QUANTITY = 100;
    private final InputStream inputStream;

    public CsvProductsParser() {
        inputStream = getProductsInputStream();
    }

    public CsvProductsParser(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    @Override
    public List<Product> parse() {
        List<Product> products = new ArrayList<>();

        try {
            CSVParser parser = CSVParser.parse(inputStream, StandardCharsets.UTF_8, CSVFormat.EXCEL);
            List<CSVRecord> records = parser.getRecords();

            // 0은 헤더
            for (int i = 1; i < records.size(); i++) {
                Product product = getProductFromRecord(records, i);
                products.add(product);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return products;
    }

    private Product getProductFromRecord(List<CSVRecord> records, int i) {
        CSVRecord record = records.get(i);
        String itemName = record.get(0);
        String maker = record.get(1);
        String specification = record.get(2);
        String unit = record.get(3);
        int price = 0;
        String stringPriceWithComma = record.get(4);
        String stringPrice = stringPriceWithComma.replaceAll(",", "");
        if (!StringUtils.isEmpty(stringPrice) && StringUtils.isNumeric(stringPrice)) {
            price = Integer.parseInt(stringPrice);
        }
        long id = ProductIdGenerator.getNewProductId();
        return new Product(
                id,
                itemName,
                maker,
                specification,
                unit,
                price,
                DEFAULT_QUANTITY
        );
    }
}

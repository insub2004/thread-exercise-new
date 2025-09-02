package com.nhnacademy.mart.product.parser;

import com.nhnacademy.mart.product.domain.Product;

import java.io.InputStream;
import java.util.List;

/**
 * /src/main/.../resource 경로에 있는 csv 파일을 파싱하기 위한 인터페이스
 */
public interface ProductParser {
    String PRODUCT_DATA = "product_data.csv";

    List<Product> parse();

    default InputStream getProductsInputStream() {
        return this.getClass()
                .getClassLoader()
                .getResourceAsStream(PRODUCT_DATA);
    }
}

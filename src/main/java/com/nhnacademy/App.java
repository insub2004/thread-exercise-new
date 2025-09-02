package com.nhnacademy;

import com.nhnacademy.customer.generator.CustomerGenerator;
import com.nhnacademy.mart.entering.EnteringQueue;
import com.nhnacademy.mart.product.parser.impl.CsvProductsParser;
import com.nhnacademy.mart.product.repository.ProductRepository;
import com.nhnacademy.mart.product.repository.impl.MemoryProductRepository;
import com.nhnacademy.mart.product.service.impl.ProductServiceImpl;

public class App {
    public static void main(String[] args) {
        EnteringQueue enteringQueue = new EnteringQueue(50);
        CustomerGenerator customerGenerator = new CustomerGenerator(enteringQueue);

        Thread enteringThread = new Thread(customerGenerator);
        enteringThread.setName("entering-thread");
        enteringThread.start();

        ProductRepository productRepository = new MemoryProductRepository();
        CsvProductsParser csvProductsParser = new CsvProductsParser();
        ProductServiceImpl productService = new ProductServiceImpl(productRepository, csvProductsParser);

    }
}

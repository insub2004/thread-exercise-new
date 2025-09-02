package com.nhnacademy.mart.product.service.impl;

import com.nhnacademy.mart.product.domain.Product;
import com.nhnacademy.mart.product.exception.OutOfStackException;
import com.nhnacademy.mart.product.exception.ProductAlreadyExistsException;
import com.nhnacademy.mart.product.exception.ProductNotFoundException;
import com.nhnacademy.mart.product.parser.ProductParser;
import com.nhnacademy.mart.product.repository.ProductRepository;
import com.nhnacademy.mart.product.repository.impl.MemoryProductRepository;
import com.nhnacademy.mart.product.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ProductServiceImplTest {
    ProductRepository productRepository;
    ProductParser productParser;

    ProductService productService;

    @BeforeEach
    void setUp() {
        productRepository = Mockito.spy(MemoryProductRepository.class);
        productParser = Mockito.mock(ProductParser.class);
        Mockito.when(productParser.parse()).thenReturn(Collections.emptyList());

        productService = new ProductServiceImpl(productRepository, productParser);
    }

    // 인터페이스 구현 객체인지 검증
    @Test
    @Order(1)
    @DisplayName("instance of ProductService")
    void instanceOf() {
        assertInstanceOf(ProductService.class, productService);
    }

    // 생성자 validation
    @Test
    @Order(2)
    @DisplayName("constructor null check")
    void constructorNullCheck() {
        assertAll(
                () -> assertThrows(IllegalArgumentException.class, () -> new ProductServiceImpl(null, productParser)),
                () -> assertThrows(IllegalArgumentException.class, () -> new ProductServiceImpl(productRepository, null))
        );
    }

    @Test
    @Order(3)
    @DisplayName("product 조회 - 성공")
    void getProduct() {
        Product excepted = new Product(1l, "주방세제", "LG", "(750㎖) 자연퐁 스팀워시 레몬", "개", 9900, 100);
        Mockito.doReturn(Optional.of(excepted))
                .when(productRepository).findById(Mockito.anyLong());

        Product actual = productService.getProduct(1l);

        assertEquals(excepted, actual);
    }

    @Test
    @Order(4)
    @DisplayName("product 조회 - 없는 제품 조회 시도")
    void getProduct_NotFound() {
        Mockito.doReturn(Optional.empty())
                .when(productRepository).findById(Mockito.anyLong());

        assertThrows(ProductNotFoundException.class, () -> productService.getProduct(22L));
    }

    @Test
    @Order(5)
    @DisplayName("product 등록 - 성공")
    void saveProduct() {
        Mockito.when(productRepository.existsById(Mockito.anyLong())).thenReturn(false);
        Mockito.doNothing().when(productRepository).save(Mockito.any(Product.class));

        Product product = new Product(1L, "주방세제", "LG", "(750㎖) 자연퐁 스팀워시 레몬", "개", 9900, 100);
        productService.saveProduct(product);

        Mockito.verify(productRepository, Mockito.times(1)).existsById(Mockito.anyLong());
        Mockito.verify(productRepository).save(Mockito.any(Product.class));    // times 생략시 default=1
    }

    @Test
    @Order(6)
    @DisplayName("product 등록 - 실패, 이미 동일한 제품 등록 시도")
    void saveProduct_AlreadyExist() {
        Mockito.doNothing().when(productRepository).save(Mockito.any(Product.class));
        Mockito.when(productRepository.existsById(Mockito.anyLong())).thenReturn(true);

        Product product = new Product(1l, "주방세제", "LG", "(750㎖) 자연퐁 스팀워시 레몬", "개", 9900, 100);

        assertThrows(ProductAlreadyExistsException.class, () -> productService.saveProduct(product));
        Mockito.verify(productRepository, Mockito.times(1)).existsById(Mockito.anyLong());
    }

    @Test
    @Order(7)
    @DisplayName("product 삭제 - 성공")
    void deleteProduct() {
        Mockito.when(productRepository.existsById(Mockito.anyLong())).thenReturn(true);

        productService.deleteProduct(1L);

        Mockito.verify(productRepository, Mockito.times(1)).deleteById(Mockito.anyLong());
    }

    @Test
    @Order(8)
    @DisplayName("product 삭제 - 실패")
    void deleteProduct_NotFound() {
        Mockito.when(productRepository.existsById(Mockito.anyLong())).thenReturn(false);

        assertThrows(ProductNotFoundException.class, () -> productService.deleteProduct(1L));
        Mockito.verify(productRepository, Mockito.times(1)).existsById(Mockito.anyLong());
    }

    @Test
    @Order(9)
    @DisplayName("수량 변경 - 성공")
    void updateQuantity() {
        Mockito.doReturn(false, true).when(productRepository).existsById(Mockito.anyLong());
        // todo [insub] save() 내부에서는 false가 나와야 하고, update() 내부에서는 ture가 나와줘야 한다!!

        // spy - save(),updateQuantity()는 실제 메서드 사용해보기
        Product expected = new Product(1L, "주방세제", "LG", "(750㎖) 자연퐁 스팀워시 레몬", "개", 9900, 100);
        productService.saveProduct(expected);

        Product savedProduct = productService.getProduct(1L);
        assertEquals(expected, savedProduct);
        assertEquals(1, productService.getTotalCount());

        productService.updateQuantity(savedProduct.getId(), 22);
        Product updatedProduct = productService.getProduct(1L);
        assertEquals(22, updatedProduct.getQuantity());
    }

    @Test
    @Order(10)
    @DisplayName("수량 변경 - 실패")
    void updateQuantity_fail() {
        Mockito.doReturn(true).when(productRepository).existsById(Mockito.anyLong());
        assertThrows(IllegalArgumentException.class, () -> productService.updateQuantity(1L, -1));
    }

    @Test
    @Order(11)
    @DisplayName("장바구니에 제품 담기 - 성공")
    void pickProduct() {
        Product expected = new Product(1L, "주방세제", "LG", "(750㎖) 자연퐁 스팀워시 레몬", "개", 9900, 100);
        productService.saveProduct(expected);

        productService.pickProduct(1L, 33);

        Product updatedProduct = productService.getProduct(1L);
        assertEquals(67, updatedProduct.getQuantity());
    }

    @Test
    @Order(12)
    @DisplayName("장바구니에 제품 담기 - 실패(OutOfStackException)")
    void pickProduct_fail() {
        Product expected = new Product(1L, "주방세제", "LG", "(750㎖) 자연퐁 스팀워시 레몬", "개", 9900, 100);
        productService.saveProduct(expected);

        assertThrows(OutOfStackException.class, () -> productService.pickProduct(1L, 300));
    }
}
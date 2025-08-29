package com.nhnacademy.customer.domain;

import ch.qos.logback.core.testUtil.RandomUtil;
import com.nhnacademy.customer.exception.InsufficientFundsException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.Month;
import java.util.Random;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CustomerTest {

    Customer customer;

    @BeforeEach
    void setCustomerEach() {
        customer = new Customer(1L, "홍길동", 1_000_000);
    }

    @Test
    @Order(1)
    @DisplayName("고객 생성자 id가 1보다 작을 경우 예외 발생")
    void invalidIdArgument() {
        int negativeInt = (-1) * RandomUtil.getPositiveInt();
        assertThrows(IllegalArgumentException.class, () -> new Customer(negativeInt, "이순신", 1_000));
    }

    @Test
    @Order(2)
    @DisplayName("고객 생성자 name이 비어있거나 null일 경우 예외 발생")
    void invalidNameArgument() {
        assertAll(
                () -> assertThrows(IllegalArgumentException.class, () -> new Customer(1L, "", 1_000)),
                () -> assertThrows(IllegalArgumentException.class, () -> new Customer(1L, null, 1_000))
        );
    }

    @Test
    @Order(3)
    @DisplayName("고객 생성자 money가 음수일 경우 예외 발생")
    void invalidMoneyArgument() {
        int negativeInt = (-1) * RandomUtil.getPositiveInt();
        assertThrows(IllegalArgumentException.class, () -> new Customer(1L, "홍길동", negativeInt));
    }

    @Test
    @Order(4)
    @DisplayName("pay 메서드 정상 동작 확인")
    void successPay() throws InsufficientFundsException {
        int amount = 700_000;
        customer.pay(amount);
        int money = customer.getMoney();
        assertEquals(300_000, money);
    }

    @Order(5)
    @DisplayName("pay 메서드 실패")
    @ParameterizedTest(name = "{index} : {arguments} is negative")
    @MethodSource("provideNegativeInteger")
    void failPay(int input) {
        // 파라미터로 음수 전달할 경우, 예외 발생
        assertThrows(IllegalArgumentException.class, () -> customer.pay(input));
        // amount가 고객이 소지하고 있는 금액보다 많을 경우, 예외 발생
        // TODO [insub] checked 예외인데 assertThrows 안에 예외를 명시해놨기 때문에 따로 예외를 처리하는 로직이 필요 없는 건가?
        assertThrows(InsufficientFundsException.class, () -> customer.pay(10_000_000));
    }

    private static Stream<Arguments> provideNegativeInteger() {
        return Stream.of(
                Arguments.of(-1, -2, -3),
                Arguments.of(-5, -44),
                Arguments.arguments(-4,-5)
        );
    }
}
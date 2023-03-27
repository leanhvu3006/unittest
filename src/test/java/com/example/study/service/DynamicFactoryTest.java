package com.example.study.service;

import static com.example.study.service.CustomerService.VALID_NUMERIC;

import java.util.Arrays;
import java.util.Collection;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class DynamicFactoryTest {

  @BeforeEach
  void beforeEach(TestInfo info) {
    System.out.println("Before execute "+info.getTestMethod().get().getName());
  }

  @AfterEach
  void afterEach(TestInfo info) {
    System.out.println("After execute "+info.getTestMethod().get().getName());
  }
  /**
    DynamicTest is a test case generated at runtime.
    must return a Stream, Collection, Iterable, Iterator, Array
    @TestFactory method cannot be static or private.
    not support lifecycle callbacks (@BeforeEach, @AfterEach)
   */
  @TestFactory
  Collection<DynamicTest> dynamicTestsFromCollection() {
    return Arrays.asList(
        DynamicTest.dynamicTest("Add test",
            () -> Assertions.assertEquals(2, Math.addExact(1, 1))),
        DynamicTest.dynamicTest("Multiply Test",
            () -> Assertions.assertEquals(4, Math.multiplyExact(2, 2))),
        DynamicTest.dynamicTest("Test",
            () -> Assertions.assertEquals(9, Math.multiplyExact(3, 3))),
        DynamicTest.dynamicTest("Test String",
            () -> Assertions.assertEquals("Valid numeric", VALID_NUMERIC)));
  }
}

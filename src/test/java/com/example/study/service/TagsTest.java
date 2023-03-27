package com.example.study.service;

import static com.example.study.service.CustomerService.VALID_NUMERIC;

import com.example.study.enums.CustomerStatus;
import java.time.LocalDate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class TagsTest {

  /**
   * filter test with env
   */
  @InjectMocks
  private CustomerService customerService;

  @Test
  @DisplayName(value = "integration test")
  @Tag("IntegrationTest")
  void integrationTest() {
    final String actual = customerService.validateNumeric("1");
    Assertions.assertEquals(VALID_NUMERIC, actual);
  }

  @Test
  @Tag("UnitTest")
  @DisplayName(value = "unit test")
  void unitTest() {
    final String actual = customerService.csvResource("csv title", CustomerStatus.SUCCESS, LocalDate.parse("2020-12-20"));
    final String expect = String.format("%s, %s, %s", "csv title", CustomerStatus.SUCCESS, LocalDate.parse("2020-12-20"));
    Assertions.assertEquals(expect, actual);
  }

  @Test
  @Tag("IntegrationTest")
  @Tag("UnitTest")
  @DisplayName(value = "integration test and unit test")
  void integrationTestAndUnitTest() {
    Assertions.assertTrue(customerService.validateNumberGreaterThan10(12));
  }
}

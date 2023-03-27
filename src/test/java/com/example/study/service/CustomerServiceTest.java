package com.example.study.service;

import static com.example.study.service.CustomerService.INVALID_NUMERIC;
import static com.example.study.service.CustomerService.VALID_NUMERIC;

import com.example.study.entitys.CustomerEntity;
import com.example.study.enums.CustomerStatus;
import com.example.study.repositorys.CustomerRepository;
import java.time.LocalDate;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

  @Mock
  private CustomerRepository customerRepository;

  @InjectMocks
  private CustomerService customerService;

  @ParameterizedTest
  @EnumSource(value = CustomerStatus.class)
//  @EnumSource(value = CustomerStatus.class, names = {"MISSING", "SUCCESS"})
  void createCustomer_successful(CustomerStatus customerStatus) {
    final CustomerStatus status;
    if (CustomerStatus.SUCCESS.equals(customerStatus) || CustomerStatus.FAILED.equals(customerStatus)) {
      status = customerStatus;
    } else {
      status = CustomerStatus.PROCESS;
    }
    final CustomerEntity customerEntity = CustomerEntity.builder()
        .customerStatus(status)
        .build();
    Mockito.when(customerRepository.save(customerEntity)).thenReturn(customerEntity);
    final var actual = customerService.createCustomer(status);
    Assertions.assertEquals(status, actual.getCustomerStatus());
  }

  @ParameterizedTest
  @MethodSource(value = {"listNumeric"})
  void validateNumeric(String numeric) {
    final String actual = customerService.validateNumeric(numeric);
    if(StringUtils.isNumeric(numeric)) {
      Assertions.assertEquals(VALID_NUMERIC, actual);
    } else {
      Assertions.assertEquals(INVALID_NUMERIC, actual);
    }
  }

  private static List<String> listNumeric() {
    return List.of(
        "0983220401",
        "0912341234",
        "abc1235512",
        "123049239z",
        "09 1230949",
        "00   23234"
    );
  }

  @ParameterizedTest
  @CsvSource({
      "Write a blog post, SUCCESS, 2020-12-20",
      "Wash the car, FAILED, 2020-12-15"
  })
  void csvResource_success(String title, CustomerStatus status, LocalDate date) {
    final String actual = customerService.csvResource(title, status, date);
    final String expect = String.format("%s, %s, %s", title, status, date);
    Assertions.assertEquals(expect, actual);
  }

  @ParameterizedTest
  @CsvFileSource(resources =  "/file_csv.csv")
  void csvFileResource_success(String title, CustomerStatus status, LocalDate date) {
    final String actual = customerService.csvResource(title, status, date);
    final String expect = String.format("%s, %s, %s", title, status, date);
    Assertions.assertEquals(expect, actual);
  }

  @ParameterizedTest
  @ValueSource(ints = {1,12,4,16,7,9,10,20})
  void validateNumberGreaterThan10_successful(Integer number) {
    if(number > 10) {
      Assertions.assertTrue(customerService.validateNumberGreaterThan10(number));
    } else {
      Assertions.assertFalse(customerService.validateNumberGreaterThan10(number));
    }
  }

}
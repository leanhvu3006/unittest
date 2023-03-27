package com.example.study.service;

import com.example.study.entitys.CustomerEntity;
import com.example.study.enums.CustomerStatus;
import com.example.study.repositorys.CustomerRepository;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomerService {

  public static final String VALID_NUMERIC = "Valid numeric";
  public static final String INVALID_NUMERIC = "Invalid numeric";
  private final CustomerRepository customerRepository;

  public CustomerEntity createCustomer(CustomerStatus customerStatus) {
    final CustomerStatus status;
    if (CustomerStatus.SUCCESS.equals(customerStatus) || CustomerStatus.FAILED.equals(customerStatus)) {
      status = customerStatus;
    } else {
      status = CustomerStatus.PROCESS;
    }
    log.info(status.name());
    return customerRepository.save(CustomerEntity.builder()
        .customerStatus(status)
        .build());
  }

  public String validateNumeric(String numeric) {
    if(StringUtils.isNumeric(numeric)) {
      log.info(VALID_NUMERIC);
      return VALID_NUMERIC;
    }
    log.info(INVALID_NUMERIC);
    return INVALID_NUMERIC;
  }

  public String csvResource(String title, CustomerStatus status, LocalDate date) {
    log.info("title: {}, status: {}, date: {}", title, status, date);
    return String.format("%s, %s, %s", title, status, date);
  }

  public Boolean validateNumberGreaterThan10(Integer number) {
    log.info(String.valueOf((number > 10)));
    return number > 10;
  }

}

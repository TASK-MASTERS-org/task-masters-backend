package com.LMS.service;

import com.LMS.entity.Driver;
import com.LMS.utils.ApiResponse;

public interface DriverService {
  ApiResponse saveDriver(Driver driver);

  ApiResponse getAllDrivers();

  ApiResponse getDriverById(Long id);
  ApiResponse deleteDriver(Long id);

  ApiResponse updateDriver(Long id, Driver updatedDriver);
}

package com.icss.xihu.service;

import com.icss.xihu.model.Address;

import java.util.List;

public interface AddressService {
    List<Address> getProvinces();
    List<Address> getCitiesByProvinceId(Integer provinceId);
}

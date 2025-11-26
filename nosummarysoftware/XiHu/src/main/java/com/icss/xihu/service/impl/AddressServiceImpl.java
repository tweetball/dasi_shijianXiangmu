package com.icss.xihu.service.impl;

import com.icss.xihu.mapper.AddressMapper;
import com.icss.xihu.model.Address;
import com.icss.xihu.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressServiceImpl implements AddressService {
    @Autowired
    private AddressMapper addressMapper;

    @Override
    public List<Address> getProvinces() {
        return addressMapper.selectProvinces();
    }

    @Override
    public List<Address> getCitiesByProvinceId(Integer provinceId) {
        return addressMapper.selectCitiesByProvinceId(provinceId);
    }
}

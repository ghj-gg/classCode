package com.sfac.springcloud.springCloudClientTest.modules.test.service;

import com.github.pagehelper.PageInfo;
import com.sfac.springcloud.springCloudClientTest.modules.common.vo.Result;
import com.sfac.springcloud.springCloudClientTest.modules.common.vo.SearchVo;
import com.sfac.springcloud.springCloudClientTest.modules.test.entity.City;

import java.util.List;

public interface CityService {

    List<City> getCitiesByCountryId(int countryId);

    PageInfo<City> getCitiesBySearchVo(int countryId, SearchVo searchVo);

    PageInfo<City> getCitiesBySearchVo(SearchVo searchVo);

    Result<City> insertCity(City city);

    Result<City> updateCity(City city);

    Result<Object> deleteCity(int cityId);
}

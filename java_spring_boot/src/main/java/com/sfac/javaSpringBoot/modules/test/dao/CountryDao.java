package com.sfac.javaSpringBoot.modules.test.dao;

import com.sfac.javaSpringBoot.modules.test.entity.Country;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
@Mapper
public interface CountryDao {

    //#和$的区别：#jdbc预处理以问号形式进行传递参数；$以直接字符串拼接的形式，不能够防止sql注入；order by排序，前台传入字段用$,如果用#好，则参数外面会自动加入引号
    @Select("select * from m_country where country_id = #{countryId}")
    @Results(id = "countryResults", value = {
            @Result(column = "country_id",property = "countryId"),
            @Result(column = "country_id",property = "cities",
                    javaType = List.class,
                    many = @Many(select = "com.sfac.javaSpringBoot.modules.test.dao.CityDao.getCitiesByCountryId"))
    })
    Country getCountryByCountryId(int countryId);

    @Select("select * from m_country where country_name = #{countryName}")
    @ResultMap(value = "countryResults")
    Country getCountryByCountryName(String countryName);
}

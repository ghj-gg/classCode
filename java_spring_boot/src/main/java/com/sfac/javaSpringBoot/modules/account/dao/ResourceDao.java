package com.sfac.javaSpringBoot.modules.account.dao;

import com.sfac.javaSpringBoot.modules.account.entity.Resource;
import com.sfac.javaSpringBoot.modules.common.vo.SearchVo;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface ResourceDao {

    @Select("<script>" +
            "select * from resource "
            + "<where> "
            + "<if test='keyWord != \"\" and keyWord != null'>"
            + " and (resource_name like '%${keyWord}%') "
            + "</if>"
            + "</where>"
            + "<choose>"
            + "<when test='orderBy != \"\" and orderBy != null'>"
            + " order by ${orderBy} ${sort}"
            + "</when>"
            + "<otherwise>"
            + " order by resource_id desc"
            + "</otherwise>"
            + "</choose>"
            + "</script>")
    List<Resource> getResourcesBySearchVo(SearchVo searchVo);

    @Insert("insert into resource (resource_uri,resource_name,permission)" +
            "values (#{resourceUri},#{resourceName},#{permission})")
    @Options(useGeneratedKeys = true,keyColumn = "resource_id",keyProperty = "resourceId")
    void insertResource(Resource resource);

    @Update("update resource set resource_uri = #{resourceUri}," +
            "resource_name = #{resourceName},permission = #{permission} where resource_id = #{resourceId}")
    void updateResource(Resource resource);

    @Select("select * from resource where resource_id = #{resourceId}")
    @Results(id = "resourceResults",value = {
            @Result(column = "resource_id",property = "resourceId"),
            @Result(column = "resource_id",property = "roles",
                    javaType = List.class,
                    many = @Many(select = "com.sfac.javaSpringBoot.modules." +
                            "account.dao.RoleDao.getRolesByResourceId"))
    })
    Resource getResourceByResourceId(int resourceId);

    @Delete("delete from resource where resource_id = #{ResourceId}")
    void deleteResource(int resourceId);

    @Select("select * from resource where resource_id=#{resourceId}")
    @ResultMap(value = "resourceResults")
    List<Resource> getResourcesByRoleId(int roleId);
}

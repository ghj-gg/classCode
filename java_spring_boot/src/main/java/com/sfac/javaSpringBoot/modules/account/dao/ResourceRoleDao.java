package com.sfac.javaSpringBoot.modules.account.dao;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface ResourceRoleDao {

    @Delete("delete from role_resource where resource_id = #{resourceId}")
    void deleteResourceRoleByResourceId(int resourceId);

    @Insert("insert into role_resource (role_id,resource_id) values (#{roleId},#{resourceId})")
    void insertResourceRole(int resourceId, int roleId);
}

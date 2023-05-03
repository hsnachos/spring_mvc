package com.hsnachos.mapper;

import org.apache.ibatis.annotations.Insert;

public interface SampleMapper {
    @Insert("insert into tbl_sample1 value (#{data})")
    int insert1(String data);

    @Insert("insert into tbl_sample2 value (#{data})")
    int insert2(String data);
}

package com.lxh.springbootkafka.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lxh.springbootkafka.model.Transaction;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;


/**
 * @author 熙恒
 * @description 针对表【transaction】的数据库操作Mapper
 * @createDate 2022-06-18 18:58:59
 * @Entity com.lxh.springbootkafka.mybatis.Transaction
 */
@Repository
@Mapper
public interface TransactionMapper extends BaseMapper<Transaction> {

}

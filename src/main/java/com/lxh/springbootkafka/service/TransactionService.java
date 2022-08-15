package com.lxh.springbootkafka.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lxh.springbootkafka.model.Transaction;


public interface TransactionService extends IService<Transaction> {

    Boolean check(JSONObject msg);
}

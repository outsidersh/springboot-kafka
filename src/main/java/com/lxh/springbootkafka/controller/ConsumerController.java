package com.lxh.springbootkafka.controller;


import cn.hutool.core.bean.BeanUtil;
import com.alibaba.fastjson.JSONObject;
import com.lxh.springbootkafka.model.Transaction;
import com.lxh.springbootkafka.service.TransactionServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;

import java.util.Map;

import static com.sun.xml.internal.ws.spi.db.BindingContextFactory.LOGGER;


/**
 * @author 熙恒
 */
@Slf4j
@Configuration
public class ConsumerController {
    @Autowired
    TransactionServiceImpl transactionService;

    @KafkaListener(topics = "first")

    public void consumerTopic(String msg) {
        // 消费消息
        // 添加到map中
        Map mapObj = JSONObject.parseObject(msg, Map.class);
//        Map map = JSONObject.parseObject(mapObj.get("data").toString(), Map.class);
        // 将map转换成Transaction
        Transaction transaction = BeanUtil.mapToBean(mapObj, Transaction.class, false);
        // 插入数据库
        transactionService.save(transaction);
        LOGGER.info("消费消息成功：" + transaction);
    }
}

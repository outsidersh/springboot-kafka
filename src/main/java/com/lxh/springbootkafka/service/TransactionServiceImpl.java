package com.lxh.springbootkafka.service;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.googlecode.aviator.AviatorEvaluator;
import com.lxh.springbootkafka.mapper.TransactionMapper;
import com.lxh.springbootkafka.model.Transaction;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TransactionServiceImpl extends ServiceImpl<TransactionMapper, Transaction> implements TransactionService {

    @Override
    public boolean save(Transaction entity) {
        return super.save(entity);
    }

    @Override
    public List<Transaction> listByIds(Collection<? extends Serializable> idList) {
        return super.listByIds(idList);
    }

    @Override
    public Boolean check(JSONObject msg) {
        // 将json转换成Transaction对象
        Transaction transaction = BeanUtil.toBean(msg, Transaction.class);
        // 将对象存储到map进行校验
        Map<String, Object> env = new HashMap<>();
        env.put("cardidlength", transaction.getCardId().toString().length());
        return (Boolean) AviatorEvaluator.execute("cardidlength == 16", env);
    }



}

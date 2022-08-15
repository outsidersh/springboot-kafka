package com.lxh.springbootkafka.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lxh.springbootkafka.model.Transaction;
import com.lxh.springbootkafka.service.TransactionServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

import static com.sun.xml.internal.ws.spi.db.BindingContextFactory.LOGGER;

@Slf4j
@SuppressWarnings("all")
@RestController
@CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*", methods = {})
public class ProducerController {

    @Autowired
    KafkaTemplate<String, String> kafka;

    @Autowired
    TransactionServiceImpl transactionServiceimpl;


    @PostMapping("/producer")
    public JSONObject producer(@RequestBody JSONObject msg) {
        // 通过kafka发送消息
        // 创建json对象
        JSONObject result = new JSONObject();
        // 符合条件的数据发送到kafka
        if ((transactionServiceimpl.check(msg))) {
            kafka.send("first", msg.toJSONString());
            LOGGER.info("消费发送成功：" + msg);
        } else {
            log.error("失败消息：" + msg);
        }
        // 将入参封装到json中返回
        result.put("msg", "success");
        result.put("data", msg);
        return result;
    }

    @PostMapping("/query")
    public JSONObject getByIds(@RequestBody(required = false) Map params) {
        // 模糊分页查询
        // 创建json对象
        JSONObject result = new JSONObject();
        result.put("msg", "success");
        // 创建分页对象
        Page<Transaction> pageInfo = new Page<>((Integer) params.get("current"), (Integer) params.get("pageSize"));
        // 创建LabdaQueryWrapper，实现id模糊查询与排序
        LambdaQueryWrapper<Transaction> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(StringUtils.isNotBlank((CharSequence) params.get("cardId")), Transaction::getCardId, params.get("cardId"));
        wrapper.orderByDesc(Transaction::getCardId);
        // 封装到json对象返回给前端
        result.put("data", transactionServiceimpl.page(pageInfo, wrapper));
        return result;
    }


//    @GetMapping("/query2/{currentPage}/{pageSize}")
//    public JSONObject getByJSON2(@PathVariable(value = "currentPage") Integer currentPage, @PathVariable(value = "pageSize") Integer pageSize) {
//        JSONObject result = new JSONObject();
////        Wrapper<Transaction> queryWrapper = new QueryWrapper<>();
//        Page page = new Page(currentPage, pageSize);
//        page.addOrder(OrderItem.desc("card_id"));
//        result.put("msg", "success");
//        result.put("data", transactionServiceimpl.page(page));
//        return result;
//    }

}

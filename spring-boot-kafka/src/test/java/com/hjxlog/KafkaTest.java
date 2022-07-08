package com.hjxlog;

import com.hjxlog.kafka.producer.KafkaProducer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import javax.annotation.Resource;

/**
 * @author: Huang JX
 * @date: 2022/7/8
 */
@SpringBootTest
public class KafkaTest {

    @Resource
    private KafkaTemplate kafkaTemplate;

    @Resource
    private KafkaProducer kafkaProducer;

    @Test
    void producerTest() {
        for (int i = 0; i < 10; i++) {
            kafkaProducer.send("topic1", "msg", "" + i);
        }
    }

}

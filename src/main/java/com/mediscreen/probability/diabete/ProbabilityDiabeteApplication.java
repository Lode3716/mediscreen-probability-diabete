package com.mediscreen.probability.diabete;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients("com.mediscreen.probability.diabete")
public class ProbabilityDiabeteApplication
{
    public static void main(String[] args) {
        SpringApplication.run(ProbabilityDiabeteApplication.class, args);
    }
}

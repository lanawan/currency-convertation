package com.sber.test.service;

import com.sber.currency.model.Currency;
import com.sber.currency.model.CurrencyRequest;
import com.sber.test.config.TestProperties;
import com.sber.test.model.CurrencyList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.annotation.ScheduledAnnotationBeanPostProcessor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.security.SecureRandom;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class TestCurrencyService implements ApplicationContextAware, BeanNameAware {
    private final Random rand = new SecureRandom();
    private int errorCount = 0;
    private int testCount = 0;
    private RestTemplate restTemplate = new RestTemplate();
    private final TestProperties testProperties;
    private String currenciesUrl;
    private Integer MAX_ERRORS;
    private Integer MAX_TIMES;

    List<Currency> currencyList = new ArrayList<>();
    Logger logger = LoggerFactory.getLogger(TestCurrencyService.class);

    private ApplicationContext applicationContext;
    private String beanName;

    public TestCurrencyService(TestProperties testProperties) {
        this.testProperties = testProperties;
        this.MAX_TIMES = testProperties.getMaxTimes();
        this.MAX_ERRORS = testProperties.getMaxErrors();
        this.currenciesUrl = testProperties.getTestCurrencyHost();
    }

    private void stopScheduledTask() {
        logger.info("The scheduled service is stopped");
        ScheduledAnnotationBeanPostProcessor bean = applicationContext.getBean(ScheduledAnnotationBeanPostProcessor.class);
        bean.postProcessBeforeDestruction(this, beanName);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }

    private void error() {
        errorCount++;
        if (errorCount == MAX_ERRORS) {
            logger.info("The maximum number of errors have been reached");
            stopScheduledTask();
        }
    }

    private void shouldStop() {
        testCount++;
        if (testCount == MAX_TIMES) {
            logger.info("The maximum number of tests have been completed");
            stopScheduledTask();
        }
    }

    @Scheduled(fixedDelay = 1000)
    public void reportCurrentTime() throws ParseException {
        if (currencyList == null) {
            try {
                CurrencyList response = restTemplate.getForObject(
                        currenciesUrl,
                        CurrencyList.class);
                currencyList = response.getCurrencies();
                errorCount = 0;
            } catch (Exception e) {
                logger.error("An error occured while trying to download the currency rate list " + e.getMessage());
                error();
            }
        } else {
            Currency currency = currencyList.get(rand.nextInt(currencyList.size()));

            Double amount = Double.valueOf(getRandom(1, 1000));
            CurrencyRequest currencyRequest = new CurrencyRequest();
            currencyRequest.setAmount(amount);
            currencyRequest.setCharCode(currency.getCharCode());
            String currencyString = currency.getValue().replace(",", ".");
            String expectingResult = String.valueOf((amount * Double.parseDouble(currencyString)));

            String result = restTemplate.postForObject(currenciesUrl, currencyRequest, String.class).replace(",", ".");

            if (!expectingResult.equals(result)) {
                logger.error("The calculated and expected values differ");
                error();
            }
            shouldStop();
        }
    }

    private int getRandom(int min, int max) {
        return rand.nextInt(max - min + 1) + min;
    }
}

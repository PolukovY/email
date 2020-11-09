package com.levik.mailsender.app.annotation.aspect;

import com.levik.mailsender.app.service.IncomingStatisticService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class IncomingStatisticAspect {

    private final IncomingStatisticService incomingStatisticService;

    @Autowired
    public IncomingStatisticAspect(IncomingStatisticService incomingStatisticService) {
        this.incomingStatisticService = incomingStatisticService;
    }

    @Around("@annotation(com.levik.mailsender.app.annotation.IncomingStatisticMarker)")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        try {
            incomingStatisticService.incStatisticByKey(IncomingStatisticService.INCOMING);
            incomingStatisticService.incStatisticByKey(IncomingStatisticService.ACTIVE);
            return joinPoint.proceed();
        } catch (Exception exe) {
            incomingStatisticService.incStatisticByKey(IncomingStatisticService.FAILED);
            throw exe;
        } finally {
            incomingStatisticService.incStatisticByKey(IncomingStatisticService.COMPLETED);
            incomingStatisticService.decStatisticByKey(IncomingStatisticService.ACTIVE);
        }

    }
}

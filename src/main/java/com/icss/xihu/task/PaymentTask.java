package com.icss.xihu.task;

import com.icss.xihu.mapper.PaymentMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 生活缴费定时任务
 * 功能概述：定期自动更新过期账单状态，将状态为0（待缴费）且到期日期已过的账单更新为2（逾期）
 */
@Component
public class PaymentTask {
    
    private static final Logger logger = LoggerFactory.getLogger(PaymentTask.class);
    
    @Autowired
    private PaymentMapper paymentMapper;
    
    /**
     * 应用启动时立即执行一次更新过期账单
     * 功能概述：在Spring应用完全启动后立即执行，确保应用启动时过期账单状态是最新的
     */
    @EventListener(ApplicationReadyEvent.class)
    public void initUpdateOverdueBills() {
        try {
            logger.info("应用启动：开始执行初始更新过期账单状态任务...");
            // 更新所有用户的过期账单（userId为null表示更新所有用户）
            int updatedCount = paymentMapper.updateOverdueBills(null);
            logger.info("应用启动：初始更新过期账单状态完成，共更新 {} 条账单", updatedCount);
        } catch (Exception e) {
            logger.error("应用启动：初始更新过期账单状态失败", e);
        }
    }
    
    /**
     * 自动更新过期账单状态
     * 功能概述：每天凌晨2点执行，将所有过期账单的状态从0（待缴费）更新为2（逾期）
     * 执行频率：每天执行一次（cron表达式：秒 分 时 日 月 周）
     */
    @Scheduled(cron = "0 0 2 * * ?") // 每天凌晨2点执行
    public void updateOverdueBills() {
        try {
            logger.info("开始执行自动更新过期账单状态任务...");
            // 更新所有用户的过期账单（userId为null表示更新所有用户）
            int updatedCount = paymentMapper.updateOverdueBills(null);
            logger.info("自动更新过期账单状态完成，共更新 {} 条账单", updatedCount);
        } catch (Exception e) {
            logger.error("自动更新过期账单状态失败", e);
        }
    }
}


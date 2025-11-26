package com.icss.xihu.util;

import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Random;

/**
 * 为所有账户添加待缴费项目工具类
 * 
 * 功能概述：
 *   1. 为所有用户账户批量生成待缴费账单
 *   2. 每个账户生成3-5个不同缴费类型的账单
 *   3. 账单金额随机生成（50-500元）
 *   4. 到期日期随机生成（未来7-30天）
 *   5. 使用事务确保数据一致性
 * 
 * 使用方法：直接运行main方法
 * 
 * 注意事项：
 *   - 需要确保数据库连接正常
 *   - 会为所有status=1的账户生成账单
 *   - 每个账户的账单类型会排除该账户已有的缴费类型
 * 
 * @author 游市生活开发团队
 * @version 1.0
 * @since 2024
 */
public class AddPaymentBills {

    /**
     * 主方法：为所有账户添加待缴费项目
     * 
     * 功能概述：
     *   1. 连接数据库并设置字符集为UTF-8
     *   2. 查询所有有效的用户缴费账户
     *   3. 为每个账户生成3-5个不同缴费类型的账单
     *   4. 批量插入账单数据
     *   5. 使用事务确保数据一致性
     * 
     * @param args 命令行参数（未使用）
     */
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/xihu?useUnicode=true&characterEncoding=utf8&characterSetResults=utf8&connectionCollation=utf8mb4_unicode_ci&serverTimezone=Asia/Shanghai";
        String user = "root";
        String password = "123456";

        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            // 设置连接字符集
            try (Statement stmt = conn.createStatement()) {
                stmt.execute("SET NAMES utf8mb4");
                stmt.execute("SET CHARACTER SET utf8mb4");
            }
            
            // 关闭自动提交，使用事务
            conn.setAutoCommit(false);

            // 获取当前日期
            LocalDate today = LocalDate.now();
            Random random = new Random();
            
            // 为每个账户生成3-5个待缴费账单
            String selectAccountsSql = "SELECT id, user_id, account_name, payment_type_id FROM user_payment_accounts WHERE status = 1";
            String insertBillSql = "INSERT INTO payment_bills (user_id, account_id, bill_number, payment_type_id, bill_amount, due_date, bill_period, bill_status, create_time, update_time) VALUES (?, ?, ?, ?, ?, ?, ?, 0, NOW(), NOW())";
            
            try (PreparedStatement selectStmt = conn.prepareStatement(selectAccountsSql);
                 PreparedStatement insertStmt = conn.prepareStatement(insertBillSql)) {
                
                ResultSet accounts = selectStmt.executeQuery();
                int totalBills = 0;
                
                while (accounts.next()) {
                    int accountId = accounts.getInt("id");
                    int userId = accounts.getInt("user_id");
                    String accountName = accounts.getString("account_name");
                    int currentPaymentTypeId = accounts.getInt("payment_type_id");
                    
                    // 为每个账户生成3-5个不同缴费类型的账单
                    int billCount = 3 + random.nextInt(3); // 3-5个账单
                    
                    // 获取所有缴费类型ID（除了当前账户已有的类型）
                    String getTypesSql = "SELECT id FROM payment_types WHERE status = 1 AND id != ? ORDER BY RAND() LIMIT ?";
                    try (PreparedStatement typesStmt = conn.prepareStatement(getTypesSql)) {
                        typesStmt.setInt(1, currentPaymentTypeId);
                        typesStmt.setInt(2, billCount);
                        ResultSet types = typesStmt.executeQuery();
                        
                        int billIndex = 0;
                        while (types.next() && billIndex < billCount) {
                            int paymentTypeId = types.getInt("id");
                            
                            // 生成账单号：BILL + 时间戳 + 随机数
                            String billNumber = "BILL" + System.currentTimeMillis() + random.nextInt(1000);
                            
                            // 生成账单金额：50-500元之间
                            double amount = 50 + random.nextDouble() * 450;
                            amount = Math.round(amount * 100.0) / 100.0;
                            
                            // 生成到期日期：未来7-30天
                            int daysToDue = 7 + random.nextInt(24);
                            LocalDate dueDate = today.plusDays(daysToDue);
                            
                            // 生成账单周期：当前月份
                            String billPeriod = today.format(DateTimeFormatter.ofPattern("yyyy年MM月"));
                            
                            // 插入账单
                            insertStmt.setInt(1, userId);
                            insertStmt.setInt(2, accountId);
                            insertStmt.setString(3, billNumber);
                            insertStmt.setInt(4, paymentTypeId);
                            insertStmt.setBigDecimal(5, java.math.BigDecimal.valueOf(amount));
                            insertStmt.setDate(6, Date.valueOf(dueDate));
                            insertStmt.setString(7, billPeriod);
                            insertStmt.addBatch();
                            
                            billIndex++;
                            totalBills++;
                            
                            System.out.println(String.format("✅ 为账户 [%s] (ID:%d) 添加账单: 类型ID=%d, 金额=¥%.2f, 到期日期=%s", 
                                accountName, accountId, paymentTypeId, amount, dueDate));
                        }
                    }
                }
                
                // 执行批量插入
                int[] results = insertStmt.executeBatch();
                // 提交事务（如果所有插入都成功）
                conn.commit();
                
                // 验证插入结果：results数组包含每个插入操作影响的行数（通常为1）
                int successCount = 0;
                for (int result : results) {
                    if (result > 0) {
                        successCount++;
                    }
                }
                
                System.out.println("\n========================================");
                System.out.println("待缴费账单添加完成！");
                System.out.println("共为所有账户添加了 " + totalBills + " 个待缴费账单");
                System.out.println("成功插入 " + successCount + " 条记录");
                System.out.println("========================================");
                
            } catch (SQLException e) {
                try {
                    conn.rollback();
                } catch (SQLException rollbackEx) {
                    System.err.println("回滚失败: " + rollbackEx.getMessage());
                }
                System.err.println("添加账单失败: " + e.getMessage());
                e.printStackTrace();
            } finally {
                try {
                    conn.setAutoCommit(true);
                } catch (SQLException e) {
                    System.err.println("恢复自动提交失败: " + e.getMessage());
                }
            }

        } catch (SQLException e) {
            System.err.println("连接数据库失败: " + e.getMessage());
            e.printStackTrace();
        }
    }
}


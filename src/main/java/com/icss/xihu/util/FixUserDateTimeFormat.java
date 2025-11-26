package com.icss.xihu.util;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * 修复用户表中的日期时间格式问题
 * 功能概述：此工具类用于修复数据库中 last_login_time 字段的格式问题。
 *          数据库中的日期时间格式为 DD/MM/YYYY HH:mm:ss（如：25/11/2025 19:26:07），
 *          需要转换为 MySQL 标准格式 YYYY-MM-DD HH:mm:ss（如：2025-11-25 19:26:07），
 *          以便 MyBatis 能够正确转换为 LocalDateTime 对象。
 * 使用方法：直接运行 main 方法即可执行修复操作。
 * 注意事项：
 *   - 确保数据库连接配置正确。
 *   - 运行前请备份数据库，以防意外。
 *   - 修复操作会更新所有用户的 last_login_time 字段。
 */
public class FixUserDateTimeFormat {

    // 定义日期时间格式解析器：用于解析数据库中的 DD/MM/YYYY HH:mm:ss 格式
    private static final DateTimeFormatter INPUT_FORMATTER = DateTimeFormatter.ofPattern("d/M/yyyy H:mm:ss");
    // 定义日期时间格式解析器：用于解析数据库中的 DD/MM/YYYY HH:mm:ss 格式（月份和日期可能是两位数）
    private static final DateTimeFormatter INPUT_FORMATTER2 = DateTimeFormatter.ofPattern("dd/MM/yyyy H:mm:ss");
    // 定义日期时间格式解析器：用于解析数据库中的 DD/MM/YYYY HH:mm:ss 格式（小时可能是两位数）
    private static final DateTimeFormatter INPUT_FORMATTER3 = DateTimeFormatter.ofPattern("d/M/yyyy HH:mm:ss");
    // 定义日期时间格式解析器：用于解析数据库中的 DD/MM/YYYY HH:mm:ss 格式（完整格式）
    private static final DateTimeFormatter INPUT_FORMATTER4 = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    public static void main(String[] args) {
        // 数据库连接配置
        String url = "jdbc:mysql://localhost:3306/xihu?useUnicode=true&characterEncoding=utf8&characterSetResults=utf8&connectionCollation=utf8mb4_unicode_ci&serverTimezone=Asia/Shanghai";
        String user = "root";
        String password = "123456";

        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            // 设置连接字符集，确保中文显示正常
            try (Statement stmt = conn.createStatement()) {
                stmt.execute("SET NAMES utf8mb4");
                stmt.execute("SET CHARACTER SET utf8mb4");
            }

            // 关闭自动提交，使用事务管理，确保批量操作的原子性
            conn.setAutoCommit(false);

            // SQL查询语句：获取所有用户的 last_login_time 字段
            String selectSql = "SELECT id, last_login_time FROM user WHERE last_login_time IS NOT NULL";
            // SQL更新语句：更新用户的 last_login_time 字段
            String updateSql = "UPDATE user SET last_login_time = ? WHERE id = ?";

            try (PreparedStatement selectStmt = conn.prepareStatement(selectSql);
                 PreparedStatement updateStmt = conn.prepareStatement(updateSql)) {

                ResultSet rs = selectStmt.executeQuery();
                int fixedCount = 0; // 统计修复的记录数

                // 遍历每个用户记录
                while (rs.next()) {
                    int userId = rs.getInt("id");
                    String lastLoginTimeStr = rs.getString("last_login_time");

                    // 修复 last_login_time 字段
                    if (lastLoginTimeStr != null && !lastLoginTimeStr.isEmpty()) {
                        try {
                            // 检查是否已经是标准格式
                            if (lastLoginTimeStr.matches("\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}")) {
                                // 已经是标准格式，跳过
                                continue;
                            }
                            
                            // 尝试解析日期时间字符串（DD/MM/YYYY HH:mm:ss 格式）
                            LocalDateTime dateTime = parseDateTime(lastLoginTimeStr);
                            if (dateTime != null) {
                                Timestamp lastLoginTime = Timestamp.valueOf(dateTime);
                                updateStmt.setTimestamp(1, lastLoginTime);
                                updateStmt.setInt(2, userId);
                                updateStmt.executeUpdate();
                                fixedCount++;
                                System.out.println("✅ 已修复用户 ID " + userId + " 的 last_login_time: " + lastLoginTimeStr + " -> " + dateTime);
                            } else {
                                System.out.println("⚠️  警告：用户 ID " + userId + " 的 last_login_time 格式无法解析: " + lastLoginTimeStr);
                            }
                        } catch (Exception e) {
                            System.out.println("❌ 错误：用户 ID " + userId + " 的 last_login_time 修复失败: " + e.getMessage());
                        }
                    }
                }

                // 提交事务
                conn.commit();
                System.out.println("\n========================================");
                System.out.println("日期时间格式修复完成！");
                System.out.println("共修复了 " + fixedCount + " 条用户记录");
                System.out.println("========================================");

            } catch (SQLException e) {
                // 发生异常时回滚事务
                try {
                    conn.rollback();
                } catch (SQLException rollbackEx) {
                    System.err.println("回滚失败: " + rollbackEx.getMessage());
                }
                System.err.println("修复失败: " + e.getMessage());
                e.printStackTrace();
            } finally {
                // 恢复自动提交模式
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

    /**
     * 解析日期时间字符串
     * 功能概述：尝试使用多种格式解析日期时间字符串，支持 DD/MM/YYYY HH:mm:ss 格式
     * @param dateTimeStr 日期时间字符串
     * @return 解析后的 LocalDateTime 对象，如果解析失败则返回 null
     */
    private static LocalDateTime parseDateTime(String dateTimeStr) {
        // 如果字符串已经是标准格式（YYYY-MM-DD HH:mm:ss），直接解析
        if (dateTimeStr.matches("\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}")) {
            try {
                return LocalDateTime.parse(dateTimeStr, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            } catch (DateTimeParseException e) {
                // 忽略解析错误，继续尝试其他格式
            }
        }

        // 尝试使用多种格式解析 DD/MM/YYYY HH:mm:ss 格式
        DateTimeFormatter[] formatters = {
            INPUT_FORMATTER4,  // dd/MM/yyyy HH:mm:ss
            INPUT_FORMATTER2,  // dd/MM/yyyy H:mm:ss
            INPUT_FORMATTER3,  // d/M/yyyy HH:mm:ss
            INPUT_FORMATTER    // d/M/yyyy H:mm:ss
        };

        for (DateTimeFormatter formatter : formatters) {
            try {
                return LocalDateTime.parse(dateTimeStr, formatter);
            } catch (DateTimeParseException e) {
                // 继续尝试下一个格式
            }
        }

        // 如果所有格式都解析失败，返回 null
        return null;
    }
}


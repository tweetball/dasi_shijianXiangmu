package com.icss.xihu.util;

import java.sql.*;

/**
 * 修复attractions表中opening_hours字段的"全天开放"文字编码问题
 * 使用方法：直接运行main方法
 */
public class FixOpeningHours {
    
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/xihu?useUnicode=true&characterEncoding=utf8&characterSetResults=utf8&connectionCollation=utf8mb4_unicode_ci&serverTimezone=Asia/Shanghai";
        String user = "root";
        String password = "123456";
        
        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            conn.setAutoCommit(false);
            
            // 设置连接字符集
            try (Statement stmt = conn.createStatement()) {
                stmt.execute("SET NAMES utf8mb4");
                stmt.execute("SET CHARACTER SET utf8mb4");
            }
            
            // 查询所有需要修复的记录（opening_hours包含问号或"全天开放"）
            String selectSql = "SELECT id, name, opening_hours FROM attractions WHERE status = 1 AND (opening_hours LIKE '%?%' OR HEX(opening_hours) LIKE '%3F%' OR opening_hours LIKE '%全天开放%')";
            
            int successCount = 0;
            int skipCount = 0;
            
            try (Statement stmt = conn.createStatement()) {
                ResultSet rs = stmt.executeQuery(selectSql);
                
                String updateSql = "UPDATE attractions SET opening_hours = ? WHERE id = ?";
                try (PreparedStatement pstmt = conn.prepareStatement(updateSql)) {
                    while (rs.next()) {
                        int id = rs.getInt("id");
                        String name = rs.getString("name");
                        String currentHours = rs.getString("opening_hours");
                        
                        // 根据景点类型和名称判断开放时间
                        String newHours = determineOpeningHours(name, currentHours);
                        
                        if (newHours != null && !newHours.equals(currentHours)) {
                            pstmt.setString(1, newHours);
                            pstmt.setInt(2, id);
                            int rows = pstmt.executeUpdate();
                            if (rows > 0) {
                                successCount++;
                                System.out.println("✅ 修复成功: ID=" + id + ", Name=" + name + ", OpeningHours=" + newHours);
                            }
                        } else {
                            skipCount++;
                            System.out.println("⏭️  跳过: ID=" + id + ", Name=" + name + " (已经是正确格式)");
                        }
                    }
                }
            }
            
            conn.commit();
            
            System.out.println("\n========================================");
            System.out.println("opening_hours字段修复完成！");
            System.out.println("成功修复: " + successCount + " 条记录");
            System.out.println("跳过: " + skipCount + " 条记录");
            System.out.println("========================================\n");
            
            // 验证修复结果
            try (Statement stmt = conn.createStatement()) {
                ResultSet rs = stmt.executeQuery("SELECT COUNT(*) as corrupted FROM attractions WHERE status = 1 AND (opening_hours LIKE '%?%' OR HEX(opening_hours) LIKE '%3F%')");
                if (rs.next()) {
                    int corrupted = rs.getInt("corrupted");
                    System.out.println("剩余损坏记录数: " + corrupted);
                    if (corrupted == 0) {
                        System.out.println("🎉 所有opening_hours字段修复完成！");
                    }
                }
            }
            
        } catch (SQLException e) {
            System.err.println("修复失败: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * 根据景点名称判断开放时间
     */
    private static String determineOpeningHours(String name, String currentHours) {
        if (name == null) return null;
        
        // 如果当前已经是正确的"全天开放"，保持不变
        if (currentHours != null && currentHours.contains("全天开放") && !currentHours.contains("?")) {
            return currentHours;
        }
        
        // 根据景点类型判断开放时间
        if (name.contains("广场") || name.contains("外滩") || name.contains("中央大街") || 
            name.contains("古文化街") || name.contains("草原") || name.contains("西湖") ||
            name.contains("鼓浪屿") || name.contains("泰山") || name.contains("古城") ||
            name.contains("洪崖洞") || name.contains("苗寨") || name.contains("青海湖") ||
            name.contains("日月潭") || name.contains("国家公园") || name.contains("亚龙湾") ||
            name.contains("维多利亚港") || name.contains("太平山顶") || name.contains("牌坊") ||
            name.contains("度假村")) {
            return "全天开放";
        }
        
        // 其他景点使用标准开放时间
        if (name.contains("公园") || name.contains("园") || name.contains("博物馆") || 
            name.contains("故宫") || name.contains("陵") || name.contains("楼") ||
            name.contains("寺") || name.contains("石窟") || name.contains("山") ||
            name.contains("塔") || name.contains("乐园")) {
            return "08:00-18:00";
        }
        
        // 默认返回"全天开放"
        return "全天开放";
    }
}


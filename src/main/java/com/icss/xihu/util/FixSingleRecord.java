package com.icss.xihu.util;

import java.sql.*;

/**
 * 修复单条attractions记录测试（ID=198）
 * 使用方法：直接运行main方法
 */
public class FixSingleRecord {
    
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
            
            // 先查询当前数据
            System.out.println("修复前的数据：");
            try (Statement stmt = conn.createStatement()) {
                ResultSet rs = stmt.executeQuery("SELECT id, name, province, city, HEX(name) as name_hex FROM attractions WHERE id = 198");
                if (rs.next()) {
                    System.out.println("ID: " + rs.getInt("id"));
                    System.out.println("Name: " + rs.getString("name"));
                    System.out.println("Province: " + rs.getString("province"));
                    System.out.println("City: " + rs.getString("city"));
                    System.out.println("Name HEX: " + rs.getString("name_hex"));
                }
            }
            
            // 执行修复 - 修复整行数据
            String updateSql = "UPDATE attractions SET " +
                "name = ?, " +
                "province = ?, " +
                "city = ?, " +
                "district = ?, " +
                "full_address = ?, " +
                "description = ?, " +
                "features = ?, " +
                "tips = ?, " +
                "best_season = ?, " +
                "visit_duration = ?, " +
                "price_range = ? " +
                "WHERE id = 198";
            try (PreparedStatement pstmt = conn.prepareStatement(updateSql)) {
                pstmt.setString(1, "天坛公园");
                pstmt.setString(2, "北京市");
                pstmt.setString(3, "北京市");
                pstmt.setString(4, "东城区");
                pstmt.setString(5, "北京市东城区天坛路甲1号");
                pstmt.setString(6, "天坛是明清两代皇帝祭天的场所，是中国现存最大的古代祭祀性建筑群。");
                pstmt.setString(7, "世界文化遗产、明清建筑、祭天文化");
                pstmt.setString(8, "建议早上或傍晚游览，避开中午时段");
                pstmt.setString(9, "春季、秋季");
                pstmt.setString(10, "2-3小时");
                pstmt.setString(11, "15-35元");
                
                int rows = pstmt.executeUpdate();
                System.out.println("\n修复执行结果: " + rows + " 行受影响");
            }
            
            // 验证修复结果
            System.out.println("\n修复后的数据：");
            try (Statement stmt = conn.createStatement()) {
                ResultSet rs = stmt.executeQuery("SELECT id, name, province, city, district, full_address, description, features, tips, best_season, visit_duration, price_range, HEX(name) as name_hex, HEX(description) as desc_hex FROM attractions WHERE id = 198");
                if (rs.next()) {
                    System.out.println("ID: " + rs.getInt("id"));
                    System.out.println("Name: " + rs.getString("name"));
                    System.out.println("Province: " + rs.getString("province"));
                    System.out.println("City: " + rs.getString("city"));
                    System.out.println("District: " + rs.getString("district"));
                    System.out.println("Full Address: " + rs.getString("full_address"));
                    System.out.println("Description: " + rs.getString("description"));
                    System.out.println("Features: " + rs.getString("features"));
                    System.out.println("Tips: " + rs.getString("tips"));
                    System.out.println("Best Season: " + rs.getString("best_season"));
                    System.out.println("Visit Duration: " + rs.getString("visit_duration"));
                    System.out.println("Price Range: " + rs.getString("price_range"));
                    System.out.println("Name HEX: " + rs.getString("name_hex"));
                    System.out.println("Description HEX: " + rs.getString("desc_hex"));
                    
                    // 检查是否修复成功
                    String nameHex = rs.getString("name_hex");
                    String descHex = rs.getString("desc_hex");
                    if (nameHex != null && !nameHex.contains("3F") && descHex != null && !descHex.contains("3F")) {
                        System.out.println("\n✅ 修复成功！整行数据已正确存储为UTF-8编码");
                    } else {
                        System.out.println("\n❌ 修复失败，数据仍包含问号（3F）");
                    }
                }
            }
            
        } catch (SQLException e) {
            System.err.println("修复失败: " + e.getMessage());
            e.printStackTrace();
        }
    }
}


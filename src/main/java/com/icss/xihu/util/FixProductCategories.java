package com.icss.xihu.util;

import java.sql.*;

/**
 * 修复商品分类名称编码问题
 * 使用方法：直接运行main方法
 */
public class FixProductCategories {

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
            System.out.println("修复前的分类数据：");
            try (Statement stmt = conn.createStatement()) {
                ResultSet rs = stmt.executeQuery("SELECT DISTINCT category, COUNT(*) as count FROM product WHERE category IS NOT NULL AND category != '' GROUP BY category ORDER BY category");
                while (rs.next()) {
                    System.out.println("分类: " + rs.getString("category") + ", 数量: " + rs.getInt("count"));
                }
            }

            // 执行修复
            String updateSql1 = "UPDATE product SET category = ? WHERE category = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(updateSql1)) {
                // 修复"家居"
                pstmt.setString(1, "家居");
                pstmt.setString(2, "瀹跺眳");
                int rows1 = pstmt.executeUpdate();
                System.out.println("\n修复'家居'分类: " + rows1 + " 行受影响");

                // 修复"茶叶"
                pstmt.setString(1, "茶叶");
                pstmt.setString(2, "鑼跺彾");
                int rows2 = pstmt.executeUpdate();
                System.out.println("修复'茶叶'分类: " + rows2 + " 行受影响");
            }

            // 验证修复结果
            System.out.println("\n修复后的分类数据：");
            try (Statement stmt = conn.createStatement()) {
                ResultSet rs = stmt.executeQuery("SELECT DISTINCT category, COUNT(*) as count FROM product WHERE category IS NOT NULL AND category != '' GROUP BY category ORDER BY category");
                while (rs.next()) {
                    System.out.println("分类: " + rs.getString("category") + ", 数量: " + rs.getInt("count"));
                }
            }

            System.out.println("\n✅ 分类名称修复完成！");

        } catch (SQLException e) {
            System.err.println("修复失败: " + e.getMessage());
            e.printStackTrace();
        }
    }
}


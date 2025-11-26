// 定义包路径，标识该类属于com.icss.xihu.util包
package com.icss.xihu.util;

// 导入Spring的自动注入注解
import org.springframework.beans.factory.annotation.Autowired;
// 导入Spring的JdbcTemplate类，用于执行SQL语句
import org.springframework.jdbc.core.JdbcTemplate;
// 导入Spring的组件注解，标识该类为Spring组件
import org.springframework.stereotype.Component;

// 导入Jakarta的PostConstruct注解，用于在Bean初始化后执行方法
import jakarta.annotation.PostConstruct;

/**
 * 数据库中文更新工具类
 * 功能概述：用于将数据库中的餐厅信息和菜单数据更新为中文，在应用启动时自动执行
 */
// 标识该类为Spring组件，会被Spring容器扫描并注册
@Component
// 数据库中文更新工具类，用于更新数据库中的中文数据
public class DatabaseChineseUpdater {

    // 自动注入JdbcTemplate，Spring容器会自动查找并注入JdbcTemplate实例
    @Autowired
    // JdbcTemplate对象，用于执行SQL语句
    private JdbcTemplate jdbcTemplate;

    /**
     * 更新数据库为中文
     * 功能概述：在应用启动时自动执行，将数据库中的餐厅信息和菜单数据更新为中文
     * 使用@PostConstruct注解，在Bean初始化后自动执行
     */
    // 使用PostConstruct注解，在Bean初始化后自动执行该方法
    @PostConstruct
    // 更新数据库为中文方法，更新餐厅信息和菜单数据为中文
    public void updateToChinese() {
        // 使用try-catch捕获异常
        try {
            // 全面更新餐厅信息为中文
            // 调用updateRestaurantInfo方法，更新餐厅信息为中文
            updateRestaurantInfo();
            
            // 更新菜单数据为中文
            // 调用updateMenuToChinese方法，更新菜单数据为中文
            updateMenuToChinese();
            
            // 打印成功信息到控制台
            System.out.println("数据库已全面更新为中文");
        // 捕获所有异常
        } catch (Exception e) {
            // 打印错误信息到控制台
            System.err.println("更新数据库失败: " + e.getMessage());
        }
    }

    /**
     * 更新餐厅信息为中文
     * 功能概述：将数据库中的餐厅信息更新为中文，包括餐厅名称、描述、特色、类别、城市、省份、价格范围、营业时间等
     */
    // 更新餐厅信息为中文方法，更新餐厅信息为中文
    private void updateRestaurantInfo() {
        // 使用try-catch捕获异常
        try {
            // 更新海底捞火锅餐厅信息
            // 使用jdbcTemplate.update方法，执行UPDATE语句，更新餐厅信息
            jdbcTemplate.update("UPDATE restaurant SET name = ?, description = ?, features = ?, category = ?, city = ?, province = ?, price_range = ?, opening_hours = ? WHERE id = ?", 
                "海底捞火锅", "知名火锅连锁品牌，服务优质，食材新鲜，24小时营业", "免费美甲,等位小食,生日福利,儿童乐园,24小时营业", "火锅", "杭州", "浙江省", "100-150元/人", "10:00-02:00", 1);
            
            // 小龙坎老火锅
            jdbcTemplate.update("UPDATE restaurant SET name = ?, description = ?, features = ?, category = ?, city = ?, province = ?, price_range = ?, opening_hours = ? WHERE id = ?", 
                "小龙坎老火锅", "正宗成都老火锅，麻辣鲜香，传统牛油锅底，深夜食堂", "牛油锅底,手工毛肚,麻辣鲜香,深夜营业,传统工艺", "火锅", "杭州", "浙江省", "100-150元/人", "10:00-02:00", 2);
            
            // 禾绿回转寿司
            jdbcTemplate.update("UPDATE restaurant SET name = ?, description = ?, features = ?, category = ?, city = ?, province = ?, price_range = ?, opening_hours = ? WHERE id = ?", 
                "禾绿回转寿司", "新鲜食材，回转寿司，性价比高，服务快速", "回转寿司,价格实惠,新鲜食材,快速服务,家庭友好", "日料", "杭州", "浙江省", "80-120元/人", "11:00-22:00", 3);
            
            // N多寿司
            jdbcTemplate.update("UPDATE restaurant SET name = ?, description = ?, features = ?, category = ?, city = ?, province = ?, price_range = ?, opening_hours = ? WHERE id = ?", 
                "N多寿司", "精致日式料理，主厨现场制作，高品质食材，需预约", "主厨现场制作,高品质食材,需预约,商务宴请,包间服务", "日料", "杭州", "浙江省", "80-120元/人", "11:00-22:00", 4);
            
            // 外婆家
            jdbcTemplate.update("UPDATE restaurant SET name = ?, description = ?, features = ?, category = ?, city = ?, province = ?, price_range = ?, opening_hours = ? WHERE id = ?", 
                "外婆家", "正宗江浙风味，茶香鸡、麻婆豆腐招牌菜，性价比高", "茶香鸡,性价比高,无需等位,环境清新,家常味道", "江浙菜", "杭州", "浙江省", "60-100元/人", "11:00-22:00", 5);
            
            // 西贝莜面村
            jdbcTemplate.update("UPDATE restaurant SET name = ?, description = ?, features = ?, category = ?, city = ?, province = ?, price_range = ?, opening_hours = ? WHERE id = ?", 
                "西贝莜面村", "西北风味餐厅，莜面、羊肉等特色菜品，健康营养", "西北风味,莜面特色,健康营养,羊肉菜品,传统工艺", "西北菜", "杭州", "浙江省", "60-100元/人", "11:00-22:00", 6);
            
            // 必胜客
            jdbcTemplate.update("UPDATE restaurant SET name = ?, description = ?, features = ?, category = ?, city = ?, province = ?, price_range = ?, opening_hours = ? WHERE id = ?", 
                "必胜客", "知名西式快餐，披萨、意面等西式美食，适合家庭聚餐", "披萨特色,意面美食,家庭聚餐,儿童友好,快速服务", "西餐", "杭州", "浙江省", "70-110元/人", "11:00-22:00", 7);
            
            // 南京大牌档
            jdbcTemplate.update("UPDATE restaurant SET name = ?, description = ?, features = ?, category = ?, city = ?, province = ?, price_range = ?, opening_hours = ? WHERE id = ?", 
                "南京大牌档", "正宗苏帮菜，盐水鸭、狮子头等经典菜品，江南风味", "苏帮菜,盐水鸭,狮子头,江南风味,传统工艺", "苏帮菜", "杭州", "浙江省", "70-110元/人", "11:00-22:00", 8);
            
            // 大龙燚火锅
            jdbcTemplate.update("UPDATE restaurant SET name = ?, description = ?, features = ?, category = ?, city = ?, province = ?, price_range = ?, opening_hours = ? WHERE id = ?", 
                "大龙燚火锅", "四川火锅品牌，麻辣鲜香，正宗川味，夜宵首选", "四川火锅,麻辣鲜香,正宗川味,夜宵首选,24小时营业", "火锅", "杭州", "浙江省", "100-150元/人", "10:00-02:00", 9);
            
            // 全聚德烤鸭
            jdbcTemplate.update("UPDATE restaurant SET name = ?, description = ?, features = ?, category = ?, city = ?, province = ?, price_range = ?, opening_hours = ? WHERE id = ?", 
                "全聚德烤鸭", "百年老字号，正宗北京烤鸭，传统工艺，商务宴请首选", "百年老字号,北京烤鸭,传统工艺,商务宴请,包间服务", "京菜", "杭州", "浙江省", "120-200元/人", "11:00-21:30", 10);
            
            System.out.println("餐厅信息已全面更新为中文");
        } catch (Exception e) {
            System.err.println("更新餐厅信息失败: " + e.getMessage());
        }
    }

    /**
     * 更新菜单数据为中文
     * 功能概述：将数据库中的菜单数据更新为中文，包括菜单类别、名称、描述等
     */
    // 更新菜单数据为中文方法，更新菜单数据为中文
    private void updateMenuToChinese() {
        // 使用try-catch捕获异常
        try {
            // 更新海底捞火锅菜单
            // 使用jdbcTemplate.update方法，执行UPDATE语句，更新菜单信息
            jdbcTemplate.update("UPDATE restaurant_menu SET category = ?, name = ?, description = ? WHERE restaurant_id = ? AND name = ?", 
                "招牌菜", "麻辣牛油锅底", "招牌麻辣牛油锅底，四川花椒香麻", 1, "Spicy Beef Base");
            jdbcTemplate.update("UPDATE restaurant_menu SET category = ?, name = ?, description = ? WHERE restaurant_id = ? AND name = ?", 
                "招牌菜", "新鲜牛肉片", "优质牛肉片，鲜嫩爽滑", 1, "Fresh Beef Slices");
            jdbcTemplate.update("UPDATE restaurant_menu SET category = ?, name = ?, description = ? WHERE restaurant_id = ? AND name = ?", 
                "招牌菜", "手工面条", "新鲜手工面条，口感劲道", 1, "Handmade Noodles");
            jdbcTemplate.update("UPDATE restaurant_menu SET category = ?, name = ?, description = ? WHERE restaurant_id = ? AND name = ?", 
                "热门推荐", "鲜虾球", "手工虾球，Q弹爽滑", 1, "Fresh Shrimp Balls");
            jdbcTemplate.update("UPDATE restaurant_menu SET category = ?, name = ?, description = ? WHERE restaurant_id = ? AND name = ?", 
                "热门推荐", "羊肉片", "新鲜羊肉片，无膻味", 1, "Lamb Slices");
            jdbcTemplate.update("UPDATE restaurant_menu SET category = ?, name = ?, description = ? WHERE restaurant_id = ? AND name = ?", 
                "饮品", "鲜榨橙汁", "新鲜榨取橙汁", 1, "Fresh Orange Juice");
            jdbcTemplate.update("UPDATE restaurant_menu SET category = ?, name = ?, description = ? WHERE restaurant_id = ? AND name = ?", 
                "饮品", "绿茶", "优质绿茶，清香怡人", 1, "Green Tea");

            // 更新小龙坎火锅菜单
            jdbcTemplate.update("UPDATE restaurant_menu SET category = ?, name = ?, description = ? WHERE restaurant_id = ? AND name = ?", 
                "招牌菜", "传统牛油锅底", "地道成都牛油锅底，麻辣鲜香", 2, "Traditional Beef Base");
            jdbcTemplate.update("UPDATE restaurant_menu SET category = ?, name = ?, description = ? WHERE restaurant_id = ? AND name = ?", 
                "招牌菜", "手工毛肚", "新鲜手工毛肚，爽脆可口", 2, "Handmade Tripe");
            jdbcTemplate.update("UPDATE restaurant_menu SET category = ?, name = ?, description = ? WHERE restaurant_id = ? AND name = ?", 
                "热门推荐", "鸭血", "新鲜鸭血，嫩滑爽口", 2, "Duck Blood");
            jdbcTemplate.update("UPDATE restaurant_menu SET category = ?, name = ?, description = ? WHERE restaurant_id = ? AND name = ?", 
                "热门推荐", "藕片", "脆嫩藕片", 2, "Lotus Root Slices");
            jdbcTemplate.update("UPDATE restaurant_menu SET category = ?, name = ?, description = ? WHERE restaurant_id = ? AND name = ?", 
                "饮品", "四川茶", "传统四川茶", 2, "Sichuan Tea");

            // 更新禾绿回转寿司菜单
            jdbcTemplate.update("UPDATE restaurant_menu SET category = ?, name = ?, description = ? WHERE restaurant_id = ? AND name = ?", 
                "招牌菜", "三文鱼刺身", "新鲜三文鱼刺身，入口即化", 3, "Salmon Sashimi");
            jdbcTemplate.update("UPDATE restaurant_menu SET category = ?, name = ?, description = ? WHERE restaurant_id = ? AND name = ?", 
                "招牌菜", "金枪鱼寿司", "优质金枪鱼寿司", 3, "Tuna Sushi");
            jdbcTemplate.update("UPDATE restaurant_menu SET category = ?, name = ?, description = ? WHERE restaurant_id = ? AND name = ?", 
                "热门推荐", "三文鱼卷", "新鲜三文鱼卷", 3, "Salmon Roll");
            jdbcTemplate.update("UPDATE restaurant_menu SET category = ?, name = ?, description = ? WHERE restaurant_id = ? AND name = ?", 
                "热门推荐", "鳗鱼寿司", "烤鳗鱼寿司", 3, "Eel Sushi");
            jdbcTemplate.update("UPDATE restaurant_menu SET category = ?, name = ?, description = ? WHERE restaurant_id = ? AND name = ?", 
                "饮品", "绿茶", "传统日式绿茶", 3, "Green Tea");

            // 更新N多寿司菜单
            jdbcTemplate.update("UPDATE restaurant_menu SET category = ?, name = ?, description = ? WHERE restaurant_id = ? AND name = ?", 
                "招牌菜", "主厨精选拼盘", "主厨精选高端刺身拼盘", 4, "Omakase Platter");
            jdbcTemplate.update("UPDATE restaurant_menu SET category = ?, name = ?, description = ? WHERE restaurant_id = ? AND name = ?", 
                "招牌菜", "和牛寿司", "优质和牛寿司", 4, "Wagyu Beef Sushi");
            jdbcTemplate.update("UPDATE restaurant_menu SET category = ?, name = ?, description = ? WHERE restaurant_id = ? AND name = ?", 
                "热门推荐", "海胆寿司", "新鲜海胆寿司", 4, "Uni Sushi");
            jdbcTemplate.update("UPDATE restaurant_menu SET category = ?, name = ?, description = ? WHERE restaurant_id = ? AND name = ?", 
                "饮品", "清酒", "高品质日式清酒", 4, "Premium Sake");

            // 更新外婆家菜单
            jdbcTemplate.update("UPDATE restaurant_menu SET category = ?, name = ?, description = ? WHERE restaurant_id = ? AND name = ?", 
                "招牌菜", "茶香鸡", "招牌茶香鸡，香嫩可口", 5, "Tea Chicken");
            jdbcTemplate.update("UPDATE restaurant_menu SET category = ?, name = ?, description = ? WHERE restaurant_id = ? AND name = ?", 
                "招牌菜", "麻婆豆腐", "经典川味麻婆豆腐", 5, "Mapo Tofu");
            jdbcTemplate.update("UPDATE restaurant_menu SET category = ?, name = ?, description = ? WHERE restaurant_id = ? AND name = ?", 
                "热门推荐", "糖醋里脊", "经典糖醋里脊", 5, "Sweet Sour Pork");
            jdbcTemplate.update("UPDATE restaurant_menu SET category = ?, name = ?, description = ? WHERE restaurant_id = ? AND name = ?", 
                "饮品", "绿茶", "优质绿茶", 5, "Green Tea");

            // 打印成功信息到控制台
            System.out.println("菜单数据已更新为中文");
        // 捕获所有异常
        } catch (Exception e) {
            // 打印错误信息到控制台
            System.err.println("更新菜单数据失败: " + e.getMessage());
        }
    }
}

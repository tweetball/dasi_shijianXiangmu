// 定义包路径，标识该类属于com.icss.xihu.util包
package com.icss.xihu.util;

// 导入Spring的自动注入注解
import org.springframework.beans.factory.annotation.Autowired;
// 导入Spring的JdbcTemplate类，用于执行SQL语句
import org.springframework.jdbc.core.JdbcTemplate;
// 导入Spring的组件注解，标识该类为Spring组件
import org.springframework.stereotype.Component;

// 导入List集合接口
import java.util.List;
// 导入Map接口
import java.util.Map;

/**
 * 数据库字符集修复工具类
 * 功能概述：用于修复数据库字符集问题，将数据库和表的字符集修改为utf8mb4，并重新插入正确编码的数据
 */
// 标识该类为Spring组件，会被Spring容器扫描并注册
@Component
// 数据库字符集修复工具类，用于修复数据库字符集问题
public class CharsetFixUtil {

    // 自动注入JdbcTemplate，Spring容器会自动查找并注入JdbcTemplate实例
    @Autowired
    // JdbcTemplate对象，用于执行SQL语句
    private JdbcTemplate jdbcTemplate;

    /**
     * 修复数据库字符集问题
     * 功能概述：修复数据库字符集问题，包括修改数据库和表的字符集、清除乱码数据、重新插入正确编码的数据、验证数据
     */
    // 修复数据库字符集问题方法，修复数据库字符集问题
    public void fixCharsetIssues() {
        // 使用try-catch捕获异常
        try {
            // 打印开始信息到控制台
            System.out.println("开始修复数据库字符集问题...");

            // 1. 修改数据库字符集
            // 使用jdbcTemplate.execute方法，执行ALTER DATABASE语句，修改数据库字符集为utf8mb4
            jdbcTemplate.execute("ALTER DATABASE xihu CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci");
            
            // 2. 修改表字符集
            // 使用jdbcTemplate.execute方法，执行ALTER TABLE语句，修改travel_destinations表的字符集为utf8mb4
            jdbcTemplate.execute("ALTER TABLE travel_destinations CONVERT TO CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci");
            // 使用jdbcTemplate.execute方法，执行ALTER TABLE语句，修改travel_recommendations表的字符集为utf8mb4
            jdbcTemplate.execute("ALTER TABLE travel_recommendations CONVERT TO CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci");
            
            // 3. 清除乱码数据
            // 使用jdbcTemplate.execute方法，执行DELETE语句，清除travel_destinations表中的乱码数据
            jdbcTemplate.execute("DELETE FROM travel_destinations");
            // 使用jdbcTemplate.execute方法，执行DELETE语句，清除travel_recommendations表中的乱码数据
            jdbcTemplate.execute("DELETE FROM travel_recommendations");
            
            // 4. 重新插入正确编码的数据
            // 调用insertProvinceData方法，插入省份数据
            insertProvinceData();
            // 调用insertCityData方法，插入城市数据
            insertCityData();
            // 调用insertRecommendationData方法，插入推荐数据
            insertRecommendationData();
            
            // 打印成功信息到控制台
            System.out.println("数据库字符集修复完成！");
            
            // 5. 验证数据
            // 调用verifyData方法，验证插入的数据是否正确
            verifyData();
            
        // 捕获所有异常
        } catch (Exception e) {
            // 打印错误信息到控制台
            System.err.println("修复过程中出现错误: " + e.getMessage());
            // 打印异常堆栈信息
            e.printStackTrace();
        }
    }

    /**
     * 插入省份数据
     * 功能概述：向travel_destinations表中插入省份数据，包括省份名称、编码、级别、父级编码、描述等
     */
    // 插入省份数据方法，向travel_destinations表中插入省份数据
    private void insertProvinceData() {
        // 定义SQL插入语句，插入省份数据
        String sql = "INSERT INTO travel_destinations (name, code, level, parent_code, description) VALUES (?, ?, ?, ?, ?)";
        
        // 定义省份数据数组，包含所有省份的信息
        Object[][] provinces = {
            {"北京市", "110000", 1, null, "中华人民共和国首都，政治文化中心"},
            {"天津市", "120000", 1, null, "直辖市，北方重要港口城市"},
            {"河北省", "130000", 1, null, "环绕京津的重要省份"},
            {"山西省", "140000", 1, null, "煤炭资源丰富的内陆省份"},
            {"内蒙古自治区", "150000", 1, null, "辽阔的草原和沙漠地区"},
            {"辽宁省", "210000", 1, null, "东北重要工业基地"},
            {"吉林省", "220000", 1, null, "东北粮食主产区"},
            {"黑龙江省", "230000", 1, null, "中国最北端省份"},
            {"上海市", "310000", 1, null, "国际金融中心"},
            {"江苏省", "320000", 1, null, "经济发达的东部省份"},
            {"浙江省", "330000", 1, null, "民营经济发达地区"},
            {"安徽省", "340000", 1, null, "中部崛起重要省份"},
            {"福建省", "350000", 1, null, "东南沿海省份"},
            {"江西省", "360000", 1, null, "红色革命根据地"},
            {"山东省", "370000", 1, null, "经济大省，孔孟之乡"},
            {"河南省", "410000", 1, null, "中原腹地，人口大省"},
            {"湖北省", "420000", 1, null, "九省通衢"},
            {"湖南省", "430000", 1, null, "鱼米之乡"},
            {"广东省", "440000", 1, null, "改革开放前沿"},
            {"广西壮族自治区", "450000", 1, null, "桂林山水甲天下"},
            {"海南省", "460000", 1, null, "热带海岛旅游胜地"},
            {"重庆市", "500000", 1, null, "山城，火锅之都"},
            {"四川省", "510000", 1, null, "天府之国"},
            {"贵州省", "520000", 1, null, "山地旅游胜地"},
            {"云南省", "530000", 1, null, "彩云之南"},
            {"西藏自治区", "540000", 1, null, "世界屋脊"},
            {"陕西省", "610000", 1, null, "十三朝古都西安所在地"},
            {"甘肃省", "620000", 1, null, "丝绸之路重要通道"},
            {"青海省", "630000", 1, null, "青藏高原门户"},
            {"宁夏回族自治区", "640000", 1, null, "塞上江南"},
            {"新疆维吾尔自治区", "650000", 1, null, "丝绸之路核心区"},
            {"台湾省", "710000", 1, null, "宝岛台湾"},
            {"香港特别行政区", "810000", 1, null, "东方之珠"},
            {"澳门特别行政区", "820000", 1, null, "世界旅游休闲中心"}
        };
        
        // 遍历省份数据数组，逐个插入省份数据
        for (Object[] province : provinces) {
            // 使用jdbcTemplate.update方法，执行INSERT语句，插入省份数据
            jdbcTemplate.update(sql, province);
        }
        
        // 打印成功信息到控制台，显示插入的省份数量
        System.out.println("省份数据插入完成，共 " + provinces.length + " 条记录");
    }

    /**
     * 插入城市数据
     * 功能概述：向travel_destinations表中插入城市数据，包括城市名称、编码、级别、父级编码、描述等
     */
    // 插入城市数据方法，向travel_destinations表中插入城市数据
    private void insertCityData() {
        // 定义SQL插入语句，插入城市数据
        String sql = "INSERT INTO travel_destinations (name, code, level, parent_code, description) VALUES (?, ?, ?, ?, ?)";
        
        // 定义城市数据数组，包含所有城市的信息
        Object[][] cities = {
            {"沈阳市", "210100", 2, "210000", "辽宁省省会，东北地区重要中心城市"},
            {"大连市", "210200", 2, "210000", "重要港口城市，东北亚国际航运中心"},
            {"鞍山市", "210300", 2, "210000", "钢铁之都"},
            {"抚顺市", "210400", 2, "210000", "煤都"},
            {"本溪市", "210500", 2, "210000", "钢铁城市"},
            {"丹东市", "210600", 2, "210000", "边境城市，鸭绿江畔"},
            {"锦州市", "210700", 2, "210000", "辽西重要城市"},
            {"营口市", "210800", 2, "210000", "辽河入海口"},
            {"阜新市", "210900", 2, "210000", "煤电之城"},
            {"辽阳市", "211000", 2, "210000", "古城辽阳"},
            {"盘锦市", "211100", 2, "210000", "石油之城"},
            {"铁岭市", "211200", 2, "210000", "北方水乡"},
            {"朝阳市", "211300", 2, "210000", "三燕古都"},
            {"葫芦岛市", "211400", 2, "210000", "海滨城市"}
        };
        
        // 遍历城市数据数组，逐个插入城市数据
        for (Object[] city : cities) {
            // 使用jdbcTemplate.update方法，执行INSERT语句，插入城市数据
            jdbcTemplate.update(sql, city);
        }
        
        // 打印成功信息到控制台，显示插入的城市数量
        System.out.println("城市数据插入完成，共 " + cities.length + " 条记录");
    }

    /**
     * 插入推荐数据
     * 功能概述：向travel_recommendations表中插入推荐数据，包括目的地编码、标题、内容、类别、图片URL、评分、价格范围、地址、标签、排序等
     */
    // 插入推荐数据方法，向travel_recommendations表中插入推荐数据
    private void insertRecommendationData() {
        // 定义SQL插入语句，插入推荐数据
        String sql = "INSERT INTO travel_recommendations (destination_code, title, content, category, image_url, rating, price_range, address, tags, sort_order) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        // 定义推荐数据数组，包含所有推荐信息
        Object[][] recommendations = {
            {"210200", "大连老虎滩海洋公园", "中国最大的现代化海洋主题公园，拥有丰富的海洋生物和精彩的海洋动物表演。", "景点", "/img/dalian/laohutan.jpg", 4.6, "220-280元", "大连市中山区滨海中路9号", "海洋公园,亲子游,动物表演", 1},
            {"210200", "大连圣亚海洋世界", "亚洲最长的海底通道，可以与海洋生物零距离接触。", "景点", "/img/dalian/shengya.jpg", 4.5, "190-240元", "大连市沙河口区中山路608-6-8号", "海底世界,海洋生物,亲子游", 2},
            {"210200", "大连森林动物园", "依山傍海的大型动物园，拥有众多珍稀动物。", "景点", "/img/dalian/zoo.jpg", 4.4, "120-150元", "大连市西岗区南石道街迎春路60号", "动物园,自然风光,亲子游", 3},
            {"210200", "星海广场", "亚洲最大的城市广场，大连的标志性景点。", "景点", "/img/dalian/xinghai.jpg", 4.7, "免费", "大连市沙河口区中山路572号", "城市广场,海景,免费景点", 4},
            {"210200", "大连海鲜美食", "品尝最新鲜的海鲜，体验大连独特的海鲜文化。", "美食", "/img/dalian/seafood.jpg", 4.5, "200-500元", "大连市各大海鲜市场", "海鲜,特色美食,海鲜市场", 5},
            {"210200", "大连现代博物馆", "了解大连历史文化的重要场所。", "景点", "/img/dalian/museum.jpg", 4.3, "免费", "大连市沙河口区会展路10号", "博物馆,历史文化,免费景点", 6},
            {"210200", "大连地铁", "便捷的城市轨道交通，连接主要景点和商圈。", "交通", "/img/dalian/metro.jpg", 4.2, "2-7元", "大连市各地铁站点", "地铁,公共交通,便民", 7},
            {"210200", "大连出租车", "24小时服务的便民出行方式。", "交通", "/img/dalian/taxi.jpg", 4.0, "起步价8元", "大连市区", "出租车,便民出行", 8}
        };
        
        // 遍历推荐数据数组，逐个插入推荐数据
        for (Object[] recommendation : recommendations) {
            // 使用jdbcTemplate.update方法，执行INSERT语句，插入推荐数据
            jdbcTemplate.update(sql, recommendation);
        }
        
        // 打印成功信息到控制台，显示插入的推荐数量
        System.out.println("推荐数据插入完成，共 " + recommendations.length + " 条记录");
    }

    /**
     * 验证数据
     * 功能概述：验证插入的数据是否正确，包括验证省份数据、城市数据、推荐数据
     */
    // 验证数据方法，验证插入的数据是否正确
    private void verifyData() {
        // 验证省份数据
        // 使用jdbcTemplate.queryForList方法，执行SELECT语句，查询省份数据（限制5条）
        List<Map<String, Object>> provinces = jdbcTemplate.queryForList("SELECT name, code FROM travel_destinations WHERE level = 1 LIMIT 5");
        // 打印验证标题到控制台
        System.out.println("=== 验证省份数据 ===");
        // 遍历省份数据，逐个打印省份信息
        for (Map<String, Object> province : provinces) {
            // 打印省份名称和编码到控制台
            System.out.println("省份: " + province.get("name") + ", 编码: " + province.get("code"));
        }
        
        // 验证城市数据
        // 使用jdbcTemplate.queryForList方法，执行SELECT语句，查询城市数据（限制5条）
        List<Map<String, Object>> cities = jdbcTemplate.queryForList("SELECT name, code FROM travel_destinations WHERE level = 2 LIMIT 5");
        // 打印验证标题到控制台
        System.out.println("=== 验证城市数据 ===");
        // 遍历城市数据，逐个打印城市信息
        for (Map<String, Object> city : cities) {
            // 打印城市名称和编码到控制台
            System.out.println("城市: " + city.get("name") + ", 编码: " + city.get("code"));
        }
        
        // 验证推荐数据
        // 使用jdbcTemplate.queryForList方法，执行SELECT语句，查询推荐数据（限制5条）
        List<Map<String, Object>> recommendations = jdbcTemplate.queryForList("SELECT title, category FROM travel_recommendations LIMIT 5");
        // 打印验证标题到控制台
        System.out.println("=== 验证推荐数据 ===");
        // 遍历推荐数据，逐个打印推荐信息
        for (Map<String, Object> rec : recommendations) {
            // 打印推荐标题和分类到控制台
            System.out.println("标题: " + rec.get("title") + ", 分类: " + rec.get("category"));
        }
    }
} 
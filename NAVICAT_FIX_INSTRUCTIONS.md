# Navicat中文显示问号问题修复指南

## 问题原因
数据在插入时使用了错误的字符编码，导致中文字符被存储为问号（HEX: 3F）。

## 解决方案

### 方案1：在Navicat中直接执行UPDATE语句（推荐）

1. **打开Navicat，连接到xihu数据库**

2. **设置连接字符集**：
   - 右键点击连接 → 编辑连接
   - 在"高级"选项卡中：
     - 字符集：选择 `utf8mb4`
     - 排序规则：选择 `utf8mb4_unicode_ci`
   - 点击"确定"并重新连接

3. **执行以下SQL语句修复数据**：
   ```sql
   SET NAMES utf8mb4;
   
   -- 更新所有损坏的景点名称
   UPDATE attractions SET name = '天坛公园' WHERE id = 45;
   UPDATE attractions SET name = '颐和园' WHERE id = 46;
   UPDATE attractions SET name = '圆明园' WHERE id = 47;
   UPDATE attractions SET name = '古文化街' WHERE id = 48;
   UPDATE attractions SET name = '天津之眼' WHERE id = 49;
   UPDATE attractions SET name = '承德避暑山庄' WHERE id = 50;
   UPDATE attractions SET name = '山海关' WHERE id = 51;
   UPDATE attractions SET name = '平遥古城' WHERE id = 52;
   UPDATE attractions SET name = '云冈石窟' WHERE id = 53;
   UPDATE attractions SET name = '呼伦贝尔大草原' WHERE id = 54;
   UPDATE attractions SET name = '响沙湾' WHERE id = 55;
   UPDATE attractions SET name = '沈阳故宫' WHERE id = 56;
   UPDATE attractions SET name = '大连星海广场' WHERE id = 57;
   UPDATE attractions SET name = '长白山天池' WHERE id = 58;
   ```

4. **验证修复结果**：
   ```sql
   SELECT id, name, province, city FROM attractions WHERE id BETWEEN 45 AND 58;
   ```

### 方案2：删除损坏数据并重新插入

如果方案1不起作用，可以删除所有损坏的记录，然后使用Navicat的"导入向导"功能重新导入正确的数据。

### 方案3：检查Navicat显示设置

1. **检查Navicat的显示编码**：
   - 工具 → 选项 → 编辑器
   - 确保"字符集"设置为 `UTF-8`

2. **检查查询结果的显示**：
   - 在查询窗口中执行：`SET NAMES utf8mb4;`
   - 然后查询数据：`SELECT * FROM attractions WHERE id = 45;`

## 注意事项

- 确保Navicat连接时使用 `utf8mb4` 字符集
- 执行UPDATE语句前先执行 `SET NAMES utf8mb4;`
- 如果问题仍然存在，可能需要重新创建数据库连接


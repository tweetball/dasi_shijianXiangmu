# Attractions表编码格式检查报告

## 1. 表级别编码设置

- **表默认字符集**: `utf8mb4`
- **表默认排序规则**: `utf8mb4_0900_ai_ci`
- **存储引擎**: `InnoDB`

## 2. 字段级别编码设置

所有文本字段的编码格式**完全一致**：

| 字段名 | 字符集 | 排序规则 | 数据类型 |
|--------|--------|----------|----------|
| name | utf8mb4 | utf8mb4_0900_ai_ci | varchar(200) |
| province | utf8mb4 | utf8mb4_0900_ai_ci | varchar(50) |
| province_id | utf8mb4 | utf8mb4_0900_ai_ci | varchar(255) |
| city | utf8mb4 | utf8mb4_0900_ai_ci | varchar(50) |
| city_id | utf8mb4 | utf8mb4_0900_ai_ci | varchar(255) |
| district | utf8mb4 | utf8mb4_0900_ai_ci | varchar(50) |
| full_address | utf8mb4 | utf8mb4_0900_ai_ci | varchar(500) |
| price_range | utf8mb4 | utf8mb4_0900_ai_ci | varchar(50) |
| opening_hours | utf8mb4 | utf8mb4_0900_ai_ci | varchar(200) |
| phone | utf8mb4 | utf8mb4_0900_ai_ci | varchar(50) |
| website | utf8mb4 | utf8mb4_0900_ai_ci | varchar(200) |
| description | utf8mb4 | utf8mb4_0900_ai_ci | text |
| image_url | utf8mb4 | utf8mb4_0900_ai_ci | varchar(500) |
| features | utf8mb4 | utf8mb4_0900_ai_ci | text |
| tips | utf8mb4 | utf8mb4_0900_ai_ci | text |
| best_season | utf8mb4 | utf8mb4_0900_ai_ci | varchar(100) |
| visit_duration | utf8mb4 | utf8mb4_0900_ai_ci | varchar(50) |
| traffic_info | utf8mb4 | utf8mb4_0900_ai_ci | text |
| ticket_info | utf8mb4 | utf8mb4_0900_ai_ci | text |

## 3. 数据编码状态

### 正常数据示例（ID=1）
- **名称**: 故宫博物院
- **HEX编码**: `E69585E5AEABE58D9AE789A9E999A2` (正确的UTF-8编码)
- **字符长度**: 5
- **字节长度**: 15 (每个中文字符3字节)

### 损坏数据示例（ID=198, 199）
- **名称**: ???? / ???
- **HEX编码**: `3F3F3F3F` / `3F3F3F` (问号字符，ASCII码0x3F)
- **字符长度**: 4 / 3
- **字节长度**: 4 / 3 (字符长度=字节长度，说明存储的就是问号本身)

## 4. 数据统计

- **总记录数**: 110条（status=1）
- **正常数据**: 44条
- **损坏数据**: 66条（包含问号或HEX编码包含3F）

## 5. 结论

✅ **编码格式完全一致**：所有字段都使用 `utf8mb4` 字符集和 `utf8mb4_0900_ai_ci` 排序规则

❌ **数据问题**：66条记录在插入时使用了错误的字符编码，导致中文字符被存储为问号（0x3F）

## 6. 问题原因

数据插入时，客户端或连接没有正确设置UTF-8编码，导致中文字符在传输过程中被错误转换。

## 7. 解决方案

需要修复这66条损坏的记录，使用正确的UTF-8编码重新插入中文数据。


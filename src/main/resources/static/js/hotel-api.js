/**
 * 统一住宿服务API管理JavaScript
 * 功能概述：提供统一的住宿服务API管理类，封装所有与酒店相关的API请求
 */

/**
 * 酒店API类
 * 功能概述：封装所有与酒店相关的API请求方法，提供统一的请求接口
 */
class HotelAPI {
    /**
     * 构造函数
     * 功能概述：初始化API基础路径和超时时间
     */
    // 构造函数，初始化API配置
    constructor() {
        // 统一API基础路径，所有API请求的基础URL
        this.baseURL = '/api/hotels';
        // 10秒超时，API请求的超时时间（毫秒）
        this.timeout = 10000;
    }

    /**
     * 统一请求方法
     * 功能概述：封装fetch API请求，提供统一的错误处理和响应格式
     * @param {string} url - 请求URL（相对于baseURL）
     * @param {Object} options - 请求选项（method、headers、body等）
     * @return {Promise<Object>} 返回包含success、data、error的响应对象
     */
    // 统一请求方法，接收URL和选项，返回响应对象
    async request(url, options = {}) {
        // 创建请求配置对象，合并默认配置和传入的选项
        const config = {
            timeout: this.timeout,                              // 超时时间
            headers: {
                'Content-Type': 'application/json',             // 默认请求头，指定内容类型为JSON
                ...options.headers                              // 合并传入的请求头
            },
            ...options                                          // 合并传入的其他选项（method、body等）
        };

        // 使用try-catch捕获异常
        try {
            // 使用fetch API发送请求，URL为baseURL + url
            const response = await fetch(this.baseURL + url, config);
            
            // 判断HTTP响应是否成功（状态码200-299）
            if (!response.ok) {
                // 如果响应不成功，抛出错误，包含HTTP状态码和状态文本
                throw new Error(`HTTP ${response.status}: ${response.statusText}`);
            }
            
            // 将响应体解析为JSON对象
            const data = await response.json();
            // 返回成功响应对象，包含success标志和data数据
            return { success: true, data };
        // 捕获所有异常
        } catch (error) {
            // 打印API请求失败错误日志到控制台
            console.error('API请求失败:', error);
            // 返回失败响应对象，包含success标志、error错误信息和data数据（null）
            return {
                success: false,
                error: error.message,                           // 错误消息
                data: null                                      // 数据为null
            };
        }
    }

    /**
     * 获取酒店列表
     * 功能概述：根据查询参数，从后端获取酒店列表
     * @param {Object} params - 查询参数（如page、pageSize、keyword等）
     * @return {Promise<Object>} 返回包含success、data的响应对象
     */
    // 获取酒店列表方法，接收查询参数，返回响应对象
    async getHotels(params = {}) {
        // 将查询参数对象转换为URL查询字符串
        const queryString = new URLSearchParams(params).toString();
        // 调用统一请求方法，URL为"?查询字符串"
        return await this.request(`?${queryString}`);
    }

    /**
     * 获取热门酒店
     * 功能概述：从后端获取热门酒店列表
     * @param {number} limit - 返回的酒店数量（默认为6）
     * @return {Promise<Object>} 返回包含success、data的响应对象
     */
    // 获取热门酒店方法，接收数量限制，返回响应对象
    async getHotHotels(limit = 6) {
        // 调用统一请求方法，URL为"/hot?limit=数量"
        return await this.request(`/hot?limit=${limit}`);
    }

    /**
     * 搜索酒店
     * 功能概述：根据关键词和筛选条件，从后端搜索酒店
     * @param {string} keyword - 搜索关键词
     * @param {Object} filters - 筛选条件（如priceRange、location等）
     * @return {Promise<Object>} 返回包含success、data的响应对象
     */
    // 搜索酒店方法，接收关键词和筛选条件，返回响应对象
    async searchHotels(keyword, filters = {}) {
        // 创建请求参数对象，包含关键词和筛选条件
        const params = {
            keyword,                                            // 搜索关键词
            ...filters                                          // 展开筛选条件对象
        };
        // 调用统一请求方法，使用POST方法，URL为"/search"，请求体为参数对象的JSON字符串
        return await this.request('/search', {
            method: 'POST',                                     // 请求方法为POST
            body: JSON.stringify(params)                        // 请求体，将参数对象转换为JSON字符串
        });
    }

    /**
     * 获取酒店详情
     * 功能概述：根据酒店ID，从后端获取酒店详情
     * @param {number} id - 酒店ID
     * @return {Promise<Object>} 返回包含success、data的响应对象
     */
    // 获取酒店详情方法，接收酒店ID，返回响应对象
    async getHotelDetail(id) {
        // 调用统一请求方法，URL为"/酒店ID"
        return await this.request(`/${id}`);
    }

    /**
     * 获取酒店房型
     * 功能概述：根据酒店ID，从后端获取该酒店的所有房型
     * @param {number} hotelId - 酒店ID
     * @return {Promise<Object>} 返回包含success、data的响应对象
     */
    // 获取酒店房型方法，接收酒店ID，返回响应对象
    async getHotelRooms(hotelId) {
        // 调用统一请求方法，URL为"/酒店ID/rooms"
        return await this.request(`/${hotelId}/rooms`);
    }

    /**
     * 创建预订
     * 功能概述：根据预订数据，向后端提交酒店预订请求
     * @param {Object} bookingData - 预订数据（包含酒店ID、房型ID、入住日期等）
     * @return {Promise<Object>} 返回包含success、data的响应对象
     */
    // 创建预订方法，接收预订数据，返回响应对象
    async createBooking(bookingData) {
        // 调用统一请求方法，使用POST方法，URL为"/bookings"，请求体为预订数据的JSON字符串
        return await this.request('/bookings', {
            method: 'POST',                                     // 请求方法为POST
            body: JSON.stringify(bookingData)                   // 请求体，将预订数据对象转换为JSON字符串
        });
    }

    /**
     * 获取用户订单
     * 功能概述：根据用户ID，从后端获取该用户的所有订单
     * @param {number} userId - 用户ID
     * @return {Promise<Object>} 返回包含success、data的响应对象
     */
    // 获取用户订单方法，接收用户ID，返回响应对象
    async getUserOrders(userId) {
        // 调用统一请求方法，URL为"/orders?userId=用户ID"
        return await this.request(`/orders?userId=${userId}`);
    }

    /**
     * 取消订单
     * 功能概述：根据订单ID，向后端提交取消订单请求
     * @param {number} orderId - 订单ID
     * @return {Promise<Object>} 返回包含success、data的响应对象
     */
    // 取消订单方法，接收订单ID，返回响应对象
    async cancelOrder(orderId) {
        // 调用统一请求方法，使用PUT方法，URL为"/orders/订单ID/cancel"
        return await this.request(`/orders/${orderId}/cancel`, {
            method: 'PUT'                                       // 请求方法为PUT
        });
    }

    /**
     * 确认订单
     * 功能概述：根据订单ID，向后端提交确认订单请求
     * @param {number} orderId - 订单ID
     * @return {Promise<Object>} 返回包含success、data的响应对象
     */
    // 确认订单方法，接收订单ID，返回响应对象
    async confirmOrder(orderId) {
        // 调用统一请求方法，使用PUT方法，URL为"/orders/订单ID/confirm"
        return await this.request(`/orders/${orderId}/confirm`, {
            method: 'PUT'                                       // 请求方法为PUT
        });
    }

    /**
     * 获取省份列表
     * 功能概述：从后端获取所有省份列表
     * @return {Promise<Object>} 返回包含success、data的响应对象
     */
    // 获取省份列表方法，返回响应对象
    async getProvinces() {
        // 调用统一请求方法，URL为"/provinces"
        return await this.request('/provinces');
    }

    /**
     * 根据省份获取城市
     * 功能概述：根据省份ID，从后端获取该省份的所有城市
     * @param {number} provinceId - 省份ID
     * @return {Promise<Object>} 返回包含success、data的响应对象
     */
    // 根据省份获取城市方法，接收省份ID，返回响应对象
    async getCitiesByProvince(provinceId) {
        // 调用统一请求方法，URL为"/cities?provinceId=省份ID"
        return await this.request(`/cities?provinceId=${provinceId}`);
    }
}

/**
 * 统一错误处理类
 * 功能概述：提供统一的错误处理功能，包括错误消息处理和错误提示显示
 */
class HotelErrorHandler {
    /**
     * 处理错误
     * 功能概述：根据错误类型，返回相应的错误提示消息
     * @param {Error} error - 错误对象
     * @param {string} context - 错误上下文（可选）
     * @return {string} 错误提示消息
     */
    // 处理错误静态方法，接收错误对象和上下文，返回错误提示消息
    static handle(error, context = '') {
        // 打印住宿服务错误日志到控制台，包含上下文和错误对象
        console.error(`住宿服务错误 [${context}]:`, error);
        
        // 根据错误类型显示不同提示
        // 判断错误消息是否包含"HTTP 404"
        if (error.message.includes('HTTP 404')) {
            // 如果包含"HTTP 404"，返回"请求的资源不存在"
            return '请求的资源不存在';
        // 判断错误消息是否包含"HTTP 500"
        } else if (error.message.includes('HTTP 500')) {
            // 如果包含"HTTP 500"，返回"服务器内部错误，请稍后重试"
            return '服务器内部错误，请稍后重试';
        // 判断错误消息是否包含"timeout"
        } else if (error.message.includes('timeout')) {
            // 如果包含"timeout"，返回"请求超时，请检查网络连接"
            return '请求超时，请检查网络连接';
        // 其他错误类型
        } else {
            // 返回通用错误提示消息
            return '操作失败，请重试';
        }
    }

    /**
     * 显示错误
     * 功能概述：在指定元素中显示错误提示，如果没有指定元素则显示全局错误提示
     * @param {string} message - 错误消息
     * @param {HTMLElement} element - 显示错误的元素（可选，如果为null则显示全局提示）
     */
    // 显示错误静态方法，接收错误消息和元素，显示错误提示
    static showError(message, element = null) {
        // 生成错误提示HTML字符串，包含错误图标和错误消息
        const errorHtml = `
            <div class="unified-error">
                <i class="fas fa-exclamation-triangle"></i>
                ${message}
            </div>
        `;
        
        // 判断是否指定了显示元素
        if (element) {
            // 如果指定了元素，将错误提示HTML设置到元素中
            element.innerHTML = errorHtml;
        // 如果没有指定元素
        } else {
            // 显示全局错误提示
            // 调用showToast方法，显示错误类型的全局提示
            this.showToast(message, 'error');
        }
    }

    /**
     * 显示成功提示
     * 功能概述：显示全局成功提示
     * @param {string} message - 成功消息
     */
    // 显示成功提示静态方法，接收成功消息，显示成功提示
    static showSuccess(message) {
        // 调用showToast方法，显示成功类型的全局提示
        this.showToast(message, 'success');
    }

    /**
     * 显示提示消息（Toast）
     * 功能概述：创建并显示一个全局提示消息，自动隐藏
     * @param {string} message - 提示消息
     * @param {string} type - 提示类型（'info'、'success'、'error'，默认为'info'）
     */
    // 显示提示消息静态方法，接收提示消息和类型，显示全局提示
    static showToast(message, type = 'info') {
        // 创建提示消息div元素
        const toast = document.createElement('div');
        // 设置提示消息的样式类，根据类型设置不同的样式类
        toast.className = `hotel-toast hotel-toast-${type}`;
        // 设置提示消息的HTML内容，包含图标和消息文本
        toast.innerHTML = `
            <i class="fas fa-${type === 'success' ? 'check-circle' : type === 'error' ? 'exclamation-circle' : 'info-circle'}"></i>
            ${message}
        `;
        
        // 将提示消息元素添加到页面body中
        document.body.appendChild(toast);
        
        // 显示动画
        // 使用setTimeout延迟执行，确保DOM已更新
        setTimeout(() => {
            // 为提示消息元素添加show样式类，显示提示消息
            toast.classList.add('show');
        }, 100);  // 延迟100毫秒执行
        
        // 自动隐藏
        // 使用setTimeout延迟执行，3秒后隐藏提示消息
        setTimeout(() => {
            // 从提示消息元素移除show样式类，隐藏提示消息
            toast.classList.remove('show');
            // 使用setTimeout延迟执行，300毫秒后移除提示消息元素
            setTimeout(() => {
                // 从页面body中移除提示消息元素
                document.body.removeChild(toast);
            }, 300);  // 延迟300毫秒执行（等待动画完成）
        }, 3000);  // 延迟3000毫秒执行（3秒后隐藏）
    }
}

/**
 * 统一加载状态管理类
 * 功能概述：提供统一的加载状态管理功能，包括显示和隐藏加载状态
 */
class HotelLoadingManager {
    /**
     * 显示加载状态
     * 功能概述：在指定元素中显示加载状态
     * @param {HTMLElement} element - 显示加载状态的元素
     * @param {string} message - 加载消息（默认为'加载中...'）
     */
    // 显示加载状态静态方法，接收元素和消息，显示加载状态
    static show(element, message = '加载中...') {
        // 生成加载状态HTML字符串，包含加载图标和加载消息
        const loadingHtml = `
            <div class="unified-loading">
                <i class="fas fa-spinner fa-spin"></i>
                ${message}
            </div>
        `;
        // 将加载状态HTML设置到元素中
        element.innerHTML = loadingHtml;
    }

    /**
     * 隐藏加载状态
     * 功能概述：隐藏指定元素中的加载状态
     * @param {HTMLElement} element - 隐藏加载状态的元素
     */
    // 隐藏加载状态静态方法，接收元素，隐藏加载状态
    static hide(element) {
        // 清空元素内容，隐藏加载状态
        element.innerHTML = '';
    }
}

/**
 * 统一数据验证类
 * 功能概述：提供统一的数据验证功能，包括预订数据验证、手机号验证、邮箱验证等
 */
class HotelValidator {
    /**
     * 验证预订数据
     * 功能概述：验证酒店预订数据的所有字段，返回验证结果和错误列表
     * @param {Object} data - 预订数据对象（包含酒店ID、房型ID、入住日期等）
     * @return {Object} 返回包含isValid（是否有效）和errors（错误列表）的对象
     */
    // 验证预订数据静态方法，接收预订数据对象，返回验证结果
    static validateBooking(data) {
        // 初始化错误列表数组
        const errors = [];
        
        // 验证酒店ID是否存在
        if (!data.hotelId) {
            // 如果酒店ID不存在，添加错误消息到错误列表
            errors.push('请选择酒店');
        }
        
        // 验证房型ID是否存在
        if (!data.roomId) {
            // 如果房型ID不存在，添加错误消息到错误列表
            errors.push('请选择房型');
        }
        
        // 验证入住日期是否存在
        if (!data.checkInDate) {
            // 如果入住日期不存在，添加错误消息到错误列表
            errors.push('请选择入住日期');
        }
        
        // 验证离店日期是否存在
        if (!data.checkOutDate) {
            // 如果离店日期不存在，添加错误消息到错误列表
            errors.push('请选择离店日期');
        }
        
        // 如果入住日期和离店日期都存在，验证日期逻辑
        if (data.checkInDate && data.checkOutDate) {
            // 创建入住日期对象
            const checkIn = new Date(data.checkInDate);
            // 创建离店日期对象
            const checkOut = new Date(data.checkOutDate);
            
            // 判断入住日期是否晚于或等于离店日期
            if (checkIn >= checkOut) {
                // 如果入住日期晚于或等于离店日期，添加错误消息到错误列表
                errors.push('离店日期必须晚于入住日期');
            }
            
            // 判断入住日期是否早于今天
            if (checkIn < new Date()) {
                // 如果入住日期早于今天，添加错误消息到错误列表
                errors.push('入住日期不能早于今天');
            }
        }
        
        // 验证联系人姓名是否存在且长度大于等于2
        if (!data.contactName || data.contactName.trim().length < 2) {
            // 如果联系人姓名不存在或长度小于2，添加错误消息到错误列表
            errors.push('请输入有效的联系人姓名');
        }
        
        // 验证联系电话是否存在且格式有效
        if (!data.contactPhone || !this.isValidPhone(data.contactPhone)) {
            // 如果联系电话不存在或格式无效，添加错误消息到错误列表
            errors.push('请输入有效的手机号码');
        }
        
        // 返回验证结果对象，包含isValid（错误列表长度为0表示有效）和errors（错误列表）
        return {
            isValid: errors.length === 0,                      // 是否有效（错误列表长度为0表示有效）
            errors                                              // 错误列表
        };
    }

    /**
     * 验证手机号格式
     * 功能概述：使用正则表达式验证手机号格式（1开头，第二位是3-9，共11位数字）
     * @param {string} phone - 手机号字符串
     * @return {boolean} 是否有效（true=有效，false=无效）
     */
    // 验证手机号格式静态方法，接收手机号字符串，返回是否有效
    static isValidPhone(phone) {
        // 定义手机号正则表达式（1开头，第二位是3-9，共11位数字）
        const phoneRegex = /^1[3-9]\d{9}$/;
        // 使用正则表达式测试手机号格式，返回是否匹配
        return phoneRegex.test(phone);
    }

    /**
     * 验证邮箱格式
     * 功能概述：使用正则表达式验证邮箱格式
     * @param {string} email - 邮箱字符串
     * @return {boolean} 是否有效（true=有效，false=无效）
     */
    // 验证邮箱格式静态方法，接收邮箱字符串，返回是否有效
    static isValidEmail(email) {
        // 定义邮箱正则表达式（包含@符号和域名）
        const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
        // 使用正则表达式测试邮箱格式，返回是否匹配
        return emailRegex.test(email);
    }
}

// 创建全局API实例
// 创建HotelAPI实例并导出到window对象，供其他模块使用
window.hotelAPI = new HotelAPI();
// 导出HotelErrorHandler类到window对象，供其他模块使用
window.hotelErrorHandler = HotelErrorHandler;
// 导出HotelLoadingManager类到window对象，供其他模块使用
window.hotelLoadingManager = HotelLoadingManager;
// 导出HotelValidator类到window对象，供其他模块使用
window.hotelValidator = HotelValidator;

// 导出供其他模块使用
// 如果支持CommonJS模块系统（Node.js环境），导出这些类和实例
if (typeof module !== 'undefined' && module.exports) {
    // 导出HotelAPI类、HotelErrorHandler类、HotelLoadingManager类和HotelValidator类
    module.exports = {
        HotelAPI,                                              // HotelAPI类
        HotelErrorHandler,                                     // HotelErrorHandler类
        HotelLoadingManager,                                  // HotelLoadingManager类
        HotelValidator                                        // HotelValidator类
    };
}

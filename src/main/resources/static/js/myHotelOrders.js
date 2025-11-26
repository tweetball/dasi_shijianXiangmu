/**
 * 我的酒店订单页面JavaScript
 * 功能概述：处理我的酒店订单页面的交互逻辑，包括订单列表加载、订单状态显示、订单操作等
 */

// 订单状态映射对象
// 定义订单状态映射，将状态码映射为状态文本和样式类
const statusMap = {
    0: { text: '待确认', class: 'status-pending' },      // 待确认状态
    1: { text: '已确认', class: 'status-confirmed' },    // 已确认状态
    2: { text: '已取消', class: 'status-canceled' }      // 已取消状态
};

/**
 * 页面初始化
 * 功能概述：当DOM加载完成后执行，加载用户信息、检查登录状态、获取订单列表
 */
// 初始化页面，当DOM加载完成后执行
$(document).ready(function() {
    // 从本地存储加载用户信息
    // 调用loadUserInfo函数，从localStorage加载用户信息
    loadUserInfo();

    // 检查用户是否已登录
    // 调用checkLogin函数，检查用户是否已登录
    if (!checkLogin()) {
        // 如果用户未登录，显示提示信息
        alert('请先登录查看订单');
        // 跳转到首页或登录页
        window.location.href = '/hc/f2';
        // 返回，不继续执行
        return;
    }

    // 获取并显示订单列表
    // 调用fetchOrders函数，从后端获取订单列表并显示
    fetchOrders();

    // 用户资料按钮点击事件
    // 为用户资料按钮绑定click事件，点击时执行showUserProfile函数
    $('#userProfileBtn').on('click', function() {
        // 调用showUserProfile函数，显示用户个人中心
        showUserProfile();
    });
});

/**
 * 检查用户是否已登录
 * 功能概述：从localStorage中获取用户ID和用户名，判断用户是否已登录
 * @return {boolean} 是否已登录（true=已登录，false=未登录）
 */
// 检查用户是否已登录函数，返回是否已登录
function checkLogin() {
    // 从localStorage中获取用户ID
    const userId = localStorage.getItem('userId');
    // 从localStorage中获取用户名
    const userName = localStorage.getItem('userName');

    // 判断用户ID和用户名是否存在
    if (userId && userName) {
        // 如果存在，将用户名显示到页面上
        $('#userName').text(userName);
        // 返回true表示已登录
        return true;
    }

    // 如果不存在，返回false表示未登录
    return false;
}

/**
 * 从本地存储加载用户信息
 * 功能概述：从localStorage中读取用户ID和用户名，并打印日志
 */
// 从本地存储加载用户信息函数，从localStorage加载用户信息
function loadUserInfo() {
    // 使用try-catch捕获异常
    try {
        // 从localStorage中获取用户ID
        const storedId = localStorage.getItem('userId');
        // 从localStorage中获取用户名
        const storedName = localStorage.getItem('userName');

        // 判断用户ID和用户名是否存在
        if (storedId && storedName) {
            // 如果存在，打印用户信息已加载日志到控制台，包含用户ID和用户名
            console.log('用户信息已加载:', { id: storedId, name: storedName });
        // 如果不存在
        } else {
            // 打印未获取到用户信息日志到控制台
            console.log('未获取到用户信息，用户未登录');
        }
    // 捕获所有异常
    } catch (error) {
        // 打印获取本地存储用户信息失败错误日志到控制台
        console.error('获取本地存储用户信息失败:', error);
    }
}

/**
 * 显示用户个人中心
 * 功能概述：从localStorage中获取用户信息，并在弹窗中显示
 */
// 显示用户个人中心函数，显示用户个人中心信息
function showUserProfile() {
    // 从localStorage中获取用户ID
    const userId = localStorage.getItem('userId');
    // 从localStorage中获取用户名
    const userName = localStorage.getItem('userName');

    // 判断用户ID和用户名是否存在
    if (!userId || !userName) {
        // 如果不存在，显示提示信息
        alert('请先登录');
        // 返回，不继续执行
        return;
    }

    // 如果存在，在弹窗中显示用户信息（包含用户ID和用户名）
    alert(`用户信息:\nID: ${userId}\n姓名: ${userName}`);
}

/**
 * 获取订单列表
 * 功能概述：从localStorage中获取用户ID，向后端获取该用户的所有订单并显示
 */
// 获取订单列表函数，从后端获取订单列表并显示
function fetchOrders() {
    // 从localStorage中获取用户ID
    const userId = localStorage.getItem('userId');

    // 判断用户ID是否存在
    if (!userId) {
        // 如果不存在，打印错误日志到控制台
        console.error('未找到用户ID');
        // 调用showNoOrders函数，显示无订单提示
        showNoOrders();
        // 返回，不继续执行
        return;
    }

    // 打印正在获取订单列表日志到控制台，包含用户ID
    console.log('正在获取用户', userId, '的订单列表');

    // 调用API获取订单
    // 使用jQuery的$.ajax方法向后端获取订单列表
    $.ajax({
        url: `/hc/orders?userId=${userId}`,                     // 请求URL，包含用户ID参数
        type: 'GET',                                            // 请求方法为GET
        dataType: 'json',                                       // 响应数据类型为JSON
        success: function(response) {
            // 成功回调函数，处理响应数据
            // 打印订单获取成功日志到控制台，包含响应数据
            console.log('订单获取成功:', response);

            // 隐藏加载状态
            // 隐藏加载状态元素
            $('#loading').hide();

            // 判断订单列表是否获取成功且不为空
            if (response.code === 200 && response.data && response.data.length > 0) {
                // 如果获取成功且不为空，调用renderOrders函数渲染订单列表
                renderOrders(response.data);
            // 如果订单列表为空或获取失败
            } else {
                // 调用showNoOrders函数，显示无订单提示
                showNoOrders();
            }
        },
        error: function(xhr, status, error) {
            // 错误回调函数，处理错误情况
            // 打印订单获取失败错误日志到控制台
            console.error('订单获取失败:', error);
            // 隐藏加载状态元素
            $('#loading').hide();
            // 调用showNoOrders函数，显示无订单提示
            showNoOrders();
            // 显示错误提示信息
            alert('获取订单失败，请稍后再试');
        }
    });
}

/**
 * 渲染订单列表
 * 功能概述：根据订单数据数组，生成订单列表的HTML并渲染到页面上，绑定订单操作事件
 * @param {Array} orders - 订单数据数组（包含订单的所有信息）
 */
// 渲染订单列表函数，接收订单数据数组，渲染订单列表
function renderOrders(orders) {
    // 获取订单列表容器元素
    const orderList = $('#orderList');
    // 清空订单列表容器内容
    orderList.empty();
    // 显示订单列表容器
    orderList.show();

    // 遍历订单数组，为每个订单生成HTML
    orders.forEach(order => {
        // 根据订单状态获取状态信息（状态文本和样式类），如果状态不存在则使用默认值
        const statusInfo = statusMap[order.status] || { text: '未知状态', class: 'text-muted' };

        // 根据订单状态动态生成按钮
        // 初始化操作按钮HTML字符串
        let actionButtons = '';
        // 判断订单状态是否为待确认（0）
        if (order.status === 0) {
            // 待确认 - 显示确认按钮
            // 生成确认订单按钮HTML字符串
            actionButtons = `
                <button class="btn btn-sm btn-primary make-sure" data-id="${order.id}">
                    <i class="fa fa-check-circle-o mr-1"></i> 确认订单
                </button>
            `;
        // 判断订单状态是否为已确认（1）
        } else if (order.status === 1) {
            // 已确认 - 显示取消按钮
            // 生成取消订单按钮HTML字符串
            actionButtons = `
                <button class="btn btn-sm btn-danger cancel-order" data-id="${order.id}">
                    <i class="fa fa-times mr-1"></i> 取消订单
                </button>
            `;
        } // 已取消状态不显示任何按钮

        // 生成订单项的HTML字符串，包含订单号、创建时间、状态、酒店信息、房型信息、入住信息、总价和操作按钮
        const orderItem = `
            <div class="order-item">
                <div class="order-header">
                    <div>
                        <span class="font-weight-bold">订单号:</span> ${order.id}
                        <span class="ml-3"><i class="fa fa-calendar mr-1"></i> ${formatDate(order.creatTime)}</span>
                    </div>
                    <div class="order-status ${statusInfo.class}">
                        ${statusInfo.text}
                    </div>
                </div>
                <div class="order-content">
                    <div class="order-image">
                        <img src="${order.hotelImg || 'https://picsum.photos/200/200?random=hotel'}" alt="${order.hotelName || '未知酒店'}">
                    </div>
                    <div class="order-details">
                        <h4 class="mb-1">${order.hotelName || '未知酒店'}</h4>
                        <p class="mb-1"><i class="fa fa-home mr-1"></i> ${order.rname || '未知房型'}</p>
                        <p class="mb-1"><i class="fa fa-calendar-check-o mr-1"></i>
                            ${formatDate(order.indate)} 至 ${formatDate(order.outdate)} (${calculateDays(order.indate, order.outdate)}晚)
                        </p>
                        <p class="mb-1"><i class="fa fa-user mr-1"></i> ${order.guests}人</p>
                        <p class="font-weight-bold">总价: ¥${order.price.toFixed(2)}</p>
                    </div>
                </div>
                <div class="order-actions">
                    ${actionButtons}
                </div>
            </div>
        `;

        // 将订单项HTML添加到订单列表容器中
        orderList.append(orderItem);
    });

    // 绑定订单操作事件
    // 为所有取消订单按钮绑定click事件
    $('.cancel-order').on('click', function() {
        // 获取按钮的data-id属性值（订单ID）
        const orderId = $(this).data('id');
        // 调用cancelOrder函数，取消订单
        cancelOrder(orderId);
    });

    // 为所有确认订单按钮绑定click事件
    $('.make-sure').on('click', function() {
        // 获取按钮的data-id属性值（订单ID）
        const orderId = $(this).data('id');
        // 调用makeSure函数，确认订单
        makeSure(orderId);
    });
}

/**
 * 显示无订单提示
 * 功能概述：显示无订单提示元素
 */
// 显示无订单提示函数，显示无订单提示
function showNoOrders() {
    // 显示无订单提示元素
    $('#noOrders').show();
}

/**
 * 格式化日期
 * 功能概述：将日期字符串或时间戳转换为"YYYY-MM-DD"格式的日期字符串
 * @param {string|number} dateStr - 日期字符串或时间戳
 * @return {string} 返回格式化后的日期字符串（格式：YYYY-MM-DD），如果日期无效则返回"未知日期"
 */
// 格式化日期函数，接收日期字符串或时间戳，返回格式化后的日期字符串
function formatDate(dateStr) {
    // 判断日期字符串是否存在
    if (!dateStr) {
        // 如果不存在，返回"未知日期"
        return '未知日期';
    }

    // 处理可能的时间戳格式
    // 判断日期字符串是否为数字类型或纯数字字符串
    if (typeof dateStr === 'number' || /^\d+$/.test(dateStr)) {
        // 如果是时间戳格式，将其转换为日期字符串（格式：YYYY-MM-DD）
        dateStr = new Date(parseInt(dateStr)).toISOString().split('T')[0];
    }

    // 创建日期对象
    const date = new Date(dateStr);
    // 返回格式化后的日期字符串（格式：YYYY-MM-DD），月份和日期补零到2位
    return `${date.getFullYear()}-${(date.getMonth() + 1).toString().padStart(2, '0')}-${date.getDate().toString().padStart(2, '0')}`;
}

/**
 * 计算入住天数
 * 功能概述：根据入住日期和离店日期，计算入住天数
 * @param {string} checkInDate - 入住日期字符串
 * @param {string} checkOutDate - 离店日期字符串
 * @return {number} 返回入住天数（向下取整）
 */
// 计算入住天数函数，接收入住日期和离店日期，返回入住天数
function calculateDays(checkInDate, checkOutDate) {
    // 创建入住日期对象
    const inDate = new Date(checkInDate);
    // 创建离店日期对象
    const outDate = new Date(checkOutDate);
    // 定义一天的毫秒数（24小时 × 60分钟 × 60秒 × 1000毫秒）
    const oneDay = 24 * 60 * 60 * 1000;
    // 计算入住天数（离店日期减去入住日期，除以一天的毫秒数，向下取整）
    return Math.floor((outDate - inDate) / oneDay);
}

/**
 * 取消订单
 * 功能概述：根据订单ID，向后端提交取消订单请求，处理响应结果
 * @param {number} orderId - 订单ID
 */
// 取消订单函数，接收订单ID，取消订单
function cancelOrder(orderId) {
    // 显示确认对话框，询问用户是否确定取消订单
    if (!confirm('确定要取消此订单吗？')) {
        // 如果用户取消操作，返回，不继续执行
        return;
    }

    // 使用jQuery的$.ajax方法向后端提交取消订单请求
    $.ajax({
        url: `/hc/orders/${orderId}/cancel`,                   // 请求URL，包含订单ID
        type: 'POST',                                           // 请求方法为POST
        dataType: 'json',                                       // 响应数据类型为JSON
        success: function(response) {
            // 成功回调函数，处理响应数据
            // 判断订单是否取消成功（响应码为200）
            if (response.code === 200) {
                // 如果取消成功，显示成功提示信息
                alert('订单已成功取消');
                // 刷新订单列表
                // 调用fetchOrders函数，重新获取订单列表
                fetchOrders();
            // 如果取消失败
            } else {
                // 显示错误提示信息（优先使用response.message，否则使用默认提示）
                alert(response.message || '取消订单失败');
            }
        },
        error: function(xhr, status, error) {
            // 错误回调函数，处理错误情况
            // 打印取消订单失败错误日志到控制台
            console.error('取消订单失败:', error);
            // 显示错误提示信息
            alert('取消订单失败，请稍后再试');
        }
    });
}

/**
 * 确认订单
 * 功能概述：根据订单ID，向后端提交确认订单请求，处理响应结果
 * @param {number} orderId - 订单ID
 */
// 确认订单函数，接收订单ID，确认订单
function makeSure(orderId) {
    // 显示确认对话框，询问用户是否确定确认订单
    if (!confirm('确定已读并确认此订单吗？')) {
        // 如果用户取消操作，返回，不继续执行
        return;
    }

    // 使用jQuery的$.ajax方法向后端提交确认订单请求
    $.ajax({
        url: `/hc/orders/${orderId}/makeSure`,                  // 请求URL，包含订单ID
        type: 'POST',                                           // 请求方法为POST
        dataType: 'json',                                       // 响应数据类型为JSON
        success: function(response) {
            // 成功回调函数，处理响应数据
            // 判断订单是否确认成功（响应码为200）
            if (response.code === 200) {
                // 如果确认成功，显示成功提示信息
                alert('订单已成功确认');
                // 刷新订单列表
                // 调用fetchOrders函数，重新获取订单列表
                fetchOrders();
            // 如果确认失败
            } else {
                // 显示错误提示信息（优先使用response.message，否则使用默认提示）
                alert(response.message || '确认订单失败');
            }
        },
        error: function(xhr, status, error) {
            // 错误回调函数，处理错误情况
            // 打印确认订单失败错误日志到控制台
            console.error('确认订单失败:', error);
            // 显示错误提示信息
            alert('确认订单失败，请稍后再试');
        }
    });
} 
/**
 * 酒店详情页面JavaScript
 * 功能概述：处理酒店详情页面的交互逻辑，包括用户信息加载、酒店ID初始化、日期选择、房型选择、预订提交等
 */

// 全局变量
// 用户信息对象，存储用户ID和用户名
let userInfo = {
    id: null,      // 用户ID，从本地存储或后端获取
    name: null     // 用户名，从本地存储或后端获取
};
// 登录状态标志，0表示未登录，1表示已登录
let loginFlag = 0;
// 酒店ID，需在页面加载时初始化，从URL参数或隐藏字段获取
let hotelId = null;

/**
 * 页面初始化
 * 功能概述：当DOM加载完成后执行，初始化页面元素和事件监听器
 */
// 使用jQuery的ready方法，当DOM加载完成后执行
$(document).ready(function() {
    // 打印初始化日志到控制台
    console.log('🚀 页面开始初始化 - jQuery已加载');
    
    // 检查页面元素是否正确加载
    // 打印房型数量到控制台
    console.log('房型数量:', $('input[name="roomType"]').length);
    // 打印立即预订按钮数量到控制台
    console.log('立即预订按钮:', $('#bookNowBtn').length);
    // 打印最低价格元素数量到控制台
    console.log('最低价格元素:', $('#minPrice').length);
    
    // 从本地存储获取用户信息
    // 调用loadUserInfo函数，从localStorage加载用户信息
    loadUserInfo();

    // 初始化酒店ID - 实际项目中应从URL或数据接口获取
    // 调用initializeHotelId函数，初始化酒店ID
    initializeHotelId();

    // 初始化页面元素
    // 调用updateHeaderWithUserInfo函数，更新页面头部用户信息
    updateHeaderWithUserInfo();

    // 渲染酒店星级评分
    // 调用renderHotelRating函数，渲染酒店星级评分显示
    renderHotelRating();

    // 设置默认日期（今天和明天）
    // 创建今天的日期对象
    const today = new Date();
    // 创建明天的日期对象，基于今天日期加1天
    const tomorrow = new Date(today);
    // 设置明天的日期为今天加1天
    tomorrow.setDate(tomorrow.getDate() + 1);

    // 设置入住日期输入框的值为今天的日期
    $('#checkInDate').val(formatDate(today));
    // 设置离店日期输入框的值为明天的日期
    $('#checkOutDate').val(formatDate(tomorrow));

    // 设置日期选择器的最小值
    // 设置入住日期选择器的最小值为今天，防止选择过去的日期
    $('#checkInDate').attr('min', formatDate(today));
    // 设置离店日期选择器的最小值为明天，防止选择今天或过去的日期
    $('#checkOutDate').attr('min', formatDate(tomorrow));

    /**
     * 监听入住日期变化事件
     * 功能概述：当用户选择入住日期时，动态更新离店日期的最小值，确保离店日期不早于入住日期+1天
     */
    // 监听入住日期变化，动态更新离店日期最小值
    $('#checkInDate').on('change', function() {
        // 获取用户选择的入住日期，转换为Date对象
        const checkInDate = new Date($(this).val());
        // 创建入住日期后一天的日期对象
        const nextDay = new Date(checkInDate);
        // 设置离店日期最小值为入住日期加1天
        nextDay.setDate(nextDay.getDate() + 1);

        // 更新离店日期最小值
        // 设置离店日期选择器的最小值为入住日期加1天
        $('#checkOutDate').attr('min', formatDate(nextDay));

        // 如果当前离店日期早于新的入住日期+1天，则更新离店日期
        // 获取当前选择的离店日期，转换为Date对象
        const currentCheckOut = new Date($('#checkOutDate').val());
        // 判断当前离店日期是否早于或等于入住日期
        if (currentCheckOut <= checkInDate) {
            // 如果离店日期早于或等于入住日期，自动设置为入住日期加1天
            $('#checkOutDate').val(formatDate(nextDay));
        }

        // 添加日期选择的视觉反馈
        // 为入住日期输入框添加边框高亮样式
        $(this).addClass('border-primary');
        // 500毫秒后移除边框高亮样式
        setTimeout(() => {
            // 移除边框高亮样式
            $(this).removeClass('border-primary');
        }, 500);

        // 更新预订摘要
        // 调用updateBookingSummary函数，更新预订摘要信息
        updateBookingSummary();
    });

    /**
     * 监听离店日期变化事件
     * 功能概述：当用户选择离店日期时，添加视觉反馈并更新预订摘要
     */
    // 添加离店日期变化的视觉反馈
    $('#checkOutDate').on('change', function() {
        // 为离店日期输入框添加边框高亮样式
        $(this).addClass('border-primary');
        // 500毫秒后移除边框高亮样式
        setTimeout(() => {
            // 移除边框高亮样式
            $(this).removeClass('border-primary');
        }, 500);
        // 调用updateBookingSummary函数，更新预订摘要信息
        updateBookingSummary();
    });

    // 初始化最低价格显示
    // 调用updateMinPrice函数，初始化并显示最低价格
    updateMinPrice();

    // 更新预订摘要
    // 调用updateBookingSummary函数，更新预订摘要信息
    updateBookingSummary();

    // 监听日期变化，更新预订摘要
    // 为入住日期和离店日期输入框绑定change事件，当日期变化时更新预订摘要
    $('#checkInDate, #checkOutDate').on('change', updateBookingSummary);

    /**
     * 监听房型选择变化事件
     * 功能概述：当用户选择房型时，更新预订表单中的隐藏字段、预订摘要、最低价格显示和房型选中状态
     */
    // 监听房型选择变化，更新预订摘要
    $('input[name="roomType"]').on('change', function() {
        // 获取当前选中的房型单选按钮元素
        const selectedRoom = $(this);
        // 判断房型是否被选中
        if (selectedRoom.is(':checked')) {
            // 获取选中的房型ID
            const roomId = selectedRoom.val();
            // 获取选中的房型名称，从最近的.room-item元素中查找.room-name元素并获取文本
            const roomName = selectedRoom.closest('.room-item').find('.room-name').text();
            // 获取选中的房型价格，从最近的.room-item元素中查找.room-price span元素并获取文本
            const roomPrice = selectedRoom.closest('.room-item').find('.room-price span').text();

            // 打印选中房型信息到控制台，用于调试
            console.log('选中房型信息:', {
                roomId: roomId,         // 房型ID
                roomName: roomName,     // 房型名称
                roomPriceRaw: roomPrice // 房型价格（原始字符串）
            });

            // 处理价格字符串，移除非数字字符
            // 使用正则表达式移除非数字和小数点的字符，保留数字和小数点
            const cleanPrice = roomPrice.replace(/[^\d.]/g, '');

            // 打印处理后的价格到控制台，用于调试
            console.log('处理后的价格:', cleanPrice);

            // 更新预订表单中的隐藏字段
            // 设置预订表单中的房型ID隐藏字段
            $('#bookRoomId').val(roomId);
            // 设置预订表单中的房型名称隐藏字段
            $('#bookRoomName').val(roomName);
            // 设置预订表单中的房型价格隐藏字段
            $('#bookRoomPrice').val(cleanPrice);

            // 更新预订摘要
            // 更新预订摘要中的房型名称显示
            $('#summaryRoomName').text(roomName);
            // 更新预订摘要中的价格显示，格式为"¥价格/晚"
            $('#summaryPrice').text(`¥${cleanPrice}/晚`);

            // 更新最低价格显示
            // 调用updateMinPrice函数，更新最低价格显示
            updateMinPrice();

            // 更新总价
            // 调用updateBookingSummary函数，更新预订摘要总价
            updateBookingSummary();

            // 更新房型选中状态
            // 移除所有房型项的选中样式
            $('.room-item').removeClass('selected');
            // 为当前选中的房型项添加选中样式
            selectedRoom.closest('.room-item').addClass('selected');
        }
    });

    /**
     * 默认选择第一个房型
     * 功能概述：延迟执行，确保DOM完全渲染后，自动选择第一个房型并触发change事件
     */
    // 默认选择第一个房型
    // 使用setTimeout延迟执行，确保DOM完全渲染后再执行
    setTimeout(function() {
        // 打印延迟初始化日志到控制台
        console.log('延迟初始化：检查房型元素');
        // 判断页面上是否存在房型单选按钮
        if ($('input[name="roomType"]').length > 0) {
            // 打印找到房型的日志到控制台
            console.log('找到房型，选择第一个');
            // 设置第一个房型单选按钮为选中状态
            $('input[name="roomType"]:first').prop('checked', true);
            // 触发第一个房型单选按钮的change事件，更新预订摘要
            $('input[name="roomType"]:first').trigger('change');
        // 如果页面上不存在房型单选按钮
        } else {
            // 打印警告日志到控制台，提示页面可能还未完全加载
            console.warn('未找到房型单选按钮，页面可能还未完全加载');
            // 尝试手动更新最低价格
            // 调用updateMinPrice函数，手动更新最低价格显示
            updateMinPrice();
        }
        
        // 确保按钮事件绑定成功
        // 打印立即预订按钮事件绑定状态到控制台，判断按钮是否存在
        console.log('立即预订按钮事件绑定状态:', $('#bookNowBtn').length > 0 ? '成功' : '失败');
    }, 100); // 延迟100ms确保DOM完全渲染

    /**
     * 点击整个房型项选择房型
     * 功能概述：当用户点击房型项时，自动选中该房型的单选按钮并触发change事件
     */
    // 点击整个房型项选择
    // 为所有房型项绑定click事件
    $('.room-item').on('click', function(e) {
        // 排除直接点击单选按钮的情况
        // 判断点击的目标元素是否是单选按钮、label标签或label标签的子元素
        if (!$(e.target).is('input[type="radio"]') &&
            !$(e.target).is('label') &&
            !$(e.target).closest('label').length) {
            // 获取当前房型项中的单选按钮元素
            const radio = $(this).find('input[type="radio"]');
            // 设置单选按钮为选中状态，并触发change事件
            radio.prop('checked', true).trigger('change');
        }
    });

    /**
     * 登录按钮事件
     * 功能概述：当用户点击登录按钮时，阻止默认行为（可以在这里调用登录函数）
     */
    // 登录按钮事件 - 模拟登录流程
    // 为登录按钮绑定click事件
    $('.login-btn').on('click', function(e) {
        // 阻止默认行为（如跳转等）
        e.preventDefault();
        // 可以在这里调用simulateLogin函数进行模拟登录（当前已注释）
        // simulateLogin();
    });

    /**
     * 预订按钮事件
     * 功能概述：当用户点击立即预订按钮时，检查房型选择、酒店ID和登录状态，然后显示预订弹窗
     */
    // 预订按钮事件
    // 为立即预订按钮绑定click事件
    $('#bookNowBtn').on('click', function() {
        // 检查是否选择了房型
        // 判断页面上是否存在选中的房型单选按钮
        if (!$('input[name="roomType"]:checked').length) {
            // 如果没有选择房型，显示提示信息
            alert('请先选择房型');
            // 返回，不继续执行
            return;
        }

        // 检查hotelId是否存在
        // 判断酒店ID是否存在
        if (!hotelId) {
            // 如果酒店ID不存在，显示提示信息
            alert('获取酒店信息失败，请刷新页面');
            // 返回，不继续执行
            return;
        }

        // 检查登录状态
        // 判断用户是否已登录（loginFlag为0表示未登录）
        if (loginFlag === 0) {
            // 如果用户未登录，显示提示信息
            alert('请先登录后再预订');
            // 可以在这里调用simulateLogin函数引导用户登录（当前已注释）
            // simulateLogin();
            // 返回，不继续执行
            return;
        }

        // 显示预订弹窗
        // 显示预订弹窗，让用户填写预订信息
        $('#bookingModal').show();
    });

    /**
     * 关闭弹窗按钮事件
     * 功能概述：当用户点击关闭按钮时，隐藏预订弹窗
     */
    // 关闭弹窗按钮事件
    // 为关闭按钮绑定click事件
    $('#closeModal').on('click', function() {
        // 隐藏预订弹窗
        $('#bookingModal').hide();
    });

    /**
     * 点击弹窗外部关闭弹窗
     * 功能概述：当用户点击弹窗外部区域时，隐藏预订弹窗
     */
    // 点击弹窗外部关闭弹窗
    // 为预订弹窗绑定click事件
    $('#bookingModal').on('click', function(e) {
        // 判断点击的目标元素是否是弹窗本身（不是弹窗内部的子元素）
        if (e.target === this) {
            // 如果点击的是弹窗外部，隐藏弹窗
            $(this).hide();
        }
    });

    /**
     * 预订表单提交事件
     * 功能概述：当用户提交预订表单时，验证表单数据、收集表单信息、使用AJAX向后端提交预订请求、处理响应结果
     */
    // 预订表单提交事件
    // 为预订表单绑定submit事件
    $('#bookingForm').on('submit', function(e) {
        // 阻止表单默认提交行为（页面跳转）
        e.preventDefault();
        // 打印表单提交事件触发日志到控制台
        console.log('📝 表单提交事件触发');

        // 表单验证
        // 打印开始表单验证日志到控制台
        console.log('🔍 开始表单验证...');
        // 调用validateForm函数验证表单数据
        if (!validateForm()) {
            // 打印表单验证失败日志到控制台
            console.error('❌ 表单验证失败');
            // 返回false，阻止表单提交
            return false;
        }
        // 打印表单验证通过日志到控制台
        console.log('✅ 表单验证通过');

        // 检查hotelId是否存在
        // 判断酒店ID是否存在
        if (!hotelId) {
            // 打印酒店ID不存在错误日志到控制台
            console.error('❌ hotelId 不存在:', hotelId);
            // 显示提示信息
            alert('获取酒店信息失败，请刷新页面');
            // 返回false，阻止表单提交
            return false;
        }
        // 打印酒店ID存在日志到控制台
        console.log('✅ hotelId 存在:', hotelId);

        // 检查登录状态
        // 判断用户是否已登录（loginFlag为0表示未登录）
        if (loginFlag === 0) {
            // 打印用户未登录错误日志到控制台
            console.error('❌ 用户未登录');
            // 显示提示信息
            alert('请先登录后再提交预订');
            // 返回false，阻止表单提交
            return false;
        }
        // 打印用户已登录日志到控制台
        console.log('✅ 用户已登录:', userInfo);

        // 检查房型是否选择
        // 获取选中的房型ID
        const selectedRoomId = $('#bookRoomId').val();
        // 获取选中的房型名称
        const selectedRoomName = $('#bookRoomName').val();
        // 判断房型ID和房型名称是否存在
        if (!selectedRoomId || !selectedRoomName) {
            // 打印房型未选择错误日志到控制台
            console.error('❌ 房型未选择');
            // 显示提示信息
            alert('请先选择房型');
            // 返回false，阻止表单提交
            return false;
        }
        // 打印房型已选择日志到控制台
        console.log('✅ 房型已选择:', selectedRoomName);

        // 收集表单数据
        // 创建表单数据对象，包含所有预订信息
        const formData = {
            hid: parseInt(hotelId, 10),                                    // 酒店ID，转换为整数
            rid: parseInt($('#bookRoomId').val(), 10),                      // 房型ID，转换为整数
            uid: parseInt(userInfo.id, 10),                                 // 用户ID，转换为整数
            rname: $('#bookRoomName').val(),                                // 房型名称（使用rname字段）
            name: $('#contactName').val(),                                  // 联系人姓名（使用name字段）
            indate: $('#checkInDate').val(),                                // 入住日期
            outdate: $('#checkOutDate').val(),                              // 退房日期
            guests: parseInt($('#guests').val(), 10),                        // 入住人数，转换为整数
            tel: $('#contactPhone').val(),                                  // 联系电话
            notes: $('#notes').val() || '',                                 // 订单备注，如果为空则使用空字符串
            price: parseFloat($('#bookTotalPrice').val())                   // 订单总价，转换为浮点数
        };

        // 打印预订信息到控制台，用于调试
        console.log('📦 预订信息:', formData);
        // 打印准备发送AJAX请求日志到控制台
        console.log('🚀 准备发送AJAX请求...');
        
        // 使用try-catch捕获异常
        try {
            // 打印请求URL到控制台
            console.log('🚀 请求URL:', '/hc/bookings');
            // 打印请求数据到控制台（JSON字符串格式）
            console.log('🚀 请求数据:', JSON.stringify(formData));
            // 打印JSON.stringify执行成功日志到控制台
            console.log('🚀 JSON.stringify 执行成功');

            // 使用AJAX向后端提交数据
            // 打印准备调用$.ajax日志到控制台
            console.log('🚀 准备调用 $.ajax...');
            // 使用jQuery的ajax方法发送POST请求
            $.ajax({
            url: '/hc/bookings',                    // 请求URL，后端预订接口
            type: 'POST',                           // 请求类型，POST方法
            contentType: 'application/json',        // 请求内容类型，JSON格式
            dataType: 'json',                       // 响应数据类型，明确指定返回JSON格式
            data: JSON.stringify(formData),         // 请求数据，将表单数据对象转换为JSON字符串
            beforeSend: function(xhr) {
                // AJAX请求发送前的回调函数
                // 打印AJAX请求发送前日志到控制台
                console.log('📤 AJAX请求发送前...');
            },
            success: function(response, textStatus, xhr) {
                // AJAX请求成功时的回调函数
                // 打印AJAX请求成功日志到控制台
                console.log('✅ AJAX请求成功!');
                // 打印HTTP状态码到控制台
                console.log('📨 HTTP状态码:', xhr.status);
                // 打印响应状态到控制台
                console.log('📨 响应状态:', textStatus);
                // 打印预订提交响应到控制台
                console.log('📨 预订提交响应:', response);
                // 打印响应类型到控制台
                console.log('📨 响应类型:', typeof response);
                // 打印响应内容到控制台（JSON字符串格式）
                console.log('📨 响应内容:', JSON.stringify(response));
                
                // 检查响应是否为对象
                // 判断响应是否为对象类型且不为null
                if (typeof response !== 'object' || response === null) {
                    // 打印响应格式错误日志到控制台
                    console.error('❌ 响应格式错误，不是JSON对象');
                    // 显示错误提示信息
                    alert('预订提交失败：服务器返回格式错误');
                    // 返回，不继续执行
                    return;
                }
                
                // 判断响应码是否为200（成功）
                if (response.code === 200) {
                    // 打印预订提交成功日志到控制台
                    console.log('✅ 预订提交成功!');
                    // 打印订单号到控制台（优先使用orderNo，否则使用data）
                    console.log('📋 订单号 (order_no):', response.orderNo || response.data);
                    // 打印统一订单号到控制台
                    console.log('🔗 统一订单号:', response.unifiedOrderNo);
                    // 打印跳转链接到控制台
                    console.log('🌐 跳转链接:', response.redirectUrl);
                    
                    // 获取订单号（优先使用 orderNo，否则使用 data）
                    // 优先使用response.orderNo，如果不存在则使用response.data
                    const orderNo = response.orderNo || response.data;
                    
                    // 显示订单号提示
                    // 显示预订成功提示，包含订单号
                    alert('预订成功！订单号：' + orderNo);
                    
                    // 关闭预订弹窗
                    // 隐藏预订弹窗
                    $('#bookingModal').hide();
                    // 打印预订弹窗已关闭日志到控制台
                    console.log('✅ 预订弹窗已关闭');
                    
                    // 优先使用 redirectUrl 跳转到支付页面
                    // 判断响应中是否存在redirectUrl
                    if (response.redirectUrl) {
                        // 打印正在跳转到支付页面日志到控制台
                        console.log('🚀 正在跳转到支付页面:', response.redirectUrl);
                        // 跳转到统一支付页面
                        window.location.href = response.redirectUrl;
                    // 如果没有redirectUrl但有unifiedOrderNo
                    } else if (response.unifiedOrderNo) {
                        // 如果没有 redirectUrl 但有 unifiedOrderNo，手动构建跳转链接
                        // 手动构建跳转链接，使用统一订单号
                        const redirectUrl = '/unified-new/payment/checkout?orderNo=' + encodeURIComponent(response.unifiedOrderNo);
                        // 打印手动构建跳转链接日志到控制台
                        console.log('🔧 手动构建跳转链接:', redirectUrl);
                        // 跳转到手动构建的支付页面
                        window.location.href = redirectUrl;
                    // 如果既没有redirectUrl也没有unifiedOrderNo
                    } else {
                        // 否则显示成功消息并询问是否查看订单管理
                        // 打印没有跳转链接警告日志到控制台
                        console.warn('⚠️ 没有跳转链接，显示确认对话框');
                        // 显示确认对话框，询问是否查看订单管理页面
                        if (confirm('是否立即查看订单管理页面？')) {
                            // 如果用户确认，跳转到订单管理页面
                            window.location.href = '/unified-new/orders';
                        } else {
                            // 如果用户取消，显示提示信息
                            alert('您可以随时在顶部导航栏中查看订单管理！');
                        }
                    }
                // 如果响应码不是200（失败）
                } else {
                    // 打印预订失败日志到控制台
                    console.error('❌ 预订失败，响应码:', response.code);
                    // 打印错误信息到控制台
                    console.error('❌ 错误信息:', response.message);
                    // 显示错误提示信息（优先使用response.message，否则使用默认提示）
                    alert(response.message || '预订失败，请稍后再试');
                }
            },
            error: function(xhr, status, error) {
                // AJAX请求失败时的回调函数
                // 打印AJAX请求失败日志到控制台
                console.error('❌ AJAX请求失败!');
                // 打印错误类型到控制台
                console.error('❌ 错误类型:', error);
                // 打印状态到控制台
                console.error('❌ 状态:', status);
                // 打印HTTP状态码到控制台
                console.error('❌ HTTP状态码:', xhr.status);
                // 打印响应内容到控制台
                console.error('❌ 响应内容:', xhr.responseText);
                // 打印响应内容长度到控制台
                console.error('❌ 响应内容长度:', xhr.responseText ? xhr.responseText.length : 0);
                // 打印响应头到控制台
                console.error('❌ 响应头:', xhr.getAllResponseHeaders());
                
                // 检查是否是HTML响应（可能是登录页面或错误页面）
                // 判断响应内容是否是HTML页面（以<!DOCTYPE或<html开头）
                if (xhr.responseText && xhr.responseText.trim().startsWith('<!DOCTYPE') || xhr.responseText.trim().startsWith('<html')) {
                    // 打印服务器返回HTML页面错误日志到控制台
                    console.error('❌ 服务器返回了HTML页面，可能是未登录或错误页面');
                    // 显示错误提示信息
                    alert('预订提交失败：请先登录或刷新页面重试');
                // 如果HTTP状态码是401或403（未授权或禁止访问）
                } else if (xhr.status === 401 || xhr.status === 403) {
                    // 显示错误提示信息
                    alert('预订提交失败：请先登录');
                    // 跳转到登录页面，并携带当前页面路径作为重定向参数
                    window.location.href = '/user/login?redirect=' + encodeURIComponent(window.location.pathname);
                // 如果HTTP状态码是0（网络连接错误）
                } else if (xhr.status === 0) {
                    // 显示网络连接错误提示信息
                    alert('预订提交失败：网络连接错误，请检查网络连接');
                // 其他错误情况
                } else {
                    // 尝试解析错误响应
                    // 使用try-catch尝试解析错误响应
                    try {
                        // 将响应内容解析为JSON对象
                        const errorResponse = JSON.parse(xhr.responseText);
                        // 显示错误提示信息（优先使用errorResponse.message，其次使用errorResponse.error，否则使用默认提示）
                        alert('预订提交失败：' + (errorResponse.message || errorResponse.error || '未知错误'));
                    // 如果解析失败
                    } catch (e) {
                        // 显示通用错误提示信息，包含HTTP状态码
                        alert('预订提交失败，请稍后再试（HTTP ' + xhr.status + '）');
                    }
                }
            },
            complete: function(xhr, textStatus) {
                // AJAX请求完成时的回调函数（无论成功或失败都会执行）
                // 打印AJAX请求完成日志到控制台
                console.log('🏁 AJAX请求完成');
                // 打印完成状态到控制台
                console.log('🏁 完成状态:', textStatus);
                // 打印HTTP状态码到控制台
                console.log('🏁 HTTP状态码:', xhr.status);
            }
            });
            // 打印$.ajax调用完成日志到控制台
            console.log('🚀 $.ajax 调用完成');
        // 捕获所有异常
        } catch (error) {
            // 打印执行AJAX请求时发生错误日志到控制台
            console.error('❌ 执行AJAX请求时发生错误:', error);
            // 打印错误堆栈到控制台
            console.error('❌ 错误堆栈:', error.stack);
            // 显示错误提示信息，包含错误消息
            alert('预订提交失败：' + error.message);
        }
    });

    // 页面加载时检查日期有效性
    // 判断入住日期是否晚于或等于离店日期
    if (new Date($('#checkInDate').val()) >= new Date($('#checkOutDate').val())) {
        // 如果日期设置错误，显示提示信息
        alert('默认日期设置错误，请重新选择入住和离店日期');
    }
    
    // 初始化随机图片
    // 调用initializeRandomImages函数，初始化随机图片显示
    initializeRandomImages();
    
    // 生成随机评论
    // 调用generateRandomReviews函数，生成随机评论显示
    generateRandomReviews();
    
    // 打印酒店详情页面初始化完成日志到控制台
    console.log('✅ 酒店详情页面初始化完成！');
    // 打印用户登录状态到控制台
    console.log('- 用户登录状态:', loginFlag === 1 ? '已登录' : '未登录');
    // 打印酒店ID到控制台
    console.log('- 酒店ID:', hotelId);
    // 打印房型数量到控制台
    console.log('- 房型数量:', $('input[name="roomType"]').length);
    // 打印所有事件绑定完成日志到控制台
    console.log('- 所有事件绑定完成');
});

/**
 * 初始化酒店ID
 * 功能概述：从隐藏字段、URL参数或预设值中获取酒店ID，并确保其为数字类型
 */
// 初始化酒店ID函数，从多个来源获取酒店ID
function initializeHotelId() {
    // 使用try-catch捕获异常
    try {
        // 方法1: 从隐藏字段获取（由后端Thymeleaf设置）
        // 从隐藏字段#bookHotelId中获取酒店ID
        hotelId = $('#bookHotelId').val();
        
        // 方法2: 若隐藏字段中没有，从URL参数获取
        // 判断酒店ID是否存在
        if (!hotelId) {
            // 如果隐藏字段中没有，从URL参数中获取id参数
            hotelId = getUrlParameter('id');
        }

        // 方法3: 若URL中也没有，使用预设值（开发环境使用）
        // 判断酒店ID是否仍然不存在
        if (!hotelId) {
            // 如果URL中也没有，使用预设值1（开发环境使用，实际项目中应移除）
            hotelId = 1;
            // 打印使用预设酒店ID日志到控制台
            console.log('使用预设酒店ID:', hotelId);
            // 将预设酒店ID设置到隐藏字段中
            $('#bookHotelId').val(hotelId);
        }

        // 确保 hotelId 是数字类型
        // 将酒店ID转换为整数类型（十进制）
        hotelId = parseInt(hotelId, 10);
        
        // 打印酒店ID已初始化日志到控制台
        console.log('酒店ID已初始化:', hotelId);
    // 捕获所有异常
    } catch (error) {
        // 打印初始化酒店ID失败错误日志到控制台
        console.error('初始化酒店ID失败:', error);
    }
}

/**
 * 从URL获取参数
 * 功能概述：从当前页面的URL查询字符串中获取指定参数的值
 * @param {string} name - 参数名称
 * @return {string} 参数值，如果不存在则返回空字符串
 */
// 从URL获取参数函数，接收参数名称，返回参数值
function getUrlParameter(name) {
    // 转义参数名称中的方括号，用于正则表达式匹配
    name = name.replace(/[\[]/, '\\[').replace(/[\]]/, '\\]');
    // 创建正则表达式，匹配URL查询字符串中的参数
    const regex = new RegExp('[\\?&]' + name + '=([^&#]*)');
    // 执行正则表达式匹配，获取参数值
    const results = regex.exec(location.search);
    // 如果匹配结果为空，返回空字符串；否则返回解码后的参数值（将+替换为空格）
    return results === null ? '' : decodeURIComponent(results[1].replace(/\+/g, ' '));
}

/**
 * 从本地存储加载用户信息
 * 功能概述：从浏览器的localStorage中获取用户ID和用户名，更新全局变量和登录状态
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
            // 如果存在，更新全局用户信息对象
            userInfo.id = storedId;
            userInfo.name = storedName;
            // 设置登录状态为已登录（1）
            loginFlag = 1;
            // 打印用户信息已加载日志到控制台
            console.log('用户信息已加载:', userInfo);
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
 * 根据用户信息更新头部显示
 * 功能概述：根据用户登录状态，更新页面头部的显示内容（显示用户名或登录按钮）
 */
// 根据用户信息更新头部显示函数，更新页面头部用户信息显示
function updateHeaderWithUserInfo() {
    // 获取页面头部内容元素
    const header = $('.header-content');
    // 获取登录按钮元素
    const loginBtn = $('.login-btn');

    // 判断用户是否已登录且用户名存在
    if (loginFlag === 1 && userInfo.name) {
        // 已登录，显示用户名
        // 隐藏登录按钮
        loginBtn.hide();
        // 创建用户名链接元素
        const userNameLink = $('<a>')
            .attr('href', '#')                                    // 设置链接地址为#
            .addClass('user-profile')                             // 添加user-profile样式类
            .html(`<i class="fa fa-user-circle"></i> ${userInfo.name}`)  // 设置链接内容，包含用户图标和用户名
            .click(function(e) {
                // 为用户名链接绑定click事件
                // 阻止默认行为（跳转）
                e.preventDefault();
                // 调用showUserProfile函数显示用户个人中心
                showUserProfile();
            });
        // 将用户名链接添加到页面头部
        header.append(userNameLink);
    // 如果用户未登录
    } else {
        // 未登录，显示登录按钮
        // 显示登录按钮
        loginBtn.show();
    }
}

/**
 * 模拟登录流程
 * 功能概述：通过prompt获取用户名，生成随机用户ID，保存到localStorage，更新全局变量和UI
 */
// 模拟登录流程函数，模拟用户登录过程
function simulateLogin() {
    // 使用prompt获取用户输入的用户名
    const username = prompt('请输入用户名:');
    // 判断用户名是否存在
    if (!username) {
        // 如果用户名为空，返回，不继续执行
        return;
    }

    // 生成随机用户ID
    // 生成0到999999之间的随机整数作为用户ID
    const userId = Math.floor(Math.random() * 1000000);

    // 保存到本地存储
    // 将用户ID保存到localStorage
    localStorage.setItem('userId', userId);
    // 将用户名保存到localStorage
    localStorage.setItem('userName', username);

    // 更新全局变量
    // 更新全局用户信息对象的ID
    userInfo.id = userId;
    // 更新全局用户信息对象的名称
    userInfo.name = username;
    // 设置登录状态为已登录（1）
    loginFlag = 1;

    // 更新UI
    // 调用updateHeaderWithUserInfo函数，更新页面头部显示
    updateHeaderWithUserInfo();

    // 显示欢迎消息
    alert(`欢迎回来，${username}！`);
}

/**
 * 显示用户个人中心
 * 功能概述：检查用户登录状态，如果已登录则显示用户信息，否则提示用户登录
 */
// 显示用户个人中心函数，显示用户个人信息
function showUserProfile() {
    // 判断用户是否未登录（loginFlag为0表示未登录）
    if (loginFlag === 0) {
        // 如果未登录，显示提示信息
        alert('请先登录');
        // 返回，不继续执行
        return;
    }

    // 显示用户信息，包含用户ID、姓名和酒店ID
    alert(`用户信息:\nID: ${userInfo.id}\n姓名: ${userInfo.name}\n酒店ID: ${hotelId}`);
}

/**
 * 渲染酒店星级评分
 * 功能概述：从页面元素中获取酒店评分，计算完整星、半星和空星的数量，渲染星级评分显示
 */
// 渲染酒店星级评分函数，渲染酒店星级评分显示
function renderHotelRating() {
    // 获取酒店评分元素
    const hotelScoreElement = $('#hotelRating');
    // 获取酒店评分的原始文本，去除首尾空白字符
    const rawScore = hotelScoreElement.text().trim();
    // 将评分文本转换为浮点数
    const score = parseFloat(rawScore);

    // 打印评分渲染日志到控制台，包含原始评分和转换后的评分
    console.log('【评分渲染】获取到的酒店评分:', rawScore, '转换后:', score);

    // 清空原有内容
    // 清空酒店评分元素中的原有内容
    hotelScoreElement.empty();

    // 处理未获取到评分的情况
    // 判断评分是否为NaN、null或undefined
    if (isNaN(score) || score === null || score === undefined) {
        // 如果评分无效，显示5个空星和"暂无评分"文本
        hotelScoreElement.html(`
            <i class="fa fa-star-o"></i>
            <i class="fa fa-star-o"></i>
            <i class="fa fa-star-o"></i>
            <i class="fa fa-star-o"></i>
            <i class="fa fa-star-o"></i>
            <span class="ml-1">暂无评分</span>
        `);
        // 返回，不继续执行
        return;
    }

    // 计算完整星、半星和空星的数量
    // 计算完整星的数量（向下取整）
    const fullStars = Math.floor(score);
    // 判断是否有半星（小数部分大于等于0.5）
    const halfStar = score % 1 >= 0.5;
    // 计算空星的数量（总数5减去完整星和半星）
    const emptyStars = 5 - fullStars - (halfStar ? 1 : 0);

    // 添加星星图标
    // 初始化星星HTML字符串
    let starsHtml = '';
    // 循环添加完整星图标
    for (let i = 0; i < fullStars; i++) {
        // 添加完整星图标
        starsHtml += '<i class="fa fa-star"></i>';
    }
    // 如果有半星，添加半星图标
    if (halfStar) {
        // 添加半星图标
        starsHtml += '<i class="fa fa-star-half-o"></i>';
    }
    // 循环添加空星图标
    for (let i = 0; i < emptyStars; i++) {
        // 添加空星图标
        starsHtml += '<i class="fa fa-star-o"></i>';
    }

    // 组合评分显示
    // 将星星图标和评分数字组合，设置到酒店评分元素中
    hotelScoreElement.html(`${starsHtml} <span class="ml-1">${score.toFixed(1)}</span>`);
}

/**
 * 格式化日期为YYYY-MM-DD
 * 功能概述：将Date对象格式化为"YYYY-MM-DD"格式的字符串
 * @param {Date} date - 日期对象
 * @return {string} 格式化后的日期字符串（YYYY-MM-DD格式）
 */
// 格式化日期为YYYY-MM-DD函数，接收日期对象，返回格式化后的日期字符串
function formatDate(date) {
    // 获取年份（4位数字）
    const year = date.getFullYear();
    // 获取月份（0-11），加1后转换为字符串，不足2位前面补0
    const month = String(date.getMonth() + 1).padStart(2, '0');
    // 获取日期（1-31），转换为字符串，不足2位前面补0
    const day = String(date.getDate()).padStart(2, '0');
    // 返回格式化后的日期字符串（YYYY-MM-DD格式）
    return `${year}-${month}-${day}`;
}

/**
 * 更新最低价格显示
 * 功能概述：遍历所有房型价格，找出最低价格并更新页面显示
 */
// 更新最低价格显示函数，更新页面最低价格显示
function updateMinPrice() {
    // 初始化最低价格为无穷大
    let minPrice = Infinity;

    // 修正选择器，匹配HTML中的实际结构：<div class="room-price">¥<span th:text="${ml.price}">788</span>/晚</div>
    // 遍历所有房型价格元素
    $('.room-price span').each(function() {
        // 获取房型价格的文本内容
        const priceStr = $(this).text();
        // 移除非数字和小数点的字符，保留数字和小数点
        const cleanPrice = priceStr.replace(/[^\d.]/g, '');
        // 将清理后的价格字符串转换为浮点数
        const price = parseFloat(cleanPrice);

        // 打印价格处理日志到控制台，用于调试
        console.log('价格处理:', priceStr, '->', cleanPrice, '->', price);

        // 判断价格是否有效且小于当前最低价格
        if (!isNaN(price) && price > 0 && price < minPrice) {
            // 如果价格有效且小于当前最低价格，更新最低价格
            minPrice = price;
        }
    });

    // 打印计算出的最低价格到控制台
    console.log('计算出的最低价格:', minPrice);

    // 判断最低价格是否有效（不是无穷大）
    if (minPrice !== Infinity) {
        // 如果最低价格有效，更新页面显示的最低价格
        $('#minPrice').text(minPrice);
        // 打印已更新页面显示的最低价格日志到控制台
        console.log('已更新页面显示的最低价格:', minPrice);
    // 如果最低价格无效（未找到有效价格）
    } else {
        // 打印未找到有效的房型价格警告日志到控制台
        console.warn('未找到有效的房型价格');
        // 如果没有找到价格，尝试从第一个房型获取
        // 获取第一个房型的价格文本
        const firstRoomPrice = $('.room-item:first .room-price span').text();
        // 判断第一个房型价格是否存在
        if (firstRoomPrice) {
            // 如果存在，移除非数字和小数点的字符，转换为浮点数
            const fallbackPrice = parseFloat(firstRoomPrice.replace(/[^\d.]/g, ''));
            // 判断备用价格是否有效
            if (!isNaN(fallbackPrice) && fallbackPrice > 0) {
                // 如果备用价格有效，更新页面显示的最低价格
                $('#minPrice').text(fallbackPrice);
                // 打印使用第一个房型价格作为备用日志到控制台
                console.log('使用第一个房型价格作为备用:', fallbackPrice);
            }
        }
    }
}

/**
 * 更新预订摘要（包含总价计算）
 * 功能概述：根据入住日期、退房日期和房型价格，计算入住天数和总价，更新预订摘要显示
 */
// 更新预订摘要函数，更新预订摘要信息（包含总价计算）
function updateBookingSummary() {
    // 打印更新预订摘要开始日志到控制台
    console.log('===== 更新预订摘要 =====');

    // 获取日期输入
    // 获取入住日期输入框的值
    const checkInDateVal = $('#checkInDate').val();
    // 获取退房日期输入框的值
    const checkOutDateVal = $('#checkOutDate').val();

    // 打印入住日期和退房日期到控制台，用于调试
    console.log('入住日期:', checkInDateVal);
    console.log('退房日期:', checkOutDateVal);

    // 判断日期是否已选择
    if (!checkInDateVal || !checkOutDateVal) {
        // 如果日期未选择，打印警告日志到控制台
        console.warn('日期未选择，总价计算中止');
        // 设置入住天数为0晚
        $('#summaryDays').text('0晚');
        // 设置总价为¥0
        $('#summaryTotal').text('¥0');
        // 设置总价隐藏字段为0
        $('#bookTotalPrice').val(0);
        // 返回，不继续执行
        return;
    }

    // 创建日期对象
    // 创建入住日期对象
    const checkInDate = new Date(checkInDateVal);
    // 创建退房日期对象
    const checkOutDate = new Date(checkOutDateVal);

    // 设置时间为0点，避免时区和时间差异影响计算
    // 设置入住日期的时间为0点0分0秒0毫秒
    checkInDate.setHours(0, 0, 0, 0);
    // 设置退房日期的时间为0点0分0秒0毫秒
    checkOutDate.setHours(0, 0, 0, 0);

    // 计算入住天数
    // 定义一天的毫秒数（24小时 * 60分钟 * 60秒 * 1000毫秒）
    const oneDay = 24 * 60 * 60 * 1000;
    // 计算退房日期和入住日期的差值（毫秒）
    const diffTime = checkOutDate - checkInDate;
    // 计算入住天数（至少1天），将毫秒差值除以一天的毫秒数，向下取整，然后取最大值1
    const diffDays = Math.max(1, Math.floor(diffTime / oneDay));

    // 打印入住天数计算日志到控制台
    console.log('入住天数计算:', diffDays, '晚');

    // 更新UI显示
    // 更新预订摘要中的入住天数显示
    $('#summaryDays').text(`${diffDays}晚`);

    // 获取房型价格并转换为数字
    // 获取房型价格隐藏字段的值
    const roomPriceStr = $('#bookRoomPrice').val();
    // 打印房型价格字符串到控制台，用于调试
    console.log('房型价格字符串:', roomPriceStr);

    // 初始化房型价格为0
    let roomPrice = 0;

    // 判断房型价格字符串是否存在
    if (roomPriceStr) {
        // 移除价格中的非数字字符，保留小数点
        // 使用正则表达式移除非数字和小数点的字符
        const cleanPrice = roomPriceStr.replace(/[^\d.]/g, '');
        // 打印清理后的价格到控制台，用于调试
        console.log('清理后的价格:', cleanPrice);

        // 将清理后的价格字符串转换为浮点数
        roomPrice = parseFloat(cleanPrice);

        // 检查价格是否有效
        // 判断价格是否为NaN或小于等于0
        if (isNaN(roomPrice) || roomPrice <= 0) {
            // 如果价格无效，设置为0
            roomPrice = 0;
            // 打印无效的房型价格错误日志到控制台
            console.error('无效的房型价格:', roomPriceStr);
        }
    // 如果房型价格字符串不存在
    } else {
        // 打印房型价格为空警告日志到控制台
        console.warn('房型价格为空，使用默认值0');
    }

    // 计算总价
    // 总价 = 房型价格 × 入住天数
    const totalPrice = roomPrice * diffDays;
    // 打印总价计算日志到控制台，用于调试
    console.log('总价计算:', roomPrice, '×', diffDays, '=', totalPrice);

    // 更新UI和隐藏字段
    // 更新预订摘要中的总价显示，格式为"¥总价"（保留2位小数）
    $('#summaryTotal').text(`¥${totalPrice.toFixed(2)}`);
    // 更新总价隐藏字段的值
    $('#bookTotalPrice').val(totalPrice);

    // 打印总价计算完成日志到控制台
    console.log('===== 总价计算完成 =====');
}

/**
 * 表单验证
 * 功能概述：验证预订表单的所有字段，包括日期、房型、联系电话、入住人数、总价、酒店ID等
 * @return {boolean} 验证是否通过（true=通过，false=不通过）
 */
// 表单验证函数，验证预订表单的所有字段
function validateForm() {
    // 初始化验证结果为true（通过）
    let isValid = true;

    // 检查日期
    // 创建入住日期对象
    const checkInDate = new Date($('#checkInDate').val());
    // 创建退房日期对象
    const checkOutDate = new Date($('#checkOutDate').val());

    // 判断入住日期是否晚于或等于退房日期
    if (checkInDate >= checkOutDate) {
        // 如果日期无效，显示提示信息
        alert('离店日期必须晚于入住日期');
        // 设置验证结果为false（不通过）
        isValid = false;
    }

    // 检查是否选择了房型
    // 判断页面上是否存在选中的房型单选按钮
    if (!$('input[name="roomType"]:checked').length) {
        // 如果没有选择房型，显示提示信息
        alert('请选择房型');
        // 设置验证结果为false（不通过）
        isValid = false;
    }

    // 检查联系电话
    // 获取联系电话输入框的值
    const contactPhone = $('#contactPhone').val();
    // 使用正则表达式验证手机号码格式（1开头，第二位是3-9，共11位数字）
    if (!/^1[3-9]\d{9}$/.test(contactPhone)) {
        // 如果手机号码格式无效，显示提示信息
        alert('请输入有效的手机号码');
        // 设置验证结果为false（不通过）
        isValid = false;
    }

    // 检查入住人数
    // 获取入住人数输入框的值，转换为整数
    const guests = parseInt($('#guests').val(), 10);
    // 判断入住人数是否为NaN或小于等于0
    if (isNaN(guests) || guests <= 0) {
        // 如果入住人数无效，显示提示信息
        alert('请选择入住人数');
        // 设置验证结果为false（不通过）
        isValid = false;
    }

    // 检查总价是否有效
    // 获取总价隐藏字段的值，转换为浮点数，如果为空则使用0
    const totalPrice = parseFloat($('#bookTotalPrice').val() || 0);
    // 判断总价是否小于等于0
    if (totalPrice <= 0) {
        // 如果总价无效，显示提示信息
        alert('订单总价异常，请检查房型价格和入住日期是否正确');
        // 设置验证结果为false（不通过）
        isValid = false;
    }

    // 检查hotelId
    // 判断酒店ID是否存在
    if (!hotelId) {
        // 如果酒店ID不存在，显示提示信息
        alert('酒店信息异常，请刷新页面');
        // 设置验证结果为false（不通过）
        isValid = false;
    }

    // 返回验证结果
    return isValid;
}

/**
 * 本地图片池
 * 功能概述：定义房型图片和头像图片的URL数组，用于随机分配图片
 */
// 本地图片池，定义房型图片URL数组
const roomImages = [
    '/img/r1.jpg', '/img/r2.jpg', '/img/r3.jpg', '/img/r4.jpg',
    '/img/r5.jpg', '/img/r6.jpg', '/img/r7.jpg', '/img/r8.jpg',
    '/img/r9.jpg', '/img/r10.jpg', '/img/r11.jpg', '/img/r12.jpg'
];

// 本地图片池，定义头像图片URL数组
const avatarImages = [
    '/img/p1.jpg', '/img/p2.png', '/img/p3.jpg', '/img/p4.jpg', '/img/p5.jpg'
];

/**
 * 基于酒店ID的伪随机数生成器
 * 功能概述：根据种子值生成0-1之间的伪随机数，相同种子值会产生相同的随机数
 * @param {number} seed - 种子值（通常使用酒店ID）
 * @return {number} 0-1之间的伪随机数
 */
// 基于酒店ID的伪随机数生成器函数，接收种子值，返回伪随机数
function seededRandom(seed) {
    // 使用正弦函数生成伪随机数，乘以10000增加随机性
    const x = Math.sin(seed) * 10000;
    // 返回小数部分（0-1之间）
    return x - Math.floor(x);
}

/**
 * 基于酒店ID的固定随机选择函数
 * 功能概述：根据种子值和索引，从图片数组中固定选择一个图片
 * @param {Array<string>} imageArray - 图片URL数组
 * @param {number} seed - 种子值（通常使用酒店ID）
 * @param {number} index - 索引值（默认为0）
 * @return {string} 选中的图片URL
 */
// 基于酒店ID的固定随机选择函数，接收图片数组、种子值和索引，返回选中的图片URL
function getSeededRandomImage(imageArray, seed, index = 0) {
    // 使用种子值和索引生成伪随机数
    const randomValue = seededRandom(seed + index * 1000);
    // 根据伪随机数从图片数组中选择一个图片URL
    return imageArray[Math.floor(randomValue * imageArray.length)];
}

/**
 * 获取酒店内不重复的房型图片数组（基于酒店ID打乱后按顺序分配）
 * 功能概述：根据酒店ID和数量，从房型图片池中打乱并选择不重复的图片数组
 * @param {number} hotelId - 酒店ID
 * @param {number} count - 需要的图片数量
 * @return {Array<string>} 选中的房型图片URL数组
 */
// 获取酒店内不重复的房型图片数组函数，接收酒店ID和数量，返回房型图片URL数组
function getUniqueRoomImages(hotelId, count) {
    // 基于酒店ID打乱房型图片数组
    // 创建房型图片数组的副本（使用展开运算符）
    const shuffledImages = [...roomImages];
    // 使用Fisher-Yates洗牌算法打乱数组（从后往前遍历）
    for (let i = shuffledImages.length - 1; i > 0; i--) {
        // 使用酒店ID和索引生成伪随机数
        const randomValue = seededRandom(hotelId + i * 777);
        // 计算随机索引位置（0到i之间）
        const j = Math.floor(randomValue * (i + 1));
        // 交换当前位置和随机位置的元素
        [shuffledImages[i], shuffledImages[j]] = [shuffledImages[j], shuffledImages[i]];
    }
    
    // 取前count张图片，如果不够就循环使用
    // 初始化选中的图片数组
    const selectedImages = [];
    // 循环选择count张图片
    for (let i = 0; i < count; i++) {
        // 如果图片数量不够，使用取模运算循环使用图片
        selectedImages.push(shuffledImages[i % shuffledImages.length]);
    }
    
    // 返回选中的图片数组
    return selectedImages;
}

/**
 * 获取酒店内不重复的头像图片数组（基于酒店ID打乱后按顺序分配）
 * 功能概述：根据酒店ID和数量，从头像图片池中打乱并选择不重复的图片数组
 * @param {number} hotelId - 酒店ID
 * @param {number} count - 需要的图片数量
 * @return {Array<string>} 选中的头像图片URL数组
 */
// 获取酒店内不重复的头像图片数组函数，接收酒店ID和数量，返回头像图片URL数组
function getUniqueAvatarImages(hotelId, count) {
    // 基于酒店ID打乱头像图片数组
    // 创建头像图片数组的副本（使用展开运算符）
    const shuffledImages = [...avatarImages];
    // 使用Fisher-Yates洗牌算法打乱数组（从后往前遍历）
    for (let i = shuffledImages.length - 1; i > 0; i--) {
        // 使用酒店ID和索引生成伪随机数
        const randomValue = seededRandom(hotelId + i * 555);
        // 计算随机索引位置（0到i之间）
        const j = Math.floor(randomValue * (i + 1));
        // 交换当前位置和随机位置的元素
        [shuffledImages[i], shuffledImages[j]] = [shuffledImages[j], shuffledImages[i]];
    }
    
    // 取前count张图片，如果不够就循环使用
    // 初始化选中的图片数组
    const selectedImages = [];
    // 循环选择count张图片
    for (let i = 0; i < count; i++) {
        // 如果图片数量不够，使用取模运算循环使用图片
        selectedImages.push(shuffledImages[i % shuffledImages.length]);
    }
    
    // 返回选中的图片数组
    return selectedImages;
}

/**
 * 初始化固定的房型图片（基于酒店ID）
 * 功能概述：根据酒店ID，为页面上的房型图片元素设置固定的图片URL
 */
// 初始化固定的房型图片函数，为房型图片元素设置固定的图片URL
function initializeRandomImages() {
    // 打印开始设置房型图片日志到控制台，包含酒店ID
    console.log('🎲 开始设置房型图片（基于酒店ID:' + hotelId + ')...');
    
    // 获取所有房型图片元素
    const roomElements = $('.random-room-image');
    // 获取房型图片元素的数量
    const roomCount = roomElements.length;
    
    // 判断房型图片元素是否存在
    if (roomCount === 0) {
        // 如果不存在，打印警告日志到控制台
        console.warn('未找到房型图片元素');
        // 返回，不继续执行
        return;
    }
    
    // 获取这个酒店ID对应的不重复房型图片
    // 调用getUniqueRoomImages函数，获取该酒店ID对应的房型图片数组
    const selectedRoomImages = getUniqueRoomImages(hotelId, roomCount);
    
    // 遍历所有房型图片元素，为每个元素设置图片URL
    roomElements.each(function(index) {
        // 获取当前房型图片元素（jQuery对象）
        const $img = $(this);
        // 获取对应的房型图片URL（如果索引超出范围，使用取模运算循环使用）
        const roomImage = selectedRoomImages[index] || selectedRoomImages[index % selectedRoomImages.length];
        // 设置图片元素的src属性为对应的图片URL
        $img.attr('src', roomImage);
        // 打印房型图片设置日志到控制台，包含索引和图片URL
        console.log(`房型${index + 1}图片已设置为:`, roomImage);
    });
    
    // 打印房型图片设置完成日志到控制台，包含酒店ID和房型数量
    console.log('✅ 房型图片设置完成！酒店ID:', hotelId, '共', roomCount, '个房型');
}

/**
 * 生成固定的评论（基于酒店ID）
 * 功能概述：根据酒店ID，从预定义的评论数据中固定选择6条评论，并渲染到页面上
 */
// 生成固定的评论函数，根据酒店ID生成固定的评论显示
function generateRandomReviews() {
    // 打印开始生成评论日志到控制台，包含酒店ID
    console.log('💬 开始生成评论（基于酒店ID:' + hotelId + ')...');
    
    // 定义评论数据数组，包含8条预定义的评论
    const reviewData = [
        {
            name: '张先生',        // 评论者姓名
            rating: 5,             // 评分（1-5星）
            date: '2025-06-15',    // 评论日期
            content: '酒店位置很好，服务态度非常好，房间干净整洁，设施齐全，性价比很高，下次还会选择这里。'  // 评论内容
        },
        {
            name: '李女士',        // 评论者姓名
            rating: 4,             // 评分（1-5星）
            date: '2025-06-10',    // 评论日期
            content: '酒店的海景房视野非常好，早餐种类丰富，就是停车场有点小，不过整体还是很满意的。'  // 评论内容
        },
        {
            name: '王先生',        // 评论者姓名
            rating: 5,             // 评分（1-5星）
            date: '2025-06-08',    // 评论日期
            content: '房间设施很新，床很舒服，前台服务很周到，地理位置优越，交通便利，强烈推荐！'  // 评论内容
        },
        {
            name: '陈女士',        // 评论者姓名
            rating: 4,             // 评分（1-5星）
            date: '2025-06-05',    // 评论日期
            content: '酒店环境优雅，服务贴心，房间宽敞明亮，卫生间也很干净，总体体验不错，会再次入住。'  // 评论内容
        },
        {
            name: '刘先生',        // 评论者姓名
            rating: 5,             // 评分（1-5星）
            date: '2025-06-02',    // 评论日期
            content: '这次住宿体验超出预期，酒店装修很有特色，工作人员很专业，房间隔音效果很好。'  // 评论内容
        },
        {
            name: '马女士',        // 评论者姓名
            rating: 4,             // 评分（1-5星）
            date: '2025-05-30',    // 评论日期
            content: '酒店的位置非常便利，周围购物和用餐都很方便，房间布置温馨，Wi-Fi速度也很快。'  // 评论内容
        },
        {
            name: '赵先生',        // 评论者姓名
            rating: 5,             // 评分（1-5星）
            date: '2025-05-28',    // 评论日期
            content: '住了三晚，每天的客房服务都很到位，前台24小时服务很贴心，酒店的健身房设施也不错。'  // 评论内容
        },
        {
            name: '孙女士',        // 评论者姓名
            rating: 4,             // 评分（1-5星）
            date: '2025-05-25',    // 评论日期
            content: '酒店的早餐很丰富，中西式都有，房间的景观很美，特别是晚上的夜景，值得推荐。'  // 评论内容
        }
    ];
    
    // 获取评论容器元素
    const reviewsContainer = $('#reviews');
    // 清空现有内容
    // 清空评论容器中的原有内容
    reviewsContainer.empty();
    
    // 基于酒店ID固定选择6个评论
    // 初始化选中的评论数组
    const selectedReviews = [];
    // 初始化已使用的索引集合（用于避免重复选择）
    const usedIndexes = new Set();
    
    // 循环选择6条评论（或评论数据数组的长度，取较小值）
    for (let i = 0; i < 6 && selectedReviews.length < Math.min(6, reviewData.length); i++) {
        // 使用酒店ID和索引生成伪随机数
        const randomValue = seededRandom(hotelId + i * 333);
        // 根据伪随机数计算评论索引位置
        let index = Math.floor(randomValue * reviewData.length);
        
        // 确保不重复
        // 初始化尝试次数
        let attempts = 0;
        // 如果索引已被使用，循环查找下一个未使用的索引
        while (usedIndexes.has(index) && attempts < reviewData.length) {
            // 索引加1，如果超出范围则循环到开头（使用取模运算）
            index = (index + 1) % reviewData.length;
            // 尝试次数加1
            attempts++;
        }
        
        // 判断索引是否未被使用
        if (!usedIndexes.has(index)) {
            // 将索引添加到已使用集合中
            usedIndexes.add(index);
            // 将对应的评论添加到选中评论数组中
            selectedReviews.push(reviewData[index]);
        }
    }
    
    // 获取这个酒店ID对应的不重复头像
    // 调用getUniqueAvatarImages函数，获取该酒店ID对应的头像图片数组
    const selectedAvatars = getUniqueAvatarImages(hotelId, selectedReviews.length);
    
    // 遍历选中的评论，为每条评论生成HTML并添加到页面
    selectedReviews.forEach((review, index) => {
        // 获取对应的头像图片URL（如果索引超出范围，使用取模运算循环使用）
        const avatar = selectedAvatars[index] || selectedAvatars[index % selectedAvatars.length];
        
        // 生成评论HTML字符串，包含头像、姓名、评分、日期和内容
        const reviewHtml = `
            <div class="review-item">
                <div class="review-header">
                    <img src="${avatar}" alt="用户头像" class="review-avatar" />
                    <span class="review-name">${review.name}</span>
                    <div class="review-rating" data-rating="${review.rating}"></div>
                    <span class="review-date">${review.date}</span>
                </div>
                <p class="review-content">${review.content}</p>
            </div>
        `;
        
        // 将评论HTML添加到评论容器中
        reviewsContainer.append(reviewHtml);
        // 打印评论生成日志到控制台，包含索引、姓名和头像URL
        console.log(`评论${index + 1}已生成: ${review.name} - 头像: ${avatar}`);
    });
    
    // 添加"查看更多评价"按钮
    // 将"查看更多评价"按钮HTML添加到评论容器中
    reviewsContainer.append(`
        <button id="loadMoreReviews" class="btn btn-link text-primary">
            查看更多评价 <i class="fa fa-angle-down ml-1"></i>
        </button>
    `);
    
    // 渲染评分星级
    // 遍历所有评分元素，为每个评分渲染星级图标
    $('.review-rating').each(function() {
        // 获取评分值（从data-rating属性中获取，转换为整数）
        const rating = parseInt($(this).attr('data-rating'));
        // 初始化星星HTML字符串
        let starsHtml = '';
        
        // 循环生成5颗星（1到5）
        for (let i = 1; i <= 5; i++) {
            // 判断当前星数是否小于等于评分值
            if (i <= rating) {
                // 如果小于等于评分值，添加实心星图标
                starsHtml += '<i class="fa fa-star"></i>';
            } else {
                // 如果大于评分值，添加空心星图标
                starsHtml += '<i class="fa fa-star-o"></i>';
            }
        }
        
        // 将星星HTML设置到评分元素中
        $(this).html(starsHtml);
    });
    
    // 绑定"查看更多评价"按钮事件
    // 为"查看更多评价"按钮绑定click事件
    $('#loadMoreReviews').on('click', function() {
        // 将按钮文本改为"已显示全部评价"
        $(this).text('已显示全部评价');
        // 禁用按钮
        $(this).prop('disabled', true);
    });
    
    // 打印评论生成完成日志到控制台，包含酒店ID和评论数量
    console.log('✅ 评论生成完成！酒店ID:', hotelId, '共', selectedReviews.length, '条评论');
}
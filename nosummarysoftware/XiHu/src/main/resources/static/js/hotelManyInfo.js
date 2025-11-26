// 全局变量
let userInfo = {
    id: null,
    name: null
};
let loginFlag = 0; // 0: 未登录, 1: 已登录
let hotelId = null; // 酒店ID，需在页面加载时初始化

$(document).ready(function() {
    // 从本地存储获取用户信息
    loadUserInfo();

    // 初始化酒店ID - 实际项目中应从URL或数据接口获取
    initializeHotelId();

    // 初始化页面元素
    updateHeaderWithUserInfo();

    // 渲染酒店星级评分
    renderHotelRating();

    // 设置默认日期（今天和明天）
    const today = new Date();
    const tomorrow = new Date(today);
    tomorrow.setDate(tomorrow.getDate() + 1);

    $('#checkInDate').val(formatDate(today));
    $('#checkOutDate').val(formatDate(tomorrow));

    // 设置日期选择器的最小值
    $('#checkInDate').attr('min', formatDate(today));
    $('#checkOutDate').attr('min', formatDate(tomorrow));

    // 监听入住日期变化，动态更新离店日期最小值
    $('#checkInDate').on('change', function() {
        const checkInDate = new Date($(this).val());
        const nextDay = new Date(checkInDate);
        nextDay.setDate(nextDay.getDate() + 1);

        // 更新离店日期最小值
        $('#checkOutDate').attr('min', formatDate(nextDay));

        // 如果当前离店日期早于新的入住日期+1天，则更新离店日期
        const currentCheckOut = new Date($('#checkOutDate').val());
        if (currentCheckOut <= checkInDate) {
            $('#checkOutDate').val(formatDate(nextDay));
        }

        // 添加日期选择的视觉反馈
        $(this).addClass('border-primary');
        setTimeout(() => {
            $(this).removeClass('border-primary');
        }, 500);

        // 更新预订摘要
        updateBookingSummary();
    });

    // 添加离店日期变化的视觉反馈
    $('#checkOutDate').on('change', function() {
        $(this).addClass('border-primary');
        setTimeout(() => {
            $(this).removeClass('border-primary');
        }, 500);
        updateBookingSummary();
    });

    // 初始化最低价格显示
    updateMinPrice();

    // 更新预订摘要
    updateBookingSummary();

    // 监听日期变化，更新预订摘要
    $('#checkInDate, #checkOutDate').on('change', updateBookingSummary);

    // 监听房型选择变化，更新预订摘要
    $('input[name="roomType"]').on('change', function() {
        const selectedRoom = $(this);
        if (selectedRoom.is(':checked')) {
            const roomId = selectedRoom.val();
            const roomName = selectedRoom.closest('.room-item').find('.room-name').text();
            const roomPrice = selectedRoom.closest('.room-item').find('.room-price span').text();

            // 处理价格字符串，移除非数字字符
            const cleanPrice = roomPrice.replace(/[^\d.]/g, '');

            // 更新预订表单中的隐藏字段
            $('#bookRoomId').val(roomId);
            $('#bookRoomName').val(roomName);
            $('#bookRoomPrice').val(cleanPrice);

            // 更新预订摘要
            $('#summaryRoomName').text(roomName);
            $('#summaryPrice').text(`¥${cleanPrice}/晚`);

            // 更新最低价格显示
            updateMinPrice();

            // 更新总价
            updateBookingSummary();

            // 更新房型选中状态
            $('.room-item').removeClass('selected');
            selectedRoom.closest('.room-item').addClass('selected');
        }
    });

    // 默认选择第一个房型
    if ($('input[name="roomType"]').length > 0) {
        $('input[name="roomType"]:first').prop('checked', true);
        $('input[name="roomType"]:first').trigger('change');
    }

    // 点击整个房型项选择
    $('.room-item').on('click', function(e) {
        // 排除直接点击单选按钮的情况
        if (!$(e.target).is('input[type="radio"]') &&
            !$(e.target).is('label') &&
            !$(e.target).closest('label').length) {
            const radio = $(this).find('input[type="radio"]');
            radio.prop('checked', true).trigger('change');
        }
    });

    // 登录按钮事件 - 模拟登录流程
    $('.login-btn').on('click', function(e) {
        e.preventDefault();
        // simulateLogin();
    });

    // 预订按钮事件
    $('#bookNowBtn').on('click', function() {
        if (!$('input[name="roomType"]:checked').length) {
            alert('请先选择房型');
            return;
        }

        // 检查hotelId是否存在
        if (!hotelId) {
            alert('获取酒店信息失败，请刷新页面');
            return;
        }

        // 检查登录状态
        if (loginFlag === 0) {
            alert('请先登录后再预订');
            // simulateLogin(); // 引导用户登录
            return;
        }

        // 显示预订弹窗
        $('#bookingModal').show();
    });

    // 关闭弹窗按钮事件
    $('#closeModal').on('click', function() {
        $('#bookingModal').hide();
    });

    // 点击弹窗外部关闭弹窗
    $('#bookingModal').on('click', function(e) {
        if (e.target === this) {
            $(this).hide();
        }
    });

    // 预订表单提交事件
    $('#bookingForm').on('submit', function(e) {
        e.preventDefault();

        // 表单验证
        if (!validateForm()) {
            return;
        }

        // 检查hotelId是否存在
        if (!hotelId) {
            alert('获取酒店信息失败，请刷新页面');
            return;
        }

        // 检查登录状态
        if (loginFlag === 0) {
            alert('请先登录后再提交预订');
            return;
        }

        // 收集表单数据
        const formData = {
            hid: hotelId,
            rid: $('#bookRoomId').val(),
            uid: userInfo.id,
            rname: $('#bookRoomName').val(),
            indate: $('#checkInDate').val(),
            outdate: $('#checkOutDate').val(),
            guests: parseInt($('#guests').val(), 10),
            name: $('#contactName').val(),
            tel: $('#contactPhone').val(),
            notes: $('#notes').val(),
            status: 0,
            creatTime: new Date().toISOString(),
            price: parseFloat($('#bookTotalPrice').val())
        };

        console.log('预订信息:', formData);

        // 使用AJAX向后端提交数据
        $.ajax({
            url: '/hc/bookings',
            type: 'POST',
            contentType: 'application/json',
            data: JSON.stringify(formData),
            success: function(response) {
                if (response.code === 200) {
                    console.log('预订提交成功:', response);
                    alert('预订成功！订单号：' + response.data);
                    $('#bookingModal').hide();
                } else {
                    alert(response.message);
                }
            },
            error: function(xhr, status, error) {
                console.error('预订提交失败:', error);
                alert('预订提交失败，请稍后再试');
            }
        });
    });

    // 页面加载时检查日期有效性
    if (new Date($('#checkInDate').val()) >= new Date($('#checkOutDate').val())) {
        alert('默认日期设置错误，请重新选择入住和离店日期');
    }
});

// 初始化酒店ID
function initializeHotelId() {
    try {
        // 方法1: 从URL参数获取
        hotelId = getUrlParameter('id');

        // 方法2: 若URL中没有，使用预设值（开发环境使用）
        if (!hotelId) {
            hotelId = 1; // 预设酒店ID，实际项目中应移除
            console.log('使用预设酒店ID:', hotelId);
        }

        // 设置到表单字段
        $('#bookHotelId').val(hotelId);
        console.log('酒店ID已初始化:', hotelId);
    } catch (error) {
        console.error('初始化酒店ID失败:', error);
    }
}

// 从URL获取参数
function getUrlParameter(name) {
    name = name.replace(/[\[]/, '\\[').replace(/[\]]/, '\\]');
    const regex = new RegExp('[\\?&]' + name + '=([^&#]*)');
    const results = regex.exec(location.search);
    return results === null ? '' : decodeURIComponent(results[1].replace(/\+/g, ' '));
}

// 从本地存储加载用户信息
function loadUserInfo() {
    try {
        const storedId = localStorage.getItem('userId');
        const storedName = localStorage.getItem('userName');

        if (storedId && storedName) {
            userInfo.id = storedId;
            userInfo.name = storedName;
            loginFlag = 1;
            console.log('用户信息已加载:', userInfo);
        } else {
            console.log('未获取到用户信息，用户未登录');
        }
    } catch (error) {
        console.error('获取本地存储用户信息失败:', error);
    }
}

// 根据用户信息更新头部显示
function updateHeaderWithUserInfo() {
    const header = $('.header-content');
    const loginBtn = $('.login-btn');

    if (loginFlag === 1 && userInfo.name) {
        // 已登录，显示用户名
        loginBtn.hide();
        const userNameLink = $('<a>')
            .attr('href', '#')
            .addClass('user-profile')
            .html(`<i class="fa fa-user-circle"></i> ${userInfo.name}`)
            .click(function(e) {
                e.preventDefault();
                showUserProfile();
            });
        header.append(userNameLink);
    } else {
        // 未登录，显示登录按钮
        loginBtn.show();
    }
}

// 模拟登录流程
function simulateLogin() {
    const username = prompt('请输入用户名:');
    if (!username) return;

    // 生成随机用户ID
    const userId = Math.floor(Math.random() * 1000000);

    // 保存到本地存储
    localStorage.setItem('userId', userId);
    localStorage.setItem('userName', username);

    // 更新全局变量
    userInfo.id = userId;
    userInfo.name = username;
    loginFlag = 1;

    // 更新UI
    updateHeaderWithUserInfo();

    alert(`欢迎回来，${username}！`);
}

// 显示用户个人中心
function showUserProfile() {
    if (loginFlag === 0) {
        alert('请先登录');
        return;
    }

    alert(`用户信息:\nID: ${userInfo.id}\n姓名: ${userInfo.name}\n酒店ID: ${hotelId}`);
}

// 渲染酒店星级评分
function renderHotelRating() {
    const hotelScoreElement = $('#hotelRating');
    const rawScore = hotelScoreElement.text().trim();
    const score = parseFloat(rawScore);

    console.log('【评分渲染】获取到的酒店评分:', rawScore, '转换后:', score);

    // 清空原有内容
    hotelScoreElement.empty();

    // 处理未获取到评分的情况
    if (isNaN(score) || score === null || score === undefined) {
        hotelScoreElement.html(`
            <i class="fa fa-star-o"></i>
            <i class="fa fa-star-o"></i>
            <i class="fa fa-star-o"></i>
            <i class="fa fa-star-o"></i>
            <i class="fa fa-star-o"></i>
            <span class="ml-1">暂无评分</span>
        `);
        return;
    }

    // 计算完整星、半星和空星的数量
    const fullStars = Math.floor(score);
    const halfStar = score % 1 >= 0.5;
    const emptyStars = 5 - fullStars - (halfStar ? 1 : 0);

    // 添加星星图标
    let starsHtml = '';
    for (let i = 0; i < fullStars; i++) {
        starsHtml += '<i class="fa fa-star"></i>';
    }
    if (halfStar) {
        starsHtml += '<i class="fa fa-star-half-o"></i>';
    }
    for (let i = 0; i < emptyStars; i++) {
        starsHtml += '<i class="fa fa-star-o"></i>';
    }

    // 组合评分显示
    hotelScoreElement.html(`${starsHtml} <span class="ml-1">${score.toFixed(1)}</span>`);
}

// 格式化日期为YYYY-MM-DD
function formatDate(date) {
    const year = date.getFullYear();
    const month = String(date.getMonth() + 1).padStart(2, '0');
    const day = String(date.getDate()).padStart(2, '0');
    return `${year}-${month}-${day}`;
}

// 更新最低价格显示
function updateMinPrice() {
    let minPrice = Infinity;

    $('.room-price span').each(function() {
        const priceStr = $(this).text();
        const cleanPrice = priceStr.replace(/[^\d.]/g, '');
        const price = parseFloat(cleanPrice);

        if (!isNaN(price) && price < minPrice) {
            minPrice = price;
        }
    });

    if (minPrice !== Infinity) {
        $('#minPrice').text(minPrice);
    }
}

// 更新预订摘要（包含总价计算）
function updateBookingSummary() {
    console.log('===== 更新预订摘要 =====');

    // 获取日期输入
    const checkInDateVal = $('#checkInDate').val();
    const checkOutDateVal = $('#checkOutDate').val();

    console.log('入住日期:', checkInDateVal);
    console.log('退房日期:', checkOutDateVal);

    if (!checkInDateVal || !checkOutDateVal) {
        console.warn('日期未选择，总价计算中止');
        $('#summaryDays').text('0晚');
        $('#summaryTotal').text('¥0');
        $('#bookTotalPrice').val(0);
        return;
    }

    // 创建日期对象
    const checkInDate = new Date(checkInDateVal);
    const checkOutDate = new Date(checkOutDateVal);

    // 设置时间为0点，避免时区和时间差异影响计算
    checkInDate.setHours(0, 0, 0, 0);
    checkOutDate.setHours(0, 0, 0, 0);

    // 计算入住天数
    const oneDay = 24 * 60 * 60 * 1000;
    const diffTime = checkOutDate - checkInDate;
    const diffDays = Math.max(1, Math.floor(diffTime / oneDay)); // 至少1天

    console.log('入住天数计算:', diffDays, '晚');

    // 更新UI显示
    $('#summaryDays').text(`${diffDays}晚`);

    // 获取房型价格并转换为数字
    const roomPriceStr = $('#bookRoomPrice').val();
    console.log('房型价格字符串:', roomPriceStr);

    let roomPrice = 0;

    if (roomPriceStr) {
        // 移除价格中的非数字字符，保留小数点
        const cleanPrice = roomPriceStr.replace(/[^\d.]/g, '');
        console.log('清理后的价格:', cleanPrice);

        roomPrice = parseFloat(cleanPrice);

        // 检查价格是否有效
        if (isNaN(roomPrice) || roomPrice <= 0) {
            roomPrice = 0;
            console.error('无效的房型价格:', roomPriceStr);
        }
    } else {
        console.warn('房型价格为空，使用默认值0');
    }

    // 计算总价
    const totalPrice = roomPrice * diffDays;
    console.log('总价计算:', roomPrice, '×', diffDays, '=', totalPrice);

    // 更新UI和隐藏字段
    $('#summaryTotal').text(`¥${totalPrice.toFixed(2)}`);
    $('#bookTotalPrice').val(totalPrice);

    console.log('===== 总价计算完成 =====');
}

// 表单验证
function validateForm() {
    let isValid = true;

    // 检查日期
    const checkInDate = new Date($('#checkInDate').val());
    const checkOutDate = new Date($('#checkOutDate').val());

    if (checkInDate >= checkOutDate) {
        alert('离店日期必须晚于入住日期');
        isValid = false;
    }

    // 检查是否选择了房型
    if (!$('input[name="roomType"]:checked').length) {
        alert('请选择房型');
        isValid = false;
    }

    // 检查联系电话
    const contactPhone = $('#contactPhone').val();
    if (!/^1[3-9]\d{9}$/.test(contactPhone)) {
        alert('请输入有效的手机号码');
        isValid = false;
    }

    // 检查入住人数
    const guests = parseInt($('#guests').val(), 10);
    if (isNaN(guests) || guests <= 0) {
        alert('请选择入住人数');
        isValid = false;
    }

    // 检查总价是否有效
    const totalPrice = parseFloat($('#bookTotalPrice').val() || 0);
    if (totalPrice <= 0) {
        alert('订单总价异常，请检查房型价格和入住日期是否正确');
        isValid = false;
    }

    // 检查hotelId
    if (!hotelId) {
        alert('酒店信息异常，请刷新页面');
        isValid = false;
    }

    return isValid;
}
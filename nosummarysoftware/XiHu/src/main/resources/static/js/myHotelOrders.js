// 订单状态映射
const statusMap = {
    0: { text: '待确认', class: 'status-pending' },
    1: { text: '已确认', class: 'status-confirmed' },
    2: { text: '已取消', class: 'status-canceled' }
};

$(document).ready(function() {
    // 从本地存储加载用户信息
    loadUserInfo();

    // 检查用户是否已登录
    if (!checkLogin()) {
        alert('请先登录查看订单');
        window.location.href = '/hc/f2'; // 跳转到首页或登录页
        return;
    }

    // 获取并显示订单列表
    fetchOrders();

    // 用户资料按钮点击事件
    $('#userProfileBtn').on('click', function() {
        showUserProfile();
    });
});

// 检查用户是否已登录
function checkLogin() {
    const userId = localStorage.getItem('userId');
    const userName = localStorage.getItem('userName');

    if (userId && userName) {
        $('#userName').text(userName);
        return true;
    }

    return false;
}

// 从本地存储加载用户信息
function loadUserInfo() {
    try {
        const storedId = localStorage.getItem('userId');
        const storedName = localStorage.getItem('userName');

        if (storedId && storedName) {
            console.log('用户信息已加载:', { id: storedId, name: storedName });
        } else {
            console.log('未获取到用户信息，用户未登录');
        }
    } catch (error) {
        console.error('获取本地存储用户信息失败:', error);
    }
}

// 显示用户个人中心
function showUserProfile() {
    const userId = localStorage.getItem('userId');
    const userName = localStorage.getItem('userName');

    if (!userId || !userName) {
        alert('请先登录');
        return;
    }

    alert(`用户信息:\nID: ${userId}\n姓名: ${userName}`);
}

// 获取订单列表
function fetchOrders() {
    const userId = localStorage.getItem('userId');

    if (!userId) {
        console.error('未找到用户ID');
        showNoOrders();
        return;
    }

    console.log('正在获取用户', userId, '的订单列表');

    // 调用API获取订单
    $.ajax({
        url: `/hc/orders?userId=${userId}`,
        type: 'GET',
        dataType: 'json',
        success: function(response) {
            console.log('订单获取成功:', response);

            // 隐藏加载状态
            $('#loading').hide();

            if (response.code === 200 && response.data && response.data.length > 0) {
                renderOrders(response.data);
            } else {
                showNoOrders();
            }
        },
        error: function(xhr, status, error) {
            console.error('订单获取失败:', error);
            $('#loading').hide();
            showNoOrders();
            alert('获取订单失败，请稍后再试');
        }
    });
}

// 渲染订单列表
function renderOrders(orders) {
    const orderList = $('#orderList');
    orderList.empty();
    orderList.show();

    orders.forEach(order => {
        const statusInfo = statusMap[order.status] || { text: '未知状态', class: 'text-muted' };

        // 根据订单状态动态生成按钮
        let actionButtons = '';
        if (order.status === 0) { // 待确认 - 显示确认按钮
            actionButtons = `
                <button class="btn btn-sm btn-primary make-sure" data-id="${order.id}">
                    <i class="fa fa-check-circle-o mr-1"></i> 确认订单
                </button>
            `;
        } else if (order.status === 1) { // 已确认 - 显示取消按钮
            actionButtons = `
                <button class="btn btn-sm btn-danger cancel-order" data-id="${order.id}">
                    <i class="fa fa-times mr-1"></i> 取消订单
                </button>
            `;
        } // 已取消状态不显示任何按钮

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

        orderList.append(orderItem);
    });

    // 绑定订单操作事件
    $('.cancel-order').on('click', function() {
        const orderId = $(this).data('id');
        cancelOrder(orderId);
    });

    $('.make-sure').on('click', function() {
        const orderId = $(this).data('id');
        makeSure(orderId);
    });
}

// 显示无订单提示
function showNoOrders() {
    $('#noOrders').show();
}

// 格式化日期
function formatDate(dateStr) {
    if (!dateStr) return '未知日期';

    // 处理可能的时间戳格式
    if (typeof dateStr === 'number' || /^\d+$/.test(dateStr)) {
        dateStr = new Date(parseInt(dateStr)).toISOString().split('T')[0];
    }

    const date = new Date(dateStr);
    return `${date.getFullYear()}-${(date.getMonth() + 1).toString().padStart(2, '0')}-${date.getDate().toString().padStart(2, '0')}`;
}

// 计算入住天数
function calculateDays(checkInDate, checkOutDate) {
    const inDate = new Date(checkInDate);
    const outDate = new Date(checkOutDate);
    const oneDay = 24 * 60 * 60 * 1000;
    return Math.floor((outDate - inDate) / oneDay);
}

// 取消订单
function cancelOrder(orderId) {
    if (!confirm('确定要取消此订单吗？')) {
        return;
    }

    $.ajax({
        url: `/hc/orders/${orderId}/cancel`,
        type: 'POST',
        dataType: 'json',
        success: function(response) {
            if (response.code === 200) {
                alert('订单已成功取消');
                fetchOrders(); // 刷新订单列表
            } else {
                alert(response.message || '取消订单失败');
            }
        },
        error: function(xhr, status, error) {
            console.error('取消订单失败:', error);
            alert('取消订单失败，请稍后再试');
        }
    });
}

// 确认订单
function makeSure(orderId) {
    if (!confirm('确定已读并确认此订单吗？')) {
        return;
    }

    $.ajax({
        url: `/hc/orders/${orderId}/makeSure`,
        type: 'POST',
        dataType: 'json',
        success: function(response) {
            if (response.code === 200) {
                alert('订单已成功确认');
                fetchOrders(); // 刷新订单列表
            } else {
                alert(response.message || '确认订单失败');
            }
        },
        error: function(xhr, status, error) {
            console.error('确认订单失败:', error);
            alert('确认订单失败，请稍后再试');
        }
    });
}
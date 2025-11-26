/**
 * 生活缴费模块JavaScript
 * 功能概述：处理生活缴费模块的交互逻辑，包括缴费类型加载、统计信息加载、账单列表加载、账单支付等
 */

// 全局变量
// 当前选中的账单ID，用于账单操作
let currentBillId = null;

/**
 * 根据缴费类型名称获取类型代码
 * 功能概述：根据缴费类型名称，返回对应的类型代码
 * @param {string} typeName - 缴费类型名称
 * @return {string} 缴费类型代码
 */
function getTypeCodeByName(typeName) {
    const typeMap = {
        '水费': 'WATER',
        '电费': 'ELECTRIC',
        '燃气费': 'GAS',
        '物业费': 'PROPERTY',
        '宽带费': 'INTERNET',
        '手机话费': 'MOBILE',
        '有线电视费': 'TV',
        '停车费': 'PARKING'
    };
    return typeMap[typeName] || '';
}

/**
 * 加载缴费类型
 * 功能概述：从预定义的缴费类型数据中加载缴费类型，并渲染到页面上
 */
// 加载缴费类型函数，加载并显示缴费类型
function loadPaymentTypes() {
    // 缴费类型数据（实际应该从后端获取）
    // 定义缴费类型数组，包含8种缴费类型
    const paymentTypes = [
        { id: 1, typeName: '水费', typeCode: 'WATER', icon: 'fas fa-tint', description: '居民生活用水费用' },        // 水费类型
        { id: 2, typeName: '电费', typeCode: 'ELECTRIC', icon: 'fas fa-bolt', description: '居民生活用电费用' },      // 电费类型
        { id: 3, typeName: '燃气费', typeCode: 'GAS', icon: 'fas fa-fire', description: '居民生活用气费用' },        // 燃气费类型
        { id: 4, typeName: '物业费', typeCode: 'PROPERTY', icon: 'fas fa-building', description: '小区物业管理费用' }, // 物业费类型
        { id: 5, typeName: '宽带费', typeCode: 'INTERNET', icon: 'fas fa-wifi', description: '家庭宽带网络费用' },    // 宽带费类型
        { id: 6, typeName: '手机话费', typeCode: 'MOBILE', icon: 'fas fa-mobile-alt', description: '手机通讯费用' },  // 手机话费类型
        { id: 7, typeName: '有线电视费', typeCode: 'TV', icon: 'fas fa-tv', description: '有线电视服务费用' },        // 有线电视费类型
        { id: 8, typeName: '停车费', typeCode: 'PARKING', icon: 'fas fa-car', description: '小区停车费用' }          // 停车费类型
    ];
    
    // 获取缴费类型网格容器元素
    const typeGrid = document.getElementById('typeGrid');
    // 判断缴费类型网格容器元素是否存在
    if (typeGrid) {
        // 如果存在，将缴费类型数组转换为HTML字符串，设置到缴费类型网格容器中
        typeGrid.innerHTML = paymentTypes.map(type => `
            <div class="type-item" onclick="filterByType('${type.typeCode}')">
                <div class="type-icon">
                    <i class="${type.icon}"></i>
                </div>
                <div class="type-name">${type.typeName}</div>
                <div class="type-desc">${type.description}</div>
            </div>
        `).join('');  // 将数组中的所有HTML字符串连接成一个字符串
    }
}

/**
 * 加载统计信息
 * 功能概述：从后端获取账单统计信息，并更新页面显示
 */
// 加载统计信息函数，加载并显示账单统计信息
function loadStats() {
    // 使用fetch API向后端获取统计信息
    fetch('/payment/stats')
    .then(response => {
        // 将响应体解析为JSON对象
        return response.json();
    })
    .then(data => {
        // 判断统计信息是否获取成功
        if (data.success) {
            // 如果获取成功，获取统计信息对象
            const stats = data.stats;
            // 更新总账单数显示（如果不存在则显示0）
            document.getElementById('totalBills').textContent = stats.totalBills || 0;
            // 更新未缴费账单数显示（如果不存在则显示0）
            document.getElementById('unpaidBills').textContent = stats.unpaidBills || 0;
            // 更新已缴费账单数显示（如果不存在则显示0）
            document.getElementById('paidBills').textContent = stats.paidBills || 0;
            // 更新逾期账单数显示（如果不存在则显示0）
            document.getElementById('overdueBills').textContent = stats.overdueBills || 0;
        // 如果获取失败
        } else {
            // 打印获取统计信息失败日志到控制台，包含错误消息
            console.log('获取统计信息失败:', data.message);
            // 如果未登录，显示0
            // 设置总账单数为0
            document.getElementById('totalBills').textContent = 0;
            // 设置未缴费账单数为0
            document.getElementById('unpaidBills').textContent = 0;
            // 设置已缴费账单数为0
            document.getElementById('paidBills').textContent = 0;
            // 设置逾期账单数为0
            document.getElementById('overdueBills').textContent = 0;
        }
    })
    .catch(error => {
        // 捕获所有异常
        // 打印加载统计信息失败错误日志到控制台
        console.error('加载统计信息失败:', error);
    });
}

/**
 * 加载账单列表
 * 功能概述：根据账单状态，从后端获取账单列表并渲染到页面上
 * @param {string} status - 账单状态（默认为空字符串，表示所有状态）
 */
// 加载账单列表函数，接收账单状态，加载并显示账单列表
function loadBills(status = '') {
    // 获取加载状态元素
    const loadingState = document.getElementById('loadingState');
    // 获取空状态元素
    const emptyState = document.getElementById('emptyState');
    // 获取账单列表容器元素
    const billList = document.getElementById('billList');
    
    // 显示加载状态
    // 如果加载状态元素存在，显示加载状态
    if (loadingState) loadingState.style.display = 'block';
    // 如果空状态元素存在，隐藏空状态
    if (emptyState) emptyState.style.display = 'none';
    // 如果账单列表容器元素存在，隐藏账单列表
    if (billList) billList.style.display = 'none';
    
    // 构建请求URL
    // 初始化请求URL为账单列表接口
    let url = '/payment/bills';
    // 如果账单状态不为空，添加到URL参数中
    if (status !== '') {
        url += `?status=${status}`;
    }
    
    // 使用fetch API向后端获取账单列表
    fetch(url)
    .then(response => {
        // 将响应体解析为JSON对象
        return response.json();
    })
    .then(data => {
        // 隐藏加载状态
        // 如果加载状态元素存在，隐藏加载状态
        if (loadingState) loadingState.style.display = 'none';
        
        // 判断账单列表是否获取成功
        if (data.success && data.list) {
            // 如果当前有选中的缴费类型，进行前端筛选
            let filteredList = data.list;
            if (currentPaymentType) {
                // 根据缴费类型代码筛选账单
                filteredList = data.list.filter(bill => {
                    // 获取账单的缴费类型代码（从paymentTypeCode字段或根据paymentTypeName推断）
                    const billTypeCode = bill.paymentTypeCode || getTypeCodeByName(bill.paymentTypeName);
                    return billTypeCode === currentPaymentType;
                });
            }
            
            // 判断筛选后的账单列表是否不为空
            if (filteredList.length > 0) {
                // 显示账单列表
                // 如果空状态元素存在，隐藏空状态
                if (emptyState) emptyState.style.display = 'none';
                // 如果账单列表容器元素存在
                if (billList) {
                    // 设置账单列表容器为flex布局
                    billList.style.display = 'flex';
                    // 将账单列表数组转换为HTML字符串，设置到账单列表容器中
                    billList.innerHTML = filteredList.map(bill => renderBillItem(bill)).join('');  // 使用renderBillItem函数渲染每个账单项
                }
            } else {
                // 显示空状态
                // 如果空状态元素存在，显示空状态
                if (emptyState) emptyState.style.display = 'block';
                // 如果账单列表容器元素存在，隐藏账单列表
                if (billList) billList.style.display = 'none';
            }
        // 如果账单列表获取失败
        } else {
            // 显示空状态
            // 如果空状态元素存在，显示空状态
            if (emptyState) emptyState.style.display = 'block';
            // 如果账单列表容器元素存在，隐藏账单列表
            if (billList) billList.style.display = 'none';
        }
    })
    .catch(error => {
        // 捕获所有异常
        // 打印加载账单失败错误日志到控制台
        console.error('加载账单失败:', error);
        // 如果加载状态元素存在，隐藏加载状态
        if (loadingState) loadingState.style.display = 'none';
        // 如果空状态元素存在
        if (emptyState) {
            // 显示空状态
            emptyState.style.display = 'block';
            // 设置空状态文本为"加载失败，请稍后重试"
            emptyState.querySelector('.empty-text').textContent = '加载失败，请稍后重试';
        }
    });
}

/**
 * 渲染账单项
 * 功能概述：根据账单数据，生成账单项的HTML字符串
 * @param {Object} bill - 账单对象，包含账单的所有信息
 * @return {string} 账单项的HTML字符串
 */
// 渲染账单项函数，接收账单对象，返回账单项的HTML字符串
function renderBillItem(bill) {
    // 根据账单状态确定状态样式类（1=已缴费=paid，2=逾期=overdue，其他=待缴费=pending）
    const statusClass = bill.billStatus === 1 ? 'paid' : (bill.billStatus === 2 ? 'overdue' : 'pending');
    // 根据账单状态确定状态文本（1=已缴费，2=逾期，其他=待缴费）
    const statusText = bill.billStatus === 1 ? '已缴费' : (bill.billStatus === 2 ? '逾期' : '待缴费');
    // 将缴费截止日期转换为本地日期字符串
    const dueDate = new Date(bill.dueDate).toLocaleDateString();
    // 如果缴费时间存在，转换为本地日期字符串，否则使用空字符串
    const paidTime = bill.paidTime ? new Date(bill.paidTime).toLocaleDateString() : '';
    
    // 根据缴费状态显示不同的按钮
    // 初始化操作按钮HTML字符串
    let actionButtons = '';
    // 判断账单状态是否为已缴费（1）
    if (bill.billStatus === 1) {
        // 已缴费：只显示查看详情按钮
        actionButtons = `<button class="view-btn" onclick="viewBillDetail(${bill.id})">查看详情</button>`;
    // 如果账单状态不是已缴费
    } else {
        // 未缴费：显示立即缴费和查看详情按钮
        actionButtons = `
            <button class="pay-btn" onclick="payBill(${bill.id})">立即缴费</button>
            <button class="view-btn" onclick="viewBillDetail(${bill.id})">查看详情</button>
        `;
    }
    
    // 生成账单项的HTML字符串，包含账单类型、状态、详情和操作按钮
    return `
        <div class="bill-item">
            <div class="bill-header">
                <div class="bill-type">
                    <div class="bill-type-icon">
                        <i class="${bill.paymentTypeIcon || 'fas fa-file-invoice'}"></i>
                    </div>
                    <div class="bill-type-info">
                        <h4>${bill.paymentTypeName || '未知类型'}</h4>
                        <p>${bill.accountName || '未知账户'}</p>
                    </div>
                </div>
                <div class="bill-status ${statusClass}">${statusText}</div>
            </div>
            <div class="bill-details">
                <div class="bill-detail">
                    <div class="bill-detail-label">账单号</div>
                    <div class="bill-detail-value">${bill.billNumber}</div>
                </div>
                <div class="bill-detail">
                    <div class="bill-detail-label">账单周期</div>
                    <div class="bill-detail-value">${bill.billPeriod}</div>
                </div>
                <div class="bill-detail">
                    <div class="bill-detail-label">缴费截止</div>
                    <div class="bill-detail-value">${dueDate}</div>
                </div>
                <div class="bill-detail">
                    <div class="bill-detail-label">账单金额</div>
                    <div class="bill-detail-value bill-amount">¥${bill.billAmount}</div>
                </div>
                ${bill.paidTime ? `
                <div class="bill-detail">
                    <div class="bill-detail-label">缴费时间</div>
                    <div class="bill-detail-value">${paidTime}</div>
                </div>
                ` : ''}
            </div>
            <div class="bill-actions">
                ${actionButtons}
            </div>
        </div>
    `;
}

// 全局变量：当前选中的缴费类型
let currentPaymentType = '';

/**
 * 按类型筛选
 * 功能概述：根据缴费类型代码，筛选并重新加载账单列表
 * @param {string} typeCode - 缴费类型代码（如'WATER'、'ELECTRIC'等）
 */
// 按类型筛选函数，接收缴费类型代码，筛选并重新加载账单列表
function filterByType(typeCode) {
    // 更新当前选中的缴费类型
    currentPaymentType = typeCode;
    
    // 更新缴费类型项的激活状态
    document.querySelectorAll('.type-item').forEach(item => {
        item.classList.remove('active');
    });
    // 为当前点击的缴费类型项添加active类
    const clickedItem = event.currentTarget;
    if (clickedItem) {
        clickedItem.classList.add('active');
    }
    
    // 打印筛选类型日志到控制台，包含缴费类型代码
    console.log('筛选类型:', typeCode);
    
    // 获取当前选中的账单状态筛选标签
    const activeTab = document.querySelector('.filter-tab.active');
    const currentStatus = activeTab ? activeTab.getAttribute('data-status') || '' : '';
    
    // 重新加载账单（保持当前状态筛选）
    loadBills(currentStatus);
}

// 注意：旧的支付模态框已删除，所有支付操作统一跳转到 /unified-new/payment/checkout

/**
 * 查看账单详情
 * 功能概述：根据账单ID，从后端获取账单详情并显示在模态框中
 * @param {number} billId - 账单ID
 */
// 查看账单详情函数，接收账单ID，获取并显示账单详情
function viewBillDetail(billId) {
    // 使用fetch API向后端获取账单详情
    fetch(`/payment/bill/${billId}`)
    .then(response => {
        // 将响应体解析为JSON对象
        return response.json();
    })
    .then(data => {
        // 判断账单详情是否获取成功
        if (data.success) {
            // 如果获取成功，调用showBillDetailModal函数显示账单详情模态框
            showBillDetailModal(data.bill);
        // 如果获取失败
        } else {
            // 显示错误提示信息，包含错误消息
            alert('获取账单详情失败: ' + data.message);
        }
    })
    .catch(error => {
        // 捕获所有异常
        // 打印获取账单详情失败错误日志到控制台
        console.error('获取账单详情失败:', error);
        // 显示错误提示信息
        alert('获取账单详情失败，请稍后重试');
    });
}

/**
 * 支付账单 - 集成新的统一支付系统
 * 功能概述：根据账单ID，创建统一订单并跳转到统一支付页面
 * @param {number} billId - 账单ID
 */
// 支付账单函数，接收账单ID，创建统一订单并跳转到统一支付页面
function payBill(billId) {
    // 设置当前选中的账单ID
    currentBillId = billId;
    
    // 显示确认对话框
    // 显示确认对话框，询问用户是否确定支付该账单
    if (confirm('确定要支付这个账单吗？')) {
        // 调用后端创建统一订单
        // 使用fetch API向后端提交支付请求
        fetch('/payment/pay', {
            method: 'POST',                                    // 请求方法为POST
            headers: {
                'Content-Type': 'application/json'           // 请求头，指定内容类型为JSON
            },
            body: JSON.stringify({ billId: billId }),        // 请求体，将账单ID转换为JSON字符串
            credentials: 'include'                           // 包含凭证（Cookie等）
        })
        .then(response => {
            // 将响应体解析为JSON对象
            return response.json();
        })
        .then(data => {
            // 判断订单是否创建成功
            if (data.success) {
                // 如果订单创建成功，跳转到统一支付页面
                // 使用响应中的redirectUrl跳转到统一支付页面
                window.location.href = data.redirectUrl;
            // 如果订单创建失败
            } else {
                // 显示错误提示信息，包含错误消息
                alert('创建订单失败: ' + data.message);
            }
        })
        .catch(error => {
            // 捕获所有异常
            // 打印支付失败错误日志到控制台
            console.error('支付失败:', error);
            // 显示错误提示信息
            alert('支付失败，请稍后重试');
        });
    }
}

/**
 * 显示账单详情模态框
 * 功能概述：根据账单数据，生成账单详情模态框的HTML并显示在页面上
 * @param {Object} bill - 账单对象，包含账单的所有信息
 */
// 显示账单详情模态框函数，接收账单对象，显示账单详情模态框
function showBillDetailModal(bill) {
    // 根据账单状态确定状态文本（1=已缴费，2=逾期，其他=待缴费）
    const statusText = bill.billStatus === 1 ? '已缴费' : (bill.billStatus === 2 ? '逾期' : '待缴费');
    // 根据账单状态确定状态样式类（1=已缴费=paid，2=逾期=overdue，其他=待缴费=pending）
    const statusClass = bill.billStatus === 1 ? 'paid' : (bill.billStatus === 2 ? 'overdue' : 'pending');
    // 将缴费截止日期转换为本地日期字符串
    const dueDate = new Date(bill.dueDate).toLocaleDateString();
    // 如果缴费时间存在，转换为本地日期字符串，否则使用空字符串
    const paidTime = bill.paidTime ? new Date(bill.paidTime).toLocaleDateString() : '';
    
    // 生成账单详情模态框的HTML字符串，包含账单的所有详细信息
    const modalHtml = `
        <div class="modal-overlay" id="billDetailModal">
            <div class="modal-content bill-detail-modal">
                <div class="modal-header">
                    <h3>账单详情</h3>
                    <button class="close-btn" onclick="closeBillDetailModal()">&times;</button>
                </div>
                <div class="modal-body">
                    <div class="bill-detail-info">
                        <div class="detail-section">
                            <h4>基本信息</h4>
                            <div class="detail-row">
                                <span class="label">缴费类型:</span>
                                <span class="value">${bill.paymentTypeName || '未知类型'}</span>
                            </div>
                            <div class="detail-row">
                                <span class="label">账户名称:</span>
                                <span class="value">${bill.accountName || '未知账户'}</span>
                            </div>
                            <div class="detail-row">
                                <span class="label">账户号码:</span>
                                <span class="value">${bill.accountNumber || '未知'}</span>
                            </div>
                            <div class="detail-row">
                                <span class="label">账单编号:</span>
                                <span class="value">${bill.billNumber}</span>
                            </div>
                        </div>
                        
                        <div class="detail-section">
                            <h4>账单信息</h4>
                            <div class="detail-row">
                                <span class="label">账单周期:</span>
                                <span class="value">${bill.billPeriod}</span>
                            </div>
                            <div class="detail-row">
                                <span class="label">账单金额:</span>
                                <span class="value amount">¥${bill.billAmount}</span>
                            </div>
                            <div class="detail-row">
                                <span class="label">缴费截止:</span>
                                <span class="value">${dueDate}</span>
                            </div>
                            <div class="detail-row">
                                <span class="label">缴费状态:</span>
                                <span class="value status ${statusClass}">${statusText}</span>
                            </div>
                            ${bill.paidTime ? `
                            <div class="detail-row">
                                <span class="label">缴费时间:</span>
                                <span class="value">${paidTime}</span>
                            </div>
                            <div class="detail-row">
                                <span class="label">实付金额:</span>
                                <span class="value amount">¥${bill.paidAmount || bill.billAmount}</span>
                            </div>
                            ` : ''}
                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    ${bill.billStatus === 0 ? `
                        <button class="btn btn-primary" onclick="closeBillDetailModal(); payBill(${bill.id});">
                            <i class="fas fa-credit-card"></i>
                            立即缴费
                        </button>
                    ` : ''}
                    <button class="btn btn-outline" onclick="closeBillDetailModal()">关闭</button>
                </div>
            </div>
        </div>
    `;
    
    // 将模态框HTML插入到页面body的末尾
    document.body.insertAdjacentHTML('beforeend', modalHtml);
    
    // 显示弹窗
    // 使用setTimeout延迟执行，确保DOM已更新
    setTimeout(() => {
        // 获取账单详情模态框元素
        const modal = document.getElementById('billDetailModal');
        // 判断模态框元素是否存在
        if (modal) {
            // 如果存在，为模态框添加show样式类，显示模态框
            modal.classList.add('show');
        }
    }, 10);  // 延迟10毫秒执行
}

/**
 * 关闭账单详情模态框
 * 功能概述：从页面中移除账单详情模态框元素
 */
// 关闭账单详情模态框函数，关闭账单详情模态框
function closeBillDetailModal() {
    // 获取账单详情模态框元素
    const modal = document.getElementById('billDetailModal');
    // 判断模态框元素是否存在
    if (modal) {
        // 如果存在，从页面中移除模态框元素
        modal.remove();
    }
}

/**
 * 页面初始化
 * 功能概述：当DOM加载完成后执行，加载缴费类型、统计信息和账单列表
 */
// 页面初始化，当DOM加载完成后执行
document.addEventListener('DOMContentLoaded', function() {
    // 打印生活缴费模块初始化日志到控制台
    console.log('生活缴费模块初始化');
    
    // 初始加载数据
    // 调用loadPaymentTypes函数，加载缴费类型
    loadPaymentTypes();
    // 调用loadStats函数，加载统计信息
    loadStats();
    // 调用loadBills函数，加载账单列表（使用默认参数，显示所有状态的账单）
    loadBills();
});

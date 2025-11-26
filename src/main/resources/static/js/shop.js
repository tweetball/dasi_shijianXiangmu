/**
 * 拼多多风格购物模块交互JavaScript
 * 功能概述：处理购物模块的交互逻辑，包括购物车管理、商品数量更新、购物车渲染、商品搜索、分页等
 */

// 全局变量
// 购物车Map对象，键为商品ID，值为商品信息对象（包含id、name、price、qty、cover）
const cart = new Map();
// 当前页码，用于分页显示商品列表
let currentPage = 1;
// 当前搜索关键词，用于商品搜索
let currentKeyword = '';
// 当前商品分类，用于商品筛选
let currentCategory = '';

// 购物车存储键名
// localStorage中存储购物车数据的键名
const CART_STORAGE_KEY = 'shopping_cart';

/**
 * 保存购物车到 localStorage
 * 功能概述：将购物车数据转换为数组格式，保存到浏览器的localStorage中
 */
// 保存购物车到localStorage函数，将购物车数据保存到localStorage
function saveCartToStorage() {
    // 使用try-catch捕获异常
    try {
        // 将购物车Map转换为数组格式
        // 使用Array.from将Map转换为数组，然后使用map方法将每个条目转换为对象格式
        const cartArray = Array.from(cart.entries()).map(([id, item]) => ({
            id: id,                                                      // 商品ID
            name: item.name,                                             // 商品名称
            price: item.price,                                           // 商品价格
            qty: item.qty,                                               // 商品数量
            cover: item.cover || '/img/default-product.jpg'             // 商品封面图片URL，如果不存在则使用默认图片
        }));
        // 将购物车数组转换为JSON字符串，保存到localStorage
        localStorage.setItem(CART_STORAGE_KEY, JSON.stringify(cartArray));
        // 打印购物车已保存日志到控制台
        console.log('购物车已保存到 localStorage');
    // 捕获所有异常
    } catch (error) {
        // 打印保存购物车失败错误日志到控制台
        console.error('保存购物车失败:', error);
    }
}

/**
 * 从 localStorage 恢复购物车
 * 功能概述：从浏览器的localStorage中读取购物车数据，恢复到购物车Map对象中
 * @return {boolean} 是否成功恢复购物车（true=成功，false=失败）
 */
// 从localStorage恢复购物车函数，从localStorage恢复购物车数据
function loadCartFromStorage() {
    // 使用try-catch捕获异常
    try {
        // 从localStorage中获取购物车数据（JSON字符串格式）
        const cartData = localStorage.getItem(CART_STORAGE_KEY);
        // 判断购物车数据是否存在
        if (cartData) {
            // 如果存在，将JSON字符串解析为数组
            const cartArray = JSON.parse(cartData);
            // 清空当前购物车Map
            cart.clear();
            // 遍历购物车数组，将每个商品添加到购物车Map中
            cartArray.forEach(item => {
                // 将商品添加到购物车Map中，键为商品ID，值为商品信息对象
                cart.set(item.id, {
                    id: item.id,                                         // 商品ID
                    name: item.name,                                     // 商品名称
                    price: item.price,                                   // 商品价格
                    qty: item.qty,                                       // 商品数量
                    cover: item.cover || '/img/default-product.jpg'     // 商品封面图片URL，如果不存在则使用默认图片
                });
            });
            // 打印购物车已恢复日志到控制台，包含商品数量
            console.log('购物车已从 localStorage 恢复，共', cart.size, '个商品');
            // 返回true表示恢复成功
            return true;
        }
    // 捕获所有异常
    } catch (error) {
        // 打印恢复购物车失败错误日志到控制台
        console.error('恢复购物车失败:', error);
    }
    // 返回false表示恢复失败
    return false;
}

/**
 * 清空购物车（退出登录时调用）
 * 功能概述：清空购物车Map对象，删除localStorage中的购物车数据，并重新渲染购物车显示
 */
// 清空购物车函数，清空购物车数据
function clearCart() {
    // 清空购物车Map对象
    cart.clear();
    // 使用try-catch捕获异常
    try {
        // 从localStorage中删除购物车数据
        localStorage.removeItem(CART_STORAGE_KEY);
        // 打印购物车已清空日志到控制台
        console.log('购物车已清空');
    // 捕获所有异常
    } catch (error) {
        // 打印清空购物车失败错误日志到控制台
        console.error('清空购物车失败:', error);
    }
    // 重新渲染购物车显示
    renderCart();
}

/**
 * 更新商品数量（支持从详情页调用）
 * 功能概述：根据商品ID或按钮元素，更新购物车中商品的数量，支持增量模式和目标数量模式
 * @param {number|string|HTMLElement} productId - 商品ID（数字或字符串）或按钮元素
 * @param {number} delta - 增量（列表页）或目标数量（详情页，如果 delta >= 1000 则视为目标数量）
 */
// 更新商品数量函数，接收商品ID或按钮元素和增量，更新购物车中商品的数量
function updateQtyForProduct(productId, delta) {
    // 判断是详情页调用（数字ID）还是列表页调用（按钮元素）
    // 判断productId是否为数字类型，或是否为不包含小数点的数字字符串
    if (typeof productId === 'number' || (typeof productId === 'string' && !isNaN(productId) && !productId.includes('.'))) {
        // 从详情页调用，productId是数字ID（字符串或数字）
        // 将productId转换为整数类型
        const id = typeof productId === 'number' ? productId : parseInt(productId);
        // 使用fetch API向后端获取商品信息
        fetch(`/shop/product/${id}`)
        .then(r => {
            // 判断HTTP响应是否成功（状态码200-299）
            if (!r.ok) {
                // 如果响应不成功，抛出错误
                throw new Error('获取商品信息失败: ' + r.status);
            }
            // 将响应体解析为JSON对象
            return r.json();
        })
        .then(product => {
            // 判断商品信息是否存在且包含ID
            if (!product || !product.id) {
                // 如果商品信息不完整，打印错误日志到控制台
                console.error('商品信息不完整:', product);
                // 显示错误提示信息
                alert('商品信息不完整，请稍后重试');
                // 返回，不继续执行
                return;
            }
            
            // 确保ID是数字类型
            // 将商品ID转换为整数类型
            const prodId = typeof product.id === 'number' ? product.id : parseInt(product.id);
            // 判断商品ID是否为NaN（无效）
            if (isNaN(prodId)) {
                // 如果商品ID无效，打印错误日志到控制台
                console.error('商品ID无效:', product.id);
                // 显示错误提示信息
                alert('商品ID无效，请稍后重试');
                // 返回，不继续执行
                return;
            }
            
            // 获取商品封面图片URL，如果不存在则使用默认图片
            const cover = product.cover || '/img/default-product.jpg';
            // 获取商品价格，转换为浮点数，如果不存在则使用0
            const price = product.price ? (typeof product.price === 'number' ? product.price : parseFloat(product.price)) : 0;
            // 获取商品名称，如果不存在则使用"未知商品"
            const name = product.name || '未知商品';
            
            // 打印获取商品信息成功日志到控制台，包含商品ID、名称、价格和封面图片URL
            console.log('获取商品信息成功:', { id: prodId, name, price, cover });
            
            // 详情页传入的是目标数量（delta >= 1000），需要计算增量
            // 判断delta是否大于等于1000（目标数量模式）
            if (delta >= 1000) {
                // 目标数量模式：计算增量
                // 获取购物车中该商品的当前数量，如果不存在则使用0
                const current = cart.get(prodId)?.qty || 0;
                // 减去1000得到实际目标数量
                const targetQty = delta - 1000;
                // 计算需要增加的数量（目标数量 - 当前数量）
                const actualDelta = targetQty - current;
                // 打印目标数量模式日志到控制台，包含当前数量、目标数量和增量
                console.log('目标数量模式 - 当前:', current, '目标:', targetQty, '增量:', actualDelta);
                // 判断增量是否为0
                if (actualDelta !== 0) {
                    // 如果增量不为0，调用updateCartItem函数更新购物车项目
                    updateCartItem(prodId, name, price, actualDelta, cover);
                } else {
                    // 如果增量为0，打印数量未变化日志到控制台
                    console.log('数量未变化，无需更新');
                }
            } else {
                // 增量模式：直接增加
                // 打印增量模式日志到控制台，包含增量
                console.log('增量模式 - 增量:', delta);
                // 调用updateCartItem函数更新购物车项目
                updateCartItem(prodId, name, price, delta, cover);
            }
        })
        .catch(err => {
            // 捕获所有异常
            // 打印获取商品信息失败错误日志到控制台
            console.error('获取商品信息失败:', err);
            // 显示错误提示信息，包含错误消息
            alert('获取商品信息失败，请稍后重试: ' + err.message);
        });
        // 返回，不继续执行
        return;
    }
    
    // 从列表页调用，productId是按钮元素
    // 判断productId是否为DOM元素对象（nodeType为1表示元素节点）
    if (typeof productId === 'object' && productId.nodeType === 1) {
        // 获取按钮元素
        const button = productId;
        // 获取按钮最近的商品卡片元素
        const card = button.closest('.product-card');
        // 判断商品卡片元素是否存在
        if (!card) {
            // 如果不存在，打印错误日志到控制台
            console.error('无法找到商品卡片元素');
            // 返回，不继续执行
            return;
        }
        
        // 获取商品名称，从商品卡片中查找.product-name元素并获取文本内容，去除首尾空白字符
        const name = card.querySelector('.product-name')?.textContent.trim();
        // 获取商品价格文本，从商品卡片中查找.product-price元素并获取文本内容，去除¥符号和首尾空白字符
        const priceText = card.querySelector('.product-price')?.textContent.replace('¥','').trim();
        // 将价格文本转换为浮点数，如果转换失败则使用0
        const price = parseFloat(priceText) || 0;
        // 获取商品ID，从商品卡片的data-product-id属性中获取，如果不存在则使用商品名称
        const id = card.getAttribute('data-product-id') || name;
        // 获取商品图片元素
        const img = card.querySelector('.product-image');
        // 获取商品封面图片URL，如果图片元素存在则使用其src属性，否则使用默认图片
        const cover = img ? img.src : '/img/default-product.jpg';

        // 判断商品名称和ID是否存在
        if (!name || !id) {
            // 如果不存在，打印错误日志到控制台
            console.error('无法获取商品信息');
            // 返回，不继续执行
            return;
        }

        // 调用updateCartItem函数更新购物车项目
        updateCartItem(id, name, price, delta, cover);
    }
}

/**
 * 更新购物车项目
 * 
 * 功能概述：
 *   1. 统一商品ID类型（字符串数字转为数字），解决ID类型不一致导致的查找失败问题
 *   2. 处理ID类型迁移：如果购物车中存在旧格式ID，自动迁移到新格式
 *   3. 更新商品数量：支持增量模式（正数增加，负数减少）
 *   4. 自动删除：当数量为0时自动从购物车中删除
 *   5. 持久化：更新后自动保存到localStorage并重新渲染购物车
 * 
 * 修复内容（2024）：
 *   - 修复了ID类型不一致导致的购物车操作失败问题
 *   - 支持字符串和数字类型的ID自动转换
 *   - 添加了ID类型迁移机制，确保数据一致性
 * 
 * @param {string|number} id - 商品ID（支持字符串或数字类型）
 * @param {string} name - 商品名称
 * @param {number} price - 商品价格
 * @param {number} delta - 数量增量（正数表示增加，负数表示减少）
 * @param {string} cover - 商品封面图片URL
 * 
 * @example
 * // 增加1个商品
 * updateCartItem(123, '商品名称', 99.00, 1, '/img/product.jpg');
 * 
 * // 减少1个商品
 * updateCartItem(123, '商品名称', 99.00, -1, '/img/product.jpg');
 */
function updateCartItem(id, name, price, delta, cover) {
    // 统一ID类型：如果是字符串数字，转换为数字；否则保持原样
    // 这样可以解决从详情页（数字ID）和列表页（可能字符串ID）添加商品时的类型不一致问题
    const normalizedId = (typeof id === 'string' && !isNaN(id) && !id.includes('.')) ? parseInt(id) : id;
    
    // 尝试获取购物车中该商品的当前数量（先尝试规范化ID，再尝试原始ID）
    let current = 0;
    if (cart.has(normalizedId)) {
        current = cart.get(normalizedId)?.qty || 0;
    } else if (cart.has(id)) {
        current = cart.get(id)?.qty || 0;
        // 如果使用原始ID找到了，但ID类型不一致，需要迁移数据
        if (normalizedId !== id) {
            const item = cart.get(id);
            cart.delete(id);
            cart.set(normalizedId, item);
        }
    }
    
    // 计算新的数量（当前数量 + 增量），确保不小于0
    const next = Math.max(0, current + delta);
    
    // 判断新数量是否为0
    if (next === 0) {
        // 如果新数量为0，从购物车中删除该商品（尝试两种ID格式）
        if (cart.has(normalizedId)) {
            cart.delete(normalizedId);
        }
        if (cart.has(id)) {
            cart.delete(id);
        }
    } else {
        // 如果新数量不为0，更新购物车中的商品信息
        // 获取购物车中该商品的现有信息（尝试两种ID格式）
        const existingItem = cart.get(normalizedId) || cart.get(id);
        // 获取商品封面图片URL，优先使用传入的cover，其次使用现有商品的cover，最后使用默认图片
        const imageUrl = cover || existingItem?.cover || '/img/default-product.jpg';
        // 更新购物车中的商品信息，使用规范化ID作为键
        cart.set(normalizedId, { id: normalizedId, name, price, qty: next, cover: imageUrl });
        // 如果原始ID和规范化ID不同，删除旧条目
        if (normalizedId !== id && cart.has(id)) {
            cart.delete(id);
        }
    }
    
    // 保存到 localStorage
    // 调用saveCartToStorage函数，将购物车数据保存到localStorage
    saveCartToStorage();
    
    // 重新渲染购物车显示
    // 调用renderCart函数，重新渲染购物车显示
    renderCart();
}

/**
 * 渲染购物车
 * 功能概述：根据购物车数据，更新购物车徽章数字、购物车内容、总价和结算按钮状态
 */
// 渲染购物车函数，更新购物车显示
function renderCart() {
    // 获取购物车徽章元素（显示商品数量）
    const cartBadge = document.getElementById('cartBadge');
    // 获取购物车商品列表容器元素
    const cartItems = document.getElementById('cartItems');
    // 获取购物车总价元素
    const cartTotal = document.getElementById('cartTotal');
    // 获取结算按钮元素
    const checkoutBtn = document.getElementById('checkoutBtn');
    
    // 判断所有必需的元素是否存在
    if (!cartBadge || !cartItems || !cartTotal || !checkoutBtn) {
        // 如果不存在，返回，不继续执行
        return;
    }
    
    // 更新徽章数字
    // 计算购物车中所有商品的总数量（使用reduce方法累加每个商品的数量）
    const totalItems = Array.from(cart.values()).reduce((sum, item) => sum + item.qty, 0);
    // 设置购物车徽章的文本内容为总数量
    cartBadge.textContent = totalItems;
    // 根据总数量显示或隐藏购物车徽章（如果总数量大于0则显示，否则隐藏）
    cartBadge.style.display = totalItems > 0 ? 'flex' : 'none';
    
    // 更新购物车内容
    // 判断购物车是否为空
    if (cart.size === 0) {
        // 如果购物车为空，显示空购物车提示
        cartItems.innerHTML = `
            <div class="empty-cart">
                <div class="empty-cart-icon">🛒</div>
                <div class="empty-cart-text">购物车空空如也~快去逛逛吧</div>
            </div>
        `;
        // 设置总价为¥0.00
        cartTotal.textContent = '¥0.00';
        // 禁用结算按钮
        checkoutBtn.disabled = true;
        // 设置结算按钮文本为"去结算(0)"
        checkoutBtn.textContent = '去结算(0)';
    // 如果购物车不为空
    } else {
        // 初始化总价为0
        let total = 0;
        // 初始化商品列表HTML字符串
        let itemsHtml = '';
        
        // 遍历购物车中的所有商品，为每个商品生成HTML
        cart.forEach((item, id) => {
            // 计算当前商品的小计（商品价格 × 商品数量）
            const itemTotal = item.price * item.qty;
            // 累加总价
            total += itemTotal;
            // 获取商品封面图片URL，如果不存在则使用默认图片
            const cover = item.cover || '/img/default-product.jpg';
            
            // 确保ID是字符串类型，用于HTML属性
            const idStr = String(id);
            // 转义商品名称中的单引号，防止JavaScript错误（修复特殊字符导致的JS语法错误）
            const escapedName = item.name.replace(/'/g, "\\'");
            // 转义封面图片URL中的单引号，防止JavaScript错误
            const escapedCover = cover.replace(/'/g, "\\'");
            
            // 生成商品项的HTML字符串，包含商品图片、名称、价格、数量控制按钮和删除按钮
            // 修复内容（2024）：
            //   - 添加了删除按钮，支持从购物车中删除商品
            //   - 正确处理ID类型（数字或字符串），确保onclick事件正确绑定
            //   - 转义特殊字符，防止JavaScript语法错误
            itemsHtml += `
                <div class="cart-item">
                    <img src="${cover}" class="cart-item-image" alt="${item.name}" onerror="this.src='/img/default-product.jpg'">
                    <div class="cart-item-info">
                        <div class="cart-item-name">${item.name}</div>
                        <div class="cart-item-price">¥${item.price.toFixed(2)}</div>
                        <div class="cart-item-controls">
                            <!-- 减少数量按钮：当数量<=1时禁用 -->
                            <button class="qty-btn" onclick="updateCartItem(${typeof id === 'number' ? id : "'" + idStr + "'"}, '${escapedName}', ${item.price}, -1, '${escapedCover}')" ${item.qty <= 1 ? 'disabled' : ''}>-</button>
                            <span class="qty">${item.qty}</span>
                            <!-- 增加数量按钮 -->
                            <button class="qty-btn" onclick="updateCartItem(${typeof id === 'number' ? id : "'" + idStr + "'"}, '${escapedName}', ${item.price}, 1, '${escapedCover}')">+</button>
                            <!-- 删除按钮：支持从购物车中完全删除该商品（2024年新增） -->
                            <button class="delete-btn" onclick="removeCartItem(${typeof id === 'number' ? id : "'" + idStr + "'"})" title="删除商品">
                                <i class="fas fa-trash-alt"></i>
                            </button>
                        </div>
                    </div>
                </div>
            `;
        });
        
        // 将商品列表HTML设置到购物车商品列表容器中
        cartItems.innerHTML = itemsHtml;
        // 设置总价显示，格式为"¥总价"（保留2位小数）
        cartTotal.textContent = `¥${total.toFixed(2)}`;
        // 启用结算按钮
        checkoutBtn.disabled = false;
        // 设置结算按钮文本为"去结算(总数量)"
        checkoutBtn.textContent = `去结算(${totalItems})`;
    }
}

/**
 * 打开购物车抽屉
 * 功能概述：显示购物车抽屉，让用户查看购物车内容
 */
// 打开购物车抽屉函数，显示购物车抽屉
function openCartDrawer() {
    // 获取购物车抽屉元素
    const drawer = document.getElementById('cartDrawer');
    // 判断购物车抽屉元素是否存在
    if (drawer) {
        // 如果存在，为购物车抽屉添加show样式类，显示抽屉
        drawer.classList.add('show');
    }
}

/**
 * 关闭购物车抽屉
 * 功能概述：隐藏购物车抽屉
 */
// 关闭购物车抽屉函数，隐藏购物车抽屉
function closeCartDrawer() {
    // 获取购物车抽屉元素
    const drawer = document.getElementById('cartDrawer');
    // 判断购物车抽屉元素是否存在
    if (drawer) {
        // 如果存在，从购物车抽屉移除show样式类，隐藏抽屉
        drawer.classList.remove('show');
    }
}

/**
 * 打开结算模态框
 * 功能概述：检查购物车是否为空，如果为空则提示用户，否则打开结算模态框
 */
// 打开结算模态框函数，打开结算模态框
function openCheckoutModal() {
    // 判断购物车是否为空
    if (cart.size === 0) {
        // 如果购物车为空，显示提示信息
        alert('请先选择商品');
        // 返回，不继续执行
        return;
    }
    
    // 检查用户登录状态
    // 使用fetch API向后端获取用户会话信息
    fetch('/user/session', { credentials: 'include' })
    .then(r => {
        // 将响应体解析为JSON对象
        return r.json();
    })
    .then(s => {
        // 判断用户是否已登录
        if (!s || !s.loggedIn) {
            // 如果用户未登录，跳转到登录页面，并携带当前页面路径作为重定向参数
            window.location.href = '/user/login?redirect=' + encodeURIComponent('/shopping');
            // 返回，不继续执行
            return;
        }
        
        // 创建结算确认弹窗
        // 创建模态框div元素
        const modal = document.createElement('div');
        // 设置模态框的样式（固定定位、全屏覆盖、居中显示）
        modal.style.cssText = `
            position: fixed; top: 0; left: 0; right: 0; bottom: 0; z-index: 3000;
            background: rgba(0,0,0,0.5); display: flex; align-items: center; justify-content: center;
        `;
        
        // 初始化总价为0
        let total = 0;
        // 初始化商品列表HTML字符串
        let itemsHtml = '';
        // 遍历购物车中的所有商品，为每个商品生成HTML
        cart.forEach((item, id) => {
            // 计算当前商品的小计（商品价格 × 商品数量）
            const itemTotal = item.price * item.qty;
            // 累加总价
            total += itemTotal;
            // 生成商品项的HTML字符串，包含商品名称、数量和价格
            itemsHtml += `
                <div style="display: flex; justify-content: space-between; padding: 8px 0; border-bottom: 1px solid #eee;">
                    <span>${item.name} × ${item.qty}</span>
                    <span>¥${itemTotal.toFixed(2)}</span>
                </div>
            `;
        });
        
        // 设置模态框的HTML内容，包含订单确认信息、商品列表、总价、收货地址输入框和操作按钮
        modal.innerHTML = `
            <div style="background: white; border-radius: 12px; padding: 20px; max-width: 400px; width: 90%;">
                <h3 style="margin: 0 0 16px 0; text-align: center;">确认订单</h3>
                <div style="max-height: 200px; overflow-y: auto; margin-bottom: 16px;">
                    ${itemsHtml}
                </div>
                <div style="display: flex; justify-content: space-between; font-weight: bold; margin-bottom: 16px; padding-top: 8px; border-top: 1px solid #eee;">
                    <span>合计：</span>
                    <span style="color: #E02E24;">¥${total.toFixed(2)}</span>
                </div>
                <div style="margin-bottom: 16px;">
                    <label style="display: block; margin-bottom: 8px; font-weight: bold;">收货地址：</label>
                    <input type="text" id="deliveryAddress" placeholder="请输入收货地址" 
                           style="width: 100%; padding: 8px; border: 1px solid #ddd; border-radius: 4px;">
                </div>
                <div style="display: flex; gap: 12px;">
                    <button onclick="closeCheckoutModal()" 
                            style="flex: 1; padding: 12px; border: 1px solid #ddd; background: white; border-radius: 6px; cursor: pointer;">
                        取消
                    </button>
                    <button onclick="confirmAndPay()" 
                            style="flex: 2; padding: 12px; background: #E02E24; color: white; border: none; border-radius: 6px; cursor: pointer;">
                        确认支付
                    </button>
                </div>
            </div>
        `;
        
        // 将模态框添加到页面body中
        document.body.appendChild(modal);
        // 将模态框保存到window对象中，方便后续关闭
        window.currentCheckoutModal = modal;
    });
}

/**
 * 关闭结算模态框
 * 功能概述：从页面中移除结算模态框元素
 */
// 关闭结算模态框函数，关闭结算模态框
function closeCheckoutModal() {
    // 判断当前结算模态框是否存在
    if (window.currentCheckoutModal) {
        // 如果存在，从页面body中移除模态框元素
        document.body.removeChild(window.currentCheckoutModal);
        // 将模态框引用设置为null
        window.currentCheckoutModal = null;
    }
}

/**
 * 从购物车删除商品
 * 
 * 功能概述：
 *   1. 支持字符串和数字类型的ID删除
 *   2. 自动尝试多种ID格式匹配（数字、字符串、字符串数字）
 *   3. 如果直接匹配失败，遍历所有键查找匹配项
 *   4. 删除后自动保存到localStorage并重新渲染购物车
 * 
 * 修复内容（2024）：
 *   - 修复了详情页和首页添加商品后无法删除的问题
 *   - 增强了ID类型兼容性，支持多种ID格式
 *   - 添加了遍历查找机制，确保即使ID格式不完全匹配也能删除
 * 
 * @param {string|number} id - 商品ID（支持字符串或数字类型）
 * 
 * @example
 * // 删除ID为123的商品
 * removeCartItem(123);
 * // 或
 * removeCartItem('123');
 */
function removeCartItem(id) {
    // 将ID转换为数字类型（如果可能），因为购物车Map中的键可能是数字类型
    const numId = typeof id === 'string' && !isNaN(id) && !id.includes('.') ? parseInt(id) : id;
    
    // 尝试删除（先尝试数字ID，如果不存在再尝试字符串ID）
    if (cart.has(numId)) {
        cart.delete(numId);
    } else if (cart.has(id)) {
        cart.delete(id);
    } else {
        // 如果都找不到，尝试遍历所有键找到匹配的
        // 这种情况可能发生在ID类型转换不一致的情况下
        for (const key of cart.keys()) {
            if (String(key) === String(id) || key === numId || key === id) {
                cart.delete(key);
                break;
            }
        }
    }
    
    // 保存到 localStorage
    saveCartToStorage();
    // 重新渲染购物车显示
    renderCart();
}

/**
 * 从详情页打开结算模态框（立即购买）
 * 功能概述：检查购物车是否为空和用户登录状态，如果都满足则打开结算模态框
 */
// 从详情页打开结算模态框函数，从详情页打开结算模态框
function openCheckoutModalForShop() {
    // 判断购物车是否为空
    if (cart.size === 0) {
        // 如果购物车为空，显示提示信息
        alert('请先选择商品');
        // 返回，不继续执行
        return;
    }
    
    // 检查用户登录状态
    // 使用fetch API向后端获取用户会话信息
    fetch('/user/session', { credentials: 'include' })
    .then(r => {
        // 将响应体解析为JSON对象
        return r.json();
    })
    .then(s => {
        // 判断用户是否已登录
        if (!s || !s.loggedIn) {
            // 如果用户未登录，跳转到登录页面，并携带当前页面路径作为重定向参数
            window.location.href = '/user/login?redirect=' + encodeURIComponent(window.location.pathname);
            // 返回，不继续执行
            return;
        }
        
        // 打开结算模态框
        // 调用openCheckoutModal函数，打开结算模态框
        openCheckoutModal();
    })
    .catch(() => {
        // 捕获所有异常，跳转到登录页面
        window.location.href = '/user/login?redirect=' + encodeURIComponent(window.location.pathname);
    });
}

/**
 * 立即购买（直接结算，不加入购物车）
 * 功能概述：直接创建订单并打开结算模态框，不将商品添加到购物车
 * @param {number} productId - 商品ID
 * @param {number} quantity - 商品数量
 * @param {string} productName - 商品名称
 * @param {number} productPrice - 商品价格
 * @param {string} productCover - 商品封面图片URL
 */
function buyNowDirect(productId, quantity, productName, productPrice, productCover) {
    // 检查用户登录状态
    fetch('/user/session', { credentials: 'include' })
    .then(r => r.json())
    .then(s => {
        // 判断用户是否已登录
        if (!s || !s.loggedIn) {
            // 如果用户未登录，跳转到登录页面
            window.location.href = '/user/login?redirect=' + encodeURIComponent(window.location.pathname);
            return;
        }
        
        // 创建临时订单数据（不加入购物车）
        const tempItems = [{
            id: productId,
            qty: quantity,
            name: productName,
            price: productPrice
        }];
        
        // 计算总价
        const total = productPrice * quantity;
        
        // 创建结算确认弹窗
        const modal = document.createElement('div');
        modal.style.cssText = `
            position: fixed; top: 0; left: 0; right: 0; bottom: 0; z-index: 3000;
            background: rgba(0,0,0,0.5); display: flex; align-items: center; justify-content: center;
        `;
        
        // 设置模态框的HTML内容
        modal.innerHTML = `
            <div style="background: white; border-radius: 12px; padding: 20px; max-width: 400px; width: 90%;">
                <h3 style="margin: 0 0 16px 0; text-align: center;">确认订单</h3>
                <div style="max-height: 200px; overflow-y: auto; margin-bottom: 16px;">
                    <div style="display: flex; justify-content: space-between; padding: 8px 0; border-bottom: 1px solid #eee;">
                        <span>${productName} × ${quantity}</span>
                        <span>¥${total.toFixed(2)}</span>
                    </div>
                </div>
                <div style="display: flex; justify-content: space-between; font-weight: bold; margin-bottom: 16px; padding-top: 8px; border-top: 1px solid #eee;">
                    <span>合计：</span>
                    <span style="color: #E02E24;">¥${total.toFixed(2)}</span>
                </div>
                <div style="margin-bottom: 16px;">
                    <label style="display: block; margin-bottom: 8px; font-weight: bold;">收货地址：</label>
                    <input type="text" id="deliveryAddress" placeholder="请输入收货地址" 
                           style="width: 100%; padding: 8px; border: 1px solid #ddd; border-radius: 4px;">
                </div>
                <div style="display: flex; gap: 12px;">
                    <button onclick="closeBuyNowModal()" 
                            style="flex: 1; padding: 12px; border: 1px solid #ddd; background: white; border-radius: 6px; cursor: pointer;">
                        取消
                    </button>
                    <button onclick="confirmBuyNowDirect(${productId}, ${quantity}, '${productName.replace(/'/g, "\\'")}', ${productPrice}, '${productCover.replace(/'/g, "\\'")}')" 
                            style="flex: 2; padding: 12px; background: #E02E24; color: white; border: none; border-radius: 6px; cursor: pointer;">
                        确认支付
                    </button>
                </div>
            </div>
        `;
        
        // 将模态框添加到页面body中
        document.body.appendChild(modal);
        // 将模态框保存到window对象中，方便后续关闭
        window.currentBuyNowModal = modal;
    })
    .catch(() => {
        // 捕获所有异常，跳转到登录页面
        window.location.href = '/user/login?redirect=' + encodeURIComponent(window.location.pathname);
    });
}

/**
 * 关闭立即购买模态框
 */
function closeBuyNowModal() {
    if (window.currentBuyNowModal) {
        document.body.removeChild(window.currentBuyNowModal);
        window.currentBuyNowModal = null;
    }
}

/**
 * 确认立即购买并提交订单
 */
function confirmBuyNowDirect(productId, quantity, productName, productPrice, productCover) {
    // 获取收货地址输入框的值
    const address = document.getElementById('deliveryAddress').value.trim();
    if (!address) {
        alert('请输入收货地址');
        return;
    }
    
    // 计算总价
    const total = productPrice * quantity;
    
    // 创建订单数据对象
    const payload = {
        items: [{
            id: productId,
            qty: quantity,
            name: productName,
            price: productPrice
        }],
        shippingAddress: address,
        totalAmount: total
    };
    
    // 提交订单
    fetch('/shop/order/checkout', {
        method: 'POST',
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify(payload)
    })
    .then(r => r.json())
    .then(d => {
        if (d.success) {
            alert('订单已创建：' + d.orderNo);
            // 关闭模态框
            closeBuyNowModal();
            // 跳转到统一支付页面
            if (d.redirectUrl) {
                window.location.href = d.redirectUrl;
            }
        } else if (d.code === 'NOT_LOGIN') {
            window.location.href = '/user/login?redirect=' + encodeURIComponent('/shopping');
        } else {
            alert('下单失败：' + (d.message || '未知错误'));
        }
    })
    .catch(err => {
        console.error('下单失败:', err);
        alert('下单失败，请稍后重试');
    });
}

/**
 * 确认支付
 * 功能概述：验证收货地址，收集购物车数据，向后端提交订单，处理响应结果
 */
// 确认支付函数，确认并提交订单
function confirmAndPay() {
    // 获取收货地址输入框的值，去除首尾空白字符
    const address = document.getElementById('deliveryAddress').value.trim();
    // 判断收货地址是否存在
    if (!address) {
        // 如果收货地址为空，显示提示信息
        alert('请输入收货地址');
        // 返回，不继续执行
        return;
    }
    
    // 计算购物车总价（使用reduce方法累加每个商品的价格 × 数量）
    const total = Array.from(cart.values()).reduce((sum, item) => sum + item.price * item.qty, 0);
    // 创建订单数据对象，包含商品列表、收货地址和总金额
    const payload = {
        // 将购物车Map转换为数组，然后使用map方法将每个商品转换为对象格式
        items: Array.from(cart.values()).map(i => ({
            id: i.id,       // 商品ID
            qty: i.qty,     // 商品数量
            name: i.name,   // 商品名称
            price: i.price  // 商品价格
        })),
        shippingAddress: address,  // 收货地址
        totalAmount: total         // 订单总金额
    };
    
    // 使用fetch API向后端提交订单
    fetch('/shop/order/checkout', {
        method: 'POST',                                    // 请求方法为POST
        headers: {'Content-Type': 'application/json'},     // 请求头，指定内容类型为JSON
        body: JSON.stringify(payload)                      // 请求体，将订单数据对象转换为JSON字符串
    })
    .then(r => {
        // 将响应体解析为JSON对象
        return r.json();
    })
    .then(d => {
        // 判断订单是否创建成功
        if (d.success) {
            // 如果订单创建成功，显示订单号提示
            alert('订单已创建：' + d.orderNo);
            // 清空购物车Map对象
            cart.clear();
            // 清空后保存到localStorage
            // 调用saveCartToStorage函数，将清空后的购物车数据保存到localStorage
            saveCartToStorage();
            // 重新渲染购物车显示
            renderCart();
            // 关闭结算模态框
            closeCheckoutModal();
            // 跳转到统一支付页面
            // 判断响应中是否存在redirectUrl
            if (d.redirectUrl) {
                // 如果存在，跳转到统一支付页面
                window.location.href = d.redirectUrl;
            }
        // 如果订单创建失败，判断失败原因
        } else if (d.code === 'NOT_LOGIN') {
            // 如果失败原因是未登录，跳转到登录页面
            window.location.href = '/user/login?redirect=' + encodeURIComponent('/shopping');
        } else {
            // 其他失败原因，显示错误提示信息（优先使用d.message，否则使用默认提示）
            alert('下单失败：' + (d.message || '未知错误'));
        }
    })
    .catch(err => {
        // 捕获所有异常
        // 打印下单失败错误日志到控制台
        console.error('下单失败:', err);
        // 显示错误提示信息
        alert('下单失败，请稍后重试');
    });
}

/**
 * 加载商品列表
 * 功能概述：根据页码、关键词和分类，从后端获取商品列表并渲染到页面上
 * @param {number} page - 页码（默认为1）
 * @param {string} keyword - 搜索关键词（默认为空字符串）
 * @param {string} category - 商品分类（默认为空字符串）
 */
// 加载商品列表函数，接收页码、关键词和分类，加载并显示商品列表
function loadProducts(page = 1, keyword = '', category = '') {
    // 更新当前页码
    currentPage = page;
    // 更新当前搜索关键词
    currentKeyword = keyword;
    // 更新当前商品分类
    currentCategory = category;
    
    // 获取加载状态元素
    const loadingState = document.getElementById('loadingState');
    // 获取空状态元素
    const emptyState = document.getElementById('emptyState');
    // 获取商品列表容器元素
    const productList = document.getElementById('productList');
    
    // 显示加载状态
    // 如果加载状态元素存在，显示加载状态
    if (loadingState) loadingState.style.display = 'block';
    // 如果空状态元素存在，隐藏空状态
    if (emptyState) emptyState.style.display = 'none';
    // 如果商品列表容器元素存在，隐藏商品列表
    if (productList) productList.style.display = 'none';
    
    // 构建查询参数
    // 所有分类都禁用分页，显示该分类下的所有商品
    // 构建请求URL，设置页码为1，每页显示99999条（不限制数量，显示所有商品）
    let url = `/shop/products?page=1&pageSize=99999`;
    // 如果关键词存在，添加到URL参数中
    if (keyword) url += `&keyword=${encodeURIComponent(keyword)}`;
    // 如果分类存在，添加到URL参数中
    if (category) url += `&category=${encodeURIComponent(category)}`;
    
    // 使用fetch API向后端获取商品列表
    fetch(url)
    .then(r => {
        // 将响应体解析为JSON对象
        return r.json();
    })
    .then(d => {
        // 获取商品列表数组，如果不存在则使用空数组
        const list = d.list || [];
        
        // 隐藏加载状态
        // 如果加载状态元素存在，隐藏加载状态
        if (loadingState) loadingState.style.display = 'none';
        
        // 判断商品列表是否为空
        if (list.length === 0) {
            // 如果商品列表为空，显示空状态
            // 如果空状态元素存在，显示空状态
            if (emptyState) emptyState.style.display = 'block';
            // 如果商品列表容器元素存在，隐藏商品列表
            if (productList) productList.style.display = 'none';
        // 如果商品列表不为空
        } else {
            // 显示商品列表
            // 如果空状态元素存在，隐藏空状态
            if (emptyState) emptyState.style.display = 'none';
            // 如果商品列表容器元素存在
            if (productList) {
                // 设置商品列表容器为grid布局
                productList.style.display = 'grid';
                // 将商品列表数组转换为HTML字符串，设置到商品列表容器中
                productList.innerHTML = list.map(p => {
                    // 获取商品封面图片URL，如果不存在则使用默认图片
                    const cover = p.cover || '/img/default-product.jpg';
                    // 获取商品名称，如果不存在则使用空字符串
                    const name = p.name || '';
                    // 获取商品价格，转换为字符串并保留2位小数，如果不存在则使用0
                    const price = (p.price || 0).toFixed(2);
                    // 计算商品原价（商品价格 × 1.5），四舍五入后转换为字符串并保留2位小数
                    const originalPrice = Math.round(p.price * 1.5).toFixed(2);
                    // 获取商品销量，如果不存在则生成随机销量（100-5099之间）
                    const soldCount = p.soldCount || Math.floor(Math.random() * 5000) + 100;
                    
                    // 生成商品卡片的HTML字符串，包含商品图片、名称、价格、原价、销量和加入购物车按钮
                    return `
                        <div class="product-card" data-product-id="${p.id}">
                            <a href="/shopping/product/${p.id}">
                                <img class="product-image" src="${cover}" alt="${name}">
                            </a>
                            <div class="product-info">
                                <div class="product-name">${name}</div>
                                <div class="product-price-section">
                                    <div class="product-price">¥${price}</div>
                                    <div class="product-original-price">¥${originalPrice}</div>
                                </div>
                                <div class="product-sold">已售${soldCount}件</div>
                                <button class="add-to-cart-btn" onclick="updateQtyForProduct(this, 1)">加入购物车</button>
                            </div>
                        </div>
                    `;
                }).join('');  // 将数组中的所有HTML字符串连接成一个字符串
            }
        }
    })
    .catch(err => {
        // 捕获所有异常
        // 打印加载商品失败错误日志到控制台
        console.error('加载商品失败:', err);
        // 如果加载状态元素存在，隐藏加载状态
        if (loadingState) loadingState.style.display = 'none';
        // 如果空状态元素存在
        if (emptyState) {
            // 显示空状态
            emptyState.style.display = 'block';
            // 设置空状态文本为"加载失败，请稍后重试"
            emptyState.querySelector('.empty-cart-text').textContent = '加载失败，请稍后重试';
        }
    });
}

/**
 * 页面初始化
 * 功能概述：当DOM加载完成后执行，恢复购物车数据、加载商品列表、初始化购物车显示
 */
// 页面初始化，当DOM加载完成后执行
document.addEventListener('DOMContentLoaded', () => {
    // 打印购物模块初始化日志到控制台
    console.log('购物模块初始化');
    
    // 从 localStorage 恢复购物车
    // 调用loadCartFromStorage函数，从localStorage恢复购物车数据
    loadCartFromStorage();
    
    // 初始加载商品
    // 调用loadProducts函数，加载商品列表（使用默认参数）
    loadProducts();
    
    // 初始化购物车显示
    // 调用renderCart函数，初始化购物车显示
    renderCart();
});

// 导出清空购物车函数，供退出登录时调用
// 将clearCart函数导出到window对象，供其他模块调用（如退出登录时清空购物车）
window.clearShoppingCart = clearCart;



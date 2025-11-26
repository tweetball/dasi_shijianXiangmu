/**
 * 酒店列表页面JavaScript
 * 功能概述：处理酒店列表页面的交互逻辑，包括数据加载、筛选、渲染等
 */

// 全局变量
// 存储从后端获取的所有酒店数据数组
let allHotels = [];
// 存储省份数据数组（含id和name）
let provinces = [];
// 存储城市数据映射对象（key: provinceId，value: 城市列表数组）
let citiesMap = {};
// 省份名称到ID的映射对象（key: 省份名称，value: 省份ID）
let provinceNameToId = {};
// 城市名称到ID的映射对象（key: 城市名称，value: 城市ID）
let cityNameToId = {};
// 省份ID到名称的映射对象（key: 省份ID，value: 省份名称）
let provinceIdToName = {};
// 城市ID到名称的映射对象（key: 城市ID，value: 城市名称）
let cityIdToName = {};

/**
 * 页面初始化
 * 功能概述：当DOM加载完成后执行，并行获取省份数据和酒店数据，初始化页面
 */
// 初始化页面，当DOM加载完成后执行
$(document).ready(function() {
    // 显示加载提示
    // 在酒店列表容器中显示加载提示HTML
    $('#hotelList').html('<div id="loading" class="loading">加载中...</div>');

    // 并行获取省份数据和酒店数据
    // 使用jQuery的$.when方法并行执行两个AJAX请求
    $.when(
        // 获取省份数据
        // 使用jQuery的$.ajax方法向后端获取省份数据
        $.ajax({
            url: '/hc/provinces',                               // 请求URL
            method: 'GET',                                      // 请求方法为GET
            dataType: 'json',                                   // 响应数据类型为JSON
            success: function(data) {
                // 成功回调函数，返回响应数据
                return data;
            },
            error: function(xhr, status, error) {
                // 错误回调函数，在酒店列表容器中显示错误提示
                $('#hotelList').html(`<div class="error-message">获取省份数据失败，请刷新页面重试</div>`);
            }
        }),
        // 获取酒店数据
        // 使用jQuery的$.ajax方法向后端获取酒店数据
        $.ajax({
            url: '/hc/allHotels',                               // 请求URL
            method: 'GET',                                      // 请求方法为GET
            dataType: 'json',                                   // 响应数据类型为JSON
            success: function(data) {
                // 成功回调函数，返回响应数据
                return data;
            },
            error: function(xhr, status, error) {
                // 错误回调函数，在酒店列表容器中显示错误提示
                $('#hotelList').html(`<div class="error-message">获取酒店数据失败，请刷新页面重试</div>`);
            }
        })
    ).done(function(provinceResponse, hotelResponse) {
        // 当两个请求都成功完成时执行
        // 提取实际数据
        // 从省份响应中提取实际数据（jQuery AJAX返回的格式为[data, statusText, jqXHR]）
        const provinceData = provinceResponse[0];
        // 从酒店响应中提取实际数据
        const hotelData = hotelResponse[0];

        // 处理响应数据，将省份数据转换为数组格式
        provinces = processResponseData(provinceData);
        // 处理响应数据，将酒店数据转换为数组格式
        allHotels = processResponseData(hotelData);

        // 构建名称和ID的映射关系
        // 调用buildNameIdMaps函数，构建省份和城市的名称与ID的映射关系
        buildNameIdMaps();
        // 初始化省份选择框
        // 调用initProvinceSelect函数，初始化省份选择框和城市选择框
        initProvinceSelect();
        // 渲染酒店列表
        // 调用renderHotelList函数，渲染所有酒店列表
        renderHotelList(allHotels);

        // 绑定筛选按钮事件
        // 为筛选按钮绑定click事件，点击时执行filterHotels函数
        $('#filterBtn').on('click', filterHotels);

        // 滚动事件 - 导航栏吸附效果
        // 为window对象绑定scroll事件，实现导航栏吸附效果
        $(window).scroll(function() {
            // 判断页面滚动距离是否大于100像素
            if ($(this).scrollTop() > 100) {
                // 如果滚动距离大于100像素，为筛选导航栏添加scrolled样式类
                $('#filterNav').addClass('scrolled');
            // 如果滚动距离小于等于100像素
            } else {
                // 从筛选导航栏移除scrolled样式类
                $('#filterNav').removeClass('scrolled');
            }
        });
    }).fail(function() {
        // 当任一请求失败时执行
        // 在酒店列表容器中显示错误提示
        $('#hotelList').html(`<div class="error-message">数据获取失败，请刷新页面重试</div>`);
    });
});

/**
 * 处理响应数据函数
 * 功能概述：将后端返回的响应数据转换为数组格式，统一处理不同格式的响应
 * @param {*} response - 后端返回的响应数据（可能是数组、对象或其他格式）
 * @return {Array} 返回数组格式的数据（如果响应是数组则直接返回，如果是对象则转换为数组，其他情况返回空数组）
 */
// 处理响应数据函数，接收响应数据，返回数组格式的数据
function processResponseData(response) {
    // 判断响应是否为数组
    if (Array.isArray(response)) {
        // 如果响应是数组，直接返回
        return response;
    }
    // 判断响应是否为对象
    if (response && typeof response === 'object') {
        // 如果响应是对象，将其转换为数组（包含该对象的数组）
        return [response];
    }
    // 其他情况，返回空数组
    return [];
}

/**
 * 构建名称和ID的映射关系
 * 功能概述：根据省份和城市数据，构建名称与ID之间的双向映射关系，方便后续查询
 */
// 构建名称和ID的映射关系函数，构建省份和城市的名称与ID的映射关系
function buildNameIdMaps() {
    // 处理省份映射
    // 遍历省份数组，为每个省份构建名称与ID的映射关系
    provinces.forEach(province => {
        // 构建省份名称到ID的映射（key: 省份名称，value: 省份ID）
        provinceNameToId[province.name] = province.id;
        // 构建省份ID到名称的映射（key: 省份ID，value: 省份名称）
        provinceIdToName[province.id] = province.name;
    });

    // 处理城市映射
    // 遍历城市映射对象的所有键（省份ID），为每个省份的城市构建名称与ID的映射关系
    Object.keys(citiesMap).forEach(provinceId => {
        // 获取该省份的城市列表数组
        const cities = citiesMap[provinceId];
        // 遍历城市列表数组，为每个城市构建名称与ID的映射关系
        cities.forEach(city => {
            // 构建城市名称到ID的映射（key: 城市名称，value: 城市ID）
            cityNameToId[city.name] = city.id;
            // 构建城市ID到名称的映射（key: 城市ID，value: 城市名称）
            cityIdToName[city.id] = city.name;
        });
    });
}

/**
 * 初始化省份选择框
 * 功能概述：初始化省份选择框和城市选择框，绑定省份选择变化事件，实现级联选择
 */
// 初始化省份选择框函数，初始化省份和城市选择框
function initProvinceSelect() {
    // 获取省份选择框元素
    const provinceSelect = $('#provinceSelect');
    // 获取城市选择框元素
    const citySelect = $('#citySelect');
    // 获取城市选择框的容器元素（用于显示/隐藏）
    const cityContainer = citySelect.closest('.filter-item');

    // 清空省份选择框并添加默认选项
    // 清空省份选择框内容，然后添加默认选项"请选择省份"
    provinceSelect.empty().append('<option value="">请选择省份</option>');

    // 填充省份选项
    // 遍历省份数组，为每个省份添加选项到省份选择框
    provinces.forEach(province => {
        // 为省份选择框添加选项，值为省份名称，显示文本也为省份名称
        provinceSelect.append(`<option value="${province.name}">${province.name}</option>`);
    });

    // 初始隐藏城市选择框
    // 初始状态下隐藏城市选择框容器
    cityContainer.hide();

    // 省份选择变化事件
    // 为省份选择框绑定change事件，当省份选择变化时执行
    provinceSelect.on('change', function() {
        // 获取选中的省份名称
        const selectedProvinceName = $(this).val();
        // 清空城市选择框内容，然后添加默认选项"请选择城市"
        citySelect.empty().append('<option value="">请选择城市</option>');

        // 判断是否选中了省份
        if (!selectedProvinceName) {
            // 如果没有选中省份，隐藏城市选择框容器
            cityContainer.hide();
            // 返回，不继续执行
            return;
        }

        // 根据省份名称获取省份ID
        const provinceId = provinceNameToId[selectedProvinceName];

        // 如果已有缓存的城市数据，直接使用
        // 判断该省份的城市数据是否已缓存
        if (citiesMap[provinceId]) {
            // 如果已缓存，直接使用缓存的城市数据填充城市选择框
            fillCitySelect(citiesMap[provinceId]);
            // 显示城市选择框容器
            cityContainer.show();
            // 返回，不继续执行
            return;
        }

        // 否则从后端获取城市数据
        // 使用jQuery的$.ajax方法向后端获取城市数据
        $.ajax({
            url: '/hc/cities',                                  // 请求URL
            method: 'GET',                                      // 请求方法为GET
            data: { provinceId: provinceId },                   // 请求参数，包含省份ID
            dataType: 'json',                                   // 响应数据类型为JSON
            success: function(cities) {
                // 成功回调函数，处理响应数据
                // 将城市数据转换为数组格式
                cities = processResponseData(cities);
                // 将城市数据缓存到citiesMap中，键为省份ID
                citiesMap[provinceId] = cities;
                // 使用城市数据填充城市选择框
                fillCitySelect(cities);
                // 显示城市选择框容器
                cityContainer.show();
            },
            error: function() {
                // 错误回调函数，显示错误提示并隐藏城市选择框容器
                alert('获取城市数据失败，请稍后重试');
                cityContainer.hide();
            }
        });
    });

    /**
     * 填充城市选择框
     * 功能概述：根据城市数据数组，填充城市选择框的选项
     * @param {Array} cities - 城市数据数组（包含id和name）
     */
    // 填充城市选择框函数，接收城市数据数组，填充城市选择框
    function fillCitySelect(cities) {
        // 清空城市选择框内容，然后添加默认选项"请选择城市"
        citySelect.empty().append('<option value="">请选择城市</option>');
        // 遍历城市数组，为每个城市添加选项到城市选择框
        cities.forEach(city => {
            // 为城市选择框添加选项，值为城市名称，显示文本也为城市名称
            citySelect.append(`<option value="${city.name}">${city.name}</option>`);
        });
    }
}

/**
 * 星级评分计算函数
 * 功能概述：根据评分值（0-5），计算需要显示的完整星、半星和空星的数量
 * @param {number} rating - 评分值（0-5）
 * @return {Object} 返回包含fullStars（完整星数量）、hasHalfStar（是否有半星）、emptyStars（空星数量）的对象
 */
// 星级评分计算函数，接收评分值，返回星级计算结果
function calculateStars(rating) {
    // 将评分值标准化到0-5范围内（确保不小于0且不大于5）
    const normalizedRating = Math.max(0, Math.min(rating, 5));
    // 将评分值乘以2并四舍五入，得到半星的数量（0-10）
    const halfStars = Math.round(normalizedRating * 2);
    // 计算完整星的数量（半星数量除以2并向下取整）
    const fullStars = Math.floor(halfStars / 2);
    // 判断是否有半星（半星数量是否为奇数）
    const hasHalfStar = halfStars % 2 === 1;
    // 计算空星的数量（总星数5减去完整星数量，如果有半星则再减1）
    const emptyStars = 5 - fullStars - (hasHalfStar ? 1 : 0);
    // 返回星级计算结果对象
    return { fullStars, hasHalfStar, emptyStars };
}

/**
 * 渲染星级评分
 * 功能概述：根据评分值，在指定容器中渲染星级评分图标和评分数字
 * @param {HTMLElement} container - 渲染星级评分的容器元素
 * @param {number} rating - 评分值（0-5）
 */
// 渲染星级评分函数，接收容器元素和评分值，渲染星级评分
function renderStarRating(container, rating) {
    // 清空容器内容
    container.innerHTML = '';
    // 调用calculateStars函数，计算星级评分结果
    const stars = calculateStars(rating);

    // 循环生成完整星图标
    // 循环生成完整星图标（数量为fullStars）
    for (let i = 0; i < stars.fullStars; i++) {
        // 创建星星图标元素
        const star = document.createElement('i');
        // 设置星星图标样式类为实心星
        star.className = 'fa fa-star';
        // 将星星图标添加到容器中
        container.appendChild(star);
    }
    // 判断是否有半星
    if (stars.hasHalfStar) {
        // 如果有半星，创建半星图标元素
        const halfStar = document.createElement('i');
        // 设置半星图标样式类为半星
        halfStar.className = 'fa fa-star-half-o';
        // 将半星图标添加到容器中
        container.appendChild(halfStar);
    }
    // 循环生成空星图标
    // 循环生成空星图标（数量为emptyStars）
    for (let i = 0; i < stars.emptyStars; i++) {
        // 创建空星图标元素
        const emptyStar = document.createElement('i');
        // 设置空星图标样式类为空星
        emptyStar.className = 'fa fa-star-o';
        // 将空星图标添加到容器中
        container.appendChild(emptyStar);
    }
    // 创建评分数字元素
    const ratingSpan = document.createElement('span');
    // 设置评分数字文本内容，保留1位小数
    ratingSpan.textContent = rating.toFixed(1);
    // 将评分数字元素添加到容器中
    container.appendChild(ratingSpan);
}

/**
 * 渲染酒店列表
 * 功能概述：根据酒店数据数组，生成酒店列表的HTML并渲染到页面上，绑定酒店详情按钮事件
 * @param {Array} hotelData - 酒店数据数组（包含酒店的所有信息）
 */
// 渲染酒店列表函数，接收酒店数据数组，渲染酒店列表
function renderHotelList(hotelData) {
    // 获取酒店列表容器元素
    const hotelList = $('#hotelList');
    // 清空酒店列表容器内容
    hotelList.empty();

    // 判断酒店数据数组是否为空
    if (hotelData.length === 0) {
        // 如果为空，在酒店列表容器中显示空状态提示
        hotelList.html('<div class="empty-message">未找到符合条件的酒店，请调整筛选条件</div>');
        // 返回，不继续执行
        return;
    }

    // 更新酒店数量显示
    // 将酒店数量显示到页面上
    $('#count').text(hotelData.length);

    // 遍历酒店数组，为每个酒店生成HTML
    hotelData.forEach(hotel => {
        // 获取酒店ID，如果不存在则使用0
        const id = hotel.id || 0;
        // 获取酒店名称，如果不存在则使用"未命名酒店"
        const name = hotel.name || '未命名酒店';
        // 获取酒店介绍，如果不存在则使用"暂无酒店介绍"
        const content = hotel.content || '暂无酒店介绍';
        // 获取酒店图片URL，如果不存在则使用空字符串
        const img = hotel.img || '';
        // 获取酒店联系电话，如果不存在则使用"暂无联系电话"
        const tel = hotel.tel || '暂无联系电话';
        // 获取酒店地址，如果不存在则使用"暂无地址"
        const address = hotel.address || '暂无地址';
        // 获取酒店等级，如果不存在则使用"未评级"
        const level = hotel.level || '未评级';
        // 获取酒店评分，如果不存在则使用0
        const score = hotel.score !== undefined ? hotel.score : 0;
        // 获取酒店省份，如果不存在则使用"未知省份"
        const province = hotel.province || '未知省份';
        // 获取酒店城市，如果不存在则使用"未知城市"
        const city = hotel.city || '未知城市';
        // 获取酒店最低价格，如果不存在则使用0
        const minPrice = hotel.minPrice !== undefined ? hotel.minPrice : 0;

        // 生成酒店项的HTML字符串，包含酒店图片、名称、等级、位置、介绍、价格和查看详情按钮
        const hotelItem = `
            <div class="hotel-item" data-id="${id}">
                <div class="hotel-content">
                    <div class="hotel-img">
                        <img src="${getImageUrl(img)}" alt="${name}外观">
                    </div>
                    <div class="hotel-info">
                        <div>
                            <div class="hotel-header">
                                <h3 class="hotel-name">${name}</h3>
                                <div class="hotel-rating"></div>
                            </div>
                            <div class="hotel-type">${level}</div>
                            <div class="hotel-location">
                                <i class="fa fa-map-marker"></i>
                                <span>${province} · ${city} · ${address}</span>
                            </div>
                            <div class="hotel-description">
                                ${content}
                            </div>
                        </div>
                        <div class="hotel-footer">
                            <div class="hotel-price">
                                <span class="hotel-current-price">¥${minPrice.toFixed(2)}</span>
                                <span class="hotel-price-unit">/晚起</span>
                            </div>
                            <button class="hotel-details-btn" data-id="${id}">
                                <i class="fa fa-arrow-right mr-1"></i>
                                <a href="/hc/findByMany?id=${id}">查看详情</a>
                            </button>
                        </div>
                    </div>
                </div>
            </div>
        `;

        // 将酒店项HTML添加到酒店列表容器中
        hotelList.append(hotelItem);

        // 获取该酒店的评分容器元素
        const ratingContainer = hotelList.find(`.hotel-item[data-id="${id}"] .hotel-rating`)[0];
        // 调用renderStarRating函数，渲染星级评分
        renderStarRating(ratingContainer, score);

        // 为该酒店的查看详情按钮绑定click事件
        hotelList.find(`.hotel-details-btn[data-id="${id}"]`).on('click', function(e) {
            // 阻止默认行为（链接跳转）
            e.preventDefault();
            // 获取按钮的data-id属性值（酒店ID）
            const hotelId = $(this).data('id');
            // 跳转到酒店详情页面，URL包含酒店ID参数
            window.location.href = `/hc/findByMany?id=${hotelId}`;
        });
    });
}

/**
 * 筛选酒店
 * 功能概述：根据省份、城市、价格范围和酒店类型，筛选酒店列表并重新渲染
 */
// 筛选酒店函数，根据筛选条件筛选酒店列表
function filterHotels() {
    // 获取选中的省份名称
    const selectedProvinceName = $('#provinceSelect').val();
    // 获取选中的城市名称
    const selectedCityName = $('#citySelect').val();
    // 获取选中的价格范围
    const priceRange = $('#priceFilter').val();
    // 获取选中的酒店类型
    const hotelType = $('#typeFilter').val();

    // 初始化筛选后的酒店数组，复制所有酒店数据
    let filteredHotels = [...allHotels];

    // 如果选中了省份，按省份筛选
    if (selectedProvinceName) {
        // 筛选出省份名称匹配的酒店
        filteredHotels = filteredHotels.filter(hotel => hotel.province === selectedProvinceName);
    }
    // 如果选中了城市，按城市筛选
    if (selectedCityName) {
        // 筛选出城市名称匹配的酒店
        filteredHotels = filteredHotels.filter(hotel => hotel.city === selectedCityName);
    }
    // 如果选中了价格范围，按价格范围筛选
    if (priceRange) {
        // 筛选出价格在指定范围内的酒店
        filteredHotels = filteredHotels.filter(hotel => {
            // 获取酒店价格（优先使用minPrice，其次使用price，都不存在则使用0）
            const hotelPrice = parseFloat(hotel.minPrice || hotel.price || 0);
            // 根据价格范围字符串判断价格是否在范围内
            if (priceRange === '0-200') return hotelPrice >= 0 && hotelPrice <= 200;        // 0-200元
            if (priceRange === '200-500') return hotelPrice > 200 && hotelPrice <= 500;    // 200-500元
            if (priceRange === '500-1000') return hotelPrice > 500 && hotelPrice <= 1000;  // 500-1000元
            if (priceRange === '1000-2000') return hotelPrice > 1000 && hotelPrice <= 2000; // 1000-2000元
            if (priceRange === '2000+') return hotelPrice > 2000;                           // 2000元以上
            // 其他情况，返回true（不过滤）
            return true;
        });
    }
    // 如果选中了酒店类型，按酒店类型筛选
    if (hotelType) {
        // 筛选出酒店等级匹配的酒店
        filteredHotels = filteredHotels.filter(hotel => hotel.level === hotelType);
    }

    // 调用renderHotelList函数，渲染筛选后的酒店列表
    renderHotelList(filteredHotels);
}

/**
 * 辅助函数：获取图片URL
 * 功能概述：根据图片路径，返回完整的图片URL（如果是相对路径则添加服务器地址）
 * @param {string} imgPath - 图片路径（相对路径或绝对路径）
 * @return {string} 返回完整的图片URL
 */
// 获取图片URL函数，接收图片路径，返回完整的图片URL
function getImageUrl(imgPath) {
    // 判断图片路径是否存在
    if (!imgPath) {
        // 如果不存在，返回默认图片URL（使用picsum.photos生成随机图片）
        return 'https://picsum.photos/seed/default/800/600';
    }
    // 判断图片路径是否以"http"开头（绝对路径）
    return imgPath.startsWith('http') ? imgPath : `http://localhost:8080${imgPath}`;  // 如果是相对路径，添加服务器地址
}

/**
 * 辅助函数：获取酒店标签HTML
 * 功能概述：根据酒店标签数据，生成酒店标签的HTML字符串
 * @param {Object} hotel - 酒店对象（包含badge和badgeText属性）
 * @return {string} 返回酒店标签的HTML字符串，如果酒店没有标签则返回空字符串
 */
// 获取酒店标签HTML函数，接收酒店对象，返回酒店标签HTML字符串
function getHotelBadgeHtml(hotel) {
    // 判断酒店是否有标签
    if (!hotel.badge) {
        // 如果没有标签，返回空字符串
        return '';
    }
    // 根据标签类型确定标签样式类（'hot'=热门推荐，其他=新上线）
    const badgeClass = hotel.badge === 'hot' ? 'badge-hot' : 'badge-new';
    // 获取标签文本（优先使用badgeText，其次根据badge类型生成默认文本）
    const badgeText = hotel.badgeText || (hotel.badge === 'hot' ? '热门推荐' : '新上线');
    // 返回酒店标签的HTML字符串
    return `<div class="hotel-badge ${badgeClass}">${badgeText}</div>`;
} 
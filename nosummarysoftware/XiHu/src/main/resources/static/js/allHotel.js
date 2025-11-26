// 全局变量
let allHotels = [];             // 存储从后端获取的所有酒店数据
let provinces = [];             // 存储省份数据（含id和name）
let citiesMap = {};             // 存储城市数据映射（key: provinceId, value: 城市列表）
let provinceNameToId = {};      // 省份名称到ID的映射
let cityNameToId = {};          // 城市名称到ID的映射
let provinceIdToName = {};      // 省份ID到名称的映射
let cityIdToName = {};          // 城市ID到名称的映射

// 初始化页面
$(document).ready(function() {
    // 显示加载提示
    $('#hotelList').html('<div id="loading" class="loading">加载中...</div>');

    // 并行获取省份数据和酒店数据
    $.when(
        // 获取省份数据
        $.ajax({
            url: '/hc/provinces',
            method: 'GET',
            dataType: 'json',
            success: function(data) {
                return data;
            },
            error: function(xhr, status, error) {
                $('#hotelList').html(`<div class="error-message">获取省份数据失败，请刷新页面重试</div>`);
            }
        }),
        // 获取酒店数据
        $.ajax({
            url: '/hc/allHotels',
            method: 'GET',
            dataType: 'json',
            success: function(data) {
                return data;
            },
            error: function(xhr, status, error) {
                $('#hotelList').html(`<div class="error-message">获取酒店数据失败，请刷新页面重试</div>`);
            }
        })
    ).done(function(provinceResponse, hotelResponse) {
        // 提取实际数据
        const provinceData = provinceResponse[0];
        const hotelData = hotelResponse[0];

        provinces = processResponseData(provinceData);
        allHotels = processResponseData(hotelData);

        buildNameIdMaps(); // 构建名称和ID的映射关系
        initProvinceSelect(); // 初始化省份选择框
        renderHotelList(allHotels); // 渲染酒店列表

        // 绑定筛选按钮事件
        $('#filterBtn').on('click', filterHotels);

        // 滚动事件 - 导航栏吸附效果
        $(window).scroll(function() {
            if ($(this).scrollTop() > 100) {
                $('#filterNav').addClass('scrolled');
            } else {
                $('#filterNav').removeClass('scrolled');
            }
        });
    }).fail(function() {
        $('#hotelList').html(`<div class="error-message">数据获取失败，请刷新页面重试</div>`);
    });
});

// 处理响应数据函数
function processResponseData(response) {
    if (Array.isArray(response)) {
        return response;
    }
    if (response && typeof response === 'object') {
        return [response];
    }
    return [];
}

// 构建名称和ID的映射关系
function buildNameIdMaps() {
    // 处理省份映射
    provinces.forEach(province => {
        provinceNameToId[province.name] = province.id;
        provinceIdToName[province.id] = province.name;
    });

    // 处理城市映射
    Object.keys(citiesMap).forEach(provinceId => {
        const cities = citiesMap[provinceId];
        cities.forEach(city => {
            cityNameToId[city.name] = city.id;
            cityIdToName[city.id] = city.name;
        });
    });
}

// 初始化省份选择框
function initProvinceSelect() {
    const provinceSelect = $('#provinceSelect');
    const citySelect = $('#citySelect');
    const cityContainer = citySelect.closest('.filter-item');

    // 清空省份选择框并添加默认选项
    provinceSelect.empty().append('<option value="">请选择省份</option>');

    // 填充省份选项
    provinces.forEach(province => {
        provinceSelect.append(`<option value="${province.name}">${province.name}</option>`);
    });

    // 初始隐藏城市选择框
    cityContainer.hide();

    // 省份选择变化事件
    provinceSelect.on('change', function() {
        const selectedProvinceName = $(this).val();
        citySelect.empty().append('<option value="">请选择城市</option>');

        if (!selectedProvinceName) {
            cityContainer.hide();
            return;
        }

        const provinceId = provinceNameToId[selectedProvinceName];

        // 如果已有缓存的城市数据，直接使用
        if (citiesMap[provinceId]) {
            fillCitySelect(citiesMap[provinceId]);
            cityContainer.show();
            return;
        }

        // 否则从后端获取城市数据
        $.ajax({
            url: '/hc/cities',
            method: 'GET',
            data: { provinceId: provinceId },
            dataType: 'json',
            success: function(cities) {
                cities = processResponseData(cities);
                citiesMap[provinceId] = cities;
                fillCitySelect(cities);
                cityContainer.show();
            },
            error: function() {
                alert('获取城市数据失败，请稍后重试');
                cityContainer.hide();
            }
        });
    });

    // 填充城市选择框
    function fillCitySelect(cities) {
        citySelect.empty().append('<option value="">请选择城市</option>');
        cities.forEach(city => {
            citySelect.append(`<option value="${city.name}">${city.name}</option>`);
        });
    }
}

// 星级评分计算函数
function calculateStars(rating) {
    const normalizedRating = Math.max(0, Math.min(rating, 5));
    const halfStars = Math.round(normalizedRating * 2);
    const fullStars = Math.floor(halfStars / 2);
    const hasHalfStar = halfStars % 2 === 1;
    const emptyStars = 5 - fullStars - (hasHalfStar ? 1 : 0);
    return { fullStars, hasHalfStar, emptyStars };
}

// 渲染星级评分
function renderStarRating(container, rating) {
    container.innerHTML = '';
    const stars = calculateStars(rating);

    for (let i = 0; i < stars.fullStars; i++) {
        const star = document.createElement('i');
        star.className = 'fa fa-star';
        container.appendChild(star);
    }
    if (stars.hasHalfStar) {
        const halfStar = document.createElement('i');
        halfStar.className = 'fa fa-star-half-o';
        container.appendChild(halfStar);
    }
    for (let i = 0; i < stars.emptyStars; i++) {
        const emptyStar = document.createElement('i');
        emptyStar.className = 'fa fa-star-o';
        container.appendChild(emptyStar);
    }
    const ratingSpan = document.createElement('span');
    ratingSpan.textContent = rating.toFixed(1);
    container.appendChild(ratingSpan);
}

// 渲染酒店列表
function renderHotelList(hotelData) {
    const hotelList = $('#hotelList');
    hotelList.empty();

    if (hotelData.length === 0) {
        hotelList.html('<div class="empty-message">未找到符合条件的酒店，请调整筛选条件</div>');
        return;
    }

    $('#count').text(hotelData.length);

    hotelData.forEach(hotel => {
        const id = hotel.id || 0;
        const name = hotel.name || '未命名酒店';
        const content = hotel.content || '暂无酒店介绍';
        const img = hotel.img || '';
        const tel = hotel.tel || '暂无联系电话';
        const address = hotel.address || '暂无地址';
        const level = hotel.level || '未评级';
        const score = hotel.score !== undefined ? hotel.score : 0;
        const province = hotel.province || '未知省份';
        const city = hotel.city || '未知城市';
        const minPrice = hotel.minPrice !== undefined ? hotel.minPrice : 0;

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

        hotelList.append(hotelItem);

        const ratingContainer = hotelList.find(`.hotel-item[data-id="${id}"] .hotel-rating`)[0];
        renderStarRating(ratingContainer, score);

        hotelList.find(`.hotel-details-btn[data-id="${id}"]`).on('click', function(e) {
            e.preventDefault();
            const hotelId = $(this).data('id');
            window.location.href = `/hc/findByMany?id=${hotelId}`;
        });
    });
}

// 筛选酒店
function filterHotels() {
    const selectedProvinceName = $('#provinceSelect').val();
    const selectedCityName = $('#citySelect').val();
    const priceRange = $('#priceFilter').val();
    const hotelType = $('#typeFilter').val();

    let filteredHotels = [...allHotels];

    if (selectedProvinceName) {
        filteredHotels = filteredHotels.filter(hotel => hotel.province === selectedProvinceName);
    }
    if (selectedCityName) {
        filteredHotels = filteredHotels.filter(hotel => hotel.city === selectedCityName);
    }
    if (priceRange) {
        filteredHotels = filteredHotels.filter(hotel => {
            const hotelPrice = parseFloat(hotel.minPrice || hotel.price || 0);
            if (priceRange === '0-200') return hotelPrice >= 0 && hotelPrice <= 200;
            if (priceRange === '200-500') return hotelPrice > 200 && hotelPrice <= 500;
            if (priceRange === '500-1000') return hotelPrice > 500 && hotelPrice <= 1000;
            if (priceRange === '1000-2000') return hotelPrice > 1000 && hotelPrice <= 2000;
            if (priceRange === '2000+') return hotelPrice > 2000;
            return true;
        });
    }
    if (hotelType) {
        filteredHotels = filteredHotels.filter(hotel => hotel.level === hotelType);
    }

    renderHotelList(filteredHotels);
}

// 辅助函数：获取图片URL
function getImageUrl(imgPath) {
    if (!imgPath) return 'https://picsum.photos/seed/default/800/600';
    return imgPath.startsWith('http') ? imgPath : `http://localhost:8080${imgPath}`;
}

// 辅助函数：获取酒店标签HTML
function getHotelBadgeHtml(hotel) {
    if (!hotel.badge) return '';
    const badgeClass = hotel.badge === 'hot' ? 'badge-hot' : 'badge-new';
    const badgeText = hotel.badgeText || (hotel.badge === 'hot' ? '热门推荐' : '新上线');
    return `<div class="hotel-badge ${badgeClass}">${badgeText}</div>`;
}
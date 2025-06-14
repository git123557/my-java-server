let map;
let trackLine;
let isTracking = false;
let positionInterval;
let trackPoints = [];

// 初始化地图
function initMap() {
    map = new AMap.Map('container', {
        zoom: 15,
        center: [116.397428, 39.90923]
    });

    // 添加定位控件
    AMap.plugin('AMap.Geolocation', function() {
        const geolocation = new AMap.Geolocation({
            enableHighAccuracy: true,
            timeout: 10000,
            buttonPosition: 'RB',
            buttonOffset: new AMap.Pixel(10, 20),
            zoomToAccuracy: true
        });
        map.addControl(geolocation);
    });

    // 开始记录按钮
    document.getElementById('startTrack').addEventListener('click', function() {
        isTracking = !isTracking;
        this.textContent = isTracking ? '停止记录' : '开始记录';

        if (isTracking) {
            startTracking();
        } else {
            stopTracking();
        }
    });

    // 清除轨迹按钮
    document.getElementById('clearTrack').addEventListener('click', function() {
        if (trackLine) {
            map.remove(trackLine);
            trackLine = null;
        }
        trackPoints = [];

        // 发送清除请求到后端
        fetch('/api/map/clearTrack', {
            method: 'POST'
        });
    });
}

// 开始记录轨迹
function startTracking() {
    positionInterval = setInterval(() => {
        AMap.plugin('AMap.Geolocation', function() {
            const geolocation = new AMap.Geolocation({
                enableHighAccuracy: true,
                timeout: 10000
            });

            geolocation.getCurrentPosition(function(status, result) {
                if (status === 'complete') {
                    const position = [result.position.lng, result.position.lat];
                    trackPoints.push(position);
                    updateTrackLine();

                    // 发送位置到后端
                    fetch(`/api/map/addPoint?lng=${result.position.lng}&lat=${result.position.lat}`, {
                        method: 'POST'
                    });
                }
            });
        });
    }, 5000); // 每5秒记录一次位置
}

// 停止记录轨迹
function stopTracking() {
    if (positionInterval) {
        clearInterval(positionInterval);
    }
}

// 更新轨迹线
function updateTrackLine() {
    if (trackPoints.length < 2) return;

    if (trackLine) {
        map.remove(trackLine);
    }

    trackLine = new AMap.Polyline({
        path: trackPoints,
        isOutline: true,
        outlineColor: '#ffeeff',
        borderWeight: 1,
        strokeColor: "#3366FF",
        strokeOpacity: 1,
        strokeWeight: 6,
        lineJoin: 'round',
        lineCap: 'round',
        zIndex: 50
    });

    map.add(trackLine);
    map.setFitView();
}

// 初始化
window.onload = initMap;
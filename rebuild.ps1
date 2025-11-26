# 停止并重新构建项目脚本
# 使用方法：.\rebuild.ps1

Write-Host "========================================" -ForegroundColor Cyan
Write-Host "  停止并重新构建项目" -ForegroundColor Cyan
Write-Host "========================================" -ForegroundColor Cyan
Write-Host ""

# 1. 检查并停止正在运行的 Java 进程
Write-Host "[1/3] 检查并停止正在运行的 Java 进程..." -ForegroundColor Yellow
$javaProcesses = Get-Process -Name java -ErrorAction SilentlyContinue
if ($javaProcesses) {
    Write-Host "  发现 $($javaProcesses.Count) 个 Java 进程，正在停止..." -ForegroundColor Yellow
    $javaProcesses | ForEach-Object {
        Write-Host "    停止进程: PID $($_.Id) - $($_.ProcessName)" -ForegroundColor Gray
        Stop-Process -Id $_.Id -Force -ErrorAction SilentlyContinue
    }
    Start-Sleep -Seconds 2
    Write-Host "  ✓ Java 进程已停止" -ForegroundColor Green
} else {
    Write-Host "  ✓ 没有发现正在运行的 Java 进程" -ForegroundColor Green
}

# 2. 检查 8080 端口
Write-Host ""
Write-Host "[2/3] 检查 8080 端口..." -ForegroundColor Yellow
$port = netstat -ano | findstr :8080
if ($port) {
    $pid = ($port -split '\s+')[-1]
    Write-Host "  发现占用 8080 端口的进程 (PID: $pid)，正在停止..." -ForegroundColor Yellow
    Stop-Process -Id $pid -Force -ErrorAction SilentlyContinue
    Start-Sleep -Seconds 2
    Write-Host "  ✓ 端口 8080 已释放" -ForegroundColor Green
} else {
    Write-Host "  ✓ 端口 8080 未被占用" -ForegroundColor Green
}

# 3. 重新构建项目
Write-Host ""
Write-Host "[3/3] 开始重新构建项目..." -ForegroundColor Yellow
Write-Host "  执行: mvn clean package -DskipTests" -ForegroundColor Gray
Write-Host ""

mvn clean package -DskipTests

if ($LASTEXITCODE -eq 0) {
    Write-Host ""
    Write-Host "========================================" -ForegroundColor Green
    Write-Host "  ✓ 构建成功！" -ForegroundColor Green
    Write-Host "========================================" -ForegroundColor Green
} else {
    Write-Host ""
    Write-Host "========================================" -ForegroundColor Red
    Write-Host "  ✗ 构建失败！" -ForegroundColor Red
    Write-Host "========================================" -ForegroundColor Red
    exit 1
}


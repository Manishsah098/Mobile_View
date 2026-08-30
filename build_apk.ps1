$env:JAVA_HOME = 'C:\Program Files\Android\Android Studio\jbr'
& 'C:\Users\Lenovo\.gradle\wrapper\dists\gradle-8.2-bin\bbg7u40eoinfdyxsxr3z4i7ta\gradle-8.2\bin\gradle.bat' assembleDebug

if ($LASTEXITCODE -eq 0) {
    $src = 'app\build\outputs\apk\debug\app-debug.apk'
    $dst = 'C:\Users\Lenovo\OneDrive\Desktop\Atlantis_The_Royal_Guest_Experience.apk'
    Copy-Item $src $dst -Force
    $f = Get-Item $dst
    Write-Host "SUCCESS - APK Updated on Desktop!"
    Write-Host ("Size: " + [math]::Round($f.Length / 1MB, 2) + " MB")
    Write-Host ("Time: " + $f.LastWriteTime)
} else {
    Write-Host "BUILD FAILED"
}

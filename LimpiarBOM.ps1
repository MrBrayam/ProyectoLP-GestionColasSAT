

$rutaProyecto = "c:\Users\braya\OneDrive\Escritorio\Proyecto LP - GestionColas SAT\src\main\java"

Write-Host "Limpiando caracteres BOM de archivos Java..."
Write-Host "Ruta del proyecto: $rutaProyecto"
Write-Host ""

$archivosJava = Get-ChildItem -Path $rutaProyecto -Recurse -Filter "*.java"

$contadorLimpiados = 0

foreach ($archivo in $archivosJava) {
    Write-Host "Procesando: $($archivo.Name)"
    
    try {
        $bytes = [System.IO.File]::ReadAllBytes($archivo.FullName)

        if ($bytes.Length -ge 3 -and $bytes[0] -eq 0xEF -and $bytes[1] -eq 0xBB -and $bytes[2] -eq 0xBF) {
            Write-Host "  -> BOM encontrado, eliminando..." -ForegroundColor Yellow
            
            $sinBom = $bytes[3..($bytes.Length-1)]
            
            [System.IO.File]::WriteAllBytes($archivo.FullName, $sinBom)
            
            $contadorLimpiados++
            Write-Host "  -> Limpiado exitosamente" -ForegroundColor Green
        } else {
            Write-Host "  -> Sin BOM, no requiere limpieza" -ForegroundColor Gray
        }
    }
    catch {
        Write-Host "  -> Error al procesar archivo: $($_.Exception.Message)" -ForegroundColor Red
    }
}

Write-Host ""
Write-Host "Proceso completado:"
Write-Host "- Archivos procesados: $($archivosJava.Count)"
Write-Host "- Archivos limpiados: $contadorLimpiados"
Write-Host ""

if ($contadorLimpiados -gt 0) {
    Write-Host "¡Limpieza completada! Ahora puedes compilar el proyecto." -ForegroundColor Green
} else {
    Write-Host "No se encontraron archivos con BOM." -ForegroundColor Blue
}

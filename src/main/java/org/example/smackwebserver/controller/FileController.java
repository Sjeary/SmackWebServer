package org.example.smackwebserver.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/files")
public class FileController {

    // 从 application.properties 或 application.yml 中读取文件存储路径
    @Value("${file.upload-dir:file_storage}")  // 默认为 "file_storage"
    private String fileStoragePath;

    // 文件上传接口
    @PostMapping("/upload")
    public ResponseEntity<Map<String, Object>> uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
        // 确保文件存储目录存在
        Path storagePath = Paths.get(fileStoragePath);
        if (!Files.exists(storagePath)) {
            Files.createDirectories(storagePath);  // 创建文件夹
        }

        // 获取文件名和文件扩展名
        String fileName = file.getOriginalFilename();
        String fileExtension = fileName.substring(fileName.lastIndexOf("."));

        // 生成唯一的文件名，避免文件名冲突
        String uniqueFileName = UUID.randomUUID().toString() + fileExtension;

        // 保存文件到本地存储路径
        Path path = storagePath.resolve(uniqueFileName);
        Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

        // 返回文件的存储路径（前端可以使用该 URL 下载文件）
        Map<String, Object> response = new HashMap<>();
        response.put("fileUrl", "/api/v1/files/" + uniqueFileName);

        return ResponseEntity.ok(response);
    }

    // 文件下载接口
    @GetMapping("/{fileName}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName) throws MalformedURLException {
        // 获取文件存储路径
        Path filePath = Paths.get(fileStoragePath).resolve(fileName);
        System.out.println(filePath);
        // 获取文件资源
        Resource resource = new UrlResource(filePath.toUri());

        // 检查文件是否存在
        if (resource.exists()) {
            // 如果文件存在，返回下载响应
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                    .body(resource);
        } else {
            // 如果文件不存在，返回 404 响应
            return ResponseEntity.notFound().build();
        }
    }
}

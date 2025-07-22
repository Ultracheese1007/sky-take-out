package com.sky.controller.admin;

import com.sky.constant.MessageConstant;
import com.sky.result.Result;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.UUID;

@RestController  //标记为 REST 控制器
@RequestMapping("/admin/common")   //接口统一以 /admin/common 开头
@Api(tags = "文件上传")  //Swagger 用于接口文档分组
@Slf4j
public class CommonController {

    // ✅ macOS 文件路径，请确认路径存在
    private static final String FILE_UPLOAD_PATH = "/Users/maxinmei/Documents/《苍穹外卖》/资料/day03/图片资源/";

    @PostMapping("/upload")  //POST 请求接口 /admin/common/upload
    @ResponseBody
    public Result uploadfile(@RequestParam("file") MultipartFile file) throws IOException {  //接收前端传来的文件（字段名必须为 file）
        if (file.isEmpty()) { //校验文件是否为空
            return Result.error("文件不能为空");
        }

        File dir = new File(FILE_UPLOAD_PATH); //检查目标文件夹是否存在
        //如果上传目录不存在，就创建文件夹
        //mkdirs() 会自动递归创建多级目录
        if (!dir.exists() || !dir.isDirectory()) {
            boolean created = dir.mkdirs();
            if (created) {
                log.info("创建文件夹成功: {}", FILE_UPLOAD_PATH);
            } else {
                log.warn("创建文件夹失败或已经存在: {}", FILE_UPLOAD_PATH);
            }
        }

        //检查上传的文件是否是 .png/.jpg/.jpeg 图片格式
        //不合法的后缀直接拒绝上传
        String originalFilename = file.getOriginalFilename();
        if (originalFilename == null || originalFilename.isEmpty()) {
            return Result.error("文件名无效");
        }

        // 获取后缀
        String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        if (!extension.equalsIgnoreCase(".png") &&
                !extension.equalsIgnoreCase(".jpg") &&
                !extension.equalsIgnoreCase(".jpeg")) {
            return Result.error("文件格式不支持");
        }

        // 生成新文件名：避免文件名冲突，使用随机 UUID 生成新名称
        originalFilename = UUID.randomUUID().toString() + extension;

        // 确保路径安全
        //使用 Files.copy() 将文件内容保存到本地
        //REPLACE_EXISTING 表示同名文件会被覆盖
        Path targetLocation = Paths.get(FILE_UPLOAD_PATH).resolve(originalFilename).normalize();
        try {
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            log.info("文件上传成功: {}", originalFilename);
        } catch (IOException e) {
            log.error("文件上传失败: {}", originalFilename, e);
            return Result.error(MessageConstant.UPLOAD_FAILED);
        }

        // 返回可访问链接
        String fileUrl = "http://localhost:8080/static/" + originalFilename;
        return Result.success(fileUrl);
    }
}

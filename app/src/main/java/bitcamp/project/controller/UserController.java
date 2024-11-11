package bitcamp.project.controller;

import bitcamp.project.annotation.LoginUser;
import bitcamp.project.service.StorageService;
import bitcamp.project.service.UserService;
import bitcamp.project.service.impl.FileServiceImpl;
import bitcamp.project.vo.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    private StorageService storageService;

    private String folderName = "user/";

    @GetMapping("list")
    public List<User> list() throws Exception{
        List<User> users =  userService.list();
        return users;
    }

    @GetMapping("finduser")
    public ResponseEntity<?> getUser(@LoginUser User loginUser) throws Exception {
        try {
            User user = userService.findUser(loginUser.getId());
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            return ResponseEntity.ok("유저 정보를 가지고오는데 문제가 생겼습니다");
        }
    }

    @PostMapping("update")
    public boolean update(
            @RequestParam("id") int id,
            @RequestParam("nickname") String nickname,
            @RequestParam("password") String password,
            @RequestParam(value = "file", required = false) MultipartFile file) throws Exception {

        User old = userService.findUser(id);

        // 프로필 이미지 파일 처리
        if (file != null && file.getSize() > 0) {
            storageService.delete(folderName + old.getPath());

            String filename = UUID.randomUUID().toString();
            HashMap<String, Object> options = new HashMap<>();
            options.put(StorageService.CONTENT_TYPE, file.getContentType());
            storageService.upload(folderName + filename,
                    file.getInputStream(),
                    options);

            // 사용자 객체에 파일 경로 설정
            old.setPath(filename);
        }

        // 사용자 정보 업데이트
        old.setNickname(nickname);
        if (password != null) {
            old.setPassword(userService.encodePassword(password));
        }
        return userService.update(id, old);
    }

    @DeleteMapping("delete/{id}")
    public boolean delete(@PathVariable int id) throws Exception{
        User old = userService.findUser(id);
        if (userService.delete(id)) {
            storageService.delete(folderName + old.getPath());
            return true;
        }else {
            return false;
        }
    }

    @PostMapping("userauthentication")
    public Boolean userAuthentication(@LoginUser User loginUser, @RequestBody Map<String, String> getPassword)throws Exception{
        String password = getPassword.get("password");

        return userService.userAuthentication(loginUser.getId(), password);
    }


}

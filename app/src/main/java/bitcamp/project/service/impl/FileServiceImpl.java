package bitcamp.project.service.impl;

import bitcamp.project.vo.AttachedFile;
import com.amazonaws.services.s3.model.ObjectMetadata;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.UUID;

public class FileServiceImpl {

    public String saveFile(MultipartFile file)throws Exception{
        // 파일 이름을 UUID로 생성
        String fileName = UUID.randomUUID().toString() + ".png";

        // 현재 실행 중인 디렉토리를 기준으로 파일 저장 경로 설정
        String uploadDir = System.getProperty("user.dir") + "\\src\\main\\frontend\\public\\images\\";
        System.out.println("---------------------------------------------------------");
        System.out.println("업로드 디렉토리: " + uploadDir);
        System.out.println("---------------------------------------------------------");

        // 디렉토리가 없으면 생성
        File directory = new File(uploadDir);
        if (!directory.exists()) {
            boolean created = directory.mkdirs(); // 디렉토리 생성 시도
            if (created) {
                System.out.println("디렉토리가 생성되었습니다: " + uploadDir);
            } else {
                System.out.println("디렉토리 생성 실패: " + uploadDir);
            }
        }

        // 파일을 해당 경로에 저장
        String filePath = uploadDir + fileName;
        file.transferTo(new File(filePath));

        System.out.println("파일이 저장되었습니다: " + filePath);
        return fileName;
    }

    public void deleteFile(String filePath)throws Exception{
        // 파일 경로 설정
        String allFilePath = System.getProperty("user.dir") + "\\src\\main\\frontend\\public\\images\\";

        // 파일 삭제
        File file = new File(allFilePath);
        if (file.exists()) {
            boolean deleted = file.delete(); // 파일 삭제 시도
            if (deleted) {
                System.out.println("파일이 성공적으로 삭제되었습니다: " + allFilePath);
            } else {
                System.out.println("파일 삭제에 실패했습니다: " + allFilePath);
            }
        } else {
            System.out.println("삭제할 파일이 존재하지 않습니다: " + allFilePath);
        }
    }


    public String upload(MultipartFile file) throws Exception {
        // Content Type을 알아내기
        String contentType = file.getContentType();
        String extension = "";
        if (contentType != null) {
            switch (contentType) {
                case "image/jpeg":
                    extension = ".jpg";
                    break;
                case "image/png":
                    extension = ".png";
                    break;
                default:
                    throw new Exception("지원하지 않는 확장자입니다 : " + contentType);
            }
        }

        // 파일 이름을 UUID로 생성
        String fileName = UUID.randomUUID().toString() + extension;

        AttachedFile attachedFile = new AttachedFile();
        attachedFile.setOriginFilename(file.getOriginalFilename());


        // 현재 실행 중인 디렉토리를 기준으로 파일 저장 경로 설정
        String uploadDir = System.getProperty("user.dir") + "\\src\\main\\frontend\\public\\images\\" + "photos\\";
        System.out.println("---------------------------------------------------------");
        System.out.println("업로드 디렉토리: " + uploadDir);
        System.out.println("---------------------------------------------------------");

        // 디렉토리가 없으면 생성
        File directory = new File(uploadDir);
        if (!directory.exists()) {
            boolean created = directory.mkdirs(); // 디렉토리 생성 시도
            if (created) {
                System.out.println("디렉토리가 생성되었습니다: " + uploadDir);
            } else {
                System.out.println("디렉토리 생성 실패: " + uploadDir);
            }
        }

        // 파일을 해당 경로에 저장
        String filePath = uploadDir + fileName;
        file.transferTo(new File(filePath));

        System.out.println("파일이 저장되었습니다: " + filePath);
        return fileName;
    }


    public void deletePhoto(String fileName) throws Exception {
        // 파일 경로 설정
        String allFilePath = System.getProperty("user.dir") + "\\src\\main\\frontend\\public\\images\\" + fileName;

        // 파일 삭제
        File file = new File(allFilePath);
        if (file.exists()) {
            boolean deleted = file.delete(); // 파일 삭제 시도
            if (deleted) {
                System.out.println("파일이 성공적으로 삭제되었습니다: " + allFilePath);
            } else {
                System.out.println("파일 삭제에 실패했습니다: " + allFilePath);
                throw new Exception("파일 삭제에 실패했습니다: ");
            }
        } else {
            System.out.println("삭제할 파일이 존재하지 않습니다: " + allFilePath);
            throw new Exception("삭제할 파일이 존재하지 않습니다");
        }
    }
}

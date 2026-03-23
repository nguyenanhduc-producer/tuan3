package org.example.tuan3.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
@RestControllerAdvice
public class GlobalExceptionHandler {
    // Xử lý lỗi khi dữ liệu đầu vào không hợp lệ (ví dụ: ID bị trống mà lúc nãy ta viết ở UserService)
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgument(IllegalArgumentException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST); // Trả về lỗi 400
    }

    // Xử lý tất cả các lỗi vặt khác chưa biết tên
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGeneralException(Exception ex) {
        return new ResponseEntity<>("Có lỗi xảy ra trên hệ thống: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR); // Trả về lỗi 500
    }
}

package org.example.tuan3.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
@Getter
public enum ErrorCode {
    UNCATEGORIZED_EXCEPTION(9999, "Lỗi hệ thống chưa xác định", HttpStatus.INTERNAL_SERVER_ERROR),
    USER_EXISTED(1001, "Người dùng đã tồn tại", HttpStatus.BAD_REQUEST),
    USER_NOT_EXISTED(1002, "Người dùng không tồn tại", HttpStatus.NOT_FOUND),
    TASK_NOT_FOUND(1003, "Công việc không tồn tại", HttpStatus.NOT_FOUND),
    INVALID_KEY(1004, "Key lỗi không hợp lệ", HttpStatus.BAD_REQUEST),
    PROJECT_NOT_FOUND(1005, "Dự án không tồn tại", HttpStatus.NOT_FOUND),
    PROJECT_ID_REQUIRED(1006, "Bạn phải nhập ID của Dự án", HttpStatus.BAD_REQUEST),
    TASK_ALREADY_DONE(1007, "Công việc đã hoàn thành, không thể thay đổi", HttpStatus.BAD_REQUEST)
    ;

    private int code;
    private String message;
    private HttpStatusCode statusCode;

    ErrorCode(int code, String message, HttpStatusCode statusCode) {
        this.code = code;
        this.message = message;
        this.statusCode = statusCode;
    }
}

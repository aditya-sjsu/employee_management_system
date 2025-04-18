package com.example.employeemanagementsystem.DTO;
import java.time.LocalDateTime;
import java.util.Date;

public class ErrorResponseDTO {
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    private String date;
    private String message;

    public ErrorResponseDTO(Date date, String message) {
        this.date = date.toString();
        this.message = message;
    }
}

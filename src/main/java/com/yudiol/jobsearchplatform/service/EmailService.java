package com.yudiol.jobsearchplatform.service;

public interface EmailService {
    void sendSimpleEmail(String toAddress, String subject, String message);
}

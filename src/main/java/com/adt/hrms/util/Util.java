package com.adt.hrms.util;

import org.springframework.stereotype.Component;

@Component
public class Util {

    public static final String DOCUMENT_UPLOAD_REMINDER = "Dear [Name],\n\n" +
            "I hope this message finds you well. We kindly remind you that the following documents have not been uploaded yet:\n\n"+
            "[valuesList]\n\n" +
            "Please upload these documents at your earliest convenience to ensure our records are up-to-date.\n\n" +
            "Thank you for your cooperation and continued dedication to our team.\n\n"+
            "Best regards,\n" +
            "[Your Name]\n" +
            "[Company Name]";

}

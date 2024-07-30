package com.adt.hrms.scheduler;

import com.adt.hrms.model.DocumentType;
import com.adt.hrms.model.Employee;
import com.adt.hrms.model.EmployeeDocument;
import com.adt.hrms.repository.DocumentTypeRepo;
import com.adt.hrms.repository.EmployeeDocumentRepo;
import com.adt.hrms.repository.EmployeeRepo;
import com.adt.hrms.service.impl.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class DocumentReminderService {

    @Autowired
    private EmployeeDocumentRepo employeeDocumentRepo;

    @Autowired
    private DocumentTypeRepo documentTypeRepo;

    @Autowired
    private EmployeeRepo employeeRepo;

    @Autowired
    private EmailService emailService;

    @Scheduled(cron = "0 0 12 */3 * ?")
    public void sendDocumentReminders() {
        List<Employee> employees = employeeRepo.findAll();
        List<DocumentType> documentTypes = documentTypeRepo.getByDocumentTypeData();
        for (Employee employee : employees) {
            List<String> missingDocuments = new ArrayList<>();
            for (DocumentType documentType : documentTypes) {
                if (!isDocumentUploaded(employee.getEmployeeId(), documentType.getId())) {
                    missingDocuments.add(documentType.getDocumentType());
                }
            }

            if (!missingDocuments.isEmpty()) {
                emailService.sendEmail(employee.getEmail(), employee.getFirstName(), missingDocuments);
            }
        }
    }

    public boolean isDocumentUploaded(int empId, int docTypeId) {
        Optional<EmployeeDocument> document = employeeDocumentRepo.findDocumentByDocTypeIdAndEmployeeId(empId, docTypeId);
        return document.isPresent();
    }

}


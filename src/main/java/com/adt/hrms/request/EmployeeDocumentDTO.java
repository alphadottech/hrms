package com.adt.hrms.request;

import com.adt.hrms.model.DocumentType;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDocumentDTO {

    private int id;
    private int empId;
    private int docTypeId;
}

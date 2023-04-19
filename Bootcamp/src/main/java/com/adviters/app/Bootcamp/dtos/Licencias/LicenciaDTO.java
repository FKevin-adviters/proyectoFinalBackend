package com.adviters.app.Bootcamp.dtos.Licencias;

import java.util.Date;

public class LicenciaDTO {
    private Long licenseId;
    private Long licenseTypeId;
    private String status;
    private Date startDate;
    private Date endDate;

    public Long getLicenseTypeId() {
        return licenseTypeId;
    }

    public void setLicenseTypeId(Long licenseTypeId) {
        this.licenseTypeId = licenseTypeId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Long getLicenseId() {
        return licenseId;
    }

    public void setLicenseId(Long licenseId) {
        this.licenseId = licenseId;
    }
}
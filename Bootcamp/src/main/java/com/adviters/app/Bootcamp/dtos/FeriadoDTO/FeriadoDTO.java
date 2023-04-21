package com.adviters.app.Bootcamp.dtos.FeriadoDTO;

import java.util.Date;

public class FeriadoDTO {
        private Date date;
        private String description;

        public FeriadoDTO(Date date, String description) {
            this.date = date;
            this.description = description;
        }

        public Date getDate() {
            return date;
        }

        public void setDate(Date date) {
            this.date = date;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }
    }

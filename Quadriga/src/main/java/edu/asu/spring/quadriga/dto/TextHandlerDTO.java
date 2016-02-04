package edu.asu.spring.quadriga.dto;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

public class TextHandlerDTO {

    @Entity
    @Table(name = "tbl_textfiles")
    public class TextFileEntity implements Serializable {
        private static final long serialVersionUID = -1798070786993154676L;

        @Id
        @JoinColumn(name = "projectid", referencedColumnName = "project_id")
        @Column(name = "projectid")
        private String projectid;
        @Basic(optional = false)
        @Column(name = "refid")
        private String refid;
        @Basic(optional = false)
        @Column(name = "filename")
        private String filename;

        public String getProjectid() {
            return projectid;
        }

        public void setProjectid(String project) {
            this.projectid = project;
        }

        public String getRefid() {
            return refid;
        }

        public void setRefid(String refid) {
            this.refid = refid;
        }

        public String getFilename() {
            return filename;
        }

        public void setFilename(String filename) {
            this.filename = filename;
        }

    }

}

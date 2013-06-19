package edu.asu.spring.quadriga.domain.rest;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import edu.asu.spring.quadriga.domain.IProject;
import edu.asu.spring.quadriga.domain.rest.RestProject;



@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
 "projectList"
})
@XmlRootElement(name = "projectList")
public class RestProjectList {
	 
	 @XmlElement(required = true, namespace="http://www.digitalhps.org/")
	 protected List<RestProject> projectList;
	 
	 public List<RestProject> getProjectList() {
	     return this.projectList;
	 }
	 public void SetProjectList(List<RestProject> projectList) {
	     this.projectList=projectList;
	 }
	 
	 public void copyProjectList(List<IProject> projectList){
		 Iterator <IProject> I = projectList.iterator();
		 this.projectList = new ArrayList<RestProject>();
		 while(I.hasNext()){
			 IProject p = I.next();
			 RestProject r = new RestProject();
			 r.setName(p.getName());
			 r.setDescription(p.getDescription());
			 r.setId(p.getId());
			 this.projectList.add(r);
		 }
	 }
}

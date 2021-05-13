package common;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class SubjectCollection implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private List<String> subjects;
	
	private List<String> courses;

	private Map<String, List<String>> courseListMap;

	private Map<String, String> subjectMap;

	public String pasrseCourseCode(String course) {
		return subjectMap.get(course);
	}

	public Map<String, List<String>> getCourseListMap() {
		return courseListMap;
	}

	public void setCourseListMap(Map<String, List<String>> courseListMap) {
		this.courseListMap = courseListMap;
	}

	public Map<String, String> getSubjectMap() {
		return subjectMap;
	}

	public void setSubjectMap(Map<String, String> subjectMap) {
		this.subjectMap = subjectMap;
	}

	public List<String> getSubjects() {
		return subjects;
	}

	public void setSubjects(List<String> subjects) {
		this.subjects = subjects;
	}

	public List<String> getCourses() {
		return courses;
	}

	public void setCourses(List<String> courses) {
		this.courses = courses;
	}
	
	
	
	

}

package common;

import java.io.Serializable;
import java.util.List;

import models.WordFile;
import javafx.scene.chart.XYChart.Series;
/**
 * Generic class that wrap all of CEMS Projects model, in order to make
 * transfers between client and server available and indicate which operation
 * has been made.
 * 
 * @author Arikz ,Dvir ben simon
 *
 * @param <E> can be any class model in project {Test,Question,Teacher,...etc}
 */
public class ModelWrapper<E> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Store class model element, in order to transfer it via client/server
	 */
	private E element;
	/**
	 * Store list of class model element, in order to transfer it via client/server
	 */
	private List<E> elements;

	private E[] elements2;

	private WordFile file;
	/**
	 * Which operation should be executed in server/client
	 */
	private Operation operation;

	public enum Operation {
		CREATE_EXAM, UPDATE_TEST, ENTERED_WRONG_ID, ERROR, LOAD_TEST, LOAD_QUESTION_LIST, OVERALL_STATISTICS,
		STATISTIC_BY_COURSE_X,STATISTIC_BY_COURSE_Y, STATISTIC_BY_TEACHER, STATISTIC_BY_STUDENT,
		EXAM_EXTENSION_REQUEST, EXAM_EXECUTE, CREATE_QUESTION, START_EXAM, TEST_STATISTICS, GET_USER,
		GET_SUBJECT_COURSE_LIST, GET_COURSE_LIST, GET_QUESTION_LIST, GET_EXAMS_LIST_BY_SUBJECT,
		GET_EXAMS_LIST_BY_COURSE, GET_EXAMS_LIST, CLOSE_EXAM, GET_QUESTION_LIST_BY_SUBJECT, EXTENSION_REQUEST,
		UPLOAD_FILE, GET_EXAM_ID, GET_QUESTION_LIST_BY_EXAM_ID, INSERT_STUDENT_TO_EXAM, GET_EXAM_BY_EXAM_ID,
		START_EXAM_SUCCESS, START_EXAM_FAILD, GET_EXECUTED_EXAM_LIST_BY_CREATOR, ERROR_INSERT_STUDENT_TO_EXAM,
		STUDENT_TIME_EXTENSION, INSERT_STUDENT_GRADE, INSERT_STUDENT_ANSWERS, GET_EXECUTED_EXAM_LIST_BY_EXECUTOR,
		GET_EXAM_IN_PROCESS, GET_EXECUTED_EXAM_STUDENT_LIST
	};

	/**
	 * All of server/client operation
	 */

	/**
	 * Constructor of creating model wrapper with one element
	 * 
	 * @param operation project operation that's need to be executed
	 */
	public ModelWrapper(Operation operation) {
		this.operation = operation;
	}

	/**
	 * Constructor of creating model wrapper with one element
	 * 
	 * @param element   project model that's need to be wrapped
	 * @param operation project operation that's need to be executed
	 */
	public ModelWrapper(E element, Operation operation) {
		this.element = element;
		this.operation = operation;
	}

	/**
	 * Constructor of creating model wrapper with list of elements
	 * 
	 * @param elements  project models list that's need to be wrapped
	 * @param operation project operation that's need to be executed
	 */
	public ModelWrapper(List<E> elements, Operation operation) {
		this.elements = elements;
		this.operation = operation;
	}

	public ModelWrapper(List<E> elements1, E[] elements2, Operation operation) {
		super();
		this.elements = elements1;
		this.elements2 = elements2;
		this.operation = operation;
	}
	
	public ModelWrapper(WordFile elements1, E elements2, Operation operation) {
		super();
		this.file = elements1;
		this.element = elements2;
		this.operation = operation;
	}

	public E getElement() {
		return element;
	}

	public void setElement(E element) {
		this.element = element;
	}

	public Operation getOperation() {
		return operation;
	}

	public void setOperation(Operation operation) {
		this.operation = operation;
	}

	public List<E> getElements() {
		return elements;
	}

	public void setElements(List<E> elements) {
		this.elements = elements;
	}

	public E[] getElements2() {
		return elements2;
	}

	public void setElements2(E[] elements2) {
		this.elements2 = elements2;
	}

	
	
	public WordFile getFile() {
		return file;
	}

	public void setFile(WordFile file) {
		this.file = file;
	}

	@Override
	public String toString() {
		return "ModelWrapper [element=" + element + ", elements=" + elements + ", operation=" + operation + "]";
	}

}

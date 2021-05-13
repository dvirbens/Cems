package common;

import java.io.Serializable;
import java.util.List;

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
	 * Store class model element, in order to transfer it via client/server
	 */
	private E element;
	/**
	 * Store list of class model element, in order to transfer it via client/server
	 */
	private List<E> elements;
	/**
	 * Which operation should be executed in server/client
	 */
	private Operation operation;

	public enum Operation {
		UPDATE_TEST, ENTERED_WRONG_ID, ERROR, LOAD_TEST, LOAD_QUESTION, LOAD_TEST_LIST, LOAD_QUESTION_LIST,
		OVERALL_STATISTICS, EXAM_EXTENSION_REQUEST, EXAM_EXECUTE, CREATE_QUESTION, START_EXAM, TEST_STATISTICS,
		GET_USER, GET_SUBJECT_COURSE_LIST, GET_COURSE_LIST,
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

	@Override
	public String toString() {
		return "ModelWrapper [element=" + element + ", elements=" + elements + ", operation=" + operation + "]";
	}

}

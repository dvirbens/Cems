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
	private int operation;

	/**
	 * All of server/client operation
	 */
	private static final long serialVersionUID = 1L;
	public static final int ENTERED_WRONG_ID = -2;
	public static final int ERROR = -1;
	public static final int LOAD_TEST = 0;
	public static final int UPDATE_TEST = 1;
	public static final int LOAD_QUESTION = 2;
	public static final int LOAD_TEST_LIST = 3;
	public static final int LOAD_QUESTION_LIST = 4;

	/**
	 * Constructor of creating model wrapper with one element
	 * 
	 * @param element   project model that's need to be wrapped
	 * @param operation project operation that's need to be executed
	 */
	public ModelWrapper(E element, int operation) {
		this.element = element;
		this.operation = operation;
	}

	/**
	 * Constructor of creating model wrapper with list of elements
	 * 
	 * @param elements  project models list that's need to be wrapped
	 * @param operation project operation that's need to be executed
	 */
	public ModelWrapper(List<E> elements, int operation) {
		this.elements = elements;
		this.operation = operation;
	}

	public E getElement() {
		return element;
	}

	public void setElement(E element) {
		this.element = element;
	}

	public int getOperation() {
		return operation;
	}

	public void setOperation(int operation) {
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

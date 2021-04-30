package common;

import java.io.Serializable;
import java.util.List;

public class ModelWrapper<E> implements Serializable {

	private E element;
	private List<E> elements;
	private int operation;

	public static final int ERROR = 0;
	public static final int LOAD_TEST = 1;
	public static final int LOAD_QUESTION = 2;
	public static final int LOAD_TEST_LIST = 3;
	public static final int LOAD_QUESTION_LIST = 4;

	public ModelWrapper(E element, int operation) {
		this.element = element;
		this.operation = operation;
	}

	public ModelWrapper(List<E> elements, int operation) {
		this.elements = elements;
		this.operation = operation;
	}

	public E getElement() {
		return element;
	}

	public void setObject(E element) {
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

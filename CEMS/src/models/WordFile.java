package models;

import java.io.Serializable;

public class WordFile implements Serializable {

	private static final long serialVersionUID = 1L;
	private String name;
	private int size = 0;
	public byte[] mybytearray;

	public WordFile() {
		super();
	}

	public WordFile(String name, int size, byte[] mybytearray) {
		this.name = name;
		this.size = size;
		this.mybytearray = mybytearray;
	}

	public void initArray(int size) {
		mybytearray = new byte[size];
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public byte[] getMybytearray() {
		return mybytearray;
	}

	public void setMybytearray(byte[] mybytearray) {
		for (int i = 0; i < mybytearray.length; i++)
			this.mybytearray[i] = mybytearray[i];
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}

package org.jarcraft.library.iotools;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.OutputStream;
import java.io.PrintWriter;

public class Writer extends PrintWriter {

	public Writer(File file) throws FileNotFoundException {
		super(file);
	}
	
	public Writer(OutputStream outputStream) {
		super(outputStream);
	}
	
	public void writeNewLine() {
		write('\n'); 
	}
	
	public void writeNL(String text) {
		write(text);
		writeNewLine();
	}
	
	public void write(String[] textArray) {
		for(String text : textArray) {
			writeNL(text);
		}
	}
	
	public void write(String[] textArray, char split) {
		for(String text : textArray) {
			write(text + split);
		}
	}
}

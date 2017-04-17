package org.jarcraft.library.iotools;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Reader extends BufferedReader{
	
	public Reader(InputStream inputStream) {
		super(new InputStreamReader(inputStream));
	}
	
	public Reader(File file) throws FileNotFoundException {
		super(new FileReader(file));
	}
	
	public String[] readAll() throws IOException {
		List<String> text = new ArrayList<String>();
		String input;
		while((input = readLine()) != null) {
			text.add(input);
		}
		return text.toArray(new String[text.size()]);
	}
	
	public void skipLines(int lines) throws IOException {
		for(int i = 0; i < lines; i++) {
			readLine();
		}
	}
}

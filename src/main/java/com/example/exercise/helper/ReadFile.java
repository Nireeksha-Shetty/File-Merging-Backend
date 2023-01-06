package com.example.exercise.helper;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class ReadFile {
	public static BufferedReader fileReader(String file) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(file));
//		Vector<String> lines = new Vector<>();
//		String line;
//		while ((line = br.readLine()) != null) {
//			if(line.length()==0) {
//				continue;
//			}
//			lines.add(line);
//			System.out.println("Thread Used: " + Thread.currentThread().getId());
//		}
//		return lines;
		return  br;
	}

}

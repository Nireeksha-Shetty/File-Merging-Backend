package com.example.exercise.service;

import org.springframework.web.multipart.MultipartFile;

import com.example.exercise.entity.Attachment;
import com.example.exercise.repository.AttachmentRepository;

import com.example.exercise.helper.ReadFile;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;


@Service
public class AttachmentServiceImpl implements AttachmentService{

    private AttachmentRepository attachmentRepository;

    public AttachmentServiceImpl(AttachmentRepository attachmentRepository) {
        this.attachmentRepository = attachmentRepository;
    }

    public Attachment saveAttachment(MultipartFile file) throws Exception {
       String fileName = StringUtils.cleanPath(file.getOriginalFilename());
       try {
            if(fileName.contains("..")) {
                throw  new Exception("Filename contains invalid path sequence "
                + fileName);
            }

            Attachment attachment
                    = new Attachment(fileName,
                    file.getContentType(),
                    file.getBytes());
            return attachmentRepository.save(attachment);

       } catch (Exception e) {
            throw new Exception("Could not save File: " + fileName);
       }
    }
    
    

    @Override
    public Attachment getAttachment(String fileId) throws Exception {
        return attachmentRepository
                .findById(fileId)
                .orElseThrow(
                        () -> new Exception("File not found with Id: " + fileId));
    }
    
    public  void fileCompare() throws IOException, InterruptedException {
		List<String> list1 = new ArrayList<String>();
		List<String> list2 = new ArrayList<String>();
		Thread thread1 = new Thread() {
			String line = " ";

			@Override
			public void run() {
				try {
					BufferedReader bf1 = ReadFile.fileReader("C:\\Users\\nshetty\\eclipse-workspace\\ExerciseOnFile\\src\\main\\resources\\static\\image\\");
					while ((line = bf1.readLine()) != null) {
						String[] data = line.split(" ");
						for (String s : data) {
							list1.add(s);
						}
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
				super.run();
			}
		};
		Thread thread2 = new Thread() {
			String line = " ";

			@Override
			public void run() {
				try {
					BufferedReader bf2 = ReadFile.fileReader("C:\\Users\\nshetty\\eclipse-workspace\\ExerciseOnFile\\src\\main\\resources\\static\\image\\file2.txt");
					while ((line = bf2.readLine()) != null) {
						String[] data = line.split(" ");
						for (String s : data) {
							list2.add(s);
						}
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
				super.run();
			}
		};
		thread1.start();
		thread2.start();
		thread1.join();
		thread2.join();

		System.out.println(list1);
		System.out.println(list2);

		Set<String> set = new HashSet<>();

		// Loop to add Common words to a HashSet
		for (int i = 0; i < list1.size(); i++) {
			for (int j = 0; j < list2.size(); j++) {
				if (list1.get(i).equals(list2.get(j))) {
					// add common elements
					set.add(list1.get(i));
					break;
				}
			}
		}
		System.out.println("Matched words are: ");
		for (String i : set) {
			System.out.print(i + " ");
		}

		Set<String> hset1 = new HashSet<>();
		// Adding unique elements from both list to HashSet
		for (String name : list1) {
			hset1.add(name);
		}
		for (String name : list2) {
			hset1.add(name);
		}
		System.out.println();

		System.out.println("Unique words are: ");
		for (String i : hset1) {
			System.out.print(i + " ");
		}
	}

	@Override
	public void frequency() throws InterruptedException {
		List<String> list = new ArrayList<String>();
		Thread thread1 = new Thread() {
			
			String line = " ";

			@Override
			public void run() {
				try {
					System.out.println("Entered2");
					BufferedReader bf1 = ReadFile.fileReader("C:\\Users\\nshetty\\eclipse-workspace\\ExerciseOnFile\\src\\main\\resources\\static\\image\\file1.txt");
					while ((line = bf1.readLine()) != null) {
						
						String[] data = line.split(" ");
						
						for (String s : data) {
							
							list.add(s);
							

						}
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
				super.run();
			}
		};
		Thread thread2 = new Thread() {
			String line = " ";

			@Override
			public void run() {
				try {
					BufferedReader bf2 = ReadFile.fileReader("C:\\Users\\nshetty\\eclipse-workspace\\ExerciseOnFile\\src\\main\\resources\\static\\image\\file2.txt");
					while ((line = bf2.readLine()) != null) {
						String[] data = line.split(" ");
						for (String s : data) {
							list.add(s);

						}
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
				super.run();
			}
		};
		thread1.start();
		thread2.start();
		thread1.join();
		thread2.join();

		System.out.println(list);
		Set<String> distinct = new HashSet<>(list);
//		Map<String, Integer> hm = new HashMap<String, Integer>();
		for (String s : distinct) {
			if (Collections.frequency(list, s) > 1) {
//				hm.put(s, Collections.frequency(list, s));
				System.out.println(s + ": " + Collections.frequency(list, s));

			}
		}
		
	}
	
	public void mergeFile(MultipartFile file1, MultipartFile file2) throws IOException {

//		 File file11 = new File("file1");
//		 File file22 = new File("file2");
		// BufferedReader object for file1.txt and file2.txt
		BufferedReader br1 =  new BufferedReader(new InputStreamReader(file1.getInputStream()));
		BufferedReader br2 =  new BufferedReader(new InputStreamReader(file2.getInputStream()));

		// PrintWriter object for file3.txt
		PrintWriter pw = new PrintWriter("C:\\Users\\nshetty\\eclipse-workspace\\ExerciseOnFile\\src\\main\\resources\\static\\image\\file3.txt");

		
		String line1 = br1.readLine();
		String line2 = br2.readLine();

		// loop to copy lines of file1.txt and file2.txt to file3.txt alternatively
		while (line1 != null || line2 != null) {
			if (line1 != null) {
				pw.println(line1);
				line1 = br1.readLine();
			}
			if (line2 != null) {
				pw.println(line2);
				line2 = br2.readLine();
			}
		}

		// closing resources
		pw.flush();
//		br1.close();
//		br2.close();
		System.out.println();
		System.out.println("file merged");
	}

	
}

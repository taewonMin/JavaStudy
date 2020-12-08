package practice;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
'd:/D_Other/'에 있는 'Tulips.jpg'파일을

'복사본_Tulips.jpg'로 복사하는 프로그램을

작성하시오. 
 */
public class FileCopy {
	public static void main(String[] args) {
		File orgFile = new File("Tulips.jpg");
		File newFile = new File("복사본_Tulips.jpg");
		
		FileInputStream fis = null;
		FileOutputStream fos = null;

		try {
			fis = new FileInputStream(orgFile);
			fos = new FileOutputStream(newFile);
			
			int c;
			while((c=fis.read()) != -1) {
				fos.write(c);
			}
		} catch(FileNotFoundException e) {
			System.out.println("파일을 찾을 수 없습니다.");
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if(fis != null) {
					fis.close();
				}
				if(fos != null) {
					fos.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}

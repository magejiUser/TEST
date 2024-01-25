package com.example.demo.posts;

import java.io.ByteArrayOutputStream;
import java.util.Optional;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

import org.springframework.beans.factory.annotation.Autowired;

public class ImageUtils {
	@Autowired
	PostRepository postRepository;
	
	public static byte[] compressImage(byte[] data) {
		Deflater deflater = new Deflater();
		deflater.setLevel(Deflater.BEST_COMPRESSION);
		deflater.setInput(data);
		deflater.finish();
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
		byte[] tmp = new byte[4*1024];
		while(!deflater.finished()) {
			int size = deflater.deflate(tmp);
			outputStream.write(tmp, 0,size);
		}
		try{
			outputStream.close();
		}catch(Exception ignored){
			
		}
		return outputStream.toByteArray();
	}

	public static byte[] decompressImage(byte[] data) {
		Inflater inflater = new Inflater();
		inflater.setInput(data);
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(data.length);
		byte[] tmp = new byte[4*1024];
		try {
			while (!inflater.finished()) {
				int count = inflater.inflate(tmp);
				byteArrayOutputStream.write(tmp, 0, count);
			}
			byteArrayOutputStream.close();
		}catch(Exception ignored) {}
		return byteArrayOutputStream.toByteArray();
	}
	
	

}

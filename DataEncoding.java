package qrcode;

import java.nio.charset.StandardCharsets;

import reedsolomon.ErrorCorrectionEncoding;

public final class DataEncoding {

	final static int encodingSize = 8;
	/**
	 * @param input
	 * @param version
	 * @return
	 */
	public static boolean[] byteModeEncoding(String input, int version) {
		//int maxLength = QRCodeInfos.getMaxInputLength(version);
		int maxLength = 49;
		
		System.out.println("maxLength is " + maxLength);
		int eccLength = QRCodeInfos.getECCLength(version);
		System.out.println("eccLength is " + eccLength);
		int[] isoCode = DataEncoding.encodeString(input, maxLength);
		System.out.println("Isocode is " + java.util.Arrays.toString(isoCode));
		int[] byteCode = DataEncoding.addInformations(isoCode);		
		System.out.println("byteCode is " + java.util.Arrays.toString(byteCode));		
		int[] filledSequence = DataEncoding.fillSequence(byteCode, 23);		
		System.out.println("filledSequence is " + java.util.Arrays.toString(filledSequence));
		int [] ErrorCorrection = DataEncoding.addErrorCorrection(filledSequence, eccLength);
		System.out.println("ErrorCorrection is " + java.util.Arrays.toString(ErrorCorrection));
		boolean[] binaryArray = DataEncoding.bytesToBinaryArray(ErrorCorrection);
		System.out.print("binaryArray is " + java.util.Arrays.toString(binaryArray));
				
		return binaryArray;
	}

	/**
	 * @param input
	 *            The string to convert to ISO-8859-1
	 * @param maxLength
	 *          The maximal number of bytes to encode (will depend on the version of the QR code) 
	 * @return A array that represents the input in ISO-8859-1. The output is
	 *         truncated to fit the version capacity
	 */
	public static int[] encodeString(String input, int maxLength) {
		byte[] inputByteSeq = input.getBytes(StandardCharsets.ISO_8859_1);		
		int loopCount;
		
		if(input.length() < maxLength) {
			loopCount = input.length();
		} else {
			loopCount = maxLength;
		}
		
		int[] inputIntSeq = new int[loopCount];
		
		for(int k = 0; k < loopCount; k++) {
			
			inputIntSeq[k] = inputByteSeq[k] & 0xFF;
		}	
		
		return inputIntSeq;
	}

	/**
	 * Add the 12 bits information data and concatenate the bytes to it
	 * 
	 * @param inputBytes
	 *            the data byte sequence
	 * @return The input bytes with an header giving the type and size of the data
	 */
	public static int[] addInformations(int[] inputBytes) {
		String binarySeqString = "";		
		String messagePrefix = "0100";
		String messagePostfix = "0000";
		String encodedLength = toBinary(inputBytes.length);		
				
		
		binarySeqString = BinarySeqGenerator(inputBytes);
		
		binarySeqString = messagePrefix + encodedLength + binarySeqString + messagePostfix;
		
		String[] binaryStringArray = new String[binarySeqString.length() / encodingSize];		
		int[] binaryArray = new int[binaryStringArray.length];
		
		System.out.println(binarySeqString);
	
		
		for(int i = 0; i < binaryStringArray.length ; i ++) {
			System.out.println(i);
			binaryStringArray[i] = binarySeqString.substring(i * encodingSize, (i + 1)*encodingSize);
			System.out.println(binaryStringArray[i]);
		}
		
		for(int i = 0; i < binaryStringArray.length; i ++) {
			binaryArray[i] = Integer.parseInt(binaryStringArray[i]);
			
		}		
			
		return binaryArray;
	}	
	
	
	
	/**
	 * Add padding bytes to the data until the size of the given array matches the
	 * finalLength
	 * 
	 * @param encodedData
	 *            the initial sequence of bytes
	 * @param finalLength
	 *            the minimum length of the returned array
	 * @return an array of length max(finalLength,encodedData.length) padded with
	 *         bytes 236,17
	 */
	public static int[] fillSequence(int[] encodedData, int finalLength) {			
		
		if (encodedData.length < finalLength) {		
			
			int[] encodedDataResize = new int[finalLength];
			
			for(int k = 0; k < encodedData.length; k ++) {
				
				encodedDataResize[k] = encodedData[k];
			}
			
			
			for(int i = 0; i < finalLength - encodedData.length; i ++) {		
					
				if(i % 2 == 0) {
					encodedDataResize[encodedData.length + i] = 236 & 0xFF;
				} else {
					encodedDataResize[encodedData.length  + i] = 17 & 0xFF;
				}
			}		
			
			return encodedDataResize;
			
		} else {
			
			return encodedData;
		}			
	}
	
	/**
	 * Add the error correction to the encodedData
	 * 
	 * @param encodedData
	 *            The byte array representing the data encoded
	 * @param eccLength
	 *            the version of the QR code
	 * @return the original data concatenated with the error correction
	 */
	
	
	
	public static int[] addErrorCorrection(int[] encodedData, int eccLength) {	
		
		int[] errorCorrection = ErrorCorrectionEncoding.encode(encodedData, eccLength); 
		
		int[] encodedDataCorrection = new int [encodedData.length + eccLength];
		
		// copying the array into encodedDataCorrection
		for (int k = 0; k < encodedData.length; k++) {
			encodedDataCorrection[k] = encodedData[k];
		}
		
		for(int i = 0; i < errorCorrection.length; i ++) {
			
			encodedDataCorrection[encodedData.length + i] = Integer.parseInt(toBinary(errorCorrection[i]));
		}
				
		return encodedDataCorrection;
	}

	/**
	 * Encode the byte array into a binary array represented with boolean using the
	 * most significant bit first.
	 * 
	 * @param data
	 *            an array of bytes
	 * @return a boolean array representing the data in binary
	 */
	public static boolean[] bytesToBinaryArray(int[] data) {
		String inputBinarySeqString = "";
		char[] inputBinaryArray = new char[data.length * 8];
		boolean[] outputBooleanArray = new boolean[data.length * 8];
		
		inputBinarySeqString = BinarySeqGenerator(data);
		inputBinaryArray = inputBinarySeqString.toCharArray();
		
		for(int i = 0; i < inputBinaryArray.length; i++) {
			if(inputBinaryArray[i] == '1') {
				outputBooleanArray[i] = true;
			}else {
				outputBooleanArray[i] = false;
			}
		}
	
		return outputBooleanArray;
	}
	
	public static String BinarySeqGenerator(int[] data) {
		String binarySeqString = "";
		
		for (int i = 0; i < data.length; i ++) {
			binarySeqString += toBinary(data[i]);
		}		
		
		return binarySeqString;
	}
	
	public static String toBinary (int decimalNumber){
		String binaryNumber = Integer.toBinaryString(decimalNumber);		
		
		// adding back leading zeroes
		for(int i = binaryNumber.length(); i < binaryNumber.length() - 8; i ++) {
			binaryNumber = "0" + binaryNumber;
		}
		
		return binaryNumber;
	}

}

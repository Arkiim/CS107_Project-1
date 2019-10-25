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
		int maxInputLength = QRCodeInfos.getMaxInputLength(version);	
		int codeWordLength = QRCodeInfos.getCodeWordsLength(version);
		
		System.out.println("maxInputLength is " + maxInputLength);
		int eccLength = QRCodeInfos.getECCLength(version);
		System.out.println("eccLength is " + eccLength);
		int[] isoCode = DataEncoding.encodeString(input, maxInputLength);
		System.out.println("Isocode is " + java.util.Arrays.toString(isoCode));
		int[] byteCode = DataEncoding.addInformations(isoCode);		
		System.out.println("byteCode is " + java.util.Arrays.toString(byteCode));		
		int[] filledSequence = DataEncoding.fillSequence(byteCode, codeWordLength);		
		System.out.println("filledSequence is " + java.util.Arrays.toString(filledSequence));
		int [] ErrorCorrection = DataEncoding.addErrorCorrection(filledSequence, eccLength);
		System.out.println("ErrorCorrection is " + java.util.Arrays.toString(ErrorCorrection));
		boolean[] binaryArray = DataEncoding.bytesToBinaryArray(ErrorCorrection);
		System.out.println("binaryArray is " + java.util.Arrays.toString(binaryArray));
				
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
		
		int messagePrefix = 0b0100;
		int messagePostfix = 0b0000;
		int encodedLength = inputBytes.length & 0xFF;		
				
		int [] endodedData = new int[inputBytes.length + 2];		
		
		endodedData[0] = encodedLength >> 4| messagePrefix << 4; 
		endodedData[1] = inputBytes[0] >> 4| (encodedLength << 4)  & 0xFF; 
		
		for(int i = 2; i < inputBytes.length + 1; i++ ) {			
			endodedData[i] = inputBytes[i - 1] >> 4| (inputBytes[i - 2] << 4) & 0xFF; 
		}
		
		endodedData[endodedData.length - 1] =  (inputBytes[inputBytes.length - 1] << 4) & 0xFF | messagePostfix;
		
		for(int i = 0; i < endodedData.length; i ++) {
			System.out.println(endodedData[i]);
		}
		
		return endodedData;
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
		
		System.out.println("finalLength " + finalLength);
		
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
			
			encodedDataCorrection[encodedData.length + i] = errorCorrection[i] ;
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
		
		boolean[] outputBooleanArray = new boolean[data.length * 8];
		
		for (int i = 0; i < data.length; i++) {
			for(int k = 0; k < 8; k++) {
				int shiftByte = data[i] << k;
				
				System.out.println("shift:" + (shiftByte & 0xFF));
				System.out.println(data[i] & 0xFF);
				
				if((shiftByte & 0b10000000) == 0b10000000) {
					outputBooleanArray[k + (8*i)] = true;
				} else {
					outputBooleanArray[k + (8*i)] = false;
				}
			}
		}
	
		return outputBooleanArray;
	}

}

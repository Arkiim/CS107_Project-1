package qrcode;

import java.nio.charset.StandardCharsets;
import reedsolomon.ErrorCorrectionEncoding;

public final class DataEncoding {

	/**test
	 * @param input test
	 * @param version
	 * @return
	 */
	
	public static boolean[] byteModeEncoding(String input, int version) {
		// TODO Implementer
		
		int[] inputBytes = encodeString(input, QRCodeInfos.getMaxInputLength(version));
		int[] encodedData = addInformations(inputBytes);
		int[] data = fillSequence(encodedData, QRCodeInfos.getCodeWordsLength(version));
		int[] dataCorrection = addErrorCorrection(data, QRCodeInfos.getECCLength(version));
		boolean[] finalBinaryArray = bytesToBinaryArray(dataCorrection);
		
		return finalBinaryArray;
	}

	/**
	 * @param input
	 *            The string to convert to ISO-8859-1
	 * @param maxLength
	 *          The maximal number of bytes to encode (will depend on the version of the QR code) 
	 * @return An array that represents the input in ISO-8859-1. The output is
	 *         truncated to fit the version capacity
	 */
	public static int[] encodeString(String input, int maxLength) {
		// TODO Implementer
		byte[] tabByte = input.getBytes(StandardCharsets.ISO_8859_1);
		
		//if the message is shorter than maxLength, reduce the sizes of the arrays
		if(maxLength >= tabByte.length ) { 
			maxLength = tabByte.length;
		} 
		
		//Only takes the maxLength first bytes of tabByte[]
		int[] inputBytes = new int[maxLength]; 
		
		for(int i = 0 ; i < maxLength; ++i) {
			inputBytes[i] = tabByte[i] & 0xFF; 
		}

		return inputBytes;
	}

	/**
	 * Add the 12 bits information data and concatenate the bytes to it
	 * 
	 * @param inputBytes
	 *            the data byte sequence
	 * @return The input bytes with an header giving the type and size of the data
	 */
	public static int[] addInformations(int[] inputBytes) {
		// TODO Implementer
		
		int lgth = inputBytes.length & 0xFF ; //49
		int prefix = 0b0100 ;
		
		int[] encodedData = new int[inputBytes.length + 2] ;
		
		//logical conjonction(&) between lgth and a byte where its 4 lightest bits are 0 (and the heaviest at 1) to be sure that we only get the 4 heaviest bits
		encodedData[0] = prefix << 4  | (lgth & 0b1111_0000) >> 4 ; // 
		// 0100_0000 or 0000_0011  = 0100_0011
		
		encodedData[1] =  (lgth & 0b0000_1111) << 4 | inputBytes[0] >> 4 ; 
		//0001_0000 or 0000_0101 = 0001_0101
		
		
		//the last element will be added after the loop in such a way that, the program wont search for a 50th element of inputBytes[] which does not exist
		for(int i = 2 ; i < encodedData.length - 1; ++i) { 
			
			int heavyB = inputBytes[i-2] & 0b0000_1111 ; // takes only the part we need and then shift it to combine both element (heavyB and lightB)

			int lightB = inputBytes[i-1] /*& 0b1111_0000*/;
			
			encodedData[i] =  heavyB << 4  | lightB >> 4 ;
			
		} 
		
		encodedData[encodedData.length - 1] = (inputBytes[inputBytes.length - 1] & 0b0000_1111) << 4  | 0b0000  ;
		
		
		return encodedData;	
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
		// TODO Implementer
		
		// If there's any place left => fill the array otherwise do nothing
		if(encodedData.length < finalLength) {
			
			//New array of size finalLength that will contain the older one plus 236 and 17 until finalLength
			int[] data = new int[finalLength];
			
			for(int i = 0 ; i < encodedData.length ; ++i) {
				data[i] = encodedData[i];
			}
			boolean j = false; 
			
			for(int i = encodedData.length ; i < finalLength ; ++i ) {
				
				if(j == false) {
					data[i] = 236;
					j = true;
				} else {
					data[i] = 17;
					j = false;
				}
				
			}
			
			return data;
			
			
		} 
		
		return encodedData;
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
		// TODO Implementer
		
		int[] correctionBytes = ErrorCorrectionEncoding.encode(encodedData , eccLength);
		//Array of size equal to the sum of both other arrays
		
		int[] dataCorrection = new int[correctionBytes.length + encodedData.length]; 
		
		for(int i = 0; i < dataCorrection.length ; ++i ) {
			
			//Once the value i is greater than the size of the array containing the bytes of encodedData, it is the eccLength bytes of dataCorrection that will fill the end of the final array dataCorrection 
			if( i < encodedData.length ) {
				dataCorrection[i] = encodedData[i] ;
			} else {
				dataCorrection[i] = correctionBytes[i-encodedData.length];
			}
			
		}
			
		return dataCorrection;
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
		// TODO Implementer
		
		int[][] binValue = getBinValue(data);
		boolean[] binaryArray = new boolean[data.length*8];
		int i = 0;
		
		for(int k = 0 ; k < data.length ; ++k) {
			
			for(int j = 0 ; j < binValue[k].length ; ++j) {
				
				//puts each array of 8 btis corresponding to an element of data[] into a boolean ( takes the 2d array to fit in the simple 1 dimension boolean one )
				if(binValue[k][j] == 1) {
					binaryArray[i] = true;
				} else {
					binaryArray[i] = false;
				}
				++i;
			}
			
		}

		return binaryArray;
	}
	
	public static int[][] getBinValue(int[] data) {
		
		int[] bitTbl = getBitIsolator();
		int[][]binValue = new int[data.length][8];
		
		for(int i = 0 ; i < data.length ; ++i) {
			
			//makes an array of 8 element to put all the 8 bits of an integer. Then repeat that for all element of data[]
			for(int j = 0 ; j< 8 ; ++j) {
				
				binValue[i][j] = (bitTbl[j] & data[i]) >> (7-j) ;
				//shift the value of 7-j in a way that the value will always be 0 or 1 (bitTbl starts at 1000_0000)

			}
			
		}
		
		return binValue;
	}
	
	public static int[] getBitIsolator() {
		
		int[] bitIsolator = new int[8]; 
		
		//Fill an array of bytes with 1 bit per bytes each one at a different position, to use the same "trick" as before in the method addInformations()
		
		for(int i = 0; i < 8 ; ++i) {
			bitIsolator[i] = 0b1000_0000 >> i;
		} 
		
		//Array starts at 1000_0000 and finishes at 0000_0001
		
		return bitIsolator;
	}
	
}

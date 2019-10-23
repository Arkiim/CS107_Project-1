package qrcode;

import java.nio.charset.StandardCharsets;

import reedsolomon.ErrorCorrectionEncoding;

public final class DataEncoding {

	/**test
	 * @param input test
	 * @param version
	 * @return
	 */
	
	public static void main(String[] args) {
		int[] inputBytes = encodeString(Main.INPUT, QRCodeInfos.getMaxInputLength(4));
		addInformations(inputBytes);
		
	}
	
	public static boolean[] byteModeEncoding(String input, int version) {
		// TODO Implementer
		
		return null;
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
		
		if(maxLength >= tabByte.length ) { // si le message prend moins de place que maxLength, r�duit la taille des arrays
			maxLength = tabByte.length;
		}
		
		int[] inputBytes = new int[maxLength]; //ne prend que les maxLength premiers octets de tabVyte[]
		
		for(int i = 0 ; i < maxLength; ++i) {
			inputBytes[i] = tabByte[i] & 0xFF; 
			//System.out.println("inputBytes[" + i + "] == " + inputBytes[i] + " = " + tabByte[i] + "== tabByte[" + i +"]");
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
		return null;
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
		return null;
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
		return null;
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
		return null;
	}

}

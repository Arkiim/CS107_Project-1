package qrcode;

import java.nio.charset.StandardCharsets;

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
		
		if(maxLength >= tabByte.length ) { // si le message prend moins de place que maxLength, réduit la taille des arrays
			maxLength = tabByte.length;
		}
		
		int[] inputBytes = new int[maxLength]; //ne prend que les maxLength premiers octets de tabVyte[]
		
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
		int lgth = 0b00110001 ; //49
		int prefix = 0b0100 ;
		
		int[] encodedData = new int[inputBytes.length + 2] ;
		
		encodedData[0] = prefix << 4  | (lgth & 0b1111_0000) >> 4 ; // Conjonction logique(&) entre lgth et un octet où ses 4 bits de poids faibles sont à 0, pour être sûr de ne prendre que les 4 bits de poids fort
		// 0100_0000 or 0000_0011  = 0100_0011
		
		encodedData[1] =  (lgth & 0b0000_1111) << 4 | inputBytes[0] >> 4 ; //0001_0000 or 0000_0101 = 0001_0101
		
		
		for(int i = 2 ; i < encodedData.length - 1; ++i) { // le dernier élément sera ajouté hors de la boucle pour ne pas que l'algorithme aille chercher un 50e élément (hors des bornes) de inputBytes
			
			int heavyB = inputBytes[i-2] & 0b0000_1111 ; // Prend seulement la moitié nécessaire et la décale pour pouvoir combiner les deux ( heavyB et lightB)
			int lightB = inputBytes[i-1] & 0b1111_0000;
			encodedData[i] =  heavyB << 4  | lightB >> 4 ;
			
		} encodedData[encodedData.length - 1] = (inputBytes[inputBytes.length - 1] & 0b0000_1111) << 4 | 0b0000  ;
		
		
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
	
	
	//Pour tester/afficher la valeur en binaire du message encodé en ayant un output sous format binaire (dans un string) 
	public static String toBinaryStr(int X) { 
		String binaryX = Integer.toBinaryString(X);
		int length = binaryX.length();
		
		for(int i = 0; i < 8 - length; i ++) {
			binaryX = "0" + binaryX;
		}
		
		//System.out.println(binaryX);
		
		return binaryX;
	}

	
}

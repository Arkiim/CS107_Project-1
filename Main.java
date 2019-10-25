package qrcode;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class Main {

	public static final String INPUT =  "Bonne journée!";

	private final static boolean[] data = { false, true, false, false, false, false, false, true, false, false, false,
			true, false, true, false, true, false, false, false, false, false, true, true, true, false, false, true,
			false, false, true, true, false, true, true, true, true, false, true, true, false, false, true, true, true,
			false, true, true, true, false, false, true, false, false, true, true, false, false, false, false, true,
			false, true, true, false, true, true, false, true, false, true, true, false, true, true, false, true, false,
			true, true, false, true, false, false, true, false, true, true, false, true, true, true, false, false, true,
			true, false, false, true, true, true, false, false, true, false, false, false, false, false, false, true,
			true, false, true, false, false, true, false, true, true, true, false, false, true, true, false, false,
			true, false, false, false, false, false, false, true, true, false, false, false, false, true, false, false,
			true, false, false, false, false, false, false, false, false, false, true, false, false, false, true, true,
			true, true, true, false, true, false, false, true, false, true, true, true, true, false, true, true, false,
			false, true, false, true, true, false, true, false, true, false, true, true, true, false, false, false,
			false, false, false, true, false, true, true, true, true, false, true, false, true, true, true, false,
			true };

	
	/*
	 * Parameters
	 */
	public static final int VERSION = 1;
	public static final int MASK = 0;
	public static final int SCALING = 20;

	public static void main(String[] args) {
		
		//DataEncoding.byteModeEncoding(INPUT, VERSION);
		
		/*
		 * Encoding
		 */
		//boolean[] encodedData = DataEncoding.byteModeEncoding(INPUT, VERSION);
		int[][] matrix = MatrixConstruction.renderQRCodeMatrix(VERSION, data, MASK);
		assertTrue(Helpers.compare(matrix, "testV1M0"),"Matrix is not what expected. Run Debug.java for more informations");
		
		
		//assertTrue(Helpers.compare(matrix,"noDataV4M5"),"The format information is wrong. Run Debug.java for more informations");
		
		//int[][] res = MatrixConstruction.renderQRCodeMatrix(1, data, 0);
		//assertTrue(Helpers.compare(res, "testV1M0"),"Matrix is not what expected. Run Debug.java for more informations");
		
		
	
		
	/**	
	    for(int i = 0; i<21; i++){
	        System.out.print("[");
	        for(int j = 0; j<21; j++){
	            System.out.print(" " + matrix[i][j] + " ");
	        }
	        System.out.print("]");
	        System.out.println();
	    }
	    **/
		
		/*
		 * image
		 */
	//	int[][] qrCode = MatrixConstruction.renderQRCodeMatrix(VERSION, encodedData,MASK);

		/*
		 * Visualization
		 */
	//	Helpers.show(qrCode, SCALING);
	}

}

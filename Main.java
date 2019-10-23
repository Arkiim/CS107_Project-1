package qrcode;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class Main {

	public static final String INPUT =  "Programming is a skill best acquired by practice.";

	/*
	 * Parameters
	 */
	public static final int VERSION = 4;
	public static final int MASK = 5;
	public static final int SCALING = 20;

	public static void main(String[] args) {
		
		//DataEncoding.byteModeEncoding(INPUT, VERSION);
		
		/*
		 * Encoding
		 */
		// boolean[] encodedData = DataEncoding.byteModeEncoding(INPUT, VERSION);		

		int size = QRCodeInfos.getMatrixSize(VERSION);
		int[][] matrix = new int[size][size];
		MatrixConstruction.addFinderPatterns(matrix);
		MatrixConstruction.addAlignmentPatterns(matrix, VERSION);
		//MatrixConstruction.addTimingPatterns(matrix);
		MatrixConstruction.addDarkModule(matrix);
		MatrixConstruction.addFormatInformation(matrix, MASK);
		assertTrue(Helpers.compare(matrix,"formatV4M5"),"The format information is wrong. Run Debug.java for more informations");
	
		
		
	
		
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

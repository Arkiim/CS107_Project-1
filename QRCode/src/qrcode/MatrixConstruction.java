package qrcode;

public class MatrixConstruction {

	/*public static void main(String[] args) {
		int [][] initializedMatrix = initializeMatrix(Main.VERSION);
		//int [][] matrix = addB(initializedMatrix, 1, 1);
		//matrix = addW(matrix, 1,2);
		//System.out.println(matrix[1][1] + ", " + matrix[1][2]);
		addFinderPatterns(initializedMatrix);
	}
	*/
	
	/*
	 * Constants defining the color in ARGB format
	 * 
	 * W = White integer for ARGB
	 * 
	 * B = Black integer for ARGB
	 * 
	 * both needs to have their alpha component to 255
	 */
	// TODO add constant for White pixel
	// TODO add constant for Black pixel
	
	public static int W = 0xFF_FF_FF_FF;
	public static int B = 0xFF_00_00_00;

	// ...  MYDEBUGCOLOR = ...;
	// feel free to add your own colors for debugging purposes
	
	public static int R = 0xFF_FF_00_00;
	public static int G = 0xFF_00_FF_00;
	public static int Blue = 0xFF_00_00_FF;

	
	/**
	 * Create the matrix of a QR code with the given data.
	 * 
	 * @param version
	 *            The version of the QR code
	 * @param data
	 *            The data to be written on the QR code
	 * @param mask
	 *            The mask used on the data. If not valid (e.g: -1), then no mask is
	 *            used.
	 * @return The matrix of the QR code
	 */
	public static int[][] renderQRCodeMatrix(int version, boolean[] data, int mask) {

		/*
		 * PART 2
		 */
		int[][] matrix = constructMatrix(version, mask);
		/*
		 * PART 3
		 */
		addDataInformation(matrix, data, mask);

		return matrix;
	}

	/*
	 * =======================================================================
	 * 
	 * ****************************** PART 2 *********************************
	 * 
	 * =======================================================================
	 */

	/**
	 * Create a matrix (2D array) ready to accept data for a given version and mask
	 * 
	 * @param version
	 *            the version number of QR code (has to be between 1 and 4 included)
	 * @param mask
	 *            the mask id to use to mask the data modules. Has to be between 0
	 *            and 7 included to have a valid matrix. If the mask id is not
	 *            valid, the modules would not be not masked later on, hence the
	 *            QRcode would not be valid
	 * @return the qrcode with the patterns and format information modules
	 *         initialized. The modules where the data should be remain empty.
	 */
	public static int[][] constructMatrix(int version, int mask) {
		// TODO Implementer
		int[][] matrix = initializeMatrix(version);
		addFinderPatterns(matrix);
		
		return matrix;

	}

	/**
	 * Create an empty 2d array of integers of the size needed for a QR code of the
	 * given version
	 * 
	 * @param version
	 *            the version number of the qr code (has to be between 1 and 4
	 *            included
	 * @return an empty matrix
	 */
	public static int[][] initializeMatrix(int version) {
		// TODO Implementer
		
		int matrixSize =  QRCodeInfos.getMatrixSize(version);
		int[][] matrix = new int[matrixSize][matrixSize];
		
		
		return matrix;
	}

	/**
	 * Add all finder patterns to the given matrix with a border of White modules.
	 * 
	 * @param matrix
	 *            the 2D array to modify: where to add the patterns
	 */
	public static void addFinderPatterns(int[][] matrix) {
		// TODO Implementer	
		
		// horizontal black lines of QRCode  (all corners)
		int aH= 0, bH = 0,  C = 7 ;
		for(int i = 0 ; i < 2 ; ++i) {
			addRowB(matrix, aH, bH, C); //top left corner
			addRowB(matrix, aH+18, bH, C+17); //top right corner
			addRowB(matrix, aH, bH+18, C); //bottom left corner
			bH+=6;
		}
		
		//vertical black lines of QRCode
		int aV = 0, bV = 0 ;
		for(int i = 0 ; i < 2 ; ++i) {
			addColB(matrix, aV, bV, C); //top left
			addColB(matrix, aV+18, bV, C); // top right
			addColB(matrix, aV, bV+18, C+17); // bottom left
			aV += 6;
		}
		
		//black lines in center 
		int rowBottomLeft = 20; 
		for(int row = 2 ; row < 5 ; ++row) {
			addRowB(matrix, 2, row, 5);
			addRowB(matrix, 20, row, 23);
			addRowB(matrix, 2, rowBottomLeft, 5);
			++rowBottomLeft;
		}
		
		// white lines inside pattern
		int aW = 1, bW = 1, CW = 6;
		for (int i = 0 ; i < 2 ; ++i) {
			addRowW(matrix, aW, bW, CW );
			addRowW(matrix, aW+18, bW, CW+18);
			addRowW(matrix, aW, bW+18, CW);
			addColW(matrix, bW, aW, CW);
			addColW(matrix, bW+18, aW, CW);
			addColW(matrix, bW, aW+18, CW+18);
			bW += 4;
		}
	
		//horizontal white lines outside pattern
		aW = 0; bW = 7; CW = 8;
		for(int i = 0 ; i < 2 ; ++i) {
			addRowW(matrix, aW, bW, CW); // top left and top right
			addRowW(matrix, 0, 17, 8); //bottom left
			addColW(matrix, bW, aW, CW); //top left and bottom left
			addColW(matrix, 17, 0, 8);
			aW += 17;
			CW += 17;
		}

	
		/*for(int col = 0 ; col < 25 ; ++col) {
			System.out.print("\n");
			for( int row = 0; row < 25 ; ++row) {
				System.out.println("matrix["+col+"]["+row+"] = " + matrix[col][row]);
			}
		}*/
		
	}

	/**
	 * Add the alignment pattern if needed, does nothing for version 1
	 * 
	 * @param matrix
	 *            The 2D array to modify
	 * @param version
	 *            the version number of the QR code needs to be between 1 and 4
	 *            included
	 */
	public static void addAlignmentPatterns(int[][] matrix, int version) {
		// TODO Implementer
	}

	/**
	 * Add the timings patterns
	 * 
	 * @param matrix
	 *            The 2D array to modify
	 */
	public static void addTimingPatterns(int[][] matrix) {
		// TODO Implementer
	}

	/**
	 * Add the dark module to the matrix
	 * 
	 * @param matrix
	 *            the 2-dimensional array representing the QR code
	 */
	public static void addDarkModule(int[][] matrix) {
		// TODO Implementer
	}

	/**
	 * Add the format information to the matrix
	 * 
	 * @param matrix
	 *            the 2-dimensional array representing the QR code to modify
	 * @param mask
	 *            the mask id
	 */
	public static void addFormatInformation(int[][] matrix, int mask) {
		// TODO Implementer
	}

	public static void addB(int[][] matrix, int col, int row) {
		
		matrix[col][row] = B;
		
	}
	
	public static void addW(int[][] matrix, int col, int row ){
		
		matrix[col][row] = W;
		
	}
	
	//fills the row number : row, from col to maxCol
	public static void addRowB (int[][] matrix, int col, int row, int maxCol){
		
		for (; col < maxCol ; ++col) {
			addB(matrix, col, row);
		}
		
	}
	
	//fills the column number : col, from row to maxRow
	public static void addColB (int[][] matrix, int col, int row, int maxRow){
		
		for (; row < maxRow ; ++row) {
			addB(matrix, col, row);
		}

	}
	
	//same but with white pixels
	public static void addRowW (int[][] matrix, int col, int row, int maxCol){
		
		for (; col < maxCol ; ++col) {
			addW(matrix, col, row);
		}

	}
	
	public static void addColW (int[][] matrix, int col, int row, int maxRow){
		
		for (; row < maxRow ; ++row) {
			addW(matrix, col, row);
		}
		
	}
	
	/*
	 * =======================================================================
	 * ****************************** PART 3 *********************************
	 * =======================================================================
	 */

	/**
	 * Choose the color to use with the given coordinate using the masking 0
	 * 
	 * @param col
	 *            x-coordinate
	 * @param row
	 *            y-coordinate
	 * @param color
	 *            : initial color without masking
	 * @return the color with the masking
	 */
	public static int maskColor(int col, int row, boolean dataBit, int masking) {
		// TODO Implementer
		return 0;
	}

	/**
	 * Add the data bits into the QR code matrix
	 * 
	 * @param matrix
	 *            a 2-dimensionnal array where the bits needs to be added
	 * @param data
	 *            the data to add
	 */
	public static void addDataInformation(int[][] matrix, boolean[] data, int mask) {
		// TODO Implementer

	}
	
	/*
	 * =======================================================================
	 * 
	 * ****************************** BONUS **********************************
	 * 
	 * =======================================================================
	 */

	/**
	 * Create the matrix of a QR code with the given data.
	 * 
	 * The mask is computed automatically so that it provides the least penalty
	 * 
	 * @param version
	 *            The version of the QR code
	 * @param data
	 *            The data to be written on the QR code
	 * @return The matrix of the QR code
	 */
	public static int[][] renderQRCodeMatrix(int version, boolean[] data) {

		int mask = findBestMasking(version, data);

		return renderQRCodeMatrix(version, data, mask);
	}

	/**
	 * Find the best mask to apply to a QRcode so that the penalty score is
	 * minimized. Compute the penalty score with evaluate
	 * 
	 * @param data
	 * @return the mask number that minimize the penalty
	 */
	public static int findBestMasking(int version, boolean[] data) {
		// TODO BONUS
		return 0;
	}

	/**
	 * Compute the penalty score of a matrix
	 * 
	 * @param matrix:
	 *            the QR code in matrix form
	 * @return the penalty score obtained by the QR code, lower the better
	 */
	public static int evaluate(int[][] matrix) {
		//TODO BONUS
	
		return 0;
	}
	

}

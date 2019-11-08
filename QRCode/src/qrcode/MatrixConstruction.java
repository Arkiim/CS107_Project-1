package qrcode;

public class MatrixConstruction {
	
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
		addAlignmentPatterns(matrix, version);
		addTimingPatterns(matrix);
		addDarkModule(matrix);
		addFormatInformation(matrix, mask);
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
		int size = matrix.length;
		
		int b = 0,  size_7 = size - 7,  C = 7 ;
		
		for(int i = 0 ; i < 2 ; ++i) {
			
			// horizontal black lines of QRCode
			
			addRowB(matrix, 0, b, C); //top left corner
			addRowB(matrix, size-7, b, size); //top right corner
			addRowB(matrix, 0, size_7, C); //bottom left corner
			
			//vertical black lines of QRCode
			
			addColB(matrix, b, 0, C); //top left
			addColB(matrix, size_7, 0, C); // top right
			addColB(matrix, b , size-7, size); // bottom left
			
			b += 6;
			size_7 += 6;
			
		}
		
		//black lines in center
		
		int size_5 = size-5;
		
		for(int row = 2 ; row < 5 ; ++row) {
			
			addRowB(matrix, 2, row, 5);
			addRowB(matrix, size-5 , row, size-2);
			addRowB(matrix, 2, size_5, 5);
			
			++size_5 ;
			
		}
		
		
		// white lines inside pattern
		
		int size_6 = size-6;
		int bW = 1, CW = C-1;
		
		for (int i = 0 ; i < 2 ; ++i) {
			
			//horizontal whites lines
			
			addRowW(matrix, 1, bW, CW );
			addRowW(matrix, size-6, bW, size-1);
			addRowW(matrix, 1, size_6, CW);
			
			//vertical whites lines
			
			addColW(matrix, bW, size-6, size-1);
			addColW(matrix, size_6, 1, CW);
			addColW(matrix, bW, 1, CW);
			
			bW += 4;
			size_6 += 4;
				
		}
	
		// white lines outside pattern
		
		int aW = 0; CW = 8;
		
		for(int i = 0 ; i < 2 ; ++i) {
			
			addRowW(matrix, aW, 7, CW); // top left and top right row
			addRowW(matrix, 0, size-8, 8); //bottom left row
			addColW(matrix, 7, aW, CW); //top left and bottom left column
			addColW(matrix, size-8, 0, 8); // top right column
			
			aW += size-8;
			CW += size-8;
			
		}
		
		
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
		if (version > 1) {
	
			int size = matrix.length ;
		
			int size_9 = size-9;
			int size_8 = size-8;
			
			for(int i = 0 ; i < 2 ; ++i) {
			
				addRowB(matrix, size-9, size_9 , size-4);
				addColB(matrix, size_9, size-9, size-4);
				addRowW(matrix, size-8, size_8, size-5);
				addW(matrix, size_8, size-7);
				addB(matrix, size-7, size-7);
				size_9 += 4;
				size_8 += 2;
		
			}
		
		}
	}
	/**
	 * Add the timings patterns
	 * 
	 * @param matrix
	 *            The 2D array to modify
	 */
	public static void addTimingPatterns(int[][] matrix) {
		// TODO Implementer
		int size = matrix.length ;
		
		addColSwitchBW(matrix, 6, 8, size-8);
		addRowSwitchBW(matrix, 8, 6,size-8 );
		
	}
	
	/**
	 * Add the dark module to the matrix
	 * 
	 * @param matrix
	 *            the 2-dimensional array representing the QR code
	 */
	public static void addDarkModule(int[][] matrix) {
		// TODO Implementer
		int size = matrix.length ;
		addB(matrix, 8, size-8);
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
		
		boolean[] formatSequence = QRCodeInfos.getFormatSequence(mask);
		
		int size = matrix.length;
		
		//arguments passed in methods addOnGivenArray() : matrix on which you want to add the data, array from where the data come from, position (column,row), part of the array you want (e.g. from i=0 to i=5 included)
		// and finally if you want them from lower, column or row, to a higher one (0, descending) or the opposite (1 ascending)
		
		//top left corner
		addOnGivenArrayRow(matrix, formatSequence, 0, 8, 0, 5, 0);
		addOnGivenArrayRow(matrix, formatSequence, 7, 8, 6, 7, 0);
		addOnGivenArrayCol(matrix, formatSequence, 8, 7, 8, 8, 0);
		addOnGivenArrayCol(matrix, formatSequence, 8, 5, 9, 14, 1);
		
		//top right corner
		addOnGivenArrayRow(matrix, formatSequence, size-8, 8, 7, 14, 0);
		
		//bottom left corner 
		addOnGivenArrayCol(matrix, formatSequence, 8, size-1, 0, 6, 1);
		
		
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
	
	//same as  before but now switching black and white
	public static void addColSwitchBW (int[][] matrix, int col, int row, int maxRow){
		boolean i = false;
		for (; row < maxRow ; ++row) {
			if(i == false) {
				addB(matrix, col, row);
				i = true;
			} else {
				addW(matrix, col, row);
				i = false;
			}
		}	
	}
	
	public static void addRowSwitchBW (int[][] matrix, int col, int row, int maxCol){
		boolean i = false;
		for (; col < maxCol ; ++col) {
			if(i == false) {
				addB(matrix, col, row);
				i = true;
			} else {
				addW(matrix, col, row);
				i = false;
			}
		}	
	}
	
	public static void addOnGivenArrayRow (int[][] matrix, boolean[] givenArray, int col, int row, int index, int maxIndex, int asc_desc) {
		
		for(; index <= maxIndex ; ++index) {
			
			if(givenArray[index] == true) {
				addB(matrix, col, row);
			} else {
				addW(matrix,col,row);
			}
			
			if( asc_desc == 1) {
				--col;
			} else {
				++col;
			}
		}
		
	}
	
	public static void addOnGivenArrayCol (int[][] matrix, boolean[] givenArray, int col, int row, int index, int maxIndex, int asc_desc) {
		
		for(; index <= maxIndex ; ++index) {
			
			if(givenArray[index] == true) {
				addB(matrix, col, row);
			} else {
				addW(matrix,col,row);
			}
			if (asc_desc == 1) {
				--row;
			} else {
				++row;
			}
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
		
		int maskedBit;
		
		int colPlusRow = (col + row) ; //col Plus Row
		int rowInt_2 = (int) row / 2 ; //makes sure to keep the integer portion of the division
		int colInt_3 = (int) col / 3 ;
		int colTimesRow = (col*row) ; // col Times Row
		int colTimesRowMod2 = colTimesRow % 2 ; // col Times Row Modulo 2
		int colTimesRowMod3 = colTimesRow % 3 ; 
		
		switch(masking) {
		
		case 0 :
			//masking a bit by negating it's binary value and then assigning the method ;
			return (colPlusRow % 2 == 0) ? maskedBit = getVal(!dataBit) : getVal(dataBit) ; 
		
			
		case 1 :
			return (row % 2 == 0) ? maskedBit = getVal(!dataBit) : getVal(dataBit) ;
			
		
		case 2 :
			return (col % 3 == 0) ?  maskedBit = getVal(!dataBit) : getVal(dataBit) ;
			
		
		case 3 : 
			return (colPlusRow % 3 == 0) ? maskedBit = getVal(!dataBit) : getVal(dataBit) ;
		
		
		case 4:
			return ((rowInt_2 + colInt_3) % 2 == 0) ? maskedBit = getVal(!dataBit) : getVal(dataBit) ;
		
		
		case 5 :
			return (colTimesRowMod2 + colTimesRowMod3 == 0) ? maskedBit = getVal(!dataBit) : getVal(dataBit) ;
			
		
		case 6 :
			return ((colTimesRowMod2 + colTimesRowMod3) % 2 == 0) ? maskedBit = getVal(!dataBit) : getVal(dataBit) ;
		
		case 7 : 
			return (( (colPlusRow % 2) + colTimesRowMod3 ) % 2 == 0) ? maskedBit = getVal(!dataBit) : getVal(dataBit) ;
		
		default :
			maskedBit = getVal(dataBit);
		
		}
		
		return maskedBit;
		
	}
	
	
	public static void main(String[] args) {
		int version = Main.VERSION;
		int mask = Main.MASK;
		boolean data[] = DataEncoding.byteModeEncoding(Main.INPUT, version);
		int matrix[][] = constructMatrix(version, mask);
		addDataInformation(matrix, data, mask);
		
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
		
 		int col = matrix.length-1,  row = matrix.length-1 ;
		//System.out.println(matrix.length + ", " + matrix[20][6] );
 		
 		for(int j = 0 ; j < matrix.length-1 ; ++j) {
 		
 			addModule(matrix, col, row, data, mask, 'a');
 			--col;
 			
 		}
 		
		
	}
	
	public static void addModule(int[][] matrix, int col, int row, boolean data[], int mask, char asc_desc) {
		
		if (asc_desc == 'a') { //if flag equals 'a' for "ascending"
			
			for (int i = 0; row > 8 ; ++i) {
				if(i % 2 == 0) {
					
					if( checkEmpty(matrix, col, row) ) { matrix[col][row] = R; }//maskColor(col, row, data[i], mask) ; 
					else { System.out.println("dd"); continue;  }
					--col;
					//System.out.println(col + ", " + row);
				
				} else {
					if( checkEmpty(matrix, col, row) ) { matrix[col][row] =R; }// maskColor(col, row, data[i], mask) ;
					else { System.out.println("dd"); continue;  }
					--row;
					++col;
					//System.out.println(col + ", " + row);
				
				}
			}

		} else if (asc_desc == 'd') { // or if it's 'd' for "descending"
			
			for ( int i = 0; row < matrix.length ; ++i) {
				if(i % 2 == 0) {
					if (checkEmpty(matrix, col, row)) {
					matrix[col][row] = G;}// maskColor(col, row, data[i], mask) ;
					else {System.out.println("dd"); continue; }
					--col;
					//System.out.println(col + ", " + row);
					
				} else {
					if(checkEmpty(matrix, col, row)) {
					matrix[col][row] = G;}//maskColor(col, row, data[i], mask) ;
					else { System.out.println("dd"); continue; }
					++row;
					++col;
					//System.out.println(col + ", " + row);
					
				}
			}
		}
		
	}
	
	public static boolean checkEmpty(int matrix[][], int col, int row) {
		
		//color = maskedBit;
		
		if (matrix[col][row] != 0) {
			return false;
		} else {
			return true;
		}
		
	}
	
	public static int getVal(boolean bit) {
		int value = 0;
		
		if (bit) {
			value = B ;
		} else {
			value = W ;
		}
		
		return value;		
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

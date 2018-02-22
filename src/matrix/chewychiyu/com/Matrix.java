package matrix.chewychiyu.com;

public class Matrix {

	//GLOBAL VARS
	private float[][] marx_;

	//CONSTRUCTORS

	//INSTANCE OF MATRIX FROM PASSING IN 2D ARRAY (FLOAT)
	public Matrix(float[][] _arr){
		marx_ = _arr;
	}

	//INSTANCE OF BLANK MATRIX FROM ROW AND COL
	public Matrix(int _row, int _col) throws MatrixDataException{
		if(_row<1||_col<1){ throw new MatrixDataException("Row and Col must be Positive"); }
		marx_ = new float[_row][_col];
	}

	//INSTANCE OF MATRIX FROM ROW AND COL AND ARRAY (FLOAT)
	public Matrix(int _row, int _col, float[] _arr) throws MatrixDataException{
		if(_row<1||_col<1){ throw new MatrixDataException("Row and Col must be Positive"); }
		if(_row*_col!=_arr.length){throw new MatrixDataException("Row and Col must be Same"); } 
		marx_ = new float[_row][_col];
		int _placeKeeper = 0;
		for(int _r = 0; _r < marx_.length; _r++){
			for(int _c = 0; _c < marx_[0].length; _c++){
				marx_[_r][_c] = _arr[_placeKeeper++];
			}
		}
	}
	//TRANSLATION FROM MARX TO ARRAY
	public float[] to_array(){
		float[] _arr = new float[marx_.length*marx_[0].length];
		int _placeKeeper = 0;
		for(int _r = 0; _r < marx_.length; _r++){
			for(int _c = 0; _c < marx_[0].length; _c++){
				_arr[_placeKeeper++] = marx_[_r][_c];
			}
		}
		return _arr;
	}
	
	
	//ADDITION OF MATRIX BY MATRIX
	public Matrix add_by(Matrix _marx2) throws MatrixDataException{
		if(marx_.length!=_marx2.marx_.length||marx_[0].length!=_marx2.marx_[0].length){ throw new MatrixDataException("Row and Col of both Matrix must be same"); }
		Matrix _add = clone(this);
		for(int _r = 0; _r < _add.marx_.length; _r++){
			for(int _c = 0; _c < _add.marx_[0].length; _c++){
				_add.marx_[_r][_c]+=_marx2.marx_[_r][_c];
			}
		}
		return _add;
	}
	
	//SUBTRACTION OF MATRIX BY MATRIX
	public Matrix sub_by(Matrix _marx2) throws MatrixDataException{
		if(marx_.length!=_marx2.marx_.length||marx_[0].length!=_marx2.marx_[0].length){ throw new MatrixDataException("Row and Col of both Matrix must be same"); }
		Matrix _sub = clone(this);
		for(int _r = 0; _r < _sub.marx_.length; _r++){
			for(int _c = 0; _c < _sub.marx_[0].length; _c++){
				_sub.marx_[_r][_c]-=_marx2.marx_[_r][_c];
			}
		}
		return _sub;
	}
	
	//RETURNS CLONES MATRIX
	public static Matrix clone(Matrix _m) throws MatrixDataException{
		Matrix _clone = new Matrix(_m.marx_.length,_m.marx_[0].length);
		for(int _r = 0; _r < _clone.marx_.length; _r++){
			for(int _c = 0; _c < _clone.marx_[0].length; _c++){
				_clone.marx_[_r][_c] = _m.marx_[_r][_c];
			}
		}
		return _clone;
	}
	
	
	//HADAMARD PRODUCT, direct multiplaction of element to element
	public Matrix hadamard_product(Matrix _marx2) throws MatrixDataException{
		Matrix _multi = clone(this);
		for(int _r = 0; _r < _multi.marx_.length; _r++){
			for(int _c = 0; _c < _multi.marx_[0].length; _c++){
				_multi.marx_[_r][_c]*=_marx2.marx_[_r][_c];
			}
		}
		return _multi;
	}
	
	
	//SCALAR MULTIPLACITON BY VARIABLE
	public Matrix multi_by(float _var) throws MatrixDataException{
		Matrix _multi = clone(this);
		for(int _r = 0; _r < _multi.marx_.length; _r++){
			for(int _c = 0; _c < _multi.marx_[0].length; _c++){
				_multi.marx_[_r][_c]*=_var;
			}
		}
		return _multi;
	}
	
	//DOT PRODUCT MULTIPLACTION BY MATRIX
	public Matrix multi_by(Matrix _marx2) throws MatrixDataException{
		if(_marx2.marx_.length!=marx_[0].length){ throw new MatrixDataException("Row and Col of both Matrix must match up"); }
		float[][] _marxTrans = new float[marx_.length][_marx2.marx_[0].length];
		for(int _rTrans = 0; _rTrans < _marxTrans.length; _rTrans++){
			for(int _cTrans = 0; _cTrans < _marxTrans[0].length; _cTrans++){
				float _product = dot_product(get_col(_cTrans,_marx2),get_row(_rTrans,this));
				_marxTrans[_rTrans][_cTrans] = _product;
			}
		}
		return new Matrix(_marxTrans);
	}
		
	//GET THE ROW OF A MATRIX ( FOR INTERNAL AND EXTERNAL CALCULATIONS ) 
	public static float[] get_row(int _index, Matrix _marx){
		float[] _rows= new float[_marx.marx_[0].length];
		for(int _col = 0; _col < _marx.marx_[0].length; _col++){
			_rows[_col] = _marx.marx_[_index][_col];
		}
		return _rows;
	}
	
	//GET THE COL OF A MATRIX ( FOR INTERNAL AND EXTERNAL CALCULATIONS )
	public static float[] get_col(int _index, Matrix _marx){
		float[] _rows = new float[_marx.marx_.length];
		for(int _row = 0; _row < _marx.marx_.length; _row++){
			_rows[_row] = _marx.marx_[_row][_index];
		}
		return _rows;
	}
	
	
	//DOT PRODUCT CALCULATIONS WITH TWO ARRAY ( FLOAT )
	private float dot_product(float[] _a, float[] _b){
		float _product = 0;
		for(int _index = 0; _index < _a.length; _index++){
			_product += (_a[_index]*_b[_index]);
		}
		return _product;
	}
	
	//GIVE MATRIX RANDOM VALUES BETWEEN MIN AND MAX INTEGERS
	public void randomize(float _min,float _max){
		for(int _r = 0; _r < marx_.length; _r++){
			for(int _c = 0; _c < marx_[0].length; _c++){
				marx_[_r][_c] = _min + (int)(Math.random()*(_max-_min+1));
			}
		}
	}

	//GIVE MATRIX RANDOM VALUES BETWEEN 0-1 FLOATS
	public void randomize(){
		for(int r = 0; r < marx_.length; r++){
			for(int c = 0; c < marx_[0].length; c++){
				marx_[r][c] = (float) Math.random();
			}
		}
	}

	//GET IDENTIY MATRIX RETURNS 2DARRAY ( FLOAT )
	public Matrix get_idenity() throws MatrixDataException{
		float[][] idenity = new float[marx_.length][marx_[0].length];
		if(idenity.length!=idenity[0].length){ throw new MatrixDataException("Row and Col must be the same for idenity");}
		for(int r = 0; r < idenity.length; r++){
			for(int c = 0; c < idenity[0].length; c++){
				idenity[r][c] = (r==c)?1:0;
			}
		}
		return new Matrix(idenity);
	}
	
	//STATIC FUNCTION TO GET DETREMINATE OF MATRIX ( FLOAT )
	public static float get_det(Matrix marx){
		if(marx.marx_.length==2){ 
			return get_det_2x2(marx);
		}else{
			float det = 0;
			for(int col = 0; col < marx.marx_[0].length; col++){
				float a = marx.marx_[0][col];
				float[][] marxDet = new float[marx.marx_.length-1][marx.marx_[0].length-1];
				for(int r = 1; r < marx.marx_.length; r++){
					for(int c = 0; c < marx.marx_[0].length; c++){
						if(c < col){
							marxDet[r-1][c] = marx.marx_[r][c];
						}else if(c > col){
							marxDet[r-1][c-1] = marx.marx_[r][c];
						}
					}
				}	
				det+= ((col%2==1)? -1: 1) * a * get_det(new Matrix(marxDet));
			}
			return det;
		}
	}
	
	//GET THE DETERMINATE AT SPECIFIC POSITION
	private static float get_det_at(int row,int col, Matrix marx) throws MatrixDataException{
		float[] marxDet = new float[(marx.marx_.length-1)*(marx.marx_[0].length-1)];
		int placeKeeper = 0;
		for(int r = 0; r < marx.marx_.length; r++){
			for(int c = 0; c < marx.marx_[0].length; c++){
				if(r!=row&&c!=col){
					marxDet[placeKeeper++] = marx.marx_[r][c];
				}
			}
		}
		Matrix m = new Matrix((marx.marx_.length-1),(marx.marx_[0].length-1),marxDet);
		return get_det(m);
	}
	
	private static float get_det_2x2(Matrix marx){
		return ((marx.marx_[0][0]*marx.marx_[1][1])-(marx.marx_[0][1]*marx.marx_[1][0]));
	}
	
	//TRANSPOSE MATRIX, T OPERATION
	public static Matrix get_transposed(Matrix marx) throws MatrixDataException{
		Matrix transposed = new Matrix(marx.marx_[0].length,marx.marx_.length);
		for(int r = 0; r < transposed.marx_.length; r++){
			for(int c = 0; c < transposed.marx_[0].length; c++){
				transposed.marx_[r][c] = marx.marx_[c][r];
			}
		}
		return transposed;
	}
	
	//COFACTOR OF MATRIX
	public static Matrix get_cofactor(Matrix marx) throws MatrixDataException{
		Matrix cofactor = clone(marx);
		for(int r = 0; r < cofactor.marx_.length; r++){
			for(int c = 0; c < cofactor.marx_[0].length; c++){
				cofactor.marx_[r][c] = (((r%2==0)?1:-1) * ((c%2==0)?1:-1))* get_det_at(r,c,marx);
			}
		}
		return cofactor;
	}
	
	
	//FUNCTION TO GET INVERSE MATRIX , PRESET IF IT IS TWO BY TWO MATRIX
	public static Matrix get_inverse(Matrix marx) throws MatrixDataException{
		float factorDet = (float) (1.0/get_det(marx));
		if(factorDet==Float.POSITIVE_INFINITY){ throw new MatrixDataException("There is invalid Determiante"); }
		if(marx.marx_.length==2){
			Matrix inverse = new Matrix(marx.marx_.clone());
			float placeHolder = inverse.marx_[0][0];
			inverse.marx_[0][0] = inverse.marx_[1][1];
			inverse.marx_[1][1] = placeHolder;
			inverse.marx_[0][1]*=-1;
			inverse.marx_[1][0]*=-1;
			return inverse.multi_by(factorDet);
		}
		return get_transposed(get_cofactor(marx)).multi_by(factorDet);
		
	}
	
	//ACTIVATION COMPRESSIONS FOR BACKPROPAGATION ML (SIGMOID, DERISIGMOID)
	public Matrix activation_sig() throws MatrixDataException{
		Matrix activation = clone(this);
		for(int r = 0; r < activation.marx_.length; r++){
			for(int c = 0; c < activation.marx_[0].length; c++){
				//SIGMOID (x)
				activation.marx_[r][c] = sigmoid(activation.marx_[r][c]);
			}
		}
		return activation;
	}
	
	//(DERISIGMOID) DEACTVATION OF ACTIVATION
	public Matrix deactivation_sig() throws MatrixDataException{
		Matrix activation = clone(this);
		for(int r = 0; r < activation.marx_.length; r++){
			for(int c = 0; c < activation.marx_[0].length; c++){
				//DERISIGMOID , revert data back (y) through rate
				activation.marx_[r][c] = (activation.marx_[r][c]) * (1 - (activation.marx_[r][c]));
			}
		}
		return activation;
	}
	
	//SIGMOID FUNCTION FOR ACTIVATION
	private float sigmoid(float input){
		return (float) (1/(1 + Math.pow(Math.E, -input)));
	}
	
	//VERBOSE OF MATRIX
 	public void verbose(){
		for(int r = 0; r < marx_.length; r++){
			for(int c = 0; c < marx_[0].length; c++){
				if(c!=marx_[0].length-1){
					System.out.print(marx_[r][c] + ", ");
				}else{
					System.out.print(marx_[r][c]);	
				}
			}
			System.out.println();
		}
		System.out.println();
	}


}

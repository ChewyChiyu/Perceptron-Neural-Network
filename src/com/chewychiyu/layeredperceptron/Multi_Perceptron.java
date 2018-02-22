package com.chewychiyu.layeredperceptron;

import matrix.chewychiyu.com.Matrix;
import matrix.chewychiyu.com.MatrixDataException;

public class Multi_Perceptron {

	//GLOBAL VARS

	//nodes per layer
	int input_nodes_, hidden_nodes_, output_nodes_;

	//weight matrix between input and hidden
	Matrix weight_input_to_hidden_;
	//weight matrix between hidden and output
	Matrix weight_hidden_to_output_;
	//bias matrix for hidden 
	Matrix hidden_bias_;
	//bias matrix for output
	Matrix output_bias_;

	
	//LEARNING RATE
	float LEARNING_RATE_ = 0.1f;
	
	//CONSTRUCTOR 
	public Multi_Perceptron(int _input_nodes, int _hidden_nodes, int _output_nodes){

		//Now many nodes per layer
		input_nodes_ = _input_nodes;
		hidden_nodes_ = _hidden_nodes;
		output_nodes_ = _output_nodes;


		try {
			//weight connections between input and hidden layers
			weight_input_to_hidden_ = new Matrix(hidden_nodes_,input_nodes_);
			//weight connections between hidden and ouput layers
			weight_hidden_to_output_ = new Matrix(output_nodes_, hidden_nodes_);
			//randomization of initial weights 0-1 float
			weight_input_to_hidden_.randomize();
			weight_hidden_to_output_.randomize();
			//bias of hidden nodes
			hidden_bias_ = new Matrix(this.hidden_nodes_,1);
			output_bias_ = new Matrix(this.output_nodes_,1);
			//randomization of initial bias 0-1 float
			hidden_bias_.randomize();
			output_bias_.randomize();
		} catch (MatrixDataException _e) {
			_e.printStackTrace();
		}

	}

	//FORWARD INPUT -> OUTPUT
	public float[] predict(float[] _inputs){
		try{
			   // Generating the Hidden Outputs
		    Matrix _input = new Matrix(_inputs.length,1,_inputs);
		    Matrix _hidden = weight_input_to_hidden_.multi_by(_input);
		    _hidden = _hidden.add_by(hidden_bias_);
		    // activation function!
		    _hidden = _hidden.activation_sig();
		    // Generating the output's output!
		    Matrix _output = weight_hidden_to_output_.multi_by(_hidden);
		    _output = _output.add_by(output_bias_);
		    _output = _output.activation_sig();
		    // Sending back to the caller!
		    return _output.to_array();
		}catch(MatrixDataException e) {
			e.printStackTrace();
		}
		return null;
	}

	
	//BACKPROPAGATION WITH GRADENT DECENT
	public void train(float[] inputs_, float[] targets_){
		try{
				// Generating the Hidden Outputs
			    Matrix _inputs = new Matrix(inputs_.length,1,inputs_);
			    Matrix _hidden = weight_input_to_hidden_.multi_by(_inputs);
			    
			    _hidden = _hidden.add_by(hidden_bias_);
			    // activation function!
			    _hidden = _hidden.activation_sig();
			    
			    
			    // Generating the output's output!
			    Matrix _outputs = weight_hidden_to_output_.multi_by(_hidden);
			    _outputs = _outputs.add_by(output_bias_);
			    _outputs = _outputs.activation_sig();
			   
			    // Convert array to matrix object
			    Matrix _targets = new Matrix(targets_.length,1,targets_);
			    // Calculate the error
			    // ERROR = TARGETS - OUTPUTS
			    Matrix _output_errors = _targets.sub_by(_outputs);
			    // let gradient = outputs * (1 - outputs);
			    // Calculate gradient
			    Matrix _gradients = _outputs.deactivation_sig();
			   _gradients = _gradients.hadamard_product(_output_errors);
			   _gradients = _gradients.multi_by(LEARNING_RATE_);

			    // Calculate deltas
			    Matrix _hidden_t = Matrix.get_transposed(_hidden);
			    Matrix _weight_ho_deltas = _gradients.multi_by(_hidden_t);
			 
			    // Adjust the weights by deltas
			    weight_hidden_to_output_ = weight_hidden_to_output_.add_by(_weight_ho_deltas);
			    // Adjust the bias by its deltas (which is just the gradients)
			    output_bias_ =  output_bias_.add_by(_gradients);
			    
			    // Calculate the hidden layer errors
			    Matrix _who_t = Matrix.get_transposed(weight_hidden_to_output_);
			    Matrix _hidden_errors = _who_t.multi_by(_output_errors);

			    // Calculate hidden gradient
			    Matrix _hidden_gradient = _hidden.deactivation_sig();
			    _hidden_gradient = _hidden_gradient.hadamard_product(_hidden_errors);
			    _hidden_gradient = _hidden_gradient.multi_by(LEARNING_RATE_);

			    // Calcuate input->hidden deltas
			    Matrix _inputs_T = Matrix.get_transposed(_inputs);
			    Matrix _weight_ih_deltas =  _hidden_gradient.multi_by(_inputs_T);
			    weight_input_to_hidden_ = weight_input_to_hidden_.add_by(_weight_ih_deltas);
			    // Adjust the bias by its deltas (which is just the gradients)
			    hidden_bias_ = hidden_bias_.add_by(_hidden_gradient);
				  
		}catch(MatrixDataException _e) {
			_e.printStackTrace();
		}
	}
	
}

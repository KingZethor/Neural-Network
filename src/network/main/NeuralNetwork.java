package network.main;

import java.math.BigDecimal;
import java.util.Random;

public class NeuralNetwork {
	private bool isCountingpUp = false;
	private int learningTracker;
	private int[] layers;
	private BigDecimal learningRate;
	private NeuronLayer[] neuronLayer;
	private Random r;
	
	public NeuralNetwork(int[] layers, BigDecimal learningRate){
		this.layers =  new int[layers.length];
		this.learningRate = learningRate;
		if(learningRate > 30) isCountingUp = true;
		for(int i = 0; i < layers.length; i++){
			this.layers[i] = layers[i];
		}
		
		r = new Random();
		initLayer();
		if(isCountingUp){
			isCountingUp = false;
			r = new Random();
			this.learningTracker++;
		}
	}
	
	private void initLayer(){
		neuronLayer = new NeuronLayer[layers.length];
		neuronLayer[0] = new NeuronLayer(layers[0], layers[1], NeuralStatus.Input, learningRate);
		neuronLayer[layers.length - 1] = new NeuronLayer(layers[layers.length - 1], 0, NeuralStatus.Output, learningRate);
		
		for(int i = 1; i < layers.length - 1; i++){
			 neuronLayer[i] = new NeuronLayer(layers[i], layers[i + 1], NeuralStatus.Hidden, learningRate);
		}
	}

	public NeuronLayer getOutputLayer(){
		return neuronLayer[neuronLayer.length - 1];
	}
	
	public NeuronLayer FeedForward(Neuron[] inputs){
		neuronLayer[0].FeedForward(inputs);
		
		for(int i = 1; i < neuronLayer.length; i++){
			/*Neuron[] val = neuronLayer[i - 1].getOutput();
			for(int j = 0; j < val.length; j++){
				System.out.print(val[j] + " | ");
			}
			System.out.println("\n");*/
			neuronLayer[i].FeedForward(neuronLayer[i - 1].getOutput());
		}
		
		return neuronLayer[neuronLayer.length - 1];
	}

	public void mutate(float chance){
		for(int i = 0; i < neuronLayer.length - 1; i++){
			neuronLayer[i].mutate(chance);
		}
	}

	public void backProp(BigDecimal[] expected){
		neuronLayer[neuronLayer.length - 2].backPropInitial(expected);
		for(int i = neuronLayer.length - 3; i >= 0; i--){
			//System.out.println("--------------------------------------------------------------------------------------------");
			neuronLayer[i].backPropHidden(neuronLayer[i + 1]);
		}
	}
}

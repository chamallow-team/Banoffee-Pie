use crate::neural_network::neuron::{Neuron, NeuronId, NeuronLink};
use crate::Rgb;

pub mod neuron;
mod utils;

#[derive(Default)]
pub(crate) struct NeuralNetwork {
    neurons: Vec<Neuron>,
    output_id: Option<NeuronId>
}

impl NeuralNetwork {
    pub fn add_neuron(&mut self, neuron: Neuron) -> NeuronId {
        self.neurons.push(neuron);
        self.neurons.len() - 1
    }
    
    pub fn set_output_id(&mut self, id: NeuronId) {
        self.output_id = Some(id);
    }

    pub fn add_neuron_link(&mut self, from: NeuronId, link: NeuronLink) {
        if self.neurons.get(from).is_none() {
            eprintln!("Impossible de trouver le neurone avec l'index '{from}'");
            return;
        }
        
        self.neurons.get_mut(from)
            .unwrap()
            .add_link(link)
    }
    
    pub fn predict(&self, rgb: Rgb) -> f64 {
        let mut rgb_normalized = Vec::new();
        rgb_normalized.push((rgb.0 / 255) as f64);
        rgb_normalized.push((rgb.1 / 255) as f64);
        rgb_normalized.push((rgb.2 / 255) as f64);
        
        if self.output_id.is_none() {
            panic!("Impossible de prédire sans neurone de sortie")
        }
        
        match self.neurons.get(self.output_id.unwrap()) {
            Some(neuron) => neuron.compute(&rgb_normalized, &self.neurons),
            None => panic!("Impossible de trouver le neurone de sortie")
        }
        
        // let mut inputs = Vec::new();
        // let mut input = self.neurons.first().unwrap();
        // inputs.push(input.compute(rgb_normalized.clone()));
        // input = self.neurons.get(1).unwrap();
        // inputs.push(input.compute(rgb_normalized.clone()));
        // input = self.neurons.get(2).unwrap();
        // inputs.push(input.compute(rgb_normalized.clone()));
        // 
        // let h1 = self.neurons.get(3).unwrap().compute(rgb_normalized.clone());
        // let h2 = self.neurons.get(4).unwrap().compute(rgb_normalized.clone());
        // 
        // self.neurons.get(5).unwrap().compute(vec![h1, h2])
    }
}
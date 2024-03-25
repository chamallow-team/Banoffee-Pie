use crate::neural_network::utils;

pub struct NeuronLink {
    to: NeuronId,
    pub weight: f64
}

impl NeuronLink {
    pub fn new(to: NeuronId, weight: f64) -> Self {
        Self { to, weight }
    }
}

pub type NeuronId = usize;

pub(crate) struct Neuron {
    bias: f64,
    links: Vec<NeuronLink>,
    is_input: bool
}

impl Neuron {
    pub(crate) fn new(bias: f64, is_input: bool) -> Self {
        Self { bias, links: Vec::new(), is_input }
    }

    pub(crate) fn add_link(&mut self, link: NeuronLink) {
        self.links.push(link);
    }

    pub(crate) fn compute(&self, inputs: &Vec<f64>, neurons: &Vec<Neuron>) -> f64 {
        if self.is_input {
            let mut pre_activation: f64 = 0.;

            for link in self.links.iter() {
                let neuron = neurons.get(link.to);
                if neuron.is_none() {
                    print!("Impossible de trouver le neurone avec l'index '{}'", link.to);
                }
                let neuron = neuron.unwrap();
                
                let weight = link.weight;
                let computed = neuron.compute(inputs, neurons);
                
                pre_activation += weight * computed;
            }

            utils::sigmoid(pre_activation)
        } else {
            let mut pre_activation: f64 = 0.;
            for n in inputs.iter() { pre_activation += self.bias - n; }

            utils::sigmoid(pre_activation)
        }
    }
}
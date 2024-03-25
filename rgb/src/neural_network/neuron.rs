use crate::neural_network::utils;

#[derive(Debug, Clone)]
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

#[derive(Debug, Clone)]
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
        if !self.is_input {
            if std::env::var("DEBUG").is_ok() { println!("Hidden neuron or output neuron, computing..."); }

            let mut pre_activation: f64 = 0.0;
            for link in self.links.iter() {
                let neuron = neurons.get(link.to).unwrap();
                let weight = link.weight;
                let computed = neuron.compute(inputs, neurons);
                pre_activation += weight * computed;
            }
            let computed = utils::sigmoid(pre_activation);
            if std::env::var("DEBUG").is_ok() { println!("Computed: {}", computed); }
            computed
        } else {
            if std::env::var("DEBUG").is_ok() { println!("Input neuron, computing..."); }
            let mut pre_activation: f64 = 0.;
            for n in inputs.iter() { pre_activation += self.bias - n; }

            let computed = utils::sigmoid(pre_activation);
            if std::env::var("DEBUG").is_ok() { println!("Computed: {}", computed); }
            computed
        }
    }
}
use std::fmt::{Display, Formatter};
use std::time::Instant;
use rand::Rng;
use rand::rngs::ThreadRng;
use crate::neural_network::NeuralNetwork;
use crate::neural_network::neuron::{Neuron, NeuronLink};

mod neural_network;

#[derive(Debug, Clone, Copy, Eq, PartialEq, Ord, PartialOrd)]
pub(crate) struct Rgb(u8, u8, u8);

impl Display for Rgb {
    fn fmt(&self, f: &mut Formatter<'_>) -> std::fmt::Result {
        write!(f, "rgb({}, {}, {})", self.0, self.1, self.2)
    }
}

impl From<(u8, u8, u8)> for Rgb {
    fn from((r, g, b): (u8, u8, u8)) -> Self {
        Self(r, g, b)
    }
}

fn generate_training_data() -> Vec<Rgb> {
    let mut v = Vec::new();
    for r in 0..255 {
        for g in 0..255 {
            for b in 0..255 {
                v.push(Rgb(r, g, b))
            }
        }
    }
    v
}

fn main() {
    let n = Instant::now();
    let datas = generate_training_data();
    println!("{} données de test généré en {}ms", datas.len(), n.elapsed().as_millis());

    let mut trng = rand::thread_rng();

    let nn = generate_nn(&mut trng);
    
    let predicted = nn.predict((41, 26, 39).into());
    println!("(41, 26, 39) = {predicted:?}")
}

fn gen_rand(trng: &mut ThreadRng) -> f64 {
    trng.gen_range(-1.0..=1.0)
}

fn generate_nn(trng: &mut ThreadRng) -> NeuralNetwork {
    let mut nn = NeuralNetwork::default();
    let id_output = nn.add_neuron(Neuron::new(gen_rand(trng), false));

    let id_input1 = nn.add_neuron(Neuron::new(gen_rand(trng), true));
    let id_input2 = nn.add_neuron(Neuron::new(gen_rand(trng), true));
    let id_input3 = nn.add_neuron(Neuron::new(gen_rand(trng), true));

    let id_h1 = nn.add_neuron(Neuron::new(gen_rand(trng), false));
    let id_h2 = nn.add_neuron(Neuron::new(gen_rand(trng), false));

    nn.add_neuron_link(
        id_output,
        NeuronLink::new(id_h1, gen_rand(trng))
    );
    nn.add_neuron_link(
        id_output,
        NeuronLink::new(id_h2, gen_rand(trng))
    );
    
    nn.add_neuron_link(
        id_h1,
        NeuronLink::new(id_input1, gen_rand(trng))
    );
    nn.add_neuron_link(
        id_h1,
        NeuronLink::new(id_input2, gen_rand(trng))
    );
    nn.add_neuron_link(
        id_h1,
        NeuronLink::new(id_input3, gen_rand(trng))
    );
    
    nn.add_neuron_link(
        id_h2,
        NeuronLink::new(id_input1, gen_rand(trng))
    );
    nn.add_neuron_link(
        id_h2,
        NeuronLink::new(id_input2, gen_rand(trng))
    );
    nn.add_neuron_link(
        id_h2,
        NeuronLink::new(id_input3, gen_rand(trng))
    );
    
    nn.set_output_id(id_output);
    
    nn
}
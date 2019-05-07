package elevador;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Elevador {
	protected static int deslocamentoTotal = 0;
	protected List<Integer> listaParadas;
	protected StatusElevador statusElevador;
	protected int andarAtual;
	protected int numeroAndares;
	protected List<Passageiro> listaPassageiros;
	
	public Elevador (int andarAtual, int numeroAndares) {
		this.listaParadas = new ArrayList<Integer>();
		this.statusElevador = StatusElevador.PARADO;
		this.andarAtual = andarAtual;
		this.numeroAndares = numeroAndares;
		this.listaPassageiros = new ArrayList<Passageiro>();
	}
	
	public Elevador (int andarAtual, int numeroAndares, List<Passageiro> listaPassageiros) {
		this.listaParadas = new ArrayList<Integer>();
		this.statusElevador = StatusElevador.PARADO;
		this.andarAtual = andarAtual;
		this.numeroAndares = numeroAndares;
		this.listaPassageiros = listaPassageiros;
	}
	
	public void iniciarFuncionamento() {
		if(verificarExistenciaDePassageiros()){
			adicionarParadasIniciais();
			
			System.out.println(String.format("Elevador iniciado no andar %d", 
					this.andarAtual));
			while(existemParadasPendentes()) {
				retirarPassageirosDoAndarDoElevador();
				adicionarPassageirosDoAndarAoElevador();
				removerParada(this.andarAtual);
				
				realizarTrajeto();
			}
			
			parar();
		}
	}
	
	
	
	public void subir(int andarDestino) {
		if(this.statusElevador != StatusElevador.SUBINDO) {
			this.statusElevador = StatusElevador.SUBINDO;
		}
		deslocamentoTotal += andarDestino - this.andarAtual;
		this.andarAtual = andarDestino;
		System.out.println(String.format("Elevador subindo até o andar %d", andarDestino));
		System.out.println(String.format("Elevador chegou no andar %d", andarDestino));
	}
	
	public void descer(int andarDestino) {
		if(this.statusElevador != StatusElevador.DESCENDO) {
			this.statusElevador = StatusElevador.DESCENDO;
		}
		deslocamentoTotal += Math.abs(andarDestino - this.andarAtual);
		this.andarAtual = andarDestino;
		
		System.out.println(String.format("Elevador descendo até o andar %d", andarDestino));
		System.out.println(String.format("Elevador chegou no andar %d", andarDestino));
	}
	
	public void parar() {
		this.statusElevador = StatusElevador.PARADO;
		System.out.println(String.format("Elevador parado no andar %s. Deslocamento total: %d andares",
				this.andarAtual, deslocamentoTotal));
	}
	
	private boolean verificarExistenciaDePassageiros() {
		if(this.listaPassageiros != null && this.listaPassageiros.size() > 0) {
			return true;
		}
		System.out.println("Elevador não irá iniciar sem passageiros");
		return false;
	}
	
	private void adicionarParadasIniciais() {
		List<Integer> listaParadasOrigem = this.listaPassageiros.stream().map(p -> p.andarOrigem)
				.collect(Collectors.toList());
		this.listaParadas.addAll(listaParadasOrigem);
		this.listaParadas = this.listaParadas.stream().distinct().collect(Collectors.toList());
	}
	
	private boolean existemParadasPendentes() {
		if (this.listaParadas.size() > 0) {
			return true;
		}
		return false;
	}
	
	/**
	 * Adiciona ao elevador (altera o status) os passageiros que estão o aguardando no andar atual, e também os seus respectivos destinos
	 */
	private void adicionarPassageirosDoAndarAoElevador() {
		List<Passageiro> listaPassageirosEntrarElevador =  this.listaPassageiros.stream()
				.filter(p -> p.andarOrigem == this.andarAtual && p.statusPassageiro == StatusPassageiro.AGUARDANDO_ELEVADOR)
				.collect(Collectors.toList());
		if(listaPassageirosEntrarElevador != null && listaPassageirosEntrarElevador.size() > 0) {
			for(Passageiro passageiro : listaPassageirosEntrarElevador) {
				passageiro.entrarNoElevador();
				adicionarParada(passageiro.andarDestino);
			}
		}
	}
	
	/**
	 * Retira do elevador (altera o status) os passageiros que chegaram ao seu destino no andar atual
	 */
	private void retirarPassageirosDoAndarDoElevador() {
		List<Passageiro> listaPassageirosSairElevador =  this.listaPassageiros.stream()
				.filter(p -> p.andarDestino == this.andarAtual && p.statusPassageiro == StatusPassageiro.NO_ELEVADOR)
				.collect(Collectors.toList());
		if(listaPassageirosSairElevador != null && listaPassageirosSairElevador.size() > 0) {
			for(Passageiro passageiro : listaPassageirosSairElevador) {
				passageiro.sairDoElevador();
			}
		}
	}
	
	private void adicionarParada(int andarAdicionado) {
		if(!this.listaParadas.contains(andarAdicionado)) {
			this.listaParadas.add(andarAdicionado);
		}
	}
	
	private void removerParada(int andarRemovido) {
		if(this.listaParadas.contains(andarRemovido)) {
			int indexAndarRemovido = this.listaParadas.indexOf(andarRemovido);
			this.listaParadas.remove(indexAndarRemovido);
		}
	}
	
	private void realizarTrajeto() {
		if(existemParadasPendentes()) {
			if(this.statusElevador == StatusElevador.DESCENDO) {
				if(this.andarAtual == 0 || !possuiDestinoAbaixo()) {
					this.statusElevador = StatusElevador.SUBINDO;
				} else {
					descer(retornarProximoDestinoAbaixo());
				}
			} else if (this.statusElevador == StatusElevador.SUBINDO) {
				if(this.andarAtual == this.numeroAndares || !possuiDestinoAcima()) {
					this.statusElevador = StatusElevador.DESCENDO;
				} else {
					subir(retornarProximoDestinoAcima());
				}
			} else {
				int proximoDestino = retornarDestinoMaisProximo(this.listaParadas);
				if (proximoDestino > this.andarAtual) {
					subir(proximoDestino);
				} else if (proximoDestino < this.andarAtual) {
					descer(proximoDestino);
				}
			}
		}
	}
	
	private boolean possuiDestinoAcima() {
		for(int destino : this.listaParadas) {
			if(destino > this.andarAtual) {
				return true;
			}
		}
		return false;
	}
	
	private boolean possuiDestinoAbaixo() {
		for(int destino : this.listaParadas) {
			if(destino < this.andarAtual) {
				return true;
			}
		}
		return false;
	}
	
	private int retornarProximoDestinoAcima() {
		List<Integer> listaDestinosAcima = this.listaParadas.stream()
				.filter(p -> p > this.andarAtual)
				.collect(Collectors.toList());
		
		return retornarDestinoMaisProximo(listaDestinosAcima);
	}
	
	private int retornarProximoDestinoAbaixo() {
		List<Integer> listaDestinosAbaixo = this.listaParadas.stream()
				.filter(p -> p < this.andarAtual)
				.collect(Collectors.toList());
		
		return retornarDestinoMaisProximo(listaDestinosAbaixo);
	}
	
	private int retornarDestinoMaisProximo(List<Integer> listaDestinos) {
		int destinoMaisProximo = this.andarAtual;
		int menorDeslocamento = this.numeroAndares + 1;
		
		for(int destino : listaDestinos) {
			int deslocamento = Math.abs(destino - this.andarAtual);
			if(deslocamento < menorDeslocamento) {
				destinoMaisProximo = destino;
				menorDeslocamento = deslocamento;
			}
		}
		
		return destinoMaisProximo;
	}
}

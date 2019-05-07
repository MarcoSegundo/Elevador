package elevador;

public class Passageiro {
	protected String nomePassageiro;
	protected int andarOrigem;
	protected int andarDestino;
	protected StatusPassageiro statusPassageiro;
	
	public Passageiro (String nomePassageiro, int andarOrigem, int andarDestino) {
		this.nomePassageiro = nomePassageiro;
		this.andarOrigem = andarOrigem;
		this.andarDestino = andarDestino;
		this.statusPassageiro = StatusPassageiro.AGUARDANDO_ELEVADOR;
	}
	
	public void entrarNoElevador() {
		this.statusPassageiro = StatusPassageiro.NO_ELEVADOR;
		System.out.println(String.format("Passageiro %s entrou no Elevador", this.nomePassageiro));
	}
	
	public void sairDoElevador() {
		this.statusPassageiro = StatusPassageiro.CHEGOU_AO_DESTINO;
		System.out.println(String.format("Passageiro %s chegou ao destino", this.nomePassageiro));
	}
}

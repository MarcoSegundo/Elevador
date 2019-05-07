import elevador.Elevador;
import elevador.Passageiro;

public class QuestaoDois {

	public static void main(String[] args) {
		Passageiro passageiroUm = new Passageiro("Um", 8, 0);
		Passageiro passageiroDois = new Passageiro("Dois", 0, 7);
		Passageiro passageiroTres = new Passageiro("Três", 3, 15);
		
		Elevador elevador = new Elevador(3, 20);
		System.out.println("Elevador iniciado no andar 3");
		
		passageiroTres.entrarNoElevador();
		elevador.descer(0);
		passageiroDois.entrarNoElevador();
		elevador.subir(7);
		passageiroDois.sairDoElevador();
		elevador.subir(8);
		passageiroUm.entrarNoElevador();
		elevador.subir(15);
		passageiroTres.sairDoElevador();
		elevador.descer(0);
		passageiroUm.sairDoElevador();
		elevador.parar();
	}

}

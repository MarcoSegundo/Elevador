import elevador.Elevador;
import elevador.Passageiro;

public class QuestaoUm {

	public static void main(String[] args) {
		Passageiro passageiroUm = new Passageiro("Um", 8, 0);
		Passageiro passageiroDois = new Passageiro("Dois", 0, 10);
		
		Elevador elevador = new Elevador(5, 10);
		
		System.out.println("Elevador iniciado no andar 5");
		
		elevador.subir(8);
		passageiroUm.entrarNoElevador();
		elevador.descer(0);
		passageiroUm.sairDoElevador();
		passageiroDois.entrarNoElevador();
		elevador.subir(10);
		passageiroDois.sairDoElevador();
		elevador.parar();
	}
}

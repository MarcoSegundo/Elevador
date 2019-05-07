import java.util.ArrayList;
import java.util.List;

import elevador.Elevador;
import elevador.Passageiro;

public class QuestaoTres {

	public static void main(String[] args) {
//		Passageiro passageiroUm = new Passageiro("Um", 8, 0);
//		Passageiro passageiroDois = new Passageiro("Dois", 0, 10);
//		
//		List<Passageiro> listaPassageiros = new ArrayList<Passageiro>();
//		listaPassageiros.add(passageiroUm);
//		listaPassageiros.add(passageiroDois);
//		
//		Elevador elevador = new Elevador(5, 10, listaPassageiros);
		
		Passageiro passageiroUm = new Passageiro("Um", 8, 0);
		Passageiro passageiroDois = new Passageiro("Dois", 0, 7);
		Passageiro passageiroTres = new Passageiro("Três", 3, 15);
		
		List<Passageiro> listaPassageiros = new ArrayList<Passageiro>();
		listaPassageiros.add(passageiroUm);
		listaPassageiros.add(passageiroDois);
		listaPassageiros.add(passageiroTres);
		
		Elevador elevador = new Elevador(3, 20, listaPassageiros);
		
		elevador.iniciarFuncionamento();

	}

}

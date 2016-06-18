package controle;


import java.util.HashMap;
import java.util.Map;

import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.nxt.UltrasonicSensor;
import lejos.robotics.navigation.DifferentialPilot;

public class Main {

	
	public static void main(String[] args) throws Exception {
		UltrasonicSensor sonic = new UltrasonicSensor(SensorPort.S1);
		Main main = new Main();
		
		int [] distancia = new int[362];
		Map<Integer, Integer> distancias = new HashMap<Integer,Integer>();
		Map<Integer, String> pontos = new HashMap<Integer, String>();
		
		int cont=0;
		int angle = 0;
				while(cont < 360) {
					if(sonic.getDistance() != 255){
						System.out.println("Pegando distancia..");
						distancia[cont] = sonic.getDistance();
						System.out.println(distancia[cont]);
						distancias.put(angle,distancia[cont]);//}Salvando a distancia no mapa
						System.out.println("Salvou ponto..");
						Thread.sleep(2000);
						main.rotate(90);
						System.out.println("Girou..");
						angle += 90;
						cont+=90;
					}
				}
				System.out.println("Definindo pontos..");
				pontos = geraPontos(distancias);
				Thread.sleep(2000);
				System.out.println("Resultado dos pontos....:\n");
				System.out.println(pontos.toString());
				
	}
	
	private static Map<Integer, String> geraPontos(Map<Integer, Integer> distancias) {
		int cont=0;
		Map<Integer, String> pontos = new HashMap<Integer, String>();
		while(cont < 360){
			if(cont == 0 || cont == 180){
				pontos.put(cont, 0+","+distancias.get(cont));
			} else{
				pontos.put(cont, distancias.get(cont)+","+0);
			}
			cont +=90;
			
		}
		
		return pontos;
		
	}

	public void rotate(double angle){
		DifferentialPilot pilot = new DifferentialPilot(36,126,Motor.B, Motor.A,true);
		pilot.setRotateSpeed(50);
		pilot.rotate(angle*1.6);	//Valor para ajustar erro de rotação.
	}

}

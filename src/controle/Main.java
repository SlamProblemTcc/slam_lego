package controle;

import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.nxt.UltrasonicSensor;
import lejos.robotics.navigation.DifferentialPilot;

public class Main {
	private static double [] pontosX = new double[15];
	private static double [] pontosY = new double[15];
	
	public static void main(String[] args) throws Exception {
		UltrasonicSensor sonic = new UltrasonicSensor(SensorPort.S1);
		Main main = new Main();
		
		int cont=0;
		double angle = 0;
		System.out.println("COMEÇOU");
				while(angle < 360) {
					if(sonic.getDistance() != 255){
						System.out.println("Pegando distancia.." + sonic.getDistance());
						geraPontos(sonic.getDistance(), angle, cont);
						System.out.println("Salvou ponto..");
						Thread.sleep(2000);
						main.rotate(45);
						angle +=45;
						System.out.println("Girou..");
						System.out.println("Angulo atual:" +angle + "\ncont atual:"+cont);
						cont++;
					}
				}
				
				System.out.println("O resultado dos pontos e: " );
				for(int i=0; i<pontosX.length ; i++){
					System.out.println("(X:"+pontosX[i]+", Y="+pontosY[i]+")");
				}
				
	}
	
	private static void geraPontos(int distancia, double angle, int cont) throws InterruptedException {
		pontosX[cont] = round(Math.sin(Math.PI/180*angle)*distancia, 0);
		pontosY[cont] = round(Math.cos(Math.PI/180*angle)*distancia, 0);
		System.out.println("Ponto criado: x="+pontosX[cont] +";y="+pontosY[cont]);
		Thread.sleep(600);
	}
	
	public static double round(double value, int places) {
	    if (places < 0) throw new IllegalArgumentException();

	    long factor = (long) Math.pow(10, places);
	    value = value * factor;
	    long tmp = Math.round(value);
	    return (double) tmp / factor;
	}

	public double rotate(double angle){
		DifferentialPilot pilot = new DifferentialPilot(36,126,Motor.B, Motor.A,true);
		pilot.setRotateSpeed(50);
		pilot.rotate(angle*1.7);	//Valor para ajustar erro de rotação.
		return angle;
	}

}

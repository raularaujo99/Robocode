package cyber;
import robocode.*;
import java.awt.Color;

	public class Cyber extends AdvancedRobot
	{
	// Variável para controlar o que fazer quando se iniciar a partida
	boolean inicio = true;
	boolean GunD = true;
	
	// Faz sempre:
	public void run() {
		// Cor do robô:
		setColors(Color.black, Color.black, Color.black);
		while(true) {
		// Sempre gira o canhão se não estiver em uma outra função:
		if (GunD == true)
	            turnGunRight(180);
		else 
	            turnGunLeft(180);
		}
	}
	// Ao escanear um robô:
	public void onScannedRobot(ScannedRobotEvent e) {
		// Verifica se não é alguém do seu time, se sim, retorna:
		String name = e.getName();
		if (name.indexOf("G2M") != -1  || name.indexOf("Border") != -1) {
	            return;
		}
		setTurnRight(e.getBearing());
		setAhead(e.getDistance() - 10);
	
		//  Pega o valor do quanto o canhão deve virar em relação ao seu ângulo atual, usando uma função para normalizar o ângulo e buscar o menor caminho. O cálculo dentro do parênteses pega o 
		// ângulo do inimigo em relação a tela, e adiciona o seu ângulo menos o ângulo do radar.
		double mira = normalRelativeAngle((e.getBearing() + (getHeading() - getRadarHeading())));
		// Dá o comando para o canhão virar em relação ao valor obtido pela função de normalizar ângulos e mira para o inimigo:
		turnGunRight(mira);
		// Atira com potência máxima
		if (e.getDistance() < 80) {
		fire(3);
		GunD = !GunD;
		}
		
	}
	
	// Ao levar um tiro de um inimigo 
	public void onHitByBullet(HitByBulletEvent e) {
	
	}
	
	// Quando bater em uma parede...
	public void onHitWall(HitWallEvent e) {
	// Quando bater na parede, anda para trás, vira para a esquerda e anda para frente.
		setBack(20);
		setTurnLeft(90);
		setAhead(20);
	}
	
	// Quando um tiro seu acertar
	public void onBulletHit(BulletHitEvent event) {
	
	}
	
	// Quando a bala se perde (não acerta nenhum robô)
	public void onBulletMissed(BulletMissedEvent event) {
	
	}
	
	// Normalização dos ângulos
	public double normalRelativeAngle(double angle) {
			// Se o ângulo estiver entre -180° e 180° retorna o ângulo, por não ser preciso normalizar.
			if (angle > -180 && angle <= 180) {
				return angle;
			}
			// Cria uma nova variável para dar retorno com o novo valor.
			double fixedAngle = angle;
	 
			// Enquanto menos que -180° adiciona 360° para normalizar o ângulo ao sistema.
			while (fixedAngle <= -180) {
				fixedAngle += 360;
			}
			// Enquanto maior que 180° diminiui 360° para pegar o ângulo equivalente.
			while (fixedAngle > 180) {
				fixedAngle -= 360;
			}
			// Retorna o ângulo obtido.
			return fixedAngle;
		}
		
		// Se o Robô bater em um inimigo: 
		public void onHitRobot(HitRobotEvent e) {
			// Recua e vira para a esquerda se for da equipe
			String name = e.getName();
			if (name.indexOf("G2M") != -1  || name.indexOf("Border") != -1) {
			back(60);
			turnRight(80);
			}
		}
	}